package br.com.webapp.model.fb.produto;

import java.util.List;

import org.primefaces.model.SortOrder;

import br.com.webapp.web.util.DAOException;

public interface ProdutoFBDAO {
	
	public ProdutoFB carregar(int codigo);
	public ProdutoFB carregar(Integer produtoId, Integer permiteVendaSemEstoque, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete);
	public ProdutoFB carregarProdComposto(Integer empresaId, String tabPrecoId, Integer condPagtoId, Integer pedVendaCompostoId);
	public ProdutoPrecoDTO carregarPreco(String tabPrecoId, Integer condPagtoId, Integer produtoId, Integer empresaId, Integer tipoFrete);
	
	public Double comissao(Integer vendedorId, Integer produtoId);
	public Double comissaoFaixa(Integer produtoId, Double percDesconto);
	
	public List<ProdutoFB> listar(int first, int pageSize, String sortField, SortOrder sortOrder, String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete);
	public List<ProdutoFB> listarVendaSemEstoqueDisponivel(int first, int pageSize, String sortField, SortOrder sortOrder, Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String[] splitDescricao,String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId, Integer tipoFrete);
	public List<ProdutoFB> listarProdutosComposto(int first, int pageSize, String sortField, SortOrder sortOrder, Integer empresaId, String descricaoFilter, String[] splitDescricao, String fabricanteFilter, String tabPrecoId, Integer condPagtoId);
	public List<ProdutoFB> listar(String descricao);
	
	public Integer countListar(String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter);
	public Integer countVendaSemEstoqueDisponivel(Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String[] splitDescricao, String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId);
	public Integer countProdutosComposto(Integer empresaId, String descricaoFilter, String[] splitDescricao, String fabricanteFilter);
	public void atualizarCodBarras(Integer produtoId, String codBarra) throws DAOException;
	public List<ProdutoFB> listaProdutosEstoqueSemContagem(Integer empresaId, Integer inventarioId, String produtoFilter);
	
	
	
}