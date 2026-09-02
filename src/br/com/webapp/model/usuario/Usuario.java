package br.com.webapp.model.usuario;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFB;
import br.com.webapp.model.fb.conferente.ConferenteFB;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;


@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -3799919152435180619L;
	
	@Id
	private Integer id;
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//	@JoinTable(name = "wms_usuario_divergencia", joinColumns = { @JoinColumn(name = "id_usuario", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_divergencia", nullable = false, updatable = false) })
//	private Set<ColetorDivergenciaFB> divergencias = new HashSet<ColetorDivergenciaFB>(0);
//	//private List<ColetorPCDivergFB> divergencias;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuariogrupo")
	private UsuarioGrupo usuarioGrupo;
	
	@Column(name="id_conferente")
	private Integer conferenteId;
	
	public Usuario(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}

	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}
	
//	public Set<ColetorDivergenciaFB> getDivergencias() {
//		return divergencias;
//	}
//
//	public void setDivergencias(Set<ColetorDivergenciaFB> divergencias) {
//		this.divergencias = divergencias;
//	}

	public Integer getConferenteId() {
		return conferenteId;
	}

	public void setConferenteId(Integer conferenteId) {
		this.conferenteId = conferenteId;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
