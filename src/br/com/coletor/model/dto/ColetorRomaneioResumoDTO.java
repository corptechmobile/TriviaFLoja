package br.com.coletor.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ColetorRomaneioResumoDTO {
	
	@Id
	private Integer romaneioItemId;
	
	private Integer romaneioId;
	private Integer produtoId;
	private Double qtdRomaneio;
	private Double qtdConferida;
	
	@Transient
	private Double qtdAjuste;
	
	public ColetorRomaneioResumoDTO() {}

	public Integer getRomaneioItemId() {
		return romaneioItemId;
	}

	public void setRomaneioItemId(Integer romaneioItemId) {
		this.romaneioItemId = romaneioItemId;
	}
	
	public Integer getRomaneioId() {
		return romaneioId;
	}

	public void setRomaneioId(Integer romaneioId) {
		this.romaneioId = romaneioId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Double getQtdRomaneio() {
		return qtdRomaneio;
	}

	public void setQtdRomaneio(Double qtdRomaneio) {
		this.qtdRomaneio = qtdRomaneio;
	}

	public Double getQtdConferida() {
		return qtdConferida;
	}

	public void setQtdConferida(Double qtdConferida) {
		this.qtdConferida = qtdConferida;
	}

	public Double getQtdAjuste() {
		qtdAjuste = getQtdRomaneio() - getQtdConferida();
		return qtdAjuste;
	}

}