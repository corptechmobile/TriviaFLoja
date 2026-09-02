package br.com.webapp.model.fb.prodcomposto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ProdCompostoFBDAOHibernate implements ProdCompostoFBDAO {
	
	private Session session;
	private StringBuilder COLUMNS;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ProdCompostoFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.ID_PEDVENDACOMPOSTO AS id, ")
		   .append(" a.ID_PESSOA_FORN AS fornecedorId, ")
		   .append(" a.CODPRODUTO AS codProduto, ")
		   .append(" a.DESCRICAO AS descricao ");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdCompostoFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM PRODCOMPOSTO a ");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("fornecedorId", Hibernate.INTEGER)
				.addScalar("codProduto", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ProdCompostoFB.class));
		return query.list();
	}
	
}
