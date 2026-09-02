package br.com.webapp.model.fb.coletorpc;

import java.util.List;

import br.com.webapp.web.util.RNException;

public interface ColetorPCItemFBDAO {
	public List<ColetorPCItemFB> listar(Integer coletorPCFBId);
	
	public List<ColetorPCItemFB> listarLotes(Integer coletorPCFBId, Integer produtoId);

	public void inserir(ColetorPCItemFB planilhaCegaItemFirebird) throws RNException;

	public ColetorPCItemFB carregar(Integer coletorPCFBId, Integer produtoId);
	
	public void updateQtd(Integer Id, double quantidade);

	public void delete(Integer Id);

	public void excluirToPlanilhaCega(Integer planilhaCegaId);

	public List<ColetorPCItemFB> gerarItens(Integer planilhaCegaId);

	
}
