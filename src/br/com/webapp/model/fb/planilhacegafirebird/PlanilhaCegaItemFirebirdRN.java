package br.com.webapp.model.fb.planilhacegafirebird;

import java.util.Calendar;

import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagemAgrupadaDTO;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;

public class PlanilhaCegaItemFirebirdRN {
	
	private PlanilhaCegaItemFirebirdDAO planilhaCegaItemFirebirdDAO;
	
	public PlanilhaCegaItemFirebirdRN(){
		this.planilhaCegaItemFirebirdDAO = DAOFactoryFirebird.criarPlanilhaCegaItemFirebirdDAO();
	}
	
	public Integer inserir(PlanilhaCegaItemFirebird planilhaCegaItemFirebird) throws RNException{
		return this.planilhaCegaItemFirebirdDAO.inserir(planilhaCegaItemFirebird);
	}
	
	public PlanilhaCegaItemFirebird convert(ColetorPCItemFB coletorPCItemFB, Integer planilhaCegaIdErp, Integer empresaId) throws RNException {
		
		try {
			
			PlanilhaCegaItemFirebird model = new PlanilhaCegaItemFirebird();
			
			model.setPlanilhaCega(planilhaCegaIdErp); 
			model.setId(coletorPCItemFB.getId());
			model.setProduto(coletorPCItemFB.getProdutoId());
			model.setLocalidade(coletorPCItemFB.getLocalidadeId());
			
			model.setQtdRecebida(coletorPCItemFB.getQtdLeitura() == null ? 0d : coletorPCItemFB.getQtdLeitura());
			model.setQtdAvaria(coletorPCItemFB.getQtdAvaria() == null ? 0d : coletorPCItemFB.getQtdAvaria());
			model.setQtdDevolvida(coletorPCItemFB.getQtdDevolvida() == null ? 0d : coletorPCItemFB.getQtdDevolvida());
			if(coletorPCItemFB.getDtVencLot()!=null){
				model.setVencimentoLote(UtilData.formatarData(coletorPCItemFB.getDtVencLot(), UtilData.FORMATO_DATA_INVERTIDA));
			}else{
				ProdutoFB produto = new ProdutoFBRN().carregar(coletorPCItemFB.getProdutoId());
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, produto.getShelfLife());
				model.setVencimentoLote(UtilData.formatarData(c.getTime(), UtilData.FORMATO_DATA_INVERTIDA));
			}
			
			model.setObservacao(null);
			model.setEstoqueAtualizado(0);
			model.setItemSemReferencia(0);
			model.setItemGeracao(1);
			model.setRestricao(0);
			model.setEstoqueAtualizado(0);
			model.setIdUnidadeCpr(coletorPCItemFB.getUnidadeId());
			model.setCodLote(coletorPCItemFB.getCodLote());
			
	
			return model;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro ao converter Item da Planilha Cega para o Trivia ERP.");
		}
		
	}

	public PlanilhaCegaItemFirebird convert(Integer id, ColetorPCItemFB rs, Integer planilhaCegaIdErp, Integer empresaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void inserirLote(PlanilhaCegaItemFirebird planilhaCegaItemFirebird) throws RNException {
		this.planilhaCegaItemFirebirdDAO.inserirLote(planilhaCegaItemFirebird);
	}

}
