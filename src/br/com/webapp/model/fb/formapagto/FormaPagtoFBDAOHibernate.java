package br.com.webapp.model.fb.formapagto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.fretetipo.FreteTipoFB;

public class FormaPagtoFBDAOHibernate implements FormaPagtoFBDAO{

	private StringBuilder COLLUMNS;
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public FormaPagtoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_FORMAPAGTOPV as id, ")
				.append(" a.DESCRICAO as descricao ");
	}
	
	@Override
	public FormaPagtoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM formapagtopv a ")
		   .append(" WHERE a.ID_FORMAPAGTOPV = :id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(FormaPagtoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (FormaPagtoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormaPagtoFB> listar() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM formapagtopv a ")
		   .append(" WHERE a.ativo = :ativo ")
		   .append(" ORDER BY a.descricao ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
										.addScalar("id", Hibernate.INTEGER)
										.addScalar("descricao", Hibernate.STRING);
		
		query.setParameter("ativo", FormaPagtoFB.ATIVO);
		query.setResultTransformer(Transformers.aliasToBean(FormaPagtoFB.class));
		
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FormaPagtoFB> listarFormaCond(Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT a.ID_FORMAPAGTOPV as id, MAX(a.DESCRICAO) as descricao ")
		   .append(" FROM formapagtopv a, CONDPAGTOFORMAPAGTOPV b, empresacondpagto c ")
		   .append(" WHERE a.id_formapagtopv = b.id_formapagtopv ")
		   .append("   AND c.id_condpagto = b.id_condpagto ")
 	       .append("   AND c.id_pessoa_emp = :empresaId ")		   
		   .append("   AND a.ativo = :ativo ")
		   .append(" GROUP BY a.ID_FORMAPAGTOPV ")
		   .append(" ORDER BY 2 ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
										.addScalar("id", Hibernate.INTEGER)
										.addScalar("descricao", Hibernate.STRING);
		
		query.setParameter("ativo", FormaPagtoFB.ATIVO);
		query.setParameter("empresaId", empresaId);
		query.setResultTransformer(Transformers.aliasToBean(FormaPagtoFB.class));
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormaPagtoFB> listarPorFreteTipo(Integer freteTipoId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM formapagtopv a, tipofreteformapagtopv b ")
		   .append(" WHERE a.id_formapagtopv = b.id_formapagtopv")
		     .append(" AND b.id_tipofrete = :freteTipoId ")
		     .append(" AND a.ativo = :ativo ")
		   .append(" ORDER BY a.descricao ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
										.addScalar("id", Hibernate.INTEGER)
										.addScalar("descricao", Hibernate.STRING);
		
		query.setParameter("freteTipoId", freteTipoId);
		query.setParameter("ativo", FormaPagtoFB.ATIVO);
		query.setResultTransformer(Transformers.aliasToBean(FormaPagtoFB.class));
		
		return query.list();
	}

	@Override
	public List<FormaPagtoFB> listarFormaCondProd(Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT a.ID_FORMAPAGTOPV as id, MAX(a.DESCRICAO) as descricao ")
		   .append(" FROM formapagtopv a, CONDPAGTOFORMAPAGTOPV b, empresacondpagto c ")
		   .append(" WHERE a.id_formapagtopv = b.id_formapagtopv ")
		   .append("   AND c.id_condpagto = b.id_condpagto ")
 	       .append("   AND c.id_pessoa_emp = :empresaId ")		   
		   .append("   AND a.ativo = :ativo ")
		   .append(" GROUP BY a.ID_FORMAPAGTOPV ")
		   .append(" ORDER BY 1 ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
										.addScalar("id", Hibernate.INTEGER)
										.addScalar("descricao", Hibernate.STRING);
		
		query.setParameter("ativo", FormaPagtoFB.ATIVO);
		query.setParameter("empresaId", empresaId);
		query.setResultTransformer(Transformers.aliasToBean(FormaPagtoFB.class));
		
		return query.list();
	}

	
}
