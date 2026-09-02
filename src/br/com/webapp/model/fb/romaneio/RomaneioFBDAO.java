package br.com.webapp.model.fb.romaneio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;

public interface RomaneioFBDAO {
	public RomaneioFB carregar(Integer id);
	public void update(RomaneioFB romaneio) throws DAOException;	
	public List<RomaneioFB> listar(String numProcTranspFilter, String numRomaneioFilter, String usuarioFilter, String produtoFilter, Date data1Filter, Date data2Filter, List<Integer> statusFilter);
	public void rollBack();
	public void excluir(Integer romaneioFBId) throws DAOException;
	public void finalizar(RomaneioFB romaneioFB, UsuarioFB usuarioLogado);
	public void cancelar(RomaneioFB romaneio, UsuarioFB usuarioLogado) throws DAOException;
	public void atualizarStatus(Integer romaneioId, String statusEmConferencia) throws DAOException;
	public Integer integracao(Integer ordemCarregId) throws DAOException;
	public List<RomaneioIntegracaoDTOFB> listarParaIntegracao(Integer romaneioId);
}
