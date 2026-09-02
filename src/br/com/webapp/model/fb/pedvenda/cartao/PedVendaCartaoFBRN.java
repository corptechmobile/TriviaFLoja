package br.com.webapp.model.fb.pedvenda.cartao;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class PedVendaCartaoFBRN {
	
	private PedVendaCartaoFBDAO pedVendaCartaoFBDAO;
	
	public PedVendaCartaoFBRN() {
		pedVendaCartaoFBDAO = DAOFactoryFirebird.criarPedVendaCartaoFBDAO();
	}
	
	public List<PedVendaCartaoFB> listar(Integer pedVendaId){
		return pedVendaCartaoFBDAO.listar(pedVendaId);
	}
}
