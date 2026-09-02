package br.com.webapp.model.fb.nfcompra;

import java.io.Serializable;

public class NFCompraItemFB implements Serializable {

	private static final long serialVersionUID = 5311536564307404629L;

	private Integer id;
	private Integer nfCompraId;
	private Integer produtoId;
	private String produtoDesc;
	private String produtoCod;
	private Integer unidadeCompraId;
	private Integer unidadeVendaId;
	private String unidadeDesc;
	private Double qtdUnidadeCompra;
	private Double qtdUnidadeVenda;
	private Double fatorConv; 
	private Integer fatorInv;
	
	
	public NFCompraItemFB() {}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNfCompraId() {
		return nfCompraId;
	}
	public void setNfCompraId(Integer nfCompraId) {
		this.nfCompraId = nfCompraId;
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
	public Integer getUnidadeCompraId() {
		return unidadeCompraId;
	}
	public void setUnidadeCompraId(Integer unidadeCompraId) {
		this.unidadeCompraId = unidadeCompraId;
	}
	public Integer getUnidadeVendaId() {
		return unidadeVendaId;
	}
	public void setUnidadeVendaId(Integer unidadeVendaId) {
		this.unidadeVendaId = unidadeVendaId;
	}
	public Double getQtdUnidadeCompra() {
		return qtdUnidadeCompra;
	}
	public void setQtdUnidadeCompra(Double qtdUnidadeCompra) {
		this.qtdUnidadeCompra = qtdUnidadeCompra;
	}
	public Double getQtdUnidadeVenda() {
		return qtdUnidadeVenda;
	}
	public void setQtdUnidadeVenda(Double qtdUnidadeVenda) {
		this.qtdUnidadeVenda = qtdUnidadeVenda;
	}
	public Double getFatorConv() {
		return fatorConv;
	}
	public void setFatorConv(Double fatorConv) {
		this.fatorConv = fatorConv;
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
	public Integer getFatorInv() {
		return fatorInv;
	}
	public void setFatorInv(Integer fatorInv) {
		this.fatorInv = fatorInv;
	} 
	
	
	
}
