package br.com.webapp.model.usuario;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOException;

public class UsuarioDAOHibernate implements UsuarioDAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public Usuario carregar(Integer usuarioId) {
		Criteria criteria = this.session.createCriteria(Usuario.class, "u");
		criteria.createCriteria("u.usuarioGrupo", "g", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("id", usuarioId));
		criteria.setMaxResults(1);
		return (Usuario) criteria.uniqueResult();
	}

	@Override
	public void salvar(Usuario usuario) {
		this.session.saveOrUpdate(usuario);
	}

	@Override
	public void excluir(Integer usuarioId) throws DAOException{
		try {
			session.delete(usuarioId);
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(UsuarioGrupo usuarioGrupo) throws DAOException {
		try {
			String hql = "delete from Usuario where usuarioGrupo.id = :usuarioGrupoId";
			Query query = this.session.createQuery(hql);
			query.setParameter("usuarioGrupoId", usuarioGrupo.getId());
			query.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

}
