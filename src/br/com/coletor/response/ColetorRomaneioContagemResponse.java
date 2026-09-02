package br.com.coletor.response;

public class ColetorRomaneioContagemResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	public static final String ACAO_EXCLUIR = "E";
	
	private String status;
	private String mensagem;
	private String acao;
	
	public ColetorRomaneioContagemResponse(){
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

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}