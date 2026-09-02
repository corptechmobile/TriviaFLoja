package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas;

import java.io.Serializable;
import java.util.Date;

public class ECFVendasFB implements Serializable{

	private static final long serialVersionUID = -7897136824143905421L;
	
	private Integer idECFERP;
	private Integer idECFPDV;
	private Integer idECFPONTOPDV;
	private Integer numNFCe;
	private String serieNFCe;
	private Date dtVenda;
	private Double valor;
	
	// transient
	private String numNFCeToString;
	
	public Integer getIdECFERP() {
		return idECFERP;
	}
	public void setIdECFERP(Integer idECFERP) {
		this.idECFERP = idECFERP;
	}
	public Integer getIdECFPDV() {
		return idECFPDV;
	}
	public void setIdECFPDV(Integer idECFPDV) {
		this.idECFPDV = idECFPDV;
	}
	public Integer getIdECFPONTOPDV() {
		return idECFPONTOPDV;
	}
	public void setIdECFPONTOPDV(Integer idECFPONTOPDV) {
		this.idECFPONTOPDV = idECFPONTOPDV;
	}
	public Integer getNumNFCe() {
		return numNFCe;
	}
	public void setNumNFCe(Integer numNFCe) {
		this.numNFCe = numNFCe;
	}
	public String getSerieNFCe() {
		return serieNFCe;
	}
	public void setSerieNFCe(String serieNFCe) {
		this.serieNFCe = serieNFCe;
	}
	public Date getDtVenda() {
		return dtVenda;
	}
	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	// transients 
	public String getNumNFCeToString() {
		if(numNFCe!=null) {
			numNFCeToString = String.format("%09d", numNFCe);
		}
		return numNFCeToString;
	}
	public void setNumNFCeToString(String numNFCeToString) {
		this.numNFCeToString = numNFCeToString;
	}
	
}
