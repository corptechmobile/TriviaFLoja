package br.com.webapp.model.fb.coletorpc;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;



public class ColetorPCItemFBDAOHibernate  implements ColetorPCItemFBDAO{
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<ColetorPCItemFB> listar(Integer coletorPCFBId) {

		String sql =	" SELECT CPI.ID_CPC_ITEM AS id,  "+
							      " cpi.ID_CPC AS coletorId,  "+
							      " cpi.ID_PRODUTO AS produtoId,   "+
							      " max(p.CODINTERNO) AS produtoCod,  "+
							      " max(p.DESCRICAO) AS produtoDesc,  "+
							      " max(cpi.QTD) AS quantidade,   "+
							      " sum(COALESCE(cpc.QTD_CONFERIDA,0)) AS qtdLeitura, "+
							      " sum(COALESCE(cpc.QTD_AVARIA,0)) AS qtdAvaria, "+
							      " sum(COALESCE(cpc.QTD_DEVOLVIDA,0)) AS qtdDevolvida, "+
							      " max(u.ID_UNIDADE)  AS  unidadeId,  "+
							      " max(u.DESCRESUMIDA)  AS unidadeDesc, "+
							      " max(p.CONTROLALOTE) AS controlaLote, "+
							      " max(cp.DTINICIO) AS dtConfIni, "+
							      " max(cpc.CODLOTE) codLote, "+
							      " max(cpc.DTVENCLOTE) dtVencLot, "+
							      " max(p.SHELFLIFE) AS shelfLife, "+
							      " max(p.PERCACEITASHELFLIFE) AS percAceitaShelfLife, "+
							      " max(e.ID_LOCALIDADE_PADRAO) AS localidadeId, "+
							      " max(p.QTDDECIMAL) as qtdDecimal "+
							 " FROM COLETOR_PC cp ,  "+
							       " COLETOR_PC_ITEM cpi "+
							       " LEFT JOIN COLETOR_PC_CONTAGEM cpc ON (cpi.ID_CPC = cpc.ID_CPC AND cpi.ID_PRODUTO = cpc.ID_PRODUTO AND cpc.EXCLUIDO = 0),   "+
							       " produto p,  "+
							       " UNIDADE u,  "+
							       " EMPRESA e   "+
							 " WHERE cp.ID_CPC = cpi.ID_CPC   "+
							   " AND p.ID_PRODUTO = cpi.ID_PRODUTO   "+
							   " AND u.ID_UNIDADE = cpi.ID_UNIDADE_CPR   "+
							   " AND cp.ID_PESSOA_EMP = e.ID_PESSOA   "+
							   " AND cp.ID_CPC = :coletorPCFBId "+
							 " GROUP BY cpi.ID_CPC_ITEM, cpi.ID_CPC, cpi.ID_PRODUTO  ";				

			Query q = this.session.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("produtoId", Hibernate.INTEGER)
					.addScalar("produtoCod", Hibernate.STRING)
					.addScalar("produtoDesc", Hibernate.STRING)
					.addScalar("quantidade", Hibernate.DOUBLE)
					.addScalar("qtdLeitura", Hibernate.DOUBLE)
					.addScalar("qtdAvaria", Hibernate.DOUBLE)
					.addScalar("qtdDevolvida", Hibernate.DOUBLE)
					.addScalar("unidadeId", Hibernate.INTEGER)
					.addScalar("unidadeDesc", Hibernate.STRING)
					.addScalar("dtConfIni", Hibernate.TIMESTAMP)
					.addScalar("codLote", Hibernate.STRING)
					.addScalar("dtVencLot", Hibernate.TIMESTAMP)
					.addScalar("controlaLote", Hibernate.INTEGER)
					.addScalar("shelfLife", Hibernate.INTEGER)
					.addScalar("percAceitaShelfLife", Hibernate.DOUBLE)
					.addScalar("localidadeId", Hibernate.INTEGER)
					.addScalar("qtdDecimal", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(ColetorPCItemFB.class));
			
					q.setParameter("coletorPCFBId", coletorPCFBId);
			
			return q.list();
	}
	
