package br.com.webapp.model.fb.pedatendimento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PedidoAtendimentoFB implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FORMA_ENTREGA_CLIENTE = "E";
    public static final String FORMA_RETIRADA_FILIAL_ESTOQUE = "R";
    public static final String FORMA_TRANSFERENCIA_FILIAL_VENDA = "T";

    public static final String STATUS_RESERVADO = "RESERVADO";
    public static final String STATUS_EM_SEPARACAO = "EM_SEPARACAO";
    public static final String STATUS_DISPONIVEL = "DISPONIVEL";
    public static final String STATUS_EM_TRANSITO = "EM_TRANSITO";
    public static final String STATUS_ATENDIDO = "ATENDIDO";
    public static final String STATUS_CANCELADO = "CANCELADO";

    @Id
    @Column
    private Integer id;

    @Column
    private Integer pedVendaId;

    @Column
    private Integer empresaVendaId;

    @Column
    private Integer empresaAtendimentoId;

    @Column
    private Integer clienteId;

    @Column
    private Integer usuarioWebId;

    @Column(length = 1)
    private String formaAtendimento;

    @Column(length = 30)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dataAcordada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dataCriacao;

    @Column(length = 500)
    private String observacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPedVendaId() {
        return pedVendaId;
    }

    public void setPedVendaId(Integer pedVendaId) {
        this.pedVendaId = pedVendaId;
    }

    public Integer getEmpresaVendaId() {
        return empresaVendaId;
    }

    public void setEmpresaVendaId(Integer empresaVendaId) {
        this.empresaVendaId = empresaVendaId;
    }

    public Integer getEmpresaAtendimentoId() {
        return empresaAtendimentoId;
    }

    public void setEmpresaAtendimentoId(Integer empresaAtendimentoId) {
        this.empresaAtendimentoId = empresaAtendimentoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getUsuarioWebId() {
        return usuarioWebId;
    }

    public void setUsuarioWebId(Integer usuarioWebId) {
        this.usuarioWebId = usuarioWebId;
    }

    public String getFormaAtendimento() {
        return formaAtendimento;
    }

    public void setFormaAtendimento(String formaAtendimento) {
        this.formaAtendimento = formaAtendimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataAcordada() {
        return dataAcordada;
    }

    public void setDataAcordada(Date dataAcordada) {
        this.dataAcordada = dataAcordada;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
