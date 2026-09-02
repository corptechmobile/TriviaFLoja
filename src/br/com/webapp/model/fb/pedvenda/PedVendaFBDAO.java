package br.com.webapp.model.fb.pedvenda;

import java.util.Date;
import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface PedVendaFBDAO {
	public PedVendaFB carregar(Integer id);
	public Integer insert(PedVendaFB pedVenda) throws DAOException;
	public void update(PedVendaFB pedVenda) throws DAOException;
	public void updateEmDigitacaoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException;
	public void updateAguardPagtoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException;
	public void updateLiberarPedVenda(Integer pedVendaFBId, Integer pedVendaStatusFBId, Date dtEfetivacao, Integer usuarioId) throws DAOException;
	public void updateNaoLiberarPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException;
	public void excluir(Integer pedVendaFBId, Integer usuarioId) throws DAOException;
	public List<PedVendaFB> listar(Integer vendedorId, Integer clienteId);
	public void rollback();
}
