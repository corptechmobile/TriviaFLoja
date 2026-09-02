package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo.ECFVendasPeriodo;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo.ECFVendasPeriodoRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

@ManagedBean(name = "ecfVendasPeriodoBean")
@SessionScoped
public class ECFVendasPeriodoBean implements Serializable, IMenuAcesso{
	
	private static final long serialVersionUID = -3335818020912907648L;
	
	private Integer menu = MenuAcessoController.ECF_VENDAS_PERIODO_BEAN;
	
	private static final String MEDIA_EM_FUNCAO_CLIENTES = "clientes";
	private static final String MEDIA_EM_FUNCAO_VENDAS = "vendas";
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private Date dataFilter1;
	private Date dataFilter2;
	private EmpresaFB empresaFilter;
	private List<ECFVendasPeriodo> lista;
	private Map<Integer, String> listaDiasSemanaHeaders;
	private Map<Integer, String> listaHorasHeaders;
	
	private Integer totalNumClientesGeral = 0;
	private Double totalNumVendasGeral = 0.0;
	private Double mediaGeralHora = 0.0;
	private Double mediaGeralDia = 0.0;
	
	private List<SelectItem> empresasSelect;
	
	private Integer valorDivMedia = 11;
	
	private String mediaEmFuncao;
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		listaDiasSemanaHeaders = new TreeMap<Integer, String>();
		listaHorasHeaders = new TreeMap<Integer, String>();
		mediaEmFuncao = MEDIA_EM_FUNCAO_VENDAS;
		onStartDatas();
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
	
	public void buscar() {
		
		totalNumClientesGeral = 0;
		totalNumVendasGeral = 0.0;
		mediaGeralHora = 0.0;
		
		ECFVendasPeriodoRN ecfVendasPeriodoRN = new ECFVendasPeriodoRN();
		Integer empresaId = empresaFilter.getId();
		lista = ecfVendasPeriodoRN.listar(empresaId, dataFilter1, dataFilter2);
		
		listaHorasHeaders = new TreeMap<Integer, String>();
		for(int x = 8; x <= 18; x++) {
			listaHorasHeaders.put(x, x + " - " + (x + 1));
		}
		
		listaDiasSemanaHeaders = new TreeMap<Integer, String>();
		for(int x = 0; x <= 6; x++) {
			listaDiasSemanaHeaders.put(x, UtilData.diaSemanaFirebirdToString(x));
		}
		
		for (ECFVendasPeriodo rs : lista) {
			totalNumClientesGeral += rs.getNumClientes();
			totalNumVendasGeral += rs.getNumVendas();
		}
		
		findMediaGeral();
	
	}
	
	public void limpar() {
		
		onStartDatas();
		mediaEmFuncao = MEDIA_EM_FUNCAO_VENDAS;
		
		lista = null;
		totalNumClientesGeral = 0;
		totalNumVendasGeral = 0.0;
		mediaGeralDia = 0.0;
		mediaGeralHora = 0.0;
		
	}
	
