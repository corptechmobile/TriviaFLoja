package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorSeparador;

public class DAOColetorSeparador {
	
	private Session session;

	public DAOColetorSeparador(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorSeparador carregar(Integer separadorId) {
		
		String sql = "SELECT s.id_pessoa AS id, "
						+ "	 p.NOMEFANTMNEM AS nome, "
						+ "	 s.ATIVO AS ativo "
						+ "	FROM separador s, pessoa p "
						+ "	WHERE s.id_pessoa = p.id_pessoa "
						+ "	AND s.id_pessoa = :separadorId ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorSeparador.class);
		query.setParameter("separadorId", separadorId);
		query.setMaxResults(1);
		
		return (ColetorSeparador)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ColetorSeparador> listar() {
		
		String sql = "SELECT s.id_pessoa AS id, "
						+ "	 p.NOMEFANTMNEM AS nome, "
						+ "	 s.ATIVO AS ativo "
						+ "	FROM separador s, pessoa p "
						+ "	WHERE s.id_pessoa = p.id_pessoa "
						+ " ORDER BY p.NOMEFANTMNEM ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorSeparador.class);
		
		return query.list();
	}

}
