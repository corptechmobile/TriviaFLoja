package br.com.webapp.model.fb.pedvendacomposto;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;

public interface PedVendaCompostoFBDAO {
	public PedVendaCompostoFB carregar(Integer pedVendaId, Integer prodCompostoId);
	public Integer insert(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException;
	public void updateQuantidade(PedVendaCompostoFB pedVendaCompostoFB) throws DAOException;
	public void excluir(Integer pedVendaCompostoId) throws DAOException;
	public List<PedVendaCompostoFB> listar(PedVendaFB pedVendaFB);
}
