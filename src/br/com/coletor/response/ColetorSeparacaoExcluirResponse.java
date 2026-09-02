package br.com.coletor.response;

public class ColetorSeparacaoExcluirResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private String status;
	private String mensagem;
	
	public ColetorSeparacaoExcluirResponse(){}

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