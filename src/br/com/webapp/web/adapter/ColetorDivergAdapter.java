package br.com.webapp.web.adapter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFB;

@ManagedBean(name = "coletorDivergAdapter")
@SessionScoped
public class ColetorDivergAdapter implements Serializable{

	private static final long serialVersionUID = 1223991406477694749L;
	
	private List<ColetorDivergenciaFB> lista;
	
	public TreeNode createDefault(List<ColetorDivergenciaFB> listaMenu) {
		TreeNode root = new DefaultTreeNode("Menus", null);
		root.setExpanded(true);

		lista = listaMenu;
		for(ColetorDivergenciaFB rs : lista){
			TreeNode treeNodeChildren = new DefaultTreeNode(rs.getDivergenciaDesc(), rs, root);
			treeNodeChildren.setExpanded(true);
		}
		
        return root;
    }
	
	public TreeNode createDefaultFilter(List<ColetorDivergenciaFB> listaMenu) {
		TreeNode root = new DefaultTreeNode("Divergencias", null);
		root.setExpanded(true);
		
		for(ColetorDivergenciaFB rs : listaMenu){
			new DefaultTreeNode(rs.getDivergenciaDesc(), rs, root);
		}
        return root;
	}
	
	public TreeNode createCheckbox(List<ColetorDivergenciaFB> listaMenu) {
		TreeNode root = new CheckboxTreeNode("Divergencias", null);
		root.setExpanded(true);
		
		lista = listaMenu;
		for(ColetorDivergenciaFB rs : lista){
			new CheckboxTreeNode(rs.getDivergenciaDesc(), rs, root);
		}
		
        return root;
	}
	
	public boolean verifyChecked(Set<ColetorDivergenciaFB> lista, ColetorDivergenciaFB divergencia){
		for(ColetorDivergenciaFB rs : lista){
			if(rs.equals(divergencia)){
				return true;
			}
		}
		return false;
	}

}
