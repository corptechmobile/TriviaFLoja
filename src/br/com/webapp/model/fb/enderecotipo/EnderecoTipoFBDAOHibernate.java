package br.com.webapp.model.fb.enderecotipo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class EnderecoTipoFBDAOHibernate implements EnderecoTipoFBDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public EnderecoTipoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tipoendereco AS id, ")
				 .append(" a.descricao AS descricao ")
				.append(" FROM tipoendereco a ") 
				.append(" WHERE a.id_tipoendereco = :id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(EnderecoTipoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (EnderecoTipoFB) query.uniqueResult(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnderecoTipoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_tipoendereco AS id, ")
				 .append(" a.descricao AS descricao ")
				.append(" FROM tipoendereco a ORDER BY a.descricao");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(EnderecoTipoFB.class));
		return query.list();
	}

}
