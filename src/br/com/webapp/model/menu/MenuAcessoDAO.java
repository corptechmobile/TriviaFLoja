package br.com.webapp.model.menu;

import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOException;

public interface MenuAcessoDAO {
	
	public MenuAcesso salvar(MenuAcesso menuAcesso);
	public void excluir(MenuAcesso menuAcesso) throws DAOException;
	public MenuAcesso carregar(Integer id_menuacesso);
	public List<MenuAcesso> listar();
	public List<MenuAcesso> listarFilhos(String cod_edt, Integer id_menugrupo);
	public MenuAcesso ultimoGrupo(Integer id_menugrupo);
	public MenuAcesso ultimoPai(MenuAcesso menuAcesso);
	public List<Object[]> menuPrincipal(Integer id_usuariogrupo);
	public List<Object[]> menuRecente(Integer id_usuario, Integer id_usuariogrupo);
	public List<Object[]> menuFavorito(Integer id_usuario, Integer id_usuariogrupo);
	public List<MenuAcesso> listar(String descricaoFilter);
	public List<MenuAcesso> listarRecentes(UsuarioFB usuario);
	public List<MenuAcesso> listarFavoritos(UsuarioFB usuario);
	public List<MenuAcesso> listar(UsuarioGrupo usuarioGrupo);
	
}
