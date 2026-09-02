package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorOrdSepItemContagem implements Serializable {
	
	private static final long serialVersionUID = -7358377805556772849L;
	
	@Id
	private Integer id;
	private String chave;
	private Integer ordemSeparacaoId;
	private Integer produtoId;
	private Integer usuarioId;
	private String codBarra;
	private Double qtd;
	private String codLote;
	private String dtVencLote;
	private Date dtLeitura;
	
	public ColetorOrdSepItemContagem(){}

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
	

	public Integer getOrdemSeparacaoId() {
		return ordemSeparacaoId;
	}

	public void setOrdemSeparacaoId(Integer ordemSeparacaoId) {
		this.ordemSeparacaoId = ordemSeparacaoId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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

	public Date getDtLeitura() {
		return dtLeitura;
	}

	public void setDtLeitura(Date dtLeitura) {
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColetorOrdSepItemContagem other = (ColetorOrdSepItemContagem) obj;
		return Objects.equals(id, other.id);
	}

}