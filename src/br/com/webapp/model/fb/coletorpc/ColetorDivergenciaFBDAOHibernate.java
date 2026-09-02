package br.com.webapp.model.fb.coletorpc;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class ColetorDivergenciaFBDAOHibernate implements ColetorDivergenciaFBDAO{

	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public ColetorDivergenciaFB carregar() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ColetorDivergenciaFB salvar(ColetorDivergenciaFB coletorPCDivergenciaFB)  {
		StringBuilder sql = new StringBuilder(); 
		sql.append(" INSERT INTO USUARIO_COLETORDIVERG(ID_USUARIO, ID_DIVERGENCIA ) " )
		.append(" VALUES(:ID_USUARIO, ID_DIVERGENCIA )" );
		
		Query query = session.createSQLQuery(sql.toString()); 
		query.setParameter("ID_USUARIO", coletorPCDivergenciaFB.getUsuarioId());
		query.setParameter("ID_DIVERGENCIA", coletorPCDivergenciaFB.getDivergenciaId());
		return coletorPCDivergenciaFB;
	}
	
	
	@Override
	public void excluir(ColetorDivergenciaFB coletorPCDivergenciaFB) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void excluir(Integer coletorId) throws DAOException {
		try {
			String sql = "DELETE FROM COLETOR_PC_DIVERG WHERE ID_CPC = :coletorId";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("coletorId", coletorId);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		
	}
	@Override
	public List<ColetorDivergenciaFB> listar(Integer divergenciaId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ColetorDivergenciaFB> listar() {
		String sql =   " SELECT d.ID_DIVERGENCIA AS divergenciaId, "
				+ "	            D.DESCRICAO as divergenciaDesc "
				+ "	       FROM COLETOR_DIVERGENCIA d "
				+ "	      ORDER BY D.ID_DIVERGENCIA  ";

		Query query = session.createSQLQuery(sql)
		.addScalar("divergenciaId", Hibernate.INTEGER)
		.addScalar("divergenciaDesc", Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(ColetorDivergenciaFB.class));
		return query.list();

	}
	@Override
	public List<ColetorDivergenciaFB> listarPorUsuario(Integer usuarioId) {
		String sql =   " SELECT d.ID_DIVERGENCIA AS divergenciaId, "
				+ "	            D.DESCRICAO as divergenciaDesc "
				+ "	       FROM COLETOR_DIVERGENCIA d, "
				+ "             USUARIO_COLETORDIVERG ud "
				+ "	      WHERE d.ID_DIVERGENCIA = ud.ID_DIVERGENCIA "
				+ "         AND ud.ID_USUARIO = :usuarioId "
				+ "	      ORDER BY D.ID_DIVERGENCIA  ";

		Query query = session.createSQLQuery(sql)
		.addScalar("divergenciaId", Hibernate.INTEGER)
		.addScalar("divergenciaDesc", Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(ColetorDivergenciaFB.class));
		
		query.setParameter("usuarioId", usuarioId);
		
		return query.list();
	}
	
	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}
	
}
