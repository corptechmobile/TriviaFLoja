package br.com.webapp.model.fb.coletorpc;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ColetorDivergenciaFBDAO {

	public ColetorDivergenciaFB carregar();

	public ColetorDivergenciaFB salvar(ColetorDivergenciaFB coletorPCDivergenciaFB);

	public void excluir(ColetorDivergenciaFB coletorPCDivergenciaFB);

	public void excluir(Integer coletorId) throws DAOException;

	public List<ColetorDivergenciaFB> listar(Integer divergenciaId);

	public List<ColetorDivergenciaFB> listar();

	public List<ColetorDivergenciaFB> listarPorUsuario(Integer usuarioId);

	public void rollBack();

		
}