	@Override
	public List<ColetorPCItemFB> listarLotes(Integer coletorPCFBId, Integer produtoId) {
		String sql =	" SELECT CPI.ID_CPC_ITEM AS id,  "+
			      " cpi.ID_CPC AS coletorId,  "+
			      " cpi.ID_PRODUTO AS produtoId,   "+
			      " cpc.CODLOTE codLote, "+
			      " cpc.DTVENCLOTE dtVencLot, "+
			      " sum(COALESCE(cpc.QTD_CONFERIDA,0)) AS qtdLeitura, "+
			      " sum(COALESCE(cpc.QTD_AVARIA,0)) AS qtdAvaria, "+
			      " sum(COALESCE(cpc.QTD_DEVOLVIDA,0)) AS qtdDevolvida "+
			 " FROM COLETOR_PC cp ,  "+
			       " COLETOR_PC_ITEM cpi "+
			       " INNER JOIN COLETOR_PC_CONTAGEM cpc ON (cpi.ID_CPC = cpc.ID_CPC AND cpi.ID_PRODUTO = cpc.ID_PRODUTO AND cpc.EXCLUIDO = 0),   "+
			       " produto p,  "+
			       " UNIDADE u,  "+
			       " EMPRESA e   "+
			 " WHERE cp.ID_CPC = cpi.ID_CPC   "+
			   " AND p.ID_PRODUTO = cpi.ID_PRODUTO   "+
			   " AND u.ID_UNIDADE = cpi.ID_UNIDADE_CPR   "+
			   " AND cp.ID_PESSOA_EMP = e.ID_PESSOA   "+
			   " AND cp.ID_CPC = :coletorPCFBId "+
			   " AND cpi.ID_PRODUTO = :produtoId "+
			 " GROUP BY cpi.ID_CPC_ITEM, cpi.ID_CPC, cpi.ID_PRODUTO, cpc.CODLOTE, cpc.DTVENCLOTE   ";				

Query q = this.session.createSQLQuery(sql)
	.addScalar("id", Hibernate.INTEGER)
	.addScalar("produtoId", Hibernate.INTEGER)
	.addScalar("qtdLeitura", Hibernate.DOUBLE)
	.addScalar("qtdAvaria", Hibernate.DOUBLE)
	.addScalar("qtdDevolvida", Hibernate.DOUBLE)
	.addScalar("codLote", Hibernate.STRING)
	.addScalar("dtVencLot", Hibernate.TIMESTAMP)
	.setResultTransformer(Transformers.aliasToBean(ColetorPCItemFB.class));

	q.setParameter("coletorPCFBId", coletorPCFBId);
	q.setParameter("produtoId", produtoId);

return q.list();
	}

