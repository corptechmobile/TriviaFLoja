package br.com.coletor.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorInv;
import br.com.coletor.model.ColetorPlanilhaCega;
import br.com.coletor.model.ColetorPlanilhaCegaItem;

public class DAOColetorPlanilhaCega {
	
	private Session session;

	public DAOColetorPlanilhaCega(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorPlanilhaCega carregar(Integer id) {
		
		String sql = " SELECT a.ID_CPC AS id, "
							+ "	(a.ID_PESSOA_EMP) AS empresaId, "
							+ "	(p2.NOMEFANTMNEM) AS empresaNomeFant, "
							+ "	(a.ID_PESSOA_FORN) AS fornecedorId, "
							+ "	(p.CNPJCPF) AS fornecedorCnpj, "
							+ "	(p.NOMEFANTMNEM) AS fornecedorNomeFant, "
							+ "	(a.STATUS) AS status, "
							+ "	(a.DTINICIO) AS dtInicio, "
							+ "	(a.DTTERMINO) AS dtTermino, "
							+ "	(a.DTCRIACAO) AS dtCriacao, "
							+ "	(a.INFORMAR_LOTE) AS informarLote "
						+ " FROM COLETOR_PC a, "
							 + " PESSOA p, "
							 + " PESSOA p2 "
						+ " WHERE a.ID_PESSOA_FORN = p.ID_PESSOA "
						  + " AND a.ID_PESSOA_EMP = p2.ID_PESSOA "
						  + " AND a.ID_CPC = :id "
						+ " ORDER BY a.ID_CPC ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPlanilhaCega.class);
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (ColetorPlanilhaCega)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorPlanilhaCega> listar(Integer usuarioId) {
		
		String sql = " SELECT a.ID_CPC AS id, "
						+ " (a.ID_PESSOA_EMP) AS empresaId, "
						+ "	(p2.NOMEFANTMNEM) AS empresaNomeFant, "
						+ "	(a.ID_PESSOA_FORN) AS fornecedorId, "
						+ "	(p.CNPJCPF) AS fornecedorCnpj, "
						+ "	(p.NOMEFANTMNEM) AS fornecedorNomeFant, "
						+ "	(a.STATUS) AS status, "
						+ "	(a.DTINICIO) AS dtInicio, "
						+ "	(a.DTTERMINO) AS dtTermino, "
						+ "	(a.DTCRIACAO) AS dtCriacao, "
						+ "	(a.INFORMAR_LOTE) AS informarLote "
					+ " FROM COLETOR_PC a, "
						 + " PESSOA p, "
						 + " PESSOA p2, "
						 + " USUARIOEMPRESA ue "
					+ " WHERE (a.status = :statusLib OR a.status = :statusEmConf) "
					  + " AND a.ID_PESSOA_FORN = p.ID_PESSOA "
					  + " AND a.ID_PESSOA_EMP = p2.ID_PESSOA "
					  + " AND a.ID_PESSOA_EMP = ue.ID_PESSOA_EMP "
					  + " AND ue.ID_USUARIO = :usuarioId "
					+ " ORDER BY a.ID_CPC ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPlanilhaCega.class);
		query.setParameter("statusLib", ColetorPlanilhaCega.STATUS_LIBERADO);
		query.setParameter("statusEmConf", ColetorPlanilhaCega.STATUS_EM_CONFERENCIA);
		query.setParameter("usuarioId", usuarioId);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorPlanilhaCegaItem> listarItens(Integer usuarioId) {
		
		String sql = " SELECT b.ID_CPC_ITEM AS id, "
						+ "	a.ID_CPC AS coletorPlanilhaCegaId, "
						+ "	c.ID_PRODUTO AS produtoId, "
						+ "	d.ID_UNIDADE AS unidCompId, "
						+ "	d.DESCCF AS unidCompDesc, "
						+ "	c.FATORUNIDBASICA AS fatorUnidBasica, "
						+ "	c.DESCFATORUNIDBASICA AS descFatorUnidBasica, "
						+ " c.CONTROLALOTE as controlaLote, "
						+ "	c.OBRIGADESCLOTE AS obrigaDescLote, "
						+ "	c.OBRIGAVENCLOTE AS obrigaVencLote, "
						+ "	c.QTDEMBFECHVENDA AS qtdEmbFechVenda, "
						+ "	c.DESCEMBFECHADA AS descEmbFechVenda, "
						+ "	c.QTDDECIMAL AS qtdDecimal, "
						+ "	b.QTD as qtd "
				+ " FROM COLETOR_PC a, COLETOR_PC_ITEM b, PRODUTO C, UNIDADE d, USUARIOEMPRESA ue "
				+ " WHERE (a.status = :statusLib OR a.status = :statusEmConf) "
				 + " AND a.ID_CPC = b.ID_CPC "
				 + " AND b.ID_PRODUTO = c.ID_PRODUTO "
				 + " AND b.ID_UNIDADE_CPR = d.ID_UNIDADE "
				 + " AND a.ID_PESSOA_EMP = ue.ID_PESSOA_EMP "
				 + " AND ue.ID_USUARIO = :usuarioId "
				+ " ORDER BY a.ID_CPC ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPlanilhaCegaItem.class);
		query.setParameter("statusLib", ColetorPlanilhaCega.STATUS_LIBERADO);
		query.setParameter("statusEmConf", ColetorPlanilhaCega.STATUS_EM_CONFERENCIA);
		query.setParameter("usuarioId", usuarioId);
		
		return query.list();
	}
	
	public void updateEmConferencia(Integer coletorPlanilhaCegaId, Date dtInicio) {
		String sql = "update coletor_pc "
						+ " set dtinicio = :dtInicio, status = :statusEmConf "
						+ " where id_cpc = :coletorPlanilhaCegaId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorPlanilhaCegaId", coletorPlanilhaCegaId);
		query.setParameter("statusEmConf", ColetorPlanilhaCega.STATUS_EM_CONFERENCIA);
		query.setParameter("dtInicio", dtInicio);
		
		query.executeUpdate();
		
	}

}