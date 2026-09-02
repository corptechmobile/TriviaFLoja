package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

import org.primefaces.model.LazyDataModel;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFB;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFB;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFBRN;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFB;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "orcamentoMetaBean")
@SessionScoped
public class OrcamentoMetaBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = 2984110114197543968L;

	private Integer menu = MenuAcessoController.ORCAMENTOMETA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private OrcamentoMetaFB selecionada;
	private Integer selecionadaId;
	private List<OrcamentoMetaFB> lista;
	private List<OrcamentoMetaItemFB> listaItens;
	private LazyDataModel<OrcamentoMetaFB> listaProdutosLDM;
	
	private String descEmpresa;
	private String descEventoFinanceiro;
	private String anomesFilter;
	private String anoMes;
	private String anoMesRef;
	private Double percAplicado;
	private Double totalPrevAnt;
	private Double totalPrevAtual;	
	private Double totalPercPrevAtual;
	private Double totalPercPrevFat;
	private Double totalRealAnt;
	
	private ProdutoLinhaFB produtoLinhaFilter;
	private EmpresaFB empresaFilter;
	private EventoFinanceiroFB eventoFinanceiroFilter;
	private Double valorOrcado;

	private int currentTab;	
	private List<SelectItem> empresaSelect;
	
	private String tituloAdd = "CADASTRAR";
	
	private boolean abrePainelComissaoFaixa = false;

	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		descEmpresa = null;
		descEventoFinanceiro = null;
		percAplicado = null;
		currentTab = 0;
		//valorOrcado = 0.0;
		novo();
		
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
			listaItens = null;
			percAplicado = null;
			valorOrcado = null;
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			selecionada = new OrcamentoMetaFBRN().carregar(anoMes, empresaId);
			if(selecionada == null) {
				selecionada = new OrcamentoMetaFB();
				if(valorOrcado!= null) {
					if(valorOrcado>0) {
						selecionada.setValorPrevFat(valorOrcado);
					}else {
						selecionada.setValorPrevFat(0.0);
					}
				}else {
					valorOrcado = 0.0;
				}
			}else {
				valorOrcado = selecionada.getValorPrevFat();
			}
			
			
			listaItens = new OrcamentoMetaItemFBRN().listar(anoMes, anoMesRef, empresaId);

			for(OrcamentoMetaItemFB rs : listaItens) {
				rs.setPrevFaturamento(0.0);
				if(rs.getPercFaturamento()!=null) {
					if(rs.getPercFaturamento()>0) {
						if(selecionada.getValorPrevFat()!=null) {
							rs.setValorOrcado(selecionada.getValorPrevFat()*(rs.getPercFaturamento()/100));
						}
						if(rs.getValorOrcado()!= null){
							rs.setPrevFaturamento(rs.getValorOrcado()/(rs.getPercFaturamento()/100));
						}	
					}
				}
			}
				
			totalizador();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public void totalizador() {
		
		totalPrevAnt = 0.0;
		totalPrevAtual = 0.0;
		totalPercPrevAtual = 0.0;
		totalPercPrevFat = 0.0;
		totalRealAnt = 0.0;
		
		for(OrcamentoMetaItemFB rs : listaItens) {
			if(rs.getValorPrevAnt()!=null) {
				totalPrevAnt += rs.getValorPrevAnt();
			}
			if(rs.getValorOrcado()!=null) {
				totalPrevAtual += rs.getValorOrcado();
			}
			
			if(rs.getValorRealAnt()!=null) {
				totalRealAnt += rs.getValorRealAnt();
			}
		}
		
		if(totalPrevAtual!=null && totalPrevAnt!=null) {
			if(totalPrevAtual>0) {
				totalPercPrevAtual = (totalPrevAtual/totalPrevAnt)*100;
			}
		}
		
		if(valorOrcado!=null && totalPrevAtual!=null){
			if(valorOrcado>0){
				totalPercPrevFat = (valorOrcado/totalPrevAtual)*100;
			}
		}
	}
	
	public void aplicar() {
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}

			selecionada = new OrcamentoMetaFBRN().carregar(anoMes, empresaId);
			if(selecionada == null) {
				selecionada = new OrcamentoMetaFB();
				if(valorOrcado>0) {
					selecionada.setValorPrevFat(valorOrcado);
				}else {
					selecionada.setValorPrevFat(0.0);
				}	
			}else {
				selecionada.setValorPrevFat(valorOrcado);
			}

			
			listaItens = new OrcamentoMetaItemFBRN().listar(anoMes, anoMesRef, empresaId);
			for(OrcamentoMetaItemFB rs : listaItens) {
				rs.setPrevFaturamento(0.0);
				if(rs.getPercFaturamento()>0) {
					rs.setValorOrcado(selecionada.getValorPrevFat()*(rs.getPercFaturamento()/100));
					rs.setPrevFaturamento(rs.getValorOrcado()/(rs.getPercFaturamento()/100));
				}else {
					if(percAplicado!=null && percAplicado!=0.0) {
						rs.setValorOrcado(rs.getValorPrevAnt()*(1+percAplicado/100));
						if(rs.getValorOrcado()>0) {
							rs.setPercPrevRealAtual((rs.getValorOrcado()/rs.getValorPrevAnt())*100);
						}
						
					}
				}
				
			}
			
			totalizador();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void novo(){
		
		this.selecionada = new OrcamentoMetaFB();
		empresaFilter = null;
		eventoFinanceiroFilter = null;
		
		this.tituloAdd = "CADASTRAR";
		this.lista = null;
		
	}
	
	
		
	public void limpar(){
		anoMes = null;
		anoMesRef = null;
		empresaFilter = null;
		descEmpresa = null;
		listaItens = null;
		selecionada = null;
		percAplicado = null;
		valorOrcado = null;
		totalPrevAnt = 0.0;
		totalPrevAtual = 0.0;
		totalPercPrevAtual = 0.0;
		totalPercPrevAtual = 0.0;
		
	}
	
	public void salvar() throws RNException{
		
		OrcamentoMetaFBRN orcamentoMetaFBRN = new OrcamentoMetaFBRN();
		OrcamentoMetaItemFBRN orcamentoMetaItemFBRN = new OrcamentoMetaItemFBRN();
		
		try {
			
			
			if(selecionada.getId() != null) {
				orcamentoMetaFBRN.alterar(selecionada);
				for(OrcamentoMetaItemFB rs : listaItens) {
					if(rs.getId()!=null) {
						orcamentoMetaItemFBRN.alterar(rs);
					}else {
						rs.setIdOrcamentoMeta(selecionada.getId());
						orcamentoMetaItemFBRN.inserir(rs);
					}	
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.editado.orcamentometa")));
			}else {
				selecionada.setAnoMes(anoMes);
				selecionada.setIdPessoaEmp(empresaFilter.getId());
				selecionadaId = orcamentoMetaFBRN.inserir(selecionada);
				selecionada = new OrcamentoMetaFBRN().carregar(anoMes, empresaFilter.getId());
				for(OrcamentoMetaItemFB rs : listaItens) {
					if(rs.getId()!=null) {
						orcamentoMetaItemFBRN.alterar(rs);
					}else {
						rs.setIdOrcamentoMeta(selecionada.getId());
						orcamentoMetaItemFBRN.inserir(rs);
					}	
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.salvo.orcamentometa")));
			}
			this.buscar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			try {
//				RequestContext.getCurrentInstance().update("edicaoComissaoFaixa");
				listaItens = null;
			} catch (Exception ex) {
//				ex.printStackTrace();
			}			
			e.printStackTrace();
		}
		//lista = null;
		
	}
	
	public void ajustar() throws RNException{
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}

			selecionada = new OrcamentoMetaFBRN().carregar(anoMes, empresaId);
			if(selecionada == null) {
				selecionada = new OrcamentoMetaFB();
				if(valorOrcado>0) {
					selecionada.setValorPrevFat(valorOrcado);
				}else {
					selecionada.setValorPrevFat(0.0);
				}	
			}else {
				selecionada.setValorPrevFat(valorOrcado);
			}

			
			listaItens = new OrcamentoMetaItemFBRN().listar(anoMes, anoMesRef, empresaId);
			for(OrcamentoMetaItemFB rs : listaItens) {
				rs.setPrevFaturamento(0.0);
				if(rs.getPercFaturamento()>0) {
					rs.setValorOrcado(selecionada.getValorPrevFat()*(rs.getPercFaturamento()/100));
					rs.setPrevFaturamento(rs.getValorOrcado()/(rs.getPercFaturamento()/100));
				}else {
					rs.setValorOrcado(rs.getValorOrcado()+(rs.getValorPrevAnt()-rs.getValorRealAnt()));
					if(rs.getValorOrcado()>0) {
						rs.setPercPrevRealAtual((rs.getValorOrcado()/rs.getValorPrevAnt())*100);
					}
				}
				
			}
			
			totalizador();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public OrcamentoMetaFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(OrcamentoMetaFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<OrcamentoMetaFB> getLista() throws RNException {
		if(lista==null){
			Integer empresaId = null;

			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			lista = new OrcamentoMetaFBRN().listar(anoMes, empresaId);
		}	
		return lista;
	}

	public void setLista(List<OrcamentoMetaFB> lista) {
		this.lista = lista;
	}

	public LazyDataModel<OrcamentoMetaFB> getListaProdutosLDM() {
		return listaProdutosLDM;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public void setListaProdutosLDM(LazyDataModel<OrcamentoMetaFB> listaProdutosLDM) {
		this.listaProdutosLDM = listaProdutosLDM;
	}
	
	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public ProdutoLinhaFB getProdutoLinhaFilter() {
		return produtoLinhaFilter;
	}

	public void setProdutoLinhaFilter(ProdutoLinhaFB produtoLinhaFilter) {
		this.produtoLinhaFilter = produtoLinhaFilter;
	}

	public boolean isAbrePainelComissaoFaixa() {
		return abrePainelComissaoFaixa;
	}

	public void setAbrePainelComissaoFaixa(boolean abrePainelComissaoFaixa) {
		this.abrePainelComissaoFaixa = abrePainelComissaoFaixa;
	}

	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public String getDescEventoFinanceiro() {
		return descEventoFinanceiro;
	}

	public void setDescEventoFinanceiro(String descEventoFinanceiro) {
		this.descEventoFinanceiro = descEventoFinanceiro;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public EventoFinanceiroFB getEventoFinanceiroFilter() {
		return eventoFinanceiroFilter;
	}

	public void setEventoFinanceiroFilter(EventoFinanceiroFB eventoFinanceiroFilter) {
		this.eventoFinanceiroFilter = eventoFinanceiroFilter;
	}

	public Double getValorOrcado() {
		return valorOrcado;
	}

	public void setValorOrcado(Double valorOrcado) {
		this.valorOrcado = valorOrcado;
	}

	public String getAnomesFilter() {
		return anomesFilter;
	}

	public void setAnomesFilter(String anomesFilter) {
		this.anomesFilter = anomesFilter;
	}

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public List<OrcamentoMetaItemFB> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<OrcamentoMetaItemFB> listaItens) {
		this.listaItens = listaItens;
	}

	public String getAnoMesRef() {
		return anoMesRef;
	}

	public void setAnoMesRef(String anoMesRef) {
		this.anoMesRef = anoMesRef;
	}

	public Double getPercAplicado() {
		return percAplicado;
	}

	public void setPercAplicado(Double percAplicado) {
		this.percAplicado = percAplicado;
	}

	public Double getTotalPrevAnt() {
		return totalPrevAnt;
	}

	public void setTotalPrevAnt(Double totalPrevAnt) {
		this.totalPrevAnt = totalPrevAnt;
	}

	public Double getTotalPrevAtual() {
		return totalPrevAtual;
	}

	public void setTotalPrevAtual(Double totalPrevAtual) {
		this.totalPrevAtual = totalPrevAtual;
	}

	public Double getTotalPercPrevAtual() {
		return totalPercPrevAtual;
	}

	public void setTotalPercPrevAtual(Double totalPercPrevAtual) {
		this.totalPercPrevAtual = totalPercPrevAtual;
	}

	public Double getTotalPercPrevFat() {
		return totalPercPrevFat;
	}

	public void setTotalPercPrevFat(Double totalPercPrevFat) {
		this.totalPercPrevFat = totalPercPrevFat;
	}

	public Double getTotalRealAnt() {
		return totalRealAnt;
	}

	public void setTotalRealAnt(Double totalRealAnt) {
		this.totalRealAnt = totalRealAnt;
	}

	public List<SelectItem> getEmpresaSelect() {
		if (this.empresaSelect == null) {
			this.empresaSelect = new ArrayList<SelectItem>();
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			this.empresaSelect = empresaFBRN.montaDadosSelect(new EmpresaFBRN().listar(), "");
		}
		
		return empresaSelect;
	}

	public void setEmpresaSelect(List<SelectItem> empresaSelect) {
		this.empresaSelect = empresaSelect;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
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
		System.out.println("[OrcamentoMetaBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("orcamentoMetaBean");
		}
	}

}
