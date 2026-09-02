package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCega;

public class ColetorPlanilhaCegaResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<EspelhoColetorPlanilhaCega> planilhaCegas;
	private String status;
	private String mensagem;
	
	public ColetorPlanilhaCegaResponse(){}

	public List<EspelhoColetorPlanilhaCega> getPlanilhaCegas() {
		return planilhaCegas;
	}

	public void setPlanilhaCegas(List<EspelhoColetorPlanilhaCega> planilhaCegas) {
		this.planilhaCegas = planilhaCegas;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
