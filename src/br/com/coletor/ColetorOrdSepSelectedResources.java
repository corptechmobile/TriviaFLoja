package br.com.coletor;

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
import br.com.coletor.model.ColetorOrdSepItem;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorOrdSepSelectedRequest;
import br.com.coletor.response.ColetorOrdSepResponse;
import br.com.coletor.response.ColetorOrdSepSelectedResponse;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;

@Path("/coletor-ordsep-selected")
public class ColetorOrdSepSelectedResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorOrdSepSelectedResponse postAutenticar(ColetorOrdSepSelectedRequest request){
		
		ColetorOrdSepSelectedResponse response = new ColetorOrdSepSelectedResponse();
		
		System.out.println("[ColetorOrdSepSelectedResources]");
		System.out.println("[ColetorOrdSepSelectedResources][token] " + request.getLogin());
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
			ColetorOrdSep ordemSeparacao = daoColetorOrdSep.carregar(request.getId());

			// TODO validar exceptions
			if(ordemSeparacao == null) {
				throw new RNException("Ordem de Carregamento não existe.");
			}
			
			if(ordemSeparacao.getStatus().equals(ColetorOrdSep.STATUS_EM_ABERTO) == false) {
				throw new RNException("Ordem de Carregamento não está com status : Em Aberto.");
			}
			
			List<ColetorOrdSepItem> itens = daoColetorOrdSep.listarItens(request.getId());
		
			response.setOrdemSeparacao(ordemSeparacao);
			response.setItens(itens);
			response.setStatus(ColetorOrdSepResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorOrdSepSelectedResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorOrdSepResponse.ERRO);
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
			
			System.out.println("[ColetorOrdSepSelectedResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorOrdSepSelectedResources][CLOSE]");
			
		}
		
		return response;
		
	}

}