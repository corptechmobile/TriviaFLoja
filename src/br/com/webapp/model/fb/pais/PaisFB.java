package br.com.webapp.model.fb.pais;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class PaisFB implements Serializable{
	
	private static final long serialVersionUID = -6126071843349012866L;

//	@Id
	private String id;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaisFB other = (PaisFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
