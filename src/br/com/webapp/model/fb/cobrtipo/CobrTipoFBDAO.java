package br.com.webapp.model.fb.cobrtipo;

import java.util.List;

public interface CobrTipoFBDAO {

	public CobrTipoFB carregar(Integer id);
	public List<CobrTipoFB> listar();
	
}
