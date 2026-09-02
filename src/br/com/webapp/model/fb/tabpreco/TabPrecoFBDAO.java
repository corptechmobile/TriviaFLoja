package br.com.webapp.model.fb.tabpreco;

import java.util.List;

public interface TabPrecoFBDAO {

	public TabPrecoFB carregar(String id);
	public List<TabPrecoFB> listar();
	public TabPrecoFB carregar(Integer idEmpresa);
}
