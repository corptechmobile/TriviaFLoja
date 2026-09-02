package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;
import br.com.coletor.model.ColetorPlanilhaCegaContagem;
import br.com.webapp.web.util.UtilData;

public class DAOColetorPlanilhaCegaContagem {
	
	private Session session;

	public DAOColetorPlanilhaCegaContagem(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<ColetorPlanilhaCegaContagem> listar(Integer coletorPlanilhaCegaId, boolean excluido) {
		
		String sql = "SELECT CHAVE as chave, "
							+" ID_CPC as coletorPlanilhaCegaId, "
							+" ID_USUARIO as usuarioId, "
							+" ID_PRODUTO as produtoId, "
							+" CODBARRA as codBarra, "
							+" QTD_CONFERIDA as qtdConferida, "
							+" QTD_DEVOLVIDA as qtdDevolvida, "
							+" QTD_AVARIA qtdAvaria, "
							+" CODLOTE as codLote, "
							+" DTVENCLOTE as dtVencLote, "
							+" DTLEITURA as dtLeitura, "
							+" DTERP as dtErp "
						+" FROM COLETOR_PC_CONTAGEM "
						+" WHERE ID_CPC = :coletorPlanilhaCegaId "
						  +" AND EXCLUIDO = :excluido "
						+" ORDER BY DTLEITURA ";
		
		Query query = this.session.createSQLQuery(sql)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("coletorPlanilhaCegaId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("qtdConferida", Hibernate.DOUBLE)
				.addScalar("qtdDevolvida", Hibernate.DOUBLE)
				.addScalar("qtdAvaria", Hibernate.DOUBLE)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.TIMESTAMP)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(ColetorPlanilhaCegaContagem.class));
		
		query.setParameter("coletorPlanilhaCegaId", coletorPlanilhaCegaId);
		query.setParameter("excluido", excluido ? 1 : 0);
		
		return query.list();
	}
	
	public void inserir(EspelhoColetorPlanilhaCegaContagem espelho) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			insert.append(" INSERT INTO COLETOR_PC_CONTAGEM ")
					.append(" ( ")
					.append(" CHAVE, ")
					.append(" ID_CPC, ")
					.append(" ID_USUARIO, ")
					.append(" ID_PRODUTO, ")
					.append(" CODBARRA, ")
					.append(" QTD_CONFERIDA, ")
					.append(" QTD_DEVOLVIDA, ")
					.append(" QTD_AVARIA, ")
					.append(espelho.getCodLote() != null ? " CODLOTE, " : "")
					.append(espelho.getDtVencLote() != null ? " DTVENCLOTE, " : "")
					.append(" DTLEITURA, ")
					.append(" DTERP, ")
					.append(" EXCLUIDO ")
					.append(" ) ")
					.append(" VALUES ")
					.append(" ( ")
					.append(" :chave, ")
					.append(" :coletorPlanilhaCegaId, ")
					.append(" :usuarioId, ")
					.append(" :produtoId, ")
					.append(" :codBarra, ")
					.append(" :qtdConferida, ")
					.append(" :qtdDevolvida, ")
					.append(" :qtdAvaria, ")
					.append(espelho.getCodLote() != null ? " :codLote, " : "")
					.append(espelho.getDtVencLote() != null ? " :dtVencLote, " : "")
					.append(" :dtLeitura, ")
					.append(" CURRENT_TIMESTAMP, ")
					.append(" :EXCLUIDO ")
					.append(" ); ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());

		    query.setParameter("chave", espelho.getChave());
		    query.setParameter("coletorPlanilhaCegaId", espelho.getColetorPlanilhaCegaId());
		    query.setParameter("usuarioId", espelho.getUsuarioId());
		    query.setParameter("produtoId", espelho.getProdutoId());
		    query.setParameter("codBarra", espelho.getCodBarra());
		    query.setParameter("qtdConferida", espelho.getQtdConferida());
		    query.setParameter("qtdDevolvida", espelho.getQtdDevolvida());
		    query.setParameter("qtdAvaria", espelho.getQtdAvaria());
		    
		    if(espelho.getCodLote() != null) {
		    	query.setParameter("codLote", espelho.getCodLote());
		    }
		    
		    if(espelho.getDtVencLote() != null) {
		    	query.setParameter("dtVencLote", UtilData.formatarStringParaData(espelho.getDtVencLote(), UtilData.FORMATO_DATA_INVERTIDA));
		    }
		    
		    query.setParameter("dtLeitura", UtilData.formatarStringParaData(espelho.getDtLeitura(), UtilData.FORMATO_DATA_HORA));
		    query.setParameter("EXCLUIDO", 0);
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format(" Erro ao tentar processar contagem com chave %s", espelho.getChave()));
		}
		
	} 

}
