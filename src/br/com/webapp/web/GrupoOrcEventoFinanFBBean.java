package br.com.webapp.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFB;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFBRN;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFBRN;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFB;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="grupoOrcEventoFinanFBBean")
@SessionScoped
public class GrupoOrcEventoFinanFBBean implements Serializable {
	private static final long serialVersionUID = -4419246826429118623L;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private String descricaoFilter;
	private String descricaoAssocFilter;
	private String grupoFinanceiro;
	private String grupoFinanceiroAssoc;

	
	private OrcamentoGrupoFB selecionada;
	private Integer selecionadaId;
	private List<EventoFinanceiroFB> lista;
	private List<EventoFinanceiroFB> listaFilter;
	private GrupoFinanceiroFB grupoFinanceiroFilter;
	private GrupoFinanceiroFB grupoFinanceiroAssocFilter;
	private List<SelectItem> grupoFinanceiroSelect;
	private List<SelectItem> grupoFinanceiroAssocSelect;
	

	private boolean filtrar;
	private boolean filtrarAssoc;
	
	private List<EventoFinanceiroFB> eveAssocSelected;
	private List<EventoFinanceiroFB> eveNaoAssocSelected;
	
	@PostConstruct
    public void init() {
		this.novo();
    }
	
	public void addEventos(){
		if(eveNaoAssocSelected.size()>0){
			try {
				EventoFinanceiroFBRN eventoFinanceiroFBRN = new EventoFinanceiroFBRN();
				for(EventoFinanceiroFB rs : eveNaoAssocSelected){
					rs.setOrcamentoGrupoId(selecionada.getId());
					eventoFinanceiroFBRN.editar(rs);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.incluido.eventoGrupoOrcamento")));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
				e.printStackTrace();
			} finally {
				eveNaoAssocSelected = new ArrayList<EventoFinanceiroFB>();
				listaFilter = new EventoFinanceiroFBRN().listarDesassociados(grupoFinanceiro, descricaoFilter, selecionada.getId());
				lista = new EventoFinanceiroFBRN().listarAssociados(grupoFinanceiroAssoc, descricaoAssocFilter, selecionada.getId());
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.eventoFinanceiro.empty.selected")));
		}
	}
	
	public void removeEventos(){
		if(eveAssocSelected.size()>0){
			try {
				EventoFinanceiroFBRN eventoFinanceiroFBRN = new EventoFinanceiroFBRN();
				for(EventoFinanceiroFB rs : eveAssocSelected){
					rs.setOrcamentoGrupoId(null);
					eventoFinanceiroFBRN.editar(rs);
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.excluido.eventoGrupoOrcamento")));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
				e.printStackTrace();
			} finally {
				eveAssocSelected = new ArrayList<EventoFinanceiroFB>();
				listaFilter = new EventoFinanceiroFBRN().listarDesassociados(grupoFinanceiro, descricaoFilter, selecionada.getId());
				lista = new EventoFinanceiroFBRN().listarAssociados(grupoFinanceiroAssoc, descricaoAssocFilter, selecionada.getId());

			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.eventoFinanceiro.empty.selected")));
		}
	}
	
	private void novo(){
		filtrar = false;
		filtrarAssoc=false;
		grupoFinanceiro = null;
		grupoFinanceiroFilter = null;
		listaFilter = new EventoFinanceiroFBRN().listarDesassociados(grupoFinanceiro, descricaoFilter, selecionadaId);
		lista = new EventoFinanceiroFBRN().listarAssociados(grupoFinanceiro, descricaoFilter, selecionadaId);
		eveAssocSelected = new ArrayList<EventoFinanceiroFB>();
		eveNaoAssocSelected = new ArrayList<EventoFinanceiroFB>();

	}
	
	public void buscar(){
		grupoFinanceiro = null;
		if(grupoFinanceiroFilter!=null) {
			grupoFinanceiro = grupoFinanceiroFilter.getId();
		}
		listaFilter = new EventoFinanceiroFBRN().listarDesassociados(grupoFinanceiro, descricaoFilter, selecionada.getId());
		//lista = new EventoFinanceiroFBRN().listarAssociados(grupoFinanceiro, descricaoFilter, selecionada.getId());
		filtrar = true;
	}
	
	public void limpar(){
		this.descricaoFilter = "";
		this.grupoFinanceiro = null;
		this.grupoFinanceiroFilter = null;
		this.listaFilter = null;
		this.filtrar = true;
		this.filtrarAssoc = true;
		buscar();
		buscarAssoc();
	}
	
	public void buscarAssoc(){
		grupoFinanceiroAssoc = null;
		if(grupoFinanceiroAssocFilter!=null) {
			grupoFinanceiroAssoc = grupoFinanceiroAssocFilter.getId();
		}
		lista = new EventoFinanceiroFBRN().listarAssociados(grupoFinanceiroAssoc, descricaoAssocFilter, selecionada.getId());
		filtrarAssoc = true;
	}
	
	public void limparAssoc(){
		this.descricaoAssocFilter = "";
		this.grupoFinanceiroAssoc = null;
		this.grupoFinanceiroAssocFilter = null;
		this.lista = null;
		this.filtrarAssoc = true;
		buscarAssoc();
	}

	
	public void onRowListaSelect(SelectEvent event){
	   System.out.println("[onRowListaSelect] total Selected: " + eveAssocSelected.size());
	}

	public void unRowListaSelect(UnselectEvent event){
		System.out.println("[unRowListaSelect] total Selected: " + eveAssocSelected.size());
	}

	public void onRowListaSelectAll(ToggleSelectEvent event){
		System.out.println("[onRowFilterSelectAll] total Selected: " + eveAssocSelected.size());
	}
	
	public void onRowFilterSelect(SelectEvent event){
	   System.out.println("[onRowFilterSelect] total Selected: " + eveNaoAssocSelected.size());
	}

	public void unRowFilterSelect(UnselectEvent event){
		System.out.println("[unRowFilterSelect] total Selected: " + eveNaoAssocSelected.size());
	}

	public void onRowFilterSelectAll(ToggleSelectEvent event){
		System.out.println("[onRowFilterSelectAll] total Selected: " + eveNaoAssocSelected.size());
	}
	
	/* gets and sets*/ 	
	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
	}
	
	public Integer getSelecionadaId() {
		if(this.selecionadaId!=null) {
			selecionada = new OrcamentoGrupoFBRN().carregar(selecionadaId);
		}
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public OrcamentoGrupoFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(OrcamentoGrupoFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<EventoFinanceiroFB> getLista() {
		return lista;
	}

	public void setLista(List<EventoFinanceiroFB> lista) {
		this.lista = lista;
	}

	public List<EventoFinanceiroFB> getListaFilter() {
		return listaFilter;
	}

	public void setListaFilter(List<EventoFinanceiroFB> listaFilter) {
		this.listaFilter = listaFilter;
	}

	public List<EventoFinanceiroFB> getEveAssocSelected() {
		return eveAssocSelected;
	}

	public void setEveAssocSelected(List<EventoFinanceiroFB> eveAssocSelected) {
		this.eveAssocSelected = eveAssocSelected;
	}

	public List<EventoFinanceiroFB> getEveNaoAssocSelected() {
		return eveNaoAssocSelected;
	}

	public void setEveNaoAssocSelected(List<EventoFinanceiroFB> eveNaoAssocSelected) {
		this.eveNaoAssocSelected = eveNaoAssocSelected;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public boolean isFiltrar() {
		return filtrar;
	}

	public void setFiltrar(boolean filtrar) {
		this.filtrar = filtrar;
	}

	public GrupoFinanceiroFB getGrupoFinanceiroFilter() {
		return grupoFinanceiroFilter;
	}

	public void setGrupoFinanceiroFilter(GrupoFinanceiroFB grupoFinanceiroFilter) {
		this.grupoFinanceiroFilter = grupoFinanceiroFilter;
	}

	public String getGrupoFinanceiro() {
		return grupoFinanceiro;
	}

	public void setGrupoFinanceiro(String grupoFinanceiro) {
		this.grupoFinanceiro = grupoFinanceiro;
	}

	public List<SelectItem> getGrupoFinanceiroSelect() {
		if (this.grupoFinanceiroSelect == null) {
			this.grupoFinanceiroSelect = new ArrayList<SelectItem>();
			GrupoFinanceiroFBRN grupoFinanceiroFBRN = new GrupoFinanceiroFBRN();
			this.grupoFinanceiroSelect = grupoFinanceiroFBRN.montaDadosSelect(new GrupoFinanceiroFBRN().listar(""), "");
		}
		
		return grupoFinanceiroSelect;
	}

	public void setGrupoFinanceiroSelect(List<SelectItem> grupoFinanceiroSelect) {
		this.grupoFinanceiroSelect = grupoFinanceiroSelect;
	}

	public String getDescricaoAssocFilter() {
		return descricaoAssocFilter;
	}

	public void setDescricaoAssocFilter(String descricaoAssocFilter) {
		this.descricaoAssocFilter = descricaoAssocFilter;
	}

	public String getGrupoFinanceiroAssoc() {
		return grupoFinanceiroAssoc;
	}

	public void setGrupoFinanceiroAssoc(String grupoFinanceiroAssoc) {
		this.grupoFinanceiroAssoc = grupoFinanceiroAssoc;
	}

	public GrupoFinanceiroFB getGrupoFinanceiroAssocFilter() {
		return grupoFinanceiroAssocFilter;
	}

	public void setGrupoFinanceiroAssocFilter(GrupoFinanceiroFB grupoFinanceiroAssocFilter) {
		this.grupoFinanceiroAssocFilter = grupoFinanceiroAssocFilter;
	}

	public List<SelectItem> getGrupoFinanceiroAssocSelect() {
		if (this.grupoFinanceiroAssocSelect == null) {
			this.grupoFinanceiroAssocSelect = new ArrayList<SelectItem>();
			GrupoFinanceiroFBRN grupoFinanceiroFBRN = new GrupoFinanceiroFBRN();
			this.grupoFinanceiroAssocSelect = grupoFinanceiroFBRN.montaDadosSelect(new GrupoFinanceiroFBRN().listar(""), "");
		}
		
		return grupoFinanceiroAssocSelect;
	}

	public void setGrupoFinanceiroAssocSelect(List<SelectItem> grupoFinanceiroAssocSelect) {
		this.grupoFinanceiroAssocSelect = grupoFinanceiroAssocSelect;
	}

	public boolean isFiltrarAssoc() {
		return filtrarAssoc;
	}

	public void setFiltrarAssoc(boolean filtrarAssoc) {
		this.filtrarAssoc = filtrarAssoc;
	}

}
