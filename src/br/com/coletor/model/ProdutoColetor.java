package br.com.coletor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProdutoColetor implements Serializable {
	
	private static final long serialVersionUID = 6307124334200863754L;

	@Id
	private Integer id;
	private String linhaProduto;
	private String unidade;
	private String codInterno;
	private String codBarra;
	private String codBarraDun14;
	private String descricao;
	private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	private Boolean controlaLote;
	private Double altura;
    private Double largura;
    private Double comprimento;
    private Double pesoBrutoKg;
    private Double pesoLiquidoKg;
	private Double mesaPallet;
	private Double alturaPallet;
	private Integer shelfLife;
	private Integer qtdDecimal;
	
	public ProdutoColetor() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Double getQtdEmbFechVenda() {
		return qtdEmbFechVenda;
	}

	public void setQtdEmbFechVenda(Double qtdEmbFechVenda) {
		this.qtdEmbFechVenda = qtdEmbFechVenda;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getLinhaProduto() {
		return linhaProduto;
	}

	public void setLinhaProduto(String linhaProduto) {
		this.linhaProduto = linhaProduto;
	}
	
	public String getCodBarraDun14() {
		return codBarraDun14;
	}

	public void setCodBarraDun14(String codBarraDun14) {
		this.codBarraDun14 = codBarraDun14;
	}

	public String getDescEmbFechVenda() {
		return descEmbFechVenda;
	}

	public void setDescEmbFechVenda(String descEmbFechVenda) {
		this.descEmbFechVenda = descEmbFechVenda;
	}
	
	public Boolean getControlaLote() {
		return controlaLote;
	}

	public void setControlaLote(Boolean controlaLote) {
		this.controlaLote = controlaLote;
	}
	
	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getComprimento() {
		return comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	public Double getPesoBrutoKg() {
		return pesoBrutoKg;
	}

	public void setPesoBrutoKg(Double pesoBrutoKg) {
		this.pesoBrutoKg = pesoBrutoKg;
	}

	public Double getPesoLiquidoKg() {
		return pesoLiquidoKg;
	}

	public void setPesoLiquidoKg(Double pesoLiquidoKg) {
		this.pesoLiquidoKg = pesoLiquidoKg;
	}

	public Double getMesaPallet() {
		return mesaPallet;
	}

	public void setMesaPallet(Double mesaPallet) {
		this.mesaPallet = mesaPallet;
	}

	public Double getAlturaPallet() {
		return alturaPallet;
	}

	public void setAlturaPallet(Double alturaPallet) {
		this.alturaPallet = alturaPallet;
	}
	
	public Integer getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
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
		ProdutoColetor other = (ProdutoColetor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
