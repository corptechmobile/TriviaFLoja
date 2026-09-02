package br.com.webapp.model.fb.gestaovendamob;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;

public class GestaoVendaMobFBDAOHibernate implements GestaoVendaMobFBDAO{

	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public GestaoVendaMobFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_GESTAOVENDAMOB as id, ")
			   .append(" a.ID_GESTAOVENDAMOB_REF as gestaoRefId, ")
			   .append(" a.NOME as nome, ")
			   .append(" a.ORDEM as ordem,")
			   .append(" a.CODEDT as codEdt ");
	}
	
	@Override
	public GestaoVendaMobFB carregar(Integer idGestaoVendaMob) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM GESTAOVENDAMOB a ")
		   .append(" WHERE a.ID_GESTAOVENDAMOB = :idGestaoVendaMob ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoRefId", Hibernate.INTEGER)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(GestaoVendaMobFB.class));
		query.setParameter("idGestaoVendaMob", idGestaoVendaMob);
		query.setMaxResults(1);
		return (GestaoVendaMobFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GestaoVendaMobFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM GESTAOVENDAMOB a ")
		   .append(" ORDER BY a.CODEDT ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoRefId", Hibernate.INTEGER)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(GestaoVendaMobFB.class));
		return query.list();
	}

}
