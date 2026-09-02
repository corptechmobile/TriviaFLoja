package br.com.coletor.request;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorOrdSepItemContagem;

public class ColetorOrdSepContagemRequest {
	
	private String url;
	private String login;
	private String senha;
	
	private Integer id;
	private List<EspelhoColetorOrdSepItemContagem> contagem;
	
	public ColetorOrdSepContagemRequest(){}

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

	public void setId(Integer id) {
		this.id = id;
	}

	public List<EspelhoColetorOrdSepItemContagem> getContagem() {
		return contagem;
	}

	public void setContagem(List<EspelhoColetorOrdSepItemContagem> contagem) {
		this.contagem = contagem;
	}

}