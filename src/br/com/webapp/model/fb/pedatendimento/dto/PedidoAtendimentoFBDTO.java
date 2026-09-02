package br.com.webapp.model.fb.pedatendimento.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import br.com.webapp.model.fb.pedatendimento.PedidoAtendimentoFB;

public class PedidoAtendimentoFBDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer pedVendaId;
    private Integer empresaVendaId;
    private Integer empresaAtendimentoId;
    private String empresaVendaDesc;
    private String empresaAtendimentoDesc;
    private String clienteDesc;
    private String formaAtendimento;
    private String status;
    private Date dataAcordada;
    private Date dataCriacao;
    private Integer qtdItens;
    private Double quantidade;

    public String getFormaAtendimentoDesc() {
        if (PedidoAtendimentoFB.FORMA_ENTREGA_CLIENTE.equals(
                formaAtendimento)) {
            return "Entrega ao cliente";
        }
        if (PedidoAtendimentoFB.FORMA_RETIRADA_FILIAL_ESTOQUE.equals(
                formaAtendimento)) {
            return "Retirada nesta filial";
        }
        if (PedidoAtendimentoFB.FORMA_TRANSFERENCIA_FILIAL_VENDA.equals(
                formaAtendimento)) {
            return "Transferencia para a loja da venda";
        }
        return formaAtendimento;
    }

    public boolean isAtrasado() {
        if (dataAcordada == null
                || PedidoAtendimentoFB.STATUS_ATENDIDO.equals(status)
                || PedidoAtendimentoFB.STATUS_CANCELADO.equals(status)) {
            return false;
        }

        Calendar hoje = Calendar.getInstance();
        zerarHorario(hoje);

        Calendar acordada = Calendar.getInstance();
        acordada.setTime(dataAcordada);
        zerarHorario(acordada);

        return acordada.before(hoje);
    }

    private void zerarHorario(Calendar data) {
        data.set(Calendar.HOUR_OF_DAY, 0);
        data.set(Calendar.MINUTE, 0);
        data.set(Calendar.SECOND, 0);
        data.set(Calendar.MILLISECOND, 0);
    }

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

    public String getEmpresaVendaDesc() {
        return empresaVendaDesc;
    }

    public void setEmpresaVendaDesc(String empresaVendaDesc) {
        this.empresaVendaDesc = empresaVendaDesc;
    }

    public String getEmpresaAtendimentoDesc() {
        return empresaAtendimentoDesc;
    }

    public void setEmpresaAtendimentoDesc(String empresaAtendimentoDesc) {
        this.empresaAtendimentoDesc = empresaAtendimentoDesc;
    }

    public String getClienteDesc() {
        return clienteDesc;
    }

    public void setClienteDesc(String clienteDesc) {
        this.clienteDesc = clienteDesc;
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

    public Integer getQtdItens() {
        return qtdItens;
    }

    public void setQtdItens(Integer qtdItens) {
        this.qtdItens = qtdItens;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
