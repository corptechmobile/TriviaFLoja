package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ECFVendasFBDAOHibernate implements ECFVendasFBDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ECFVendasFB> listarACancelar(Integer empresaId, Integer vendedorId, Integer tipoVendedorId, Date dataFilter1, Date dataFilter2) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT ECF.id_ecfvendas AS idECFERP, " + 
						  " ECF.id_ecfvendas_pdv AS idECFPDV, " +
						  " ECF.id_ecfpontovenda AS idECFPONTOPDV, " +
						  " ECF.ccf AS numNFCe, " + 
						  " ECF.serie AS serieNFCe, " +
						  " ECF.DATAVENDA dtVenda, " + 
						  " ECF.valorvenda valor " + 
					  "FROM ecf_vendas ecf, " +
						  " vendedor v, " + 
						  " formapagtorec fpr, " + 
						  " ecf_vendasformarec ECFFR " + 
						" WHERE ecf.id_ecfvendas=ecffr.id_ecfvendas " + 
						  " AND ecffr.id_formapagtorec=fpr.id_formapagtorec " + 
						  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " + 
						  " AND ecf.concluida=1 " + 
						  " AND ecf.cancelada=0 " + 
						  " AND ecf.id_pessoa_emp = :id_pessoa " + 
						  " AND ecffr.id_formapagtorec=2 " + 
						  " AND ecf.id_pessoa_vend = v.id_pessoa "+
						  " AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  "+
						  " AND v.id_tipovendedor = COALESCE(:tipoVendedor,v.id_tipovendedor)  "+
						  " AND ecffr.id_ecfvendasformarec not IN (SELECT CC.id_ecfvendasformarec " + 
																   " FROM CARTAO CC " +
																  " WHERE CC.id_ecfvendasformarec = ecffr.id_ecfvendasformarec) ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("idECFERP", Hibernate.INTEGER)
				.addScalar("idECFPDV", Hibernate.INTEGER)
				.addScalar("idECFPONTOPDV", Hibernate.INTEGER)
				.addScalar("numNFCe", Hibernate.INTEGER)
				.addScalar("serieNFCe", Hibernate.STRING)
				.addScalar("dtVenda", Hibernate.DATE)
				.addScalar("valor", Hibernate.DOUBLE)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa", empresaId)
				.setParameter("vendedor", vendedorId)
				.setParameter("tipoVendedor", tipoVendedorId)
				.setResultTransformer(Transformers.aliasToBean(ECFVendasFB.class));
		
		return query.list();
	}

}
