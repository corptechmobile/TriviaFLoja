package br.com.webapp.model.fb.relatorio.vendaforn.pedvenda;

import java.io.Serializable;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;

public class VendaFornPedVenda implements Serializable{

	private static final long serialVersionUID = -4009524747609094688L;
	
	public static final String TIPO_PEDIDO = "Pedido";
	public static final String TIPO_CUPOM = "Cupom";
	
	private Integer id;
	private String tipo;
	private String cliente;
	private Double qtde;
	private String un;
	private Double preco; 
	private Double valor;
	private Double desconto;
	private String condPagto;
	private Integer encomenda;
	private String encomendaToString;
	
	public VendaFornPedVenda() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Double getQtde() {
		return qtde;
	}
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public String getCondPagto() {
		return condPagto;
	}
	public void setCondPagto(String condPagto) {
		this.condPagto = condPagto;
	}
	public Integer getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(Integer encomenda) {
		this.encomenda = encomenda;
	}
	public String getEncomendaToString() {
		if(encomenda.equals(PedVendaFB.PEDIDO)) {
			encomendaToString = "Pedido";
		}else if(encomenda.equals(PedVendaFB.ENCOMENDA)) {
			encomendaToString = "Encomenda";
		}else if(encomenda.equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			encomendaToString = "Composto";
		}
		return encomendaToString;
	}

	public void setEncomendaToString(String encomendaToString) {
		this.encomendaToString = encomendaToString;
	}
}
