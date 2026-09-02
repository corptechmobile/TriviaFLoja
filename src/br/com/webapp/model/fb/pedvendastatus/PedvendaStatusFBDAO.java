package br.com.webapp.model.fb.pedvendastatus;

import java.util.List;

public interface PedvendaStatusFBDAO {
	public PedVendaStatusFB carregar(Integer id);
	public List<PedVendaStatusFB> listar();
}
