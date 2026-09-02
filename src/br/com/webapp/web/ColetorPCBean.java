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
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBDTO;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFBRN;
import br.com.webapp.model.fb.coletorpc.SituacaoPC;
import br.com.webapp.model.fb.coletorpc.SituacaoPCRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="coletorPCBean")
@SessionScoped
public class ColetorPCBean implements Serializable{
	
	private static final long serialVersionUID = -550167041931276965L;

	private Integer menu = MenuAcessoController.COLETORPCBEAN_MENU;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	private Integer selecionadaId;
	private FornecedorFB fornecedor;
	private ColetorPCFB selecionada;
	private List<ColetorPCFBDTO> lista;
	private List<NFCompraFB> listaNFCompra;
	private List<NFCompraFB> nfsSelected;
	private List<ColetorPCItemFB> itens;
	private FornecedorFB fornecedorSelecionada;
	private String produtoFilter;
	private String planilhaCegaIdFilter;
	private EmpresaFB empresaFilter;
	private EmpresaFB empresa;
	private String fornecedorFilter;
	private String notafiscalFilter;
	private boolean concluidoFilter;
	private List<SituacaoPC> situacaoFilter;
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
		//this.situacaoFilterDefault();
		this.onStartDatas();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
		}
	}
	
	public void buscar(){
		
		lista = null;
	}
	
	public void limpar(){
		this.onStartDatas();
		produtoFilter = null;
		planilhaCegaIdFilter = null;
		empresaFilter = null;
		fornecedorFilter = null;
		notafiscalFilter = null;
		//this.situacaoFilterDefault();
		lista = null;
		listaNFCompra = null;
		nfsSelected = null;
	}
	

	public List<FornecedorFB> completeFornecedor(String descricaoFilter) {
		return new FornecedorFBRN().listarParaPlanilhaCega(empresa.getId(), descricaoFilter);
    }
	
	public void closeFilterFornecedor(){
		selecionada.setFornecedor(fornecedor);
		selecionada.setFornecedorId(fornecedor.getId());
		if(fornecedor.getId()!=null && empresa.getId()!=null){
			listaNFCompra = new NFCompraFBRN().listar(fornecedor.getId(), empresa.getId());
			if(listaNFCompra.size()==0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", UtilMessage.mensagem("msg.fornecedor.semnfcompra.planilhacega")));
			}
		}
		
	}
	
	public void novo(){
		
		this.editando = false;
		this.tituloAdd = "CADASTRAR";
		this.filterFornecedor = false;
		this.empresa = null;
		this.fornecedor = null;
		
	}
	
	public void novaPlanilha() {
		this.selecionadaId = null;
		this.selecionada = new ColetorPCFBRN().novo(contextoBean.getUsuarioLogado());
		
		this.editando = false;
		this.tituloAdd = "CADASTRAR";
		this.filterFornecedor = false;
		this.fornecedor = null;
		this.empresa = null; 
		listaNFCompra = null;
		itens = null;
		lista = null;
		nfsSelected = null;
		empresaFilter = null;
		fornecedorFilter = null;
		notafiscalFilter = null;
		//this.situacaoFilterDefault();
		disabledBtnSalvar = true;
		disabledBtnLiberarConf = true;
	}
	
	
	public void excluir() {
		try {
			ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
			coletorPCFBRN.excluir(selecionadaId);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.excluir.coletorpc")));	
			selecionada = null;
			selecionadaId = null;
			empresa = null;
			buscar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void onRowSelect(SelectEvent event){
		   System.out.println("[onRowSelect] total Selected: " + nfsSelected.size());
		   NFCompraFB nf =  (NFCompraFB) event.getObject();
		   try {
				List<NFCompraFB> nfs = new ArrayList<NFCompraFB>();
				nfs.add(nf);
				ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
				ColetorPCItemFBRN planilhaCegaItemRN = new ColetorPCItemFBRN();
				selecionadaId = planilhaCegaItemRN.processarNFs(selecionada, nfs, true, contextoBean.getUsuarioLogado());
				selecionada = coletorPCFBRN.carregar(selecionadaId);

				if(selecionadaId!=null){
					itens = new ColetorPCItemFBRN().listar(selecionadaId);
				}
				
				habilitarDesabilitarBtns(nfs);
				
			} catch (Exception e) {
				this.nfsSelected.remove(nf);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
				e.printStackTrace();
			}
		}
		public void unRowSelect(UnselectEvent event){
			System.out.println("[unRowSelect] total Selected: " + nfsSelected.size());
			NFCompraFB nf =  (NFCompraFB) event.getObject();
			try {
				List<NFCompraFB> nfs = new ArrayList<NFCompraFB>();
				nfs.add(nf);
				ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
				ColetorPCItemFBRN planilhaCegaItemRN = new ColetorPCItemFBRN();
				selecionadaId = planilhaCegaItemRN.processarNFs(selecionada, nfs, false, contextoBean.getUsuarioLogado());
				selecionada = coletorPCFBRN.carregar(selecionadaId);
				
				if(selecionadaId!=null){
					itens = new ColetorPCItemFBRN().listar(selecionadaId);
				}
				
				nfs.remove(nf);
				
				habilitarDesabilitarBtns(nfs);
			
			} catch (RNException e) {
				this.nfsSelected.add(nf);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
				e.printStackTrace();
			}
			
		}

		public void onRowSelectAll(ToggleSelectEvent event){
			
			System.out.println("[onRowSelectAll] total Selected: " + nfsSelected.size());
			try {
				
				ColetorPCItemFBRN planilhaCegaItemRN = new ColetorPCItemFBRN();
				selecionadaId = planilhaCegaItemRN.processarNFs(selecionada, this.nfsSelected, event.isSelected(), contextoBean.getUsuarioLogado());
				selecionada = new ColetorPCFBRN().carregar(selecionadaId);
				
				if(selecionadaId!=null){
					itens = new ColetorPCItemFBRN().listar(selecionadaId);
				}
				
				habilitarDesabilitarBtns(this.nfsSelected);
			
			} catch (RNException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
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
			
			selecionada = new ColetorPCFBRN().salvar(selecionada, contextoBean.getUsuarioLogado());
			limpar();
			buscar();
			
			
			if(editando) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.editar.coletorpc")));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.salvar.coletorpc")));
			}	
			
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("closeEdicaoPlanilhaCega();");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
		this.novaPlanilha();
		
}
	
	
	public void editar() {
		
		ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
		selecionada = coletorPCFBRN.carregar(selecionadaId);
		
		if(selecionada == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", UtilMessage.mensagem("msg.editar.coletorpc")));	
		}

		nfsSelected = null;
		empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());
		fornecedor = new FornecedorFBRN().carregar(selecionada.getFornecedorId());
		listaNFCompra = new NFCompraFBRN().listar(selecionada.getFornecedorId(), selecionada.getEmpresaId());
		nfsSelected = new NFCompraFBRN().listarPorPlanilhaCegaEFornecedor(selecionada);
		
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
			ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
			coletorPCFBRN.liberar(this.selecionada);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.liberada.coletorpc")));
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
		
	
	public void verificarEmpresa() {
		filterFornecedor = true;
		
		selecionada.setEmpresa(empresa);
		selecionada.setEmpresaId(empresa.getId());
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
	
	private void situacaoFilterDefault(){
		situacaoFilter = new ArrayList<SituacaoPC>();
		situacaoFilter.add(SituacaoPCRN.getEmDigitacao());
		situacaoFilter.add(SituacaoPCRN.getLiberadoParaConferencia());
		situacaoFilter.add(SituacaoPCRN.getEmConferencia());
		situacaoFilter.add(SituacaoPCRN.getFinalizada());
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
	
	public ColetorPCFB getSelecionada() {
		if(selecionada==null && selecionadaId != null){
			ColetorPCFBRN planilhaCegaRN = new ColetorPCFBRN();
			selecionada = planilhaCegaRN.carregar(selecionadaId);
			if(selecionada != null) {
				listaNFCompra = new NFCompraFBRN().listarPorPlanilhaCegaEFornecedor(this.getSelecionada());
				nfsSelected = new ArrayList<NFCompraFB>();
				for(NFCompraFB rs : listaNFCompra){
					if(rs.getColetorPCFB() != null){
						nfsSelected.add(rs);
					}
				}
				if(nfsSelected.size()>0){
					filterFornecedor = false;
				}else{
					filterFornecedor = true;
				}
			}else {
				novo();
			}
		}
		return selecionada;
	}
	
	public void setSelecionada(ColetorPCFB selecionada) {
		this.selecionada = selecionada;
	}
	public List<ColetorPCFBDTO> getLista() {
		if(lista == null) {
			this.lista = new ColetorPCFBRN().listar(empresaFilter, fornecedorFilter, planilhaCegaIdFilter, notafiscalFilter, planilhaCegaIdFilter, produtoFilter, data1Filter, data2Filter, concluidoFilter, null);
		}
		return lista;
	}
	public void setLista(List<ColetorPCFBDTO> lista) {
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
	
	public boolean isConcluidoFilter() {
		return concluidoFilter;
	}

	public void setConcluidoFilter(boolean concluidoFilter) {
		this.concluidoFilter = concluidoFilter;
	}

	public List<SituacaoPC> getSituacaoFilter() {
		return situacaoFilter;
	}

	public void setSituacaoFilter(List<SituacaoPC> situacaoFilter) {
		this.situacaoFilter = situacaoFilter;
	}

	public List<SelectItem> getSituacaoSelect() {
		return situacaoSelect;
	}
	public void setSituacaoSelect(List<SelectItem> situacaoSelect) {
		this.situacaoSelect = situacaoSelect;
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

	public List<ColetorPCItemFB> getItens() {
		if(itens == null  && selecionadaId != null ) {
			itens = new ColetorPCItemFBRN().listar(selecionadaId);
		}
		return itens;
	}

	public void setItens(List<ColetorPCItemFB> itens) {
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
		      session.removeAttribute("coletorPCBean");
		  }
	}
	
	

}
