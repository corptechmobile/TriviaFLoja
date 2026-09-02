package br.com.webapp.model.fb.usuario;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class UsuarioFBDAOHibernate implements UsuarioFBDAO{
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public UsuarioFB carregar(String login) {
		String sql = "select u.id_usuario AS id, " 
						+ " v.id_pessoa AS vendedorId, " 
						+ " u.id_gestaovenda AS gestaoVendaId, "
						+ " g.codedt AS gestaoVendaCodEdt, " 
						+ " u.nome as nome, " 
						+ " u.login AS login, "
						+ " u.senha AS senha, "
						+ " u.ativo AS ativo, "
						+ " u.isvendedor AS isVendedor, "
						+ " u.id_pessoa_emp AS empresaId "
					+ " FROM usuario u "
					+ " LEFT JOIN vendedor v ON (u.id_usuario = v.id_usuario) "
					+ " LEFT JOIN gestaovenda g ON (u.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE upper(u.login) = :login and u.ativo = :ativo ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("vendedorId", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("login", Hibernate.STRING)
				.addScalar("senha", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("isVendedor", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(UsuarioFB.class));
		q.setParameter("login", login);
		q.setParameter("ativo", UsuarioFB.ATIVO);
		q.setMaxResults(1);
		
		return (UsuarioFB) q.uniqueResult();
	}

	@Override
	public UsuarioFB carregar(Integer usuarioId) {
		String sql = "select u.id_usuario AS id, " 
						 + " v.id_pessoa AS vendedorId, " 
						 + " u.id_gestaovenda AS gestaoVendaId, "
						 + " g.codedt AS gestaoVendaCodEdt, " 
						 + " u.nome as nome, " 
						 + " u.login AS login, "
						 + " u.senha AS senha, "
						 + " u.ativo AS ativo, "
						 + " u.isvendedor AS isVendedor, "
							+ " u.id_pessoa_emp AS empresaId "
					+ " FROM usuario u "
					+ " LEFT JOIN vendedor v ON (u.id_usuario = v.id_usuario) "
					+ " LEFT JOIN gestaovenda g ON (u.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE u.id_usuario = :ID_USUARIO ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("vendedorId", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("login", Hibernate.STRING)
				.addScalar("senha", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("isVendedor", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(UsuarioFB.class));
		q.setParameter("ID_USUARIO", usuarioId);
		q.setMaxResults(1);
		
		return (UsuarioFB) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioFB> listar(String descricaoFilter, Boolean situacaoFilter) {
		String varWhere = "";
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			varWhere += " AND (u.login like :descricaoFilter or u.nome like :descricaoFilter) ";
		}
		
		if(situacaoFilter!=null) {
			varWhere += " AND u.ativo = :situacaoFilter";
		}
		
		String sql = "select u.id_usuario AS id, " 
						+ " v.id_pessoa AS vendedorId, " 
						+ " u.id_gestaovenda AS gestaoVendaId, "
						+ " g.codedt AS gestaoVendaCodEdt, " 
						+ " u.nome as nome, " 
						+ " u.login AS login, "
						+ " u.senha AS senha, "
						+ " u.ativo AS ativo, "
						+ " u.isvendedor AS isVendedor, "
						+ " u.id_pessoa_emp AS empresaId "
					+ " FROM usuario u "
					+ " LEFT JOIN vendedor v ON (u.id_usuario = v.id_usuario) "
					+ " LEFT JOIN gestaovenda g ON (u.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE u.id_usuario is not null " + varWhere 
					+ " ORDER BY u.nome ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("vendedorId", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("login", Hibernate.STRING)
				.addScalar("senha", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("isVendedor", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(UsuarioFB.class));
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			q.setParameter("descricaoFilter", "%"+descricaoFilter+"%");
		}
		
		if(situacaoFilter!=null) {
			q.setParameter("situacaoFilter", (situacaoFilter == false ? UsuarioFB.INATIVO : UsuarioFB.ATIVO));
		}
		
		return q.list();
	}

	@Override
	public void salvarNovaSenha(Integer usuarioId, String senhaNova) throws DAOException {
		try {
			
			System.out.println("[UsuarioFBDAOHibernate][salvarNovaSenha][id]" + usuarioId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE USUARIO SET ")
					        .append("SENHA = :SENHA ")
					      .append(" WHERE ID_USUARIO = :ID_USUARIO");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_USUARIO", usuarioId);
			query.setParameter("SENHA", senhaNova);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

}
