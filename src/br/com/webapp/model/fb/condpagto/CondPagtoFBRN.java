package br.com.webapp.model.fb.condpagto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class CondPagtoFBRN {

	private CondPagtoFBDAO condPagtoFBDAO;
	
	public CondPagtoFBRN() {
		this.condPagtoFBDAO = DAOFactoryFirebird.criarCondPagtoFB();
	}
	
	public CondPagtoFB carregar(Integer id) {
		return this.condPagtoFBDAO.carregar(id);
	}
	
	public CondPagtoFB carregar(Integer condPagtoId, Integer empresaId, Integer clienteId) {
		return this.condPagtoFBDAO.carregar(condPagtoId, empresaId, clienteId);
	}
	
	public List<CondPagtoFB> listar(){
		return this.condPagtoFBDAO.listar();
	}
	
	public List<CondPagtoFB> listar(Integer formaPagtoFBId, Integer empresaId){
		return this.condPagtoFBDAO.listar(formaPagtoFBId, empresaId);
	}
	
	public List<CondPagtoFB> listar(Integer formaPagtoId, Integer empresaId, Integer clienteId) {
		return this.condPagtoFBDAO.listar(formaPagtoId, empresaId, clienteId);
	}
	
	
	public List<SelectItem> montaDadosSelect(List<CondPagtoFB> condPagtoes, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (condPagtoes != null) {
			for (CondPagtoFB rs : condPagtoes) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}

	public List<SelectItem> montaDadosSelect(List<CondPagtoFB> condPagtoes, String string, Double valPedido) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (condPagtoes != null) {
			for (CondPagtoFB rs : condPagtoes) {
				item = new SelectItem(rs, rs.getDescricao() + " - " + rs.getParcelas(valPedido));
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}

	


}
