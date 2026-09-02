package br.com.webapp.model.fb.gestaovenda;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class GestaoVendaFB implements Serializable{

	private static final long serialVersionUID = 5874282861957808463L;
	
//	@Id
	private Integer id;
	
//	@Column
	private Integer gestaoRefId;
	
//	@Column
	private Integer usuarioId;
	
//	@Column
	private String nome;
	
//	@Column
	private Integer ordem;
	
//	@Column 
	private Double alcada;
	
//	@Column
	private Integer numDias;
	
//	@Column
	private Integer vendasEmEstq;
	
//	@Column
	private String codEdt;
	
//	@Column
	private Integer verProdNaoDispVenda;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGestaoRefId() {
		return gestaoRefId;
	}

	public void setGestaoRefId(Integer gestaoRefId) {
		this.gestaoRefId = gestaoRefId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Double getAlcada() {
		return alcada;
	}

	public void setAlcada(Double alcada) {
		this.alcada = alcada;
	}

	public Integer getNumDias() {
		return numDias;
	}

	public void setNumDias(Integer numDias) {
		this.numDias = numDias;
	}

	public Integer getVendasEmEstq() {
		return vendasEmEstq;
	}

	public void setVendasEmEstq(Integer vendasEmEstq) {
		this.vendasEmEstq = vendasEmEstq;
	}

	public String getCodEdt() {
		return codEdt;
	}

	public void setCodEdt(String codEdt) {
		this.codEdt = codEdt;
	}

	public Integer getVerProdNaoDispVenda() {
		return verProdNaoDispVenda;
	}

	public void setVerProdNaoDispVenda(Integer verProdNaoDispVenda) {
		this.verProdNaoDispVenda = verProdNaoDispVenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		GestaoVendaFB other = (GestaoVendaFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
