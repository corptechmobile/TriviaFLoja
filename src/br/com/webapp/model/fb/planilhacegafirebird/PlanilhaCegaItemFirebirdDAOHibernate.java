package br.com.webapp.model.fb.planilhacegafirebird;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;


public class PlanilhaCegaItemFirebirdDAOHibernate implements PlanilhaCegaItemFirebirdDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Integer inserir(PlanilhaCegaItemFirebird rs) throws RNException {
		
		
		try {
			
			String sql = "INSERT INTO PLANILHACEGAITEM "+
									    " ( "+
									    " ID_PLANILHACEGA, "+
									    " ID_PRODUTO, "+
									    " ID_LOCALIDADE, "+ 
									    " QTDRECEBIDA, "+
									    " QTDAVARIA, "+
									    " VENCIMENTOLOTE, "+
									    " OBSERVACAO, "+
									    " ITEMSEMREFERENCIA, "+
									    " ITEMDAGERACAO, "+
									    " RESTRICAO, "+
									    " ESTOQUEATUALIZADO, "+
									    " QTDDEVOLVIDA, "+
									    " ID_UNIDADE_CPR "+
									    " ) "+
									 " VALUES ( "+
									  	" :ID_PLANILHACEGA, "+
									  	" :ID_PRODUTO, "+
									  	" :ID_LOCALIDADE, "+
									  	" :QTDRECEBIDA, "+
									  	" :QTDAVARIA, "+
									  	" :VENCIMENTOLOTE, "+
									  	" :OBSERVACAO, "+ 
									  	" :ITEMSEMREFERENCIA, "+  
									  	" :ITEMDAGERACAO, "+
									  	" :RESTRICAO, "+
									  	" :ESTOQUEATUALIZADO, "+ 
									  	" :QTDDEVOLVIDA, "+
									  	" :ID_UNIDADE_CPR "+ 
									 " ) RETURNING ID_PLANILHACEGAITEM;";
	
			Query q = this.session.createSQLQuery(sql);
	        q.setParameter("ID_PLANILHACEGA", rs.getPlanilhaCega());
	        q.setParameter("ID_PRODUTO", rs.getProduto());
	        q.setParameter("ID_LOCALIDADE", rs.getLocalidade());
	        q.setParameter("QTDRECEBIDA", rs.getQtdRecebida());
	        q.setParameter("QTDAVARIA", rs.getQtdAvaria());
	        q.setParameter("VENCIMENTOLOTE", rs.getVencimentoLote());
	        q.setParameter("OBSERVACAO", rs.getObservacao());
	        q.setParameter("ITEMSEMREFERENCIA", rs.getItemSemReferencia());
	        q.setParameter("ITEMDAGERACAO", rs.getItemGeracao());
	        q.setParameter("RESTRICAO", rs.getRestricao());
	        q.setParameter("ESTOQUEATUALIZADO", rs.getEstoqueAtualizado());
	        q.setParameter("QTDDEVOLVIDA", rs.getQtdDevolvida());
	        q.setParameter("ID_UNIDADE_CPR", rs.getIdUnidadeCpr());
	        
			// 2. No Hibernate 3, precisamos usar o .addScalar() para ele entender o retorno do INSERT
	        // O primeiro parâmetro do addScalar deve ser o nome exato da coluna retornada no passo anterior.
	        ((org.hibernate.SQLQuery) q).addScalar("ID_PLANILHACEGAITEM", Hibernate.INTEGER);

	        // 3. Agora o uniqueResult() funcionará sem estourar exceção de formato
	        Integer idGerado = (Integer) q.uniqueResult();

	        return idGerado;
	        
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro na inclusão do Item - Planilha Cega no Trivia ERP.");
		}
		
	}

	@Override
	public void inserirLote(PlanilhaCegaItemFirebird rs) throws RNException {
		try {
			
			Integer planilhaCegaItemLoteId = getSeq();
			
			String sql = "INSERT INTO PLANILHACEGAITEMLOTE "+
									  " ( "+
					                    " ID_PLANILHACEGAITEMLOTE, "+
								        " ID_PLANILHACEGAITEM, "+
								    	" QTDRECEBIDA, "+
								        " QTDAVARIA, "+
								    	" QTDDEVOLVIDA, "+
								    	" VENCIMENTOLOTE, "+
								    	" CODLOTE, "+
								    	" FABRICACAOLOTE "+
									  " ) "+
									 " VALUES ( "+
									 	" :ID_PLANILHACEGAITEMLOTE, "+
									 	" :ID_PLANILHACEGAITEM, "+
									  	" :QTDRECEBIDA, "+
									  	" :QTDAVARIA, "+
									  	" :QTDDEVOLVIDA, "+
									  	" :VENCIMENTOLOTE, "+
									  	" :CODLOTE, "+
									  	" CURRENT_DATE "+
									 " );";
	
			Query q = this.session.createSQLQuery(sql);
			q.setParameter("ID_PLANILHACEGAITEMLOTE", planilhaCegaItemLoteId);
			q.setParameter("ID_PLANILHACEGAITEM", rs.getId());
			q.setParameter("QTDRECEBIDA", rs.getQtdRecebida());
			q.setParameter("QTDAVARIA", rs.getQtdAvaria());
			q.setParameter("QTDDEVOLVIDA", rs.getQtdDevolvida());
			q.setParameter("VENCIMENTOLOTE", rs.getVencimentoLote());
			q.setParameter("CODLOTE", rs.getCodLote());
			
			q.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro na inclusão do Item Lote - Planilha Cega no Trivia ERP.");
		}
		
	}
	
	@SuppressWarnings("unused")
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_PLANILHACEGAITEMLOTE_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaFB.");
		}
	}
	
}
