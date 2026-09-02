package br.com.webapp.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import br.com.webapp.model.configuracao.Configuracao;
import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFB;
import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFBRN;
import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.cobrtipo.CobrTipoFB;
import br.com.webapp.model.fb.cobrtipo.CobrTipoFBRN;
import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBRN;
import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBRN;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFBRN;
import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFB;
import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.dto.PedVendaDivergFBDTO;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFB;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFBRN;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTORN;
import br.com.webapp.model.fb.pedvendaitemprodlote.dto.PedVendaItemProdLoteDTORN;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFBRN;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBRN;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.produto.datamodel.ProdutoFBLazyDM;
import br.com.webapp.model.fb.tabpreco.TabPrecoFB;
import br.com.webapp.model.fb.tabpreco.TabPrecoFBRN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="pedTransfNovoBean")
@SessionScoped
public class PedTransfNovoBean implements Serializable {

	private static final long serialVersionUID = -7807824809032896640L;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	@ManagedProperty(value="#{calcularPisoBean}")
	private CalcularPisoBean calcularPisoBean;
	
	//Controllers
	private int stepIndex;
	private boolean renderedStep;
	private boolean podeMudarVendedor;
	private boolean podeMudarEmpresa;
	private boolean podeCadastrarCliente;
	private Integer encomenda;
	
	private List<SelectItem> empresaSelectItem;
	private List<SelectItem> vendedorSelectItem;
	private List<SelectItem> condPagtoSelectItem;
	private List<SelectItem> freteTipoSelectItem;
	private List<SelectItem> formaPagtoSelectItem;
	private List<SelectItem> tipoMovFiscSelectItem;
	
	private Integer selecionadaId;
	private PedVendaFB selecionada;
	private PedVendaItemFB pedVendaItem;
	private List<PedVendaItemFBDTO> listaPedVendaItem;
	private List<PedVendaDivergFBDTO> listaDivergencias;
	private List<PedVendaCompostoFB> listaPedVendaComposto;
	private List<PedVendaCartaoFB> listaPedVendaCartao;
	
	private EmpresaFB empresa;
	private VendedorFB vendedor;
	private ClienteFB cliente;
	private ClienteFB clienteModif;
	
	// Produto - Filter
	private String descProdFilter;
	private String codBarraFilter;
	private String fabricanteFilter;
	private String linhaProdFilter;
	private boolean filterPorCodBarra;
	private boolean comEstoqueFilter;
	private boolean semEstoqueFilter;
	private boolean openResultListaProdutos;
	
	private AlcadaCondPagtoFB alcada;
	private double descItem;
	private ProdutoFB produtoSelecionada;
	private LazyDataModel<ProdutoFB> listaProdutosLDM;
	
	private boolean msgCondPagto;
	
	private boolean descPedido;
	
	private boolean renderedBtnEncomenda;
	private boolean renderedBtnComposto;
	private boolean renderedSelectFreteTipo;
	private boolean renderedSelectFormaPagto;
	private boolean renderedSelectMovFiscTipo;
	
	// Cliente Filter
	private List<ClienteFB> listaCli;
	private String descricaoFilterCli;
	
