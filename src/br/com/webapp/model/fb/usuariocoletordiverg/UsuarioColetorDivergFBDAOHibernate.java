package br.com.webapp.model.fb.usuariocoletordiverg;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.web.util.DAOException;

public class UsuarioColetorDivergFBDAOHibernate implements UsuarioColetorDivergFBDAO{

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void excluir(UsuarioColetorDivergFB usuarioDivergenciFB) {
		String sql = "DELETE FROM USUARIO_COLETORDIVERG "
				+ "WHERE ID_USUARIO = :usuarioId "
				+ "AND  ID_DIVERGENCIA = :divergenciaId ";
			Query query =  session.createSQLQuery(sql);
			query.setParameter("usuarioId", usuarioDivergenciFB.getUsuarioId());
			query.setParameter("divergenciaId", usuarioDivergenciFB.getDivergenciaId());
			query.executeUpdate();
	}
	
	@Override
	public void excluir(Integer usuarioId) {
		
		String sql = "DELETE FROM USUARIO_COLETORDIVERG "
				+ "WHERE ID_USUARIO = :usuarioId ";
		
			Query query =  session.createSQLQuery(sql);
			query.setParameter("usuarioId", usuarioId);
			query.executeUpdate();
		
	}
	
	@Override
	public UsuarioColetorDivergFB carregar() {	
		return null;
	} 
	
	@Override
	public UsuarioColetorDivergFB carregar(Integer usuarioId, Integer divergenciaId) {
		String sql =   " SELECT d.ID_DIVERGENCIA AS divergenciaId, "
				+ "	            d.ID_USUARIO as usuarioId "
				+ "	       FROM USUARIO_COLETORDIVERG d "
				+ "	      WHERE D.ID_USUARIO = :usuarioId  "
				+ "	        AND D.ID_DIVERGENCIA = :divergenciaId  ";

		Query query = session.createSQLQuery(sql)
		.addScalar("divergenciaId", Hibernate.INTEGER)
		.addScalar("usuarioId", Hibernate.INTEGER)
		.setResultTransformer(Transformers.aliasToBean(UsuarioColetorDivergFB.class));
		query.setMaxResults(1);
		
		query.setParameter("usuarioId", usuarioId);
		query.setParameter("divergenciaId", divergenciaId);
		return (UsuarioColetorDivergFB) query.uniqueResult();		
		
	}
	
	@Override
	public List<UsuarioColetorDivergFB> listar() {	
		return null;
	}
	@Override
	public UsuarioColetorDivergFB salvar(UsuarioColetorDivergFB usuarioColetorDivergFB) throws DAOException {
		StringBuilder sql = new StringBuilder();
		
		try {
		
			sql.append("INSERT INTO USUARIO_COLETORDIVERG( ID_USUARIO, ID_DIVERGENCIA )" )
			.append(" VALUES(:ID_USUARIO, :ID_DIVERGENCIA)");
			
			Query query = session.createSQLQuery(sql.toString()); 
			query.setParameter("ID_USUARIO", usuarioColetorDivergFB.getUsuarioId());		
			query.setParameter("ID_DIVERGENCIA", usuarioColetorDivergFB.getDivergenciaId());
			
			query.executeUpdate();
			
			return usuarioColetorDivergFB;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void salvar(Integer usuarioId, Integer divergenciaId) throws DAOException {
		StringBuilder sql = new StringBuilder();
		
		try {
		
			sql.append("INSERT INTO USUARIO_COLETORDIVERG( ID_USUARIO, ID_DIVERGENCIA )" )
			.append(" VALUES(:ID_USUARIO, :ID_DIVERGENCIA)");
			
			Query query = session.createSQLQuery(sql.toString()); 
			query.setParameter("ID_USUARIO", usuarioId);		
			query.setParameter("ID_DIVERGENCIA", divergenciaId);
			
			query.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}		
	}
	
	
	

	
		
}
