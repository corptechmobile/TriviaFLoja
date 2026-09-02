package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorInvContagem;

public class ColetorInvContagemResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private String status;
	private String mensagem;
	
	private List<EspelhoColetorInvContagem> transmitidos;
	private List<EspelhoColetorInvContagem> excluidos;
	
	public ColetorInvContagemResponse(){}

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

	public List<EspelhoColetorInvContagem> getTransmitidos() {
		return transmitidos;
	}

	public void setTransmitidos(List<EspelhoColetorInvContagem> transmitidos) {
		this.transmitidos = transmitidos;
	}

	public List<EspelhoColetorInvContagem> getExcluidos() {
		return excluidos;
	}

	public void setExcluidos(List<EspelhoColetorInvContagem> excluidos) {
		this.excluidos = excluidos;
	}
	
}
