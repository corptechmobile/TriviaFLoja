package br.com.webapp.model.fb.enderecotipo;

import java.util.List;

public interface EnderecoTipoFBDAO {

	public EnderecoTipoFB carregar(Integer id);
	public List<EnderecoTipoFB> listar();
	
}
