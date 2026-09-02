package br.com.webapp.model.fb.prodcomposto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ProdCompostoItemFBDAOHibernate implements ProdCompostoItemFBDAO {
	
	private StringBuilder COLUMNS_DTO;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ProdCompostoItemFBDAOHibernate() {
		COLUMNS_DTO = new StringBuilder();
		COLUMNS_DTO.append(" a.id_prodcomposto as prodCompostoId, ")
				   .append(" a.id_produto as produtoId, ")
				   .append(" b.codinterno as produtoCod, ")
				   .append(" b.descricao as produtoDesc, ")
				   .append(" c.descresumida as produtoDescUnid, ")
				   .append(" a.quantidade as quantidade, ")
				   .append(" b.qtddecimal AS qtdDecimal ");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdCompostoItemFBDTO> listar(Integer prodCompostoId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS_DTO)
		   .append(" FROM prodcompostoitem a, produto b, unidade c ")
		   .append(" WHERE a.id_produto = b.id_produto ")
		     .append(" AND b.id_unidade_venda = c.id_unidade ")
		     .append(" AND a.id_prodcomposto = :prodCompostoId ")
		   .append(" ORDER BY b.descricao ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("prodCompostoId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoDescUnid", Hibernate.STRING)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdCompostoItemFBDTO.class));
		query.setParameter("prodCompostoId", prodCompostoId);
		return query.list();
	}
}
