package br.com.webapp.model.fb.produtolinha;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "linhaproduto")
public class ProdutoLinhaFB implements Serializable {

	private static final long serialVersionUID = -1368923695921886913L;
	
	@Id
	@Column(name = "id_linhaproduto")
	private Integer id;
	
	@Column(name = "id_linhaproduto_pai")
	private Integer produtoLinhaPaiId;
	
	@Column(name = "codedt")
	private String codEDT;
	
	@Column(name = "ordem")
	private Integer ordem;
	
	@Column(name = "descricao")
	private String descricao;
	
	public ProdutoLinhaFB() {}
	
	public ProdutoLinhaFB(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProdutoLinhaPaiId() {
		return produtoLinhaPaiId;
	}

	public void setProdutoLinhaPaiId(Integer produtoLinhaPaiId) {
		this.produtoLinhaPaiId = produtoLinhaPaiId;
	}

	public String getCodEDT() {
		return codEDT;
	}

	public void setCodEDT(String codEDT) {
		this.codEDT = codEDT;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
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
        if ( !(other instanceof ProdutoLinhaFB) ) return false;
        final ProdutoLinhaFB o = (ProdutoLinhaFB) other;
        if ( !o.getId().equals( getId() ) ) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = 29 * getId();
        return result;
    }
	
}
