package br.com.webapp.model.fb.produtolinha;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFB;

public class ProdutoLinhaFBDAOHibernate implements ProdutoLinhaFBDAO{
	
	private StringBuilder COLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ProdutoLinhaFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_LINHAPRODUTO as id, ")
			   .append(" a.ID_LINHAPRODUTO_PAI as produtoLinhaPaiId, ")
			   .append(" a.CODEDT as codEDT, ")
			   .append(" a.ORDEM as ordem, ")
			   .append(" a.DESCRICAO as descricao ");
	}
	
	@Override
	public ProdutoLinhaFB carregar(Integer prodLinhaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("FROM linhaproduto a ")
		   .append(" WHERE a.ID_LINHAPRODUTO =:id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("produtoLinhaPaiId", Hibernate.INTEGER)
				.addScalar("codEDT", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ProdutoLinhaFB.class));
			query.setParameter("id", prodLinhaId);
			query.setMaxResults(1);
		
		return  (ProdutoLinhaFB) query.uniqueResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoLinhaFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("FROM linhaproduto a ")
		   .append(" ORDER BY a.CODEDT ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("produtoLinhaPaiId", Hibernate.INTEGER)
				.addScalar("codEDT", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ProdutoLinhaFB.class));

		return query.list();
	}

}
