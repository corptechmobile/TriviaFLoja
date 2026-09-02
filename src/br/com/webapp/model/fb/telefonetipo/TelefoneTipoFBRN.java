package br.com.webapp.model.fb.telefonetipo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class TelefoneTipoFBRN {

	private TelefoneTipoFBDAO telefoneTipoFBDAO;
	
	public TelefoneTipoFBRN() {
		this.telefoneTipoFBDAO = DAOFactoryFirebird.criarTelefoneTipoDAO();
	}
	
	public TelefoneTipoFB carregar(Integer id) {
		return this.telefoneTipoFBDAO.carregar(id);
	}
	
	public List<TelefoneTipoFB> listar(){
		return this.telefoneTipoFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelect(List<TelefoneTipoFB> telefoneTipos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (telefoneTipos != null) {
			for (TelefoneTipoFB telefoneTipo : telefoneTipos) {
				item = new SelectItem(telefoneTipo, telefoneTipo.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}
	
}
