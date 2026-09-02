package br.com.webapp.model.fb.municipio;

import java.util.List;

public interface MunicipioFBDAO {

	public MunicipioFB carregar(Integer id);
	public List<MunicipioFB> listar();
	public List<MunicipioFB> listar(String estadoId);
	public MunicipioFB listar(String localidade, String uf);
	
}
