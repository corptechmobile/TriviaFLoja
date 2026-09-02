package br.com.webapp.model.menu;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public class MenuHomeDAOHibernate implements MenuHomeDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public MenuAcesso carregar(UsuarioFB usuario) {
		Criteria criteria = this.session.createCriteria(MenuHome.class);
		criteria.add(Restrictions.eq("usuarioId", usuario.getId()));
		criteria.setMaxResults(1);
		MenuHome menuHome = (MenuHome) criteria.uniqueResult();
		if(menuHome!=null) {
			return menuHome.getMenuAcesso();
		}
		
		return null;
	}

	@Override
	public MenuHome salvar(MenuHome menuHome) {
		MenuHome merged = (MenuHome) this.session.merge(menuHome);
		this.session.flush();
		this.session.clear();
		
		return merged;
	}

}
