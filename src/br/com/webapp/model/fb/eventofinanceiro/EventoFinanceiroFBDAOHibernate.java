package br.com.webapp.model.fb.eventofinanceiro;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOException;

public class EventoFinanceiroFBDAOHibernate implements EventoFinanceiroFBDAO{
	
	private StringBuilder COLUMNS;

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public EventoFinanceiroFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" ef.ID_EVENTOFINANCEIRO as id, ")
			   .append(" ef.ID_GRUPOFINANCEIRO as grupoFinanceiroId, ")
			   .append(" upper(gf.DESCRICAO) as descGrupoFinanceiro, ")
			   .append(" ef.MNEMONICO, ")
			   .append(" upper(ef.DESCRICAO) as descEventoFinanceiro, ")
			   .append(" ef.FIXO, ")
			   .append(" ef.BLOQUEADO, ")
			   .append(" ef.ID_CODFISCTRIBUTO as codFiscTributoId, ")
			   .append(" ef.OBRIGACODFISCTRIBUTO as obrigaCodFiscTributo, ")
			   .append(" ef.PRESTACAOCONTAS as prestacaoContas, ")
			   .append(" ef.RESTRITOSISTEMA as restritoSistema ");
	}
	
	
	@Override
	public EventoFinanceiroFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM eventofinanceiro ef, grupofinanceiro gf ")
		   .append(" WHERE ef.ID_GRUPOFINANCEIRO = gf.ID_GRUPOFINANCEIRO ")
		   .append("   AND ef.id_eventofinanceiro = :id ");
		   
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("grupoFinanceiroId", Hibernate.STRING)
				.addScalar("descGrupoFinanceiro", Hibernate.STRING)
				.addScalar("mnemonico", Hibernate.STRING)
				.addScalar("descEventoFinanceiro", Hibernate.STRING)
				.addScalar("fixo", Hibernate.INTEGER)
				.addScalar("bloqueado", Hibernate.INTEGER)
				.addScalar("codFiscTributoId", Hibernate.STRING)
				.addScalar("obrigaCodFiscTributo", Hibernate.INTEGER)
				.addScalar("prestacaoContas", Hibernate.INTEGER)
				.addScalar("restritoSistema", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (EventoFinanceiroFB) query.uniqueResult();
	}
	
	
