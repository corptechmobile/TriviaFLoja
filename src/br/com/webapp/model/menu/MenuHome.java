package br.com.webapp.model.menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="menuhome")
public class MenuHome implements Serializable {
	
	private static final long serialVersionUID = -1453797603421323998L;

	@Id
	@Column(name="id_usuario")
	private Integer usuarioId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_menuacesso")
	private MenuAcesso menuAcesso;
	
	public MenuHome() {}
	
	public MenuHome(Integer usuarioId, MenuAcesso menuAcesso) {
		super();
		this.usuarioId = usuarioId;
		this.menuAcesso = menuAcesso;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public MenuAcesso getMenuAcesso() {
		return menuAcesso;
	}

	public void setMenuAcesso(MenuAcesso menuAcesso) {
		this.menuAcesso = menuAcesso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
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
		MenuHome other = (MenuHome) obj;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		return true;
	}
	
}
