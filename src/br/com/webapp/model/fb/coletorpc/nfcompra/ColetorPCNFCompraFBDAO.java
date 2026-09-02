package br.com.webapp.model.fb.coletorpc.nfcompra;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ColetorPCNFCompraFBDAO  {
	
	
	public List<ColetorPCNFCompraFB> listar(ColetorPCNFCompraFB nfCompraId);
	public Integer insert(ColetorPCNFCompraFB coletorPCFBNF) throws DAOException;
	public ColetorPCNFCompraFB carregar(Integer coletorFBId, Integer nfCompraId);
	public Object delete();
	void delete(Integer coletorPCId, Integer nfCompraId);
	public void delete(ColetorPCNFCompraFB coletorPCNFCompraFB);
	public List<ColetorPCNFCompraFB> listar(Integer coletorFBId);
	public List<ColetorPCNFCompraFB> carregar(Integer coletorFBId);
	
}
