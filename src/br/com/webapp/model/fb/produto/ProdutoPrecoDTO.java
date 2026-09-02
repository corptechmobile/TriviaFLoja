package br.com.webapp.model.fb.produto;

import java.io.Serializable;

public class ProdutoPrecoDTO implements Serializable {

	private static final long serialVersionUID = -6389786768326699655L;
	
	private Integer id;
	private String codInterno;
	private String descricao;
	private Double preco;
	private Double precoPromo;
	private Double qtdPromoMin;
	private Double qtdPromoMax;
	
	
	public ProdutoPrecoDTO() {}

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
		ProdutoPrecoDTO other = (ProdutoPrecoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
