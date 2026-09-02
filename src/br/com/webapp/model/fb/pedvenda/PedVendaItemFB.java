package br.com.webapp.model.fb.pedvenda;

import java.io.Serializable;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;

//@Entity
public class PedVendaItemFB implements Serializable {
	
	private static final long serialVersionUID = 8612830435027203747L;
	
	public static final Double PERCDESCONTO = 0.0;
	public static final Double PESOLIQUIDOKG = 0.0;
	public static final Double PESOBRUTOKG = 0.0;
	public static final Double COMISSAO = 0.0;
	public static final Double CUSTOGERULTCOMPRA = 0.0;
	public static final Double CUSTOEMBALAGEM = 0.0;
	public static final Double CUSTOTERCEIRIZACAO = 0.0;
	public static final Double CUSTOFRETEUNIT = 0.0;
	public static final Double MKUPATUAL = 0.0;
	public static final Double PRECOSUGERIDOVENDA = 0.0;
	public static final Double MKUPCALCULADO = 0.0;
	public static final Double VALORCOMISSAO = 0.0;
	public static final Double QUANTIDADENF = 0.0;
	public static final Integer ASSOCNFITEM = 0;
	public static final Double QTDSALDOATENDER = 0.0;
	public static final Double VALORDESCONTO = 0.0;
	public static final String ID_TRIBUTICMS = null;
	public static final Double MVAST = 0.0;
	public static final Double VALORSTUNIT = 0.0;
	public static final Double CUSTOGERULTCOMPRAUV = 0.0;
	public static final Double QUANTORIGINAL = 0.0;
	public static final Integer ATUCCVENDEDOR = 0;
	public static final Double ALIQIPI = 0.0;
	public static final Double VALIPI = 0.0;
	public static final Double COMISSAO_FABR = 0.0;
	public static final Double PRECOREFCCVENDEDOR = 0.0;
	public static final Double ALIQICMSST = 0.0;
	public static final Double ALIQICMS = 0.0;
	public static final Double DOSAGEMINICIAL = 0.0;
	public static final Double DOSAGEMFINAL = 0.0;
	public static final Double PRECOMOEDA = 0.0;
	public static final Double QTDEMBALAGEMFECH = 0.0;
	public static final Double PRECOTABELAMOEDA = 0.0;
	public static final Double ALIQPIS = 0.0;
	public static final Double ALIQCOFINS = 0.0;
	public static final Double QTDMINPROMO = 0.0;
	public static final Double QTDMAXPROMO = 0.0;
	public static final Double PRECOPROMORIG = 0.0;
	public static final Double QUANTNFCE = 0.0;
	public static final Integer SEQ_PEDVENDAITEM = 0;

	//PK
//	@Id
//	@Column
	private Integer id;
	//FK
	
//	@Column
	private Integer pedVendaId;
	
//	@Column
	private Integer pedVendaCompostoId;
	
//	@Column
	private Integer produtoId;
	
	private Integer usuarioWebId;
	
	//Atributos
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double quantidade;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoTabela;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoProm;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double preco;
	
	//Venda Sel
	private Double quantidadeSel;	
	
	private Double precoUnidVendaSel;
	
	private Double fatorConvSel;
	
	private Integer unidadeVendaSel;	
	
	private Integer unidade;	
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double percDesconto;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double pesoLiquidoKg;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double pesoBrutoKg;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double comissao;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double custoGerUltCompra;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double custoEmbalagem;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double custoTerceirizacao;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double custoFreteUnit;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double mkUltAtual;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double precoSugeridoVenda;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double mkUpCalculado;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double valorComissao;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double quantidadeEnf;
	
//	@Column(name = "assocnfitem")
	private Integer assocNfItem;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double qtdSaldoAtender;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double valorDesconto;
	
//	@Column
	private String idTributICMS;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double mVast;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double valorSTUnit;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double custoGerUltCompraUv;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double quantOriginal;
	
//	@Column
	private Integer atuCCVendedor;
	
//	@Column
	private Integer seqPedVendaItem;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double aliqIPI;
	
//	@Column(columnDefinition = "Decimal(18,2) default '0.00'")
	private Double valIPI;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double comissaoFabr;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoRefCCVendedor;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double aliqICMSSt;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double aliqICMS;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double dosagemInicial;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double dosagemFinal;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoMoeda;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double qtdeEmbalagemFech;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoTabelaMoeda;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double aliqPIS;
	
//	@Column(columnDefinition = "Decimal(5,2) default '0.00'")
	private Double aliqCOFINS;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double qtdMinPromo;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double qtdMaxPromo;
	
//	@Column(columnDefinition = "Decimal(18,4) default '0.0000'")
	private Double precoPromorIg;
	
//	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double quantNFCE;
	
	@Transient
	private Double subTotal;
	
	public PedVendaItemFB() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPedVendaCompostoId() {
		return pedVendaCompostoId;
	}

