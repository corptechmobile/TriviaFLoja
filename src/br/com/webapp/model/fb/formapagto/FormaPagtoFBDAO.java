package br.com.webapp.model.fb.formapagto;

import java.util.List;

public interface FormaPagtoFBDAO {

	public FormaPagtoFB carregar(Integer id);
	public List<FormaPagtoFB> listar();
	public List<FormaPagtoFB> listarFormaCond(Integer empresaId);	
	public List<FormaPagtoFB> listarPorFreteTipo(Integer freteTipoId);
	public List<FormaPagtoFB> listarFormaCondProd(Integer empresaId);

}
