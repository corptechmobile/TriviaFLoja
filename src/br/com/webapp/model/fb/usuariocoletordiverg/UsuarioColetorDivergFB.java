package br.com.webapp.model.fb.usuariocoletordiverg;

import java.io.Serializable;

public class UsuarioColetorDivergFB implements Serializable{

	private static final long serialVersionUID = 2188667098918731002L;
	
	private Integer usuarioId; 
	private Integer divergenciaId;
	
	public UsuarioColetorDivergFB(){}
	
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Integer getDivergenciaId() {
		return divergenciaId;
	}
	public void setDivergenciaId(Integer divergenciaId) {
		this.divergenciaId = divergenciaId;
	}
	
}
