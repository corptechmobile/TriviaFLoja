package br.com.webapp.model.fb.tabpreco;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class TabPrecoFB implements Serializable{

	private static final long serialVersionUID = -1665720726509230266L;
	
//	@Id
	private String id;
	
//	@Column
	private String descricao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
        if ( !(other instanceof TabPrecoFB) ) return false;
        final TabPrecoFB o = (TabPrecoFB) other;
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
		return this.getDescricao();
	}
}
