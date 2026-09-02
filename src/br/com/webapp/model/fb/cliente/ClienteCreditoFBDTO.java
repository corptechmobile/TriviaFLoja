package br.com.webapp.model.fb.cliente;

import java.util.Date;

public class ClienteCreditoFBDTO {

	private Integer clienteId;
	private Double responsabilidade;
	private Double saldoDisponivel;
	private Double limiteCredito;
	private Integer possuiChequeDev;
	private Integer possuiDupVenc;
	
	public ClienteCreditoFBDTO() {}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Double getResponsabilidade() {
		return responsabilidade;
	}

	public void setResponsabilidade(Double responsabilidade) {
		this.responsabilidade = responsabilidade;
	}

	public Double getSaldoDisponivel() {
		return saldoDisponivel;
	}

	public void setSaldoDisponivel(Double saldoDisponivel) {
		this.saldoDisponivel = saldoDisponivel;
	}

	public Double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public Integer getPossuiChequeDev() {
		return possuiChequeDev;
	}

	public void setPossuiChequeDev(Integer possuiChequeDev) {
		this.possuiChequeDev = possuiChequeDev;
	}

	public Integer getPossuiDupVenc() {
		return possuiDupVenc;
	}

	public void setPossuiDupVenc(Integer possuiDupVenc) {
		this.possuiDupVenc = possuiDupVenc;
	}
	
}
