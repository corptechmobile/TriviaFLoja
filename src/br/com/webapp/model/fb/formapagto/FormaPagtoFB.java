package br.com.webapp.model.fb.formapagto;

import java.io.Serializable;

//@Entity
public class FormaPagtoFB implements Serializable {
	
	private static final long serialVersionUID = 6815090585869583111L;

	public static final Integer FORMAPAGTO_AVISTA = 0;
	public static final Integer FORMAPAGTO_NOTAPROMISSORIA = 1;
	public static final Integer FORMAPAGTO_BANCARIA = 2;

	public static final int INATIVO = 0;
	public static final int ATIVO = 1;
	
//	@Id
	private Integer id;
	
//	@Column
	private String descricao;
	
	public FormaPagtoFB() { }
	
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

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof FormaPagtoFB) ) return false;
        final FormaPagtoFB o = (FormaPagtoFB) other;
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