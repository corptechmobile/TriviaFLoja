package br.com.webapp.model.fb.grupofinanceiro;

import java.util.List;

public interface GrupoFinanceiroFBDAO {

	public GrupoFinanceiroFB carregar(String Id);
	public List<GrupoFinanceiroFB> listar(String descricao);
	public List<GrupoFinanceiroFB> listar();
}
