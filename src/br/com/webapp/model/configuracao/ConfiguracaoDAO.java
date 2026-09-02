package br.com.webapp.model.configuracao;

import java.util.List;

public interface ConfiguracaoDAO {
	public Configuracao carregar(String nome);
	public List<Configuracao> listar();
}
