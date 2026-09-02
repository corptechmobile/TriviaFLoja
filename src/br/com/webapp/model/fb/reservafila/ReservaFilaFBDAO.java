package br.com.webapp.model.fb.reservafila;

import br.com.webapp.web.util.DAOException;

public interface ReservaFilaFBDAO {
	public void insert(ReservaFilaFB reservaFilaFB) throws DAOException;
	public void excluir(Integer pedVendaItemFBId) throws DAOException;
	public ReservaFilaFB carregar(Integer pedVendaItemFBId);
}
