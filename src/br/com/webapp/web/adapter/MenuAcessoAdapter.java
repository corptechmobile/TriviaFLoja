package br.com.webapp.web.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.webapp.model.menu.MenuAcesso;

@ManagedBean(name = "menuAcessoAdapter")
@SessionScoped
public class MenuAcessoAdapter implements Serializable {
	
	private static final long serialVersionUID = 6933655900226597464L;
	
	private List<MenuAcesso> lista;
	
	private List<MenuAcesso> getChildrens(MenuAcesso menuAcesso){
		List<MenuAcesso> result = new ArrayList<MenuAcesso>();
		for(MenuAcesso rs : lista){
	    		if(rs.getParent()!=null){
		    		if(rs.getParent().equals(menuAcesso)){
		    			result.add(rs);
		    		}
	    		}
		}
    	
    	return result;
    }

	public TreeNode createDefault(List<MenuAcesso> listaMenu) {
		TreeNode root = new DefaultTreeNode("Menus", null);
		root.setExpanded(true);

		lista = listaMenu;
		for(MenuAcesso rs : lista){
			if(rs.getParent()==null){
				TreeNode treeNode = new DefaultTreeNode(rs.getDescricao(), rs, root);
				treeNode.setExpanded(true);
				
				List<MenuAcesso> childrens = this.getChildrens(rs);
				if(childrens.size()>0){
					addChildrenDefault(treeNode, childrens);
				}
			}
		}
		
        return root;
    }
	
	public TreeNode createDefaultFilter(List<MenuAcesso> listaMenu) {
		TreeNode root = new DefaultTreeNode("Menus", null);
		root.setExpanded(true);
		
		for(MenuAcesso rs : listaMenu){
			TreeNode treeNode = new DefaultTreeNode(rs.getDescricao(), rs, root);
			treeNode.setExpanded(true);
		}
        return root;
	}
	
	private void addChildrenDefault(TreeNode parent, List<MenuAcesso> children){
		for(MenuAcesso rsChildren : children){
			TreeNode treeNodeChildren = new DefaultTreeNode(rsChildren.getDescricao(), rsChildren, parent);
			treeNodeChildren.setExpanded(true);
			
			List<MenuAcesso> subChildrens = this.getChildrens(rsChildren);
			if(subChildrens.size()>0){
				addChildrenDefault(treeNodeChildren, subChildrens);
			}
		}
	}
	
	public TreeNode createCheckbox(List<MenuAcesso> listaMenu) {
		TreeNode root = new CheckboxTreeNode("Menus", null);
		root.setExpanded(true);
		
		lista = listaMenu;
		for(MenuAcesso rs : lista){
			if(rs.getParent()==null){
				TreeNode treeNode = new CheckboxTreeNode(rs.getDescricao(), rs, root);
				treeNode.setExpanded(true);
				
				List<MenuAcesso> childrens = this.getChildrens(rs);
				if(childrens.size()>0){
					addChildrenCheckBox(treeNode, childrens);
				}
			}
		}
		
        return root;
	}
	
	private void addChildrenCheckBox(TreeNode parent, List<MenuAcesso> children){
		for(MenuAcesso rsChildren : children){
			TreeNode treeNodeChildren = new CheckboxTreeNode(rsChildren.getDescricao(), rsChildren, parent);
			treeNodeChildren.setExpanded(true);
			//treeNodeChildren.setSelected(checked);
			
			List<MenuAcesso> subChildrens = this.getChildrens(rsChildren);
			if(subChildrens.size()>0){
				addChildrenCheckBox(treeNodeChildren, subChildrens);
			}
		}
	}

	
	public boolean verifyChecked(Set<MenuAcesso> lista, MenuAcesso menuValidar){
		for(MenuAcesso rs : lista){
			if(rs.equals(menuValidar)){
				return true;
			}
		}
		return false;
	}

}
