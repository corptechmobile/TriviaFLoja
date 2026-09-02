package br.com.webapp.model.menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="menurecente")
public class MenuRecente implements Serializable {
	
	private static final long serialVersionUID = -355096700224739529L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menurecente")
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_menuacesso")
	private MenuAcesso menuAcesso;
	
	@Column(name="id_usuario")
	private Integer usuario;
	
	private int acessos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MenuAcesso getMenuAcesso() {
		return menuAcesso;
	}

	public void setMenuAcesso(MenuAcesso menuAcesso) {
		this.menuAcesso = menuAcesso;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public int getAcessos() {
		return acessos;
	}

	public void setAcessos(int acessos) {
		this.acessos = acessos;
	}

}