package br.com.webapp.model.fb.coletorpc;

import java.util.List;

import org.hibernate.Session;

import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFB;
import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraItemFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ColetorPCItemFBRN {

	private ColetorPCItemFBDAO coletorPCItemDAO;

	public ColetorPCItemFBRN() {
		this.coletorPCItemDAO = DAOFactoryFirebird.criarColetorPCItemFBDAO();
	}
	
	public ColetorPCItemFBRN(Session session) {
		this.coletorPCItemDAO = DAOFactoryFirebird.criarColetorPCItemFBDAO(session);
	}

	public List<ColetorPCItemFB> listar(Integer coletorPCFBId) {
		return this.coletorPCItemDAO.listar(coletorPCFBId);
	}
	
	public List<ColetorPCItemFB> listarLotes(Integer coletorPCFBId, Integer produtoId) {
		return this.coletorPCItemDAO.listarLotes(coletorPCFBId, produtoId);
	}
	
	private void delete(Integer Id) {
	this.coletorPCItemDAO.delete(Id);	
	}	
	private void updateQuantidade(Integer id, Double quantidade) {
		this.coletorPCItemDAO.updateQtd(id, quantidade);
	}

	public void inserir(ColetorPCItemFB coletorPCItemFB) throws RNException {
		this.coletorPCItemDAO.inserir(coletorPCItemFB);
	}

	public ColetorPCItemFB carregar(Integer coletorPCFBId, Integer produtoId) {
		return this.coletorPCItemDAO.carregar(coletorPCFBId, produtoId);
	}

	public Integer processarNFs(ColetorPCFB coletorPCFB, List<NFCompraFB> nfs, boolean incluir, UsuarioFB usuarioFB) throws RNException {

		Integer coletorPCId = null;
		ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
		try {

			if (coletorPCFB != null) {
				coletorPCId = coletorPCFB.getId();
				
				if (coletorPCFB.getId() == null) {
					coletorPCFB = coletorPCFBRN.salvar(coletorPCFB, usuarioFB);
					coletorPCId = coletorPCFB.getId();
				}	
			}
			
			NFCompraFBRN nfCompraFBRN = new NFCompraFBRN();
			NFCompraItemFBRN nfCompraItemRN = new NFCompraItemFBRN();
			ColetorPCNFCompraFBRN coletorPCNFCompraFBRN = new ColetorPCNFCompraFBRN();
			ColetorPCItemFBRN coletorPCItemFBRN = new ColetorPCItemFBRN();
			if (incluir) {
				for (NFCompraFB nf : nfs) {
					if (nfCompraItemRN.listar(nf).size() == 0) {
						throw new RNException(
								String.format(UtilMessage.mensagem("msg.erro.itens.empty.nfcompra.planilhacega"),
										nf.getNumNf(), nf.getSerieNf()));
					}
					ColetorPCNFCompraFB coletorPCNFCompraFB = coletorPCNFCompraFBRN.carregar(coletorPCFB.getId(), nf.getId());
					if (coletorPCNFCompraFB == null) {
						coletorPCNFCompraFB = new ColetorPCNFCompraFB();
						coletorPCNFCompraFB.setColetorId(coletorPCFB.getId());
						coletorPCNFCompraFB.setNfCompraId(nf.getId());
						coletorPCNFCompraFBRN.insert(coletorPCNFCompraFB);
					}

					
				}
			} else {
					if(coletorPCFB != null) {
						coletorPCId = coletorPCFB.getId();
						
						if(nfs.size()==0) {
							nfs = nfCompraFBRN.listarPorPlanilhaCegaEFornecedor(coletorPCFB);
						}
					}	
					
					if(nfs.size()>0) {
						for (NFCompraFB nf : nfs) {
							ColetorPCNFCompraFB coletorPCNFCompraFB = coletorPCNFCompraFBRN.carregar(coletorPCFB.getId(), nf.getId());
							if (coletorPCNFCompraFB != null) {
								coletorPCNFCompraFBRN.delete(coletorPCNFCompraFB);
							}
						}
					}
			}
			
			this.excluirToPlanilhaCega(coletorPCFB.getId());
			List<ColetorPCItemFB> listaNFCompraItem = this.gerarItens(coletorPCFB.getId());
			
			ColetorPCItemFB coletorPCItemFB = null;
			for (ColetorPCItemFB nfi : listaNFCompraItem) {
				coletorPCItemFB = new ColetorPCItemFB();
				coletorPCItemFB.setColetorId(coletorPCFB.getId());
				coletorPCItemFB.setProdutoId(nfi.getProdutoId());
				coletorPCItemFB.setQuantidade(nfi.getQuantidade());
				coletorPCItemFB.setUnidadeId(nfi.getUnidadeId());
				coletorPCItemFBRN.inserir(coletorPCItemFB);
				coletorPCItemFB = null;
			}
		} catch (Exception e) {

			coletorPCFBRN.rollBack();
			if (e instanceof RNException) {
				throw new RNException(e.getMessage());
			} else {

			}

		}
		return coletorPCId;

	}

	private List<ColetorPCItemFB> gerarItens(Integer planilhaCegaId) {
		return this.coletorPCItemDAO.gerarItens(planilhaCegaId);
	}

	private void excluirToPlanilhaCega(Integer planilhaCegaId) {
		this.coletorPCItemDAO.excluirToPlanilhaCega(planilhaCegaId);
	}

	

	

}
