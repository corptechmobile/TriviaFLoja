package br.com.webapp.model.fb.cliente;

import java.util.Date;

public class ClienteNaoPositivadoFBDTO {
	
	private Integer id;
	private String descricao;
	private String tipoPessoa;
	private String cnpj;
	private String codArea;
	private String numero;
	private String cidade;
	private String bairro;
	private Date dtUltimaCompra;
	private Double maiorCompra;
	private Integer freqCompra;
	private Double mediaCompra;
	
	public ClienteNaoPositivadoFBDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodArea() {
		return codArea;
	}

	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Date getDtUltimaCompra() {
		return dtUltimaCompra;
	}

	public void setDtUltimaCompra(Date dtUltimaCompra) {
		this.dtUltimaCompra = dtUltimaCompra;
	}

	public Double getMaiorCompra() {
		return maiorCompra;
	}

	public void setMaiorCompra(Double maiorCompra) {
		this.maiorCompra = maiorCompra;
	}

	public Integer getFreqCompra() {
		return freqCompra;
	}

	public void setFreqCompra(Integer freqCompra) {
		this.freqCompra = freqCompra;
	}

	public Double getMediaCompra() {
		return mediaCompra;
	}

	public void setMediaCompra(Double mediaCompra) {
		this.mediaCompra = mediaCompra;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
}
