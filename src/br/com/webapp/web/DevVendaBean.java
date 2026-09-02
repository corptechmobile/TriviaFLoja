package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;
import br.com.webapp.model.fb.relatorio.devvenda.DevVendaDTO;
import br.com.webapp.model.fb.relatorio.devvenda.DevVendaDTORN;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.Funcoes;

@ManagedBean(name = "devVendaBean")
@SessionScoped
public class DevVendaBean implements Serializable, IMenuAcesso{
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.DEVVENDAFORM_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;

	private static final long serialVersionUID = -4728916230625173285L;
	
	private DevVendaDTO selecionada;
	private VendasProdutoDTO selecionadaProduto;
	private List<DevVendaDTO> lista;
	private List<DevVendaDTO> listaDevCliente;
	private List<DevVendaDTO> listaDevProduto;
	private Map<String, Double> totalUNListaProdutos;
	private Integer fornecedorFilter;
	private String boletimFilter;
	private String boletim;
	private String porFilter;
	private String porFilterLabel;
	private String porFilterColumn;
	private EmpresaFB empresaFilter;
	private Date dataFilter1;
	private Date dataFilter2;
	private Double valorTotal;
	private Double valorTotalDev;
	private List<SelectItem> empresasSelect;
	private ClienteFB clienteFilter;
	private ProdutoFB produtoFilter;
	private VendedorFB vendedorFilter;
	private ProdutoLinhaFB linhaProdutoFilter;
	private List<SelectItem> vendedorSelect;

	
	private boolean renderedTabDevProduto;
	private boolean renderedTabForn;
	private boolean renderedTabDevCliente;
	private boolean renderedTabFPagto;
	private int currentTab;
	private String tituloPedidos;
	
	private DiasUteisFB diasUteis;
	
	@PostConstruct
	public void init(){
		System.out.println("[DevVendaBean][init]");
		onStartDatas();
		
		currentTab = 0;
		porFilter = "linhaProduto";
		
		renderedTabForn = false;
		selecionada = null;
		selecionadaProduto = null;
		listaDevProduto = null;
		listaDevCliente = null;
		
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
		
		lista = null;
		listaDevCliente = null;
		listaDevProduto = null;
		
		valorTotalDev = 0.0;
		DevVendaDTORN devVendaDTORN = new DevVendaDTORN();
		
		String agruparPor = "";
		if(DevVendaDTO.AGRUPAR_BOLETIM.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_BOLETIM;
		}else if(DevVendaDTO.AGRUPAR_CLIENTE.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_CLIENTE;
		}else if(DevVendaDTO.AGRUPAR_LINHAPRODUTO.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_LINHAPRODUTO;
		}else if(DevVendaDTO.AGRUPAR_FORNECEDOR.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_FORNECEDOR;
		}else if(DevVendaDTO.AGRUPAR_VENDEDOR.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_VENDEDOR;
		}else if(DevVendaDTO.AGRUPAR_PRODUTO.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_PRODUTO;
		}else if(DevVendaDTO.AGRUPAR_EMPRESA.equals(porFilter)) {
			agruparPor = DevVendaDTO.AGRUPAR_EMPRESA;
		}
		
		lista = devVendaDTORN.listar(boletimFilter, empresaFilter, vendedorFilter, clienteFilter, fornecedorFilter, produtoFilter, linhaProdutoFilter, dataFilter1, dataFilter2, agruparPor);
				
		if(lista!=null) {
			
			for (DevVendaDTO rs : lista) {
				valorTotalDev += rs.getVlDevolvido();
			}
			
			for (DevVendaDTO rs : lista) {
				Double dou = 0.0;
				try {
					dou = (rs.getVlDevolvido()/valorTotalDev) * 100;
				} catch (Exception e) {
					dou = 0.0;
					e.printStackTrace();
				}
				rs.setPercValor(Funcoes.arrendondaValor(0, dou));
			}
		
		}
		
		startDiasUteis();
		
		currentTab = 0;
		selecionada = null;
		selecionadaProduto = null;
		listaDevProduto = null;
		renderedTabForn = true;
		renderedTabDevProduto = false;
		renderedTabDevCliente = false;
		
	}
	
