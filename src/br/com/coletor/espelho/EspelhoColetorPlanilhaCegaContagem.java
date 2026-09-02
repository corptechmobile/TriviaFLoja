package br.com.coletor.espelho;

import br.com.coletor.model.ColetorPlanilhaCegaContagem;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorPlanilhaCegaContagem {
	
	private String chave;
	private Integer coletorPlanilhaCegaId;
	private Integer usuarioId;
	private Integer produtoId;
	private String codBarra;
	private Double qtdConferida;
	private Double qtdDevolvida;
	private Double qtdAvaria;
	private String codLote;
	private String dtVencLote;
	private String dtLeitura;
	
	public EspelhoColetorPlanilhaCegaContagem() {}
	
	public EspelhoColetorPlanilhaCegaContagem(ColetorPlanilhaCegaContagem model) {
		super();
		this.chave = model.getChave();
		this.coletorPlanilhaCegaId = model.getColetorPlanilhaCegaId();
		this.usuarioId = model.getUsuarioId();
		this.produtoId = model.getProdutoId();
		this.codBarra = model.getCodBarra();
		this.qtdConferida = model.getQtdConferida();
		this.qtdDevolvida = model.getQtdDevolvida();
		this.qtdAvaria = model.getQtdAvaria();
		this.codLote = model.getCodLote();
		this.dtVencLote = UtilData.formatarData(model.getDtVencLote(), UtilData.FORMATO_DATA_HORA);;
		this.dtLeitura = UtilData.formatarData(model.getDtLeitura(), UtilData.FORMATO_DATA_HORA);;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getColetorPlanilhaCegaId() {
		return coletorPlanilhaCegaId;
	}

	public void setColetorPlanilhaCegaId(Integer coletorPlanilhaCegaId) {
		this.coletorPlanilhaCegaId = coletorPlanilhaCegaId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public Double getQtdConferida() {
		return qtdConferida;
	}

	public void setQtdConferida(Double qtdConferida) {
		this.qtdConferida = qtdConferida;
	}

	public Double getQtdDevolvida() {
		return qtdDevolvida;
	}

	public void setQtdDevolvida(Double qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
	}

	public Double getQtdAvaria() {
		return qtdAvaria;
	}

	public void setQtdAvaria(Double qtdAvaria) {
		this.qtdAvaria = qtdAvaria;
	}

	public String getCodLote() {
		return codLote;
	}

	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}

	public String getDtVencLote() {
		return dtVencLote;
	}

	public void setDtVencLote(String dtVencLote) {
		this.dtVencLote = dtVencLote;
	}

	public String getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(String dtLeitura) {
		this.dtLeitura = dtLeitura;
	}

}