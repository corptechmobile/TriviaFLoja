package br.com.webapp.model.fb.diasuteis;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class DiasUteisFBDAOHibernate implements DiasUteisFBDAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public DiasUteisFB carregar(String dataFilter1, String dataFilter2, String dataFilter3) {
		String sql = "SELECT max(tab.anoMes) AS anoMes, " +
						   " SUM(tab.diasNaoUteisMes) AS diasNaoUteisMes, " +  
						   " SUM(tab.diasNaoUteisDtAtual) AS diasNaoUteisDtAtual, " + 
						   " SUM(tab.totalDiasMes) AS totalDiasMes, " + 
						   " (SUM(tab.totalDiasMes - diasNaoUteisMes)) AS diasUteis, " + 
						   " (EXTRACT(DAY FROM CAST(:dataFilter2 AS DATE)) - SUM(tab.diasNaoUteisDtAtual)) AS prazoDecorrido " + 
					" FROM ( " + 
						" SELECT COUNT(a.id_feriado) AS diasNaoUteisMes, " + 
							   " 0 AS diasNaoUteisDtAtual, " + 
							   " 0 AS totalDiasMes, " +
							   " null as anoMes, " + 
							   " null as anoMesCurrent " +
							" FROM feriado a " + 
							" WHERE a.data between :dataFilter1 AND :dataFilter3  " + 
					" union " + 
						" SELECT 0 AS diasNaoUteisMes, " + 
							  " COUNT(a.id_feriado) AS diasNaoUteisDtAtual, " + 
							  " 0 AS totalDiasMes, " + 
							  " CAST(EXTRACT(YEAR FROM CAST(:dataFilter2 AS DATE)) || CASE WHEN EXTRACT(MONTH FROM CAST(:dataFilter2 AS DATE)) < 10 THEN LPAD(CAST(EXTRACT(MONTH FROM CAST(:dataFilter2 AS DATE)) AS CHAR), 2, '0') ELSE EXTRACT(MONTH FROM CAST(:dataFilter2 AS DATE)) END AS INT) AS anoMes, " +
							  " CAST(EXTRACT(YEAR FROM CURRENT_DATE) || CASE WHEN EXTRACT(MONTH FROM CURRENT_DATE) < 10 THEN LPAD(CAST(EXTRACT(MONTH FROM CURRENT_DATE) AS CHAR), 2, '0') ELSE EXTRACT(MONTH FROM CURRENT_DATE) END AS INT) AS anoMesCurrent " +
						    " FROM feriado a " + 
						    " WHERE a.data between :dataFilter1 AND :dataFilter2 " + 
					" union " + 
						" SELECT 0 AS diasNaoUteisMes, " + 
							  " 0 AS diasNaoUteisDtAtual, " + 
							  " EXTRACT(DAY FROM DATEADD(-EXTRACT(DAY FROM DATEADD(1 MONTH TO CAST(:dataFilter2 AS DATE) )) DAY TO DATEADD(1 MONTH TO CAST(:dataFilter2 AS DATE)))) AS totalDiasMes, " +
							  " null as anoMes, " + 
							  " null as anoMesCurrent " +
							" FROM RDB$DATABASE " + 
					" ) tab ";
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("diasNaoUteisMes", Hibernate.INTEGER)
				.addScalar("diasNaoUteisDtAtual", Hibernate.INTEGER)
				.addScalar("totalDiasMes", Hibernate.INTEGER)
				.addScalar("diasUteis", Hibernate.INTEGER)
				.addScalar("prazoDecorrido", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(DiasUteisFB.class));
		
		q.setParameter("dataFilter1", dataFilter1);
		q.setParameter("dataFilter2", dataFilter2);
		q.setParameter("dataFilter3", dataFilter3);
		q.setMaxResults(1);
		
		return (DiasUteisFB) q.uniqueResult();
	}

}
