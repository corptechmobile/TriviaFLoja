package br.com.coletor.espelho;

import br.com.coletor.model.ColetorInvContagem;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorInvContagem {
	
	private String chave;
	private Integer coletorInvId;
	private Integer usuarioId;
	private Integer produtoId;
	private String produtoNovoDesc;
	private String codBarra;
	private Double qtdUn;
	private Double qtdEmb;
	private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	private String dtLeitura;
	private Integer flagZerar; 
	
	public EspelhoColetorInvContagem() {}
	
	public EspelhoColetorInvContagem(ColetorInvContagem model) {
		super();
		this.chave = model.getChave();
		this.coletorInvId = model.getColetorInvId();
		this.usuarioId = model.getUsuarioId();
		this.produtoId = model.getProdutoId();
		this.produtoNovoDesc = model.getProdutoNovoDesc();
		this.codBarra = model.getCodBarra();
		this.qtdUn = model.getQtdUn();
		this.qtdEmb = model.getQtdEmb();
		this.qtdEmbFechVenda = model.getQtdEmbFechVenda();
		this.descEmbFechVenda = model.getDescEmbFechVenda();
		this.dtLeitura = UtilData.formatarData(model.getDtLeitura(), UtilData.FORMATO_DATA_HORA);;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getColetorInvId() {
		return coletorInvId;
	}

	public void setColetorInvId(Integer coletorInvId) {
		this.coletorInvId = coletorInvId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getProdutoNovoDesc() {
		return produtoNovoDesc;
	}

	public void setProdutoNovoDesc(String produtoNovoDesc) {
		this.produtoNovoDesc = produtoNovoDesc;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public Double getQtdUn() {
		return qtdUn;
	}

	public void setQtdUn(Double qtdUn) {
		this.qtdUn = qtdUn;
	}

	public Double getQtdEmb() {
		return qtdEmb;
	}

	public void setQtdEmb(Double qtdEmb) {
		this.qtdEmb = qtdEmb;
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

	public String getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(String dtLeitura) {
		this.dtLeitura = dtLeitura;
	}

	public Integer getFlagZerar() {
		return flagZerar;
	}

	public void setFlagZerar(Integer flagZerar) {
		this.flagZerar = flagZerar;
	}

}
