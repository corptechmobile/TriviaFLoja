package br.com.webapp.model.fb.produto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;

//@Entity
public class ProdutoEstoqueLoteFB implements Serializable {

	private static final long serialVersionUID = -2854245913701917960L;
	
//	@Id
    private Integer depositoId;
	private String depositoDesc;
	
//	@Id    
    private Integer empresaId;
    private String empresaDesc;
    
//    @Id
    private Integer localidadeId;
    private String localidadeDesc;
    
//    @Id
    private Integer produtoId;
    
//    @Id
    private Integer produtoLoteId;
    private String codLote;
    private Date dtVencLote;
    private Date dtProducao;
    
    private Double qtdTotal;
    private Double qtdEmpresa;
    private Double qtdVendido;
    private Double qtdDisponivel;
    private Double qtdReservado;
    private Double qtdBloqueado;
    
	public ProdutoEstoqueLoteFB(){}
	
	// gets e sets
	public Integer getDepositoId() {
		return depositoId;
	}

	public void setDepositoId(Integer depositoId) {
		this.depositoId = depositoId;
	}

	public String getDepositoDesc() {
		return depositoDesc;
	}

	public void setDepositoDesc(String depositoDesc) {
		this.depositoDesc = depositoDesc;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public String getEmpresaDesc() {
		return empresaDesc;
	}

	public void setEmpresaDesc(String empresaDesc) {
		this.empresaDesc = empresaDesc;
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
	
	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getProdutoLoteId() {
		return produtoLoteId;
	}

	public void setProdutoLoteId(Integer produtoLoteId) {
		this.produtoLoteId = produtoLoteId;
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
	
	public Date getDtProducao() {
		return dtProducao;
	}

	public void setDtProducao(Date dtProducao) {
		this.dtProducao = dtProducao;
	}

	public Double getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(Double qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}

	public Double getQtdTotal() {
		return qtdTotal;
	}

	public void setQtdTotal(Double qtdTotal) {
		this.qtdTotal = qtdTotal;
	}

	public Double getQtdEmpresa() {
		return qtdEmpresa;
	}

	public void setQtdEmpresa(Double qtdEmpresa) {
		this.qtdEmpresa = qtdEmpresa;
	}

	public Double getQtdVendido() {
		return qtdVendido;
	}

	public void setQtdVendido(Double qtdVendido) {
		this.qtdVendido = qtdVendido;
	}

	public Double getQtdReservado() {
		return qtdReservado;
	}

	public void setQtdReservado(Double qtdReservado) {
		this.qtdReservado = qtdReservado;
	}

	public Double getQtdBloqueado() {
		return qtdBloqueado;
	}

	public void setQtdBloqueado(Double qtdBloqueado) {
		this.qtdBloqueado = qtdBloqueado;
	}
	
}
