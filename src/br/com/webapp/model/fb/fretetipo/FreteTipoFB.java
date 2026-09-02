package br.com.webapp.model.fb.fretetipo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;

//@Entity
public class FreteTipoFB implements Serializable{
	
	private static final long serialVersionUID = 4665827932097225608L;
	
	public static final int FRETE_CIF = 0;
	public static final int FRETE_FOB = 1;
	
	
//	@Id
	private Integer id;
	private String descricao;
	private Integer movFiscTipoId;
	
	public FreteTipoFB() {}

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
	
	public Integer getMovFiscTipoId() {
		return movFiscTipoId;
	}

	public void setMovFiscTipoId(Integer movFiscTipoId) {
		this.movFiscTipoId = movFiscTipoId;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof FreteTipoFB) ) return false;
        final FreteTipoFB o = (FreteTipoFB) other;
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
