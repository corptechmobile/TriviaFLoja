package br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg;

import java.io.Serializable;
import java.util.Date;

import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFB;
import br.com.webapp.web.converter.CurrencyFormat;
import br.com.webapp.web.util.Funcoes;

public class PedVendaDivergRel implements Serializable{

	private static final long serialVersionUID = -7784610683119630678L;
	
	private Integer pedVendaId;
	
	private Integer condPagtoId;
	private String condPagto;
	
	private Integer empresaId;
	private String empresa;
	
	private Integer vendedorId;
	private String vendedor;
	
	private Integer clienteId;
	private String cliente;
	
	private Integer usuarioId;
	private String usuario;
	
	private Integer produtoId;//Pode vir nulo
	private String produtoCod;
	private String produto;
	
	private Double desconto;
	
	private Integer tipoDiverg;
	
	private Integer situacaoDiverg;
	
	private String observacao;
	
	private Date entrada;
	
	private Date interacao;
	
	//Transient
	private String situacaoDivergToString;
	
	//Transient
	private String encomendaToString;
	
	//Transient
	private String tipoDivergToString;

	public Integer getPedVendaId() {
		return pedVendaId;
	}

	public void setPedVendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
	}

	public Integer getCondPagtoId() {
		return condPagtoId;
	}

	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}

	public String getCondPagto() {
		return condPagto;
	}

	public void setCondPagto(String condPagto) {
		this.condPagto = condPagto;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Integer getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Integer vendedorId) {
		this.vendedorId = vendedorId;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getTipoDiverg() {
		return tipoDiverg;
	}

	public void setTipoDiverg(Integer tipoDiverg) {
		this.tipoDiverg = tipoDiverg;
	}

	public Integer getSituacaoDiverg() {
		return situacaoDiverg;
	}

	public void setSituacaoDiverg(Integer situacaoDiverg) {
		this.situacaoDiverg = situacaoDiverg;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getInteracao() {
		return interacao;
	}

	public void setInteracao(Date interacao) {
		this.interacao = interacao;
	}

	public String getSituacaoDivergToString() {
		return situacaoDivergToString;
	}

	public void setSituacaoDivergToString(String situacaoDivergToString) {
		this.situacaoDivergToString = situacaoDivergToString;
	}

	public String getEncomendaToString() {
		return encomendaToString;
	}

	public void setEncomendaToString(String encomendaToString) {
		this.encomendaToString = encomendaToString;
	}

	public String getTipoDivergToString() {
		if (this.getTipoDiverg().equals(PedVendaDivergFB.DIVERGENCIA_POR_DESCONTO)) {
			this.tipoDivergToString = "Inclusão de desconto " + "<b>" + Funcoes.formatNumber(this.getDesconto(), null, 2, 2) + "%" + "</b>";
			if (this.getProdutoCod() != null) {
				this.tipoDivergToString += " no produto " + "<b>" + this.getProdutoCod() + " - " + this.getProduto() + "</b>";
			}
		}else if (this.getTipoDiverg().equals(PedVendaDivergFB.DIVERGENCIA_POR_LOTES_DIFERENTES)) {
			this.tipoDivergToString = "Lotes diferentes no produto " +"<b>" + this.getProdutoCod() + " - " + this.getProduto() + "</b>";
		}else if (this.getTipoDiverg().equals(PedVendaDivergFB.DIVERGENCIA_POR_VENDA_SEM_ESTOQUE_DISP)) {
			this.tipoDivergToString = "Encomenda de produto " + "<b>" + this.getProdutoCod() + " - " + this.getProduto() + "</b>";
		}
		return tipoDivergToString;
	}

	public void setTipoDivergToString(String tipoDivergToString) {
		this.tipoDivergToString = tipoDivergToString;
	}
}
