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
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFB;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "linhaProdutoMetaBean")
@SessionScoped
public class LinhaProdutoMetaBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = 3883538892996892636L;

	private Integer menu = MenuAcessoController.LINHAPRODUTOMETA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private LinhaProdutoMetaFB selecionada;
	private Integer selecionadaId;
	private List<LinhaProdutoMetaFB> lista;
	private List<LinhaProdutoMetaFB> listaItens;
	private LazyDataModel<LinhaProdutoMetaFB> listaProdutosLDM;
	
	private String descEmpresa;
	private String anomesFilter;
	private String anoMes;
	private String anoMesRef;
	private String aplicarTipo;
	private String atribuirTipo;
	
	private Double percAplicado;
	private Double percAtribuido;
	private Double totalPrevAnt;
	private Double totalPrevAtual;	
	private Double totalPercPrevAtual;
	private Double totalPercPrevFat;
	private Double totalPercPrevFatAnt;
	private Double totalRealAnt;
	
	private ProdutoLinhaFB produtoLinhaFilter;
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	private boolean incluirDevolucao;

	private EventoFinanceiroFB eventoFinanceiroFilter;
	private Double valor;

	private int currentTab;	
	private List<SelectItem> empresaSelect;
	private List<SelectItem> vendedorSelect;
	
	private String tituloAdd = "";
	
	private boolean abrePainelComissaoFaixa = false;

	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		descEmpresa = null;
		percAplicado = null;
		percAtribuido = null;		
		currentTab = 0;
		aplicarTipo = "1";
		atribuirTipo = "0";
		//valor = 0.0;
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
			percAtribuido = null;
			valor = null;
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			Integer vendedorId = null;
			if(vendedorFilter != null) {
				vendedorId = vendedorFilter.getId();
			}
			
			Integer nivelLinhaProduto = empresaFilter.getNivelLinhaProduto();
			System.out.println("NivelLinhaProduto Definir: "+empresaFilter.getNivelLinhaProduto());
			
			listaItens = new LinhaProdutoMetaFBRN().listar(anoMes, anoMesRef, empresaId, vendedorId, nivelLinhaProduto, incluirDevolucao);

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
		totalPercPrevFatAnt = 0.0;
		totalRealAnt = 0.0;
		
		for(LinhaProdutoMetaFB rs : listaItens) {
			if(rs.getValorPrevAnt()!=null) {
				totalPrevAnt += rs.getValorPrevAnt();
			}
			if(rs.getValor()!=null) {
				totalPrevAtual += rs.getValor();
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
		
		if(valor!=null && totalPrevAtual!=null){
			if(valor>0){
				totalPercPrevFat = (valor/totalPrevAtual)*100;
			}
		}
		
		
		if(totalRealAnt!=null && totalPrevAnt!=null) {
			if(totalPrevAnt>0) {
				totalPercPrevFatAnt = (totalRealAnt/totalPrevAnt)*100;
			}
		}
		
		
	}
	
	public void aplicar() {
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			Integer vendedorId = null;
			if(vendedorFilter != null) {
				vendedorId = vendedorFilter.getId();
			}
			
			Integer nivelLinhaProduto = empresaFilter.getNivelLinhaProduto();

			//listaItens = new LinhaProdutoMetaFBRN().listar(anoMes, anoMesRef, empresaId, vendedorId, nivelLinhaProduto);
			for(LinhaProdutoMetaFB rs : listaItens) {
				if(percAplicado!=null){
					//if(percAplicado>0){
						// aplicar sobre o faturamento
						if("1".equals(aplicarTipo)) {
							if(rs.getValorRealAnt()!=null) {
								if(rs.getValorRealAnt()>0) {
									rs.setValor(rs.getValorRealAnt()*(1+(percAplicado/100)));
								}
							}	
						// aplicar sobre o previsto
						}else {
							if(rs.getValorPrevAnt()!=null) {
								if(rs.getValorPrevAnt()>0) {
									rs.setValor(rs.getValorPrevAnt()*(1+(percAplicado/100)));
								}
							}		
						}	
					//}
				}	
				
			}
			
			
			totalizador();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void atribuir() {
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			Integer vendedorId = null;
			if(vendedorFilter != null) {
				vendedorId = vendedorFilter.getId();
			}
			
			Integer nivelLinhaProduto = empresaFilter.getNivelLinhaProduto();

			//listaItens = new LinhaProdutoMetaFBRN().listar(anoMes, anoMesRef, empresaId, vendedorId, nivelLinhaProduto);
			for(LinhaProdutoMetaFB rs : listaItens) {
				if(percAtribuido!=null){
					if(percAtribuido>0){
						// aplicar sobre o faturamento
						if("0".equals(atribuirTipo)) {
							rs.setPercPositivacao(percAtribuido);
						// aplicar sobre o previsto
						}else {
							rs.setMixProduto(percAtribuido);
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
		
		this.selecionada = new LinhaProdutoMetaFB();
		empresaFilter = null;
		vendedorFilter = null;
		incluirDevolucao = false;
		
		this.tituloAdd = "";
	}
	
	
		
	public void limpar(){
		anoMes = null;
		anoMesRef = null;
		empresaFilter = null;
		vendedorFilter = null;
		descEmpresa = null;
		listaItens = null;
		selecionada = null;
		percAplicado = null;
		percAtribuido = null;
		valor = null;
		totalPrevAnt = 0.0;
		totalPrevAtual = 0.0;
		totalPercPrevAtual = 0.0;
		totalPercPrevAtual = 0.0;
		tituloAdd = "";
		incluirDevolucao = false;
		
	}
	
	public void salvar() throws RNException{
		
		LinhaProdutoMetaFBRN linhaProdutoMetaFBRN = new LinhaProdutoMetaFBRN();
		
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			Integer vendedorId = null;
			if(vendedorFilter != null) {
				vendedorId = vendedorFilter.getId();
			}
			
			
			if(listaItens != null) {
				for(LinhaProdutoMetaFB rs : listaItens) {
					rs.setAnoMes(anoMes);
					rs.setIdPessoaEmp(empresaId);
					rs.setIdVendedor(vendedorId);

					if(rs.getId()!=null) {
						linhaProdutoMetaFBRN.alterar(rs);
						tituloAdd = "Alterou";
					}else {
						linhaProdutoMetaFBRN.inserir(rs);
						tituloAdd = "Cadastrou";
					}	
				}
			}
			
			if("Alterou".equals(tituloAdd)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.editado.linhaprodutometa")));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.salvo.linhaprodutometa")));
			}	

			this.buscar();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));		
			e.printStackTrace();
		}
		//lista = null;
		
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public LinhaProdutoMetaFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(LinhaProdutoMetaFB selecionada) {
		this.selecionada = selecionada;
	}

	public VendedorFB getVendedorFilter() {
		return vendedorFilter;
	}

	public void setVendedorFilter(VendedorFB vendedorFilter) {
		this.vendedorFilter = vendedorFilter;
	}
	
	public boolean isIncluirDevolucao() {
		return incluirDevolucao;
	}

	public void setIncluirDevolucao(boolean incluirDevolucao) {
		this.incluirDevolucao = incluirDevolucao;
	}

	public List<LinhaProdutoMetaFB> getLista() throws RNException {
		if(lista==null){
			Integer empresaId = null;

			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			lista = new LinhaProdutoMetaFBRN().listar(anoMes, empresaId);
		}	
		return lista;
	}

	public void setLista(List<LinhaProdutoMetaFB> lista) {
		this.lista = lista;
	}

	public LazyDataModel<LinhaProdutoMetaFB> getListaProdutosLDM() {
		return listaProdutosLDM;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public void setListaProdutosLDM(LazyDataModel<LinhaProdutoMetaFB> listaProdutosLDM) {
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
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

	public List<LinhaProdutoMetaFB> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<LinhaProdutoMetaFB> listaItens) {
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

	public Double getPercAtribuido() {
		return percAtribuido;
	}

	public void setPercAtribuido(Double percAtribuido) {
		this.percAtribuido = percAtribuido;
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

	public String getAplicarTipo() {
		return aplicarTipo;
	}

	public void setAplicarTipo(String aplicarTipo) {
		this.aplicarTipo = aplicarTipo;
	}

	public String getAtribuirTipo() {
		return atribuirTipo;
	}

	public void setAtribuirTipo(String atribuirTipo) {
		this.atribuirTipo = atribuirTipo;
	}

	public Double getTotalPercPrevFatAnt() {
		return totalPercPrevFatAnt;
	}

	public void setTotalPercPrevFatAnt(Double totalPercPrevFatAnt) {
		this.totalPercPrevFatAnt = totalPercPrevFatAnt;
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

	public List<SelectItem> getVendedorSelect() {
		if (this.vendedorSelect == null) {
			this.vendedorSelect = new ArrayList<SelectItem>();
			VendedorFBRN vendedorFBRN = new VendedorFBRN();
			this.vendedorSelect = vendedorFBRN.montaDadosSelect(new VendedorFBRN().listar(""), "");
		}
		
		return vendedorSelect;
	}

	public void setVendedorSelect(List<SelectItem> vendedorSelect) {
		this.vendedorSelect = vendedorSelect;
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
		System.out.println("[LinhaProdutoMetaBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("linhaProdutoMetaBean");
		}
	}

}
