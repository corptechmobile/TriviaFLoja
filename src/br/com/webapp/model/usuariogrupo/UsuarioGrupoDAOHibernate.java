package br.com.webapp.model.usuariogrupo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.webapp.web.util.DAOException;


public class UsuarioGrupoDAOHibernate implements UsuarioGrupoDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public UsuarioGrupo salvar(UsuarioGrupo usuarioGrupo) {
		UsuarioGrupo merged = (UsuarioGrupo) this.session.merge(usuarioGrupo);
		this.session.flush();
		this.session.clear();
		
		return merged;
	}
	
	public void excluir(UsuarioGrupo usuarioGrupo) throws DAOException {
		try {
			this.session.delete(usuarioGrupo);
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	public UsuarioGrupo carregar(Integer usuarioGrupo) {
		return (UsuarioGrupo) this.session.get(UsuarioGrupo.class, usuarioGrupo);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioGrupo> listar() {
		Criteria criteria = this.session.createCriteria(UsuarioGrupo.class);
		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioGrupo> listar(String descricao) {
		Criteria criteria = this.session.createCriteria(UsuarioGrupo.class);
		if(descricao != null){
			criteria.add(Restrictions.ilike("descricao", "%" + descricao + "%"));
		}
		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}

	@Override
	public void rollback() {
		try {
			this.session.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
