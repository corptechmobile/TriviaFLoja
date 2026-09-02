package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.webapp.web.util.UtilData;

public class ECFVendasPeriodo implements Serializable{

	private static final long serialVersionUID = 7224187485995459828L;
	
	private Integer dia;
	private Integer hora;
	private Integer numClientes; 
	private Double numVendas;
	
	//Transient
	private String diaSemana;
	private Double percClientesDia;
	private Double mediaClientesDia;
	private Double percNumVendasDia;
	private Double mediaNumVendasDia;
	
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public Integer getNumClientes() {
		return numClientes;
	}
	public void setNumClientes(Integer numClientes) {
		this.numClientes = numClientes;
	}
	public Double getNumVendas() {
		return numVendas;
	}
	public void setNumVendas(Double numVendas) {
		this.numVendas = numVendas;
	}
	public Double getPercClientesDia() {
		return percClientesDia;
	}
	public void setPercClientesDia(Double percClientesDia) {
		this.percClientesDia = percClientesDia;
	}
	public Double getMediaClientesDia() {
		return mediaClientesDia;
	}
	public void setMediaClientesDia(Double mediaClientesDia) {
		this.mediaClientesDia = mediaClientesDia;
	}
	public Double getPercNumVendasDia() {
		return percNumVendasDia;
	}
	public void setPercNumVendasDia(Double percNumVendasDia) {
		this.percNumVendasDia = percNumVendasDia;
	}
	public Double getMediaNumVendasDia() {
		return mediaNumVendasDia;
	}
	public void setMediaNumVendasDia(Double mediaNumVendasDia) {
		this.mediaNumVendasDia = mediaNumVendasDia;
	}
	public String getDiaSemana() {
		diaSemana = UtilData.diaSemanaFirebirdToString(dia);
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
}
