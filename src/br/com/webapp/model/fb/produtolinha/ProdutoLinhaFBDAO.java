package br.com.webapp.model.fb.produtolinha;

import java.util.List;

public interface ProdutoLinhaFBDAO {

	public ProdutoLinhaFB carregar(Integer prodLinhaId);
	public List<ProdutoLinhaFB> listar();
}
