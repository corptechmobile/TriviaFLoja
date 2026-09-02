package br.com.coletor.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.UsuarioColetor;

public class DAOUsuarioColetor {
	
	private Session session;

	public DAOUsuarioColetor(Session session) {
		super();
		this.session = session;
	}

	public UsuarioColetor autenticar(String login, String senha) {
		
		String sql = " SELECT id_usuario as id, "
						  + " UPPER(login) AS nome, "
						  + " UPPER(login) AS login "
						+ " FROM usuario "
						+ " WHERE lower(login) = :login "
						  + " and lower(senha) = :senha "
						  + " and ativo = 1 ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(UsuarioColetor.class);
		query.setParameter("login", login.toLowerCase());
		query.setParameter("senha", senha.toLowerCase());
		query.setMaxResults(1);
		
		return (UsuarioColetor) query.uniqueResult();
	}

}
