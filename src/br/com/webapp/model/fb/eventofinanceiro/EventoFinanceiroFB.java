package br.com.webapp.model.fb.eventofinanceiro;

import java.io.Serializable;
import java.util.Date;

//@Entity
public class EventoFinanceiroFB implements Serializable{
	private static final long serialVersionUID = 2701902945339197308L;
	private Integer id;
	private String numTitulo;
	private Integer parcela;
	private String grupoFinanceiroId;
	private String descGrupoFinanceiro;
	private String mnemonico;
	private Integer eventoFinanceiroId;
	private String descEventoFinanceiro;
	private Integer fixo;
	private Integer bloqueado;
	private String codFiscTributoId;
	private Integer obrigaCodFiscTributo;
	private Integer prestacaoContas;
	private Integer restritoSistema; 
	private Integer orcamentoGrupoId;
	private Integer fornecedorId;
	private String fornecedorDesc;
	private Date dtVencimento;
	private Double valorPago;
	private Double valorAvencer;
	private Double valorVencido;
	private Double valorTotal;
	private Double percentual;
	
	public EventoFinanceiroFB() {}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumTitulo() {
		return numTitulo;
	}

	public void setNumTitulo(String numTitulo) {
		this.numTitulo = numTitulo;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public String getGrupoFinanceiroId() {
		return grupoFinanceiroId;
	}

	public void setGrupoFinanceiroId(String grupoFinanceiroId) {
		this.grupoFinanceiroId = grupoFinanceiroId;
	}

	public String getDescGrupoFinanceiro() {
		return descGrupoFinanceiro;
	}

	public void setDescGrupoFinanceiro(String descGrupoFinanceiro) {
		this.descGrupoFinanceiro = descGrupoFinanceiro;
	}

	public String getMnemonico() {
		return mnemonico;
	}

	public void setMnemonico(String mnemonico) {
		this.mnemonico = mnemonico;
	}

	public Integer getEventoFinanceiroId() {
		return eventoFinanceiroId;
	}

	public void setEventoFinanceiroId(Integer eventoFinanceiroId) {
		this.eventoFinanceiroId = eventoFinanceiroId;
	}

	public String getDescEventoFinanceiro() {
		return descEventoFinanceiro;
	}

	public void setDescEventoFinanceiro(String descEventoFinanceiro) {
		this.descEventoFinanceiro = descEventoFinanceiro;
	}

	public Integer getFixo() {
		return fixo;
	}

	public void setFixo(Integer fixo) {
		this.fixo = fixo;
	}

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getCodFiscTributoId() {
		return codFiscTributoId;
	}

	public void setCodFiscTributoId(String codFiscTributoId) {
		this.codFiscTributoId = codFiscTributoId;
	}

	public Integer getPrestacaoContas() {
		return prestacaoContas;
	}

	public void setPrestacaoContas(Integer prestacaoContas) {
		this.prestacaoContas = prestacaoContas;
	}

	public Integer getRestritoSistema() {
		return restritoSistema;
	}

	public void setRestritoSistema(Integer restritoSistema) {
		this.restritoSistema = restritoSistema;
	}

	public Integer getObrigaCodFiscTributo() {
		return obrigaCodFiscTributo;
	}

	public void setObrigaCodFiscTributo(Integer obrigaCodFiscTributo) {
		this.obrigaCodFiscTributo = obrigaCodFiscTributo;
	}

	public Integer getOrcamentoGrupoId() {
		return orcamentoGrupoId;
	}

	public void setOrcamentoGrupoId(Integer orcamentoGrupoId) {
		this.orcamentoGrupoId = orcamentoGrupoId;
	}

	public Integer getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public String getFornecedorDesc() {
		return fornecedorDesc;
	}

	public void setFornecedorDesc(String fornecedorDesc) {
		this.fornecedorDesc = fornecedorDesc;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getValorAvencer() {
		return valorAvencer;
	}

	public void setValorAvencer(Double valorAvencer) {
		this.valorAvencer = valorAvencer;
	}

	public Double getValorVencido() {
		return valorVencido;
	}

	public void setValorVencido(Double valorVencido) {
		this.valorVencido = valorVencido;
	}

	public Double getValorTotal() {
		if(this.valorPago==null) {
			this.valorPago = 0d;
		}
		
		if(this.valorAvencer==null) {
			this.valorAvencer = 0d;
		}

		if(this.valorPago==null) {
			this.valorVencido = 0d;
		}

		return this.valorPago+this.valorAvencer+this.valorVencido;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Double getPercentual() {
		return percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
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
		EventoFinanceiroFB other = (EventoFinanceiroFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getDescEventoFinanceiro();
	}

}
