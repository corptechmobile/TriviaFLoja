package br.com.webapp.model.fb.planilhacegafirebird;

import java.io.Serializable;

public class PlanilhaCegaItemFirebird implements Serializable {

	private static final long serialVersionUID = 1333325117155452055L;
	
	public static final Integer ID_LOCALIDADE = 461;
	public static final Integer ITEMSEMREFERENCIA = 0;
	public static final Integer ITEMGERACAO = 0;
	public static final Integer RESTRICAO = 0;
	public static final Integer ESTOQUEATUALIZADO = 0;
	
	private Integer id;
	private Integer planilhaCega;
	private Integer produto;
	private Integer localidade; 
	private Double qtdRecebida;
	private Double qtdAvaria;
	private Double qtdDevolvida;
	private String vencimentoLote;
	private String codLote;
	private String observacao;
	private Integer itemSemReferencia;
	private Integer itemGeracao;
	private Integer restricao;
	private Integer estoqueAtualizado;
	private Integer idUnidadeCpr;
	
	
	public PlanilhaCegaItemFirebird(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanilhaCega() {
		return planilhaCega;
	}

	public void setPlanilhaCega(Integer planilhaCega) {
		this.planilhaCega = planilhaCega;
	}

	public Integer getProduto() {
		return produto;
	}

	public void setProduto(Integer produto) {
		this.produto = produto;
	}

	public Integer getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Integer localidade) {
		this.localidade = localidade;
	}

	public Double getQtdRecebida() {
		return qtdRecebida;
	}

	public void setQtdRecebida(Double qtdRecebida) {
		this.qtdRecebida = qtdRecebida;
	}

	public Double getQtdAvaria() {
		return qtdAvaria;
	}

	public void setQtdAvaria(Double qtdAvaria) {
		this.qtdAvaria = qtdAvaria;
	}

	public Double getQtdDevolvida() {
		return qtdDevolvida;
	}

	public void setQtdDevolvida(Double qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
	}

	public String getVencimentoLote() {
		return vencimentoLote;
	}

	public void setVencimentoLote(String vencimentoLote) {
		this.vencimentoLote = vencimentoLote;
	}

	public String getCodLote() {
		return codLote;
	}

	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getItemSemReferencia() {
		return itemSemReferencia;
	}

	public void setItemSemReferencia(Integer itemSemReferencia) {
		this.itemSemReferencia = itemSemReferencia;
	}

	public Integer getItemGeracao() {
		return itemGeracao;
	}

	public void setItemGeracao(Integer itemGeracao) {
		this.itemGeracao = itemGeracao;
	}

	public Integer getRestricao() {
		return restricao;
	}

	public void setRestricao(Integer restricao) {
		this.restricao = restricao;
	}

	public Integer getEstoqueAtualizado() {
		return estoqueAtualizado;
	}

	public void setEstoqueAtualizado(Integer estoqueAtualizado) {
		this.estoqueAtualizado = estoqueAtualizado;
	}

	public Integer getIdUnidadeCpr() {
		return idUnidadeCpr;
	}

	public void setIdUnidadeCpr(Integer idUnidadeCpr) {
		this.idUnidadeCpr = idUnidadeCpr;
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
		PlanilhaCegaItemFirebird other = (PlanilhaCegaItemFirebird) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}