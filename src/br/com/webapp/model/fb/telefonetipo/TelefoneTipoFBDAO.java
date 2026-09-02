package br.com.webapp.model.fb.telefonetipo;

import java.util.List;

public interface TelefoneTipoFBDAO {

	public TelefoneTipoFB carregar(Integer id);
	public List<TelefoneTipoFB> listar();
}
