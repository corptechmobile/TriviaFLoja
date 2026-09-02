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
import br.com.coletor.request.ColetorSeparacaoExcluirRequest;
import br.com.coletor.response.ColetorSeparacaoExcluirResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separacao-excluir")
public class ColetorSeparacaoExcluirResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparacaoExcluirResponse postAutenticar(ColetorSeparacaoExcluirRequest request){
		
		ColetorSeparacaoExcluirResponse response = new ColetorSeparacaoExcluirResponse();
		
		System.out.println("[ColetorSeparacaoExcluirResources]");
		System.out.println("[ColetorSeparacaoExcluirResources][token] " + request.getLogin());
		
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
			
			daoColetorSeparacao.excluir(request.getId());
			
			response.setStatus(ColetorSeparacaoExcluirResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparacaoExcluirResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparacaoExcluirResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparacaoExcluirResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparacaoExcluirResources][CLOSE]");
			
		}
		
		return response;
		
	}

}