package br.com.webapp.model.fb.pedvendaitem.dto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class PedVendaItemFBDTODAOHibernate implements PedVendaItemFBDTODAO {
	
	private StringBuilder COLLUMNS;

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	public PedVendaItemFBDTODAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_PEDVENDAITEM AS id, ")
			    .append(" a.ID_PEDVENDA AS pedVendaId, ")
			    .append(" a.ID_PRODUTO AS produtoId, ") 
			    .append(" (select pc.ID_PRODCOMPOSTO FROM PEDVENDACOMPOSTO pv INNER JOIN PRODCOMPOSTO pc ON (pv.CODPRODUTO = pc.CODPRODUTO AND pv.ID_PEDVENDACOMPOSTO = a.ID_PEDVENDACOMPOSTO)) AS prodCompostoId, ") 
			    .append(" b.CODINTERNO AS produtoCodInterno, ")
			    .append(" b.DESCRICAO AS produtoDesc, ")
			    .append(" c.DESCRESUMIDA AS unidadeDesc, ")
			    .append(" a.quantidade AS quantidade, ")
			    .append(" a.PRECO AS preco, ")
			    .append(" a.PRECOPROM AS precoProm, ")
			    .append(" a.PRECOTABELA AS precoTabela, ")
			    .append(" a.PERCDESCONTO AS percDesconto, ")
			    .append(" a.VALORDESCONTO AS valorDesconto, ")
			    .append(" a.PESOBRUTOKG AS pesoBrutoKg, ")
			    .append(" a.PESOLIQUIDOKG AS pesoLiquidoKg, ")
			    .append(" b.qtddecimal AS qtdDecimal, ")
			    .append(" b.qtdembfechvenda AS qtdVendaAtac, ")
			    .append(" (select first 1 proi2.qtdMin from TABPROMOCAO pro2 INNER JOIN TABPROMOCAOITEM proi2 ON (pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO and proi2.id_tabpreco = pv.ID_TABPRECO and proi2.id_produto = a.ID_PRODUTO and pro2.id_pessoa_emp = pv.id_pessoa_emp and current_date between pro2.DATAINICIO and pro2.DATAFIM)) qtdPromoMin, ")
			    .append(" (select first 1 proi2.qtdMax from TABPROMOCAO pro2 INNER JOIN TABPROMOCAOITEM proi2 ON (pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO and proi2.id_tabpreco = pv.ID_TABPRECO and proi2.id_produto = a.ID_PRODUTO and pro2.id_pessoa_emp = pv.id_pessoa_emp and current_date between pro2.DATAINICIO and pro2.DATAFIM)) qtdPromoMax, ")
			    .append(" b.controlalote AS controlaLote, ")
				.append(" a.custogerultcomprauv AS custoGerUltCompraUv ");
		
		
			    
	}
	
	@Override
	public PedVendaItemFBDTO carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS)
		   .append(" FROM pedvenda pv, pedvendaitem a, produto b, unidade c ")
		   .append(" WHERE pv.id_pedvenda = a.id_pedvenda ")
		      .append("AND a.ID_PRODUTO = b.ID_PRODUTO ")
		   	  .append("AND b.ID_UNIDADE_VENDA = c.ID_UNIDADE ")
			  .append("AND a.ID_PEDVENDAITEM = :id ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("prodCompostoId", Hibernate.INTEGER)
				.addScalar("produtoCodInterno", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdPromoMin", Hibernate.DOUBLE)
				.addScalar("qtdPromoMax", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFBDTO.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (PedVendaItemFBDTO) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaItemFBDTO> listar(Integer pedVendaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLLUMNS)
		   .append(" FROM pedvenda pv ")
		   .append("      INNER JOIN pedvendaitem a ON (pv.id_pedvenda = a.id_pedvenda) ")
		   .append("      INNER JOIN produto b ON (a.ID_PRODUTO = b.ID_PRODUTO) ")
           .append("      INNER JOIN unidade c ON (b.ID_UNIDADE_VENDA = c.ID_UNIDADE) ")
		   .append(" WHERE  a.ID_PEDVENDA = :pedVendaId ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("prodCompostoId", Hibernate.INTEGER)
				.addScalar("produtoCodInterno", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdPromoMin", Hibernate.DOUBLE)
				.addScalar("qtdPromoMax", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)				
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFBDTO.class));
		
		query.setParameter("pedVendaId", pedVendaId);
		
		return query.list();
	}
	
}
