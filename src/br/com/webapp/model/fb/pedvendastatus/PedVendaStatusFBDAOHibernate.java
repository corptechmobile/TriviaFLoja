package br.com.webapp.model.fb.pedvendastatus;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class PedVendaStatusFBDAOHibernate implements PedvendaStatusFBDAO{
	
	private StringBuilder COLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public PedVendaStatusFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_PEDVENDASTATUS as id, ")
			   .append(" a.DESCRICAO as descricao, ")
			   .append(" a.CARTEIRA as carteira, ")
			   .append(" a.EFETIVADO as efetivado, ")
			   .append(" a.CREDCLIENTE as credCliente, ")
			   .append(" a.TRIVIAMOBILE as triviaMobile, ")
			   .append(" a.ATUALIZAMOEDA as atualizaMoeda ");
	}

	@Override
	public PedVendaStatusFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLUMNS)
		   .append(" FROM pedvendastatus a ")
		   .append(" WHERE a.ID_PEDVENDASTATUS = :id ");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaStatusFB.class);
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (PedVendaStatusFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaStatusFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLUMNS)
		   .append("FROM pedvendastatus a ")
		   .append(" ORDER BY a.DESCRICAO ");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaStatusFB.class);
		return query.list();
	}

}
