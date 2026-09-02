package br.com.webapp.model.fb.produtocb;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.web.util.DAOException;

public class ProdutoCBFBDAOHibernate implements ProdutoCBFBDAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public ProdutoCBFB carregar(String codigobarras) {
		Criteria criteria = this.session.createCriteria(ProdutoCBFB.class, "p");
		criteria.createCriteria("p.produto", "pr", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("p.id.codigoBarras,", codigobarras));
		criteria.add(Restrictions.eq("p.excluido", false));
		criteria.setMaxResults(1);
		
		return (ProdutoCBFB) criteria.uniqueResult();
	}

	@Override
	public ProdutoCBFB carregar(Integer produtoId, String codigobarras) {
		Criteria criteria = this.session.createCriteria(ProdutoCBFB.class, "p");
		criteria.createCriteria("p.produto", "pr", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("p.id.produtoId", produtoId));
		criteria.add(Restrictions.eq("p.id.codigoBarras", codigobarras));
		criteria.add(Restrictions.eq("p.excluido", false));
		criteria.setMaxResults(1);
		
		return (ProdutoCBFB) criteria.uniqueResult();
	}
	
	public ProdutoCBFB carregar(Integer produtoId, Double qtd) {
		Criteria criteria = this.session.createCriteria(ProdutoCBFB.class, "p");
		criteria.createCriteria("p.produto", "pr", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("p.id.produtoId", produtoId));
		criteria.add(Restrictions.eq("p.qtd", qtd));
		criteria.add(Restrictions.eq("p.excluido", false));
		criteria.setMaxResults(1);
		
		return (ProdutoCBFB) criteria.uniqueResult();
	}

	@Override
	public void excluir(ProdutoCBFB produtoCBFB) throws DAOException {
		try {
			
			StringBuilder hql = new StringBuilder();
			hql.append("update ProdutoCBFB "
							+ " set excluido = true, "
								+ " usuarioUpdateId = :usuarioUpdateId, "
								+ " dtUpdate = current_timestamp ")
					      .append(" WHERE id.produtoId = :ID_PRODUTO ")
					      .append("  AND id.codigoBarras = :CODIGOBARRAS");
			
			Query query = (Query) session.createQuery(hql.toString());
			query.setParameter("ID_PRODUTO", produtoCBFB.getId().getProdutoId());
			query.setParameter("CODIGOBARRAS", produtoCBFB.getId().getCodigoBarras());
			query.setParameter("usuarioUpdateId", produtoCBFB.getUsuarioUpdateId());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoCBFB> listar() {
		Criteria criteria = this.session.createCriteria(ProdutoCBFB.class, "p");
		criteria.createCriteria("p.produto", "pr", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("p.excluido", false));
		criteria.addOrder(Order.asc("p.id.codigoBarras"));
		return criteria.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoCBFB> listar(ProdutoLinhaFB produtoLinhaFilter, String produtoFilter, String codigoBarraFilter) {
		Criteria criteria = this.session.createCriteria(ProdutoCBFB.class, "p");
		criteria.createCriteria("p.produto", "pr", Criteria.INNER_JOIN);
		
		if(produtoLinhaFilter != null) {
			criteria.createCriteria("pr.produtoLinha", "prl", Criteria.INNER_JOIN);
			Disjunction dProdLinha = Restrictions.disjunction();
			dProdLinha.add(Restrictions.like("prl.codEDT", produtoLinhaFilter.getCodEDT() + "%"));
			dProdLinha.add(Restrictions.eq("prl.codEDT", produtoLinhaFilter.getCodEDT()));
			criteria.add(dProdLinha);
		}
		
		if(produtoFilter != null && !"".equals(produtoFilter)) {
			Disjunction dProd = Restrictions.disjunction();
			dProd.add(Restrictions.like("pr.descricao", "%"+produtoFilter.trim()+"%").ignoreCase());
			dProd.add(Restrictions.eq("pr.codInterno", produtoFilter.trim()).ignoreCase());
			criteria.add(dProd);
		}

		if(codigoBarraFilter != null && !"".equals(codigoBarraFilter)) {
			criteria.add(Restrictions.eq("p.id.codigoBarras", codigoBarraFilter));
		}
		
		criteria.add(Restrictions.eq("p.excluido", false));
		
		criteria.addOrder(Order.asc("p.id.codigoBarras"));
		return criteria.list();
	}

	@Override
	public void update(ProdutoCBFB produtoCBFB) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PRODUTOCB SET ")
					        .append("QTD = :QTD, ")
					        .append("DT_UPDATE = :DT_UPDATE, ")
					        .append("ID_USUARIO_UPDATE = :ID_USUARIO_UPDATE ")
					      .append(" WHERE ID_PRODUTO = :ID_PRODUTO ")
					      .append("   AND CODIGOBARRAS = :CODIGOBARRAS");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PRODUTO", produtoCBFB.getId().getProdutoId());
			query.setParameter("CODIGOBARRAS", produtoCBFB.getId().getCodigoBarras());
	        query.setParameter("QTD", produtoCBFB.getQtd());
	        query.setParameter("DT_UPDATE", produtoCBFB.getDtUpdate());
	        query.setParameter("ID_USUARIO_UPDATE", produtoCBFB.getUsuarioUpdateId());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}		
	}

	@Override
	public void insert(ProdutoCBFB produtoCBFB) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PRODUTOCB (ID_PRODUTO,  CODIGOBARRAS, QTD, DT_CREATE, DT_UPDATE, ID_USUARIO_CREATE, ID_USUARIO_UPDATE, EXCLUIDO) ")
			.append("VALUES (:ID_PRODUTO, ")
			        .append(":CODIGOBARRAS, ")
			        .append(":QTD, ")
			        .append(":DT_CREATE, ")
			        .append(":DT_UPDATE, ")
			        .append(":ID_USUARIO_CREATE, ")
			        .append(":ID_USUARIO_UPDATE, ")
			        .append(":EXCLUIDO) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PRODUTO", produtoCBFB.getId().getProdutoId());
			query.setParameter("CODIGOBARRAS", produtoCBFB.getId().getCodigoBarras());
	        query.setParameter("QTD", produtoCBFB.getQtd());
	        query.setParameter("DT_CREATE", produtoCBFB.getDtCreate());
	        query.setParameter("DT_UPDATE", produtoCBFB.getDtUpdate());
	        query.setParameter("ID_USUARIO_CREATE", produtoCBFB.getUsuarioCreateId());
	        query.setParameter("ID_USUARIO_UPDATE", produtoCBFB.getUsuarioUpdateId());
	        query.setParameter("EXCLUIDO", produtoCBFB.isExcluido());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}		
	}
	
	@Override
	public ProdutoCBFB salvar(ProdutoCBFB produtoCBFB) throws DAOException {
		try {
			ProdutoCBFB merger = (ProdutoCBFB) this.session.merge(produtoCBFB);
			this.session.flush();
			this.session.clear();
			return merger;
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}	
	}

}
