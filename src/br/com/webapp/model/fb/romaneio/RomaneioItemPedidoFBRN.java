package br.com.webapp.model.fb.romaneio;

import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class RomaneioItemPedidoFBRN {

	private RomaneioItemPedidoFBDAO romaneioItemPedidoFBDAO;

	public RomaneioItemPedidoFBRN() {
		this.romaneioItemPedidoFBDAO = DAOFactoryFirebird.criarRomaneioItemPedidoFBDAO();
	}

	public List<RomaneioItemFB> listar(Integer romaneioFBId) {
		return this.romaneioItemPedidoFBDAO.listar(romaneioFBId);
	}
	
	private void delete(Integer Id) {
	this.romaneioItemPedidoFBDAO.delete(Id);	
	}	
	
	public void updateQuantidade(Integer id, Double quantidade) {
		this.romaneioItemPedidoFBDAO.updateQtd(id, quantidade);
	}

	public List<RomaneioItemDTOFB> listarParaAjuste(RomaneioItemFB itemSelecionado) {
		return this.romaneioItemPedidoFBDAO.listarParaAjuste(itemSelecionado);
	}

}
