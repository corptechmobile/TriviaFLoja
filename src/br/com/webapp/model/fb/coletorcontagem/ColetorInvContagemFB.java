package br.com.webapp.model.fb.coletorcontagem;

import java.io.Serializable;
import java.util.Date;

import br.com.webapp.web.util.Funcoes;

//@Entity
public class ColetorInvContagemFB implements Serializable{
	
	private static final long serialVersionUID = -1762709718357551494L;
	
	public static final Integer EXCLUIDO = 1;
	public static final Integer ZERAR_CONTAGENS = 1;

	//	@Id
	private Integer id;
	private Integer coletorInvId;
	private Integer usuarioId;	
	private Integer produtoId;
	private String produtoCod;
	private String produtoDesc;
	private String usuarioDesc;
	private String chave;
	private Double qtdUn;
	private Double qtdEmb;
	private Double qtdConv;
	private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	private String codBarra;
	private Date dtLeitura;
	private Date dtErp;
	private boolean excluido;
	
	public ColetorInvContagemFB() { }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColetorInvId() {
		return coletorInvId;
	}

	public void setColetorInvId(Integer coletorInvId) {
		this.coletorInvId = coletorInvId;
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

	public String getUsuarioDesc() {
		return usuarioDesc;
	}

	public void setUsuarioDesc(String usuarioDesc) {
		this.usuarioDesc = usuarioDesc;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Double getQtdUn() {
		return qtdUn;
	}

	public void setQtdUn(Double qtdUn) {
		this.qtdUn = qtdUn;
	}

	public Double getQtdEmb() {
		return qtdEmb;
	}

	public void setQtdEmb(Double qtdEmb) {
		this.qtdEmb = qtdEmb;
	}

	public Double getQtdConv() {
		return qtdConv;
	}

	public void setQtdConv(Double qtdConv) {
		this.qtdConv = qtdConv;
	}

	public Double getQtdEmbFechVenda() {
		return qtdEmbFechVenda;
	}

	public void setQtdEmbFechVenda(Double qtdEmbFechVenda) {
		this.qtdEmbFechVenda = qtdEmbFechVenda;
	}

	public String getDescEmbFechVenda() {
		return descEmbFechVenda;
	}

	public void setDescEmbFechVenda(String descEmbFechVenda) {
		this.descEmbFechVenda = descEmbFechVenda;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public Date getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(Date dtLeitura) {
		this.dtLeitura = dtLeitura;
	}

	public Date getDtErp() {
		return dtErp;
	}

	public void setDtErp(Date dtErp) {
		this.dtErp = dtErp;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}


}
