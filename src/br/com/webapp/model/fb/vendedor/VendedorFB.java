package br.com.webapp.model.fb.vendedor;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class VendedorFB implements Serializable {

	private static final long serialVersionUID = -4700478287446059670L;
	
//	@Id
	private Integer id;
	private Integer gestaoVendaId;
	private String gestaoVendaCodEdt;
	private Double alcada;
	private String cnpjCpf;
	private String razaoSocial;
	private String nomeFantasia;
	private String tipoVendedor;
	
	public VendedorFB(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGestaoVendaId() {
		return gestaoVendaId;
	}

	public void setGestaoVendaId(Integer gestaoVendaId) {
		this.gestaoVendaId = gestaoVendaId;
	}

	public String getGestaoVendaCodEdt() {
		return gestaoVendaCodEdt;
	}

	public void setGestaoVendaCodEdt(String gestaoVendaCodEdt) {
		this.gestaoVendaCodEdt = gestaoVendaCodEdt;
	}
	
	public Double getAlcada() {
		return alcada;
	}

	public void setAlcada(Double alcada) {
		this.alcada = alcada;
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

	public String getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(String tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof VendedorFB) ) return false;
        final VendedorFB o = (VendedorFB) other;
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
