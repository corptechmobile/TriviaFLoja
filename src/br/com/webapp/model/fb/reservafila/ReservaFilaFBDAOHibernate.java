package br.com.webapp.model.fb.reservafila;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.webapp.model.fb.reserva.ReservaFB;
import br.com.webapp.web.util.DAOException;

public class ReservaFilaFBDAOHibernate implements ReservaFilaFBDAO {
	
	private StringBuilder COLUMNS;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public ReservaFilaFBDAOHibernate() {
	COLUMNS = new StringBuilder();
	COLUMNS.append(" a.ID_PEDVENDA AS pedVendaId, ")
		   .append(" a.ID_PEDVENDAITEM AS pedVendaItemId, ")
		   .append(" a.ID_PESSOA_EMP AS empresaId, ")
		   .append(" a.ID_PRODUTO AS produtoId, ")
		   .append(" a.QUANTIDADE AS quantidade, ")
		   .append(" a.retestqdisp AS retEstqDisp ");
	}
	
	@Override
	public void insert(ReservaFilaFB reservaFilaFB) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO RESERVAFILA (ID_PESSOA_EMP, ID_PEDVENDA, ID_PEDVENDAITEM, ID_PRODUTO, QUANTIDADE, RETESTQDISP) VALUES (:ID_PESSOA_EMP, :ID_PEDVENDA, :ID_PEDVENDAITEM, :ID_PRODUTO, :QUANTIDADE, :RETESTQDISP);");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("ID_PESSOA_EMP", reservaFilaFB.getEmpresaId());
			query.setParameter("ID_PEDVENDA", reservaFilaFB.getPedVendaId());
			query.setParameter("ID_PEDVENDAITEM", reservaFilaFB.getPedVendaItemId());
			query.setParameter("ID_PRODUTO", reservaFilaFB.getProdutoId());
			query.setParameter("QUANTIDADE", reservaFilaFB.getQuantidade());
			query.setParameter("RETESTQDISP", reservaFilaFB.getRetEstqDisp());
			
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(Integer pedVendaItemFBId) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "DELETE FROM RESERVAFILA WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM");
			Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFB.class);
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public ReservaFilaFB carregar(Integer pedVendaItemFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM RESERVAFILA a ")
		   .append(" WHERE a.ID_PEDVENDAITEM = :pedVendaItemId");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ReservaFilaFB.class);
		query.setParameter("pedVendaItemId", pedVendaItemFBId);
		query.setMaxResults(1);
		return (ReservaFilaFB) query.uniqueResult();
	}

}
