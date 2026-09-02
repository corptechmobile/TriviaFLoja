package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class RomaneioIntegracaoDTOFB implements Serializable{

	private static final long serialVersionUID = 6809304531626489930L;

	private Integer ordemcarregId;
	private Integer status;
	private Date momentoFimSeparacao;
	private String mensagem;
	
	public RomaneioIntegracaoDTOFB() {}


	public Integer getOrdemcarregId() {
		return ordemcarregId;
	}


	public void setOrdemcarregId(Integer ordemcarregId) {
		this.ordemcarregId = ordemcarregId;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getMomentoFimSeparacao() {
		return momentoFimSeparacao;
	}

	public void setMomentoFimSeparacao(Date momentoFimSeparacao) {
		this.momentoFimSeparacao = momentoFimSeparacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	

	
	
}
