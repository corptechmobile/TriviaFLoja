package br.com.webapp.model.fb.parametro;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class ParametroFBRN {

	private ParametroFBDAO parametroFBDAO;
	
	public ParametroFBRN() {
		parametroFBDAO = DAOFactoryFirebird.criarParametroFBDAO();
	}
	
	public ParametroFB carregar(String nome) {
		return this.parametroFBDAO.carregar(nome);
	}
	
	public List<ParametroFB> listar(){
		return this.parametroFBDAO.listar();
	}

}
