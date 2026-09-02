package br.com.webapp.model.fb.fretetipo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class FreteTipoFBDAOHibernate implements FreteTipoFBDAO{
	
	private StringBuilder COLLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public FreteTipoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_TIPOFRETE as id, ")
				.append(" a.ID_TIPOMOVFISC as movFiscTipoId, ")
				.append(" a.DESCRICAO as descricao ");
	}
	
	@Override
	public FreteTipoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipofrete a ")
		   .append(" WHERE a.ID_TIPOFRETE = :id");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("movFiscTipoId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(FreteTipoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (FreteTipoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FreteTipoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipofrete a ")
		   .append(" ORDER BY a.DESCRICAO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(FreteTipoFB.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FreteTipoFB> listar(Integer formaPagtoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipofrete a, tipofreteformapagtopv b ")
		   .append(" WHERE a.id_tipofrete = b.id_tipofrete ")
		     .append(" AND b.id_formapagtopv = :id_formapagtopv ")
		   .append(" ORDER BY a.DESCRICAO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(FreteTipoFB.class));
		query.setParameter("id_formapagtopv", formaPagtoId);
		return query.list();
	}

}
