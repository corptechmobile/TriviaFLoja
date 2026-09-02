package br.com.webapp.model.fb.planilhacegafirebird;

import java.io.Serializable;

public class PlanilhaCegaFirebird implements Serializable {
	
	private static final long serialVersionUID = -429934990796827479L;
	
	public static final Integer ID_PESSOA_CONF = 15427;
	public static final Integer ID_USUARIO = 1;
	public static final Integer ID_USUARIO_GERADOR = 1;
	public static final Integer CONFRONTADA = 0;
	public static final Integer FINALIZADA = 0;
	
	private Integer id;
	private Integer pessoaConf;
	private Integer usuario;
	private String dataConferencia;
	private Integer confrontada;
	private Integer finalizada;
	private Integer usuarioGerador;
	private String momentoGeracao;
	
	public PlanilhaCegaFirebird(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPessoaConf() {
		return pessoaConf;
	}

	public void setPessoaConf(Integer pessoaConf) {
		this.pessoaConf = pessoaConf;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public String getDataConferencia() {
		return dataConferencia;
	}

	public void setDataConferencia(String dataConferencia) {
		this.dataConferencia = dataConferencia;
	}

	public Integer getConfrontada() {
		return confrontada;
	}

	public void setConfrontada(Integer confrontada) {
		this.confrontada = confrontada;
	}

	public Integer getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Integer finalizada) {
		this.finalizada = finalizada;
	}

	public Integer getUsuarioGerador() {
		return usuarioGerador;
	}

	public void setUsuarioGerador(Integer usuarioGerador) {
		this.usuarioGerador = usuarioGerador;
	}

	public String getMomentoGeracao() {
		return momentoGeracao;
	}

	public void setMomentoGeracao(String momentoGeracao) {
		this.momentoGeracao = momentoGeracao;
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
		PlanilhaCegaFirebird other = (PlanilhaCegaFirebird) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
