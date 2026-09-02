package br.com.webapp.model.fb.romaneio;

import java.util.List;

import br.com.webapp.model.fb.nfcompra.NFCompraFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraItemFB;
import br.com.webapp.model.fb.nfcompra.NFCompraItemFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class RomaneioItemFBRN {

	private RomaneioItemFBDAO romaneioItemFBDAO;

	public RomaneioItemFBRN() {
		this.romaneioItemFBDAO = DAOFactoryFirebird.criarRomaneioItemFBDAO();
	}

	public List<RomaneioItemFB> listar(Integer romaneioFBId) {
		return this.romaneioItemFBDAO.listar(romaneioFBId);
	}
	
	private void delete(Integer Id) {
	this.romaneioItemFBDAO.delete(Id);	
	}	
	private void updateQuantidade(Integer id, Double quantidade) {
		this.romaneioItemFBDAO.updateQtd(id, quantidade);
	}

	public List<RomaneioItemDTOFB> listarParaAjuste(RomaneioItemFB itemSelecionado) {
		return this.romaneioItemFBDAO.listarParaAjuste(itemSelecionado);
	}

	public void atualizarQtdConferida(Integer romaneioId, Integer produtoId, Double qtd) throws DAOException {
		this.romaneioItemFBDAO.atualizarQtdConferida(romaneioId, produtoId, qtd);
	}

	public List<RomaneioItemDTOFB> listarParaIntegracao(RomaneioFB romaneio) {
		// TODO Auto-generated method stub
		return null;
	}

	public void integracao(Integer seqId, Integer romaneioId, Integer ordemcarregId) throws DAOException {
		this.romaneioItemFBDAO.integracao(seqId, romaneioId, ordemcarregId);
	}

}
