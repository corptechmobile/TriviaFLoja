package br.com.webapp.model.fb.produto;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ProdutoEstoqueLoteFBDAO {
	public List<ProdutoEstoqueLoteFB> listarEstoque(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque);
	public List<ProdutoEstoqueLoteFB> listarLotes(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque);
	public ProdutoEstoqueLoteFB carregar(Integer usuarioId, ProdutoEstoqueLoteFB produtoEstoqueLoteFB, Integer soComEstoque);
}
