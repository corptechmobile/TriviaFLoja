package br.com.webapp.model.fb.parametro;

import java.util.List;

public interface ParametroFBDAO {
	public ParametroFB carregar(String nome);
	public List<ParametroFB> listar();
}
