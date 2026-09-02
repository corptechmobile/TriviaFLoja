package br.com.webapp.model.fb.linhaprodutometa;

import java.io.Serializable;

import br.com.webapp.model.fb.fretetipo.FreteTipoFB;

//@Entity
public class LinhaProdutoMetaFB implements Serializable{
	private static final long serialVersionUID = -177176762102988407L;

	private Integer id;
	private String anoMes;
	private Integer idPessoaEmp;
	private String descFornecedor;
	private String cnpjCpf;
	private Integer idVendedor;
	private String descVendedor;
	private String cnpjCpfVendedor;
	private Integer idLinhaProduto;
	private String descLinhaProduto;
	private Double valorPrevAnt;
	private Double valorRealAnt;
	private Double percPrevRealAnt;
	private Double percPrevRealAtual;
	private Double valor;
	private Double percPositivacao;
	private Double mixProduto;
	
	//@Transient
	private Double totalPrevAtual;

	//@Transient
	private Double totalPrevAnt;

	public LinhaProdutoMetaFB() {}

	public String getAnoMes() {
		return anoMes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getIdLinhaProduto() {
		return idLinhaProduto;
	}

	public void setIdLinhaProduto(Integer idLinhaProduto) {
		this.idLinhaProduto = idLinhaProduto;
	}

	public String getDescLinhaProduto() {
		return descLinhaProduto;
	}

	public void setDescLinhaProduto(String descLinhaProduto) {
		this.descLinhaProduto = descLinhaProduto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getValorPrevAnt() {
		return valorPrevAnt;
	}

	public void setValorPrevAnt(Double valorPrevAnt) {
		this.valorPrevAnt = valorPrevAnt;
	}

	public Double getValorRealAnt() {
		return valorRealAnt;
	}

	public void setValorRealAnt(Double valorRealAnt) {
		this.valorRealAnt = valorRealAnt;
	}

	public Double getPercPrevRealAnt() {
		return percPrevRealAnt;
	}

	public void setPercPrevRealAnt(Double percPrevRealAnt) {
		this.percPrevRealAnt = percPrevRealAnt;
	}

	public Double getPercPrevRealAtual() {
		return percPrevRealAtual;
	}

	public void setPercPrevRealAtual(Double percPrevRealAtual) {
		this.percPrevRealAtual = percPrevRealAtual;
	}

	public Double getTotalPrevAtual() {
		return totalPrevAtual;
	}

	public void setTotalPrevAtual(Double totalPrevAtual) {
		this.totalPrevAtual = totalPrevAtual;
	}

	public Double getTotalPrevAnt() {
		return totalPrevAnt;
	}

	public void setTotalPrevAnt(Double totalPrevAnt) {
		this.totalPrevAnt = totalPrevAnt;
	}
	
	public Double getPercPositivacao() {
		return percPositivacao;
	}

	public void setPercPositivacao(Double percPositivacao) {
		this.percPositivacao = percPositivacao;
	}

	public Double getMixProduto() {
		return mixProduto;
	}

	public void setMixProduto(Double mixProduto) {
		this.mixProduto = mixProduto;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getDescVendedor() {
		return descVendedor;
	}

	public void setDescVendedor(String descVendedor) {
		this.descVendedor = descVendedor;
	}

	public String getCnpjCpfVendedor() {
		return cnpjCpfVendedor;
	}

	public void setCnpjCpfVendedor(String cnpjCpfVendedor) {
		this.cnpjCpfVendedor = cnpjCpfVendedor;
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
		LinhaProdutoMetaFB other = (LinhaProdutoMetaFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
