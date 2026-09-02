package br.com.webapp.model.menu;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="menufavorito")
public class MenuFavorito implements Serializable{

	private static final long serialVersionUID = 1098932254359534625L;
	
	@EmbeddedId
	private MenuFavoritoId id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_menuacesso", referencedColumnName="id_menuacesso", insertable=false, updatable=false)
	private MenuAcesso menuAcesso;
	
	public MenuFavorito() {}

	public MenuFavoritoId getId() {
		return id;
	}

	public void setId(MenuFavoritoId id) {
		this.id = id;
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
		MenuFavorito other = (MenuFavorito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
