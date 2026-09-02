package br.com.webapp.model.fb.condpagto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class CondPagtoFBDAOHibernate implements CondPagtoFBDAO{

	private StringBuilder COLLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public CondPagtoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_CONDPAGTO as id, ")
				.append(" a.DESCRICAO as descricao, ")
				.append(" C.id_tabpreco as tabPrecoId, ")
				.append(" (select count(id_desdobra) from desdobra where ID_CONDPAGTO = a.ID_CONDPAGTO) as parcelas, ")
				.append(" a.dispContrCred ");
	}
	
	@Override
	public CondPagtoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM condpagto a, empresacondpagto c ")
		   .append(" WHERE C.ID_CONDPAGTO = A.ID_CONDPAGTO AND a.ID_CONDPAGTO = :id ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("parcelas", Hibernate.INTEGER)
				.addScalar("dispContrCred", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(CondPagtoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (CondPagtoFB) query.uniqueResult();
	}
	
	
	@Override
	public CondPagtoFB carregar(Integer condPagtoId, Integer empresaId, Integer clienteId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.ID_CONDPAGTO as id, ")
		   .append(" 	   a.DESCRICAO as descricao, ")
		   .append(" 	   COALESCE(d.id_tabpreco, c.id_tabpreco) as tabPrecoId, ")
		   .append(" 	   (select count(id_desdobra) from desdobra where ID_CONDPAGTO = a.ID_CONDPAGTO) as parcelas, ")
		   .append(" 	   a.dispContrCred ")
		   .append(" FROM condpagto a, empresacondpagto c ")
		   .append("      LEFT JOIN cli_tab_cond_emp d ON (d.ID_CONDPAGTO = c.ID_CONDPAGTO ")
		   .append("                                   AND d.id_pessoa_emp = c.id_pessoa_emp ")
		   .append("                                   AND d.id_pessoa_cli = :clienteId ")
		   .append("                                   AND d.id_pessoa_emp = :empresaId) ")
		   .append(" WHERE C.ID_CONDPAGTO = A.ID_CONDPAGTO ")
		   .append("   AND a.ID_CONDPAGTO = :condPagtoId ")
		   .append("   AND c.id_pessoa_emp = :empresaId ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("parcelas", Hibernate.INTEGER)
				.addScalar("dispContrCred", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(CondPagtoFB.class));
		query.setParameter("condPagtoId", condPagtoId);
		query.setParameter("empresaId", empresaId);
		query.setParameter("clienteId", clienteId);
		query.setMaxResults(1);
		
		return (CondPagtoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CondPagtoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM condpagto a, empresacondpagto c  ")
		   .append(" WHERE a.TIPO = :TIPO ")
		   .append("   AND C.ID_CONDPAGTO = A.ID_CONDPAGTO ")
		     .append(" AND a.ATIVO = :ATIVO ")
		   .append(" ORDER BY a.DESCRICAO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("parcelas", Hibernate.INTEGER)
				.addScalar("dispContrCred", Hibernate.INTEGER)				
				.setResultTransformer(Transformers.aliasToBean(CondPagtoFB.class));
		query.setParameter("TIPO", CondPagtoFB.TIPO_VENDA);
		query.setParameter("ATIVO", CondPagtoFB.ATIVO);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CondPagtoFB> listar(Integer formaPagtoFBId, Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM condpagto a, condpagtoformapagtopv b, empresacondpagto c ")
		   .append(" WHERE a.ID_CONDPAGTO = b.ID_CONDPAGTO ")
		   .append("   AND C.ID_CONDPAGTO = A.ID_CONDPAGTO ")
		   .append("   AND C.ID_PESSOA_EMP = :ID_PESSOA_EMP ")
		     .append(" AND a.TIPO = :TIPO ")
		     .append(" AND a.ATIVO = :ATIVO ")
		     .append(" AND b.ID_FORMAPAGTOPV = :ID_FORMAPAGTOPV ")
		   .append(" ORDER BY a.DESCRICAO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("parcelas", Hibernate.INTEGER)
				.addScalar("dispContrCred", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(CondPagtoFB.class));
		query.setParameter("TIPO", CondPagtoFB.TIPO_VENDA);
		query.setParameter("ATIVO", CondPagtoFB.ATIVO);
		query.setParameter("ID_FORMAPAGTOPV", formaPagtoFBId);
		query.setParameter("ID_PESSOA_EMP", empresaId);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CondPagtoFB> listar(Integer formaPagtoId, Integer empresaId, Integer clienteId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.ID_CONDPAGTO as id, ")
				.append(" a.DESCRICAO as descricao, ")
				.append(" d.id_tabpreco as tabPrecoId, ")
				.append(" (select count(id_desdobra) from desdobra where ID_CONDPAGTO = a.ID_CONDPAGTO) as parcelas, ")
				.append(" a.dispContrCred ")
		   .append(" FROM condpagto a, condpagtoformapagtopv b, empresacondpagto c, cli_tab_cond_emp d  ")
		   .append(" WHERE a.ID_CONDPAGTO = b.ID_CONDPAGTO ")
		     .append(" AND c.ID_CONDPAGTO = a.ID_CONDPAGTO ")
		     .append(" AND d.ID_CONDPAGTO = a.ID_CONDPAGTO ")
		     .append(" AND d.id_pessoa_emp = c.id_pessoa_emp ")	     
		     .append(" AND c.id_pessoa_emp = :empresaId ")
		     .append(" AND d.id_pessoa_cli = :clienteId ")
		     .append(" AND a.TIPO = :TIPO ")
		     .append(" AND a.ATIVO = :ATIVO ")
		     .append(" AND b.ID_FORMAPAGTOPV = :ID_FORMAPAGTOPV ")
		   .append(" ORDER BY a.DESCRICAO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("parcelas", Hibernate.INTEGER)
				.addScalar("dispContrCred", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(CondPagtoFB.class));
		query.setParameter("TIPO", CondPagtoFB.TIPO_VENDA);
		query.setParameter("ATIVO", CondPagtoFB.ATIVO);
		query.setParameter("ID_FORMAPAGTOPV", formaPagtoId);
		query.setParameter("empresaId", empresaId);
		query.setParameter("clienteId", clienteId);		
		
		return query.list();
	}

	

}
