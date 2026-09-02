package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class RomaneioItemFB implements Serializable{

	private static final long serialVersionUID = -758358393552995068L;

	private Integer romaneioItemId;
	private Integer romaneioId;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private String unidadeDesc;
	private Double qtdRomaneio;
	private Double qtdConferida;
	private Double qtdAjuste;
	private Double qtdRetirada;
	private Double qtdContagem;
	private Double leituras;
	
		public RomaneioItemFB() {}

	public Integer getRomaneioItemId() {
		return romaneioItemId;
	}

	public void setRomaneioItemId(Integer romaneioItemId) {
		this.romaneioItemId = romaneioItemId;
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

	public String getUnidadeDesc() {
		return unidadeDesc;
	}

	public void setUnidadeDesc(String unidadeDesc) {
		this.unidadeDesc = unidadeDesc;
	}

	public Double getQtdRomaneio() {
		return qtdRomaneio;
	}

	public void setQtdRomaneio(Double qtdRomaneio) {
		this.qtdRomaneio = qtdRomaneio;
	}

	public Double getQtdConferida() {
		return qtdConferida;
	}

	public void setQtdConferida(Double qtdConferida) {
		this.qtdConferida = qtdConferida;
	}

	public Double getQtdAjuste() {
		return qtdAjuste;
	}

	public Double getQtdRetirada() {
		return qtdRetirada;
	}

	public void setQtdRetirada(Double qtdRetirada) {
		this.qtdRetirada = qtdRetirada;
	}

	public void setQtdAjuste(Double qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}

	public Double getQtdContagem() {
		return qtdContagem;
	}

	public void setQtdContagem(Double qtdContagem) {
		this.qtdContagem = qtdContagem;
	}

	public Double getLeituras() {
		return leituras;
	}

	public void setLeituras(Double leituras) {
		this.leituras = leituras;
	}
	
}
