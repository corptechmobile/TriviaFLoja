package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ECFVendasPeriodoDAOHibernate implements ECFVendasPeriodoDAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ECFVendasPeriodo> listar(Integer empresaId, Date dataFilter1, Date dataFilter2) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("	SELECT EXTRACT (weekday  from ev.momentovenda ) dia, " + 
				   "  		   EXTRACT (hour  from ev.momentovenda ) hora, " + 
				   "  		   COUNT(EV.id_ecfvendas) numClientes, " + 
				   "  		   SUM(EV.valorvenda) numVendas " + 
				   "	FROM  ecf_vendas ev " + 
				   "	WHERE ev.datavenda between :d1 and :d2 " + 
				   "  	AND ev.cancelada = 0 " + 
				   "  	AND ev.concluida = 1 " + 
				   "	AND ev.id_pessoa_emp = :pessoa_emp " +
				   "	GROUP BY EXTRACT (weekday  from ev.momentovenda ), " + 
				   "         	 EXTRACT (hour  from ev.momentovenda ) ");
		
		Query query = (Query) this.session.createSQLQuery(sql.toString())
					.addScalar("dia", Hibernate.INTEGER)
					.addScalar("hora", Hibernate.INTEGER)
					.addScalar("numClientes", Hibernate.INTEGER)
					.addScalar("numVendas", Hibernate.DOUBLE)
					.setParameter("d1", dataFilter1)
					.setParameter("d2", dataFilter2)
					.setParameter("pessoa_emp", empresaId)
					.setResultTransformer(Transformers.aliasToBean(ECFVendasPeriodo.class));
		return query.list();
		
	}
}
