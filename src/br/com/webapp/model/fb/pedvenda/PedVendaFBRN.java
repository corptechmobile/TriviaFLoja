package br.com.webapp.model.fb.pedvenda;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFB;
import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFBRN;
import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBRN;
import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBRN;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFB;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBRN;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFBRN;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTORN;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.produto.ProdutoPrecoDTO;
import br.com.webapp.model.fb.tabpreco.TabPrecoFB;
import br.com.webapp.model.fb.tabpreco.TabPrecoFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;
import br.com.webapp.web.util.UtilMessage;

public class PedVendaFBRN {

	private PedVendaFBDAO pedVendaFBDAO;

	public PedVendaFBRN() {
		this.pedVendaFBDAO = DAOFactoryFirebird.criarPedVendaFBDAO();
	}
	
	public PedVendaFB novo(EmpresaFB empresaFB, VendedorFB vendedorFB, ClienteFB clienteFB, UsuarioFB usuarioFB, Integer encomenda) throws RNException {
		
		TabPrecoFBRN tabPrecoFBRN = new TabPrecoFBRN();
		//TabPrecoFB tabPreco = tabPrecoFBRN.carregar(clienteFB.getCondPagto().getTabPrecoId());
		TabPrecoFB tabPreco = tabPrecoFBRN.carregar(empresaFB.getIdTabPrecoPadraoFDL());
		if(tabPreco == null) {
			throw new RNException(UtilMessage.mensagem("msg.erro.empresasemtabeladepreco.pedvenda"));
		}
		
		PedVendaStatusFBRN pedVendaStatusFBRN = new PedVendaStatusFBRN();
		PedVendaStatusFB pedVendaStatusFB = pedVendaStatusFBRN.carregar(PedVendaFB.SITUACAO_DIGITACAO);
		
		PedVendaFB pedVendaFB = new PedVendaFB();
		pedVendaFB.setEncomenda(encomenda);
		
		pedVendaFB.setEmpresaId(empresaFB.getId());
		pedVendaFB.setUsuarioId(usuarioFB.getId());
		pedVendaFB.setUsuarioWebId(usuarioFB.getId());
		pedVendaFB.setVendedorId(vendedorFB.getId());
		pedVendaFB.setClienteId(clienteFB.getId());
		
		pedVendaFB.setPedVendaStatusId(pedVendaStatusFB.getId());
		pedVendaFB.setPedVendaStatus(pedVendaStatusFB);
		
		pedVendaFB.setTabPrecoId(tabPreco.getId());
		pedVendaFB.setTabPreco(tabPreco);
		
		pedVendaFB.setFreteTipoId(clienteFB.getFreteTipoId());
		pedVendaFB.setFreteTipo(clienteFB.getFreteTipo());
		
		FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(clienteFB.getFreteTipoId());
		MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(freteTipoFB.getMovFiscTipoId());
		
		// Carrega o tipo de movimenta��o fiscal de acordo com a configura��o do frete do cliente e da informa��o na tabela TIPOFRETE
		if(freteTipoFB.getMovFiscTipoId()!=null) {
			pedVendaFB.setMovFiscTipoId(movFiscTipoFB.getId());
			pedVendaFB.setMovFiscTipo(movFiscTipoFB);
		}else{
			pedVendaFB.setMovFiscTipoId(clienteFB.getMovFiscTipoId());
			pedVendaFB.setMovFiscTipo(clienteFB.getMovFiscTipo());
		}

		pedVendaFB.setFormaPagtoId(clienteFB.getFormaPagtoId());
		pedVendaFB.setFormaPagto(clienteFB.getFormaPagto());
		
		pedVendaFB.setCondPagtoId(clienteFB.getCondPagtoId());
		pedVendaFB.setCondPagto(clienteFB.getCondPagto());
		
		pedVendaFB.setCobrTipoId(clienteFB.getCobrTipoId());
		pedVendaFB.setCobrTipo(clienteFB.getCobrTipo());
		
		pedVendaFB.setUsuarioLockId(PedVendaFB.USUARIO_LOCK_ID);
		pedVendaFB.setEnderecoEntregaId(clienteFB.getEnderecoEntregaId());
		pedVendaFB.setMoedaId(PedVendaFB.MOEDA_ID);
		pedVendaFB.setPessoaVendaOrdemId(PedVendaFB.PESSOA_VENDAORDREM_ID);
		pedVendaFB.setEntrada(new Date());
		pedVendaFB.setConclusao(null);
		pedVendaFB.setEfetivacao(null);
		pedVendaFB.setLiquidacao(null);
		pedVendaFB.setValPedido(0.0);
		pedVendaFB.setNumPedCli(PedVendaFB.NUMPEDCLI);
		pedVendaFB.setValFrete(PedVendaFB.VALFRETE);
		pedVendaFB.setValDespAcess(PedVendaFB.VALDESPACESS);
		pedVendaFB.setObservacao("");
		pedVendaFB.setEntrega(PedVendaFB.ENTREGA);
		pedVendaFB.setBloqPreco(PedVendaFB.BLOQPRECO);
		pedVendaFB.setBloqCred(PedVendaFB.BLOQCRED);
		pedVendaFB.setBloqCar(PedVendaFB.BLOQCAR);
		pedVendaFB.setPrevRetirada(new Date()); // TODO falar com alex
		pedVendaFB.setNumSolExterna(PedVendaFB.IDNUMSOLEXTERNA);
		pedVendaFB.setLiberadoInteg(PedVendaFB.LIBERADOINTEG);
		pedVendaFB.setValorDesconto(PedVendaFB.VALORDESCONTO);
		pedVendaFB.setValorST(PedVendaFB.VALORST);
		pedVendaFB.setUfCli(clienteFB.getEstadoId());
		pedVendaFB.setAliqICMSDest(PedVendaFB.ALIQICMSDEST);
		pedVendaFB.setValTotGeradoDup(PedVendaFB.VALTOTGERADODUP);
		pedVendaFB.setSeparaAnt(PedVendaFB.SEPARAANT);
		pedVendaFB.setTipoPedido(PedVendaFB.TIPOPEDIDO);
		pedVendaFB.setPrevRetiradaDataHora(new Date()); // falar com alex
		pedVendaFB.setDataUltAlteracao(new Date());
		pedVendaFB.setSeqPedVenda(PedVendaFB.SEQ_PEDVENDA);
		pedVendaFB.setCalcVendDUpdataEfet(PedVendaFB.CALCVENCDUPDATAEFET);
		pedVendaFB.setImpresso(PedVendaFB.IMPRESSO);
		pedVendaFB.setOrcamento(PedVendaFB.ORCAMENTO);
		pedVendaFB.setValorIPI(PedVendaFB.VALORIPI);
		pedVendaFB.setObservacao2("");
		pedVendaFB.setDescFlex(PedVendaFB.DESCFLEX);
		pedVendaFB.setDescGestaoVenda(PedVendaFB.DESCGESTAOVENDA);
		pedVendaFB.setSaldoInicVendedor(PedVendaFB.SALDOINICVENDEDOR);
		pedVendaFB.setCompoEFluxo(PedVendaFB.COMPOEFLUXO);
		pedVendaFB.setValTaxaEntrega(PedVendaFB.VALTAXAENTREGA);
		pedVendaFB.setUfEmpresa(PedVendaFB.UFEMPRESA); // TODO implementar para Pegar a uf da empresa
		pedVendaFB.setPercRateioDup(PedVendaFB.PERCRATEIODUP);
		pedVendaFB.setValorCotacao(PedVendaFB.VALORCOTACAO);
		pedVendaFB.setDataCotacao(PedVendaFB.DATACOTACAO);
		pedVendaFB.setTipoCambio(PedVendaFB.TIPOCAMBIO);
		pedVendaFB.setTipoEntradaPedido(PedVendaFB.TIPOENTRADAPEDIDO);
		pedVendaFB.setValidadeCotacaoDias(PedVendaFB.VALIDADECOTACAODIAS);
		pedVendaFB.setContato(PedVendaFB.CONTATO);
		pedVendaFB.setContatoEmail(PedVendaFB.CONTATOEMAIL);
		pedVendaFB.setContatoTelefone(PedVendaFB.CONTATOTELEFONE);
		pedVendaFB.setPrevCliente(PedVendaFB.PREVCLIENTE);
		pedVendaFB.setPedn(PedVendaFB.PEDN);
		pedVendaFB.setDesconto(0.0);
		
		return pedVendaFB; 
	}
	
