package br.com.webapp.model.fb.relatorio.devvenda;

import java.io.Serializable;
import java.util.Date;

public class DevVendaDTO implements Serializable{

	private static final long serialVersionUID = -6653428311817763193L;
	public static final String AGRUPAR_CLIENTE = "cliente";
	public static final String AGRUPAR_BOLETIM = "boletim";
	public static final String AGRUPAR_PRODUTO = "produto";
	public static final String AGRUPAR_BOLETIM_PRODUTO = "boletimproduto";
	public static final String AGRUPAR_VENDEDOR = "vendedor";
	public static final String AGRUPAR_LINHAPRODUTO = "linhaproduto";
	public static final String AGRUPAR_FORNECEDOR = "fornecedor";
	public static final String AGRUPAR_EMPRESA = "empresa";
	
	private Integer id;
	private Integer boletimId;
	private Date momento;
	private String empresaDesc;
	private String empresaCNPJ;
	private Integer clienteId;
	private String clienteDesc;
	private String clienteCNPJ;
	private Integer vendedorId;
	private String vendedorDesc;
	private String vendedorCNPJ;
	private String fornecedorDesc;
	private String fornecedorCNPJ;
	private String conferenteDesc;
	private String conferenteCNPJ;
	private String produtoDesc;
	private String produtoCod;
	private Integer produtoId;
	private String linhaProdutoDesc;
	private String motivo;
	private String numTitulo;
	private String numNF;
	private String serieNF;
	private Double saldoTitulo;
	private Double vlDevolvido;
	private Double valorTotal;
	private Double percValor;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBoletimId() {
		return boletimId;
	}

	public void setBoletimId(Integer boletimId) {
		this.boletimId = boletimId;
	}

	public String getEmpresaDesc() {
		return empresaDesc;
	}
	
	public void setEmpresaDesc(String empresaDesc) {
		this.empresaDesc = empresaDesc;
	}
	
	public String getEmpresaCNPJ() {
		return empresaCNPJ;
	}
	
	public void setEmpresaCNPJ(String empresaCNPJ) {
		this.empresaCNPJ = empresaCNPJ;
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
	
	public String getClienteCNPJ() {
		return clienteCNPJ;
	}
	
	public void setClienteCNPJ(String clienteCNPJ) {
		this.clienteCNPJ = clienteCNPJ;
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

	public String getVendedorCNPJ() {
		return vendedorCNPJ;
	}

	public void setVendedorCNPJ(String vendedorCNPJ) {
		this.vendedorCNPJ = vendedorCNPJ;
	}
	
	public String getFornecedorDesc() {
		return fornecedorDesc;
	}

	public void setFornecedorDesc(String fornecedorDesc) {
		this.fornecedorDesc = fornecedorDesc;
	}

	public String getFornecedorCNPJ() {
		return fornecedorCNPJ;
	}

	public void setFornecedorCNPJ(String fornecedorCNPJ) {
		this.fornecedorCNPJ = fornecedorCNPJ;
	}

	public String getConferenteDesc() {
		return conferenteDesc;
	}

	public void setConferenteDesc(String conferenteDesc) {
		this.conferenteDesc = conferenteDesc;
	}

	public String getConferenteCNPJ() {
		return conferenteCNPJ;
	}

	public void setConferenteCNPJ(String conferenteCNPJ) {
		this.conferenteCNPJ = conferenteCNPJ;
	}

	public String getProdutoDesc() {
		return produtoDesc;
	}
	
	public void setProdutoDesc(String produtoDesc) {
		this.produtoDesc = produtoDesc;
	}
	
	public String getProdutoCod() {
		return produtoCod;
	}
	
	public void setProdutoCod(String produtoCod) {
		this.produtoCod = produtoCod;
	}
	
	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getLinhaProdutoDesc() {
		return linhaProdutoDesc;
	}

	public void setLinhaProdutoDesc(String linhaProdutoDesc) {
		this.linhaProdutoDesc = linhaProdutoDesc;
	}

	public Date getMomento() {
		return momento;
	}
	public void setMomento(Date momento) {
		this.momento = momento;
	}

	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getNumTitulo() {
		return numTitulo;
	}

	public void setNumTitulo(String numTitulo) {
		this.numTitulo = numTitulo;
	}

	public String getNumNF() {
		return numNF;
	}

	public void setNumNF(String numNF) {
		this.numNF = numNF;
	}

	public String getSerieNF() {
		return serieNF;
	}

	public void setSerieNF(String serieNF) {
		this.serieNF = serieNF;
	}

	public Double getSaldoTitulo() {
		return saldoTitulo;
	}

	public void setSaldoTitulo(Double saldoTitulo) {
		this.saldoTitulo = saldoTitulo;
	}

	public Double getVlDevolvido() {
		return vlDevolvido;
	}
	
	public void setVlDevolvido(Double vlDevolvido) {
		this.vlDevolvido = vlDevolvido;
	}
	
	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getPercValor() {
		return percValor;
	}
	
	public void setPercValor(Double percValor) {
		this.percValor = percValor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DevVendaDTO other = (DevVendaDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
