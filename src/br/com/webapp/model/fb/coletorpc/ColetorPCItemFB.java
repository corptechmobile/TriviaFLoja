package br.com.webapp.model.fb.coletorpc;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class ColetorPCItemFB implements Serializable{

	private static final long serialVersionUID = 7482447247576742832L;
	
	private Integer id;
	private Integer coletorId;
	private String produtoCod;
	private Integer unidadeId;
	private String unidadeDesc;
	private Integer produtoId;
	private String produtoDesc;
	private Double quantidade;
	private Double qtdLeitura;
	private Double qtdAvaria;
	private Double qtdDevolvida;
	private String codLote;
	private Date dtVencLot;
	private Date dtConfIni;
	private int controlaLote;
	private Double leituras;
	private Double avarias;	
	private Integer shelfLife;
	private Double percAceitaShelfLife;
	private Integer localidadeId;
	private Integer qtdDecimal;
	
	@Transient
	private Double totLeituras;
	
	@Transient
	private Double tolShelfLife;	
	
	@Transient
	private Integer produtoPrazo;
	
	@Transient
	private Double percProdPrazo;

	public ColetorPCItemFB() {}
	
	public Double getQtdLeitura() {
		return qtdLeitura;
	}

	public void setQtdLeitura(Double qtdLeitura) {
		this.qtdLeitura = qtdLeitura;
	}

	public Double getQtdAvaria() {
		return qtdAvaria;
	}

	public void setQtdAvaria(Double qtdAvaria) {
		this.qtdAvaria = qtdAvaria;
	}

	public Double getQtdDevolvida() {
		return qtdDevolvida;
	}

	public void setQtdDevolvida(Double qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
	}

	public String getCodLote() {
		return codLote;
	}

	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}

	public Date getDtVencLot() {
		return dtVencLot;
	}

	public void setDtVencLot(Date dtVencLot) {
		this.dtVencLot = dtVencLot;
	}

	public Date getDtConfIni() {
		return dtConfIni;
	}

	public void setDtConfIni(Date dtConfIni) {
		this.dtConfIni = dtConfIni;
	}

	public int getControlaLote() {
		return controlaLote;
	}

	public void setControlaLote(int controlaLote) {
		this.controlaLote = controlaLote;
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

	public Integer getLocalidadeId() {
		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId) {
		this.localidadeId = localidadeId;
	}
	
	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getColetorId() {
		return coletorId;
	}
	public void setColetorId(Integer coletorId) {
		this.coletorId = coletorId;
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
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	public String getProdutoDesc() {
		return produtoDesc;
	}
	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getProdutoCod() {
		return produtoCod;
	}
	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}
	
	public Integer getProdutoPrazo() {
		produtoPrazo = null;
		if(dtConfIni != null && dtVencLot != null){
			produtoPrazo = UtilData.daysBetweenDates(dtConfIni, dtVencLot);
		}
		return produtoPrazo;
	}
	
	public Double getTotLeituras() {
		return (qtdLeitura+qtdAvaria+qtdDevolvida);
	}

	public void setTotLeituras(Double totLeituras) {
		this.totLeituras = totLeituras;
	}

	public void setProdutoPrazo(Integer produtoPrazo) {
		this.produtoPrazo = produtoPrazo;
	}
	
	public Double getPercProdPrazo() {
		percProdPrazo = 0d;
		if(produtoPrazo != null && produtoPrazo > 0 && tolShelfLife != null && tolShelfLife > 0){
			percProdPrazo = Funcoes.percentual(tolShelfLife.doubleValue(), produtoPrazo.doubleValue());
		}
		return percProdPrazo;
	}
	
	public void setPercProdPrazo(Double percProdPrazo) {
		this.percProdPrazo = percProdPrazo;
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


	@Override
	public int hashCode() {
		return Objects.hash(coletorId, id, produtoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorPCItemFB other = (ColetorPCItemFB) obj;
		return Objects.equals(coletorId, other.coletorId) && Objects.equals(id, other.id)
				&& Objects.equals(produtoId, other.produtoId);
	}

}
