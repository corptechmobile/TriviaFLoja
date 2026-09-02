package br.com.webapp.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "calculadoraPisoTesteBean")
@SessionScoped
public class CalculadoraPisoTesteBean implements Serializable{

	private static final long serialVersionUID = -1748202943149499557L;
	
	@ManagedProperty(value="#{calcularPisoBean}")
	private CalcularPisoBean calcularPisoBean;
	
	private Double qtdVendaAtac;
	
	public void novo() {
		calcularPisoBean.novo();
		calcularPisoBean.setQtdVendaAtac(2.02);
		calcularPisoBean.setQtdDecimal(2);
	}

	public Double getQtdVendaAtac() {
		return qtdVendaAtac;
	}

	public void setQtdVendaAtac(Double qtdVendaAtac) {
		this.qtdVendaAtac = qtdVendaAtac;
	}

	public CalcularPisoBean getCalcularPisoBean() {
		return calcularPisoBean;
	}

	public void setCalcularPisoBean(CalcularPisoBean calcularPisoBean) {
		this.calcularPisoBean = calcularPisoBean;
	}

	
	
}
