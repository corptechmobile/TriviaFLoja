package br.com.webapp.model.fb.gestaovendamob;

import java.util.List;

public interface GestaoVendaMobFBDAO {

	public GestaoVendaMobFB carregar(Integer idGestaoVendaMob);
	public List<GestaoVendaMobFB> listar();
}
