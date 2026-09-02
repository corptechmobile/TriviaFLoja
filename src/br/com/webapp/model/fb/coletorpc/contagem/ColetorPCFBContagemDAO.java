package br.com.webapp.model.fb.coletorpc.contagem;

import java.util.List;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;

public interface ColetorPCFBContagemDAO {

	public ColetorPCFB carregar(Integer id);
	public List<ColetorPCFBContagem> listar(Integer coletorPlanilhaCegaId, boolean excluido);
	public Integer insert(EspelhoColetorPlanilhaCegaContagem espelho) throws DAOException;
	public void update(ColetorPCFBContagem coletorPCFBContagem) throws DAOException;
	public List<ColetorPCFBContagem> listarLeiturasProduto(Integer coletorPlanilhaCegaId, Integer produtoId);
	public void excluirLeitura(ColetorPCFBContagem coletorPCFBContagem) throws DAOException;
	public void excluirTodasLeituras(Integer coletorId) throws DAOException;
	
}
