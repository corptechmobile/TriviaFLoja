package br.com.webapp.model.fb.empresa;

import java.io.Serializable;

//@Entity
public class EmpresaFB implements Serializable {
	
	private static final long serialVersionUID = 7848950522960104739L;
	
	public final static Integer COMISSAO_LINHAPRODUTO = 1;
	public final static Integer COMISSAO_VENDEDOR_PRODUTO = 0;
	
//	@Id
	private Integer id;
	private String cnpjCpf;
	private String razaoSocial;
	private String nomeFantasia;
	private Integer tipoComissao;
	private Integer nivelLinhaProduto;
	private String idTabPrecoPadraoFDL;
	private Integer idClientePadraoFDL;
	private Integer idVendedorPadraoFDL;
	private Integer permitePromocao;
	
	
	public EmpresaFB() {}

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
	
	public Integer getTipoComissao() {
		return tipoComissao;
	}

	public void setTipoComissao(Integer tipoComissao) {
		this.tipoComissao = tipoComissao;
	}

	public Integer getNivelLinhaProduto() {
		return nivelLinhaProduto;
	}

	public void setNivelLinhaProduto(Integer nivelLinhaProduto) {
		this.nivelLinhaProduto = nivelLinhaProduto;
	}

	public String getIdTabPrecoPadraoFDL() {
		return idTabPrecoPadraoFDL;
	}

	public void setIdTabPrecoPadraoFDL(String idTabPrecoPadraoFDL) {
		this.idTabPrecoPadraoFDL = idTabPrecoPadraoFDL;
	}

	public Integer getIdClientePadraoFDL() {
		return idClientePadraoFDL;
	}

	public void setIdClientePadraoFDL(Integer idClientePadraoFDL) {
		this.idClientePadraoFDL = idClientePadraoFDL;
	}

	public Integer getIdVendedorPadraoFDL() {
		return idVendedorPadraoFDL;
	}

	public void setIdVendedorPadraoFDL(Integer idVendedorPadraoFDL) {
		this.idVendedorPadraoFDL = idVendedorPadraoFDL;
	}

	public Integer getPermitePromocao() {
		return permitePromocao;
	}

	public void setPermitePromocao(Integer permitePromocao) {
		this.permitePromocao = permitePromocao;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof EmpresaFB) ) return false;
        final EmpresaFB o = (EmpresaFB) other;
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
