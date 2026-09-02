package br.com.coletor.request;

public class AuthColetorRequest {
	
	private String login;
	private String senha;
	
	public AuthColetorRequest(){}

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
	
}