	public Integer findNumClientes(Integer dia, Integer hora) {
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getDia().equals(dia) && rs.getHora().equals(hora)) {
				return rs.getNumClientes();
			}
		}
		return 0;
	}
	
	public Integer findNumTotalClientes(Integer dia) {
		Integer result = 0;
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getDia().equals(dia)) {
				result += rs.getNumClientes();
			}
		}
		return result;
	}
	
	public Integer findNumTotalClientesHora(Integer hora) {
		Integer result = 0;
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getHora().equals(hora)) {
				result += rs.getNumClientes();
			}
		}
		return result;
	}
	
	public Double findPercTotal(Integer dia, Integer hora) {
		BigDecimal perc = new BigDecimal(0.0);
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientesDia = findNumTotalClientes(dia);
				Integer valTotalClientesDiaHora = findNumClientes(dia, hora);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(valTotalClientesDia, valTotalClientesDiaHora))).setScale(4, RoundingMode.HALF_UP);
				
			}else {
				
				Double valTotalVendasDia = findNumTotalVendas(dia);
				Double valTotalVendasDiaHora = findNumVendas(dia, hora);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(valTotalVendasDia, valTotalVendasDiaHora))).setScale(4, RoundingMode.HALF_UP);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return perc.doubleValue();
	}
	
	public Double findPercHoraTotal(Integer hora) {
		BigDecimal perc = new BigDecimal(0.0);
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientesHora = findNumTotalClientesHora(hora);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(totalNumClientesGeral, valTotalClientesHora))).setScale(4, RoundingMode.HALF_UP);
				
			}else {
				
				Double valTotalVendasHora = findNumTotalVendasHora(hora);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(totalNumVendasGeral, valTotalVendasHora))).setScale(4, RoundingMode.HALF_UP);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return perc.doubleValue();
	}
	
	public Double findPercDiaTotal(Integer dia) {
		BigDecimal perc = new BigDecimal(0.0);
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientesHora = findNumTotalClientes(dia);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(totalNumClientesGeral, valTotalClientesHora))).setScale(4, RoundingMode.HALF_UP);
				
			}else {
				
				Double valTotalVendasHora = findNumTotalVendas(dia);
				perc =  new BigDecimal(String.valueOf(Funcoes.percentual(totalNumVendasGeral, valTotalVendasHora))).setScale(4, RoundingMode.HALF_UP);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return perc.doubleValue();
	}
	
	public Double findMediaDia(Integer dia) {
		BigDecimal mediaDia = new BigDecimal(0.0);
		
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientesDia = findNumTotalClientes(dia);
				mediaDia =  new BigDecimal(String.valueOf(valTotalClientesDia / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				
			}else {
				
				Double valTotalVendasDia = findNumTotalVendas(dia);
				mediaDia =  new BigDecimal(String.valueOf(valTotalVendasDia / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return mediaDia.doubleValue();
		
	}
	
	public String findStyleClassCell(Integer dia, Integer hora){
		String styleClass = "";
		
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientesDia = findNumTotalClientes(dia);
				Integer valTotalClientesDiaHora = findNumClientes(dia, hora);
				BigDecimal mediaDia =  new BigDecimal(String.valueOf(valTotalClientesDia / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				BigDecimal mediaDiaTer = new BigDecimal(String.valueOf(mediaDia.doubleValue() * 0.8)).setScale(0, RoundingMode.HALF_UP);
				
				if(valTotalClientesDiaHora>=mediaDia.intValue() && valTotalClientesDiaHora > 0.0) {
					styleClass = "customBgRed";
				}else if(valTotalClientesDiaHora>=mediaDiaTer.intValue() && valTotalClientesDiaHora > 0.0) {
					styleClass = "customBgYellow";
				}
				
			}else {
				
				Double valTotalVendasDia = findNumTotalVendas(dia);
				Double valTotalVendasDiaHora = Funcoes.arrendondaValor(0, findNumVendas(dia, hora));
				BigDecimal mediaDia =  new BigDecimal(String.valueOf(valTotalVendasDia / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				BigDecimal mediaDiaTer = new BigDecimal(String.valueOf(mediaDia.doubleValue() * 0.8)).setScale(0, RoundingMode.HALF_UP); 
				
				if(valTotalVendasDiaHora>=mediaDia.doubleValue() && valTotalVendasDiaHora > 0.0) {
					styleClass = "customBgRed";
				}else if(valTotalVendasDiaHora>=mediaDiaTer.doubleValue() && valTotalVendasDiaHora > 0.0) {
					styleClass = "customBgYellow";
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return styleClass;
	}
	
	public String findStyleClassCellTotal(Integer keyDiaHora, String diaORhora){
		String styleClass = "";
		
		try {
			
			Double media = 0.0;
			if(diaORhora.equals("dia")) {
				media = mediaGeralDia;
			}else {
				media = mediaGeralHora;
			}
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				
				Integer valTotalClientes = 0;
				if(diaORhora.equals("hora")) {
					valTotalClientes = findNumTotalClientesHora(keyDiaHora);
				}else {
					valTotalClientes = findNumTotalClientes(keyDiaHora);
				}
				
				BigDecimal mediaTer = new BigDecimal(String.valueOf(media.doubleValue() * 0.8)).setScale(0, RoundingMode.HALF_UP);
				
				if(valTotalClientes>=media.intValue() && valTotalClientes > 0.0) {
					styleClass = "customBgRed";
				}else if(valTotalClientes>=mediaTer.intValue() && valTotalClientes > 0.0) {
					styleClass = "customBgYellow";
				}
				
			}else {
				
				Double valTotalVendas = 0.0;
				if(diaORhora.equals("hora")) {
					valTotalVendas = findNumTotalVendasHora(keyDiaHora);
				}else {
					valTotalVendas = findNumTotalVendas(keyDiaHora);
				}
				BigDecimal mediaTer = new BigDecimal(String.valueOf(media.doubleValue() * 0.8)).setScale(0, RoundingMode.HALF_UP); 
				
				if(valTotalVendas>=media.doubleValue() && valTotalVendas > 0.0) {
					styleClass = "customBgRed";
				}else if(valTotalVendas>=mediaTer.doubleValue() && valTotalVendas > 0.0) {
					styleClass = "customBgYellow";
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return styleClass;
	}
	
	public Double findNumVendas(Integer dia, Integer hora) {
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getDia().equals(dia) && rs.getHora().equals(hora)) {
				return rs.getNumVendas();
			}
		}
		return 0.0;
	}
	
	public Double findNumTotalVendas(Integer dia) {
		Double result = 0.0;
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getDia().equals(dia)) {
				result += rs.getNumVendas();
			}
		}
		return result;
	}
	
	public Double findNumTotalVendasHora(Integer hora) {
		Double result = 0.0;
		for(ECFVendasPeriodo rs : lista) {
			if(rs.getHora().equals(hora)) {
				result += rs.getNumVendas();
			}
		}
		return result;
	}
	
	public void findMediaGeral() {
		mediaGeralHora = 0.0;
		BigDecimal mediaHora = new BigDecimal(0.0);
		BigDecimal mediaDia = new BigDecimal(0.0);
		
		try {
			
			if(mediaEmFuncao.equals(MEDIA_EM_FUNCAO_CLIENTES)) {
				mediaHora =  new BigDecimal(String.valueOf(totalNumClientesGeral / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				mediaDia =  new BigDecimal(String.valueOf(totalNumClientesGeral / 7)).setScale(0, RoundingMode.HALF_UP);
			}else {
				mediaHora =  new BigDecimal(String.valueOf(totalNumVendasGeral / valorDivMedia)).setScale(0, RoundingMode.HALF_UP);
				mediaDia =  new BigDecimal(String.valueOf(totalNumVendasGeral / 7)).setScale(0, RoundingMode.HALF_UP);
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		mediaGeralHora = mediaHora.doubleValue();
		mediaGeralDia = mediaDia.doubleValue();
	}
	

	// gets and sets	
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

	public List<ECFVendasPeriodo> getLista() {
		return lista;
	}

	public void setLista(List<ECFVendasPeriodo> lista) {
		this.lista = lista;
	}

	public Map<Integer, String> getListaDiasSemanaHeaders() {
		return listaDiasSemanaHeaders;
	}

	public void setListaDiasSemanaHeaders(Map<Integer, String> listaDiasSemanaHeaders) {
		this.listaDiasSemanaHeaders = listaDiasSemanaHeaders;
	}

	public Map<Integer, String> getListaHorasHeaders() {
		return listaHorasHeaders;
	}

	public void setListaHorasHeaders(Map<Integer, String> listaHorasHeaders) {
		this.listaHorasHeaders = listaHorasHeaders;
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

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
	public Integer getTotalNumClientesGeral() {
		return totalNumClientesGeral;
	}

	public void setTotalNumClientesGeral(Integer totalNumClientesGeral) {
		this.totalNumClientesGeral = totalNumClientesGeral;
	}

	public Double getTotalNumVendasGeral() {
		return totalNumVendasGeral;
	}

	public void setTotalNumVendasGeral(Double totalNumVendasGeral) {
		this.totalNumVendasGeral = totalNumVendasGeral;
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
	
	public String getMediaEmFuncao() {
		return mediaEmFuncao;
	}

	public void setMediaEmFuncao(String mediaEmFuncao) {
		this.mediaEmFuncao = mediaEmFuncao;
	}
	
	public Double getMediaGeralHora() {
		return mediaGeralHora;
	}

	public void setMediaGeralHora(Double mediaGeralHora) {
		this.mediaGeralHora = mediaGeralHora;
	}
	
	public Double getMediaGeralDia() {
		return mediaGeralDia;
	}

	public void setMediaGeralDia(Double mediaGeralDia) {
		this.mediaGeralDia = mediaGeralDia;
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
		System.out.println("[ECFVendasPeriodoBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("ecfVendasPeriodoBean");
		}
	}

}
