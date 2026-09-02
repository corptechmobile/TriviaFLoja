package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTORN;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.ECFVendasFB;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.ECFVendasFBRN;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTORN;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvenda.VendaFornPedVenda;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvenda.VendaFornPedVendaRN;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumo;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumoRN;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFBRN;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTORN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

@ManagedBean(name = "vendaFornBean")
@SessionScoped
public class VendaFornBean implements Serializable, IMenuAcesso{
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.VENDAFORM_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;

	private static final long serialVersionUID = -4728916230625173285L;
	
	private VendaFornDTO selecionada;
	private VendasProdutoDTO selecionadaProduto;
	private VendaFornPedVenda selecionadaPedVenda;
	private VendaFornFPagtoDTO selecionadaFPagto;
	private VendaFornFPagtoDTO selecionadaFPagtoCartoes;
	private List<VendaFornDTO> lista;
	private List<VendasProdutoDTO> listaProdutos;
	private Map<String, Double> totalUNListaProdutos;
	private List<VendaFornPedVenda> listaPedidos;
	private List<ECFVendasFB> listaECFs;
	private VendaFornResumo resumo;
	private Double faturamentoDia;
	private Double devolucaoDia;
	private Double mediaDia;	
	private Double ticketMedioDia;
	private Integer numClientesDoDia;
	private Integer numClientes;
	private List<VendaFornFPagtoDTO> formas;
	private String autoServicoPedAmbos;
	private String porFilter;
	private String porFilterLabel;
	private String porFilterColumn;
	private String vendasPorFilter;
	private String segmentoFilter;
	private EmpresaFB empresaFilter;
	private Date dataFilter1;
	private Date dataFilter2;
	private Double valorTotal;
	private Double valorTotalDev;
	private Double valorTotalLiq;
	private Double valorTotalProd;
	private Double valorTotalProdDev;
	private Double valorTotalPedVenda;
	private Double qtdTotalPedVenda;
	private Double somaValorTotal;
	private Double mediaDescontos;
	private Double somaValorTotalFPagto;
	private List<SelectItem> empresasSelect;
	private VendedorFB vendedorFilter;
	private TipoVendedorFB tipoVendedorFilter;
	private List<SelectItem> vendedorSelect;
	private List<SelectItem> tipoVendedorSelect;
	
	private boolean renderedTabProduto;
	private boolean renderedTabForn;
	private boolean renderedTabPedido;
	private boolean renderedTabFPagto;
	private int currentTab;
	private String tituloPedidos;
	
	private DiasUteisFB diasUteis;
	
