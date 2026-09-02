package br.com.webapp.model.fb.romaneio;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

import java.util.List;

public class RomaneioContagemFBRN {	
	
	private RomaneioContagemFBDAO romaneioContagemFBDAO;
	
	public RomaneioContagemFBRN() {
		this.romaneioContagemFBDAO = DAOFactoryFirebird.criarRomaneioContagemFB();
	}
	
	public List<RomaneioContagemFB> listar(Integer romaneioId, boolean excluido){
		return this.romaneioContagemFBDAO.listar(romaneioId, excluido);
	}
	
	public List<RomaneioContagemFB> listarLeiturasProduto(Integer romaneioId, Integer produtoId) {
		return this.romaneioContagemFBDAO.listarLeiturasProduto(romaneioId, produtoId);
	}

	public void excluirLeitura(RomaneioContagemFB RomaneioContagemFB) throws DAOException, RNException {
		try {
			this.romaneioContagemFBDAO.excluirLeitura(RomaneioContagemFB);
		}catch (Exception e) {
			e.printStackTrace();
			
			rollBack();
			
			//integracao.rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else if(e instanceof DAOException) {
				throw new DAOException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.excluir.leitura.contagem"));
			}
		}		
			
		}
		
	private void rollBack() {
		this.romaneioContagemFBDAO.rollBack();
	}

	public void excluirTodasLeituras(Integer romaneioId, Integer produtoId) throws DAOException {
		this.romaneioContagemFBDAO.excluirTodasLeituras(romaneioId, produtoId);
	}

}
