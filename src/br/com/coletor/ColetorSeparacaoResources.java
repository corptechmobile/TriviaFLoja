package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorSeparacao;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.ColetorSeparacao;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorSeparacaoRequest;
import br.com.coletor.response.ColetorSeparacaoResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separacao")
public class ColetorSeparacaoResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparacaoResponse postAutenticar(ColetorSeparacaoRequest request){
		
		ColetorSeparacaoResponse response = new ColetorSeparacaoResponse();
		
		System.out.println("[ColetorSeparacaoResources]");
		System.out.println("[ColetorSeparacaoResources][token] " + request.getLogin());
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
			
			DAOColetorSeparacao daoColetorSeparacao = new DAOColetorSeparacao(session);
		
			response.setLista(daoColetorSeparacao.listar(usuario.getId(), request.getOrdemCarregId(), ColetorSeparacao.STATUS_EM_SEPARACAO));
			response.setStatus(ColetorSeparacaoResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparacaoResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparacaoResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparacaoResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparacaoResources][CLOSE]");
			
		}
		
		return response;
		
	}

}