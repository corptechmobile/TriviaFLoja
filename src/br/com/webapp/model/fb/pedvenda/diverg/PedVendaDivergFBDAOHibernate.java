package br.com.webapp.model.fb.pedvenda.diverg;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.pedvenda.diverg.dto.PedVendaDivergFBDTO;
import br.com.webapp.web.util.DAOException;

public class PedVendaDivergFBDAOHibernate implements PedVendaDivergFBDAO{

	private Session session;
	StringBuilder COLUMNS;
	StringBuilder COLUMNSDTO;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public PedVendaDivergFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_PEDVENDADIVERG as id, ")
			   .append(" a.ID_CONDPAGTO as condPagtoId, ")
			   .append(" a.DESCONTO as desconto, ")
			   .append(" a.INTERACAO as dtInteracao, ")
			   .append(" a.DT_CREATE as dt_create, ")
			   .append(" a.DT_UPDATE as dt_update, ")
			   .append(" a.OBSERVACAO as observacao, ")
			   .append(" a.SITUACAO as situacao, ")
			   .append(" a.TIPO as tipo, ")
			   .append(" a.VALIDAR as validar, ")
			   .append(" a.ID_PEDVENDA as pedVendaId, ")
			   .append(" a.ID_PEDVENDAITEM as pedVendaItemId, ")
			   .append(" a.ID_USUARIO as usuarioId ");
		
