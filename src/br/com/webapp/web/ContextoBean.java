package br.com.webapp.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.webapp.model.configuracao.Configuracao;
import br.com.webapp.model.configuracao.ConfiguracaoRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.usuario.UsuarioFBRN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.menu.MenuHomeRN;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.model.usuario.UsuarioRN;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {
	
	private static final long serialVersionUID = -21049677816104133L;
	
	private String titulo;
	private UsuarioFB usuarioLogado = null;
	private boolean empresaFilter;
	private boolean vendedorFilter;
	private List<Configuracao> configuracoes;
	
	public ContextoBean(){
		titulo = "Trívia | F-Loja";
	}
	
	public UsuarioFB getUsuarioLogado() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		
		if(usuarioLogado == null || !login.equals(usuarioLogado.getLogin().toUpperCase())){
			if(login != null){
				
				UsuarioFBRN usuarioFBRN = new UsuarioFBRN();
				usuarioLogado = usuarioFBRN.carregar(login);
				usuarioLogado.setEmpresas(new EmpresaFBRN().listar(usuarioLogado));
				
				Usuario usuario = new UsuarioRN().carregar(usuarioLogado.getId());
				if(usuario!=null) {
					usuarioLogado.setUsuarioGrupo(usuario.getUsuarioGrupo()); 
					System.out.println("[ContextoBean]MenuAcesso: " + usuarioLogado.getUsuarioGrupo().getMenus().size());
				}
				
				usuarioLogado.setHome(new MenuHomeRN().carregar(usuarioLogado));
				System.out.println("[ContextoBean]Usuario: " + usuarioLogado.getLogin().toUpperCase());
				if(this.usuarioLogado.getHome()!=null){
					System.out.println("[ContextoBean]Home: " + this.usuarioLogado.getHome().getDescricao());
				}
				
				// Vendedor
				if(usuarioLogado.getIsVendedor() != null && usuarioLogado.getIsVendedor() == 1 && usuarioLogado.getVendedorId()!=null) {
					System.out.println("[ContextoBean]VendedorId: " + usuarioLogado.getVendedorId());
					
					vendedorFilter = false;
					VendedorFBRN vendedorFBRN = new VendedorFBRN();
					VendedorFB vendedorFB = vendedorFBRN.carregar(usuarioLogado);
					if(vendedorFB!=null) {
						usuarioLogado.setVendedor(vendedorFB);
						System.out.println("[ContextoBean]Vendedor: " + vendedorFB.getCnpjCpf() + " - " + vendedorFB.getNomeFantasia());
					}
				}else {
					vendedorFilter = true;
				}
				
				// Empresas
				if(usuarioLogado.getEmpresas()!=null) {
					System.out.println("[ContextoBean]Empresas: " + usuarioLogado.getEmpresas().size());
					if(usuarioLogado.getEmpresas().size()>1) {
						empresaFilter = true;
					}
					
					for(EmpresaFB rs : usuarioLogado.getEmpresas()) {
						System.out.println("[ContextoBean]Empresa: " + rs.getId() + " - " + rs.getNomeFantasia());
					}
				}
				
				// Configuracoes
				configuracoes = new ConfiguracaoRN().listar();
				
			}			
		}
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(UsuarioFB usuario) {
		this.usuarioLogado = usuario;
	}
	
	public void refreshUsuarioLogado(){
		usuarioLogado = null;
		getUsuarioLogado();
	}
	
	public boolean verificaPermissao(Integer menuAcesso){
		if(usuarioLogado.getUsuarioGrupo()!=null) {
			for(MenuAcesso rs : usuarioLogado.getUsuarioGrupo().getMenus()){
				if(rs.getId().intValue() == menuAcesso.intValue()){
					return true;
				}
			}
		}
		return false;
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean isEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(boolean empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public boolean isVendedorFilter() {
		return vendedorFilter;
	}

	public void setVendedorFilter(boolean vendedorFilter) {
		this.vendedorFilter = vendedorFilter;
	}

	public List<Configuracao> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<Configuracao> configuracoes) {
		this.configuracoes = configuracoes;
	}

}
