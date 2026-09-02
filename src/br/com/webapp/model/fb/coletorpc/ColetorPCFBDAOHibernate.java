package br.com.webapp.model.fb.coletorpc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;

import br.com.webapp.model.fb.coletor.ColetorInvFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;

@SuppressWarnings({ "unchecked", "deprecation" })
public class ColetorPCFBDAOHibernate implements ColetorPCFBDAO {

	private StringBuilder COLLUMNS;
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public ColetorPCFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_CPC as id, ")
		        .append(" a.ID_ERP as idErp, ")
				.append(" a.ID_PESSOA_EMP as empresaId, ")
				.append(" a.ID_PESSOA_FORN as fornecedorId, ")
				.append(" a.ID_USUARIO as usuarioId, ")
				.append(" u.NOME as usuarioDesc, ")
				.append(" a.DTINICIO as dtInicio, ")
				.append(" a.DTTERMINO as dtTermino, ")
				.append(" a.DTCRIACAO as dtCriacao, ")
				.append(" a.DTLIBERACAO as dtLiberacao, ")
				.append(" a.STATUS, ")
				.append(" a.INFORMAR_LOTE as informarLote ");
	}

	
	@Override
	public void excluir(Integer coletorPCFBId) throws DAOException{
		try {
			String sql = "UPDATE COLETOR_PC SET STATUS = :status WHERE ID_CPC = :ID_CPC";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_CPC", coletorPCFBId);
			query.setParameter("status", ColetorPCFB.STATUS_EXCLUIDO);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public ColetorPCFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM COLETOR_PC a INNER JOIN USUARIO u ON (a.ID_USUARIO = u.ID_USUARIO) ")
		   .append(" WHERE a.ID_CPC = :id ");
		Query query = session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idErp", Hibernate.INTEGER)
	 			.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("fornecedorId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("dtLiberacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.addScalar("informarLote", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (ColetorPCFB) query.uniqueResult();
	}

	@Override
	public List<ColetorPCFB> listar(Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM COLETOR_PC a INNER JOIN USUARIO u ON (a.ID_USUARIO = u.ID_USUARIO) ")
		   .append(" WHERE a.ID_PESSOA_EMP = :empresaId ");
		Query query = session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idErp", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("fornecedorId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("dtLiberacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.addScalar("informarLote", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFB.class));
		query.setParameter("empresaId", empresaId);
		return query.list();
	}

	@Override
	public List<ColetorPCFB> listar(Integer empresaId, Integer fornecedorId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM COLETOR_PC a ")
		   .append(" WHERE a.ID_PESSOA_EMP = :empresaId ")
		   .append(" AND a.ID_PESSOA_FORN = :fornecedorId ");
		Query query = session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idErp", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("fornecedorId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("dtLiberacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.addScalar("informarLote", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFB.class));
		query.setParameter("empresaId", empresaId);
		query.setParameter("fornecedorId", fornecedorId);
		return query.list();
	}
	
	@Override
	public List<Integer> listarPendentesProcessar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.ID_CPC ")
		   .append(" FROM COLETOR_PC a ")
		   .append(" WHERE a.status = :status ")
		   .append(" AND a.ID_ERP IS NULL ")
		   .append(" ORDER BY a.ID_CPC ");
		SQLQuery query = session.createSQLQuery(sql.toString());
		query.setParameter("status", ColetorPCFB.STATUS_FINALIZADO);
		// Obtenha a lista de resultados
	    List<Object> results = query.list();

	    // Construa a lista de IDs
	    List<Integer> idsCPC = new ArrayList<>();
	    for (Object result : results) {
	        if (result != null && result instanceof Number) {
	            idsCPC.add(((Number) result).intValue());
	        }
	    }

	    return idsCPC;
	}

	@Override
	public Integer insert(ColetorPCFB coletorPC) throws DAOException {
		try {
			Integer coletorPCId = getSeq();
			System.out.println("[ColetorPCFBDAOHibernate][insert][id]" + coletorPCId);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO COLETOR_PC (ID_CPC, ID_PESSOA_EMP, ID_PESSOA_FORN, ID_USUARIO, DTINICIO, DTTERMINO, STATUS, DTCRIACAO, INFORMAR_LOTE)")
			.append("VALUES (:ID_CPC, ")
			        .append(":ID_PESSOA_EMP, ")
			        .append(":ID_PESSOA_FORN, ")
			        .append(":ID_USUARIO, ")
			        .append(":DTINICIO, ")
			        .append(":DTTERMINO, ")
			        .append(":STATUS, ")
			        .append(":DTCRIACAO, ")
			        .append(":INFORMAR_LOTE) ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("ID_CPC", coletorPCId);
			query.setParameter("ID_PESSOA_EMP", coletorPC.getEmpresaId());
	        query.setParameter("ID_PESSOA_FORN", coletorPC.getFornecedorId());
	        query.setParameter("ID_USUARIO", coletorPC.getUsuarioId());
	        query.setParameter("DTINICIO", coletorPC.getDtInicio());
	        query.setParameter("DTTERMINO", coletorPC.getDtTermino());
	        query.setParameter("STATUS", coletorPC.getStatus());
	        query.setParameter("DTCRIACAO", coletorPC.getDtCriacao());
	        query.setParameter("INFORMAR_LOTE", coletorPC.getInformarLote());
			query.executeUpdate();
			return coletorPCId;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void update(ColetorPCFB coletorPC) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_PC set DTLIBERACAO = :dtLiberacao, DTTERMINO = :dtTermino, STATUS = :status, INFORMAR_LOTE = :informarLote, ID_ERP = :idErp ")
			   .append(" WHERE ID_CPC = :coletorPCId ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("coletorPCId", coletorPC.getId());
			query.setParameter("dtLiberacao", coletorPC.getDtLiberacao());
			query.setParameter("dtTermino", coletorPC.getDtTermino());
			query.setParameter("status", coletorPC.getStatus());
			query.setParameter("idErp", coletorPC.getIdErp());
			query.setParameter("informarLote", coletorPC.getInformarLote());
			
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_COLETOR_PC_ID, 1) from rdb$database;";
			Query q = session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do ColetorPCFB.");
		}
	}

	@Override
	public List<ColetorPCFBDTO> listar(EmpresaFB empresaFilter, String fornecedorFilter, String planilhaCegaIdFilter, String notafiscalFilter, String planilhaCegaFilter, String produtoFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter, Set<EmpresaFB> empresas) {

		String varWhere = "";
		if(empresaFilter != null) {
			varWhere = " AND cp.ID_PESSOA_EMP = :empresa ";
		}

		if(planilhaCegaIdFilter != null && !"".equals(planilhaCegaIdFilter)) {
			varWhere += " AND cp.id_cpc =  :planilhaCegaIdFilter ";


		}
		if(notafiscalFilter!= null && !"".equals(notafiscalFilter)) {
			varWhere += " AND nfc.NUMNF = :notafiscalFilter ";
		}

		if(fornecedorFilter != null&& !"".equals(fornecedorFilter)) {

			varWhere += " AND UPPER(fnc.RAZAOSOCIALNOME) LIKE :fornecedorFilter ";
		}
		
		if(produtoFilter != null && !"".equals(produtoFilter)) {
			varWhere += " AND UPPER(p.descricao) LIKE :produtoFilter ";
		}
		
		if(data1Filter != null && data2Filter != null) {
			varWhere += " AND cp.DTCRIACAO BETWEEN :dt1 AND :dt2 ";
		}
		
		if(concluidoFilter) {
			varWhere += " AND cp.STATUS = :status ";
		}else {
			varWhere += " AND cp.STATUS not in (:status) ";
		}



		String sql = " SELECT cp.ID_CPC id, "+
				     " 		  max(cp.ID_ERP) AS idErp, "+
				     " 		  max(cp.ID_PESSOA_EMP) AS empresaId, "+
					 " 		  max(emp.RAZAOSOCIALNOME) AS empresaDesc, "+
					 " 		  max(fnc.ID_PESSOA) AS fornecedorId, "+
					 "     	  max(fnc.RAZAOSOCIALNOME) AS fornecedorDesc, "+
					 "     	  max(u.login) AS usuarioDesc, "+
					 "     	  max(cp.DTINICIO) AS dtInicio, "+
					 "     	  max(cp.DTTERMINO) AS dtTermino, "+
					 "     	  max(cp.DTCRIACAO) AS dtCriacao, "+
					 "     	  max(cp.DTLIBERACAO) AS dtLiberacao, "+
					 "     	  max(cp.status) AS status "+
					 "   FROM COLETOR_PC cp "+
					 "        LEFT JOIN PESSOA emp ON (cp.ID_PESSOA_EMP = emp.ID_PESSOA) "+
					 "     	  LEFT JOIN PESSOA fnc ON (cp.ID_PESSOA_FORN = fnc.ID_PESSOA) "+
					 "     	  LEFT JOIN COLETOR_PC_NFCOMPRA cpn ON (cp.ID_CPC = cpn.ID_CPC) "+
					 "     	  LEFT JOIN NFCOMPRA nfc ON (cpn.ID_NFCOMPRA = nfc.ID_NFCOMPRA) "+
					 "     	  LEFT JOIN COLETOR_PC_ITEM cpi ON (cpi.ID_CPC = cp.ID_CPC) "+
					 "     	  LEFT JOIN PRODUTO p ON (cpi.ID_PRODUTO = p.ID_PRODUTO) "+
					 "     	  INNER JOIN USUARIO u ON (cp.ID_USUARIO = u.ID_USUARIO) "+
					 "  WHERE 1 = 1 "+
					 "    AND cp.status <> :statusExcluido "+ 
					 "   "+varWhere+" "+
					 "  GROUP BY CP.ID_CPC ";

		Query q = this.session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idErp", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("fornecedorId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("fornecedorDesc", Hibernate.STRING)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("dtLiberacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFBDTO.class));

		if(empresaFilter != null) {
			q.setParameter("empresa", empresaFilter.getId());
		}

		if(planilhaCegaIdFilter != null && !"".equals(planilhaCegaIdFilter)) {
			q.setParameter("planilhaCegaIdFilter", planilhaCegaIdFilter);

		}

		if(notafiscalFilter!= null && !"".equals(notafiscalFilter)) {
			q.setParameter("notafiscalFilter", notafiscalFilter);
		}

		if(fornecedorFilter!= null && !"".equals(fornecedorFilter)) {
			q.setParameter("fornecedorFilter", "%"+fornecedorFilter.toUpperCase()+"%");
		}

		if(produtoFilter!= null && !"".equals(produtoFilter)) {
			q.setParameter("produtoFilter","%"+produtoFilter.toUpperCase()+"%");
		}
		
		if(data1Filter != null && data2Filter != null) {
			q.setParameter("dt1", data1Filter);
			q.setParameter("dt2", data2Filter);
		}
		
		q.setParameter("status", ColetorInvFB.STATUS_FINALIZADO);
		q.setParameter("statusExcluido", ColetorPCFB.STATUS_EXCLUIDO);

		return q.list();
	}
	
	@Override
	public Integer verificarFinalizacaoAutomatica(Integer coletorPCFBId) {
		String sql = "SELECT CASE WHEN sum(tab.qtdAConferir) = sum(tab.qtdConferida) "
				+ "		AND sum(tab.qtdDevolvida) = 0 "
				+ "		AND sum(tab.qtdAvaria) = 0 THEN 1 ELSE 0 END AS finalizar "
				+ "	FROM ("
				+ "	  SELECT ID_PRODUTO, "
				+ "	  		 0 AS qtdAConferir, "
				+ "	  		 QTD_CONFERIDA as qtdConferida, "
				+ "			 QTD_DEVOLVIDA as qtdDevolvida, "
				+ "			 QTD_AVARIA qtdAvaria "
				+ "		 FROM COLETOR_PC_CONTAGEM "
				+ "		 WHERE ID_CPC = :coletorPCFBId "
				+ "		   AND EXCLUIDO = :excluido "
				+ "	UNION "
				+ "	  SELECT ID_PRODUTO, "
				+ "	  		 QTD AS qtdAConferir, "
				+ "	  		 0 as qtdConferida, "
				+ "			 0 as qtdDevolvida, "
				+ "			 0 qtdAvaria "
				+ "		 FROM COLETOR_PC_ITEM"
				+ "		 WHERE ID_CPC = :coletorPCFBId"
				+ "	 ) tab ";
		
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar("finalizar", new IntegerType());
		query.setParameter("coletorPCFBId", coletorPCFBId);
		query.setParameter("excluido", 0);
		query.setMaxResults(1);
		Object result = query.uniqueResult();
	    if (result != null) {
	        return (Integer) result;
	    } else {
	        return 0;
	    }
	}
	
	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}

}