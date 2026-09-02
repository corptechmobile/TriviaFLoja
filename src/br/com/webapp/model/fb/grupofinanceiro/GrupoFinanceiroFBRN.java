package br.com.webapp.model.fb.grupofinanceiro;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class GrupoFinanceiroFBRN {

	private GrupoFinanceiroFBDAO grupoFinanceiroFBDAO;
	
	public GrupoFinanceiroFBRN() {
		this.grupoFinanceiroFBDAO = DAOFactoryFirebird.criarGrupoFinanceiroFBDAO();
	}
	
	public GrupoFinanceiroFB carregar(String grupoFinanceiroId) {
		return this.grupoFinanceiroFBDAO.carregar(grupoFinanceiroId);
	}
	
	public List<GrupoFinanceiroFB> listar(String descricao) {
		return this.grupoFinanceiroFBDAO.listar(descricao);
	}

	public List<GrupoFinanceiroFB> listar() {
		return this.grupoFinanceiroFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<GrupoFinanceiroFB> gruposFinanceiros, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (gruposFinanceiros != null) {
			for (GrupoFinanceiroFB grupoFinanceiroFB : gruposFinanceiros) {
				item = new SelectItem(grupoFinanceiroFB, grupoFinanceiroFB.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		 
		return select;
		
	}
	
}
	

