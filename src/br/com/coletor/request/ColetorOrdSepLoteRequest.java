package br.com.coletor.request;

public class ColetorOrdSepLoteRequest {
	
	private String url;
	private String login;
	private String senha;
	private Integer ordemCarregItemId;
	private Integer produtoId;
	
	public ColetorOrdSepLoteRequest(){}

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

	public Integer getOrdemCarregItemId() {
		return ordemCarregItemId;
	}

	public void setOrdemCarregItemId(Integer ordemCarregItemId) {
		this.ordemCarregItemId = ordemCarregItemId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

}