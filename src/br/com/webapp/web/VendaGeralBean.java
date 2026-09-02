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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import com.google.gson.Gson;


import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFB;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFBRN;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFBRN;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFBRN;
import br.com.webapp.model.fb.parametro.ParametroFB;
import br.com.webapp.model.fb.parametro.ParametroFBRN;
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
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTORN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "vendaGeralBean")
@SessionScoped
public class VendaGeralBean implements Serializable, IMenuAcesso{
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.VENDAGERAL_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;

	private static final long serialVersionUID = -4728916230625173285L;
	
	private VendaFornDTO selecionada;
	private VendasProdutoDTO selecionadaProduto;
	private VendaFornPedVenda selecionadaPedVenda;
	private VendaFornFPagtoDTO selecionadaFPagto;
	private VendaFornFPagtoDTO selecionadaFPagtoCartoes;
	private EventoFinanceiroFB selecionadaEventoFinanceiroFB;
	private List<VendaFornDTO> lista;
	private List<VendasProdutoDTO> listaProdutos;
	private List<EventoFinanceiroFB> listaFinanceiro;
	private List<EventoFinanceiroFB> listaFinanceiroDetalhe;
	private Map<String, Double> totalUNListaProdutos;
	private List<VendaFornPedVenda> listaPedidos;
	private List<ECFVendasFB> listaECFs;
	private List<VendaFornDTO> listaMesChart;
	private VendaFornResumo resumo;
	private Double faturamentoDia;
	private Double devolucaoDia;
	private Double mediaDia;	
	private Double ticketMedioDia;
	private Double margem;
	private Double markup;	
	private Integer numClientesDoDia;
	private Integer numClientes;
	private Integer eventoFinanceiroId;
	private String grupoFinanceiroId;
	private List<VendaFornFPagtoDTO> formas;
	private String autoServicoPedAmbos;
	private String porFilter;
	private String vendasPorFilter;
	private String segmentoFilter;
	private String porFilterLabel;
	private String porFilterColumn;
	private String tipoPosicao;
	private EmpresaFB empresaFilter;
	private FornecedorFB fornecedorFilter;
	private Date dataFilter1;
	private Date dataFilter2;
	private Double valorTotal;
	private Double valorTotalDev;
	private Double valorTotalLiq;
	private Double valorTotalProd;
	private Double valorTotalProdDev;
	private Double valorTotalPedVenda;
	private Double valorTotalPago;
	private Double valorTotalAVencer;
	private Double valorTotalVencido;
	private Double valorTotalFinanceiro;
	private Double valorTotalDetPago;
	private Double valorTotalDetAVencer;
	private Double valorTotalDetVencido;
	private Double valorTotalDetFinanceiro;
	private Double qtdTotalPedVenda;
	private Double somaValorTotal;
	private Double mediaDescontos;
	private Double descontoDia;
	private Double descontoMes;
	private Double somaValorTotalFPagto;
	private List<SelectItem> empresasSelect;
	private List<SelectItem> fornecedorSelect;
	private GrupoFinanceiroFB grupoFinanceiroFilter;
	private List<SelectItem> gruposFinanceiroSelect;
	private VendedorFB vendedorFilter;
	private List<SelectItem> vendedorSelect;
	
	private boolean renderedTabProduto;
	private boolean renderedTabForn;
	private boolean renderedTabPedido;
	private boolean renderedTabFPagto;
	private boolean renderedTabDetVendas;
	private boolean renderedTabFinanceiro;
	private int currentTab;
	private String tituloPedidos;
	
	private DiasUteisFB diasUteis;
	
