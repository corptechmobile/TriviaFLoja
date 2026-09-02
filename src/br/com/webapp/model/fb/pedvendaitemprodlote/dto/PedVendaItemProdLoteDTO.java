package br.com.webapp.model.fb.pedvendaitemprodlote.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.webapp.web.util.Funcoes;

public class PedVendaItemProdLoteDTO implements Serializable {
	
	private static final long serialVersionUID = -404031953666328630L;
	
	private Integer produtoId;
	private String codlote;
	private Date dtVencimento;
	private Double qtd;
	private Integer qtdDecimal; 
	private String qtdToString;
	
	public PedVendaItemProdLoteDTO() {}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getCodlote() {
		return codlote;
	}

	public void setCodlote(String codlote) {
		this.codlote = codlote;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public String getQtdToString() {
		qtdToString = Funcoes.formatNumber(qtd, null, qtdDecimal, qtdDecimal);
		return qtdToString;
	}

	public void setQtdToString(String qtdToString) {
		this.qtdToString = qtdToString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codlote == null) ? 0 : codlote.hashCode());
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
		PedVendaItemProdLoteDTO other = (PedVendaItemProdLoteDTO) obj;
		if (codlote == null) {
			if (other.codlote != null)
				return false;
		} else if (!codlote.equals(other.codlote))
			return false;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}
	
}
