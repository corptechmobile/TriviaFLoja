package br.com.webapp.model.fb.orcamentogrupo;

import java.io.Serializable;

//@Entity
public class OrcamentoGrupoFB implements Serializable{
	private static final long serialVersionUID = 310319060043200399L;
	private Integer id;
	private String descricao;
	private Integer ativo;
	private Double percFaturamento; 
	private Integer ordem;
	
	
	public OrcamentoGrupoFB() {}

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

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Double getPercFaturamento() {
		return percFaturamento;
	}

	public void setPercFaturamento(Double percFaturamento) {
		this.percFaturamento = percFaturamento;
	}
	
	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
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
		OrcamentoGrupoFB other = (OrcamentoGrupoFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}

}
