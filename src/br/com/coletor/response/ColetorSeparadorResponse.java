package br.com.coletor.response;

import br.com.coletor.model.ColetorSeparador;

public class ColetorSeparadorResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private ColetorSeparador separador;
	private String status;
	private String mensagem;
	
	public ColetorSeparadorResponse(){}

	public ColetorSeparador getSeparador() {
		return separador;
	}

	public void setSeparador(ColetorSeparador separador) {
		this.separador = separador;
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