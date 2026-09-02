package br.com.webapp.model.menu;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public interface MenuHomeDAO {
	public MenuAcesso carregar(UsuarioFB usuario);
	public MenuHome salvar(MenuHome menuHome);
}
