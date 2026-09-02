package br.com.webapp.model.fb.planilhacegafirebird;

import br.com.webapp.web.util.RNException;

public interface PlanilhaCegaFirebirdDAO {
	public Integer gerarId() throws RNException;
	public Integer inserir(PlanilhaCegaFirebird planilhaCegaFirebird) throws RNException;
	public PlanilhaCegaFirebird verificarReAberturaConferencia(Integer idErp);
	public void rollBack();
	
}
