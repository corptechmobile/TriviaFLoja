package br.com.webapp.model.fb.conferente;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ConferenteFBRN {

	private ConferenteFBDAO conferenteFBDAO;

	public ConferenteFBRN() {
		this.conferenteFBDAO = DAOFactoryFirebird.criarConferenteFBDAO();
	}

	public ConferenteFB carregar(Integer conferenteId) {
		return this.conferenteFBDAO.carregar(conferenteId);
	}

	public List<ConferenteFB> listar(UsuarioFB usuarioFB) {
		return this.conferenteFBDAO.listar(usuarioFB);
	}

	public List<SelectItem> montaDadosSelect(List<ConferenteFB> conferentes, String string) {

		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (conferentes != null) {
			for (ConferenteFB conferente : conferentes) {
				item = new SelectItem(conferente, conferente.getNomeFantasia());
				item.setEscape(false);
				select.add(item);
			}
		}

		return select;
	}

	public List<ConferenteFB> listar() {
		return this.conferenteFBDAO.listar();
	}

}
