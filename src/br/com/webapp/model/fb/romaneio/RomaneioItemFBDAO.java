package br.com.webapp.model.fb.romaneio;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;

public interface RomaneioItemFBDAO {
	public RomaneioItemFB carregar(Integer romaneioFBId, Integer produtoId);
	public List<RomaneioItemFB> listar(Integer romaneioFBId);
	public List<RomaneioItemDTOFB> listarParaAjuste(RomaneioItemFB itemSelecionado);
	public void updateQtd(Integer Id, double quantidade);
	public void delete(Integer Id);
	public void atualizarQtdConferida(Integer romaneioId, Integer produtoId, Double qtd) throws DAOException;
	public void integracao(Integer seqId, Integer romaneioId, Integer ordemcarregId) throws DAOException;
}
