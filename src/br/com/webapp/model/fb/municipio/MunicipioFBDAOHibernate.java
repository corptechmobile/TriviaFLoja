package br.com.webapp.model.fb.municipio;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class MunicipioFBDAOHibernate implements MunicipioFBDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public MunicipioFB carregar(Integer id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_municipio AS id, ")
				 .append(" a.id_estado AS estadoId, ")
				 .append(" a.codmunicipioibge AS codMunicipioIbge, ")
				 .append(" a.nome AS descricao ")
				.append(" FROM municipio a ") 
				.append(" WHERE a.id_municipio = :id");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("estadoId", Hibernate.STRING)
				.addScalar("codMunicipioIbge", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MunicipioFB.class));
		
		query.setParameter("id", id);
		
		query.setMaxResults(1);
		
		return (MunicipioFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioFB> listar() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT a.id_municipio AS id, ")
				 .append(" a.id_estado AS estadoId, ")
				 .append(" a.codmunicipioibge AS codMunicipioIbge, ")
				 .append(" a.nome AS descricao ")
				.append(" FROM municipio a ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("estadoId", Hibernate.STRING)
				.addScalar("codMunicipioIbge", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MunicipioFB.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioFB> listar(String estadoId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT a.id_municipio AS id, ")
				 .append(" a.id_estado AS estadoId, ")
				 .append(" a.codmunicipioibge AS codMunicipioIbge, ")
				 .append(" a.nome AS descricao ")
				.append(" FROM municipio a ")
				.append(" WHERE a.id_estado = :estadoId ");
		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("estadoId", Hibernate.STRING)
				.addScalar("codMunicipioIbge", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MunicipioFB.class));
		query.setParameter("estadoId", estadoId);
		return query.list();
	}

	@Override
	public MunicipioFB listar(String localidade, String uf) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id_municipio AS id, ")
				 .append(" a.id_estado AS estadoId, ")
				 .append(" a.codmunicipioibge AS codMunicipioIbge, ")
				 .append(" a.nome AS descricao ")
				.append(" FROM municipio a ") 
				.append(" WHERE a.id_estado = :uf")
				.append(" AND a.nome = :localidade");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("estadoId", Hibernate.STRING)
				.addScalar("codMunicipioIbge", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MunicipioFB.class));
		query.setParameter("localidade", localidade);
		query.setParameter("uf", uf);
		query.setMaxResults(1);
		
		return (MunicipioFB) query.uniqueResult();
	}

}
