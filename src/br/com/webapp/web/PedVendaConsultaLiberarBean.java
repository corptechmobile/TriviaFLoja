package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTO;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTORN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name = "pedVendaConsultaLiberarBean")
@SessionScoped
public class PedVendaConsultaLiberarBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = 6496721284527250903L;
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.PEDVENDA_LIBERACAO_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private List<PedVendaFBDTO> lista;
	
	//
	private String descricaoFilter;
	private Date dataFilter1;
	private Date dataFilter2;
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	
	//	
	private List<SelectItem> empresasSelect;
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		onStartDatas();
		
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
	
	public void buscar() {
		lista = null;
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
		
		descricaoFilter = null;
		lista = null;
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
	
	// gets and sets	
	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
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
	
	public void closeFilterVendedor(){ }
	
	public List<VendedorFB> completeVendedor(String query) {
		List<VendedorFB> filteredVendedor = new ArrayList<VendedorFB>();
    	filteredVendedor = new VendedorFBRN().listar(query);
        return filteredVendedor;
    }
	
	public List<SelectItem> getEmpresasSelect() {
		if (this.empresasSelect == null) {
			
			this.empresasSelect = new ArrayList<SelectItem>();
			
			EmpresaFBRN empresaRN = new EmpresaFBRN();
			this.empresasSelect = empresaRN.montaDadosSelect(this.getContextoBean().getUsuarioLogado().getEmpresas(), "");
			
		}
		return empresasSelect;
	}

	public List<PedVendaFBDTO> getLista() {
		if (lista == null) {
			lista = new PedVendaFBDTORN().listarBloqueados(descricaoFilter, vendedorFilter, dataFilter1, dataFilter2, ContextoUtil.getContextoBean().getUsuarioLogado());
		}
		return lista;
	}

	public void setLista(List<PedVendaFBDTO> lista) {
		this.lista = lista;
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
		    session.removeAttribute("pedVendaConsultaLiberarBean");
		}
	}

}
