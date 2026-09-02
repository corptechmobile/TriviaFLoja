package br.com.coletor.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorInv;

public class DAOColetorInv {
	
	private Session session;

	public DAOColetorInv(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorInv carregar(Integer id) {
		
		String sql = "SELECT a.id_coletor_inv AS id, "
						 + " a.descricao, "
						 + " a.status, "
						 + " a.dtInicio, "
						 + " a.dtTermino, "
						 + " a.dtCriacao "
					+ " FROM coletor_inv a "
					+ " WHERE a.id_coletor_inv = :id ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (ColetorInv)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ColetorInv> listar() {
		
		String sql = "SELECT FIRST 30 "
						 + " a.id_coletor_inv AS id, "
						 + " a.descricao, "
						 + " a.status, "
						 + " a.dtInicio, "
						 + " a.dtTermino, "
						 + " a.dtCriacao "
					+ " FROM coletor_inv a "
					+ " ORDER BY a.id_coletor_inv DESC ";
		//+ " WHERE a.status = :status "		
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		//query.setParameter("status", ColetorInv.STATUS_EM_ABERTO);
		//query.setMaxResults(1);
		
		return query.list();
	}

	public void updateEmConferencia(Integer coletorInvId, Date dtInicio) {
		String sql = "update coletor_inv "
						+ " set dtinicio = :dtInicio "
						+ " where id_coletor_inv = :coletorInvId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorInvId", coletorInvId);
		query.setParameter("dtInicio", dtInicio);
		
		query.executeUpdate();
		
	}

}
