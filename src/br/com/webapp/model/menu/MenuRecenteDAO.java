package br.com.webapp.model.menu;

import java.util.List;

public interface MenuRecenteDAO {
	
	public MenuRecente salvar(MenuRecente menuRecente);
	public void excluir(MenuRecente menuRecente);
	public MenuRecente carregar(Integer menuRecente);
	public MenuRecente carregar(Integer id_menuacesso, Integer id_usuario);
	public List<MenuRecente> listar();

}
