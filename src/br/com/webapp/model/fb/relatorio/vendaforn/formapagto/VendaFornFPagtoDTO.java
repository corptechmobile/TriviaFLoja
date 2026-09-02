package br.com.webapp.model.fb.relatorio.vendaforn.formapagto;

import java.io.Serializable;
import java.util.List;

import br.com.webapp.web.util.Funcoes;

public class VendaFornFPagtoDTO implements Serializable{

	private static final long serialVersionUID = -3939101792958236212L;
	
	private Integer formaPagtoRecId;
	private Integer condPagtoId;
	private String descricao;
	private Double valor;
	private Double desconto;
	private Integer parcela;
	private Integer tipoSql;

	// Transient
	private List<VendaFornFPagtoDTO> filhos;
	
	public VendaFornFPagtoDTO() {}
	
	public Integer getFormaPagtoRecId() {
		return formaPagtoRecId;
	}
	public void setFormaPagtoRecId(Integer formaPagtoRecId) {
		this.formaPagtoRecId = formaPagtoRecId;
	}
	public Integer getCondPagtoId() {
		return condPagtoId;
	}
	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		if(valor == null && filhos != null) {
			valor = 0.0;
			for(VendaFornFPagtoDTO rs : filhos) {
				valor += rs.getValor();
			}
		}
		return valor;
	}
	public Integer getTipoSql() {
		return tipoSql;
	}

	public void setTipoSql(Integer tipoSql) {
		this.tipoSql = tipoSql;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getDesconto() {
		if(desconto == null && filhos != null) {
			desconto = 0.0;
			for(VendaFornFPagtoDTO rs : filhos) {
				desconto += rs.getDesconto();
			}
			desconto = (valor*desconto) / (valor);
		}
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public Integer getParcela() {
		return parcela;
	}
	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}
	
	// Transient
	public List<VendaFornFPagtoDTO> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<VendaFornFPagtoDTO> filhos) {
		this.filhos = filhos;
	}
	
	// Method - > percentual sob o valor Total
	public Double perc(Double total) {
		try {
			return Funcoes.percentual(total, valor);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condPagtoId == null) ? 0 : condPagtoId.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((formaPagtoRecId == null) ? 0 : formaPagtoRecId.hashCode());
		result = prime * result + ((parcela == null) ? 0 : parcela.hashCode());
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
		VendaFornFPagtoDTO other = (VendaFornFPagtoDTO) obj;
		if (condPagtoId == null) {
			if (other.condPagtoId != null)
				return false;
		} else if (!condPagtoId.equals(other.condPagtoId))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (formaPagtoRecId == null) {
			if (other.formaPagtoRecId != null)
				return false;
		} else if (!formaPagtoRecId.equals(other.formaPagtoRecId))
			return false;
		if (parcela == null) {
			if (other.parcela != null)
				return false;
		} else if (!parcela.equals(other.parcela))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao + " -> parcela (" + (parcela != null ? parcela : 0) + ")" + " -> filhos (" + (filhos != null ? filhos.size() : 0) + ")";
	}

}
