package br.com.webapp.model.fb.orcamentogrupo;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class OrcamentoGrupoFBDAOHibernate implements OrcamentoGrupoFBDAO{
	
	private StringBuilder COLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public OrcamentoGrupoFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" og.ID_ORCAMENTOGRUPO as id, ")
			   .append(" og.DESCRICAO, ")
			   .append(" og.PERCFAT as percFaturamento, ")
			   .append(" og.ORDEM as ordem, ")			   
			   .append(" og.ATIVO ");
	}
	
	
	@Override
	public OrcamentoGrupoFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM ORCAMENTOGRUPO og")
		   .append(" WHERE og.ID_ORCAMENTOGRUPO = :id ");
		   
			Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("percFaturamento", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoGrupoFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (OrcamentoGrupoFB) query.uniqueResult();
	}
	
	
@SuppressWarnings("unchecked")
@Override
public List<OrcamentoGrupoFB> listar(String descricao) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM ORCAMENTOGRUPO og ");
		
		if (descricao != null && !"".equals(descricao)) {
			sql.append(" WHERE upper(og.descricao) like '%"+descricao.toUpperCase()+"%' ");
		}
		
		sql.append(" ORDER BY og.ordem, og.DESCRICAO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("ativo", Hibernate.INTEGER)
				.addScalar("ordem", Hibernate.INTEGER)				
				.addScalar("percFaturamento", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(OrcamentoGrupoFB.class));
		
		return query.list();
	}

@SuppressWarnings("unchecked")
@Override
public List<OrcamentoGrupoFB> listar() {
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT ").append(COLUMNS)
	   .append("  FROM ORCAMENTOGRUPO og");
	sql.append(" ORDER BY og.ordem, og.DESCRICAO  ");
	
	Query query = (Query) session.createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("descricao", Hibernate.STRING)
			.addScalar("ativo", Hibernate.INTEGER)
			.addScalar("ordem", Hibernate.INTEGER)			
			.addScalar("percFaturamento", Hibernate.DOUBLE)
			.setResultTransformer(Transformers.aliasToBean(OrcamentoGrupoFB.class));
	
	return query.list(); 
}

@Override
public void excluir(Integer Id) throws DAOException {
	try {
		
		System.out.println("[ComissaoFaixaDescFBDAOHibernate][delete][id]" + Id);
	
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ORCAMENTOGRUPO WHERE ID_ORCAMENTOGRUPO = :ID_ORCAMENTOGRUPO ");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_ORCAMENTOGRUPO", Id);
        
		query.executeUpdate();
        
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
	
}

@Override
public Integer insert(OrcamentoGrupoFB orcamentoGrupoFB) throws DAOException {
	try {
		
		Integer orcamentoGrupoFBId = getSeq();
		System.out.println("[OrcamentoGrupoFBDAOHibernate][insert][id]" + orcamentoGrupoFBId);
	
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ORCAMENTOGRUPO (ID_ORCAMENTOGRUPO, DESCRICAO, PERCFAT, ATIVO, ORDEM) ")
		.append("VALUES (:ID_ORCAMENTOGRUPO, ")
		        .append(":DESCRICAO, ")
		        .append(":PERCFAT, ")
		        .append(":ATIVO, ")
		        .append(":ORDEM) ");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_ORCAMENTOGRUPO", orcamentoGrupoFBId);
		query.setParameter("DESCRICAO", orcamentoGrupoFB.getDescricao());
        query.setParameter("PERCFAT", orcamentoGrupoFB.getPercFaturamento());
        query.setParameter("ATIVO", orcamentoGrupoFB.getAtivo());
        query.setParameter("ORDEM", orcamentoGrupoFB.getOrdem());

		query.executeUpdate();
        
		return orcamentoGrupoFBId;
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
		
}

@Override
public void alterar(OrcamentoGrupoFB orcamentoGrupoFB) throws DAOException {
	try {
		
		System.out.println("[OrcamentoGrupoFBDAOHibernate][update][id]" + orcamentoGrupoFB.getId());
	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ORCAMENTOGRUPO SET ")
				        .append("DESCRICAO = :DESCRICAO, ")
				        .append("PERCFAT = :PERCFAT, ")
				        .append("ORDEM = :ORDEM, ")
				        .append("ATIVO = :ATIVO ")
				      .append(" WHERE ID_ORCAMENTOGRUPO = :ID_ORCAMENTOGRUPO");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_ORCAMENTOGRUPO", orcamentoGrupoFB.getId());
		query.setParameter("DESCRICAO", orcamentoGrupoFB.getDescricao());
        query.setParameter("PERCFAT", orcamentoGrupoFB.getPercFaturamento());
        query.setParameter("ATIVO", orcamentoGrupoFB.getAtivo());
        query.setParameter("ORDEM", orcamentoGrupoFB.getOrdem());
        
		query.executeUpdate();
        
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
}

private Integer getSeq() throws DAOException {
	try {
		String sql = "select gen_id(GEN_ORCAMENTOGRUPO_ID, 1) from rdb$database;";
		Query q = (Query) session.createSQLQuery(sql);
		BigInteger key = (BigInteger) q.uniqueResult();
		return Integer.parseInt(key.toString());
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException("Erro ao gerar Sequence do OrcamentoGrupoFB.");
	}
}

}
