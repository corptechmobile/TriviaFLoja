package br.com.coletor.response;

import java.util.List;

import br.com.coletor.model.ColetorSeparacao;

public class ColetorSeparacaoResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<ColetorSeparacao> lista;
	private String status;
	private String mensagem;
	
	public ColetorSeparacaoResponse(){}

	public List<ColetorSeparacao> getLista() {
		return lista;
	}

	public void setLista(List<ColetorSeparacao> lista) {
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