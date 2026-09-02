package br.com.webapp.model.fb.coletorpc;

import java.io.Serializable;
import java.util.Date;

public class ColetorPCDivergFB implements Serializable{
	
	private static final long serialVersionUID = 7804807564848164239L;

	private Integer coletorId; 
	private Integer divergenciaId; 
	private String divergenciaDesc;
	private Date dtAprovacao;
	private Date dtCreate;
	private Date dtUpdate;
	private Integer usuarioAprovacaoId;
	private String usuarioLogin;
	private String usuarioNome;
	
	public ColetorPCDivergFB() {}

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

	public Integer getColetorId() {
		return coletorId;
	}

	public void setColetorId(Integer coletorId) {
		this.coletorId = coletorId;
	}

	public Date getDtAprovacao() {
		return dtAprovacao;
	}

	public void setDtAprovacao(Date dtAprovacao) {
		this.dtAprovacao = dtAprovacao;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Date getDtUpdate() {
		return dtUpdate;
	}

	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
	}

	public Integer getUsuarioAprovacaoId() {
		return usuarioAprovacaoId;
	}

	public void setUsuarioAprovacaoId(Integer usuarioAprovacaoId) {
		this.usuarioAprovacaoId = usuarioAprovacaoId;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}	
	
}
