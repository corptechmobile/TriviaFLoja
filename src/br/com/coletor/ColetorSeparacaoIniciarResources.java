package br.com.coletor;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.TransactionException;

import br.com.coletor.dao.DAOColetorOrdSep;
import br.com.coletor.dao.DAOColetorSeparacao;
import br.com.coletor.dao.DAOColetorSeparador;
import br.com.coletor.dao.DAOUsuarioColetor;
import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.ColetorSeparacao;
import br.com.coletor.model.ColetorSeparador;
import br.com.coletor.model.UsuarioColetor;
import br.com.coletor.request.ColetorSeparacaoIniciarRequest;
import br.com.coletor.response.ColetorSeparacaoIniciarResponse;
import br.com.webapp.web.util.HibernateUtil;

@Path("/coletor-separacao-iniciar")
public class ColetorSeparacaoIniciarResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ColetorSeparacaoIniciarResponse postAutenticar(ColetorSeparacaoIniciarRequest request){
		
		ColetorSeparacaoIniciarResponse response = new ColetorSeparacaoIniciarResponse();
		
		System.out.println("[ColetorSeparacaoIniciarResources]");
		System.out.println("[ColetorSeparacaoIniciarResources][token] " + request.getLogin());
		
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
			ColetorSeparacao separacao = daoColetorSeparacao.carregar(request.getOrdemCarregId(), request.getSeparadorId());
			
			if(separacao != null) {
				throw new Exception(String.format("A Separação já foi iniciada pelo Usuário %s.", separacao.getUsuarioNome()));
			}
			
			DAOColetorOrdSep daoColetorOrdSep = new DAOColetorOrdSep(session);
			ColetorOrdSep coletorOrdSep = daoColetorOrdSep.carregar(request.getOrdemCarregId());
			if(coletorOrdSep == null) {
				throw new Exception(String.format("A Ordem de Carregamento %s não foi encontrado.", request.getOrdemCarregId()));
			}
			
			if(coletorOrdSep.getStatus().equals(ColetorOrdSep.STATUS_CANCELADO)) {
				throw new Exception(String.format("OC %s está com status Cancelada.", request.getOrdemCarregId()));
			}
			
			DAOColetorSeparador daoColetorSeparador = new DAOColetorSeparador(session);
			ColetorSeparador separador = daoColetorSeparador.carregar(request.getSeparadorId());
			
			if(separador == null) {
				throw new Exception("Separador não encontrado.");
			}
			
			if(separador != null && separador.getAtivo().equals(0)) {
				throw new Exception(String.format("O Separador %s está inativo.", separador.getNome()));
			}
			
			daoColetorSeparacao.iniciar(request.getOrdemCarregId(), coletorOrdSep.getNumPedVenda(), coletorOrdSep.getEmpresaId(), coletorOrdSep.getClienteId(), usuario.getId(), request.getSeparadorId(), new Date());
			
			response.setStatus(ColetorSeparacaoIniciarResponse.SUCESSO);
			
			session.getTransaction().commit();
			
			System.out.println("[ColetorSeparacaoIniciarResources][SUCESSO]");
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			response.setStatus(ColetorSeparacaoIniciarResponse.ERRO);
			response.setMensagem(e.getMessage());
			
			if(session != null){
				try {
					session.getTransaction().rollback();
				} catch (TransactionException e2) {
					e2.printStackTrace();
				}
			}
			
			System.out.println("[ColetorSeparacaoIniciarResources][ERRO]");
			
		}finally{
		
			if (session != null && session.isOpen()) {
		        try {
		            session.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
			
			System.out.println("[ColetorSeparacaoIniciarResources][CLOSE]");
			
		}
		
		return response;
		
	}

}