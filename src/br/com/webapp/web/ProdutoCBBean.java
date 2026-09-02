package br.com.webapp.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.produtocb.ProdutoCBFB;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBId;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="produtoCBBean")
@SessionScoped
public class ProdutoCBBean implements Serializable, IMenuAcesso {
	
	private static final long serialVersionUID = 2269055631511998748L;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private ProdutoLinhaFB produtoLinhaFilter;
	private ProdutoFB produtoFB;
	private String produtoFilter;
	private String codigoBarraFilter;
	
	
	private String codigoBarras;
	private ProdutoCBFB selecionada = new ProdutoCBFB();
	private List<ProdutoCBFB> lista;
	private List<SelectItem> produtoLinhasSelect;
	// private List<SelectItem> unidadesSelect;
	
	private String tituloAdd = "CADASTRAR";
	private boolean editando = false;
	private boolean filterProduto;
	
	
	private Integer menu = MenuAcessoController.PRODUTOCBBEAN_MENU;
	
	@PostConstruct
	public void init(){
		this.novo();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
			lista = new ArrayList<ProdutoCBFB>();
		}
	}
	
	public void buscar(){
		ProdutoCBFBRN produtoCBFBRN = new ProdutoCBFBRN();
		lista = produtoCBFBRN.listar(produtoLinhaFilter, produtoFilter, codigoBarraFilter);
	}
	
	public void limpar(){
		produtoLinhaFilter = null;
		produtoFilter = null;
		codigoBarraFilter = null;
		
		buscar();
	}
	
	public void salvar(){
		ProdutoCBFBRN produtoCBFBRN = new ProdutoCBFBRN();
		try {
			
			selecionada.setUsuarioUpdateId(contextoBean.getUsuarioLogado().getId());
			selecionada.setDtUpdate(new Date());
			
			if(editando){
				produtoCBFBRN.salvar(this.selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.editado.produtocb")));
			}else{
				ProdutoCBFBId produtoCBFBId = new ProdutoCBFBId(produtoFB.getId(), codigoBarras); 
				selecionada.setId(produtoCBFBId);

				selecionada.setUsuarioCreateId(contextoBean.getUsuarioLogado().getId());
				selecionada.setDtCreate(new Date());
				
				produtoCBFBRN.salvar(this.selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.produtocb")));
			}
			
			this.lista = null;
			this.novo();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}finally {
			buscar();
		} 
		
	}
	
	public void novo(){
		
		this.produtoFB = null;
		this.codigoBarras = null;
		this.selecionada = new ProdutoCBFBRN().novo(contextoBean.getUsuarioLogado(), null);
		
		this.editando = false;
		this.filterProduto = false;
		this.tituloAdd = "CADASTRAR";
		this.lista = null;
		buscar();
		
	}
	
	public void excluir(){
		
		ProdutoCBFBRN produtoCBRN = new ProdutoCBFBRN();
		try {
			selecionada.setUsuarioUpdateId(contextoBean.getUsuarioLogado().getId());
			produtoCBRN.excluir(this.selecionada);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.excluido.produtocb")));
		} catch (DAOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}
		
		this.novo();
		
	}
	
	public void editar(){
		this.codigoBarras = null;
		this.tituloAdd = "EDITAR";
		this.editando = true;
	}
	
	public void verCodigoBarras(){
		
		ProdutoCBFBRN produtoCBRN = new ProdutoCBFBRN();
		try {
			
			if(codigoBarras != null && !"".equals(codigoBarras)){
				System.out.println("[ProdutoCBBean][verCodigoBarras]["+codigoBarras+"]");
				
				codigoBarras = codigoBarras.trim();
				codigoBarras = codigoBarras.replaceAll("[^A-Za-z0-9]","");
				
				this.selecionada = produtoCBRN.carregar(codigoBarras);
				if(this.selecionada!=null){
					
					if(this.selecionada.getId().getProdutoId() != null){
						System.out.println("[ProdutoCBFBBean][verCodigoBarras][Produto]["+this.selecionada.getId().getProdutoId()+"]");
						ProdutoFB produtoFB = new ProdutoFBRN().carregar(this.selecionada.getId().getProdutoId());
						if(produtoFB.getUnidadeDesc()!=null){
							System.out.println("[ProdutoCBBean][verCodigoBarras][Unidade]["+produtoFB.getUnidadeDesc()+"]");
						}
					}
					
					filterProduto = false;
					editando = true;
					tituloAdd = "EDITAR";
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.produto_ja_existe.produtocb")));
					
				}else{
					selecionada = produtoCBRN.novo(contextoBean.getUsuarioLogado(), codigoBarras);
					filterProduto = true;
					tituloAdd = "CADASTRAR";
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, UtilMessage.mensagem("msg.verificar_codigo_vazio.produtocb")));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
		}
		
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public ProdutoLinhaFB getProdutoLinhaFilter() {
		return produtoLinhaFilter;
	}

	public void setProdutoLinhaFilter(ProdutoLinhaFB produtoLinhaFilter) {
		this.produtoLinhaFilter = produtoLinhaFilter;
	}

	public ProdutoFB getProdutoFB() {
		return produtoFB;
	}

	public void setProdutoFB(ProdutoFB produtoFB) {
		this.produtoFB = produtoFB;
	}

	public String getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(String produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public String getCodigoBarraFilter() {
		return codigoBarraFilter;
	}

	public void setCodigoBarraFilter(String codigoBarraFilter) {
		this.codigoBarraFilter = codigoBarraFilter;
	}
	
	public List<SelectItem> getProdutoLinhasSelect() {
		if (this.produtoLinhasSelect == null) {
			this.produtoLinhasSelect = new ArrayList<SelectItem>();
			ProdutoLinhaFBRN produtoLinhaFBRN = new ProdutoLinhaFBRN();
			this.produtoLinhasSelect = produtoLinhaFBRN.montaDadosSelect(new ProdutoLinhaFBRN().listar(), "");
		}
		
		return produtoLinhasSelect;
	}

	public ProdutoCBFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ProdutoCBFB selecionada) {
		this.selecionada = selecionada;
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public List<ProdutoCBFB> getLista() {
		return lista;
	}

	public void setLista(List<ProdutoCBFB> lista) {
		this.lista = lista;
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
	
	public boolean isFilterProduto() {
		return filterProduto;
	}

	public void setFilterProduto(boolean filterProduto) {
		this.filterProduto = filterProduto;
	}
	
	public List<ProdutoFB> completeProduto(String descricaoFilter) {
		return new ProdutoFBRN().listar(descricaoFilter);
    }
	
	public void openFilterProduto(){
		this.filterProduto = true;
		this.produtoFB = null;
	}
	
	public void closeFilterProduto(){
		this.filterProduto = false;
		if(selecionada!= null && produtoFB!=null){
			selecionada.getId().setProdutoId(produtoFB.getId());
		}
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
		  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		  if(request.getParameter("id")!=null){
			  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		      session.removeAttribute("produtoCBBean");
		  }
	}

}
