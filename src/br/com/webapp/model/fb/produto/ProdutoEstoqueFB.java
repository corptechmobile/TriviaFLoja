package br.com.webapp.model.fb.produto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;

//@Entity
public class ProdutoEstoqueFB implements Serializable {

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
    
    private Integer qtdDecimal;
    private Double qtdVendaAtac;
    private Double qtdDisponivel;
    private Integer permiteVendaSemEstoque;
    
    @Transient
    private Double qtdReservar;
    
    @Transient
    private Integer volume; // TODO volume reservado
    
    @Transient
    private Integer volumeDisponivel;
    
    @Transient
    private String qtdDisponivelToString;
    
	public ProdutoEstoqueFB(){}
	
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
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}
	
	public Double getQtdVendaAtac() {
		if(qtdVendaAtac==null || qtdVendaAtac==0.0) {
			qtdVendaAtac = 1.0;
		}
		return qtdVendaAtac;
	}

	public void setQtdVendaAtac(Double qtdVendaAtac) {
		this.qtdVendaAtac = qtdVendaAtac;
	}

	public Double getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(Double qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}
	
	public Integer getPermiteVendaSemEstoque() {
		return permiteVendaSemEstoque;
	}

	public void setPermiteVendaSemEstoque(Integer permiteVendaSemEstoque) {
		this.permiteVendaSemEstoque = permiteVendaSemEstoque;
	}

	// Transient
	public String getQtdDisponivelToString() {
		qtdDisponivelToString = Funcoes.formatNumber(qtdDisponivel, null, qtdDecimal, qtdDecimal);
		return qtdDisponivelToString;
	}
	
	public void setQtdDisponivelToString(String qtdDisponivelToString) {
		this.qtdDisponivelToString = qtdDisponivelToString;
	}
	
	public Integer getVolume() {
		try {
			Double varVolume = Funcoes.arrendondaValor(0, (qtdReservar / getQtdVendaAtac()));
			volume = varVolume.intValue();
		} catch (Exception e) {
			volume = 0;
			//e.printStackTrace();
		}
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
	public Integer getVolumeDisponivel() {
		try {
			Double varVolume = Funcoes.arrendondaValor(0, (qtdDisponivel / getQtdVendaAtac()));
			volumeDisponivel = varVolume.intValue();
		} catch (Exception e) {
			volumeDisponivel = 0;
			//e.printStackTrace();
		}
		return volumeDisponivel;
	}

	public void setVolumeDisponivel(Integer volumeDisponivel) {
		this.volumeDisponivel = volumeDisponivel;
	}

	public Double getQtdReservar() {
		return qtdReservar;
	}
	
	public void setQtdReservar(Double qtdReservar) {
		this.qtdReservar = qtdReservar;
	}
	
}
