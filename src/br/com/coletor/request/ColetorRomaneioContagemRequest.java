package br.com.coletor.request;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorRomaneioContagem;

public class ColetorRomaneioContagemRequest {
	
	private String url;
	private String login;
	private String senha;
	
	private Integer coletorRomaneioId;
	private List<EspelhoColetorRomaneioContagem> contagem;
	
	public ColetorRomaneioContagemRequest(){}

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

	public Integer getColetorRomaneioId() {
		return coletorRomaneioId;
	}

	public void setColetorRomaneioId(Integer coletorRomaneioId) {
		this.coletorRomaneioId = coletorRomaneioId;
	}

	public List<EspelhoColetorRomaneioContagem> getContagem() {
		return contagem;
	}

	public void setContagem(List<EspelhoColetorRomaneioContagem> contagem) {
		this.contagem = contagem;
	}

}