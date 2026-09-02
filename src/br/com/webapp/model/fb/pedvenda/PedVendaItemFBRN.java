package br.com.webapp.model.fb.pedvenda;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFB;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBRN;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFB;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFBRN;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBDTO;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.reserva.ReservaFB;
import br.com.webapp.model.fb.reserva.ReservaFBRN;
import br.com.webapp.model.fb.reservafila.ReservaFilaFBRN;
import br.com.webapp.model.fb.reservalote.ReservaLoteFB;
import br.com.webapp.model.fb.reservalote.ReservaLoteFBRN;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class PedVendaItemFBRN {

	private PedVendaItemFBDAO pedVendaItemFBDAO;
	
	public PedVendaItemFBRN() {
		this.pedVendaItemFBDAO = DAOFactoryFirebird.criarPedVendaItemFBDAO();
	}
	
	public PedVendaItemFB carregar(Integer id) {
		return this.pedVendaItemFBDAO.carregar(id);
	}
	
	public List<PedVendaItemFB> listar(PedVendaFB pedVenda){
		return this.pedVendaItemFBDAO.listar(pedVenda);
	}

	public List<PedVendaItemFB> listarProdCompostos(Integer pedVendaId, Integer pedVendaCompostoId) {
		return this.pedVendaItemFBDAO.listarProdCompostos(pedVendaId, pedVendaCompostoId);
	}
	
	public PedVendaItemFB novo(PedVendaFB pedVendaFB, ProdutoFB produtoFB, Integer pedVendaCompostoId) {
		PedVendaItemFB pedVendaItemFB = null;
		
		// verificar se item existe no pedido
		if(pedVendaFB.getId()!=null && (pedVendaFB.getIsPedido() || pedVendaFB.getIsEncomenda())) {
			pedVendaItemFB = this.carregar(pedVendaFB.getId(), produtoFB.getId());
		}else if(pedVendaCompostoId!=null && pedVendaFB.getIsProdComposto()) {
			pedVendaItemFB = this.carregar(pedVendaFB.getId(), produtoFB.getId(), pedVendaCompostoId);
		}
		
		if(pedVendaItemFB==null) {
			pedVendaItemFB = new PedVendaItemFB();
			pedVendaItemFB.setProdutoId(produtoFB.getId());
			pedVendaItemFB.setUnidade(produtoFB.getUnidadeId());
			if(pedVendaFB!=null) {
				pedVendaItemFB.setPedVendaId(pedVendaFB.getId());
			}
			
			pedVendaItemFB.setUsuarioWebId(pedVendaFB.getUsuarioWebId());
			pedVendaItemFB.setQuantidade(0.0);
			pedVendaItemFB.setPercDesconto(0.0);
			pedVendaItemFB.setPreco(produtoFB.getPrecoPromo()==null ? produtoFB.getPreco() : produtoFB.getPrecoPromo());
			pedVendaItemFB.setPrecoTabela(produtoFB.getPreco());
			pedVendaItemFB.setPrecoProm(produtoFB.getPrecoPromo()==null ? 0.0 : produtoFB.getPrecoPromo());
			
			pedVendaItemFB.setPesoLiquidoKg(0.0);
			pedVendaItemFB.setPesoBrutoKg(0.0);
			pedVendaItemFB.setComissao(PedVendaItemFB.COMISSAO);
			pedVendaItemFB.setCustoGerUltCompra(PedVendaItemFB.CUSTOGERULTCOMPRA);
			pedVendaItemFB.setCustoEmbalagem(PedVendaItemFB.CUSTOEMBALAGEM);
			pedVendaItemFB.setCustoTerceirizacao(PedVendaItemFB.CUSTOTERCEIRIZACAO);
			pedVendaItemFB.setCustoFreteUnit(PedVendaItemFB.CUSTOFRETEUNIT);
			pedVendaItemFB.setMkUltAtual(PedVendaItemFB.MKUPATUAL);
			pedVendaItemFB.setPrecoSugeridoVenda(PedVendaItemFB.PRECOSUGERIDOVENDA);
			pedVendaItemFB.setMkUpCalculado(PedVendaItemFB.MKUPCALCULADO);
			pedVendaItemFB.setValorComissao(PedVendaItemFB.VALORCOMISSAO);
			pedVendaItemFB.setQuantidadeEnf(PedVendaItemFB.QUANTIDADENF);
			pedVendaItemFB.setAssocNfItem(PedVendaItemFB.ASSOCNFITEM);
			pedVendaItemFB.setQtdSaldoAtender(PedVendaItemFB.QTDSALDOATENDER);
			pedVendaItemFB.setValorDesconto(PedVendaItemFB.VALORDESCONTO);
			pedVendaItemFB.setIdTributICMS(PedVendaItemFB.ID_TRIBUTICMS);
			pedVendaItemFB.setmVast(PedVendaItemFB.MVAST);
			pedVendaItemFB.setValorSTUnit(PedVendaItemFB.VALORSTUNIT);
			pedVendaItemFB.setCustoGerUltCompraUv(PedVendaItemFB.CUSTOGERULTCOMPRAUV);
			pedVendaItemFB.setQuantOriginal(PedVendaItemFB.QUANTORIGINAL);
			pedVendaItemFB.setAtuCCVendedor(PedVendaItemFB.ATUCCVENDEDOR);
			pedVendaItemFB.setSeqPedVendaItem(PedVendaItemFB.SEQ_PEDVENDAITEM);
			pedVendaItemFB.setAliqIPI(PedVendaItemFB.ALIQIPI);
			pedVendaItemFB.setValIPI(PedVendaItemFB.VALIPI);
			pedVendaItemFB.setComissaoFabr(PedVendaItemFB.COMISSAO_FABR);
			pedVendaItemFB.setPrecoRefCCVendedor(PedVendaItemFB.PRECOREFCCVENDEDOR);
			pedVendaItemFB.setAliqICMSSt(PedVendaItemFB.ALIQICMSST);
			pedVendaItemFB.setAliqICMS(produtoFB.getAliqICMS());
			pedVendaItemFB.setDosagemInicial(PedVendaItemFB.DOSAGEMINICIAL);
			pedVendaItemFB.setDosagemFinal(PedVendaItemFB.DOSAGEMFINAL);
			pedVendaItemFB.setPrecoMoeda(PedVendaItemFB.PRECOMOEDA);
			pedVendaItemFB.setQtdeEmbalagemFech(PedVendaItemFB.QTDEMBALAGEMFECH);
			pedVendaItemFB.setPrecoTabelaMoeda(PedVendaItemFB.PRECOTABELAMOEDA);
			pedVendaItemFB.setAliqPIS(produtoFB.getAliqPIS());
			pedVendaItemFB.setAliqCOFINS(produtoFB.getAliqCOFINS());
			pedVendaItemFB.setQtdMinPromo(PedVendaItemFB.QTDMINPROMO);
			pedVendaItemFB.setQtdMaxPromo(PedVendaItemFB.QTDMAXPROMO);
			pedVendaItemFB.setPrecoPromorIg(PedVendaItemFB.PRECOPROMORIG);
			pedVendaItemFB.setQuantNFCE(PedVendaItemFB.QUANTNFCE);
			
		}
		return pedVendaItemFB;
	}

	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId) {
		return this.pedVendaItemFBDAO.carregar(pedVendaFBId, produtoFBId);
	}
	
	public PedVendaItemFB carregar(Integer pedVendaFBId, Integer produtoFBId, Integer pedVendaCompostoId) {
		return this.pedVendaItemFBDAO.carregar(pedVendaFBId, produtoFBId, pedVendaCompostoId);
	}
	
	public void salvarProdComposto(FacesContext facesContext, Integer pedVendaFBId, PedVendaFB pedVendaFB, ProdutoFB produtoFB, PedVendaItemFB pedVendaItemFB, double alcada, boolean descPedido) throws RNException, DAOException {
		
		Integer pedVendaCompostoId = null;
		PedVendaCompostoFB pedVendaCompostoFB = new PedVendaCompostoFBRN().carregar(pedVendaFBId, produtoFB.getId());
		if(pedVendaCompostoFB!=null) {
			pedVendaCompostoId = pedVendaCompostoFB.getId();
			pedVendaCompostoFB.setQuantidade(pedVendaItemFB.getQuantidade());
			new PedVendaCompostoFBRN().updateQuantidade(pedVendaCompostoFB);
		}else {
			pedVendaCompostoFB = new PedVendaCompostoFBRN().novo(produtoFB);
			pedVendaCompostoFB.setQuantidade(pedVendaItemFB.getQuantidade());
			pedVendaCompostoId = new PedVendaCompostoFBRN().insert(pedVendaCompostoFB);
		}
		
		ProdutoFB prodItem = null;
		PedVendaItemFB item = null;
		
		for(ProdCompostoItemFBDTO rs : produtoFB.getComposicoes()) {
			
			prodItem = new ProdutoFBRN().carregar(pedVendaFB.getEncomenda(), rs.getProdutoId(), pedVendaFB.getEncomenda(), pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), pedVendaFB.getUsuarioId(), pedVendaFB.getTabPrecoId(), pedVendaFB.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, false, false, pedVendaFB.getFreteTipoId());
			
			if(prodItem==null) {
				throw new RNException("Algum item da composição está sem preço.");
			}
			
			if(prodItem.getNcmOpFisc()==0) {
				throw new RNException("Não existe Operação Fiscal para a NCM/Tipo de Operação: "+prodItem.getCodInterno()+" / "+pedVendaFB.getMovFiscTipo().getOpFiscTipoDesc()+ ".");
			}
			
			item = this.novo(pedVendaFB, prodItem, pedVendaCompostoId);
			item.setQuantidade(rs.getQuantidade() * pedVendaItemFB.getQuantidade());
			item.setPedVendaCompostoId(pedVendaCompostoId);
			item.setPreco(Funcoes.precoDesconto(item.getPrecoTabela(), pedVendaItemFB.getPercDesconto()));
			item.setPercDesconto(pedVendaItemFB.getPercDesconto());
			
			this.salvar(facesContext, pedVendaFBId, pedVendaFB, prodItem, item, alcada, descPedido);
		}
		
		
	}

	public void salvar(FacesContext facesContext, Integer pedVendaFBId, PedVendaFB pedVendaFB, ProdutoFB produtoFB, PedVendaItemFB pedVendaItemFB, double alcada, boolean descPedido) throws RNException {

		try {
			
			if(pedVendaFBId==null) {
				throw new RNException("Erro ao processar Pedido.");
			}
			
			if(produtoFB==null) {
				throw new RNException("Erro ao processar Produto do Pedido.");
			}
			
			if(pedVendaItemFB==null) {
				throw new RNException("Erro ao processar Item do Pedido.");
			}
			
			if((produtoFB.getEstoques()==null || produtoFB.getEstoques().size()==0) && pedVendaFB.getIsPedido() && pedVendaFB.getIsEncomenda()) {
				throw new RNException("Erro ao processar Pedido, Produto sem localidade selecionada.");
			}
			
			if(pedVendaItemFB.getQuantidade()<=0.0) {
				throw new RNException("Quantidade inválida.");
			}
			
			if(pedVendaItemFB.getId()!=null) {
				if(pedVendaItemFB.getQtdSaldoAtender()<pedVendaItemFB.getQuantOriginal()) { 
					throw new RNException("Item parcialmente atendido, não pode ser Alterado.");
				}else {
					pedVendaItemFB.setQtdSaldoAtender(pedVendaItemFB.getQuantidade());
					pedVendaItemFB.setQuantOriginal(pedVendaItemFB.getQuantidade());
				}
			}else {
				pedVendaItemFB.setQtdSaldoAtender(pedVendaItemFB.getQuantidade());
				pedVendaItemFB.setQuantOriginal(pedVendaItemFB.getQuantidade());
			}
			
			// Verificar se controla lote
			boolean isControlaLote = (produtoFB.getControlaLote().equals(ProdutoFB.PRODUTO_CONTROLA_LOTE));
			System.out.println("[PedVendaItemFBRN][salvar][produtoFB][ControlaLote]"+isControlaLote);
			
			// Verificar se produto vende sem estoque
			boolean isPermiteVendaSemEstoque = (produtoFB.getPermiteVendaSemEstoque().equals(ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE));
			System.out.println("[PedVendaItemFBRN][salvar][produtoFB][PermiteVendaSemEstoque]"+isPermiteVendaSemEstoque);
			
			if(pedVendaFB.getIsEncomenda() && isPermiteVendaSemEstoque==false) {
				throw new RNException("O Produto não pode ser vendido por Encomenda.");
			}
			
			// Verificar Estoque Esta disponivel
			String varLoteCod = "";
			int lotesDiffs = -1;
			int vendaSemEstoque = 0;
			
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			
			// Distribui qtd dos Itens Compostos para as Localidade com qtd disponivel
			if(pedVendaFB.getIsProdComposto()) {
				Double qtdPedVendaItem = pedVendaItemFB.getQuantidade();
				List<ProdutoEstoqueFB> estoque = produtoEstoqueFBRN.listar(PedVendaFB.PEDIDO, pedVendaFB.getEmpresaId(), pedVendaFB.getUsuarioId(), produtoFB.getId(), produtoFB.getControlaLote(), Funcoes.SO_ESTOQUE, produtoFB.getPermiteVendaSemEstoque());
				
				for(ProdutoEstoqueFB rs : estoque) {
					
					rs.setQtdReservar(0.0); // inicia zerado
					
					Double qtdReserva = new ReservaFBRN().qtdReservada(pedVendaItemFB, rs.getLocalidadeId(), rs.getProdutoLoteId(), produtoFB.getControlaLote());
					if(qtdReserva!=null) {
						rs.setQtdDisponivel(rs.getQtdDisponivel()+qtdReserva);
					}
					
					if(rs.getQtdDisponivel() > 0d && qtdPedVendaItem > 0d) {
						if(rs.getQtdDisponivel() < qtdPedVendaItem) {
							Double qtdAplicarReserva = (qtdPedVendaItem - rs.getQtdDisponivel());
							rs.setQtdReservar(qtdAplicarReserva);
							qtdPedVendaItem = (qtdPedVendaItem - qtdAplicarReserva);
						}else if(rs.getQtdDisponivel()>=qtdPedVendaItem) {
							rs.setQtdReservar(qtdPedVendaItem);
							qtdPedVendaItem = 0.0; 
						}
					}
				}
				
				produtoFB.setEstoques(estoque);
			}
			
				Double qtdReservada = 0.0;
				
				for(ProdutoEstoqueFB rs : produtoFB.getEstoques()) {
					if(rs.getQtdReservar() != null && rs.getQtdReservar().doubleValue() > 0.0) {
						qtdReservada += rs.getQtdReservar();
						if(pedVendaFB.getIsEncomenda()==false) {
							
							ProdutoEstoqueFB produtoEstoqueFB = null;
							produtoEstoqueFB = produtoEstoqueFBRN.carregar(pedVendaFB.getUsuarioId(), produtoFB.getControlaLote(), rs, Funcoes.SO_ESTOQUE);
							
							Double qtdDisponivel = 0.0;							
							if(produtoEstoqueFB != null) {
								qtdDisponivel =  produtoEstoqueFB.getQtdDisponivel();
							}
							
							Double reservada = new ReservaFBRN().qtdReservada(pedVendaItemFB, rs.getLocalidadeId(), rs.getProdutoLoteId(), produtoFB.getControlaLote());
							if(rs.getQtdReservar().doubleValue() > (qtdDisponivel + reservada)) {
								rs.setQtdDisponivel(qtdDisponivel);
								throw new RNException("Quantidade não disponível para a Localidade "+rs.getLocalidadeId()+" "+rs.getLocalidadeDesc()+".");
							}
						
							if(!varLoteCod.equals(rs.getCodLote())) {
								lotesDiffs++;
								varLoteCod = rs.getCodLote() == null ? "" : rs.getCodLote();
							}
							
						}else{
							vendaSemEstoque++;
						}
					}
				}
				
			// Peso Liquido e Bruto
			pedVendaItemFB.setPesoBrutoKg(produtoFB.getPesoBrutoKg()*qtdReservada); 
			pedVendaItemFB.setPesoLiquidoKg(produtoFB.getPesoLiquidoKg()*qtdReservada);
			
			produtoEstoqueFBRN.bloqueEstoque(pedVendaFB.getEmpresaId(), produtoFB.getId());
			
			// Comissao
			// De acordo com o parametro da empresa vai buscar a comiss�o por produto ou por linha de produto(faixa) 
			EmpresaFB empresa = new EmpresaFBRN().carregar(pedVendaFB.getEmpresaId());
			Double percComissao = new ProdutoFBRN().comissao(empresa.getTipoComissao(), pedVendaFB.getVendedorId(), pedVendaItemFB.getProdutoId(), pedVendaItemFB.getPercDesconto());
			pedVendaItemFB.setComissao(percComissao);

			// Custo Medio
			pedVendaItemFB.setCustoGerUltCompraUv(produtoFB.getCustoMedioOnline());
			
			// Salvar Item
			pedVendaItemFB.setPedVendaId(pedVendaFBId);
			Integer pedVendaItemFBId = this.salvar(pedVendaItemFB);
			pedVendaItemFB.setId(pedVendaItemFBId);
			
			// Salvar Reserva / Reserva Lote
			if(pedVendaFB.getIsEncomenda()) {
				ReservaFilaFBRN reservaFilaFBRN = new ReservaFilaFBRN();
				for(ProdutoEstoqueFB rs : produtoFB.getEstoques()) {
					reservaFilaFBRN.salvar(pedVendaFB.getEmpresaId(), pedVendaFBId, pedVendaItemFBId, rs.getProdutoId(), rs.getQtdReservar());
				}
			}else if(pedVendaFB.getIsPedido() || pedVendaFB.getIsProdComposto()){
				ReservaFBRN reservaFBRN = new ReservaFBRN();
				reservaFBRN.salvar(pedVendaFBId, pedVendaItemFBId, produtoFB.getEstoques(), isControlaLote);
			}
			
			// Divergencias
			PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();

			// Salvar Divergencias Desconto
			if(descPedido==false) {
				pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
				if(pedVendaItemFB.getPercDesconto()>alcada) {
					boolean divergCriada = pedVendaDivergFBRN.bloqueioDesconto(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getCondPagtoId(), pedVendaItemFB.getPercDesconto(), pedVendaFB.getUsuarioId());
					
					if(facesContext!=null && divergCriada==true) {
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", String.format(UtilMessage.mensagem("msg.salvo.pedvendaitem.desconto.bloqueado"), produtoFB.getCodInterno())));
					}
				}
			}
			
			// Salvar Divergencias Lote
			pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES);
			if(lotesDiffs > 0 && isControlaLote) {
				boolean divergCriada = pedVendaDivergFBRN.bloqueioLote(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getUsuarioId());
				
				if(facesContext!=null && divergCriada==true) {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", UtilMessage.mensagem("msg.salvo.pedvendaitem.lote.bloqueado")));
				}
			}
			
			// Salvar Divergencia Venda Sem Estoque
			pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP);
			if(vendaSemEstoque > 0) {
				boolean divergCriada = pedVendaDivergFBRN.bloqueioVendaSemEstoqueDisp(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getUsuarioId());
				
				if(facesContext!=null && divergCriada==true) {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", UtilMessage.mensagem("msg.salvo.pedvendaitem.vendasemestoquedisp.bloqueado")));
				}
			}
			
