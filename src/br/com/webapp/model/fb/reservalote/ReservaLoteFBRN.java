package br.com.webapp.model.fb.reservalote;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ReservaLoteFBRN {

	private ReservaLoteFBDAO reservaLoteFBDAO;
	
	public ReservaLoteFBRN() {
		reservaLoteFBDAO = DAOFactoryFirebird.criarReservaLoteFBDAO();
	}
	
	public ReservaLoteFB novo(Integer reservaId, ProdutoEstoqueFB produtoEstoqueFB) {
		
		ReservaLoteFB reservaLoteFB = new ReservaLoteFB();
		reservaLoteFB.setProdutoLoteId(produtoEstoqueFB.getProdutoLoteId());
		reservaLoteFB.setLocalidadeId(produtoEstoqueFB.getLocalidadeId());
		reservaLoteFB.setQuantidade(produtoEstoqueFB.getQtdReservar());
		reservaLoteFB.setOrdemCarregItemId(ReservaLoteFB.ORDEMCARREGITEMID);
		reservaLoteFB.setOrdemProdRequisicaoItemId(ReservaLoteFB.ORDEMPRODREQUISICAOITEMID);
		reservaLoteFB.setReservaId(reservaId);
		
		return reservaLoteFB;
		
	}
	
	public void insert(Integer reservaId, ProdutoEstoqueFB produtoEstoqueFB) throws DAOException {
		ReservaLoteFB reservaLoteFB = this.novo(reservaId, produtoEstoqueFB);
		this.reservaLoteFBDAO.insert(reservaLoteFB);
	}
	
	public void insert(ReservaLoteFB reservaLoteFB) throws DAOException {
		this.reservaLoteFBDAO.insert(reservaLoteFB);
	}
	
	public void update(ReservaLoteFB reservaLoteFB) throws DAOException {
		this.reservaLoteFBDAO.update(reservaLoteFB);
	}
	
	public ReservaLoteFB carregar(Integer id) {
		return this.reservaLoteFBDAO.carregar(id);
	}
	
	public List<ReservaLoteFB> listar(){
		return this.reservaLoteFBDAO.listar();
	}

	public List<ReservaLoteFB> listar(PedVendaItemFB pedVendaItem) {
		return this.reservaLoteFBDAO.listar(pedVendaItem);
	}

}
