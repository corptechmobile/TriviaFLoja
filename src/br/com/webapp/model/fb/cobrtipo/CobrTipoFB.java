package br.com.webapp.model.fb.cobrtipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


//@Entity
public class CobrTipoFB implements Serializable{
	
	private static final long serialVersionUID = -2433337184458388050L;

//	@Id
	private Integer id;
	
//	@Column
	private String descricao;

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
        if ( !(other instanceof CobrTipoFB) ) return false;
        final CobrTipoFB o = (CobrTipoFB) other;
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
