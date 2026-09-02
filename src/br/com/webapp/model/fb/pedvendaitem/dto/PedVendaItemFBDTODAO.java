package br.com.webapp.model.fb.pedvendaitem.dto;

import java.util.List;

public interface PedVendaItemFBDTODAO {
	public PedVendaItemFBDTO carregar(Integer pedVendaItemId);
	public List<PedVendaItemFBDTO> listar(Integer pedVendaId);
}
