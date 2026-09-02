package br.com.coletor.espelho;

import br.com.coletor.model.ColetorPlanilhaCegaItem;

public class EspelhoColetorPlanilhaCegaItem {
	
	private Integer id;
    private Integer coletorPlanilhaCegaId;
    private Integer produtoId;
    private Integer unidCompId;
    private String unidCompDesc;
    private Double fatorUnidBasica;
    private String descFatorUnidBasica;
    
    private Boolean obrigaDescLote;
    private Boolean obrigaVencLote;
    private Boolean controlaLote;
    
    private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	
	private Integer qtdDecimal;
    
    private Double qtd;

    public EspelhoColetorPlanilhaCegaItem(){}

	public EspelhoColetorPlanilhaCegaItem(ColetorPlanilhaCegaItem model) {
		super();
		this.id = model.getId();
		this.coletorPlanilhaCegaId = model.getColetorPlanilhaCegaId();
		this.produtoId = model.getProdutoId();
		this.unidCompId = model.getUnidCompId();
		this.unidCompDesc = model.getUnidCompDesc();
		this.fatorUnidBasica = model.getFatorUnidBasica();
		this.descFatorUnidBasica = model.getDescFatorUnidBasica();
		this.obrigaDescLote = model.getObrigaDescLote();
		this.obrigaVencLote = model.getObrigaVencLote();
		this.controlaLote = model.getControlaLote();
		this.qtdEmbFechVenda = model.getQtdEmbFechVenda();
		this.descEmbFechVenda = model.getDescEmbFechVenda();
		this.qtdDecimal = model.getQtdDecimal();
		this.qtd = model.getQtd();
	}

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

	public Double getFatorUnidBasica() {
		return fatorUnidBasica;
	}

	public void setFatorUnidBasica(Double fatorUnidBasica) {
		this.fatorUnidBasica = fatorUnidBasica;
	}

	public String getDescFatorUnidBasica() {
		return descFatorUnidBasica;
	}

	public void setDescFatorUnidBasica(String descFatorUnidBasica) {
		this.descFatorUnidBasica = descFatorUnidBasica;
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