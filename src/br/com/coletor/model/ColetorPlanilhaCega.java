package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorPlanilhaCega implements Serializable {
	
	private static final long serialVersionUID = -4591308995779289541L;
	
	public static final String STATUS_DIGITACAO = "D";
	public static final String STATUS_LIBERADO = "L";
	public static final String STATUS_EM_CONFERENCIA = "C";
	public static final String STATUS_FINALIZADO = "F";
	public static final String STATUS_EXCLUIDO = "E";
	
	@Id
	private Integer id;

	private Integer empresaId;
	private String empresaNomeFant;
	
	private Integer fornecedorId;
	private String fornecedorCnpj;
	private String fornecedorNomeFant;
	
	private String status;
	private Date dtInicio;
	private Date dtTermino;
	private Date dtCriacao;
	private Boolean informarLote;
	
	public ColetorPlanilhaCega(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}
	
	public String getEmpresaNomeFant() {
		return empresaNomeFant;
	}

	public void setEmpresaNomeFant(String empresaNomeFant) {
		this.empresaNomeFant = empresaNomeFant;
	}

	public Integer getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
	
	public String getFornecedorCnpj() {
		return fornecedorCnpj;
	}

	public void setFornecedorCnpj(String fornecedorCnpj) {
		this.fornecedorCnpj = fornecedorCnpj;
	}
	
	public String getFornecedorNomeFant() {
		return fornecedorNomeFant;
	}

	public void setFornecedorNomeFant(String fornecedorNomeFant) {
		this.fornecedorNomeFant = fornecedorNomeFant;
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

	public Boolean getInformarLote() {
		return informarLote;
	}

	public void setInformarLote(Boolean informarLote) {
		this.informarLote = informarLote;
	}
	
}