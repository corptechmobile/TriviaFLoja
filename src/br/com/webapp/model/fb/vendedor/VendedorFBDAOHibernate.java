package br.com.webapp.model.fb.vendedor;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public class VendedorFBDAOHibernate implements VendedorFBDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public VendedorFB carregar(Integer vendedorId) {
		String sql = "select v.id_pessoa AS id, " 
						 + " v.id_gestaovenda AS gestaoVendaId, "
						 + " g.codedt AS gestaoVendaCodEdt, " 
						 + " g.alcada AS alcada, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, "
						 + " p.nomefantmnem AS nomeFantasia "
					+ " FROM pessoa p, vendedor v "
					+ " LEFT JOIN gestaovenda g ON (v.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE v.id_pessoa = p.id_pessoa "
					  + " AND v.id_pessoa = :vendedorId ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(VendedorFB.class));
		q.setParameter("vendedorId", vendedorId);
		q.setMaxResults(1);
		
		return (VendedorFB) q.uniqueResult();
	}
	
	@Override
	public VendedorFB carregar(UsuarioFB usuarioFB) {
		String sql = "select v.id_pessoa AS id, " 
						 + " v.id_gestaovenda AS gestaoVendaId, "
						 + " g.codedt AS gestaoVendaCodEdt, " 
						 + " g.alcada AS alcada, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, "
						 + " p.nomefantmnem AS nomeFantasia "
					+ " FROM pessoa p, vendedor v "
					+ " LEFT JOIN gestaovenda g ON (v.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE v.id_pessoa = p.id_pessoa "
					  + " AND v.id_usuario = :usuarioId ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(VendedorFB.class));
		q.setParameter("usuarioId", usuarioFB.getId());
		q.setMaxResults(1);
		
		return (VendedorFB) q.uniqueResult();
	}

	@Override
	public VendedorFB carregar(String cnpjCpf) {
		String sql = "select v.id_pessoa AS id, " 
						 + " v.id_gestaovenda AS gestaoVendaId, "
						 + " g.codedt AS gestaoVendaCodEdt, " 
						 + " g.alcada AS alcada, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, "
						 + " p.nomefantmnem AS nomeFantasia "
					+ " FROM pessoa p, vendedor v "
					+ " LEFT JOIN gestaovenda g ON (v.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE v.id_pessoa = p.id_pessoa "
					  + " AND p.cnpjCpf = :cnpjCpf ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(VendedorFB.class));
		q.setParameter("cnpjCpf", cnpjCpf);
		q.setMaxResults(1);
		
		return (VendedorFB) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendedorFB> listar(String descricaoFilter) {
		
		String varWhere = ""; 
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			varWhere = " AND (p.razaosocialnome like :descricaoFilterLike";
			varWhere += " or p.nomefantmnem like :descricaoFilterLike ";
			varWhere += " or p.cnpjcpf = :descricaoFilter )";
		}
		
		String sql = "select v.id_pessoa AS id, " 
						 + " v.id_gestaovenda AS gestaoVendaId, "
						 + " g.codedt AS gestaoVendaCodEdt, " 
						 + " g.alcada AS alcada, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, "
						 + " p.nomefantmnem AS nomeFantasia "
					+ " FROM pessoa p, vendedor v "
					+ " LEFT JOIN gestaovenda g ON (v.id_gestaovenda = g.id_gestaovenda) "
					+ " WHERE v.id_pessoa = p.id_pessoa and v.ativo = 1 " + varWhere
					+ " ORDER BY p.nomefantmnem";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoVendaId", Hibernate.INTEGER)
				.addScalar("gestaoVendaCodEdt", Hibernate.STRING)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(VendedorFB.class));
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
			q.setParameter("descricaoFilter", descricaoFilter);
		}
		
		return q.list();
	}

	@Override
	public VendedorFB verificarAssocTipoVendEmp(Integer vendedorId, Integer empresaId) {
		
		String sql = " SELECT v.ID_PESSOA as id   "+
					 "   FROM vendedor v,  "+
					 "        tipovendedor t,  "+
					 "        ASSOCTIPOVENDEMPRESA a "+
					 "  WHERE v.ID_TIPOVENDEDOR = t.ID_TIPOVENDEDOR  "+
					 "    AND T.ID_TIPOVENDEDOR = a.ID_TIPOVENDEDOR  "+
					 "    AND a.ID_PESSOA_EMP = :empresaId "+
					 "    AND V.ID_PESSOA = :vendedorId "+
					 "  GROUP BY v.ID_PESSOA ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(VendedorFB.class));
		q.setParameter("empresaId", empresaId);
		q.setParameter("vendedorId", vendedorId);
		q.setMaxResults(1);
		
		return (VendedorFB) q.uniqueResult();
	}

}
