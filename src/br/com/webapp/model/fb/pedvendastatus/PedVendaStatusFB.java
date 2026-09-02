package br.com.webapp.model.fb.pedvendastatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PedVendaStatusFB implements Serializable{

	private static final long serialVersionUID = -2394690284426510393L;
	
	@Id
	@Column
	private Integer id;
	
	@Column
	private String descricao;
	
	@Column
	private Boolean carteira;
	
	@Column
	private Boolean efetivado;
	
	@Column
	private Boolean credCliente;
	
	@Column
	private Boolean triviaMobile;
	
	@Column
	private Boolean atualizaMoeda;
	
	public PedVendaStatusFB() {}

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

	public Boolean getCarteira() {
		return carteira;
	}

	public void setCarteira(Boolean carteira) {
		this.carteira = carteira;
	}

	public Boolean getEfetivado() {
		return efetivado;
	}

	public void setEfetivado(Boolean efetivado) {
		this.efetivado = efetivado;
	}

	public Boolean getCredCliente() {
		return credCliente;
	}

	public void setCredCliente(Boolean credCliente) {
		this.credCliente = credCliente;
	}

	public Boolean getTriviaMobile() {
		return triviaMobile;
	}

	public void setTriviaMobile(Boolean triviaMobile) {
		this.triviaMobile = triviaMobile;
	}

	public Boolean getAtualizaMoeda() {
		return atualizaMoeda;
	}

	public void setAtualizaMoeda(Boolean atualizaMoeda) {
		this.atualizaMoeda = atualizaMoeda;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof PedVendaStatusFB) ) return false;
        final PedVendaStatusFB o = (PedVendaStatusFB) other;
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
