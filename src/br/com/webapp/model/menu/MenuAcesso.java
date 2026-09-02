package br.com.webapp.model.menu;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="menuacesso")
public class MenuAcesso implements Serializable {
	//, Comparable<MenuAcesso>
	private static final long serialVersionUID = -5916861434620366648L;
	
	public MenuAcesso(){}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menuacesso")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_parent")
	private MenuAcesso parent;
	
    @OneToMany(mappedBy = "parent")
    @OrderBy("ordem")
    private Set<MenuAcesso> children = new HashSet<MenuAcesso>();

	@Column(length=50)
	private String descricao;
	
	@Column(length=100)
	private String alt;
	
	@Column(length=80)
	private String pgm;
	
	@Column(nullable=false, columnDefinition = "integer default 0")
	private int ordem;
	
	@Column(length=300, nullable=true)
	private String caminho;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MenuAcesso getParent() {
		return parent;
	}

	public void setParent(MenuAcesso parent) {
		this.parent = parent;
	}

	public Set<MenuAcesso> getChildren() {
		return Collections.unmodifiableSet(this.children);
	}

	public void setChildren(Set<MenuAcesso> children) {
		this.children = children;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof MenuAcesso) ) return false;
        final MenuAcesso o = (MenuAcesso) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId();
        return result;
    }

	@Override
	public String toString() {
		return this.getDescricao();
	}
	
}