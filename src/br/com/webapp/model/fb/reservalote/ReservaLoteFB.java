package br.com.webapp.model.fb.reservalote;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReservaLoteFB implements Serializable{

	private static final long serialVersionUID = -8645995895022944763L;

	public static final Integer ORDEMCARREGITEMID = null;
	public static final Integer ORDEMPRODREQUISICAOITEMID = null;
	
	@Id
	@Column
	private Integer id;
	
	//FKs
	
	@Column
	private Integer produtoLoteId;
	
	@Column
	private Integer localidadeId;
	
	@Column
	private Integer ordemProdRequisicaoItemId;
	
	@Column
	private Integer ordemCarregItemId;
	
	@Column
	private Integer reservaId;
	
	//Attributes
	
	@Column
	private Double quantidade;
	
	//Getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProdutoLoteId() {
		return produtoLoteId;
	}

	public void setProdutoLoteId(Integer produtoLoteId) {
		this.produtoLoteId = produtoLoteId;
	}

	public Integer getLocalidadeId() {
		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId) {
		this.localidadeId = localidadeId;
	}

	public Integer getOrdemProdRequisicaoItemId() {
		return ordemProdRequisicaoItemId;
	}

	public void setOrdemProdRequisicaoItemId(Integer ordemProdRequisicaoItemId) {
		this.ordemProdRequisicaoItemId = ordemProdRequisicaoItemId;
	}

	public Integer getOrdemCarregItemId() {
		return ordemCarregItemId;
	}

	public void setOrdemCarregItemId(Integer ordemCarregItemId) {
		this.ordemCarregItemId = ordemCarregItemId;
	}

	public Integer getReservaId() {
		return reservaId;
	}

	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
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
		ReservaLoteFB other = (ReservaLoteFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
