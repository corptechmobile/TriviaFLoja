package br.com.webapp.model.fb.orcamentogrupo;

import java.io.Serializable;

//@Entity
public class OrcamentoGrupoAssEventoFB implements Serializable{
	private static final long serialVersionUID = 7960203769239539668L;
	private Integer id;
	private Integer idOrcamentoGrupo;
	private String descOrcamentoGrupo;
	private Integer idGrupoFinanceiro;
	private String descGrupoFinanceiro;
	private Integer idEventoFinanceiro;
	private String descEventoFinanceiro;
	
	public OrcamentoGrupoAssEventoFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdOrcamentoGrupo() {
		return idOrcamentoGrupo;
	}

	public void setIdOrcamentoGrupo(Integer idOrcamentoGrupo) {
		this.idOrcamentoGrupo = idOrcamentoGrupo;
	}

	public String getDescOrcamentoGrupo() {
		return descOrcamentoGrupo;
	}

	public void setDescOrcamentoGrupo(String descOrcamentoGrupo) {
		this.descOrcamentoGrupo = descOrcamentoGrupo;
	}

	public Integer getIdGrupoFinanceiro() {
		return idGrupoFinanceiro;
	}

	public void setIdGrupoFinanceiro(Integer idGrupoFinanceiro) {
		this.idGrupoFinanceiro = idGrupoFinanceiro;
	}

	public String getDescGrupoFinanceiro() {
		return descGrupoFinanceiro;
	}

	public void setDescGrupoFinanceiro(String descGrupoFinanceiro) {
		this.descGrupoFinanceiro = descGrupoFinanceiro;
	}

	public Integer getIdEventoFinanceiro() {
		return idEventoFinanceiro;
	}

	public void setIdEventoFinanceiro(Integer idEventoFinanceiro) {
		this.idEventoFinanceiro = idEventoFinanceiro;
	}

	public String getDescEventoFinanceiro() {
		return descEventoFinanceiro;
	}

	public void setDescEventoFinanceiro(String descEventoFinanceiro) {
		this.descEventoFinanceiro = descEventoFinanceiro;
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
		OrcamentoGrupoAssEventoFB other = (OrcamentoGrupoAssEventoFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getDescEventoFinanceiro();
	}
	

	

}
