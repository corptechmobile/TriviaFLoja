package br.com.webapp.model.menu;

import java.util.List;

public interface MenuFavoritoDAO {
	public MenuFavorito carregar(MenuFavoritoId menuFavoritoId);
	public MenuFavorito salvar(MenuFavorito menuFavorito);
	public void excluir(MenuFavorito menuFavorito);
	public List<MenuFavorito> listar();
	public List<MenuFavorito> listar(Integer usuarioId);
}
