package br.com.webapp.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.menu.MenuAcessoRN;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name="menuHomeView")
@SessionScoped
public class MenuHomeView implements Serializable {
	
	private static final long serialVersionUID = -2582270934437248681L;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private DefaultMenuItem selectedNode;
	private MenuModel model;
	private List<MenuAcesso> menus;
	private List<MenuAcesso> menusUsuario;
	private String menuAcessoIdForDelete;
	private String urlDescricaoHome;
	
    @PostConstruct
    public void init() { }
    
    private List<MenuAcesso> getChildrens(MenuAcesso menuAcesso){
    	List<MenuAcesso> result = new ArrayList<MenuAcesso>();
    	for(MenuAcesso rs : menus){
    		if(rs.getParent()!=null){
	    		if(rs.getParent().equals(menuAcesso)){
	    			result.add(rs);
	    		}
    		}
    	}
    	
    	return result;
    }
    
    private DefaultSubMenu createSubMenu(MenuAcesso menu) {
    	
    	DefaultSubMenu subMenu = new DefaultSubMenu(menu.getDescricao());
    	List<MenuAcesso> childrens = this.getChildrens(menu);
    	if(childrens.size() > 0){
    		for(MenuAcesso rs : childrens){
    			List<MenuAcesso> subChildrens = this.getChildrens(rs);
    			if(subChildrens.size() > 0){
    				DefaultSubMenu novoSubMenu = createSubMenu(rs);
    				novoSubMenu.setExpanded(false);
    				if(novoSubMenu.getElementsCount() > 0){
    					subMenu.addElement(novoSubMenu);
    				}
    			}else{
    				if(verificaPermissaoItem(rs)){
    					subMenu.addElement(createItemMenu(rs, false));
    				}
    			}
    		}
    	}
    	
    	return subMenu;
    }		
    
    private DefaultMenuItem createItemMenu(MenuAcesso menu, boolean favoritos){
    	DefaultMenuItem item = new DefaultMenuItem(menu.getDescricao());
    	item.setHref("javascript:;");
    	item.setOnclick("javascript:openTab("+menu.getId()+", '"+menu.getDescricao()+"', '"+montarUrlMenuItem(menu)+"', '"+menu.getPgm()+"?id="+menu.getId()+"', '1');");
    	if(favoritos){
    		item.setIcon("ui-icon-star");
    		item.setRel("menufavoritos");
    		item.setId(menu.getId().toString());
    		item.setTarget("" + menu.getId());
    	}else{
    		item.setRel("nocontextmenu");
    	}
    	
    	return item;
    }
    
    public boolean verificaPermissaoItem(MenuAcesso menu){
    	for(MenuAcesso rs : menusUsuario){
    		if(rs.getId().intValue() == menu.getId().intValue()){
    			return true;
        	}
    	}
    	return false;
    }
    
    public void updateMenu(){
    	model=null;
    }
    
    public void deleteMenuFavoritos(){
    	FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        
    	if(params.get("menuAcessoIdForDelete") != null){
	        int menuId = Integer.parseInt(params.get("menuAcessoIdForDelete").toString());
	    	MenuAcessoController.removeFavoritos(menuId, ContextoUtil.getContextoBean().getUsuarioLogado());
	    	model=null;
    	}
    }
    
    public MenuModel getModel() {
    	if(model==null){
    		
    		MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
    		menusUsuario = new ArrayList<MenuAcesso>();
    		if(contextoBean.getUsuarioLogado().getUsuarioGrupo()!=null) {
    			menusUsuario.addAll(contextoBean.getUsuarioLogado().getUsuarioGrupo().getMenus()); //menuAcessoRN.listar(contextoBean.getUsuarioLogado().getUsuarioGrupo());
    		}
    		
	    	List<MenuAcesso> menusUsuarioFavoritos = menuAcessoRN.listarFavoritos(contextoBean.getUsuarioLogado());
	    	List<MenuAcesso> menusUsuarioRecentes = menuAcessoRN.listarRecentes(contextoBean.getUsuarioLogado());
	    	
	    	model = new DefaultMenuModel();
	    	
	    	// Menu Favoritos
	    	DefaultSubMenu favoritosSubMenu = new DefaultSubMenu("Favoritos");
	    	favoritosSubMenu.setExpanded(true);
	    	favoritosSubMenu.setStyleClass("uiMenuFavoritos");
	    	if(menusUsuarioFavoritos.size()>0){
	    		for(MenuAcesso rs : menusUsuarioFavoritos){
	    			if(verificaPermissaoItem(rs)){
	    				favoritosSubMenu.addElement(createItemMenu(rs, true));
	    			}
	    		}
	    		
			}
	    	
	    	// Recentes
	    	if(menusUsuarioRecentes.size()>0){
	    		DefaultSeparator defaultSeparator = new DefaultSeparator();
	    		favoritosSubMenu.addElement(defaultSeparator);
	    		
	    		for(MenuAcesso rs : menusUsuarioRecentes){
	    			if(verificaPermissaoItem(rs)){
	    				favoritosSubMenu.addElement(createItemMenu(rs, false));
	    			}
	    		}
	    	}
	    	
	    	model.addElement(favoritosSubMenu);
	    	
	    	// Menu Acesso
	    	menus = menuAcessoRN.listar();
	    	for(MenuAcesso rs : menus){
	    		if(rs.getParent()==null){
		    		DefaultSubMenu novoSubMenu = createSubMenu(rs);
		    		novoSubMenu.setStyleClass("uiMenu uiSubMenu"+rs.getId());
					if(novoSubMenu.getElementsCount() > 0){
						model.addElement(novoSubMenu);
					}
	    		}
	    	}
		}
        return model;
    } 
    
    private String montarUrlMenuItem(MenuAcesso menuAcesso){
    	String varUrlDescricao = menuAcesso.getCaminho() + " / " + menuAcesso.getDescricao();
    	return varUrlDescricao;
    }
    
	public DefaultMenuItem getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(DefaultMenuItem selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<MenuAcesso> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuAcesso> menus) {
		this.menus = menus;
	}

	public List<MenuAcesso> getMenusUsuario() {
		return menusUsuario;
	}

	public void setMenusUsuario(List<MenuAcesso> menusUsuario) {
		this.menusUsuario = menusUsuario;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public String getMenuAcessoIdForDelete() {
		return menuAcessoIdForDelete;
	}

	public void setMenuAcessoIdForDelete(String menuAcessoIdForDelete) {
		this.menuAcessoIdForDelete = menuAcessoIdForDelete;
	}

	public String getUrlDescricaoHome() {
// TODO Implementar 
//		ContextoBean contextoBean = ContextoUtil.getContextoBean();
//		UsuarioRN usuarioRN = new UsuarioRN();
//		Usuario usuarioLogado = usuarioRN.carregar(contextoBean.getUsuarioLogado().getId());
//		if(usuarioLogado != null){
//			urlDescricaoHome = montarUrlMenuItem(usuarioLogado.getHome());
//		}
		return urlDescricaoHome;
	}

	public void setUrlDescricaoHome(String urlDescricaoHome) {
		this.urlDescricaoHome = urlDescricaoHome;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
}
