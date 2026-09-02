package br.com.webapp.model.fb.pedvenda.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;

@Entity
public class PedVendaFBDTO implements Serializable {

	private static final long serialVersionUID = 4357632341460891307L;
	
	@Id
	@Column
	private Integer pedVendaId;
	
	@Column
	private Integer empresaId;
	
	@Column
	private String empresaDesc;
	
	@Column
	private Integer clienteId;
	
	@Column
	private String clienteDesc;
	
	@Column
	private String nomeCliente;

	@Column
	private Integer vendedorId;
	
	@Column
	private String vendedorDesc;
	
	@Column
	private Integer condPagtoId;
	
	@Column
	private String condPagtoDesc;
	
	@Column
	private Integer pedVendaStatusId;
	
	@Column
	private String pedVendaStatusDesc;
	
	@Column
	private Integer formaPagtoId;
	
	@Column
	private String formaPagtoDesc;
	
	@Column
	private Double valor;
	
	@Column 
	private Date dtEntrada;
	
	@Column
	private Date dtConclusao;
	
	@Column
	private Date dtEfetivacao;
	
	@Column
	private Integer encomenda;
	
	@Transient
	private String encomendaToString;
	
	public PedVendaFBDTO() {}

	public Integer getPedVendaId() {
		return pedVendaId;
	}

	public void setPedvendaId(Integer pedVendaId) {
		this.pedVendaId = pedVendaId;
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

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteDesc() {
		return clienteDesc;
	}

	public void setClienteDesc(String clienteDesc) {
		this.clienteDesc = clienteDesc;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Integer vendedorId) {
		this.vendedorId = vendedorId;
	}

	public String getVendedorDesc() {
		return vendedorDesc;
	}

	public void setVendedorDesc(String vendedorDesc) {
		this.vendedorDesc = vendedorDesc;
	}

	public Integer getCondPagtoId() {
		return condPagtoId;
	}

	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}

	public String getCondPagtoDesc() {
		return condPagtoDesc;
	}

	public void setCondPagtoDesc(String condPagtoDesc) {
		this.condPagtoDesc = condPagtoDesc;
	}

	public Integer getPedVendaStatusId() {
		return pedVendaStatusId;
	}

	public void setPedVendaStatusId(Integer pedVendaStatusId) {
		this.pedVendaStatusId = pedVendaStatusId;
	}

	public String getPedVendaStatusDesc() {
		return pedVendaStatusDesc;
	}

	public void setPedVendaStatusDesc(String pedVendaStatusDesc) {
		this.pedVendaStatusDesc = pedVendaStatusDesc;
	}

	public Integer getFormaPagtoId() {
		return formaPagtoId;
	}

	public void setFormaPagtoId(Integer formaPagtoId) {
		this.formaPagtoId = formaPagtoId;
	}

	public String getFormaPagtoDesc() {
		return formaPagtoDesc;
	}

	public void setFormaPagtoDesc(String formaPagtoDesc) {
		this.formaPagtoDesc = formaPagtoDesc;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public Date getDtConclusao() {
		return dtConclusao;
	}

	public void setDtConclusao(Date dtConclusao) {
		this.dtConclusao = dtConclusao;
	}

	public Date getDtEfetivacao() {
		return dtEfetivacao;
	}

	public void setDtEfetivacao(Date dtEfetivacao) {
		this.dtEfetivacao = dtEfetivacao;
	}
	
	public Integer getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(Integer encomenda) {
		this.encomenda = encomenda;
	}
	
	public String getEncomendaToString() {
		if(encomenda.equals(PedVendaFB.PEDIDO)) {
			encomendaToString = "Pedido";
		}else if(encomenda.equals(PedVendaFB.ENCOMENDA)) {
			encomendaToString = "Encomenda";
		}else if(encomenda.equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			encomendaToString = "Composto";
		}
		return encomendaToString;
	}

	public void setEncomendaToString(String encomendaToString) {
		this.encomendaToString = encomendaToString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedVendaId == null) ? 0 : pedVendaId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedVendaFBDTO other = (PedVendaFBDTO) obj;
		if (pedVendaId == null) {
			if (other.pedVendaId != null)
				return false;
		} else if (!pedVendaId.equals(other.pedVendaId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getPedVendaId() + " | " + this.getPedVendaStatusDesc();
	}

/*
    
	from pedvenda a, pessoa emp, pessoa cli, pessoa vend, condpagto cp, pedvendastatus ps
	where a.id_pessoa_emp = emp.id_pessoa
	and a.id_pessoa_cli = cli.id_pessoa
	and a.id_pessoa_vend = vend.id_pessoa
	and a.id_condpagto = cp.id_condpagto
	and a.id_pedvendastatus = ps.id_pedvendastatus
	and a.efetivacao between '2018-06-01 00:00:00' and'2018-06-10 23:59:59'

*/

}
