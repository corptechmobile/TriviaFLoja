package br.com.webapp.model.fb.pedvenda;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.web.util.DAOException;

public class PedVendaItemFBDAOHibernate implements PedVendaItemFBDAO{

	private StringBuilder COLUMNS;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public PedVendaItemFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_PEDVENDAITEM as id, ")
			   .append(" a.ID_PEDVENDA as pedVendaId, ")
			   .append(" a.ID_PRODUTO as produtoId, ")
			   .append(" a.ID_PEDVENDACOMPOSTO as pedVendaCompostoId, ")
			   .append(" a.QUANTIDADE as quantidade, ")
			   .append(" a.PRECOTABELA as precoTabela, ")
			   .append(" a.PRECOPROM as precoProm, ")
			   .append(" a.PRECO as preco, ")
			   .append(" a.PERCDESCONTO as percDesconto, ")
			   .append(" a.PESOLIQUIDOKG as pesoLiquidoKg, ")
			   .append(" a.PESOBRUTOKG as pesoBrutoKg, ")
			   .append(" a.COMISSAO as comissao, ")
			   .append(" a.CUSTOGERULTCOMPRA as custoGerUltCompra,")
			   .append(" a.CUSTOEMBALAGEM as custoEmbalagem ,")
			   .append(" a.CUSTOTERCEIRIZACAO as custoTerceirizacao ,")
			   .append(" a.CUSTOFRETEUNIT as custoFreteUnit,")
			   .append(" a.MKUPATUAL as mkUltAtual ,")
			   .append(" a.PRECOSUGERIDOVENDA as precoSugeridoVenda ,")
			   .append(" a.MKUPCALCULADO as mkUpCalculado ,")
			   .append(" a.VALORCOMISSAO as valorComissao ,")
			   .append(" a.QUANTIDADENF as quantidadeEnf ,")
			   .append(" a.ASSOCNFITEM as assocNfItem ,")
			   .append(" a.QTDSALDOATENDER as qtdSaldoAtender ,")
			   .append(" a.VALORDESCONTO as valorDesconto ,")
			   .append(" a.ID_TRIBUTICMS as idTributICMS ,") //AVISO: Possível chave estrangeira'
			   .append(" a.MVAST as mVast ,")
			   .append(" a.VALORSTUNIT as valorSTUnit ,")
			   .append(" a.CUSTOGERULTCOMPRAUV as custoGerUltCompraUv ,")
			   .append(" a.QUANTORIGINAL as quantOriginal ,")
			   .append(" a.ATUCCVENDEDOR as atuCCVendedor ,")
			   .append(" a.SEQ_PEDVENDAITEM as seqPedVendaItem ,")
			   .append(" a.ALIQIPI as aliqIPI ,")
			   .append(" a.VALIPI as valIPI ,")
			   .append(" a.COMISSAO_FABR as comissaoFabr ,")
			   .append(" a.PRECOREFCCVENDEDOR as precoRefCCVendedor ,")
			   .append(" a.ALIQICMSST as aliqICMSSt ,")
			   .append(" a.ALIQICMS as aliqICMS ,")
			   .append(" a.DOSAGEMINICIAL as dosagemInicial ,")
			   .append(" a.DOSAGEMFINAL as dosagemFinal ,")
			   .append(" a.PRECOMOEDA as precoMoeda, ")
			   .append(" a.QTDEMBALAGEMFECH as qtdeEmbalagemFech, ")
			   .append(" a.PRECOTABELAMOEDA as precoTabelaMoeda, ")
			   .append(" a.ALIQPIS as aliqPIS, ")
			   .append(" a.ALIQCOFINS as aliqCOFINS, ")
			   .append(" a.QTDMINPROMO as qtdMinPromo, ")
			   .append(" a.QTDMAXPROMO as qtdMaxPromo, ")
			   .append(" a.PRECOPROMORIG as precoPromorIg, ")
			   .append(" a.QUANTNFCE as quantNFCE, ")
		       .append(" a.ID_USUARIO_WEB as usuarioWebId ");
		
	}
	
	@Override
	public PedVendaItemFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvendaitem a, pedvenda b ")
		   .append(" WHERE a.ID_PEDVENDA = b.ID_PEDVENDA ")
		   	  .append("AND a.ID_PEDVENDAITEM = :id ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("pedVendaCompostoId", Hibernate.INTEGER)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("comissao", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompra", Hibernate.DOUBLE)
				.addScalar("custoEmbalagem", Hibernate.DOUBLE)
				.addScalar("custoTerceirizacao", Hibernate.DOUBLE)
				.addScalar("custoFreteUnit", Hibernate.DOUBLE)
				.addScalar("mkUltAtual", Hibernate.DOUBLE)
				.addScalar("precoSugeridoVenda", Hibernate.DOUBLE)
				.addScalar("mkUpCalculado", Hibernate.DOUBLE)
				.addScalar("valorComissao", Hibernate.DOUBLE)
				.addScalar("quantidadeEnf", Hibernate.DOUBLE)
				.addScalar("assocNfItem", Hibernate.INTEGER)
				.addScalar("qtdSaldoAtender", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("idTributICMS", Hibernate.STRING)
				.addScalar("mVast", Hibernate.DOUBLE)
				.addScalar("valorSTUnit", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.addScalar("quantOriginal", Hibernate.DOUBLE)
				.addScalar("atuCCVendedor", Hibernate.INTEGER)
				.addScalar("seqPedVendaItem", Hibernate.INTEGER)
				.addScalar("aliqIPI", Hibernate.DOUBLE)
				.addScalar("valIPI", Hibernate.DOUBLE)
				.addScalar("comissaoFabr", Hibernate.DOUBLE)
				.addScalar("precoRefCCVendedor", Hibernate.DOUBLE)
				.addScalar("aliqICMSSt", Hibernate.DOUBLE)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("dosagemInicial", Hibernate.DOUBLE)
				.addScalar("dosagemFinal", Hibernate.DOUBLE)
				.addScalar("precoMoeda", Hibernate.DOUBLE)
				.addScalar("qtdeEmbalagemFech", Hibernate.DOUBLE)
				.addScalar("precoTabelaMoeda", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("qtdMinPromo", Hibernate.DOUBLE)
				.addScalar("qtdMaxPromo", Hibernate.DOUBLE)
				.addScalar("precoPromorIg", Hibernate.DOUBLE)
				.addScalar("quantNFCE", Hibernate.DOUBLE)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFB.class));
		
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (PedVendaItemFB) query.uniqueResult();
	}
	
	@Override
	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvendaitem a, pedvenda b ")
		   .append(" WHERE a.ID_PEDVENDA = b.ID_PEDVENDA ")
		   	  .append("AND a.ID_PEDVENDA = :pedVendaFBId ")
		   	  .append("AND a.ID_PRODUTO = :produtoFBId ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("pedVendaCompostoId", Hibernate.INTEGER)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("comissao", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompra", Hibernate.DOUBLE)
				.addScalar("custoEmbalagem", Hibernate.DOUBLE)
				.addScalar("custoTerceirizacao", Hibernate.DOUBLE)
				.addScalar("custoFreteUnit", Hibernate.DOUBLE)
				.addScalar("mkUltAtual", Hibernate.DOUBLE)
				.addScalar("precoSugeridoVenda", Hibernate.DOUBLE)
				.addScalar("mkUpCalculado", Hibernate.DOUBLE)
				.addScalar("valorComissao", Hibernate.DOUBLE)
				.addScalar("quantidadeEnf", Hibernate.DOUBLE)
				.addScalar("assocNfItem", Hibernate.INTEGER)
				.addScalar("qtdSaldoAtender", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("idTributICMS", Hibernate.STRING)
				.addScalar("mVast", Hibernate.DOUBLE)
				.addScalar("valorSTUnit", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.addScalar("quantOriginal", Hibernate.DOUBLE)
				.addScalar("atuCCVendedor", Hibernate.INTEGER)
				.addScalar("seqPedVendaItem", Hibernate.INTEGER)
				.addScalar("aliqIPI", Hibernate.DOUBLE)
				.addScalar("valIPI", Hibernate.DOUBLE)
				.addScalar("comissaoFabr", Hibernate.DOUBLE)
				.addScalar("precoRefCCVendedor", Hibernate.DOUBLE)
				.addScalar("aliqICMSSt", Hibernate.DOUBLE)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("dosagemInicial", Hibernate.DOUBLE)
				.addScalar("dosagemFinal", Hibernate.DOUBLE)
				.addScalar("precoMoeda", Hibernate.DOUBLE)
				.addScalar("qtdeEmbalagemFech", Hibernate.DOUBLE)
				.addScalar("precoTabelaMoeda", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("qtdMinPromo", Hibernate.DOUBLE)
				.addScalar("qtdMaxPromo", Hibernate.DOUBLE)
				.addScalar("precoPromorIg", Hibernate.DOUBLE)
				.addScalar("quantNFCE", Hibernate.DOUBLE)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFB.class));
		query.setParameter("pedVendaFBId", pedVendaFBId);
		query.setParameter("produtoFBId", produtoFBId);
		query.setMaxResults(1);
		return (PedVendaItemFB) query.uniqueResult();
	}
	
	@Override
	public Integer insert(PedVendaItemFB pedVendaItem) throws DAOException {
		try {
			
			Integer pedVendaItemFBId = getSeq();
			
			System.out.println("[PedVendaItemFBDAOHibernate][insert][id]" + pedVendaItemFBId);
			
			String varCollumns = "";
			String varValues = "";
			if(pedVendaItem.getPedVendaCompostoId()!=null) {
				varCollumns = ", ID_PEDVENDACOMPOSTO";
				varValues = ", :ID_PEDVENDACOMPOSTO";
				
			}
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PEDVENDAITEM (ID_PEDVENDAITEM, ID_PEDVENDA, ID_PRODUTO")
													.append(varCollumns)
													.append(", QUANTIDADE, QUANTIDADESEL, FATORCONVSEL, ID_UNIDADE_VENDASEL, PRECOUNIDVENDASEL, PRECOTABELA, PRECOPROM, PRECOPROMSEL, PRECO, PERCDESCONTO, PESOLIQUIDOKG, PESOBRUTOKG, COMISSAO, CUSTOGERULTCOMPRA, CUSTOEMBALAGEM, CUSTOTERCEIRIZACAO, CUSTOFRETEUNIT, MKUPATUAL, PRECOSUGERIDOVENDA, MKUPCALCULADO, VALORCOMISSAO, QUANTIDADENF, ASSOCNFITEM, QTDSALDOATENDER, VALORDESCONTO, ID_TRIBUTICMS, MVAST, VALORSTUNIT, CUSTOGERULTCOMPRAUV, QUANTORIGINAL, ATUCCVENDEDOR, SEQ_PEDVENDAITEM, ALIQIPI, VALIPI, COMISSAO_FABR, PRECOREFCCVENDEDOR, ALIQICMSST, ALIQICMS, DOSAGEMINICIAL, DOSAGEMFINAL, PRECOMOEDA, QTDEMBALAGEMFECH, PRECOTABELAMOEDA, ALIQPIS, ALIQCOFINS, QTDMINPROMO, QTDMAXPROMO, PRECOPROMORIG, QUANTNFCE, ID_USUARIO_WEB) ")
			.append("VALUES (:ID_PEDVENDAITEM, ")
			        .append(":ID_PEDVENDA, ")
			        .append(":ID_PRODUTO").append(varValues).append(", ")
			        .append(":QUANTIDADE, ")
			        .append(":QUANTIDADESEL, ")
			        .append(":FATORCONVSEL, ")
			        .append(":ID_UNIDADE_VENDASEL, ")
			        .append(":PRECOUNIDVENDASEL, ")
			        .append(":PRECOTABELA, ")
			        .append(":PRECOPROM, ")
			        .append(":PRECOPROMSEL, ")
			        .append(":PRECO, ")
			        .append(":PERCDESCONTO, ")
			        .append(":PESOLIQUIDOKG, ")
			        .append(":PESOBRUTOKG, ")
			        .append(":COMISSAO, ")
			        .append(":CUSTOGERULTCOMPRA, ")
			        .append(":CUSTOEMBALAGEM, ")
			        .append(":CUSTOTERCEIRIZACAO, ")
			        .append(":CUSTOFRETEUNIT, ")
			        .append(":MKUPATUAL, ")
			        .append(":PRECOSUGERIDOVENDA, ")
			        .append(":MKUPCALCULADO, ")
			        .append(":VALORCOMISSAO, ")
			        .append(":QUANTIDADENF, ")
			        .append(":ASSOCNFITEM, ")
			        .append(":QTDSALDOATENDER, ")
			        .append(":VALORDESCONTO, ")
			        .append(":ID_TRIBUTICMS, ")
			        .append(":MVAST, ")
			        .append(":VALORSTUNIT, ")
			        .append(":CUSTOGERULTCOMPRAUV, ")
			        .append(":QUANTORIGINAL, ")
			        .append(":ATUCCVENDEDOR, ")
			        .append(":SEQ_PEDVENDAITEM, ")
			        .append(":ALIQIPI, ")
			        .append(":VALIPI, ")
			        .append(":COMISSAO_FABR, ")
			        .append(":PRECOREFCCVENDEDOR, ")
			        .append(":ALIQICMSST, ")
			        .append(":ALIQICMS, ")
			        .append(":DOSAGEMINICIAL, ")
			        .append(":DOSAGEMFINAL, ")
			        .append(":PRECOMOEDA, ")
			        .append(":QTDEMBALAGEMFECH, ")
			        .append(":PRECOTABELAMOEDA, ")
			        .append(":ALIQPIS, ")
			        .append(":ALIQCOFINS, ")
			        .append(":QTDMINPROMO, ")
			        .append(":QTDMAXPROMO, ")
			        .append(":PRECOPROMORIG, ")
			        .append(":QUANTNFCE, ")
					.append(":ID_USUARIO_WEB) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBId);
			query.setParameter("ID_PEDVENDA", pedVendaItem.getPedVendaId());
			query.setParameter("ID_PRODUTO", pedVendaItem.getProdutoId());
			
			if(pedVendaItem.getPedVendaCompostoId()!=null) {
				query.setParameter("ID_PEDVENDACOMPOSTO", pedVendaItem.getPedVendaCompostoId());
			}
			
			query.setParameter("QUANTIDADE", pedVendaItem.getQuantidade());
			query.setParameter("QUANTIDADESEL", pedVendaItem.getQuantidade());
			query.setParameter("FATORCONVSEL", 1);
			query.setParameter("ID_UNIDADE_VENDASEL", pedVendaItem.getUnidade());
			query.setParameter("PRECOUNIDVENDASEL", pedVendaItem.getPreco());			
			query.setParameter("PRECOTABELA", pedVendaItem.getPrecoTabela());
			query.setParameter("PRECOPROM", pedVendaItem.getPrecoProm() == null ? pedVendaItem.getPrecoTabela() : pedVendaItem.getPrecoProm());
			query.setParameter("PRECOPROMSEL", pedVendaItem.getPrecoProm() == null ? pedVendaItem.getPrecoTabela() : pedVendaItem.getPrecoProm());			
			query.setParameter("PRECO", pedVendaItem.getPreco());
			query.setParameter("PERCDESCONTO", pedVendaItem.getPercDesconto());
			query.setParameter("PESOLIQUIDOKG", pedVendaItem.getPesoLiquidoKg());
			query.setParameter("PESOBRUTOKG", pedVendaItem.getPesoBrutoKg());
			query.setParameter("COMISSAO", pedVendaItem.getComissao());
			query.setParameter("CUSTOGERULTCOMPRA", pedVendaItem.getCustoGerUltCompra());
			query.setParameter("CUSTOEMBALAGEM", pedVendaItem.getCustoEmbalagem());
			query.setParameter("CUSTOTERCEIRIZACAO", pedVendaItem.getCustoTerceirizacao());
			query.setParameter("CUSTOFRETEUNIT", pedVendaItem.getCustoFreteUnit());
			query.setParameter("MKUPATUAL", pedVendaItem.getMkUltAtual());
			query.setParameter("PRECOSUGERIDOVENDA", pedVendaItem.getPrecoSugeridoVenda());
			query.setParameter("MKUPCALCULADO", pedVendaItem.getMkUpCalculado());
			query.setParameter("VALORCOMISSAO", pedVendaItem.getValorComissao());
			query.setParameter("QUANTIDADENF", pedVendaItem.getQuantidadeEnf());
			query.setParameter("ASSOCNFITEM", pedVendaItem.getAssocNfItem());
			query.setParameter("QTDSALDOATENDER", pedVendaItem.getQtdSaldoAtender());
			query.setParameter("VALORDESCONTO", pedVendaItem.getValorDesconto());
			query.setParameter("ID_TRIBUTICMS", pedVendaItem.getIdTributICMS());
			query.setParameter("MVAST", pedVendaItem.getmVast());
			query.setParameter("VALORSTUNIT", pedVendaItem.getValorSTUnit());
			query.setParameter("CUSTOGERULTCOMPRAUV", pedVendaItem.getCustoGerUltCompraUv());
			query.setParameter("QUANTORIGINAL", pedVendaItem.getQuantOriginal());
			query.setParameter("ATUCCVENDEDOR", pedVendaItem.getAtuCCVendedor());
			query.setParameter("SEQ_PEDVENDAITEM", pedVendaItem.getSeqPedVendaItem());
			query.setParameter("ALIQIPI", pedVendaItem.getAliqIPI());
			query.setParameter("VALIPI", pedVendaItem.getValIPI());
			query.setParameter("COMISSAO_FABR", pedVendaItem.getComissaoFabr());
			query.setParameter("PRECOREFCCVENDEDOR", pedVendaItem.getPrecoRefCCVendedor());
			query.setParameter("ALIQICMSST", pedVendaItem.getAliqICMSSt());
			query.setParameter("ALIQICMS", pedVendaItem.getAliqICMS());
			query.setParameter("DOSAGEMINICIAL", pedVendaItem.getDosagemInicial());
			query.setParameter("DOSAGEMFINAL", pedVendaItem.getDosagemFinal());
			query.setParameter("PRECOMOEDA", pedVendaItem.getPrecoMoeda());
			query.setParameter("QTDEMBALAGEMFECH", pedVendaItem.getQtdeEmbalagemFech());
			query.setParameter("PRECOTABELAMOEDA", pedVendaItem.getPrecoTabelaMoeda());
			query.setParameter("ALIQPIS", pedVendaItem.getAliqPIS());
			query.setParameter("ALIQCOFINS", pedVendaItem.getAliqCOFINS());
			query.setParameter("QTDMINPROMO", pedVendaItem.getQtdMinPromo());
			query.setParameter("QTDMAXPROMO", pedVendaItem.getQtdMaxPromo());
			query.setParameter("PRECOPROMORIG", pedVendaItem.getPrecoPromorIg());
			query.setParameter("QUANTNFCE", pedVendaItem.getQuantNFCE());
			query.setParameter("ID_USUARIO_WEB", pedVendaItem.getUsuarioWebId());
			
			query.executeUpdate();
			
			return pedVendaItemFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId, Integer pedVendaCompostoId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvendaitem a, pedvenda b ")
		   .append(" WHERE a.ID_PEDVENDA = b.ID_PEDVENDA ")
		   	  .append("AND a.ID_PEDVENDA = :pedVendaFBId ")
		   	  .append("AND a.ID_PEDVENDACOMPOSTO = :pedVendaCompostoId ")
		   	  .append("AND a.ID_PRODUTO = :produtoFBId ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("pedVendaCompostoId", Hibernate.INTEGER)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("comissao", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompra", Hibernate.DOUBLE)
				.addScalar("custoEmbalagem", Hibernate.DOUBLE)
				.addScalar("custoTerceirizacao", Hibernate.DOUBLE)
				.addScalar("custoFreteUnit", Hibernate.DOUBLE)
				.addScalar("mkUltAtual", Hibernate.DOUBLE)
				.addScalar("precoSugeridoVenda", Hibernate.DOUBLE)
				.addScalar("mkUpCalculado", Hibernate.DOUBLE)
				.addScalar("valorComissao", Hibernate.DOUBLE)
				.addScalar("quantidadeEnf", Hibernate.DOUBLE)
				.addScalar("assocNfItem", Hibernate.INTEGER)
				.addScalar("qtdSaldoAtender", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("idTributICMS", Hibernate.STRING)
				.addScalar("mVast", Hibernate.DOUBLE)
				.addScalar("valorSTUnit", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.addScalar("quantOriginal", Hibernate.DOUBLE)
				.addScalar("atuCCVendedor", Hibernate.INTEGER)
				.addScalar("seqPedVendaItem", Hibernate.INTEGER)
				.addScalar("aliqIPI", Hibernate.DOUBLE)
				.addScalar("valIPI", Hibernate.DOUBLE)
				.addScalar("comissaoFabr", Hibernate.DOUBLE)
				.addScalar("precoRefCCVendedor", Hibernate.DOUBLE)
				.addScalar("aliqICMSSt", Hibernate.DOUBLE)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("dosagemInicial", Hibernate.DOUBLE)
				.addScalar("dosagemFinal", Hibernate.DOUBLE)
				.addScalar("precoMoeda", Hibernate.DOUBLE)
				.addScalar("qtdeEmbalagemFech", Hibernate.DOUBLE)
				.addScalar("precoTabelaMoeda", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("qtdMinPromo", Hibernate.DOUBLE)
				.addScalar("qtdMaxPromo", Hibernate.DOUBLE)
				.addScalar("precoPromorIg", Hibernate.DOUBLE)
				.addScalar("quantNFCE", Hibernate.DOUBLE)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFB.class));
		query.setParameter("pedVendaFBId", pedVendaFBId);
		query.setParameter("produtoFBId", produtoFBId);
		query.setParameter("pedVendaCompostoId", pedVendaCompostoId);
		query.setMaxResults(1);
		return (PedVendaItemFB) query.uniqueResult();
	}
	
	@Override
	public Integer update(PedVendaItemFB pedVendaItem) throws DAOException {
		try {
			
			System.out.println("[PedVendaItemFBDAOHibernate][insert][id]" + pedVendaItem.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDAITEM ")
							.append("SET ")
						    .append("QUANTIDADE = :QUANTIDADE, ")
						    .append("QUANTIDADESEL = :QUANTIDADE, ")
						    .append("PRECOUNIDVENDASEL = :PRECO, ")
						    .append("ID_UNIDADE_VENDASEL = :UNIDADEID, ")
						    .append("PRECOTABELA = :PRECOTABELA, ")
						    .append("PRECOPROM = :PRECOPROM, ")
						    .append("PRECOPROMSEL = :PRECOPROM, ")
						    .append("PRECO = :PRECO, ")
						    .append("PERCDESCONTO = :PERCDESCONTO, ")
						    .append("PESOLIQUIDOKG = :PESOLIQUIDOKG, ")
						    .append("PESOBRUTOKG = :PESOBRUTOKG, ")
						    .append("COMISSAO = :COMISSAO, ")
						    .append("CUSTOGERULTCOMPRA = :CUSTOGERULTCOMPRA, ")
						    .append("CUSTOEMBALAGEM = :CUSTOEMBALAGEM, ")
						    .append("CUSTOTERCEIRIZACAO = :CUSTOTERCEIRIZACAO, ")
						    .append("CUSTOFRETEUNIT = :CUSTOFRETEUNIT, ")
						    .append("MKUPATUAL = :MKUPATUAL, ")
						    .append("PRECOSUGERIDOVENDA = :PRECOSUGERIDOVENDA, ")
						    .append("MKUPCALCULADO = :MKUPCALCULADO, ")
						    .append("VALORCOMISSAO = :VALORCOMISSAO, ")
						    .append("QUANTIDADENF = :QUANTIDADENF, ")
						    .append("ASSOCNFITEM = :ASSOCNFITEM, ")
						    .append("QTDSALDOATENDER = :QTDSALDOATENDER, ")
						    .append("VALORDESCONTO = :VALORDESCONTO, ")
						    .append("ID_TRIBUTICMS = :ID_TRIBUTICMS, ")
						    .append("MVAST = :MVAST, ")
						    .append("VALORSTUNIT = :VALORSTUNIT, ")
						    .append("CUSTOGERULTCOMPRAUV = :CUSTOGERULTCOMPRAUV, ")
						    .append("QUANTORIGINAL = :QUANTORIGINAL, ")
						    .append("ATUCCVENDEDOR = :ATUCCVENDEDOR, ")
						    // TODO .append("SEQ_PEDVENDAITEM = :SEQ_PEDVENDAITEM, ")
						    .append("ALIQIPI = :ALIQIPI, ")
						    .append("VALIPI = :VALIPI, ")
						    .append("COMISSAO_FABR = :COMISSAO_FABR, ")
						    .append("PRECOREFCCVENDEDOR = :PRECOREFCCVENDEDOR, ")
						    .append("ALIQICMSST = :ALIQICMSST, ")
						    .append("ALIQICMS = :ALIQICMS, ")
						    .append("DOSAGEMINICIAL = :DOSAGEMINICIAL, ")
						    .append("DOSAGEMFINAL = :DOSAGEMFINAL, ")
						    .append("PRECOMOEDA = :PRECOMOEDA, ")
						    .append("QTDEMBALAGEMFECH = :QTDEMBALAGEMFECH, ")
						    .append("PRECOTABELAMOEDA = :PRECOTABELAMOEDA, ")
						    .append("ALIQPIS = :ALIQPIS, ")
						    .append("ALIQCOFINS = :ALIQCOFINS, ")
						    .append("QTDMINPROMO = :QTDMINPROMO, ")
						    .append("QTDMAXPROMO = :QTDMAXPROMO, ")
						    .append("PRECOPROMORIG = :PRECOPROMORIG, ")
						    .append("QUANTNFCE = :QUANTNFCE, ")
						    .append("ID_USUARIO_WEB = :ID_USUARIO_WEB ")
						.append("WHERE (ID_PEDVENDAITEM = :ID_PEDVENDAITEM) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDAITEM", pedVendaItem.getId());
			query.setParameter("QUANTIDADE", pedVendaItem.getQuantidade());
			query.setParameter("PRECOTABELA", pedVendaItem.getPrecoTabela());
			query.setParameter("PRECOPROM", pedVendaItem.getPrecoProm() == null ? pedVendaItem.getPrecoTabela() : pedVendaItem.getPrecoProm());
			query.setParameter("PRECO", pedVendaItem.getPreco());
			query.setParameter("PERCDESCONTO", pedVendaItem.getPercDesconto());
			query.setParameter("PESOLIQUIDOKG", pedVendaItem.getPesoLiquidoKg());
			query.setParameter("PESOBRUTOKG", pedVendaItem.getPesoBrutoKg());
			query.setParameter("COMISSAO", pedVendaItem.getComissao());
			query.setParameter("CUSTOGERULTCOMPRA", pedVendaItem.getCustoGerUltCompra());
			query.setParameter("CUSTOEMBALAGEM", pedVendaItem.getCustoEmbalagem());
			query.setParameter("CUSTOTERCEIRIZACAO", pedVendaItem.getCustoTerceirizacao());
			query.setParameter("CUSTOFRETEUNIT", pedVendaItem.getCustoFreteUnit());
			query.setParameter("MKUPATUAL", pedVendaItem.getMkUltAtual());
			query.setParameter("PRECOSUGERIDOVENDA", pedVendaItem.getPrecoSugeridoVenda());
			query.setParameter("MKUPCALCULADO", pedVendaItem.getMkUpCalculado());
			query.setParameter("VALORCOMISSAO", pedVendaItem.getValorComissao());
			query.setParameter("QUANTIDADENF", pedVendaItem.getQuantidadeEnf());
			query.setParameter("ASSOCNFITEM", pedVendaItem.getAssocNfItem());
			query.setParameter("QTDSALDOATENDER", pedVendaItem.getQtdSaldoAtender());
			query.setParameter("VALORDESCONTO", pedVendaItem.getValorDesconto());
			query.setParameter("ID_TRIBUTICMS", pedVendaItem.getIdTributICMS());
			query.setParameter("MVAST", pedVendaItem.getmVast());
			query.setParameter("VALORSTUNIT", pedVendaItem.getValorSTUnit());
			query.setParameter("CUSTOGERULTCOMPRAUV", pedVendaItem.getCustoGerUltCompraUv());
			query.setParameter("QUANTORIGINAL", pedVendaItem.getQuantOriginal());
			query.setParameter("ATUCCVENDEDOR", pedVendaItem.getAtuCCVendedor());
			// TODO NAO PRECISA TRIGGER Q ATUALIZA query.setParameter("SEQ_PEDVENDAITEM", pedVendaItem.getSeqPedVendaItem());
			query.setParameter("ALIQIPI", pedVendaItem.getAliqIPI());
			query.setParameter("VALIPI", pedVendaItem.getValIPI());
			query.setParameter("COMISSAO_FABR", pedVendaItem.getComissaoFabr());
			query.setParameter("PRECOREFCCVENDEDOR", pedVendaItem.getPrecoRefCCVendedor());
			query.setParameter("ALIQICMSST", pedVendaItem.getAliqICMSSt());
			query.setParameter("ALIQICMS", pedVendaItem.getAliqICMS());
			query.setParameter("DOSAGEMINICIAL", pedVendaItem.getDosagemInicial());
			query.setParameter("DOSAGEMFINAL", pedVendaItem.getDosagemFinal());
			query.setParameter("PRECOMOEDA", pedVendaItem.getPrecoMoeda());
			query.setParameter("QTDEMBALAGEMFECH", pedVendaItem.getQtdeEmbalagemFech());
			query.setParameter("PRECOTABELAMOEDA", pedVendaItem.getPrecoTabelaMoeda());
			query.setParameter("ALIQPIS", pedVendaItem.getAliqPIS());
			query.setParameter("ALIQCOFINS", pedVendaItem.getAliqCOFINS());
			query.setParameter("QTDMINPROMO", pedVendaItem.getQtdMinPromo());
			query.setParameter("QTDMAXPROMO", pedVendaItem.getQtdMaxPromo());
			query.setParameter("PRECOPROMORIG", pedVendaItem.getPrecoPromorIg());
			query.setParameter("QUANTNFCE", pedVendaItem.getQuantNFCE());
			query.setParameter("UNIDADEID", pedVendaItem.getUnidade());
			query.setParameter("ID_USUARIO_WEB", pedVendaItem.getUsuarioWebId());
			
			query.executeUpdate();
			
			return pedVendaItem.getId();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public void updatePreco(PedVendaItemFBDTO pedVendaItemFBDTO) throws DAOException {
		try {
			
			System.out.println("[PedVendaItemFBDAOHibernate][updatePreco][id]" + pedVendaItemFBDTO.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDAITEM ")
							.append("SET ")
						    .append("PRECOTABELA = :PRECOTABELA, ")
						    .append("PRECOPROM = :PRECOPROM, ")
						    .append("PRECOPROMSEL = :PRECOPROM, ")
						    .append("PRECO = :PRECO, ")
						    .append("PRECOUNIDVENDASEL = :PRECO, ")
						    .append("PERCDESCONTO = :PERCDESCONTO, ")
						    .append("VALORDESCONTO = :VALORDESCONTO ")
						.append("WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFBDTO.getId());
			query.setParameter("PRECOTABELA", pedVendaItemFBDTO.getPrecoTabela());
			query.setParameter("PRECOPROM", pedVendaItemFBDTO.getPrecoProm() == null ? pedVendaItemFBDTO.getPrecoTabela() : pedVendaItemFBDTO.getPrecoProm());
			query.setParameter("PRECO", pedVendaItemFBDTO.getPreco());
			query.setParameter("PERCDESCONTO", pedVendaItemFBDTO.getPercDesconto());
			query.setParameter("VALORDESCONTO", (pedVendaItemFBDTO.getPrecoTabela() - pedVendaItemFBDTO.getPreco()));
			
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(PedVendaItemFB pedVendaItemFB, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaItemFBDAOHibernate][excluir][id]" + pedVendaItemFB.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDAITEM SET ID_USUARIO_WEB = :usuarioId WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFB.getId());
			query.setParameter("usuarioId", usuarioId);
			
			query.executeUpdate();
			
			sql = new StringBuilder();
			sql.append("DELETE FROM PEDVENDAITEM WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM ");
			
			query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDAITEM", pedVendaItemFB.getId());
			
			query.executeUpdate();

		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaItemFB> listar(PedVendaFB pedVenda) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvendaitem a ")
		   .append(" WHERE a.ID_PEDVENDA = :idPedVenda");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("pedVendaCompostoId", Hibernate.INTEGER)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("comissao", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompra", Hibernate.DOUBLE)
				.addScalar("custoEmbalagem", Hibernate.DOUBLE)
				.addScalar("custoTerceirizacao", Hibernate.DOUBLE)
				.addScalar("custoFreteUnit", Hibernate.DOUBLE)
				.addScalar("mkUltAtual", Hibernate.DOUBLE)
				.addScalar("precoSugeridoVenda", Hibernate.DOUBLE)
				.addScalar("mkUpCalculado", Hibernate.DOUBLE)
				.addScalar("valorComissao", Hibernate.DOUBLE)
				.addScalar("quantidadeEnf", Hibernate.DOUBLE)
				.addScalar("assocNfItem", Hibernate.INTEGER)
				.addScalar("qtdSaldoAtender", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("idTributICMS", Hibernate.STRING)
				.addScalar("mVast", Hibernate.DOUBLE)
				.addScalar("valorSTUnit", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.addScalar("quantOriginal", Hibernate.DOUBLE)
				.addScalar("atuCCVendedor", Hibernate.INTEGER)
				.addScalar("seqPedVendaItem", Hibernate.INTEGER)
				.addScalar("aliqIPI", Hibernate.DOUBLE)
				.addScalar("valIPI", Hibernate.DOUBLE)
				.addScalar("comissaoFabr", Hibernate.DOUBLE)
				.addScalar("precoRefCCVendedor", Hibernate.DOUBLE)
				.addScalar("aliqICMSSt", Hibernate.DOUBLE)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("dosagemInicial", Hibernate.DOUBLE)
				.addScalar("dosagemFinal", Hibernate.DOUBLE)
				.addScalar("precoMoeda", Hibernate.DOUBLE)
				.addScalar("qtdeEmbalagemFech", Hibernate.DOUBLE)
				.addScalar("precoTabelaMoeda", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("qtdMinPromo", Hibernate.DOUBLE)
				.addScalar("qtdMaxPromo", Hibernate.DOUBLE)
				.addScalar("precoPromorIg", Hibernate.DOUBLE)
				.addScalar("quantNFCE", Hibernate.DOUBLE)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFB.class));
		query.setParameter("idPedVenda", pedVenda.getId());
		return query.list();
	}
	
	@Override
	public List<PedVendaItemFB> listarProdCompostos(Integer pedVendaId, Integer pedVendaCompostoId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvendaitem a ")
		   .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA")
			 .append(" AND a.ID_PEDVENDACOMPOSTO = :ID_PEDVENDACOMPOSTO");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("pedVendaCompostoId", Hibernate.INTEGER)
				.addScalar("quantidade", Hibernate.DOUBLE)
				.addScalar("precoTabela", Hibernate.DOUBLE)
				.addScalar("precoProm", Hibernate.DOUBLE)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("percDesconto", Hibernate.DOUBLE)
				.addScalar("pesoLiquidoKg", Hibernate.DOUBLE)
				.addScalar("pesoBrutoKg", Hibernate.DOUBLE)
				.addScalar("comissao", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompra", Hibernate.DOUBLE)
				.addScalar("custoEmbalagem", Hibernate.DOUBLE)
				.addScalar("custoTerceirizacao", Hibernate.DOUBLE)
				.addScalar("custoFreteUnit", Hibernate.DOUBLE)
				.addScalar("mkUltAtual", Hibernate.DOUBLE)
				.addScalar("precoSugeridoVenda", Hibernate.DOUBLE)
				.addScalar("mkUpCalculado", Hibernate.DOUBLE)
				.addScalar("valorComissao", Hibernate.DOUBLE)
				.addScalar("quantidadeEnf", Hibernate.DOUBLE)
				.addScalar("assocNfItem", Hibernate.INTEGER)
				.addScalar("qtdSaldoAtender", Hibernate.DOUBLE)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("idTributICMS", Hibernate.STRING)
				.addScalar("mVast", Hibernate.DOUBLE)
				.addScalar("valorSTUnit", Hibernate.DOUBLE)
				.addScalar("custoGerUltCompraUv", Hibernate.DOUBLE)
				.addScalar("quantOriginal", Hibernate.DOUBLE)
				.addScalar("atuCCVendedor", Hibernate.INTEGER)
				.addScalar("seqPedVendaItem", Hibernate.INTEGER)
				.addScalar("aliqIPI", Hibernate.DOUBLE)
				.addScalar("valIPI", Hibernate.DOUBLE)
				.addScalar("comissaoFabr", Hibernate.DOUBLE)
				.addScalar("precoRefCCVendedor", Hibernate.DOUBLE)
				.addScalar("aliqICMSSt", Hibernate.DOUBLE)
				.addScalar("aliqICMS", Hibernate.DOUBLE)
				.addScalar("dosagemInicial", Hibernate.DOUBLE)
				.addScalar("dosagemFinal", Hibernate.DOUBLE)
				.addScalar("precoMoeda", Hibernate.DOUBLE)
				.addScalar("qtdeEmbalagemFech", Hibernate.DOUBLE)
				.addScalar("precoTabelaMoeda", Hibernate.DOUBLE)
				.addScalar("aliqPIS", Hibernate.DOUBLE)
				.addScalar("aliqCOFINS", Hibernate.DOUBLE)
				.addScalar("qtdMinPromo", Hibernate.DOUBLE)
				.addScalar("qtdMaxPromo", Hibernate.DOUBLE)
				.addScalar("precoPromorIg", Hibernate.DOUBLE)
				.addScalar("quantNFCE", Hibernate.DOUBLE)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaItemFB.class));
		query.setParameter("ID_PEDVENDA", pedVendaId);
		query.setParameter("ID_PEDVENDACOMPOSTO", pedVendaCompostoId);
		return query.list();
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_PEDVENDAITEM_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaItemFB.");
		}
	}
}
