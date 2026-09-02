package br.com.webapp.model.fb.relatorio.vendaforn;

import java.io.Serializable;

public class VendaFornDTO implements Serializable{

	private static final long serialVersionUID = -6653428311817763193L;
	private Integer id;
	private String descricao;
	private Double valor;
	private Double vlDevolvido;
	private Double valorAnoAnt;
	private Double vlDevolvidoAnoAnt;
	private Double valorMesAnt;
	private Double vlDevolvidoMesAnt;
	private Double percValor;
	private Double margem;
	private Double markup;
	private Double desconto;
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getVlDevolvido() {
		return vlDevolvido;
	}
	public void setVlDevolvido(Double vlDevolvido) {
		this.vlDevolvido = vlDevolvido;
	}

	public Double getValorAnoAnt() {
		return valorAnoAnt;
	}
	public void setValorAnoAnt(Double valorAnoAnt) {
		this.valorAnoAnt = valorAnoAnt;
	}
	public Double getVlDevolvidoAnoAnt() {
		return vlDevolvidoAnoAnt;
	}
	public void setVlDevolvidoAnoAnt(Double vlDevolvidoAnoAnt) {
		this.vlDevolvidoAnoAnt = vlDevolvidoAnoAnt;
	}
	public Double getValorMesAnt() {
		return valorMesAnt;
	}
	public void setValorMesAnt(Double valorMesAnt) {
		this.valorMesAnt = valorMesAnt;
	}
	public Double getVlDevolvidoMesAnt() {
		return vlDevolvidoMesAnt;
	}
	public void setVlDevolvidoMesAnt(Double vlDevolvidoMesAnt) {
		this.vlDevolvidoMesAnt = vlDevolvidoMesAnt;
	}
	public Double getPercValor() {
		return percValor;
	}
	public void setPercValor(Double percValor) {
		this.percValor = percValor;
	}
	
	public Double getMargem() {
		return margem;
	}
	public void setMargem(Double margem) {
		this.margem = margem;
	}
	public Double getMarkup() {
		return markup;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
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
		VendaFornDTO other = (VendaFornDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
