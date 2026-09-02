package br.com.webapp.model.fb.reservalote;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.web.util.DAOException;

public class ReservaLoteFBDAOHibernate implements ReservaLoteFBDAO{

	private StringBuilder COLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ReservaLoteFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_RESERVAPRODUTOLOTE as id, ")
			   .append(" a.ID_PRODUTOLOTE as produtoLoteId, ")
			   .append(" a.ID_LOCALIDADE as localidadeId, ")
			   .append(" a.ID_ORDEMPRODREQUISICAOITEM as ordemProdRequisicaoItemId, ")
			   .append(" a.ID_ORDEMCARRGITEM as ordemCarregItemId, ")
			   .append(" a.ID_RESERVA as reservaId, ")
			   .append(" a.QUANTIDADE as quantidade ");
	}
	
	@Override
	public void insert(ReservaLoteFB reservaLoteFB) throws DAOException {
		try {
			
			reservaLoteFB.setId(getSeq());
			System.out.println("[ReservaLoteFBDAOHibernate][insert][id]" + reservaLoteFB.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append( "INSERT INTO RESERVAPRODUTOLOTE (ID_RESERVAPRODUTOLOTE, ID_PRODUTOLOTE, ID_LOCALIDADE, QUANTIDADE, ID_ORDEMPRODREQUISICAOITEM, ID_ORDEMCARRGITEM, ID_RESERVA) "
						+ "VALUES (:ID_RESERVAPRODUTOLOTE, :ID_PRODUTOLOTE, :ID_LOCALIDADE, :QUANTIDADE, :ID_ORDEMPRODREQUISICAOITEM, :ID_ORDEMCARRGITEM, :ID_RESERVA);");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaLoteFB.class);
			query.setParameter("ID_RESERVAPRODUTOLOTE", reservaLoteFB.getId());
			query.setParameter("ID_PRODUTOLOTE", reservaLoteFB.getProdutoLoteId());
			query.setParameter("ID_LOCALIDADE", reservaLoteFB.getLocalidadeId());
			query.setParameter("QUANTIDADE", reservaLoteFB.getQuantidade());
			query.setParameter("ID_ORDEMPRODREQUISICAOITEM", reservaLoteFB.getOrdemProdRequisicaoItemId());
			query.setParameter("ID_ORDEMCARRGITEM", reservaLoteFB.getOrdemCarregItemId());
			query.setParameter("ID_RESERVA", reservaLoteFB.getReservaId());
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void update(ReservaLoteFB reservaLoteFB) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE RESERVAPRODUTOLOTE" + 
					"SET ID_PRODUTOLOTE = :prodLoteId," + 
					"    ID_LOCALIDADE = :localidadeId," + 
					"    QUANTIDADE = :quantidade," + 
					"    ID_ORDEMPRODREQUISICAOITEM = :ordProdReqItem," + 
					"    ID_ORDEMCARRGITEM = :ordCarregItem," + 
					"    ID_RESERVA = :reservaId" + 
					"WHERE (ID_RESERVAPRODUTOLOTE = :id);");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaLoteFB.class);
			query.setParameter("id", reservaLoteFB.getId());
			query.setParameter("prodLoteId", reservaLoteFB.getProdutoLoteId());
			query.setParameter("localidadeId", reservaLoteFB.getLocalidadeId());
			query.setParameter("quantidade", reservaLoteFB.getQuantidade());
			query.setParameter("ordProdReqItem", reservaLoteFB.getOrdemProdRequisicaoItemId());
			query.setParameter("ordCarregItem", reservaLoteFB.getOrdemCarregItemId());
			query.setParameter("reservaId", reservaLoteFB.getReservaId());
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	/*
	@Override
	public void excluir(Integer pedVendaItemFBId) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "DELETE FROM RESERVA WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM AND ID_PEDVENDAITEM IS NOT NULL");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	*/

	@Override
	public ReservaLoteFB carregar(Integer id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM RESERVAPRODUTOLOTE a ")
		   .append(" WHERE a.ID_RESERVAPRODUTOLOTE =:id ");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaLoteFB.class);
		
		query.setParameter("id", id);
		
		query.setMaxResults(1);
		return (ReservaLoteFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservaLoteFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   		.append(" FROM RESERVAPRODUTOLOTE a ");
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaLoteFB.class);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReservaLoteFB> listar(PedVendaItemFB pedVendaItem) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
			   .append(" FROM RESERVAPRODUTOLOTE a, ")
			   		.append(" RESERVA B ")
			   .append(" WHERE a.ID_RESERVA = b.ID_RESERVA")
			     .append(" AND b.ID_PEDVENDAITEM = :ID_PEDVENDAITEM");
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaLoteFB.class);
		query.setParameter("ID_PEDVENDAITEM", pedVendaItem.getId());
		return query.list();
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_RESERVAPRODUTOLOTE_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence da ReservaLoteFB.");
		}
	}

}
