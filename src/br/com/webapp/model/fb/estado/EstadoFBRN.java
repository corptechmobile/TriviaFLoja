package br.com.webapp.model.fb.estado;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class EstadoFBRN {

	private EstadoFBDAO estadoFBDAO;
	
	public EstadoFBRN() {
		this.estadoFBDAO = DAOFactoryFirebird.criarEstadoFB();
	}
	
	public EstadoFB carregar(String id) {
		return estadoFBDAO.carregar(id);
	}
	
	public List<EstadoFB> listar(){
		return estadoFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelect(List<EstadoFB> estados, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (estados != null) {
			for (EstadoFB estado : estados) {
				item = new SelectItem(estado, estado.getNome());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}
}
