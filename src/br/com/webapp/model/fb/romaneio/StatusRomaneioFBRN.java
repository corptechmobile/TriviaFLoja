package br.com.webapp.model.fb.romaneio;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


public class StatusRomaneioFBRN {
	public static StatusRomaneioFB getEmAberto(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_EM_ABERTO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.emaberto.romaneio"));
	}

	public static StatusRomaneioFB getEmConferencia(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_EM_CONFERENCIA, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.emconferencia.romaneio"));
	}

	public static StatusRomaneioFB getConferidoComCorte(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_CONFERIDO_COMCORTE, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.conferidocomcorte.romaneio"));
	}

	public static StatusRomaneioFB getConferido(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_CONFERIDO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.conferido.romaneio"));
	}

	public static StatusRomaneioFB getFinalizado(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_FINALIZADO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.finalizado.romaneio"));
	}

	public static StatusRomaneioFB getCancelado(){
		return new StatusRomaneioFB(RomaneioFB.STATUS_CANCELADO, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.status.cancelado.romaneio"));
	}
	

	public static List<SelectItem> montaDadosSelectItens(){
		List<SelectItem> selectItens = new ArrayList<>();
		selectItens.add(new SelectItem(getEmAberto()));
		selectItens.add(new SelectItem(getEmConferencia()));
		selectItens.add(new SelectItem(getConferidoComCorte()));
		selectItens.add(new SelectItem(getConferido()));
		selectItens.add(new SelectItem(getFinalizado()));
		selectItens.add(new SelectItem(getCancelado()));
		return selectItens;
	}

	public static StatusRomaneioFB carregar(String id) {
		StatusRomaneioFB model = null;
		if(id.equals(RomaneioFB.STATUS_EM_ABERTO)){
			model = getEmAberto();
		}else if(id.equals(RomaneioFB.STATUS_EM_CONFERENCIA)){
			model = getEmConferencia();
		}else if(id.equals(RomaneioFB.STATUS_CONFERIDO_COMCORTE)){
			model = getConferidoComCorte();
		}else if(id.equals(RomaneioFB.STATUS_CONFERIDO)){
			model = getConferido();
		}else if(id.equals(RomaneioFB.STATUS_FINALIZADO)){
			model = getFinalizado();
		}else if(id.equals(RomaneioFB.STATUS_CANCELADO)){
			model = getCancelado();
		}
		return model;
	}

}
