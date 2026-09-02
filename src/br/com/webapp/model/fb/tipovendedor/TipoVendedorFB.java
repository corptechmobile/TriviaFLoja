package br.com.webapp.model.fb.tipovendedor;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class TipoVendedorFB implements Serializable {

	private static final long serialVersionUID = -1099850272293500439L;

	//	@Id
	private Integer id;
	private String descricao;
	private boolean integraTriviaMobile;  
	private Double descflex;
	private boolean isDistribuicao; 
	
	public TipoVendedorFB(){}

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

	public boolean isIntegraTriviaMobile() {
		return integraTriviaMobile;
	}

	public void setIntegraTriviaMobile(boolean integraTriviaMobile) {
		this.integraTriviaMobile = integraTriviaMobile;
	}

	public Double getDescflex() {
		return descflex;
	}

	public void setDescflex(Double descflex) {
		this.descflex = descflex;
	}

	public boolean isDistribuicao() {
		return isDistribuicao;
	}

	public void setDistribuicao(boolean isDistribuicao) {
		this.isDistribuicao = isDistribuicao;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof TipoVendedorFB) ) return false;
        final TipoVendedorFB o = (TipoVendedorFB) other;
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
