package br.com.webapp.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.menu.MenuAcessoRN;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.model.usuariogrupo.UsuarioGrupoRN;
import br.com.webapp.web.adapter.MenuAcessoAdapter;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "usuarioGrupoBean")
@SessionScoped
public class UsuarioGrupoBean implements Serializable, IMenuAcesso {
	
	private static final long serialVersionUID = -6823632907679081433L;
	
	@ManagedProperty("#{menuAcessoAdapter}")
    private MenuAcessoAdapter menuAcessoAdapter;
	
	private TreeNode[] treeTableSelected;
	private TreeNode treeTable;

	private List<MenuAcesso> listaMenu;
	private Set<MenuAcesso> permissoes = new HashSet<MenuAcesso>();
	
	private String descricaoFilter;
	private UsuarioGrupo selecionada = new UsuarioGrupo();
	private List<UsuarioGrupo> lista;
	private String tituloAdd = "CADASTRAR";
	
	private boolean botNovo = false;
	private boolean inputInfo = true;
	
	private Integer menu = MenuAcessoController.USUARIOGRUPOBEAN_MENU;
	
	
	@PostConstruct
    public void init() {
		this.treeTable = null;
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
		}
		
    }
	
	public void buscar(){
		lista = null;
	}
	
	public void limpar(){
		descricaoFilter = null;
		lista = null;
	}
	
	public void salvar(){
		
		UsuarioGrupoRN usuarioGrupoRN = new UsuarioGrupoRN();
		
		permissoes = new HashSet<MenuAcesso>();
		for(TreeNode node : treeTable.getChildren()) {
			salvarCheckMenus(node.getChildren());
        }
		
		selecionada.setMenus(permissoes);
		
		this.selecionada = usuarioGrupoRN.salvar(this.selecionada);
		
		if(botNovo){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.editado.grupousuario")));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.cadastrado.grupousuario")));
		}
		
		this.lista = null;
		this.novo();
		
	}
	
	private void salvarCheckMenus(List<TreeNode> list){
		for(TreeNode t : list){
			if(t.getChildCount()>0){
				salvarCheckMenus(t.getChildren());
			}else{
				if(t.isSelected()){
					permissoes.add((MenuAcesso)t.getData());
				}
			}
		}
	}
	
	public void novo(){
		
		this.selecionada = new UsuarioGrupo();
		
		this.botNovo = false;
		this.tituloAdd = "CADASTRAR";
		this.inputInfo = true;
		
		novoUnCheckedTreeNode(treeTable.getChildren());
		
	}
	
	public void novoUnCheckedTreeNode(List<TreeNode> list){
		for(TreeNode node : list) {
			if(node.getChildCount()>0){
				node.setSelected(false);
				novoUnCheckedTreeNode(node.getChildren());
			}else if (node.isSelected()){
				node.setSelected(false);
			}
        }
	}
	
	public void excluir(){
		
		try{
			UsuarioGrupoRN usuarioGrupoRN = new UsuarioGrupoRN();
			usuarioGrupoRN.excluir(this.selecionada);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.excluido.grupousuario")));
			
			this.novo();
			this.lista = null;
			
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, UtilMessage.mensagem("msg.erro.excluido.grupousuario")));
		}
		
	}
	
	public void editar(){
		
		this.tituloAdd = "EDITAR";
		this.botNovo = true;
		
		for(TreeNode t : this.treeTable.getChildren()){
			//t.setSelected(menuAcessoAdapter.verifyChecked(selecionada.getMenus(), (MenuAcesso)t.getData()));
			if(t.getChildCount()>0){
				editarCheckMenus(t.getChildren());
			}
		}
		
	}
	
	private void editarCheckMenus(List<TreeNode> list){
		for(TreeNode t : list){
			t.setSelected(menuAcessoAdapter.verifyChecked(selecionada.getMenus(), (MenuAcesso)t.getData()));
			if(t.getChildCount()>0){
				editarCheckMenus(t.getChildren());
			}
		}
	}
	
	public void onNodeSelect(NodeSelectEvent event){
		event.getTreeNode().setSelected(true);
	}
	
	public void onNodeUnselect(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
    }
	
	public void onNodeMenuAppSelect(NodeSelectEvent event){
		event.getTreeNode().setSelected(true);
	}
	
	public void onNodeMenuAppUnselect(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
    }

	public UsuarioGrupo getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(UsuarioGrupo selecionada) {
		this.selecionada = selecionada;
	}

	public List<UsuarioGrupo> getLista() {
		
		if(this.lista == null){
			UsuarioGrupoRN usuarioGrupoRN = new UsuarioGrupoRN();
			this.lista = usuarioGrupoRN.listar(descricaoFilter);
		}
		
		return lista;
	}

	public void setLista(List<UsuarioGrupo> lista) {
		this.lista = lista;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}
	
	public boolean isBotNovo() {
		return botNovo;
	}

	public void setBotNovo(boolean botNovo) {
		this.botNovo = botNovo;
	}

	public boolean isInputInfo() {
		return inputInfo;
	}

	public void setInputInfo(boolean inputInfo) {
		this.inputInfo = inputInfo;
	}

	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
	}
	
	public TreeNode getTreeTable() {
		if(this.listaMenu == null){
			MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
			this.listaMenu = menuAcessoRN.listar();
			this.treeTable = menuAcessoAdapter.createCheckbox(listaMenu);
		}
		return treeTable;
	}
	
	public void setTreeTable(TreeNode treeTable) {
		this.treeTable = treeTable;
	}
	
	public TreeNode[] getTreeTableSelected() {
		return treeTableSelected;
	}

	public void setTreeTableSelected(TreeNode[] treeTableSelected) {
		this.treeTableSelected = treeTableSelected;
	}
	
	public List<MenuAcesso> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<MenuAcesso> listaMenu) {
		this.listaMenu = listaMenu;
	}
	
	public MenuAcessoAdapter getMenuAcessoAdapter() {
		return menuAcessoAdapter;
	}

	public void setMenuAcessoAdapter(MenuAcessoAdapter menuAcessoAdapter) {
		this.menuAcessoAdapter = menuAcessoAdapter;
	}

	public Set<MenuAcesso> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<MenuAcesso> permissoes) {
		this.permissoes = permissoes;
	}
	
	public int getMenu() {
		return menu;
	}

	public void setMenu(int menu) {
		this.menu = menu;
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
		      session.removeAttribute("usuarioGrupoBean");
		      session.removeAttribute("menuAcessoAdapter");
		      session.removeAttribute("menuAppAdapter");
		  }
	}

}