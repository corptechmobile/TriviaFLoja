package br.com.webapp.model.fb.romaneio;

import java.util.List;


public interface RomaneioItemPedidoFBDAO {
	public RomaneioItemFB carregar(Integer romaneioFBId, Integer produtoId);
	public List<RomaneioItemFB> listar(Integer romaneioFBId);
	public List<RomaneioItemDTOFB> listarParaAjuste(RomaneioItemFB itemSelecionado);
	public void updateQtd(Integer Id, double quantidade);
	public void delete(Integer Id);
}
