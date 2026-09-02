package br.com.webapp.web;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.webapp.model.fb.coletor.ColetorInvFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioFB;
import br.com.webapp.model.fb.romaneio.RomaneioFBDTO;
import br.com.webapp.model.fb.romaneio.RomaneioFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioItemFB;
import br.com.webapp.model.fb.romaneio.RomaneioItemFBRN;
import br.com.webapp.model.fb.romaneio.StatusRomaneioFB;
import br.com.webapp.model.fb.romaneio.StatusRomaneioFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;



@ManagedBean(name="romaneioBean")
@SessionScoped
public class RomaneioBean implements Serializable{
	
	private static final long serialVersionUID = -550167041931276965L;

	private Integer menu = MenuAcessoController.ROMANEIOBEAN_MENU;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	private Integer selecionadaId;
	private FornecedorFB fornecedor;
	private RomaneioFB selecionada;
	private List<RomaneioFB> lista;
	private List<NFCompraFB> listaNFCompra;
	private List<NFCompraFB> nfsSelected;
	private List<RomaneioItemFB> itens;
	private FornecedorFB fornecedorSelecionada;
	private String numProcTranspFilter;
	private String numRomaneioFilter;
	private String usuarioFilter;
	private String tipoDataFilter;
	private String tipoUsuarioFilter;
	private String statusFilter;	
	private String produtoFilter;
	private String planilhaCegaIdFilter;
	private EmpresaFB empresaFilter;
	private EmpresaFB empresa;
	private String fornecedorFilter;
	private String notafiscalFilter;
	private List<Integer> situacaoFilter;
	private List<SelectItem> situacaoSelect;
	private Date data1Filter;
	private Date data2Filter;
	private String tituloAdd = "CADASTRAR";
	private boolean editando = false;	
	private List<SelectItem> empresasSelect;
	private boolean filterFornecedor;
	private boolean disabledBtnSalvar;
	private boolean disabledBtnLiberarConf;
	private boolean disabledBtnCancelar;
	
	
	@PostConstruct
	public void init(){
		this.novo();
		this.onStartDatas();
		this.onStartStatus();
		this.buscar();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
		}
	}
	
	public void buscar(){
		lista = new RomaneioFBRN().listar(numProcTranspFilter, numRomaneioFilter, usuarioFilter, produtoFilter, data1Filter, data2Filter, situacaoFilter);
	}
	
	public void limpar(){
		this.onStartDatas();
		this.onStartStatus();
		produtoFilter = null;
		buscar();
	}
	

	public void novo(){
		
		this.editando = false;
		this.tituloAdd = "CADASTRAR";
		
	}
	
	
	
	public void excluir() {
		try {
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			romaneioFBRN.excluir(selecionadaId);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.excluir.romaneio")));	
			selecionada = null;
			selecionadaId = null;
			empresa = null;
			buscar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void habilitarDesabilitarBtns(List<NFCompraFB> nfs) {
		if(nfs.size()>0) {
			disabledBtnSalvar = false;
			disabledBtnLiberarConf = false;
		}else {
			disabledBtnSalvar = true;
			disabledBtnLiberarConf = true;
		}
	}	
	
	
	public void salvar() {
		try {
			
			selecionada = new RomaneioFBRN().salvar(selecionada, contextoBean.getUsuarioLogado());
			limpar();
			
			if(editando) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.editar.romaneio")));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.salvar.romaneio")));
			}	
			
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("closeEdicaoPlanilhaCega();");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
		
		
}
	
	
	public void editar() {
		
		RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
		selecionada = romaneioFBRN.carregar(selecionadaId);
		
		if(selecionada == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", UtilMessage.mensagem("msg.editar.romaneio")));	
		}

		nfsSelected = null;
		
		if(listaNFCompra.size()==0 && nfsSelected.size()>0) {
			listaNFCompra = nfsSelected;
		}
		
		
		this.tituloAdd = "EDITAR";
		this.editando = true;
		
		
		
		disabledBtnSalvar = false;
		disabledBtnLiberarConf = false;
		
	}
	
	
	
	public void verifcar()	{
		
	}
	
	public void liberar(){
		try {
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			romaneioFBRN.liberar(this.selecionada, contextoBean.getUsuarioLogado());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.liberada.romaneio")));
			this.novo();	
			limpar();
			buscar();	
			
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("closeEdicaoPlanilhaCega();");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		} 	
	}
		
	
	public void onStartDatas(){
		
		Calendar caIni = Calendar.getInstance();
		caIni.set(Calendar.DATE, 1);
		caIni.set(Calendar.HOUR, 0);
		caIni.set(Calendar.MINUTE, 0);
		caIni.set(Calendar.SECOND, 0);
		caIni.set(Calendar.AM_PM, Calendar.AM);
		
		this.data1Filter = caIni.getTime();
		
		Calendar caFim = Calendar.getInstance();
		caFim.set(Calendar.HOUR, 11);
		caFim.set(Calendar.MINUTE, 59);
		caFim.set(Calendar.SECOND, 59);
		caFim.set(Calendar.AM_PM, Calendar.PM);
		this.data2Filter = caFim.getTime();
	
	} 
	
	private void onStartStatus() {
		situacaoFilter = new ArrayList<Integer>();
		situacaoFilter.add(0);
		situacaoFilter.add(1);
		situacaoFilter.add(2);
		situacaoFilter.add(3);
		
	}
	
	//Get Set
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
	public Integer getMenu() {
		return menu;
	}
	public void setMenu(Integer menu) {
		this.menu = menu;
	}
	public Integer getSelecionadaId() {
		return selecionadaId;
	}
	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}
	
	
	
	public RomaneioFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(RomaneioFB selecionada) {
		this.selecionada = selecionada;
	}

	public String getNumProcTranspFilter() {
		return numProcTranspFilter;
	}

	public void setNumProcTranspFilter(String numProcTranspFilter) {
		this.numProcTranspFilter = numProcTranspFilter;
	}

	public String getNumRomaneioFilter() {
		return numRomaneioFilter;
	}

	public void setNumRomaneioFilter(String numRomaneioFilter) {
		this.numRomaneioFilter = numRomaneioFilter;
	}

	public String getUsuarioFilter() {
		return usuarioFilter;
	}

	public void setUsuarioFilter(String usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	public String getTipoDataFilter() {
		return tipoDataFilter;
	}

	public void setTipoDataFilter(String tipoDataFilter) {
		this.tipoDataFilter = tipoDataFilter;
	}

	public String getTipoUsuarioFilter() {
		return tipoUsuarioFilter;
	}

	public void setTipoUsuarioFilter(String tipoUsuarioFilter) {
		this.tipoUsuarioFilter = tipoUsuarioFilter;
	}
	
	public String getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	public List<RomaneioFB> getLista() {
		return lista;
	}
	public void setLista(List<RomaneioFB> lista) {
		this.lista = lista;
	}
	public FornecedorFB getFornecedor() {
		
		return fornecedor;
	}

	public void setFornecedor(FornecedorFB fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getProdutoFilter() {
		return produtoFilter;
	}
	public void setProdutoFilter(String produtoFilter) {
		this.produtoFilter = produtoFilter;
	}
	public String getPlanilhaCegaIdFilter() {
		return planilhaCegaIdFilter;
	}
	public void setPlanilhaCegaIdFilter(String planilhaCegaIdFilter) {
		this.planilhaCegaIdFilter = planilhaCegaIdFilter;
	}
	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}
	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}
	public String getFornecedorFilter() {
		return fornecedorFilter;
	}
	public void setFornecedorFilter(String fornecedorFilter) {
		this.fornecedorFilter = fornecedorFilter;
	}
	public String getNotafiscalFilter() {
		return notafiscalFilter;
	}
	public void setNotafiscalFilter(String notafiscalFilter) {
		this.notafiscalFilter = notafiscalFilter;
	}
	
	public List<Integer> getSituacaoFilter() {
		return situacaoFilter;
	}

	public void setSituacaoFilter(List<Integer> situacaoFilter) {
		this.situacaoFilter = situacaoFilter;
	}

	public Date getData1Filter() {
		return data1Filter;
	}
	public void setData1Filter(Date data1Filter) {
		this.data1Filter = data1Filter;
	}
	public Date getData2Filter() {
		return data2Filter;
	}
	public void setData2Filter(Date data2Filter) {
		this.data2Filter = data2Filter;
	}
	public String getTituloAdd() {
		return tituloAdd;
	}
	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}
	public boolean isEditando() {
		return editando;
	}
	public void setEditando(boolean editando) {
		this.editando = editando;
	}
	public List<SelectItem> getEmpresasSelect() {
		if(empresasSelect==null) {
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			empresasSelect = empresaFBRN.montaDadosSelect(empresaFBRN.listar(contextoBean.getUsuarioLogado()), "");
		}
		return empresasSelect;
	}
	public void setEmpresasSelect(List<SelectItem> empresasSelect) {
		this.empresasSelect = empresasSelect;
	}
	public boolean isFilterFornecedor() {
		return filterFornecedor;
	}
	public void setFilterFornecedor(boolean filterFornecedor) {
		this.filterFornecedor = filterFornecedor;
	}
	public boolean isDisabledBtnSalvar() {
		return disabledBtnSalvar;
	}
	public void setDisabledBtnSalvar(boolean disabledBtnSalvar) {
		this.disabledBtnSalvar = disabledBtnSalvar;
	}
	public boolean isDisabledBtnLiberarConf() {
		return disabledBtnLiberarConf;
	}
	public void setDisabledBtnLiberarConf(boolean disabledBtnLiberarConf) {
		this.disabledBtnLiberarConf = disabledBtnLiberarConf;
	}
	public boolean isDisabledBtnCancelar() {
		return disabledBtnCancelar;
	}
	public void setDisabledBtnCancelar(boolean disabledBtnCancelar) {
		this.disabledBtnCancelar = disabledBtnCancelar;
	}
	

	public List<NFCompraFB> getListaNFCompra() {
		return listaNFCompra;
	}

	public void setListaNFCompra(List<NFCompraFB> listaNFCompra) {
		this.listaNFCompra = listaNFCompra;
	}

	public List<NFCompraFB> getNfsSelected() {
		return nfsSelected;
	}

	public void setNfsSelected(List<NFCompraFB> nfsSelected) {
		this.nfsSelected = nfsSelected;
	}

	public List<RomaneioItemFB> getItens() {
		if(itens == null  && selecionadaId != null ) {
			itens = new RomaneioItemFBRN().listar(selecionadaId);
		}
		return itens;
	}

	public void setItens(List<RomaneioItemFB> itens) {
		this.itens = itens;
	}

	public FornecedorFB getFornecedorSelecionada() {
		return fornecedorSelecionada;
	}

	public void setFornecedorSelecionada(FornecedorFB fornecedorSelecionada) {
		this.fornecedorSelecionada = fornecedorSelecionada;
	}

	
	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	// Implementation Menu
	public void addHome() {
		MenuAcessoController.addHome(menu, contextoBean.getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	public void addRecentes() {
		MenuAcessoController.addRecentes(menu, contextoBean.getUsuarioLogado());
	}

	public void addFavoritos() {
		MenuAcessoController.addFavoritos(menu, contextoBean.getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	public void clearSession() {
		  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		  if(request.getParameter("id")!=null){
			  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		      session.removeAttribute("romaneioBean");
		  }
	}
	
	

}
