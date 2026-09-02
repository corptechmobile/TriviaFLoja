package br.com.webapp.model.fb.coletorpc.nfcompra;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletor.ColetorInvFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;

public class ColetorPCNFCompraFBDAOHibernate implements ColetorPCNFCompraFBDAO{

	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<ColetorPCNFCompraFB> listar(ColetorPCNFCompraFB nfCompraId) {
		String sql = "SELECT ID_CPC AS id, "
					+" ID_NFCOMPRA AS nfCompraId "
					+ "FROM COLETOR_PC_NFCOMPRA "    
					+"WHERE ID_NFCOMPRA = :nfCompraId"; 
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPCNFCompraFB.class);
		query.setParameter("ID_NFCOMPRA", nfCompraId);			
		return query.list();
	}

	@Override
	public Integer insert(ColetorPCNFCompraFB coletorPCNFCompraFB) {
		try {
			
			StringBuilder insert = new StringBuilder(); 
			insert.append(" INSERT INTO COLETOR_PC_NFCOMPRA (ID_CPC, ID_NFCOMPRA) ")
			      .append(" VALUES (:coletorPCId, :nfCompraId) ");
				
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString()); 
		    
		    query.setParameter("coletorPCId", coletorPCNFCompraFB.getColetorId());
		    query.setParameter("nfCompraId", coletorPCNFCompraFB.getNfCompraId());
			
		    query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;
	}
	
	
	
	@Override
	@SuppressWarnings("deprecation")
	public ColetorPCNFCompraFB carregar(Integer coletorFBId, Integer nfCompraId) {
		String sql = "SELECT ID_CPC AS coletorId, "
				+" ID_NFCOMPRA AS nfCompraId "
				+" FROM COLETOR_PC_NFCOMPRA "    
				+" WHERE ID_NFCOMPRA = :nfCompraId "
				+" AND ID_CPC = :coletorFBId";
		Query query = session.createSQLQuery(sql.toString())
				.addScalar("coletorId", Hibernate.INTEGER)
	 			.addScalar("nfCompraId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCNFCompraFB.class));
		query.setParameter("coletorFBId", coletorFBId);
		query.setParameter("nfCompraId", nfCompraId);
		query.setMaxResults(1);
		return (ColetorPCNFCompraFB) query.uniqueResult();
	}

	@Override
	public void delete(Integer coletorPCId, Integer nfCompraId) {
	    
	        String sql = "DELETE FROM COLETOR_PC_NFCOMPRA "
	                    + "WHERE ID_CPC = :coletorPCId "
	                    + "AND ID_NFCOMPRA = :nfCompraId ";
	        Query query = session.createSQLQuery(sql);
	        query.setParameter("coletorPCId", coletorPCId);
	        query.setParameter("nfCompraId", nfCompraId);
	        query.executeUpdate();
	 
	}

	@Override
	public Object delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ColetorPCNFCompraFB coletorPCNFCompraFB) {
		  String sql = "DELETE FROM COLETOR_PC_NFCOMPRA "
                  + "WHERE ID_CPC = :coletorPCId "
                  + "AND ID_NFCOMPRA = :nfCompraId";
      Query query = session.createSQLQuery(sql);
      query.setParameter("coletorPCId", coletorPCNFCompraFB.getColetorId());
      query.setParameter("nfCompraId", coletorPCNFCompraFB.getNfCompraId());
      query.executeUpdate();	
	}

	@Override
	public List<ColetorPCNFCompraFB> listar(Integer coletorFBId) {
		String sql = "SELECT ID_CPC AS id, "
				+" ID_NFCOMPRA AS nfCompraId "
				+ "FROM COLETOR_PC_NFCOMPRA "    
				+"WHERE ID_CPC = :coletorFBId"; 
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPCNFCompraFB.class);
		query.setParameter("coletorFBId", coletorFBId);			
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorPCNFCompraFB> carregar(Integer coletorFBId) {
		String sql = "SELECT ID_CPC AS id, "
					+" ID_NFCOMPRA AS nfCompraId "
					+ "FROM COLETOR_PC_NFCOMPRA "    
					+"WHERE ID_CPC = :coletorFBId"; 
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorPCNFCompraFB.class);
		query.setParameter("coletorFBId", coletorFBId);			
		return query.list();
	}

}
