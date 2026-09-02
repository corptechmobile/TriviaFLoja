package br.com.webapp.model.fb.romaneio;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.coletor.model.ColetorOrdSepItemContagem;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;

public class RomaneioFBDAOHibernate implements RomaneioFBDAO {

	private StringBuilder COLLUMNS;
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public RomaneioFBDAOHibernate() {
		COLLUMNS = new StringBuilder(); 
		COLLUMNS.append(" r.ID_ROMANEIO as romaneioId, ")
		        .append(" r.ID_PROCTRANSP as procTranspId, ")
				.append(" r.STATUS as STATUS, ")
				.append(" r.MOMENTOGER as momentoGer, ")
				.append(" r.ID_USUARIO_GER as usuarioIdGer, ")
				.append(" DECODE(POSITION(' ',ug.NOME),0,ug.NOME,SUBSTRING(ug.NOME FROM 1 FOR POSITION(' ',ug.NOME))) as usuarioGer, ")
				.append(" r.MOMENTOCONF as momentoConf, ")
				.append(" r.ID_USUARIO_CONF as usuarioIdConf, ")
				.append(" DECODE(POSITION(' ',uc.NOME),0,uc.NOME,SUBSTRING(uc.NOME FROM 1 FOR POSITION(' ',uc.NOME))) as usuarioConf, ")
				.append(" r.MOMENTOCORTE as momentoCorte, ")
				.append(" r.ID_USUARIO_CORTE as usuarioIdCorte, ")
				.append(" DECODE(POSITION(' ',uct.NOME),0,uct.NOME,SUBSTRING(uct.NOME FROM 1 FOR POSITION(' ',uct.NOME))) as usuarioCorte, ")
				.append(" r.MOMENTOFINALIZADO as momentoFinalizado, ")
				.append(" r.ID_USUARIO_FINALIZADO as usuarioIdFinalizado, ")
				.append(" DECODE(POSITION(' ',uf.NOME),0,uf.NOME,SUBSTRING(uf.NOME FROM 1 FOR POSITION(' ',uf.NOME))) as usuarioFinalizado, ")
				.append(" r.MOMENTOCANC as momentoCanc, ")
				.append(" r.ID_USUARIO_CANC as usuarioIdCanc, ")
		        .append(" DECODE(POSITION(' ',uca.NOME),0,uca.NOME,SUBSTRING(uca.NOME FROM 1 FOR POSITION(' ',uca.NOME))) as usuarioCanc ");
	}

	
	@Override
	public void excluir(Integer romaneioFBId) throws DAOException{
		try {
			String sql = "UPDATE ROMANEIO SET STATUS = :status WHERE ID_ROMANEIO = :ID_ROMANEIO";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_ROMANEIO", romaneioFBId);
			query.setParameter("status", RomaneioFB.STATUS_CANCELADO);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public RomaneioFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM ROMANEIO r ")
		   .append("      LEFT JOIN USUARIO ug ON (r.ID_USUARIO_GER = ug.ID_USUARIO) ")
		   .append("	  LEFT JOIN USUARIO uc ON (r.ID_USUARIO_CONF = uc.ID_USUARIO) ")
		   .append("	  LEFT JOIN USUARIO uct ON (r.ID_USUARIO_CORTE = uct.ID_USUARIO) ")
		   .append("	  LEFT JOIN USUARIO uf ON (r.ID_USUARIO_FINALIZADO = uf.ID_USUARIO) ")
		   .append("	  LEFT JOIN USUARIO uca ON (r.ID_USUARIO_CANC = uca.ID_USUARIO) ") 
		   .append(" WHERE r.ID_ROMANEIO = :id ");
		Query query = session.createSQLQuery(sql.toString())
				.addScalar("romaneioId", Hibernate.INTEGER)
				.addScalar("procTranspId", Hibernate.INTEGER)
	 			.addScalar("usuarioIdGer", Hibernate.INTEGER)
	 			.addScalar("usuarioGer", Hibernate.STRING)
	 			.addScalar("momentoGer", Hibernate.TIMESTAMP)
	 			.addScalar("usuarioIdConf", Hibernate.INTEGER)
	 			.addScalar("usuarioConf", Hibernate.STRING)
	 			.addScalar("momentoConf", Hibernate.TIMESTAMP)
	 			.addScalar("usuarioIdCorte", Hibernate.INTEGER)
	 			.addScalar("usuarioCorte", Hibernate.STRING)
	 			.addScalar("momentoCorte", Hibernate.TIMESTAMP)
	 			.addScalar("usuarioIdFinalizado", Hibernate.INTEGER)
	 			.addScalar("usuarioFinalizado", Hibernate.STRING)
	 			.addScalar("momentoFinalizado", Hibernate.TIMESTAMP)
	 			.addScalar("usuarioIdCanc", Hibernate.INTEGER)
	 			.addScalar("usuarioCanc", Hibernate.STRING)
	 			.addScalar("momentoCanc", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(RomaneioFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (RomaneioFB) query.uniqueResult();
	}
	
	@Override
	public void cancelar(RomaneioFB romaneio, UsuarioFB usuarioLogado) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ROMANEIO set MOMENTOCANC = :MOMENTOCANC, ID_USUARIO_CANC = :ID_USUARIO_CANC, STATUS = :status ")
			   .append(" WHERE ID_ROMANEIO = :romaneioId ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("romaneioId", romaneio.getRomaneioId());
			query.setParameter("MOMENTOCANC", romaneio.getMomentoCanc());
			query.setParameter("ID_USUARIO_CANC", usuarioLogado.getId());
			query.setParameter("status", RomaneioFB.STATUS_CANCELADO);
			
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@Override
	public void update(RomaneioFB romaneio) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ROMANEIO set MOMENTOFINALIZADO = :MOMENTOFINALIZADO, ID_USUARIO_FINALIZADO = :ID_USUARIO_FINALIZADO, STATUS = :status ")
			   .append(" WHERE ID_ROMANEIO = :romaneioId ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("romaneioId", romaneio.getRomaneioId());
			query.setParameter("MOMENTOFINALIZADO", romaneio.getMomentoFinalizado());
			query.setParameter("ID_USUARIO_FINALIZADO", romaneio.getUsuarioFinalizado());
			query.setParameter("status", romaneio.getStatus().toString());
			
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_ROMANEIO_ID, 1) from rdb$database;";
			Query q = session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do RomaneioFB.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RomaneioFB> listar(String numProcTranspFilter, String numRomaneioFilter, String usuarioFilter, String produtoFilter, Date data1Filter, Date data2Filter, List<Integer> statusFilter) {

		String varWhere = "";
		if(numProcTranspFilter != null && !"".equals(numProcTranspFilter)) {
			varWhere = " AND r.id_proctransp = :numProcTranspFilter ";
		}

		if(numRomaneioFilter != null && !"".equals(numRomaneioFilter)) {
			varWhere += " AND r.id_romaneio =  :numRomaneioFilter ";
		}

		if(produtoFilter != null && !"".equals(produtoFilter)) {
			varWhere += " AND (UPPER(p.descricao) LIKE :produtoFilter or p.codinterno = :produtoFilterCod) ";
		}
		
		if(data1Filter != null && data2Filter != null) {
			varWhere += " AND r.momentoger BETWEEN :dt1 AND :dt2 ";
		}
		
		if (statusFilter != null && !statusFilter.isEmpty()) {
		    StringBuilder inClause = new StringBuilder();
		    
		    for (int i = 0; i < statusFilter.size(); i++) {
		        // Como o JSF pode às vezes mandar os IDs como String ou Long dependendo da submissão,
		        // convertemos usando Number para garantir o valor numérico correto.
		        Object item = statusFilter.get(i);
		        Integer idStatus = null;
		        
		        if (item instanceof Number) {
		            idStatus = ((Number) item).intValue();
		        } else if (item instanceof String) {
		            idStatus = Integer.parseInt((String) item);
		        }

		        if (idStatus != null) {
		            inClause.append(idStatus);
		            if (i < statusFilter.size() - 1) {
		                inClause.append(",");
		            }
		        }
		    }
		    
		    String clausulaFinal = inClause.toString().replaceAll(",$", "");
		    if (!clausulaFinal.isEmpty()) {
		        varWhere += " AND r.status IN (" + clausulaFinal + ") "; 
		    }
		}



		String sql = " select r.id_romaneio as romaneioId, "+
				     "        r.id_proctransp as procTranspId, "+
				     "        MAX(r.status) as status, "+
				     "        MAX(r.momentoger) as momentoGer, "+
				     "        MAX(r.momentoConf) as momentoConf, "+
				     "        MAX(r.momentoCorte) as momentoCorte, "+
				     "        MAX(r.momentoFinalizado) as momentoFinalizado, "+
				     "        MAX(r.momentoCanc) as momentoCanc, "+
				     "        MAX(r.id_usuario_ger) as usuarioIdGer, "+
				     "        MAX(r.id_usuario_conf) as usuarioIdConf, "+
				     "        MAX(r.ID_USUARIO_CORTE) as usuarioIdCorte, "+
				     "        MAX(r.ID_USUARIO_FINALIZADO) as usuarioIdFinalizado, "+
				     "        MAX(r.ID_USUARIO_CANC) as usuarioIdCanc "+
				     "   from romaneio r, "+
				     "        romaneioitem ri, "+
				     "        proctransp pt, "+
				     "        produto p "+
				     "  where r.id_romaneio = ri.id_romaneio "+
				     "    and r.id_proctransp = pt.id_proctransp "+
				     "    and ri.id_produto = p.id_produto "+
					 "   "+varWhere+" "+
				     " GROUP BY r.id_romaneio, r.id_proctransp "; 

		Query q = this.session.createSQLQuery(sql)
				.addScalar("romaneioId", Hibernate.INTEGER)
				.addScalar("procTranspId", Hibernate.INTEGER)
				.addScalar("status", Hibernate.STRING)
				.addScalar("momentoGer", Hibernate.TIMESTAMP)
				.addScalar("momentoConf", Hibernate.TIMESTAMP)
				.addScalar("momentoCorte", Hibernate.TIMESTAMP)
				.addScalar("momentoFinalizado", Hibernate.TIMESTAMP)
				.addScalar("momentoCanc", Hibernate.TIMESTAMP)
				.addScalar("usuarioIdGer", Hibernate.INTEGER)
				.addScalar("usuarioIdConf", Hibernate.INTEGER)
				.addScalar("usuarioIdCorte", Hibernate.INTEGER)
				.addScalar("usuarioIdFinalizado", Hibernate.INTEGER)
				.addScalar("usuarioIdCanc", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RomaneioFB.class));


		if(numProcTranspFilter != null && !"".equals(numProcTranspFilter)) {
			q.setParameter("numProcTranspFilter", numProcTranspFilter);
		}

		if(numRomaneioFilter != null && !"".equals(numRomaneioFilter)) {
			q.setParameter("numRomaneioFilter", numRomaneioFilter);
		}

		if(produtoFilter!= null && !"".equals(produtoFilter)) {
			q.setParameter("produtoFilter","%"+produtoFilter.toUpperCase()+"%");
			q.setParameter("produtoFilterCod",produtoFilter);
		}
		
		if(data1Filter != null && data2Filter != null) {
			q.setParameter("dt1", data1Filter);
			q.setParameter("dt2", data2Filter);
		}
		
//		if(statusFilter!=null) {
//			q.setParameter("statusFilter", statusFilter);
//		}

		return q.list();
	}
	
	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}

	@Override
	public void finalizar(RomaneioFB romaneioFB, UsuarioFB usuarioLogado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizarStatus(Integer romaneioId, String statusEmConferencia) throws DAOException {
		try {
			
			String sql= "update ROMANEIO SET status = :emConferencia where id_romaneio = :romaneioId";
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("romaneioId", romaneioId);
			query.setParameter("emConferencia", statusEmConferencia);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		
	}
	
	
	public Integer integracao(Integer ordemCarregId) throws DAOException {
		Integer seqId = getSeqId();
		
		String sql = "INSERT INTO WMS_ORDEMCARREG "
					+ " (ID_ORDEMCARREG_WMS, ID_ORDEMCARREG, STATUS, MOMENTOFIMSEPARACAO, MENSAGEM) "
					+ "VALUES "
					+ " (:ID_ORDEMCARREG_WMS, :ID_ORDEMCARREG, 0, CURRENT_TIMESTAMP, '')";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepItemContagem.class);
		query.setParameter("ID_ORDEMCARREG_WMS", seqId);
		query.setParameter("ID_ORDEMCARREG", ordemCarregId);
		int result = query.executeUpdate();
		if(result == 0) {
			throw new DAOException("Erro ao realizar integração da Conferência/Saída com o ERP.");
		}
		
		return seqId;
	}
	
	private Integer getSeqId() throws DAOException {
		try {
			String sql = "select gen_id(GEN_WMS_ORDEMCARREG_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaFB.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RomaneioIntegracaoDTOFB> listarParaIntegracao(Integer romaneioId) {

		String sql = " SELECT o.ID_ORDEMCARREG AS ordemcarregId, max(crc.DTLEITURA) AS momentoFimSeparacao, 0 AS status  "
					+ "  FROM ROMANEIO r "
					+ "       LEFT JOIN COLETOR_ROMANEIO_CONTAGEM crc ON (crc.ID_ROMANEIO = r.ID_ROMANEIO "
					+ "                                               AND r.ID_ROMANEIO = :romaneioId), "
					+ "       ROMANEIOITEMPEDIDO ri, "
					+ "       ORDEMCARREG o, "
					+ "       ordemcarregitem oci "
					+ " WHERE r.ID_ROMANEIO = ri.ID_ROMANEIO "
					+ "   AND ri.ID_ORDEMCARREGITEM = oci.ID_ORDEMCARREGITEM "
					+ "   AND oci.ID_ORDEMCARREG = o.ID_ORDEMCARREG "
					+ "   AND r.ID_ROMANEIO = :romaneioId "
					+ "GROUP BY o.ID_ORDEMCARREG "; 

		Query q = this.session.createSQLQuery(sql)
				.addScalar("ordemcarregId", Hibernate.INTEGER)
				.addScalar("momentoFimSeparacao", Hibernate.DATE)
				.setResultTransformer(Transformers.aliasToBean(RomaneioIntegracaoDTOFB.class));


		q.setParameter("romaneioId", romaneioId);
		

		return q.list();
	}

	


}

