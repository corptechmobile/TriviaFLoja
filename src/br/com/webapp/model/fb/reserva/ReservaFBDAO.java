package br.com.webapp.model.fb.reserva;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.web.util.DAOException;

public interface ReservaFBDAO {

	public Integer insert(ReservaFB reservaFB) throws DAOException;
	public void update(ReservaFB reservaFB) throws DAOException;
	public void excluir(Integer pedVendaItemFBId) throws DAOException;
	public ReservaFB carregar(Integer reservaId);
	public List<ReservaFB> listar(PedVendaFB pedVendaFB);
	public List<ReservaFB> listar(PedVendaItemFB pedVendaItemFB);
	public Double qtdReservadaControlaLote(Integer pedVendaItemId, Integer localidadeId, Integer produtoLoteId);
	public Double qtdReservadaNaoControlaLote(Integer pedVendaItemId, Integer localidadeId);
	
}
