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

@ManagedBean(name="pedVendaNovoBean")
@SessionScoped
public class PedVendaNovoBean implements Serializable {

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
	private boolean renderedBuscaCli;
	private boolean podeBuscarCli;
	private boolean clienteNaoEncontrado;
	private boolean tipoCnpj;
	private boolean tipoCpf;
	private boolean tipoOutros;
	private boolean alterouCli;
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
	private ClienteFB clienteAlterar;
	private ClienteFB clienteModif;
	private ClienteFB clienteNovo;
	private ClienteFB clienteBusca;
	private String nomeConsumidor;
	private String tipoPessoaF;
	private String tipoPessoaJ;
	private String tipoPessoaO;
	private String nomePF;
	private String nomePJ;
	private String nomePO;
	private String cnpj;
	private String cpf;
	private String outros;
	
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
	
	// Cliente Filter
	private List<ClienteFB> listaCli;
	private String descricaoFilterCli;
	
	@PostConstruct
	public void init(){
		renderedBtnComposto = false;
		renderedBtnEncomenda = false;
		renderedSelectFreteTipo = false;
		renderedSelectFormaPagto = false;
		renderedBuscaCli = false;
		if(contextoBean.getConfiguracoes() != null) {
			try {
				renderedBtnComposto = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_COMPOSTO))).isAtivo();
				renderedBtnEncomenda = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_ENCOMENDA))).isAtivo();
				renderedSelectFreteTipo = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_PODE_MUDAR_FRETE_TIPO))).isAtivo();
				renderedSelectFormaPagto = contextoBean.getConfiguracoes().get(contextoBean.getConfiguracoes().indexOf(new Configuracao(Configuracao.PEDIDO_PODE_MUDAR_FORMA_PAGTO))).isAtivo();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void buscarCli() {
		if(vendedor==null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Selecione o Vendedor!"));
		}else {
			listaCli = new ClienteFBRN().listar(descricaoFilterCli);
		}	
	}
	
	public void limparFilterCli() {
		clienteModif = null;
		
		listaCli = null;
		descricaoFilterCli = null;
	}
	
	public void limparCli() {
		cliente.setCnpjCpf(null);
		cliente.setNomeFantasia("");
	}
	
	private void limparListas() {
		empresaSelectItem = null;
		vendedorSelectItem = null;
		condPagtoSelectItem = null;
		freteTipoSelectItem = null;
		formaPagtoSelectItem = null;

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
	
	public void cadastrarNovoCliente() {
		cliente = new ClienteFB();
		cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);
		
	}
	
	public void carregaClientePadrao() throws RNException {
		
		ClienteFB clientePadraEmpresa = new ClienteFBRN().carregar(empresa.getIdClientePadraoFDL());
		
		if(clientePadraEmpresa!=null) {
			cliente.setCnpjCpf(clientePadraEmpresa.getCnpjCpf());
			cliente.setTipoPessoa(clientePadraEmpresa.getTipoPessoa());
		}else {
			cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
			cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);
			cliente.setCnpjCpf(null);
		}	
		
		if(cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
			nomePF = clientePadraEmpresa.getNomeFantasia();
			nomeConsumidor = "";
			cnpj = null;
			cpf = clientePadraEmpresa.getCnpjCpf();
			selecionada.setNomeCliente("");
		}else if(cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
			nomePJ = clientePadraEmpresa.getNomeFantasia();
			selecionada.setNomeCliente("");
			cpf = null;
			nomeConsumidor = "";
			cnpj = clientePadraEmpresa.getCnpjCpf();
		}else {
			nomePO = clientePadraEmpresa.getNomeFantasia();
			outros = clientePadraEmpresa.getCnpjCpf();
			
			cnpj = null;
			cpf = null;
			//selecionada.setNomeCliente(clientePadraEmpresa.getNomeFantasia());
		}		
		
		if(cliente!=null) {
			selecionada.setClienteId(clientePadraEmpresa.getId());
		}
		
		if(this.empresa!=null && this.selecionada!=null && selecionada.getId()!=null) {
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.mudarEmpresa(selecionada, empresa);
		}
		
		
	}
	
	private void verTipoDescontoPedido() {
		descPedido = Funcoes.PADRAO_DE_DESCONTO_NO_PEDIDO;
	}
	
	public void novoCalculoPiso() {
		calcularPisoBean.setQtdVendaAtac(produtoSelecionada.getQtdVendaAtac());
		calcularPisoBean.setQtdDecimal(produtoSelecionada.getQtdDecimal());
	}
	
	public void novoPedVendaNormal() {
		verTipoDescontoPedido();
		
		encomenda = PedVendaFB.PEDIDO;
		novo();
	}

	public void novoPedVendaEncomenda() {
		verTipoDescontoPedido();
		
		encomenda = PedVendaFB.ENCOMENDA;
		novo();
	}
	
	public void novoPedVendaProdComposto() {
		verTipoDescontoPedido();
		
		encomenda = PedVendaFB.PEDIDO_PRODUTO_COMPOSTO;
		novo();
	}
	
	private void novo() {
		
		selecionadaId = null;
		stepIndex = 0;
		renderedStep = true;
		renderedBuscaCli = true;
		podeCadastrarCliente = false;
		clienteNaoEncontrado = false;
		msgCondPagto = false;
		
		filterPorCodBarra = false;
		
		nomeConsumidor = "";
		nomePF = "";
		nomePJ = "";
		cpf = "";
		cnpj = "";
		outros = "";
		
		selecionada = new PedVendaFB();
		selecionada.setPedVendaStatusId(PedVendaFB.SITUACAO_DIGITACAO);
		selecionada.setDesconto(0.0);
		selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
		cliente = new ClienteFB();
		cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);
		
		clienteNovo = cliente;
		
		if(contextoBean.getUsuarioLogado().getVendedor()!=null) {
			vendedor = contextoBean.getUsuarioLogado().getVendedor();
		}
		
		if(contextoBean.getUsuarioLogado().getEmpresas()!=null){
			if(contextoBean.getUsuarioLogado().getEmpresaId() != null) {
				empresa = new EmpresaFBRN().carregar(contextoBean.getUsuarioLogado().getEmpresaId());
			}else {
				empresa = contextoBean.getUsuarioLogado().getEmpresas().get(0);
			}
		}
		
		if(empresa.getIdClientePadraoFDL()!=null) {
			ClienteFB clientePadrao = new ClienteFBRN().carregar(empresa.getIdClientePadraoFDL());
			if(clientePadrao!=null) {
				cliente.setTipoPessoa(clientePadrao.getTipoPessoa());
				cliente.setCnpjCpf(clientePadrao.getCnpjCpf());
				cliente.setNomeFantasia(clientePadrao.getNomeFantasia());
			}
			
		}
		
		if("F".equals(cliente.getTipoPessoa())) {
			tipoPessoaJ = "";
			tipoPessoaO = "";
			tipoPessoaF = cliente.getTipoPessoa();
			nomePF = cliente.getNomeFantasia();
			cpf = cliente.getCnpjCpf();
		}else if("J".equals(cliente.getTipoPessoa())) {
			tipoPessoaJ = cliente.getTipoPessoa();
			tipoPessoaO = "";
			tipoPessoaF = "";
			nomePJ = cliente.getNomeFantasia();
			cnpj = cliente.getCnpjCpf();
		}else {
			tipoPessoaJ = "";
			tipoPessoaF = "";
			tipoPessoaO = cliente.getTipoPessoa();
			nomePO = cliente.getNomeFantasia();
			outros = cliente.getCnpjCpf();
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
			renderedBuscaCli = false;
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
				nomeConsumidor = selecionada.getNomeCliente();
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
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
				
				openItens();
				
				verAlcada();
				carregarInfoCli();
				buscarItensPedido();
				buscarDivergencias();
				
				listaPedVendaCartao = new PedVendaCartaoFBRN().listar(selecionadaId);
			}else {
				novoPedVendaNormal();
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
	
	public void novoClienteCadastro() {
		cliente = new ClienteFB();
		cliente.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		cliente.setAtivo(ClienteFB.CLIENTE_ATIVO);
	}

	public void visualizar() {
		editar();
		renderedStep = false;
	}
	
	public void excluir() {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.excluir(selecionadaId, contextoBean.getUsuarioLogado().getId());
			
			novoPedVendaNormal();
			
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
		renderedBuscaCli = false;
	}
	
	public void openCondPagto() {
		stepIndex = 2;
		renderedBuscaCli = false;
	}
	
	public void openConcluir() {
		stepIndex = 3;
		renderedBuscaCli = false;
	}
	
	public void openFilterCli() {
		renderedBuscaCli = true;
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("closePanelFiltroCli();");		
	}

	public void verificarEmpresa() {}
	
	public void verificarVendedor() {}
	
	public void verificarTipoPessoa(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if("F".equals(cliente.getTipoPessoa())) {
			requestContext.execute("focus('cpf');");
		}else if("J".equals(cliente.getTipoPessoa())) {
			requestContext.execute("focus('cnpj');");
		}else {
			requestContext.execute("focus('nomeConsumidor');");
		}
		
		podeCadastrarCliente = false;
	}
	
	public void verificarCondPagto(){
		if(selecionada.getCondPagto()!=null){
			try {
				
				msgCondPagto = true;
				
				selecionada.setCondPagtoId(selecionada.getCondPagto().getId());
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
				
				String tabPrecoId = selecionada.getCondPagto().getTabPrecoId();
				CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(selecionada.getCondPagto().getId(), selecionada.getEmpresaId(), selecionada.getClienteId());
				if(condPagtoFB!=null){
					tabPrecoId = condPagtoFB.getTabPrecoId();
				}
				selecionada.setTabPreco(new TabPrecoFBRN().carregar(tabPrecoId));
				selecionada.setTabPrecoId(tabPrecoId);
				
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
				selecionada.setFormaPagtoId(selecionada.getFormaPagto().getId());
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
				if(selecionada.getId() != null) {
					PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
					selecionada = pedVendaFBRN.mudarInformacoes(selecionada);	
				}
				
				selecionada.setFreteTipo(null);
				selecionada.setFreteTipoId(null);
				freteTipoSelectItem = null;
				
				selecionada.setCondPagto(null);
				selecionada.setCondPagtoId(null);
				condPagtoSelectItem = null;

			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			}
		}
	}
	
	public void verificarFreteTipo(){
		if(selecionada.getFreteTipo()!=null){
			
			try {
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
				// Alterando Tipo Movimentacao fiscal de acordo com a sele��o do frete na tela
				FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(selecionada.getFreteTipo().getId());
				if(freteTipoFB.getMovFiscTipoId()!=null) {
					MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(freteTipoFB.getMovFiscTipoId());
					if(movFiscTipoFB!=null) {
						selecionada.setMovFiscTipo(movFiscTipoFB);
						selecionada.setMovFiscTipoId(movFiscTipoFB.getId());
					}
					
				}else {
					ClienteFB clienteFB = new ClienteFBRN().carregar(selecionada.getClienteId());
					MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(clienteFB.getMovFiscTipoId());
					selecionada.setMovFiscTipoId(clienteFB.getMovFiscTipoId());
					selecionada.setMovFiscTipo(movFiscTipoFB);
				}
				
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
	
	public void verificarMovFiscTipo(){
		if(selecionada.getMovFiscTipo()!=null){
			try {
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
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
	
	
	public void verificarPrevRetirada(ValueChangeEvent event){
		if(event.getOldValue().equals(event.getNewValue()) == false){
			try {
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
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
		if(event.getOldValue() != null && event.getOldValue().equals(event.getNewValue()) == false){
			try {
				selecionada.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
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
		clienteNaoEncontrado = false;
		
		if(cliente!=null && cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
			cnpj = null;
			if("".equals(cpf) || cpf == null) {
				cpf = cliente.getCnpjCpf();
			}
			clienteVer = clienteFBRN.carregar(cpf);
		}else if(cliente!=null && cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
			cpf = null;
			nomeConsumidor = "";
			
			if("".equals(cnpj) || cnpj == null) {
				cnpj = cliente.getCnpjCpf();
			}
			
			clienteVer = clienteFBRN.carregar(cnpj);
		}else {
			if("".equals(outros) || outros == null) {
				outros = cliente.getCnpjCpf();
			}
			
			cnpj = null;
			cpf = null;
			clienteVer = clienteFBRN.carregar(outros);
		}

		
		if(clienteVer!=null){
			if(clienteVer.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
				nomePF = clienteVer.getNomeFantasia();
				nomeConsumidor = "";
				selecionada.setNomeCliente("");
			}else if(clienteVer.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
				nomePJ = clienteVer.getNomeFantasia();
				selecionada.setNomeCliente("");
				nomeConsumidor = "";
			}else {
				nomePO = clienteVer.getNomeFantasia();
				selecionada.setNomeCliente(nomeConsumidor);
			}
			
			if(clienteVer.getBloqManual() == 1) {
				clienteVer = null;
				//novoCliente();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.inativo.bloqueado")));
				
			}else if(clienteVer.getColigada()==1) {
				clienteVer = null;
				//novoCliente();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.coligado")));

			}else {
				// vai chegar informa��es de limite de credito
				try {
					clienteFBRN.verificarLimiteCredito(clienteVer, selecionada);
				} catch (RNException e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
				}	
				
				if(clienteVer.getCobrTipoId()!=null && clienteVer.getCobrTipo()==null) {
					CobrTipoFB cobrTipoFB = new CobrTipoFBRN().carregar(clienteVer.getCobrTipoId());
					clienteVer.setCobrTipo(cobrTipoFB);
				}
				
				if(clienteVer.getFormaPagtoId()!=null && clienteVer.getFormaPagto()==null) {
					FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(clienteVer.getFormaPagtoId());
					clienteVer.setFormaPagto(formaPagtoFB);
				}
				
				if(clienteVer.getMovFiscTipoId()!=null && clienteVer.getMovFiscTipo()==null) {
					MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(clienteVer.getMovFiscTipoId());
					clienteVer.setMovFiscTipo(movFiscTipoFB);
				}
				
				if(clienteVer.getFreteTipoId()!=null && clienteVer.getFreteTipo()==null) {
					FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(clienteVer.getFreteTipoId());
					clienteVer.setFreteTipo(freteTipoFB);
				}
				
				if(clienteVer.getCondPagtoId()!=null && clienteVer.getCondPagto()==null) {
					CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(clienteVer.getCondPagtoId());
					clienteVer.setCondPagto(condPagtoFB);
				}
				
				
				cliente = clienteVer;
				PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
				try {

					if(selecionada != null && selecionada.getId()!=null) {
						selecionada.setClienteId(clienteVer.getId());
						selecionada = pedVendaFBRN.mudarCliente(selecionada, cliente);
						verificarCondPagto();
					}else {
						selecionada = pedVendaFBRN.novo(empresa, vendedor, cliente, contextoBean.getUsuarioLogado(), encomenda);						
					}	
					
					if(nomeConsumidor!=null && !"".equals(nomeConsumidor)) {
						selecionada.setNomeCliente(nomeConsumidor);
					}
					
					verAlcada();
				} catch (RNException e) {
					e.printStackTrace();
					selecionada = null;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
				}
			}	
			
		}else{
			if(cpf!=null && cpf.length() == 11) {
				novoClienteCadastro();
				podeCadastrarCliente = true;
				cliente.setCnpjCpf(cpf);
			}else {
				podeCadastrarCliente = false;
				clienteNaoEncontrado = true;
				renderedBuscaCli = true; 
			}
			cliente.setEstadoId("PE");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.naocadastrado")));
		}
		
		listaCli = null;
		renderedBuscaCli = false;
		
		if(cliente!=null && cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_OUTRO)) {
			openItens();
		}	
	}
	

	public void verificarCpfCnpjBuscaCli(){
		ClienteFBRN clienteFBRN = new ClienteFBRN();
		
		if(clienteBusca!=null) { 
		
			if(clienteBusca.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
				cnpj = null;
				nomePF = clienteBusca.getNomeFantasia();
				if("".equals(cpf) || cpf == null) {
					cpf = clienteBusca.getCnpjCpf();
				}
				nomeConsumidor = "";
				
			}else if(clienteBusca.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
				cpf = null;
				nomeConsumidor = "";
				nomePJ = clienteBusca.getNomeFantasia();
				if("".equals(cnpj) || cnpj == null) {
					cnpj = clienteBusca.getCnpjCpf();
				}
				nomeConsumidor = "";
				
			}else {
				if("".equals(outros) || outros == null) {
					outros = clienteBusca.getCnpjCpf();
				}	
				nomePO = clienteBusca.getNomeFantasia();
				cnpj = null;
				cpf = null;
	
			}
			
			if(clienteBusca.getBloqManual() == 1) {
				clienteBusca = null;
				//novoCliente();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.inativo.bloqueado")));
				
			}else if(clienteBusca.getColigada()==1) {
				clienteBusca = null;
				//novoCliente();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.coligado")));

			}else {
				// vai chegar informa��es de limite de credito
				try {
					clienteFBRN.verificarLimiteCredito(clienteBusca, selecionada);
				} catch (RNException e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
				}	
				
				if(clienteBusca.getCobrTipoId()!=null && clienteBusca.getCobrTipo()==null) {
					CobrTipoFB cobrTipoFB = new CobrTipoFBRN().carregar(clienteBusca.getCobrTipoId());
					clienteBusca.setCobrTipo(cobrTipoFB);
				}
				
				if(clienteBusca.getFormaPagtoId()!=null && clienteBusca.getFormaPagto()==null) {
					FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(clienteBusca.getFormaPagtoId());
					clienteBusca.setFormaPagto(formaPagtoFB);
				}
				
				if(clienteBusca.getMovFiscTipoId()!=null && clienteBusca.getMovFiscTipo()==null) {
					MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(clienteBusca.getMovFiscTipoId());
					clienteBusca.setMovFiscTipo(movFiscTipoFB);
				}
				
				if(clienteBusca.getFreteTipoId()!=null && clienteBusca.getFreteTipo()==null) {
					FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(clienteBusca.getFreteTipoId());
					clienteBusca.setFreteTipo(freteTipoFB);
				}
				
				if(clienteBusca.getCondPagtoId()!=null && clienteBusca.getCondPagto()==null) {
					CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(clienteBusca.getCondPagtoId());
					clienteBusca.setCondPagto(condPagtoFB);
				}
				
				cliente = clienteBusca;
				
				PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
				try {
					if(selecionada != null && selecionada.getId()!=null) {
						selecionada.setClienteId(clienteBusca.getId());
						selecionada.setCobrTipoId(cliente.getCobrTipoId());
						selecionada.setCondPagtoId(cliente.getCondPagtoId());
						selecionada.setFreteTipoId(cliente.getFreteTipoId());
						selecionada.setMovFiscTipoId(cliente.getMovFiscTipoId());
						selecionada = pedVendaFBRN.mudarCliente(selecionada, cliente);
						verificarCondPagto();
					}else {
						selecionada = pedVendaFBRN.novo(empresa, vendedor, cliente, contextoBean.getUsuarioLogado(), encomenda);						
					}				
					
					if(nomeConsumidor!=null && !"".equals(nomeConsumidor)) {
						selecionada.setNomeCliente(nomeConsumidor);
					}


					verAlcada();
				} catch (RNException e) {
					e.printStackTrace();
					selecionada = null;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
				}
			}	
			
		}
		
		if(selecionada != null) {
			listaCli = null;
			renderedBuscaCli = false;
			if(cliente!=null && cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_OUTRO)) {
				openItens();
			}	
		}
	}
	
	
	public void verificarCpfCnpjModifCli(){
		try {
			ClienteFBRN clienteFBRN = new ClienteFBRN();
			
			if(clienteModif!=null){
				if(clienteModif.getBloqManual() == 1) {
					clienteModif = null;
					//novoCliente();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.inativo.bloqueado")));
					
				}else if(clienteModif.getColigada()==1) {
					clienteModif = null;
					//novoCliente();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.aviso.pedvenda.cliente.coligado")));

				}else {

					// vai chegar informa��es de limite de credito
					try {
						clienteFBRN.verificarLimiteCredito(clienteModif, selecionada);
					} catch (RNException e) {
						e.printStackTrace();
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
					}	

					selecionada.setClienteId(clienteModif.getId());
					clienteAlterar.setId(clienteModif.getId());
					clienteAlterar.setTipoPessoa(clienteModif.getTipoPessoa());
					
					if(clienteModif.getCobrTipoId()!=null && clienteModif.getCobrTipo()==null) {
						CobrTipoFB cobrTipoFB = new CobrTipoFBRN().carregar(clienteModif.getCobrTipoId());
						clienteModif.setCobrTipo(cobrTipoFB);
						clienteAlterar.setCobrTipoId(clienteModif.getCobrTipoId());
						if(!clienteModif.getCobrTipoId().equals(selecionada.getCobrTipoId())) {
							clienteAlterar.setCobrTipoId(selecionada.getCobrTipoId());
							alterouCli = true;
						}
					}
					
					if(clienteModif.getFormaPagtoId()!=null) {
						if(clienteModif.getFormaPagto()==null) {
					
							FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(clienteModif.getFormaPagtoId());
							clienteModif.setFormaPagto(formaPagtoFB);
							clienteAlterar.setFormaPagtoId(clienteModif.getFormaPagtoId());
							if(!clienteModif.getFormaPagtoId().equals(selecionada.getFormaPagtoId())) {
								clienteAlterar.setFormaPagtoId(selecionada.getFormaPagtoId());
								alterouCli = true;
							}
						}
					}else {
						clienteModif.setFormaPagto(selecionada.getFormaPagto());
						clienteAlterar.setFormaPagto(selecionada.getFormaPagto());
						clienteModif.setFormaPagtoId(selecionada.getFormaPagtoId());
						clienteAlterar.setFormaPagtoId(selecionada.getFormaPagtoId());
					}	
					
					if(clienteModif.getMovFiscTipoId()!=null && clienteModif.getMovFiscTipo()==null) {
						MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(clienteModif.getMovFiscTipoId());
						clienteModif.setMovFiscTipo(movFiscTipoFB);
						clienteAlterar.setMovFiscTipoId(clienteModif.getMovFiscTipoId());
						if(!clienteModif.getMovFiscTipoId().equals(selecionada.getMovFiscTipoId())) {
							clienteAlterar.setMovFiscTipoId(selecionada.getMovFiscTipoId());
							alterouCli = true;
						}
					}
					
					if(clienteModif.getFreteTipoId()!=null && clienteModif.getFreteTipo()==null) {
						FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(clienteModif.getFreteTipoId());
						clienteModif.setFreteTipo(freteTipoFB);
						clienteAlterar.setFreteTipoId(clienteModif.getFreteTipoId());
						if(!clienteModif.getFreteTipoId().equals(selecionada.getFreteTipoId())) {
							clienteAlterar.setFreteTipoId(selecionada.getFreteTipoId());
							alterouCli = true;
						}
						
					}
					
					if(clienteModif.getCondPagtoId()!=null && clienteModif.getCondPagto()==null) {
						CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(clienteModif.getCondPagtoId());
						clienteModif.setCondPagto(condPagtoFB);
						clienteAlterar.setCondPagtoId(clienteModif.getCondPagtoId());
						if(!clienteModif.getCondPagtoId().equals(selecionada.getCondPagtoId())) {
							clienteAlterar.setCondPagtoId(selecionada.getCondPagtoId());
							alterouCli = true;
						}
					}
					
					//  && !ClienteFB.TIPO_PESSOA_OUTRO.equals(clienteModif.getTipoPessoa())
					if(alterouCli) {
						showConfirmation();
					}
										
					if(clienteModif.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
						cnpj = null;
						nomePF = clienteModif.getNomeFantasia();
						cpf = clienteModif.getCnpjCpf();
						nomeConsumidor = "";
						
					}else if(clienteModif.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
						cpf = null;
						nomeConsumidor = "";
						nomePJ = clienteModif.getNomeFantasia();
						cnpj = clienteModif.getCnpjCpf();
						
					}else {
						outros = clienteModif.getCnpjCpf();
						nomePO = clienteModif.getNomeFantasia();
						cnpj = null;
						cpf = null;
			
					}
					
					
					cliente = clienteModif;
					
					if(selecionada.getId() != null) {
						PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
						selecionada = pedVendaFBRN.mudarCliente(selecionada, cliente);
						verificarCondPagto();
					}
				}
			}	
			
			clienteModif = null;
			listaCli = null;
			renderedBuscaCli = false;
			
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
 			if(listaProdutosLDM.getRowCount()==1) {
				produtoSelecionada = listaProdutosLDM.getRowData("0");
				
				if(produtoSelecionada.getInPedVenda()) {
					produtoSelecionada = null;
					focusFilterProduto();
				}else {
					addProduto();
				}
 			}else{
 				RequestContext.getCurrentInstance().execute("PF('UIdtItensPedVendaBean').clearFilters();");
 			}
 			
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
			
			pedVendaItem = new PedVendaItemFBRN().novo(selecionada, produtoSelecionada, null);
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
			
			produtoSelecionada = new ProdutoFBRN().carregar(selecionada.getEncomenda(), produtoId, selecionada.getEncomenda(), selecionada.getEmpresaId(), selecionada.getMovFiscTipo().getOpFiscTipoId(), contextoBean.getUsuarioLogado().getId(), selecionada.getTabPrecoId(), selecionada.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter, selecionada.getFreteTipoId());
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
	
	public void excluirProdComposto(Integer pedVendaCompostoId, Integer usuarioId) {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.excluirProdComposto(selecionada, pedVendaCompostoId, usuarioId);
			
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
		if(produtoSelecionada.getQtdPromoMin() != null && produtoSelecionada.getQtdPromoMax() != null){
			if(pedVendaItem.getPrecoProm() > 0 && (produtoSelecionada.getQtdPromoMin() > 0 || produtoSelecionada.getQtdPromoMax() > 0)) {
				if(total>=produtoSelecionada.getQtdPromoMin() && total<=produtoSelecionada.getQtdPromoMax()) {
					pedVendaItem.setPreco(pedVendaItem.getPrecoProm());
					pedVendaItem.setPercDesconto(0.0);
					descItem = 0d;
				}else {
					pedVendaItem.setPreco(pedVendaItem.getPrecoTabela());
				}
			}
		}
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
	
	public void somarQtdPedVendaItem(ProdutoEstoqueFB est, Double qtdPromoMax) {
		try {
			
			if(est.getQtdReservar()==null) {
				est.setQtdReservar(0.0);
			}
			
			if(est.getQtdReservar()<=est.getQtdDisponivel()) {
				if(qtdPromoMax!=null && qtdPromoMax > 0 && est.getQtdReservar()<=qtdPromoMax) {
						Double qtd = (est.getQtdReservar()==null ? 0.0 : est.getQtdReservar()) + est.getQtdVendaAtac();
						if(qtd<=est.getQtdDisponivel()) {
							//if(qtd<=qtdPromoMax) {
								est.setQtdReservar(qtd);
							//}	
						}

				}else {
					Double qtd = (est.getQtdReservar()==null ? 0.0 : est.getQtdReservar()) + est.getQtdVendaAtac();
					if(qtd<=est.getQtdDisponivel()) {
						est.setQtdReservar(qtd);
					}
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
			
			pedVendaItem.setUsuarioWebId(contextoBean.getUsuarioLogado().getId());
			
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
			
			if(cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_OUTRO)) {
				if(selecionada.getNomeCliente() !=null && !"".equals(selecionada.getNomeCliente()) 
						                           && !selecionada.getObservacao().contains(selecionada.getNomeCliente())) {
					selecionada.setObservacao("Cliente: "+selecionada.getNomeCliente()+" - "+selecionada.getObservacao());
				}
			}	

			pedVendaFBRN.concluir(FacesContext.getCurrentInstance(), selecionada);
			
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
			totalPrecoTab += rs.getInPromocao() ? Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (rs.getPrecoProm() * rs.getQuantidade()))) : Funcoes.arrendondaValor(2, Funcoes.arrendondaValor(4, (rs.getPrecoTabela() * rs.getQuantidade())));
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
	
	private void carregarInfoCli() {
		
		if(empresa.getIdClientePadraoFDL()!=null) {
			ClienteFB clientePadrao = new ClienteFBRN().carregar(empresa.getIdClientePadraoFDL());
			if(clientePadrao!=null) {
				nomePO = clientePadrao.getNomeFantasia();
				outros = clientePadrao.getCnpjCpf();
			}
		}

		if(cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
			cnpj = null;
			nomePF = cliente.getNomeFantasia();
			if("".equals(cpf) || cpf == null) {
				cpf = cliente.getCnpjCpf();
			}
			
		}else if(cliente.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
			cpf = null;
			nomeConsumidor = "";
			nomePJ = cliente.getNomeFantasia();
			if("".equals(cnpj) || cnpj == null) {
				cnpj = cliente.getCnpjCpf();
			}
			
		}else {
			if("".equals(outros) || outros == null) {
				outros = cliente.getCnpjCpf();
			}	
			nomePO = cliente.getNomeFantasia();
			cnpj = null;
			cpf = null;

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
		
		clienteAlterar = new ClienteFB();
		clienteAlterar.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		clienteAlterar.setAtivo(ClienteFB.CLIENTE_ATIVO);
		
		alterouCli = false;
		renderedBuscaCli = false;
	}
	
	public void showConfirmation() {
		RequestContext.getCurrentInstance().execute("PF('confirmDialog').show();");
    }

    public void confirmAction() {
		try {
			
			if(clienteAlterar.getCobrTipoId()!=null && clienteAlterar.getCobrTipo()==null) {
				CobrTipoFB cobrTipoFB = new CobrTipoFBRN().carregar(clienteAlterar.getCobrTipoId());
				clienteAlterar.setCobrTipo(cobrTipoFB);
			}
			
			if(clienteAlterar.getFormaPagtoId()!=null && clienteAlterar.getFormaPagto()==null) {
				FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(clienteAlterar.getFormaPagtoId());
				clienteAlterar.setFormaPagto(formaPagtoFB);
			}
			
			if(clienteAlterar.getMovFiscTipoId()!=null && clienteAlterar.getMovFiscTipo()==null) {
				MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFBRN().carregar(clienteAlterar.getMovFiscTipoId());
				clienteAlterar.setMovFiscTipo(movFiscTipoFB);
			}
			
			if(clienteAlterar.getFreteTipoId()!=null && clienteAlterar.getFreteTipo()==null) {
				FreteTipoFB freteTipoFB = new FreteTipoFBRN().carregar(clienteAlterar.getFreteTipoId());
				clienteAlterar.setFreteTipo(freteTipoFB);
			}
			
			if(clienteAlterar.getCondPagtoId()!=null && clienteAlterar.getCondPagto()==null) {
				CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(clienteAlterar.getCondPagtoId());
				clienteAlterar.setCondPagto(condPagtoFB);
			}

        
	        if(selecionada.getId() != null) {
				PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
				selecionada = pedVendaFBRN.mudarCliente(selecionada, clienteAlterar);
				verificarCondPagto();
			}
	        
	        clienteAlterar = null;
			alterouCli = false;
			
			RequestContext.getCurrentInstance().execute("PF('confirmDialog').hide();");
	        
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, e.getMessage()));
			e.printStackTrace();
		}

		
    }

    public void cancelAction() {
    	clienteAlterar = null;
		alterouCli = false;
		
		formaPagtoSelectItem = null;
		selecionada.setFormaPagtoId(null);
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
	
	public ClienteFB getClienteAlterar() {
		return clienteAlterar;
	}

	public void setClienteAlterar(ClienteFB clienteAlterar) {
		this.clienteAlterar = clienteAlterar;
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
		if(condPagtoSelectItem == null && selecionada != null && selecionada.getFormaPagtoId() != null) {
			CondPagtoFBRN condPagtoFBRN = new CondPagtoFBRN();
			List<CondPagtoFB> listaCondPagto = condPagtoFBRN.listar(selecionada.getFormaPagtoId(), selecionada.getEmpresaId(), selecionada.getClienteId());
			if(listaCondPagto != null && listaCondPagto.size()==0 && selecionada.getFormaPagtoId()!=null) {
				listaCondPagto = condPagtoFBRN.listar(selecionada.getFormaPagtoId(), selecionada.getEmpresaId());
			}
			condPagtoSelectItem = condPagtoFBRN.montaDadosSelect(listaCondPagto, null);
			
			if(listaCondPagto != null && selecionada.getCondPagtoId() == null) {
				ClienteFB clienteFB = new ClienteFBRN().carregar(selecionada.getClienteId());
				if(clienteFB!=null && listaCondPagto.contains(clienteFB.getCondPagto())) {
					selecionada.setCondPagtoId(clienteFB.getCondPagtoId());
					selecionada.setCondPagto(clienteFB.getCondPagto());
				}else{
					selecionada.setCondPagtoId(listaCondPagto.get(0).getId());
					selecionada.setCondPagto(listaCondPagto.get(0));
				}	
				
			}
			
		}	
		
		return condPagtoSelectItem;
	}

	public void setCondPagtoSelectItem(List<SelectItem> condPagtoSelectItem) {
		this.condPagtoSelectItem = condPagtoSelectItem;
	}
	
	public List<SelectItem> getFreteTipoSelectItem() {
		if(freteTipoSelectItem == null && selecionada != null && selecionada.getFormaPagtoId() != null) {
			FreteTipoFBRN freteTipoFBRN = new FreteTipoFBRN();
			List<FreteTipoFB> listaFreteTipo = freteTipoFBRN.listar(selecionada.getFormaPagtoId());
			if(listaFreteTipo != null && listaFreteTipo.size() > 0 && selecionada.getFreteTipo() == null) {
				ClienteFB clienteFB = new ClienteFBRN().carregar(selecionada.getClienteId());
				if(clienteFB!=null && listaFreteTipo.contains(clienteFB.getFreteTipo())) {
					selecionada.setFreteTipoId(clienteFB.getFreteTipoId());
					selecionada.setFreteTipo(clienteFB.getFreteTipo());
				}else {
					selecionada.setFreteTipoId(listaFreteTipo.get(0).getId());
					selecionada.setFreteTipo(listaFreteTipo.get(0));
				}
				
			}
			
			freteTipoSelectItem = freteTipoFBRN.montaDadosSelect(listaFreteTipo, null);
			
			if(selecionadaId!=null && selecionada.getFreteTipoId()!=null) {
				verificarCondPagto();
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
			//List<FormaPagtoFB> listaFormaPagto = formaPagtoFBRN.listarPorFreteTipo(selecionada.getFreteTipoId());
			List<FormaPagtoFB> listaFormaPagto = formaPagtoFBRN.listarFormaCond(selecionada.getEmpresaId());
			formaPagtoSelectItem = formaPagtoFBRN.montaDadosSelect(listaFormaPagto, null);
			
			if(listaFormaPagto != null && listaFormaPagto.size()>0 && selecionada.getFormaPagtoId() == null) {
						ClienteFB clienteFB = new ClienteFBRN().carregar(selecionada.getClienteId());
						if(clienteFB!=null && clienteFB.getCobrTipoId()==0) {
							FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(0);
							selecionada.setFormaPagto(formaPagtoFB);
							selecionada.setFormaPagtoId(formaPagtoFB.getId());
						}else {
							FormaPagtoFB formaPagtoFB = new FormaPagtoFBRN().carregar(2);
							selecionada.setFormaPagto(formaPagtoFB);
							selecionada.setFormaPagtoId(formaPagtoFB.getId());
						}
			}
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
			tipoMovFiscSelectItem = movFiscTipoFBRN.montaDadosSelect(movFiscTipoFBRN.listarPedVenda(), null);
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
	
	public boolean isRenderedBuscaCli() {
		return renderedBuscaCli;
	}

	public void setRenderedBuscaCli(boolean renderedBuscaCli) {
		this.renderedBuscaCli = renderedBuscaCli;
	}

	public boolean isPodeBuscarCli() {
		return podeBuscarCli;
	}

	public void setPodeBuscarCli(boolean podeBuscarCli) {
		this.podeBuscarCli = podeBuscarCli;
	}

	public boolean isClienteNaoEncontrado() {
		return clienteNaoEncontrado;
	}

	public void setClienteNaoEncontrado(boolean clienteNaoEncontrado) {
		this.clienteNaoEncontrado = clienteNaoEncontrado;
	}

	public boolean isTipoCnpj() {
		return tipoCnpj;
	}

	public void setTipoCnpj(boolean tipoCnpj) {
		this.tipoCnpj = tipoCnpj;
	}

	public boolean isTipoCpf() {
		return tipoCpf;
	}

	public void setTipoCpf(boolean tipoCpf) {
		this.tipoCpf = tipoCpf;
	}

	public boolean isTipoOutros() {
		return tipoOutros;
	}

	public void setTipoOutros(boolean tipoOutros) {
		this.tipoOutros = tipoOutros;
	}

	public boolean isAlterouCli() {
		return alterouCli;
	}

	public void setAlterouCli(boolean alterouCli) {
		this.alterouCli = alterouCli;
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

	public ClienteFB getClienteNovo() {
		return clienteNovo;
	}

	public void setClienteNovo(ClienteFB clienteNovo) {
		this.clienteNovo = clienteNovo;
	}

	public ClienteFB getClienteBusca() {
		return clienteBusca;
	}

	public void setClienteBusca(ClienteFB clienteBusca) {
		this.clienteBusca = clienteBusca;
	}

	public String getNomeConsumidor() {
		return nomeConsumidor;
	}

	public void setNomeConsumidor(String nomeConsumidor) {
		this.nomeConsumidor = nomeConsumidor;
	}

	public String getTipoPessoaF() {
		return tipoPessoaF;
	}

	public void setTipoPessoaF(String tipoPessoaF) {
		this.tipoPessoaF = tipoPessoaF;
	}

	public String getTipoPessoaJ() {
		return tipoPessoaJ;
	}

	public void setTipoPessoaJ(String tipoPessoaJ) {
		this.tipoPessoaJ = tipoPessoaJ;
	}

	public String getTipoPessoaO() {
		return tipoPessoaO;
	}

	public void setTipoPessoaO(String tipoPessoaO) {
		this.tipoPessoaO = tipoPessoaO;
	}

	public String getNomePF() {
		return nomePF;
	}

	public void setNomePF(String nomePF) {
		this.nomePF = nomePF;
	}

	public String getNomePJ() {
		return nomePJ;
	}

	public void setNomePJ(String nomePJ) {
		this.nomePJ = nomePJ;
	}

	public String getNomePO() {
		return nomePO;
	}

	public void setNomePO(String nomePO) {
		this.nomePO = nomePO;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	//	
	public void clearSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.removeAttribute("pedVendaNovoBean");
	}

	

}