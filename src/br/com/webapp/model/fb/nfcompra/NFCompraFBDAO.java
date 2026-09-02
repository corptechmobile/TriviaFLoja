package br.com.webapp.model.fb.nfcompra;

import java.util.List;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;



public interface NFCompraFBDAO {
	public List<NFCompraItemFB> listar(NFCompraFB nfCompra);

	public List<NFCompraFB> listar(Integer fornecedorId, Integer empresaId);

	public List<NFCompraFB> listarPorPlanilhaCegaEFornecedor(ColetorPCFB coletorPCFB);

	public List<NFCompraFB> listarPorPlanilhaCegaId(Integer coletorPCFBId);

	public NFCompraFB salvar(NFCompraFB nfCompra);

	public List<NFCompraFBRN> listar();

	public void atualizarPlanilhaCega(Integer planilhaCegaId, Integer planilhaCegaIdErp) throws DAOException;
}






