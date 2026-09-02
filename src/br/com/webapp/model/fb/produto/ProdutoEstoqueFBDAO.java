package br.com.webapp.model.fb.produto;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ProdutoEstoqueFBDAO {
	public List<ProdutoEstoqueFB> listarControlaLote(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque);
	public List<ProdutoEstoqueFB> listarNaoControlaLote(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque);
	public List<ProdutoEstoqueFB> listarVendaSemEstoqueDisponivel(Integer empresaId, Integer usuarioId, Integer produtoId);
	public List<ProdutoEstoqueFB> listarProdComposto(Integer empresaId, Integer usuarioId, Integer prodCompostoId, Integer soComEstoque);
	public List<ProdutoEstoqueFB> listarTodos(Integer empresaId, Integer usuarioId, Integer produtoControlaLote, Integer produtoId, Integer soComEstoque);
	public ProdutoEstoqueFB carregarControlaLote(Integer usuarioId, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque);
	public ProdutoEstoqueFB carregarNaoControlaLote(Integer usuarioId, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque);
	public ProdutoEstoqueFB carregarTodos(Integer usuarioId, Integer produtoControlaLote, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque);
	public void bloqueEstoque(Integer empresaFBId, Integer produtoFBId) throws DAOException;

}
