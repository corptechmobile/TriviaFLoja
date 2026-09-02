package br.com.webapp.model.fb.reserva;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilMessage;

public class ReservaFBDAOHibernate implements ReservaFBDAO{

	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ReservaFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_RESERVA AS id, ")
			   .append(" a.ID_PEDVENDA AS pedVendaId, ")
			   .append(" a.ID_PEDVENDAITEM AS pedVendaItemId, ")
			   .append(" a.ID_PRODUTO AS produtoId, ")
			   .append(" a.ID_LOCALIDADE AS localidadeId, ")
			   .append(" a.TIPO AS tipoId, ")
			   .append(" a.QUANTIDADE AS quantidade, ")
			   .append(" a.ORDEMRETIRADA AS ordemRetirada ");
	}
	
	@Override
	public ReservaFB carregar(Integer reservaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM RESERVA a ")
		   .append(" WHERE a.ID_RESERVA =:id ");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
		query.setMaxResults(1);
		return (ReservaFB) query.uniqueResult();
	}

	@Override
	public Integer insert(ReservaFB reservaFB) throws DAOException {
		try {
			
			reservaFB.setId(getSeq());
			System.out.println("[ReservaFBDAOHibernate][insert][id]" + reservaFB.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO RESERVA (ID_RESERVA, ID_PEDVENDAITEM, ID_PRODUTO, ID_LOCALIDADE, TIPO, ORDEMRETIRADA, QUANTIDADE, ID_PEDVENDA) VALUES (:ID_RESERVA, :ID_PEDVENDAITEM, :ID_PRODUTO, :ID_LOCALIDADE, :TIPO, :ORDEMRETIRADA, :QUANTIDADE, :ID_PEDVENDA);");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("ID_RESERVA", reservaFB.getId());
			query.setParameter("ID_PEDVENDAITEM", reservaFB.getPedVendaItemId());
			query.setParameter("ID_PRODUTO", reservaFB.getProdutoId());
			query.setParameter("ID_LOCALIDADE", reservaFB.getLocalidadeId());
			query.setParameter("TIPO", reservaFB.getTipoId());
			query.setParameter("ORDEMRETIRADA", reservaFB.getOrdemRetirada());
			query.setParameter("QUANTIDADE", reservaFB.getQuantidade());
			query.setParameter("ID_PEDVENDA", reservaFB.getPedVendaId());
			
			query.executeUpdate();
			
			return reservaFB.getId();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new DAOException(UtilMessage.mensagem("msg.erro.reservar.produtolocalidade.pedvenda"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(UtilMessage.mensagem("msg.erro.reservar.item.pedvenda"));
		}	

	}

	@Override
	public void update(ReservaFB reservaFB) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "UPDATE RESERVA" + 
							" SET ID_PEDVENDAITEM = :pedVendaItemId, " + 
								" ID_PRODUTO = :produtoId, " + 
								" ID_LOCALIDADE = :localidadeId, " + 
								" TIPO = :tipoId, " + 
								" QUANTIDADE = :qtd, " + 
								" ORDEMRETIRADA = :ordRet " + 
							" WHERE (ID_RESERVA = :id); ");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("pedVendaItemId", reservaFB.getPedVendaItemId());
			query.setParameter("produtoId", reservaFB.getProdutoId());
			query.setParameter("localidadeId", reservaFB.getLocalidadeId());
			query.setParameter("tipoId", reservaFB.getTipoId());
			query.setParameter("qtd", reservaFB.getQuantidade());
			query.setParameter("ordRet", reservaFB.getOrdemRetirada());
			query.setParameter("pedVendaId", reservaFB.getPedVendaId());
			query.setParameter("id", reservaFB.getId());
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(UtilMessage.mensagem("msg.erro.reservar.item.pedvenda"));
		}
	}
	
	@Override
	public void excluir(Integer pedVendaItemFBId) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "DELETE FROM RESERVA WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(UtilMessage.mensagem("msg.erro.reservar.item.pedvenda"));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservaFB> listar(PedVendaFB pedVendaFB) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM RESERVA a ")
		   .append(" WHERE a.ID_PEDVENDA = :pedVendaId");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
		query.setParameter("pedVendaId", pedVendaFB.getId());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservaFB> listar(PedVendaItemFB pedVendaItemFB) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM RESERVA a ")
		   .append(" WHERE a.ID_PEDVENDAITEM = :pedVendaItemId");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
		query.setParameter("pedVendaItemId", pedVendaItemFB.getId());
		return query.list();
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_RESERVA_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence da ReservaFB.");
		}
	}

	@Override
	public Double qtdReservadaControlaLote(Integer pedVendaItemId, Integer localidadeId, Integer produtoLoteId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.QUANTIDADE AS quantidade ")
			   .append(" FROM RESERVA a, RESERVAPRODUTOLOTE b ")
			   .append(" WHERE a.ID_RESERVA = b.ID_RESERVA ")
			     .append(" AND a.ID_LOCALIDADE = :ID_LOCALIDADE")
				 .append(" AND a.ID_PEDVENDAITEM = :ID_PEDVENDAITEM")
				 .append(" AND b.ID_PRODUTOLOTE = :ID_PRODUTOLOTE");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_LOCALIDADE", localidadeId);
		query.setParameter("ID_PEDVENDAITEM", pedVendaItemId);
		query.setParameter("ID_PRODUTOLOTE", produtoLoteId);
		
		query.setMaxResults(1);
		
		Object objResult = query.uniqueResult();
		if(objResult!=null) {
			return ((BigDecimal) objResult).doubleValue();
		}
		return 0.0;
	}
	
	@Override
	public Double qtdReservadaNaoControlaLote(Integer pedVendaItemId, Integer localidadeId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.QUANTIDADE AS quantidade ")
		   .append(" FROM RESERVA a ")
		   .append(" WHERE a.ID_LOCALIDADE = :ID_LOCALIDADE ")
		     .append(" AND a.ID_PEDVENDAITEM = :ID_PEDVENDAITEM");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_LOCALIDADE", localidadeId);
		query.setParameter("ID_PEDVENDAITEM", pedVendaItemId);
		
		query.setMaxResults(1);
		
		Object objResult = query.uniqueResult();
		if(objResult!=null) {
			return ((BigDecimal) objResult).doubleValue();
		}
		return 0.0;
	}

}