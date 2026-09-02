package br.com.webapp.model.fb.coletorpc;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ColetorPCDivergFBRN {

	private ColetorPCDivergFBDAO coletorPCDivergFBDAO;
	
	public ColetorPCDivergFBRN() {
		this.coletorPCDivergFBDAO = DAOFactoryFirebird.criarDivergPCFB();
	}
	
	public ColetorPCDivergFB carregar(Integer coletorId, int status) {
		return this.coletorPCDivergFBDAO.carregar(coletorId, status);
	}
	
	public ColetorPCDivergFB salvar(ColetorPCDivergFB coletorPCDivergFB) throws DAOException {
		return this.coletorPCDivergFBDAO.salvar(coletorPCDivergFB); 
	}
	
	public void excluir() {
		this.coletorPCDivergFBDAO.excluir();
	}
	
	public List<ColetorPCDivergFB> listar(Integer coletorPCId){
		return this.coletorPCDivergFBDAO.listar(coletorPCId);
	}

	public ColetorPCDivergFB novo(Integer coletorId, int status) throws DAOException {
		ColetorPCDivergFB coletorPCDivergFB = new ColetorPCDivergFBRN().carregar(coletorId, status);
		if(coletorPCDivergFB == null) {
			coletorPCDivergFB = new ColetorPCDivergFB();
			coletorPCDivergFB.setColetorId(coletorId);
			coletorPCDivergFB.setDivergenciaId(status);
			coletorPCDivergFB.setDtCreate(new Date());
			coletorPCDivergFB.setDtUpdate(new Date());
			coletorPCDivergFB = new ColetorPCDivergFBRN().salvar(coletorPCDivergFB);
		}	
		
		return coletorPCDivergFB;
	}

	public void aprovar(ColetorPCDivergFB divergencia, UsuarioFB usuarioLogado) throws DAOException {
		divergencia.setDtAprovacao(new Date());
		divergencia.setUsuarioAprovacaoId(usuarioLogado.getId());
		this.coletorPCDivergFBDAO.update(divergencia);
	}

	public List<ColetorPCDivergFB> listar() {	
		return this.coletorPCDivergFBDAO.listar();
	}

	
}