	public PedVendaFB carregar(Integer id) {
		return this.pedVendaFBDAO.carregar(id);
	}
	
	public List<PedVendaFB> listar(Integer vendedorId, Integer clienteId){
		return this.pedVendaFBDAO.listar(vendedorId, clienteId);
	}
	
	public Integer salvar(FacesContext facesContext, PedVendaFB pedVendaFB, ProdutoFB produtoFB, PedVendaItemFB pedVendaItemFB, Double alcada, Double descMaxUsuarioGrupo, boolean descPedido) throws RNException {
		try {
			
			Integer pedVendaFBId = null;
			
			PedVendaFB selecionadaValidar = new PedVendaFBRN().carregar(pedVendaFB.getId());
			if(selecionadaValidar!=null && !pedVendaFB.getPedVendaStatusId().equals(selecionadaValidar.getPedVendaStatusId())) {
				throw new RNException(UtilMessage.mensagem("msg.erro.status.alterado.pedvenda"));
			}
			
			
			if(pedVendaItemFB.getQuantidade()<=0.0) {
				throw new RNException(UtilMessage.mensagem("msg.erro.item.qtd.invalida.pedvenda"));
			}
			
			if(pedVendaItemFB.getPreco() <= 0d) {
				throw new RNException(UtilMessage.mensagem("msg.erro.item.preco.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getCondPagtoId()==null){
				throw new RNException(UtilMessage.mensagem("msg.erro.condpagto.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getFreteTipoId()==null){
				throw new RNException(UtilMessage.mensagem("msg.erro.frete.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getFormaPagtoId()==null){
				throw new RNException(UtilMessage.mensagem("msg.erro.formapagto.invalida.pedvenda"));
			}
			
			
			System.out.println("pedVendaItemFB.getPercDesconto(): " + pedVendaItemFB.getPercDesconto());
//			if(pedVendaItemFB.getPercDesconto() < 0d) {
//				throw new RNException(UtilMessage.mensagem("msg.erro.item.desconto.invalida.pedvenda"));
//			}
			
			if(pedVendaItemFB.getPercDesconto() > descMaxUsuarioGrupo) {
				throw new RNException(UtilMessage.mensagem("msg.erro.item.desconto.max.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getId()==null) {
				
				pedVendaFB.setValPedido(pedVendaItemFB.getSubTotal());
				pedVendaFBId = this.insert(pedVendaFB);
				
				
				PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
				if(pedVendaFB.getIsEncomenda() || pedVendaFB.getIsPedido()) {
					pedVendaItemFBRN.salvar(facesContext, pedVendaFBId, pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				}else {
					pedVendaItemFBRN.salvarProdComposto(facesContext, pedVendaFBId, pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				}
				
				pedVendaFB.setId(pedVendaFBId);
				
			}else {
				
				this.verificarPedido(pedVendaFB.getId());
				
				PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
				if(pedVendaFB.getIsEncomenda() || pedVendaFB.getIsPedido()) {
					pedVendaItemFBRN.salvar(facesContext, pedVendaFB.getId(), pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				}else {
					pedVendaItemFBRN.salvarProdComposto(facesContext, pedVendaFB.getId(), pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				}
				
				if(!pedVendaFB.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
					pedVendaFB.setConclusao(null);
				}
				
				updateTotais(pedVendaFB);
				update(pedVendaFB);
				
			}
			
			if(facesContext!=null) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.pedvendaitem")));
			}
			
			return pedVendaFB.getId();
		
		} catch (Exception e) {
			e.printStackTrace();
			pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
	public void updateEmDigitacaoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		this.pedVendaFBDAO.updateEmDigitacaoPedVenda(pedVendaFBId, usuarioId);
	}

	public void updateLiberarPedVenda(Integer pedVendaFBId, Integer pedVendaStatusFBId, Date dtEfetivacao, Integer usuarioId) throws DAOException {
		this.pedVendaFBDAO.updateLiberarPedVenda(pedVendaFBId, pedVendaStatusFBId, dtEfetivacao, usuarioId);
	}

	public void updateNaoLiberarPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		this.pedVendaFBDAO.updateNaoLiberarPedVenda(pedVendaFBId, usuarioId);
	}

	public void updateAguardPagtoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws RNException {
		try {
			PedVendaFB verPedVendaFB = this.carregar(pedVendaFBId);
			if(verPedVendaFB==null) {
				throw new RNException(String.format(UtilMessage.mensagem("msg.erro.excluir.empty.pedvenda"), "" + pedVendaFBId));
			}
			
			if(verPedVendaFB.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_EM_RECEBIMENTO) == false){
				throw new RNException(UtilMessage.mensagem("msg.erro.diferente.emrecebimento.pedvenda"));
			}
				
			this.pedVendaFBDAO.updateAguardPagtoPedVenda(pedVendaFBId, usuarioId);
			
		} catch (Exception e) {
			throw new RNException(e.getMessage());
		}
	}

	public void concluir(FacesContext facesContext, PedVendaFB pedVendaFB) throws RNException, DAOException {
		
		try {
			
			// Validacoes
			
			this.verificarPedido(pedVendaFB.getId());
			
			if(UtilData.daysBetweenDates(new Date(), pedVendaFB.getPrevRetirada()) < 0) {
				throw new RNException(UtilMessage.mensagem("msg.erro.data.retirada.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getCondPagtoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.condpagto.empty.pedvenda"));
			}
			
			if(pedVendaFB.getTabPrecoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.tabpreco.empy.pedvenda"));
			}
			
			if(pedVendaFB.getFormaPagtoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.formapagto.empy.pedvenda"));
			}
			
			if(pedVendaFB.getFreteTipoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.fretetipo.empy.pedvenda"));
			}
			
			// Validar bloqueio de credito
			ClienteFBRN clienteFBRN = new ClienteFBRN();
			ClienteFB cliente = clienteFBRN.carregar(pedVendaFB.getClienteId());
			boolean temBloqueioCredito = clienteFBRN.validarCredito(cliente, pedVendaFB);

			
			
			// Verificar itens e suas divergencias
			boolean temDivergencia = new PedVendaDivergFBRN().existeDivergenciaPedVenda(pedVendaFB.getId());
			
			if(temDivergencia || temBloqueioCredito) {
				if(temDivergencia) {
					pedVendaFB.setBloqPreco(1);
				}
				
				if(temBloqueioCredito) {
					pedVendaFB.setBloqCred(1);
				}
				
				//pedVendaFB.setBloqCar(bloqCar);
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_BLOQUEADA);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_BLOQUEADA));
			}else {
				
			   //if((pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_NOTAFISCAL) && 
			   // 	   pedVendaFB.getMovFiscTipo().getEtapaLancaCar().equals(MovFiscTipoFB.ETAPA_GERADUPLICATA_EMISSNF) && 
			   //	               pedVendaFB.getFormaPagto().getId().equals(FormaPagtoFB.FORMAPAGTO_BANCARIA))){
			   
			   if((pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_NOTAFISCAL) && 
						   pedVendaFB.getMovFiscTipo().getEtapaLancaCar().equals(MovFiscTipoFB.ETAPA_GERADUPLICATA_EMISSNF))){	   
				   
				   //if(pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL) == false 
						   //&& pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL_E_NFE) == false) {
					pedVendaFB.setEfetivacao(new Date());
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_LIBERADA);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_LIBERADA));
				}else {
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO));
				}
				
			}
			
			pedVendaFB.setConclusao(new Date());
			
			Calendar c = Calendar.getInstance();
			c.setTime(pedVendaFB.getPrevRetirada());
			c.set(Calendar.HOUR, 8);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.AM_PM, Calendar.AM);
			pedVendaFB.setPrevRetiradaDataHora(c.getTime());
			
			// salvar
			update(pedVendaFB);
			
			if(facesContext!=null) {
				if(pedVendaFB.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_BLOQUEADA)) {
					if(temBloqueioCredito) {
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.concluido.pedvenda.bloqueado.credito")));
					}
					
					if(temDivergencia) {
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.concluido.pedvenda.bloqueado.preco")));
					}	
				}else {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.concluido.pedvenda")));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
	public PedVendaFB verificarPedido(Integer pedVendaFBId) throws RNException, DAOException {
		PedVendaFB verPedido = new PedVendaFBRN().carregar(pedVendaFBId);
		verPedido.setMovFiscTipo(new MovFiscTipoFBRN().carregar(verPedido.getMovFiscTipoId()));
		if(verPedido.isPodeEditar()==false){
			retornThrowPedido(verPedido);
		}
		
		return verPedido;
	}

	private Integer insert(PedVendaFB pedVendaFB) throws DAOException {
		return this.pedVendaFBDAO.insert(pedVendaFB);
	}

	private void update(PedVendaFB pedVendaFB) throws DAOException {
		this.pedVendaFBDAO.update(pedVendaFB);
	}

	private void retornThrowPedido(PedVendaFB verPedido) throws RNException {
		if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_BLOQUEADA)){
			throw UtilMessage.exceptionMensagem("msg.erro.bloqueado.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_CANCELADA)){
			throw UtilMessage.exceptionMensagem("msg.erro.cancelada.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_TOTALMENTE_ATENDIDA)){
			throw UtilMessage.exceptionMensagem("msg.erro.totalmenteatentida.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_EM_RECEBIMENTO)){
			throw UtilMessage.exceptionMensagem("msg.erro.emrecebimento.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_ENCERRADO)){
			throw UtilMessage.exceptionMensagem("msg.erro.encerrado.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_LIBERADA)){
			throw UtilMessage.exceptionMensagem("msg.erro.liberada.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_NAO_LIBERADO)){
			throw UtilMessage.exceptionMensagem("msg.erro.naoliberado.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_PAGTO_BLOQUEADO)){
			throw UtilMessage.exceptionMensagem("msg.erro.pagtobloqueado.pedvenda", null);
		}else if(verPedido.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_PARCIAL_ATENDIDA)){
			throw UtilMessage.exceptionMensagem("msg.erro.parcialatendida.pedvenda", null);
		}
		
	}

	public AlcadaCondPagtoFB mudarCondPagto(FacesContext facesContext, PedVendaFB pedVendaFB, AlcadaCondPagtoFB alcada, UsuarioFB usuario, boolean descPedido) throws RNException {
		
		try {
			
			PedVendaFB pedVendaOld = this.verificarPedido(pedVendaFB.getId());
			
//			pedVendaFB.setCondPagtoId(pedVendaFB.getCondPagto().getId());
			
//			TabPrecoFBRN tabPrecoFBRN = new TabPrecoFBRN();
//			pedVendaFB.setTabPreco(tabPrecoFBRN.carregar(pedVendaFB.getCondPagto().getTabPrecoId()));
			
			if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
			}
			
			// Validar Itens
			boolean temDivergencia = false;
			
			if(alcada.getCondPagtoId()==null || !alcada.getCondPagtoId().equals(pedVendaFB.getCondPagto().getId()) || !pedVendaOld.getCondPagtoId().equals(pedVendaFB.getCondPagto().getId()) ) {
				
				alcada = new AlcadaCondPagtoFBRN().carregar(usuario, pedVendaFB.getCondPagtoId());
				
				if(descPedido) {
					PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
					pedVendaDivergFBRN.excluir(pedVendaFB.getId(), null, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
					if(pedVendaFB.getDesconto()>alcada.getAlcada()) {
						pedVendaDivergFBRN.bloqueioDesconto(pedVendaFB.getId(), null, pedVendaFB.getCondPagtoId(), pedVendaFB.getDesconto(), pedVendaFB.getUsuarioId());
						temDivergencia = true;
					}
				}else {
					
					ProdutoFBRN produtoFBRN = new ProdutoFBRN();
					PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
					PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
					List<PedVendaItemFBDTO>  listaPedVendaItem = new PedVendaItemFBDTORN().listar(pedVendaFB.getId());
					
					Double totalPedido = 0.0;
					Double totalPrecoTab = 0.0;
					Double prModificado = 0.0;
					ProdutoPrecoDTO produtoPrecoDTO = null;
					for(PedVendaItemFBDTO rs : listaPedVendaItem) {
						
						produtoPrecoDTO = produtoFBRN.carregarPreco(pedVendaFB.getTabPrecoId(), pedVendaFB.getCondPagtoId(), rs.getProdutoId(), pedVendaFB.getEmpresaId(), pedVendaFB.getFreteTipo().getId());
						if(produtoPrecoDTO==null) {
							throw new RNException(String.format(UtilMessage.mensagem("msg.erro.produtopreco.dto.empy.pedvenda"), rs.getProdutoCodInterno(), pedVendaFB.getCondPagto().getDescricao()));
						}
						
						rs.setPrecoTabela(produtoPrecoDTO.getPreco());
						rs.setPrecoProm(produtoPrecoDTO.getPrecoPromo());
						
						if(rs.getInPromocao()==false) {
							prModificado = Funcoes.precoDesconto(rs.getPrecoTabela(), rs.getPercDesconto());
							rs.setPreco(prModificado);
							rs.setSubTotal(Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4,(rs.getPreco() * rs.getQuantidade()))));
							//rs.setPercDesconto(rs.getPercDesconto());
						}else {
							rs.setPreco(produtoPrecoDTO.getPrecoPromo());
							Double desconto = Funcoes.descontoPreco(produtoPrecoDTO.getPreco(), produtoPrecoDTO.getPrecoPromo());
							//rs.setPercDesconto(desconto);
						}
						
						pedVendaItemFBRN.updatePreco(rs);
						
						totalPrecoTab += rs.getInPromocao() ? Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4,(rs.getPrecoProm() * rs.getQuantidade()))) : Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4,(rs.getPrecoTabela() * rs.getQuantidade())));
						totalPedido += Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (rs.getPreco() * rs.getQuantidade())));
					
					}
					
					totalPrecoTab = Funcoes.arrendondaValor(2, totalPrecoTab);
					
					for(PedVendaItemFBDTO rs : listaPedVendaItem) {
						
						pedVendaDivergFBRN.excluir(rs.getPedVendaId(), rs.getId(), PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
						if(rs.getPercDesconto()>alcada.getAlcada()) {
							pedVendaDivergFBRN.bloqueioDesconto(rs.getPedVendaId(), rs.getId(), pedVendaFB.getCondPagtoId(), rs.getPercDesconto(), pedVendaFB.getUsuarioId());
							temDivergencia = true;
						}
						
					}
					
					pedVendaFB.setValPedido(totalPedido);
					pedVendaFB.setValPedidoPrTab(totalPrecoTab);
					
				}
			}
			
			// Validar bloqueio de credito
			ClienteFBRN clienteFBRN = new ClienteFBRN();
			ClienteFB cliente = clienteFBRN.carregar(pedVendaFB.getClienteId());
			boolean temBloqueioCredito = clienteFBRN.validarCredito(cliente, pedVendaFB);
			
			
			if(facesContext!=null && temDivergencia) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.aguadando.liberacao")));
			}
			
			if(facesContext!=null && temBloqueioCredito) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.bloqueiocredito")));
			}

