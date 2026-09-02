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

import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.cliente.ClienteNaoPositivadoFBDTO;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFB;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBRN;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBDTO;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBRN;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFB;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name = "clienteNaoPositivadoBean")
@SessionScoped
public class ClienteNaoPositivadoBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = 1016340966522168204L;

	private Integer menu = MenuAcessoController.CLIENTENAOPOSITIVADO_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private ClienteNaoPositivadoFBDTO selecionada;
	private Integer selecionadaId;
	private List<ClienteNaoPositivadoFBDTO> listaItens;
	
	private Date dataFilter1;
	private Date dataFilter2;
	private String cidadeFilter;
	private String bairroFilter;
	

	private String tituloAdd = "CADASTRAR";
	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		onStartDatas();
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
	
	public void buscar() {
		try {
			listaItens = new ClienteFBRN().listarClientesNaoPositivados(dataFilter1, dataFilter2, null, cidadeFilter, bairroFilter, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
		}
	}
	
	
	public String styloTotais(Double valor) {
		String styleCss = "";
		if(valor<100) {
			styleCss = "customBgGreenMeta";
		}else if(valor>100){ 
			styleCss = "customBgRedMeta";
		}else{
			styleCss = "customBgYellowMeta";
		}
		if(valor==0.0) {
			styleCss = "";
		}
		
		return styleCss;
	}
	
		
	public void limpar(){
		listaItens = null;
		onStartDatas();
		cidadeFilter = null;
		bairroFilter = null;

	}
	

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	public ClienteNaoPositivadoFBDTO getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ClienteNaoPositivadoFBDTO selecionada) {
		this.selecionada = selecionada;
	}

	public List<ClienteNaoPositivadoFBDTO> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ClienteNaoPositivadoFBDTO> listaItens) {
		this.listaItens = listaItens;
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

	public String getCidadeFilter() {
		return cidadeFilter;
	}

	public void setCidadeFilter(String cidadeFilter) {
		this.cidadeFilter = cidadeFilter;
	}

	public String getBairroFilter() {
		return bairroFilter;
	}

	public void setBairroFilter(String bairroFilter) {
		this.bairroFilter = bairroFilter;
	}


	// Implementation Menu
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
		System.out.println("[ClienteNaoPositivadoBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("clienteNaoPositivadoBean");
		}
	}

}
