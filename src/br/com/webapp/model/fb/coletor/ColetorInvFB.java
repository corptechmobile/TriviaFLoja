package br.com.webapp.model.fb.coletor;

import java.io.Serializable;
import java.util.Date;

public class ColetorInvFB implements Serializable{
	
	private static final long serialVersionUID = -7874112490286503404L;

	public static final String STATUS_ABERTO = "A";
	public static final String STATUS_EMPROCESSAMENTO = "P";
	public static final String STATUS_FINALIZADO = "F";
	public static final String STATUS_EXCLUIDO = "E";

	private Integer id;
	private Integer empresaId;
	private String empresaDesc;
	private Integer usuarioId;
	private String usuarioDesc;
	private String descricao;
	private Date dtInicio;
	private Date dtTermino;
	private Date dtCriacao;
	private String status;
	
	public ColetorInvFB() { }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioDesc() {
		return usuarioDesc;
	}

	public void setUsuarioDesc(String usuarioDesc) {
		this.usuarioDesc = usuarioDesc;
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

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ColetorInvFB other = (ColetorInvFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}

}
