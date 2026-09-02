package br.com.webapp.model.fb.gestaovenda;

import java.util.List;

public interface GestaoVendaFBDAO {

	public GestaoVendaFB carregar(Integer idGestaoVenda);
	public List<GestaoVendaFB> listar();
}
