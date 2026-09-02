package br.com.webapp.model.fb.tabpreco;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class TabPrecoFBDAOHibernate implements TabPrecoFBDAO{

	private StringBuilder COLUMNS;
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public TabPrecoFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_TABPRECO as id, ")
			   .append(" a.DESCRICAO as descricao ");
	} 
	
	@Override
	public TabPrecoFB carregar(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM tabpreco a ")
		   .append(" WHERE a.ID_TABPRECO = :id");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TabPrecoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (TabPrecoFB) query.uniqueResult();
	}
	
	@Override
	public TabPrecoFB carregar(Integer idEmpresa) {
		
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM tabpreco a ")
		   .append(" WHERE a.ID_TABPRECO = :id");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TabPrecoFB.class));
		
		query.setParameter("id", idEmpresa);
		query.setMaxResults(1);
		
		return (TabPrecoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TabPrecoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM tabpreco a ")
		   .append( "ORDER BY a.DESCRICAO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TabPrecoFB.class));
		return query.list();
	}

	

	
}
