package br.com.coletor.response;

import java.util.List;

import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.ColetorOrdSepItem;

public class ColetorOrdSepSelectedResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private ColetorOrdSep ordemSeparacao;
	private List<ColetorOrdSepItem> itens;
	private String status;
	private String mensagem;
	
	public ColetorOrdSepSelectedResponse(){}

	public ColetorOrdSep getOrdemSeparacao() {
		return ordemSeparacao;
	}

	public void setOrdemSeparacao(ColetorOrdSep ordemSeparacao) {
		this.ordemSeparacao = ordemSeparacao;
	}

	public List<ColetorOrdSepItem> getItens() {
		return itens;
	}

	public void setItens(List<ColetorOrdSepItem> itens) {
		this.itens = itens;
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
