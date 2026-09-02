package br.com.webapp.model.fb.usuariocoletordiverg;

import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class UsuarioColetorDivergFBRN {
	
	private UsuarioColetorDivergFBDAO usuarioColetorDivergFBDAO;
	
	public UsuarioColetorDivergFBRN() {
		this.usuarioColetorDivergFBDAO = DAOFactoryFirebird.criarUsuarioColetorDiverg();
				
	}
	
	public void excluir(UsuarioColetorDivergFB usuarioDivergenciFB) {
		this.usuarioColetorDivergFBDAO.excluir(usuarioDivergenciFB);
	}
	
	public UsuarioColetorDivergFB carregar() {
		return this.usuarioColetorDivergFBDAO.carregar();
	}
	
	public List<UsuarioColetorDivergFB> listar(Integer usuarioId){
		return this.usuarioColetorDivergFBDAO.listar();
	}
	public UsuarioColetorDivergFB salvar(UsuarioColetorDivergFB usuarioColetorDivergFB) throws DAOException {
		return this.usuarioColetorDivergFBDAO.salvar(usuarioColetorDivergFB);
	}

	public UsuarioColetorDivergFB carregar(Integer usuarioId, Integer divergenciaId) {
		return this.usuarioColetorDivergFBDAO.carregar(usuarioId, divergenciaId);
	}

	public void excluir(Integer usuarioId) {
		this.usuarioColetorDivergFBDAO.excluir(usuarioId);
		
	}

	public void salvar(Integer usuarioId, Integer divergenciaId) throws DAOException {
		this.usuarioColetorDivergFBDAO.salvar(usuarioId, divergenciaId);
	}
	
}
