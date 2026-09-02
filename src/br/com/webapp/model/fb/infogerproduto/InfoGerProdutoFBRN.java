package br.com.webapp.model.fb.infogerproduto;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class InfoGerProdutoFBRN {
	
	private InfoGerProdutoFBDAO infoGerProdutoFBDAO;
	
	public InfoGerProdutoFBRN(){
		this.infoGerProdutoFBDAO = DAOFactoryFirebird.criarInfoGerProdutoFBDAO();
	}
	
	public List<InfoGerProdutoFB> listar(String query) {
		return this.infoGerProdutoFBDAO.listar(query);
	}

	public InfoGerProdutoFB carregar(int codigo) {
		return this.infoGerProdutoFBDAO.carregar(codigo);
	}

	public void update(InfoGerProdutoFB infoGerProdutoFB) throws DAOException {
		this.infoGerProdutoFBDAO.update(infoGerProdutoFB);
	}
	
	public void rollBack() {
		this.infoGerProdutoFBDAO.rollBack();
	}


}
