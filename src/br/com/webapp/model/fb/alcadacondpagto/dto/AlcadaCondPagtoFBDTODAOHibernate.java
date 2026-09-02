package br.com.webapp.model.fb.alcadacondpagto.dto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFB;
import br.com.webapp.web.util.DAOException;


public class AlcadaCondPagtoFBDTODAOHibernate implements AlcadaCondPagtoFBDTODAO{

	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public AlcadaCondPagtoFBDTODAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.id_gestaovenda as gestaoVendaId, ")
			   .append(" g.nome as gestaoVendaDesc, ")
			   .append(" a.id_condpagto as condPagtoId, ")
			   .append(" c.descricao as condPagtoDesc, ")
			   .append(" a.alcada as alcada ");
	}
	
	@Override
	public AlcadaCondPagtoFBDTO carregar(GestaoVendaFB gestaoVendaId, CondPagtoFB condPagtoId) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM alcadacondpagto a, gestaovenda g, condpagto c ")
		   .append(" WHERE a.id_gestaovenda = g.id_gestaovenda ")
		   .append(" AND a.id_condpagto = c.id_condpagto ")
		   .append(" AND g.id_gestaovenda =:gestaoVendaId ")
		   .append(" AND c.id_condpagto =:condPagtoId ");
		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaDesc", Hibernate.STRING)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("condPagtoDesc", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFBDTO.class));
		
		query.setParameter("gestaoVendaId", gestaoVendaId.getId());
		query.setParameter("condPagtoId", condPagtoId.getId());
		
		query.setMaxResults(1);
		return (AlcadaCondPagtoFBDTO) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlcadaCondPagtoFBDTO> listar(Integer gestaoVendaId, Integer condPagtoId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM alcadacondpagto a, gestaovenda g, condpagto c ")
		   .append(" WHERE a.id_gestaovenda = g.id_gestaovenda ")
		   .append(" AND a.id_condpagto = c.id_condpagto ");
		
	   if (gestaoVendaId != null) {
		   sql.append(" AND g.id_gestaovenda =:gestaoVendaId ");
	   }
	   
	   if (condPagtoId != null) {
		   sql.append(" AND c.id_condpagto =:condPagtoId ");
	   }
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaDesc", Hibernate.STRING)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("condPagtoDesc", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFBDTO.class));
		
		if (gestaoVendaId != null) {
			query.setParameter("gestaoVendaId", gestaoVendaId);
	   }
	   
	   if (condPagtoId != null) {
		   query.setParameter("condPagtoId", condPagtoId);
	   }
		return query.list();
	}

	@Override
	public void insert(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO alcadacondpagto (id_gestaovenda, id_condpagto, alcada)")
		   .append(" VALUES (:id_gestaovenda, :id_condpagto, :alcada) ");
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaDesc", Hibernate.STRING)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("condPagtoDesc", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFBDTO.class));
		q.setParameter("id_gestaovenda", alcadaCondPagtoFBDTO.getGestaoVendaId());
		q.setParameter("id_condpagto", alcadaCondPagtoFBDTO.getCondPagtoId());
		q.setParameter("alcada", alcadaCondPagtoFBDTO.getAlcada());
		
		q.executeUpdate();
	}

	@Override
	public void update(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE OR INSERT INTO ALCADACONDPAGTO (id_gestaovenda, id_condpagto, alcada)")
			   .append(" VALUES (:id_gestaovenda, :id_condpagto, :alcada) ");
			Query q = (Query) session.createSQLQuery(sql.toString())
					.addScalar("gestaoVendaId", Hibernate.INTEGER)
					.addScalar("gestaoVendaDesc", Hibernate.STRING)
					.addScalar("condPagtoId", Hibernate.INTEGER)
					.addScalar("condPagtoDesc", Hibernate.STRING)
					.addScalar("alcada", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFBDTO.class));
			q.setParameter("id_gestaovenda", alcadaCondPagtoFBDTO.getGestaoVendaId());
			q.setParameter("id_condpagto", alcadaCondPagtoFBDTO.getCondPagtoId());
			q.setParameter("alcada", alcadaCondPagtoFBDTO.getAlcada());
			
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void delete(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM ALCADACONDPAGTO")
			   .append(" WHERE id_gestaovenda =:id_gestaovenda and id_condpagto =:id_condpagto ");
			Query q = (Query) session.createSQLQuery(sql.toString())
					.addScalar("gestaoVendaId", Hibernate.INTEGER)
					.addScalar("gestaoVendaDesc", Hibernate.STRING)
					.addScalar("condPagtoId", Hibernate.INTEGER)
					.addScalar("condPagtoDesc", Hibernate.STRING)
					.addScalar("alcada", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(AlcadaCondPagtoFBDTO.class));
			q.setParameter("id_gestaovenda", alcadaCondPagtoFBDTO.getGestaoVendaId());
			q.setParameter("id_condpagto", alcadaCondPagtoFBDTO.getCondPagtoId());
			
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@Override
	public void rollback() {
		try {
			this.session.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
