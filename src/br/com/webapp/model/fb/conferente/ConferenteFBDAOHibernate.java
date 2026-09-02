package br.com.webapp.model.fb.conferente;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public class ConferenteFBDAOHibernate implements ConferenteFBDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}

	@Override
	public ConferenteFB carregar(Integer conferenteId) {
		String sql = "SELECT c.id_pessoa AS id, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, " 
						 + " p.nomefantmnem AS nomeFantasia " 
					+ " FROM conferente c, pessoa p " 
					+ " WHERE c.id_pessoa = p.id_pessoa "
					+ "   AND c.ativo = 1 "
					+ "   AND c.id_pessoa = :conferenteId ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)      
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ConferenteFB.class));
		
		q.setParameter("conferenteId", conferenteId);
		q.setMaxResults(1);
		
		return (ConferenteFB) q.uniqueResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConferenteFB> listar(UsuarioFB usuarioFB) {
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
				.setResultTransformer(Transformers.aliasToBean(ConferenteFB.class));
		
		q.setParameter("usuarioId", usuarioFB.getId());
		
		return q.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConferenteFB> listar() {
		String sql = "SELECT c.id_pessoa AS id, " 
						 + " p.cnpjcpf AS cnpjCpf, " 
						 + " p.razaosocialnome AS razaoSocial, " 
						 + " p.nomefantmnem AS nomeFantasia " 
					+ " FROM conferente c, pessoa p " 
					+ " WHERE c.id_pessoa = p.id_pessoa "
					+ "   AND c.ativo = 1 "
					+ " ORDER BY 4 ";

		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ConferenteFB.class));
		
		return q.list();
	}

}
