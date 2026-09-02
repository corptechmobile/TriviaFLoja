package br.com.webapp.model.fb.telefonetipo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class TelefoneTipoFBDAOHibernate implements TelefoneTipoFBDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public TelefoneTipoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tipofone AS id, ")
				 .append(" a.descricao AS descricao ")
				 .append(" FROM tipofone a ") 
				 .append(" WHERE a.id_tipofone = :id");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TelefoneTipoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (TelefoneTipoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TelefoneTipoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tipofone AS id, ")
				 .append(" a.descricao AS descricao ")
				 .append(" FROM tipofone a ORDER by a.descricao ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(TelefoneTipoFB.class));
		return query.list();
	}

}
