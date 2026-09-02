package br.com.webapp.model.fb.gestaovendamob;

import java.io.Serializable;

//@Entity
public class GestaoVendaMobFB implements Serializable{

	private static final long serialVersionUID = -4376184407711692873L;

//	@Id
	private Integer id;
	
//	@Column
	private Integer gestaoRefId;
	
//	@Column
	private String nome;
	
//	@Column
	private Integer ordem;
	
//	@Column
	private String codEdt;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGestaoRefId() {
		return gestaoRefId;
	}

	public void setGestaoRefId(Integer gestaoRefId) {
		this.gestaoRefId = gestaoRefId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getCodEdt() {
		return codEdt;
	}

	public void setCodEdt(String codEdt) {
		this.codEdt = codEdt;
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
		GestaoVendaMobFB other = (GestaoVendaMobFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
