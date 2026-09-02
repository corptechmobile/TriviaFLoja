package br.com.webapp.model.menu;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryPostGres;

public class MenuHomeRN {

	private MenuHomeDAO menuHomeDAO;
	
	public MenuHomeRN(){
		this.menuHomeDAO = DAOFactoryPostGres.criarMenuHomeDAO();
	}
	
	public MenuAcesso carregar(UsuarioFB usuario) {
		return this.menuHomeDAO.carregar(usuario);
	}

	public MenuHome salvar(MenuHome menuHome) {
		return this.menuHomeDAO.salvar(menuHome);
	}

}
