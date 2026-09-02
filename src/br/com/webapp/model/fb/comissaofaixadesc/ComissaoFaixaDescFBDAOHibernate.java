package br.com.webapp.model.fb.comissaofaixadesc;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class ComissaoFaixaDescFBDAOHibernate implements ComissaoFaixaDescFBDAO{
	
	private StringBuilder COLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ComissaoFaixaDescFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_COMISSAOFAIXADESC as id, ")
			   .append(" a.ID_LINHAPRODUTO as produtoLinhaId, ")
			   .append(" l.codEdt, ")
			   .append(" upper(l.descricao) as produtoLinhaDesc, ")
			   .append(" a.FAIXADESC1 as faixaDesc1, ")
			   .append(" a.FAIXADESC2 as faixaDesc2, ")
			   .append(" a.PERCCOMISSAO as percComissao ");
	}
	
	
	@Override
	public ComissaoFaixaDescFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM comissaofaixadesc a, linhaproduto l ")
		   .append(" WHERE a.id_linhaproduto = l.id_linhaproduto ")
		   .append(" AND a.id_comissaofaixadesc =:id ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("produtoLinhaId", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("faixaDesc1", Hibernate.DOUBLE)
				.addScalar("faixaDesc2", Hibernate.DOUBLE)
				.addScalar("percComissao", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ComissaoFaixaDescFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (ComissaoFaixaDescFB) query.uniqueResult();
	}
	
	@Override
	public ComissaoFaixaDescFB validarFaixa(ComissaoFaixaDescFB comissaoFaixaDescFB) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("   FROM comissaofaixadesc a, linhaproduto l ")
		   .append("  WHERE a.id_linhaproduto = l.id_linhaproduto ")
		   .append("    AND a.ID_LINHAPRODUTO = :linhaprodutoiD ")
		   .append("    AND ((a.FAIXADESC1 between :faixa1 and :faixa2) or (a.FAIXADESC2 between :faixa1 and :faixa2)) ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("produtoLinhaId", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)				
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("faixaDesc1", Hibernate.DOUBLE)
				.addScalar("faixaDesc2", Hibernate.DOUBLE)
				.addScalar("percComissao", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ComissaoFaixaDescFB.class));
			query.setParameter("linhaprodutoiD", comissaoFaixaDescFB.getProdutoLinhaId());
			query.setParameter("faixa1", comissaoFaixaDescFB.getFaixaDesc1());
			query.setParameter("faixa2", comissaoFaixaDescFB.getFaixaDesc2());
			query.setMaxResults(1);
		return (ComissaoFaixaDescFB) query.uniqueResult();
	}
	
@SuppressWarnings("unchecked")
@Override
public List<ComissaoFaixaDescFB> listar(String linhaProduto, Double faixa1, Double faixa2) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM comissaofaixadesc a, linhaproduto l ")
		   .append(" WHERE a.id_linhaproduto = l.id_linhaproduto ");

		if (linhaProduto != null && !"".equals(linhaProduto)) {
			sql.append(" AND upper(l.descricao) like '%"+linhaProduto.toUpperCase()+"%' ");
		}
		
		if(faixa1 != null) {
			sql.append(" AND a.faixadesc1 >= "+faixa1+" ");
		}

		if(faixa2 != null) {
			sql.append(" AND a.faixadesc2 <= "+faixa2+" ");
		}
		
		sql.append(" ORDER BY l.descricao, a.faixadesc1  ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("produtoLinhaId", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)				
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("faixaDesc1", Hibernate.DOUBLE)
				.addScalar("faixaDesc2", Hibernate.DOUBLE)
				.addScalar("percComissao", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ComissaoFaixaDescFB.class));
		
		
		return query.list();
	}

	@Override
	public Integer insert(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException {
		try {
			
			Integer comissaoFaixaDescFBId = getSeq();
			System.out.println("[ComissaoFaixaDescFBDAOHibernate][insert][id]" + comissaoFaixaDescFBId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO COMISSAOFAIXADESC (ID_COMISSAOFAIXADESC, ID_LINHAPRODUTO, FAIXADESC1, FAIXADESC2, PERCCOMISSAO) ")
			.append("VALUES (:ID_COMISSAOFAIXADESC, ")
			        .append(":ID_LINHAPRODUTO, ")
			        .append(":FAIXADESC1, ")
			        .append(":FAIXADESC2, ")
			        .append(":PERCCOMISSAO) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COMISSAOFAIXADESC", comissaoFaixaDescFBId);
			query.setParameter("ID_LINHAPRODUTO", comissaoFaixaDescFB.getProdutoLinhaId());
	        query.setParameter("FAIXADESC1", comissaoFaixaDescFB.getFaixaDesc1());
	        query.setParameter("FAIXADESC2", comissaoFaixaDescFB.getFaixaDesc2());
	        query.setParameter("PERCCOMISSAO", comissaoFaixaDescFB.getPercComissao());

			query.executeUpdate();
	        
			return comissaoFaixaDescFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
			
	}
	
	@Override
	public void alterar(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException {
		try {
			
			System.out.println("[ComissaoFaixaDescFBDAOHibernate][update][id]" + comissaoFaixaDescFB.getId());
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COMISSAOFAIXADESC SET ")
					        .append("FAIXADESC1 = :FAIXADESC1, ")
					        .append("FAIXADESC2 = :FAIXADESC2, ")
					        .append("PERCCOMISSAO = :PERCCOMISSAO ")
					      .append(" WHERE ID_COMISSAOFAIXADESC = :ID_COMISSAOFAIXADESC");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COMISSAOFAIXADESC", comissaoFaixaDescFB.getId());
	        query.setParameter("FAIXADESC1", comissaoFaixaDescFB.getFaixaDesc1());
	        query.setParameter("FAIXADESC2", comissaoFaixaDescFB.getFaixaDesc2());
	        query.setParameter("PERCCOMISSAO", comissaoFaixaDescFB.getPercComissao());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_comissaoFaixaDesc_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do ComissaoFaixaDescFB.");
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

	@Override
	public ComissaoFaixaDescFB salvar(ComissaoFaixaDescFB comissaoFaixaDescFB) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(Integer Id) throws DAOException {
		try {
			
			System.out.println("[ComissaoFaixaDescFBDAOHibernate][delete][id]" + Id);
		
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM COMISSAOFAIXADESC WHERE ID_COMISSAOFAIXADESC = :ID_COMISSAOFAIXADESC ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COMISSAOFAIXADESC", Id);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	

}
