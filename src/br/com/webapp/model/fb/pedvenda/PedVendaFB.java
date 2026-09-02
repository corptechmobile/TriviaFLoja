package br.com.webapp.model.fb.pedvenda;

import java.io.Serializable;
import java.util.Date;

import br.com.webapp.model.fb.cobrtipo.CobrTipoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.tabpreco.TabPrecoFB;
import br.com.webapp.web.util.Funcoes;


//@Entity
public class PedVendaFB implements Serializable{

	private static final long serialVersionUID = 1169615151257434353L;
	
	public static final Integer PEDIDO_PRODUTO_COMPOSTO = 2;
	public static final Integer ENCOMENDA = 1;
	public static final Integer PEDIDO = 0;
	
	public static final Integer SITUACAO_AGUARDANDO_PAGTO = 0;
	public static final Integer SITUACAO_BLOQUEADA = 1;
	public static final Integer SITUACAO_CANCELADA = 2;
	public static final Integer SITUACAO_PAGTO_BLOQUEADO = 3;
	public static final Integer SITUACAO_LIBERADA = 4;
	public static final Integer SITUACAO_PARCIAL_ATENDIDA = 5;
	public static final Integer SITUACAO_TOTALMENTE_ATENDIDA = 6;
	public static final Integer SITUACAO_ENCERRADO = 7;
	public static final Integer SITUACAO_DIGITACAO = 8;
	public static final Integer SITUACAO_AGUARDANDO_LIBERAR = 9;
	public static final Integer SITUACAO_EM_RECEBIMENTO = 10;
	public static final Integer SITUACAO_NAO_LIBERADO = 11;
	
	
	public static final String NUMPEDCLI = "";
	public static final Double VALFRETE = 0.0;
	public static final Double VALDESPACESS = 0.0;
	public static final Integer ENTREGA = 0;
	public static final Integer BLOQPRECO = 0;
	public static final Integer BLOQCRED = 0;
	public static final Integer BLOQCAR = 0;
	public static final Integer USUARIO_LOCK_ID = null;
	public static final String IDNUMSOLEXTERNA = "";
	public static final Integer LIBERADOINTEG = 0;
	public static final Double VALORDESCONTO = 0.0;
	public static final Double VALORST = 0.0;
	public static final Double ALIQICMSDEST = 0.0;
	public static final Double VALTOTGERADODUP = 0.0;
	public static final Integer SEPARAANT = 0;
	public static final Integer TIPOPEDIDO = 0;
	public static final Integer CALCVENCDUPDATAEFET = 0;
	public static final Integer IMPRESSO = 0;
	public static final Integer ORCAMENTO = 0;
	public static final Double VALORIPI = 0.0;
	public static final Double DESCFLEX = 0.0;
	public static final Double DESCGESTAOVENDA = 0.0;
	public static final Double SALDOINICVENDEDOR = 0.0;
	public static final Integer COMPOEFLUXO = 0;
	public static final Double VALTAXAENTREGA = 0.0;
	public static final String UFEMPRESA = "PE";
	public static final Double PERCRATEIODUP = 0.0;
	public static final Integer MOEDA_ID = 0; // Default 0 = real
	public static final Double VALORCOTACAO = 0.0;
	public static final Date DATACOTACAO = null;
	public static final Integer TIPOCAMBIO = 0;
	public static final Integer TIPOENTRADAPEDIDO = 0;
	public static final Integer VALIDADECOTACAODIAS = 0;
	public static final String CONTATO = "";
	public static final String CONTATOEMAIL = "";
	public static final String CONTATOTELEFONE = "";
	public static final Integer PESSOA_VENDAORDREM_ID = null;
	public static final Date PREVCLIENTE = null;
	public static final Integer PEDN = 0;
	public static final Integer SEQ_PEDVENDA = 0;
	


	//PK
	//@Id
	private Integer id;
	
	//FKs
	private Integer clienteId;
	
	private Integer vendedorId;
	
	private Integer empresaId;
	
	private Integer condPagtoId;
	
	private Integer movFiscTipoId;
	
	private Integer freteTipoId;
	
	private String tabPrecoId;
	
	private Integer pedVendaStatusId;
	
	private Integer cobrTipoId;
	
	private Integer usuarioId;
	
	private Integer usuarioWebId;

	private Integer formaPagtoId;
	
	private Integer usuarioLockId;
	
	private Integer enderecoEntregaId;
	
	private Integer moedaId;
	
	private Integer pessoaVendaOrdemId;
	
	//Atributos
	private Date entrada;
	
	private Date conclusao;
	
	private Date efetivacao;
	
