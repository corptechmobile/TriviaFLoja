package br.com.webapp.model.fb.reserva;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReservaFB implements Serializable{

	private static final long serialVersionUID = -5265751844986613345L;
	
	public static final String TIPOID = "F";
	public static final Integer ORDEMRETIRADA = 1;
	
	//PK
	@Id
	@Column
	private Integer id;
	
	//FKs
	@Column
	private Integer pedVendaId;
	
	@Column
	private Integer pedVendaItemId;
	
	@Column
	private Integer produtoId;
	
	@Column
	private Integer localidadeId;
	
	@Column(length = 1)
	private String tipoId;
	
	//Attributes
	@Column(columnDefinition = "Decimal(18,3) default '0.000'")
	private Double quantidade;
	
	@Column
	private Integer ordemRetirada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getLocalidadeId() {
		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId) {
		this.localidadeId = localidadeId;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getOrdemRetirada() {
		return ordemRetirada;
	}

	public void setOrdemRetirada(Integer ordemRetirada) {
		this.ordemRetirada = ordemRetirada;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localidadeId == null) ? 0 : localidadeId.hashCode());
		result = prime * result + ((pedVendaId == null) ? 0 : pedVendaId.hashCode());
		result = prime * result + ((pedVendaItemId == null) ? 0 : pedVendaItemId.hashCode());
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
		ReservaFB other = (ReservaFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (localidadeId == null) {
			if (other.localidadeId != null)
				return false;
		} else if (!localidadeId.equals(other.localidadeId))
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
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ""+this.getId();
	}
	
}
