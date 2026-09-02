package br.com.coletor.espelho;

import br.com.webapp.model.fb.produtocb.ProdutoCBFB;
import br.com.webapp.web.util.UtilData;

public class EspelhoProdutoCB {
	
	private Integer produtoId;
	private String codigoBarras;
	private Double qtd;
	private String dtCreate;
	private String dtUpdate;
	private boolean excluido;
	
	public EspelhoProdutoCB() {}
	
	public EspelhoProdutoCB(ProdutoCBFB model) {
		super();
		this.produtoId = model.getId().getProdutoId();
		this.codigoBarras = model.getId().getCodigoBarras();
		this.qtd = model.getQtd();
		this.excluido = model.isExcluido();
		
		this.dtCreate = UtilData.formatarData(model.getDtCreate(), UtilData.FORMATO_DATA_HORA);
		this.dtUpdate = UtilData.formatarData(model.getDtUpdate(), UtilData.FORMATO_DATA_HORA);
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public String getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(String dtCreate) {
		this.dtCreate = dtCreate;
	}

	public String getDtUpdate() {
		return dtUpdate;
	}

	public void setDtUpdate(String dtUpdate) {
		this.dtUpdate = dtUpdate;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	
}