package br.com.webapp.model.menu;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryPostGres;

public class MenuAcessoRN {
	
	private MenuAcessoDAO menuDAO;
	
	public MenuAcessoRN(){
		this.menuDAO = DAOFactoryPostGres.criarMenuAcessoDAO();
	}
	
	public MenuAcesso salvar(MenuAcesso menuAcesso){
		if(menuAcesso.getParent() != null){
			menuAcesso.setCaminho(this.montarUrl(this.carregar(menuAcesso.getParent().getId())));
		}else{
			menuAcesso.setCaminho("");
		}
		return this.menuDAO.salvar(menuAcesso);
	}
	public void excluir(MenuAcesso menuAcesso) throws DAOException{
		this.menuDAO.excluir(menuAcesso);
	}
	public MenuAcesso carregar(Integer id_menuacesso){
		return this.menuDAO.carregar(id_menuacesso);
	}
	public List<MenuAcesso> listar(){
		return this.menuDAO.listar();
	}
	public List<MenuAcesso> listarFilhos(String cod_edt, Integer id_menugrupo){
		return this.menuDAO.listarFilhos(cod_edt, id_menugrupo);
	}
	public MenuAcesso ultimoGrupo(Integer id_menugrupo){
		return this.menuDAO.ultimoGrupo(id_menugrupo);
	}
	public MenuAcesso ultimoPai(MenuAcesso menuAcesso){
		return this.menuDAO.ultimoPai(menuAcesso);
	}
	public List<Object[]> menuPrincipal(Integer id_usuariogrupo){
		return this.menuDAO.menuPrincipal(id_usuariogrupo);
	}
	public List<Object[]> menuRecente(Integer id_usuario, Integer id_usuariogrupo){
		return this.menuDAO.menuRecente(id_usuario, id_usuariogrupo);
	}
	public List<Object[]> menuFavorito(Integer id_usuario, Integer id_usuariogrupo){
		return this.menuDAO.menuFavorito(id_usuario, id_usuariogrupo);
	}
	public boolean verificar(List<MenuAcesso> lista, MenuAcesso menuAcesso){
		
		for(MenuAcesso rs : lista){
			if(rs.equals(menuAcesso)){ return true; }
		}
		
		return false;
		
	}

	public List<MenuAcesso> listar(String descricaoFilter) {
		return this.menuDAO.listar(descricaoFilter);
	}
	
	public List<MenuAcesso> listarRecentes(UsuarioFB usuario) {
		return this.menuDAO.listarRecentes(usuario);
	}

	public List<MenuAcesso> listarFavoritos(UsuarioFB usuario) {
		return this.menuDAO.listarFavoritos(usuario);
	}
	
	public String montarUrl(MenuAcesso menuAcesso){
    	String varUrlDescricao = "";
    	if(menuAcesso.getParent()!=null){
    		varUrlDescricao = montarUrlParent(menuAcesso.getParent(), menuAcesso.getDescricao());
    	}else{
    		varUrlDescricao = menuAcesso.getDescricao();
    	}
    	
    	return varUrlDescricao;
    }
    
    private String montarUrlParent(MenuAcesso menuAcesso, String url){
    	String resultUrl = "";
    	if(menuAcesso.getParent()!=null){
    		resultUrl = montarUrlParent(menuAcesso.getParent(), menuAcesso.getDescricao() + " / " + url);
    	}else{
    		resultUrl = menuAcesso.getDescricao() + " / " + url;
    	}
    	
    	return resultUrl;
    } 
    
    public void montaDadosSelect(List<SelectItem> select, List<MenuAcesso> menus, String prefixo) {
		SelectItem item = null;
		List<MenuAcesso> filhos = null;
		if (menus != null) {
			for (MenuAcesso menu : menus) {
				
				if(menu.getParent()==null){
					item = new SelectItem(menu, prefixo + menu.getDescricao());
					item.setEscape(false);
					select.add(item);
					
					filhos = getChildrens(menu, menus);
					if(filhos.size() > 0){
						this.montaDadosSelectMain(select, filhos, menus, prefixo + "  ");
					}
					
				}
				
			} 
		}
	}
    
    private void montaDadosSelectMain(List<SelectItem> select, List<MenuAcesso> filhos, List<MenuAcesso> menus, String prefixo){
    	SelectItem item = null;
		List<MenuAcesso> menuFilhos = null;
    	for (MenuAcesso menu : filhos) {
    		
			item = new SelectItem(menu, prefixo + menu.getDescricao());
			item.setEscape(false);
			select.add(item);
			
			menuFilhos = getChildrens(menu, menus);
			if(filhos.size() > 0){
				this.montaDadosSelectMain(select, menuFilhos, menus, prefixo + "  ");
			}
			
		} 
    }
    
    private List<MenuAcesso> getChildrens(MenuAcesso menuAcesso, List<MenuAcesso> lista){
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

	public List<MenuAcesso> listar(UsuarioGrupo usuarioGrupo) {
		return this.menuDAO.listar(usuarioGrupo);
	}

}
