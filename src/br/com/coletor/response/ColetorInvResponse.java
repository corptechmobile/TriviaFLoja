package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorInv;

public class ColetorInvResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<EspelhoColetorInv> coletorInv;
	private String status;
	private String mensagem;
	
	public ColetorInvResponse(){}
	
	public List<EspelhoColetorInv> getColetorInv() {
		return coletorInv;
	}

	public void setColetorInv(List<EspelhoColetorInv> coletorInv) {
		this.coletorInv = coletorInv;
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
