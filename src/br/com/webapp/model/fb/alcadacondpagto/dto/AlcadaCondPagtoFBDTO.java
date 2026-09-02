package br.com.webapp.model.fb.alcadacondpagto.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class AlcadaCondPagtoFBDTO implements Serializable{

	private static final long serialVersionUID = -6625530898339293712L;
	
//	@Id
	private Integer gestaoVendaId;
	
//	@Column
	private String gestaoVendaDesc;
	
//	@Id
	private Integer condPagtoId;
	
//	@Column
	private String condPagtoDesc;
	
//	@Column
	private Double alcada;

	public Integer getGestaoVendaId() {
		return gestaoVendaId;
	}

	public void setGestaoVendaId(Integer gestaoVendaId) {
		this.gestaoVendaId = gestaoVendaId;
	}

	public String getGestaoVendaDesc() {
		return gestaoVendaDesc;
	}

	public void setGestaoVendaDesc(String gestaoVendaDesc) {
		this.gestaoVendaDesc = gestaoVendaDesc;
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

	public Double getAlcada() {
		return alcada;
	}

	public void setAlcada(Double alcada) {
		this.alcada = alcada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condPagtoId == null) ? 0 : condPagtoId.hashCode());
		result = prime * result + ((gestaoVendaId == null) ? 0 : gestaoVendaId.hashCode());
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
		AlcadaCondPagtoFBDTO other = (AlcadaCondPagtoFBDTO) obj;
		if (condPagtoId == null) {
			if (other.condPagtoId != null)
				return false;
		} else if (!condPagtoId.equals(other.condPagtoId))
			return false;
		if (gestaoVendaId == null) {
			if (other.gestaoVendaId != null)
				return false;
		} else if (!gestaoVendaId.equals(other.gestaoVendaId))
			return false;
		return true;
	}
}
