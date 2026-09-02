package br.com.webapp.model.fb.infogerproduto;

import java.io.Serializable;
import java.util.List;

import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBDTO;
import br.com.webapp.web.util.Funcoes;

//@Entity
public class InfoGerProdutoFB implements Serializable {
	
	private static final long serialVersionUID = 1204474708490551594L;
	
	//@Id
	private Integer produtoId;
	private Double custoMedioOnLine;
	private Double custoMedio;
	private Double custoGerAtual;
	
	public InfoGerProdutoFB() {}

	
	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Double getCustoMedioOnLine() {
		return custoMedioOnLine;
	}

	public void setCustoMedioOnLine(Double custoMedioOnLine) {
		this.custoMedioOnLine = custoMedioOnLine;
	}

	public Double getCustoMedio() {
		return custoMedio;
	}

	public void setCustoMedio(Double custoMedio) {
		this.custoMedio = custoMedio;
	}

	public Double getCustoGerAtual() {
		return custoGerAtual;
	}

	public void setCustoGerAtual(Double custoGerAtual) {
		this.custoGerAtual = custoGerAtual;
	}


}
