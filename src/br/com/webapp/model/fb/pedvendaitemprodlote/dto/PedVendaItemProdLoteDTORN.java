package br.com.webapp.model.fb.pedvendaitemprodlote.dto;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class PedVendaItemProdLoteDTORN {
	
	private PedVendaItemProdLoteDTODAO pedVendaItemProdLoteDTODAO;

	public PedVendaItemProdLoteDTORN() {
		this.pedVendaItemProdLoteDTODAO = DAOFactoryFirebird.criarPedVendaItemProdLoteDTODAO();
	}
	
	public List<PedVendaItemProdLoteDTO> lista(Integer pedVendaItemFBId){
		return this.pedVendaItemProdLoteDTODAO.listar(pedVendaItemFBId);
	}

}
