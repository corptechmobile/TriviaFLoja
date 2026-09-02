package br.com.webapp.model.fb.pais;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class PaisFBDAOHibernate implements PaisFBDAO{

	private Session session;
	
	public void SetSession(Session session) {
		this.session = session;
	}
	
	@Override
	public PaisFB carregar(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tabpais AS id, ")
		   .append(" a.nomepais AS descricao ")
		   .append(" FROM tabpais a")
		   .append(" WHERE a.id_tabpais = :idPais ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(PaisFB.class));
		query.setParameter("idPais", id);
		query.setMaxResults(1);
		return (PaisFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaisFB> listar() {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tabpais AS id, ")
		   .append(" a.nomepais AS descricao ")
		   .append(" FROM tabpais a");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(PaisFB.class));
		return query.list();
	}
	
}
