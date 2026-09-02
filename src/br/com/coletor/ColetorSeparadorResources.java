package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorSeparador;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorSeparadorRequest;
import br.com.coletor.response.ColetorSeparadorResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separador")
public class ColetorSeparadorResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparadorResponse postAutenticar(ColetorSeparadorRequest request){
		
		ColetorSeparadorResponse response = new ColetorSeparadorResponse();
		
		System.out.println("[ColetorSeparadorResources]");
		System.out.println("[ColetorSeparadorResources][token] " + request.getLogin());
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
			
			DAOColetorSeparador daoColetorSeparador = new DAOColetorSeparador(session);
			response.setSeparador(daoColetorSeparador.carregar(request.getNumFilter()));
			
			if(response.getSeparador() == null) {
				throw new Exception("Separador não encontrado.");
			}
			
			if(response.getSeparador() != null && response.getSeparador().getAtivo().equals(0)) {
				throw new Exception(String.format("O Separador %s está inativo.", response.getSeparador().getNome()));
			}
			
			response.setStatus(ColetorSeparadorResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparadorResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparadorResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparadorResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparadorResources][CLOSE]");
			
		}
		
		return response;
		
	}

}