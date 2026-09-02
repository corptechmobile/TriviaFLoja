package br.com.webapp.model.fb.reservalote;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.web.util.DAOException;

public interface ReservaLoteFBDAO {

	public void insert(ReservaLoteFB reservaLoteFB) throws DAOException;
	public void update(ReservaLoteFB reservaLoteFB) throws DAOException;
	public ReservaLoteFB carregar(Integer id);
	public List<ReservaLoteFB> listar();
	public List<ReservaLoteFB> listar(PedVendaItemFB pedVendaItem);
	
}
