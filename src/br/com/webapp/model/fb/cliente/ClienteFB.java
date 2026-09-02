package br.com.webapp.model.fb.cliente;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.com.webapp.model.fb.cobrtipo.CobrTipoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;

@Entity
public class ClienteFB implements Serializable {

	private static final long serialVersionUID = 6288798802096486637L;
	
	public static final String TIPO_PESSOA_FISICA = "F";
	public static final String TIPO_PESSOA_JURIDICA = "J";
	public static final String TIPO_PESSOA_OUTRO = "O";
	
	public static final int CLIENTE_ATIVO = 1;
	public static final int CLIENTE_INATIVO = 0;
	
	public static final int CLIENTE_BLOQUEADO = 1;
	public static final int CLIENTE_SEMBLOQUEIO = 0;

	// Atributos -> Pessoa
	@Id
	private Integer id;
	private Integer enderecoPrincipalId;
	private Integer telefonePrincipalId;
	private String tipoPessoa;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpjCpf;
	private String inscEst; 
	private String inscMun;
	private String numRg;
	private String email;
	
	// Atributos -> Cliente
	private Integer clienteTipoId;
	private Integer enderecoCobrancaId;
	private Integer enderecoEntregaId;
	private Integer usuarioUltAlteracao;
	private Date dataUltAlteracao;
	private int ativo;
	private int bloqManual;
	private int coligada;
	
	// Atributos -> Endereco Principal
	private Integer enderecoTipoId;
	private String paisId;
	private String estadoId;
	private Integer municipioId;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String cep;
	private String pontoReferencia;
	
	// Atributos -> Telefone
	private Integer telefoneTipoId;
	private Integer codArea;
	private String numero;
	private String ramal;
	
	// Atributos -> Default -> CondPagto / PedVenda
	private Integer freteTipoId;
	private Integer movFiscTipoId;
	private Integer condPagtoId;
	private Integer formaPagtoId;
	private Integer cobrTipoId;
	
	//
	@Transient
	private FreteTipoFB freteTipo;
	
	@Transient
	private MovFiscTipoFB movFiscTipo;
	
	@Transient
	private CondPagtoFB condPagto;
	
	@Transient
	private FormaPagtoFB formaPagto;
	
	@Transient
	private CobrTipoFB cobrTipo;
	
	public ClienteFB(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnderecoPrincipalId() {
		return enderecoPrincipalId;
	}

	public void setEnderecoPrincipalId(Integer enderecoPrincipalId) {
		this.enderecoPrincipalId = enderecoPrincipalId;
	}

	public Integer getTelefonePrincipalId() {
		return telefonePrincipalId;
	}

	public void setTelefonePrincipalId(Integer telefonePrincipalId) {
		this.telefonePrincipalId = telefonePrincipalId;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getInscEst() {
		return inscEst;
	}

	public void setInscEst(String inscEst) {
		this.inscEst = inscEst;
	}

	public String getInscMun() {
		return inscMun;
	}

	public void setInscMun(String inscMun) {
		this.inscMun = inscMun;
	}

	public String getNumRg() {
		return numRg;
	}

	public void setNumRg(String numRg) {
		this.numRg = numRg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getClienteTipoId() {
		return clienteTipoId;
	}

	public void setClienteTipoId(Integer clienteTipoId) {
		this.clienteTipoId = clienteTipoId;
	}

	public Integer getEnderecoCobrancaId() {
		return enderecoCobrancaId;
	}

	public void setEnderecoCobrancaId(Integer enderecoCobrancaId) {
		this.enderecoCobrancaId = enderecoCobrancaId;
	}

	public Integer getEnderecoEntregaId() {
		return enderecoEntregaId;
	}

	public void setEnderecoEntregaId(Integer enderecoEntregaId) {
		this.enderecoEntregaId = enderecoEntregaId;
	}

	public Integer getUsuarioUltAlteracao() {
		return usuarioUltAlteracao;
	}

	public void setUsuarioUltAlteracao(Integer usuarioUltAlteracao) {
		this.usuarioUltAlteracao = usuarioUltAlteracao;
	}

	public Date getDataUltAlteracao() {
		return dataUltAlteracao;
	}

	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	
	public int getBloqManual() {
		return bloqManual;
	}

	public void setBloqManual(int bloqManual) {
		this.bloqManual = bloqManual;
	}
	
	public int getColigada() {
		return coligada;
	}

	public void setColigada(int coligada) {
		this.coligada = coligada;
	}

	public Integer getEnderecoTipoId() {
		return enderecoTipoId;
	}

	public void setEnderecoTipoId(Integer enderecoTipoId) {
		this.enderecoTipoId = enderecoTipoId;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public Integer getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Integer municipioId) {
		this.municipioId = municipioId;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public Integer getTelefoneTipoId() {
		return telefoneTipoId;
	}

	public void setTelefoneTipoId(Integer telefoneTipoId) {
		this.telefoneTipoId = telefoneTipoId;
	}

	public Integer getCodArea() {
		return codArea;
	}

	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}
	
	public Integer getFreteTipoId() {
		return freteTipoId;
	}

	public void setFreteTipoId(Integer freteTipoId) {
		this.freteTipoId = freteTipoId;
	}

	public Integer getMovFiscTipoId() {
		return movFiscTipoId;
	}

	public void setMovFiscTipoId(Integer movFiscTipoId) {
		this.movFiscTipoId = movFiscTipoId;
	}

	public Integer getCondPagtoId() {
		return condPagtoId;
	}

	public void setCondPagtoId(Integer condPagtoId) {
		this.condPagtoId = condPagtoId;
	}

	public Integer getFormaPagtoId() {
		return formaPagtoId;
	}

	public void setFormaPagtoId(Integer formaPagtoId) {
		this.formaPagtoId = formaPagtoId;
	}
	
	public Integer getCobrTipoId() {
		return cobrTipoId;
	}

	public void setCobrTipoId(Integer cobrTipoId) {
		this.cobrTipoId = cobrTipoId;
	}
	
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
		ClienteFB other = (ClienteFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nomeFantasia;
	}

}
