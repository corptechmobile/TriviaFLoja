package br.com.webapp.model.configuracao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="configuracao")
public class Configuracao implements Serializable {
	
	private static final long serialVersionUID = -648350298079768602L;
	
	public static final String PEDIDO_COMPOSTO = "composto";
	public static final String PEDIDO_ENCOMENDA = "encomenda";
	public static final String PEDIDO_PODE_MUDAR_FRETE_TIPO = "ciffob";
	public static final String PEDIDO_PODE_MUDAR_FORMA_PAGTO = "formapagto";
	public static final String PEDIDO_PODE_MUDAR_TIPO_MOV_FISC = "movfisctipo";
	
	@Id
	@Column(length=20)
	private String nome;
	
	@Column(length=100)
	private String descricao;
	
	private boolean ativo;
	
	public Configuracao() {}

	public Configuracao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Configuracao other = (Configuracao) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
