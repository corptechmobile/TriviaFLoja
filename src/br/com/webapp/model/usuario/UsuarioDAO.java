package br.com.webapp.model.usuario;

import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOException;

public interface UsuarioDAO {
	public void salvar(Usuario usuario);
	public void excluir(Integer usuarioId) throws DAOException;
	public void excluir(UsuarioGrupo usuarioGrupo) throws DAOException;
	public Usuario carregar(Integer usuarioId);
	
}
