package br.com.webapp.model.fb.romaneio;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface RomaneioContagemFBDAO {

	public RomaneioContagemFB carregar(Integer id);
	public List<RomaneioContagemFB> listar(Integer coletorPlanilhaCegaId, boolean excluido);
	public List<RomaneioContagemFB> listarLeiturasProduto(Integer coletorPlanilhaCegaId, Integer produtoId);
	public void excluirLeitura(RomaneioContagemFB RomaneioContagemFB) throws DAOException;
	public void excluirTodasLeituras(Integer romaneioId, Integer produtoId) throws DAOException;
	public void rollBack();
	
}
