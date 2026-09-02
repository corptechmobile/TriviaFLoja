package br.com.coletor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorInv;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorInv;
import br.com.coletor.model.ColetorInv;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorInvRequest;
import br.com.coletor.response.ColetorInvResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-inv")
public class ColetorInvResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorInvResponse postAutenticar(ColetorInvRequest request){
		
		ColetorInvResponse response = new ColetorInvResponse();
		
		System.out.println("[ColetorInvResources]");
		System.out.println("[ColetorInvResources][token] " + request.getLogin());
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
			List<EspelhoColetorInv> coletorInvs = new ArrayList<EspelhoColetorInv>();
			for(ColetorInv rs : daoColetorInv.listar()) {
				coletorInvs.add(new EspelhoColetorInv(rs));
			}
			
			response.setColetorInv(coletorInvs);
			response.setStatus(ColetorInvResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorInvResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorInvResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorInvResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorInvResources][CLOSE]");
			
		}
		
		return response;
		
	}

}
