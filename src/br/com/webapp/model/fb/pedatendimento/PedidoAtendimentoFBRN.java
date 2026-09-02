package br.com.webapp.model.fb.pedatendimento;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;

public class PedidoAtendimentoFBRN {

    private PedidoAtendimentoFBDAO pedidoAtendimentoDAO;

    public PedidoAtendimentoFBRN() {
        PedidoAtendimentoFBDAOHibernate dao =
                new PedidoAtendimentoFBDAOHibernate();
        dao.setSession(HibernateUtil.getSessionFactoryFirebird()
                .getCurrentSession());
        this.pedidoAtendimentoDAO = dao;
    }

    public void validarAlteracao(Integer pedVendaItemId) throws RNException {
        if (pedVendaItemId == null) {
            return;
        }

        PedidoAtendimentoFB existente =
                pedidoAtendimentoDAO.carregarPorPedVendaItem(pedVendaItemId);
        if (existente != null
                && !PedidoAtendimentoFB.STATUS_RESERVADO.equals(
                        existente.getStatus())) {
            throw new RNException(
                    "O item ja esta em processamento pela filial de atendimento.");
        }
    }

    public Integer validarSelecao(PedVendaFB pedVenda,
            ProdutoFB produto, String formaAtendimento,
            Date dataAcordada) throws RNException {
        if (pedVenda == null || produto == null
                || produto.getEstoques() == null) {
            return null;
        }

        Set<Integer> empresasSelecionadas = new HashSet<Integer>();
        for (ProdutoEstoqueFB estoque : produto.getEstoques()) {
            if (estoque.getQtdReservar() != null
                    && estoque.getQtdReservar().doubleValue() > 0.0) {
                empresasSelecionadas.add(estoque.getEmpresaId());
            }
        }

        if (empresasSelecionadas.size() > 1) {
            throw new RNException(
                    "Selecione o estoque de apenas uma filial para cada item.");
        }

        if (empresasSelecionadas.isEmpty()) {
            return null;
        }

        Integer empresaAtendimentoId =
                empresasSelecionadas.iterator().next();
        if (!empresaAtendimentoId.equals(pedVenda.getEmpresaId())) {
            if (!isFormaValida(formaAtendimento)) {
                throw new RNException(
                        "Informe como o item de outra filial sera atendido.");
            }
            if (dataAcordada == null) {
                throw new RNException(
                        "Informe a data acordada com o cliente.");
            }
        }

        return empresaAtendimentoId;
    }

    public void sincronizar(PedVendaFB pedVenda,
            PedVendaItemFB pedVendaItem, ProdutoFB produto,
            String formaAtendimento, Date dataAcordada,
            String observacao, Integer usuarioWebId) throws RNException {
        try {
            validarAlteracao(pedVendaItem.getId());
            Integer empresaAtendimentoId = validarSelecao(
                    pedVenda, produto, formaAtendimento, dataAcordada);

            pedidoAtendimentoDAO.excluirItensPorPedVendaItem(
                    pedVendaItem.getId());
            pedidoAtendimentoDAO.excluirCabecalhosSemItens(
                    pedVenda.getId());

            if (empresaAtendimentoId == null
                    || empresaAtendimentoId.equals(pedVenda.getEmpresaId())) {
                return;
            }

            PedidoAtendimentoFB pedidoAtendimento =
                    pedidoAtendimentoDAO.carregarPorChave(
                            pedVenda.getId(), empresaAtendimentoId,
                            formaAtendimento, dataAcordada);

            if (pedidoAtendimento == null) {
                pedidoAtendimento = novo(pedVenda,
                        empresaAtendimentoId, formaAtendimento,
                        dataAcordada, observacao, usuarioWebId);
                pedidoAtendimento.setId(
                        pedidoAtendimentoDAO.insert(pedidoAtendimento));
            }

            salvarItens(pedidoAtendimento.getId(),
                    pedVendaItem, produto.getEstoques());

            new ProdutoEstoqueFBRN().bloqueEstoque(
                    empresaAtendimentoId, produto.getId());
        } catch (RNException e) {
            pedidoAtendimentoDAO.rollback();
            throw e;
        } catch (Exception e) {
            pedidoAtendimentoDAO.rollback();
            throw new RNException(e.getMessage());
        }
    }

    public void excluirPorPedVendaItem(Integer pedVendaId,
            Integer pedVendaItemId) throws RNException {
        try {
            validarAlteracao(pedVendaItemId);
            pedidoAtendimentoDAO.excluirItensPorPedVendaItem(
                    pedVendaItemId);
            pedidoAtendimentoDAO.excluirCabecalhosSemItens(
                    pedVendaId);
        } catch (Exception e) {
            pedidoAtendimentoDAO.rollback();
            throw new RNException(e.getMessage());
        }
    }

    public PedidoAtendimentoFB carregarPorPedVendaItem(
            Integer pedVendaItemId) {
        if (pedVendaItemId == null) {
            return null;
        }
        return pedidoAtendimentoDAO.carregarPorPedVendaItem(
                pedVendaItemId);
    }

    private PedidoAtendimentoFB novo(PedVendaFB pedVenda,
            Integer empresaAtendimentoId, String formaAtendimento,
            Date dataAcordada, String observacao,
            Integer usuarioWebId) {
        PedidoAtendimentoFB pedido = new PedidoAtendimentoFB();
        pedido.setPedVendaId(pedVenda.getId());
        pedido.setEmpresaVendaId(pedVenda.getEmpresaId());
        pedido.setEmpresaAtendimentoId(empresaAtendimentoId);
        pedido.setClienteId(pedVenda.getClienteId());
        pedido.setUsuarioWebId(usuarioWebId);
        pedido.setFormaAtendimento(formaAtendimento);
        pedido.setStatus(PedidoAtendimentoFB.STATUS_RESERVADO);
        pedido.setDataAcordada(dataAcordada);
        pedido.setDataCriacao(new Date());
        pedido.setObservacao(observacao);
        return pedido;
    }

    private void salvarItens(Integer pedidoAtendimentoId,
            PedVendaItemFB pedVendaItem,
            List<ProdutoEstoqueFB> estoques) throws DAOException {
        for (ProdutoEstoqueFB estoque : estoques) {
            if (estoque.getQtdReservar() != null
                    && estoque.getQtdReservar().doubleValue() > 0.0) {
                PedidoAtendimentoItemFB item =
                        new PedidoAtendimentoItemFB();
                item.setPedidoAtendimentoId(pedidoAtendimentoId);
                item.setPedVendaItemId(pedVendaItem.getId());
                item.setProdutoId(estoque.getProdutoId());
                item.setLocalidadeId(estoque.getLocalidadeId());
                item.setProdutoLoteId(estoque.getProdutoLoteId());
                item.setQuantidade(estoque.getQtdReservar());
                pedidoAtendimentoDAO.insertItem(item);
            }
        }
    }

    private boolean isFormaValida(String formaAtendimento) {
        return PedidoAtendimentoFB.FORMA_ENTREGA_CLIENTE.equals(
                    formaAtendimento)
                || PedidoAtendimentoFB.FORMA_RETIRADA_FILIAL_ESTOQUE.equals(
                    formaAtendimento)
                || PedidoAtendimentoFB.FORMA_TRANSFERENCIA_FILIAL_VENDA.equals(
                    formaAtendimento);
    }
}
