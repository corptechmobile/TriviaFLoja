package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorOrdSepLote;

public class DAOColetorOrdSepLote {
	
	private Session session;

	public DAOColetorOrdSepLote(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorOrdSepLote carregar(Integer ordemCarregItemId, Integer produtoId, Integer produtoLoteId) {
		
		String sql = "  select "+
				 "    tab1.id_produtolote||tab1.id_localidade||tab1.id_deposito AS id,  "+
				 "	  tab1.id_produtolote AS produtoLoteId, "+
				 "	  tab1.id_localidade AS localidadeId, "+
				 "	  tab1.descLocalidade AS localidadeDesc,  "+
				 "	  tab1.codrefproduto AS produtoCodRef, "+
				 "	  tab1.DescProd AS produtoDesc, "+
				 "	  tab1.DescUnid AS unidade, "+
				 "	  tab1.codlote AS codLote, "+
				 "	  tab1.VencLote AS dtVencLote, "+
				 "	  tab1.QtdDisp AS qtdDisponivel, "+
				 "	  tab1.qtdReservada AS qtdReservada, "+
				 "	  tab1.id_deposito AS depositoId, "+
				 "	  tab1.id_pessoa_emp AS empresaId "+
				 "	from "+
				 "	  ( "+
				 "	    SELECT "+
				 "	      prl.id_produtolote, "+
				 "	      lc.id_localidade, "+
				 "	      lc.nome descLocalidade, "+
				 "	      pr.codrefproduto, "+
				 "	      pr.desclogistica DescProd, "+
				 "	      un.descresumida DescUnid, "+
				 "	      prl.codlote, "+
				 "	      prl.vencimento VencLote, "+
				 "	      epl.disponivel QtdDisp, "+
				 "	      coalesce((select "+
				 "	        sum(ocirl.quantidade) quantidade "+
				 "	        from "+
				 "	          ordemcarregitem_reslote ocirl, "+
				 "	          reservaprodutolote rpl "+
				 "	        where "+
				 "	          ocirl.id_produtolote = epl.id_produtolote "+
				 "	        and "+
				 "	          rpl.id_localidade = epl.id_localidade "+
				 "	        and "+
				 "	          ocirl.id_ordemcarrgitem = oci.id_ordemcarregitem "+
				 "	        and "+
				 "	          ocirl.id_reservaprodutolote = rpl.id_reservaprodutolote),0) qtdReservada, "+
				 "	      (select "+
			   	 "	        rpl.id_reservaprodutolote "+
				 "	        from "+
				 "	          reservaprodutolote rpl "+
				 "	        where "+
				 "	          rpl.id_produtolote = epl.id_produtolote "+
				 "	        and "+
				 "	          rpl.id_localidade = epl.id_localidade "+
				 "	        and "+
				 "	          rpl.id_ordemcarrgitem = oci.id_ordemcarregitem) id_reservaprodutolote, "+
				 "	      dp.id_deposito,  "+
				 "	      dp.id_pessoa_emp "+
				 "	    from "+
				 "	      PEDVENDAITEM pvi, "+
				 "	      PEDVENDA pv, "+
				 "	      ORDEMCARREGITEM oci, "+
				 "	      estoque_produtolote epl, "+
				 "	      produtolote prl, "+
				 "	      PRODUTO pr, "+
				 "	      UNIDADE un, "+
				 "	      LOCALIDADE lc, "+
				 "	      DEPOSITO dp "+
				 "	    where oci.ID_PEDVENDAITEM = pvi.ID_PEDVENDAITEM "+
				 "	    and pvi.ID_PEDVENDA = pv.ID_PEDVENDA "+
				 "	    and oci.id_ordemcarregitem = :ordemCarregItemId "+
				 "	    and epl.id_produto = :produtoId "+
				 "	    and epl.id_produto = pvi.id_produto "+
				 "	    and epl.id_produto = pr.id_produto "+
				 "	    and prl.id_produtolote = epl.id_produtolote "+
				 "	    and prl.id_produtolote = :produtoLoteId "+
				 "	    and pr.id_unidade_venda = un.id_unidade "+
				 "	    and epl.id_localidade = lc.id_localidade "+
				 "	    and lc.id_deposito = dp.id_deposito "+
				 "	    and dp.id_pessoa_emp = pv.id_pessoa_emp "+
				 "	    and lc.estqdisp = 1 "+
				 "	    and epl.total <> 0 "+
				 "	  ) tab1 "+
				 "	where "+
				 "	  tab1.QtdDisp <> 0 or tab1.qtdReservada <> 0 "+
				 "	order by "+
				 "	  tab1.qtdReservada desc ";	
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepLote.class);
		query.setParameter("ordemCarregItemId", ordemCarregItemId);
		query.setParameter("produtoId", produtoId);
		query.setParameter("produtoLoteId", produtoLoteId);
		query.setMaxResults(1);
		
		return (ColetorOrdSepLote) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorOrdSepLote> listar(Integer ordemCarregItemId, Integer produtoId) {
		
		String sql = "  select "+
				 "    tab1.id_produtolote||tab1.id_localidade||tab1.id_deposito AS id,  "+
				 "	  tab1.id_pessoa_emp AS empresaId, "+
				 "	  tab1.id_produtolote AS produtoLoteId, "+
				 "	  tab1.id_localidade AS localidadeId, "+
				 "	  tab1.descLocalidade AS localidadeDesc,  "+
				 "    tab1.id_produto as produtoId, "+
				 "	  tab1.codrefproduto AS produtoCodRef, "+
				 "	  tab1.DescProd AS produtoDesc, "+
				 "	  tab1.DescUnid AS unidade, "+
				 "	  tab1.codlote AS codLote, "+
				 "	  tab1.VencLote AS dtVencLote, "+
				 "	  tab1.QtdDisp AS qtdDisponivel, "+
				 "	  tab1.qtdReservada AS qtdReservada, "+
				 "	  tab1.id_deposito AS depositoId "+
				 "	from "+
				 "	  ( "+
				 "	    SELECT "+
				 "	      prl.id_produtolote, "+
				 "	      lc.id_localidade, "+
				 "	      lc.nome descLocalidade, "+
				 "	      pr.codrefproduto, "+
				 "	      pr.desclogistica DescProd, "+
				 "	      un.descresumida DescUnid, "+
				 "	      prl.codlote, "+
				 "	      prl.vencimento VencLote, "+
				 "	      epl.disponivel QtdDisp, "+
				 "	      coalesce((select "+
				 "	        sum(ocirl.quantidade) quantidade "+
				 "	        from "+
				 "	          ordemcarregitem_reslote ocirl, "+
				 "	          reservaprodutolote rpl "+
				 "	        where "+
				 "	          ocirl.id_produtolote = epl.id_produtolote "+
				 "	        and "+
				 "	          rpl.id_localidade = epl.id_localidade "+
				 "	        and "+
				 "	          ocirl.id_ordemcarrgitem = oci.id_ordemcarregitem "+
				 "	        and "+
				 "	          ocirl.id_reservaprodutolote = rpl.id_reservaprodutolote),0) qtdReservada, "+
				 "	      (select "+
			   	 "	        rpl.id_reservaprodutolote "+
				 "	        from "+
				 "	          reservaprodutolote rpl "+
				 "	        where "+
				 "	          rpl.id_produtolote = epl.id_produtolote "+
				 "	        and "+
				 "	          rpl.id_localidade = epl.id_localidade "+
				 "	        and "+
				 "	          rpl.id_ordemcarrgitem = oci.id_ordemcarregitem) id_reservaprodutolote, "+
				 "	      dp.id_deposito,  "+
				 "	      pr.id_produto,  "+
				 "	      dp.id_pessoa_emp "+
				 "	    from "+
				 "	      PEDVENDAITEM pvi, "+
				 "	      PEDVENDA pv, "+
				 "	      ORDEMCARREGITEM oci, "+
				 "	      estoque_produtolote epl, "+
				 "	      produtolote prl, "+
				 "	      PRODUTO pr, "+
				 "	      UNIDADE un, "+
				 "	      LOCALIDADE lc, "+
				 "	      DEPOSITO dp "+
				 "	    where oci.ID_PEDVENDAITEM = pvi.ID_PEDVENDAITEM "+
				 "	    and pvi.ID_PEDVENDA = pv.ID_PEDVENDA "+
				 "	    and oci.id_ordemcarregitem = :ordemCarregItemId "+
				 "	    and epl.id_produto = :produtoId "+
				 "	    and epl.id_produto = pvi.id_produto "+
				 "	    and epl.id_produto = pr.id_produto "+
				 "	    and prl.id_produtolote = epl.id_produtolote "+
				 "	    and pr.id_unidade_venda = un.id_unidade "+
				 "	    and epl.id_localidade = lc.id_localidade "+
				 "	    and lc.id_deposito = dp.id_deposito "+
				 "	    and dp.id_pessoa_emp = pv.id_pessoa_emp "+
				 "	    and lc.estqdisp = 1 "+
				 "	    and epl.total <> 0 "+
				 "	  ) tab1 "+
				 "	where "+
				 "	  tab1.QtdDisp <> 0 or tab1.qtdReservada <> 0 "+
				 "	order by "+
				 "	  tab1.qtdReservada desc ";		
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepLote.class);
		query.setParameter("ordemCarregItemId", ordemCarregItemId);
		query.setParameter("produtoId", produtoId);
		
		return query.list();
	}
	
}