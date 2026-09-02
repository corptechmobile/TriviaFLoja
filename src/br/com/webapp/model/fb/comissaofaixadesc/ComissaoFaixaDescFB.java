package br.com.webapp.model.fb.comissaofaixadesc;

import java.io.Serializable;

//@Entity
public class ComissaoFaixaDescFB implements Serializable{
	private static final long serialVersionUID = 7533853151112875566L;

	//@Id
	private Integer id;
	private Integer produtoLinhaId;
	private String codEdt;
	private String produtoLinhaDesc;
	private Double faixaDesc1;
	private Double faixaDesc2;
	private Double percComissao;
	
	public ComissaoFaixaDescFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProdutoLinhaId() {
		return produtoLinhaId;
	}

	public void setProdutoLinhaId(Integer produtoLinhaId) {
		this.produtoLinhaId = produtoLinhaId;
	}

	public Double getFaixaDesc1() {
		return faixaDesc1;
	}

	public void setFaixaDesc1(Double faixaDesc1) {
		this.faixaDesc1 = faixaDesc1;
	}

	public Double getFaixaDesc2() {
		return faixaDesc2;
	}

	public void setFaixaDesc2(Double faixaDesc2) {
		this.faixaDesc2 = faixaDesc2;
	}

	public String getProdutoLinhaDesc() {
		return produtoLinhaDesc;
	}

	public void setProdutoLinhaDesc(String produtoLinhaDesc) {
		this.produtoLinhaDesc = produtoLinhaDesc;
	}

	public Double getPercComissao() {
		return percComissao;
	}

	public void setPercComissao(Double percComissao) {
		this.percComissao = percComissao;
	}

	public String getCodEdt() {
		return codEdt;
	}

	public void setCodEdt(String codEdt) {
		this.codEdt = codEdt;
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
		ComissaoFaixaDescFB other = (ComissaoFaixaDescFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
