package br.com.coletor.espelho;

import br.com.coletor.model.ColetorInv;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorInv {
	
	private Integer id;
	private String descricao;
	private String status;
	private String dtInicio;
	private String dtTermino;
	private String dtCriacao;
	
	public EspelhoColetorInv() {}
	
	public EspelhoColetorInv(ColetorInv model) {
		super();
		this.id = model.getId();
		this.descricao = model.getDescricao();
		this.status = model.getStatus();
		this.dtInicio = UtilData.formatarData(model.getDtInicio(), UtilData.FORMATO_DATA_HORA);
		this.dtTermino = UtilData.formatarData(model.getDtTermino(), UtilData.FORMATO_DATA_HORA);
		this.dtCriacao = UtilData.formatarData(model.getDtCriacao(), UtilData.FORMATO_DATA_HORA);
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}

	public String getDtTermino() {
		return dtTermino;
	}

	public void setDtTermino(String dtTermino) {
		this.dtTermino = dtTermino;
	}

	public String getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(String dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
}
