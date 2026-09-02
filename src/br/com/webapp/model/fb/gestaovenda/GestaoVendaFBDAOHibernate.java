package br.com.webapp.model.fb.gestaovenda;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;

public class GestaoVendaFBDAOHibernate implements GestaoVendaFBDAO{

	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public GestaoVendaFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_GESTAOVENDA as id, ")
			   .append(" a.ID_GESTAOVENDA_REF as gestaoRefId, ")
			   .append(" a.ID_USUARIO as usuarioId, ")
			   .append(" a.NOME as nome, ")
			   .append(" a.ORDEM as ordem,")
			   .append(" a.ALCADA as alcada, ")
			   .append(" a.NUMDIAS as numDias, ")
			   .append(" a.VENDASEMESTQ as vendasEmEstq, ")
			   .append(" a.CODEDT as codEdt, ")
			   .append(" a.VERPRODNAODISPVENDA as verProdNaoDispVenda ");
	}
	
	@Override
	public GestaoVendaFB carregar(Integer idGestaoVenda) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM GESTAOVENDA a ")
		   .append(" WHERE a.ID_GESTAOVENDA = :idGestaoVenda ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoRefId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("numDias", Hibernate.INTEGER)
				.addScalar("vendasEmEstq", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)
				.addScalar("verProdNaoDispVenda", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(GestaoVendaFB.class));
		query.setParameter("idGestaoVenda", idGestaoVenda);
		query.setMaxResults(1);
		return (GestaoVendaFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GestaoVendaFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM GESTAOVENDA a ")
		   .append(" ORDER BY a.CODEDT ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("gestaoRefId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("nome", Hibernate.STRING)
				.addScalar("ordem", Hibernate.INTEGER)
				.addScalar("alcada", Hibernate.DOUBLE)
				.addScalar("numDias", Hibernate.INTEGER)
				.addScalar("vendasEmEstq", Hibernate.INTEGER)
				.addScalar("codEdt", Hibernate.STRING)
				.addScalar("verProdNaoDispVenda", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(GestaoVendaFB.class));
		return query.list();
	}

}
