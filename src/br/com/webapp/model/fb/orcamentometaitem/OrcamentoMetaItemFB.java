package br.com.webapp.model.fb.orcamentometaitem;

import java.io.Serializable;

import br.com.webapp.model.fb.fretetipo.FreteTipoFB;

//@Entity
public class OrcamentoMetaItemFB implements Serializable{
	private static final long serialVersionUID = -177176762102988407L;

	private Integer id;
	private String anoMes;
	private Integer idPessoaEmp;
	private String descFornecedor;
	private Integer idOrcamentoMeta;
	private String cnpjCpf;
	private Integer idOrcamentoGrupo;
	private String descOrcamentoGrupo;
	private Double valorPrevAnt;
	private Double valorRealAnt;
	private Double percPrevRealAnt;
	private Double percPrevRealAtual;
	private Double percFaturamento;
	private Double prevFaturamento;
	private Double valorOrcado;
	
	//@Transient
	private Double totalPrevAtual;

	//@Transient
	private Double totalPrevAnt;

	public OrcamentoMetaItemFB() {}

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

	public Integer getIdOrcamentoGrupo() {
		return idOrcamentoGrupo;
	}

	public void setIdOrcamentoGrupo(Integer idOrcamentoGrupo) {
		this.idOrcamentoGrupo = idOrcamentoGrupo;
	}

	public String getDescOrcamentoGrupo() {
		return descOrcamentoGrupo;
	}

	public void setDescOrcamentoGrupo(String descOrcamentoGrupo) {
		this.descOrcamentoGrupo = descOrcamentoGrupo;
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

	public Double getValorOrcado() {
		return valorOrcado;
	}

	public void setValorOrcado(Double valorOrcado) {
		this.valorOrcado = valorOrcado;
	}

	public Integer getIdOrcamentoMeta() {
		return idOrcamentoMeta;
	}

	public void setIdOrcamentoMeta(Integer idOrcamentoMeta) {
		this.idOrcamentoMeta = idOrcamentoMeta;
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

	public Double getPercFaturamento() {
		return percFaturamento;
	}

	public void setPercFaturamento(Double percFaturamento) {
		this.percFaturamento = percFaturamento;
	}

	public Double getPrevFaturamento() {
		return prevFaturamento;
	}

	public void setPrevFaturamento(Double prevFaturamento) {
		this.prevFaturamento = prevFaturamento;
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
		OrcamentoMetaItemFB other = (OrcamentoMetaItemFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
