package br.com.webapp.model.fb.usuario;

import java.io.Serializable;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;

//@Entity
public class UsuarioFB implements Serializable {
	
	private static final long serialVersionUID = -2811194148135968821L;
	
	public static final Integer ATIVO = 1;
	public static final Integer INATIVO = 0;
	
	
//	@Id
	private Integer id;
	private Integer vendedorId;
	private Integer gestaoVendaId;
	private String gestaoVendaCodEdt;
	private String nome;
	private String login;
	private String senha;
	private Integer ativo;
	private Integer isVendedor;
	private Integer empresaId;
	
	//	@Transient
	private VendedorFB vendedor;
	
//	@Transient
	private UsuarioGrupo usuarioGrupo;
	
//	@Transient
	private List<EmpresaFB> empresas;
	
//	@Transient
	private MenuAcesso home;
	
//	@Transient
	private List<MenuAcesso> menusfavorito;
	
	public UsuarioFB() {}
	
	public UsuarioFB(Integer id){
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Integer vendedorId) {
		this.vendedorId = vendedorId;
	}

	public Integer getGestaoVendaId() {
		return gestaoVendaId;
	}

	public void setGestaoVendaId(Integer gestaoVendaId) {
		this.gestaoVendaId = gestaoVendaId;
	}

	public String getGestaoVendaCodEdt() {
		return gestaoVendaCodEdt;
	}

	public void setGestaoVendaCodEdt(String gestaoVendaCodEdt) {
		this.gestaoVendaCodEdt = gestaoVendaCodEdt;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public VendedorFB getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorFB vendedor) {
		this.vendedor = vendedor;
	}
	
	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}

	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}
	
	public List<EmpresaFB> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaFB> empresas) {
		this.empresas = empresas;
	}
	
	public MenuAcesso getHome() {
		return home;
	}

	public void setHome(MenuAcesso home) {
		this.home = home;
	}

	public List<MenuAcesso> getMenusfavorito() {
		return menusfavorito;
	}

	public void setMenusfavorito(List<MenuAcesso> menusfavorito) {
		this.menusfavorito = menusfavorito;
	}
	
	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	
	public Integer getIsVendedor() {
		return isVendedor;
	}

	public void setIsVendedor(Integer isVendedor) {
		this.isVendedor = isVendedor;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioFB other = (UsuarioFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getLogin();
	}

}
