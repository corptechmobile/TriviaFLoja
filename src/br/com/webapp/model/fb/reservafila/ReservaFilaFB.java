package br.com.webapp.model.fb.reservafila;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReservaFilaFB implements Serializable {
	
	private static final long serialVersionUID = 1472448195048294173L;

	public static final Integer RETESTDISP_ATIVO = 1;
	public static final Integer RETESTDISP_INATIVO = 0;
	
	@Id
	private Integer pedVendaId;
	
	@Id
	private Integer pedVendaItemId;
	
	@Id
	private Integer produtoId;
	
	private Integer empresaId;
	private Double quantidade;
	private Integer retEstqDisp;
	
	public ReservaFilaFB() {}

	public Integer getPedVendaId() {
		return pedVendaId;
	}

	public void setPedVendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
	}

	public Integer getPedVendaItemId() {
		return pedVendaItemId;
	}

	public void setPedVendaItemId(Integer pedVendaItemId) {
		this.pedVendaItemId = pedVendaItemId;
	}
	
	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getRetEstqDisp() {
		return retEstqDisp;
	}

	public void setRetEstqDisp(Integer retEstqDisp) {
		this.retEstqDisp = retEstqDisp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresaId == null) ? 0 : empresaId.hashCode());
		result = prime * result + ((pedVendaId == null) ? 0 : pedVendaId.hashCode());
		result = prime * result + ((pedVendaItemId == null) ? 0 : pedVendaItemId.hashCode());
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
		ReservaFilaFB other = (ReservaFilaFB) obj;
		if (empresaId == null) {
			if (other.empresaId != null)
				return false;
		} else if (!empresaId.equals(other.empresaId))
			return false;
		if (pedVendaId == null) {
			if (other.pedVendaId != null)
				return false;
		} else if (!pedVendaId.equals(other.pedVendaId))
			return false;
		if (pedVendaItemId == null) {
			if (other.pedVendaItemId != null)
				return false;
		} else if (!pedVendaItemId.equals(other.pedVendaItemId))
			return false;
		return true;
	}

}
