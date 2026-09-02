package br.com.coletor;

import java.util.Date;
import java.util.List;

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
import br.com.coletor.model.ColetorRomaneioItem;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorRomaneioSelectedRequest;
import br.com.coletor.response.ColetorRomaneioResponse;
import br.com.coletor.response.ColetorRomaneioSelectedResponse;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;

@Path("/coletor-romaneio-selected")
public class ColetorRomaneioSelectedResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorRomaneioSelectedResponse postAutenticar(ColetorRomaneioSelectedRequest request){
		
		ColetorRomaneioSelectedResponse response = new ColetorRomaneioSelectedResponse();
		
		System.out.println("[ColetorRomaneioSelectedResources]");
		System.out.println("[ColetorRomaneioSelectedResources][token] " + request.getLogin());
		
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
			DAOColetorRomaneioContagem daoColetorRomaneioContagem = new DAOColetorRomaneioContagem(session); 
			ColetorRomaneio romaneio = daoColetorRomaneio.carregar(request.getId());

			// TODO validar exceptions
			if(romaneio == null) {
				throw new RNException("Romaneio não existe.");
			}
			
			if(romaneio.getStatus().equals(ColetorRomaneio.STATUS_EM_ABERTO) == false 
					&& romaneio.getStatus().equals(ColetorRomaneio.STATUS_EM_CONFERENCIA) == false) {
				throw new RNException(String.format("Romaneio não está com status: %s.", romaneio.getStatusDesc()));
			}
			
			if(romaneio.getUsuarioConfId() != null && romaneio.getUsuarioConfId().equals(usuario.getId()) == false) {
				throw new RNException(String.format("Romaneio está Em Conferência por: %s", romaneio.getUsuarioConfNome()));
			}
			
			if(romaneio.getDtInicio() == null) {
				daoColetorRomaneio.updateEmConferencia(romaneio.getId(), new Date(), usuario.getId());
			}
			
			List<ColetorRomaneioItem> itens = daoColetorRomaneio.listarItens(request.getId());
			List<EspelhoColetorRomaneioContagem> contagem = daoColetorRomaneioContagem.convertEspelho(daoColetorRomaneioContagem.listar(request.getId(), usuario.getId()));
			
			response.setRomaneio(romaneio);
			response.setItens(itens);
			response.setContagem(contagem);
			response.setStatus(ColetorRomaneioResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorRomaneioSelectedResources][SUCESSO]");
			
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
			
			System.out.println("[ColetorRomaneioSelectedResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorRomaneioSelectedResources][CLOSE]");
			
		}
		
		return response;
		
	}

}