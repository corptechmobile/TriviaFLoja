package br.com.coletor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorPlanilhaCegaItem implements Serializable {

	private static final long serialVersionUID = 1644042771718987976L;

	@Id
	private Integer id;

    private Integer coletorPlanilhaCegaId;
    private Integer produtoId;
    
    private Integer unidCompId;
    private String unidCompDesc;
    
    private String descFatorUnidBasica;
    private Double fatorUnidBasica;
    
    private Boolean obrigaDescLote;
    private Boolean obrigaVencLote;
    private Boolean controlaLote;
    
    private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	
	private Integer qtdDecimal;
	
	private Double qtd;
	
	public ColetorPlanilhaCegaItem(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColetorPlanilhaCegaId() {
		return coletorPlanilhaCegaId;
	}

	public void setColetorPlanilhaCegaId(Integer coletorPlanilhaCegaId) {
		this.coletorPlanilhaCegaId = coletorPlanilhaCegaId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getUnidCompId() {
		return unidCompId;
	}

	public void setUnidCompId(Integer unidCompId) {
		this.unidCompId = unidCompId;
	}

	public String getUnidCompDesc() {
		return unidCompDesc;
	}

	public void setUnidCompDesc(String unidCompDesc) {
		this.unidCompDesc = unidCompDesc;
	}

	public String getDescFatorUnidBasica() {
		return descFatorUnidBasica;
	}

	public void setDescFatorUnidBasica(String descFatorUnidBasica) {
		this.descFatorUnidBasica = descFatorUnidBasica;
	}

	public Double getFatorUnidBasica() {
		return fatorUnidBasica;
	}

	public void setFatorUnidBasica(Double fatorUnidBasica) {
		this.fatorUnidBasica = fatorUnidBasica;
	}

	public Boolean getObrigaDescLote() {
		return obrigaDescLote;
	}

	public void setObrigaDescLote(Boolean obrigaDescLote) {
		this.obrigaDescLote = obrigaDescLote;
	}

	public Boolean getObrigaVencLote() {
		return obrigaVencLote;
	}

	public void setObrigaVencLote(Boolean obrigaVencLote) {
		this.obrigaVencLote = obrigaVencLote;
	}

	public Boolean getControlaLote() {
		return controlaLote;
	}

	public void setControlaLote(Boolean controlaLote) {
		this.controlaLote = controlaLote;
	}

	public Double getQtdEmbFechVenda() {
		return qtdEmbFechVenda;
	}

	public void setQtdEmbFechVenda(Double qtdEmbFechVenda) {
		this.qtdEmbFechVenda = qtdEmbFechVenda;
	}

	public String getDescEmbFechVenda() {
		return descEmbFechVenda;
	}

	public void setDescEmbFechVenda(String descEmbFechVenda) {
		this.descEmbFechVenda = descEmbFechVenda;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

}