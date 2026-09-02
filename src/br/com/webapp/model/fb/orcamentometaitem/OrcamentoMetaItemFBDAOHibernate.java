package br.com.webapp.model.fb.orcamentometaitem;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class OrcamentoMetaItemFBDAOHibernate implements OrcamentoMetaItemFBDAO{

	private StringBuilder COLUMNS;

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public OrcamentoMetaItemFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" omi.ID_ORCAMENTOMETAITEM as id, ")
			   .append(" omi.ID_ORCAMENTOMETA as idOrcamentoMeta, ")
			   .append(" om.anoMes, ")
			   .append(" om.ID_PESSOA_EMP as idPessoaEmp, ")
			   .append(" f.NOMEFANTMNEM as descFornecedor, ")
			   .append(" f.cnpjCpf, ")
			   .append(" omi.ID_ORCAMENTOGRUPO as idOrcamentoGrupo, ")
			   .append(" og.DESCRICAO as descOrcamentoGrupo, ")
			   .append(" COALESCE(omi.VALORORCADO,0) as valorOrcado, ")
			   .append(" 0 AS valorPrevAnt, ")
			   .append(" 0 AS valorRealAnt, ")
			   .append(" 0 AS percPrevRealAnt, ") 
			   .append(" 0 AS percPrevRealAtual, ")
			   .append(" og.PERCFAT AS percFaturamento ");

	}

	
	@Override
	public OrcamentoMetaItemFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("   FROM ORCAMENTOMETA om, PESSOA f, orcamentogrupo og, orcamentometaitem omi ")
		   .append("  WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("    and om.ID_ORCAMENTOMETA = omi.ID_ORCAMENTOMETA ")
		   .append("    and omi.ID_ORCAMENTOGRUPO = og.ID_ORCAMENTOGRUPO ")
		   .append("    and omi.ID_ORCAMENTOMETAITEM = :id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idOrcamentoMeta", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("idOrcamentoGrupo", Hibernate.INTEGER)
				.addScalar("descOrcamentoGrupo", Hibernate.STRING)
				.addScalar("valorOrcado", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percFaturamento", Hibernate.DOUBLE)

				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaItemFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (OrcamentoMetaItemFB) query.uniqueResult();
	}


@SuppressWarnings("unchecked")
@Override
public List<OrcamentoMetaItemFB> listar(String anomes, String anomesref, Integer idPessoaEmp) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ")
		          .append(" max(omi.id_orcamentometaitem) AS id, ")
		          .append(" max(omi.id_orcamentometa) AS idOrcamentoMeta, ")
		       	  .append(" om.anoMes, ") 
		       	  .append(" om.id_pessoa_emp AS idPessoaEmp, ")
		       	  .append(" max(f.nomefantmnem) AS descFornecedor, ")
		       	  .append(" max(f.cnpjCpf) AS cnpjCpf, ")
		       	  .append(" og.id_orcamentogrupo AS idOrcamentoGrupo, ")
		       	  .append(" max(og.descricao) AS descOrcamentoGrupo, ")
		       	  .append(" max(og.ordem) AS ordemOrcamentoGrupo, ")
		       	  .append(" max(COALESCE(omi.valororcado, 0)) AS valorOrcado, ")
		       	  .append(" max(COALESCE(omiAnt.valororcado, 0)) AS valorPrevAnt, ")
		       	  .append(" sum(t.VALORBRUTO) AS valorRealAnt, ")
		       	  .append(" CASE max(COALESCE(omiAnt.valororcado, 0)) WHEN 0 THEN 0 ELSE round(COALESCE(Sum(t.valorbruto), 0) / max(COALESCE(omiAnt.valororcado, 0))*100,0) END AS percPrevRealAnt, ")
		       	  .append(" CASE Max(COALESCE(omiAnt.valororcado, 0)) WHEN 0 THEN 0 ELSE round(Max(COALESCE(omi.valororcado, 0)) / Max(COALESCE(omiAnt.valororcado, 0))*100,0) END AS percPrevRealAtual, ")
		       	  .append(" max(og.percfat) AS percFaturamento ")				
		   .append(" FROM orcamentogrupo og ")
         	    .append(" LEFT JOIN ORCAMENTOMETA om ON (om.ANOMES = :anoMes and om.ID_PESSOA_EMP = :id_pessoa_emp) ")
			    .append(" LEFT JOIN orcamentometaitem omi ON (om.ID_ORCAMENTOMETA = omi.ID_ORCAMENTOMETA and ")
				                                    .append(" omi.ID_ORCAMENTOGRUPO = og.ID_ORCAMENTOGRUPO and ")
				                                    .append(" omi.ID_ORCAMENTOMETA = om.ID_ORCAMENTOMETA) ")
         	    .append(" LEFT JOIN ORCAMENTOMETA omAnt ON (omAnt.ANOMES = :anoMesRef and omAnt.ID_PESSOA_EMP = :id_pessoa_emp) ")
			    .append(" LEFT JOIN orcamentometaitem omiAnt ON (omAnt.ID_ORCAMENTOMETA = omiAnt.ID_ORCAMENTOMETA and ")
				                                    .append(" omiAnt.ID_ORCAMENTOGRUPO = og.ID_ORCAMENTOGRUPO and ")
				                                    .append(" omiAnt.ID_ORCAMENTOMETA = omAnt.ID_ORCAMENTOMETA) ")
				                                    .append(" LEFT JOIN EVENTOFINANCEIRO ef ON (og.ID_ORCAMENTOGRUPO = ef.ID_ORCAMENTOGRUPO) ")
				                                    .append(" LEFT JOIN titulo t ON (ef.ID_EVENTOFINANCEIRO = t.ID_EVENTOFINANCEIRO ")
				                                    .append("		                 and (extract(year from t.DATAPAGAMENTO)||LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0')) = :anoMesRef) ")
			    .append(" LEFT JOIN PESSOA f on (om.ID_PESSOA_EMP = f.ID_PESSOA and f.ID_PESSOA = :id_pessoa_emp) ")
	    .append(" GROUP BY om.ANOMES, om.ID_PESSOA_EMP, og.id_orcamentogrupo ") 
		.append(" ORDER BY 9, 8 ");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idOrcamentoMeta", Hibernate.INTEGER) 
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("idOrcamentoGrupo", Hibernate.INTEGER)
				.addScalar("descOrcamentoGrupo", Hibernate.STRING)
				.addScalar("valorOrcado", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percFaturamento", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaItemFB.class));
			query.setParameter("anoMes", anomes);
			query.setParameter("anoMesRef", anomesref);
	        query.setParameter("id_pessoa_emp", idPessoaEmp);


		return query.list();
	}

	@Override
	public Integer insert(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException {
		try {

			Integer orcamentoMetaItemFBId = getSeq();
			System.out.println("[MetaGastoFinanceiroFBDAOHibernate][insert][id]" + orcamentoMetaItemFBId);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ORCAMENTOMETAITEM (id_orcamentoMetaItem, id_orcamentoMeta, id_orcamentoGrupo, valorOrcado) ")
			.append("VALUES (:id_orcamentoMetaItem, ")
			        .append(":id_orcamentoMeta, ")
			        .append(":id_orcamentoGrupo, ")
			        .append(":valorOrcado) ");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("id_orcamentoMetaItem", orcamentoMetaItemFBId);
			query.setParameter("id_orcamentoMeta", orcamentoMetaItemFB.getIdOrcamentoMeta());
	        query.setParameter("id_orcamentoGrupo", orcamentoMetaItemFB.getIdOrcamentoGrupo());
	        query.setParameter("valorOrcado", orcamentoMetaItemFB.getValorOrcado());

			query.executeUpdate();

			return orcamentoMetaItemFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public void alterar(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ORCAMENTOMETAITEM SET ")
					        .append("VALORORCADO = :VALORORCADO ")
					      .append(" WHERE ID_ORCAMENTOMETAITEM = :ID_ORCAMENTOMETAITEM");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_ORCAMENTOMETAITEM", orcamentoMetaItemFB.getId());
	        query.setParameter("VALORORCADO", orcamentoMetaItemFB.getValorOrcado());

			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_orcamentoMetaItem_ID, 1) from rdb$database;";
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
	public OrcamentoMetaItemFB salvar(OrcamentoMetaItemFB orcamentoMetaItemFB) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ORCAMENTOMETAITEM ")
		      .append(" WHERE ID_ORCAMENTOMETAITEM = :ID_ORCAMENTOMETAITEM");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_ORCAMENTOMETAITEM", orcamentoMetaItemFB.getId());

			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<OrcamentoMetaItemFB> listar(String anomes, Integer idPessoaEmp) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("   FROM ORCAMENTOMETA om, PESSOA f, orcamentogrupo og, orcamentometaitem omi ")
		   .append("  WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("    and om.ID_ORCAMENTOMETA = omi.ID_ORCAMENTOMETA ")
		   .append("    and omi.ID_ORCAMENTOGRUPO = og.ID_ORCAMENTOGRUPO ");


		if (anomes != null && !"".equals(anomes)) {
			sql.append(" AND om.anomes = '"+anomes+"' ");
		}

		if (idPessoaEmp != null) {
			sql.append(" AND om.ID_PESSOA_EMP = "+idPessoaEmp+" ");
		}

		sql.append(" ORDER BY om.ANOMES, om.ID_PESSOA_EMP, og.descricao ");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idOrcamentoMeta", Hibernate.INTEGER)
				.addScalar("ANOMES", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("idOrcamentoGrupo", Hibernate.INTEGER)
				.addScalar("descOrcamentoGrupo", Hibernate.STRING)
				.addScalar("valorOrcado", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percFaturamento", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaItemFB.class));


		return query.list();
	
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<OrcamentoMetaItemFBDTO> listarAno(String ano, Integer idPessoaEmp, Integer idOrcamentoGrupo) {
		String varWhere = "";
		if(idOrcamentoGrupo!=null) {
			varWhere = " WHERE b.idOrcamentoGrupo = "+idOrcamentoGrupo+" ";
		}
		
		String sql = "SELECT "+
			       " b.idOrcamentoGrupo as idOrcamentoGrupo, "+
			       " max(b.descOrcamentoGrupo) AS descOrcamentoGrupo, "+
			       " max(b.ORDEM) as ordem, "+
			       " max(b.prevJan) as prevJan, "+
			       " sum(b.fatJan) as fatJan, "+
			       " sum(case b.prevJan when 0 then 0 else (b.fatJan/b.prevJan)*100 end) as percJan, "+
			       " max(b.prevFev) as prevFev, "+
			       " sum(b.fatFev) as fatFev, "+
			       " sum(case b.prevFev when 0 then 0 else (b.fatFev/b.prevFev)*100 end) as percFev, "+
			       " max(b.prevMar) as prevMar, "+
			       " sum(b.fatMar) as fatMar, "+
			       " sum(case b.prevMar when 0 then 0 else (b.fatMar/b.prevMar)*100 end) as percMar, "+
			       " max(b.prevAbr) as prevAbr, "+
			       " sum(b.fatAbr) as fatAbr, "+
			       " sum(case b.prevAbr when 0 then 0 else (b.fatAbr/b.prevAbr)*100 end) as percAbr, "+
			       " max(b.prevMai) as prevMai, "+
			       " sum(b.fatMai) as fatMai, "+
			       " sum(case b.prevMai when 0 then 0 else (b.fatMai/b.prevMai)*100 end) as percMai, "+
			       " max(b.prevJun) as prevJun, "+
			       " sum(b.fatJun) as fatJun, "+
			       " sum(case b.prevJun when 0 then 0 else (b.fatJun/b.prevJun)*100 end) as percJun, "+
			       " max(b.prevJul) as prevJul, "+
			       " sum(b.fatJul) as fatJul, "+
			       " sum(case b.prevJul when 0 then 0 else (b.fatJul/b.prevJul)*100 end) as percJul, "+
			       " max(b.prevAgo) as prevAgo, "+
			       " sum(b.fatAgo) as fatAgo, "+
			       " sum(case b.prevAgo when 0 then 0 else (b.fatAgo/b.prevAgo)*100 end) as percAgo, "+
			       " max(b.prevSet) as prevSet, "+
			       " sum(b.fatSet) as fatSet, "+
			       " sum(case b.prevSet when 0 then 0 else (b.fatSet/b.prevSet)*100 end) as percSet, "+
			       " max(b.prevOut) as prevOut, "+
			       " sum(b.fatOut) as fatOut, "+
			       " sum(case b.prevOut when 0 then 0 else (b.fatOut/b.prevOut)*100 end) as percOut, "+
			       " max(b.prevNov) as prevNov, "+
			       " sum(b.fatNov) as fatNov, "+
			       " sum(case b.prevNov when 0 then 0 else (b.fatNov/b.prevNov)*100 end) as percNov, "+
			       " max(b.prevDez) as prevDez, "+
			       " sum(b.fatDez) as fatDez, "+
			       " sum(case b.prevDez when 0 then 0 else (b.fatDez/b.prevDez)*100 end) as percDez, "+
			       " max(b.prevTot) as prevTotal, "+
			       " sum(b.fatTot) as fatTotal, "+
			       " sum(case b.prevTot when 0 then 0 else (b.fatTot/b.prevTot)*100 end) as percTotal "+
			  " from( "+
				" SELECT "+ 
					       " a.ID_ORCAMENTOGRUPO as idOrcamentoGrupo, "+
					       " max(a.descOrcamentoGrupo) AS descOrcamentoGrupo, "+
					       " max(a.ORDEM) as ordem, "+
					       " max(a.prevJan) as prevJan, "+
					       " sum(a.fatJan) as fatJan, "+
					       " max(a.prevFev) as prevFev, "+
					       " sum(a.fatFev) as fatFev, "+
					       " max(a.prevMar) as prevMar, "+
					       " sum(a.fatMar) as fatMar, "+
					       " max(a.prevAbr) as prevAbr, "+
					       " sum(a.fatAbr) as fatAbr, "+
					       " max(a.prevMai) as prevMai, "+
					       " sum(a.fatMai) as fatMai, "+
					       " max(a.prevJun) as prevJun, "+
					       " sum(a.fatJun) as fatJun, "+
					       " max(a.prevJul) as prevJul, "+
					       " sum(a.fatJul) as fatJul, "+
					       " max(a.prevAgo) as prevAgo, "+
					       " sum(a.fatAgo) as fatAgo, "+
					       " max(a.prevSet) as prevSet, "+
					       " sum(a.fatSet) as fatSet, "+
					       " max(a.prevOut) as prevOut, "+
					       " sum(a.fatOut) as fatOut, "+
					       " max(a.prevNov) as prevNov, "+
					       " sum(a.fatNov) as fatNov, "+
					       " max(a.prevDez) as prevDez, "+
					       " sum(a.fatDez) as fatDez, "+
					       " max(a.prevJan+a.prevFev+a.prevMar+a.prevAbr+a.prevMai+a.prevJun+a.prevJul+a.prevAgo+a.prevSet+a.prevOut+a.prevNov+a.prevDez) as prevTot, "+
					       " max(a.fatJan+a.fatFev+a.fatMar+a.fatAbr+a.fatMai+a.fatJun+a.fatJul+a.fatAgo+a.fatSet+a.fatOut+a.fatNov+a.fatDez) as fatTot "+
					  " from( "+
					" select i.ID_ORCAMENTOGRUPO, "+
					       " max(g.DESCRICAO) as descOrcamentoGrupo, "+
					       " max(g.ORDEM) as ordem, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '01' then i.VALORORCADO else 0 end) as prevJan, "+
					       " 0 as fatJan, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '02' then i.VALORORCADO else 0 end) as prevFev, "+
					       " 0 as fatFev, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '03' then i.VALORORCADO else 0 end) as prevMar, "+
					       " 0 as fatMar, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '05' then i.VALORORCADO else 0 end) as prevAbr, "+
					       " 0 as fatAbr, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '05' then i.VALORORCADO else 0 end) as prevMai, "+
					       " 0 as fatMai, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '06' then i.VALORORCADO else 0 end) as prevJun, "+
					       " 0 as fatJun, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '07' then i.VALORORCADO else 0 end) as prevJul, "+
					       " 0 as fatJul, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '08' then i.VALORORCADO else 0 end) as prevAgo, "+
					       " 0 as fatAgo, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '09' then i.VALORORCADO else 0 end) as prevSet, "+
					       " 0 as fatSet, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '10' then i.VALORORCADO else 0 end) as prevOut, "+
					       " 0 as fatOut, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '11' then i.VALORORCADO else 0 end) as prevNov, "+
					       " 0 as fatNov, "+
					       " max(case substring(o.ANOMES from 5 for 2) when '12' then i.VALORORCADO else 0 end) as prevDez, "+
					       " 0 as fatDez "+
					  " from orcamentometa o, "+
					       " ORCAMENTOMETAITEM i, "+
					       " ORCAMENTOGRUPO g "+
					 " where o.ID_ORCAMENTOMETA = i.ID_ORCAMENTOMETA "+
					   " and i.ID_ORCAMENTOGRUPO = g.ID_ORCAMENTOGRUPO "+
					   " and substring(o.ANOMES from 1 for 4) = :ano "+
					   " and o.ID_PESSOA_EMP = :idPessoaEmp "+
					 " group by i.ID_ORCAMENTOGRUPO "+
					" union "+
					" select g.ID_ORCAMENTOGRUPO, "+
					       " max(g.DESCRICAO) as descOrcamentoGrupo, "+
					       " max(g.ORDEM) as ordem, "+
					       " 0 as prevJan, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '01' then t.valorbruto else 0 end) as fatJan, "+
					       " 0 as prevFev, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '02' then t.valorbruto else 0 end) as fatFev, "+
					       " 0 as prevMar, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '03' then t.valorbruto else 0 end) as fatMar, "+
					       " 0 as prevAbr, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '04' then t.valorbruto else 0 end) as fatAbr, "+
					       " 0 as prevMai, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '05' then t.valorbruto else 0 end) as fatMai, "+
					       " 0 as prevJun, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '06' then t.valorbruto else 0 end) as fatJun, "+
					       " 0 as prevJul, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '07' then t.valorbruto else 0 end) as fatJul, "+
					       " 0 as prevAgo, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '08' then t.valorbruto else 0 end) as fatAgo, "+
					       " 0 as prevSet, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '09' then t.valorbruto else 0 end) as fatSet, "+
					       " 0 as prevOut, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '10' then t.valorbruto else 0 end) as fatOut, "+
					       " 0 as prevNov, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '11' then t.valorbruto else 0 end) as fatNov, "+
					       " 0 as prevDez, "+
					       " sum(case LPAD(EXTRACT(MONTH FROM t.DATAPAGAMENTO),2,'0') when '12' then t.valorbruto else 0 end) as fatDez "+
					  " from ORCAMENTOGRUPO g, "+
					       " EVENTOFINANCEIRO ef, "+
					       " titulo t "+
					 " where g.ID_ORCAMENTOGRUPO = ef.ID_ORCAMENTOGRUPO "+
					   " and ef.ID_EVENTOFINANCEIRO = t.ID_EVENTOFINANCEIRO "+
					   " and (extract(year from t.DATAPAGAMENTO) = :ano) "+
					 " group by g.ID_ORCAMENTOGRUPO "+
					 " ) as a "+
 					 " group by a.ID_ORCAMENTOGRUPO "+
					 " ) as b "+
					 " "+varWhere+" "+
					 " group by b.idOrcamentoGrupo "+
					   " order by 3, 2 ";

			Query query = (Query) session.createSQLQuery(sql)
					.addScalar("idOrcamentoGrupo", Hibernate.INTEGER) 
					.addScalar("descOrcamentoGrupo", Hibernate.STRING)
					.addScalar("prevJan", Hibernate.DOUBLE)
					.addScalar("fatJan", Hibernate.DOUBLE)
					.addScalar("percJan", Hibernate.DOUBLE)
					.addScalar("prevFev", Hibernate.DOUBLE)
					.addScalar("fatFev", Hibernate.DOUBLE)
					.addScalar("percFev", Hibernate.DOUBLE)
					.addScalar("prevMar", Hibernate.DOUBLE)
					.addScalar("fatMar", Hibernate.DOUBLE)
					.addScalar("percMar", Hibernate.DOUBLE)
					.addScalar("prevAbr", Hibernate.DOUBLE)
					.addScalar("fatAbr", Hibernate.DOUBLE)
					.addScalar("percAbr", Hibernate.DOUBLE)
					.addScalar("prevMai", Hibernate.DOUBLE)
					.addScalar("fatMai", Hibernate.DOUBLE)
					.addScalar("percMai", Hibernate.DOUBLE)
					.addScalar("prevJun", Hibernate.DOUBLE)
					.addScalar("fatJun", Hibernate.DOUBLE)
					.addScalar("percJun", Hibernate.DOUBLE)
					.addScalar("prevJul", Hibernate.DOUBLE)
					.addScalar("fatJul", Hibernate.DOUBLE)
					.addScalar("percJul", Hibernate.DOUBLE)
					.addScalar("prevAgo", Hibernate.DOUBLE)
					.addScalar("fatAgo", Hibernate.DOUBLE)
					.addScalar("percAgo", Hibernate.DOUBLE)
					.addScalar("prevSet", Hibernate.DOUBLE)
					.addScalar("fatSet", Hibernate.DOUBLE)
					.addScalar("percSet", Hibernate.DOUBLE)
					.addScalar("prevOut", Hibernate.DOUBLE)
					.addScalar("fatOut", Hibernate.DOUBLE)
					.addScalar("percOut", Hibernate.DOUBLE)
					.addScalar("prevNov", Hibernate.DOUBLE)
					.addScalar("fatNov", Hibernate.DOUBLE)
					.addScalar("percNov", Hibernate.DOUBLE)
					.addScalar("prevDez", Hibernate.DOUBLE)
					.addScalar("fatDez", Hibernate.DOUBLE)
					.addScalar("percDez", Hibernate.DOUBLE)
					.addScalar("prevTotal", Hibernate.DOUBLE)
					.addScalar("fatTotal", Hibernate.DOUBLE)
					.addScalar("percTotal", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(OrcamentoMetaItemFBDTO.class));
				query.setParameter("ano", ano);
		        query.setParameter("idPessoaEmp", idPessoaEmp);


			return query.list();
		}



}
