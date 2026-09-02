package br.com.coletor;

import java.util.List;

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
import br.com.coletor.request.ColetorRomaneioRequest;
import br.com.coletor.response.ColetorRomaneioResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-romaneio")
public class ColetorRomaneioResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorRomaneioResponse postAutenticar(ColetorRomaneioRequest request){
		
		ColetorRomaneioResponse response = new ColetorRomaneioResponse();
		
		System.out.println("[RomaneioColetorResources]");
		System.out.println("[RomaneioColetorResources][token] " + request.getLogin());
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
			
			DAOColetorRomaneio daoRomaneioColetor = new DAOColetorRomaneio(session);
			List<ColetorRomaneio> romaneios = daoRomaneioColetor.listar(usuario.getId(), request.getNumFilter());
			response.setRomaneios(romaneios);
			response.setStatus(ColetorRomaneioResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[RomaneioColetorResources][SUCESSO]");
			
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
			
			System.out.println("[RomaneioColetorResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[RomaneioColetorResources][CLOSE]");
			
		}
		
		return response;
		
	}
	
}