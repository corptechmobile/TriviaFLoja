package br.com.webapp.model.fb.alcadacondpagto;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class AlcadaCondPagtoFBRN {
	
	private AlcadaCondPagtoFBDAO alcadaCondPagtoFBDAO;
	
	public AlcadaCondPagtoFBRN(){
		this.alcadaCondPagtoFBDAO = DAOFactoryFirebird.criarAlcadaCondPagtoFBDAO();
	}
	
	public AlcadaCondPagtoFB carregar(UsuarioFB usuario, Integer condPagtoId) {
		Integer gestaoVendaId = null;
		
		if(usuario.getGestaoVendaId()!=null) {
			gestaoVendaId = usuario.getGestaoVendaId();
		}else if(usuario.getVendedor()!=null) {
			gestaoVendaId = usuario.getVendedor().getGestaoVendaId();
		}
		
		AlcadaCondPagtoFB alcada = null;
		if(gestaoVendaId == null) {
			alcada = new AlcadaCondPagtoFB();
			alcada.setAlcada(0.0);
			
			return alcada;
		}
		
		alcada = this.alcadaCondPagtoFBDAO.carregar(gestaoVendaId, condPagtoId);
		if(alcada==null) {
			alcada = new AlcadaCondPagtoFB();
			alcada.setAlcada(0.0);
		}
		return alcada;
	}
	
}
