package br.com.webapp.model.fb.tabpreco;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class TabPrecoFBRN {

	private TabPrecoFBDAO tabPrecoFBDAO;

	public TabPrecoFBRN() {
		this.tabPrecoFBDAO = DAOFactoryFirebird.criarTabPrecoFBDAO();
	}
	
	public TabPrecoFB carregar(String id) {
		return this.tabPrecoFBDAO.carregar(id);
	}
	
	public TabPrecoFB carregar(Integer idEmpresa) {
		return this.tabPrecoFBDAO.carregar(idEmpresa);
	}
	
	public List<TabPrecoFB> listar(){
		return this.tabPrecoFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<TabPrecoFB> precos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (precos != null) {
			for (TabPrecoFB preco : precos) {
				item = new SelectItem(preco, preco.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}

	
	
	
}
