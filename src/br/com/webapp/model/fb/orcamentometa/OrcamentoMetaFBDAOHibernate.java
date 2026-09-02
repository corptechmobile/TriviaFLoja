package br.com.webapp.model.fb.orcamentometa;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFB;
import br.com.webapp.web.util.DAOException;

public class OrcamentoMetaFBDAOHibernate implements OrcamentoMetaFBDAO{

	private StringBuilder COLUMNS;

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public OrcamentoMetaFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" om.ID_ORCAMENTOMETA as id, ")
			   .append(" om.ANOMES as anoMes, ")
			   .append(" om.ID_PESSOA_EMP as idPessoaEmp, ")
			   .append(" f.NOMEFANTMNEM as descFornecedor, ")
			   .append(" f.CNPJCPF as cnpjCpf, ")
			   .append(" om.VALORPREVFAT as valorPrevFat ");
	}

	
	@Override
	public OrcamentoMetaFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM ORCAMENTOMETA om, PESSOA f ")
		   .append(" WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("   AND om.ID_ORCAMENTOMETA = :id");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("valorPrevFat", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (OrcamentoMetaFB) query.uniqueResult();
	}

	@Override
	public OrcamentoMetaFB carregar(String anomes, Integer idPessoaEmp) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM ORCAMENTOMETA om, PESSOA f ")
		   .append(" WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("   AND om.ANOMES = :anomes")
		   .append("   AND om.ID_PESSOA_EMP = :idPessoaEmp");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("valorPrevFat", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaFB.class));
			query.setParameter("anomes", anomes);
			query.setParameter("idPessoaEmp", idPessoaEmp);
			query.setMaxResults(1);
		return (OrcamentoMetaFB) query.uniqueResult();
	}

	@Override
	public OrcamentoMetaFB validarFaixa(OrcamentoMetaFB orcamentoMetaFB) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM ORCAMENTOMETA om, PESSOA f ")
		   .append(" WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("valorPrevFat", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaFB.class));
			query.setParameter("id_pessoa_emp", orcamentoMetaFB.getIdPessoaEmp());
			query.setParameter("anomes", orcamentoMetaFB.getAnoMes());
			query.setMaxResults(1);
		return (OrcamentoMetaFB) query.uniqueResult();
	}

@SuppressWarnings("unchecked")
@Override
public List<OrcamentoMetaFB> listar(String ano) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM ORCAMENTOMETA om, PESSOA f ")
		   .append(" WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ");


		if (ano != null && !"".equals(ano)) {
			sql.append(" AND substring(om.ANOMES from 1 for 4) = '"+ano+"' ");
		}

		sql.append(" ORDER BY om.ANOMES, om.ID_PESSOA_EMP ");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("valorPrevFat", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaFB.class));


		return query.list();
	}

	@Override
	public Integer insert(OrcamentoMetaFB orcamentoMetaFB) throws DAOException {
		try {

			Integer orcamentoMetaFBId = getSeq();
			System.out.println("[OrcamentoMetaFBDAOHibernate][insert][id]" + orcamentoMetaFBId);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ORCAMENTOMETA (ANOMES, ID_PESSOA_EMP, VALORPREVFAT) ")
			.append("VALUES (:ANOMES, ")
			        .append(":ID_PESSOA_EMP, ")
			        .append(":VALORPREVFAT) ");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ANOMES", orcamentoMetaFB.getAnoMes());
			query.setParameter("ID_PESSOA_EMP", orcamentoMetaFB.getIdPessoaEmp());
	        query.setParameter("VALORPREVFAT", orcamentoMetaFB.getValorPrevFat());

			query.executeUpdate();

			return orcamentoMetaFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public void alterar(OrcamentoMetaFB orcamentoMetaFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ORCAMENTOMETA SET ")
					        .append("VALORPREVFAT = :VALORPREVFAT ")
					      .append(" WHERE ANOMES = :ANOMES")
					      .append("   AND ID_PESSOA_EMP = :ID_PESSOA_EMP");



			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ANOMES", orcamentoMetaFB.getAnoMes());
			query.setParameter("ID_PESSOA_EMP", orcamentoMetaFB.getIdPessoaEmp());
	        query.setParameter("VALORPREVFAT", orcamentoMetaFB.getValorPrevFat());

			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_orcamentoMeta_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do Orçamento Meta.");
		}
	}

	@Override
	public void rollback() {
		try {
			this.session.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrcamentoMetaFB salvar(OrcamentoMetaFB orcamentoMetaFB) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(OrcamentoMetaFB orcamentoMetaFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ORCAMENTOMETA ")
		      .append(" WHERE ANOMES = :ANOMES")
		      .append("   AND ID_PESSOA_EMP = :ID_PESSOA_EMP");


			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ANOMES", orcamentoMetaFB.getAnoMes());
			query.setParameter("ID_PESSOA_EMP", orcamentoMetaFB.getIdPessoaEmp());

			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrcamentoMetaFB> listar(String anoMes, Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM ORCAMENTOMETA om, PESSOA f ")
		   .append(" WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("   AND om.ANOMES = :ANOMES")
	       .append("   AND om.ID_PESSOA_EMP = :ID_PESSOA_EMP");
		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("valorPrevFat", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaFB.class));


		query.setParameter("ANOMES", anoMes);
		query.setParameter("ID_PESSOA_EMP", empresaId);
		
		return query.list();

		
	}


}