	public void openDevCliente(){
		currentTab = currentTab + 1;
		renderedTabDevCliente = true;
		renderedTabDevProduto = false;
		listaDevProduto = null;
		
		valorTotalDev = 0.0;
		DevVendaDTORN devVendaDTORN = new DevVendaDTORN();
		ClienteFB cliente = new ClienteFBRN().carregar(selecionada.getClienteId());

		
		if(DevVendaDTO.AGRUPAR_CLIENTE.equals(porFilter)) {
			clienteFilter = new ClienteFBRN().carregar(selecionada.getClienteId());
		}else if(DevVendaDTO.AGRUPAR_LINHAPRODUTO.equals(porFilter)) {
			linhaProdutoFilter = new ProdutoLinhaFBRN().carregar(selecionada.getId());  
		}else if(DevVendaDTO.AGRUPAR_FORNECEDOR.equals(porFilter)) {
			fornecedorFilter = selecionada.getId();
		}else if(DevVendaDTO.AGRUPAR_VENDEDOR.equals(porFilter)) {
			vendedorFilter = new VendedorFBRN().carregar(selecionada.getId());
		}else if(DevVendaDTO.AGRUPAR_EMPRESA.equals(porFilter)) {
			empresaFilter = new EmpresaFBRN().carregar(selecionada.getId());
		}else if(DevVendaDTO.AGRUPAR_PRODUTO.equals(porFilter)) {
			produtoFilter = new ProdutoFBRN().carregar(selecionada.getId());
		}	
		
		listaDevCliente = devVendaDTORN.listar(boletimFilter, empresaFilter, vendedorFilter, clienteFilter, fornecedorFilter, produtoFilter, linhaProdutoFilter, dataFilter1, dataFilter2, DevVendaDTO.AGRUPAR_BOLETIM);
				
		if(listaDevCliente!=null) {
			
			for (DevVendaDTO rs : listaDevCliente) {
				valorTotalDev += rs.getVlDevolvido();
			}
			
			for (DevVendaDTO rs : listaDevCliente) {
				Double dou = 0.0;
				try {
					dou = (rs.getVlDevolvido()/valorTotalDev) * 100;
				} catch (Exception e) {
					dou = 0.0;
					e.printStackTrace();
				}
				rs.setPercValor(Funcoes.arrendondaValor(0, dou));
			}
		
		}		 
	}
	
