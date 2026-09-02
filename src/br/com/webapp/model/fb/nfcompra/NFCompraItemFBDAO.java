package br.com.webapp.model.fb.nfcompra;

import java.util.List;

import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;


public interface NFCompraItemFBDAO {
	public List<NFCompraItemFB> listar(Integer version);

	public List<NFCompraItemFB> listar(NFCompraFB nf);
}
