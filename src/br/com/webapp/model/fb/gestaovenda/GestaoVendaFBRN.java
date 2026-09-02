package br.com.webapp.model.fb.gestaovenda;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class GestaoVendaFBRN {

	private GestaoVendaFBDAO gestaoVendaFBDAO;
	
	public GestaoVendaFBRN() {
		gestaoVendaFBDAO = DAOFactoryFirebird.criarGestaoVendaFBDAO();
	}
	
	public GestaoVendaFB carregar(Integer idGestaoVenda) {
		return this.gestaoVendaFBDAO.carregar(idGestaoVenda);
	}
	
	public List<GestaoVendaFB> listar(){
		return this.gestaoVendaFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelected(List<GestaoVendaFB> lista, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (lista != null) {
			for (GestaoVendaFB rs : lista) {
				String nome = montaArvore(rs);
				item = new SelectItem(rs, nome);
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}
	
	private String montaArvore(GestaoVendaFB gestaoVendaFB) {
		String nome = gestaoVendaFB.getNome();
		int tamCodEDTDivididoPor3 = gestaoVendaFB.getCodEdt().length()/3;
		String str = "";
		for(int i = 0; i < tamCodEDTDivididoPor3; i++) {
			str = str.concat(" ");
		}
		str = str.concat(nome);
		return str;
	}
}
