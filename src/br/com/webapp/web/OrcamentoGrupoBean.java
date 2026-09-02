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

import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFB;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "orcamentoGrupoBean")
@SessionScoped
public class OrcamentoGrupoBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = 4871415123809778600L;

	private Integer menu = MenuAcessoController.GRUPOORCAMENTO_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private OrcamentoGrupoFB selecionada;
	private Integer selecionadaId;
	private List<OrcamentoGrupoFB> lista;
	
	private String descricaoFilter;
	
	private String tituloAdd = "CADASTRAR";
	
	private boolean abrePainelComissaoFaixa = false;

	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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

			lista = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void novo(){
		
		this.selecionada = new OrcamentoGrupoFB();
		
		this.tituloAdd = "CADASTRAR";
		this.lista = null;
		
	}
	
	public void editar(){
		
		this.tituloAdd = "EDITAR";
		
	}
	
	public void eventos(){
		
	}
	
		
	public void limpar(){
		descricaoFilter = null;
		lista = null;
	}
	
	public void excluir() throws DAOException {
		OrcamentoGrupoFBRN orcamentoGrupoFBRN  = new OrcamentoGrupoFBRN();
		orcamentoGrupoFBRN.excluir(selecionadaId);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.excluido.orcamentogrupo")));
		lista = null;
	}
	
	public void salvar() throws RNException{
		
		OrcamentoGrupoFBRN orcamentoGrupoFBRN = new OrcamentoGrupoFBRN();
		
		try {
			
			if(selecionada.getId() != null) {
				orcamentoGrupoFBRN.alterar(selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.editado.orcamentogrupo")));
			}else {
				selecionadaId = orcamentoGrupoFBRN.inserir(selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.orcamentogrupo")));
			}
			this.novo();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			try {
				RequestContext.getCurrentInstance().update("edicaoOrcamentoGrupo");
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

	public OrcamentoGrupoFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(OrcamentoGrupoFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<OrcamentoGrupoFB> getLista() throws RNException {
		if(lista==null){
			lista = new OrcamentoGrupoFBRN().listar(descricaoFilter);
		}	
		return lista;
	}

	public void setLista(List<OrcamentoGrupoFB> lista) {
		this.lista = lista;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public boolean isAbrePainelComissaoFaixa() {
		return abrePainelComissaoFaixa;
	}

	public void setAbrePainelComissaoFaixa(boolean abrePainelComissaoFaixa) {
		this.abrePainelComissaoFaixa = abrePainelComissaoFaixa;
	}

	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
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
		System.out.println("[OrcamentoGrupoBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("orcamentoGrupoBean");
		}
	}

}
