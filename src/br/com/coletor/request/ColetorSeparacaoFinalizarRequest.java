package br.com.coletor.request;

public class ColetorSeparacaoFinalizarRequest {
	
	private String url;
	private String login;
	private String senha;
	private Integer id;
	
	public ColetorSeparacaoFinalizarRequest(){}

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

	public Integer getId() {
		return id;
	}

	public void setColetorSeparacaoId(Integer id) {
		this.id = id;
	}

}