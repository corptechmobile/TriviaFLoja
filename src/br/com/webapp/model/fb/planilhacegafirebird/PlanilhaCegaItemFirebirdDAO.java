package br.com.webapp.model.fb.planilhacegafirebird;

import br.com.webapp.web.util.RNException;

public interface PlanilhaCegaItemFirebirdDAO {
	public Integer inserir(PlanilhaCegaItemFirebird planilhaCegaItemFirebird) throws RNException;

	public void inserirLote(PlanilhaCegaItemFirebird planilhaCegaItemFirebird) throws RNException;
}
