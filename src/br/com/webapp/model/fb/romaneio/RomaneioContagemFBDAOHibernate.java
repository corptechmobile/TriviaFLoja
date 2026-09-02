package br.com.webapp.model.fb.romaneio;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class RomaneioContagemFBDAOHibernate implements RomaneioContagemFBDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public RomaneioContagemFB carregar(Integer id) {
		return null;
	}
	
	
	public List<RomaneioContagemFB> listar(Integer coletorPlanilhaCegaId, boolean excluido) {
		
		String sql = "SELECT c.CHAVE as chave, "
						  +" c.ID_ROMANEIO as romaneioId, "
						  +" c.ID_USUARIO as usuarioId, "
						  +" u.NOME as usuarioNome, "
						  +" c.ID_PRODUTO as produtoId, "
						  +" p.CODINTERNO as produtoCod, "
						  +" p.DESCRESUMIDA as produtoDesc, "
						  +" un.id_unidade as unidadeId, "
						  +" un.DESCCF as unidadeDesc, "
						  +" c.CODBARRA as codBarra, "
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
					+" WHERE c.ID_ROMANEIO = i.ID_ROMANEIO "
					+"   AND I.ID_PRODUTO = p.ID_PRODUTO "
					+"   AND I.ID_UNIDADE_CPR = un.ID_UNIDADE "
					+"   AND c.ID_USUARIO = u.ID_USUARIO "
					+"   AND c.ID_ROMANEIO = :coletorPlanilhaCegaId "
					+"   AND c.EXCLUIDO = :excluido "
					+" ORDER BY DTLEITURA ";
		
		Query q = this.session.createSQLQuery(sql)
				.addScalar("romaneioId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
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
				.setResultTransformer(Transformers.aliasToBean(RomaneioContagemFB.class));
		
		q.setParameter("coletorPlanilhaCegaId", coletorPlanilhaCegaId);
		q.setParameter("excluido", excluido ? 1 : 0);
		
		return q.list();
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RomaneioContagemFB> listarLeiturasProduto(Integer romaneioId, Integer produtoId) {
			

		String sql =  " SELECT c.CHAVE as chave,  "+
						" 	   c.ID_ROMANEIO as romaneioId,  "+
						" 	   c.ID_USUARIO as usuarioId,  "+
						" 	   max(us.NOME) as usuarioNome,  "+
						" 	   c.ID_PRODUTO as produtoId,  "+
						" 	   max(p.CODINTERNO) as produtoCod,  "+
						" 	   max(p.DESCRESUMIDA) as produtoDesc,  "+
						" 	   max(u.id_unidade) as unidadeId,  "+
						" 	   max(u.DESCCF) as unidadeDesc,  "+
						" 	   max(c.CODBARRA) as codBarra,  "+
						" 	   max(c.QTD) as qtd,  "+
						" 	   max(c.DTLEITURA) as dtLeitura,  "+
						" 	   max(p.QTDDECIMAL) as qtdDecimal  "+
						"  FROM  COLETOR_ROMANEIO_CONTAGEM c,  "+
						"        ROMANEIOITEM i, "+
						"        ROMANEIOITEMPEDIDO rip,   "+
						"        produto p,  "+
						"        ORDEMCARREGITEM oci,   "+
						"        pedvendaitem pvi,  "+
						"        unidade u,  "+
						"        usuario us  "+
						"  WHERE c.ID_ROMANEIO = i.ID_ROMANEIO  "+
						"    AND c.ID_PRODUTO = i.ID_PRODUTO  "+
						"    AND I.ID_PRODUTO = p.ID_PRODUTO  "+
						"    AND c.ID_ROMANEIO = rip.ID_ROMANEIO   "+
						"    AND rip.ID_ORDEMCARREGITEM = oci.ID_ORDEMCARREGITEM   "+
						"    AND oci.ID_PEDVENDAITEM = pvi.ID_PEDVENDAITEM   "+
						"    AND pvi.ID_UNIDADE_VENDASEL = u.ID_UNIDADE  "+
						"    AND pvi.ID_PRODUTO = i.ID_PRODUTO   "+
						"    AND c.ID_USUARIO = us.ID_USUARIO  "+
						"    AND c.ID_ROMANEIO = :romaneioId  "+
						"    AND i.ID_PRODUTO = :produtoId   "+
						"    GROUP BY c.CHAVE,  "+
						" 	   c.ID_ROMANEIO,  "+
						" 	   c.ID_USUARIO,  "+
						" 	   c.ID_PRODUTO  "+
						"  ORDER BY 12 ";
		
			Query q = this.session.createSQLQuery(sql)
				.addScalar("romaneioId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("unidadeId", Hibernate.INTEGER)
				.addScalar("unidadeDesc", Hibernate.STRING)
				.addScalar("qtd", Hibernate.DOUBLE)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("usuarioNome", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RomaneioContagemFB.class));
			
			q.setParameter("romaneioId", romaneioId);
			q.setParameter("produtoId", produtoId);
			
			return q.list();		
	}

	@Override
	public void excluirLeitura(RomaneioContagemFB romaneioContagemFB) throws DAOException {
		try {
			
			String sql = "delete from COLETOR_ROMANEIO_CONTAGEM WHERE chave = :chave";
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("chave", romaneioContagemFB.getChave());
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	public void excluirTodasLeituras(Integer romaneioId, Integer produtoId) throws DAOException {
		try {
			String sql = " DELETE FROM COLETOR_ROMANEIO_CONTAGEM "+
		                 " WHERE ID_ROMANEIO = :romaneioId "+
					     "   AND ID_PRODUTO = :produtoId "; 
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("romaneioId", romaneioId);
			query.setParameter("produtoId", produtoId);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}
	
	
	
}
