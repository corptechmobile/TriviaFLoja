package br.com.webapp.model.fb.pedatendimento;

import java.util.Date;

import br.com.webapp.web.util.DAOException;

public interface PedidoAtendimentoFBDAO {

    Integer insert(PedidoAtendimentoFB pedidoAtendimento) throws DAOException;

    Integer insertItem(PedidoAtendimentoItemFB item) throws DAOException;

    PedidoAtendimentoFB carregarPorChave(Integer pedVendaId,
            Integer empresaAtendimentoId, String formaAtendimento,
            Date dataAcordada);

    PedidoAtendimentoFB carregarPorPedVendaItem(Integer pedVendaItemId);

    void excluirItensPorPedVendaItem(Integer pedVendaItemId)
            throws DAOException;

    void excluirCabecalhosSemItens(Integer pedVendaId) throws DAOException;

    void rollback();
}
