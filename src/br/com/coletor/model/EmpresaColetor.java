package br.com.coletor.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpresaColetor implements Serializable {
	
	private static final long serialVersionUID = -7987077030117294532L;
	@Id
	private Integer id;
	private String cnpjCpf;
	private String nomeFantasia;
	private boolean confCegaSaida;
	
	public EmpresaColetor(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public boolean isConfCegaSaida() {
		return confCegaSaida;
	}

	public void setConfCegaSaida(boolean confCegaSaida) {
		this.confCegaSaida = confCegaSaida;
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
		EmpresaColetor other = (EmpresaColetor) obj;
		return Objects.equals(id, other.id);
	}

}