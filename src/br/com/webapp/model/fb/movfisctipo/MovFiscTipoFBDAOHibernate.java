package br.com.webapp.model.fb.movfisctipo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class MovFiscTipoFBDAOHibernate implements MovFiscTipoFBDAO{

	private StringBuilder COLLUMNS;
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public MovFiscTipoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_TIPOMOVFISC as id, ")
				.append(" a.DESCRICAO as descricao, ")
				.append(" a.ID_TIPOOPERACAOFISCAL as opFiscTipoId, ")
				.append(" b.DESCRICAO as opFiscTipoDesc, ")
				.append(" a.TIPOIMPFISCAL as impFiscalTipo, ")
				.append(" a.LANCACARCAP as lancaCarCap, ")
				.append(" a.ETAPALANCACAR as etapaLancaCar ");
				
	}

	@Override
	public MovFiscTipoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipomovfisc a, tipooperacaofiscal b ")
		   .append(" WHERE a.ID_TIPOMOVFISC = :id ")
		     .append(" AND a.id_tipooperacaofiscal = b.id_tipooperacaofiscal ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("opFiscTipoId", Hibernate.INTEGER)
				.addScalar("opFiscTipoDesc", Hibernate.STRING)
				.addScalar("impFiscalTipo", Hibernate.INTEGER)
				.addScalar("lancaCarCap", Hibernate.INTEGER)
				.addScalar("etapaLancaCar", Hibernate.INTEGER)				
				.setResultTransformer(Transformers.aliasToBean(MovFiscTipoFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (MovFiscTipoFB) query.uniqueResult();
	}

	@Override
	public MovFiscTipoFB carregarDefault() { 
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipomovfisc a, tipooperacaofiscal b, parametro p ")
		   .append(" WHERE a.ID_TIPOMOVFISC = p.valor ")
		     .append(" AND a.id_tipooperacaofiscal = b.id_tipooperacaofiscal ")
			 .append(" AND p.seq = 19 ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("opFiscTipoId", Hibernate.INTEGER)
				.addScalar("opFiscTipoDesc", Hibernate.STRING)
				.addScalar("impFiscalTipo", Hibernate.INTEGER)
				.addScalar("lancaCarCap", Hibernate.INTEGER)
				.addScalar("etapaLancaCar", Hibernate.INTEGER)				
				.setResultTransformer(Transformers.aliasToBean(MovFiscTipoFB.class));
		query.setMaxResults(1);
		
		return (MovFiscTipoFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovFiscTipoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS.toString())
		   .append(" FROM tipomovfisc a, tipooperacaofiscal b ")
		   .append(" WHERE a.id_tipooperacaofiscal = b.id_tipooperacaofiscal ")
		   .append(" ORDER BY a.DESCRICAO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("opFiscTipoId", Hibernate.INTEGER)
				.addScalar("opFiscTipoDesc", Hibernate.STRING)
				.addScalar("impFiscalTipo", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(MovFiscTipoFB.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovFiscTipoFB> listarTransfOutras() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tmf.id_tipomovfisc AS id, ")
		   .append("        tmf.descricao ")
		   .append("   FROM tipomovfisc tmf ")
		   .append("  WHERE tmf.natureza = 'S' ")
		   .append("	AND tmf.ativo = 1 ")
		   .append("	AND tmf.classe in (2, 6, 9, 10) ")
		   .append("  ORDER BY 2 ");		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MovFiscTipoFB.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovFiscTipoFB> listarPedVenda() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tmf.id_tipomovfisc AS id, ")
		   .append("        tmf.descricao ")
		   .append("   FROM tipomovfisc tmf ")
		   .append("  WHERE tmf.natureza = 'S' ")
		   .append("	AND tmf.ativo = 1 ")
		   .append("	AND tmf.classe = 0 ")
		   .append("  ORDER BY 2 ");		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(MovFiscTipoFB.class));
		return query.list();
	}
	
	
}
