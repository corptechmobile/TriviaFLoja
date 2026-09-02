package br.com.webapp.model.menu;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MenuRecenteDAOHibernate implements MenuRecenteDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public MenuRecente salvar(MenuRecente menuRecente) {
		MenuRecente merged = (MenuRecente) this.session.merge(menuRecente);
		this.session.flush();
		this.session.clear();
		
		return merged;
	}
	
	public void excluir(MenuRecente menuRecente) {
		this.session.delete(menuRecente);
		
	}
	
	public MenuRecente carregar(Integer menuRecente) {
		return (MenuRecente) this.session.get(MenuRecente.class, menuRecente);
	}
	
	public MenuRecente carregar(Integer id_menuacesso, Integer id_usuario) {
		Criteria criteria = this.session.createCriteria(MenuRecente.class);
		criteria.add(Restrictions.eq("menuAcesso.id", id_menuacesso));
		criteria.add(Restrictions.eq("usuario", id_usuario));
		return (MenuRecente) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuRecente> listar() {
		Criteria criteria = this.session.createCriteria(MenuRecente.class);
		//criteria.add(Restrictions.eq("empresa", empresa));
		//criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}
	
}
