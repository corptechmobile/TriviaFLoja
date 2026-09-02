package br.com.coletor;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorRomaneio;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.ColetorRomaneio;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorRomaneioCancelarRequest;
import br.com.coletor.response.ColetorRomaneioCancelarResponse;
import br.com.coletor.response.ColetorRomaneioResponse;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;

@Path("/coletor-romaneio-cancelar")
public class ColetorRomaneioCancelarResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorRomaneioCancelarResponse postAutenticar(ColetorRomaneioCancelarRequest request){
		
		ColetorRomaneioCancelarResponse response = new ColetorRomaneioCancelarResponse();
		
		System.out.println("[ColetorRomaneioCancelarResources]");
		System.out.println("[ColetorRomaneioCancelarResources][token] " + request.getLogin());
		
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
			ColetorRomaneio romaneio = daoColetorRomaneio.carregar(request.getId());

			// TODO validar exceptions
			if(romaneio == null) {
				throw new RNException("Romaneio não existe.");
			}
			
			if(romaneio.getStatus().equals(ColetorRomaneio.STATUS_EM_CONFERENCIA) == false) {
				throw new RNException(String.format("O Romaneio não pode ser Cancelado. Romaneio com status: %s.", romaneio.getStatusDesc()));
			}
			
			if(romaneio.getUsuarioConfId() != null && romaneio.getUsuarioConfId().equals(usuario.getId()) == false) {
				throw new RNException(String.format("Romaneio está Em Conferência por: %s", romaneio.getUsuarioConfNome()));
			}
			
			daoColetorRomaneio.updateCancelado(romaneio.getId(), new Date(), usuario.getId());
			
			response.setStatus(ColetorRomaneioResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorRomaneioCancelarResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorRomaneioResponse.ERRO);
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
			
			System.out.println("[ColetorRomaneioCancelarResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorRomaneioCancelarResources][CLOSE]");
			
		}
		
		return response;
		
	}

}