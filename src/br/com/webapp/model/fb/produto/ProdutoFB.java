package br.com.webapp.model.fb.produto;

import java.io.Serializable;
import java.util.List;

import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBDTO;
import br.com.webapp.web.util.Funcoes;

//@Entity
public class ProdutoFB implements Serializable {
	
	private static final long serialVersionUID = 1204474708490551594L;
	
	public static final int PRODUTO_NAO_CONTROLA_LOTE = 0;
	public static final int PRODUTO_CONTROLA_LOTE = 1;
	public static final int PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE = 0;
	public static final int PRODUTO_PERMITE_VENDA_SEM_ESTOQUE = 1;
	
	
	//@Id
	private Integer id;
	private String codInterno;
	private String descricao;
	private String produtoCodBarras;
	private String produtoLinhaDesc;
	private String produtoLinhaCodEdt;
	private String unidadeDesc;
	private Integer unidadeId;
	private Integer produtoGrupoId;
	private Integer ativo;
	private Double qtdDisponivel;
	private Double qtdVendaAtac;
	private Double qtdPromoMin;
	private Double qtdPromoMax;
	private Double pesoBrutoKg;
	private Double pesoLiquidoKg;
	private Integer qtdDecimal;
	private Double preco;
	private Double precoPromo;
	private Double aliquota;
	private String tributoIcmsId;
	private Double mvaInterna;
	private Integer controlaLote;
	private Integer obrigaDescLote;
    private Integer obrigaVencLote;
	private Integer permiteVendaSemEstoque;
	private Integer shelfLife;
	
	private Integer ncmId;
	private String ncmCodigo;
	private Integer ncmOpFisc;
	
	private Double aliqICMS;
	private Double aliqPIS;
	private Double aliqCOFINS;
	
	private Double custoMedioOnline;
	
	
//	@Transient
	private boolean disponivel;
	
//	@Transient
	private List<ProdutoEstoqueFB> estoques;
	
//	@Transient
	private String qtdDisponivelToString;
	
//	@Transient
	private String qtdVendaAtacToString;
	
//	@Transient
	private Boolean inPedVenda;
	
//	@Transient
	private Boolean inPromocao;
	
//	@Transient
	private List<ProdCompostoItemFBDTO> composicoes;
	
	public ProdutoFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getProdutoCodBarras() {
		return produtoCodBarras;
	}

	public void setProdutoCodBarras(String produtoCodBarras) {
		this.produtoCodBarras = produtoCodBarras;
	}

	public String getProdutoLinhaDesc() {
		return produtoLinhaDesc;
	}

	public void setProdutoLinhaDesc(String produtoLinhaDesc) {
		this.produtoLinhaDesc = produtoLinhaDesc;
	}

	public String getProdutoLinhaCodEdt() {
		return produtoLinhaCodEdt;
	}

	public void setProdutoLinhaCodEdt(String produtoLinhaCodEdt) {
		this.produtoLinhaCodEdt = produtoLinhaCodEdt;
	}

	public String getUnidadeDesc() {
		return unidadeDesc;
	}

	public void setUnidadeDesc(String unidadeDesc) {
		this.unidadeDesc = unidadeDesc;
	}

