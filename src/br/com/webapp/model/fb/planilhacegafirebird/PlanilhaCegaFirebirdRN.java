package br.com.webapp.model.fb.planilhacegafirebird;

import java.util.List;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFBRN;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagem;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagemRN;
import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFB;
import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.model.usuario.UsuarioRN;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;
import br.com.webapp.web.util.UtilMessage;

public class PlanilhaCegaFirebirdRN {
	
	private PlanilhaCegaFirebirdDAO planilhaCegaFirebirdDAO;
	
	public PlanilhaCegaFirebirdRN(){
		this.planilhaCegaFirebirdDAO = DAOFactoryFirebird.criarPlanilhaCegaFirebirdDAO();
	}
	
	public Integer gerarId() throws RNException {
		return this.planilhaCegaFirebirdDAO.gerarId();
	}
	
	public Integer inserir(PlanilhaCegaFirebird planilhaCegaFirebird) throws RNException{
		planilhaCegaFirebird.setId(gerarId());
		return this.planilhaCegaFirebirdDAO.inserir(planilhaCegaFirebird);
	}
	
	public PlanilhaCegaFirebird convert(ColetorPCFB planilhaCega) throws RNException {
		
		try {
			
			PlanilhaCegaFirebird model = new PlanilhaCegaFirebird();
			Integer usuarioContagem = null;
			Integer usuarioConferenteId = planilhaCega.getEmpresaId();
			List<ColetorPCFBContagem> listColetorPCFBContagem = new ColetorPCFBContagemRN().listar(planilhaCega.getId(), false);
			if(listColetorPCFBContagem!=null && listColetorPCFBContagem.size()>0) {
				for(ColetorPCFBContagem rs:listColetorPCFBContagem) {
					usuarioContagem = rs.getUsuarioId();
				}
				
				// Pega o conferente associado ao usuario no f-loja
				Usuario usuarioLeitura = new UsuarioRN().carregar(usuarioContagem);
				if(usuarioLeitura!=null && usuarioLeitura.getConferenteId()!=null) {
					usuarioConferenteId = usuarioLeitura.getConferenteId();
				}
				
			}
			
			
			
			model.setPessoaConf(usuarioConferenteId);
			model.setUsuarioGerador(planilhaCega.getUsuarioId());
			model.setUsuario(planilhaCega.getUsuarioId());
			model.setConfrontada(0);
			model.setFinalizada(0);
			
			model.setDataConferencia(UtilData.formatarData(planilhaCega.getDtInicio(), UtilData.FORMATO_DATA_HORA));
			model.setMomentoGeracao(UtilData.formatarData(planilhaCega.getDtCriacao(), UtilData.FORMATO_DATA_HORA));
	
			return model;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro ao converter PlanilhaCega para o Trivia ERP.");
		}
		
	}

	@SuppressWarnings("unlikely-arg-type")
	public Integer integracao(Integer planilhaCegaId, Integer empresaId) throws Exception {
		
		ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
		ColetorPCItemFBRN coletorPCItemFBRN = new ColetorPCItemFBRN();
		ColetorPCNFCompraFBRN coletorPCNFCompraFBRN = new ColetorPCNFCompraFBRN();
		NFCompraFBRN nfCompraFBRN = new NFCompraFBRN();
		PlanilhaCegaItemFirebirdRN planilhaCegaItemFirebirdRN = new PlanilhaCegaItemFirebirdRN();
		
		Integer planilhaCegaIdErp;
		ColetorPCFB coletorPCFB;
		List<ColetorPCItemFB> itens;
		List<ColetorPCItemFB> itensLote;
		List<ColetorPCNFCompraFB> nfs;
		ProdutoFBRN produtoFBRN = new ProdutoFBRN();
		
		coletorPCFB = coletorPCFBRN.carregar(planilhaCegaId);
		planilhaCegaIdErp = this.inserir(this.convert(coletorPCFB));
		
		itens = coletorPCItemFBRN.listar(coletorPCFB.getId());
		for(ColetorPCItemFB rs : itens){	
			Integer idPlanilhaCegaItem = planilhaCegaItemFirebirdRN.inserir(planilhaCegaItemFirebirdRN.convert(rs, planilhaCegaIdErp, empresaId));
			ProdutoFB produtoFB = produtoFBRN.carregar(rs.getProdutoId());
			if(1 == produtoFB.getControlaLote() || 1 == produtoFB.getObrigaDescLote() || 1 == produtoFB.getObrigaVencLote()) {
				itensLote = coletorPCItemFBRN.listarLotes(coletorPCFB.getId(), rs.getProdutoId());
				for(ColetorPCItemFB rs2 : itensLote){
					rs2.setId(idPlanilhaCegaItem);
					planilhaCegaItemFirebirdRN.inserirLote(planilhaCegaItemFirebirdRN.convert(rs2, planilhaCegaIdErp, empresaId));
				}	
			}
		}
		
		nfCompraFBRN.atualizarPlanilhaCega(planilhaCegaId, planilhaCegaIdErp);
		
		return planilhaCegaIdErp;
	}

	public void reAbrirConferencia(Integer idErp) throws RNException {
		PlanilhaCegaFirebirdRN planilhaCegaFirebirdRN = new PlanilhaCegaFirebirdRN();
		PlanilhaCegaFirebird planilhaCegaFirebird = planilhaCegaFirebirdRN.verificarReAberturaConferencia(idErp);
		if(planilhaCegaFirebird != null) {
			throw new RNException(UtilMessage.mensagem("msg.erro.reabrir.planilhacega.integracao.firebird"));
		}
	}

	public PlanilhaCegaFirebird verificarReAberturaConferencia(Integer idErp) {
		return this.planilhaCegaFirebirdDAO.verificarReAberturaConferencia(idErp);
	}
	
	public void rollBack() {
		this.planilhaCegaFirebirdDAO.rollBack();
	}

}