@SuppressWarnings("unchecked")
@Override
public List<EventoFinanceiroFB> listar(String descricao) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM eventofinanceiro ef, grupofinanceiro gf ")
		   .append(" WHERE ef.ID_GRUPOFINANCEIRO = gf.ID_GRUPOFINANCEIRO ");

		if (descricao != null && !"".equals(descricao)) {
			sql.append(" AND upper(ef.descricao) like '%"+descricao.toUpperCase()+"%' ");
		}
		
		sql.append(" ORDER BY gf.DESCRICAO, ef.DESCRICAO  ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("grupoFinanceiroId", Hibernate.STRING)
				.addScalar("descGrupoFinanceiro", Hibernate.STRING)
				.addScalar("mnemonico", Hibernate.STRING)
				.addScalar("descEventoFinanceiro", Hibernate.STRING)
				.addScalar("fixo", Hibernate.INTEGER)
				.addScalar("bloqueado", Hibernate.INTEGER)
				.addScalar("codFiscTributoId", Hibernate.STRING)
				.addScalar("obrigaCodFiscTributo", Hibernate.INTEGER)
				.addScalar("prestacaoContas", Hibernate.INTEGER)
				.addScalar("restritoSistema", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
		
		
		return query.list();
	}

public List<EventoFinanceiroFB> listarAssociados(String grupoFinanceiro, String descricao, Integer grupoOrcamentoId) {
	StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("  FROM eventofinanceiro ef, grupofinanceiro gf ")
		   .append(" WHERE ef.ID_GRUPOFINANCEIRO = gf.ID_GRUPOFINANCEIRO ");

		if (descricao != null && !"".equals(descricao)) {
			sql.append(" AND upper(ef.descricao) like '%"+descricao.toUpperCase()+"%' ");
		}
		
		if (grupoFinanceiro != null && !"".equals(grupoFinanceiro) && !"null".equals(grupoFinanceiro)) {
			sql.append(" AND ef.ID_GRUPOFINANCEIRO = '"+grupoFinanceiro+"' ");
		}		
		
		sql.append(" AND ef.ID_ORCAMENTOGRUPO = "+grupoOrcamentoId);
		
		sql.append(" ORDER BY gf.DESCRICAO, ef.DESCRICAO  ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("grupoFinanceiroId", Hibernate.STRING)
				.addScalar("descGrupoFinanceiro", Hibernate.STRING)
				.addScalar("mnemonico", Hibernate.STRING)
				.addScalar("descEventoFinanceiro", Hibernate.STRING)
				.addScalar("fixo", Hibernate.INTEGER)
				.addScalar("bloqueado", Hibernate.INTEGER)
				.addScalar("codFiscTributoId", Hibernate.STRING)
				.addScalar("obrigaCodFiscTributo", Hibernate.INTEGER)
				.addScalar("prestacaoContas", Hibernate.INTEGER)
				.addScalar("restritoSistema", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
		
		
		return query.list();
	}


@SuppressWarnings("unchecked")
@Override
public List<EventoFinanceiroFB> listar() {
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT ").append(COLUMNS)
	   .append("  FROM eventofinanceiro ef, grupofinanceiro gf ")
	   .append(" WHERE ef.ID_GRUPOFINANCEIRO = gf.ID_GRUPOFINANCEIRO ");

	sql.append(" ORDER BY gf.DESCRICAO, ef.DESCRICAO  ");
	
	Query query = (Query) session.createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("grupoFinanceiroId", Hibernate.STRING)
			.addScalar("descGrupoFinanceiro", Hibernate.STRING)
			.addScalar("mnemonico", Hibernate.STRING)
			.addScalar("descEventoFinanceiro", Hibernate.STRING)
			.addScalar("fixo", Hibernate.INTEGER)
			.addScalar("bloqueado", Hibernate.INTEGER)
			.addScalar("codFiscTributoId", Hibernate.STRING)
			.addScalar("obrigaCodFiscTributo", Hibernate.INTEGER)
			.addScalar("prestacaoContas", Hibernate.INTEGER)
			.addScalar("restritoSistema", Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
	
	return query.list();
}

public void editar(EventoFinanceiroFB eventoFinanceiroFB) throws DAOException {
try {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE EVENTOFINANCEIRO SET ")
				        .append("ID_ORCAMENTOGRUPO = :ID_ORCAMENTOGRUPO ")
				      .append(" WHERE ID_EVENTOFINANCEIRO = :ID_EVENTOFINANCEIRO");
		
		Query query = (Query) session.createSQLQuery(sql.toString());
		query.setParameter("ID_ORCAMENTOGRUPO", eventoFinanceiroFB.getOrcamentoGrupoId());
		query.setParameter("ID_EVENTOFINANCEIRO", eventoFinanceiroFB.getId());
        
		query.executeUpdate();
        
	} catch (Exception e) {
		e.printStackTrace();
		throw new DAOException(e.getMessage());
	}
}

@Override
public List<EventoFinanceiroFB> listarDesassociados(String grupoFinanceiro, String descricao, Integer id) {
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT ").append(COLUMNS)
	   .append("  FROM eventofinanceiro ef, grupofinanceiro gf ")
	   .append(" WHERE ef.ID_GRUPOFINANCEIRO = gf.ID_GRUPOFINANCEIRO ");

	if (descricao != null && !"".equals(descricao)) {
		sql.append(" AND upper(ef.descricao) like '%"+descricao.toUpperCase()+"%' ");
	}
	
	if (grupoFinanceiro != null && !"".equals(grupoFinanceiro)) {
		sql.append(" AND ef.ID_GRUPOFINANCEIRO = '"+grupoFinanceiro+"' ");
	}
	
	sql.append(" AND (ef.ID_ORCAMENTOGRUPO not in ("+id+") or coalesce(ef.ID_ORCAMENTOGRUPO,0) = 0) ");
	
	sql.append(" ORDER BY gf.DESCRICAO, ef.DESCRICAO  ");
	
	Query query = (Query) session.createSQLQuery(sql.toString())
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("grupoFinanceiroId", Hibernate.STRING)
			.addScalar("descGrupoFinanceiro", Hibernate.STRING)
			.addScalar("mnemonico", Hibernate.STRING)
			.addScalar("descEventoFinanceiro", Hibernate.STRING)
			.addScalar("fixo", Hibernate.INTEGER)
			.addScalar("bloqueado", Hibernate.INTEGER)
			.addScalar("codFiscTributoId", Hibernate.STRING)
			.addScalar("obrigaCodFiscTributo", Hibernate.INTEGER)
			.addScalar("prestacaoContas", Hibernate.INTEGER)
			.addScalar("restritoSistema", Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
	
	
	return query.list();

}

@SuppressWarnings("unchecked")
@Override
public List<EventoFinanceiroFB> listar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, GrupoFinanceiroFB grupoFinanceiroFilter, Date dataFilter, Date dataFilter2) {
	String varWhere = "";
	
	if(empresaFilter.getId()!=null) {
		varWhere += " and t.id_pessoa_emp = :empresaId ";
	}
	
	if(vendedorFilter.getId()!=null) {
		varWhere += " and t.id_grupofinanceiro = :vendedorId ";
	}
	
	if(grupoFinanceiroFilter!=null) {
		varWhere += " and gf.id_grupofinanceiro = :grupoFinanceiroId ";
	}
	
	String sql = " select tab.id_grupofinanceiro as grupoFinanceiroId, "+
			     "        tab.id_eventofinanceiro as eventoFinanceiroId, "+
				 "  	  max(tab.grupo) as descGrupoFinanceiro, "+
				 "        max(tab.evento) as descEventoFinanceiro, "+
				 "        coalesce(sum(tab.pago),0) as valorPago, "+
				 "        coalesce(sum(tab.avencer),0) as valorAvencer, "+
				 "        coalesce(sum(tab.vencida),0) as valorVencido from ( "+
				 "     select gf.id_grupofinanceiro, "+
				 "            ef.id_eventofinanceiro, "+
				 "            (gf.descricao) as grupo, "+
				 "            (ef.descricao) as evento, "+
				 "            (select sum(tm.valormov) "+
				 "                  from titulomov tm "+
				 "                 where tm.id_titulo = t.id_titulo "+
				 "                   and tm.momento between :dt1 and :dt2 "+
				 "                   and tm.id_usuario <> 0 "+
				 "                   and tm.id_tipomovtit in (1,2, 10, 11)) as pago, "+
				 "            (iif(t.datavencimento >= current_date and t.id_titulosit = 1,t.saldotitulo,0)) as avencer, "+
				 "            (iif(t.datavencimento < current_date and t.id_titulosit = 1,t.saldotitulo,0)) as vencida, "+
				 "            t.id_titulosit, "+
				 "            t.valorbruto, "+
				 "            t.valorliquido, "+
				 "            t.saldotitulo, "+
				 "            t.id_titulo "+
				 "       from titulo t, "+
				 "            eventofinanceiro ef, "+
				 "            grupofinanceiro gf "+
				 "       where ef.id_eventofinanceiro = t.id_eventofinanceiro "+
				 "         and gf.id_grupofinanceiro = ef.id_grupofinanceiro "+
				 "         and t.tipo = 'P' "+
				 "         and gf.id_grupofinanceiro = 'NCR' "+
				 "         and (t.datavencimento between :dt1 and :dt2 or t.datapagamento between :dt1 and :dt2) "+
				 "       "+varWhere+" "+				 
				 " ) as tab "+
				 " group by tab.id_grupofinanceiro, "+
				 "        tab.id_eventofinanceiro "+
				 " union "+
				 " select gf.id_grupofinanceiro as grupoFinanceiroId, "+
				 "        ef.id_eventofinanceiro as eventoFinanceiroId, "+
				 "        max(gf.descricao) as descGrupoFinanceiro, "+
				 "        max(ef.descricao) as descEventoFinanceiro, "+
				 "        sum(case t.id_titulosit when 3 then t.valorliquido else 0 end) as valorPago, "+
				 "        sum(iif(t.datavencimento >= current_date and t.id_titulosit = 1,t.valorliquido,0)) as valorAVencer, "+
				 "        sum(iif(t.datavencimento < current_date and t.id_titulosit = 1,t.valorliquido,0)) as valorVencido "+
				 "   from titulo t, "+
				 "        eventofinanceiro ef, "+
				 "        grupofinanceiro gf "+
				 "   where ef.id_eventofinanceiro = t.id_eventofinanceiro "+
				 "     and gf.id_grupofinanceiro = ef.id_grupofinanceiro "+
				 "     and t.tipo = 'P' "+
				 "     and gf.id_grupofinanceiro <> 'NCR' "+
				 "     and (t.datavencimento between :dt1 and :dt2 or t.datapagamento between :dt1 and :dt2) "+
				 "     "+varWhere+" "+
				 "     group by gf.id_grupofinanceiro, "+
				 "              ef.id_eventofinanceiro ";	 

		
	Query query = (Query) session.createSQLQuery(sql)
			.addScalar("grupoFinanceiroId", Hibernate.STRING)
			.addScalar("eventoFinanceiroId", Hibernate.INTEGER)
			.addScalar("descGrupoFinanceiro", Hibernate.STRING)
			.addScalar("descEventoFinanceiro", Hibernate.STRING)
			.addScalar("valorPago", Hibernate.DOUBLE)
			.addScalar("valorAvencer", Hibernate.DOUBLE)
			.addScalar("valorVencido", Hibernate.DOUBLE)
			.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
	
	if(empresaFilter.getId()!=null) {
		query.setParameter("empresaId", empresaFilter.getId());
	}
	
	if(vendedorFilter.getId()!=null) {
		query.setParameter("vendedorId", vendedorFilter.getId());
	}
	
	if(grupoFinanceiroFilter!=null) {
		query.setParameter("grupoFinanceiroId", grupoFinanceiroFilter.getId());
	}
	
	query.setParameter("dt1", dataFilter);
	query.setParameter("dt2", dataFilter2);
	
	
	return query.list();

}

@SuppressWarnings("unchecked")
@Override
public List<EventoFinanceiroFB> listarDetalhe(EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter, Date dataFilter2, String grupoFinanceiroId, Integer eventoFinanceiroId) {
	String varWhere = "";
	String varCampo = " (case t.id_titulosit when 2 then coalesce(t.valorliquido,0) else case t.id_titulosit when 3 then coalesce(t.valorliquido,0) else 0 end end) as valorPago, ";
	
	if(empresaFilter.getId()!=null) {
		varWhere += " and t.id_pessoa_emp = :empresaId ";
	}
	
	if(vendedorFilter.getId()!=null) {
		varWhere += " and t.id_pessoa_clifor = :vendedorId ";
	}
	
	if(grupoFinanceiroId != null && !"".equals(grupoFinanceiroId)) {
		varWhere += " and gf.id_grupofinanceiro = :grupoFinanceiroId";
		
		if("NCR".equals(grupoFinanceiroId)) {
			varCampo = "(select sum(coalesce(tm.valormov,0)) "+
					   "   from titulomov tm "+
					   "  where tm.id_titulo = t.id_titulo "+
					   "    and tm.momento between :dt1 and :dt2 "+
					   "    and tm.id_usuario <> 0 "+
					   "    and tm.id_tipomovtit in (1,2, 10, 11)) as valorPago, ";
		}
		
		
	}

	if(eventoFinanceiroId !=null) {
		varWhere += " and ef.id_eventofinanceiro = :eventoFinanceiroId ";
	}


	String sql = " select gf.id_grupofinanceiro as grupoFinanceiroId, "+
					"        ef.id_eventofinanceiro as eventoFinanceiroId, "+
					"        t.id_titulo as id, "+
					"        t.parcela, "+
					"        t.numTitulo||'-'||t.parcela as numTitulo, "+
					"        (gf.descricao) as descGrupoFinanceiro, "+
					"        (ef.descricao) as descEventoFinanceiro, "+
					"        f.nomefantmnem as fornecedorDesc,  "+
					"        "+varCampo+" "+
					"        (iif(t.datavencimento >= current_date and t.id_titulosit = 1,coalesce(t.valorliquido,0),0)) as valorAVencer, "+
					"        (iif(t.datavencimento < current_date and t.id_titulosit = 1,coalesce(t.valorliquido,0),0)) as valorVencido, "+
					"        t.datavencimento as dtVencimento "+
					"   from titulo t, "+
					"        eventofinanceiro ef, "+
					"        grupofinanceiro gf, "+
					"        pessoa f "+
					"   where ef.id_eventofinanceiro = t.id_eventofinanceiro "+
					"     and gf.id_grupofinanceiro = ef.id_grupofinanceiro "+
					"     and t.id_pessoa_clifor = f.id_pessoa "+
					"     and t.tipo = 'P' "+
					"       "+varWhere+" "+
					"     and (t.datavencimento between :dt1 and :dt2 or t.datapagamento between :dt1 and :dt2) ";
		
		
	Query query = (Query) session.createSQLQuery(sql)
			.addScalar("grupoFinanceiroId", Hibernate.STRING)
			.addScalar("eventoFinanceiroId", Hibernate.INTEGER)
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("descGrupoFinanceiro", Hibernate.STRING)
			.addScalar("descEventoFinanceiro", Hibernate.STRING)
			.addScalar("numTitulo", Hibernate.STRING)
			.addScalar("valorPago", Hibernate.DOUBLE)
			.addScalar("valorAvencer", Hibernate.DOUBLE)
			.addScalar("valorVencido", Hibernate.DOUBLE)
			.addScalar("fornecedorDesc", Hibernate.STRING)
			.addScalar("dtVencimento", Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(EventoFinanceiroFB.class));
	
	if(empresaFilter.getId()!=null) {
		query.setParameter("empresaId", empresaFilter.getId());
	}
	
	if(vendedorFilter.getId()!=null) {
		query.setParameter("vendedorId", vendedorFilter.getId());
	}
	
	if(grupoFinanceiroId != null && !"".equals(grupoFinanceiroId)) {
		query.setParameter("grupoFinanceiroId", grupoFinanceiroId);
	}

	if(eventoFinanceiroId !=null) {
		query.setParameter("eventoFinanceiroId", eventoFinanceiroId);
	}

	query.setParameter("dt1", dataFilter);
	query.setParameter("dt2", dataFilter2);
	
	
	return query.list();}



}
