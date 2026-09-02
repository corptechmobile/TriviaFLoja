package br.com.webapp.model.fb.parametro;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ParametroFBDAOHibernate implements ParametroFBDAO{

	private Session session;
	
	public void SetSession(Session session) {
		this.session = session;
	}
	
	@Override
	public ParametroFB carregar(String nome) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.seq AS id, ")
		   .append(" a.descricao, ")
		   .append(" a.nome, ")
		   .append(" a.tipoDado, ")
		   .append(" a.valor ")
		   .append(" FROM parametro a")
		   .append(" WHERE a.nome = :nome ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("tipoDado", Hibernate.STRING)
				.addScalar("valor", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ParametroFB.class));
		query.setParameter("nome", nome);
		query.setMaxResults(1);
		return (ParametroFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroFB> listar() {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.seq AS id, ")
		   .append(" a.descricao, ")
		   .append(" a.nome, ")
		   .append(" a.tipoDado, ")
		   .append(" a.valor ")
		   .append(" FROM parametro a ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("tipoDado", Hibernate.STRING)
				.addScalar("valor", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ParametroFB.class));
		return query.list();
	}
	
}
