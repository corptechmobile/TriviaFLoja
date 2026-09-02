package br.com.webapp.model.usuario;

import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOFactoryPostGres;

public class UsuarioRN {
	
	private UsuarioDAO usuarioDAO;
	public UsuarioRN(){
		this.usuarioDAO = DAOFactoryPostGres.criarUsuarioDAO();
	}
	
	public void salvar(Usuario usuario) {
		this.usuarioDAO.salvar(usuario);
	}
	
	public void excluir(Integer usuarioId) {
		this.excluir(usuarioId);
	}
	
	public void excluir(UsuarioGrupo usuarioGrupo) {
		this.excluir(usuarioGrupo);
	}

	public Usuario carregar(Integer usuarioId) {
		return this.usuarioDAO.carregar(usuarioId);
	}

}
