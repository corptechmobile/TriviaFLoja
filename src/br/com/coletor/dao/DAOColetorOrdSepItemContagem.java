package br.com.coletor.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.espelho.EspelhoColetorOrdSepItemContagem;
import br.com.coletor.model.ColetorOrdSepItemContagem;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilData;

public class DAOColetorOrdSepItemContagem {
	
	private Session session;

	public DAOColetorOrdSepItemContagem(Session session) {
		super();
		this.session = session;
	}

	public void inserir(EspelhoColetorOrdSepItemContagem espelho) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			insert.append(" INSERT INTO COLETOR_ORDSEPITEM_CONTAGEM ")
					.append(" ( ")
					.append(" CHAVE, ")
					.append(" ID_ORDEMCARREG, ")
					.append(" ID_USUARIO, ")
					.append(" ID_PRODUTO, ")
					.append(" CODBARRA, ")
					.append(" QTD, ")
					.append(" CODLOTE, ")
					.append(" DTVENCLOTE, ")
					.append(" DTLEITURA ")
					.append(" ) ")
					.append(" VALUES ")
					.append(" ( ")
					.append(" :CHAVE, ")
					.append(" :ID_ORDEMCARREG, ")
					.append(" :ID_USUARIO, ")
					.append(" :ID_PRODUTO, ")
					.append(" :CODBARRA, ")
					.append(" :QTD, ")
					.append(" :CODLOTE, ")
					.append(" :DTVENCLOTE, ")
					.append(" :DTLEITURA ")
					.append(" ); ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());

		    query.setParameter("CHAVE", espelho.getChave());
		    query.setParameter("ID_ORDEMCARREG", espelho.getOrdemSeparacaoId());
		    query.setParameter("ID_USUARIO", espelho.getUsuarioId());
		    query.setParameter("ID_PRODUTO", espelho.getProdutoId());
		    query.setParameter("CODBARRA", espelho.getCodBarra());
		    query.setParameter("QTD", espelho.getQtd());
		    query.setParameter("CODLOTE", espelho.getCodLote());
		    query.setParameter("DTLEITURA", espelho.getDtVencLote());
		    query.setParameter("DTLEITURA", UtilData.formatarStringParaData(espelho.getDtLeitura(), UtilData.FORMATO_DATA_HORA));
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format(" Erro ao tentar processar contagem com chave %s", espelho.getChave()));
		}
		
	}
	
	public void integracao(Integer seqId, Integer coletorOrdSepId) throws DAOException {
		String sql = "INSERT INTO WMS_ORDEMCARREGITEM "
						+ "(ID_ORDEMCARREGITEM_WMS, ID_ORDEMCARREG_WMS, ID_ORDEMCARREGITEM, QUANTIDADE) "
						+ "SELECT "
							+ "	(select gen_id(GEN_WMS_ORDEMCARREGITEM_ID, 1) from rdb$database) AS ID_ORDEMCARREGITEM_WMS, "
							+ "	"+seqId+" AS ID_ORDEMCARREG_WMS, "
							+ "	b.ID_ORDEMCARREGITEM, "
							+ "	COALESCE(SUM(a.QTD), 0) AS QUANTIDADE "
						+ " FROM ORDEMCARREGITEM b, "
							 + " COLETOR_ORDSEPITEM_CONTAGEM a, "
							 + " PEDVENDAITEM c "
						+ " WHERE b.ID_ORDEMCARREG = :ID_ORDEMCARREG "
						  + " AND a.ID_ORDEMCARREG = b.ID_ORDEMCARREG "
						  + " AND a.ID_PRODUTO = c.ID_PRODUTO "
						  + " AND b.ID_PEDVENDAITEM = c.ID_PEDVENDAITEM "
						+ " GROUP BY b.ID_ORDEMCARREG, b.ID_ORDEMCARREGITEM ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepItemContagem.class);
		query.setParameter("ID_ORDEMCARREG", coletorOrdSepId);
		int result = query.executeUpdate();
		if(result == 0) {
			throw new DAOException("Erro ao realizar integração dos Itens de Conferência/Saída com o ERP.");
		}
	}

}
