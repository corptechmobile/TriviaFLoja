package br.com.webapp.model.fb.infogerproduto;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.coletor.ColetorInvFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.Funcoes;

public class InfoGerProdutoFBDAOHibernate implements InfoGerProdutoFBDAO{
	
	public static String ORDER_BY_CODIGO = "codInterno";
	public static String ORDER_BY_LINHA = "produtoLinhaDesc";
	public static String ORDER_BY_DESCRICAO = "descricao";
	public static String ORDER_BY_DISPONIVEL = "qtdDisponivel";
	public static String ORDER_BY_PRECO = "preco";
	
	private StringBuilder COLLUMNS;
	private StringBuilder COLLUMNS_PRODCOMPOSTO;
	private StringBuilder ORDERBY;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public InfoGerProdutoFBDAOHibernate() {
	}

	@Override
	public List<InfoGerProdutoFB> listar(String descricao) {
		String varWhere = ""; 
		if(descricao!=null && !"".equals(descricao)) {
			varWhere = " AND (p.descricao like :descricaoFilterLike or p.codinterno = :descricaoFilter ) ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.id_produto as id,") 
		   .append("       p.codinterno, ")
		   .append("       p.descricao ")
				.append(" FROM produto p ")
				.append(" WHERE p.ativo = :ativo ")
 			    .append(varWhere);
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(InfoGerProdutoFB.class));
		
		if(descricao!=null && !"".equals(descricao)) {
			q.setParameter("descricaoFilterLike", "%" + descricao + "%");
			q.setParameter("descricaoFilter", descricao);
		}
		
		q.setParameter("ativo", 1);
		
		return q.list();	
	}

	@Override
	public InfoGerProdutoFB carregar(int produtoId) {
		
		String sql = "select igp.id_produto as produtoId, "+
					 "       igp.customedio as custoMedio, "+
					 "       igp.customedioonline as custoMedioOnLine, "+
					 "       igp.custogeratual as custoGerAtual "+
					 "  from infoger_produto igp,  "+
					 "       produto p "+
					 " where p.id_produto = igp.id_produto "+
					 "   and p.codinterno = :produtoId ";		
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("custoMedio", Hibernate.DOUBLE)
				.addScalar("custoMedioOnLine", Hibernate.DOUBLE)
				.addScalar("custoGerAtual", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(InfoGerProdutoFB.class));
		
		q.setParameter("produtoId", produtoId);
		
		return (InfoGerProdutoFB) q.uniqueResult();	
	}
	
	@Override
	public void update(InfoGerProdutoFB infoGerProdutoFB) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE INFOGER_PRODUTO SET ")
					        .append("CUSTOMEDIO = :CUSTOMEDIO, ")
					        .append("CUSTOMEDIOONLINE = :CUSTOMEDIOONLINE, ")
					        .append("CUSTOGERATUALUV = :CUSTOGERATUALUV ")
					      .append(" WHERE ID_PRODUTO = :ID_PRODUTO");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PRODUTO", infoGerProdutoFB.getProdutoId());
			query.setParameter("CUSTOMEDIO", infoGerProdutoFB.getCustoMedio());
			query.setParameter("CUSTOMEDIOONLINE", infoGerProdutoFB.getCustoMedioOnLine());
	        query.setParameter("CUSTOGERATUALUV", infoGerProdutoFB.getCustoGerAtual());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}
	
	
	

	
}
