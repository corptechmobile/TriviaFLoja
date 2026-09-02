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

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFB;
import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "comissaoFaixaDescBean")
@SessionScoped
public class ComissaoFaixaDescBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = -8864969787932467151L;
	private Integer menu = MenuAcessoController.COMISSAOFAIXADESC_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private ComissaoFaixaDescFB selecionada;
	private Integer selecionadaId;
	private List<ComissaoFaixaDescFB> lista;
	private LazyDataModel<ComissaoFaixaDescFB> listaProdutosLDM;
	
	private String descLinhaProd;
	private ProdutoLinhaFB produtoLinhaFilter;
	private Double descfaixa1;
	private Double descfaixa2;
	
	private List<SelectItem> linhaProdutoSelect;
	
	private String tituloAdd = "CADASTRAR";
	
	private boolean abrePainelComissaoFaixa = false;

	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		descLinhaProd = null;
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

			if(descfaixa1 == 0.0) {
				descfaixa1 = null;
			}
			if(descfaixa2 == 0.0) {
				descfaixa2 = null;
			}

			lista = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void novo(){
		
		this.selecionada = new ComissaoFaixaDescFB();
		produtoLinhaFilter = null;
		
		this.tituloAdd = "CADASTRAR";
		this.lista = null;
		
	}
	
	public void editar(){
		
		this.tituloAdd = "EDITAR";
		if(selecionada!=null) {
			produtoLinhaFilter = new ProdutoLinhaFBRN().carregar(selecionada.getProdutoLinhaId());
		}
		
	}
	
		
	public void limpar(){
		produtoLinhaFilter = null;
		descLinhaProd = null;
		lista = null;
		descfaixa1 = null;
		descfaixa2 = null;
	}
	
	public void excluir() {
		ComissaoFaixaDescFBRN comissaoFaixaDescFBRN = new ComissaoFaixaDescFBRN();
		try {
			comissaoFaixaDescFBRN.excluir(selecionadaId);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.excluido.comissaofaixa")));
			lista = null;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void salvar() throws RNException{
		
		ComissaoFaixaDescFBRN comissaoFaixaDescFBRN = new ComissaoFaixaDescFBRN();
		selecionada.setProdutoLinhaId(produtoLinhaFilter.getId());
		
		try {
			
			if(selecionada.getPercComissao()<0) {
				throw new RNException(UtilMessage.mensagem("msg.erro.comissaofaixa.negativo"));
			}

			if (selecionada.getFaixaDesc2() < selecionada.getFaixaDesc1()) {
				throw new RNException(UtilMessage.mensagem("msg.erro.faixafinalinvalida.comissaofaixa")); 
			}
			if(selecionada.getId() != null) {
				comissaoFaixaDescFBRN.alterar(selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.editado.comissaofaixa")));
			}else {
				selecionadaId = comissaoFaixaDescFBRN.inserir(selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.comissaofaixa")));
			}
			this.novo();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			try {
				RequestContext.getCurrentInstance().update("edicaoComissaoFaixa");
				lista = null;
			} catch (Exception ex) {
//				ex.printStackTrace();
			}			
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

	public ComissaoFaixaDescFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ComissaoFaixaDescFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<ComissaoFaixaDescFB> getLista() throws RNException {
		if(lista==null){
			lista = new ComissaoFaixaDescFBRN().listar(descLinhaProd, descfaixa1, descfaixa2);
		}	
		return lista;
	}

	public void setLista(List<ComissaoFaixaDescFB> lista) {
		this.lista = lista;
	}

	public String getDescLinhaProd() {
		return descLinhaProd;
	}

	public void setDescLinhaProd(String descLinhaProd) {
		this.descLinhaProd = descLinhaProd;
	}

	public Double getDescfaixa1() {
		return descfaixa1;
	}

	public void setDescfaixa1(Double descfaixa1) {
		this.descfaixa1 = descfaixa1;
	}

	public Double getDescfaixa2() {
		return descfaixa2;
	}

	public void setDescfaixa2(Double descfaixa2) {
		this.descfaixa2 = descfaixa2;
	}

	public LazyDataModel<ComissaoFaixaDescFB> getListaProdutosLDM() {
		return listaProdutosLDM;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public void setListaProdutosLDM(LazyDataModel<ComissaoFaixaDescFB> listaProdutosLDM) {
		this.listaProdutosLDM = listaProdutosLDM;
	}
	
	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public List<SelectItem> getLinhaProdutoSelect() {
		if (this.linhaProdutoSelect == null) {
			this.linhaProdutoSelect = new ArrayList<SelectItem>();
			ProdutoLinhaFBRN produtoLinhaFBRN = new ProdutoLinhaFBRN();
			this.linhaProdutoSelect = produtoLinhaFBRN.montaDadosSelect(new ProdutoLinhaFBRN().listar(), "");
		}
		
		return linhaProdutoSelect;
	}

	public void setLinhaProdutoSelect(List<SelectItem> linhaProdutoSelect) {
		this.linhaProdutoSelect = linhaProdutoSelect;
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
		System.out.println("[ComissaoFaixaDescBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("comissaoFaixaDescBean");
		}
	}

}
