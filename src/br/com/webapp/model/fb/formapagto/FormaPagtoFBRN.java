package br.com.webapp.model.fb.formapagto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class FormaPagtoFBRN {

	private FormaPagtoFBDAO formaPagtoFBDAO;
	
	public FormaPagtoFBRN () {
		formaPagtoFBDAO = DAOFactoryFirebird.criarFormaPagtoFBDAO();
	}
	
	public FormaPagtoFB carregar(Integer id) {
		return this.formaPagtoFBDAO.carregar(id);
	}
	
	public List<FormaPagtoFB> listar(){
		return this.formaPagtoFBDAO.listar();
	}
	
	public List<FormaPagtoFB> listarFormaCond(Integer empresaId) {
		return this.formaPagtoFBDAO.listarFormaCond(empresaId);
	}
	
	public List<FormaPagtoFB> listarFormaCondProd(Integer empresaId) {
		return this.formaPagtoFBDAO.listarFormaCondProd(empresaId);
	}
	
	public List<FormaPagtoFB> listarPorFreteTipo(Integer freteTipoId) {
		return this.formaPagtoFBDAO.listarPorFreteTipo(freteTipoId);
	}
	
	public List<SelectItem> montaDadosSelect(List<FormaPagtoFB> formaPagtos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (formaPagtos != null) {
			for (FormaPagtoFB rs : formaPagtos) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}

	

	

	

}
