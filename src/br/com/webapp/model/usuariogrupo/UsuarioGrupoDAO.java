package br.com.webapp.model.usuariogrupo;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface UsuarioGrupoDAO {
	
	public UsuarioGrupo salvar(UsuarioGrupo usuarioGrupo);
	public void excluir(UsuarioGrupo usuarioGrupo) throws DAOException;
	public UsuarioGrupo carregar(Integer usuarioGrupo);
	public List<UsuarioGrupo> listar();
	public List<UsuarioGrupo> listar(String descricao);
	public void rollback();

}
