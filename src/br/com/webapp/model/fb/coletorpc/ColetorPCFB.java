package br.com.webapp.model.fb.coletorpc;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Transient;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;

public class ColetorPCFB implements Serializable {

	private static final long serialVersionUID = 5724540951189732587L;
	public static final String STATUS_EM_ABERTO = "A";
	public static final String STATUS_LIBERADO = "L";
	public static final String STATUS_EM_CONFERENCIA = "C";
	public static final String STATUS_FINALIZADO = "F";
	public static final String STATUS_INTEGRADA = "I";
	public static final String STATUS_EXCLUIDO = "E";
	
	private Integer id;
	private Integer idErp;
	private Set<ColetorPCItemFB> item;
	private Integer empresaId;
	private Integer fornecedorId;
	private Integer usuarioId;
	private String status;
	private Date dtInicio;
	private Date dtTermino;
	private Date dtCriacao;
	private Date dtLiberacao;
	private Boolean informarLote;
	private Boolean integrada;

	@Transient
	private EmpresaFB empresa;

	@Transient
	private FornecedorFB fornecedor;

	public ColetorPCFB() {}

	public Set<ColetorPCItemFB> getItem() {
		return item;
	}

	public void setItem(Set<ColetorPCItemFB> item) {
		this.item = item;
	}

	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	public FornecedorFB getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorFB fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdErp() {
		return idErp;
	}

	public void setIdErp(Integer idErp) {
		this.idErp = idErp;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public Integer getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtTermino() {
		return dtTermino;
	}

	public void setDtTermino(Date dtTermino) {
		this.dtTermino = dtTermino;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtLiberacao() {
		return dtLiberacao;
	}

	public void setDtLiberacao(Date dtLiberacao) {
		this.dtLiberacao = dtLiberacao;
	}

	public Boolean getInformarLote() {
		return informarLote;
	}

	public void setInformarLote(Boolean informarLote) {
		this.informarLote = informarLote;
	}

	public Boolean getIntegrada() {
		return integrada;
	}

	public void setIntegrada(Boolean integrada) {
		this.integrada = integrada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		ColetorPCFB other = (ColetorPCFB) obj;
		return Objects.equals(id, other.id);
	}

	

}
