package br.com.coletor.response;

import java.util.List;

import br.com.coletor.model.ColetorOrdSepLote;

public class ColetorOrdSepLoteResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<ColetorOrdSepLote> lista;
	private String status;
	private String mensagem;
	
	public ColetorOrdSepLoteResponse(){}

	public List<ColetorOrdSepLote> getLista() {
		return lista;
	}

	public void setLista(List<ColetorOrdSepLote> lista) {
		this.lista = lista;
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