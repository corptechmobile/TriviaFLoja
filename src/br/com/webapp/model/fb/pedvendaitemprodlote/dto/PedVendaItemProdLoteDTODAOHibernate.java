package br.com.webapp.model.fb.pedvendaitemprodlote.dto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class PedVendaItemProdLoteDTODAOHibernate implements PedVendaItemProdLoteDTODAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<PedVendaItemProdLoteDTO> listar(Integer pedVendaItemFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tab.id_produto AS produtoId, ")
				  .append(" SUM(tab.quantidade) AS qtd, ")
				  .append(" tab.codlote AS codlote, ")
				  .append(" MAX(tab.vencimento) AS dtVencimento, ")
				  .append(" MAX(pr.qtddecimal) AS qtdDecimal ")
	    .append(" FROM ( ")
	        .append(" SELECT b.id_produto, ")
	               .append(" d.quantidade, ")
	               .append(" e.codlote, ")
	               .append(" e.vencimento ")
	        .append(" FROM pedvendaitem b, ")
	             .append(" reserva c, ")
	             .append(" reservaprodutolote d, ")
	             .append(" produtolote e ")
	        .append(" WHERE b.id_pedvendaitem = c.id_pedvendaitem ")
	          .append(" AND c.id_reserva = d.id_reserva ")
	          .append(" AND d.id_produtolote = e.id_produtolote ")
	          .append(" AND b.id_pedvendaitem = :pedVendaItemFBId ")
	    .append(" UNION ALL ")
	        .append(" SELECT b.id_produto, ")
	               .append(" e.quantidade, ")
	               .append(" g.codlote, ")
	               .append(" g.vencimento ")
	        .append(" FROM pedvendaitem b, ")
	             .append(" ordemcarregitem c, ")
	             .append(" regsaidaitem d, ")
	             .append(" regsaidaitemlote e, ")
	             .append(" produtolote g ")
	        .append(" WHERE b.id_pedvendaitem = c.id_pedvendaitem ")
	          .append(" AND c.id_ordemcarregitem = d.id_ordemcarrgitem ")
	          .append(" AND d.id_regsaidaitem = e.id_regsaidaitem ")
	          .append(" AND e.id_produtolote = g.id_produtolote ")
	          .append(" AND b.id_pedvendaitem = :pedVendaItemFBId ")
	    .append(" ) tab, produto pr ")
	    .append(" WHERE tab.id_produto = pr.id_produto ")
	    .append(" GROUP BY tab.codlote, tab.id_produto ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("codlote", Hibernate.STRING)
				.addScalar("dtVencimento", Hibernate.DATE)
				.addScalar("qtd", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemProdLoteDTO.class));
		
		query.setParameter("pedVendaItemFBId", pedVendaItemFBId);
		
		return query.list();
	}

}
