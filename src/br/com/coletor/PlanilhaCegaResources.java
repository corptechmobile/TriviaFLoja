package br.com.coletor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorPlanilhaCega;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.espelho.EspelhoColetorPlanilhaCega;
import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaItem;
import br.com.coletor.exceptions.SincColetorException;
import br.com.coletor.model.ColetorPlanilhaCega;
import br.com.coletor.model.ColetorPlanilhaCegaItem;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorPlanilhaCegaRequest;
import br.com.coletor.response.ColetorPlanilhaCegaResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-pc")
public class PlanilhaCegaResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorPlanilhaCegaResponse postAutenticar(ColetorPlanilhaCegaRequest request){
		
		ColetorPlanilhaCegaResponse response = new ColetorPlanilhaCegaResponse();
		
		System.out.println("[PlanilhaCegaColetorResources]");
		System.out.println("[PlanilhaCegaColetorResources][token] " + request.getLogin());
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
			
			EspelhoColetorPlanilhaCega espelho;
			DAOColetorPlanilhaCega daoPlanilhaCegaColetor = new DAOColetorPlanilhaCega(session);
			List<EspelhoColetorPlanilhaCega> planilhaCegaColetors = new ArrayList<EspelhoColetorPlanilhaCega>();
			List<ColetorPlanilhaCegaItem> itens = daoPlanilhaCegaColetor.listarItens(usuario.getId());
			for(ColetorPlanilhaCega rs : daoPlanilhaCegaColetor.listar(usuario.getId())) {
				espelho = new EspelhoColetorPlanilhaCega(rs);
				espelho.setItens(itens(rs.getId(), itens));
				planilhaCegaColetors.add(espelho);
			}
			
			response.setPlanilhaCegas(planilhaCegaColetors);
			response.setStatus(ColetorPlanilhaCegaResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[PlanilhaCegaColetorResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorPlanilhaCegaResponse.ERRO);
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
			
			System.out.println("[PlanilhaCegaColetorResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[PlanilhaCegaColetorResources][CLOSE]");
			
		}
		
		return response;
		
	}
	
	private List<EspelhoColetorPlanilhaCegaItem> itens(Integer coletorPlanilhaCegaId, List<ColetorPlanilhaCegaItem> lista){
		List<EspelhoColetorPlanilhaCegaItem> result = new ArrayList<>();
		for(ColetorPlanilhaCegaItem rs : lista) {
			if(rs.getColetorPlanilhaCegaId().equals(coletorPlanilhaCegaId)) {
				result.add(new EspelhoColetorPlanilhaCegaItem(rs));
			}
		}
		return result;
	}

}
