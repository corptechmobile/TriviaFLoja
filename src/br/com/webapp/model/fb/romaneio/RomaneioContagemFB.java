package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;

public class RomaneioContagemFB implements Serializable {

	private static final long serialVersionUID = -7757511054106442191L;

	private Integer id;
	private String chave;
	private Integer romaneioId;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private Integer usuarioId;
	private String usuarioNome;
	private String codBarra;
	private Double qtd;
	private Integer unidadeId;
	private String unidadeDesc;
	private Date dtLeitura;
	private Integer qtdDecimal;
	private Double leituras;

	public RomaneioContagemFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getRomaneioId() {
		return romaneioId;
	}

	public void setRomaneioId(Integer romaneioId) {
		this.romaneioId = romaneioId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public Integer getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}

	public String getUnidadeDesc() {
		return unidadeDesc;
	}

	public void setUnidadeDesc(String unidadeDesc) {
		this.unidadeDesc = unidadeDesc;
	}

	public Date getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(Date dtLeitura) {
		this.dtLeitura = dtLeitura;
	}

	public Double getLeituras() {
		return leituras;
	}

	public void setLeituras(Double leituras) {
		this.leituras = leituras;
	}

	public String getProdutoCod() {
		return produtoCod;
	}

	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}

	public String getProdutoDesc() {
		return produtoDesc;
	}

	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

}
