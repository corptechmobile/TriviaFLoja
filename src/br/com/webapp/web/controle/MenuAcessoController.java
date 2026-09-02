package br.com.webapp.web.controle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.menu.MenuAcessoRN;
import br.com.webapp.model.menu.MenuFavorito;
import br.com.webapp.model.menu.MenuFavoritoId;
import br.com.webapp.model.menu.MenuFavoritoRN;
import br.com.webapp.model.menu.MenuHome;
import br.com.webapp.model.menu.MenuHomeRN;
import br.com.webapp.model.menu.MenuRecente;
import br.com.webapp.model.menu.MenuRecenteRN;

public class MenuAcessoController {
	
	public static final int MUDARSENHABEAN_MENU = -1;
	public static final int CLIENTE_CADASTRO_BEAN = 4;
	public static final int CLIENTE_CONSULTA_BEAN = 6;
	
	public static final int PEDVENDA_CONSULTA_BEAN = 5;
	public static final int PEDVENDA_LIBERACAO_BEAN = 7;
	public static final int ALCADA_PAGTO_BEAN = 9;
	public static final Integer USUARIOGRUPOBEAN_MENU = 11;
	public static final Integer USUARIOBEAN_MENU = 12;
	public static final Integer PRODUTO_CONSULTA_BEAN = 13;
	public static final Integer VENDAFORM_BEAN = 14;
	public static final Integer PEDVENDA_DIVERG_BEAN = 15;
	public static final Integer ECF_VENDAS_PERIODO_BEAN = 16;
	public static final Integer COMISSAOFAIXADESC_BEAN = 17;
	public static final Integer ORCAMENTOMETA_BEAN = 18;
	public static final Integer CONSULTAORCAMENTOMETA_BEAN = 19;
	public static final Integer LINHAPRODUTOMETA_BEAN = 20;	
	public static final Integer CONSULTALINHAPRODUTOMETA_BEAN = 21;
	public static final Integer GRUPOORCAMENTO_BEAN = 23;
	public static final Integer PEDVENDA_RECEBIMENTO_BEAN = 24;
	public static final Integer CLIENTENAOPOSITIVADO_BEAN = 25;
	public static final Integer PEDTRANSF_CONSULTA_BEAN = 26;
	public static final Integer DEVVENDAFORM_BEAN = 27;
	public static final Integer VENDAGERAL_BEAN = 29;
	public static final Integer INVENTARIOBEAN_MENU = 30;
	public static final Integer PRODUTOCBBEAN_MENU = 31;
	public static final Integer COLETORPCBEAN_MENU = 32;
	public static final Integer ROMANEIOBEAN_MENU = 33;
	
	
	public static void addHome(int menuId, UsuarioFB usuario, FacesContext facesContext){
		System.out.println("[addHome][menuId]: " + menuId);
		try {
	    	MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
	    	MenuAcesso menuAdd = menuAcessoRN.carregar(menuId);
	 
	    	if(menuAdd!=null){
		    	MenuHomeRN menuHomeRN = new MenuHomeRN();
		    	MenuHome menuHome = new MenuHome(usuario.getId(), menuAdd);
		    	menuHomeRN.salvar(menuHome);
		    	
		    	usuario.setHome(menuAdd);
		    	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Home", "Sua Página Principal está definida, no seu próximo acesso ela será exibida!"));
	    	}else{
	    		System.out.println("[addHome][menuId]["+menuId+"][Erro]: not found MenuAcesso");
	    		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Home", "Não foi possível incluir como Página principal!"));
	    	}
	    	
		} catch (Exception e) {
			System.out.println("[addHome][menuId]["+menuId+"][Erro]: " + e.getMessage());
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Home", "Não foi possável incluir como Página principal!"));
		}
		
    }
    
    public static void addFavoritos(int menuId, UsuarioFB usuario, FacesContext facesContext){
    	System.out.println("[addFavoritos][menuId]: " + menuId);
    	try {
    		MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
    		MenuAcesso menuAdd = menuAcessoRN.carregar(menuId);
        	
        	if(menuAdd!=null){
        		MenuFavoritoRN menuFavoritoRN = new MenuFavoritoRN();
        		MenuFavorito menuFavorito = new MenuFavorito();
        		menuFavorito.setId(new MenuFavoritoId(menuId, usuario.getId()));
        		menuFavoritoRN.salvar(menuFavorito);
        		
        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Favoritos", "Página definida como favorita"));
        		
        	}else{
        		System.out.println("[addFavoritos][menuId]["+menuId+"][Erro]: not found MenuAcesso");
        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Favoritos", "Não foi possível definir como Favorito!"));
        	}
        	
		} catch (Exception e) {
			System.out.println("[addFavoritos][menuId]["+menuId+"][Erro]: " + e.getMessage());
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Favoritos", "Não foi possível definir como Favorito!"));
		}
    	
    }
    
    public static void removeFavoritos(int menuId, UsuarioFB usuario){
    	System.out.println("[removeFavoritos][menuId]: " + menuId);
    	try {
    		MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
    		MenuAcesso menuAdd = menuAcessoRN.carregar(menuId);
        	
        	if(menuAdd!=null){
        		MenuFavoritoRN menuFavoritoRN = new MenuFavoritoRN();
        		MenuFavorito menuFavorito = new MenuFavorito();
        		menuFavorito.setId(new MenuFavoritoId(menuId, usuario.getId()));
        		menuFavoritoRN.excluir(menuFavorito);
        	}else{
        		System.out.println("[removeFavoritos][menuId]["+menuId+"][Erro]: not found MenuAcesso");
        	}
        	
		} catch (Exception e) {
			System.out.println("[removeFavoritos][menuId]["+menuId+"][Erro]: " + e.getMessage());
		}
    	
    }
    
    public static void addRecentes(int menuId, UsuarioFB usuario) {
    	System.out.println("[addRecentes][menuId]: " + menuId);
    	try {
    		MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
    		MenuAcesso menuAdd = menuAcessoRN.carregar(menuId);
        	
        	if(menuAdd!=null){
    	    	MenuRecenteRN menuRecenteRN = new MenuRecenteRN();
    			MenuRecente menuRecente = menuRecenteRN.carregar(menuAdd.getId(), usuario.getId());
    			
    			if(menuRecente != null){
    				menuRecente.setMenuAcesso(menuAdd);
    				menuRecente.setUsuario(usuario.getId());
    				menuRecente.setAcessos(menuRecente.getAcessos() + 1);
    				menuRecenteRN.salvar(menuRecente);
    			}else{
    				menuRecente = new MenuRecente();
    				menuRecente.setMenuAcesso(menuAdd);
    				menuRecente.setUsuario(usuario.getId());
    				menuRecente.setAcessos(1);
    				menuRecenteRN.salvar(menuRecente);
    			}
    	    	
        	}else{
        		System.out.println("[addRecentes][menuId]["+menuId+"][Erro]: not found MenuAcesso");
        	}
        	
		} catch (Exception e) {
			System.out.println("[addRecentes][menuId]["+menuId+"][Erro]: " + e.getMessage());
		}
    }

}
