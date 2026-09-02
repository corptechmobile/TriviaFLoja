package br.com.coletor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorOrdSep;
import br.com.coletor.dao.DAOColetorOrdSepItemContagem;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorOrdSepItemContagem;
import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorOrdSepContagemRequest;
import br.com.coletor.response.ColetorOrdSepContagemResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-ordsep-contagem")
public class ColetorOrdSepContagemResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorOrdSepContagemResponse postAutenticar(ColetorOrdSepContagemRequest request){
		
		ColetorOrdSepContagemResponse response = new ColetorOrdSepContagemResponse();
		
		System.out.println("[ColetorOrdSepContagemResources]");
		System.out.println("[ColetorOrdSepContagemResources][token] " + request.getLogin());
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
			ColetorOrdSep coletorOrdSep = daoColetorOrdSep.carregar(request.getId());
			
			if(coletorOrdSep == null) {
				throw new Exception("Ordem Separação não existe!");
			}
			
			if(coletorOrdSep.getStatus().equals(ColetorOrdSep.STATUS_ENTREGUE)) {
				throw new Exception("Não é possível processar contagem, o Ordem Separação com status: Entregue!");
			}
			
			if(coletorOrdSep.getStatus().equals(ColetorOrdSep.STATUS_CANCELADO)) {
				throw new Exception("Não é possível processar contagem, o Ordem Separação com status: Cancelado!");
			}
			
			DAOColetorOrdSepItemContagem daoColetorOrdSepItemContagem = new DAOColetorOrdSepItemContagem(session);
			for(EspelhoColetorOrdSepItemContagem rs : request.getContagem()) {
				daoColetorOrdSepItemContagem.inserir(rs);
			}
			
			Integer seqId = daoColetorOrdSep.integracao(request.getId(), usuario.getId());
			daoColetorOrdSepItemContagem.integracao(seqId, request.getId());
			
			response.setStatus(ColetorOrdSepContagemResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorOrdSepContagemResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorOrdSepContagemResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorOrdSepContagemResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorOrdSepContagemResources][CLOSE]");
			
		}
		
		return response;
		
	}

}
