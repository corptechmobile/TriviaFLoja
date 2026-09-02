package br.com.webapp.model.fb.pedvenda.diverg;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFB;
import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFBRN;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.dto.PedVendaDivergFBDTO;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class PedVendaDivergFBRN {

	private PedVendaDivergFBDAO pedVendaDivergFBDAO;
	
	public PedVendaDivergFBRN() {
		pedVendaDivergFBDAO = DAOFactoryFirebird.criarPedVendaDivergFBDAO();
	}
	
	public boolean bloqueioDesconto(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer condPagtoId, Double desconto, Integer usuarioId) throws DAOException {
		
		PedVendaDivergFB divergenciaVer = this.existDescontoLiberado(pedVendaFBId, pedVendaItemFBId, condPagtoId, desconto);
		if(divergenciaVer==null){
			PedVendaDivergFB pedVendaDivergFB = new PedVendaDivergFB();
			pedVendaDivergFB.setPedVendaId(pedVendaFBId);
			if(pedVendaItemFBId!=null) {
				pedVendaDivergFB.setPedVendaItemId(pedVendaItemFBId);
			}
			pedVendaDivergFB.setCondPagtoId(condPagtoId);
			pedVendaDivergFB.setDesconto(desconto);
			pedVendaDivergFB.setDt_create(new Date());
			pedVendaDivergFB.setDt_update(new Date());
			pedVendaDivergFB.setSituacao(PedVendaDivergFB.SITUACAO_EM_ABERTO);
			pedVendaDivergFB.setValidar(PedVendaDivergFB.VALIDAR);
			pedVendaDivergFB.setTipo(PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
			pedVendaDivergFB.setUsuarioId(usuarioId);
			
			this.pedVendaDivergFBDAO.insert(pedVendaDivergFB);
			
			return true;
			
		}
		
		return false;
	}
	
	public boolean bloqueioLote(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer usuarioId) throws DAOException {
		
		PedVendaDivergFB divergenciaVer = this.existLoteLiberado(pedVendaFBId, pedVendaItemFBId);
		if(divergenciaVer==null){
			PedVendaDivergFB pedVendaDivergFB = new PedVendaDivergFB();
			pedVendaDivergFB.setPedVendaId(pedVendaFBId);
			if(pedVendaItemFBId!=null) {
				pedVendaDivergFB.setPedVendaItemId(pedVendaItemFBId);
			}
			pedVendaDivergFB.setDt_create(new Date());
			pedVendaDivergFB.setDt_update(new Date());
			pedVendaDivergFB.setSituacao(PedVendaDivergFB.SITUACAO_EM_ABERTO);
			pedVendaDivergFB.setValidar(PedVendaDivergFB.VALIDAR);
			pedVendaDivergFB.setTipo(PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES);
			pedVendaDivergFB.setUsuarioId(usuarioId);
			
			this.pedVendaDivergFBDAO.insert(pedVendaDivergFB);
			
			return true;
			
		}
		
		return false;
	}
	
	public boolean bloqueioVendaSemEstoqueDisp(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer usuarioId) throws DAOException {
		PedVendaDivergFB divergenciaVer = this.existVendaSemEstoqueDispLiberado(pedVendaFBId, pedVendaItemFBId);
		if(divergenciaVer==null){
			PedVendaDivergFB pedVendaDivergFB = new PedVendaDivergFB();
			pedVendaDivergFB.setPedVendaId(pedVendaFBId);
			if(pedVendaItemFBId!=null) {
				pedVendaDivergFB.setPedVendaItemId(pedVendaItemFBId);
			}
			pedVendaDivergFB.setDt_create(new Date());
			pedVendaDivergFB.setDt_update(new Date());
			pedVendaDivergFB.setSituacao(PedVendaDivergFB.SITUACAO_EM_ABERTO);
			pedVendaDivergFB.setValidar(PedVendaDivergFB.VALIDAR);
			pedVendaDivergFB.setTipo(PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP);
			pedVendaDivergFB.setUsuarioId(usuarioId);
			
			this.pedVendaDivergFBDAO.insert(pedVendaDivergFB);
			
			return true;
			
		}
		
		return false;
	}

	public PedVendaDivergFB existDescontoLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer condPagtoId, Double desconto) {
		return this.pedVendaDivergFBDAO.existDescontoLiberado(pedVendaFBId, pedVendaItemFBId, condPagtoId, desconto);
	}
	
	private PedVendaDivergFB existLoteLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId) {
		return this.pedVendaDivergFBDAO.existLoteLiberado(pedVendaFBId, pedVendaItemFBId);
	}
	
	private PedVendaDivergFB existVendaSemEstoqueDispLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId) {
		return this.pedVendaDivergFBDAO.existVendaSemEstoqueDispLiberado(pedVendaFBId, pedVendaItemFBId);
	}

	public boolean existeDivergenciaPedVenda(Integer pedVendaFBId) {
		boolean temDivergencia = false;
		List<PedVendaDivergFB> divergencias = this.listar(pedVendaFBId);
		if(divergencias!=null) {
			for(PedVendaDivergFB rs : divergencias) {
				if(rs.getValidar()==PedVendaDivergFB.VALIDAR && (rs.getSituacao()==PedVendaDivergFB.SITUACAO_EM_ABERTO || rs.getSituacao()==PedVendaDivergFB.SITUACAO_NAO_LIBERADO)) {
					temDivergencia = true;
				}
			}
		}
		
		return temDivergencia;
	}

	public PedVendaDivergFB carregar(Integer id) {
		return this.pedVendaDivergFBDAO.carregar(id);
	}
	
	public List<PedVendaDivergFB> listar(){
		return this.pedVendaDivergFBDAO.listar();
	}

	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId) throws DAOException {
		this.pedVendaDivergFBDAO.excluir(pedVendaFBId, pedVendaItemFBId);
	}

	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId, int situacaoDiverg, int tipoDiverg) throws DAOException {
		this.pedVendaDivergFBDAO.excluir(pedVendaFBId, pedVendaItemFBId, situacaoDiverg, tipoDiverg);
	}
	
	public void liberar(Integer pedVendaFBId, List<PedVendaDivergFBDTO> divergenciasSelected, String obsDivergencia, UsuarioFB usuarioLogado, Integer condPagtoId) throws RNException {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			PedVendaFB verPedido = pedVendaFBRN.carregar(pedVendaFBId);
			if(!verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_BLOQUEADA)){
				throw UtilMessage.exceptionMensagem("msg.erro.nao.liberar.pedvendaliberacao", null);
			}else{
				
				// Verificar se tem divergencias por desconto maior q alcada 
				int countDivergDesconto = 0;
				for(PedVendaDivergFBDTO rs : divergenciasSelected){
					if(rs.getTipo().equals(PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO)) {
						countDivergDesconto++;
					}
				}
				
				AlcadaCondPagtoFB alcada = null;
				if(countDivergDesconto>0) {
					alcada = new AlcadaCondPagtoFBRN().carregar(usuarioLogado, condPagtoId);
				}
				
				for(PedVendaDivergFBDTO rs : divergenciasSelected){
					
					if(countDivergDesconto>0 && rs.getTipo().equals(PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO)) {
						if(alcada.getAlcada() < rs.getDesconto()) {
							throw UtilMessage.exceptionMensagem("msg.erro.nao.liberar.sem.alcada.pedvendaliberacao", null);
						}
					}
					
					rs.setSituacao(PedVendaDivergFB.SITUACAO_LIBERADO);
					rs.setValidar(PedVendaDivergFB.NAO_VALIDAR);
					rs.setUsuarioId(usuarioLogado.getId());
					rs.setDtInteracao(new Date());
					rs.setDt_update(new Date());
					rs.setObservacao(obsDivergencia);
					this.pedVendaDivergFBDAO.updateLiberacao(rs);
				}
			}
			
			// verificar se existe divergencia
			List<PedVendaDivergFBDTO> divergPendentes = this.listarToLiberar(pedVendaFBId);
			if(divergPendentes.size()==0){
				MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(verPedido.getMovFiscTipoId());
				if(movFiscTipoFB.getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL) == false 
						 && movFiscTipoFB.getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL_E_NFE) == false
						 		&& verPedido.getFormaPagtoId().equals(FormaPagtoFB.FORMAPAGTO_NOTAPROMISSORIA) == false) {
					pedVendaFBRN.updateLiberarPedVenda(verPedido.getId(), PedVendaFB.SITUACAO_LIBERADA, new Date(), usuarioLogado.getId());
				}else {
					pedVendaFBRN.updateLiberarPedVenda(verPedido.getId(), PedVendaFB.SITUACAO_AGUARDANDO_PAGTO, null, usuarioLogado.getId());
				}
			}
			
		} catch (Exception e) {
			this.pedVendaDivergFBDAO.rollback();
			if(e.getMessage()==null) {
				throw new RNException("Erro ao processar - Liberação, entre em contato com o Suporte Corptech");
			}else {
				throw new RNException(e.getMessage());
			}
			
		}
	}

	public void naoLiberar(Integer pedVendaFBId, List<PedVendaDivergFBDTO> divergenciasSelected, String obsDivergencia, UsuarioFB usuarioLogado) throws RNException {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			PedVendaFB verPedido = pedVendaFBRN.carregar(pedVendaFBId);
			if(!verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_BLOQUEADA)){
				throw UtilMessage.exceptionMensagem("msg.erro.nao.liberar.pedvendaliberacao", null);
			}else{
				for(PedVendaDivergFBDTO rs : divergenciasSelected){
					rs.setSituacao(PedVendaDivergFB.SITUACAO_NAO_LIBERADO);
					rs.setValidar(PedVendaDivergFB.VALIDAR);
					rs.setUsuarioId(usuarioLogado.getId());
					rs.setDtInteracao(new Date());
					rs.setDt_update(new Date());
					rs.setObservacao(obsDivergencia);
					this.pedVendaDivergFBDAO.updateLiberacao(rs);
				}
			}
			
			// verificar se existe em aberto ou nao liberado
			//List<PedVendaDivergFBDTO> divergPendentes = this.listarToLiberar(pedVendaFBId);
			//if(divergPendentes.size()==0){
				pedVendaFBRN.updateNaoLiberarPedVenda(verPedido.getId(), usuarioLogado.getId());
			//}
			
			
		} catch (Exception e) {
			this.pedVendaDivergFBDAO.rollback();
			if(e.getMessage()==null) {
				throw new RNException("Erro ao processar - Não Liberação, entre em contato com o Suporte Corptech");
			}else {
				throw new RNException(e.getMessage());
			}
		}		
	}

	public List<PedVendaDivergFBDTO> listarToLiberar(Integer pedVendaFBId) {
		return this.pedVendaDivergFBDAO.listarToLiberar(pedVendaFBId);
	}

	public List<PedVendaDivergFB> listar(Integer selecionadaId) {
		return this.pedVendaDivergFBDAO.listar(selecionadaId);
	}

	public List<PedVendaDivergFBDTO> listarDTO(Integer selecionadaId) {
		return this.pedVendaDivergFBDAO.listarDTO(selecionadaId);
	}

}
