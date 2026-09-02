package br.com.webapp.model.fb.produto;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.Funcoes;

public class ProdutoFBDAOHibernate implements ProdutoFBDAO{
	
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
	
	public ProdutoFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		  COLLUMNS.append("tab1.ID_PRODUTO AS id, ")
				  .append("tab1.CODINTERNO AS codInterno, ") 
				  .append("tab1.DescProd AS descricao, ") // 3
				  .append("tab1.DescLinha AS produtoLinhaDesc, ")
				  .append("tab1.DESCRESUMIDA AS unidadeDesc, ")
				  .append("tab1.unidadeId AS unidadeId, ")
				  .append("tab1.ATIVO AS ativo, ")
				  .append("tab1.QtdDisp AS qtdDisponivel, ")
				  
				  .append("tab1.pesobrutokg AS pesoBrutoKg, ")
				  .append("tab1.pesoliquidokg AS pesoLiquidoKg, ")
				  .append("tab1.qtdvendaatac AS qtdVendaAtac, ")
				  .append("tab1.qtddecimal AS qtdDecimal, ")
				  .append("tab1.qtdPromoMin AS qtdPromoMin, ")
				  .append("tab1.qtdPromoMax AS qtdPromoMax, ")
				  
				  .append("cast((((COALESCE(cond.valadicfinanc, 0) / 100.0) + 1) * tab1.PRECO) as numeric(18,2)) AS preco, ")
				  .append("tab1.ID_GRUPOPRODUTO AS produtoGrupoId, ")
				  .append("tab1.LINHAPRODCODEDT AS produtoLinhaCodEdt, ")
				  .append("tab1.ALIQUOTA AS aliquota, ")
				  .append("tab1.ID_TRIBUTICMS AS tributoIcmsId, ")
				  //.append("cast( iif(coalesce(tab1.PrecoProm,0) <> 0, (((COALESCE(cond.valadicfinanc, 0) / 100.0) + 1) * tab1.PrecoProm), (((COALESCE(cond.valadicfinanc, 0) / 100.0) + 1) * tab1.PRECO)) as numeric(18,2)) AS precoPromo, ") 
				  .append("cast( ")
				  .append("iif(coalesce(tab1.PrecoProm,0) <> 0, (((coalesce(cond.valadicfinanc, 0) / 100.0) + 1) * tab1.PrecoProm), ")
				  .append("  (((coalesce(cond.valadicfinanc, 0) / 100.0) + 1) * tab1.PRECO))   as numeric(18,2)) as precoPromo, ")
				  .append("tab1.controlalote AS controlaLote, ")
				  .append("tab1.obrigaDescLote AS obrigaDescLote, ")
				  .append("tab1.obrigaVencLote AS obrigaVencLote, ")
				  .append("tab1.permitevendasemestoque AS permiteVendaSemEstoque, ")
		  
		  		  .append("tab1.ncmId AS ncmId, ")
		  		  .append("tab1.ncmCodigo AS ncmCodigo, ")
		  		  .append("tab1.ncmOpFisc AS ncmOpFisc, ")
		  		  .append("tab1.MVAINTERNA AS mvaInterna, ")
		  		  .append("tab1.aliqICMS AS aliqICMS, ")
		  		  .append("tab1.aliqPIS AS aliqPIS, ")
		  		  .append("tab1.aliqCOFINS AS aliqCOFINS, ")
		  
		  		  .append("tab1.customedioonline AS custoMedioOnline ");
		  
		  
		  COLLUMNS_PRODCOMPOSTO = new StringBuilder();
		  COLLUMNS_PRODCOMPOSTO.append("tab1.ID_PRODUTO AS id, ")
				  .append("tab1.CODINTERNO AS codInterno, ") 
				  .append("tab1.DescProd AS descricao, ") // 3
				  .append("tab1.DescLinha AS produtoLinhaDesc, ")
				  .append("tab1.DESCRESUMIDA AS unidadeDesc, ")
				  .append("tab1.unidadeId, ")
				  .append("tab1.ATIVO AS ativo, ")
				  
				  .append("(select qtdDisponivel from view_prodcomposto where id_prodcomposto = tab1.ID_PRODUTO and id_empresa = :ID_PESSOA_EMP) AS qtdDisponivel, ") 
				  
				  .append("tab1.pesobrutokg AS pesoBrutoKg, ")
				  .append("tab1.pesoliquidokg AS pesoLiquidoKg, ")
				  .append("tab1.qtdvendaatac AS qtdVendaAtac, ")
				  .append("tab1.qtddecimal AS qtdDecimal, ")
				  
				  .append("tab1.PRECO AS preco, ")
				  .append("tab1.ID_GRUPOPRODUTO AS produtoGrupoId, ")
				  .append("tab1.LINHAPRODCODEDT AS produtoLinhaCodEdt, ")
				  .append("tab1.ALIQUOTA AS aliquota, ")
				  .append("tab1.ID_TRIBUTICMS AS tributoIcmsId, ")
				  .append("iif(coalesce(tab1.PrecoProm,0) <> 0, tab1.PrecoProm, tab1.PRECO) AS precoPromo, ")
				  .append("tab1.controlalote AS controlaLote, ")
				  .append("tab1.obrigaDescLote AS obrigaDescLote, ")
				  .append("tab1.obrigaVencLote AS obrigaVencLote, ")
				  .append("tab1.permitevendasemestoque AS permiteVendaSemEstoque, ")
		  
		  		  .append("tab1.ncmId AS ncmId, ")
		  		  .append("tab1.ncmCodigo AS ncmCodigo, ")
		  		  .append("tab1.ncmOpFisc AS ncmOpFisc, ")
		  		  .append("tab1.MVAINTERNA AS mvaInterna, ")
		  		  .append("tab1.aliqICMS AS aliqICMS, ")
		  		  .append("tab1.aliqPIS AS aliqPIS, ")
		  		  .append("tab1.aliqCOFINS AS aliqCOFINS, ")
		  
		  		  .append("tab1.customedioonline AS custoMedioOnline ");
		  
