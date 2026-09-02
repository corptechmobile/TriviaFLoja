package br.com.webapp.model.fb.coletorpc;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class ColetorPCDivergFBDAOHibernate implements ColetorPCDivergFBDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ColetorPCDivergFB carregar(Integer coletorId, int divergenciaId) {
		
		String sql =   " SELECT cpd.ID_CPC AS coletorId, "+
					          " cpd.ID_DIVERGENCIA AS divergenciaId, "+
				              " d.DESCRICAO as divergenciaDesc, "+
					          " cpd.DTAPROVACAO AS dtAprovacao, "+
					          " cpd.DTCREATE AS dtCreate, "+
					          " cpd.DTUPDATE AS dtUpdate, "+
					          " u.id_usuario AS usuarioAprovacaoId, "+
					          " u.LOGIN AS usuarioLogin, "+
					          " u.NOME AS usuarioNome "+
					     " FROM COLETOR_PC_DIVERG cpd "+
					          " LEFT JOIN USUARIO u ON (u.ID_USUARIO = cpd.ID_USUARIO_APROVACAO), "+
					          " COLETOR_DIVERGENCIA d "+
					    " WHERE cpd.ID_DIVERGENCIA = d.ID_DIVERGENCIA "+
					     "  AND cpd.ID_DIVERGENCIA = :divergenciaId "+
					      " AND cpd.ID_CPC = :coletorId ";
		
		Query query = session.createSQLQuery(sql)
				.addScalar("coletorId", Hibernate.INTEGER)
	 			.addScalar("divergenciaId", Hibernate.INTEGER)
	 			.addScalar("divergenciaDesc", Hibernate.STRING)
				.addScalar("dtAprovacao", Hibernate.TIMESTAMP)
				.addScalar("dtCreate", Hibernate.TIMESTAMP)
				.addScalar("dtUpdate", Hibernate.TIMESTAMP)
				.addScalar("usuarioAprovacaoId", Hibernate.INTEGER)
				.addScalar("usuarioLogin", Hibernate.STRING)
				.addScalar("usuarioNome", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCDivergFB.class));
		
		query.setParameter("divergenciaId", divergenciaId);
		query.setParameter("coletorId", coletorId);
		query.setMaxResults(1);
		
		return (ColetorPCDivergFB) query.uniqueResult();
		
	}
	
	@Override
	public ColetorPCDivergFB salvar(ColetorPCDivergFB coletorPCDivergFB) throws DAOException {
		try {

				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO COLETOR_PC_DIVERG(ID_DIVERGENCIA, ID_CPC, DTCREATE, DTUPDATE) ")
					    .append(" VALUES (:ID_DIVERGENCIA, :ID_CPC, :DTCREATE, :DTUPDATE) ");
	
				Query query = session.createSQLQuery(sql.toString());
				query.setParameter("ID_DIVERGENCIA", coletorPCDivergFB.getDivergenciaId());
				query.setParameter("ID_CPC", coletorPCDivergFB.getColetorId());
				query.setParameter("DTCREATE", coletorPCDivergFB.getDtCreate());
		        query.setParameter("DTUPDATE", coletorPCDivergFB.getDtUpdate());
				query.executeUpdate();

				return coletorPCDivergFB;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	@Override
	public void excluir() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<ColetorPCDivergFB> listar(Integer coletorPCId) {
		String sql =   " SELECT cpd.ID_CPC AS coletorId, "+
					          " cpd.ID_DIVERGENCIA AS divergenciaId, "+
				              " d.DESCRICAO as divergenciaDesc, "+
					          " cpd.DTAPROVACAO AS dtAprovacao, "+
					          " cpd.DTCREATE AS dtCreate, "+
					          " cpd.DTUPDATE AS dtUpdate, "+
					          " u.id_usuario AS usuarioAprovacaoId, "+
					          " u.LOGIN AS usuarioLogin, "+
					          " u.NOME AS usuarioNome "+
					     " FROM COLETOR_PC_DIVERG cpd "+
					          " LEFT JOIN USUARIO u ON (u.ID_USUARIO = cpd.ID_USUARIO_APROVACAO), "+
					          " COLETOR_DIVERGENCIA d "+
					    " WHERE cpd.ID_DIVERGENCIA = d.ID_DIVERGENCIA "+
					      " AND cpd.ID_CPC = :coletorPCId ";

		Query query = session.createSQLQuery(sql)
			.addScalar("coletorId", Hibernate.INTEGER)
			.addScalar("divergenciaId", Hibernate.INTEGER)
			.addScalar("divergenciaDesc", Hibernate.STRING)
			.addScalar("dtAprovacao", Hibernate.TIMESTAMP)
			.addScalar("dtCreate", Hibernate.TIMESTAMP)
			.addScalar("dtUpdate", Hibernate.TIMESTAMP)
			.addScalar("usuarioAprovacaoId", Hibernate.INTEGER)
			.addScalar("usuarioLogin", Hibernate.STRING)
			.addScalar("usuarioNome", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(ColetorPCDivergFB.class));
		
		query.setParameter("coletorPCId", coletorPCId);
		
		return query.list();
	}

	@Override
	public void update(ColetorPCDivergFB coletorPCDivergFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_PC_DIVERG SET DTAPROVACAO = :DTAPROVACAO, ID_USUARIO_APROVACAO = :ID_USUARIO_APROVACAO ")
				    .append(" WHERE ID_DIVERGENCIA = :ID_DIVERGENCIA AND ID_CPC = :ID_CPC ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("ID_DIVERGENCIA", coletorPCDivergFB.getDivergenciaId());
			query.setParameter("ID_CPC", coletorPCDivergFB.getColetorId());
			query.setParameter("DTAPROVACAO", coletorPCDivergFB.getDtAprovacao());
	        query.setParameter("ID_USUARIO_APROVACAO", coletorPCDivergFB.getUsuarioAprovacaoId());
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@Override
	public List<ColetorPCDivergFB> listar() {
		// TODO Auto-generated method stub
		return null;
	}

}