	@PostConstruct
	public void init(){
		renderedBtnComposto = false;
		renderedBtnEncomenda = false;
		renderedSelectFreteTipo = false;
		renderedSelectFormaPagto = false;
		renderedSelectMovFiscTipo = false;
		if(contextoBean.getConfiguracoes() != null) {
			try {
				renderedBtnComposto = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_COMPOSTO))).isAtivo();
				renderedBtnEncomenda = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_ENCOMENDA))).isAtivo();
				renderedSelectFreteTipo = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_PODE_MUDAR_FRETE_TIPO))).isAtivo();
				renderedSelectFormaPagto = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_PODE_MUDAR_FORMA_PAGTO))).isAtivo();
				renderedSelectMovFiscTipo = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_PODE_MUDAR_TIPO_MOV_FISC))).isAtivo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void buscarCli() {
		listaCli = new ClienteFBRN().listarClienteTransferencia(descricaoFilterCli, Funcoes.IS_TRANSFERENCIA);
	}
	
	public void limparFilterCli() {
		clienteModif = null;
		
		listaCli = null;
		descricaoFilterCli = null;
	}
	
	private void limparListas() {
		empresaSelectItem = null;
		vendedorSelectItem = null;
		condPagtoSelectItem = null;
		freteTipoSelectItem = null;
		formaPagtoSelectItem = null;
		tipoMovFiscSelectItem = null;

		listaPedVendaItem = null;
		listaPedVendaComposto = null;
		listaDivergencias = null;
		listaPedVendaCartao = null;

	}
	
	private void limparFilterProduto() {
		descProdFilter = null;
		fabricanteFilter = null;
		linhaProdFilter = null;
		codBarraFilter = null;
		produtoSelecionada = null;
		comEstoqueFilter = true;
		semEstoqueFilter = false;
	}
	
	private void limparProdutoSelected() {
		listaDivergencias = null;
		listaPedVendaItem = null;
		produtoSelecionada = null;
		pedVendaItem = null;
		descItem = 0.0;
	}
	
	private void verTipoDescontoPedido() {
		descPedido = Funcoes.PADRAO_DE_DESCONTO_NO_PEDIDO;
	}
	
	public void novoCalculoPiso() {
		calcularPisoBean.setQtdVendaAtac(produtoSelecionada.getQtdVendaAtac());
		calcularPisoBean.setQtdDecimal(produtoSelecionada.getQtdDecimal());
	}
	
	public void novoPedTransf() {
		verTipoDescontoPedido();
		
		encomenda = PedVendaFB.PEDIDO;
		novo();
	}

	private void novo() {
		
		selecionadaId = null;
		stepIndex = 0;
		renderedStep = true;
		podeCadastrarCliente = false;
		msgCondPagto = false;
		
		filterPorCodBarra = false;
		
		selecionada = new PedVendaFB();
		selecionada.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
		selecionada.setDesconto(0.0);
		cliente = new ClienteFB();
		cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);
		
		if(contextoBean.getUsuarioLogado().getVendedor()!=null) {
			vendedor = contextoBean.getUsuarioLogado().getVendedor();
		}
		
		if(contextoBean.getUsuarioLogado().getEmpresas()!=null){
			empresa = contextoBean.getUsuarioLogado().getEmpresas().get(0);
		}
		
		limparFilterCli();
		limparFilterProduto();
		limparListas();
		limparProdutoSelected();
		closeResultListaProduto();
		
	}
	
	public void editar() {
		if(selecionadaId!=null) {
			
			verTipoDescontoPedido();
			
			renderedStep = true;
			filterPorCodBarra = false;
			podeCadastrarCliente = false;
			msgCondPagto = false;
			
			closeResultListaProduto();
			limparFilterProduto();
			limparListas();
			limparProdutoSelected();
			
			selecionada = new PedVendaFBRN().carregar(selecionadaId);
			if(selecionada!=null) {
				cliente = new ClienteFBRN().carregar(selecionada.getClienteId());
				vendedor = new VendedorFBRN().carregar(selecionada.getVendedorId());
				empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());
				
				CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(selecionada.getCondPagtoId());
				TabPrecoFB tabPreco = new TabPrecoFBRN().carregar(selecionada.getTabPrecoId());
				PedVendaStatusFB pedVendaStatusFB = new PedVendaStatusFBRN().carregar(selecionada.getPedVendaStatusId());
				
				FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(selecionada.getFreteTipoId());
				MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(selecionada.getMovFiscTipoId());
				FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(selecionada.getFormaPagtoId());
				CobrTipoFB cobrTipoFB = new CobrTipoFBRN().carregar(selecionada.getCobrTipoId());
				
				selecionada.setCondPagto(condPagtoFB);
				selecionada.setTabPreco(tabPreco);
				selecionada.setPedVendaStatus(pedVendaStatusFB);
				selecionada.setFreteTipo(freteTipoFB);
				selecionada.setMovFiscTipo(movFiscTipoFB);
				selecionada.setFormaPagto(formaPagtoFB);
				selecionada.setCobrTipo(cobrTipoFB);
				
				openItens();
				
				verAlcada();
				buscarItensPedido();
				buscarDivergencias();
				
				listaPedVendaCartao = new PedVendaCartaoFBRN().listar(selecionadaId);
			}else {
				novoPedTransf();
			}
		}
	}
	
	public void novoCliente() {
		cliente = new ClienteFB();
		cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);

		if(empresa.getIdClientePadraoFDL()!=null) {
			ClienteFB clientePadrao = new ClienteFBRN().carregar(empresa.getIdClientePadraoFDL());
			if(clientePadrao!=null) {
				cliente.setTipoPessoa(clientePadrao.getTipoPessoa());
				cliente.setCnpjCpf(clientePadrao.getCnpjCpf());
			}
		}
		
	}

	
	public void visualizar() {
		editar();
		renderedStep = false;
	}
	
	public void excluir() {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.excluir(selecionadaId, contextoBean.getUsuarioLogado().getId());
			
			novoPedTransf();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	public void openCliente() {
		stepIndex = 0;
		
	}
	
	public void openItens() {
		stepIndex = 1;
	}
	
	public void openCondPagto() {
		stepIndex = 2;
	}
	
	public void openConcluir() {
		stepIndex = 3;
	}
	
	public void verificarEmpresa() {}
	
	public void verificarVendedor() {}
	
	public void verificarTipoPessoa(){
		podeCadastrarCliente = false;
	}
	
	public void verificarCondPagto(){
		if(selecionada.getCondPagto()!=null){
			try {
				
				msgCondPagto = true;
				
				System.out.println("Alcada: " + alcada.getAlcada());
				
				selecionada.setCondPagtoId(selecionada.getCondPagto().getId());
				selecionada.setTabPreco(new TabPrecoFBRN().carregar(selecionada.getCondPagto().getTabPrecoId()));
				selecionada.setTabPrecoId(selecionada.getCondPagto().getTabPrecoId());
				
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					alcada = pedVendaFBRN.mudarCondPagto(FacesContext.getCurrentInstance(), selecionada, alcada, contextoBean.getUsuarioLogado(), descPedido);
					
					buscarItensPedido();
					buscarDivergencias();
				}
				
			} catch (RNException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
			
			if(openResultListaProdutos) {
				buscarProduto();
			}else if(produtoSelecionada != null && pedVendaItem != null){
				cancelarItemProduto();
			}
		}
	}
	
	public void verificarFormaPagto(){
		if(selecionada.getFormaPagto()!=null){
			try {
				
				freteTipoSelectItem = null;
				selecionada.setFormaPagtoId(selecionada.getFormaPagto().getId());
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	public void verificarMovFiscTipo(){
		if(selecionada.getMovFiscTipo()!=null){
			try {
				
				freteTipoSelectItem = null;
				selecionada.setMovFiscTipoId(selecionada.getMovFiscTipo().getId());
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	
	public void verificarFreteTipo(){
		if(selecionada.getFreteTipo()!=null){
			
			try {
				
				selecionada.setFreteTipoId(selecionada.getFreteTipo().getId());
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	public void verificarPrevRetirada(ValueChangeEvent event){
		if(event.getOldValue().equals(event.getNewValue()) == false){
			try {
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);
				}
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	public void verificarObservacao(ValueChangeEvent event){
		if(event.getOldValue().equals(event.getNewValue()) == false){
			try {
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);
				}
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	public void verificarCpfCnpj(){
		ClienteFBRN clienteFBRN = new ClienteFBRN();
		ClienteFB clienteVer = null;
		
		if(cliente!=null && cliente.getCnpjCpf() != null && !"".equals(cliente.getCnpjCpf())) {
			clienteVer = clienteFBRN.carregar(cliente.getCnpjCpf());
		}	
		
		if(clienteVer!=null){
			if(clienteVer.getBloqManual() == 1) {
				clienteVer = null;
				novoCliente();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.inativo.bloqueado")));
				
			}else {
			
				if(clienteVer.getFormaPagtoId()==null) {
					List<FormaPagtoFB> formaPagtoFB = new FormaPagtoFBRN().listarFormaCond(empresa.getId());
					clienteVer.setFormaPagto(formaPagtoFB.get(0));
					clienteVer.setFormaPagtoId(formaPagtoFB.get(0).getId());
				}

				cliente = clienteVer;
				
				PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
				try {
					selecionada = pedVendaFBRN.novo(empresa, vendedor, cliente, contextoBean.getUsuarioLogado(), encomenda);
					verAlcada();
				} catch (RNException e) {
					e.printStackTrace();
					selecionada = null;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
				}
			}	
			
		}else{
			if(cliente.getCnpjCpf()!=null && cliente.getCnpjCpf().length() == 11) {
				podeCadastrarCliente = true;
			}else {
				podeCadastrarCliente = false;
			}
			cliente.setEstadoId("PE");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.naocadastrado")));
		}
		
		listaCli = null;

	}
	
	public void verificarCpfCnpjModifCli(){
		try {
			
			if(clienteModif!=null){
				if(clienteModif.getBloqManual() == 1) {
					clienteModif = null;
					novoCliente();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.inativo.bloqueado")));
					
				}else if(clienteModif.getColigada()==1) {
					clienteModif = null;
					novoCliente();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.coligado")));

				}else {

					selecionada.setClienteId(clienteModif.getId());
					
					if(clienteModif.getFormaPagtoId()==null) {
						List<FormaPagtoFB> formaPagtoFB = new FormaPagtoFBRN().listarFormaCond(selecionada.getEmpresaId());
						clienteModif.setFormaPagto(formaPagtoFB.get(0));
						clienteModif.setFormaPagtoId(formaPagtoFB.get(0).getId());
					}

					
					cliente = clienteModif;
					
					if(selecionada.getId() != null) {
						PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
						selecionada = pedVendaFBRN.mudarCliente(selecionada, cliente);
					}
				}
			}	
			
			clienteModif = null;
			listaCli = null;
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			e.printStackTrace();
		}
	}
	
	private void verAlcada() {
		alcada = new AlcadaCondPagtoFBRN().carregar(contextoBean.getUsuarioLogado(), selecionada.getCondPagtoId());
	}

	public void buscarProduto() {
		
		cancelarItemProduto();
		openResultListaProdutos=true;
		
		if(filterPorCodBarra) {
			descProdFilter = null;
			linhaProdFilter = null;
			fabricanteFilter = null;
		}else {
			codBarraFilter = null;
		}
		
		
		try {
 			listaProdutosLDM = new ProdutoFBLazyDM(FacesContext.getCurrentInstance(), selecionada, descProdFilter, codBarraFilter, fabricanteFilter, linhaProdFilter, comEstoqueFilter, semEstoqueFilter, listaPedVendaItem);
// 			if(listaProdutosLDM.getRowCount()==1) {
//				produtoSelecionada = listaProdutosLDM.getRowData("0");
//				
//				if(produtoSelecionada.getInPedVenda()) {
//					produtoSelecionada = null;
//					focusFilterProduto();
//				}else {
//					addProduto();
//				}
// 			}else{
 				RequestContext.getCurrentInstance().execute("PF('UIdtItensPedTransfBean').clearFilters();");
// 			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
		}
		
	}
	
	public void addProduto() {
		try {
			
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			produtoSelecionada.setEstoques(produtoEstoqueFBRN.listar(selecionada.getEncomenda(), selecionada.getEmpresaId(), contextoBean.getUsuarioLogado().getId(), produtoSelecionada.getId(), produtoSelecionada.getControlaLote(), Funcoes.SO_ESTOQUE, produtoSelecionada.getPermiteVendaSemEstoque()));
			if(selecionada.getIsProdComposto()) {
				produtoSelecionada.setComposicoes(new ProdCompostoItemFBRN().listar(produtoSelecionada.getId()));
			}
			
			pedVendaItem = new PedVendaItemFBRN().novoPedTransf(selecionada, produtoSelecionada, null);
			descItem = pedVendaItem.getPercDesconto();
			
			if(produtoSelecionada.getEstoques().size()==0) {
				pedVendaItem = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", UtilMessage.mensagem("msg.produtosemestoque.pedvendabean")));
			}
			
			calcularPisoBean.novo();
			
			closeResultListaProduto();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addProdutoPedTransf() {
		try {
			
			ProdutoEstoqueFBRN produtoEstoqueFBRN = new ProdutoEstoqueFBRN();
			produtoSelecionada.setEstoques(produtoEstoqueFBRN.listarTodos(selecionada.getEmpresaId(), contextoBean.getUsuarioLogado().getId(), produtoSelecionada.getControlaLote(), produtoSelecionada.getId(), Funcoes.IS_TRANSFERENCIA));
			
			pedVendaItem = new PedVendaItemFBRN().novoPedTransf(selecionada, produtoSelecionada, null);
			descItem = pedVendaItem.getPercDesconto();
			
			if(produtoSelecionada.getEstoques().size()==0) {
				pedVendaItem = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", UtilMessage.mensagem("msg.produtosemestoque.pedvendabean")));
			}
			
			calcularPisoBean.novo();
			
			closeResultListaProduto();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editarProduto(Integer pedVendaItemId, Integer produtoId) {
		try {
			
			produtoSelecionada = new ProdutoFBRN().carregar(selecionada.getEncomenda(), produtoId, selecionada.getEncomenda(), selecionada.getEmpresaId(), selecionada.getMovFiscTipo().getOpFiscTipoId(), contextoBean.getUsuarioLogado().getId(), selecionada.getTabPrecoId(), selecionada.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter, produtoId);
			pedVendaItem = new PedVendaItemFBRN().editar(pedVendaItemId, produtoSelecionada, selecionada.getEncomenda());
			descItem = pedVendaItem.getPercDesconto();
			
			calcularPisoBean.novo();
			
			if(descPedido) {
				selecionada.setDesconto(Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), selecionada.getValPedidoPrTab()));
				salvarDesconto();
			}
			
			closeResultListaProduto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarProdComposto(PedVendaCompostoFB pedVendaComposto) {
		try {
			
			produtoSelecionada = new ProdutoFBRN().carregarProdComposto(selecionada.getEmpresaId(), selecionada.getUsuarioId(), selecionada.getMovFiscTipo().getOpFiscTipoId(), selecionada.getTabPrecoId(), selecionada.getCondPagtoId(), pedVendaComposto);
			pedVendaItem = new PedVendaItemFBRN().novo(selecionada, produtoSelecionada, pedVendaComposto.getId());
			pedVendaItem.setQuantidade(pedVendaComposto.getQuantidade());
			descItem = 0d;
			
			calcularPisoBean.novo();
			
			if(descPedido) {
				selecionada.setDesconto(Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), selecionada.getValPedidoPrTab()));
				salvarDesconto();
			}
			
			closeResultListaProduto();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluirProduto(Integer pedVendaItemFBId) {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.excluirItem(selecionada, pedVendaItemFBId, contextoBean.getUsuarioLogado().getId());
			
			closeResultListaProduto();
			limparProdutoSelected();
			buscarItensPedido();
			
			if(descPedido) {
				selecionada.setDesconto(Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), selecionada.getValPedidoPrTab()));
				salvarDesconto();
			}
			
			focusFilterProduto();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.pedvendaitem")));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	public void excluirProdComposto(Integer pedVendaCompostoId) {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.excluirProdComposto(selecionada, pedVendaCompostoId, contextoBean.getUsuarioLogado().getId());
			
			closeResultListaProduto();
			limparProdutoSelected();
			buscarItensPedido();
			
			if(descPedido) {
				selecionada.setDesconto(Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), selecionada.getValPedidoPrTab()));
				salvarDesconto();
			}
			
			focusFilterProduto();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.pedvendaitem")));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	public void closeResultListaProduto(){
		openResultListaProdutos = false;
		listaProdutosLDM = null;
	}
	
	public void updateQtdPedVendaItem() {
		Double total = 0.0;
		for(ProdutoEstoqueFB rs : produtoSelecionada.getEstoques()) {
			if(rs.getQtdReservar()!=null && rs.getQtdReservar()>0.0) {
				total += rs.getQtdReservar();
			}
		}
		pedVendaItem.setPesoBrutoKg(produtoSelecionada.getPesoBrutoKg()*total);
		pedVendaItem.setPesoLiquidoKg(produtoSelecionada.getPesoLiquidoKg()*total);
		pedVendaItem.setQuantidade(total);
	}
	
	public void addQtdPedVendaItem(ProdutoEstoqueFB est) {
		try {
			
			if(est.getQtdReservar()==null) {
				est.setQtdReservar(0.0);
			}
			
			if(est.getQtdReservar()<=est.getQtdDisponivel()) {
				Double qtd = 0.0;
				Double qtdVol = est.getQtdReservar() / est.getQtdVendaAtac();
				Integer qtdTrunc = qtdVol.intValue();
		        if((qtdTrunc * est.getQtdVendaAtac()) < est.getQtdReservar()){
		        	qtd = (qtdTrunc + 1) * est.getQtdVendaAtac();
		        }else {
		        	qtd = est.getQtdReservar();
		        }
		        
				if(qtd<=est.getQtdDisponivel()) {
					est.setQtdReservar(qtd);
				}else {
					est.setQtdReservar(est.getQtdDisponivel());
				}
			
			}
			
			updateQtdPedVendaItem();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void somarQtdPedVendaItem(ProdutoEstoqueFB est) {
		try {
			
			if(est.getQtdReservar()==null) {
				est.setQtdReservar(0.0);
			}
			
			if(est.getQtdReservar()<=est.getQtdDisponivel()) {
				Double qtd = (est.getQtdReservar()==null ? 0.0 : est.getQtdReservar()) + est.getQtdVendaAtac();
				if(qtd<=est.getQtdDisponivel()) {
					est.setQtdReservar(qtd);
				}
			}
			
			updateQtdPedVendaItem();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void subtrairQtdPedVendaItem(ProdutoEstoqueFB est) {
		try {
			
			if(est.getQtdReservar()==null) {
				est.setQtdReservar(0.0);
			}
			
			if(est.getQtdReservar()>=est.getQtdVendaAtac()) {
				Double qtd = est.getQtdReservar() - est.getQtdVendaAtac();
				est.setQtdReservar(qtd);
			}
			
			updateQtdPedVendaItem();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvarItemProduto() {
		
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			selecionadaId = pedVendaFBRN.salvar(FacesContext.getCurrentInstance(), selecionada, produtoSelecionada, pedVendaItem, alcada.getAlcada(), contextoBean.getUsuarioLogado().getUsuarioGrupo().getDescontoMaximo(), descPedido);
			
			limparProdutoSelected();
			buscarItensPedido();
			
			if(descPedido) {
				selecionada.setDesconto(Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), selecionada.getValPedidoPrTab()));
				salvarDesconto();
			}
			
			closeResultListaProduto();
			buscarDivergencias();
			focusFilterProduto();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
		}
		
	}
	
	public void salvarItemProdutoPedTransf() {
		
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			selecionadaId = pedVendaFBRN.salvarPedTransf(FacesContext.getCurrentInstance(), selecionada, produtoSelecionada, pedVendaItem, alcada.getAlcada(), contextoBean.getUsuarioLogado().getUsuarioGrupo().getDescontoMaximo(), descPedido);
			
			limparProdutoSelected();
			buscarItensPedido();
			
			closeResultListaProduto();
			buscarDivergencias();
			focusFilterProduto();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
		}
		
	}

	public boolean salvarDesconto() {
		if(selecionada!=null && descPedido){
			try {
				
				Double descontoMaximo = contextoBean.getUsuarioLogado().getUsuarioGrupo().getDescontoMaximo();
				PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
				pedVendaFBRN.incluirDesconto(FacesContext.getCurrentInstance(), selecionada, listaPedVendaItem, alcada.getAlcada(), descontoMaximo);
				
				buscarDivergencias();
				
				return true;
				
			} catch (RNException e) {
				
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Desconto", e.getMessage()));
				
			}
		
		}
		
		return false;
	}
	
	public void focusFilterProduto() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if(filterPorCodBarra) {
			requestContext.execute("focusInPedVendaNovoCodBarraFilter();");
		}else {
			requestContext.execute("focusInPedVendaNovoDescricaoFilter();");
		}
	}
	
	public void concluir() {
		
		msgCondPagto = false;
		
		try {
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.concluirPedTransf(FacesContext.getCurrentInstance(), selecionada);
			
			//pedVendaFBRN.geraDuplicataPedido(selecionada);
			
			limparProdutoSelected();
			buscarItensPedido();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
		}
		
	}
	
	private void buscarItensPedido() {
		listaPedVendaItem = new PedVendaItemFBDTORN().listar(selecionada.getId());
		
		Double total = 0.0;
		Double totalPrecoTab = 0.0;
		Double totalPesoBruto = 0.0;
		Double totalVolumes = 0.0;
		for(PedVendaItemFBDTO rs : listaPedVendaItem) {
			total += rs.getSubTotal(); 
			totalPrecoTab += rs.getInPromocao() ? Funcoes.arrendondaValor(2, (rs.getPrecoProm() * rs.getQuantidade())) : Funcoes.arrendondaValor(2, (rs.getPrecoTabela() * rs.getQuantidade()));
			totalPesoBruto += rs.getPesoBrutoKg();
			totalVolumes += rs.getVolume();
		}
		selecionada.setValPedido(total);
		selecionada.setValPedidoPrTab(totalPrecoTab);
		selecionada.setPesoBrutoKg(totalPesoBruto);
		selecionada.setVolume(totalVolumes);
		
		if(selecionada.getIsProdComposto()) {
			listaPedVendaComposto = new PedVendaCompostoFBRN().listar(selecionada);
		}
		
		if(selecionada.getIsPedido()) {
			PedVendaItemProdLoteDTORN vendaItemProdLoteDTORN = new PedVendaItemProdLoteDTORN();
			for(PedVendaItemFBDTO rs : listaPedVendaItem) {
				if(rs.getControlaLote().equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
					rs.setLotes(vendaItemProdLoteDTORN.lista(rs.getId()));
				}
			}
		}
	}
	
	private void buscarDivergencias() {
		if(selecionada.getId()!=null) {
			listaDivergencias = new PedVendaDivergFBRN().listarDTO(selecionada.getId());
		}
	}

	public void cancelarItemProduto() {
		pedVendaItem = null;
		produtoSelecionada = null;
		closeResultListaProduto();
		focusFilterProduto();
	}
	
	public void precoChangedProduto(ValueChangeEvent e){
		//assign new value to localeCode
		if(!e.getNewValue().equals(e.getOldValue()) && e.getNewValue()!=null){
			Double precoModificado = Double.parseDouble(e.getNewValue().toString()); 
			pedVendaItem.setPreco(precoModificado);
			descItem = Funcoes.descontoPrecoPedVenda(pedVendaItem.getPrecoTabela(), precoModificado);
			pedVendaItem.setPercDesconto(descItem);
			if(pedVendaItem.getPercDesconto() > alcada.getAlcada()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", UtilMessage.mensagem("msg.desconto.pedvendaitem.bloqueado")));
			}
		}
	}
	
	public void descontoChangedProduto(ValueChangeEvent e){
		//assign new value to localeCode
		if(!e.getNewValue().equals(e.getOldValue()) && e.getNewValue()!=null){
			Double descontoModificado = Double.parseDouble(e.getNewValue().toString());
			Double precoModificado = Funcoes.precoDesconto(pedVendaItem.getPrecoTabela(), descontoModificado);
			pedVendaItem.setPreco(precoModificado);
			pedVendaItem.setPercDesconto(descontoModificado);
			if(pedVendaItem.getPercDesconto() > alcada.getAlcada()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", UtilMessage.mensagem("msg.desconto.pedvendaitem.bloqueado")));
			}
		}
	}
	
	public void descPedVendaChanged(ValueChangeEvent e){
		//assign new value to localeCode
		if(!e.getNewValue().equals(e.getOldValue()) && e.getNewValue()!=null){
			selecionada.setDesconto((Double)e.getNewValue());
			salvarDesconto();
		}
	}
	
	public void prDescPedVendaChanged(ValueChangeEvent e) {
		//assign new value to localeCode
		if(!e.getNewValue().equals(e.getOldValue()) && e.getNewValue()!=null){
			Double novoValPedido = (Double) e.getNewValue();
			Double novoDesconto = Funcoes.descontoPrecoPedVenda(selecionada.getValPedidoPrTab(), novoValPedido);
			selecionada.setDesconto(novoDesconto);
			salvarDesconto();
		}
	}
	
	public void editarCliente() {
		limparFilterCli();
		
		clienteModif = new ClienteFB();
		clienteModif.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		clienteModif.setAtivo(ClienteFB.CLIENTE_ATIVO);
	}
	
	// gets and sets
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
	public CalcularPisoBean getCalcularPisoBean() {
		return calcularPisoBean;
	}

	public void setCalcularPisoBean(CalcularPisoBean calcularPisoBean) {
		this.calcularPisoBean = calcularPisoBean;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}
	
	public PedVendaFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(PedVendaFB selecionada) {
		this.selecionada = selecionada;
	}
	
	public PedVendaItemFB getPedVendaItem() {
		return pedVendaItem;
	}

	public void setPedVendaItem(PedVendaItemFB pedVendaItem) {
		this.pedVendaItem = pedVendaItem;
	}

	public ClienteFB getCliente() {
		return cliente;
	}

	public void setCliente(ClienteFB cliente) {
		this.cliente = cliente;
	}
	
	public int getStepIndex() {
		return stepIndex;
	}

	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}
	
	public List<SelectItem> getEmpresaSelectItem() {
		if(empresaSelectItem==null) {
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			empresaSelectItem = empresaFBRN.montaDadosSelect(empresaFBRN.listar(contextoBean.getUsuarioLogado()), "");
		}
		return empresaSelectItem;
	}

	public void setEmpresaSelectItem(List<SelectItem> empresaSelectItem) {
		this.empresaSelectItem = empresaSelectItem;
	}

	public List<SelectItem> getVendedorSelectItem() {
		if(vendedorSelectItem==null) {
			VendedorFBRN vendedorFBRN = new VendedorFBRN();
			vendedorSelectItem = vendedorFBRN.montaDadosSelect(vendedorFBRN.listar(null), "");
		}
		return vendedorSelectItem;
	}

	public void setVendedorSelectItem(List<SelectItem> vendedorSelectItem) {
		this.vendedorSelectItem = vendedorSelectItem;
	}
	
	public List<SelectItem> getCondPagtoSelectItem() {
		if(condPagtoSelectItem==null) {
			CondPagtoFBRN condPagtoFBRN = new CondPagtoFBRN();
			List<CondPagtoFB> listaCondPagto = condPagtoFBRN.listar(selecionada.getFormaPagtoId(), selecionada.getEmpresaId(), selecionada.getClienteId());
			condPagtoSelectItem = condPagtoFBRN.montaDadosSelect(listaCondPagto, null);
		}
		return condPagtoSelectItem;
	}

	public void setCondPagtoSelectItem(List<SelectItem> condPagtoSelectItem) {
		this.condPagtoSelectItem = condPagtoSelectItem;
	}
	
	public List<SelectItem> getFreteTipoSelectItem() {
		if(freteTipoSelectItem==null && selecionada.getFormaPagtoId() != null) {
			FreteTipoFBRN freteTipoFBRN = new FreteTipoFBRN();
			List<FreteTipoFB> listaFreteTipo = freteTipoFBRN.listar(selecionada.getFormaPagtoId());
			freteTipoSelectItem = freteTipoFBRN.montaDadosSelect(listaFreteTipo, null);
			if(listaFreteTipo != null) {
				if(listaFreteTipo.contains(selecionada.getFreteTipo()) == false) {
					selecionada.setFreteTipo(listaFreteTipo.get(0));
					selecionada.setFreteTipoId(selecionada.getFreteTipo().getId());
				}
			}
		}
		return freteTipoSelectItem;
	}

	public void setFreteTipoSelectItem(List<SelectItem> freteTipoSelectItem) {
		this.freteTipoSelectItem = freteTipoSelectItem;
	}
	
	public List<SelectItem> getFormaPagtoSelectItem() {
		if(formaPagtoSelectItem==null) {
			FormaPagtoFBRN formaPagtoFBRN = new FormaPagtoFBRN();
			formaPagtoSelectItem = formaPagtoFBRN.montaDadosSelect(formaPagtoFBRN.listar(), null);
		}
		return formaPagtoSelectItem;
	}

	public void setFormaPagtoSelectItem(List<SelectItem> formaPagtoSelectItem) {
		this.formaPagtoSelectItem = formaPagtoSelectItem;
	}
	
	public List<SelectItem> getTipoMovFiscSelectItem() {
		if(tipoMovFiscSelectItem==null) {
			MovFiscTipoFBRN movFiscTipoFBRN = new MovFiscTipoFBRN();
			MovFiscTipoFB MovFiscTipo = movFiscTipoFBRN.carregar(selecionada.getMovFiscTipoId());
			tipoMovFiscSelectItem = movFiscTipoFBRN.montaDadosSelect(movFiscTipoFBRN.listarTransfOutras(), null);
//			if(listaMovFiscTipo != null) {
//				if(listaMovFiscTipo.contains(selecionada.getMovFiscTipo()) == true) {
//					selecionada.setMovFiscTipo(listaMovFiscTipo.get(0));
//					selecionada.setMovFiscTipoId(selecionada.getMovFiscTipo().getId());
//				}
//			}			
		}		 
		return tipoMovFiscSelectItem;
	}

	public void setTipoMovFiscSelectItem(List<SelectItem> tipoMovFiscSelectItem) {
		this.tipoMovFiscSelectItem = tipoMovFiscSelectItem;
	}

	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	public VendedorFB getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorFB vendedor) {
		this.vendedor = vendedor;
	}
	
	public boolean isPodeMudarEmpresa() {
		podeMudarEmpresa = false;
		if(contextoBean.getUsuarioLogado().getEmpresas()!=null) {
			if(contextoBean.getUsuarioLogado().getEmpresas().size()>1) {
				podeMudarEmpresa = true;
			}
		}
		return podeMudarEmpresa;
	}

	public void setPodeMudarEmpresa(boolean podeMudarEmpresa) {
		this.podeMudarEmpresa = podeMudarEmpresa;
	}

	public boolean isPodeMudarVendedor() {
		podeMudarVendedor = false;
		if(contextoBean.getUsuarioLogado().getVendedor()==null) {
			podeMudarVendedor = true;
		}
		return podeMudarVendedor;
	}

	public void setPodeMudarVendedor(boolean podeMudarVendedor) {
		this.podeMudarVendedor = podeMudarVendedor;
	}

	public boolean isPodeCadastrarCliente() {
		return podeCadastrarCliente;
	}

	public void setPodeCadastrarCliente(boolean podeCadastrarCliente) {
		this.podeCadastrarCliente = podeCadastrarCliente;
	}

	public LazyDataModel<ProdutoFB> getListaProdutosLDM() {
		return listaProdutosLDM;
	}

	public void setListaProdutosLDM(LazyDataModel<ProdutoFB> listaProdutosLDM) {
		this.listaProdutosLDM = listaProdutosLDM;
	}
	
	public String getDescProdFilter() {
		return descProdFilter;
	}

	public void setDescProdFilter(String descProdFilter) {
		this.descProdFilter = descProdFilter;
	}
	
	public String getFabricanteFilter() {
		return fabricanteFilter;
	}

	public void setFabricanteFilter(String fabricanteFilter) {
		this.fabricanteFilter = fabricanteFilter;
	}
	
	public String getLinhaProdFilter() {
		return linhaProdFilter;
	}

	public void setLinhaProdFilter(String linhaProdFilter) {
		this.linhaProdFilter = linhaProdFilter;
	}
	
	public ProdutoFB getProdutoSelecionada() {
		return produtoSelecionada;
	}

	public void setProdutoSelecionada(ProdutoFB produtoSelecionada) {
		this.produtoSelecionada = produtoSelecionada;
	}

	public boolean isComEstoqueFilter() {
		return comEstoqueFilter;
	}

	public void setComEstoqueFilter(boolean comEstoqueFilter) {
		this.comEstoqueFilter = comEstoqueFilter;
	}

	public boolean isSemEstoqueFilter() {
		return semEstoqueFilter;
	}

	public void setSemEstoqueFilter(boolean semEstoqueFilter) {
		this.semEstoqueFilter = semEstoqueFilter;
	}

	public double getDescItem() {
		return descItem;
	}

	public void setDescItem(double descItem) {
		this.descItem = descItem;
	}
	
	public List<PedVendaItemFBDTO> getListaPedVendaItem() {
		return listaPedVendaItem;
	}

	public void setListaPedVendaItem(List<PedVendaItemFBDTO> listaPedVendaItem) {
		this.listaPedVendaItem = listaPedVendaItem;
	}
	
	public List<PedVendaCompostoFB> getListaPedVendaComposto() {
		return listaPedVendaComposto;
	}

	public void setListaPedVendaComposto(List<PedVendaCompostoFB> listaPedVendaComposto) {
		this.listaPedVendaComposto = listaPedVendaComposto;
	}
	
	public boolean isOpenResultListaProdutos() {
		return openResultListaProdutos;
	}

	public void setOpenResultListaProdutos(boolean openResultListaProdutos) {
		this.openResultListaProdutos = openResultListaProdutos;
	}
	
	public AlcadaCondPagtoFB getAlcada() {
		return alcada;
	}

	public void setAlcada(AlcadaCondPagtoFB alcada) {
		this.alcada = alcada;
	}
	
	public boolean isMsgCondPagto() {
		return msgCondPagto;
	}

	public void setMsgCondPagto(boolean msgCondPagto) {
		this.msgCondPagto = msgCondPagto;
	}
	
	public Integer getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(Integer encomenda) {
		this.encomenda = encomenda;
	}
	
	public String getCodBarraFilter() {
		return codBarraFilter;
	}

	public void setCodBarraFilter(String codBarraFilter) {
		this.codBarraFilter = codBarraFilter;
	}

	public boolean isFilterPorCodBarra() {
		return filterPorCodBarra;
	}

	public void setFilterPorCodBarra(boolean filterPorCodBarra) {
		this.filterPorCodBarra = filterPorCodBarra;
	}
	
	public List<PedVendaDivergFBDTO> getListaDivergencias() {
		return listaDivergencias;
	}

	public void setListaDivergencias(List<PedVendaDivergFBDTO> listaDivergencias) {
		this.listaDivergencias = listaDivergencias;
	}
	
	public boolean isDescPedido() {
		return descPedido;
	}
	
	public ClienteFB getClienteModif() {
		return clienteModif;
	}

	public void setClienteModif(ClienteFB clienteModif) {
		this.clienteModif = clienteModif;
	}

	public void setDescPedido(boolean descPedido) {
		this.descPedido = descPedido;
	}
	
	public boolean isRenderedStep() {
		return renderedStep;
	}

	public void setRenderedStep(boolean renderedStep) {
		this.renderedStep = renderedStep;
	}
	
	public List<PedVendaCartaoFB> getListaPedVendaCartao() {
		return listaPedVendaCartao;
	}

	public void setListaPedVendaCartao(List<PedVendaCartaoFB> listaPedVendaCartao) {
		this.listaPedVendaCartao = listaPedVendaCartao;
	}
	
	public boolean isRenderedBtnEncomenda() {
		return renderedBtnEncomenda;
	}

	public void setRenderedBtnEncomenda(boolean renderedBtnEncomenda) {
		this.renderedBtnEncomenda = renderedBtnEncomenda;
	}

	public boolean isRenderedBtnComposto() {
		return renderedBtnComposto;
	}

	public void setRenderedBtnComposto(boolean renderedBtnComposto) {
		this.renderedBtnComposto = renderedBtnComposto;
	}
	
	public boolean isRenderedSelectFreteTipo() {
		return renderedSelectFreteTipo;
	}

	public void setRenderedSelectFreteTipo(boolean renderedSelectFreteTipo) {
		this.renderedSelectFreteTipo = renderedSelectFreteTipo;
	}
	
	public boolean isRenderedSelectFormaPagto() {
		return renderedSelectFormaPagto;
	}

	public void setRenderedSelectFormaPagto(boolean renderedSelectFormaPagto) {
		this.renderedSelectFormaPagto = renderedSelectFormaPagto;
	}
	
	public boolean isRenderedSelectMovFiscTipo() {
		return renderedSelectMovFiscTipo;
	}

	public void setRenderedSelectMovFiscTipo(boolean renderedSelectMovFiscTipo) {
		this.renderedSelectMovFiscTipo = renderedSelectMovFiscTipo;
	}

	public List<ClienteFB> getListaCli() {
		return listaCli;
	}

	public void setListaCli(List<ClienteFB> listaCli) {
		this.listaCli = listaCli;
	}

	public String getDescricaoFilterCli() {
		return descricaoFilterCli;
	}

	public void setDescricaoFilterCli(String descricaoFilterCli) {
		this.descricaoFilterCli = descricaoFilterCli;
	}

	//	
	public void clearSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.removeAttribute("pedTransfNovoBean");
	}

	

}