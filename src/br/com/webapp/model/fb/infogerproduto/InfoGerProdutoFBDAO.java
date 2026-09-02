package br.com.webapp.model.fb.infogerproduto;

import java.util.List;

import org.primefaces.model.SortOrder;

import br.com.webapp.web.util.DAOException;

public interface InfoGerProdutoFBDAO {
	
	public InfoGerProdutoFB carregar(int codigo);
	public List<InfoGerProdutoFB> listar(String descricao);
	void update(InfoGerProdutoFB infoGerProdutoFB) throws DAOException;
	public void rollBack();
	
}