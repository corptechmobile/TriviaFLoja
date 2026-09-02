
package br.com.webapp.model.fb.coletorcontagem;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;

public class ColetorInvContagemFBDAOHibernate implements ColetorInvContagemFBDAO{

	private StringBuilder COLLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ColetorInvContagemFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_COLETOR_INV_CONTAGEM as id, ")
			    .append(" a.ID_COLETOR_INV as coletorInvId, ")
				.append(" a.ID_USUARIO as usuarioId, ")
				.append(" a.ID_PRODUTO as produtoId, ")
				.append(" p.descricao as produtoDesc, ")
				.append(" p.codinterno as produtoCod, ")
				.append(" a.codBarra, ")
				.append(" a.CHAVE, ")
				.append(" u.nome as usuarioDesc, ")
				.append(" a.DTLEITURA as dtLeitura, ")
				.append(" a.DTERP as dtErp, ")
				.append(" a.qtdUn, ")
				.append(" a.qtdEmb, ")
				.append(" a.qtdEmbFechVenda, ")
				.append("(coalesce(a.qtdUn,0)+(coalesce(a.qtdEmb,0)*coalesce(a.qtdEmbFechVenda,0))) as qtdConv, ")				
				.append(" a.descEmbFechVenda ");

	}
	
	@Override
	public ColetorInvContagemFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM coletor_inv_contagem a, ")
		   .append("      produto p, ")
		   .append("      usuario u ")
		   .append(" WHERE a.ID_PRODUTO = P.ID_PRODUTO ")
		   .append("   AND a.ID_USUARIO = u.ID_USUARIO ")
		   .append("   AND a.ID_COLETOR_INV_CONTAGEM = :id ")
		   .append(" ORDER BY 5");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("coletorInvId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("qtdUn", Hibernate.DOUBLE)
				.addScalar("qtdEmb", Hibernate.DOUBLE)
				.addScalar("qtdConv", Hibernate.DOUBLE)
				.addScalar("qtdEmbFechVenda", Hibernate.DOUBLE)
				.addScalar("descEmbFechVenda", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("dtErp", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (ColetorInvContagemFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvContagemFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM coletor_inv_contagem a, ")
		   .append("      produto p, ")
		   .append("      usuario u ")
		   .append(" WHERE a.ID_PRODUTO = P.ID_PRODUTO ")
		   .append("   AND a.ID_USUARIO = u.ID_USUARIO ")
		   .append(" ORDER BY 5");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("coletorInvId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("qtdUn", Hibernate.DOUBLE)
				.addScalar("qtdEmb", Hibernate.DOUBLE)
				.addScalar("qtdConv", Hibernate.DOUBLE)				
				.addScalar("qtdEmbFechVenda", Hibernate.DOUBLE)
				.addScalar("descEmbFechVenda", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("dtErp", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFB.class));
		return query.list();
	}

	@Override
	public ColetorInvContagemFB salvar(ColetorInvContagemFB inventario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insert(ColetorInvContagemFB inventario) throws DAOException {
		try {
			
			Integer inventarioId = getSeq();
			System.out.println("[ColetorInvFBDAOHibernate][insert][id]" + inventarioId);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO COLETOR_INV (ID_COLETOR_INV, ID_PESSOA_EMP, ID_USUARIO, DESCRICAO, DTCRIACAO, STATUS) ")
			.append("VALUES (:ID_COLETOR_INV, ")
			        .append(":ID_PESSOA_EMP, ")
			        .append(":ID_USUARIO, ")
			        .append(":DESCRICAO, ")
			        .append(":DTCRIACAO, ")
			        .append(":STATUS) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventarioId);
//			query.setParameter("ID_PESSOA_EMP", inventario.getEmpresaId());
	        query.setParameter("ID_USUARIO", inventario.getUsuarioId());
//	        query.setParameter("DESCRICAO", inventario.getDescricao());
//	        query.setParameter("DTCRIACAO", inventario.getDtCriacao());
//	        query.setParameter("STATUS", inventario.getStatus());
	        
			query.executeUpdate();
	        
			return inventarioId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
			
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_COLETORINV_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do ColetorInvFB.");
		}	}

	@Override
	public void update(ColetorInvContagemFB inventario) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][update][id]" + inventario.getId());
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_INV SET ")
					        .append("DESCRICAO = :DESCRICAO, ")
					        .append("STATUS = :STATUS, ")
					        .append("DTINICIO = :DTINICIO, ")
					        .append("DTTERMINO = :DTTERMINO ")
					      .append(" WHERE ID_COLETOR_INV = :ID_COLETOR_INV");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventario.getId());
//			query.setParameter("DESCRICAO", inventario.getDescricao());
//			query.setParameter("STATUS", inventario.getStatus());
//	        query.setParameter("DTINICIO", inventario.getDtInicio());
//	        query.setParameter("DTTERMINO", inventario.getDtTermino());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(Integer contagemId) throws DAOException {
		try {
			
			System.out.println("[ColetorInvContagemFBDAOHibernate][excluir][id]" + contagemId);

			
			String sql = "UPDATE COLETOR_INV_CONTAGEM SET EXCLUIDO = :EXCLUIDO "+
			             " WHERE ID_COLETOR_INV_CONTAGEM = :ID ";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID", contagemId);
			query.setParameter("EXCLUIDO", ColetorInvContagemFB.EXCLUIDO);
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		
	}
	
	@Override
	public void excluir(Integer inventarioId, Integer produtoId, String codBarra, String agrupadoPorFilter) throws DAOException {
		try {
			String varWhere = "";
			
			if("produto".equals(agrupadoPorFilter)) {
				varWhere = " AND ID_PRODUTO = :ID_PRODUTO ";
			}else{
				varWhere = " AND CODBARRA = :CODBARRA ";
			}
			
			
			System.out.println("[ColetorInvContagemFBDAOHibernate][excluir][id]" + inventarioId);
			System.out.println("[ColetorInvContagemFBDAOHibernate][excluir][produtoId]" + produtoId);	
			System.out.println("[ColetorInvContagemFBDAOHibernate][excluir][codBarra]" + codBarra);	
			
			String sql = " UPDATE COLETOR_INV_CONTAGEM SET EXCLUIDO = :EXCLUIDO "+
			             " WHERE ID_COLETOR_INV = :ID_COLETOR_INV "+
					     " "+varWhere+" ";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventarioId);
			query.setParameter("EXCLUIDO", ColetorInvContagemFB.EXCLUIDO);
			
			if("produto".equals(agrupadoPorFilter)) {
				query.setParameter("ID_PRODUTO", produtoId);
			}else {
				query.setParameter("CODBARRA", codBarra);
			}	

			
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}		

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvContagemFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter) {
		String sql = "";
		String varWhere = "";
		
		if(empresaFilter!=null) {
			varWhere += " AND a.ID_PESSOA_EMP = :empresaId ";
		}
		
		if(data1Filter != null && data2Filter != null) {
			varWhere += " AND a.DTCRIACAO BETWEEN :dt1 AND :dt2 ";
		}

		if(concluidoFilter) {
			varWhere += " AND a.STATUS = :status ";
		}else {
			varWhere += " AND a.STATUS <> :status ";
		}

		sql = " SELECT "+
				" "+COLLUMNS.toString()+" "+	
			   "  FROM coletor_inv a, "+
			   "       pessoa p, "+
			   "       usuario u "+
			   " WHERE a.ID_PESSOA_EMP = P.ID_PESSOA "+
			   "   AND a.ID_USUARIO = u.ID_USUARIO "+
			   "    AND a.excluido <> :excluido "+
			   " "+varWhere+" "+
			   " ORDER BY a.DTCRIACAO desc";
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("coletorInvId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("qtdUn", Hibernate.DOUBLE)
				.addScalar("qtdEmb", Hibernate.DOUBLE)
				.addScalar("qtdEmbFechVenda", Hibernate.DOUBLE)
				.addScalar("descEmbFechVenda", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("dtErp", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFB.class));
		
		
		if(empresaFilter!=null) {
			query.setParameter("empresaId", empresaFilter.getId());
		}
		
		if(data1Filter != null && data2Filter != null) {
			query.setParameter("dt1", data1Filter);
			query.setParameter("dt2", data2Filter);
		}
		
		

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvContagemFBDTO> listarProdutoEmbalagem(Integer coletorInvId, String produtoFilter, String usuarioFilter, boolean divergenciaFilter, String agrupadoPorFilter) {
		String sql = "";
		String varWhere = "";
		String varWhereTab = "";
		String varGroup = "";
		String varCampo = "";
		String varCampoCount = "";
		
		if(produtoFilter != null && !"".equals(produtoFilter)) {
			varWhereTab += " AND (tab.produtoCod = :produtoFilter or UPPER(tab.produtoDesc) like :produtoFilterLike) ";
		}
		
		if(usuarioFilter != null && !"".equals(usuarioFilter)) {
			varWhere += " AND (UPPER(u.nome) like :usuarioFilter)";
		}

		if(divergenciaFilter) {
			varWhereTab += " AND (tab.qtdDivergencia > 1 or tab.qtdDivEmb > 1 or tab.qtdDivDescEmb > 1 or tab.produtoId is null)";
		}
		
		if("produto".equals(agrupadoPorFilter)) {
			varCampo = " coalesce(p.codinterno,a.codbarra) ";
			varCampoCount = "a.codbarra";
			varGroup = " coalesce(a.id_produto,a.codbarra) ";
		}else {
			varCampo = " a.codbarra ";
			varCampoCount = "a.id_produto";
			varGroup = " a.codbarra ";
		}

		sql = "  SELECT  tab.codigo, "+
				"         max(tab.produtoId) as produtoId, "+
				"         max(tab.produtoDesc) as produtoDesc, "+
				"         max(tab.produtoCod) as produtoCod, "+
				"         max(tab.coletorInvId) as coletorInvId, "+
				"         max(tab.qtdDivergencia) as qtdDivergencia, "+
				"         max(tab.qtdDivEmb) as qtdDivEmb, "+
				"         max(tab.qtdDivDescEmb) qtdDivDescEmb, "+
				"         sum(tab.qtdUn) as qtdUn, "+
				"         sum(tab.qtdEmb) as qtdEmb, "+
				"         max(tab.qtdEmbFechVenda) as qtdEmbFechVenda, "+
				"         max(tab.descEmbFechVenda) as descEmbFechVenda, "+
				"         max(tab.custoMedio) as custoMedio, "+
				"         sum(tab.qtdConv) as qtdConv "+
				"  FROM ( "+
		        "         SELECT  "+varGroup+" as codigo, "+
				"         max(a.id_produto) as produtoId, "+
				"         max(coalesce(p.descresumida,a.produtonovodesc)) as produtoDesc, "+
				"         max("+varCampo+") as produtoCod, "+
				"         max(a.id_coletor_inv) as coletorInvId, "+
				"         count(distinct("+varCampoCount+")) as qtdDivergencia, "+
				"         COUNT(distinct(a.qtdembfechvenda)) qtdDivEmb, "+
				"         COUNT(distinct(a.descembfechvenda)) qtdDivDescEmb, "+
				"         sum(a.qtdUn) as qtdUn, "+
				"         sum(a.qtdEmb) as qtdEmb, "+
				"         max(a.qtdEmbFechVenda) as qtdEmbFechVenda, "+
				"         max(a.descEmbFechVenda) as descEmbFechVenda, "+
				"         max(ig.custogeratualuv) as custoMedio, "+
				"         sum(coalesce(a.qtdUn,0)+(coalesce(a.qtdEmb,0)*coalesce(a.qtdEmbFechVenda,0))) as qtdConv "+
				"  FROM coletor_inv_contagem a "+
				"       LEFT JOIN produto p ON (a.ID_PRODUTO = P.ID_PRODUTO) "+
				"		LEFT JOIN ( "+
				"	            SELECT "+
				"	                ID_PRODUTO, "+
				"	                MAX(DATAULTCOMPRA) as max_data "+
				"	            FROM "+
				"	                infoger_produto "+
				"	            GROUP BY "+
				"	                ID_PRODUTO "+
				"	        ) AS latest_ig ON (p.ID_PRODUTO = latest_ig.ID_PRODUTO) "+
				"	        LEFT JOIN infoger_produto ig ON (latest_ig.ID_PRODUTO = ig.ID_PRODUTO AND latest_ig.max_data = ig.DATAULTCOMPRA) "+
				"	        INNER JOIN usuario u ON ( a.ID_USUARIO = u.ID_USUARIO) "+
				"  WHERE a.id_coletor_inv = :coletorInvId "+
				"    AND a.excluido <> :excluido "+
				"  "+varWhere+" "+
				" group by "+varGroup+") as tab "+
				" WHERE 1 = 1 "+varWhereTab+" "+
				" group by tab.codigo ";
				
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("coletorInvId", Hibernate.INTEGER)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("codigo", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("qtdDivergencia", Hibernate.INTEGER)
				.addScalar("qtdDivEmb", Hibernate.INTEGER)
				.addScalar("qtdDivDescEmb", Hibernate.INTEGER)
				.addScalar("qtdUn", Hibernate.DOUBLE)
				.addScalar("qtdEmb", Hibernate.DOUBLE)
				.addScalar("qtdEmbFechVenda", Hibernate.DOUBLE)
				.addScalar("custoMedio", Hibernate.DOUBLE)
				.addScalar("qtdConv", Hibernate.DOUBLE)
				.addScalar("descEmbFechVenda", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFBDTO.class));
		
			query.setParameter("coletorInvId", coletorInvId);
			query.setParameter("excluido", ColetorInvContagemFB.EXCLUIDO);
			
			if(produtoFilter != null && !"".equals(produtoFilter)) {
				query.setParameter("produtoFilter", produtoFilter.toUpperCase());
				query.setParameter("produtoFilterLike", "%" + produtoFilter.toUpperCase() + "%");
			}
			
			if(usuarioFilter != null && !"".equals(usuarioFilter)) {
				query.setParameter("usuarioFilter", "%" + usuarioFilter.toUpperCase() + "%");
			}
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvContagemFB> listar(Integer coletorInvId, ColetorInvContagemFBDTO leitura, String descEmbFechVenda, String agrupadoPorFilter) {
		String sql = "";
		String varWhere = "";
		
		if("produto".equals(agrupadoPorFilter)) {
			if(leitura.getProdutoId()==null) {
				varWhere = "   AND (a.codbarra = :produtoCod) ";
			}else {
				varWhere = " and a.id_produto = :produtoCod ";
			}	
		}else {
			varWhere = " and a.codbarra = :produtoCod ";
		}
		
		
		sql = " SELECT "+
				" "+COLLUMNS.toString()+" "+	
			   "  FROM coletor_inv_contagem a "+
			   "       LEFT JOIN produto p ON (a.ID_PRODUTO = P.ID_PRODUTO), "+
			   "       usuario u "+
			   " WHERE a.ID_USUARIO = u.ID_USUARIO "+
			   "   AND a.ID_COLETOR_INV = :coletorInvId "+
			   "   "+varWhere+" "+
				"  AND a.excluido <> :excluido "+			   
			   " ORDER BY a.DTLEITURA desc";
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("coletorInvId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("chave", Hibernate.STRING)
				.addScalar("qtdUn", Hibernate.DOUBLE)
				.addScalar("qtdEmb", Hibernate.DOUBLE)
				.addScalar("qtdConv", Hibernate.DOUBLE)				
				.addScalar("qtdEmbFechVenda", Hibernate.DOUBLE)
				.addScalar("descEmbFechVenda", Hibernate.STRING)
				.addScalar("codBarra", Hibernate.STRING)
				.addScalar("dtLeitura", Hibernate.TIMESTAMP)
				.addScalar("dtErp", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFB.class));
		
		
			query.setParameter("coletorInvId", coletorInvId);
			query.setParameter("produtoCod", leitura.getCodigo());
			//query.setParameter("descEmbFechVenda", descEmbFechVenda);
			query.setParameter("excluido", ColetorInvContagemFB.EXCLUIDO);

		return query.list();
	}

	@Override
	public void atualizarProduto(Integer coletorInvId, Integer produtoId, String codBarra) throws DAOException {
	try {
			
			System.out.println("[ColetorInvContagemFBDAOHibernate][update][codbarra]" + codBarra);
			System.out.println("[ColetorInvContagemFBDAOHibernate][update][produto]" + produtoId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_INV_CONTAGEM SET ")
					        .append("ID_PRODUTO = :produtoId ")
					      .append(" WHERE codbarra = :codBarra")
					      .append("   AND ID_COLETOR_INV = :coletorInvId ")
					      .append("   AND ID_PRODUTO IS NULL ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("produtoId", produtoId);
			query.setParameter("coletorInvId", coletorInvId);
			query.setParameter("codBarra", codBarra);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvContagemFBDTO> verificarDivergencias(Integer coletorInvId) {
		String sql = "";
		
		sql =   " select coalesce(max(tab2.qtdDivEmb),0) as qtdDivEmb, "+
						"        coalesce(max(tab2.qtdDivDescEmb),0) as qtdDivDescEmb, "+
						"        coalesce(max(tab2.qtdDivCodBarra),0) as qtdDivCodBarra, "+
						"        coalesce(max(tab2.qtdDivProduto),0) as qtdDivProduto "+
						" from ( "+
						" select coalesce(max(tab.qtdDivEmb),0) as qtdDivEmb, "+
						"        coalesce(max(tab.qtdDivDescEmb),0) as qtdDivDescEmb, "+
						"        coalesce(max(tab.qtdDivCodBarra),0) as qtdDivCodBarra, "+
						"        coalesce(max(tab.qtdDivProduto),0) as qtdDivProduto "+
						" from ( "+
						        " select coalesce(a.id_produto,a.codbarra) as codigo, "+
						"                COUNT(distinct(a.qtdembfechvenda)) as qtdDivEmb, "+
						"                COUNT(distinct(a.descembfechvenda)) as qtdDivDescEmb, "+
						"                count(distinct(a.codbarra)) as qtdDivCodBarra, "+
						"                0 as qtdDivProduto "+
						"          FROM coletor_inv_contagem a "+
						"          where a.id_coletor_inv = :coletorInvId "+
						"            and a.excluido <> :excluido "+
						"            and a.flagzerar <> :flagzerar "+
						"         group by coalesce(a.id_produto,a.codbarra) "+
						" ) as tab "+
						" where tab.qtdDivEmb>1 "+
						"    or tab.qtdDivDescEmb > 1 "+
						"    or tab.qtdDivCodBarra > 1 "+
						" union "+
						" select coalesce(max(tab.qtdDivEmb),0) as qtdDivEmb, "+
						"        coalesce(max(tab.qtdDivDescEmb),0) as qtdDivDescEmb, "+
						"        coalesce(max(tab.qtdDivCodBarra),0) as qtdDivCodBarra, "+
						"        coalesce(max(tab.qtdDivProduto),0) as qtdDivProduto "+
						" from ( "+
						        " select a.codbarra, "+
						"                COUNT(distinct(a.qtdembfechvenda)) qtdDivEmb, "+
						"                COUNT(distinct(a.descembfechvenda)) qtdDivDescEmb, "+
						"                0 as qtdDivCodBarra, "+
						"                count(distinct a.id_produto) qtdDivProduto "+
						"          FROM coletor_inv_contagem a "+
						"          where a.id_coletor_inv = :coletorInvId "+
						"            and a.excluido <> :excluido "+
						"            and a.flagzerar <> :flagzerar "+
						"         group by a.codbarra "+
						" ) as tab "+
						" where tab.qtdDivEmb>1 or tab.qtdDivDescEmb > 1 or qtdDivProduto > 1 "+
						" ) as tab2 ";				
				
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("qtdDivCodBarra", Hibernate.INTEGER)
				.addScalar("qtdDivEmb", Hibernate.INTEGER)
				.addScalar("qtdDivDescEmb", Hibernate.INTEGER)
				.addScalar("qtdDivProduto", Hibernate.INTEGER)				
				.setResultTransformer(Transformers.aliasToBean(ColetorInvContagemFBDTO.class));
		
			query.setParameter("coletorInvId", coletorInvId);
			query.setParameter("excluido", ColetorInvContagemFB.EXCLUIDO);
			query.setParameter("flagzerar", ColetorInvContagemFB.ZERAR_CONTAGENS);
			
		return query.list();	
	}


}
