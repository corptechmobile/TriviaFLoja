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
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFB;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg.PedVendaDivergRel;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg.PedVendaDivergRelRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name = "pedVendaDivergRelBean")
@SessionScoped
public class PedVendDivergRelBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = -7462293152142877814L;
	private Integer menu = MenuAcessoController.PEDVENDA_DIVERG_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private PedVendaDivergRel selecionada;
	private List<PedVendaDivergRel> lista;
	
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	private ClienteFB clienteFilter;
	private UsuarioFB usuarioFilter;
	private String produtoFilter;
	private Integer tipoDataFilter;
	private Integer tipoDivergFilter;
	private Integer tipoSituacaoFilter;
	private String tipoDivergLabel;
	private Date dataFilter1;
	private Date dataFilter2;
	private boolean filterPorCodBarra;
	private String codBarraFilter;
	private String linhaProdFilter;
	private boolean semEstoqueFilter;
	
	private List<SelectItem> empresaSelect;
	private List<SelectItem> tipoDataSelect;
	private List<SelectItem> tipoDivergSelect;
	private List<SelectItem> situacaoDivergSelect;
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		limpar();
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

	public List<VendedorFB> completeVendedor(String query) {
		List<VendedorFB> filteredVendedor = new ArrayList<VendedorFB>();
    	filteredVendedor = new VendedorFBRN().listar(query);
        return filteredVendedor;
    }
	
	public void onStartDatas(){
		
		if(dataFilter1==null || dataFilter2==null){
			Calendar caIni = Calendar.getInstance();	
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
	
	public List<ClienteFB> completeCliente(String query) {
		List<ClienteFB> filteredCliente = new ArrayList<ClienteFB>();
        if (query != null && !query.equals("")) {
        	filteredCliente = new ClienteFBRN().listar(query);
		}  
        return filteredCliente;
    }
	
	public void buscar() {
		lista = null;
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public PedVendaDivergRel getSelecionada() {
		return selecionada;
	}
	
	public void setSelecionada(PedVendaDivergRel selecionada) {
		this.selecionada = selecionada;
	}
	
	public List<PedVendaDivergRel> getLista() {
		if (lista == null) {
			PedVendaDivergRelRN pedVendaDivergRelRN = new PedVendaDivergRelRN();
			lista = pedVendaDivergRelRN.listar(vendedorFilter, empresaFilter, produtoFilter, tipoDataFilter, dataFilter1, dataFilter2, usuarioFilter, tipoDivergFilter, tipoSituacaoFilter);
			for (PedVendaDivergRel rs : lista) {
				if (rs.getSituacaoDiverg().equals(PedVendaDivergFB.SITUACAO_EM_ABERTO)) {
					rs.setSituacaoDivergToString("Em Aberto");
				}else if (rs.getSituacaoDiverg().equals(PedVendaDivergFB.SITUACAO_LIBERADO)) {
					rs.setSituacaoDivergToString("Liberado");
				}else if (rs.getSituacaoDiverg().equals(PedVendaDivergFB.SITUACAO_NAO_LIBERADO)) {
					rs.setSituacaoDivergToString("Não Liberado");
				}
			}
		}
		return lista;
	}
	
	public void setLista(List<PedVendaDivergRel> lista) {
		this.lista = lista;
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
	
	public ClienteFB getClienteFilter() {
		return clienteFilter;
	}

	public void setClienteFilter(ClienteFB clienteFilter) {
		this.clienteFilter = clienteFilter;
	}

	public UsuarioFB getUsuarioFilter() {
		return usuarioFilter;
	}

	public void setUsuarioFilter(UsuarioFB usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	public String getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(String produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public Integer getTipoDataFilter() {
		return tipoDataFilter;
	}

	public void setTipoDataFilter(Integer tipoDataFilter) {
		this.tipoDataFilter = tipoDataFilter;
	}

	public Integer getTipoDivergFilter() {
		return tipoDivergFilter;
	}

	public void setTipoDivergFilter(Integer tipoDivergFilter) {
		this.tipoDivergFilter = tipoDivergFilter;
	}

	public Integer getTipoSituacaoFilter() {
		return tipoSituacaoFilter;
	}

	public void setTipoSituacaoFilter(Integer tipoSituacaoFilter) {
		this.tipoSituacaoFilter = tipoSituacaoFilter;
	}

	public String getTipoDivergLabel() {
		return tipoDivergLabel;
	}

	public void setTipoDivergLabel(String tipoDivergLabel) {
		this.tipoDivergLabel = tipoDivergLabel;
	}
	
	public void limpar() {
		setTipoDataFilter(1);
		setTipoDivergFilter(0);
		setTipoSituacaoFilter(3);
		setDataFilter1(null);
		setDataFilter2(null);
		onStartDatas();
		setVendedorFilter(null);
		setClienteFilter(null);
		setProdutoFilter(null);
		lista = null;
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

	public List<SelectItem> getEmpresaSelect() {
		
		if (this.empresaSelect == null) {
			
			this.empresaSelect = new ArrayList<SelectItem>();
			
			EmpresaFBRN empresaRN = new EmpresaFBRN();
			this.empresaSelect = empresaRN.montaDadosSelect(this.getContextoBean().getUsuarioLogado().getEmpresas(), "");
			
		}
		return empresaSelect;
	}
	
	public void setEmpresaSelect(List<SelectItem> empresaSelect) {
		this.empresaSelect = empresaSelect;
	}
	
	public List<SelectItem> getTipoDataSelect() {
		Map<Integer, String> tiposDatas = new HashMap<Integer, String>();
		tiposDatas.put(1, "Entrada");
		tiposDatas.put(2, "Interacao");
		
		tipoDataSelect = new ArrayList<SelectItem>();
		
		for (Integer key : tiposDatas.keySet()) {
			tipoDataSelect.add(new SelectItem(key, tiposDatas.get(key)));
		}
		
		return tipoDataSelect;
	}
	
	public void setTipoDataSelect(List<SelectItem> tipoDataSelect) {
		this.tipoDataSelect = tipoDataSelect;
	}
	
	public List<SelectItem> getTipoDivergSelect() {
		Map<Integer, String> tipos = new HashMap<Integer, String>();
		
		tipos.put(PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO, "Por Desconto");
		tipos.put(PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES, "Por Lotes Diferentes");
		tipos.put(PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP, "Por Venda Sem Estoque Disponível");
		
		tipoDivergSelect = new ArrayList<SelectItem>();
		tipoDivergSelect.add(new SelectItem(0, "Todas"));
		
		for (Integer key : tipos.keySet()) {
			tipoDivergSelect.add(new SelectItem(key, tipos.get(key)));
		}
		
		return tipoDivergSelect;
	}
	
	public void setTipoDivergSelect(List<SelectItem> tipoDivergSelect) {
		this.tipoDivergSelect = tipoDivergSelect;
	}
	
	public List<SelectItem> getSituacaoDivergSelect() {
		
		Map<Integer, String> situacoes = new HashMap<Integer, String>();
		
		situacoes.put(PedVendaDivergFB.SITUACAO_EM_ABERTO, "Em Aberto");
		situacoes.put(PedVendaDivergFB.SITUACAO_LIBERADO, "Liberado");
		situacoes.put(PedVendaDivergFB.SITUACAO_NAO_LIBERADO, "Não Liberado");
		
		situacaoDivergSelect = new ArrayList<SelectItem>();
		situacaoDivergSelect.add(new SelectItem(3, "Todas"));
		
		for (Integer key : situacoes.keySet()) {
			situacaoDivergSelect.add(new SelectItem(key, situacoes.get(key)));
		}
		return situacaoDivergSelect;
	}
	
	public void setSituacaoDivergSelect(List<SelectItem> situacaoDivergSelect) {
		this.situacaoDivergSelect = situacaoDivergSelect;
	}
	
	public boolean isFilterPorCodBarra() {
		return filterPorCodBarra;
	}

	public void setFilterPorCodBarra(boolean filterPorCodBarra) {
		this.filterPorCodBarra = filterPorCodBarra;
	}

	public String getCodBarraFilter() {
		return codBarraFilter;
	}

	public void setCodBarraFilter(String codBarraFilter) {
		this.codBarraFilter = codBarraFilter;
	}

	public String getLinhaProdFilter() {
		return linhaProdFilter;
	}

	public void setLinhaProdFilter(String linhaProdFilter) {
		this.linhaProdFilter = linhaProdFilter;
	}

	public boolean isSemEstoqueFilter() {
		return semEstoqueFilter;
	}

	public void setSemEstoqueFilter(boolean semEstoqueFilter) {
		this.semEstoqueFilter = semEstoqueFilter;
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
		    session.removeAttribute("pedVendaConsultaBean");
		}
	}
	
}
