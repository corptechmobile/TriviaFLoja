package br.com.webapp.model.fb.coletorpc.contagem;

import java.util.Date;

import javax.persistence.Transient;

public class ColetorPCFBContagemAgrupadaDTO {

	private Integer produtoId;
	private String codigolote;
	private Double fatorConv;
	private Double qtdUnidadeCompra;
	private Double qtdUnidadeVenda;
	private Double qtdRecebida;
	private Double qtdAvaria;
	private Date dtVencLot;
	private Double vlrItem;
	
	@Transient
	private Double qtdRecebidaConv;
	@Transient
	private Double qtdAvariaConv;
	@Transient
	private Double qtdDevolvidaConv;
	
	public ColetorPCFBContagemAgrupadaDTO() {}
	
	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getCodigolote() {
		return codigolote;
	}

	public void setCodigolote(String codigolote) {
		this.codigolote = codigolote;
	}
	
	public Double getFatorConv() {
		return fatorConv;
	}

	public void setFatorConv(Double fatorConv) {
		this.fatorConv = fatorConv;
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

	public Date getDtVencLot() {
		return dtVencLot;
	}

	public void setDtVencLot(Date dtVencLot) {
		this.dtVencLot = dtVencLot;
	}
	
	public Double getQtdRecebidaConv() {
		qtdRecebidaConv = 0d;
		if(fatorConv!=null && qtdRecebida!=null){
			qtdRecebidaConv = qtdRecebida > 0 ? (qtdRecebida/fatorConv) : 0d;
		}
		return qtdRecebidaConv;
	}

	public void setQtdRecebidaConv(Double qtdRecebidaConv) {
		this.qtdRecebidaConv = qtdRecebidaConv;
	}

	public Double getQtdAvariaConv() {
		qtdAvariaConv = 0d;
		if(fatorConv!=null && qtdAvaria!=null){
			qtdAvariaConv = qtdAvaria > 0 ? (qtdAvaria/fatorConv) : 0d;
		}
		return qtdAvariaConv;
	}

	public void setQtdAvariaConv(Double qtdAvariaConv) {
		this.qtdAvariaConv = qtdAvariaConv;
	}
	
	public Double getQtdDevolvidaConv() {
		qtdDevolvidaConv = (qtdUnidadeVenda - qtdRecebidaConv);
		return qtdDevolvidaConv;
	}

	public void setQtdDevolvidaConv(Double qtdDevolvidaVendaConv) {
		this.qtdDevolvidaConv = qtdDevolvidaVendaConv;
	}
	
	public Double getVlrItem() {
		return vlrItem;
	}

	public void setVlrItem(Double vlrItem) {
		this.vlrItem = vlrItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((produtoId == null) ? 0 : produtoId.hashCode());
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
		ColetorPCFBContagemAgrupadaDTO other = (ColetorPCFBContagemAgrupadaDTO) obj;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

}
