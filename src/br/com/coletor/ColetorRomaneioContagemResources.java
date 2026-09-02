package br.com.coletor;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorRomaneio;
import br.com.coletor.dao.DAOColetorRomaneioContagem;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorRomaneioContagem;
import br.com.coletor.model.ColetorRomaneio;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.model.dto.ColetorRomaneioCorteDTO;
import br.com.coletor.request.ColetorRomaneioContagemRequest;
import br.com.coletor.response.ColetorRomaneioContagemResponse;
import br.com.webapp.model.fb.romaneio.RomaneioFBRN;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;

@Path("/coletor-romaneio-contagem")
public class ColetorRomaneioContagemResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorRomaneioContagemResponse postAutenticar(ColetorRomaneioContagemRequest request){
		
		ColetorRomaneioContagemResponse response = new ColetorRomaneioContagemResponse();
		
		System.out.println("[ColetorRomaneioContagemResources]");
		System.out.println("[ColetorRomaneioContagemResources][token] " + request.getLogin());
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
			
			DAOColetorRomaneio daoColetorRomaneio = new DAOColetorRomaneio(session);
			ColetorRomaneio romaneio = daoColetorRomaneio.carregar(request.getColetorRomaneioId());
			
			if(romaneio == null) {
				throw new Exception("Romaneio não existe!");
			}
			
			if(romaneio.getStatus().equals(ColetorRomaneio.STATUS_EM_CONFERENCIA) == false) {
				throw new Exception(String.format("Não é possível processar contagem, o Romaneio com status: %s!", romaneio.getStatusDesc()));
			}
			
			if(romaneio.getUsuarioConfId() != null && romaneio.getUsuarioConfId().equals(usuario.getId()) == false) {
				throw new RNException(String.format("Romaneio está Em Conferência por: %s", romaneio.getUsuarioConfNome()));
			}
			
			DAOColetorRomaneioContagem daoColetorRomaneioContagem = new DAOColetorRomaneioContagem(session);
			
			daoColetorRomaneioContagem.delete(request.getColetorRomaneioId(), usuario.getId());
			for(EspelhoColetorRomaneioContagem rs : request.getContagem()) {
				daoColetorRomaneioContagem.inserir(rs);
			}
			
			daoColetorRomaneioContagem.integracao(request.getColetorRomaneioId());
			
			ColetorRomaneioCorteDTO countCortes = daoColetorRomaneio.countItensCortes(romaneio.getId());
			if(countCortes != null) {
				daoColetorRomaneio.updateConferidoCorte(romaneio.getId(), new Date(), usuario.getId());
			}else{
				daoColetorRomaneio.updateConferido(romaneio.getId(), new Date(), usuario.getId());
				daoColetorRomaneio.updateFinalizado(romaneio.getId(), new Date(), usuario.getId());
				new RomaneioFBRN().integracaoRomaneio(romaneio.getId());
			}
			
			response.setStatus(ColetorRomaneioContagemResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorRomaneioContagemResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorRomaneioContagemResponse.ERRO);
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
			
			System.out.println("[ColetorRomaneioContagemResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorRomaneioContagemResources][CLOSE]");
			
		}
		
		return response;
		
	}

}
