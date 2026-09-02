package br.com.coletor.request;

public class ColetorRomaneioRequest {
	
	private String url;
	private String login;
	private String senha;
	private Integer numFilter;
	
	public ColetorRomaneioRequest(){}

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
	
	public Integer getNumFilter() {
		return numFilter;
	}

	public void setNumFilter(Integer numFilter) {
		this.numFilter = numFilter;
	}

}
