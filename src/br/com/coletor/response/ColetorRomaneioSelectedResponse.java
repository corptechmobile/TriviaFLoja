package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorRomaneioContagem;
import br.com.coletor.model.ColetorRomaneio;
import br.com.coletor.model.ColetorRomaneioItem;
import br.com.coletor.request.ColetorRomaneioContagemRequest;

public class ColetorRomaneioSelectedResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private ColetorRomaneio romaneio;
	private List<ColetorRomaneioItem> itens;
	private List<EspelhoColetorRomaneioContagem> contagem;
	private String status;
	private String mensagem;
	
	public ColetorRomaneioSelectedResponse(){}

	public ColetorRomaneio getRomaneio() {
		return romaneio;
	}

	public void setRomaneio(ColetorRomaneio romaneio) {
		this.romaneio = romaneio;
	}

	public List<ColetorRomaneioItem> getItens() {
		return itens;
	}

	public void setItens(List<ColetorRomaneioItem> itens) {
		this.itens = itens;
	}
	
	public List<EspelhoColetorRomaneioContagem> getContagem() {
		return contagem;
	}

	public void setContagem(List<EspelhoColetorRomaneioContagem> contagem) {
		this.contagem = contagem;
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
