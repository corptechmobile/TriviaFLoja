package br.com.webapp.model.fb.produtocb;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable 
public class ProdutoCBFBId implements Serializable {
	
	private static final long serialVersionUID = -6686186747703776227L;

	@Column(name = "id_produto")
	private Integer produtoId;
	
	@Column(name = "codigobarras")
	private String codigoBarras;
	
	public ProdutoCBFBId() {}

	public ProdutoCBFBId(Integer produtoId, String codigoBarras) {
		super();
		this.produtoId = produtoId;
		this.codigoBarras = codigoBarras;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoBarras, produtoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoCBFBId other = (ProdutoCBFBId) obj;
		return Objects.equals(codigoBarras, other.codigoBarras) && Objects.equals(produtoId, other.produtoId);
	}
	
}
