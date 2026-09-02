package br.com.webapp.model.fb.produtocb;

import java.util.List;

import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.web.util.DAOException;

public interface ProdutoCBFBDAO {
	public ProdutoCBFB carregar(String codigobarras);
	public ProdutoCBFB carregar(Integer produtoId, String codigobarras);
	public ProdutoCBFB carregar(Integer produtoId, Double qtd);
	
	public void excluir(ProdutoCBFB produtoCB) throws DAOException;
	
	public List<ProdutoCBFB> listar();
	public List<ProdutoCBFB> listar(ProdutoLinhaFB produtoLinhaFilter, String produtoFilter, String codigoBarraFilter);
	
	public void update(ProdutoCBFB produtoCBFB) throws DAOException;
	public void insert(ProdutoCBFB produtoCBFB) throws DAOException;
	public ProdutoCBFB salvar(ProdutoCBFB selecionada) throws DAOException;
	
}