		ORDERBY = new StringBuilder();
		   ORDERBY.append(" ORDER BY 3 ");

	}

	@Override
	public ProdutoFB carregar(Integer produtoId, Integer permiteVendaSemEstoque, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
		.append(" FROM ")
		 .append("(select ")
		    .append("pr.ID_PRODUTO, ")
		    .append("pr.CODINTERNO, ")
		    .append("pr.DESCRICAO DescProd, ")
		   
		    .append("lp.CODEDT LinhaProdCodEdt, ")
		    .append("lp.DESCRICAO DescLinha, ")

		   .append("(select ")
		      .append("un.DESCRESUMIDA ")
		    .append("from ")
		      .append("UNIDADE un ")
		    .append("where un.ID_UNIDADE = pr.ID_UNIDADE_VENDA ")
		    .append(") DESCRESUMIDA, ")
		    .append("pr.ID_UNIDADE_VENDA unidadeId, ")
		    .append("pr.ATIVO, ")
		   .append("(select ")
		      .append("coalesce(sum(pl2.QUANTIDADE),0) ")
		    .append("from ")
		      .append("PRODUTO_LOCALIDADE pl2, ")
		      .append("LOCALIDADE lc2, ")
		      .append("DEPOSITO dp2, ")
		      .append("EMPRESA ep2 ")
		    .append("where pl2.ID_LOCALIDADE = lc2.ID_LOCALIDADE ")
		    .append("and lc2.ID_DEPOSITO = dp2.ID_DEPOSITO ")
		    .append("and dp2.ID_PESSOA_EMP = ep2.ID_PESSOA ")
		    .append("and pl2.TIPO = 'F' ")
		    .append("and lc2.ESTQDISP  = 1 ")
		    .append("and pl2.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and ((dp2.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp2.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep2.COMPARTILHAESTOQUE = 1)) ")
//		        .append("--mostra o estoque da prÃ›pria empresa ou ")
//		        .append("--se a empresa excherga estoque compartilhado e nâ€žo Ãˆ transferÃ�ncia entâ€žo mostra o estoque das empresas que compartilham estoque ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(re3.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVA re3, ")
		      .append("LOCALIDADE lc3, ")
		      .append("DEPOSITO dp3, ")
		      .append("EMPRESA ep3 ")
		    .append("where re3.ID_LOCALIDADE = lc3.ID_LOCALIDADE ")
		    .append("and lc3.ID_DEPOSITO = dp3.ID_DEPOSITO ")
		    .append("and dp3.ID_PESSOA_EMP = ep3.ID_PESSOA ")
		    .append("and re3.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and re3.TIPO = 'F' ")
		    .append("and lc3.ESTQDISP  = 1 ")
		    .append("and ((dp3.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp3.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep3.COMPARTILHAESTOQUE = 1)) ")
//		        .append("--abate da reserva da prÃ›pria empresa ou ")
//		        .append("--se a empresa excherga estoque compartilhado e nâ€žo Ãˆ transferÃ�ncia entâ€žo abate da reserva das empresas que compartilham estoque ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(rf4.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVAFILA rf4, ")
		      .append("EMPRESA ep4 ")
		    .append("where rf4.ID_PESSOA_EMP = ep4.ID_PESSOA ")
		    .append("and rf4.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and :ISTRANSFERENCIA = 0 ")
		    .append("and ((rf4.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, rf4.ID_PESSOA_EMP)) or ")
		        .append("(:COMPARTILHAESTOQUE = 1 and ep4.ENCHERGAESTQCOMPART = 1)) ")
//		        .append("--se nao E transferencia entâ€žo... ")
//		        .append("--abate da fila da prÃ›pria empresa ou ")
//		        .append("--se a empresa compartilha estoque abate da fila das empresas que enxergam estoque compartilhado) ")
		    .append(") QtdDisp, ")
		      .append("cast((coalesce(tpi.PRECO,0) - iif(coalesce(tpi.PRECO,0) <> 0, coalesce((select first 1 tpf.valordesconto ")
		                                                                                .append("from tabpromocaofdl tpf ")
		                                                                                .append("where tpf.id_produto = tpi.id_produto ")
		                                                                                .append("and tpf.ativo = 1),0) ")
		                                         .append(",0) ")
		               .append(") ")
		               .append("as numeric(18,4) ")
		             .append(") ")
		         .append("Preco , ")
		    .append("pr.ID_GRUPOPRODUTO, ")
		    .append("ALr.aliquota aliquota, ")
    		.append("nof.id_tributicms_de id_tributicms, ")		   
		    .append("(select first 1 ")
		            .append("proi2.VALOR Preco ")
		      .append("from ")
		        .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
		        .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
		          .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
		          .append("and proi2.id_produto = tpi.ID_PRODUTO ")
		          .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		          .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		          .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1)")
		    .append(")) PrecoProm, ")
		    .append("(select first 1 ")
                    .append("proi2.qtdMin ")
		      .append("from ")
		        .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
		      .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
		      .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
		      .append("and proi2.id_produto = tpi.ID_PRODUTO ")
		      .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) qtdPromoMin, ")
		    .append("(select first 1 ")
		            .append("proi2.qtdMax ")
			   .append("from ")
	                .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	           .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	             .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	             .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	             .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		         .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		         .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) qtdPromoMax, ")
		    .append("tp.naoincluifretepreco, ")
		    .append("pr.pesobrutokg, ")
		    .append("pr.pesoliquidokg, ")
		    .append("pr.qtdembfechvenda as qtdvendaatac, ")
		    .append("pr.qtddecimal, ")
		    .append("pr.controlalote, ")
		    .append("pr.obrigaDescLote, ")
			.append("pr.obrigaVencLote, ")
			.append("COALESCE(pr.permitevendasemestoque, 0) AS permitevendasemestoque, ")
		    .append("pr.id_ncm AS ncmId, ")
		    .append("n.cod_ncm AS ncmCodigo, ")
		    .append("CAST((SELECT COUNT(ID_NCM) FROM NCMEMPRESAOPERFISCAL WHERE ID_NCM = pr.ID_NCM AND ID_PESSOA_EMP = :ID_PESSOA_EMP AND ID_TIPOOPERACAOFISCAL = :ID_TIPOOPERACAOFISCAL) AS INT) AS ncmOpFisc, ")
		    .append("ne.MVAINTERNA AS MVAINTERNA, ")
		    
		    .append("al.aliquota AS aliqICMS, ")
		    .append("nof.aliqpis AS aliqPIS, ")
		    .append("nof.aliqcofins AS aliqCOFINS, ")
		    
		    .append("ipr.customedioonline ")
		  .append("from ")
		    .append("PRODUTO pr, ")
		    
		    .append("LINHAPRODUTO lp, ")
		    
		    .append("NCM n, ")
		    .append("NCMEMPRESAOPERFISCAL nof, ")
		    .append("ALIQICMS al, ")
		    .append("TABPRECOITEM tpi, ")
		    .append("TABPRECO tp, ")
		    .append("INFOGER_PRODUTO ipr, ")
		    .append("ncmempresa ne, ")
    		.append("aliqicms alR ")		    
		    
		  .append("where lp.ID_LINHAPRODUTO = pr.ID_LINHAPRODUTO ")    

		  .append("and tpi.ID_PRODUTO = pr.ID_PRODUTO ")
		  .append("and tpi.preco is not null ")
		  .append("and pr.ATIVO = 1 ")
		  .append("and pr.id_ncm = n.id_ncm ")
		  .append("and tpi.id_tabpreco = tp.id_tabpreco ")
		  .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
		  .append("and pr.DISPONIVELVENDA = 1 ")
		  .append("and pr.ID_PRODUTO = :ID_PRODUTO ")
		  .append("and pr.id_produto = ipr.id_produto ")
		  .append("and ipr.id_pessoa_emp = :ID_PESSOA_EMP ")
		  
		  .append("and nof.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and nof.id_ncm = n.id_ncm ")
		  .append("and nof.id_aliqicms_de = al.id_aliqicms ")
		  .append("and nof.id_tipooperacaofiscal = :ID_TIPOOPERACAOFISCAL ")
		  .append("and Ne.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and ne.id_ncm = pr.id_ncm ")
		  .append("and ne.id_aliqicms_ref = ALr.id_aliqicms ")
		  
		  .append(") tab1, CONDPAGTO cond WHERE cond.id_condpagto = :ID_CONDPAGTO ")
		.append("order by ")
		  .append("tab1.DescProd ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
								.addScalar("id", Hibernate.INTEGER)
								.addScalar("codInterno", Hibernate.STRING)
								.addScalar("descricao", Hibernate.STRING)
								.addScalar("produtoLinhaDesc", Hibernate.STRING)
								.addScalar("produtoLinhaCodEdt", Hibernate.STRING)
								.addScalar("unidadeDesc", Hibernate.STRING)
								.addScalar("unidadeId", Hibernate.INTEGER)
								.addScalar("produtoGrupoId", Hibernate.INTEGER)
								.addScalar("ativo", Hibernate.INTEGER)
								.addScalar("qtdDisponivel", Hibernate.DOUBLE)
								.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
								.addScalar("qtdPromoMin", Hibernate.DOUBLE)
								.addScalar("qtdPromoMax", Hibernate.DOUBLE)								
								.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
								.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
								.addScalar("qtdDecimal", Hibernate.INTEGER)
								.addScalar("preco", Hibernate.DOUBLE)
								.addScalar("precoPromo", Hibernate.DOUBLE)
								.addScalar("aliquota", Hibernate.DOUBLE)
								.addScalar("tributoIcmsId", Hibernate.STRING)
								.addScalar("mvaInterna", Hibernate.DOUBLE)
								.addScalar("controlaLote", Hibernate.INTEGER)
								.addScalar("obrigaDescLote", Hibernate.INTEGER)
								.addScalar("obrigaVencLote", Hibernate.INTEGER)
								.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
								.addScalar("ncmId", Hibernate.INTEGER)
								.addScalar("ncmCodigo", Hibernate.STRING)
								.addScalar("ncmOpFisc", Hibernate.INTEGER)
								.addScalar("aliqICMS", Hibernate.DOUBLE)
								.addScalar("aliqPIS", Hibernate.DOUBLE)
								.addScalar("aliqCOFINS", Hibernate.DOUBLE)
								.addScalar("custoMedioOnline", Hibernate.DOUBLE)
								.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("ID_CONDPAGTO", condPagtoId);
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_TIPOOPERACAOFISCAL", opFiscTipoId);
		q.setParameter("EMPENCHESTQCOMPART", empEnchEstqCompart);
		q.setParameter("ISTRANSFERENCIA", isTransferencia);
		q.setParameter("COMPARTILHAESTOQUE", compartilhaEstoque);
		q.setParameter("tipoFrete", tipoFrete);
		
//		q.setParameter("PERMITEVENDASEMESTOQUE", permiteVendaSemEstoque);
		
		q.setMaxResults(1);
		
		return (ProdutoFB) q.uniqueResult();
	}
	
	@Override
	public ProdutoFB carregarProdComposto(Integer empresaId, String tabPrecoId, Integer condPagtoId, Integer pedVendaCompostoId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_PRODCOMPOSTO.toString()) 
		.append(" FROM ")
		.append("(select  ")
        .append("prComp.ID_PRODCOMPOSTO AS ID_PRODUTO, ")
        .append("prComp.CODPRODUTO AS CODINTERNO, ")
        .append("prComp.DESCRICAO AS DescProd, ")
        .append("f.NOMEFANTMNEM AS DescLinha, ")
        .append("(select un.DESCRESUMIDA from UNIDADE un, PRODUTO prUn where un.ID_UNIDADE = prUn.ID_UNIDADE_VENDA AND prUn.ID_PRODUTO = prComp.ID_PRODUTO_BASE) DESCRESUMIDA, ")
        .append("(select un.id_unidade from UNIDADE un, PRODUTO prUn where un.ID_UNIDADE = prUn.ID_UNIDADE_VENDA AND prUn.ID_PRODUTO = prComp.ID_PRODUTO_BASE) unidadeId, ")
        .append("1 AS ATIVO, ")
