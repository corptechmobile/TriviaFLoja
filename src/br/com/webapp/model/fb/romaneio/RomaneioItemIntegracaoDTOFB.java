package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;

import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class RomaneioItemIntegracaoDTOFB implements Serializable{

	private static final long serialVersionUID = -758358393552995068L;

	private Integer id_romaneio;
	private Integer id_ordemcarreg;
	private Integer id_ordemcarregitem;
	private Integer quantidade;
	
	public RomaneioItemIntegracaoDTOFB() {}

	public Integer getId_romaneio() {
		return id_romaneio;
	}

	public void setId_romaneio(Integer id_romaneio) {
		this.id_romaneio = id_romaneio;
	}

	public Integer getId_ordemcarreg() {
		return id_ordemcarreg;
	}

	public void setId_ordemcarreg(Integer id_ordemcarreg) {
		this.id_ordemcarreg = id_ordemcarreg;
	}

	public Integer getId_ordemcarregitem() {
		return id_ordemcarregitem;
	}

	public void setId_ordemcarregitem(Integer id_ordemcarregitem) {
		this.id_ordemcarregitem = id_ordemcarregitem;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	
	
}
