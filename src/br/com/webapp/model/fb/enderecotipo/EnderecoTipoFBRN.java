package br.com.webapp.model.fb.enderecotipo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class EnderecoTipoFBRN {

	private EnderecoTipoFBDAO enderecoTipoFBDAO;
	
	public EnderecoTipoFBRN() {
		this.enderecoTipoFBDAO = DAOFactoryFirebird.criarEnderecoFBDAO();
	}
	
	public EnderecoTipoFB carregar(Integer id) {
		return this.enderecoTipoFBDAO.carregar(id);
	}
	
	public List<EnderecoTipoFB> listar(){
		return this.enderecoTipoFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelect(List<EnderecoTipoFB> enderecoTipos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (enderecoTipos != null) {
			for (EnderecoTipoFB enderecoTipo : enderecoTipos) {
				item = new SelectItem(enderecoTipo, enderecoTipo.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}
}
