package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorOrdSepLote implements Serializable {
	
	private static final long serialVersionUID = -9118960797554536867L;

	@Id
	private Integer id; 
	
	private Integer empresaId;
	
	private Integer produtoLoteId;
	private Integer produtoId;
	private String produtoCodRef;
	private String produtoDesc;
	
	private Integer depositoId;
	private Integer localidadeId;
	private String localidadeDesc;
	
	private String codLote;
	private Date dtVencLote;
	private String unidade;
	
	private Double qtdDisponivel;
	private Double qtdReservada;
	
	
	public ColetorOrdSepLote() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getEmpresaId() {
		return empresaId;
	}


	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}


	public Integer getProdutoLoteId() {
		return produtoLoteId;
	}


	public void setProdutoLoteId(Integer produtoLoteId) {
		this.produtoLoteId = produtoLoteId;
	}


	public Integer getProdutoId() {
		return produtoId;
	}


	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}


	public String getProdutoCodRef() {
		return produtoCodRef;
	}


	public void setProdutoCodRef(String produtoCodRef) {
		this.produtoCodRef = produtoCodRef;
	}


	public String getProdutoDesc() {
		return produtoDesc;
	}


	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}


	public Integer getDepositoId() {
		return depositoId;
	}


	public void setDepositoId(Integer depositoId) {
		this.depositoId = depositoId;
	}


	public Integer getLocalidadeId() {
		return localidadeId;
	}


	public void setLocalidadeId(Integer localidadeId) {
		this.localidadeId = localidadeId;
	}


	public String getLocalidadeDesc() {
		return localidadeDesc;
	}


	public void setLocalidadeDesc(String localidadeDesc) {
		this.localidadeDesc = localidadeDesc;
	}


	public String getCodLote() {
		return codLote;
	}


	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}


	public Date getDtVencLote() {
		return dtVencLote;
	}


	public void setDtVencLote(Date dtVencLote) {
		this.dtVencLote = dtVencLote;
	}


	public String getUnidade() {
		return unidade;
	}


	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}


	public Double getQtdDisponivel() {
		return qtdDisponivel;
	}


	public void setQtdDisponivel(Double qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}


	public Double getQtdReservada() {
		return qtdReservada;
	}


	public void setQtdReservada(Double qtdReservada) {
		this.qtdReservada = qtdReservada;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorOrdSepLote other = (ColetorOrdSepLote) obj;
		return Objects.equals(id, other.id);
	}
	
	
	

}


