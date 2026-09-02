package br.com.webapp.model.menu;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MenuFavoritoDAOHibernate implements MenuFavoritoDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	@Override
	public MenuFavorito carregar(MenuFavoritoId menuFavoritoId) {
		Criteria criteria = this.session.createCriteria(MenuFavorito.class);
		criteria.add(Restrictions.eq("id", menuFavoritoId));
		criteria.setMaxResults(1);
		return (MenuFavorito) criteria.uniqueResult();
	}

	@Override
	public MenuFavorito salvar(MenuFavorito menuFavorito) {
		MenuFavorito merged = (MenuFavorito) this.session.merge(menuFavorito);
		this.session.flush();
		this.session.clear();
		
		return merged;
	}
	
	@Override
	public void excluir(MenuFavorito menuFavorito) {
		this.session.delete(menuFavorito);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuFavorito> listar() {
		Criteria criteria = this.session.createCriteria(MenuFavorito.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuFavorito> listar(Integer usuarioId) {
		Criteria criteria = this.session.createCriteria(MenuFavorito.class);
		criteria.add(Restrictions.eq("usuario", usuarioId));
		return criteria.list();
	}

}
