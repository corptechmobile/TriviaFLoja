package br.com.coletor.response;

import br.com.coletor.espelho.EspelhoUsuarioColetor;

public class AuthColetorResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private EspelhoUsuarioColetor usuario;
	
	private String status;
	private String mensagem;
	
	public AuthColetorResponse() {}
	
	public EspelhoUsuarioColetor getUsuario() {
		return usuario;
	}

	public void setUsuario(EspelhoUsuarioColetor usuario) {
		this.usuario = usuario;
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
