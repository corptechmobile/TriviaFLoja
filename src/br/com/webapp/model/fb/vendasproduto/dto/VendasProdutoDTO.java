package br.com.webapp.model.fb.vendasproduto.dto;

import java.io.Serializable;

public class VendasProdutoDTO implements Serializable{

	private static final long serialVersionUID = 3510064345098609915L;
	
	private String descricao;
	private String produtoCod;
	private Integer produtoId;
	private String produto;
	private Double qtdeAuto;
	private String un;
	private Double preco;
	private Double valor;
	private Double vlDevolvido;
	private Double desconto;
	private Double margem;
	private Double markup;	
	
	public VendasProdutoDTO() {}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getProdutoCod() {
		return produtoCod;
	}
	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public Double getQtdeAuto() {
		return qtdeAuto;
	}
	public void setQtdeAuto(Double qtdeAuto) {
		this.qtdeAuto = qtdeAuto;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getVlDevolvido() {
		return vlDevolvido;
	}
	public void setVlDevolvido(Double vlDevolvido) {
		this.vlDevolvido = vlDevolvido;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getMargem() {
		return margem;
	}

	public void setMargem(Double margem) {
		this.margem = margem;
	}

	public Double getMarkup() {
		return markup;
	}

	public void setMarkup(Double markup) {
		this.markup = markup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		VendasProdutoDTO other = (VendasProdutoDTO) obj;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

}
