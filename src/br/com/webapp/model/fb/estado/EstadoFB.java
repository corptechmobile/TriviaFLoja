package br.com.webapp.model.fb.estado;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class EstadoFB implements Serializable{

	private static final long serialVersionUID = 140989188747209992L;
	
//	@Id
	private String id;
	private String nome;
	private String codEstadoIbge;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodEstadoIbge() {
		return codEstadoIbge;
	}
	public void setCodEstadoIbge(String codEstadoIbge) {
		this.codEstadoIbge = codEstadoIbge;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof EstadoFB) ) return false;
        final EstadoFB o = (EstadoFB) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId().hashCode();
        return result;
    }
	
	@Override
	public String toString() {
		return this.getId();
	}

}