			update(pedVendaFB);
			
			return alcada;
			
		} catch (Exception e) {
			this.pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
	public PedVendaFB mudarInformacoes(PedVendaFB pedVendaFB) throws RNException {
		try {
			this.verificarPedido(pedVendaFB.getId());
			
			if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
			}
			
			update(pedVendaFB);
			
			return pedVendaFB;
		} catch (Exception e) {
			throw new RNException(e.getMessage());
		}
	}

	public PedVendaFB mudarCliente(PedVendaFB pedVendaFB, ClienteFB clienteFB) throws RNException{
		
		try {
			
			this.verificarPedido(pedVendaFB.getId());
		
//			if(clienteFB.getCondPagtoId().equals(pedVendaFB.getCondPagtoId()) && clienteFB.getMovFiscTipoId().equals(pedVendaFB.getMovFiscTipoId())){
				
				if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
				}
				
				pedVendaFB.setClienteId(clienteFB.getId());
				pedVendaFB.setCobrTipo(clienteFB.getCobrTipo());
				pedVendaFB.setCobrTipoId(clienteFB.getCobrTipoId());
				pedVendaFB.setCondPagtoId(clienteFB.getCondPagtoId());
				pedVendaFB.setCondPagto(clienteFB.getCondPagto());
				pedVendaFB.setFreteTipoId(clienteFB.getFreteTipoId());
				pedVendaFB.setFreteTipo(clienteFB.getFreteTipo());
				pedVendaFB.setMovFiscTipoId(clienteFB.getMovFiscTipoId());
				pedVendaFB.setMovFiscTipo(clienteFB.getMovFiscTipo());
				
				if(clienteFB.getFormaPagtoId()==null || ClienteFB.TIPO_PESSOA_OUTRO.equals(clienteFB.getTipoPessoa())) {
					
					if(clienteFB.getCobrTipoId()==0) {
						FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(0);
						pedVendaFB.setFormaPagto(formaPagtoFB);
						pedVendaFB.setFormaPagtoId(formaPagtoFB.getId());
					}else {
						FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(2);
						pedVendaFB.setFormaPagto(formaPagtoFB);
						pedVendaFB.setFormaPagtoId(formaPagtoFB.getId());
					}
					
					
					if(ClienteFB.TIPO_PESSOA_OUTRO.equals(clienteFB.getTipoPessoa())){
						FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(FreteTipoFB.FRETE_FOB);
						pedVendaFB.setFreteTipoId(FreteTipoFB.FRETE_FOB);
						pedVendaFB.setFreteTipo(freteTipoFB);
					}
				}	
				
				update(pedVendaFB);
				
//			}else {
//				UtilMessage.exceptionMensagem("msg.aviso.pedvenda.cliente.naopodealterar", null);
//			}
			
			return pedVendaFB;
		
		} catch (Exception e) {
			throw new RNException(e.getMessage());
		}
		
	}

	public PedVendaFB mudarEmpresa(PedVendaFB pedVendaFB, EmpresaFB empresaFB) throws RNException{
		
		try {
			
			this.verificarPedido(pedVendaFB.getId());
		
			pedVendaFB.setEmpresaId(empresaFB.getId());
			update(pedVendaFB);
				
			return pedVendaFB;
		
		} catch (Exception e) {
			throw new RNException(e.getMessage());
		}
		
	}

	
	public void incluirDesconto(FacesContext facesContext, PedVendaFB pedVendaFB, List<PedVendaItemFBDTO> listaPedVendaItem, Double alcada, Double descontoMaximo) throws RNException{
		try {
			
			this.verificarPedido(pedVendaFB.getId());
			
			// Limite maximo desconto
			if(pedVendaFB.getDesconto()>descontoMaximo) {
				throw new RNException(String.format(UtilMessage.mensagem("msg.erro.desconto.maximo.usuariogrupo.pedvenda"), Funcoes.formatNumber(descontoMaximo, null, 0, 0) + "%"));
			}

			if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
			}
			
			boolean temDivergencia = false;
			PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
			pedVendaDivergFBRN.excluir(pedVendaFB.getId(), null, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
			if(pedVendaFB.getDesconto()>alcada) {
				pedVendaDivergFBRN.bloqueioDesconto(pedVendaFB.getId(), null, pedVendaFB.getCondPagtoId(), pedVendaFB.getDesconto(), pedVendaFB.getUsuarioId());
				temDivergencia = true;
			}
			
			Double totalPedido = 0.0;
			Double totalPrecoTab = 0.0;
			Double prModificado = 0.0;
			for(PedVendaItemFBDTO rs : listaPedVendaItem) {
				totalPrecoTab += rs.getInPromocao() ? Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (rs.getPrecoProm() * rs.getQuantidade()))) : Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (rs.getPrecoTabela() * rs.getQuantidade())));
			}
			
			totalPrecoTab = Funcoes.arrendondaValor(2, totalPrecoTab);
			
			PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
			for(PedVendaItemFBDTO rs : listaPedVendaItem) {
				if(rs.getInPromocao()==false) {
					prModificado = Funcoes.precoDesconto(rs.getPrecoTabela(), pedVendaFB.getDesconto());
					rs.setPreco(prModificado);
					rs.setSubTotal(Funcoes.arrendondaValor(2, (rs.getPreco() * rs.getQuantidade())));
					rs.setPercDesconto(pedVendaFB.getDesconto());
					
					pedVendaItemFBRN.updatePreco(rs);
				}
				totalPedido += Funcoes.arrendondaValor(2, rs.getPreco() * rs.getQuantidade());
			}
			
			if(facesContext!=null && temDivergencia) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", UtilMessage.mensagem("msg.aviso.pedvenda.aguadando.liberacao")));
			}
			
			pedVendaFB.setValPedido(totalPedido);
			pedVendaFB.setValPedidoPrTab(totalPrecoTab);
			update(pedVendaFB);
			
		} catch (Exception e) {
			this.pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
	}

	public void excluirItem(PedVendaFB pedVendaFB, Integer pedVendaItemFBId, Integer usuarioId) throws RNException {
		try {
			PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
			PedVendaItemFB pedVendaItem = pedVendaItemFBRN.carregar(pedVendaItemFBId);
			pedVendaItemFBRN.excluir(pedVendaItem, usuarioId);
			
			if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
			}
			
			updateTotais(pedVendaFB);
			
			update(pedVendaFB);
			
		} catch (Exception e) {
			this.pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
	public void excluirProdComposto(PedVendaFB pedVendaFB, Integer pedVendaCompostoId, Integer usuarioId) throws RNException {
		try {
			PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
			List<PedVendaItemFB> itens = pedVendaItemFBRN.listarProdCompostos(pedVendaFB.getId(), pedVendaCompostoId);
			for(PedVendaItemFB rs : itens) {
				pedVendaItemFBRN.excluir(rs, usuarioId);
			}
			
			// 
			new PedVendaCompostoFBRN().excluir(pedVendaCompostoId);
			
			if(!pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
			}
			
			updateTotais(pedVendaFB);
			
			update(pedVendaFB);
			
		} catch (Exception e) {
			this.pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
	private void updateTotais(PedVendaFB pedVendaFB) {
		List<PedVendaItemFBDTO> listaPedVendaItem = new PedVendaItemFBDTORN().listar(pedVendaFB.getId());
		Double total = 0.0;
		Double totalPrecoTab = 0.0;
		Double totalPesoBruto = 0.0;
		for(PedVendaItemFBDTO rs : listaPedVendaItem) {
			total += rs.getSubTotal();
			totalPrecoTab += rs.getInPromocao() ? Funcoes.arrendondaValor(2, Funcoes.truncValor(4,(rs.getPrecoProm() * rs.getQuantidade()))) : Funcoes.arrendondaValor(2, Funcoes.truncValor(4,(rs.getPrecoTabela() * rs.getQuantidade())));
			totalPesoBruto += rs.getPesoBrutoKg();
		}
		pedVendaFB.setValPedido(total);
		pedVendaFB.setValPedidoPrTab(totalPrecoTab);
		pedVendaFB.setPesoBrutoKg(totalPesoBruto);
	}

	public void excluir(Integer pedVendaFBId, Integer usuarioId) throws RNException {
		try {
			PedVendaFB verPedVendaFB = this.carregar(pedVendaFBId);
			if(verPedVendaFB!=null) {
				if(verPedVendaFB.isPodeExcluir()) {
					this.pedVendaFBDAO.excluir(pedVendaFBId, usuarioId);
				}else {
					throw new RNException(String.format(UtilMessage.mensagem("msg.erro.excluir.pedvenda"), "" + pedVendaFBId));
				}
			}else {
				throw new RNException(String.format(UtilMessage.mensagem("msg.erro.excluir.empty.pedvenda"), "" + pedVendaFBId));		
			}
		} catch (Exception e) {
			this.pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
	}

	public Integer salvarPedTransf(FacesContext facesContext, PedVendaFB pedVendaFB, ProdutoFB produtoFB, PedVendaItemFB pedVendaItemFB, Double alcada, Double descMaxUsuarioGrupo, boolean descPedido) throws RNException {
		try {
			
			Integer pedVendaFBId = null;
			
			if(pedVendaItemFB.getQuantidade()<=0.0) {
				throw new RNException(UtilMessage.mensagem("msg.erro.item.qtd.invalida.pedvenda"));
			}
			
			if(pedVendaItemFB.getPreco() <= 0d) {
				throw new RNException(UtilMessage.mensagem("msg.erro.item.preco.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getId()==null) {
				
				pedVendaFB.setValPedido(pedVendaItemFB.getSubTotal());
				pedVendaFBId = this.insert(pedVendaFB);
				
				
				PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
				pedVendaItemFBRN.salvarItemPedTransf(facesContext, pedVendaFBId, pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				
				pedVendaFB.setId(pedVendaFBId);
				
			}else {
				
				this.verificarPedido(pedVendaFB.getId());
				
				PedVendaItemFBRN pedVendaItemFBRN = new PedVendaItemFBRN();
				pedVendaItemFBRN.salvarItemPedTransf(facesContext, pedVendaFB.getId(), pedVendaFB, produtoFB, pedVendaItemFB, alcada, descPedido);
				
				if(!pedVendaFB.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_DIGITACAO)) {
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_DIGITACAO));
					pedVendaFB.setConclusao(null);
				}
				
				updateTotais(pedVendaFB);
				update(pedVendaFB);
				
			}
			
			if(facesContext!=null) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.pedvendaitem")));
			}
			
			return pedVendaFB.getId();
		
		} catch (Exception e) {
			e.printStackTrace();
			pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}

	public void concluirPedTransf(FacesContext facesContext, PedVendaFB pedVendaFB) throws RNException, DAOException {
		
		try {
			
			// Validacoes
			
			this.verificarPedido(pedVendaFB.getId());
			
			if(UtilData.daysBetweenDates(new Date(), pedVendaFB.getPrevRetirada()) < 0) {
				throw new RNException(UtilMessage.mensagem("msg.erro.data.retirada.invalida.pedvenda"));
			}
			
			if(pedVendaFB.getCondPagtoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.condpagto.empty.pedvenda"));
			}
			
			if(pedVendaFB.getTabPrecoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.tabpreco.empy.pedvenda"));
			}
			
			if(pedVendaFB.getFormaPagtoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.formapagto.empy.pedvenda"));
			}
			
			if(pedVendaFB.getFreteTipoId()==null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.fretetipo.empy.pedvenda"));
			}
			
			if(pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL) == false && pedVendaFB.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL_E_NFE) == false) {
				pedVendaFB.setEfetivacao(new Date());
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_LIBERADA);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_LIBERADA));
				
			}else {
				pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO);
				pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO));
			}
			

			if(pedVendaFB.getPedVendaStatus().getId().equals(PedVendaFB.SITUACAO_LIBERADA)) {
				if(pedVendaFB.getMovFiscTipo().getLancaCarCap().equals(MovFiscTipoFB.LANCACAR_GERAFINANCEIRO) && pedVendaFB.getMovFiscTipo().getEtapaLancaCar().equals(MovFiscTipoFB.LANCACAR_GERAFINANCEIRO)) {
					pedVendaFB.setPedVendaStatusId(PedVendaFB.SITUACAO_BLOQUEADA);
					pedVendaFB.setPedVendaStatus(new PedVendaStatusFBRN().carregar(PedVendaFB.SITUACAO_BLOQUEADA));
				}
			}

			
			
			pedVendaFB.setConclusao(new Date());
			
			Calendar c = Calendar.getInstance();
			c.setTime(pedVendaFB.getPrevRetirada());
			c.set(Calendar.HOUR, 8);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.AM_PM, Calendar.AM);
			pedVendaFB.setPrevRetiradaDataHora(c.getTime());
			
			// salvar
			update(pedVendaFB);
			
			if(facesContext!=null) {
				if(pedVendaFB.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_BLOQUEADA)) {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.concluido.pedvenda.bloqueado")));
				}else {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.concluido.pedvenda")));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			pedVendaFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
		
	}
	
}
