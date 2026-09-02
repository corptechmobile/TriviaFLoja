package br.com.coletor;

import br.com.webapp.model.fb.coletorpc.ColetorPCFBRN;
import br.com.webapp.model.fb.planilhacegafirebird.PlanilhaCegaFirebirdRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.RNException;

public class PlanilhaCegaIntegracao {
	
	public Integer finalizarPlanilhaCega(Integer planilhaCegaId, Integer empresaId) throws Exception {
		PlanilhaCegaFirebirdRN planilhaCegaFirebirdRN = new PlanilhaCegaFirebirdRN();
		return planilhaCegaFirebirdRN.integracao(planilhaCegaId, empresaId);
	}
	
	public void reAbrirConferencia(Integer idErp, UsuarioFB usuario) throws RNException {
		if(idErp != null) {
			PlanilhaCegaFirebirdRN planilhaCegaFirebirdRN = new PlanilhaCegaFirebirdRN();
			planilhaCegaFirebirdRN.reAbrirConferencia(idErp);
		}
	}

	public void rollBack() {
		ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
		coletorPCFBRN.rollBack();
	}

	public PlanilhaCegaIntegracao() {
	}

}
