package br.com.webapp.model.fb.diasuteis;

import java.io.Serializable;

public class DiasUteisFB implements Serializable {
	
	private static final long serialVersionUID = 4687136334320406524L;
	
	private String anoMes;
	private int diasNaoUteisMes;
	private int diasNaoUteisDtAtual;
	private int totalDiasMes;
	private int diasUteis;
	private int prazoDecorrido;
	
	// Transient
	private String anoMesToString;

	public DiasUteisFB() {}

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public int getDiasNaoUteisMes() {
		return diasNaoUteisMes;
	}

	public void setDiasNaoUteisMes(int diasNaoUteisMes) {
		this.diasNaoUteisMes = diasNaoUteisMes;
	}

	public int getDiasNaoUteisDtAtual() {
		return diasNaoUteisDtAtual;
	}

	public void setDiasNaoUteisDtAtual(int diasNaoUteisDtAtual) {
		this.diasNaoUteisDtAtual = diasNaoUteisDtAtual;
	}

	public int getTotalDiasMes() {
		return totalDiasMes;
	}

	public void setTotalDiasMes(int totalDiasMes) {
		this.totalDiasMes = totalDiasMes;
	}

	public int getDiasUteis() {
		return diasUteis;
	}

	public void setDiasUteis(int diasUteis) {
		this.diasUteis = diasUteis;
	}

	public int getPrazoDecorrido() {
		return prazoDecorrido;
	}

	public void setPrazoDecorrido(int prazoDecorrido) {
		this.prazoDecorrido = prazoDecorrido;
	}
	
	// Transient
	public String getAnoMesToString() {
		if(anoMes!=null) {
			anoMesToString = anoMes.substring(4, 6) + "/" + anoMes.substring(0, 4);
		}
		return anoMesToString;
	}

	public void setAnoMesToString(String anoMesToString) {
		this.anoMesToString = anoMesToString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoMes == null) ? 0 : anoMes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiasUteisFB other = (DiasUteisFB) obj;
		if (anoMes == null) {
			if (other.anoMes != null)
				return false;
		} else if (!anoMes.equals(other.anoMes))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
