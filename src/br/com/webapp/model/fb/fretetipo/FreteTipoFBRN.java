package br.com.webapp.model.fb.fretetipo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class FreteTipoFBRN {

	private FreteTipoFBDAO freteTipoFBDAO;
	
	public FreteTipoFBRN() {
		this.freteTipoFBDAO = DAOFactoryFirebird.criarFreteTipoDAO();
	}
	
	public FreteTipoFB carregar(Integer id) {
		return this.freteTipoFBDAO.carregar(id);
	}
	
	public List<FreteTipoFB> listar(){
		return this.freteTipoFBDAO.listar();
	}
	
	public List<FreteTipoFB> listar(Integer formaPagtoId){
		return this.freteTipoFBDAO.listar(formaPagtoId);
	}
	
	public List<SelectItem> montaDadosSelect(List<FreteTipoFB> freteTipos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (freteTipos != null) {
			for (FreteTipoFB rs : freteTipos) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}
}
