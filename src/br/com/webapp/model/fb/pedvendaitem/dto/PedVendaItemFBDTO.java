package br.com.webapp.model.fb.pedvendaitem.dto;

import java.io.Serializable;
import java.util.List;

import br.com.webapp.model.fb.pedvendaitemprodlote.dto.PedVendaItemProdLoteDTO;
import br.com.webapp.web.util.Funcoes;

//@Entity
public class PedVendaItemFBDTO implements Serializable {

	private static final long serialVersionUID = 566533800723410421L;
	
	//@Id
	private Integer id;
	private Integer pedVendaId;
	private Integer produtoId;
	private Integer prodCompostoId;
	private String produtoCodInterno;
	private String produtoDesc;
	private String unidadeDesc;
	private Double quantidade;
	private Double preco;
	private Double precoProm;
	private Double qtdPromoMin;
	private Double qtdPromoMax;
	private Double precoTabela;
	private Integer qtdDecimal;
    private Double qtdVendaAtac;
    private Double percDesconto;
    private Double valorDesconto;
    private Double pesoBrutoKg;
	private Double pesoLiquidoKg;
	private Integer controlaLote;
	private Double custoGerUltCompraUv;
    
//  @Transient
    private Double subTotal;
    
//  @Transient
    private String quantidadeToString;
    
//  @Transient
    private Boolean inPromocao;
    
//  @Transient
    private Integer volume;
    
//  @Transient
    private List<PedVendaItemProdLoteDTO> lotes;
    
	public PedVendaItemFBDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getProdCompostoId() {
		return prodCompostoId;
	}

	public void setProdCompostoId(Integer prodCompostoId) {
		this.prodCompostoId = prodCompostoId;
	}

	public String getProdutoCodInterno() {
		return produtoCodInterno;
	}

	public void setProdutoCodInterno(String produtoCodInterno) {
		this.produtoCodInterno = produtoCodInterno;
	}

	public String getProdutoDesc() {
		return produtoDesc;
	}

	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}

	public String getUnidadeDesc() {
		return unidadeDesc;
	}

	public void setUnidadeDesc(String unidadeDesc) {
		this.unidadeDesc = unidadeDesc;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Double getPrecoProm() {
		return precoProm;
	}

	public void setPrecoProm(Double precoProm) {
		this.precoProm = precoProm;
	}

	public Double getQtdPromoMin() {
		return qtdPromoMin;
	}

	public void setQtdPromoMin(Double qtdPromoMin) {
		this.qtdPromoMin = qtdPromoMin;
	}

	public Double getQtdPromoMax() {
		return qtdPromoMax;
	}

	public void setQtdPromoMax(Double qtdPromoMax) {
		this.qtdPromoMax = qtdPromoMax;
	}

	public Double getPrecoTabela() {
		return precoTabela;
	}

	public void setPrecoTabela(Double precoTabela) {
		this.precoTabela = precoTabela;
	}

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public Double getQtdVendaAtac() {
		return qtdVendaAtac;
	}

	public void setQtdVendaAtac(Double qtdVendaAtac) {
		this.qtdVendaAtac = qtdVendaAtac;
	}
	
	public Double getPercDesconto() {
		return percDesconto;
	}

	public void setPercDesconto(Double percDesconto) {
		this.percDesconto = percDesconto;
	}
	
	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Double getPesoBrutoKg() {
		return pesoBrutoKg;
	}

	public void setPesoBrutoKg(Double pesoBrutoKg) {
		this.pesoBrutoKg = pesoBrutoKg;
	}

	public Double getPesoLiquidoKg() {
		return pesoLiquidoKg;
	}

	public void setPesoLiquidoKg(Double pesoLiquidoKg) {
		this.pesoLiquidoKg = pesoLiquidoKg;
	}
	
	public Integer getControlaLote() {
		return controlaLote;
	}

	public void setControlaLote(Integer controlaLote) {
		this.controlaLote = controlaLote;
	}
	
	public Double getCustoGerUltCompraUv() {
		return custoGerUltCompraUv;
	}

	public void setCustoGerUltCompraUv(Double custoGerUltCompraUv) {
		this.custoGerUltCompraUv = custoGerUltCompraUv;
	}

	// Transient
	public Double getSubTotal() {
		subTotal = Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (preco * quantidade)));
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	public String getQuantidadeToString() {
		quantidadeToString = Funcoes.formatNumber(quantidade, null, qtdDecimal, qtdDecimal);
		return quantidadeToString;
	}

	public void setQuatidadeToString(String quantidadeToString) {
		this.quantidadeToString = quantidadeToString;
	}
	
	public Integer getVolume() {
		try {
			Double varVolume = Funcoes.arrendondaValor(0, (quantidade / getQtdVendaAtac()));
			volume = varVolume.intValue();
		} catch (Exception e) {
			volume = 0;
			//e.printStackTrace();
		}
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
    
    public Boolean getInPromocao() {
		inPromocao = false;
		
		if(precoProm!=null && !precoProm.equals(getPrecoTabela())) {
			if(qtdPromoMax !=null && qtdPromoMax > 0) {
				if(quantidade >= qtdPromoMin && quantidade <= qtdPromoMax) {
					inPromocao = true;
				}
			}else {
				inPromocao = true;
			}
		}
		return inPromocao;
	}

	public void setInPromocao(Boolean inPromocao) {
		this.inPromocao = inPromocao;
	}
	
	public List<PedVendaItemProdLoteDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<PedVendaItemProdLoteDTO> lotes) {
		this.lotes = lotes;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof PedVendaItemFBDTO) ) return false;
        final PedVendaItemFBDTO o = (PedVendaItemFBDTO) other;
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
