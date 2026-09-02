package br.com.webapp.model.fb.coletorpc.contagem;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.coletor.espelho.EspelhoColetorPlanilhaCegaContagem;
import br.com.coletor.model.ColetorPlanilhaCegaContagem;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilData;

public class ColetorPCFBContagemDAOHibernate implements ColetorPCFBContagemDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public ColetorPCFB carregar(Integer id) {
		return null;
	}
	
	
	public List<ColetorPCFBContagem> listar(Integer coletorPlanilhaCegaId, boolean excluido) {
		
		String sql = "SELECT c.CHAVE as chave, "
						  +" c.ID_CPC as coletorId, "
						  +" c.ID_USUARIO as usuarioId, "
						  +" u.NOME as usuarioNome, "
						  +" c.ID_PRODUTO as produtoId, "
						  +" p.CODINTERNO as produtoCod, "
						  +" p.DESCRESUMIDA as produtoDesc, "
						  +" un.id_unidade as unidadeId, "
						  +" un.DESCCF as unidadeDesc, "
						  +" c.CODBARRA as codBarras, "
						  +" c.QTD_CONFERIDA as qtdConferida, "
						  +" c.QTD_DEVOLVIDA as qtdDevolvida, "
						  +" c.QTD_AVARIA qtdAvaria, "
						  +" c.CODLOTE as codLote, "
						  +" c.DTVENCLOTE as dtVencLote, "
						  +" c.DTLEITURA as dtLeitura, "
						  +" c.DTERP as dtErp, "
						  +" p.QTDDECIMAL as qtdDecimal "
					+" FROM COLETOR_PC_CONTAGEM c, "
					+"      COLETOR_PC_ITEM i,"
					+"      UNIDADE un,"
					+"      PRODUTO p, "	
					+"      USUARIO u "	  
					+" WHERE c.ID_CPC = i.ID_CPC "
					+"   AND I.ID_PRODUTO = p.ID_PRODUTO "
					+"   AND I.ID_UNIDADE_CPR = un.ID_UNIDADE "
					+"   AND c.ID_USUARIO = u.ID_USUARIO "
					+"   AND c.ID_CPC = :coletorPlanilhaCegaId "
					+"   AND c.EXCLUIDO = :excluido "
					+" ORDER BY DTLEITURA ";
		
		Query q = this.session.createSQLQuery(sql)
				.addScalar("coletorId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("codBarras", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("qtdConferida", Hibernate.DOUBLE)
				.addScalar("qtdDevolvida", Hibernate.DOUBLE)
				.addScalar("qtdAvaria", Hibernate.DOUBLE)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.TIMESTAMP)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("usuarioNome", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFBContagem.class));
		
		q.setParameter("coletorPlanilhaCegaId", coletorPlanilhaCegaId);
		q.setParameter("excluido", excluido ? 1 : 0);
		
		return q.list();
		
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
				.append(" CODLOTE, ")
				.append(" DTVENCLOTE, ")
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
				.append(" :codLote, ")
				.append(" :dtVencLote, ")
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
	    query.setParameter("codLote", espelho.getCodLote());
	    query.setParameter("dtVencLote", UtilData.formatarStringParaData(espelho.getDtVencLote(), UtilData.FORMATO_DATA_HORA));
	    query.setParameter("dtLeitura", UtilData.formatarStringParaData(espelho.getDtLeitura(), UtilData.FORMATO_DATA_HORA));
	    query.setParameter("EXCLUIDO", 0);
	    
	    query.executeUpdate();
	    
	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception(String.format(" Erro ao tentar processar contagem com chave %s", espelho.getChave()));
	}
	
} 

	@Override
	public void update(ColetorPCFBContagem coletorPCFBContagem) throws DAOException {
	}

	@Override
	public Integer insert(EspelhoColetorPlanilhaCegaContagem espelho) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColetorPCFBContagem> listarLeiturasProduto(Integer coletorPlanilhaCegaId, Integer produtoId) {
			
		
			String sql = "SELECT c.CHAVE as chave, "
							  +" c.ID_CPC as coletorId, "
							  +" c.ID_USUARIO as usuarioId, "
							  +" u.NOME as usuarioNome, "
							  +" c.ID_PRODUTO as produtoId, "
							  +" p.CODINTERNO as produtoCod, "
							  +" p.DESCRESUMIDA as produtoDesc, "
							  +" un.id_unidade as unidadeId, "
							  +" un.DESCCF as unidadeDesc, "
							  +" c.CODBARRA as codBarras, "
							  +" c.QTD_CONFERIDA as qtdConferida, "
							  +" c.QTD_DEVOLVIDA as qtdDevolvida, "
							  +" c.QTD_AVARIA qtdAvaria, "
							  +" c.CODLOTE as codLote, "
							  +" c.DTVENCLOTE as dtVencLote, "
							  +" c.DTLEITURA as dtLeitura, "
							  +" c.DTERP as dtErp, "
							  +" p.QTDDECIMAL as qtdDecimal "
						+" FROM COLETOR_PC_CONTAGEM c, "
						+"      COLETOR_PC_ITEM i,"
						+"      UNIDADE un,"
						+"      PRODUTO p, "	
						+"      USUARIO u "	  
						+" WHERE c.ID_CPC = i.ID_CPC "
						+"   AND c.ID_PRODUTO = i.ID_PRODUTO "
						+"   AND I.ID_PRODUTO = p.ID_PRODUTO "
						+"   AND I.ID_UNIDADE_CPR = un.ID_UNIDADE "
						+"   AND c.ID_USUARIO = u.ID_USUARIO "
						+"   AND c.ID_CPC = :coletorPlanilhaCegaId "
						+"   AND i.ID_PRODUTO = :produtoId " 
						+"   AND c.EXCLUIDO = :excluido "
						+" ORDER BY DTLEITURA ";
	
			Query q = this.session.createSQLQuery(sql)
				.addScalar("coletorId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("codBarras", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("qtdConferida", Hibernate.DOUBLE)
				.addScalar("qtdDevolvida", Hibernate.DOUBLE)
				.addScalar("qtdAvaria", Hibernate.DOUBLE)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.TIMESTAMP)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("usuarioNome", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ColetorPCFBContagem.class));
			
			q.setParameter("coletorPlanilhaCegaId", coletorPlanilhaCegaId);
			q.setParameter("produtoId", produtoId);
			q.setParameter("excluido", 0);
			
			return q.list();		
	}

	@Override
	public void excluirLeitura(ColetorPCFBContagem coletorPCFBContagem) throws DAOException {
		try {
			String sql = "UPDATE COLETOR_PC_CONTAGEM SET EXCLUIDO = :statusExcluido WHERE chave = :chave ";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("chave", coletorPCFBContagem.getChave());
			query.setParameter("statusExcluido", 1);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@Override
	public void excluirTodasLeituras(Integer coletorId) throws DAOException {
		try {
			String sql = "UPDATE COLETOR_PC_CONTAGEM SET EXCLUIDO = :statusExcluido WHERE ID_CPC = :coletorId ";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("coletorId", coletorId);
			query.setParameter("statusExcluido", 1);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
}
