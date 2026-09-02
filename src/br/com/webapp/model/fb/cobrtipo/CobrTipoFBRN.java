package br.com.webapp.model.fb.cobrtipo;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class CobrTipoFBRN {

	private CobrTipoFBDAO cobrTipoFBDAO;
	
	public CobrTipoFBRN() {
		this.cobrTipoFBDAO = DAOFactoryFirebird.criarCobrTipoFB();
	}
	
	public CobrTipoFB carregar(Integer id) {
		return this.cobrTipoFBDAO.carregar(id);
	}
	
	public List<CobrTipoFB> listar(){
		return this.cobrTipoFBDAO.listar();
	}
}
