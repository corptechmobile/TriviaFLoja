package br.com.webapp.model.menu;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.web.util.DAOException;

public class MenuAcessoDAOHibernate implements MenuAcessoDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public MenuAcesso salvar(MenuAcesso menuAcesso) {
		MenuAcesso merged = (MenuAcesso) this.session.merge(menuAcesso);
		this.session.flush();
		this.session.clear();
		
		return merged;
	}
	
	public void excluir(MenuAcesso menuAcesso) throws DAOException {
		
		try {
			
			String sql = " delete from menurecente where id_menuacesso = :id_menuacesso ";
			this.session.createSQLQuery(sql).setParameter("id_menuacesso", menuAcesso.getId()).executeUpdate();
			
			sql = " delete from menufavorito where id_menuacesso = :id_menuacesso ";
			this.session.createSQLQuery(sql).setParameter("id_menuacesso", menuAcesso.getId()).executeUpdate();
			
			sql = " delete from menu_usuariogrupo where id_menuacesso = :id_menuacesso ";
			this.session.createSQLQuery(sql).setParameter("id_menuacesso", menuAcesso.getId()).executeUpdate();
			
//			sql = " update usuario set id_menuacesso_home = null where id_menuacesso_home = :id_menuacesso ";
//			this.session.createSQLQuery(sql).setParameter("id_menuacesso", menuAcesso.getId()).executeUpdate();
			
//			sql = " delete from menuacesso where id_parent = :id_parent ";
//			this.session.createSQLQuery(sql).setParameter("id_parent", menuAcesso.getId()).executeUpdate();
			
			sql = " delete from menuacesso where id_menuacesso = :id_menuacesso ";
			this.session.createSQLQuery(sql).setParameter("id_menuacesso", menuAcesso.getId()).executeUpdate();
			
		}catch (PersistenceException e) {
			e.printStackTrace();
	        throw new DAOException(e.getMessage());
		}catch(ConstraintViolationException e){
			e.printStackTrace();
			
			if(e.getConstraintName().equals("fkfd6824cba1d0af5a")) {
	            throw new DAOException("Não é possível excluir esse menu, delete os filhos do mesmo!");
	        } else {
	        	throw new DAOException(e.getMessage());
	        }
		}catch (Exception e) {
	    	e.printStackTrace();
	    	throw new DAOException(e.getMessage());
		}
		
	}
	
	public MenuAcesso carregar(Integer id_menuacesso) {
		return (MenuAcesso) this.session.get(MenuAcesso.class, id_menuacesso);
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuAcesso> listar() {
		Criteria criteria = this.session.createCriteria(MenuAcesso.class);
		criteria.addOrder(Order.asc("ordem"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<MenuAcesso> listarFilhos(String cod_edt, Integer id_menugrupo) {
		Criteria criteria = this.session.createCriteria(MenuAcesso.class);
		criteria.add(Restrictions.like("cod_edt", cod_edt + "%"));
		criteria.add(Restrictions.ne("cod_edt", cod_edt));
		criteria.add(Restrictions.eq("menuGrupo.id", id_menugrupo));
		criteria.addOrder(Order.asc("cod_edt"));
		return criteria.list();
	}

	public MenuAcesso ultimoGrupo(Integer id_menugrupo) {
		
		String hql = " select e " +
						" from MenuAcesso e " +
						" where e.menuGrupo.id = :menugrupo " +
						  " and LENGTH(e.cod_edt) = 3 " +
						" order by e.cod_edt DESC "; 

		Query q = this.session.createQuery(hql); 
		q.setParameter("menugrupo", id_menugrupo); 
		q.setMaxResults(1);
		
		return (MenuAcesso) q.uniqueResult();
		
	}
	
	public MenuAcesso ultimoPai(MenuAcesso menuAcesso) {
		
		Criteria criteria = this.session.createCriteria(MenuAcesso.class);
		//criteria.add(Restrictions.eq("menuGrupo", menuAcesso.getMenuGrupo()));
		//criteria.add(Restrictions.like("cod_edt", menuAcesso.getCod_edt() + "%"));
		//criteria.add(Restrictions.ne("cod_edt", menuAcesso.getCod_edt()));
		//criteria.addOrder(Order.desc("cod_edt"));
		criteria.setMaxResults(1);
		
		return (MenuAcesso) criteria.uniqueResult();
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> menuPrincipal(Integer id_usuariogrupo){

		String sql = " SELECT a.id_menuacesso, "+
							   " a.id_menugrupo, "+
							   " a.cod_edt, "+
							   " COALESCE(a.id_menuacesso_pai, 0) AS id_pai, "+
							   " c.descricao as pai, "+
							   " a.descricao, "+
							   " b.descricao as grupo, "+
							   " (SELECT count(id_menuacesso) FROM menuacesso WHERE id_menuacesso_pai = a.id_menuacesso ) as total, "+
							   " a.pgm "+
							" FROM menuacesso a "+
								 " LEFT JOIN menuacesso c ON (a.id_menuacesso_pai = c.id_menuacesso), "+
								 " menugrupo b, "+
								 " menu_usuariogrupo d "+
							" WHERE a.id_menugrupo = b.id_menugrupo "+
						  	  " AND a.id_menuacesso = d.id_menuacesso "+
							  " AND d.id_usuariogrupo = :id_usuariogrupo "+
							" ORDER BY b.ordem, "+
									 " a.cod_edt ";

	    Query query = (Query) this.session.createSQLQuery(sql);
	    query.setParameter("id_usuariogrupo", id_usuariogrupo); 

	    return query.list();

	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> menuRecente(Integer id_usuario, Integer id_usuariogrupo){
		
		String sql = "SELECT m.id_menuacesso, "+
			                " MAX(m.descricao) as descricao, "+
			                " MAX(m.pgm) as pgm, "+
			                " MAX(m.id_menuacesso_pai) as id_menuacesso_pai, "+
			                " MAX(f.acessos) as acessos	"+
			             " FROM menuacesso m, "+
			                  " menu_usuariogrupo g, "+
			                  " menurecente f "+
			            " WHERE m.id_menuacesso = g.id_menuacesso  "+
			              " AND m.id_menuacesso = f.id_menuacesso "+
			              " AND g.id_menuacesso = f.id_menuacesso    "+
			              " AND f.id_usuario = :id_usuario "+
			              " AND g.id_usuariogrupo = :id_usuariogrupo "+
						  " AND m.id_menuacesso not in (SELECT id_menuacesso  "+
																 "FROM menufavorito  "+
															"WHERE id_usuario = :id_usuario "+
															"GROUP BY id_menuacesso) "+
			            "GROUP BY m.id_menuacesso "+
			            "ORDER BY acessos DESC LIMIT 5 ";
		
		Query query = (Query) this.session.createSQLQuery(sql);
		query.setParameter("id_usuario", id_usuario); 
		query.setParameter("id_usuariogrupo", id_usuariogrupo);
		
		return query.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> menuFavorito(Integer id_usuario, Integer id_usuariogrupo){
		
		String sql = "SELECT m.id_menuacesso, "+
			                "MAX(m.descricao) as descricao, "+
			                "MAX(m.pgm) as pgm "+
			               "FROM menuacesso m, "+
			                    "menu_usuariogrupo g, "+
			                    "menufavorito f "+
			              "WHERE m.id_menuacesso = g.id_menuacesso  "+
			                "AND m.id_menuacesso = f.id_menuacesso "+
			                "AND g.id_menuacesso = f.id_menuacesso    "+
			                "AND f.id_usuario = :id_usuario "+
			                "AND g.id_usuariogrupo = :id_usuariogrupo "+
			              "GROUP BY m.id_menuacesso "+
			              "ORDER BY 2 ";
		
		Query query = (Query) this.session.createSQLQuery(sql);
		query.setParameter("id_usuario", id_usuario); 
		query.setParameter("id_usuariogrupo", id_usuariogrupo);
		
		return query.list();
		
	}

	@SuppressWarnings("unchecked")
	public List<MenuAcesso> listar(String descricaoFilter) {
		Criteria criteria = this.session.createCriteria(MenuAcesso.class);
		if(descricaoFilter!=null && !"".equals(descricaoFilter)){
			criteria.add(Restrictions.like("descricao", "%" + descricaoFilter + "%").ignoreCase());
		}
		//criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("ordem"));
		
		return (List<MenuAcesso>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<MenuAcesso> listarRecentes(UsuarioFB usuario) {
		String hql = "select m " +
						" from MenuAcesso m, " +
							 " MenuRecente mr " +
						" where m.id = mr.menuAcesso.id " +
						  " AND mr.id.usuario = :usuarioId "+ 
						  " AND m.id not in (select mf.id.menuAcesso from MenuFavorito mf where mf.id.usuario = :usuarioId) "+
						 " group by m "+
						 " order by max(mr.acessos) desc "; 

				Query q = this.session.createQuery(hql).setMaxResults(5); 
				q.setParameter("usuarioId", usuario.getId()); 
				
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<MenuAcesso> listarFavoritos(UsuarioFB usuario) {
		String hql = " select m from MenuFavorito mf inner join mf.menuAcesso m where mf.id.usuario = :usuarioId group by m order by m.descricao  "; 

		Query q = this.session.createQuery(hql); 
		q.setParameter("usuarioId", usuario.getId()); 
				
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuAcesso> listar(UsuarioGrupo usuarioGrupo) {
		String hql = " select f from UsuarioGrupo u inner join u.menus f where u.id = :usuarioGrupoId order by f.descricao "; 
		Query q = this.session.createQuery(hql); 
		q.setParameter("usuarioGrupoId", usuarioGrupo.getId()); 
				
		return q.list();
	}

}
