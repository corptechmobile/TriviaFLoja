package br.com.webapp.model.fb.produto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;

@Entity
@Table(name = "produto")
@org.hibernate.annotations.Entity(
		dynamicUpdate = true,
		dynamicInsert = true
)
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 563096029567970047L;

	@Id
	@Column(name = "id_produto")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_linhaproduto", nullable = true)
	private ProdutoLinhaFB produtoLinha;
	
	@Column(name = "codinterno")
	private String codInterno;
	
	@Column(name = "descricao")
	private String descricao;
	
	public Produto() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProdutoLinhaFB getProdutoLinha() {
		return produtoLinha;
	}

	public void setProdutoLinha(ProdutoLinhaFB produtoLinha) {
		this.produtoLinha = produtoLinha;
	}

	public String getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}
	
}