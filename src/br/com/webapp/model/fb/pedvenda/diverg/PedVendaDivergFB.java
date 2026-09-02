package br.com.webapp.model.fb.pedvenda.diverg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PedVendaDivergFB implements Serializable{

	private static final long serialVersionUID = 6975265055863338997L;
	
	public static final int DIVERGENCIA_POR_DESCONTO = 1;
	public static final int DIVERGENCIA_POR_LOTES_DIFERENTES = 2;
	public static final int DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP = 3;
	
	public static final int SITUACAO_EM_ABERTO = 0;
	public static final int SITUACAO_LIBERADO = 1;
	public static final int SITUACAO_NAO_LIBERADO = 2;

	public static final int NAO_VALIDAR = 0;
	public static final int VALIDAR = 1;


	@Id
	@Column
	private Integer id;
	
	//FKs
	@Column
	private Integer pedVendaId;
	
	@Column
	private Integer pedVendaItemId;
	
	@Column
	private Integer usuarioId;
	
	//Attributes
	@Column
	private Integer condPagtoId;
	
	@Column(columnDefinition = "Decimal(18,3)")
	private Double desconto;
	
	@Column(columnDefinition = "integer default '0'")
	private Integer tipo;
	
	@Column(columnDefinition = "integer default '0'")
	private Integer situacao;
	
	@Column(columnDefinition = "boolean default true")
	private Integer validar;
	
	@Column(length = 50)
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtInteracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, insertable=true, updatable=false)
	private Date dt_create;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date dt_update;

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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Integer getCondPagtoId() {
		return condPagtoId;
	}

	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
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
		PedVendaDivergFB other = (PedVendaDivergFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedVendaDivergFB [id=" + id + ", pedVendaId=" + pedVendaId + ", pedVendaItemId=" + pedVendaItemId
				+ ", usuarioId=" + usuarioId + ", desconto=" + desconto + ", tipo=" + tipo
				+ ", situacao=" + situacao + ", validar=" + validar + ", observacao=" + observacao + ", dtInteracao="
				+ dtInteracao + ", dt_create=" + dt_create + ", dt_update=" + dt_update + "]";
	}
	
}
