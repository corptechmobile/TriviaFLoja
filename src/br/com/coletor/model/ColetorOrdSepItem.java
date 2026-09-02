package br.com.coletor.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorOrdSepItem implements Serializable {
	
	private static final long serialVersionUID = -7503772447795097868L;
	
	@Id
	private Integer id;
	private Integer ordemSeparacaoId;
	private Integer produtoId;
	private Double qtd;
	private String unidVendaDesc;
	
	public ColetorOrdSepItem(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdemSeparacaoId() {
		return ordemSeparacaoId;
	}

	public void setOrdemSeparacaoId(Integer ordemSeparacaoId) {
		this.ordemSeparacaoId = ordemSeparacaoId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public String getUnidVendaDesc() {
		return unidVendaDesc;
	}

	public void setUnidVendaDesc(String unidVendaDesc) {
		this.unidVendaDesc = unidVendaDesc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorOrdSepItem other = (ColetorOrdSepItem) obj;
		return Objects.equals(id, other.id);
	}
	
}