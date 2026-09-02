package br.com.webapp.model.fb.estado;

import java.util.List;

public interface EstadoFBDAO {

	public EstadoFB carregar(String id);
	public List<EstadoFB> listar();
	
}
