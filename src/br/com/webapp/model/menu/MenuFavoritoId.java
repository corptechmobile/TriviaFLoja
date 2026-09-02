package br.com.webapp.model.menu;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuFavoritoId implements Serializable {

	private static final long serialVersionUID = -401896836175167186L;
	
	@Basic(optional=false)
	@Column(name="id_menuacesso", nullable=false)
	private Integer menuAcesso;
	
	@Basic(optional=false)
	@Column(name="id_usuario", nullable=false)
	private Integer usuario;
	
	public MenuFavoritoId() {}
	
	public MenuFavoritoId(Integer menuAcesso, Integer usuario) {
		super();
		this.menuAcesso = menuAcesso;
		this.usuario = usuario;
	}

	public Integer getMenuAcesso() {
		return menuAcesso;
	}

	public void setMenuAcesso(Integer menuAcesso) {
		this.menuAcesso = menuAcesso;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuAcesso == null) ? 0 : menuAcesso.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		MenuFavoritoId other = (MenuFavoritoId) obj;
		if (menuAcesso == null) {
			if (other.menuAcesso != null)
				return false;
		} else if (!menuAcesso.equals(other.menuAcesso))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
