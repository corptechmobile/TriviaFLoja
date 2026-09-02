package br.com.webapp.model.fb.pais;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class PaisFBRN {

	private PaisFBDAO paisFBDAO;
	
	public PaisFBRN() {
		paisFBDAO = DAOFactoryFirebird.criarPaisFBDAO();
	}
	
	public PaisFB carregar(String id) {
		return this.paisFBDAO.carregar(id);
	}
	
	public List<PaisFB> listar(){
		return this.paisFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelect(List<PaisFB> paises, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (paises != null) {
			for (PaisFB pais : paises) {
				item = new SelectItem(pais, pais.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}
}
