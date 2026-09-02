package br.com.webapp.model.fb.produto;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ProdutoEstoqueLoteFBRN {
	
	private ProdutoEstoqueLoteFBDAO produtoEstoqueLoteFBDAO;
	
	public ProdutoEstoqueLoteFBRN(){
		this.produtoEstoqueLoteFBDAO = DAOFactoryFirebird.criarProdutoEstoqueLoteFBDAO();
	}
	
	public List<ProdutoEstoqueLoteFB> listarEstoque(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		return this.produtoEstoqueLoteFBDAO.listarEstoque(empresaId, usuarioId, produtoId, soComEstoque);
	}

	public List<ProdutoEstoqueLoteFB> listarLotes(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		return this.produtoEstoqueLoteFBDAO.listarLotes(empresaId, usuarioId, produtoId, soComEstoque);
	}

	public ProdutoEstoqueLoteFB carregar(Integer usuarioId, ProdutoEstoqueLoteFB produtoEstoqueLoteFB, Integer soComEstoque) {
		return this.produtoEstoqueLoteFBDAO.carregar(usuarioId, produtoEstoqueLoteFB, soComEstoque);
	}

}
