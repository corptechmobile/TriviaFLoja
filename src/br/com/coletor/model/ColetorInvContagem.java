package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorInvContagem implements Serializable {

	private static final long serialVersionUID = 4241846380379259985L;
	
	public static final String STATUS_EM_ABERTO = "A";
	public static final String STATUS_FINALIZADO = "F";
	
	@Id
	private Integer id;

	private String chave;
	private Integer coletorInvId;
	private Integer usuarioId;
	private Integer produtoId;
	private String produtoNovoDesc;
	private String codBarra;
	private Double qtdUn;
	private Double qtdEmb;
	private Double qtdEmbFechVenda;
	private String descEmbFechVenda;
	private Date dtLeitura;
	
	public ColetorInvContagem(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
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

	public String getProdutoNovoDesc() {
		return produtoNovoDesc;
	}

	public void setProdutoNovoDesc(String produtoNovoDesc) {
		this.produtoNovoDesc = produtoNovoDesc;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
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

	public Date getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(Date dtLeitura) {
		this.dtLeitura = dtLeitura;
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
		ColetorInvContagem other = (ColetorInvContagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