		COLUMNSDTO = new StringBuilder();
		COLUMNSDTO.append(" a.ID_PEDVENDADIVERG as id, ")
				  .append(" a.ID_CONDPAGTO as condPagtoId, ")
				  .append(" cp.DESCRICAO as condPagtoDesc, ")
				  .append(" a.DESCONTO as desconto, ")
				  .append(" a.INTERACAO as dtInteracao, ")
				  .append(" a.DT_CREATE as dt_create, ")
				  .append(" a.DT_UPDATE as dt_update, ")
				  .append(" a.OBSERVACAO as observacao, ")
				  .append(" a.SITUACAO as situacao, ")
				  .append(" a.TIPO as tipo, ")
				  .append(" a.VALIDAR as validar, ")
				  .append(" a.ID_PEDVENDA as pedVendaId, ")
				  .append(" a.ID_PEDVENDAITEM as pedVendaItemId, ")
				  .append(" u.ID_USUARIO as usuarioId, ")
				  .append(" upper(u.login) as usuarioNome, ")
				  .append(" pr.ID_PRODUTO as produtoId, ")
				  .append(" pr.CODINTERNO as produtoCod, ")
				  .append(" pr.DESCRICAO as produtoDesc ");
	}
	
	@Override
	public PedVendaDivergFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
		   .append(" FROM PEDVENDADIVERG a ")
		   .append(" WHERE a.ID_PEDVENDADIVERG = :id");
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (PedVendaDivergFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaDivergFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
		   .append(" FROM PEDVENDADIVERG a ");
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaDivergFB> listar(Integer pedVendaFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
		   .append(" FROM PEDVENDADIVERG a ")
		   .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA");
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		query.setParameter("ID_PEDVENDA", pedVendaFBId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaDivergFBDTO> listarDTO(Integer pedVendaFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNSDTO.toString())
		   .append(" FROM PEDVENDADIVERG a ")
		   .append(" LEFT JOIN CONDPAGTO cp ON (a.ID_CONDPAGTO = cp.ID_CONDPAGTO) ")
		   .append(" LEFT JOIN PEDVENDAITEM i ON (a.ID_PEDVENDAITEM = i.ID_PEDVENDAITEM) ")
		   .append(" LEFT JOIN USUARIO u ON (a.ID_USUARIO = u.ID_USUARIO) ")
		   .append(" LEFT JOIN PRODUTO pr ON (i.ID_PRODUTO = pr.ID_PRODUTO) ")
		   .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA ")
		   .append(" ORDER BY a.dt_create desc ");
		Query query = (Query) this.session.createSQLQuery(sql.toString())
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("pedVendaId", Hibernate.INTEGER)
									.addScalar("pedVendaItemId", Hibernate.INTEGER)
									.addScalar("condPagtoId", Hibernate.INTEGER)
									.addScalar("condPagtoDesc", Hibernate.STRING)
									.addScalar("produtoId", Hibernate.INTEGER)
									.addScalar("produtoCod", Hibernate.STRING)
									.addScalar("produtoDesc", Hibernate.STRING)
									.addScalar("usuarioId", Hibernate.INTEGER)
									.addScalar("usuarioNome", Hibernate.STRING)
									.addScalar("desconto", Hibernate.DOUBLE)
									.addScalar("tipo", Hibernate.INTEGER)
									.addScalar("situacao", Hibernate.INTEGER)
									.addScalar("validar", Hibernate.INTEGER)
									.addScalar("observacao", Hibernate.STRING)
									.addScalar("dtInteracao", Hibernate.DATE)
									.addScalar("dt_create", Hibernate.DATE)
									.addScalar("dt_update", Hibernate.DATE)
									.setResultTransformer(Transformers.aliasToBean(PedVendaDivergFBDTO.class));
		
		query.setParameter("ID_PEDVENDA", pedVendaFBId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaDivergFBDTO> listarToLiberar(Integer pedVendaFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNSDTO.toString())
		   .append(" FROM PEDVENDADIVERG a ")
		   .append(" LEFT JOIN CONDPAGTO cp ON (a.ID_CONDPAGTO = cp.ID_CONDPAGTO) ")
		   .append(" LEFT JOIN PEDVENDAITEM i ON (a.ID_PEDVENDAITEM = i.ID_PEDVENDAITEM) ")
		   .append(" LEFT JOIN USUARIO u ON (a.ID_USUARIO = u.ID_USUARIO) ")
		   .append(" LEFT JOIN PRODUTO pr ON (i.ID_PRODUTO = pr.ID_PRODUTO) ")
		   .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA ")
		     .append(" AND a.VALIDAR = :VALIDAR ")
		   .append(" ORDER BY a.dt_create desc");
		Query query = (Query) this.session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("pedVendaItemId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("condPagtoDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioNome", Hibernate.STRING)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("tipo", Hibernate.INTEGER)
				.addScalar("situacao", Hibernate.INTEGER)
				.addScalar("validar", Hibernate.INTEGER)
				.addScalar("observacao", Hibernate.STRING)
				.addScalar("dtInteracao", Hibernate.DATE)
				.addScalar("dt_create", Hibernate.DATE)
				.addScalar("dt_update", Hibernate.DATE)
				.setResultTransformer(Transformers.aliasToBean(PedVendaDivergFBDTO.class));
		
		query.setParameter("ID_PEDVENDA", pedVendaFBId);
		query.setParameter("VALIDAR", PedVendaDivergFB.VALIDAR);
		return query.list();
	}

	@Override
	public Integer insert(PedVendaDivergFB pedVendaDivergFB) throws DAOException {
		try {
			Integer pedVendaDivergFBId = getSeq();
			System.out.println("[PPedVendaDivergFBDAOHibernate][insert][id]" + pedVendaDivergFBId);
			
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO PEDVENDADIVERG (ID_PEDVENDADIVERG, ID_CONDPAGTO, DESCONTO, INTERACAO, DT_CREATE, DT_UPDATE, OBSERVACAO, SITUACAO, TIPO, VALIDAR, ID_PEDVENDA, ID_PEDVENDAITEM, ID_USUARIO) ")
			   .append(" VALUES (:ID_PEDVENDADIVERG, :ID_CONDPAGTO, :DESCONTO, :INTERACAO, :DT_CREATE, :DT_UPDATE, :OBSERVACAO, :SITUACAO, :TIPO, :VALIDAR, :ID_PEDVENDA, :ID_PEDVENDAITEM, :ID_USUARIO); ");
			Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
			query.setParameter("ID_PEDVENDADIVERG", pedVendaDivergFBId);
			query.setParameter("ID_CONDPAGTO", pedVendaDivergFB.getCondPagtoId());
			query.setParameter("DESCONTO", pedVendaDivergFB.getDesconto());
			query.setParameter("INTERACAO", pedVendaDivergFB.getDtInteracao());
			query.setParameter("DT_CREATE", pedVendaDivergFB.getDt_create());
			query.setParameter("DT_UPDATE", pedVendaDivergFB.getDt_update());
			query.setParameter("OBSERVACAO", pedVendaDivergFB.getObservacao());
			query.setParameter("SITUACAO", pedVendaDivergFB.getSituacao());
			query.setParameter("TIPO", pedVendaDivergFB.getTipo());
			query.setParameter("VALIDAR", pedVendaDivergFB.getValidar());
			query.setParameter("ID_PEDVENDA", pedVendaDivergFB.getPedVendaId());
			query.setParameter("ID_PEDVENDAITEM", pedVendaDivergFB.getPedVendaItemId());
			query.setParameter("ID_USUARIO", pedVendaDivergFB.getUsuarioId());
			query.executeUpdate();
			return pedVendaDivergFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void update(PedVendaDivergFB pedVendaDivergFB) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE PEDVENDADIVERG ")
			   .append(" SET ID_CONDPAGTO = :ID_CONDPAGTO, DESCONTO = :DESCONTO, INTERACAO = :INTERACAO, DT_CREATE = :DT_CREATE, DT_UPDATE = :DT_UPDATE, OBSERVACAO = :OBSERVACAO, SITUACAO = :SITUACAO, TIPO = :TIPO, VALIDAR = :VALIDAR, ID_PEDVENDA = :ID_PEDVENDA, ID_PEDVENDAITEM = :ID_PEDVENDAITEM, ID_USUARIO = :ID_USUARIO")
			   .append(" WHERE (ID_PEDVENDADIVERG = :id); ");
			Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
			query.setParameter("ID_CONDPAGTO", pedVendaDivergFB.getCondPagtoId());
			query.setParameter("DESCONTO", pedVendaDivergFB.getDesconto());
			query.setParameter("INTERACAO", pedVendaDivergFB.getDtInteracao());
			query.setParameter("DT_CREATE", pedVendaDivergFB.getDt_create());
			query.setParameter("DT_UPDATE", pedVendaDivergFB.getDt_update());
			query.setParameter("OBSERVACAO", pedVendaDivergFB.getObservacao());
			query.setParameter("SITUACAO", pedVendaDivergFB.getSituacao());
			query.setParameter("TIPO", pedVendaDivergFB.getTipo());
			query.setParameter("VALIDAR", pedVendaDivergFB.getValidar());
			query.setParameter("ID_PEDVENDA", pedVendaDivergFB.getPedVendaId());
			query.setParameter("ID_PEDVENDAITEM", pedVendaDivergFB.getPedVendaItemId());
			query.setParameter("ID_USUARIO", pedVendaDivergFB.getUsuarioId());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public void updateLiberacao(PedVendaDivergFBDTO pedVendaDivergFB) throws DAOException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE PEDVENDADIVERG ")
			   .append(" SET INTERACAO = :INTERACAO, DT_UPDATE = :DT_UPDATE, OBSERVACAO = :OBSERVACAO, SITUACAO = :SITUACAO, VALIDAR = :VALIDAR, ID_USUARIO = :ID_USUARIO ")
			   .append(" WHERE ID_PEDVENDADIVERG = :ID_PEDVENDADIVERG ");
			Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
			query.setParameter("ID_PEDVENDADIVERG", pedVendaDivergFB.getId());
			query.setParameter("INTERACAO", pedVendaDivergFB.getDtInteracao());
			query.setParameter("DT_UPDATE", pedVendaDivergFB.getDt_update());
			query.setParameter("OBSERVACAO", pedVendaDivergFB.getObservacao());
			query.setParameter("SITUACAO", pedVendaDivergFB.getSituacao());
			query.setParameter("VALIDAR", pedVendaDivergFB.getValidar());
			query.setParameter("ID_USUARIO", pedVendaDivergFB.getUsuarioId());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId) throws DAOException {
		try {
			
			String varWhere = "";
			if(pedVendaItemFBId==null) {
				varWhere = " WHERE a.ID_PEDVENDA = :ID_PEDVENDA AND a.ID_PEDVENDAITEM IS NULL ";
			}else {
				varWhere = " WHERE a.ID_PEDVENDAITEM = :ID_PEDVENDAITEM ";
			}
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM PEDVENDADIVERG a").append(varWhere);
			Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
			
			if(pedVendaItemFBId==null) {
				query.setParameter("ID_PEDVENDA", pedVendaFBId);
			}else {
				query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			}
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId, int situacao, int divergenciaPor) throws DAOException {
		try {
			
			String varWhere = " AND ID_PEDVENDAITEM = :ID_PEDVENDAITEM ";
			if(pedVendaItemFBId==null) {
				varWhere = " AND ID_PEDVENDAITEM IS NULL ";
			}
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM PEDVENDADIVERG ")
				.append(" WHERE ID_PEDVENDA = :ID_PEDVENDA ")
				  .append(" AND TIPO = :DIVERGENCIA_POR ")
				  .append(" AND SITUACAO = :SITUACAO ").append(varWhere);
			Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
			
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("DIVERGENCIA_POR", divergenciaPor);
			query.setParameter("SITUACAO", situacao);
			
			if(pedVendaItemFBId!=null) {
				query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			}
			
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}
	
	@Override
	public PedVendaDivergFB existDescontoLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer codPagtoId, Double desconto) {
		String varWhere = ""; 
		if(pedVendaItemFBId!=null) {
			varWhere = " AND a.ID_PEDVENDAITEM = :pedVendaItemFBId";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
			   .append(" FROM PEDVENDADIVERG a ")
			   .append(" WHERE a.ID_PEDVENDA = :pedVendaFBId ")
			     .append(" AND a.ID_CONDPAGTO = :codPagtoId ")	 
			     .append(" AND a.DESCONTO >= :desconto ")
			     .append(" AND a.TIPO = :tipo ")
			     .append(" AND a.SITUACAO = :situacao ").append(varWhere);
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		query.setParameter("desconto", desconto);
		query.setParameter("codPagtoId", codPagtoId);
		query.setParameter("tipo", PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
		query.setParameter("situacao", PedVendaDivergFB.SITUACAO_EM_ABERTO);
		query.setParameter("pedVendaFBId", pedVendaFBId);
		
		if(pedVendaItemFBId!=null) {
			query.setParameter("pedVendaItemFBId", pedVendaItemFBId);
		}
		
		query.setMaxResults(1);
		return (PedVendaDivergFB) query.uniqueResult();
	}

	@Override
	public PedVendaDivergFB existLoteLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId) {
		String varWhere = ""; 
		if(pedVendaItemFBId!=null) {
			varWhere = " AND a.ID_PEDVENDAITEM = :pedVendaItemFBId";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
			   .append(" FROM PEDVENDADIVERG a ")
			   .append(" WHERE a.ID_PEDVENDA = :pedVendaFBId ")
			     .append(" AND a.TIPO = :tipo ")
			     .append(" AND a.SITUACAO = :situacao ").append(varWhere);
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		query.setParameter("tipo", PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES);
		query.setParameter("situacao", PedVendaDivergFB.SITUACAO_LIBERADO);
		query.setParameter("pedVendaFBId", pedVendaFBId);
		
		if(pedVendaItemFBId!=null) {
			query.setParameter("pedVendaItemFBId", pedVendaItemFBId);
		}
		
		query.setMaxResults(1);
		return (PedVendaDivergFB) query.uniqueResult();
	}
	
	@Override
	public PedVendaDivergFB existVendaSemEstoqueDispLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId) {
		String varWhere = ""; 
		if(pedVendaItemFBId!=null) {
			varWhere = " AND a.ID_PEDVENDAITEM = :pedVendaItemFBId";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS.toString())
			   .append(" FROM PEDVENDADIVERG a ")
			   .append(" WHERE a.ID_PEDVENDA = :pedVendaFBId ")
			     .append(" AND a.TIPO = :tipo ")
			     .append(" AND a.SITUACAO = :situacao ").append(varWhere);
		Query query = (Query) this.session.createSQLQuery(sql.toString()).addEntity(PedVendaDivergFB.class);
		query.setParameter("tipo", PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP);
		query.setParameter("situacao", PedVendaDivergFB.SITUACAO_LIBERADO);
		query.setParameter("pedVendaFBId", pedVendaFBId);
		
		if(pedVendaItemFBId!=null) {
			query.setParameter("pedVendaItemFBId", pedVendaItemFBId);
		}
		
		query.setMaxResults(1);
		return (PedVendaDivergFB) query.uniqueResult();
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_PEDVENDADIVERG_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaFB.");
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
