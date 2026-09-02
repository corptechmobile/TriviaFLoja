package br.com.webapp.model.fb.gestaovendamob;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class GestaoVendaMobFBRN {

	private GestaoVendaMobFBDAO gestaoVendaFBDAO;
	
	public GestaoVendaMobFBRN(){
		gestaoVendaFBDAO = DAOFactoryFirebird.criarGestaoVendaMobFBDAO();
	}
	
	public GestaoVendaMobFB carregar(Integer idGestaoVendaMob) {
		return this.gestaoVendaFBDAO.carregar(idGestaoVendaMob);
	}
	
	public List<GestaoVendaMobFB> listar(){
		return this.gestaoVendaFBDAO.listar();
	}

	public List<SelectItem> montaDadosSelected(List<GestaoVendaMobFB> lista, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (lista != null) {
			for (GestaoVendaMobFB rs : lista) {
				String nome = montaArvore(rs);
				item = new SelectItem(rs, nome);
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}
	
	private String montaArvore(GestaoVendaMobFB gestaoVendaFB) {
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