	public void openDevProduto() {
		
		currentTab = currentTab + 1;
		renderedTabDevCliente = false;
		renderedTabDevProduto = true;
		
		valorTotalDev = 0.0;
		DevVendaDTORN devVendaDTORN = new DevVendaDTORN();
		//ClienteFB cliente = new ClienteFBRN().carregar(selecionada.getClienteId());
		String boletim = selecionada.getId()+"";
		listaDevProduto = devVendaDTORN.listar(boletim, null, null, null, null, null, null, dataFilter1, dataFilter2, DevVendaDTO.AGRUPAR_BOLETIM_PRODUTO);
				
		if(listaDevProduto!=null) {
			
			for (DevVendaDTO rs : listaDevProduto) {
				valorTotalDev += rs.getVlDevolvido();
			}
			
			for (DevVendaDTO rs : listaDevProduto) {
				Double dou = 0.0;
				try {
					dou = (rs.getVlDevolvido()/valorTotalDev) * 100;
				} catch (Exception e) {
					dou = 0.0;
					e.printStackTrace();
				}
				rs.setPercValor(Funcoes.arrendondaValor(0, dou));
			}
		
		}		 
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
	    		renderedTabDevProduto = false;
	    		renderedTabDevCliente = false;
	        }else if(event.getTab().getId().equals("tabDtVendaProdBeanPedVenda")) {
	        	if(renderedTabDevCliente || renderedTabDevProduto) {
	        		currentTab = 1;
	        	}else {
	        		currentTab = 0;
	        	}
	        	
	        }
    	} catch (Exception e) {
			//e.printStackTrace();
		}
    }
	
	public void limpar() {
		
		onStartDatas();
		
		currentTab = 0;
		empresaFilter = null;
		clienteFilter = null;
		vendedorFilter = null;
		produtoFilter = null;
		boletimFilter = null;
		renderedTabForn = false;
		renderedTabDevProduto = false;
		renderedTabDevCliente = false;
		
		lista = null;
		selecionada = null;
		listaDevCliente = null;
		listaDevProduto = null;
		
		valorTotalDev = 0d;
		
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

	public DevVendaDTO getSelecionada() {
		return selecionada;
	}


	public void setSelecionada(DevVendaDTO selecionada) {
		this.selecionada = selecionada;
	}

	public VendasProdutoDTO getSelecionadaProduto() {
		return selecionadaProduto;
	}

	public void setSelecionadaProduto(VendasProdutoDTO selecionadaProduto) {
		this.selecionadaProduto = selecionadaProduto;
	}

	public List<DevVendaDTO> getLista() {
		return lista;
	}

	public void setLista(List<DevVendaDTO> lista) {
		this.lista = lista;
	}

	public Map<String, Double> getTotalUNListaProdutos() {
		return totalUNListaProdutos;
	}

	public void setTotalUNListaProdutos(Map<String, Double> totalUNListaProdutos) {
		this.totalUNListaProdutos = totalUNListaProdutos;
	}

	public String getBoletimFilter() {
		return boletimFilter;
	}

	public void setBoletimFilter(String boletimFilter) {
		this.boletimFilter = boletimFilter;
	}

	public String getBoletim() {
		return boletim;
	}

	public void setBoletim(String boletim) {
		this.boletim = boletim;
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

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}


	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
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

	public boolean isRenderedTabForn() {
		return renderedTabForn;
	}

	public void setRenderedTabForn(boolean renderedTabForn) {
		this.renderedTabForn = renderedTabForn;
	}
	
	public boolean isRenderedTabFPagto() {
		return renderedTabFPagto;
	}

	public void setRenderedTabFPagto(boolean renderedTabFPagto) {
		this.renderedTabFPagto = renderedTabFPagto;
	}

	public int getCurrentTab() {
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

	public String getTituloPedidos() {
		return tituloPedidos;
	}

	public void setTituloPedidos(String tituloPedidos) {
		this.tituloPedidos = tituloPedidos;
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

	public ClienteFB getClienteFilter() {
		return clienteFilter;
	}

	public void setClienteFilter(ClienteFB clienteFilter) {
		this.clienteFilter = clienteFilter;
	}
	
	public List<VendedorFB> completeVendedor(String query) {
		List<VendedorFB> filteredVendedor = new ArrayList<VendedorFB>();
    	filteredVendedor = new VendedorFBRN().listar(query);
        return filteredVendedor;
    }
	
	public void closeFilterVendedor(){
		//this.filterCliente = false;
	}

	public List<ClienteFB> completeCliente(String query) {
		List<ClienteFB> filteredCliente = new ArrayList<ClienteFB>();
        if (query != null && !query.equals("")) {
        	filteredCliente = new ClienteFBRN().listar(query);
		}  
        return filteredCliente;
    }
	
	public void closeFilterCliente(){
		//this.filterCliente = false;
	}

	public ProdutoFB getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(ProdutoFB produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public List<ProdutoFB> completeProduto(String query) {
		List<ProdutoFB> filteredProduto = new ArrayList<ProdutoFB>();
        if (query != null && !query.equals("")) {
        	filteredProduto = new ProdutoFBRN().listar(query);
		}  
        return filteredProduto;
    }
	
	public void closeFilterProduto(){
		//this.filterCliente = false;
	}

	public List<DevVendaDTO> getListaDevCliente() {
		return listaDevCliente;
	}

	public void setListaDevCliente(List<DevVendaDTO> listaDevCliente) {
		this.listaDevCliente = listaDevCliente;
	}

	public List<DevVendaDTO> getListaDevProduto() {
		return listaDevProduto;
	}

	public void setListaDevProduto(List<DevVendaDTO> listaDevProduto) {
		this.listaDevProduto = listaDevProduto;
	}

	public boolean isRenderedTabDevProduto() {
		return renderedTabDevProduto;
	}

	public void setRenderedTabDevProduto(boolean renderedTabDevProduto) {
		this.renderedTabDevProduto = renderedTabDevProduto;
	}

	public boolean isRenderedTabDevCliente() {
		return renderedTabDevCliente;
	}

	public void setRenderedTabDevCliente(boolean renderedTabDevCliente) {
		this.renderedTabDevCliente = renderedTabDevCliente;
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
		    session.removeAttribute("devVendaBean");
		}
	}

}
