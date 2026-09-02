package br.com.webapp.model.fb.pedvenda;

import java.util.List;

import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.web.util.DAOException;

public interface PedVendaItemFBDAO {
	public PedVendaItemFB carregar(Integer id);
	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId);
	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId, Integer pedVendaCompostoId);
	public Integer insert(PedVendaItemFB pedVendaItem) throws DAOException;
	public Integer update(PedVendaItemFB pedVendaItem) throws DAOException;
	public void updatePreco(PedVendaItemFBDTO pedVendaItemFBDTO) throws DAOException;
	public void excluir(PedVendaItemFB pedVendaItemFB, Integer usuarioId) throws DAOException;
	public List<PedVendaItemFB> listar(PedVendaFB pedVenda);
	public List<PedVendaItemFB> listarProdCompostos(Integer pedVendaId, Integer pedVendaCompostoId);
}
