package br.com.webapp.model.fb.prodcomposto;

import java.io.Serializable;

public class ProdCompostoItemFB implements Serializable {

	private static final long serialVersionUID = -1950055298721310634L;
	
	private Integer prodCompostoId;
	private Integer produtoId;
	private Double quantidade;
	
	public ProdCompostoItemFB(){}

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

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
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
		ProdCompostoItemFB other = (ProdCompostoItemFB) obj;
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
