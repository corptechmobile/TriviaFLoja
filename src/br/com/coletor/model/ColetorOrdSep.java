package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorOrdSep implements Serializable {
	
	private static final long serialVersionUID = -6769836810573925992L;
	
	public static final Integer STATUS_EM_ABERTO = 0;
	public static final Integer STATUS_ENTREGUE = 1;
	public static final Integer STATUS_CANCELADO = 2;
	
	@Id
	private Integer id;
	private Integer numPedVenda;

	private Integer empresaId;
	private String empresaNomeFant;
	
	private Integer clienteId;
	private String clienteCnpj;
	private String clienteNomeFant;
	
	private String tipoFrete;
	private String tipoFretePedVenda;
	private Integer totalItensPedVenda;
	private Double totalVolumesPedVenda;
	
	private Integer numProcTransp;
	private Integer ordemEntrega;
	private String rotaEntrega;
	private String bairroEntrega;
	private String cidadeEntrega;
	private String estadoEntrega;
	private String nomeTransportador;
	private String placaVeiculo;
	private String observacao;

	//private Date dtInicioConf; // falar com alex
	//private Date dtTerminoConf;
	
	private Date dtEntradaPedVenda;
	private Date dtPrevSaida;
	private Date dtCreate;
	private Integer status;
	
	public ColetorOrdSep(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumPedVenda() {
		return numPedVenda;
	}

	public void setNumPedVenda(Integer numPedVenda) {
		this.numPedVenda = numPedVenda;
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

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteCnpj() {
		return clienteCnpj;
	}

	public void setClienteCnpj(String clienteCnpj) {
		this.clienteCnpj = clienteCnpj;
	}

	public String getClienteNomeFant() {
		return clienteNomeFant;
	}

	public void setClienteNomeFant(String clienteNomeFant) {
		this.clienteNomeFant = clienteNomeFant;
	}

	public String getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(String tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public String getTipoFretePedVenda() {
		return tipoFretePedVenda;
	}

	public void setTipoFretePedVenda(String tipoFretePedVenda) {
		this.tipoFretePedVenda = tipoFretePedVenda;
	}

	public Integer getTotalItensPedVenda() {
		return totalItensPedVenda;
	}

	public void setTotalItensPedVenda(Integer totalItensPedVenda) {
		this.totalItensPedVenda = totalItensPedVenda;
	}

	public Double getTotalVolumesPedVenda() {
		return totalVolumesPedVenda;
	}

	public void setTotalVolumesPedVenda(Double totalVolumesPedVenda) {
		this.totalVolumesPedVenda = totalVolumesPedVenda;
	}

	public Integer getNumProcTransp() {
		return numProcTransp;
	}

	public void setNumProcTransp(Integer numProcTransp) {
		this.numProcTransp = numProcTransp;
	}

	public Integer getOrdemEntrega() {
		return ordemEntrega;
	}

	public void setOrdemEntrega(Integer ordemEntrega) {
		this.ordemEntrega = ordemEntrega;
	}

	public String getRotaEntrega() {
		return rotaEntrega;
	}

	public void setRotaEntrega(String rotaEntrega) {
		this.rotaEntrega = rotaEntrega;
	}

	public String getBairroEntrega() {
		return bairroEntrega;
	}

	public void setBairroEntrega(String bairroEntrega) {
		this.bairroEntrega = bairroEntrega;
	}

	public String getCidadeEntrega() {
		return cidadeEntrega;
	}

	public void setCidadeEntrega(String cidadeEntrega) {
		this.cidadeEntrega = cidadeEntrega;
	}

	public String getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public String getNomeTransportador() {
		return nomeTransportador;
	}

	public void setNomeTransportador(String nomeTransportador) {
		this.nomeTransportador = nomeTransportador;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDtEntradaPedVenda() {
		return dtEntradaPedVenda;
	}

	public void setDtEntradaPedVenda(Date dtEntradaPedVenda) {
		this.dtEntradaPedVenda = dtEntradaPedVenda;
	}

	public Date getDtPrevSaida() {
		return dtPrevSaida;
	}

	public void setDtPrevSaida(Date dtPrevSaida) {
		this.dtPrevSaida = dtPrevSaida;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		ColetorOrdSep other = (ColetorOrdSep) obj;
		return Objects.equals(id, other.id);
	}
	
}