package br.com.coletor.response;

import java.util.List;

import br.com.coletor.model.ColetorOrdSep;

public class ColetorOrdSepResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<ColetorOrdSep> coletorOrdSep;
	private String status;
	private String mensagem;
	
	public ColetorOrdSepResponse(){}
	
	public List<ColetorOrdSep> getColetorOrdSep() {
		return coletorOrdSep;
	}

	public void setColetorOrdSep(List<ColetorOrdSep> coletorOrdSep) {
		this.coletorOrdSep = coletorOrdSep;
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
