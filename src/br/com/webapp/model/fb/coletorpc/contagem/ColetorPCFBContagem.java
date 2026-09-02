package br.com.webapp.model.fb.coletorpc.contagem;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

public class ColetorPCFBContagem implements Serializable{

	private static final long serialVersionUID = -7698007711921143901L;
	
	private Integer coletorId;
	private Integer usuarioId;
	private String usuarioNome;
	private String chave;
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private Integer unidadeId;
	private String unidadeDesc;
	private String codBarras;
	private String codLote;
	private Date dtVencLote;
	private Date dtLeitura;
	private boolean excluida;
	private Integer shelfLife;
	private Integer controlalote;
	private Double qtdNF;
	private Double qtdConferida;
	private Double qtdDevolvida;
	private Double qtdAvaria;
	private Double percAceitaShelfLife;
	private Double leituras;
	private Double avarias;
	private Integer qtdDecimal;

	@Transient
	private Double tolShelfLife;	
	
	public ColetorPCFBContagem() {}
	
	public Integer getColetorId() {
		return coletorId;
	}

	public void setColetorId(Integer coletorId) {
		this.coletorId = coletorId;
	}


	public Integer getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getChave() {
		return chave;
	}
	
	public void setChave(String chave) {
		this.chave = chave;
	}
	
	public Integer getProdutoId() {
		return produtoId;
	}
	
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getProdutoCod() {
		return produtoCod;
	}

	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}

	public String getProdutoDesc() {
		return produtoDesc;
	}

	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}

	public Integer getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}

	public String getUnidadeDesc() {
		return unidadeDesc;
	}

	public void setUnidadeDesc(String unidadeDesc) {
		this.unidadeDesc = unidadeDesc;
	}

	public String getCodBarras() {
		return codBarras;
	}
	
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	
	public Double getQtdNF() {
		return qtdNF;
	}

	public void setQtdNF(Double qtdNF) {
		this.qtdNF = qtdNF;
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
	
	public Date getDtVencLote() {
		return dtVencLote;
	}
	
	public void setDtVencLote(Date dtVencLote) {
		this.dtVencLote = dtVencLote;
	}
	
	public Date getDtLeitura() {
		return dtLeitura;
	}
	
	public void setDtLeitura(Date dtLeitura) {
		this.dtLeitura = dtLeitura;
	}
	
	public boolean isExcluida() {
		return excluida;
	}
	
	public void setExcluida(boolean excluida) {
		this.excluida = excluida;
	}

	public Integer getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}

	public Double getPercAceitaShelfLife() {
		return percAceitaShelfLife;
	}

	public void setPercAceitaShelfLife(Double percAceitaShelfLife) {
		this.percAceitaShelfLife = percAceitaShelfLife;
	}
	
	public Integer getControlalote() {
		return controlalote;
	}

	public void setControlalote(Integer controlalote) {
		this.controlalote = controlalote;
	}

	public Double getLeituras() {
		return leituras;
	}

	public void setLeituras(Double leituras) {
		this.leituras = leituras;
	}

	public Double getAvarias() {
		return avarias;
	}

	public void setAvarias(Double avarias) {
		this.avarias = avarias;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public Double getTolShelfLife() {
		tolShelfLife = null;
		if(percAceitaShelfLife > 0.0 && shelfLife > 0){
			tolShelfLife = (percAceitaShelfLife * shelfLife) / 100;
		}
		return tolShelfLife;
	}
	
	public void setTolShelfLife(Double tolShelfLife) {
		this.tolShelfLife = tolShelfLife;
	}

}