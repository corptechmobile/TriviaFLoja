package br.com.webapp.model.fb.telefonetipo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class TelefoneTipoFB implements Serializable{

	private static final long serialVersionUID = -5974483296811225777L;
	
//	@Id
	private Integer id;
	private String descricao;
	
	public TelefoneTipoFB() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof TelefoneTipoFB) ) return false;
        final TelefoneTipoFB o = (TelefoneTipoFB) other;
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
		return this.getDescricao();
	}
	
}
