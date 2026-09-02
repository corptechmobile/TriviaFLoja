package br.com.webapp.model.fb.nfcompra;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class NFCompraFBRN {
	private NFCompraFBDAO nfCompraFBDAO;
	public NFCompraFBRN(){
		this.nfCompraFBDAO = DAOFactoryFirebird.criarNFCompraFBDAO();
	}
	
	
	public List<NFCompraFBRN> listar(){
		return this.nfCompraFBDAO.listar();
	}
	
	public List<NFCompraFB> listarPorPlanilhaCegaEFornecedor(ColetorPCFB coletorPCFB) {
		return this.nfCompraFBDAO.listarPorPlanilhaCegaEFornecedor(coletorPCFB);
	}
	
	public List<NFCompraFB> listarPorPlanilhaCegaId(Integer coletorPCFBId) {
		return this.nfCompraFBDAO.listarPorPlanilhaCegaId(coletorPCFBId);
	}
	
	public NFCompraFB salvar(NFCompraFB nfCompra)throws DAOException{
		if(nfCompra.getDt_create()==null){
			nfCompra.setDt_create((java.sql.Date) new Date());
		}
		
		return this.nfCompraFBDAO.salvar(nfCompra);
	}
	
	public List<NFCompraItemFB> listar(NFCompraFB nfCompra){
		return this.nfCompraFBDAO.listar(nfCompra);
	}
	public List<NFCompraFB> listar(Integer fornecedorId, Integer empresaId) {	
		return this.nfCompraFBDAO.listar(fornecedorId, empresaId);
	}


	public void atualizarPlanilhaCega(Integer planilhaCegaId, Integer planilhaCegaIdErp) throws DAOException {
		this.nfCompraFBDAO.atualizarPlanilhaCega(planilhaCegaId, planilhaCegaIdErp);
	}

}


