package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ColetorRomaneio implements Serializable {

	private static final long serialVersionUID = 161660754854192238L;
	
	public static final String STATUS_EM_ABERTO = "0";
	public static final String STATUS_EM_CONFERENCIA = "1";
	public static final String STATUS_CONFERIDO = "2";
	public static final String STATUS_CONFERIDO_CORTE = "3";
	public static final String STATUS_FINALIZADO = "4";
	public static final String STATUS_CANCELADO = "5";
	
	@Id
	private Integer id;
	
	private Integer procTranspId;
	private Integer empresaId;
	private String empresaNomeFant;
	
	private String status;
	
	@Transient
	private String statusDesc;
	
	private Date dtInicio;
	private Date dtTermino;
	private Date dtCorte;
	private Date dtCancelado;
	private Date dtCriacao;

	private Integer usuarioCriacaoId;
	private String usuarioCriacaoNome;
	
	private Integer usuarioConfId;
	private String usuarioConfNome;
	
	private Integer usuarioCorteId;
	private String usuarioCorteNome;
	
	private Integer usuarioTerminoId;
	private String usuarioTerminoNome;
	
	private Integer usuarioCanceladoId;
	private String usuarioCanceladoNome;
	
	public ColetorRomaneio() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProcTranspId() {
		return procTranspId;
	}

	public void setProcTranspId(Integer procTranspId) {
		this.procTranspId = procTranspId;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public String getEmpresaNomeFant() {
		return empresaNomeFant;
	}

	public void setEmpresaNomeFant(String empresaNomeFant) {
		this.empresaNomeFant = empresaNomeFant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtTermino() {
		return dtTermino;
	}

	public void setDtTermino(Date dtTermino) {
		this.dtTermino = dtTermino;
	}

	public Date getDtCorte() {
		return dtCorte;
	}

	public void setDtCorte(Date dtCorte) {
		this.dtCorte = dtCorte;
	}

	public Date getDtCancelado() {
		return dtCancelado;
	}

	public void setDtCancelado(Date dtCancelado) {
		this.dtCancelado = dtCancelado;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Integer getUsuarioCriacaoId() {
		return usuarioCriacaoId;
	}

	public void setUsuarioCriacaoId(Integer usuarioCriacaoId) {
		this.usuarioCriacaoId = usuarioCriacaoId;
	}

	public String getUsuarioCriacaoNome() {
		return usuarioCriacaoNome;
	}

	public void setUsuarioCriacaoNome(String usuarioCriacaoNome) {
		this.usuarioCriacaoNome = usuarioCriacaoNome;
	}

	public Integer getUsuarioConfId() {
		return usuarioConfId;
	}

	public void setUsuarioConfId(Integer usuarioConfId) {
		this.usuarioConfId = usuarioConfId;
	}

	public String getUsuarioConfNome() {
		return usuarioConfNome;
	}

	public void setUsuarioConfNome(String usuarioConfNome) {
		this.usuarioConfNome = usuarioConfNome;
	}

	public Integer getUsuarioCorteId() {
		return usuarioCorteId;
	}

	public void setUsuarioCorteId(Integer usuarioCorteId) {
		this.usuarioCorteId = usuarioCorteId;
	}

	public String getUsuarioCorteNome() {
		return usuarioCorteNome;
	}

	public void setUsuarioCorteNome(String usuarioCorteNome) {
		this.usuarioCorteNome = usuarioCorteNome;
	}

	public Integer getUsuarioTerminoId() {
		return usuarioTerminoId;
	}

	public void setUsuarioTerminoId(Integer usuarioTerminoId) {
		this.usuarioTerminoId = usuarioTerminoId;
	}

	public String getUsuarioTerminoNome() {
		return usuarioTerminoNome;
	}

	public void setUsuarioTerminoNome(String usuarioTerminoNome) {
		this.usuarioTerminoNome = usuarioTerminoNome;
	}

	public Integer getUsuarioCanceladoId() {
		return usuarioCanceladoId;
	}

	public void setUsuarioCanceladoId(Integer usuarioCanceladoId) {
		this.usuarioCanceladoId = usuarioCanceladoId;
	}

	public String getUsuarioCanceladoNome() {
		return usuarioCanceladoNome;
	}

	public void setUsuarioCanceladoNome(String usuarioCanceladoNome) {
		this.usuarioCanceladoNome = usuarioCanceladoNome;
	}
	
	// Transient
	public String getStatusDesc() {
		if(this.status.equals(STATUS_EM_ABERTO)) {
			statusDesc = "Em Aberto";
		}else if(this.status.equals(STATUS_EM_CONFERENCIA)) {
			statusDesc = "Em Conferência";
		}else if(this.status.equals(STATUS_CONFERIDO)) {
			statusDesc = "Conferido";
		}else if(this.status.equals(STATUS_CONFERIDO_CORTE)) {
			statusDesc = "Conferido com Corte";
		}else if(this.status.equals(STATUS_FINALIZADO)) {
			statusDesc = "Finalizado";
		}else if(this.status.equals(STATUS_CANCELADO)) {
			statusDesc = "Cancelado";
		}
		return statusDesc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorRomaneio other = (ColetorRomaneio) obj;
		return Objects.equals(id, other.id);
	}
	
}