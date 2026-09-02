package br.com.webapp.model.fb.coletorpc;

import java.io.Serializable;
import java.util.Objects;

public class ColetorDivergenciaFB  implements Serializable {

	private static final long serialVersionUID = -3241764910537738600L;
	
	public static final int QTDNF_QTDCONFERENCIA = 1;
	public static final int AVARIA = 2;
	public static final int SHEL_FLIFE = 3;
	public static final int FINAL_DE_MES = 4;
	public static final int QTD_DEVOLUCAO_VENDA_QTDCONFERENCIA = 5;
	
	private Integer divergenciaId; 
	private Integer usuarioId;
	private String divergenciaDesc;

	
	
	public ColetorDivergenciaFB() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getDivergenciaId() {
		return divergenciaId;
	}
	public void setDivergenciaId(Integer divergenciaId) {
		this.divergenciaId = divergenciaId;
	}
	public String getDivergenciaDesc() {
		return divergenciaDesc;
	}
	public void setDivergenciaDesc(String divergenciaDesc) {
		this.divergenciaDesc = divergenciaDesc;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(divergenciaId, usuarioId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorDivergenciaFB other = (ColetorDivergenciaFB) obj;
		return Objects.equals(divergenciaId, other.divergenciaId) && Objects.equals(usuarioId, other.usuarioId);
	}
	
	
	
	
		
}
