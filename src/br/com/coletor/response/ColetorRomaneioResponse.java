package br.com.coletor.response;

import java.util.List;

import br.com.coletor.model.ColetorRomaneio;

public class ColetorRomaneioResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<ColetorRomaneio> romaneios;
	private String status;
	private String mensagem;
	
	public ColetorRomaneioResponse(){}

	public List<ColetorRomaneio> getRomaneios() {
		return romaneios;
	}

	public void setRomaneios(List<ColetorRomaneio> romaneios) {
		this.romaneios = romaneios;
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
