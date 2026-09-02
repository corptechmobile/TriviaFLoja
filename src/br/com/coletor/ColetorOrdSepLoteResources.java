package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorOrdSepLote;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorOrdSepLoteRequest;
import br.com.coletor.response.ColetorOrdSepLoteResponse;
import br.com.coletor.response.ColetorSeparacaoResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-ordsep-lote")
public class ColetorOrdSepLoteResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorOrdSepLoteResponse postAutenticar(ColetorOrdSepLoteRequest request){
		
		ColetorOrdSepLoteResponse response = new ColetorOrdSepLoteResponse();
		
		System.out.println("[ColetorOrdSepLoteResources]");
		System.out.println("[ColetorOrdSepLoteResources][token] " + request.getLogin());
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
			
			DAOColetorOrdSepLote daoColetorSeparacaoLote = new DAOColetorOrdSepLote(session);
		
			response.setLista(daoColetorSeparacaoLote.listar(request.getOrdemCarregItemId(), request.getProdutoId()));
			response.setStatus(ColetorSeparacaoResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorOrdSepLoteResources][SUCESSO]");
			
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
			
			System.out.println("[ColetorOrdSepLoteResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorOrdSepLoteResources][CLOSE]");
			
		}
		
		return response;
		
	}

}