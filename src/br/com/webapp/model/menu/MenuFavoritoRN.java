package br.com.webapp.model.menu;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryPostGres;

public class MenuFavoritoRN {
	
	private MenuFavoritoDAO menuFavoritoDAO;
	
	public MenuFavoritoRN(){
		this.menuFavoritoDAO = DAOFactoryPostGres.criarMenuFavoritoDAO();
	}
	
	public MenuFavorito carregar(MenuFavoritoId menuFavoritoId){
		return this.menuFavoritoDAO.carregar(menuFavoritoId);
	}
	
	public MenuFavorito salvar(MenuFavorito menuFavorito){
		return this.menuFavoritoDAO.salvar(menuFavorito);
	}
	
	public void excluir(MenuFavorito menuFavorito){
		this.menuFavoritoDAO.excluir(menuFavorito);
	}

	public List<MenuFavorito> listar(){
		return this.menuFavoritoDAO.listar();
	}
	
	public List<MenuFavorito> listar(Integer usuarioId){
		return this.menuFavoritoDAO.listar(usuarioId);
	}

}
