package br.com.coletor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorPlanilhaCega;
import br.com.coletor.dao.DAOColetorPlanilhaCegaContagem;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;
import br.com.coletor.model.ColetorPlanilhaCega;
import br.com.coletor.model.ColetorPlanilhaCegaContagem;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorPlanilhaCegaContagemRequest;
import br.com.coletor.response.ColetorPlanilhaCegaContagemResponse;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.UtilData;

@Path("/coletor-pc-contagem")
public class PlanilhaCegaContagemResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorPlanilhaCegaContagemResponse postAutenticar(ColetorPlanilhaCegaContagemRequest request){
		
		ColetorPlanilhaCegaContagemResponse response = new ColetorPlanilhaCegaContagemResponse();
		
		System.out.println("[PlanilhaCegaContagemResources]");
		System.out.println("[PlanilhaCegaContagemResources][token] " + request.getLogin());
		//System.out.println("senha: " + request.getSenha());
		
		Session session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();
		
		try {
			
			session.beginTransaction().begin();
			session.getTransaction().setTimeout(10000);
			
			DAOUsuarioColetor daoUsuarioColetor = new DAOUsuarioColetor(session);
			UsuarioColetor usuario = daoUsuarioColetor.autenticar(request.getLogin(), request.getSenha());
			
			if(usuario == null){
				throw new Exception("Login ou senha invalida!");
			}
			
			DAOColetorPlanilhaCega daoColetorPlanilhaCega = new DAOColetorPlanilhaCega(session);
			ColetorPlanilhaCega coletorPlanilhaCega = daoColetorPlanilhaCega.carregar(request.getColetorPlanilhaCegaId());
			
			if(coletorPlanilhaCega == null) {
				response.setAcao(ColetorPlanilhaCegaContagemResponse.ACAO_EXCLUIR);
				throw new Exception("Planilha Cega não existe!");
			}
			
			if(coletorPlanilhaCega.getStatus().equals(ColetorPlanilhaCega.STATUS_FINALIZADO)) {
				response.setAcao(ColetorPlanilhaCegaContagemResponse.ACAO_EXCLUIR);
				throw new Exception("Não é possível processar contagem, a Planilha Cega foi finalizada!");
			}
			
			if(coletorPlanilhaCega.getStatus().equals(ColetorPlanilhaCega.STATUS_EXCLUIDO)) {
				response.setAcao(ColetorPlanilhaCegaContagemResponse.ACAO_EXCLUIR);
				throw new Exception("Não é possível processar contagem, a Planilha Cega foi excluída!");
			}
			
			List<EspelhoColetorPlanilhaCegaContagem> transmitidos = new ArrayList<EspelhoColetorPlanilhaCegaContagem>();
			Date dtInicio = null;
			DAOColetorPlanilhaCegaContagem daoColetorPlanilhaCegaContagem = new DAOColetorPlanilhaCegaContagem(session);
			for(EspelhoColetorPlanilhaCegaContagem rs : request.getContagem()) {
				
				if(coletorPlanilhaCega.getDtInicio() == null) {
					Date dtLeitura = UtilData.formatarStringParaData(rs.getDtLeitura(), UtilData.FORMATO_DATA_HORA);
					if(dtInicio == null || dtInicio.getTime() < dtLeitura.getTime()) {
						dtInicio = dtLeitura;
					}
				}
				daoColetorPlanilhaCegaContagem.inserir(rs);
				transmitidos.add(rs);
			}
			
			session.flush();
			
			if(dtInicio != null && coletorPlanilhaCega.getDtInicio() == null) {
				daoColetorPlanilhaCega.updateEmConferencia(coletorPlanilhaCega.getId(), dtInicio);
			}
			
			List<EspelhoColetorPlanilhaCegaContagem> excluidos = new ArrayList<EspelhoColetorPlanilhaCegaContagem>();
			for(ColetorPlanilhaCegaContagem rs : daoColetorPlanilhaCegaContagem.listar(request.getColetorPlanilhaCegaId(), true)) {
				excluidos.add(new EspelhoColetorPlanilhaCegaContagem(rs));
			}
			
			response.setStatus(ColetorPlanilhaCegaContagemResponse.SUCESSO);
			response.setTransmitidos(transmitidos);
			response.setExcluidos(excluidos);
			
			ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN(session);
			ColetorPCFB coletorPCFB = coletorPCFBRN.carregar(coletorPlanilhaCega.getId());
			
			UsuarioFB usuarioFB = new UsuarioFB(coletorPCFB.getUsuarioId());
			coletorPCFBRN.finalizar(coletorPCFB, usuarioFB, session);
			
			session.getTransaction().commit();
			
			System.out.println("[PlanilhaCegaContagemResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorPlanilhaCegaContagemResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if (session != null && session.isOpen()) {
		        try {
		            if (session.getTransaction() != null && session.getTransaction().isActive()) {
		                session.getTransaction().rollback();
		            }
		        } catch (TransactionException e2) {
		            e2.printStackTrace();
		        }
		    }
			
			System.out.println("[PlanilhaCegaContagemResources][ERRO]");
			
		}finally{
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			System.out.println("[PlanilhaCegaContagemResources][CLOSE]");
		}
		
		return response;
		
	}

}
