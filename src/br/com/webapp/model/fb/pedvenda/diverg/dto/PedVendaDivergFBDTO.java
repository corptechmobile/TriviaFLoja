package br.com.webapp.model.fb.pedvenda.diverg.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class PedVendaDivergFBDTO implements Serializable {

	private static final long serialVersionUID = 874494089866137139L;
	
	private Integer id;
	private Integer pedVendaId;
	private Integer pedVendaItemId;
	private Integer condPagtoId;
	private String condPagtoDesc;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private Integer usuarioId;
	private String usuarioNome;
	private Double desconto;
	private Integer tipo;
	private Integer situacao;
	private Integer validar;
	private String observacao;
	private Date dtInteracao;
	private Date dt_create;
	private Date dt_update;
	
	public PedVendaDivergFBDTO() {}

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
	
	public Integer getCondPagtoId() {
		return condPagtoId;
	}

	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}
	
	public String getCondPagtoDesc() {
		return condPagtoDesc;
	}

	public void setCondPagtoDesc(String condPagtoDesc) {
		this.condPagtoDesc = condPagtoDesc;
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

	public String getProdutoDesc() {
		return produtoDesc;
	}

	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Integer getValidar() {
		return validar;
	}

	public void setValidar(Integer validar) {
		this.validar = validar;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDtInteracao() {
		return dtInteracao;
	}

	public void setDtInteracao(Date dtInteracao) {
		this.dtInteracao = dtInteracao;
	}

	public Date getDt_create() {
		return dt_create;
	}

	public void setDt_create(Date dt_create) {
		this.dt_create = dt_create;
	}

	public Date getDt_update() {
		return dt_update;
	}

	public void setDt_update(Date dt_update) {
		this.dt_update = dt_update;
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
		PedVendaDivergFBDTO other = (PedVendaDivergFBDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
