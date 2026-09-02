package br.com.webapp.model.fb.produtocb;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.webapp.model.fb.produto.Produto;

@Entity
@Table(name = "produtocb")
public class ProdutoCBFB implements Serializable {
	
	private static final long serialVersionUID = 6896466218859112040L;
	
	@EmbeddedId
	private ProdutoCBFBId id;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto", updatable = false, insertable = false)
	private Produto produto;
	
	@Column(name = "qtd")
	private Double qtd;
	
	@Column(name = "dt_create")
	private Date dtCreate;
	
	@Column(name = "dt_update")
	private Date dtUpdate;
	
	@Column(name = "id_usuario_create")
	private Integer usuarioCreateId;
	
	@Column(name = "id_usuario_update")
	private Integer usuarioUpdateId;
	
	@Column(name = "excluido")
	private boolean excluido;
	
	public ProdutoCBFB() {}

	public ProdutoCBFBId getId() {
		return id;
	}

	public void setId(ProdutoCBFBId id) {
		this.id = id;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}
	
	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Date getDtUpdate() {
		return dtUpdate;
	}

	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
	}

	public Integer getUsuarioCreateId() {
		return usuarioCreateId;
	}

	public void setUsuarioCreateId(Integer usuarioCreateId) {
		this.usuarioCreateId = usuarioCreateId;
	}

	public Integer getUsuarioUpdateId() {
		return usuarioUpdateId;
	}

	public void setUsuarioUpdateId(Integer usuarioUpdateId) {
		this.usuarioUpdateId = usuarioUpdateId;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
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
		ProdutoCBFB other = (ProdutoCBFB) obj;
		return Objects.equals(id, other.id);
	}

}
