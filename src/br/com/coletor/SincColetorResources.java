package br.com.coletor;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOEmpresaColetor;
import br.com.coletor.dao.DAOProdutoColetor;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoProdutoCB;
import br.com.coletor.exceptions.SincColetorException;
import br.com.coletor.model.EmpresaColetor;
import br.com.coletor.model.ProdutoColetor;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.SincColetorRequest;
import br.com.coletor.response.SincColetorResponse;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBRN;
import br.com.webapp.web.util.HibernateUtil;

@Path("/sinc-coletor")
public class SincColetorResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public SincColetorResponse postAutenticar(SincColetorRequest request){
		
		SincColetorResponse response = new SincColetorResponse();
		
		System.out.println("[SincColetorResponse]");
		System.out.println("[SincColetorResponse][token] " + request.getLogin());
		//System.out.println("senha: " + request.getSenha());
		
		Session session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();
		
		try {
			
			session.beginTransaction().begin();
			session.getTransaction().setTimeout(10000);
			
			DAOUsuarioColetor daoUsuarioColetor = new DAOUsuarioColetor(session);
			UsuarioColetor usuario = daoUsuarioColetor.autenticar(request.getLogin(), request.getSenha());
			
			if(usuario == null){
				throw new SincColetorException("Login ou senha invalida!");
			}
			
			DAOEmpresaColetor daoEmpresaColetor = new DAOEmpresaColetor(session);
			List<EmpresaColetor> empresas = daoEmpresaColetor.listar(usuario.getId()); 
			
			DAOProdutoColetor daoProdutoColetor = new DAOProdutoColetor(session);
			List<ProdutoColetor> produtos = daoProdutoColetor.listar(); 
			
			ProdutoCBFBRN produtoCBFBRN = new ProdutoCBFBRN();
			List<EspelhoProdutoCB> produtoCBs = produtoCBFBRN.listarToSincColetor();
			
			response.setEmpresas(empresas);
			response.setProdutos(produtos);
			response.setProdutoCBs(produtoCBs);
			response.setStatus(SincColetorResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[SincColetorResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(SincColetorResponse.ERRO);
			
			if(e instanceof SincColetorException) {
				response.setMensagem(e.getMessage());
			}else {
				response.setMensagem("Erro ao processar informações de Sincronizaçõo. Por favor entre em contato com o Suporte.");
			}
			
			
			if (session != null && session.isOpen()) {
		        try {
		            if (session.getTransaction() != null && session.getTransaction().isActive()) {
		                session.getTransaction().rollback();
		            }
		        } catch (TransactionException e2) {
		            e2.printStackTrace();
		        }
		    }
			
			System.out.println("[SincColetorResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[SincColetorResources][CLOSE]");
			
		}
		
		return response;
		
	}

}