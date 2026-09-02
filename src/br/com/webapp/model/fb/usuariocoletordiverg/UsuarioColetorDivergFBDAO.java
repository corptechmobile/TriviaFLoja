package br.com.webapp.model.fb.usuariocoletordiverg;

import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.web.util.DAOException;

public interface UsuarioColetorDivergFBDAO {

	public void excluir(UsuarioColetorDivergFB usuarioDivergenciFB);
	
	public void excluir(Integer usuarioId);

	public UsuarioColetorDivergFB carregar();

	public UsuarioColetorDivergFB carregar(Integer usuarioId, Integer divergenciaId);
	
	List<UsuarioColetorDivergFB> listar();

	public UsuarioColetorDivergFB salvar(UsuarioColetorDivergFB usuarioColetorDivergFB) throws DAOException;

	public void salvar(Integer usuarioId, Integer divergenciaId) throws DAOException;

	
}
