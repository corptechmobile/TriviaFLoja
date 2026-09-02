package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;

public class ColetorPlanilhaCegaContagemResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	public static final String ACAO_EXCLUIR = "E";
	
	private String status;
	private String mensagem;
	private String acao;
	
	private List<EspelhoColetorPlanilhaCegaContagem> transmitidos;
	private List<EspelhoColetorPlanilhaCegaContagem> excluidos;
	
	public ColetorPlanilhaCegaContagemResponse(){
		this.acao = null;
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

	public List<EspelhoColetorPlanilhaCegaContagem> getTransmitidos() {
		return transmitidos;
	}

	public void setTransmitidos(List<EspelhoColetorPlanilhaCegaContagem> transmitidos) {
		this.transmitidos = transmitidos;
	}

	public List<EspelhoColetorPlanilhaCegaContagem> getExcluidos() {
		return excluidos;
	}

	public void setExcluidos(List<EspelhoColetorPlanilhaCegaContagem> excluidos) {
		this.excluidos = excluidos;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}
