package br.com.webapp.model.fb.pedvendacomposto;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class PedVendaCompostoFBRN {
	
	private PedVendaCompostoFBDAO pedVendaCompostoFBDAO;
	
	public PedVendaCompostoFBRN() {
		this.pedVendaCompostoFBDAO = DAOFactoryFirebird.criarPedVendaCompostoFBDAO();
	}
	
	public PedVendaCompostoFB novo(ProdutoFB produtoFB) {
		PedVendaCompostoFB pedVendaCompostoFB = new PedVendaCompostoFB();
		pedVendaCompostoFB.setCodProduto(produtoFB.getCodInterno());
		pedVendaCompostoFB.setDescricao(produtoFB.getDescricao());
		pedVendaCompostoFB.setQuantidade(0.0);
		pedVendaCompostoFB.setUnidadeId(Funcoes.UNIDADE_PADRAO);
		return pedVendaCompostoFB;
	}

	public PedVendaCompostoFB carregar(Integer pedVendaId, Integer prodCompostoId) {
		return this.pedVendaCompostoFBDAO.carregar(pedVendaId, prodCompostoId);
	}
	
	public Integer insert(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException {
		return this.pedVendaCompostoFBDAO.insert(pedVendaCompostoFB);
	}
	
	public void updateQuantidade(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException {
		this.pedVendaCompostoFBDAO.updateQuantidade(pedVendaCompostoFB);
	}
	
	public void excluir(Integer pedVendaCompostoId) throws DAOException {
		this.pedVendaCompostoFBDAO.excluir(pedVendaCompostoId);
	}

	public List<PedVendaCompostoFB> listar(PedVendaFB pedVendaFB){
		return this.pedVendaCompostoFBDAO.listar(pedVendaFB);
	}

	

}