	public void setPedVendaCompostoId(Integer pedVendaCompostoId) {
		this.pedVendaCompostoId = pedVendaCompostoId;
	}
	
	public Integer getPedVendaId() {
		return pedVendaId;
	}
	public void setPedVendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
	}
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	public Integer getUsuarioWebId() {
		return usuarioWebId;
	}

	public void setUsuarioWebId(Integer usuarioWebId) {
		this.usuarioWebId = usuarioWebId;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPrecoTabela() {
		return precoTabela;
	}
	public void setPrecoTabela(Double precoTabela) {
		this.precoTabela = precoTabela;
	}
	public Double getPrecoProm() {
		return precoProm;
	}
	public void setPrecoProm(Double precoProm) {
		this.precoProm = precoProm;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getQuantidadeSel() {
		return quantidadeSel;
	}

	public void setQuantidadeSel(Double quantidadeSel) {
		this.quantidadeSel = quantidadeSel;
	}

	public Double getPrecoUnidVendaSel() {
		return precoUnidVendaSel;
	}

	public void setPrecoUnidVendaSel(Double precoUnidVendaSel) {
		this.precoUnidVendaSel = precoUnidVendaSel;
	}

	public Double getFatorConvSel() {
		return fatorConvSel;
	}

	public void setFatorConvSel(Double fatorConvSel) {
		this.fatorConvSel = fatorConvSel;
	}

	public Integer getUnidadeVendaSel() {
		return unidadeVendaSel;
	}

	public void setUnidadeVendaSel(Integer unidadeVendaSel) {
		this.unidadeVendaSel = unidadeVendaSel;
	}

	public Integer getUnidade() {
		return unidade;
	}

	public void setUnidade(Integer unidade) {
		this.unidade = unidade;
	}

	public Double getPercDesconto() {
		return percDesconto;
	}
	public void setPercDesconto(Double percDesconto) {
		this.percDesconto = percDesconto;
	}
	public Double getPesoLiquidoKg() {
		return pesoLiquidoKg;
	}
	public void setPesoLiquidoKg(Double pesoLiquidoKg) {
		this.pesoLiquidoKg = pesoLiquidoKg;
	}
	public Double getPesoBrutoKg() {
		return pesoBrutoKg;
	}
	public void setPesoBrutoKg(Double pesoBrutoKg) {
		this.pesoBrutoKg = pesoBrutoKg;
	}
	public Double getComissao() {
		return comissao;
	}
	public void setComissao(Double comissao) {
		this.comissao = comissao;
	}
	public Double getCustoGerUltCompra() {
		return custoGerUltCompra;
	}
	public void setCustoGerUltCompra(Double custoGerUltCompra) {
		this.custoGerUltCompra = custoGerUltCompra;
	}
	public Double getCustoEmbalagem() {
		return custoEmbalagem;
	}
	public void setCustoEmbalagem(Double custoEmbalagem) {
		this.custoEmbalagem = custoEmbalagem;
	}
	public Double getCustoTerceirizacao() {
		return custoTerceirizacao;
	}
	public void setCustoTerceirizacao(Double custoTerceirizacao) {
		this.custoTerceirizacao = custoTerceirizacao;
	}
	public Double getCustoFreteUnit() {
		return custoFreteUnit;
	}
	public void setCustoFreteUnit(Double custoFreteUnit) {
		this.custoFreteUnit = custoFreteUnit;
	}
	public Double getMkUltAtual() {
		return mkUltAtual;
	}
	public void setMkUltAtual(Double mkUltAtual) {
		this.mkUltAtual = mkUltAtual;
	}
	public Double getPrecoSugeridoVenda() {
		return precoSugeridoVenda;
	}
	public void setPrecoSugeridoVenda(Double precoSugeridoVenda) {
		this.precoSugeridoVenda = precoSugeridoVenda;
	}
	public Double getMkUpCalculado() {
		return mkUpCalculado;
	}
	public void setMkUpCalculado(Double mkUpCalculado) {
		this.mkUpCalculado = mkUpCalculado;
	}
	public Double getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(Double valorComissao) {
		this.valorComissao = valorComissao;
	}
	public Double getQuantidadeEnf() {
		return quantidadeEnf;
	}
	public void setQuantidadeEnf(Double quantidadeEnf) {
		this.quantidadeEnf = quantidadeEnf;
	}
	public Integer getAssocNfItem() {
		return assocNfItem;
	}
	public void setAssocNfItem(Integer assocNfItem) {
		this.assocNfItem = assocNfItem;
	}
	public Double getQtdSaldoAtender() {
		return qtdSaldoAtender;
	}
	public void setQtdSaldoAtender(Double qtdSaldoAtender) {
		this.qtdSaldoAtender = qtdSaldoAtender;
	}
	public Double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public String getIdTributICMS() {
		return idTributICMS;
	}
	public void setIdTributICMS(String idTributICMS) {
		this.idTributICMS = idTributICMS;
	}
	public Double getmVast() {
		return mVast;
	}
	public void setmVast(Double mVast) {
		this.mVast = mVast;
	}
	public Double getValorSTUnit() {
		return valorSTUnit;
	}
	public void setValorSTUnit(Double valorSTUnit) {
		this.valorSTUnit = valorSTUnit;
	}
	public Double getCustoGerUltCompraUv() {
		return custoGerUltCompraUv;
	}
	public void setCustoGerUltCompraUv(Double custoGerUltCompraUv) {
		this.custoGerUltCompraUv = custoGerUltCompraUv;
	}
	public Double getQuantOriginal() {
		return quantOriginal;
	}
	public void setQuantOriginal(Double quantOriginal) {
		this.quantOriginal = quantOriginal;
	}
	public Integer getAtuCCVendedor() {
		return atuCCVendedor;
	}
	public void setAtuCCVendedor(Integer atuCCVendedor) {
		this.atuCCVendedor = atuCCVendedor;
	}
	public Integer getSeqPedVendaItem() {
		return seqPedVendaItem;
	}
	public void setSeqPedVendaItem(Integer seqPedVendaItem) {
		this.seqPedVendaItem = seqPedVendaItem;
	}
	public Double getAliqIPI() {
		return aliqIPI;
	}
	public void setAliqIPI(Double aliqIPI) {
		this.aliqIPI = aliqIPI;
	}
	public Double getValIPI() {
		return valIPI;
	}
	public void setValIPI(Double valIPI) {
		this.valIPI = valIPI;
	}
	public Double getComissaoFabr() {
		return comissaoFabr;
	}
	public void setComissaoFabr(Double comissaoFabr) {
		this.comissaoFabr = comissaoFabr;
	}
	public Double getPrecoRefCCVendedor() {
		return precoRefCCVendedor;
	}
	public void setPrecoRefCCVendedor(Double precoRefCCVendedor) {
		this.precoRefCCVendedor = precoRefCCVendedor;
	}
	public Double getAliqICMSSt() {
		return aliqICMSSt;
	}
	public void setAliqICMSSt(Double aliqICMSSt) {
		this.aliqICMSSt = aliqICMSSt;
	}
	public Double getAliqICMS() {
		return aliqICMS;
	}
	public void setAliqICMS(Double aliqICMS) {
		this.aliqICMS = aliqICMS;
	}
	public Double getDosagemInicial() {
		return dosagemInicial;
	}
	public void setDosagemInicial(Double dosagemInicial) {
		this.dosagemInicial = dosagemInicial;
	}
	public Double getDosagemFinal() {
		return dosagemFinal;
	}
	public void setDosagemFinal(Double dosagemFinal) {
		this.dosagemFinal = dosagemFinal;
	}
	public Double getPrecoMoeda() {
		return precoMoeda;
	}
	public void setPrecoMoeda(Double precoMoeda) {
		this.precoMoeda = precoMoeda;
	}
	public Double getQtdeEmbalagemFech() {
		return qtdeEmbalagemFech;
	}
	public void setQtdeEmbalagemFech(Double qtdeEmbalagemFech) {
		this.qtdeEmbalagemFech = qtdeEmbalagemFech;
	}
	public Double getPrecoTabelaMoeda() {
		return precoTabelaMoeda;
	}
	public void setPrecoTabelaMoeda(Double precoTabelaMoeda) {
		this.precoTabelaMoeda = precoTabelaMoeda;
	}
	public Double getAliqPIS() {
		return aliqPIS;
	}
	public void setAliqPIS(Double aliqPIS) {
		this.aliqPIS = aliqPIS;
	}
	public Double getAliqCOFINS() {
		return aliqCOFINS;
	}
	public void setAliqCOFINS(Double aliqCOFINS) {
		this.aliqCOFINS = aliqCOFINS;
	}
	public Double getQtdMinPromo() {
		return qtdMinPromo;
	}
	public void setQtdMinPromo(Double qtdMinPromo) {
		this.qtdMinPromo = qtdMinPromo;
	}
	public Double getQtdMaxPromo() {
		return qtdMaxPromo;
	}
	public void setQtdMaxPromo(Double qtdMaxPromo) {
		this.qtdMaxPromo = qtdMaxPromo;
	}
	public Double getPrecoPromorIg() {
		return precoPromorIg;
	}
	public void setPrecoPromorIg(Double precoPromorIg) {
		this.precoPromorIg = precoPromorIg;
	}
	public Double getQuantNFCE() {
		return quantNFCE;
	}
	public void setQuantNFCE(Double quantNFCE) {
		this.quantNFCE = quantNFCE;
	}
	
	// Transients
	public Double getSubTotal() {
		subTotal = Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4,(preco * quantidade)));
		return subTotal;
	}
	
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof PedVendaItemFB) ) return false;
        final PedVendaItemFB o = (PedVendaItemFB) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId().hashCode();
        return result;
    }

}