package br.com.coletor.request;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorInvContagem;

public class ColetorInvContagemRequest {
	
	private String url;
	private String login;
	private String senha;
	
	private Integer coletorInvId;
	private List<EspelhoColetorInvContagem> contagem;
	
	public ColetorInvContagemRequest(){}

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
	
	public Integer getColetorInvId() {
		return coletorInvId;
	}

	public void setColetorInvId(Integer coletorInvId) {
		this.coletorInvId = coletorInvId;
	}

	public List<EspelhoColetorInvContagem> getContagem() {
		return contagem;
	}

	public void setContagem(List<EspelhoColetorInvContagem> contagem) {
		this.contagem = contagem;
	}

}
