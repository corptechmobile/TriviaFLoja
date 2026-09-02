package br.com.webapp.model.menu;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryPostGres;


public class MenuRecenteRN {
	
	private MenuRecenteDAO menuRecenteDAO;
	
	public MenuRecenteRN(){
		this.menuRecenteDAO = DAOFactoryPostGres.criarMenuRecenteDAO();
	}
	
	public MenuRecente salvar(MenuRecente menuRecente){
		return this.menuRecenteDAO.salvar(menuRecente);
	}
	public void excluir(MenuRecente menuRecente){
		this.menuRecenteDAO.excluir(menuRecente);
	}
	public MenuRecente carregar(Integer menuRecente){
		return this.menuRecenteDAO.carregar(menuRecente);
	}
	public MenuRecente carregar(Integer id_menuacesso, Integer id_usuario){
		return this.menuRecenteDAO.carregar(id_menuacesso, id_usuario);
	}
	public List<MenuRecente> listar(){
		return this.menuRecenteDAO.listar();
	}

}