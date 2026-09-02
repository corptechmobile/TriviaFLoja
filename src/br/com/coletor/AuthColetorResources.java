package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoUsuarioColetor;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.AuthColetorRequest;
import br.com.coletor.response.AuthColetorResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/autenticar-coletor")
public class AuthColetorResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AuthColetorResponse postAutenticar(AuthColetorRequest request){
		
		AuthColetorResponse response = new AuthColetorResponse();
		
		System.out.println("[AuthColetorResponse]");
		System.out.println("[AuthColetorResponse][token] " + request.getLogin());
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
			
			EspelhoUsuarioColetor espelhoUsuario = new EspelhoUsuarioColetor();
			espelhoUsuario.setId(usuario.getId());
			espelhoUsuario.setLogin(usuario.getLogin());
			espelhoUsuario.setNome(usuario.getNome());
			espelhoUsuario.setSenha(null);
			
			response.setUsuario(espelhoUsuario);
			response.setStatus(AuthColetorResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[AuthColetorResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(AuthColetorResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[AuthColetorResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[AuthColetorResources][CLOSE]");
			
		}
		
		return response;
		
	}

}