//        .append("9999999.99 AS QtdDisp,  ")
        .append("(select sum(CAST((CAST((((COALESCE(cond.valadicfinanc, 0) / 100.0) +1) * tpi.preco) as numeric(18,2)) * prci.quantidade) AS numeric(18,2))) ")
                .append("from tabprecoitem tpi, prodcompostoitem prci, CONDPAGTO cond ")
               .append("where tpi.ID_PRODUTO = prci.id_produto ")
                 .append("and prci.id_prodcomposto = prComp.id_prodcomposto ")
                 .append("and tpi.preco is not null ")
                 .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
                 .append("and cond.id_condpagto = :ID_CONDPAGTO ")
            .append(") AS Preco, ")
        .append("NULL AS ID_GRUPOPRODUTO, ")
        .append("NULL AS LinhaProdCodEdt, ")
        .append("NULL AS ALIQUOTA, ")
        .append("NULL AS ID_TRIBUTICMS, ")
        .append("NULL AS PrecoProm, ")
        .append("0 AS naoincluifretepreco, ")
        .append("0 AS pesobrutokg, ")
        .append("0 AS pesoliquidokg, ")
        .append("1 AS qtdvendaatac, ")
        .append("0 AS qtddecimal, ")
        .append("0 AS controlalote, ")
        .append("0 AS obrigaDescLote, ")
		.append("0 AS obrigaVencLote, ")
        .append("0 AS permitevendasemestoque, ")
        .append("NULL AS ncmId, ")
        .append("NULL AS ncmCodigo, ")
        .append("NULL AS ncmOpFisc, ")
        .append("NULL AS MVAINTERNA, ")
        .append("0 AS aliqICMS, ")
	    .append("0 AS aliqPIS, ")
	    .append("0 AS aliqCOFINS, ")
        .append("0 AS customedioonline ")
      .append("from  ")
        .append("PRODCOMPOSTO prComp, PESSOA f, PEDVENDACOMPOSTO pc ")
      .append("where prComp.id_pessoa_forn = f.id_pessoa ")
        .append("and prComp.CODPRODUTO = pc.CODPRODUTO ")
        .append("and pc.ID_PEDVENDACOMPOSTO = :ID_PEDVENDACOMPOSTO ")
      .append(") tab1  ")
    .append("order by tab1.DescProd ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("produtoLinhaCodEdt", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("produtoGrupoId", Hibernate.INTEGER)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoPromo", Hibernate.DOUBLE)
				.addScalar("aliquota", Hibernate.DOUBLE)
				.addScalar("tributoIcmsId", Hibernate.STRING)
				.addScalar("mvaInterna", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("obrigaDescLote", Hibernate.INTEGER)
				.addScalar("obrigaVencLote", Hibernate.INTEGER)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.addScalar("ncmId", Hibernate.INTEGER)
				.addScalar("ncmCodigo", Hibernate.STRING)
				.addScalar("ncmOpFisc", Hibernate.INTEGER)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("custoMedioOnline", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("ID_TABPRECO", tabPrecoId);
		//q.setParameter("ID_UNIDADE", Funcoes.UNIDADE_PADRAO);
		q.setParameter("ID_PEDVENDACOMPOSTO", pedVendaCompostoId);
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_CONDPAGTO", condPagtoId);
		
		q.setMaxResults(1);
		
		return (ProdutoFB) q.uniqueResult();
		
	}
	
	@Override
	public ProdutoPrecoDTO carregarPreco(String tabPrecoId, Integer condPagtoId, Integer produtoId, Integer empresaId, Integer tipoFrete) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pr.id_produto AS id, ")
				 .append(" pr.codinterno AS codInterno, ")
				 .append(" pr.descricao AS descricao, ")
				 .append(" cast((((COALESCE(cond.valadicfinanc, 0) / 100.0) + 1) * tpi.PRECO) as numeric(18,4)) AS preco, ")
				 .append(" cast(((COALESCE(cond.valadicfinanc, 0) / 100.0) + 1) * (select first 1 proi2.VALOR Preco ")
				   .append(" FROM TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
		           .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
		             .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
		             .append("and proi2.id_produto = tpi.ID_PRODUTO ")
		             .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
					 .append(" and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
					 .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
				  .append(")) as numeric(18,4)) precoPromo, ")
				    .append("(select first 1 ")
		            .append("proi2.qtdMin ")
		 	        .append("from ")
		 	        .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
			           .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
			             .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
			             .append("and proi2.id_produto = tpi.ID_PRODUTO ")
			             .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
				      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
				      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
				    .append(")) qtdPromoMin, ")
				    .append("(select first 1 ")
		            .append("proi2.qtdMax ")
		 	        .append("from ")
	                .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
		           .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
		             .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
		             .append("and proi2.id_produto = tpi.ID_PRODUTO ")
		             .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
				      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
				      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
				    .append(")) qtdPromoMax ")
			  .append(" from produto pr, tabprecoitem tpi, CONDPAGTO cond, empresacondpagto ec ")
		      .append(" where tpi.id_produto = pr.ID_PRODUTO ") // 
		        .append(" AND ec.ID_CONDPAGTO = cond.ID_CONDPAGTO ")
			    .append(" AND ec.id_pessoa_emp = :ID_PESSOA_EMP  ")	
		        .append(" and tpi.preco is not null ")
		        .append(" and pr.id_produto = :ID_PRODUTO ")
		        .append(" and tpi.id_tabpreco = :ID_TABPRECO ")
				.append(" and cond.id_condpagto = :ID_CONDPAGTO ");
		
				Query q = (Query) session.createSQLQuery(sql.toString())
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("codInterno", Hibernate.STRING)
						.addScalar("descricao", Hibernate.STRING)
						.addScalar("preco", Hibernate.DOUBLE)
						.addScalar("precoPromo", Hibernate.DOUBLE)
						.addScalar("qtdPromoMin", Hibernate.DOUBLE)
						.addScalar("qtdPromoMax", Hibernate.DOUBLE)						
						.setResultTransformer(Transformers.aliasToBean(ProdutoPrecoDTO.class));
				
				q.setParameter("ID_TABPRECO", tabPrecoId);
				q.setParameter("ID_CONDPAGTO", condPagtoId);
				q.setParameter("ID_PRODUTO", produtoId);
				q.setParameter("ID_PESSOA_EMP", empresaId);
				q.setParameter("tipoFrete", tipoFrete);

				
				q.setMaxResults(1);
		
		return (ProdutoPrecoDTO) q.uniqueResult();
	}
	
	@Override
	public Double comissao(Integer vendedorId, Integer produtoId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select a.perccomissao ")
			    .append(" from comistipovend a, ")
			         .append(" vendedor b ")
			    .append(" where a.id_tipovendedor = b.id_tipovendedor ")
			      .append(" and b.id_pessoa = :ID_VENDEDOR ")
			      .append(" and a.id_produto = :ID_PRODUTO ");
		
		Query q = (Query) session.createSQLQuery(sql.toString());
		q.setParameter("ID_VENDEDOR", vendedorId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setMaxResults(1);
		
		Object result = q.uniqueResult();
		if(result !=null) {
			return ((BigDecimal) result).doubleValue();
		}
		
		return  0.0;
		
	}


	public Double comissaoFaixa(Integer produtoId, Double desconto) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select fc.perccomissao ") 
		     .append("from comissaofaixadesc fc, ")
		          .append("produto p ")
		    .append("where fc.id_linhaproduto = p.id_linhaproduto ")
		      .append("and :DESCONTO between fc.faixadesc1 and fc.faixadesc2 ")
		      .append("and p.id_produto = :ID_PRODUTO ");
		
		Query q = (Query) session.createSQLQuery(sql.toString());
		q.setParameter("DESCONTO", desconto);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setMaxResults(1);
		
		Object result = q.uniqueResult();
		if(result !=null) {
			return ((BigDecimal) result).doubleValue();
		}
		
		return  0.0;
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoFB> listar(int first, int pageSize, String sortField, SortOrder sortOrder, String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (upper(pr.DESCRICAO) like :descricaoFilterLike";
			varWhere += " or upper(pr.CODINTERNO) = :descricaoFilter) ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " upper(pr.DESCRICAO) like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			varWhere += " AND pr.CODBARRA = :codBarraFilter ";
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			varWhere += " AND upper(lp.DESCRICAO) like :linhaProdFilter ";
		}
		
		String varWhereTab1 = " WHERE cond.id_condpagto = :ID_CONDPAGTO "; 
		if(comEstoqueFilter==true && semEstoqueFilter==false) {
			varWhereTab1 += " AND tab1.QtdDisp > 0";
		}
		
		if(comEstoqueFilter==false && semEstoqueFilter==true) {
			varWhereTab1 += " AND tab1.QtdDisp <= 0";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FIRST "+pageSize+" SKIP "+first+" ").append(COLLUMNS.toString()) 
		.append(" FROM ")
		 .append("(select ")
		    .append("pr.ID_PRODUTO, ")
		    .append("pr.CODINTERNO, ")
		    .append("pr.DESCRICAO DescProd, ")
		    .append("lp.CODEDT LinhaProdCodEdt, ")
		    .append("lp.DESCRICAO DescLinha, ")
		   .append("(select ")
		      .append("un.DESCRESUMIDA ")
		    .append("from ")
		      .append("UNIDADE un ")
		    .append("where un.ID_UNIDADE = pr.ID_UNIDADE_VENDA ")
		    .append(") DESCRESUMIDA, ")
		    .append("pr.ID_UNIDADE_VENDA unidadeId, ") 
		    .append("pr.ATIVO, ")
		   .append("(select ")
		      .append("coalesce(sum(pl2.QUANTIDADE),0) ")
		    .append("from ")
		      .append("PRODUTO_LOCALIDADE pl2, ")
		      .append("LOCALIDADE lc2, ")
		      .append("DEPOSITO dp2, ")
		      .append("EMPRESA ep2 ")
		    .append("where pl2.ID_LOCALIDADE = lc2.ID_LOCALIDADE ")
		    .append("and lc2.ID_DEPOSITO = dp2.ID_DEPOSITO ")
		    .append("and dp2.ID_PESSOA_EMP = ep2.ID_PESSOA ")
		    .append("and pl2.TIPO = 'F' ")
		    .append("and lc2.ESTQDISP  = 1 ")
		    .append("and pl2.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and ((dp2.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp2.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep2.COMPARTILHAESTOQUE = 1)) ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(re3.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVA re3, ")
		      .append("LOCALIDADE lc3, ")
		      .append("DEPOSITO dp3, ")
		      .append("EMPRESA ep3 ")
		    .append("where re3.ID_LOCALIDADE = lc3.ID_LOCALIDADE ")
		    .append("and lc3.ID_DEPOSITO = dp3.ID_DEPOSITO ")
		    .append("and dp3.ID_PESSOA_EMP = ep3.ID_PESSOA ")
		    .append("and re3.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and re3.TIPO = 'F' ")
		    .append("and lc3.ESTQDISP  = 1 ")
		    .append("and ((dp3.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp3.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep3.COMPARTILHAESTOQUE = 1)) ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(rf4.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVAFILA rf4, ")
		      .append("EMPRESA ep4 ")
		    .append("where rf4.ID_PESSOA_EMP = ep4.ID_PESSOA ")
		    .append("and rf4.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and :ISTRANSFERENCIA = 0 ")
		    .append("and ((rf4.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, rf4.ID_PESSOA_EMP)) or ")
		        .append("(:COMPARTILHAESTOQUE = 1 and ep4.ENCHERGAESTQCOMPART = 1)) ")
		    .append(") QtdDisp, ")
		      .append("cast((coalesce(tpi.PRECO,0) - iif(coalesce(tpi.PRECO,0) <> 0, coalesce((select first 1 tpf.valordesconto ")
		                                                                                .append("from tabpromocaofdl tpf ")
		                                                                                .append("where tpf.id_produto = tpi.id_produto ")
		                                                                                .append("and tpf.ativo = 1),0) ")
		                                         .append(",0) ")
		               .append(") ")
		               .append("as numeric(18,4) ")
		             .append(") ")
		         .append("Preco , ")
		    .append("pr.ID_GRUPOPRODUTO, ")
		    .append("ALr.aliquota aliquota, ")
    		.append("nof.id_tributicms_de id_tributicms, ")		   
		    .append("(select first 1 ")
            .append("proi2.VALOR Preco ")
 	        .append("from ")
            .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	       .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	         .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	         .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	         .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) PrecoProm, ")
		    .append("(select first 1 ")
            .append("proi2.qtdMin ")
 	        .append("from ")
            .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	       .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	         .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	         .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	         .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) qtdPromoMin, ")
		    .append("(select first 1 ")
            .append("proi2.qtdMax ")
 	        .append("from ")
            .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	       .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	         .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	         .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	         .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
		      .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
		      .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) qtdPromoMax, ")
		    .append("tp.naoincluifretepreco, ")
		    .append("pr.pesobrutokg, ")
		    .append("pr.pesoliquidokg, ")
		    .append("pr.qtdembfechvenda as qtdvendaatac, ")
		    .append("pr.qtddecimal, ")
		    .append("pr.controlalote, ")
		    .append("pr.obrigaDescLote, ")
			.append("pr.obrigaVencLote, ")
		    .append("COALESCE(pr.permitevendasemestoque, 0) AS permitevendasemestoque, ") 
		    .append("pr.id_ncm AS ncmId, ")
		    .append("n.cod_ncm AS ncmCodigo, ") 
		    .append("CAST((SELECT COUNT(ID_NCM) FROM NCMEMPRESAOPERFISCAL WHERE ID_NCM = pr.ID_NCM AND ID_PESSOA_EMP = :ID_PESSOA_EMP AND ID_TIPOOPERACAOFISCAL = :ID_TIPOOPERACAOFISCAL) AS INT) AS ncmOpFisc, ")
		    .append("ne.MVAINTERNA AS MVAINTERNA, ")
		    .append("al.aliquota AS aliqICMS, ")
		    .append("nof.aliqpis AS aliqPIS, ")
		    .append("nof.aliqcofins AS aliqCOFINS, ")
		    .append("ipr.customedioonline ")
		  .append("from ")
		    .append("PRODUTO pr, ")
		    .append("LINHAPRODUTO lp, ")
		    .append("NCM n, ")
		    .append("NCMEMPRESAOPERFISCAL nof, ")
		    .append("ALIQICMS al, ")
		    .append("TABPRECOITEM tpi, ")
		    .append("TABPRECO tp, ")
		    .append("INFOGER_PRODUTO ipr, ")
		    .append("ncmempresa ne, ")
    		.append("aliqicms alR ")		    
		  .append("where lp.ID_LINHAPRODUTO = pr.ID_LINHAPRODUTO ")  
		  .append("and tpi.ID_PRODUTO = pr.ID_PRODUTO ")
		  .append("and tpi.preco is not null ")
		  .append("and pr.id_ncm = n.id_ncm ")
		  .append("and pr.ATIVO = 1 ")
		  .append("and tpi.id_tabpreco = tp.id_tabpreco ")
		  .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
		  .append("and pr.DISPONIVELVENDA = 1 ").append(varWhere)
		  .append("and pr.id_produto = ipr.id_produto ")
		  .append("and ipr.id_pessoa_emp = :ID_PESSOA_EMP ")
		  .append("and nof.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and nof.id_ncm = n.id_ncm ")
		  .append("and nof.id_aliqicms_de = al.id_aliqicms ")
		  .append("and nof.id_tipooperacaofiscal = :ID_TIPOOPERACAOFISCAL ")
		  .append("and Ne.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and ne.id_ncm = pr.id_ncm ")
		  .append("and ne.id_aliqicms_ref = ALr.id_aliqicms ")
		  .append(") tab1, CONDPAGTO cond ")
		  .append(varWhereTab1)
		  .append(getOrderByFiltroProduto(sortField, sortOrder));
		
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("produtoLinhaCodEdt", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("produtoGrupoId", Hibernate.INTEGER)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdPromoMin", Hibernate.DOUBLE)
				.addScalar("qtdPromoMax", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoPromo", Hibernate.DOUBLE)
				.addScalar("aliquota", Hibernate.DOUBLE)
				.addScalar("tributoIcmsId", Hibernate.STRING)
				.addScalar("mvaInterna", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("obrigaDescLote", Hibernate.INTEGER)
				.addScalar("obrigaVencLote", Hibernate.INTEGER)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.addScalar("ncmId", Hibernate.INTEGER)
				.addScalar("ncmCodigo", Hibernate.STRING)
				.addScalar("ncmOpFisc", Hibernate.INTEGER)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("custoMedioOnline", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("ID_CONDPAGTO", condPagtoId);
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_TIPOOPERACAOFISCAL", opFiscTipoId);
		q.setParameter("EMPENCHESTQCOMPART", empEnchEstqCompart);
		q.setParameter("ISTRANSFERENCIA", isTransferencia);
		q.setParameter("COMPARTILHAESTOQUE", compartilhaEstoque);
		q.setParameter("tipoFrete", tipoFrete);
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			q.setParameter("codBarraFilter", codBarraFilter);
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			q.setParameter("linhaProdFilter", "%"+ linhaProdFilter + "%");
		}
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoFB>  listarVendaSemEstoqueDisponivel(int first, int pageSize, String sortField, SortOrder sortOrder, Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId, Integer tipoFrete) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (upper(pr.DESCRICAOupper( like :descricaoFilterLike";
			varWhere += " or upper(pr.CODINTERNO) = :descricaoFilter) ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " upper(pr.DESCRICAO) like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			varWhere += " AND pr.CODBARRA = :codBarraFilter ";
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			varWhere += " AND upper(lp.DESCRICAO) like :linhaProdFilter ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FIRST "+pageSize+" SKIP "+first+" ").append(COLLUMNS.toString()) 
		.append(" FROM ")
		 .append("(select ")
		    .append("pr.ID_PRODUTO, ")
		    .append("pr.CODINTERNO, ")
		    .append("pr.DESCRICAO DescProd, ")
		    .append("lp.CODEDT LinhaProdCodEdt, ")
		    .append("lp.DESCRICAO DescLinha, ")
		   .append("(select ")
		      .append("un.DESCRESUMIDA ")
		    .append("from ")
		      .append("UNIDADE un ")
		    .append("where un.ID_UNIDADE = pr.ID_UNIDADE_VENDA ")
		    .append(") DESCRESUMIDA, ")
		    .append("pr.ID_UNIDADE_VENDA unidadeId, ")		    
		    .append("pr.ATIVO, ")
		    .append("9999999.99 AS QtdDisp, ")
		      .append("cast((coalesce(tpi.PRECO,0) - iif(coalesce(tpi.PRECO,0) <> 0, coalesce((select first 1 tpf.valordesconto ")
		                                                                                .append("from tabpromocaofdl tpf ")
		                                                                                .append("where tpf.id_produto = tpi.id_produto ")
		                                                                                .append("and tpf.ativo = 1),0) ")
		                                         .append(",0) ")
		               .append(") ")
		               .append("as numeric(18,4) ")
		             .append(") ")
		         .append("Preco , ")
		    .append("pr.ID_GRUPOPRODUTO, ")
		    .append("ALr.aliquota aliquota, ")
    		.append("nof.id_tributicms_de id_tributicms, ")		   
		    .append("(select first 1 ")
		            .append("proi2.VALOR Preco ")
		      .append("from ")
              .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
         .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
           .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
           .append("and proi2.id_produto = tpi.ID_PRODUTO ")
           .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
           .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
           .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
		    .append(")) PrecoProm, ")
		    .append("(select first 1 ")
            .append("proi2.qtdMin ")
  	        .append("from ")
            .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	       .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	         .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	         .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	         .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
			  .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
			  .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
			.append(")) qtdPromoMin, ")
			.append("(select first 1 ")
			.append("proi2.qtdMax ")
			.append("from ")
            .append("TABPROMOCAO pro2, TABPROMOCAOITEM proi2  ")
	       .append("where pro2.ID_TABPROMOCAO = proi2.ID_TABPROMOCAO ")
	         .append("and proi2.id_tabpreco = tpi.ID_TABPRECO ")
	         .append("and proi2.id_produto = tpi.ID_PRODUTO ")
	         .append("and pro2.id_pessoa_emp = :ID_PESSOA_EMP ")
	         .append("and current_date between pro2.DATAINICIO and pro2.DATAFIM ")
	         .append("and ((pro2.id_tipofrete = :tipoFrete) OR (pro2.id_tipofrete IS NULL) OR (pro2.id_tipofrete = -1) ")
			.append(")) qtdPromoMax, ")
		    .append("tp.naoincluifretepreco, ")
		    .append("pr.pesobrutokg, ")
		    .append("pr.pesoliquidokg, ")
		    .append("pr.qtdembfechvenda as qtdvendaatac, ")
		    .append("pr.qtddecimal, ")
		    .append("pr.controlalote, ")
		    .append("pr.obrigaDescLote, ")
			.append("pr.obrigaVencLote, ")
		    .append("COALESCE(pr.permitevendasemestoque, 0) AS permitevendasemestoque, ") 
		    .append("pr.id_ncm AS ncmId, ") 
		    .append("n.cod_ncm AS ncmCodigo, ") 
		    .append("CAST((SELECT COUNT(ID_NCM) FROM NCMEMPRESAOPERFISCAL WHERE ID_NCM = pr.ID_NCM AND ID_PESSOA_EMP = :ID_PESSOA_EMP AND ID_TIPOOPERACAOFISCAL = :ID_TIPOOPERACAOFISCAL) AS INT) AS ncmOpFisc, ")
		    .append("ne.MVAINTERNA AS MVAINTERNA, ")
		    
		    .append("al.aliquota AS aliqICMS, ")
		    .append("nof.aliqpis AS aliqPIS, ")
		    .append("nof.aliqcofins AS aliqCOFINS, ")
		    
		    .append("ipr.customedioonline ")
		  .append("from ")
		    .append("PRODUTO pr, ")
		    
	        .append("LINHAPRODUTO lp, ")
		    
		    .append("NCM n, ")
		    .append("NCMEMPRESAOPERFISCAL nof, ")
		    .append("ALIQICMS al, ")
		    
		    .append("TABPRECOITEM tpi, ")
		    .append("TABPRECO tp, ")
		    .append("INFOGER_PRODUTO ipr, ")
		    .append("ncmempresa ne, ")
    		.append("aliqicms alR ")		    
		    
		    
		  .append("where lp.ID_LINHAPRODUTO = pr.ID_LINHAPRODUTO ")
		  
		  .append("and tpi.ID_PRODUTO = pr.ID_PRODUTO ")
		  .append("and tpi.preco is not null ")
		  .append("and pr.id_ncm = n.id_ncm ")
		  .append("and pr.ATIVO = 1 ")
		  .append("and tpi.id_tabpreco = tp.id_tabpreco ")
		  .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
		  .append("and pr.DISPONIVELVENDA = 1 ")
		  .append("and pr.permitevendasemestoque = :PERMITEVENDASEMESTOQUE ").append(varWhere)
		  .append("and pr.id_produto = ipr.id_produto ")
		  .append("and ipr.id_pessoa_emp = :ID_PESSOA_EMP ")
		  
		  .append("and nof.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and nof.id_ncm = n.id_ncm ")
		  .append("and nof.id_aliqicms_de = al.id_aliqicms ")
		  .append("and nof.id_tipooperacaofiscal = :ID_TIPOOPERACAOFISCAL ")
		  .append("and Ne.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and ne.id_ncm = pr.id_ncm ")
		  .append("and ne.id_aliqicms_ref = ALr.id_aliqicms ")
		  
		  .append(") tab1, CONDPAGTO cond WHERE cond.id_condpagto = :ID_CONDPAGTO ")
		  .append(getOrderByFiltroProduto(sortField, sortOrder));
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("produtoLinhaCodEdt", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("produtoGrupoId", Hibernate.INTEGER)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoPromo", Hibernate.DOUBLE)
				.addScalar("aliquota", Hibernate.DOUBLE)
				.addScalar("tributoIcmsId", Hibernate.STRING)
				.addScalar("mvaInterna", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("obrigaDescLote", Hibernate.INTEGER)
				.addScalar("obrigaVencLote", Hibernate.INTEGER)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.addScalar("ncmId", Hibernate.INTEGER)
				.addScalar("ncmCodigo", Hibernate.STRING)
				.addScalar("ncmOpFisc", Hibernate.INTEGER)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("custoMedioOnline", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_TIPOOPERACAOFISCAL", opFiscTipoId);
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("ID_CONDPAGTO", condPagtoId);
		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE);
		q.setParameter("tipoFrete", tipoFrete);
		
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			q.setParameter("codBarraFilter", codBarraFilter);
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			q.setParameter("linhaProdFilter", "%"+ linhaProdFilter + "%");
		}
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoFB>  listarProdutosComposto(int first, int pageSize, String sortField, SortOrder sortOrder, Integer empresaId, String descricaoFilter, String[] splitDescricao, String fabricanteFilter, String tabPrecoId, Integer condPagtoId) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (prComp.DESCRICAOUPPER like :descricaoFilterLike";
			varWhere += " or upper(prComp.CODPRODUTO) = :descricaoFilter) ";
		}
		
		String fornecedorFilter = "";
		if(fornecedorFilter!=null && !"".equals(fornecedorFilter)) {
			varWhere += " AND f.NOMEFANTMNEM like :fabricanteFilter ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " prComp.DESCRICAOUPPER like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(fabricanteFilter!=null && !"".equals(fabricanteFilter)) {
			varWhere += " AND f.NOMEFANTMNEM like :fabricanteFilter ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FIRST "+pageSize+" SKIP "+first+" ").append(COLLUMNS_PRODCOMPOSTO.toString()) 
		.append(" FROM ")
		.append("(select ")
        .append("prComp.ID_PRODCOMPOSTO AS ID_PRODUTO, ")
        .append("prComp.CODPRODUTO AS CODINTERNO, ")
        .append("prComp.DESCRICAO AS DescProd, ")
        .append("f.NOMEFANTMNEM AS DescLinha, ")
       .append("(select un.DESCRESUMIDA from UNIDADE un, PRODUTO prUn where un.ID_UNIDADE = prUn.ID_UNIDADE_VENDA AND prUn.ID_PRODUTO = prComp.ID_PRODUTO_BASE) DESCRESUMIDA, ")
       .append("(select un.id_unidade from UNIDADE un, PRODUTO prUn where un.ID_UNIDADE = prUn.ID_UNIDADE_VENDA AND prUn.ID_PRODUTO = prComp.ID_PRODUTO_BASE) unidadeId, ")
        .append("1 AS ATIVO, ")
        //.append("9999999.99 AS QtdDisp,  ")
        .append("(select sum(CAST((tpi.preco * prci.quantidade) AS numeric(18,2))) ")
                .append("from tabprecoitem tpi, prodcompostoitem prci, CONDPAGTO cond ")
               .append("where tpi.ID_PRODUTO = prci.id_produto ")
                 .append("and prci.id_prodcomposto = prComp.id_prodcomposto ")
                 .append("and tpi.preco is not null ")
                 .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
                 .append("and cond.ID_CONDPAGTO = :ID_CONDPAGTO ")
            .append(") AS Preco, ")
        .append("NULL AS ID_GRUPOPRODUTO, ")
        .append("NULL AS LinhaProdCodEdt, ")
        .append("NULL AS ALIQUOTA, ")
        .append("NULL AS ID_TRIBUTICMS, ")
        .append("NULL AS PrecoProm, ")
        .append("0 AS naoincluifretepreco, ")
        .append("0 AS pesobrutokg, ")
        .append("0 AS pesoliquidokg, ")
        .append("1 AS qtdvendaatac, ")
        .append("0 AS qtddecimal, ")
        .append("0 AS controlalote, ")
        .append("1 AS permitevendasemestoque, ")
        .append("NULL AS ncmId, ")
        .append("NULL AS ncmCodigo, ")
        .append("NULL AS ncmOpFisc, ")
        .append("NULL AS MVAINTERNA, ")
        .append("0 AS aliqICMS, ")
	    .append("0 AS aliqPIS, ")
	    .append("0 AS aliqCOFINS, ")
        .append("0 AS customedioonline ")
      .append("from  ")
        .append("PRODCOMPOSTO prComp, PESSOA f ")
      .append("where prComp.id_pessoa_forn = f.id_pessoa ").append(varWhere)
      .append(") tab1  ")
    .append(getOrderByFiltroProduto(sortField, sortOrder));
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoLinhaDesc", Hibernate.STRING)
				.addScalar("produtoLinhaCodEdt", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("produtoGrupoId", Hibernate.INTEGER)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("precoPromo", Hibernate.DOUBLE)
				.addScalar("aliquota", Hibernate.DOUBLE)
				.addScalar("tributoIcmsId", Hibernate.STRING)
				.addScalar("mvaInterna", Hibernate.DOUBLE)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("obrigaDescLote", Hibernate.INTEGER)
				.addScalar("obrigaVencLote", Hibernate.INTEGER)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.addScalar("ncmId", Hibernate.INTEGER)
				.addScalar("ncmCodigo", Hibernate.STRING)
				.addScalar("ncmOpFisc", Hibernate.INTEGER)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("custoMedioOnline", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("ID_CONDPAGTO", condPagtoId);
		//q.setParameter("ID_UNIDADE", Funcoes.UNIDADE_PADRAO);
		q.setParameter("ID_PESSOA_EMP", empresaId);
		
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(fabricanteFilter!=null && !"".equals(fabricanteFilter)) {
			q.setParameter("fabricanteFilter", "%" + fabricanteFilter + "%");
		}
		
		return q.list();
	}
	
	public Integer countListar(String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (upper(pr.DESCRICAO) like :descricaoFilterLike";
			varWhere += " or upper(pr.CODINTERNO) = :descricaoFilter) ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " upper(pr.DESCRICAO) like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			varWhere += " AND pr.CODBARRA = :codBarraFilter ";
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			varWhere += " AND upper(lp.DESCRICAO) like :linhaProdFilter ";
		}
		
		String varWhereTab1 = ""; 
		if(comEstoqueFilter==true && semEstoqueFilter==false) {
			varWhereTab1 = " WHERE tab1.QtdDisp > 0";
		}
		
		if(comEstoqueFilter==false && semEstoqueFilter==true) {
			varWhereTab1 = " WHERE tab1.QtdDisp <= 0";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CAST(count(tab1.ID_PRODUTO) AS INT) ") 
		.append(" FROM ")
		 .append("(select ")
		    .append("pr.ID_PRODUTO, ")
		    .append("pr.CODINTERNO, ")
		    .append("pr.DESCRICAO DescProd, ")
		   
		    .append("lp.CODEDT LinhaProdCodEdt, ")
		    .append("lp.DESCRICAO DescLinha, ")
		    
		    .append("pr.ATIVO, ")
		   .append("(select ")
		      .append("coalesce(sum(pl2.QUANTIDADE),0) ")
		    .append("from ")
		      .append("PRODUTO_LOCALIDADE pl2, ")
		      .append("LOCALIDADE lc2, ")
		      .append("DEPOSITO dp2, ")
		      .append("EMPRESA ep2 ")
		    .append("where pl2.ID_LOCALIDADE = lc2.ID_LOCALIDADE ")
		    .append("and lc2.ID_DEPOSITO = dp2.ID_DEPOSITO ")
		    .append("and dp2.ID_PESSOA_EMP = ep2.ID_PESSOA ")
		    .append("and pl2.TIPO = 'F' ")
		    .append("and lc2.ESTQDISP  = 1 ")
		    .append("and pl2.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and ((dp2.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp2.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep2.COMPARTILHAESTOQUE = 1)) ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(re3.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVA re3, ")
		      .append("LOCALIDADE lc3, ")
		      .append("DEPOSITO dp3, ")
		      .append("EMPRESA ep3 ")
		    .append("where re3.ID_LOCALIDADE = lc3.ID_LOCALIDADE ")
		    .append("and lc3.ID_DEPOSITO = dp3.ID_DEPOSITO ")
		    .append("and dp3.ID_PESSOA_EMP = ep3.ID_PESSOA ")
		    .append("and re3.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and re3.TIPO = 'F' ")
		    .append("and lc3.ESTQDISP  = 1 ")
		    .append("and ((dp3.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp3.ID_PESSOA_EMP)) or ")
		        .append("(:EMPENCHESTQCOMPART = 1 and ep3.COMPARTILHAESTOQUE = 1)) ")
		    .append(") - ")
		   .append("(select ")
		      .append("coalesce(sum(rf4.QUANTIDADE),0) ")
		    .append("from ")
		      .append("RESERVAFILA rf4, ")
		      .append("EMPRESA ep4 ")
		    .append("where rf4.ID_PESSOA_EMP = ep4.ID_PESSOA ")
		    .append("and rf4.ID_PRODUTO = pr.ID_PRODUTO ")
		    .append("and :ISTRANSFERENCIA = 0 ")
		    .append("and ((rf4.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, rf4.ID_PESSOA_EMP)) or ")
		        .append("(:COMPARTILHAESTOQUE = 1 and ep4.ENCHERGAESTQCOMPART = 1)) ")
		    .append(") QtdDisp, ")
		    .append("tp.naoincluifretepreco, ")
		    .append("pr.pesobrutokg, ")
		    .append("pr.pesoliquidokg, ")
		    .append("pr.qtdembfechvenda as qtdvendaatac, ")
		    .append("pr.qtddecimal, ")
		    .append("pr.controlalote, ")
		    .append("pr.obrigaDescLote, ")
			.append("pr.obrigaVencLote, ")
		    .append("COALESCE(pr.permitevendasemestoque, 0) AS permitevendasemestoque, ") 
		    .append("pr.id_ncm AS ncmId, ")
		    .append("n.cod_ncm AS ncmCodigo, ") 
		    .append("ipr.customedioonline ")
		  .append("from ")
		    .append("PRODUTO pr, ")
		    
		    .append("LINHAPRODUTO lp, ")
		    
		    .append("NCM n, ")
		    .append("NCMEMPRESAOPERFISCAL nof, ")
		    .append("ALIQICMS al, ")
		    .append("TABPRECOITEM tpi, ")
		    .append("TABPRECO tp, ")
		    .append("INFOGER_PRODUTO ipr ")
		  .append("where lp.ID_LINHAPRODUTO = pr.ID_LINHAPRODUTO ")  
		  .append("and tpi.ID_PRODUTO = pr.ID_PRODUTO ")
		  .append("and tpi.preco is not null ")
		  .append("and pr.id_ncm = n.id_ncm ")
		  .append("and pr.ATIVO = 1 ")
		  .append("and tpi.id_tabpreco = tp.id_tabpreco ")
		  .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
		  .append("and pr.DISPONIVELVENDA = 1 ").append(varWhere)
		  .append("and pr.id_produto = ipr.id_produto ")
		  .append("and ipr.id_pessoa_emp = :ID_PESSOA_EMP ")
		  
		  .append("and nof.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and nof.id_ncm = n.id_ncm ")
		  .append("and nof.id_aliqicms_de = al.id_aliqicms ")
		  .append("and nof.id_tipooperacaofiscal = :ID_TIPOOPERACAOFISCAL ")
		  
		  .append(") tab1 ")
		  .append(varWhereTab1);
		
		
		Query q = (Query) session.createSQLQuery(sql.toString());
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_TIPOOPERACAOFISCAL", opFiscTipoId);
		q.setParameter("EMPENCHESTQCOMPART", empEnchEstqCompart);
		q.setParameter("ISTRANSFERENCIA", isTransferencia);
		q.setParameter("COMPARTILHAESTOQUE", compartilhaEstoque);
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			q.setParameter("codBarraFilter", codBarraFilter);
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			q.setParameter("linhaProdFilter", "%"+ linhaProdFilter + "%");
		}
		
		try {
			return (Integer) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public Integer countVendaSemEstoqueDisponivel(Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (upper(pr.DESCRICAO) like :descricaoFilterLike";
			varWhere += " or upper(pr.CODINTERNO) = :descricaoFilter) ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " upper(pr.DESCRICAO) like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			varWhere += " AND pr.CODBARRA = :codBarraFilter ";
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			varWhere += " AND upper(lp.DESCRICAO) like :linhaProdFilter ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CAST(count(tab1.ID_PRODUTO) AS INT) ") 
		.append(" FROM ")
		 .append("(select ")
		    .append("pr.ID_PRODUTO, ")
		    .append("pr.CODINTERNO, ")
		    .append("pr.DESCRICAO DescProd, ")
		   
		    .append("lp.CODEDT LinhaProdCodEdt, ")
		    .append("lp.DESCRICAO DescLinha, ")
		    
		    .append("pr.ATIVO, ")
		    .append("9999999.99 AS QtdDisp, ")
		    .append("pr.ID_GRUPOPRODUTO, ")
		    .append("tp.naoincluifretepreco, ")
		    .append("pr.pesobrutokg, ")
		    .append("pr.pesoliquidokg, ")
		    .append("pr.qtdembfechvenda as qtdvendaatac, ")
		    .append("pr.qtddecimal, ")
		    .append("pr.controlalote, ")
		    .append("pr.obrigaDescLote, ")
			.append("pr.obrigaVencLote, ")
		    .append("COALESCE(pr.permitevendasemestoque, 0) AS permitevendasemestoque, ") 
		    .append("pr.id_ncm AS ncmId, ") 
		    .append("n.cod_ncm AS ncmCodigo, ") 
		    .append("ipr.customedioonline ")
		  .append("from ")
		    .append("PRODUTO pr, ")
		    
		    .append("LINHAPRODUTO lp, ")
		    
		    .append("NCM n, ")
		    .append("NCMEMPRESAOPERFISCAL nof, ")
		    .append("ALIQICMS al, ")
		    .append("TABPRECOITEM tpi, ")
		    .append("TABPRECO tp, ")
		    .append("INFOGER_PRODUTO ipr ")
		  
		  .append("where lp.ID_LINHAPRODUTO = pr.ID_LINHAPRODUTO ")
		  
		  .append("and tpi.ID_PRODUTO = pr.ID_PRODUTO ")
		  .append("and tpi.preco is not null ")
		  .append("and pr.id_ncm = n.id_ncm ")
		  .append("and pr.ATIVO = 1 ")
		  .append("and tpi.id_tabpreco = tp.id_tabpreco ")
		  .append("and tpi.ID_TABPRECO = :ID_TABPRECO ")
		  .append("and pr.DISPONIVELVENDA = 1 ")
		  .append("and pr.permitevendasemestoque = :PERMITEVENDASEMESTOQUE ").append(varWhere)
		  .append("and pr.id_produto = ipr.id_produto ")
		  .append("and ipr.id_pessoa_emp = :ID_PESSOA_EMP ")
		  .append("and nof.id_pessoa_emp = ipr.id_pessoa_emp ")
		  .append("and nof.id_ncm = n.id_ncm ")
		  .append("and nof.id_aliqicms_de = al.id_aliqicms ")
		  .append("and nof.id_tipooperacaofiscal = :ID_TIPOOPERACAOFISCAL ")
		  .append(") tab1 ");
		
		Query q = (Query) session.createSQLQuery(sql.toString());
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_TIPOOPERACAOFISCAL", opFiscTipoId);
		q.setParameter("ID_TABPRECO", tabPrecoId);
		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE);
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			q.setParameter("codBarraFilter", codBarraFilter);
		}
		
		if(linhaProdFilter!=null && !"".equals(linhaProdFilter)) {
			q.setParameter("linhaProdFilter", "%"+ linhaProdFilter + "%");
		}
		
		try {
			return (Integer) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Integer countProdutosComposto(Integer empresaId, String descricaoFilter, String[] splitDescricao, String fabricanteFilter) {
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			varWhere = " AND (upper(pr.DESCRICAO) like :descricaoFilterLike";
			varWhere += " or upper(pr.CODPRODUTO) = :descricaoFilter) ";
		}
		
		String fornecedorFilter = "";
		if(fornecedorFilter!=null && !"".equals(fornecedorFilter)) {
			varWhere += " AND f.NOMEFANTMNEM like :fabricanteFilter ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " upper(pr.DESCRICAO) like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		if(fabricanteFilter!=null && !"".equals(fabricanteFilter)) {
			varWhere += " AND f.NOMEFANTMNEM like :fabricanteFilter ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("select CAST(count(pr.ID_PRODCOMPOSTO) AS INT) ")
		      .append(" from  ")
		        .append("PRODCOMPOSTO pr, PESSOA f ")
		      .append("where pr.id_pessoa_forn = f.id_pessoa ").append(varWhere);
		
		Query q = (Query) session.createSQLQuery(sql.toString());
		if(descricaoFilter!=null && !"".equals(descricaoFilter) && splitDescricao.length==1) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		if(fabricanteFilter!=null && !"".equals(fabricanteFilter)) {
			q.setParameter("fabricanteFilter", "%" + fabricanteFilter + "%");
		}
		
		try {
			return (Integer) q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private String getOrderByFiltroProduto(String orderByCollumn, SortOrder sortOrder) {
		String varSortOrder = " ASC";
		if(sortOrder!=null && sortOrder == SortOrder.DESCENDING) {
			varSortOrder = " DESC";
		}
		if(orderByCollumn!=null && orderByCollumn.equals(this.ORDER_BY_CODIGO)) {
			return " ORDER BY 2" + varSortOrder;
		}else if(orderByCollumn!=null && orderByCollumn.equals(this.ORDER_BY_LINHA)) {
			return " ORDER BY 4" + varSortOrder;
		}else if(orderByCollumn!=null && orderByCollumn.equals(this.ORDER_BY_DESCRICAO)) {
			return " ORDER BY 3" + varSortOrder;
		}else if(orderByCollumn!=null && orderByCollumn.equals(this.ORDER_BY_PRECO)) {
			return " ORDER BY 12" + varSortOrder;
		}else if(orderByCollumn!=null && orderByCollumn.equals(this.ORDER_BY_DISPONIVEL)) {
			return " ORDER BY 7" + varSortOrder;
		}else {
			return " ORDER BY 2" + varSortOrder;
		}
	}

	@Override
	public List<ProdutoFB> listar(String descricao) {
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
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		if(descricao!=null && !"".equals(descricao)) {
			q.setParameter("descricaoFilterLike", "%" + descricao + "%");
			q.setParameter("descricaoFilter", descricao);
		}
		
		q.setParameter("ativo", 1);
		
		return q.list();	
	}

	@Override
	public ProdutoFB carregar(int codigo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.id_produto as id,") 
		   .append("       p.codinterno, ")
		   .append("       p.descresumida as descricao, ")
		   .append("       p.shelflife, ")
		   .append("       p.controlaLote, ")
		   .append("       p.obrigaDescLote, ")
		   .append("       p.obrigaVencLote ")
		   .append(" FROM produto p ")
		   .append(" WHERE p.id_produto = :id ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("shelfLife", Hibernate.INTEGER)
				.addScalar("controlaLote", Hibernate.INTEGER)
				.addScalar("obrigaDescLote", Hibernate.INTEGER)
				.addScalar("obrigaVencLote", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("id", codigo);
		q.setMaxResults(1);
		return (ProdutoFB) q.uniqueResult();	
	}

	@Override
	public void atualizarCodBarras(Integer produtoId, String codBarra) throws DAOException {
		try {
			
			System.out.println("[ProdutoFBDAOHibernate][update][id]" + produtoId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE produto SET ")
					        .append("codbarra = :codBarra ")
					      .append(" WHERE ID_PRODUTO = :produtoId");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("produtoId", produtoId);
			query.setParameter("codBarra", codBarra);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<ProdutoFB> listaProdutosEstoqueSemContagem(Integer empresaId, Integer inventarioId, String produtoFilter) {
		
		String varWhere = "";
		
		if(produtoFilter != null && !"".equals(produtoFilter)) {
			varWhere += " AND (p.codInterno = :produtoFilter "+
		                "      or CB.CODIGOBARRAS = :produtoFilter "+
		                "      or  UPPER(p.descricao) like :produtoFilterLike) ";
		}
		 
		String sql =   " SELECT pl.ID_PRODUTO as id,  "+
						      " max(p.codInterno) AS codInterno, "+
						      " max(CB.CODIGOBARRAS) AS produtoCodBarras, "+
						      " max(p.DESCRICAO) AS descricao,  "+
						      " max(un.DESCRESUMIDA) unidadeDesc, "+
						      " sum(pl.QUANTIDADE) AS qtdDisponivel "+
						 " FROM produto p "+
						      " LEFT JOIN produtocb cb ON (p.ID_PRODUTO = cb.id_produto AND cb.qtd = 1), "+
						      " unidade un, "+
						      " PRODUTO_LOCALIDADE pl  "+
						      " LEFT JOIN COLETOR_INV_CONTAGEM cic ON (PL.ID_PRODUTO = cic.ID_PRODUTO "+
						      "									       AND cic.ID_COLETOR_INV = :inventarioId "+
						      "                                        AND cic.excluido = 0), "+
						      " LOCALIDADE l, "+
						      " DEPOSITO D, "+
						      " EMPRESA E "+
						 " WHERE p.ID_PRODUTO = pl.ID_PRODUTO "+
						   " AND p.ID_UNIDADE_VENDA = un.ID_UNIDADE  "+
						   " AND PL.ID_LOCALIDADE = L.ID_LOCALIDADE "+
						   " AND L.ID_DEPOSITO = D.ID_DEPOSITO  "+
						   " AND D.ID_PESSOA_EMP = E.ID_PESSOA  "+
						   " AND ((d.ID_PESSOA_EMP = coalesce(:empresaId, d.ID_PESSOA_EMP)) or (:EMPENCHESTQCOMPART = 1 and e.COMPARTILHAESTOQUE = 1)) "+
						   " AND p.ATIVO = 1 "+
						   " AND PL.TIPO = 'F' "+
						   " AND L.ESTQDISP = 1 "+
						   " AND PL.QUANTIDADE > 0 "+
						   " AND cic.ID_PRODUTO IS NULL  "+varWhere+
						" GROUP BY pl.ID_PRODUTO  "+
						   " ORDER BY 4 ";
		
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("codInterno", Hibernate.STRING)
				.addScalar("produtoCodBarras", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoFB.class));
		
		q.setParameter("empresaId", empresaId);
		q.setParameter("inventarioId", inventarioId);
		q.setParameter("EMPENCHESTQCOMPART", 1);
		
		if(produtoFilter != null && !"".equals(produtoFilter)) {
			q.setParameter("produtoFilter", produtoFilter.toUpperCase());
			q.setParameter("produtoFilterLike", "%" + produtoFilter.toUpperCase() + "%");
		}
		
		return q.list();	
	}
}
