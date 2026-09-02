package br.com.coletor.espelho;

public class EspelhoUsuarioColetor {
	
	private Integer id;
	private String login;
	private String nome;
	private String senha;
	
	public EspelhoUsuarioColetor() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