	@PostConstruct
	public void init(){
		System.out.println("[VendaFornBean][init]");
		onStartDatas();
		setAutoServicoPedAmbos("ambos");
		
		currentTab = 0;
		porFilter = "linhaProduto";
		
		renderedTabForn = false;
		selecionada = null;
		selecionadaProduto = null;
		listaProdutos = null;
		listaPedidos = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			if(contextoBean.verificaPermissao(menu) == false){
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../page404.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				addRecentes();
			}
		}
	}
	
	private void startDiasUteis() {
		diasUteis = new DiasUteisFBRN().carregar(dataFilter2); 
	}

	public void buscar() {
		
		if (empresaFilter == null) {
			empresaFilter = new EmpresaFB();
		}
		
		if (vendedorFilter == null) {
			vendedorFilter = new VendedorFB();
		}
		
		
		lista = null;
		formas = null;
		
		valorTotal = 0.0;
		valorTotalDev = 0.0;
		valorTotalLiq = 0.0;
		valorTotalPedVenda = 0.0;
		qtdTotalPedVenda = 0.0;
		somaValorTotalFPagto=0.0;
		VendaFornDTORN vendaFornDTORN = new VendaFornDTORN();
		vendasPorFilter = "pedido";
		if (autoServicoPedAmbos.equals("ambos") && !porFilter.equals("fpagto")) {
			lista = vendaFornDTORN.listarAmbos(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
		} else if (autoServicoPedAmbos.equals("autoServico") && !porFilter.equals("fpagto")) {
			lista = vendaFornDTORN.listarAutoServico(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
		} else if (!porFilter.equals("fpagto")){
			lista = vendaFornDTORN.listarPedido(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
		} else if (porFilter.equals("fpagto")){
			vendasPorFilter = "pedido";
			
			VendaFornFPagtoDTORN vendaFornFPagtoDTORN = new VendaFornFPagtoDTORN();
			formas = vendaFornFPagtoDTORN.listar(empresaFilter, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2);
			if(formas!=null) {
				for(VendaFornFPagtoDTO rs : formas) {
					somaValorTotalFPagto += rs.getValor();
				}
			}
		}
		
		if(lista!=null) {
			
			for (VendaFornDTO rs : lista) {
				valorTotal += rs.getValor();
				valorTotalDev += rs.getVlDevolvido();
			}
			
			valorTotalLiq = valorTotal - valorTotalDev;
			
			for (VendaFornDTO rs : lista) {
				Double dou = 0.0;
				try {
					dou = (rs.getValor()/valorTotal) * 100;
				} catch (Exception e) {
					dou = 0.0;
					e.printStackTrace();
				}
				rs.setPercValor(Funcoes.arrendondaValor(0, dou));
			}
		
		}
		
		startDiasUteis();
		
		
		VendaFornResumoRN vendaFornResumoRN = new VendaFornResumoRN();
		resumo = vendaFornResumoRN.carregarMes(empresaFilter, vendedorFilter, tipoVendedorFilter, null, dataFilter2, vendasPorFilter, segmentoFilter);
		numClientes = 0;
		if(resumo != null) {
			numClientes = resumo.getNumClientes();
		}
		
		numClientesDoDia = 0;
		faturamentoDia = 0.0;
		devolucaoDia = 0.0;
		VendaFornResumo vendaFornResumo = vendaFornResumoRN.carregar(empresaFilter, vendedorFilter, tipoVendedorFilter, null, dataFilter2, dataFilter2, vendasPorFilter, segmentoFilter);
		if(vendaFornResumo != null) {
			faturamentoDia = vendaFornResumo.getFaturamento();
			devolucaoDia = vendaFornResumo.getDevolucao();
			numClientesDoDia = vendaFornResumo.getNumClientes();
		}
		
		ticketMedioDia = 0.0;
		if(numClientesDoDia > 0){
			ticketMedioDia = (faturamentoDia - devolucaoDia)/numClientesDoDia;
		}
		
		mediaDia = 0.0;
		try {
			if(diasUteis.getPrazoDecorrido() > 0){
				mediaDia = (resumo.getFaturamento()-resumo.getDevolucao())/diasUteis.getPrazoDecorrido();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		currentTab = 0;
		selecionada = null;
		selecionadaProduto = null;
		selecionadaFPagto = null;
		selecionadaFPagtoCartoes = null;
		listaProdutos = null;
		listaPedidos = null;
		listaECFs = null;
		renderedTabForn = true;
		renderedTabProduto = false;
		renderedTabPedido = false;
		renderedTabFPagto = false;
		
	}
	
	public void buscarNFCes() {
		listaECFs = new ECFVendasFBRN().listarACancelar(empresaFilter.getId(), vendedorFilter.getId(), tipoVendedorFilter.getId(), dataFilter1, dataFilter2);
	}
	
	public void openProduto() {
		
		currentTab = currentTab + 1;
		
		renderedTabProduto = true;
		renderedTabPedido = false;
		renderedTabFPagto = false;

		selecionadaFPagto = null;
		selecionadaFPagtoCartoes = null;
		listaProdutos = null;
		listaECFs = null;
		formas = null;
	}
	
	public void openPedido() {
		
		currentTab = currentTab + 1;
		
		renderedTabPedido = true;
		
		listaPedidos = null;
		listaECFs = null;
	}
	
	public void openPedidoFPagto(){
		
		currentTab = currentTab + 1;
		
		renderedTabProduto = false;
		renderedTabPedido = true;
		renderedTabFPagto = false;
		
		selecionada = null;
		selecionadaProduto = null;
		listaPedidos = null;
		listaECFs = null;
	}
	
	public void openPedidoPorCartao(){
		
		currentTab = currentTab + 1;
		
		renderedTabProduto = false;
		renderedTabPedido = true;
		
		selecionada = null;
		selecionadaProduto = null;
		listaPedidos = null;
		listaECFs = null;
	}
	
	public void openFPagto() {
		
		currentTab = currentTab + 1;
		
		renderedTabProduto = false;
		renderedTabPedido = false;
		renderedTabFPagto = true;
		
		selecionada = null;
		selecionadaProduto = null;
		listaProdutos = null;
		listaECFs = null;
		
	}
	
	public void onTabChange(TabChangeEvent event) {
		try {
			currentTab = Integer.parseInt(event.getTab().getAttributes().get("activeIndex").toString());
		} catch (Exception e) {
			//e.printStackTrace();
		}
    }
         
    public void onTabClose(TabCloseEvent event) {
    	try {
	        if(event.getTab().getId().equals("tabDtVendaProdBean") || event.getTab().getId().equals("tabDtVendaFPagtoBean")) {
	        	currentTab = 0;
	    		renderedTabProduto = false;
	    		renderedTabFPagto = false;
	    		renderedTabPedido = false;
	    		listaPedidos = null;
	        }else if(event.getTab().getId().equals("tabDtVendaProdBeanPedVenda")) {
	        	if(renderedTabFPagto || renderedTabProduto) {
	        		currentTab = 1;
	        	}else {
	        		currentTab = 0;
	        	}
	        	
	        	renderedTabPedido = false;
	        }
    	} catch (Exception e) {
			//e.printStackTrace();
		}
    }
	
	public void limpar() {
		
		onStartDatas();
		
		setAutoServicoPedAmbos("ambos");
		empresaFilter = null;
		renderedTabForn = false;
		renderedTabProduto = false;
		renderedTabFPagto = false;
		
		lista = null;
		selecionada = null;
		listaProdutos = null;
		listaECFs = null;
		formas = null;
		resumo = null;
		
	}
	
	public void onStartDatas(){
		
		if(dataFilter1==null || dataFilter2==null){
			Calendar caIni = Calendar.getInstance();
			caIni.set(Calendar.DAY_OF_MONTH, 1);
			//caIni.set(Calendar.MONTH, Calendar.OCTOBER); // TODO TESTE
			caIni.set(Calendar.HOUR, 0);
			caIni.set(Calendar.MINUTE, 0);
			caIni.set(Calendar.SECOND, 0);
			caIni.set(Calendar.AM_PM, Calendar.AM);
			
			this.dataFilter1 = caIni.getTime();
			
			Calendar caFim = Calendar.getInstance();
			// caFim.set(Calendar.DAY_OF_MONTH, 31); // TODO TESTE
			//caFim.set(Calendar.MONTH, Calendar.OCTOBER); // TODO TESTE
			caFim.set(Calendar.HOUR, 11);
			caFim.set(Calendar.MINUTE, 59);
			caFim.set(Calendar.SECOND, 59);
			caFim.set(Calendar.AM_PM, Calendar.PM);
			
			this.dataFilter2 = caFim.getTime();
			
			startDiasUteis();
		}
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public VendaFornDTO getSelecionada() {
		return selecionada;
	}


	public void setSelecionada(VendaFornDTO selecionada) {
		this.selecionada = selecionada;
	}

	public VendasProdutoDTO getSelecionadaProduto() {
		return selecionadaProduto;
	}

	public void setSelecionadaProduto(VendasProdutoDTO selecionadaProduto) {
		this.selecionadaProduto = selecionadaProduto;
	}

	public VendaFornPedVenda getSelecionadaPedVenda() {
		return selecionadaPedVenda;
	}

	public void setSelecionadaPedVenda(VendaFornPedVenda selecionadaPedVenda) {
		this.selecionadaPedVenda = selecionadaPedVenda;
	}
	
	public VendaFornFPagtoDTO getSelecionadaFPagto() {
		return selecionadaFPagto;
	}

	public void setSelecionadaFPagto(VendaFornFPagtoDTO selecionadaFPagto) {
		this.selecionadaFPagto = selecionadaFPagto;
	}
	
	public VendaFornFPagtoDTO getSelecionadaFPagtoCartoes() {
		return selecionadaFPagtoCartoes;
	}

	public void setSelecionadaFPagtoCartoes(VendaFornFPagtoDTO selecionadaFPagtoCartoes) {
		this.selecionadaFPagtoCartoes = selecionadaFPagtoCartoes;
	}

	public List<VendaFornDTO> getLista() {
		return lista;
	}

	public void setLista(List<VendaFornDTO> lista) {
		this.lista = lista;
	}

	public List<VendasProdutoDTO> getListaProdutos() {
		if(listaProdutos == null && selecionada!=null) {
			VendasProdutoDTORN vendasProdutoDTORN = new VendasProdutoDTORN();
			if (autoServicoPedAmbos.equals("ambos")) {
				listaProdutos = vendasProdutoDTORN.listarProdutosAmbos(empresaFilter, vendedorFilter, dataFilter1, dataFilter2, selecionada.getId(), porFilter);
			} else if (autoServicoPedAmbos.equals("autoServico")) {
				listaProdutos = vendasProdutoDTORN.listarProdutosAutoServico(empresaFilter, vendedorFilter, dataFilter1, dataFilter2, selecionada.getId(), porFilter);
			} else {
				listaProdutos = vendasProdutoDTORN.listarProdutosPedido(empresaFilter, vendedorFilter, dataFilter1, dataFilter2, selecionada.getId(), porFilter);
			}
			
			valorTotalProd = 0.0;
			valorTotalProdDev = 0.0;
			totalUNListaProdutos = new HashMap<String, Double>();
			for (VendasProdutoDTO rs : listaProdutos) {
				valorTotalProd += rs.getValor();
				valorTotalProdDev += rs.getVlDevolvido();
				totalUNListaProdutos.put(rs.getUn(), 0.0);
			}
			
			for (VendasProdutoDTO rs : listaProdutos) {
				totalUNListaProdutos.put(rs.getUn(), totalUNListaProdutos.get(rs.getUn()).doubleValue() + rs.getQtdeAuto());
			}
		}
		return listaProdutos;
	}

	public void setListaProdutos(List<VendasProdutoDTO> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	public Map<String, Double> getTotalUNListaProdutos() {
		return totalUNListaProdutos;
	}

	public void setTotalUNListaProdutos(Map<String, Double> totalUNListaProdutos) {
		this.totalUNListaProdutos = totalUNListaProdutos;
	}

	public List<VendaFornPedVenda> getListaPedidos() {
		if(listaPedidos==null && ((selecionada !=null && selecionadaProduto != null) || selecionadaFPagto != null)) {
			valorTotalPedVenda = 0.0;
			qtdTotalPedVenda = 0.0;
			VendaFornPedVendaRN vendaFornPedVendaRN = new VendaFornPedVendaRN();
			if("fpagto".equals(porFilter)) {
				listaPedidos = vendaFornPedVendaRN.listarPedVendaFpgto(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
			}else {
				listaPedidos = vendaFornPedVendaRN.listarPedVenda(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
			}
			
			for(VendaFornPedVenda rs : listaPedidos) {
				if(rs.getValor() != null){
					valorTotalPedVenda += rs.getValor();
				}	
				if(rs.getQtde() != null) {
					qtdTotalPedVenda += rs.getQtde();
				}
				
			}
		}
		return listaPedidos;
	}

	public void setListaPedidos(List<VendaFornPedVenda> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public VendaFornResumo getResumo() {
		return resumo;
	}

	public void setResumo(VendaFornResumo resumo) {
		this.resumo = resumo;
	}
	
	public Double getFaturamentoDia() {
		return faturamentoDia;
	}

	public void setFaturamentoDia(Double faturamentoDia) {
		this.faturamentoDia = faturamentoDia;
	}

	public Double getDevolucaoDia() {
		return devolucaoDia;
	}

	public void setDevolucaoDia(Double devolucaoDia) {
		this.devolucaoDia = devolucaoDia;
	}
	
	public Integer getNumClientesDoDia() {
		return numClientesDoDia;
	}

	public void setNumClientesDoDia(Integer numClientesDoDia) {
		this.numClientesDoDia = numClientesDoDia;
	}

	public List<VendaFornFPagtoDTO> getFormas() {
		return formas;
	}

	public void setFormas(List<VendaFornFPagtoDTO> formas) {
		this.formas = formas;
	}

	public String getAutoServicoPedAmbos() {
		return autoServicoPedAmbos;
	}

	public void setAutoServicoPedAmbos(String autoServicoPedAmbos) {
		this.autoServicoPedAmbos = autoServicoPedAmbos;
	}

	public String getPorFilter() {
		return porFilter;
	}

	public void setPorFilter(String porFilter) {
		this.porFilter = porFilter;
	}

	public String getPorFilterLabel() {
		if (porFilter.equals("fornecedor")) {
			porFilterLabel = "Fornecedores";
		}else if (porFilter.equals("vendedor")) {
			porFilterLabel = "Vendedores";
		}else if (porFilter.equals("linhaProduto")) {
			porFilterLabel = "Linhas de Produtos";
		}else if (porFilter.equals("fpagto")) {
			porFilterLabel = "F. Pagto";
		}else if (porFilter.equals("tipovend")) {
			porFilterLabel = "Tipo Vendedor";
		}else {
			porFilterLabel = "";
		}
		return porFilterLabel;
	}

	public void setPorFilterLabel(String porFilterLabel) {
		this.porFilterLabel = porFilterLabel;
	}

	public String getPorFilterColumn() {
		if (porFilter.equals("fornecedor")) {
			porFilterColumn = "Fornecedor";
		}else if (porFilter.equals("vendedor")) {
			porFilterColumn = "Vendedor";
		}else if (porFilter.equals("tipovend")) {
			porFilterColumn = "Tipo Vendedor";
		}else {
			porFilterColumn = "Linha de Produto";
		}
		return porFilterColumn;
	}

	public void setPorFilterColumn(String porFilterColumn) {
		this.porFilterColumn = porFilterColumn;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}


	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public String getVendasPorFilter() {
		return vendasPorFilter;
	}

	public void setVendasPorFilter(String vendasPorFilter) {
		this.vendasPorFilter = vendasPorFilter;
	}

	public String getSegmentoFilter() {
		return segmentoFilter;
	}

	public void setSegmentoFilter(String segmentoFilter) {
		this.segmentoFilter = segmentoFilter;
	}

	public Date getDataFilter1() {
		return dataFilter1;
	}


	public void setDataFilter1(Date dataFilter1) {
		this.dataFilter1 = dataFilter1;
	}


	public Date getDataFilter2() {
		return dataFilter2;
	}


	public void setDataFilter2(Date dataFilter2) {
		this.dataFilter2 = dataFilter2;
	}


	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Double getValorTotalDev() {
		return valorTotalDev;
	}

	public void setValorTotalDev(Double valorTotalDev) {
		this.valorTotalDev = valorTotalDev;
	}
	
	public Double getValorTotalLiq() {
		return valorTotalLiq;
	}

	public void setValorTotalLiq(Double valorTotalLiq) {
		this.valorTotalLiq = valorTotalLiq;
	}

	public Double getValorTotalProd() {
		return valorTotalProd;
	}

	public void setValorTotalProd(Double valorTotalProd) {
		this.valorTotalProd = valorTotalProd;
	}
	
	public Double getValorTotalProdDev() {
		return valorTotalProdDev;
	}

	public void setValorTotalProdDev(Double valorTotalProdDev) {
		this.valorTotalProdDev = valorTotalProdDev;
	}
	
	public Double getValorTotalPedVenda() {
		return valorTotalPedVenda;
	}

	public void setValorTotalPedVenda(Double valorTotalPedVenda) {
		this.valorTotalPedVenda = valorTotalPedVenda;
	}
	
	public Double getQtdTotalPedVenda() {
		return qtdTotalPedVenda;
	}

	public void setQtdTotalPedVenda(Double qtdTotalPedVenda) {
		this.qtdTotalPedVenda = qtdTotalPedVenda;
	}

	public Double getSomaValorTotal() {
		return somaValorTotal;
	}

	public void setSomaValorTotal(Double somaValorTotal) {
		this.somaValorTotal = somaValorTotal;
	}

	public Double getMediaDescontos() {
		return mediaDescontos;
	}

	public void setMediaDescontos(Double mediaDescontos) {
		this.mediaDescontos = mediaDescontos;
	}

	public List<SelectItem> getEmpresasSelect() {
		if (this.empresasSelect == null) {
			
			this.empresasSelect = new ArrayList<SelectItem>();
			
			EmpresaFBRN empresaRN = new EmpresaFBRN();
			this.empresasSelect = empresaRN.montaDadosSelect(this.getContextoBean().getUsuarioLogado().getEmpresas(), "");
			
		}
		return empresasSelect;
	}


	public void setEmpresasSelect(List<SelectItem> empresasSelect) {
		this.empresasSelect = empresasSelect;
	}

	public boolean isRenderedTabProduto() {
		return renderedTabProduto;
	}

	public void setRenderedTabProduto(boolean renderedTabProduto) {
		this.renderedTabProduto = renderedTabProduto;
	}

	public boolean isRenderedTabForn() {
		return renderedTabForn;
	}

	public void setRenderedTabForn(boolean renderedTabForn) {
		this.renderedTabForn = renderedTabForn;
	}
	
	public boolean isRenderedTabPedido() {
		return renderedTabPedido;
	}

	public void setRenderedTabPedido(boolean renderedTabPedido) {
		this.renderedTabPedido = renderedTabPedido;
	}
	
	public boolean isRenderedTabFPagto() {
		return renderedTabFPagto;
	}

	public void setRenderedTabFPagto(boolean renderedTabFPagto) {
		this.renderedTabFPagto = renderedTabFPagto;
	}

	public int getCurrentTab() {
		if (currentTab == 1) {
			getListaProdutos();
		}
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}
	
	public DiasUteisFB getDiasUteis() {
		return diasUteis;
	}

	public void setDiasUteis(DiasUteisFB diasUteis) {
		this.diasUteis = diasUteis;
	}

	public Double getSomaValorTotalFPagto() {
		return somaValorTotalFPagto;
	}

	public void setSomaValorTotalFPagto(Double somaValorTotalFPagto) {
		this.somaValorTotalFPagto = somaValorTotalFPagto;
	}

	public List<ECFVendasFB> getListaECFs() {
		return listaECFs;
	}

	public void setListaECFs(List<ECFVendasFB> listaECFs) {
		this.listaECFs = listaECFs;
	}
	
	public String getTituloPedidos() {
		return tituloPedidos;
	}

	public void setTituloPedidos(String tituloPedidos) {
		this.tituloPedidos = tituloPedidos;
	}
	
	public Integer getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(Integer numClientes) {
		this.numClientes = numClientes;
	}

	public Double getTicketMedioDia() {
		return ticketMedioDia;
	}

	public void setTicketMedioDia(Double ticketMedioDia) {
		this.ticketMedioDia = ticketMedioDia;
	}

	public Double getMediaDia() {
		return mediaDia;
	}

	public void setMediaDia(Double mediaDia) {
		this.mediaDia = mediaDia;
	}

	public VendedorFB getVendedorFilter() {
		return vendedorFilter;
	}

	public void setVendedorFilter(VendedorFB vendedorFilter) {
		this.vendedorFilter = vendedorFilter;
	}

	public TipoVendedorFB getTipoVendedorFilter() {
		return tipoVendedorFilter;
	}

	public void setTipoVendedorFilter(TipoVendedorFB tipoVendedorFilter) {
		this.tipoVendedorFilter = tipoVendedorFilter;
	}

	public List<SelectItem> getVendedorSelect() {
		if (this.vendedorSelect == null) {
			this.vendedorSelect = new ArrayList<SelectItem>();
			VendedorFBRN vendedorFBRN = new VendedorFBRN();
			this.vendedorSelect = vendedorFBRN.montaDadosSelect(new VendedorFBRN().listar(""), "");
		}

		return vendedorSelect;
	}

	public void setVendedorSelect(List<SelectItem> vendedorSelect) {
		this.vendedorSelect = vendedorSelect;
	}

	public List<SelectItem> getTipoVendedorSelect() {
		if (this.tipoVendedorSelect == null) {
			this.tipoVendedorSelect = new ArrayList<SelectItem>();
			TipoVendedorFBRN tipoVendedorFBRN = new TipoVendedorFBRN();
			this.tipoVendedorSelect = tipoVendedorFBRN.montaDadosSelect(new TipoVendedorFBRN().listar(""), "");
		}
		
		return tipoVendedorSelect;
	}

	public void setTipoVendedorSelect(List<SelectItem> tipoVendedorSelect) {
		this.tipoVendedorSelect = tipoVendedorSelect;
	}

	public void addHome() {
		MenuAcessoController.addHome(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	public void addRecentes() {
		MenuAcessoController.addRecentes(menu, ContextoUtil.getContextoBean().getUsuarioLogado());
	}

	public void addFavoritos() {
		MenuAcessoController.addFavoritos(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	public void clearSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("vendaFornBean");
		}
	}

}
