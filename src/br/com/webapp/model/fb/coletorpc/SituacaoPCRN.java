package br.com.webapp.model.fb.coletorpc;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


public class SituacaoPCRN {
	public static SituacaoPC getEmDigitacao(){
		return new SituacaoPC(ColetorPCFB.STATUS_EM_ABERTO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.situacao.emdigitacao.planilhacega"));
	}

	public static SituacaoPC getLiberadoParaConferencia(){
		return new SituacaoPC(ColetorPCFB.STATUS_LIBERADO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.situacao.liberado.planilhacega"));
	}


	public static SituacaoPC getEmConferencia(){
		return new SituacaoPC(ColetorPCFB.STATUS_EM_CONFERENCIA, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.situacao.emconferencia.planilhacega"));
	}

	public static SituacaoPC getFinalizada(){
		return new SituacaoPC(ColetorPCFB.STATUS_FINALIZADO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.situacao.finalizada.planilhacega"));
	}

	public static List<SelectItem> montaDadosSelectItens(){
		List<SelectItem> selectItens = new ArrayList<>();
		selectItens.add(new SelectItem(getEmDigitacao()));
		selectItens.add(new SelectItem(getEmConferencia()));
		selectItens.add(new SelectItem(getFinalizada()));
		return selectItens;
	}

	public static SituacaoPC carregar(String id) {
		SituacaoPC model = null;
		if(id.equals(ColetorPCFB.STATUS_EM_ABERTO)){
			model = getEmDigitacao();
		}else if(id.equals(ColetorPCFB.STATUS_LIBERADO)){
			model = getLiberadoParaConferencia();
		}else if(id.equals(ColetorPCFB.STATUS_EM_CONFERENCIA)){
			model = getEmConferencia();
		}else if(id.equals(ColetorPCFB.STATUS_FINALIZADO)){
			model = getFinalizada();
		}
		return model;
	}
}
