package br.com.webapp.model.fb.pedatendimento;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.webapp.web.util.DAOException;

public class PedidoAtendimentoFBDAOHibernate implements PedidoAtendimentoFBDAO {

    private Session session;
    private StringBuilder columns;

    public PedidoAtendimentoFBDAOHibernate() {
        columns = new StringBuilder();
        columns.append(" a.ID_PEDATENDIMENTO AS id, ")
               .append(" a.ID_PEDVENDA AS pedVendaId, ")
               .append(" a.ID_PESSOA_EMP_VENDA AS empresaVendaId, ")
               .append(" a.ID_PESSOA_EMP_ATEND AS empresaAtendimentoId, ")
               .append(" a.ID_PESSOA_CLI AS clienteId, ")
               .append(" a.ID_USUARIO_WEB AS usuarioWebId, ")
               .append(" a.FORMA_ATENDIMENTO AS formaAtendimento, ")
               .append(" a.STATUS AS status, ")
               .append(" a.DATA_ACORDADA AS dataAcordada, ")
               .append(" a.DATA_CRIACAO AS dataCriacao, ")
               .append(" a.OBSERVACAO AS observacao ");
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Integer insert(PedidoAtendimentoFB pedidoAtendimento)
            throws DAOException {
        try {
            pedidoAtendimento.setId(getSeq("GEN_PEDATENDIMENTO_ID"));
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO PEDATENDIMENTO (")
               .append("ID_PEDATENDIMENTO, ID_PEDVENDA, ")
               .append("ID_PESSOA_EMP_VENDA, ID_PESSOA_EMP_ATEND, ")
               .append("ID_PESSOA_CLI, ID_USUARIO_WEB, ")
               .append("FORMA_ATENDIMENTO, STATUS, DATA_ACORDADA, ")
               .append("DATA_CRIACAO, OBSERVACAO) VALUES (")
               .append(":ID, :ID_PEDVENDA, :ID_EMPRESA_VENDA, ")
               .append(":ID_EMPRESA_ATENDIMENTO, :ID_CLIENTE, ")
               .append(":ID_USUARIO_WEB, :FORMA_ATENDIMENTO, :STATUS, ")
               .append(":DATA_ACORDADA, :DATA_CRIACAO, :OBSERVACAO)");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("ID", pedidoAtendimento.getId());
            query.setParameter("ID_PEDVENDA", pedidoAtendimento.getPedVendaId());
            query.setParameter("ID_EMPRESA_VENDA", pedidoAtendimento.getEmpresaVendaId());
            query.setParameter("ID_EMPRESA_ATENDIMENTO", pedidoAtendimento.getEmpresaAtendimentoId());
            query.setParameter("ID_CLIENTE", pedidoAtendimento.getClienteId());
            query.setParameter("ID_USUARIO_WEB", pedidoAtendimento.getUsuarioWebId());
            query.setParameter("FORMA_ATENDIMENTO", pedidoAtendimento.getFormaAtendimento());
            query.setParameter("STATUS", pedidoAtendimento.getStatus());
            query.setDate("DATA_ACORDADA", pedidoAtendimento.getDataAcordada());
            query.setTimestamp("DATA_CRIACAO", pedidoAtendimento.getDataCriacao());
            query.setParameter("OBSERVACAO", pedidoAtendimento.getObservacao());
            query.executeUpdate();

            return pedidoAtendimento.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao salvar o Pedido de Atendimento.");
        }
    }

    @Override
    public Integer insertItem(PedidoAtendimentoItemFB item)
            throws DAOException {
        try {
            item.setId(getSeq("GEN_PEDATENDITEM_ID"));
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO PEDATENDITEM (")
               .append("ID_PEDATENDITEM, ID_PEDATENDIMENTO, ")
               .append("ID_PEDVENDAITEM, ID_PRODUTO, ID_LOCALIDADE, ")
               .append("ID_PRODUTOLOTE, QUANTIDADE) VALUES (")
               .append(":ID, :ID_PEDATENDIMENTO, :ID_PEDVENDAITEM, ")
               .append(":ID_PRODUTO, :ID_LOCALIDADE, ")
               .append(":ID_PRODUTOLOTE, :QUANTIDADE)");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("ID", item.getId());
            query.setParameter("ID_PEDATENDIMENTO", item.getPedidoAtendimentoId());
            query.setParameter("ID_PEDVENDAITEM", item.getPedVendaItemId());
            query.setParameter("ID_PRODUTO", item.getProdutoId());
            query.setParameter("ID_LOCALIDADE", item.getLocalidadeId());
            query.setParameter("ID_PRODUTOLOTE", item.getProdutoLoteId());
            query.setParameter("QUANTIDADE", item.getQuantidade());
            query.executeUpdate();

            return item.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao salvar o item do Pedido de Atendimento.");
        }
    }

    @Override
    public PedidoAtendimentoFB carregarPorChave(Integer pedVendaId,
            Integer empresaAtendimentoId, String formaAtendimento,
            Date dataAcordada) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columns)
           .append(" FROM PEDATENDIMENTO a ")
           .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA ")
           .append(" AND a.ID_PESSOA_EMP_ATEND = :ID_EMPRESA_ATENDIMENTO ")
           .append(" AND a.FORMA_ATENDIMENTO = :FORMA_ATENDIMENTO ");

        if (dataAcordada == null) {
            sql.append(" AND a.DATA_ACORDADA IS NULL ");
        } else {
            sql.append(" AND a.DATA_ACORDADA = :DATA_ACORDADA ");
        }

        Query query = session.createSQLQuery(sql.toString())
                .addEntity(PedidoAtendimentoFB.class);
        query.setParameter("ID_PEDVENDA", pedVendaId);
        query.setParameter("ID_EMPRESA_ATENDIMENTO", empresaAtendimentoId);
        query.setParameter("FORMA_ATENDIMENTO", formaAtendimento);
        if (dataAcordada != null) {
            query.setDate("DATA_ACORDADA", dataAcordada);
        }
        query.setMaxResults(1);
        return (PedidoAtendimentoFB) query.uniqueResult();
    }

    @Override
    public PedidoAtendimentoFB carregarPorPedVendaItem(
            Integer pedVendaItemId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(columns)
           .append(" FROM PEDATENDIMENTO a ")
           .append(" INNER JOIN PEDATENDITEM i ")
           .append(" ON i.ID_PEDATENDIMENTO = a.ID_PEDATENDIMENTO ")
           .append(" WHERE i.ID_PEDVENDAITEM = :ID_PEDVENDAITEM ");

        Query query = session.createSQLQuery(sql.toString())
                .addEntity(PedidoAtendimentoFB.class);
        query.setParameter("ID_PEDVENDAITEM", pedVendaItemId);
        query.setMaxResults(1);
        return (PedidoAtendimentoFB) query.uniqueResult();
    }

    @Override
    public void excluirItensPorPedVendaItem(Integer pedVendaItemId)
            throws DAOException {
        try {
            Query query = session.createSQLQuery(
                    "DELETE FROM PEDATENDITEM WHERE ID_PEDVENDAITEM = :ID_PEDVENDAITEM");
            query.setParameter("ID_PEDVENDAITEM", pedVendaItemId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao atualizar os itens do Pedido de Atendimento.");
        }
    }

    @Override
    public void excluirCabecalhosSemItens(Integer pedVendaId)
            throws DAOException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM PEDATENDIMENTO a ")
               .append(" WHERE a.ID_PEDVENDA = :ID_PEDVENDA ")
               .append(" AND NOT EXISTS (SELECT 1 FROM PEDATENDITEM i ")
               .append(" WHERE i.ID_PEDATENDIMENTO = a.ID_PEDATENDIMENTO)");
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("ID_PEDVENDA", pedVendaId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao remover Pedido de Atendimento sem itens.");
        }
    }

    private Integer getSeq(String generator) throws DAOException {
        try {
            Query query = session.createSQLQuery(
                    "select gen_id(" + generator + ", 1) from rdb$database");
            BigInteger key = (BigInteger) query.uniqueResult();
            return Integer.valueOf(key.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao gerar a sequencia do Pedido de Atendimento.");
        }
    }

    @Override
    public void rollback() {
        try {
            Transaction transaction = session.getTransaction();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
