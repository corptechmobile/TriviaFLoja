package br.com.webapp.model.usuariogrupo;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.webapp.model.menu.MenuAcesso;

@Entity
@Table(name="usuariogrupo")
public class UsuarioGrupo implements Serializable {
	
	private static final long serialVersionUID = -5440544036578084961L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuariogrupo")
	private Integer id;
	
	@Column(length=100, nullable=false)
	private String descricao;
	
	@Column(length=150, nullable=false)
	private String email;
	
	@Column(nullable=false, columnDefinition = "decimal(15,2) default '0.00'")
	private Double descontoMaximo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "menu_usuariogrupo", joinColumns = { @JoinColumn(name = "id_usuariogrupo", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_menuacesso", nullable = false, updatable = false) })
	private Set<MenuAcesso> menus = new HashSet<MenuAcesso>(0);
	
	public UsuarioGrupo() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Double getDescontoMaximo() {
		return descontoMaximo;
	}

	public void setDescontoMaximo(Double descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}

	public Set<MenuAcesso> getMenus() {
		return Collections.unmodifiableSet(menus);
	}

	public void setMenus(Set<MenuAcesso> menus) {
		this.menus = menus;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof UsuarioGrupo) ) return false;
        final UsuarioGrupo o = (UsuarioGrupo) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId();
        return result;
    }

}