	public Integer getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}

	public Integer getProdutoGrupoId() {
		return produtoGrupoId;
	}

	public void setProdutoGrupoId(Integer produtoGrupoId) {
		this.produtoGrupoId = produtoGrupoId;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Double getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(Double qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getPrecoPromo() {
		return precoPromo;
	}

	public void setPrecoPromo(Double precoPromo) {
		this.precoPromo = precoPromo;
	}

	public Double getAliquota() {
		return aliquota;
	}

	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}

	public String getTributoIcmsId() {
		return tributoIcmsId;
	}

	public void setTributoIcmsId(String tributoIcmsId) {
		this.tributoIcmsId = tributoIcmsId;
	}

	public Double getMvaInterna() {
		return mvaInterna;
	}

	public void setMvaInterna(Double mvaInterna) {
		this.mvaInterna = mvaInterna;
	}
	
	public Double getQtdVendaAtac() {
		if(qtdVendaAtac==null || qtdVendaAtac==0.0) {
			qtdVendaAtac = 1.0;
		}
		return qtdVendaAtac;
	}

	public void setQtdVendaAtac(Double qtdVendaAtac) {
		this.qtdVendaAtac = qtdVendaAtac;
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

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}
	
	public Integer getControlaLote() {
		return controlaLote;
	}

	public void setControlaLote(Integer controlaLote) {
		this.controlaLote = controlaLote;
	}
	
	public Integer getObrigaDescLote() {
		return obrigaDescLote;
	}

	public void setObrigaDescLote(Integer obrigaDescLote) {
		this.obrigaDescLote = obrigaDescLote;
	}

	public Integer getObrigaVencLote() {
		return obrigaVencLote;
	}

	public void setObrigaVencLote(Integer obrigaVencLote) {
		this.obrigaVencLote = obrigaVencLote;
	}

	public Integer getPermiteVendaSemEstoque() {
		return permiteVendaSemEstoque;
	}

	public void setPermiteVendaSemEstoque(Integer permiteVendaSemEstoque) {
		this.permiteVendaSemEstoque = permiteVendaSemEstoque;
	}
	
	public Integer getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}

	public Integer getNcmId() {
		return ncmId;
	}

	public void setNcmId(Integer ncmId) {
		this.ncmId = ncmId;
	}

	public String getNcmCodigo() {
		return ncmCodigo;
	}

	public void setNcmCodigo(String ncmCodigo) {
		this.ncmCodigo = ncmCodigo;
	}

	// Transients
	public boolean isDisponivel() {
		disponivel = false;
		if(this.getQtdDisponivel()>0) {
			disponivel = true;
		}
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	public List<ProdutoEstoqueFB> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<ProdutoEstoqueFB> estoques) {
		this.estoques = estoques;
	}
	
	public String getQtdDisponivelToString() {
		qtdDisponivelToString = Funcoes.formatNumber(qtdDisponivel, null, qtdDecimal, qtdDecimal);
		return qtdDisponivelToString;
	}

	public void setQtdDisponivelToString(String qtdDisponivelToString) {
		this.qtdDisponivelToString = qtdDisponivelToString;
	}
	
	public String getQtdVendaAtacToString() {
		qtdVendaAtacToString = Funcoes.formatNumber(getQtdVendaAtac(), null, qtdDecimal, qtdDecimal);
		return qtdVendaAtacToString;
	}

	public void setQtdVendaAtacToString(String qtdVendaAtacToString) {
		this.qtdVendaAtacToString = qtdVendaAtacToString;
	}
	
	public Integer getNcmOpFisc() {
		return ncmOpFisc;
	}

	public void setNcmOpFisc(Integer ncmOpFisc) {
		this.ncmOpFisc = ncmOpFisc;
	}
	
	public Double getAliqICMS() {
		return aliqICMS;
	}

	public void setAliqICMS(Double aliqICMS) {
		this.aliqICMS = aliqICMS;
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

	public Double getCustoMedioOnline() {
		return custoMedioOnline;
	}

	public void setCustoMedioOnline(Double custoMedioOnline) {
		this.custoMedioOnline = custoMedioOnline;
	}
	
	// Transient
	public Boolean getInPedVenda() {
		return inPedVenda;
	}

	public void setInPedVenda(Boolean inPedVenda) {
		this.inPedVenda = inPedVenda;
	}
	
	public Boolean getInPromocao() {
		inPromocao = false;
		if(precoPromo!=null && !precoPromo.equals(getPreco())) {
			inPromocao = true;
		}
		return inPromocao;
	}

	public void setInPromocao(Boolean inPromocao) {
		this.inPromocao = inPromocao;
	}
	
	public List<ProdCompostoItemFBDTO> getComposicoes() {
		return composicoes;
	}

	public void setComposicoes(List<ProdCompostoItemFBDTO> composicoes) {
		this.composicoes = composicoes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoFB other = (ProdutoFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}

}
