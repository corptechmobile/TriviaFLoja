package br.com.coletor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorOrdSep;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorOrdSepRequest;
import br.com.coletor.response.ColetorOrdSepResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-ordsep")
public class ColetorOrdSepResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorOrdSepResponse postAutenticar(ColetorOrdSepRequest request){
		
		ColetorOrdSepResponse response = new ColetorOrdSepResponse();
		
		System.out.println("[ColetorOrdSepResources]");
		System.out.println("[ColetorOrdSepResources][token] " + request.getLogin());
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
			
			DAOColetorOrdSep daoColetorOrdSep = new DAOColetorOrdSep(session);
			List<ColetorOrdSep> coletorOrdSeps = new ArrayList<ColetorOrdSep>();
			coletorOrdSeps.addAll(daoColetorOrdSep.listarSemProcTransp(usuario.getId(), request.getNumFilter()));
			coletorOrdSeps.addAll(daoColetorOrdSep.listarComProcTransp(usuario.getId(), request.getNumFilter()));
		
			response.setColetorOrdSep(coletorOrdSeps);
			response.setStatus(ColetorOrdSepResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorOrdSepResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorOrdSepResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorOrdSepResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorOrdSepResources][CLOSE]");
			
		}
		
		return response;
		
	}

}