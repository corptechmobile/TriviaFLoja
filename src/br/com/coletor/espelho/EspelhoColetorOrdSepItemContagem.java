package br.com.coletor.espelho;

import br.com.coletor.model.ColetorOrdSepItemContagem;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorOrdSepItemContagem {
	
	private String chave;
	private Integer ordemSeparacaoId;
	private Integer usuarioId;
	private Integer produtoId;
	private String codBarra;
	private Double qtd;
	private String codLote;
	private String dtVencLote;
	private String dtLeitura;
	
	public EspelhoColetorOrdSepItemContagem() {}
	
	public EspelhoColetorOrdSepItemContagem(ColetorOrdSepItemContagem model) {
		super();
		this.chave = model.getChave();
		this.ordemSeparacaoId = model.getOrdemSeparacaoId();
		this.usuarioId = model.getUsuarioId();
		this.produtoId = model.getProdutoId();
		this.codBarra = model.getCodBarra();
		this.qtd = model.getQtd();
		this.codLote = model.getCodLote();
		this.dtVencLote = model.getDtVencLote();
		this.dtLeitura = UtilData.formatarData(model.getDtLeitura(), UtilData.FORMATO_DATA_HORA);
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getOrdemSeparacaoId() {
		return ordemSeparacaoId;
	}

	public void setOrdemSeparacaoId(Integer ordemSeparacaoId) {
		this.ordemSeparacaoId = ordemSeparacaoId;
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

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public String getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(String dtLeitura) {
		this.dtLeitura = dtLeitura;
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
	
}