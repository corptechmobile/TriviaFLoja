package br.com.webapp.model.fb.cobrtipo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class CobrTipoFBDAOHibernate implements CobrTipoFBDAO{

	private StringBuilder COLLUMNS;
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public CobrTipoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_TIPOCOBR as id, ")
				.append(" a.DESCRICAO as descricao ");
	}
	
	@Override
	public CobrTipoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS)
		   .append(" FROM tipocobr a ")
		   .append(" WHERE a.ID_TIPOCOBR = :id ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(CobrTipoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (CobrTipoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CobrTipoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS)
		   .append(" FROM tipocobr a ")
		   .append(" ORDER BY a.DESCRICAO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(CobrTipoFB.class));
		return query.list();
	}

	
}
