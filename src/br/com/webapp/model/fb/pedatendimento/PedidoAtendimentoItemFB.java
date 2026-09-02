package br.com.webapp.model.fb.pedatendimento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PedidoAtendimentoItemFB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Integer id;

    @Column
    private Integer pedidoAtendimentoId;

    @Column
    private Integer pedVendaItemId;

    @Column
    private Integer produtoId;

    @Column
    private Integer localidadeId;

    @Column
    private Integer produtoLoteId;

    @Column(columnDefinition = "Decimal(18,3)")
    private Double quantidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPedidoAtendimentoId() {
        return pedidoAtendimentoId;
    }

    public void setPedidoAtendimentoId(Integer pedidoAtendimentoId) {
        this.pedidoAtendimentoId = pedidoAtendimentoId;
    }

    public Integer getPedVendaItemId() {
        return pedVendaItemId;
    }

    public void setPedVendaItemId(Integer pedVendaItemId) {
        this.pedVendaItemId = pedVendaItemId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getLocalidadeId() {
        return localidadeId;
    }

    public void setLocalidadeId(Integer localidadeId) {
        this.localidadeId = localidadeId;
    }

    public Integer getProdutoLoteId() {
        return produtoLoteId;
    }

    public void setProdutoLoteId(Integer produtoLoteId) {
        this.produtoLoteId = produtoLoteId;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
