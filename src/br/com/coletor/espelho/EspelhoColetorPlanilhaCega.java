package br.com.coletor.espelho;

import java.util.List;

import br.com.coletor.model.ColetorPlanilhaCega;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.UtilData;

public class EspelhoColetorPlanilhaCega {
	
	private Integer id;
	
	private Integer empresaId;
	private String empresaNomeFant;
	
	private Integer fornecedorId;
	private String fornecedorCnpj;
	private String fornecedorNomeFant;
	
	private String status;
	private String dtInicio;
	private String dtTermino;
	private String dtCriacao;
	private Boolean informarLote;
	
	private List<EspelhoColetorPlanilhaCegaItem> itens;
	
	public EspelhoColetorPlanilhaCega() {}
	
	public EspelhoColetorPlanilhaCega(ColetorPlanilhaCega model) {
		super();
		this.id = model.getId();
		this.empresaId = model.getEmpresaId();
		this.empresaNomeFant = model.getEmpresaNomeFant();
		this.fornecedorId = model.getFornecedorId();
		this.fornecedorCnpj = Funcoes.formatCnpjCpfCep(model.getFornecedorCnpj());
		this.fornecedorNomeFant = model.getFornecedorNomeFant();
		this.status = model.getStatus();
		this.dtInicio = UtilData.formatarData(model.getDtInicio(), UtilData.FORMATO_DATA_HORA);
		this.dtTermino = UtilData.formatarData(model.getDtTermino(), UtilData.FORMATO_DATA_HORA);
		this.dtCriacao = UtilData.formatarData(model.getDtCriacao(), UtilData.FORMATO_DATA_HORA);
		this.informarLote = model.getInformarLote();
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

	public String getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}

	public String getDtTermino() {
		return dtTermino;
	}

	public void setDtTermino(String dtTermino) {
		this.dtTermino = dtTermino;
	}

	public String getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(String dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Boolean getInformarLote() {
		return informarLote;
	}

	public void setInformarLote(Boolean informarLote) {
		this.informarLote = informarLote;
	}

	public List<EspelhoColetorPlanilhaCegaItem> getItens() {
		return itens;
	}

	public void setItens(List<EspelhoColetorPlanilhaCegaItem> itens) {
		this.itens = itens;
	}

}