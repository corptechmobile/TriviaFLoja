package br.com.webapp.model.fb.prodcomposto;

import java.io.Serializable;

import br.com.webapp.web.util.Funcoes;

public class ProdCompostoItemFBDTO implements Serializable {
	
	private static final long serialVersionUID = 6213032768474391998L;
	
	private Integer prodCompostoId;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private String produtoDescUnid;
	private Double quantidade;
	private Integer qtdDecimal;

	// @Transient
	private String qtdToString;
	
	public ProdCompostoItemFBDTO(){}

	public Integer getProdCompostoId() {
		return prodCompostoId;
	}

	public void setProdCompostoId(Integer prodCompostoId) {
		this.prodCompostoId = prodCompostoId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getProdutoCod() {
		return produtoCod;
	}

	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}

	public String getProdutoDesc() {
		return produtoDesc;
	}

	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}
	
	public String getProdutoDescUnid() {
		return produtoDescUnid;
	}

	public void setProdutoDescUnid(String produtoDescUnid) {
		this.produtoDescUnid = produtoDescUnid;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}
	
	// Transient
	public String getQtdToString() {
		qtdToString = Funcoes.formatNumber(quantidade, null, qtdDecimal, qtdDecimal);
		return qtdToString;
	}

	public void setQtdToString(String qtdToString) {
		this.qtdToString = qtdToString;
	}
	
	// funcao
	public String subTotalToString(Double qtd) {
		return Funcoes.formatNumber((quantidade * qtd), null, qtdDecimal, qtdDecimal) + " " + produtoDescUnid;
	} 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prodCompostoId == null) ? 0 : prodCompostoId.hashCode());
		result = prime * result + ((produtoId == null) ? 0 : produtoId.hashCode());
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
		ProdCompostoItemFBDTO other = (ProdCompostoItemFBDTO) obj;
		if (prodCompostoId == null) {
			if (other.prodCompostoId != null)
				return false;
		} else if (!prodCompostoId.equals(other.prodCompostoId))
			return false;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

}
