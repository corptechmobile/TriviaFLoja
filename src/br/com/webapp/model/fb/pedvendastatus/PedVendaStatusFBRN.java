package br.com.webapp.model.fb.pedvendastatus;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class PedVendaStatusFBRN {
	
	private PedvendaStatusFBDAO pedvendaStatusFBDAO;
	
	public PedVendaStatusFBRN() {
		this.pedvendaStatusFBDAO = DAOFactoryFirebird.criarPedVendaStatusFBDAO();
	}
	
	public PedVendaStatusFB carregar(Integer id) {
		return this.pedvendaStatusFBDAO.carregar(id);
	}
	
	public List<PedVendaStatusFB> listar(){
		return this.pedvendaStatusFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<PedVendaStatusFB> status, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (status != null) {
			for (PedVendaStatusFB rs : status) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	} 
}
