package br.com.webapp.model.fb.produto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.SortOrder;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFB;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBRN;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ProdutoFBRN {
	
	private ProdutoFBDAO produtoFBDAO;
	private int first;
	private int pageSize;
	private String sortField;
	private SortOrder sortOrder;
	
	public ProdutoFBRN(){
		this.produtoFBDAO = DAOFactoryFirebird.criarProdutoFBDAO();
	}
	
	public void paginacao(int first, int pageSize, String sortField, SortOrder sortOrder) {
		this.first=first;
		this.pageSize=pageSize;
		this.sortField=sortField; 
		this.sortOrder=sortOrder;
	}
	
	private void validarFilter(Integer empresaId, Integer opFiscTipoId, String tabPrecoId) throws RNException {
		if(empresaId==null) {
			throw UtilMessage.exceptionMensagem("msg.erro.consulta.empresa.pedvenda", null);
		}
		
		if(opFiscTipoId==null) {
			UtilMessage.exceptionMensagem("msg.erro.consulta.opfisctipo.pedvenda", null);
		}
		
		if(tabPrecoId==null || "".equals(tabPrecoId)) {
			UtilMessage.exceptionMensagem("msg.erro.consulta.tabpreco.pedvenda", null);
		}
	}

	public ProdutoFB carregar(Integer pedVendaEcomenda, Integer produtoId, Integer permiteVendaSemEstoque, Integer empresaId, Integer opFiscTipoId, Integer usuarioId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete) throws RNException {
		
		validarFilter(empresaId, opFiscTipoId, tabPrecoId);
		
		ProdutoFB produtoFB = this.produtoFBDAO.carregar(produtoId, permiteVendaSemEstoque, empresaId, opFiscTipoId, tabPrecoId, condPagtoId, isTransferencia, compartilhaEstoque, empEnchEstqCompart, comEstoqueFilter, semEstoqueFilter, tipoFrete);
		if(produtoFB!=null && (pedVendaEcomenda.equals(PedVendaFB.PEDIDO) || pedVendaEcomenda.equals(PedVendaFB.ENCOMENDA))) {
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			produtoFB.setEstoques(produtoEstoqueFBRN.listar(pedVendaEcomenda, empresaId, usuarioId, produtoId, produtoFB.getControlaLote(), Funcoes.SO_ESTOQUE, produtoFB.getPermiteVendaSemEstoque()));
		}
		
		return produtoFB;
	}
	
	public ProdutoFB carregarProdComposto(Integer empresaId, Integer usuarioId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, PedVendaCompostoFB pedVendaComposto) throws RNException {
		
		validarFilter(empresaId, opFiscTipoId, tabPrecoId);
		
		ProdutoFB produtoFB = this.produtoFBDAO.carregarProdComposto(empresaId, tabPrecoId, condPagtoId, pedVendaComposto.getId());
		if(produtoFB!=null) {
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			produtoFB.setEstoques(produtoEstoqueFBRN.listar(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO, empresaId, usuarioId, produtoFB.getId(), produtoFB.getControlaLote(), Funcoes.SO_ESTOQUE, produtoFB.getPermiteVendaSemEstoque()));
			for(ProdutoEstoqueFB rs : produtoFB.getEstoques()) {
				rs.setQtdReservar(pedVendaComposto.getQuantidade());
				rs.setQtdDisponivel(rs.getQtdDisponivel() + pedVendaComposto.getQuantidade());
			}
			produtoFB.setComposicoes(new ProdCompostoItemFBRN().listar(produtoFB.getId()));
		}
		
		
		return produtoFB;
		
	}

	public ProdutoPrecoDTO carregarPreco(String tabPrecoId, Integer condPagtoId, Integer produtoId, Integer empresaId, Integer tipoFrete) {
		return this.produtoFBDAO.carregarPreco(tabPrecoId, condPagtoId, produtoId, empresaId, tipoFrete);
	}

	public Double comissao(Integer tipoComissao, Integer vendedorId, Integer produtoId, Double percDesconto) {
		if(tipoComissao.equals(EmpresaFB.COMISSAO_LINHAPRODUTO)) {
			return this.produtoFBDAO.comissaoFaixa(produtoId, percDesconto);
		}else {
			return this.produtoFBDAO.comissao(vendedorId, produtoId);
		}
	}

	
	public List<ProdutoFB> listar(String descricaoFilter, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter, Integer tipoFrete) throws RNException {
		
		validarFilter(empresaId, opFiscTipoId, tabPrecoId);
		
		if(linhaProdFilter != null) {
			linhaProdFilter = linhaProdFilter.toUpperCase();
		}
		
		String[] splitDescricao = null;
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			descricaoFilter = null;
		}else {
			if (descricaoFilter != null && !"".equals(descricaoFilter)) {
				descricaoFilter = descricaoFilter.toUpperCase();
				splitDescricao = descricaoFilter.split(" ");
			}
			
		}
		
		
		return this.produtoFBDAO.listar(first, pageSize, sortField, sortOrder, descricaoFilter, splitDescricao, codBarraFilter, linhaProdFilter, empresaId, opFiscTipoId, tabPrecoId, condPagtoId, isTransferencia, compartilhaEstoque, empEnchEstqCompart, comEstoqueFilter, semEstoqueFilter, tipoFrete);
	}
	
	public List<ProdutoFB> listarVendaSemEstoqueDisponivel(Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId, Integer tipoFrete) throws RNException {
		
		validarFilter(empresaId, opFiscTipoId, tabPrecoId);
		
		if(linhaProdFilter != null) {
			linhaProdFilter = linhaProdFilter.toUpperCase();
		}
		
		String[] splitDescricao = null;
		if(codBarraFilter!=null && !"".equals(codBarraFilter)) {
			descricaoFilter = null;
		}else {
			if (descricaoFilter != null && !"".equals(descricaoFilter)) {
				descricaoFilter = descricaoFilter.toUpperCase();
				splitDescricao = descricaoFilter.split(" ");
			}
		}
		return this.produtoFBDAO.listarVendaSemEstoqueDisponivel(first, pageSize, sortField, sortOrder, empresaId, opFiscTipoId, descricaoFilter, splitDescricao, codBarraFilter, linhaProdFilter, tabPrecoId, condPagtoId, tipoFrete);
	}

	public List<ProdutoFB> listarProdutosComposto(Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String fabricanteFilter, String tabPrecoId, Integer condPagtoId) throws RNException {
		
		validarFilter(empresaId, opFiscTipoId, tabPrecoId);
		
		String[] splitDescricao = null;
		if (descricaoFilter != null && !"".equals(descricaoFilter)) {
			descricaoFilter = descricaoFilter.toUpperCase();
			splitDescricao = descricaoFilter.split(" ");
		}
		
		return this.produtoFBDAO.listarProdutosComposto(first, pageSize, sortField, sortOrder, empresaId, descricaoFilter, splitDescricao, fabricanteFilter, tabPrecoId, condPagtoId);
	}

	public Integer countListar(String descricaoFilter, String codBarraFilter, String linhaProdFilter, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, Integer isTransferencia, Integer compartilhaEstoque, Integer empEnchEstqCompart, boolean comEstoqueFilter, boolean semEstoqueFilter) {
		
		if(linhaProdFilter != null) {
			linhaProdFilter = linhaProdFilter.toUpperCase();
		}
		
		String[] splitDescricao = null;
		if (descricaoFilter != null && !"".equals(descricaoFilter)) {
			descricaoFilter = descricaoFilter.toUpperCase();
			splitDescricao = descricaoFilter.split(" ");
		}
		
		return this.produtoFBDAO.countListar(descricaoFilter, splitDescricao, codBarraFilter, linhaProdFilter, empresaId, opFiscTipoId, tabPrecoId, condPagtoId, isTransferencia, compartilhaEstoque, empEnchEstqCompart, comEstoqueFilter, semEstoqueFilter);
	}
	
	public Integer countVendaSemEstoqueDisponivel(Integer empresaId, Integer opFiscTipoId, String descricaoFilter, String codBarraFilter, String linhaProdFilter, String tabPrecoId, Integer condPagtoId) {
		
		if(linhaProdFilter != null) {
			linhaProdFilter = linhaProdFilter.toUpperCase();
		}
		
		String[] splitDescricao = null;
		if (descricaoFilter != null && !"".equals(descricaoFilter)) {
			descricaoFilter = descricaoFilter.toUpperCase();
			splitDescricao = descricaoFilter.split(" ");
		}
		
		return this.produtoFBDAO.countVendaSemEstoqueDisponivel(empresaId, opFiscTipoId, descricaoFilter, splitDescricao, codBarraFilter, linhaProdFilter, tabPrecoId, condPagtoId);
	}
	
	public Integer countProdutosComposto(Integer empresaId, String descricaoFilter, String fabricanteFilter) {
		
		String[] splitDescricao = null;
		if (descricaoFilter != null && !"".equals(descricaoFilter)) {
			descricaoFilter = descricaoFilter.toUpperCase();
			splitDescricao = descricaoFilter.split(" ");
		}
		
		return this.produtoFBDAO.countProdutosComposto(empresaId, descricaoFilter, splitDescricao, fabricanteFilter);
	}

	public List<ProdutoFB> listar(String query) {
		return this.produtoFBDAO.listar(query);
	}

	public ProdutoFB carregar(int codigo) {
		return this.produtoFBDAO.carregar(codigo);
	}
	

	public List<SelectItem> montaDadosSelect(List<ProdutoFB> produtos, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (produtos != null) {
			for (ProdutoFB produto : produtos) {
				item = new SelectItem(produto, produto.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}

	public void atualizarCodBarras(Integer produtoId, String codBarra) throws DAOException {
		this.produtoFBDAO.atualizarCodBarras(produtoId, codBarra);
	}

	public List<ProdutoFB> listaProdutosEstoqueSemContagem(Integer empresaId, Integer inventarioId, String produtoFilter) {
		return this.produtoFBDAO.listaProdutosEstoqueSemContagem(empresaId, inventarioId, produtoFilter);
	}

}
