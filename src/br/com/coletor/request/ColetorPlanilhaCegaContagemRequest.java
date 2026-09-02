package br.com.coletor.request;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;

public class ColetorPlanilhaCegaContagemRequest {
	
	private String url;
	private String login;
	private String senha;
	
	private Integer coletorPlanilhaCegaId;
	private List<EspelhoColetorPlanilhaCegaContagem> contagem;
	
	public ColetorPlanilhaCegaContagemRequest(){}

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

	public Integer getColetorPlanilhaCegaId() {
		return coletorPlanilhaCegaId;
	}

	public void setColetorPlanilhaCegaId(Integer coletorPlanilhaCegaId) {
		this.coletorPlanilhaCegaId = coletorPlanilhaCegaId;
	}

	public List<EspelhoColetorPlanilhaCegaContagem> getContagem() {
		return contagem;
	}

	public void setContagem(List<EspelhoColetorPlanilhaCegaContagem> contagem) {
		this.contagem = contagem;
	}

}