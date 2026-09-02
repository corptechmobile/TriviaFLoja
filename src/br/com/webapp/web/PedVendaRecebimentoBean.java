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

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaFBRN;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTO;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTORN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "pedVendaRecebimentoBean")
@SessionScoped
public class PedVendaRecebimentoBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = 6496721284527250903L;
	
	// Menu Acesso
	private Integer menu = MenuAcessoController.PEDVENDA_RECEBIMENTO_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private Integer selecionadaId;
	private List<PedVendaFBDTO> lista;
	//
	private String descricaoFilter;
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	
	//	
	private List<SelectItem> empresasSelect;
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		if(contextoBean.getUsuarioLogado().getEmpresas().size()>=1) {
			empresaFilter = contextoBean.getUsuarioLogado().getEmpresas().get(0);
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
		selecionadaId = null;
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
	}
	
	public void alterarStatus() {
		try {
			
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			pedVendaFBRN.updateAguardPagtoPedVenda(selecionadaId, contextoBean.getUsuarioLogado().getId());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.status.aguardpagto.pedvenda")));
			selecionadaId = null;
			buscar();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	// gets and sets	
	
	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}
	
	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
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
			lista = new PedVendaFBDTORN().listarEmRecebimento(descricaoFilter, vendedorFilter, this.getContextoBean().getUsuarioLogado());
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
		    session.removeAttribute("pedVendaRecebimentoBean");
		}
	}

}
