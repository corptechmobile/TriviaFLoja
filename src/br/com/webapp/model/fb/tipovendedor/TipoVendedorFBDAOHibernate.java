package br.com.webapp.model.fb.tipovendedor;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public class TipoVendedorFBDAOHibernate implements TipoVendedorFBDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public TipoVendedorFB carregar(Integer tipoVendedorId) {
		String sql = "select tv.id_tipovendedor AS id, " 
						 + " tv.descricao, "
						 + " tv.integraTriviaMobile, "
						 + " tv.descflex, "
						 + " tv.isDistribuicao "
					+ " FROM tipovendedor tv "
					+ " WHERE tv.id_tipovendedor = :tipoVendedorId ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("integraTriviaMobile", Hibernate.BOOLEAN)
				.addScalar("descflex", Hibernate.DOUBLE)
				.addScalar("isDistribuicao", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(TipoVendedorFB.class));
		q.setParameter("tipoVendedorId", tipoVendedorId);
		q.setMaxResults(1);
		
		return (TipoVendedorFB) q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoVendedorFB> listar(String descricaoFilter) {
		
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			varWhere = " AND tv.descricao like :descricaoFilterLike ";
		}
		
		String sql = "select tv.id_tipovendedor AS id, " 
				 + " tv.descricao, "
				 + " tv.integraTriviaMobile, "
				 + " tv.descflex, "
				 + " tv.isDistribuicao "
			+ " FROM tipovendedor tv "
			+ " WHERE 1 = 1 "
			+ " "+varWhere+" " 
			+ " ORDER BY 2 ";
			
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("integraTriviaMobile", Hibernate.BOOLEAN)
				.addScalar("descflex", Hibernate.DOUBLE)
				.addScalar("isDistribuicao", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(TipoVendedorFB.class));
		
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
		}
		
		return q.list();
	}

}