	private Date liquidacao;
	
	private Double valPedido;
	
	private String numPedCli;
	
	private String nomeCliente;
	
	private Double valFrete;
	
	private Double valDespAcess;
	
	private String observacao;
	
	private Integer entrega;
	
	private Integer bloqPreco;
	
	private Integer bloqCred;
	
	private Integer bloqCar;
	
	private Date prevRetirada;
	
	private String numSolExterna;
	
	private Integer liberadoInteg;
	
	private Double valorDesconto;
	
	private Double valorST;
	
	private String ufCli;
	
	private Double aliqICMSDest;
	
	private Double valTotGeradoDup;
	
	private Integer separaAnt;
	
	private Integer tipoPedido;
	
	private Date prevRetiradaDataHora;
	
	private Date dataUltAlteracao;
	
	private Integer seqPedVenda;
	
	private Integer calcVendDUpdataEfet;
	
	private Integer impresso;
	
	private Integer orcamento;
	
	private Double valorIPI;
	
	private String observacao2;
	
	private Double descFlex;
	
	private Double descGestaoVenda;
	
	private Double saldoInicVendedor;
	
	private Integer compoEFluxo;
	
	private Double valTaxaEntrega;
	
	private String ufEmpresa;
	
	private Double percRateioDup;
	
	private Double valorCotacao;
	
	private Date dataCotacao;
	
	private Integer tipoCambio;
	
	private Integer tipoEntradaPedido;
	
	private Integer validadeCotacaoDias;
	
	private String contato;
	
	private String contatoEmail;
	
	private String contatoTelefone;
	
	private Date prevCliente;
	
	private Integer pedn;
	
	private Integer encomenda;
	
	private Double desconto;
	
	private boolean existeOc;
	
	//@Transient
	private FreteTipoFB freteTipo;
	
	//@Transient
	private MovFiscTipoFB movFiscTipo;
	
	//@Transient
	private CondPagtoFB condPagto;
	
	//@Transient
	private TabPrecoFB tabPreco;
	
	//@Transient
	private FormaPagtoFB formaPagto;
	
	//@Transient
	private CobrTipoFB cobrTipo;
	
	//@Transient
	private Double valPedidoPrTab;
	
	//@Transient
	private Double descMedioPedido;
	
	//@Transient
	private Double pesoBrutoKg;
	
	//@Transient
	private Double volume;
	
	private Double descontoVl;
	
	//@Transient
	private boolean podeEditar;
	
	//@Transient
	private boolean podeExcluir;
	
	//@Transient
	private boolean podeConcluir;
	
	//@Transient
	private PedVendaStatusFB pedVendaStatus;
	
	//@Transient
	private Boolean isPedido;
	
	//@Transient
	private Boolean isEncomenda;
		
	//@Transient
	private Boolean isProdComposto;
	
	//@Transient
	private Integer permitePromocao;
	
