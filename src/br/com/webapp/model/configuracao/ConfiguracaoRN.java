package br.com.webapp.model.configuracao;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryPostGres;

public class ConfiguracaoRN {

	private ConfiguracaoDAO configuracaoDAO;
	
	public ConfiguracaoRN(){
		this.configuracaoDAO = DAOFactoryPostGres.criarConfiguracaoDAO();
	}
	public Configuracao carregar(String nome){
		return this.configuracaoDAO.carregar(nome);
	}
	public List<Configuracao> listar(){
		return this.configuracaoDAO.listar();
	}
	
}
