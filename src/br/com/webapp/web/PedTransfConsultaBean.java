package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTO;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTORN;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFBRN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "pedTransfConsultaBean")
@SessionScoped
public class PedTransfConsultaBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = -5501425284814883822L;
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.PEDTRANSF_CONSULTA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private PedVendaFBDTO selecionada;
	private Integer selecionadaId;
	private List<PedVendaFBDTO> lista;
	private boolean editando = false;
	
	//Filtros
	private String numPedidoFilter;
	private String tipoDataFilter;
	private Date dataFilter1;
	private Date dataFilter2;
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	private ClienteFB clienteFilter;
	private PedVendaStatusFB statusFilter;
	private Boolean carteiraFilter = true;
	private Boolean desconsPeriodoFilter = false;
	
	private Double valorTotal;
	
	private List<SelectItem> empresasSelect;
	private List<SelectItem> statusSelect;
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		onStartDatas();
		setValorTotal(0.0);
		
		if(contextoBean.getUsuarioLogado().getEmpresas().size()>=1) {
			empresaFilter = contextoBean.getUsuarioLogado().getEmpresas().get(0);
		}
		
		if(contextoBean.getUsuarioLogado().getVendedor()!=null) {
			vendedorFilter = contextoBean.getUsuarioLogado().getVendedor();
		}
		
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

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
	public PedVendaFBDTO getSelecionada() {
		if (this.selecionada == null) {
			PedVendaFBDTORN pedVendaFBDTORN = new PedVendaFBDTORN();
			selecionada = pedVendaFBDTORN.carregar(selecionadaId);
			
		}
		return selecionada;
	}

	public void setSelecionada(PedVendaFBDTO selecionada) {
		this.selecionada = selecionada;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public List<PedVendaFBDTO> getLista() {
		if (lista == null) {
			if (desconsPeriodoFilter == true) {
				tipoDataFilter = null;
				dataFilter1 = null;
				dataFilter2 = null; 
			}
			
			lista = new PedVendaFBDTORN().listar(numPedidoFilter, tipoDataFilter, dataFilter1, dataFilter2, empresaFilter, vendedorFilter, clienteFilter, statusFilter, carteiraFilter, Funcoes.IS_TRANSFERENCIA, this.getContextoBean().getUsuarioLogado());
			valorTotal = 0.0;
			for (PedVendaFBDTO rs : lista) {
				valorTotal += rs.getValor();
			}
		}
		return lista;
	}

	public void setLista(List<PedVendaFBDTO> lista) {
		this.lista = lista;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public String getNumPedidoFilter() {
		return numPedidoFilter;
	}

	public void setNumPedidoFilter(String numPedidoFilter) {
		this.numPedidoFilter = numPedidoFilter;
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

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public VendedorFB getVendedorFilter() {
		return vendedorFilter;
	}

	public void setVendedorFilter(VendedorFB vendedorFilter) {
		this.vendedorFilter = vendedorFilter;
	}
	
	public List<VendedorFB> completeVendedor(String query) {
		List<VendedorFB> filteredVendedor = new ArrayList<VendedorFB>();
    	filteredVendedor = new VendedorFBRN().listar(query);
        return filteredVendedor;
    }
	
	public void closeFilterVendedor(){
		//this.filterCliente = false;
	}

	public ClienteFB getClienteFilter() {
		return clienteFilter;
	}

	public void setClienteFilter(ClienteFB clienteFilter) {
		this.clienteFilter = clienteFilter;
	}
	
	public List<ClienteFB> completeCliente(String query) {
		List<ClienteFB> filteredCliente = new ArrayList<ClienteFB>();
        if (query != null && !query.equals("")) {
        	filteredCliente = new ClienteFBRN().listarClienteTransferencia(query, Funcoes.IS_TRANSFERENCIA);
		}  
        return filteredCliente;
    }
	
	public void closeFilterCliente(){
		//this.filterCliente = false;
	}

	public PedVendaStatusFB getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(PedVendaStatusFB statusFilter) {
		this.statusFilter = statusFilter;
	}
	
	public Boolean getCarteiraFilter() {
		return carteiraFilter;
	}

	public void setCarteiraFilter(Boolean carteiraFilter) {
		this.carteiraFilter = carteiraFilter;
	}

	public Boolean getDesconsPeriodoFilter() {
		return desconsPeriodoFilter;
	}

	public void setDesconsPeriodoFilter(Boolean desconsPeriodoFilter) {
		this.desconsPeriodoFilter = desconsPeriodoFilter;
		if (this.desconsPeriodoFilter == false) {
			onStartDatas();
		}
	}
	
//	public void editar() {
//		editando = true;
//		empresaFilter = new EmpresaFBRN().carregar(selecionada.getEmpresaId());
//		vendedorFilter = new VendedorFBRN().carregar(selecionada.getVendedorId());
//		clienteFilter = new ClienteFBRN().carregar(selecionada.getClienteId());
//		statusFilter = new PedVendaStatusFBRN().carregar(selecionada.getPedVendaStatusId());
//	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<SelectItem> getEmpresasSelect() {
		if (this.empresasSelect == null) {
			
			this.empresasSelect = new ArrayList<SelectItem>();
			
			EmpresaFBRN empresaRN = new EmpresaFBRN();
			this.empresasSelect = empresaRN.montaDadosSelect(this.getContextoBean().getUsuarioLogado().getEmpresas(), "");
			
		}
		return empresasSelect;
	}

	public List<SelectItem> getStatusSelect() {
		if (this.statusSelect == null) {
			this.statusSelect = new ArrayList<SelectItem>();
			PedVendaStatusFBRN statusRN = new PedVendaStatusFBRN();
			this.statusSelect = statusRN.montaDadosSelect(statusRN.listar(), "");
		}
		return statusSelect;
	}
	
	public void buscar() {
		lista = null;
	}
	
	public void pedVendaExcluido(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.pedvenda")));
		buscar();
	}
	
	public void limpar() {
		
		empresaFilter = null;
		if(contextoBean.getUsuarioLogado().getEmpresas().size()>=1) {
			empresaFilter = contextoBean.getUsuarioLogado().getEmpresas().get(0);
		}
		
		vendedorFilter = null;
		if(contextoBean.getUsuarioLogado().getVendedor()!=null) {
			vendedorFilter = contextoBean.getUsuarioLogado().getVendedor();
		}
		
		numPedidoFilter = null;
		clienteFilter = null;
		statusFilter = null;
		lista = null;
		carteiraFilter = true;
		dataFilter1 = null;
		dataFilter2 = null;
		onStartDatas();
	}
	
	public void onStartDatas(){
		
		if(dataFilter1==null || dataFilter2==null){
			Calendar caIni = Calendar.getInstance();	
			caIni.set(Calendar.DATE, 1);
			caIni.set(Calendar.HOUR, 0);
			caIni.set(Calendar.MINUTE, 0);
			caIni.set(Calendar.SECOND, 0);
			caIni.set(Calendar.AM_PM, Calendar.AM);
			
			this.dataFilter1 = caIni.getTime();
			
			Calendar caFim = Calendar.getInstance();
			caFim.set(Calendar.HOUR, 11);
			caFim.set(Calendar.MINUTE, 59);
			caFim.set(Calendar.SECOND, 59);
			caFim.set(Calendar.AM_PM, Calendar.PM);
			
			this.dataFilter2 = caFim.getTime();
		}
	}
	
	public String getTipoDataFilter() {
		return tipoDataFilter;
	}

	public void setTipoDataFilter(String tipoDataFilter) {
		this.tipoDataFilter = tipoDataFilter;
	}
	
	@Override
	public void addHome() {
		MenuAcessoController.addHome(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());		
	}

	@Override
	public void addRecentes() {
		MenuAcessoController.addRecentes(menu, ContextoUtil.getContextoBean().getUsuarioLogado());
	}

	@Override
	public void addFavoritos() {
		MenuAcessoController.addFavoritos(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	@Override
	public void clearSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("pedTransfConsultaBean");
		}
	}

}
