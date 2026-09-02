package br.com.webapp.model.fb.pedvendaitem.dto;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class PedVendaItemFBDTORN {
	
	private PedVendaItemFBDTODAO pedVendaItemFBDTODAO;

	public PedVendaItemFBDTORN() {
		this.pedVendaItemFBDTODAO = DAOFactoryFirebird.criarPedVendaItemFBDTODAO();
	}
	
	public PedVendaItemFBDTO carregar(Integer pedVendaItemId){
		return this.pedVendaItemFBDTODAO.carregar(pedVendaItemId);
	}
	
	public List<PedVendaItemFBDTO> listar(Integer pedVendaId){
		return this.pedVendaItemFBDTODAO.listar(pedVendaId);
	}
}
