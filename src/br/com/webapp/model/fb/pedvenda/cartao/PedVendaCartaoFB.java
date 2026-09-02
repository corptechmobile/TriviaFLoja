package br.com.webapp.model.fb.pedvenda.cartao;

import java.io.Serializable;

public class PedVendaCartaoFB implements Serializable{

	private static final long serialVersionUID = 5390448136630027606L;
	
	private String nomeAdministradora;
	private Double valor;
	private Integer qtdParcela;
	
	public String getNomeAdministradora() {
		return nomeAdministradora;
	}
	public void setNomeAdministradora(String nomeAdministradora) {
		this.nomeAdministradora = nomeAdministradora;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getQtdParcela() {
		return qtdParcela;
	}
	public void setQtdParcela(Integer qtdParcela) {
		this.qtdParcela = qtdParcela;
	}
}
