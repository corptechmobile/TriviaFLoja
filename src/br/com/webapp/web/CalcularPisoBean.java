package br.com.webapp.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "calcularPisoBean")
@SessionScoped
public class CalcularPisoBean implements Serializable{

	private static final long serialVersionUID = -7190775009338869587L;

	private Integer volume; 
	private Double qtdVendaAtac; 
	private String altLargOuAreaTotal;
	private Integer qtdDecimal;
	private Double total;

	private Double areaTotal;
	private Double comprimento;
	private Double largura;
	
	private boolean dezPorcento;
	private boolean renderedResultado;
	
	@PostConstruct
	public void init() {
		System.out.println("[CalcularPisoBean][verAltLargOuAreaTotal][init]");
		novo();
	}
	
	public void changeRadio() {
		comprimento = 0.0;
		largura = 0.0;
		areaTotal = 0.0;
		renderedResultado = false;
		dezPorcento = false;
	} 
	
	public void calcularCompAlt() {
		try {
			areaTotal = comprimento * largura;
			calcularCaixas(areaTotal);
			renderedResultado = true;
		} catch (Exception e) {
			renderedResultado = false;
			e.printStackTrace();
		}
	} 
	
	public void calcularAreaTotal() {
		try {
			calcularCaixas(areaTotal);
			renderedResultado = true;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			renderedResultado = false;
			e.printStackTrace();
		}
	} 
	
	private void calcularCaixas(Double area) {
		
		volume = 0;
		double caixas = Math.ceil(area/qtdVendaAtac);
		
		total = caixas * qtdVendaAtac;
		volume = (int) caixas;
		
		if(dezPorcento) {
			volume = (int) Math.ceil(volume * 1.10);
		}
		
		total = volume * qtdVendaAtac;
		
//		String resultado = "Para " + Funcoes.formatNumber(area, null, qtdDecimal, qtdDecimal) + " m², a quantidade mínima que você deverá adicionar ao carrinho é: "+Funcoes.formatNumber(total, null, qtdDecimal, qtdDecimal)+" (Totalizando "+volume+" caixas.)";
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", resultado));
		
		
	} 
	
	public void addDezPorcento() {
		if(altLargOuAreaTotal.equals("altLarg")) {
			calcularCompAlt();
		}else {
			calcularAreaTotal();
		}
	}
	
	public void selecionarQuantidade() { } 
	
	public void novo() {
		altLargOuAreaTotal = "altLarg";
		areaTotal = 0.0;
		largura = 0.0;
		comprimento = 0.0;
	    renderedResultado = false;
	    dezPorcento = false;
	} 
	
	// gets and sets

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Double getQtdVendaAtac() {
		return qtdVendaAtac;
	}

	public void setQtdVendaAtac(Double qtdVendaAtac) {
		this.qtdVendaAtac = qtdVendaAtac;
	}

	public String getAltLargOuAreaTotal() {
		if(altLargOuAreaTotal==null || "".equals(altLargOuAreaTotal)) {
			altLargOuAreaTotal = "altLarg";
		}
		return altLargOuAreaTotal;
	}

	public void setAltLargOuAreaTotal(String altLargOuAreaTotal) {
		this.altLargOuAreaTotal = altLargOuAreaTotal;
	}

	public Double getComprimento() {
		return comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(Double areaTotal) {
		this.areaTotal = areaTotal;
	}

	public boolean isDezPorcento() {
		return dezPorcento;
	}

	public void setDezPorcento(boolean dezPorcento) {
		this.dezPorcento = dezPorcento;
	}

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public boolean isRenderedResultado() {
		return renderedResultado;
	}

	public void setRenderedResultado(boolean renderedResultado) {
		this.renderedResultado = renderedResultado;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