	@PostConstruct
	public void init(){
		System.out.println("[VendaGeralBean][init]");
		onStartDatas();
		setAutoServicoPedAmbos("ambos");
		
		currentTab = 0;
		porFilter = "linhaProduto";
		tipoPosicao = "noMes";
		
		ParametroFB parametroSegmento = new ParametroFBRN().carregar("TIPOSEGGRUPOEMP");
		if(parametroSegmento!=null && "1".equals(parametroSegmento.getValor())) {
			vendasPorFilter = "notafiscal";
		}else {
			vendasPorFilter = "pedido";
		}
		 
		renderedTabForn = false;
		selecionada = null;
		selecionadaProduto = null;
		listaProdutos = null;
		listaPedidos = null;
		
		buscar();
		
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
		
		
		int dias = Funcoes.CalcularAtraso(dataFilter1, dataFilter2);
		if(dias<=30) {
			lista = null;
			formas = null;
			listaFinanceiro = null;
			listaFinanceiroDetalhe = null;
			
			valorTotal = 0.0;
			valorTotalDev = 0.0;
			valorTotalLiq = 0.0;
			valorTotalPedVenda = 0.0;
			qtdTotalPedVenda = 0.0;
			somaValorTotalFPagto=0.0;

			startDiasUteis();
			
			VendaFornResumoRN vendaFornResumoRN = new VendaFornResumoRN();
			resumo = vendaFornResumoRN.carregarMes(empresaFilter, vendedorFilter, null, fornecedorFilter, dataFilter2, vendasPorFilter, segmentoFilter);
			numClientes = 0;
			if(resumo != null) {
				numClientes = resumo.getNumClientes();
			}
			
			numClientesDoDia = 0;
			faturamentoDia = 0.0;
			devolucaoDia = 0.0;
			VendaFornResumo vendaFornResumo = vendaFornResumoRN.carregar(empresaFilter, vendedorFilter, null, fornecedorFilter, dataFilter2, dataFilter2, vendasPorFilter, segmentoFilter);
			if(vendaFornResumo != null) {
				faturamentoDia = vendaFornResumo.getFaturamento();
				devolucaoDia = vendaFornResumo.getDevolucao();
				numClientesDoDia = vendaFornResumo.getNumClientes();
				margem = vendaFornResumo.getMargem();
				markup = vendaFornResumo.getMarkup();	
				descontoDia = vendaFornResumo.getDesconto();
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
			
			listaFinanceiro = new EventoFinanceiroFBRN().listar(empresaFilter, vendedorFilter, null, dataFilter1, dataFilter2);
			if(listaFinanceiro!=null) {
				
				valorTotalPago = 0d;
				valorTotalAVencer = 0d;
				valorTotalVencido = 0d;
				valorTotalFinanceiro = 0d;
				for (EventoFinanceiroFB ef : listaFinanceiro) {
					valorTotalPago += ef.getValorPago();
					valorTotalAVencer += ef.getValorAvencer();
					valorTotalVencido += ef.getValorVencido();
				}	
				
				valorTotalFinanceiro = valorTotalPago + valorTotalAVencer + valorTotalVencido;
			
				for (EventoFinanceiroFB ef : listaFinanceiro) {
					Double perc = (double) Math.round((ef.getValorTotal()*1.0/valorTotalFinanceiro*1.0)*100);
					ef.setPercentual(perc);
					
					
				}
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
			renderedTabFinanceiro = false;
			
		}else {
			
			lista = null;
			formas = null;
			listaFinanceiro = null;
			listaFinanceiroDetalhe = null;
			
			valorTotal = 0.0;
			valorTotalDev = 0.0;
			valorTotalLiq = 0.0;
			valorTotalPedVenda = 0.0;
			qtdTotalPedVenda = 0.0;
			somaValorTotalFPagto=0.0;
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", UtilMessage.mensagem("msg.painelresultado.pediodoinvalido")));			
		}
		
		
	}
	
	public void buscarPor() {
		
		VendaFornDTORN vendaFornDTORN = new VendaFornDTORN();
		if (!porFilter.equals("fpagto")) {
			if("noDia".equals(tipoPosicao)) {
				lista = vendaFornDTORN.listarPorSegmento(empresaFilter, vendedorFilter, null, fornecedorFilter, porFilter, dataFilter2, dataFilter2, segmentoFilter, vendasPorFilter); 
			}else {
				lista = vendaFornDTORN.listarPorSegmento(empresaFilter, vendedorFilter, null, fornecedorFilter, porFilter, dataFilter1, dataFilter2, segmentoFilter, vendasPorFilter);
			}
			
		} else if (porFilter.equals("fpagto")){
			VendaFornFPagtoDTORN vendaFornFPagtoDTORN = new VendaFornFPagtoDTORN();
			if("noDia".equals(tipoPosicao)) {
				formas = vendaFornFPagtoDTORN.listar(empresaFilter, vendedorFilter, null, dataFilter2, dataFilter2); 
			}else {
				formas = vendaFornFPagtoDTORN.listar(empresaFilter, vendedorFilter, null, dataFilter1, dataFilter2);
			}

			if(formas!=null) {
				for(VendaFornFPagtoDTO rs : formas) {
					somaValorTotalFPagto += rs.getValor();
				}
			}
		}
		
		if(lista!=null) {
			valorTotal = 0d;
			valorTotalDev = 0d;
		
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
		
		
	}
	
	public void buscarFinanceiro() {
		
		renderedTabFinanceiro = false;
		listaFinanceiro = null; 
		listaFinanceiroDetalhe = null;
		
		listaFinanceiro = new EventoFinanceiroFBRN().listar(empresaFilter, vendedorFilter, grupoFinanceiroFilter, dataFilter1, dataFilter2);
		if(listaFinanceiro!=null) {
			
			valorTotalPago = 0d;
			valorTotalAVencer = 0d;
			valorTotalVencido = 0d;
			valorTotalFinanceiro = 0d;
			for (EventoFinanceiroFB ef : listaFinanceiro) {
				valorTotalPago += ef.getValorPago();
				valorTotalAVencer += ef.getValorAvencer();
				valorTotalVencido += ef.getValorVencido();
			}	
			
			valorTotalFinanceiro = valorTotalPago + valorTotalAVencer + valorTotalVencido;
		
			for (EventoFinanceiroFB ef : listaFinanceiro) {
				Double perc = (double) Math.round((ef.getValorTotal()*1.0/valorTotalFinanceiro*1.0)*100);
				ef.setPercentual(perc);
			}
		}
		
		
	}
	
	public void buscarNFCes() {
		listaECFs = new ECFVendasFBRN().listarACancelar(empresaFilter.getId(), vendedorFilter.getId(), null, dataFilter1, dataFilter2);
	}
	
	public void buscarDashBoardMes() {

		if(empresaFilter!=null){
			
			int dias = Funcoes.CalcularAtraso(dataFilter1, dataFilter2);
			if(dias<=30) {
	
				listaMesChart = new VendaFornDTORN().dashBoardPorMes(empresaFilter.getId(), vendedorFilter.getId(), null, dataFilter1, dataFilter2, vendasPorFilter, segmentoFilter);
					if(listaMesChart!=null){
						Gson gson = new Gson();
						RequestContext.getCurrentInstance().addCallbackParam("collumnLineDBEmp", gson.toJson("collumnLineDBEmp"));
						RequestContext.getCurrentInstance().addCallbackParam("listaEmpDiaChart", gson.toJson(listaMesChart));
					}	
				}
		}
	}	
	
	public void buscarDashBoardComAno() {

		if(empresaFilter!=null){
			int dias = Funcoes.CalcularAtraso(dataFilter1, dataFilter2);
			if(dias<=30) {
			
				List<VendaFornDTO> listaCompChart = new VendaFornDTORN().dashBoardPorMesAno(empresaFilter.getId(), vendedorFilter.getId(), null, dataFilter1, dataFilter2, vendasPorFilter, segmentoFilter);
				if(listaCompChart!=null){
					Gson gson = new Gson();
					RequestContext.getCurrentInstance().addCallbackParam("collumnBarDB", gson.toJson("collumnBarDB"));
					RequestContext.getCurrentInstance().addCallbackParam("listafatChart", gson.toJson(listaCompChart));
				}
			}	
		}	
	}		
	
	public void openProduto() {
		
		currentTab = currentTab + 1;
		
		renderedTabProduto = true;
		renderedTabPedido = false;
		renderedTabFPagto = false;
		renderedTabDetVendas = true;

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
	
	public void openNotas() {
		
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
	
	public void openDetalhe() {
		currentTab = currentTab + 1;
		
		renderedTabProduto = false;
		renderedTabPedido = false;
		renderedTabFPagto = false;
		renderedTabDetVendas = true;

		
		selecionadaFPagto = null;
		selecionadaFPagtoCartoes = null;
		listaProdutos = null;
		listaECFs = null;
		formas = null; 
		
		buscarPor();
	}
	
	
	public void openFinanceiro(){
		
		currentTab = currentTab + 1;
		
		renderedTabFinanceiro = true;
		
		listaFinanceiroDetalhe = new EventoFinanceiroFBRN().listarDetalhe(empresaFilter, vendedorFilter, dataFilter1, dataFilter2, grupoFinanceiroId, eventoFinanceiroId);
		if(listaFinanceiroDetalhe!=null) {
			
			valorTotalDetPago = 0d;
			valorTotalDetAVencer = 0d;
			valorTotalDetVencido = 0d;
			valorTotalDetFinanceiro = 0d;
			for (EventoFinanceiroFB ef : listaFinanceiroDetalhe) {
				if(ef.getValorPago()!=null) {
					valorTotalDetPago += ef.getValorPago();
				}
				
				if(ef.getValorAvencer()!=null) {
					valorTotalDetAVencer += ef.getValorAvencer();
				}
				
				if(ef.getValorVencido()!=null) {
					valorTotalDetVencido += ef.getValorVencido();
				}	
			}	
			
			valorTotalDetFinanceiro = valorTotalDetPago + valorTotalDetAVencer + valorTotalDetVencido;
			
			for (EventoFinanceiroFB ef : listaFinanceiroDetalhe) {
				Double perc = (double) Math.round((ef.getValorTotal()*1.0/valorTotalDetFinanceiro*1.0)*100);
				ef.setPercentual(perc);
			}			
		}

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
	        if(event.getTab().getId().equals("tabDtVendaProdBean") || event.getTab().getId().equals("tabDtVendaFPagtoBean") || event.getTab().getId().equals("tabDetalhe")) {
	        	currentTab = 0;
	    		renderedTabProduto = false;
	    		renderedTabFPagto = false;
	    		renderedTabPedido = false;
	    		renderedTabDetVendas = false;
	    		listaPedidos = null;
	        }else if(event.getTab().getId().equals("tabDtVendaProdBeanPedVenda")) {
	        	if(renderedTabFPagto || renderedTabProduto || renderedTabDetVendas) {
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

	public EventoFinanceiroFB getSelecionadaEventoFinanceiroFB() {
		return selecionadaEventoFinanceiroFB;
	}

	public void setSelecionadaEventoFinanceiroFB(EventoFinanceiroFB selecionadaEventoFinanceiroFB) {
		this.selecionadaEventoFinanceiroFB = selecionadaEventoFinanceiroFB;
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
			if("noDia".equals(tipoPosicao)) {
				listaProdutos = vendasProdutoDTORN.listarPorSegmento(empresaFilter, vendedorFilter, fornecedorFilter, porFilter, dataFilter2, dataFilter2, segmentoFilter, vendasPorFilter, selecionada.getId()); 
			}else {
				listaProdutos = vendasProdutoDTORN.listarPorSegmento(empresaFilter, vendedorFilter, fornecedorFilter, porFilter, dataFilter1, dataFilter2, segmentoFilter, vendasPorFilter, selecionada.getId());
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
	
	public List<EventoFinanceiroFB> getListaFinanceiro() {
		return listaFinanceiro;
	}

	public void setListaFinanceiro(List<EventoFinanceiroFB> listaFinanceiro) {
		this.listaFinanceiro = listaFinanceiro;
	}

	public List<EventoFinanceiroFB> getListaFinanceiroDetalhe() {
		return listaFinanceiroDetalhe;
	}

	public void setListaFinanceiroDetalhe(List<EventoFinanceiroFB> listaFinanceiroDetalhe) {
		this.listaFinanceiroDetalhe = listaFinanceiroDetalhe;
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
			if("pedido".equals(vendasPorFilter)) {
				if("noDia".equals(tipoPosicao)) {
					listaPedidos = vendaFornPedVendaRN.listarPedVenda(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter2, dataFilter2, porFilter); 
				}else {
					listaPedidos = vendaFornPedVendaRN.listarPedVenda(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
				}
			}else {
				if("noDia".equals(tipoPosicao)) {
					listaPedidos = vendaFornPedVendaRN.listarNotas(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter2, dataFilter2, porFilter, segmentoFilter); 
				}else {
					listaPedidos = vendaFornPedVendaRN.listarNotas(selecionada, selecionadaProduto, selecionadaFPagto, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter, segmentoFilter);
				}
				
			}	
			for(VendaFornPedVenda rs : listaPedidos) {
				valorTotalPedVenda += rs.getValor();
				qtdTotalPedVenda += rs.getQtde();
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

	public Integer getEventoFinanceiroId() {
		return eventoFinanceiroId;
	}

	public void setEventoFinanceiroId(Integer eventoFinanceiroId) {
		this.eventoFinanceiroId = eventoFinanceiroId;
	}

	public String getGrupoFinanceiroId() {
		return grupoFinanceiroId;
	}

	public void setGrupoFinanceiroId(String grupoFinanceiroId) {
		this.grupoFinanceiroId = grupoFinanceiroId;
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

	public String getPorFilterLabel() {
		if (porFilter.equals("fornecedor")) {
			porFilterLabel = "Fornecedores";
		}else if (porFilter.equals("vendedor")) {
			porFilterLabel = "Vendedores";
		}else if (porFilter.equals("linhaProduto")) {
			porFilterLabel = "Linhas de Produtos";
		}else if (porFilter.equals("fpagto")) {
			porFilterLabel = "F. Pagto";
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
		}else {
			porFilterColumn = "Linha de Produto";
		}
		return porFilterColumn;
	}

	public void setPorFilterColumn(String porFilterColumn) {
		this.porFilterColumn = porFilterColumn;
	}

	public String getTipoPosicao() {
		return tipoPosicao;
	}

	public void setTipoPosicao(String tipoPosicao) {
		this.tipoPosicao = tipoPosicao;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public FornecedorFB getFornecedorFilter() {
		return fornecedorFilter;
	}

	public void setFornecedorFilter(FornecedorFB fornecedorFilter) {
		this.fornecedorFilter = fornecedorFilter;
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
	
	public Double getValorTotalPago() {
		return valorTotalPago;
	}

	public void setValorTotalPago(Double valorTotalPago) {
		this.valorTotalPago = valorTotalPago;
	}

	public Double getValorTotalAVencer() {
		return valorTotalAVencer;
	}

	public void setValorTotalAVencer(Double valorTotalAVencer) {
		this.valorTotalAVencer = valorTotalAVencer;
	}

	public Double getValorTotalVencido() {
		return valorTotalVencido;
	}

	public void setValorTotalVencido(Double valorTotalVencido) {
		this.valorTotalVencido = valorTotalVencido;
	}

	public Double getValorTotalFinanceiro() {
		return valorTotalFinanceiro;
	}

	public void setValorTotalFinanceiro(Double valorTotalFinanceiro) {
		this.valorTotalFinanceiro = valorTotalFinanceiro;
	}

	public Double getValorTotalDetPago() {
		return valorTotalDetPago;
	}

	public void setValorTotalDetPago(Double valorTotalDetPago) {
		this.valorTotalDetPago = valorTotalDetPago;
	}

	public Double getValorTotalDetAVencer() {
		return valorTotalDetAVencer;
	}

	public void setValorTotalDetAVencer(Double valorTotalDetAVencer) {
		this.valorTotalDetAVencer = valorTotalDetAVencer;
	}

	public Double getValorTotalDetVencido() {
		return valorTotalDetVencido;
	}

	public void setValorTotalDetVencido(Double valorTotalDetVencido) {
		this.valorTotalDetVencido = valorTotalDetVencido;
	}

	public Double getValorTotalDetFinanceiro() {
		return valorTotalDetFinanceiro;
	}

	public void setValorTotalDetFinanceiro(Double valorTotalDetFinanceiro) {
		this.valorTotalDetFinanceiro = valorTotalDetFinanceiro;
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

	public Double getDescontoDia() {
		return descontoDia;
	}

	public void setDescontoDia(Double descontoDia) {
		this.descontoDia = descontoDia;
	}

	public Double getDescontoMes() {
		return descontoMes;
	}

	public void setDescontoMes(Double descontoMes) {
		this.descontoMes = descontoMes;
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

	public List<SelectItem> getFornecedorSelect() {
		if (this.fornecedorSelect == null) {
			this.fornecedorSelect = new ArrayList<SelectItem>();
			FornecedorFBRN fornecedorFBRN = new FornecedorFBRN();
			this.fornecedorSelect = fornecedorFBRN.montaDadosSelect(new FornecedorFBRN().listar(""), "");
		}

		return fornecedorSelect;
	}

	public void setFornecedorSelect(List<SelectItem> fornecedorSelect) {
		this.fornecedorSelect = fornecedorSelect;
	}

	public GrupoFinanceiroFB getGrupoFinanceiroFilter() {
		return grupoFinanceiroFilter;
	}

	public void setGrupoFinanceiroFilter(GrupoFinanceiroFB grupoFinanceiroFilter) {
		this.grupoFinanceiroFilter = grupoFinanceiroFilter;
	}

	public List<SelectItem> getGruposFinanceiroSelect() {
		if (this.gruposFinanceiroSelect == null) {
			
			this.gruposFinanceiroSelect = new ArrayList<SelectItem>();
			
			EmpresaFBRN empresaRN = new EmpresaFBRN();
			GrupoFinanceiroFBRN grupoFinanceiroFBRN = new GrupoFinanceiroFBRN();
			this.empresasSelect = empresaRN.montaDadosSelect(this.getContextoBean().getUsuarioLogado().getEmpresas(), "");
			this.gruposFinanceiroSelect = grupoFinanceiroFBRN.montaDadosSelect(grupoFinanceiroFBRN.listar(), "");
		}
		return gruposFinanceiroSelect;
	}

	public void setGruposFinanceiroSelect(List<SelectItem> gruposFinanceiroSelect) {
		this.gruposFinanceiroSelect = gruposFinanceiroSelect;
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

	public boolean isRenderedTabFinanceiro() {
		return renderedTabFinanceiro;
	}

	public void setRenderedTabFinanceiro(boolean renderedTabFinanceiro) {
		this.renderedTabFinanceiro = renderedTabFinanceiro;
	}

	public boolean isRenderedTabDetVendas() {
		return renderedTabDetVendas;
	}

	public void setRenderedTabDetVendas(boolean renderedTabDetVendas) {
		this.renderedTabDetVendas = renderedTabDetVendas;
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
	
	public List<VendaFornDTO> getListaMesChart() {
		return listaMesChart;
	}

	public void setListaMesChart(List<VendaFornDTO> listaMesChart) {
		this.listaMesChart = listaMesChart;
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

	public Double getMargem() {
		return margem;
	}

	public void setMargem(Double margem) {
		this.margem = margem;
	}

	public Double getMarkup() {
		return markup;
	}

	public void setMarkup(Double markup) {
		this.markup = markup;
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