	@Override
	public void inserir(ColetorPCItemFB rs)throws RNException {
	
		try {
			Integer coletorPCItemFBID = getSeq();
			String sql = "INSERT INTO COLETOR_PC_ITEM "+
									    " ( "+
									    " ID_CPC_ITEM, "+
									    " ID_CPC, "+
									    " ID_UNIDADE_CPR, "+
									    " ID_PRODUTO, "+
									    " QTD "+
									    " ) "+
									 " VALUES ( "+
									  	" :ID_CPC_ITEM, "+
									  	" :ID_CPC, "+
									  	" :ID_UNIDADE_CPR, "+
									  	" :ID_PRODUTO, "+
									  	" :QTD "+
									 " );";

			Query q = this.session.createSQLQuery(sql);
			q.setParameter("ID_CPC_ITEM", coletorPCItemFBID);
			q.setParameter("ID_CPC", rs.getColetorId());
			q.setParameter("ID_UNIDADE_CPR", rs.getUnidadeId());
			q.setParameter("ID_PRODUTO", rs.getProdutoId());
			q.setParameter("QTD", rs.getQuantidade());
			q.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro na inclusão do Item - Planilha Cega no Trivia ERP.");
		}


	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_COLETOR_PC_ITEM_ID, 1) from rdb$database;";
			Query q = session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do ColetorPC.");
		}
	}

	@Override
	public ColetorPCItemFB carregar(Integer coletorPCFBId, Integer produtoId) {
			
		String sql =	" SELECT CPI.ID_CPC_ITEM AS id, "+
								   " cp.ID_CPC AS coletorId,  "+
								   " p.ID_PRODUTO AS produtoId,  "+
								   " p.CODINTERNO AS produtoCod, "+
								   " p.DESCRICAO AS produtoDesc, "+
								   " cpi.QTD AS quantidade,  "+
								   " u.ID_UNIDADE  AS  unidadeId, "+
								   " u.DESCRESUMIDA  AS unidadeDesc, "+
								   " p.QTDDECIMAL as qtdDecimal "+
							  " FROM COLETOR_PC cp, "+
							       " COLETOR_PC_ITEM cpi, "+
							       " produto p,  "+
							       " UNIDADE u 			 "+
							 " WHERE cp.ID_CPC = cpi.ID_CPC  "+
							   " AND cpi.ID_PRODUTO = p.ID_PRODUTO  "+
							   " AND u.ID_UNIDADE = cpi.ID_UNIDADE_CPR  "+
							   " AND p.ID_PRODUTO = :produtoId  "+
							   " AND cp.ID_CPC = :coletorPCFBId ";
							  

						Query q = this.session.createSQLQuery(sql)
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("coletorId", Hibernate.INTEGER)
						.addScalar("produtoId", Hibernate.INTEGER)
						.addScalar("produtoCod", Hibernate.STRING)
						.addScalar("produtoDesc", Hibernate.STRING)
						.addScalar("quantidade", Hibernate.DOUBLE)
						.addScalar("unidadeId", Hibernate.INTEGER)
						.addScalar("unidadeDesc", Hibernate.STRING)
						.addScalar("qtdDecimal", Hibernate.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(ColetorPCItemFB.class));
						q.setParameter("coletorPCFBId", coletorPCFBId);
						q.setParameter("produtoId", produtoId);
						q.setMaxResults(1);
						return (ColetorPCItemFB) q.uniqueResult();
	}

	@Override
	public void updateQtd(Integer Id, double quantidade) {
				String sql = "UPDATE COLETOR_PC_ITEM SET QTD= :quantidade WHERE ID_CPC_ITEM = :ID_CPC_ITEM ";
				Query query = (Query) session.createSQLQuery(sql.toString());
				query.setParameter("ID_CPC_ITEM", Id); 
				query.setParameter("quantidade", quantidade);
				query.executeUpdate();
	}

	@Override
	public void delete(Integer Id) {
		String sql = "DELETE FROM COLETOR_PC_ITEM WHERE ID_CPC_ITEM = :Id ";
		Query query = session.createSQLQuery(sql);
	      query.setParameter("Id", Id);
	      query.executeUpdate();	
	}

	@Override
	public void excluirToPlanilhaCega(Integer planilhaCegaId) {
		String sql = "delete from COLETOR_PC_ITEM where ID_CPC = :planilhaCegaId";
		Query query = session.createSQLQuery(sql);
	    query.setParameter("planilhaCegaId", planilhaCegaId);
	    query.executeUpdate();	
	}

	@Override
	public List<ColetorPCItemFB> gerarItens(Integer planilhaCegaId) {
		String sql = " select max(pc.ID_CPC) AS coletorId,  "+
					"         nfi.id_produto AS produtoId,  "+
					"         nfi.ID_UNIDADE AS unidadeId,  "+
					"         sum(nfi.QUANTIDADE) AS quantidade  "+
					"    from COLETOR_PC_NFCOMPRA pc,  "+
					" 	    NFCOMPRA nf,  "+
					" 	    NFCOMPRAITEM nfi  "+
					"    where pc.ID_NFCOMPRA = nf.ID_NFCOMPRA   "+
					"      and nf.ID_NFCOMPRA = nfi.ID_NFCOMPRA   "+
					"      and pc.ID_CPC = :planilhaCegaId  "+
					"    group by nfi.id_produto, nfi.ID_UNIDADE  "+
					"    order by 2 ";			
		
		Query q = this.session.createSQLQuery(sql)
			.addScalar("coletorId", Hibernate.INTEGER)
			.addScalar("produtoId", Hibernate.INTEGER)
			.addScalar("quantidade", Hibernate.DOUBLE)
			.addScalar("unidadeId", Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(ColetorPCItemFB.class));
		
		q.setParameter("planilhaCegaId", planilhaCegaId);
		
		return q.list();
	}

	
}
