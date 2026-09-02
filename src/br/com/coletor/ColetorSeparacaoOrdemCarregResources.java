package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorOrdSep;
import br.com.coletor.dao.DAOColetorSeparacao;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.ColetorSeparacao;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorSeparacaoRequest;
import br.com.coletor.response.ColetorSeparacaoPedVendaResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separacao-ordemcarreg")
public class ColetorSeparacaoOrdemCarregResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparacaoPedVendaResponse postAutenticar(ColetorSeparacaoRequest request){
		
		ColetorSeparacaoPedVendaResponse response = new ColetorSeparacaoPedVendaResponse();
		
		System.out.println("[ColetorSeparacaoOrdemCarregResources]");
		System.out.println("[ColetorSeparacaoOrdemCarregResources][token] " + request.getLogin());
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
			response.setSeparacao(daoColetorSeparacao.carregar(request.getOrdemCarregId(), request.getSeparadorId()));
			
			if(response.getSeparacao() != null && response.getSeparacao().getStatus().equals(ColetorSeparacao.STATUS_FINALIZADA)) {
				throw new Exception("Separação finalizada.");
			}
			
			if(response.getSeparacao() != null 
					&& response.getSeparacao().getStatus().equals(ColetorSeparacao.STATUS_EM_SEPARACAO)
					&& response.getSeparacao().getUsuarioId().equals(usuario.getId()) == false) {
				throw new Exception(String.format("A Separação já foi iniciada pelo Usuário %s.", response.getSeparacao().getUsuarioNome()));
			}
			
			if(response.getSeparacao() != null 
					&& response.getSeparacao().getStatus().equals(ColetorSeparacao.STATUS_EM_SEPARACAO)) {
				throw new Exception("A Separação já foi iniciada.");
			}
			
			DAOColetorOrdSep daoColetorOrdSep = new DAOColetorOrdSep(session);
			ColetorOrdSep coletorOrdSep = daoColetorOrdSep.carregar(request.getOrdemCarregId());
			if(coletorOrdSep == null) {
				throw new Exception(String.format("OC %s não foi encontrada.", request.getOrdemCarregId()));
			}
			
			if(coletorOrdSep.getStatus().equals(ColetorOrdSep.STATUS_CANCELADO)) {
				throw new Exception(String.format("OC %s está com status Cancelada.", request.getOrdemCarregId()));
			}
			
			if(response.getSeparacao() == null) {
				response.setSeparacao(new ColetorSeparacao(coletorOrdSep));
			}
			
			response.setStatus(ColetorSeparacaoPedVendaResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparacaoOrdemCarregResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparacaoPedVendaResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparacaoOrdemCarregResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparacaoOrdemCarregResources][CLOSE]");
			
		}
		
		return response;
		
	}

}