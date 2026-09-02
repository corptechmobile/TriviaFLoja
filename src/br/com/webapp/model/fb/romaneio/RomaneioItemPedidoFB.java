package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class RomaneioItemPedidoFB implements Serializable{

	private static final long serialVersionUID = -758358393552995068L;

	private Integer romaneioItemPedidoId;
	private Integer romaneioId;
	private Integer procTranspItemId;
	private Integer ordemCarregItemId;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private String unidadeDesc;
	private Double qtdPti;
	private Double qtdOci;
	private Double qtdRetirada;
	
	public RomaneioItemPedidoFB() {}

	public Integer getRomaneioItemPedidoId() {
			return romaneioItemPedidoId;
	}

	public void setRomaneioItemPedidoId(Integer romaneioItemPedidoId) {
		this.romaneioItemPedidoId = romaneioItemPedidoId;
	}

	public Integer getRomaneioId() {
		return romaneioId;
	}

	public void setRomaneioId(Integer romaneioId) {
		this.romaneioId = romaneioId;
	}

	public Integer getProcTranspItemId() {
		return procTranspItemId;
	}

	public void setProcTranspItemId(Integer procTranspItemId) {
		this.procTranspItemId = procTranspItemId;
	}

	public Integer getOrdemCarregItemId() {
		return ordemCarregItemId;
	}

	public void setOrdemCarregItemId(Integer ordemCarregItemId) {
		this.ordemCarregItemId = ordemCarregItemId;
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

	public Double getQtdPti() {
		return qtdPti;
	}

	public void setQtdPti(Double qtdPti) {
		this.qtdPti = qtdPti;
	}

	public Double getQtdOci() {
		return qtdOci;
	}

	public void setQtdOci(Double qtdOci) {
		this.qtdOci = qtdOci;
	}

	public Double getQtdRetirada() {
		return qtdRetirada;
	}

	public void setQtdRetirada(Double qtdRetirada) {
		this.qtdRetirada = qtdRetirada;
	}

}
