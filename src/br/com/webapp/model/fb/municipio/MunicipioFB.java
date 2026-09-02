package br.com.webapp.model.fb.municipio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "municipio")
public class MunicipioFB implements Serializable{

	private static final long serialVersionUID = -5584633074979762815L;

//	@Id
	private Integer id;
	private String estadoId;
	private String codMunicipioIbge;
	private String nome;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(String estado) {
		this.estadoId = estado;
	}
	public String getCodMunicipioIbge() {
		return codMunicipioIbge;
	}
	public void setCodMunicipioIbge(String codMunicipioIbge) {
		this.codMunicipioIbge = codMunicipioIbge;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof MunicipioFB) ) return false;
        final MunicipioFB o = (MunicipioFB) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId();
        return result;
    }
	
	@Override
	public String toString() {
		return this.getNome();
	}
	
}
