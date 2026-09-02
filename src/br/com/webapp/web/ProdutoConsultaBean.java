package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.model.LazyDataModel;

import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBRN;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.datamodel.ProdutoFBLazyDM;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.RNException;

@ManagedBean(name = "produtoConsultaBean")
@SessionScoped
public class ProdutoConsultaBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = -8864969787932467151L;
	private Integer menu = MenuAcessoController.PRODUTO_CONSULTA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private ProdutoFB selecionada;
	private List<ProdutoFB> lista;
	private LazyDataModel<ProdutoFB> listaProdutosLDM;
	private List<ProdutoEstoqueLoteFB> listarEstoque;
	private List<ProdutoEstoqueLoteFB> listarEstoqueLote;
	
	private int currentTab;
	
	
	private MovFiscTipoFB movFiscTipoFB;
	private String descProdFilter;
	private String codBarraFilter;
	private String fabricanteFilter;
	private String linhaProdFilter;
	private boolean filterPorCodBarra;
	private boolean comEstoqueFilter;
	private boolean semEstoqueFilter;
	private boolean renderedTabLotesProduto;	
	private boolean exportar;

	private Double totalQtd;
	private Double totalQtd2;
	private Double totalVendido;
	private Double totalEmpresa;
	private Double totalBloqueado;
	private Double totalBloqueado2;
	private Double totalReservado;
	private Double totalReservado2;
	private Double totalDisponivel;
	private Double totalDisponivel2;
	
	private List<SelectItem> empresaSelectItem;
	private List<SelectItem> condPagtoSelectItem;
	private List<SelectItem> formaPagtoSelectItem;
	
	private EmpresaFB empresaFilter;
	private EmpresaFB empresaProdutoFilter;
	private CondPagtoFB condicaoFilter;
	private FormaPagtoFB formaFilter;
	private boolean soComEstoqueFilter;
	
	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		comEstoqueFilter = true;
		lista = new ArrayList<ProdutoFB>();
		listaProdutosLDM = null;
		exportar = true;
		
		if(contextoBean.getUsuarioLogado().getEmpresas().size()>=1) {
			if(contextoBean.getUsuarioLogado().getEmpresaId() != null) {
				empresaFilter = new EmpresaFBRN().carregar(contextoBean.getUsuarioLogado().getEmpresaId());
			}else {
				empresaFilter = contextoBean.getUsuarioLogado().getEmpresas().get(0);
			}
		}
		
		if (movFiscTipoFB == null) {
			MovFiscTipoFBRN movFiscTipoFBRN = new MovFiscTipoFBRN();
			movFiscTipoFB = movFiscTipoFBRN.carregarDefault();
		}
		
		descProdFilter = null;
		codBarraFilter = null;
		fabricanteFilter = null;
		linhaProdFilter = null;
		filterPorCodBarra = false;
		
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
//			ProdutoFBRN produtoFBRN = new ProdutoFBRN();
//			if (this.filterPorCodBarra == true) {
//				lista = produtoFBRN.listar(null, codBarraFilter, empresaFilter.getId(), movFiscTipoFB.getId(), condicaoFilter.getTabPrecoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter);
//			}else {
//				lista = produtoFBRN.listar(descProdFilter, null, empresaFilter.getId(), movFiscTipoFB.getId(), condicaoFilter.getTabPrecoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter);
//			}
			
			if(filterPorCodBarra) {
				descProdFilter = null;
				linhaProdFilter = null;
				fabricanteFilter = null;
			}else {
				codBarraFilter = null;
			}
			
			exportar = true;
			CondPagtoFB condPagto = new CondPagtoFBRN().carregar(condicaoFilter.getId(), empresaFilter.getId(), null);
			
			listaProdutosLDM = new ProdutoFBLazyDM(FacesContext.getCurrentInstance(), empresaFilter.getId(), movFiscTipoFB.getOpFiscTipoId(), condPagto.getTabPrecoId(), condicaoFilter.getId(), descProdFilter, codBarraFilter, fabricanteFilter, linhaProdFilter, comEstoqueFilter, semEstoqueFilter);
			RequestContext.getCurrentInstance().execute("PF('UIdtItensProdutoBean').clearFilters();");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openLotesProduto() {
		currentTab = currentTab + 1;
		renderedTabLotesProduto = true;
		soComEstoqueFilter = true;
		
		empresaProdutoFilter = empresaFilter;
		
		buscarEstoqueLote();
	}
	
	public void limparFormaCond() {
		formaFilter = null;
		condicaoFilter = null;
		condPagtoSelectItem = null;
		formaPagtoSelectItem = null;
	}
	
	public void limparCond() {
		condicaoFilter = null;
		condPagtoSelectItem = null;
	}
	
	public void verificaExportacao() {
		exportar = false;
	}
	
	public void buscarEstoqueLote() {
				
		ProdutoEstoqueLoteFBRN produtoEstoqueLoteFBRN = new ProdutoEstoqueLoteFBRN();
		Integer soComEstoque = 0;
		if(soComEstoqueFilter) {
			soComEstoque = 1;
		}
		
		Integer empresaProdFilter = null;
		
		if(empresaProdutoFilter!=null) {
			empresaProdFilter = empresaProdutoFilter.getId();
		}

		listarEstoque = produtoEstoqueLoteFBRN.listarEstoque(empresaProdFilter, contextoBean.getUsuarioLogado().getId(), selecionada.getId(), soComEstoque);
		
		totalQtd = 0d;
		totalVendido = 0d;
		totalEmpresa = 0d;
		totalBloqueado = 0d;
		totalReservado = 0d;
		totalDisponivel = 0d;
		
		for(ProdutoEstoqueLoteFB rs:listarEstoque) {
			if(rs.getQtdTotal()!=null) {
				totalQtd += rs.getQtdTotal();
			}	
			
			if(rs.getQtdVendido()!=null) {
				totalVendido += rs.getQtdVendido();
			}	
			
			if(rs.getQtdEmpresa()!=null) {
				totalEmpresa += rs.getQtdEmpresa();
			}	
			
			if(rs.getQtdBloqueado()!=null) {
				totalBloqueado += rs.getQtdBloqueado();
			}	
			
			if(rs.getQtdReservado()!=null) {
				totalReservado += rs.getQtdReservado();
			}
			
			if(rs.getQtdDisponivel()!=null) {
				totalDisponivel += rs.getQtdDisponivel();
			}	
		}
		
		listarEstoqueLote = produtoEstoqueLoteFBRN.listarLotes(empresaProdFilter, contextoBean.getUsuarioLogado().getId(), selecionada.getId(), soComEstoque);
		
		totalQtd2 = 0d;
		totalReservado2 = 0d;
		totalBloqueado2 = 0d;
		totalDisponivel2 = 0d;
		
		for(ProdutoEstoqueLoteFB rs2:listarEstoqueLote) {
			if(rs2.getQtdTotal()!=null) {
				totalQtd2 += rs2.getQtdTotal();
			}	
			
			if(rs2.getQtdReservado()!=null) {
				totalReservado2 += rs2.getQtdReservado();
			}
			
			if(rs2.getQtdBloqueado()!=null) {
				totalBloqueado2 += rs2.getQtdBloqueado();
			}
			
			if(rs2.getQtdDisponivel()!=null) {
				totalDisponivel2 += rs2.getQtdDisponivel();
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
    	currentTab = 0;
		renderedTabLotesProduto = false;
    }
	
	
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public ProdutoFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ProdutoFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<ProdutoFB> getLista() throws RNException {
		return lista;
	}

	public void setLista(List<ProdutoFB> lista) {
		this.lista = lista;
	}

	public String getDescProdFilter() {
		return descProdFilter;
	}

	public void setDescProdFilter(String descProdFilter) {
		this.descProdFilter = descProdFilter;
	}

	public String getCodBarraFilter() {
		return codBarraFilter;
	}

	public void setCodBarraFilter(String codBarraFilter) {
		this.codBarraFilter = codBarraFilter;
	}
	
	public String getFabricanteFilter() {
		return fabricanteFilter;
	}

	public void setFabricanteFilter(String fabricanteFilter) {
		this.fabricanteFilter = fabricanteFilter;
	}
	
	public String getLinhaProdFilter() {
		return linhaProdFilter;
	}

	public void setLinhaProdFilter(String linhaProdFilter) {
		this.linhaProdFilter = linhaProdFilter;
	}

	public boolean isFilterPorCodBarra() {
		return filterPorCodBarra;
	}
	
	public void setFilterPorCodBarra(boolean filterPorCodBarra) {
		this.filterPorCodBarra = filterPorCodBarra;
	}

	public boolean isComEstoqueFilter() {
		return comEstoqueFilter;
	}

	public void setComEstoqueFilter(boolean comEstoqueFilter) {
		this.comEstoqueFilter = comEstoqueFilter;
	}

	public boolean isSemEstoqueFilter() {
		return semEstoqueFilter;
	}

	public void setSemEstoqueFilter(boolean semEstoqueFilter) {
		this.semEstoqueFilter = semEstoqueFilter;
	}

	public boolean isExportar() {
		return exportar;
	}

	public void setExportar(boolean exportar) {
		this.exportar = exportar;
	}

	public List<SelectItem> getEmpresaSelectItem() {
		if (empresaSelectItem == null) {
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			List<EmpresaFB> empresas = new ArrayList<EmpresaFB>();
			empresas = empresaFBRN.listar(this.getContextoBean().getUsuarioLogado());
			empresaSelectItem = empresaFBRN.montaDadosSelect(empresas, "");
		}
		
		return empresaSelectItem;
	}

	public void setEmpresaSelectItem(List<SelectItem> empresaSelectItem) {
		this.empresaSelectItem = empresaSelectItem;
	}

	public List<SelectItem> getCondPagtoSelectItem() {
		//if (condPagtoSelectItem == null && formaFilterSelecionado == null) {
			CondPagtoFBRN condPagtoFBRN = new CondPagtoFBRN();
			List<CondPagtoFB> conds = new ArrayList<CondPagtoFB>();
			if(formaFilter != null && empresaFilter != null) {
				conds = condPagtoFBRN.listar(formaFilter.getId(), empresaFilter.getId());
			}
			
			if(conds.size()>0 && condicaoFilter == null) {
				condicaoFilter = conds.get(0);
			}
			
			condPagtoSelectItem = condPagtoFBRN.montaDadosSelect(conds, " ");
		//}
		return condPagtoSelectItem;
	}

	public void setCondPagtoSelectItem(List<SelectItem> condPagtoSelectItem) {
		this.condPagtoSelectItem = condPagtoSelectItem;
	}

	public List<SelectItem> getFormaPagtoSelectItem() {
		//if (formaPagtoSelectItem == null) {
			FormaPagtoFBRN formaPagtoFBRN = new FormaPagtoFBRN();
			List<FormaPagtoFB> formas = new ArrayList<FormaPagtoFB>();
			if(empresaFilter != null) {
				formas = formaPagtoFBRN.listarFormaCond(empresaFilter.getId());
			}
			
			if(formas.size()>0 && formaFilter == null) {
				formaFilter = formas.get(0);
			}
			
			formaPagtoSelectItem = formaPagtoFBRN.montaDadosSelect(formas, " ");
		//}
		return formaPagtoSelectItem;
	}

	public void setFormaPagtoSelectItem(List<SelectItem> formaPagtoSelectItem) {
		this.formaPagtoSelectItem = formaPagtoSelectItem;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public CondPagtoFB getCondicaoFilter() {
		return condicaoFilter;
	}

	public void setCondicaoFilter(CondPagtoFB condicaoFilter) {
		this.condicaoFilter = condicaoFilter;
	}

	public FormaPagtoFB getFormaFilter() {
		return formaFilter;
	}

	public void setFormaFilter(FormaPagtoFB formaFilter) {
		this.formaFilter = formaFilter;
	}
	
	public MovFiscTipoFB getMovFiscTipoFB() {
		return movFiscTipoFB;
	}

	public void setMovFiscTipoFB(MovFiscTipoFB movFiscTipoFB) {
		this.movFiscTipoFB = movFiscTipoFB;
	}
	
	public LazyDataModel<ProdutoFB> getListaProdutosLDM() {
		return listaProdutosLDM;
	}

	public void setListaProdutosLDM(LazyDataModel<ProdutoFB> listaProdutosLDM) {
		this.listaProdutosLDM = listaProdutosLDM;
	}
	
	public List<ProdutoEstoqueLoteFB> getListarEstoque() {
		return listarEstoque;
	}

	public void setListarEstoque(List<ProdutoEstoqueLoteFB> listarEstoque) {
		this.listarEstoque = listarEstoque;
	}

	public List<ProdutoEstoqueLoteFB> getListarEstoqueLote() {
		return listarEstoqueLote;
	}

	public void setListarEstoqueLote(List<ProdutoEstoqueLoteFB> listarEstoqueLote) {
		this.listarEstoqueLote = listarEstoqueLote;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public boolean isRenderedTabLotesProduto() {
		return renderedTabLotesProduto;
	}

	public void setRenderedTabLotesProduto(boolean renderedTabLotesProduto) {
		this.renderedTabLotesProduto = renderedTabLotesProduto;
	}

	public Double getTotalQtd() {
		return totalQtd;
	}

	public void setTotalQtd(Double totalQtd) {
		this.totalQtd = totalQtd;
	}

	public Double getTotalQtd2() {
		return totalQtd2;
	}

	public void setTotalQtd2(Double totalQtd2) {
		this.totalQtd2 = totalQtd2;
	}

	public Double getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(Double totalVendido) {
		this.totalVendido = totalVendido;
	}

	public Double getTotalEmpresa() {
		return totalEmpresa;
	}

	public void setTotalEmpresa(Double totalEmpresa) {
		this.totalEmpresa = totalEmpresa;
	}

	public Double getTotalBloqueado() {
		return totalBloqueado;
	}

	public void setTotalBloqueado(Double totalBloqueado) {
		this.totalBloqueado = totalBloqueado;
	}

	public Double getTotalBloqueado2() {
		return totalBloqueado2;
	}

	public void setTotalBloqueado2(Double totalBloqueado2) {
		this.totalBloqueado2 = totalBloqueado2;
	}

	public Double getTotalReservado() {
		return totalReservado;
	}

	public void setTotalReservado(Double totalReservado) {
		this.totalReservado = totalReservado;
	}

	public Double getTotalReservado2() {
		return totalReservado2;
	}

	public void setTotalReservado2(Double totalReservado2) {
		this.totalReservado2 = totalReservado2;
	}

	public Double getTotalDisponivel() {
		return totalDisponivel;
	}

	public void setTotalDisponivel(Double totalDisponivel) {
		this.totalDisponivel = totalDisponivel;
	}

	public Double getTotalDisponivel2() {
		return totalDisponivel2;
	}

	public void setTotalDisponivel2(Double totalDisponivel2) {
		this.totalDisponivel2 = totalDisponivel2;
	}

	public EmpresaFB getEmpresaProdutoFilter() {
		return empresaProdutoFilter;
	}

	public void setEmpresaProdutoFilter(EmpresaFB empresaProdutoFilter) {
		this.empresaProdutoFilter = empresaProdutoFilter;
	}

	public boolean isSoComEstoqueFilter() {
		return soComEstoqueFilter;
	}

	public void setSoComEstoqueFilter(boolean soComEstoqueFilter) {
		this.soComEstoqueFilter = soComEstoqueFilter;
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
		System.out.println("[ProdutoConsultaBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("produtoConsultaBean");
		}
	}

}
