package br.com.webapp.model.fb.condpagto;

import java.io.Serializable;

import br.com.webapp.web.util.Funcoes;

//@Entity
public class CondPagtoFB implements Serializable{
	
	private static final long serialVersionUID = -7874112490286503404L;

	public static final String TIPO_VENDA = "V";
	public static final String TIPO_COMPRA = "C";

	public static final int ATIVO = 1;
	public static final int INATIVO = 0;
	
//	@Id
	private Integer id;
	private String descricao;
	private String tabPrecoId;
	private Integer parcelas;
	private Integer dispContrCred;
	
	public CondPagtoFB() { }
	
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
	
	public String getTabPrecoId() {
		return tabPrecoId;
	}

	public void setTabPrecoId(String tabPrecoId) {
		this.tabPrecoId = tabPrecoId;
	}
	
	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
	public String getParcelas(Double valor) {
		if(valor!=null && valor>0.0) {
			return parcelas + "x de " + Funcoes.formatNumber(valor/parcelas, null, 2, 2);
		}
		return "";
	}
	
	public Integer getDispContrCred() {
		return dispContrCred;
	}

	public void setDispContrCred(Integer dispContrCred) {
		this.dispContrCred = dispContrCred;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof CondPagtoFB) ) return false;
        final CondPagtoFB o = (CondPagtoFB) other;
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
