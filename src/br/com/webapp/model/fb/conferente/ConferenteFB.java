package br.com.webapp.model.fb.conferente;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.webapp.model.fb.vendedor.VendedorFB;

//@Entity
public class ConferenteFB implements Serializable {

	private static final long serialVersionUID = 3910903594015272857L;
	
//	@Id
	private Integer id;
	private String cnpjCpf;
	private String razaoSocial;
	private String nomeFantasia;
	
	public ConferenteFB(){}

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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof ConferenteFB) ) return false;
        final ConferenteFB o = (ConferenteFB) other;
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
		return this.getNomeFantasia();
	}

}
