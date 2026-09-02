package br.com.webapp.model.fb.fornecedor;

import java.util.List;

public interface FornecedorFBDAO {
	public FornecedorFB carregar(Integer fornecedorId);
	public List<FornecedorFB> listarParaPlanilhaCega(Integer empresaId, String descricaoFilter);
	public List<FornecedorFB> listar(String descricaoFilter, Integer empresaId);
	public List<FornecedorFB> listar(String descricao);
}
