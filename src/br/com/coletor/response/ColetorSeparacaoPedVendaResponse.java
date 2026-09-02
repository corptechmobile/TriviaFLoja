package br.com.coletor.response;

import br.com.coletor.model.ColetorSeparacao;

public class ColetorSeparacaoPedVendaResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private ColetorSeparacao separacao;
	private String status;
	private String mensagem;
	
	public ColetorSeparacaoPedVendaResponse(){}

	public ColetorSeparacao getSeparacao() {
		return separacao;
	}

	public void setSeparacao(ColetorSeparacao separacao) {
		this.separacao = separacao;
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