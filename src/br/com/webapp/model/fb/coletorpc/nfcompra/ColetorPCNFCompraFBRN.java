package br.com.webapp.model.fb.coletorpc.nfcompra;
import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ColetorPCNFCompraFBRN {
	
	
	private ColetorPCNFCompraFBDAO  coletorPCFBNFDAO;

	public ColetorPCNFCompraFBRN () {
		this.coletorPCFBNFDAO = DAOFactoryFirebird.criarNFCompraFBNFDAO();
	}

	public void insert(ColetorPCNFCompraFB coletorPCFBNF) throws DAOException {
		this.coletorPCFBNFDAO.insert(coletorPCFBNF);
	}
	
	public List<ColetorPCNFCompraFB> carregar(Integer coletorFBId) {
		return this.coletorPCFBNFDAO.carregar(coletorFBId);
	}
	
	
	public ColetorPCNFCompraFB carregar(Integer coletorFBId, Integer nfCompraId) {
		return this.coletorPCFBNFDAO.carregar(coletorFBId, nfCompraId);
	}

	public void delete(Integer coletorPCId, Integer nfCompraId) throws DAOException {
	    this.coletorPCFBNFDAO.delete(coletorPCId, nfCompraId);
	}

	public void delete(ColetorPCNFCompraFB coletorPCNFCompraFB) throws DAOException{
		this.coletorPCFBNFDAO.delete(coletorPCNFCompraFB);
	}

	public List<ColetorPCNFCompraFB> listar(Integer coletorFBId) {
		return this.coletorPCFBNFDAO.listar(coletorFBId);
	}  
}
