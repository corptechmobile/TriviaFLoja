package br.com.coletor;

import java.util.Date;

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
import br.com.coletor.request.ColetorSeparacaoFinalizarRequest;
import br.com.coletor.response.ColetorSeparacaoFinalizarResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separacao-finalizar")
public class ColetorSeparacaoFinalizarResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparacaoFinalizarResponse postAutenticar(ColetorSeparacaoFinalizarRequest request){
		
		ColetorSeparacaoFinalizarResponse response = new ColetorSeparacaoFinalizarResponse();
		
		System.out.println("[ColetorSeparacaoFinalizarResources]");
		System.out.println("[ColetorSeparacaoFinalizarResources][token] " + request.getLogin());
		
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
			ColetorSeparacao separacao = daoColetorSeparacao.carregar(request.getId());
			
			if(separacao == null) {
				throw new Exception(String.format("A Separação %s não existe.", request.getId()));
			}
			
			if(separacao != null && separacao.getStatus().equals(ColetorSeparacao.STATUS_FINALIZADA)) {
				throw new Exception(String.format("A Separação para a OC %s e Separador %s já foi finalizada.", separacao.getOrdemCarregId().toString(), separacao.getSeparadorNome()));
			}
			
			daoColetorSeparacao.finalizar(request.getId(), new Date());
			
			response.setStatus(ColetorSeparacaoFinalizarResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparacaoFinalizarResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparacaoFinalizarResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparacaoFinalizarResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparacaoFinalizarResources][CLOSE]");
			
		}
		
		return response;
		
	}

}