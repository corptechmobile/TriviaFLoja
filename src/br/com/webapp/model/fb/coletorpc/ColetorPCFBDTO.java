package br.com.webapp.model.fb.coletorpc;

import java.io.Serializable;
import java.util.Date;

public class ColetorPCFBDTO implements Serializable{

	private static final long serialVersionUID = 4191740546931615657L;
	private Integer id;
	private Integer idErp;
	private Integer empresaId;
	private String empresaDesc;
	private String fornecedorDesc;
	private Integer fornecedorId;
	private Integer usuarioId;
	private String usuarioDesc;
	private String status;
	private Date dtInicio;
	private Date dtTermino;
	private Date dtCriacao;
	private Date dtLiberacao;
	private Boolean informarLote;
	
	public ColetorPCFBDTO() {}

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
	
	public String getEmpresaDesc() {
		return empresaDesc;
	}
	
	public void setEmpresaDesc(String empresaDesc) {
		this.empresaDesc = empresaDesc;
	}
	
	public String getFornecedorDesc() {
		return fornecedorDesc;
	}
	
	public void setFornecedorDesc(String fornecedorDesc) {
		this.fornecedorDesc = fornecedorDesc;
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
	
	public String getUsuarioDesc() {
		return usuarioDesc;
	}
	
	public void setUsuarioDesc(String usuarioDesc) {
		this.usuarioDesc = usuarioDesc;
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

}
