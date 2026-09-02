package br.com.webapp.model.fb.grupofinanceiro;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class GrupoFinanceiroFBDAOHibernate implements GrupoFinanceiroFBDAO{
	
	private StringBuilder COLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public GrupoFinanceiroFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" gf.ID_GRUPOFINANCEIRO as id, ")
			   .append(" upper(gf.DESCRICAO) as descricao, ")
			   .append(" gf.FIXO, ")
			   .append(" gf.TIPO, ")
			   .append(" gf.APROVACAOELETRONICA as aprovacaoEletronica, ")
			   .append(" gf.ID_TIPODOCCTB as idTipoDocCTB, ")
			   .append(" gf.ID_CONTACTB_PROV as idContaCTBProv, ")
			   .append(" gf.COMPOEATRASOHISTCLI as compoeAtrasoHistCli ");
	}
	
	
	@Override
	public GrupoFinanceiroFB carregar(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM grupofinanceiro gf ")
		   .append(" WHERE gf.id_grupofinanceiro = '"+id+"' ");
		   
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("fixo", Hibernate.INTEGER)
				.addScalar("tipo", Hibernate.STRING)
				.addScalar("aprovacaoEletronica", Hibernate.INTEGER)
				.addScalar("idTipoDocCTB", Hibernate.STRING)
				.addScalar("idContaCTBProv", Hibernate.INTEGER)
				.addScalar("compoeAtrasoHistCli", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(GrupoFinanceiroFB.class));

			query.setMaxResults(1);
		return (GrupoFinanceiroFB) query.uniqueResult();
	}
	
	
@SuppressWarnings("unchecked")
@Override
public List<GrupoFinanceiroFB> listar(String descricao) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM grupofinanceiro gf ");

		if (descricao != null && !"".equals(descricao)) {
			sql.append(" WHERE upper(gf.descricao) like '%"+descricao.toUpperCase()+"%' ");
		}
		
		sql.append(" ORDER BY gf.DESCRICAO  ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("fixo", Hibernate.INTEGER)
				.addScalar("tipo", Hibernate.STRING)
				.addScalar("aprovacaoEletronica", Hibernate.INTEGER)
				.addScalar("idTipoDocCTB", Hibernate.STRING)
				.addScalar("idContaCTBProv", Hibernate.INTEGER)
				.addScalar("compoeAtrasoHistCli", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(GrupoFinanceiroFB.class));
		
		
		return query.list();
	}

@SuppressWarnings("unchecked")
@Override
public List<GrupoFinanceiroFB> listar() {
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT ").append(COLUMNS)
	   .append("  FROM grupofinanceiro gf ");
	sql.append(" ORDER BY gf.DESCRICAO  ");
	
	Query query = (Query) session.createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.STRING)
			.addScalar("descricao", Hibernate.STRING)
			.addScalar("fixo", Hibernate.INTEGER)
			.addScalar("tipo", Hibernate.STRING)
			.addScalar("aprovacaoEletronica", Hibernate.INTEGER)
			.addScalar("idTipoDocCTB", Hibernate.STRING)
			.addScalar("idContaCTBProv", Hibernate.INTEGER)
			.addScalar("compoeAtrasoHistCli", Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(GrupoFinanceiroFB.class));
	
	return query.list();
}

}