//			System.out.println("PRODUTO ID: " + pedVendaItemFB.getProdutoId());
//			System.out.println("Fim salvar");
			
		} catch (Exception e) {
			System.out.println("[PedVendaItemFBRN][salvar][Exception]");
			throw new RNException(e.getMessage());
		}
		
	}

	public Integer salvar(PedVendaItemFB pedVendaItemFB) throws RNException {
		try {
			
			boolean isExistePedVendaItemFB = false;
			if(pedVendaItemFB.getId()!=null) {
				isExistePedVendaItemFB = pedVendaItemFB.getId()!=null; //(this.carregar(pedVendaItemFB.getPedVendaId(), pedVendaItemFB.getProdutoId())!=null);
			}
			
			System.out.println("[PedVendaItemFBRN][salvar][pedVendaItemFB][Existe]"+isExistePedVendaItemFB);
			
			if(pedVendaItemFB.getPreco().equals(pedVendaItemFB.getPrecoTabela())) {
				pedVendaItemFB.setValorDesconto(0.0);
			}else {
				pedVendaItemFB.setValorDesconto((pedVendaItemFB.getPrecoTabela() - pedVendaItemFB.getPreco())); //*pedVendaItemFB.getQuantidade()
			}
			
			pedVendaItemFB.setQuantidadeEnf(pedVendaItemFB.getQuantidade());
			
			if(isExistePedVendaItemFB) {
				return this.update(pedVendaItemFB);
			}else {
				return this.insert(pedVendaItemFB);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException(e.getMessage());
		}
	}

	private Integer insert(PedVendaItemFB pedVendaItemFB) throws DAOException {
		return this.pedVendaItemFBDAO.insert(pedVendaItemFB);
	}

	private Integer update(PedVendaItemFB pedVendaItemFB) throws DAOException {
		return this.pedVendaItemFBDAO.update(pedVendaItemFB);
	}
	
	public void excluir(PedVendaItemFB pedVendaItemFB, Integer usuarioId) throws RNException, DAOException{
		new PedVendaFBRN().verificarPedido(pedVendaItemFB.getPedVendaId());
		PedVendaItemFB verItem = this.carregar(pedVendaItemFB.getId());
		if(verItem.getQtdSaldoAtender()<verItem.getQuantOriginal()){
			throw UtilMessage.exceptionMensagem("msg.excluido.erro.qtdatendida_maior_zero.pedvendaitem", null);
		}
		
		ReservaFBRN reservaFBRN = new ReservaFBRN();
		reservaFBRN.excluir(pedVendaItemFB.getId());
		
		PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
		pedVendaDivergFBRN.excluir(pedVendaItemFB.getPedVendaId(), pedVendaItemFB.getId());
		
		this.pedVendaItemFBDAO.excluir(verItem, usuarioId);
	}

	public PedVendaItemFB editar(Integer pedVendaItemId, ProdutoFB produtoSelecionada, Integer encomenda) {
		PedVendaItemFB pedVendaItem = this.carregar(pedVendaItemId);
		if(encomenda.equals(PedVendaFB.ENCOMENDA)) {
			updateQtdResevada(produtoSelecionada.getEstoques(), pedVendaItem.getQuantidade());
		}else if(encomenda.equals(PedVendaFB.PEDIDO)) {
			if(produtoSelecionada.getControlaLote()==ProdutoFB.PRODUTO_CONTROLA_LOTE) {
				ReservaLoteFBRN reservaLoteFBRN = new ReservaLoteFBRN();
				List<ReservaLoteFB> loteFBs = reservaLoteFBRN.listar(pedVendaItem);
				for(ReservaLoteFB rs : loteFBs) {
					updateQtdResevada(produtoSelecionada.getEstoques(), rs);
				}
			}else {
				ReservaFBRN reservaFBRN = new ReservaFBRN();
				List<ReservaFB> reservaFBs  = reservaFBRN.listar(pedVendaItem);
				for(ReservaFB rs : reservaFBs) {
					updateQtdResevada(produtoSelecionada.getEstoques(), rs);
				}
			}
		}else if(encomenda.equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			
		}
		
		return pedVendaItem;
	}
	
	public void updateQtdResevada(List<ProdutoEstoqueFB> estoque, ReservaLoteFB reservaLoteFB) {
		for(ProdutoEstoqueFB rs : estoque) {
			if(reservaLoteFB.getLocalidadeId().equals(rs.getLocalidadeId()) && reservaLoteFB.getProdutoLoteId().equals(rs.getProdutoLoteId())) {
				rs.setQtdReservar(reservaLoteFB.getQuantidade());
				rs.setQtdDisponivel(rs.getQtdDisponivel() + reservaLoteFB.getQuantidade());
			}
		}
	}

	public void updateQtdResevada(List<ProdutoEstoqueFB> estoque, ReservaFB reservaFB) {
		for(ProdutoEstoqueFB rs : estoque) {
			if(reservaFB.getLocalidadeId().equals(rs.getLocalidadeId())){
				rs.setQtdReservar(reservaFB.getQuantidade());
				rs.setQtdDisponivel(rs.getQtdDisponivel() + reservaFB.getQuantidade());
			}
		}
	}
	
	public void updateQtdResevada(List<ProdutoEstoqueFB> estoque, Double quantidade) {
		for(ProdutoEstoqueFB rs : estoque) {
			rs.setQtdReservar(quantidade);
		}
	}

	public void updatePreco(PedVendaItemFBDTO pedVendaItemFBDTO) throws DAOException {
		this.pedVendaItemFBDAO.updatePreco(pedVendaItemFBDTO);
	}

	public PedVendaItemFB novoPedTransf(PedVendaFB pedVendaFB, ProdutoFB produtoFB, Integer pedVendaCompostoId) {
		PedVendaItemFB pedVendaItemFB = null;
		
		// verificar se item existe no pedido
		if(pedVendaFB.getId()!=null && (pedVendaFB.getIsPedido() || pedVendaFB.getIsEncomenda())) {
			pedVendaItemFB = this.carregar(pedVendaFB.getId(), produtoFB.getId());
		}else if(pedVendaCompostoId!=null && pedVendaFB.getIsProdComposto()) {
			pedVendaItemFB = this.carregar(pedVendaFB.getId(), produtoFB.getId(), pedVendaCompostoId);
		}
		
		if(pedVendaItemFB==null) {
			pedVendaItemFB = new PedVendaItemFB();
			pedVendaItemFB.setProdutoId(produtoFB.getId());
			pedVendaItemFB.setUnidade(produtoFB.getUnidadeId());
			if(pedVendaFB!=null) {
				pedVendaItemFB.setPedVendaId(pedVendaFB.getId());
			}
			
			pedVendaItemFB.setQuantidade(0.0);
			pedVendaItemFB.setPercDesconto(0.0);
			pedVendaItemFB.setPreco(produtoFB.getCustoMedioOnline());
			pedVendaItemFB.setPrecoTabela(produtoFB.getCustoMedioOnline());
			pedVendaItemFB.setPrecoProm(produtoFB.getCustoMedioOnline());
			
			pedVendaItemFB.setPesoLiquidoKg(0.0);
			pedVendaItemFB.setPesoBrutoKg(0.0);
			pedVendaItemFB.setComissao(PedVendaItemFB.COMISSAO);
			pedVendaItemFB.setCustoGerUltCompra(PedVendaItemFB.CUSTOGERULTCOMPRA);
			pedVendaItemFB.setCustoEmbalagem(PedVendaItemFB.CUSTOEMBALAGEM);
			pedVendaItemFB.setCustoTerceirizacao(PedVendaItemFB.CUSTOTERCEIRIZACAO);
			pedVendaItemFB.setCustoFreteUnit(PedVendaItemFB.CUSTOFRETEUNIT);
			pedVendaItemFB.setMkUltAtual(PedVendaItemFB.MKUPATUAL);
			pedVendaItemFB.setPrecoSugeridoVenda(PedVendaItemFB.PRECOSUGERIDOVENDA);
			pedVendaItemFB.setMkUpCalculado(PedVendaItemFB.MKUPCALCULADO);
			pedVendaItemFB.setValorComissao(PedVendaItemFB.VALORCOMISSAO);
			pedVendaItemFB.setQuantidadeEnf(PedVendaItemFB.QUANTIDADENF);
			pedVendaItemFB.setAssocNfItem(PedVendaItemFB.ASSOCNFITEM);
			pedVendaItemFB.setQtdSaldoAtender(PedVendaItemFB.QTDSALDOATENDER);
			pedVendaItemFB.setValorDesconto(PedVendaItemFB.VALORDESCONTO);
			pedVendaItemFB.setIdTributICMS(PedVendaItemFB.ID_TRIBUTICMS);
			pedVendaItemFB.setmVast(PedVendaItemFB.MVAST);
			pedVendaItemFB.setValorSTUnit(PedVendaItemFB.VALORSTUNIT);
			pedVendaItemFB.setCustoGerUltCompraUv(PedVendaItemFB.CUSTOGERULTCOMPRAUV);
			pedVendaItemFB.setQuantOriginal(PedVendaItemFB.QUANTORIGINAL);
			pedVendaItemFB.setAtuCCVendedor(PedVendaItemFB.ATUCCVENDEDOR);
			pedVendaItemFB.setSeqPedVendaItem(PedVendaItemFB.SEQ_PEDVENDAITEM);
			pedVendaItemFB.setAliqIPI(PedVendaItemFB.ALIQIPI);
			pedVendaItemFB.setValIPI(PedVendaItemFB.VALIPI);
			pedVendaItemFB.setComissaoFabr(PedVendaItemFB.COMISSAO_FABR);
			pedVendaItemFB.setPrecoRefCCVendedor(PedVendaItemFB.PRECOREFCCVENDEDOR);
			pedVendaItemFB.setAliqICMSSt(PedVendaItemFB.ALIQICMSST);
			pedVendaItemFB.setAliqICMS(produtoFB.getAliqICMS());
			pedVendaItemFB.setDosagemInicial(PedVendaItemFB.DOSAGEMINICIAL);
			pedVendaItemFB.setDosagemFinal(PedVendaItemFB.DOSAGEMFINAL);
			pedVendaItemFB.setPrecoMoeda(PedVendaItemFB.PRECOMOEDA);
			pedVendaItemFB.setQtdeEmbalagemFech(PedVendaItemFB.QTDEMBALAGEMFECH);
			pedVendaItemFB.setPrecoTabelaMoeda(PedVendaItemFB.PRECOTABELAMOEDA);
			pedVendaItemFB.setAliqPIS(produtoFB.getAliqPIS());
			pedVendaItemFB.setAliqCOFINS(produtoFB.getAliqCOFINS());
			pedVendaItemFB.setQtdMinPromo(PedVendaItemFB.QTDMINPROMO);
			pedVendaItemFB.setQtdMaxPromo(PedVendaItemFB.QTDMAXPROMO);
			pedVendaItemFB.setPrecoPromorIg(PedVendaItemFB.PRECOPROMORIG);
			pedVendaItemFB.setQuantNFCE(PedVendaItemFB.QUANTNFCE);
			
		}
		return pedVendaItemFB;
	}

	public void salvarItemPedTransf(FacesContext facesContext, Integer pedVendaFBId, PedVendaFB pedVendaFB, ProdutoFB produtoFB, PedVendaItemFB pedVendaItemFB, double alcada, boolean descPedido) throws RNException {

		try {
			
			if(pedVendaFBId==null) {
				throw new RNException("Erro ao processar Pedido.");
			}
			
			if(produtoFB==null) {
				throw new RNException("Erro ao processar Produto do Pedido.");
			}
			
			if(pedVendaItemFB==null) {
				throw new RNException("Erro ao processar Item do Pedido.");
			}
			
			if((produtoFB.getEstoques()==null || produtoFB.getEstoques().size()==0) && pedVendaFB.getIsPedido() && pedVendaFB.getIsEncomenda()) {
				throw new RNException("Erro ao processar Pedido, Produto sem localidade selecionada.");
			}
			
			if(pedVendaItemFB.getQuantidade()<=0.0) {
				throw new RNException("Quantidade inválida.");
			}
			
			if(pedVendaItemFB.getId()!=null) {
				if(pedVendaItemFB.getQtdSaldoAtender()<pedVendaItemFB.getQuantOriginal()) { 
					throw new RNException("Item parcialmente atendido, não pode ser Alterado.");
				}else {
					pedVendaItemFB.setQtdSaldoAtender(pedVendaItemFB.getQuantidade());
					pedVendaItemFB.setQuantOriginal(pedVendaItemFB.getQuantidade());
				}
			}else {
				pedVendaItemFB.setQtdSaldoAtender(pedVendaItemFB.getQuantidade());
				pedVendaItemFB.setQuantOriginal(pedVendaItemFB.getQuantidade());
			}
			
			// Verificar se controla lote
			boolean isControlaLote = (produtoFB.getControlaLote().equals(ProdutoFB.PRODUTO_CONTROLA_LOTE));
			System.out.println("[PedVendaItemFBRN][salvar][produtoFB][ControlaLote]"+isControlaLote);
			
			// Verificar se produto vende sem estoque
			boolean isPermiteVendaSemEstoque = (produtoFB.getPermiteVendaSemEstoque().equals(ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE));
			System.out.println("[PedVendaItemFBRN][salvar][produtoFB][PermiteVendaSemEstoque]"+isPermiteVendaSemEstoque);
			
			if(pedVendaFB.getIsEncomenda() && isPermiteVendaSemEstoque==false) {
				throw new RNException("O Produto não pode ser vendido por Encomenda.");
			}
			
			// Verificar Estoque Esta disponivel
			String varLoteCod = "";
			int lotesDiffs = -1;
			int vendaSemEstoque = 0;
			
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			
			// Distribui qtd dos Itens Compostos para as Localidade com qtd disponivel
			if(pedVendaFB.getIsProdComposto()) {
				Double qtdPedVendaItem = pedVendaItemFB.getQuantidade();
				List<ProdutoEstoqueFB> estoque = produtoEstoqueFBRN.listar(PedVendaFB.PEDIDO, pedVendaFB.getEmpresaId(), pedVendaFB.getUsuarioId(), produtoFB.getId(), produtoFB.getControlaLote(), Funcoes.SO_ESTOQUE, produtoFB.getPermiteVendaSemEstoque());
				
				for(ProdutoEstoqueFB rs : estoque) {
					
					rs.setQtdReservar(0.0); // inicia zerado
					
					Double qtdReserva = new ReservaFBRN().qtdReservada(pedVendaItemFB, rs.getLocalidadeId(), rs.getProdutoLoteId(), produtoFB.getControlaLote());
					if(qtdReserva!=null) {
						rs.setQtdDisponivel(rs.getQtdDisponivel()+qtdReserva);
					}
					
					if(rs.getQtdDisponivel() > 0d && qtdPedVendaItem > 0d) {
						if(rs.getQtdDisponivel() < qtdPedVendaItem) {
							Double qtdAplicarReserva = (qtdPedVendaItem - rs.getQtdDisponivel());
							rs.setQtdReservar(qtdAplicarReserva);
							qtdPedVendaItem = (qtdPedVendaItem - qtdAplicarReserva);
						}else if(rs.getQtdDisponivel()>=qtdPedVendaItem) {
							rs.setQtdReservar(qtdPedVendaItem);
							qtdPedVendaItem = 0.0; 
						}
					}
				}
				
				produtoFB.setEstoques(estoque);
			}
			
				Double qtdReservada = 0.0;
				
				for(ProdutoEstoqueFB rs : produtoFB.getEstoques()) {
					if(rs.getQtdReservar() != null && rs.getQtdReservar().doubleValue() > 0.0) {
						qtdReservada += rs.getQtdReservar();
						if(pedVendaFB.getIsEncomenda()==false) {
							
							ProdutoEstoqueFB produtoEstoqueFB = null;
							produtoEstoqueFB = produtoEstoqueFBRN.carregarTodos(pedVendaFB.getUsuarioId(), produtoFB.getControlaLote(), rs, Funcoes.IS_TRANSFERENCIA);
							
							Double qtdDisponivel = 0.0;							
							if(produtoEstoqueFB != null) {
								qtdDisponivel =  produtoEstoqueFB.getQtdDisponivel();
							}
							if(qtdDisponivel == 0.0) {
								qtdDisponivel = rs.getQtdDisponivel();
							}
							
							Double reservada = new ReservaFBRN().qtdReservada(pedVendaItemFB, rs.getLocalidadeId(), rs.getProdutoLoteId(), produtoFB.getControlaLote());
							if(rs.getQtdReservar().doubleValue() > (qtdDisponivel + reservada)) {
								rs.setQtdDisponivel(qtdDisponivel);
								throw new RNException("Quantidade não disponível para a Localidade "+rs.getLocalidadeId()+" "+rs.getLocalidadeDesc()+".");
							}
						
							if(!varLoteCod.equals(rs.getCodLote())) {
								lotesDiffs++;
								varLoteCod = rs.getCodLote() == null ? "" : rs.getCodLote();
							}
							
						}else{
							vendaSemEstoque++;
						}
					}
				}
				
			// Peso Liquido e Bruto
			pedVendaItemFB.setPesoBrutoKg(produtoFB.getPesoBrutoKg()*qtdReservada); 
			pedVendaItemFB.setPesoLiquidoKg(produtoFB.getPesoLiquidoKg()*qtdReservada);
			
			produtoEstoqueFBRN.bloqueEstoque(pedVendaFB.getEmpresaId(), produtoFB.getId());
			
			// Comissao
			// De acordo com o parametro da empresa vai buscar a comiss�o por produto ou por linha de produto(faixa) 
			EmpresaFB empresa = new EmpresaFBRN().carregar(pedVendaFB.getEmpresaId());
			Double percComissao = new ProdutoFBRN().comissao(empresa.getTipoComissao(), pedVendaFB.getVendedorId(), pedVendaItemFB.getProdutoId(), pedVendaItemFB.getPercDesconto());
			pedVendaItemFB.setComissao(percComissao);

			// Custo Medio
			pedVendaItemFB.setCustoGerUltCompraUv(produtoFB.getCustoMedioOnline());
			
			// Salvar Item
			pedVendaItemFB.setPedVendaId(pedVendaFBId);
			Integer pedVendaItemFBId = this.salvar(pedVendaItemFB);
			pedVendaItemFB.setId(pedVendaItemFBId);
			
			// Salvar Reserva / Reserva Lote
			if(pedVendaFB.getIsEncomenda()) {
				ReservaFilaFBRN reservaFilaFBRN = new ReservaFilaFBRN();
				for(ProdutoEstoqueFB rs : produtoFB.getEstoques()) {
					reservaFilaFBRN.salvar(pedVendaFB.getEmpresaId(), pedVendaFBId, pedVendaItemFBId, rs.getProdutoId(), rs.getQtdReservar());
				}
			}else if(pedVendaFB.getIsPedido() || pedVendaFB.getIsProdComposto()){
				ReservaFBRN reservaFBRN = new ReservaFBRN();
				reservaFBRN.salvar(pedVendaFBId, pedVendaItemFBId, produtoFB.getEstoques(), isControlaLote);
			}
			
			// Divergencias
//			PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();

			// Salvar Divergencias Desconto
//			if(descPedido==false) {
//				pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO);
//				if(pedVendaItemFB.getPercDesconto()>alcada) {
//					boolean divergCriada = pedVendaDivergFBRN.bloqueioDesconto(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getCondPagtoId(), pedVendaItemFB.getPercDesconto(), pedVendaFB.getUsuarioId());
//					
//					if(facesContext!=null && divergCriada==true) {
//						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", String.format(UtilMessage.mensagem("msg.salvo.pedvendaitem.desconto.bloqueado"), produtoFB.getCodInterno())));
//					}
//				}
//			}
			
			// Salvar Divergencias Lote
//			pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES);
//			if(lotesDiffs > 0 && isControlaLote) {
//				boolean divergCriada = pedVendaDivergFBRN.bloqueioLote(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getUsuarioId());
//				
//				if(facesContext!=null && divergCriada==true) {
//					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", UtilMessage.mensagem("msg.salvo.pedvendaitem.lote.bloqueado")));
//				}
//			}
			
			// Salvar Divergencia Venda Sem Estoque
//			pedVendaDivergFBRN.excluir(pedVendaFBId, pedVendaItemFBId, PedVendaDivergFB.SITUACAO_EM_ABERTO, PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP);
//			if(vendaSemEstoque > 0) {
//				boolean divergCriada = pedVendaDivergFBRN.bloqueioVendaSemEstoqueDisp(pedVendaFBId, pedVendaItemFB.getId(), pedVendaFB.getUsuarioId());
//				
//				if(facesContext!=null && divergCriada==true) {
//					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Divergência", UtilMessage.mensagem("msg.salvo.pedvendaitem.vendasemestoquedisp.bloqueado")));
//				}
//			}
			
//			System.out.println("PRODUTO ID: " + pedVendaItemFB.getProdutoId());
//			System.out.println("Fim salvar");
			
		} catch (Exception e) {
			System.out.println("[PedVendaItemFBRN][salvar][Exception]");
			throw new RNException(e.getMessage());
		}
		
	}		
}