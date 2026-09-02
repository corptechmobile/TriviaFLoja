package br.com.coletor.espelho;

import br.com.coletor.model.ColetorRomaneioContagem;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorRomaneioContagem {
	
	private String chave;
	private Integer coletorRomaneioId;
	private Integer usuarioId;
	private Integer produtoId;
	private String codBarra;
	private Double qtd;
	private String dtLeitura;
	
	public EspelhoColetorRomaneioContagem() {}
	
	public EspelhoColetorRomaneioContagem(ColetorRomaneioContagem model) {
		super();
		this.chave = model.getChave();
		this.coletorRomaneioId = model.getRomaneioId();
		this.usuarioId = model.getUsuarioId();
		this.produtoId = model.getProdutoId();
		this.codBarra = model.getCodBarra();
		this.qtd = model.getQtd();
		this.dtLeitura = UtilData.formatarData(model.getDtLeitura(), UtilData.FORMATO_DATA_HORA);;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getColetorRomaneioId() {
		return coletorRomaneioId;
	}

	public void setColetorRomaneioId(Integer coletorRomaneioId) {
		this.coletorRomaneioId = coletorRomaneioId;
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

	public Double getQtd() {
		return qtd;
	}

	public void setQtdConferida(Double qtd) {
		this.qtd = qtd;
	}

	public String getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(String dtLeitura) {
		this.dtLeitura = dtLeitura;
	}

}