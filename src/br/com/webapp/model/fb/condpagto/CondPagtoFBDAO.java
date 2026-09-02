package br.com.webapp.model.fb.condpagto;

import java.util.List;

public interface CondPagtoFBDAO {
	public CondPagtoFB carregar(Integer id);
	public CondPagtoFB carregar(Integer condPagtoId, Integer empresaId, Integer clienteId);
	public List<CondPagtoFB> listar();
	public List<CondPagtoFB> listar(Integer formaPagtoFBId, Integer empresaId);
	public List<CondPagtoFB> listar(Integer formaPagtoId, Integer empresaId, Integer clienteId);
}
