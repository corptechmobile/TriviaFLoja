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

import br.com.coletor.dao.DAOColetorInv;
import br.com.coletor.dao.DAOColetorInvContagem;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorInvContagem;
import br.com.coletor.model.ColetorInv;
import br.com.coletor.model.ColetorInvContagem;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorInvContagemRequest;
import br.com.coletor.response.ColetorInvContagemResponse;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.UtilData;

@Path("/coletor-inv-contagem")
public class ColetorInvContagemResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorInvContagemResponse postAutenticar(ColetorInvContagemRequest request){
		
		ColetorInvContagemResponse response = new ColetorInvContagemResponse();
		
		System.out.println("[ColetorInvContagemResources]");
		System.out.println("[ColetorInvContagemResources][token] " + request.getLogin());
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
			
			DAOColetorInv daoColetorInv = new DAOColetorInv(session);
			ColetorInv coletorInv = daoColetorInv.carregar(request.getColetorInvId());
			
			if(coletorInv == null) {
				throw new Exception("Inventário/Coletor não existe!");
			}
			
			if(coletorInv.getStatus().equals(ColetorInv.STATUS_FINALIZADO)) {
				throw new Exception("Não é possível processar contagem, o Inventário/Coletor foi finalizado!");
			}
			
			
			
			List<EspelhoColetorInvContagem> transmitidos = new ArrayList<EspelhoColetorInvContagem>();
			Date dtInicio = null;
			DAOColetorInvContagem daoColetorInvContagem = new DAOColetorInvContagem(session);
			for(EspelhoColetorInvContagem rs : request.getContagem()) {
				
				if(coletorInv.getDtInicio() == null) {
					Date dtLeitura = UtilData.formatarStringParaData(rs.getDtLeitura(), UtilData.FORMATO_DATA_HORA);
					if(dtInicio == null || dtInicio.getTime() < dtLeitura.getTime()) {
						dtInicio = dtLeitura;
					}
				}
				daoColetorInvContagem.inserir(rs);
				transmitidos.add(rs);
			}
			
			if(dtInicio != null && coletorInv.getDtInicio() == null) {
				daoColetorInv.updateEmConferencia(coletorInv.getId(), dtInicio);
			}
			
			List<EspelhoColetorInvContagem> excluidos = new ArrayList<EspelhoColetorInvContagem>();
			for(ColetorInvContagem rs : daoColetorInvContagem.listar(request.getColetorInvId(), true)) {
				excluidos.add(new EspelhoColetorInvContagem(rs));
			}
			
			response.setStatus(ColetorInvContagemResponse.SUCESSO);
			response.setTransmitidos(transmitidos);
			response.setExcluidos(excluidos); 
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorInvContagemResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorInvContagemResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorInvContagemResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorInvContagemResources][CLOSE]");
			
		}
		
		return response;
		
	}

}