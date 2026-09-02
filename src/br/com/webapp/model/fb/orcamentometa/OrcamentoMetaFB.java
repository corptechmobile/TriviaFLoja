package br.com.webapp.model.fb.orcamentometa;

import java.io.Serializable;

//@Entity
public class OrcamentoMetaFB implements Serializable{
	private static final long serialVersionUID = -177176762102988407L;

	private Integer id;
	private String anoMes;
	private Integer idPessoaEmp;
	private String descFornecedor;
	private String cnpjCpf;
	private Double valorPrevFat;

	public OrcamentoMetaFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public Integer getIdPessoaEmp() {
		return idPessoaEmp;
	}

	public void setIdPessoaEmp(Integer idPessoaEmp) {
		this.idPessoaEmp = idPessoaEmp;
	}

	public String getDescFornecedor() {
		return descFornecedor;
	}

	public void setDescFornecedor(String descFornecedor) {
		this.descFornecedor = descFornecedor;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public Double getValorPrevFat() {
		return valorPrevFat;
	}

	public void setValorPrevFat(Double valorPrevFat) {
		this.valorPrevFat = valorPrevFat;
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
		OrcamentoMetaFB other = (OrcamentoMetaFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
