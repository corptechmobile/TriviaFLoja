package br.com.webapp.model.fb.movfisctipo;

import java.util.List;

public interface MovFiscTipoFBDAO {

	public MovFiscTipoFB carregar(Integer id);
	public MovFiscTipoFB carregarDefault();
	public List<MovFiscTipoFB> listar();
	public List<MovFiscTipoFB> listarTransfOutras();
	public List<MovFiscTipoFB> listarPedVenda();
}
