package br.com.webapp.model.fb.fornecedor;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFB;


public class FornecedorFBDAOHibernate implements FornecedorFBDAO{
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public FornecedorFB carregar(Integer fornecedorId) {
		
		String sql = "select f.id_pessoa as id, "
				+ "          p.tipopessoa as tipopessoa, "
				+ "          f.ativo, "
				+ "          p.cnpjcpf as cnpjCpf, "
				+ "          p.razaosocialnome as razaoSocial, "
				+ "     	 p.nomefantmnem as nomeFantasia "
				+ "    from fornecedor f, "
				+ "         pessoa p "
				+ "   where f.id_pessoa = p.id_pessoa "
				+ "   AND f.id_pessoa = :id";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("tipoPessoa", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)      
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(FornecedorFB.class));
			
		
		q.setParameter("id", fornecedorId);
		q.setMaxResults(1);   
		
		return (FornecedorFB) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FornecedorFB> listarParaPlanilhaCega(Integer empresaId, String descricaoFilter) {
		
		String varWhere = "";
		if(descricaoFilter != null && "".equals(descricaoFilter) == false) {
			varWhere += " AND (p.cnpjcpf = :descricaoFilter ";
			varWhere += " or p.razaosocialnome like :descricaoFilterLike ";
			varWhere += " or p.nomefantmnem like :descricaoFilterLike ";
			varWhere += " ) ";
		}
		
		String sql = "SELECT f.id_pessoa as id, "
				+ "        p.tipopessoa as tipopessoa, "
				+ "        f.ativo, "
				+ "        p.cnpjcpf as cnpjCpf, "
				+ "        p.razaosocialnome as razaoSocial, "
				+ "        p.nomefantmnem as nomeFantasia "
				+ "   FROM fornecedor f, "
				+ "        pessoa p "
				+ "   WHERE f.id_pessoa = p.id_pessoa  " + varWhere
				+ "   AND EXISTS (SELECT n.id_nfcompra  "
				+ "   					FROM NFCOMPRA n  "
				+ "   					WHERE n.ID_NFCOMPRASTATUS IN (:statusNFDigitada, :statusNFLiberada) "
				+ "   					  AND n.ID_PESSOA_FORN = f.ID_PESSOA "
				+ "   					  AND n.ID_PESSOA_EMP = :empresaId "
				+ "                       AND not exists (select cpn.id_nfcompra from coletor_pc_nfcompra cpn, coletor_pc cp "
	            + "										   where cp.ID_CPC = cpn.ID_CPC "
	            + "											 and cpn.id_nfcompra = n.ID_NFCOMPRA  "
	            + "										     and cp.STATUS <> :statusColetorExcluido)) ";	
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("tipoPessoa", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("cnpjCpf", Hibernate.STRING)      
				.addScalar("razaoSocial", Hibernate.STRING)
				.addScalar("nomeFantasia", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(FornecedorFB.class));
			
		
		q.setParameter("empresaId", empresaId);
		q.setParameter("statusNFDigitada", NFCompraFB.STATUS_DIGITADA);
		q.setParameter("statusNFLiberada", NFCompraFB.STATUS_LIBERADA);
		q.setParameter("statusColetorExcluido", ColetorPCFB.STATUS_EXCLUIDO);
		
		if(descricaoFilter != null && "".equals(descricaoFilter) == false) {
			q.setParameter("descricaoFilter", descricaoFilter);
			q.setParameter("descricaoFilterLike", "%"+ descricaoFilter + "%");
		}
		
		return q.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FornecedorFB> listar(String descricaoFilter, Integer empresaId) {


		String sql = 	" SELECT f.id_pessoa as id,  "+
						       " p.tipopessoa as tipoPessoa,  "+
						       " f.ativo,  "+
						       " p.cnpjcpf as cnpjCpf,  "+
						       " p.razaosocialnome as razaoSocial,  "+
						       " p.nomefantmnem as nomeFantasia  "+
						  " FROM fornecedor f,  "+
						       " pessoa p  "+
						  " WHERE f.id_pessoa = p.id_pessoa    "+
						  " AND EXISTS ( SELECT n.id_nfcompra   "+
						  					" FROM NFCOMPRA n   "+
						  					" WHERE n.ID_NFCOMPRASTATUS = :liberadoConferencia  "+
						  					  " AND n.ID_PESSOA_FORN = f.ID_PESSOA  "+
						  					  " AND n.ID_PESSOA_EMP = :empresaId ) ";

		Query q = (Query) session.createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("tipoPessoa", Hibernate.STRING)
			.addScalar("ativo", Hibernate.INTEGER)
			.addScalar("cnpjCpf", Hibernate.STRING)      
			.addScalar("razaoSocial", Hibernate.STRING)
			.addScalar("nomeFantasia", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(FornecedorFB.class));
			
			q.setParameter("empresaId", empresaId);
			q.setParameter("liberadoConferencia", 2);
			
			if(descricaoFilter != null && "".equals(descricaoFilter) == false) {
				q.setParameter("descricaoFilter", descricaoFilter);
				q.setParameter("descricaoFilterLike", "%"+ descricaoFilter + "%");
			}

			return q.list();		
					  							  
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FornecedorFB> listar(String descricao) {
		
		String varWhere = "";
		if(descricao != null && "".equals(descricao) == false) {
			varWhere += " AND (p.cnpjcpf = :descricaoFilter ";
			varWhere += " or p.razaosocialnome like :descricaoFilterLike ";
			varWhere += " or p.nomefantmnem like :descricaoFilterLike ";
			varWhere += " ) ";
		}
		
		
		String sql = 	" SELECT f.id_pessoa as id,  "+
						       " p.tipopessoa as tipoPessoa,  "+
						       " f.ativo,  "+
						       " p.cnpjcpf as cnpjCpf,  "+
						       " p.razaosocialnome as razaoSocial,  "+
						       " p.nomefantmnem as nomeFantasia  "+
						  " FROM fornecedor f,  "+
						       " pessoa p  "+
						  " WHERE f.id_pessoa = p.id_pessoa "+varWhere+" "+
						  " ORDER BY 6 ";     

		Query q = (Query) session.createSQLQuery(sql.toString())
		.addScalar("id", Hibernate.INTEGER)
		.addScalar("tipoPessoa", Hibernate.STRING)
		.addScalar("ativo", Hibernate.INTEGER)
		.addScalar("cnpjCpf", Hibernate.STRING)      
		.addScalar("razaoSocial", Hibernate.STRING)
		.addScalar("nomeFantasia", Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(FornecedorFB.class));
		
		if(descricao != null && "".equals(descricao) == false) {
			q.setParameter("descricaoFilter", descricao);
			q.setParameter("descricaoFilterLike", "%"+ descricao + "%");
		}
		
		return q.list();
	}

}
