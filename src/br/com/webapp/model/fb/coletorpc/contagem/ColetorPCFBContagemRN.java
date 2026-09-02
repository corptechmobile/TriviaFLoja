package br.com.webapp.model.fb.coletorpc.contagem;
import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import java.util.ArrayList;
import java.util.List;

public class ColetorPCFBContagemRN {	
	
	private ColetorPCFBContagemDAO coletorPCFBContagemDAO;
	
	public ColetorPCFBContagemRN() {
		this.coletorPCFBContagemDAO = DAOFactoryFirebird.criarColetorPCFBContagem();
	}
	
	public List<ColetorPCFBContagem> listar(Integer coletorPlanilhaCegaId, boolean excluido){
		return this.coletorPCFBContagemDAO.listar(coletorPlanilhaCegaId, excluido);
	}
	
	public Integer inserir (EspelhoColetorPlanilhaCegaContagem espelho)throws DAOException { 
		return this.coletorPCFBContagemDAO.insert(espelho);
	}

	public List<ColetorPCFBContagem> listarLeiturasProduto(Integer coletorPlanilhaCegaId, Integer produtoId) {
		return this.coletorPCFBContagemDAO.listarLeiturasProduto(coletorPlanilhaCegaId, produtoId);
	}

	public void excluirLeitura(ColetorPCFBContagem coletorPCFBContagem) throws DAOException {
		this.coletorPCFBContagemDAO.excluirLeitura(coletorPCFBContagem);
		
	}

	public void excluirTodasLeituras(Integer coletorId) throws DAOException {
		this.coletorPCFBContagemDAO.excluirTodasLeituras(coletorId);
	}

	public List<ColetorPCFBContagemAgrupadaDTO> listaItensMovAgrupado(ColetorPCFB planilhaCega) {
		// TODO Auto-generated method stub
		return null;
	}

}
