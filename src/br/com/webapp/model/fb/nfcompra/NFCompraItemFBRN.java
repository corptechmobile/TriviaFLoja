package br.com.webapp.model.fb.nfcompra;

import java.util.List;

import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.web.util.DAOFactoryFirebird;


public class NFCompraItemFBRN {
private NFCompraItemFBDAO nfCompraItemFBDAO;
	
	public NFCompraItemFBRN(){
		this.nfCompraItemFBDAO = DAOFactoryFirebird.criarNFCompraItemFB();
	}

	public List<NFCompraItemFB> listar(Integer version) {
		return this.nfCompraItemFBDAO.listar(version);
	}

	public List<NFCompraItemFB> listar(NFCompraFB nf) {
		
		return this.nfCompraItemFBDAO.listar(nf);
	}
}
