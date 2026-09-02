package br.com.webapp.model.fb.estado;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class EstadoFBDAOHibernate implements EstadoFBDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public EstadoFB carregar(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_estado AS id, ")
				 .append(" a.nome AS descricao, ")
				 .append(" a.codestadoibge AS codEstadoIbge ")
				 .append(" FROM estado a ")
				 .append(" WHERE a.id_estado = :id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("codEstadoIbge", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(EstadoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (EstadoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_estado AS id, ")
				 .append(" a.nome AS descricao, ")
				 .append(" a.codestadoibge AS codEstadoIbge ")
				 .append(" FROM estado a ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("codEstadoIbge", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(EstadoFB.class));
		
		return query.list();
	}

}
