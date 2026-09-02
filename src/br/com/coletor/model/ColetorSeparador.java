package br.com.coletor.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorSeparador implements Serializable {
	
	private static final long serialVersionUID = 4478289194605122272L;

	@Id
	private Integer id;
	
	private String nome;
	
	private Integer ativo;
	
	public ColetorSeparador(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorSeparador other = (ColetorSeparador) obj;
		return Objects.equals(id, other.id);
	}

}