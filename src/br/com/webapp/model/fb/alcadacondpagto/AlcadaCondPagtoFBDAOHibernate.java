package br.com.webapp.model.fb.alcadacondpagto;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class AlcadaCondPagtoFBDAOHibernate implements AlcadaCondPagtoFBDAO {
	
	private StringBuilder COLLUMNS;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public AlcadaCondPagtoFBDAOHibernate(){
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.id_gestaovenda AS gestaoVendaId, ")
				 .append(" a.id_condpagto AS condPagtoId, ")
				 .append(" a.alcada AS alcada ");
	}
	
	@Override
	public AlcadaCondPagtoFB carregar(Integer gestaoVendaId, Integer condPagtoId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
			.append(" FROM alcadacondpagto a ")
			.append(" WHERE a.id_gestaovenda = :gestaoVendaId ")
			  .append(" AND a.id_condpagto = :condPagtoId ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("alcada", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFB.class));
		q.setParameter("gestaoVendaId", gestaoVendaId);
		q.setParameter("condPagtoId", condPagtoId);
		q.setMaxResults(1);
		
		return (AlcadaCondPagtoFB) q.uniqueResult();
	}

	
	
}