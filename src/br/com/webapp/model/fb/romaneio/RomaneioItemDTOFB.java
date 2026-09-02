package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;

import javax.persistence.Column;

public class RomaneioItemDTOFB implements Serializable{

	private static final long serialVersionUID = -758358393552995068L;

	private Integer romaneioItemId;
	private Integer romaneioId;
	private Integer pedVendaId;
	private Integer romaneioItemPedidoId;
	private Integer clienteId;
	private String clienteTipo;
	private String clienteDesc;
	private String cnpjCpf;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private String unidadeDesc;
	private Double qtdRomaneio;
	private Double qtdConferida;
	private Double qtdAjuste;
	private Double qtdPedido;
	private Double qtdRetirada;
	private Integer qtdDecimal;
	
	public RomaneioItemDTOFB() {}

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

	public Integer getRomaneioItemPedidoId() {
		return romaneioItemPedidoId;
	}

	public void setRomaneioItemPedidoId(Integer romaneioItemPedidoId) {
		this.romaneioItemPedidoId = romaneioItemPedidoId;
	}

	public Integer getPedVendaId() {
		return pedVendaId;
	}

	public void setPedVendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteTipo() {
		return clienteTipo;
	}

	public void setClienteTipo(String clienteTipo) {
		this.clienteTipo = clienteTipo;
	}

	public String getClienteDesc() {
		return clienteDesc;
	}

	public void setClienteDesc(String clienteDesc) {
		this.clienteDesc = clienteDesc;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
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

	public void setQtdAjuste(Double qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}

	public Double getQtdPedido() {
		return qtdPedido;
	}

	public void setQtdPedido(Double qtdPedido) {
		this.qtdPedido = qtdPedido;
	}

	public Double getQtdRetirada() {
		return qtdRetirada;
	}

	public void setQtdRetirada(Double qtdRetirada) {
		this.qtdRetirada = qtdRetirada;
	}

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}
	
}
