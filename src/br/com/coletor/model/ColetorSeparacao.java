package br.com.coletor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColetorSeparacao implements Serializable {
	
	private static final long serialVersionUID = -7095936558731837303L;
	
	public static final Integer STATUS_EM_SEPARACAO = 0;
	public static final Integer STATUS_FINALIZADA = 1;
	
	@Id
	private Integer id;
	private Integer ordemCarregId;
	private Integer pedVendaId;

	private Integer empresaId;
	private String empresaNomeFant;
	
	private Integer clienteId;
	private String clienteCnpjCpf;
	private String clienteNomeFant;
	
	private Date dtInicioSep;
	private Date dtTerminoSep;
	
	private Integer separadorId;
	private String separadorNome;
	
	private Integer usuarioId;
	private String usuarioNome;
	
	private Integer status;
	
	public ColetorSeparacao(){}

	public ColetorSeparacao(ColetorOrdSep coletorOrdSep) {
		this.ordemCarregId = coletorOrdSep.getId();
		this.pedVendaId = coletorOrdSep.getNumPedVenda();
		this.empresaId = coletorOrdSep.getEmpresaId();
		this.empresaNomeFant = coletorOrdSep.getEmpresaNomeFant();
		this.clienteId = coletorOrdSep.getClienteId();
		this.clienteCnpjCpf = coletorOrdSep.getClienteCnpj();
		this.clienteNomeFant = coletorOrdSep.getClienteNomeFant();
	}

	public Integer getId() {
		return id;
	}

	public void setColetorSeparacaoId(Integer id) {
		this.id = id;
	}

	public Integer getOrdemCarregId() {
		return ordemCarregId;
	}

	public void setOrdemCarregId(Integer ordemCarregId) {
		this.ordemCarregId = ordemCarregId;
	}

	public Integer getPedVendaId() {
		return pedVendaId;
	}

	public void setPedVendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
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

	public String getClienteCnpjCpf() {
		return clienteCnpjCpf;
	}

	public void setClienteCnpjCpf(String clienteCnpjCpf) {
		this.clienteCnpjCpf = clienteCnpjCpf;
	}

	public String getClienteNomeFant() {
		return clienteNomeFant;
	}

	public void setClienteNomeFant(String clienteNomeFant) {
		this.clienteNomeFant = clienteNomeFant;
	}

	public Date getDtInicioSep() {
		return dtInicioSep;
	}

	public void setDtInicioSep(Date dtInicioSep) {
		this.dtInicioSep = dtInicioSep;
	}

	public Date getDtTerminoSep() {
		return dtTerminoSep;
	}

	public void setDtTerminoSep(Date dtTerminoSep) {
		this.dtTerminoSep = dtTerminoSep;
	}

	public Integer getSeparadorId() {
		return separadorId;
	}

	public void setSeparadorId(Integer separadorId) {
		this.separadorId = separadorId;
	}

	public String getSeparadorNome() {
		return separadorNome;
	}

	public void setSeparadorNome(String separadorNome) {
		this.separadorNome = separadorNome;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
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
		ColetorSeparacao other = (ColetorSeparacao) obj;
		return Objects.equals(id, other.id);
	}	

}