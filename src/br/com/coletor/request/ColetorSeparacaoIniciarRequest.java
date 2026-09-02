package br.com.coletor.request;

public class ColetorSeparacaoIniciarRequest {
	
	private String url;
	private String login;
	private String senha;
	private Integer ordemCarregId;
	private Integer separadorId;
	
	public ColetorSeparacaoIniciarRequest(){}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getOrdemCarregId() {
		return ordemCarregId;
	}

	public void setOrdemCarregId(Integer ordemCarregId) {
		this.ordemCarregId = ordemCarregId;
	}

	public Integer getSeparadorId() {
		return separadorId;
	}

	public void setSeparadorId(Integer separadorId) {
		this.separadorId = separadorId;
	}

}