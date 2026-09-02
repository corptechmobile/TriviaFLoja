package br.com.webapp.model.fb.municipio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class MunicipioFBRN {

	private MunicipioFBDAO municipioFBDAO;
	
	public MunicipioFBRN() {
		municipioFBDAO = DAOFactoryFirebird.criarMunicipioFB();
	}
	
	public MunicipioFB carregar(Integer id) {
		return this.municipioFBDAO.carregar(id);
	}
	
	public List<MunicipioFB> listar(){
		return this.municipioFBDAO.listar();
	}

	public List<MunicipioFB> listar(String estadoId) {
		return this.municipioFBDAO.listar(estadoId);
	}

	public List<SelectItem> montaDadosSelect(List<MunicipioFB> municipios, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (municipios != null) {
			for (MunicipioFB municipio : municipios) {
				item = new SelectItem(municipio, municipio.getNome());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}

	public MunicipioFB carregar(String localidade, String uf) {
		return this.municipioFBDAO.listar(localidade, uf);
	}
}
