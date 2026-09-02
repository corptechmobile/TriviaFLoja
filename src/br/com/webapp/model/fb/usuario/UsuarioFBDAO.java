package br.com.webapp.model.fb.usuario;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface UsuarioFBDAO {
	public UsuarioFB carregar(String login);
	public UsuarioFB carregar(Integer usuarioId);
	public List<UsuarioFB> listar(String descricaoFilter, Boolean situacaoFilter);
	public void salvarNovaSenha(Integer usuarioId, String senhaNova) throws DAOException;
}
