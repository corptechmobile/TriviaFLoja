package br.com.webapp.model.fb.conferente;

import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public interface ConferenteFBDAO {
	public ConferenteFB carregar(Integer conferenteId);
	public List<ConferenteFB> listar(UsuarioFB usuarioFB);
	public List<ConferenteFB> listar();
}
