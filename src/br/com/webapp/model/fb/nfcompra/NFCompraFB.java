package br.com.webapp.model.fb.nfcompra;

import java.io.Serializable;
import java.sql.Date;


import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;


public class NFCompraFB implements Serializable {

	private static final long serialVersionUID = -2899103202393334027L;
	public static final int STATUS_DIGITADA = 0;
	public static final int STATUS_LIBERADA = 2;
	
	private Integer id; 
	private Integer empresaId;
	private Integer fornecedorId;
	private String fornecedorDesc;
	private String statusDesc;
	private String chaveAcessoNfe;
	private String numNf;
	private String serieNf;
	private Date dtEmissao;
	private Date dtCreate;
	private Date dtEntrada;
	private Double volume;
	private Integer itens;
	private Date dtCadastro;
	private String descricaoStatus;
	private Double valorTotalNf;
	
	private ColetorPCFB coletorPCFB;

	public ColetorPCFB getColetorPCFB() {
		return coletorPCFB;
	}
	
	public void setColetorPCFB(ColetorPCFB coletorPCFB) {
		this.coletorPCFB = coletorPCFB;
	}
	
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
	public Integer getFornecedorId() {
		return fornecedorId;
	}
	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
	
	public String getFornecedorDesc() {
		return fornecedorDesc;
	}
	public void setFornecedorDesc(String fornecedorDesc) {
		this.fornecedorDesc = fornecedorDesc;
	}
	public String getChaveAcessoNfe() {
		return chaveAcessoNfe;
	}
	public void setChaveAcessoNfe(String chaveAcessoNfe) {
		this.chaveAcessoNfe = chaveAcessoNfe;
	}
	public String getNumNf() {
		return numNf;
	}
	public void setNumNf(String numNf) {
		this.numNf = numNf;
	}
	public String getSerieNf() {
		return serieNf;
	}
	public void setSerieNf(String serieNf) {
		this.serieNf = serieNf;
	}
	public Double getValorTotalNf() {
		return valorTotalNf;
	}
	public void setValorTotalNf(Double valorTotalNf) {
		this.valorTotalNf = valorTotalNf;
	}
	
	public Date getDtCreate() {
		return dtCreate;
	}
	
	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}
	
	public Date getDtEntrada() {
		return dtEntrada;
	}
	
	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}
	
	public Double getVolume() {
		return volume;
	}
	
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	
	public Integer getItens() {
		return itens;
	}
	
	public void setItens(Integer itens) {
		this.itens = itens;
	}
	
	public Date getDtCadastro() {
		return dtCadastro;
	}
	
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	
	public String getDescricaoStatus() {
		return descricaoStatus;
	}
	
	public void setDescricaoStatus(String descricaoStatus) {
		this.descricaoStatus = descricaoStatus;
	}
	
	public Date getDt_create() {
		return dtCreate;
	}
	
	public void setDt_create(Date dtCreate) {
		this.dtCreate = dtCreate;
	}
	
	public Date getDtEmissao() {
		return dtEmissao;
	}
	
	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	
	public String getStatusDesc() {
		return statusDesc;
	}
	
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
}
