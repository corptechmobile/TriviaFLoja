package br.com.webapp.model.fb.empresa;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public class EmpresaFBDAOHibernate implements EmpresaFBDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public EmpresaFB carregar(Integer empresaId) {
		String sql = "select e.id_pessoa AS id, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, " 
						 + " p.nomefantmnem AS nomeFantasia, " 
						 + " e.tipocomissao AS tipoComissao, "
						 + " e.nivellinhaproduto, "
						 + " e.ID_TABPRECO_PADRAO_FDL as idTabPrecoPadraoFDL, "
						 + " e.ID_CLIENTE_PADRAO_FDL as idClientePadraoFDL, "
						 + " e.ID_VENDEDOR_PADRAO_FDL as idVendedorPadraoFDL "
					+ " FROM empresa e, pessoa p " 
					+ " WHERE e.id_pessoa = :empresaId "
					+ " AND e.id_pessoa = p.id_pessoa ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)      
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.addScalar("tipoComissao", Hibernate.INTEGER)
				.addScalar("nivelLinhaProduto", Hibernate.INTEGER)
				.addScalar("idTabPrecoPadraoFDL", Hibernate.STRING)
				.addScalar("idClientePadraoFDL", Hibernate.INTEGER)
				.addScalar("idVendedorPadraoFDL", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EmpresaFB.class));
		
		q.setParameter("empresaId", empresaId);
		q.setMaxResults(1);
		
		return (EmpresaFB) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaFB> listar(UsuarioFB usuarioFB) {
		String sql = "select e.id_pessoa AS id, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, "
						 + " p.nomefantmnem AS nomeFantasia, "
						 + " e.tipocomissao AS tipoComissao, "
						 + " e.nivellinhaproduto, "
						 + " e.ID_TABPRECO_PADRAO_FDL as idTabPrecoPadraoFDL, "
						 + " e.ID_CLIENTE_PADRAO_FDL as idClientePadraoFDL, "
						 + " e.ID_VENDEDOR_PADRAO_FDL as idVendedorPadraoFDL "
					+ " FROM empresa e, pessoa p, usuarioempresa ue " 
					+ " WHERE e.id_pessoa = p.id_pessoa "
					+ " AND e.id_pessoa = ue.id_pessoa_emp "
					+ " AND ue.id_usuario = :usuarioId "
					+ "ORDER BY ue.emppadrao desc, e.id_pessoa ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.addScalar("tipoComissao", Hibernate.INTEGER)
				.addScalar("nivelLinhaProduto", Hibernate.INTEGER)
				.addScalar("idTabPrecoPadraoFDL", Hibernate.STRING)
				.addScalar("idClientePadraoFDL", Hibernate.INTEGER)
				.addScalar("idVendedorPadraoFDL", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EmpresaFB.class));
		
		q.setParameter("usuarioId", usuarioFB.getId());
		
		return q.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaFB> listar() {
		String sql = "SELECT e.id_pessoa AS id, " 
				 + " 		 p.cnpjcpf AS cnpjCpf, " 
				 + " 		 p.razaosocialnome AS razaoSocial, "
				 + " 		 p.nomefantmnem AS nomeFantasia, "
				 + " 		 e.tipocomissao AS tipoComissao, "
				 + " 		 e.nivellinhaproduto, "
				 + " 		 e.ID_TABPRECO_PADRAO_FDL as idTabPrecoPadraoFDL, "
				 + " 		 e.ID_CLIENTE_PADRAO_FDL as idClientePadraoFDL, "
				 + " 		 e.ID_VENDEDOR_PADRAO_FDL as idVendedorPadraoFDL "
				 + "    FROM empresa e, pessoa p " 
				 + "   WHERE e.id_pessoa = p.id_pessoa ";

		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.addScalar("tipoComissao", Hibernate.INTEGER)
				.addScalar("nivelLinhaProduto", Hibernate.INTEGER)
				.addScalar("idTabPrecoPadraoFDL", Hibernate.STRING)
				.addScalar("idClientePadraoFDL", Hibernate.INTEGER)
				.addScalar("idVendedorPadraoFDL", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EmpresaFB.class));
		
		return q.list();
	}

}
