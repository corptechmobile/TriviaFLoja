package br.com.webapp.model.fb.prodcomposto;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class ProdCompostoFBRN {
	
	private ProdCompostoFBDAO prodCompostoFBDAO;
	
	public ProdCompostoFBRN() {
		this.prodCompostoFBDAO = DAOFactoryFirebird.criarProdCompostoFBDAO();
	}
	
	public List<ProdCompostoFB> listar(){
		return this.prodCompostoFBDAO.listar();
	}

}
