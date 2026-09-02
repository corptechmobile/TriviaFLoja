package br.com.webapp.model.fb.pedvendacomposto;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;

public class PedVendaCompostoFBDAOHibernate  implements PedVendaCompostoFBDAO {
	
	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public PedVendaCompostoFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_PEDVENDACOMPOSTO AS id, ")
			   .append(" MAX(a.ID_UNIDADE) AS unidadeId, ")
			   .append(" MAX(a.CODPRODUTO) AS codProduto, ")
			   .append(" MAX(a.DESCRICAO) AS descricao, ")
			   .append(" MAX(a.QUANTIDADE) AS quantidade ");
	}
	
	@Override
	public PedVendaCompostoFB carregar(Integer pedVendaId, Integer prodCompostoId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM PEDVENDACOMPOSTO a, PEDVENDA b, PEDVENDAITEM c, PRODCOMPOSTO d ")
		   .append(" WHERE b.ID_PEDVENDA = :ID_PEDVENDA ")
		   	 .append(" AND d.ID_PRODCOMPOSTO = :ID_PRODCOMPOSTO ")
		     .append(" AND a.CODPRODUTO = d.CODPRODUTO ")
		     .append(" AND b.ID_PEDVENDA = c.ID_PEDVENDA ")
		     .append(" AND a.ID_PEDVENDACOMPOSTO = c.ID_PEDVENDACOMPOSTO ")
			 .append(" GROUP BY a.ID_PEDVENDACOMPOSTO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("codProduto", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(PedVendaCompostoFB.class));
		query.setParameter("ID_PEDVENDA", pedVendaId);
		query.setParameter("ID_PRODCOMPOSTO", prodCompostoId);
		query.setMaxResults(1);
		return (PedVendaCompostoFB) query.uniqueResult();
	}
	
	@Override
	public Integer insert(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException {
		try {
			Integer pedVendaItemFBId = getSeq();
			
			System.out.println("[PedVendaCompostoFBDAOHibernate][insert][id]" + pedVendaItemFBId);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PEDVENDACOMPOSTO (ID_PEDVENDACOMPOSTO, ID_UNIDADE, CODPRODUTO, DESCRICAO, QUANTIDADE) ")
					.append("VALUES (:ID_PEDVENDACOMPOSTO, ")
					        .append(":ID_UNIDADE, ")
					        .append(":CODPRODUTO, ")
					        .append(":DESCRICAO, ")
					        .append(":QUANTIDADE) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDACOMPOSTO", pedVendaItemFBId);
			query.setParameter("ID_UNIDADE", pedVendaCompostoFB.getUnidadeId());
			query.setParameter("CODPRODUTO", pedVendaCompostoFB.getCodProduto());
			query.setParameter("DESCRICAO", pedVendaCompostoFB.getDescricao());
			query.setParameter("QUANTIDADE", pedVendaCompostoFB.getQuantidade());
			
			query.executeUpdate();
			
			return pedVendaItemFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void updateQuantidade(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException {
		try {
			System.out.println("[PedVendaCompostoFBDAOHibernate][update][id]" + pedVendaCompostoFB.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDACOMPOSTO SET QUANTIDADE = :QUANTIDADE WHERE ID_PEDVENDACOMPOSTO = :ID_PEDVENDACOMPOSTO");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDACOMPOSTO", pedVendaCompostoFB.getId());
			query.setParameter("QUANTIDADE", pedVendaCompostoFB.getQuantidade());
			
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public void excluir(Integer pedVendaCompostoId) throws DAOException {
		try {
			System.out.println("[PedVendaCompostoFBDAOHibernate][excluir][id]" + pedVendaCompostoId);
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM PEDVENDACOMPOSTO WHERE ID_PEDVENDACOMPOSTO = :ID_PEDVENDACOMPOSTO");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDACOMPOSTO", pedVendaCompostoId);
			
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaCompostoFB> listar(PedVendaFB pedVendaFB) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM PEDVENDACOMPOSTO a, PEDVENDA b, PEDVENDAITEM c ")
		   .append(" WHERE b.ID_PEDVENDA = :pedVendaId ")
		     .append(" AND b.ID_PEDVENDA = c.ID_PEDVENDA ")
		     .append(" AND a.ID_PEDVENDACOMPOSTO = c.ID_PEDVENDACOMPOSTO ")
			 .append(" GROUP BY a.ID_PEDVENDACOMPOSTO ")
			 .append(" ORDER BY a.ID_PEDVENDACOMPOSTO ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("codProduto", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(PedVendaCompostoFB.class));
		
		query.setParameter("pedVendaId", pedVendaFB.getId());
		return query.list();
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_PEDVENDACOMPOSTO_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaCompostoFB.");
		}
	}

}