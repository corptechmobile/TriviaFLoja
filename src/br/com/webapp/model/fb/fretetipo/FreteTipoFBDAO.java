package br.com.webapp.model.fb.fretetipo;

import java.util.List;

public interface FreteTipoFBDAO {
	public FreteTipoFB carregar(Integer id);
	public List<FreteTipoFB> listar();
	public List<FreteTipoFB> listar(Integer formaPagtoId);
}
