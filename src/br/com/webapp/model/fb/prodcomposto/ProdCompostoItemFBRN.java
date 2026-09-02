package br.com.webapp.model.fb.prodcomposto;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class ProdCompostoItemFBRN {
	
	private ProdCompostoItemFBDAO prodCompostoItemFBDAO;
	
	public ProdCompostoItemFBRN() {
		this.prodCompostoItemFBDAO = DAOFactoryFirebird.criarProdCompostoItemFBDAO();
	}
	
	public List<ProdCompostoItemFBDTO> listar(Integer prodCompostoId){
		return this.prodCompostoItemFBDAO.listar(prodCompostoId);
	}

}
