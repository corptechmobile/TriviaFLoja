package br.com.webapp.model.fb.produto;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ProdutoEstoqueFBRN {
	
	private ProdutoEstoqueFBDAO produtoEstoqueFBDAO;
	
	public ProdutoEstoqueFBRN(){
		this.produtoEstoqueFBDAO = DAOFactoryFirebird.criarProdutoEstoqueFBDAO();
	}
	
	public List<ProdutoEstoqueFB> listar(Integer pedVendaEcomenda, Integer empresaId, Integer usuarioId, Integer produtoId, Integer produtoControlaLote, Integer soComEstoque, Integer permiteVendaSemEstoque) {
		if(permiteVendaSemEstoque.equals(ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE) && pedVendaEcomenda.equals(PedVendaFB.ENCOMENDA)) {
			return this.produtoEstoqueFBDAO.listarVendaSemEstoqueDisponivel(empresaId, usuarioId, produtoId);
		}else if(pedVendaEcomenda.equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			return this.produtoEstoqueFBDAO.listarProdComposto(empresaId, usuarioId, produtoId, soComEstoque);
		}else {
			if(produtoControlaLote.equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
				return this.produtoEstoqueFBDAO.listarControlaLote(empresaId, usuarioId, produtoId, soComEstoque);
			}else {
				return this.produtoEstoqueFBDAO.listarNaoControlaLote(empresaId, usuarioId, produtoId, soComEstoque);
			}
		}
	}

	public ProdutoEstoqueFB carregar(Integer usuarioId, Integer produtoControlaLote, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque) {
		if(produtoControlaLote.equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
			return this.produtoEstoqueFBDAO.carregarControlaLote(usuarioId, produtoEstoqueFB, soComEstoque);
		}else {
			return this.produtoEstoqueFBDAO.carregarNaoControlaLote(usuarioId, produtoEstoqueFB, soComEstoque);
		}
	}
	
	public void bloqueEstoque(Integer empresaFBId, Integer produtoFBId) throws DAOException {
		this.produtoEstoqueFBDAO.bloqueEstoque(empresaFBId, produtoFBId);
	}

	public List<ProdutoEstoqueFB> listarTodos(Integer empresaId, Integer usuarioId, Integer produtoControlaLote, Integer produtoId, Integer soComEstoque){
		return this.produtoEstoqueFBDAO.listarTodos(empresaId, usuarioId, produtoControlaLote, produtoId, soComEstoque);
	}

	public ProdutoEstoqueFB carregarTodos(Integer usuarioId, Integer produtoControlaLote, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque) {
		return this.produtoEstoqueFBDAO.carregarTodos(usuarioId, produtoControlaLote, produtoEstoqueFB, soComEstoque);
	}



}
