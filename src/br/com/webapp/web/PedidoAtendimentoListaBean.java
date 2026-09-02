package br.com.webapp.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.pedatendimento.PedidoAtendimentoFB;
import br.com.webapp.model.fb.pedatendimento.PedidoAtendimentoFBRN;
import br.com.webapp.model.fb.pedatendimento.dto.PedidoAtendimentoFBDTO;

@ManagedBean(name = "pedidoAtendimentoListaBean")
@SessionScoped
public class PedidoAtendimentoListaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{contextoBean}")
    private ContextoBean contextoBean;

    private Integer empresaAtendimentoId;
    private String status;
    private Date dataInicial;
    private Date dataFinal;
    private List<PedidoAtendimentoFBDTO> lista;
    private List<SelectItem> empresaSelectItem;
    private List<SelectItem> statusSelectItem;

    @PostConstruct
    public void init() {
        if (contextoBean.getUsuarioLogado().getEmpresaId() != null) {
            empresaAtendimentoId =
                    contextoBean.getUsuarioLogado().getEmpresaId();
        } else if (contextoBean.getUsuarioLogado().getEmpresas() != null
                && !contextoBean.getUsuarioLogado().getEmpresas().isEmpty()) {
            empresaAtendimentoId = contextoBean.getUsuarioLogado()
                    .getEmpresas().get(0).getId();
        }
    }

    public void buscar() {
        lista = null;
    }

    public void limparFiltros() {
        status = null;
        dataInicial = null;
        dataFinal = null;
        lista = null;
    }

    public List<PedidoAtendimentoFBDTO> getLista() {
        if (lista == null) {
            lista = new PedidoAtendimentoFBRN().listar(
                    empresaAtendimentoId, status, dataInicial,
                    dataFinal, contextoBean.getUsuarioLogado().getId());
        }
        return lista;
    }

    public List<SelectItem> getEmpresaSelectItem() {
        if (empresaSelectItem == null) {
            EmpresaFBRN empresaFBRN = new EmpresaFBRN();
            empresaSelectItem = empresaFBRN.montaDadosSelect(
                    empresaFBRN.listar(contextoBean.getUsuarioLogado()), "");
        }
        return empresaSelectItem;
    }

    public List<SelectItem> getStatusSelectItem() {
        if (statusSelectItem == null) {
            statusSelectItem = new ArrayList<SelectItem>();
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_RESERVADO, "Reservado"));
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_EM_SEPARACAO,
                    "Em separacao"));
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_DISPONIVEL, "Disponivel"));
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_EM_TRANSITO,
                    "Em transito"));
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_ATENDIDO, "Atendido"));
            statusSelectItem.add(new SelectItem(
                    PedidoAtendimentoFB.STATUS_CANCELADO, "Cancelado"));
        }
        return statusSelectItem;
    }

    public ContextoBean getContextoBean() {
        return contextoBean;
    }

    public void setContextoBean(ContextoBean contextoBean) {
        this.contextoBean = contextoBean;
    }

    public Integer getEmpresaAtendimentoId() {
        return empresaAtendimentoId;
    }

    public void setEmpresaAtendimentoId(Integer empresaAtendimentoId) {
        this.empresaAtendimentoId = empresaAtendimentoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setLista(List<PedidoAtendimentoFBDTO> lista) {
        this.lista = lista;
    }
}