	public PedVendaFB() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClienteId() {
		return clienteId;
	}
	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}
	public Integer getVendedorId() {
		return vendedorId;
	}
	public void setVendedorId(Integer vendedorId) {
		this.vendedorId = vendedorId;
	}
	public Integer getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}
	public Integer getCondPagtoId() {
		return condPagtoId;
	}
	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}
	public Integer getMovFiscTipoId() {
		return movFiscTipoId;
	}
	public void setMovFiscTipoId(Integer movFiscTipoId) {
		this.movFiscTipoId = movFiscTipoId;
	}
	public Integer getFreteTipoId() {
		return freteTipoId;
	}
	public void setFreteTipoId(Integer freteTipoId) {
		this.freteTipoId = freteTipoId;
	}
	public String getTabPrecoId() {
		return tabPrecoId;
	}
	public void setTabPrecoId(String tabPrecoId) {
		this.tabPrecoId = tabPrecoId;
	}
	public Integer getPedVendaStatusId() {
		return pedVendaStatusId;
	}
	public void setPedVendaStatusId(Integer pedVendaStatusId) {
		this.pedVendaStatusId = pedVendaStatusId;
	}
	public Integer getCobrTipoId() {
		return cobrTipoId;
	}
	public void setCobrTipoId(Integer cobrTipoId) {
		this.cobrTipoId = cobrTipoId;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Integer getUsuarioWebId() {
		return usuarioWebId;
	}

	public void setUsuarioWebId(Integer usuarioWebId) {
		this.usuarioWebId = usuarioWebId;
	}

	public Integer getFormaPagtoId() {
		return formaPagtoId;
	}
	public void setFormaPagtoId(Integer formaPagtoId) {
		this.formaPagtoId = formaPagtoId;
	}
	public Integer getUsuarioLockId() {
		return usuarioLockId;
	}
	public void setUsuarioLockId(Integer usuarioLockId) {
		this.usuarioLockId = usuarioLockId;
	}
	public Integer getEnderecoEntregaId() {
		return enderecoEntregaId;
	}
	public void setEnderecoEntregaId(Integer enderecoEntregaId) {
		this.enderecoEntregaId = enderecoEntregaId;
	}
	public Integer getMoedaId() {
		return moedaId;
	}
	public void setMoedaId(Integer moedaId) {
		this.moedaId = moedaId;
	}
	public Integer getPessoaVendaOrdemId() {
		return pessoaVendaOrdemId;
	}
	public void setPessoaVendaOrdemId(Integer pessoaVendaOrdemId) {
		this.pessoaVendaOrdemId = pessoaVendaOrdemId;
	}
	public Date getEntrada() {
		return entrada;
	}
	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}
	public Date getConclusao() {
		return conclusao;
	}
	public void setConclusao(Date conclusao) {
		this.conclusao = conclusao;
	}
	public Date getEfetivacao() {
		return efetivacao;
	}
	public void setEfetivacao(Date efetivacao) {
		this.efetivacao = efetivacao;
	}
	public Date getLiquidacao() {
		return liquidacao;
	}
	public void setLiquidacao(Date liquidacao) {
		this.liquidacao = liquidacao;
	}
	public Double getValPedido() {
		return valPedido;
	}
	public void setValPedido(Double valPedido) {
		this.valPedido = valPedido;
	}
	public String getNumPedCli() {
		return numPedCli;
	}
	
	public void setNumPedCli(String numPedCli) {
		this.numPedCli = numPedCli;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Double getValFrete() {
		return valFrete;
	}
	public void setValFrete(Double valFrete) {
		this.valFrete = valFrete;
	}
	public Double getValDespAcess() {
		return valDespAcess;
	}
	public void setValDespAcess(Double valDespAcess) {
		this.valDespAcess = valDespAcess;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getEntrega() {
		return entrega;
	}
	public void setEntrega(Integer entrega) {
		this.entrega = entrega;
	}
	public Integer getBloqPreco() {
		return bloqPreco;
	}
	public void setBloqPreco(Integer bloqPreco) {
		this.bloqPreco = bloqPreco;
	}
	public Integer getBloqCred() {
		return bloqCred;
	}
	public void setBloqCred(Integer bloqCred) {
		this.bloqCred = bloqCred;
	}
	public Integer getBloqCar() {
		return bloqCar;
	}
	public void setBloqCar(Integer bloqCar) {
		this.bloqCar = bloqCar;
	}
	public Date getPrevRetirada() {
		return prevRetirada;
	}
	public void setPrevRetirada(Date prevRetirada) {
		this.prevRetirada = prevRetirada;
	}
	public String getNumSolExterna() {
		return numSolExterna;
	}
	public void setNumSolExterna(String numSolExterna) {
		this.numSolExterna = numSolExterna;
	}
	public Integer getLiberadoInteg() {
		return liberadoInteg;
	}
	public void setLiberadoInteg(Integer liberadoInteg) {
		this.liberadoInteg = liberadoInteg;
	}
	public Double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public Double getValorST() {
		return valorST;
	}
	public void setValorST(Double valorST) {
		this.valorST = valorST;
	}
	public String getUfCli() {
		return ufCli;
	}
	public void setUfCli(String ufCli) {
		this.ufCli = ufCli;
	}
	public Double getAliqICMSDest() {
		return aliqICMSDest;
	}
	public void setAliqICMSDest(Double aliqICMSDest) {
		this.aliqICMSDest = aliqICMSDest;
	}
	public Double getValTotGeradoDup() {
		return valTotGeradoDup;
	}
	public void setValTotGeradoDup(Double valTotGeradoDup) {
		this.valTotGeradoDup = valTotGeradoDup;
	}
	public Integer getSeparaAnt() {
		return separaAnt;
	}
	public void setSeparaAnt(Integer separaAnt) {
		this.separaAnt = separaAnt;
	}
	public Integer getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(Integer tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public Date getPrevRetiradaDataHora() {
		return prevRetiradaDataHora;
	}
	public void setPrevRetiradaDataHora(Date prevRetiradaDataHora) {
		this.prevRetiradaDataHora = prevRetiradaDataHora;
	}
	public Date getDataUltAlteracao() {
		return dataUltAlteracao;
	}
	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}
	public Integer getSeqPedVenda() {
		return seqPedVenda;
	}
	public void setSeqPedVenda(Integer seqPedVenda) {
		this.seqPedVenda = seqPedVenda;
	}
	public Integer getCalcVendDUpdataEfet() {
		return calcVendDUpdataEfet;
	}
	public void setCalcVendDUpdataEfet(Integer calcVendDUpdataEfet) {
		this.calcVendDUpdataEfet = calcVendDUpdataEfet;
	}
	public Integer getImpresso() {
		return impresso;
	}
	public void setImpresso(Integer impresso) {
		this.impresso = impresso;
	}
	public Integer getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(Integer orcamento) {
		this.orcamento = orcamento;
	}
	public Double getValorIPI() {
		return valorIPI;
	}
	public void setValorIPI(Double valorIPI) {
		this.valorIPI = valorIPI;
	}
	public String getObservacao2() {
		return observacao2;
	}
	public void setObservacao2(String observacao2) {
		this.observacao2 = observacao2;
	}
	public Double getDescFlex() {
		return descFlex;
	}
	public void setDescFlex(Double descFlex) {
		this.descFlex = descFlex;
	}
	public Double getDescGestaoVenda() {
		return descGestaoVenda;
	}
	public void setDescGestaoVenda(Double descGestaoVenda) {
		this.descGestaoVenda = descGestaoVenda;
	}
	public Double getSaldoInicVendedor() {
		return saldoInicVendedor;
	}
	public void setSaldoInicVendedor(Double saldoInicVendedor) {
		this.saldoInicVendedor = saldoInicVendedor;
	}
	public Integer getCompoEFluxo() {
		return compoEFluxo;
	}
	public void setCompoEFluxo(Integer compoEFluxo) {
		this.compoEFluxo = compoEFluxo;
	}
	public Double getValTaxaEntrega() {
		return valTaxaEntrega;
	}
	public void setValTaxaEntrega(Double valTaxaEntrega) {
		this.valTaxaEntrega = valTaxaEntrega;
	}
	public Double getPercRateioDup() {
		return percRateioDup;
	}
	public void setPercRateioDup(Double percRateioDup) {
		this.percRateioDup = percRateioDup;
	}
	public Double getValorCotacao() {
		return valorCotacao;
	}
	public void setValorCotacao(Double valorCotacao) {
		this.valorCotacao = valorCotacao;
	}
	public Date getDataCotacao() {
		return dataCotacao;
	}
	public void setDataCotacao(Date dataCotacao) {
		this.dataCotacao = dataCotacao;
	}
	public Integer getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(Integer tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public Integer getTipoEntradaPedido() {
		return tipoEntradaPedido;
	}
	public void setTipoEntradaPedido(Integer tipoEntradaPedido) {
		this.tipoEntradaPedido = tipoEntradaPedido;
	}
	public Integer getValidadeCotacaoDias() {
		return validadeCotacaoDias;
	}
	public void setValidadeCotacaoDias(Integer validadeCotacaoDias) {
		this.validadeCotacaoDias = validadeCotacaoDias;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getContatoEmail() {
		return contatoEmail;
	}
	public void setContatoEmail(String contatoEmail) {
		this.contatoEmail = contatoEmail;
	}
	public String getContatoTelefone() {
		return contatoTelefone;
	}
	public void setContatoTelefone(String contatoTelefone) {
		this.contatoTelefone = contatoTelefone;
	}
	public Date getPrevCliente() {
		return prevCliente;
	}
	public void setPrevCliente(Date prevCliente) {
		this.prevCliente = prevCliente;
	}
	public Integer getPedn() {
		return pedn;
	}
	public void setPedn(Integer pedn) {
		this.pedn = pedn;
	}
	public String getUfEmpresa() {
		return ufEmpresa;
	}
	public void setUfEmpresa(String ufEmpresa) {
		this.ufEmpresa = ufEmpresa;
	}
	
	public Integer getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(Integer encomenda) {
		this.encomenda = encomenda;
	}
	
	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	
	public boolean isExisteOc() {
		return existeOc;
	}

	public void setExisteOc(boolean existeOc) {
		this.existeOc = existeOc;
	}
	
	// Transient
	
	public FreteTipoFB getFreteTipo() {
		return freteTipo;
	}

	public void setFreteTipo(FreteTipoFB freteTipo) {
		this.freteTipo = freteTipo;
	}

	public MovFiscTipoFB getMovFiscTipo() {
		return movFiscTipo;
	}

	public void setMovFiscTipo(MovFiscTipoFB movFiscTipo) {
		this.movFiscTipo = movFiscTipo;
	}

	public CondPagtoFB getCondPagto() {
		return condPagto;
	}

	public void setCondPagto(CondPagtoFB condPagto) {
		this.condPagto = condPagto;
	}
	
	public TabPrecoFB getTabPreco() {
		return tabPreco;
	}

	public void setTabPreco(TabPrecoFB tabPreco) {
		this.tabPreco = tabPreco;
	}

	public FormaPagtoFB getFormaPagto() {
		return formaPagto;
	}

	public void setFormaPagto(FormaPagtoFB formaPagto) {
		this.formaPagto = formaPagto;
	}

	public CobrTipoFB getCobrTipo() {
		return cobrTipo;
	}

	public void setCobrTipo(CobrTipoFB cobrTipo) {
		this.cobrTipo = cobrTipo;
	}
	
	public Double getValPedidoPrTab() {
		return valPedidoPrTab;
	}

	public void setValPedidoPrTab(Double valPedidoPrTab) {
		this.valPedidoPrTab = valPedidoPrTab;
	}
	
	public Double getDescMedioPedido() {
		if(valPedidoPrTab!=null) {
			descMedioPedido = Funcoes.descontoPreco(valPedidoPrTab, valPedido);
		}
		return descMedioPedido;
	}
	
	public void setDescMedioPedido(Double descMedioPedido) {
		this.descMedioPedido = descMedioPedido;
	}
	
	public Double getDescontoVl() {
		descontoVl = valPedidoPrTab - valPedido;
		return descontoVl;
	}

	public void setDescontoVl(Double descontoVl) {
		this.descontoVl = descontoVl;
	}

	public Double getPesoBrutoKg() {
		return pesoBrutoKg;
	}

	public void setPesoBrutoKg(Double pesoBrutoKg) {
		this.pesoBrutoKg = pesoBrutoKg;
	}

	public boolean isPodeEditar() {
		podeEditar = false;
		if(this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_DIGITACAO) 
						|| this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO) 
							|| this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_NAO_LIBERADO)
								//|| (this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_LIBERADA) 
								//		&& this.getMovFiscTipo() != null 
								//			&& this.getMovFiscTipo().getImpFiscalTipo().equals(MovFiscTipoFB.IMP_FISCAL_TIPO_CUPOMFISCAL) == false
								//				&& this.isExisteOc() == false)
							){
			podeEditar = true;
		}
		return podeEditar;
	}

	public void setPodeEditar(boolean podeEditar) {
		this.podeEditar = podeEditar;
	}
	
	public boolean isPodeExcluir() {
		podeExcluir = false;
		if(this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_AGUARDANDO_PAGTO) 
				|| this.getPedVendaStatusId().equals(PedVendaFB.SITUACAO_DIGITACAO)){
			podeExcluir = true;
		}
		return podeExcluir;
	}

	public void setPodeExcluir(boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}
	
	public boolean isPodeConcluir() {
		podeConcluir = false;
		if(PedVendaFB.SITUACAO_DIGITACAO.equals(this.getPedVendaStatusId())){
			podeConcluir = true;
		}
		return podeConcluir;
	}

	public void setPodeConcluir(boolean podeConcluir) {
		this.podeConcluir = podeConcluir;
	}
	
	public Boolean getIsPedido() {
		isPedido = false;
		if(PedVendaFB.PEDIDO.equals(this.getEncomenda())){
			isPedido = true;
		}
		return isPedido;
	}

	public void setIsPedido(Boolean isPedido) {
		this.isPedido = isPedido;
	}

	public Boolean getIsEncomenda() {
		isEncomenda = false;
		if(PedVendaFB.ENCOMENDA.equals(this.getEncomenda())){
			isEncomenda = true;
		}
		return isEncomenda;
	}

	public void setIsEncomenda(Boolean isEncomenda) {
		this.isEncomenda = isEncomenda;
	}

	public Boolean getIsProdComposto() {
		isProdComposto = false;
		if(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO.equals(this.getEncomenda())){
			isProdComposto = true;
		}
		return isProdComposto;
	}

	public void setIsProdComposto(Boolean isProdComposto) {
		this.isProdComposto = isProdComposto;
	}

	public Integer getPermitePromocao() {
		return permitePromocao;
	}

	public void setPermitePromocao(Integer permitePromocao) {
		this.permitePromocao = permitePromocao;
	}

	public PedVendaStatusFB getPedVendaStatus() {
		return pedVendaStatus;
	}

	public void setPedVendaStatus(PedVendaStatusFB pedVendaStatus) {
		this.pedVendaStatus = pedVendaStatus;
	}
	
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if ( !(other instanceof PedVendaFB) ) return false;
        final PedVendaFB o = (PedVendaFB) other;
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
