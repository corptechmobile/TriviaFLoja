package br.com.webapp.model.fb.movfisctipo;

import java.io.Serializable;

//@Entity
public class MovFiscTipoFB implements Serializable {
	
	private static final long serialVersionUID = 4529135497797748600L;

	public static final Integer IMP_FISCAL_TIPO_CUPOMFISCAL_E_NFE = 0;
	public static final Integer IMP_FISCAL_TIPO_NOTAFISCAL = 1;
	public static final Integer VENDA_CONSULMIDOR = 1;
	public static final Integer IMP_FISCAL_TIPO_CUPOMFISCAL = 4;
	public static final Integer LANCACAR_GERAFINANCEIRO = 1;
	public static final Integer ETAPA_GERADUPLICATA_EMISSNF = 3;
	
//	@Id
	private Integer id;
	private String descricao;
	
	private Integer opFiscTipoId;
	private String opFiscTipoDesc;
	
	private Integer impFiscalTipo;
	private Integer lancaCarCap;
	private Integer etapaLancaCar;
	
	public MovFiscTipoFB() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOpFiscTipoId() {
		return opFiscTipoId;
	}

	public void setOpFiscTipoId(Integer opFiscTipoId) {
		this.opFiscTipoId = opFiscTipoId;
	}

	public String getOpFiscTipoDesc() {
		return opFiscTipoDesc;
	}

	public void setOpFiscTipoDesc(String opFiscTipoDesc) {
		this.opFiscTipoDesc = opFiscTipoDesc;
	}
	
	public Integer getImpFiscalTipo() {
		return impFiscalTipo;
	}

	public void setImpFiscalTipo(Integer impFiscalTipo) {
		this.impFiscalTipo = impFiscalTipo;
	}

	public Integer getLancaCarCap() {
		return lancaCarCap;
	}

	public void setLancaCarCap(Integer lancaCarCap) {
		this.lancaCarCap = lancaCarCap;
	}

	public Integer getEtapaLancaCar() {
		return etapaLancaCar;
	}

	public void setEtapaLancaCar(Integer etapaLancaCar) {
		this.etapaLancaCar = etapaLancaCar;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof MovFiscTipoFB) ) return false;
        final MovFiscTipoFB o = (MovFiscTipoFB) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId();
        return result;
    }
	
	@Override
	public String toString() {
		return this.getDescricao();
	}

}
