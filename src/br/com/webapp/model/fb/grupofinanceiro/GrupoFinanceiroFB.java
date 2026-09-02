package br.com.webapp.model.fb.grupofinanceiro;

import java.io.Serializable;

//@Entity
public class GrupoFinanceiroFB implements Serializable{
	private static final long serialVersionUID = -2066918618125411500L;
	private String id;
	private String descricao;
	private Integer fixo;
	private String tipo;
	private Integer aprovacaoEletronica;
	private String idTipoDocCTB;
	private Integer idContaCTBProv;
	private Integer compoeAtrasoHistCli; 
	
	public GrupoFinanceiroFB() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFixo() {
		return fixo;
	}

	public void setFixo(Integer fixo) {
		this.fixo = fixo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getAprovacaoEletronica() {
		return aprovacaoEletronica;
	}

	public void setAprovacaoEletronica(Integer aprovacaoEletronica) {
		this.aprovacaoEletronica = aprovacaoEletronica;
	}

	public String getIdTipoDocCTB() {
		return idTipoDocCTB;
	}

	public void setIdTipoDocCTB(String idTipoDocCTB) {
		this.idTipoDocCTB = idTipoDocCTB;
	}

	public Integer getIdContaCTBProv() {
		return idContaCTBProv;
	}

	public void setIdContaCTBProv(Integer idContaCTBProv) {
		this.idContaCTBProv = idContaCTBProv;
	}

	public Integer getCompoeAtrasoHistCli() {
		return compoeAtrasoHistCli;
	}

	public void setCompoeAtrasoHistCli(Integer compoeAtrasoHistCli) {
		this.compoeAtrasoHistCli = compoeAtrasoHistCli;
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
		GrupoFinanceiroFB other = (GrupoFinanceiroFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
