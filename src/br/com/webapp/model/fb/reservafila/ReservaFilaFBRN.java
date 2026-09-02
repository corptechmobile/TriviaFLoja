package br.com.webapp.model.fb.reservafila;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ReservaFilaFBRN {
	
	private ReservaFilaFBDAO reservaFilaFBDAO;
	
	public ReservaFilaFBRN() {
		this.reservaFilaFBDAO = DAOFactoryFirebird.criarReservaFilaFBDAO();
	}
	
	public void salvar(Integer empresaFBId, Integer pedVendaFBId, Integer pedVendaItemFBId, Integer produtoFBId, Double quantidade) throws DAOException {
		this.excluir(pedVendaItemFBId);
		
		ReservaFilaFB reservaFilaFB = new ReservaFilaFB();
		reservaFilaFB.setEmpresaId(empresaFBId);
		reservaFilaFB.setPedVendaId(pedVendaFBId);
		reservaFilaFB.setPedVendaItemId(pedVendaItemFBId);
		reservaFilaFB.setProdutoId(produtoFBId);
		reservaFilaFB.setQuantidade(quantidade);
		reservaFilaFB.setRetEstqDisp(ReservaFilaFB.RETESTDISP_ATIVO);
		this.reservaFilaFBDAO.insert(reservaFilaFB);
	}

	public void insert(ReservaFilaFB reservaFilaFB) throws DAOException {
		this.reservaFilaFBDAO.insert(reservaFilaFB);
	}
	
	public void excluir(Integer pedVendaItemFBId) throws DAOException {
		this.reservaFilaFBDAO.excluir(pedVendaItemFBId);
	}

	public ReservaFilaFB carregar(Integer pedVendaItemFBId) {
		return this.reservaFilaFBDAO.carregar(pedVendaItemFBId);
	}
	
}
