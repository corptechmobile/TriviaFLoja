package br.com.coletor.response;

import java.util.List;

import br.com.coletor.espelho.EspelhoProdutoCB;
import br.com.coletor.model.EmpresaColetor;
import br.com.coletor.model.ProdutoColetor;

public class SincColetorResponse {
	
	public static final String SUCESSO = "S";
	public static final String ERRO = "E";
	
	private List<EmpresaColetor> empresas;
	private List<ProdutoColetor> produtos;
	private List<EspelhoProdutoCB> produtoCBs;
	private String status;
	private String mensagem;
	
	public SincColetorResponse(){}
	
	public List<EmpresaColetor> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaColetor> empresas) {
		this.empresas = empresas;
	}

	public List<ProdutoColetor> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoColetor> produtos) {
		this.produtos = produtos;
	}
	
	public List<EspelhoProdutoCB> getProdutoCBs() {
		return produtoCBs;
	}

	public void setProdutoCBs(List<EspelhoProdutoCB> produtoCBs) {
		this.produtoCBs = produtoCBs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
