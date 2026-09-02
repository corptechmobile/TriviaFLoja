package br.com.webapp.model.fb.vendasproduto.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class VendasProdutoDTODAOHibernate implements VendasProdutoDTODAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendasProdutoDTO> listarAutoServico(EmpresaFB empresaFB, VendedorFB vendedorFB, Date dataFilter1, Date dataFilter2, Integer id, String porFilter) {
		
		String varFrom = "";
		String varCollumn = "";
		String varWhere = "";
		String varWhereDev = "";
		if(porFilter.equals("fornecedor")) {
			
			varFrom = " produto pr, unidade u, pessoa p, ";
			
			varCollumn = " pr.id_produto AS produtoId, MAX(p.nomefantmnem) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(ecfi.preco) preco, ";
			
			varWhere = " AND pr.id_pessoa_forn = p.id_pessoa AND pr.id_pessoa_forn = :id  "; 
			
			varWhereDev = " AND bi.id_produto = pr.id_produto " + 
				   	  	  " AND pr.id_pessoa_forn = p.id_pessoa " +
				   	  	  " AND pr.id_pessoa_forn = :id "; 
			
		}else if(porFilter.equals("linhaProduto")) {
			
			varCollumn = " pr.id_produto AS produtoId, MAX(lp.descricao) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(ecfi.preco) preco, ";
			
			varFrom = " produto pr, unidade u, linhaproduto lp, linhaproduto dep, ";
			
			varWhere = " AND dep.id_linhaproduto = :id " + 
					   " AND lp.id_linhaproduto = pr.id_linhaproduto " + 
					   " AND lp.codedt like dep.codedt || '%'";
			
			varWhereDev = " AND dep.id_linhaproduto = :id " + 
					      " AND lp.id_linhaproduto = pr.id_linhaproduto " + 
					      " AND lp.codedt like dep.codedt || '%'" + 
					      " AND bi.id_produto = pr.id_produto ";
		}else if(porFilter.equals("tipovend")) {
			
			varCollumn = " pr.id_produto AS produtoId, MAX(tv.descricao) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(ecfi.preco) preco, ";

			varFrom = " produto pr, unidade u, vendedor v, tipovendedor tv, ";
			
			varWhere = " AND ecf.id_pessoa_vend = V.id_pessoa "+
					   " AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				       " AND tv.ID_TIPOVENDEDOR = :id  "; 
			
			varWhereDev = " AND b.id_pessoa_vend = V.id_pessoa " + 
					      " AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				          " AND tv.ID_TIPOVENDEDOR = :id  "; 
			
		}else {
			varFrom = " produto pr, unidade u, ";
			varCollumn = " pr.id_produto AS produtoId, 'Sem Vendedor' AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(ecfi.preco) preco, ";
			if(id.equals(-1)) {
				varWhere = "";
				varWhereDev = " AND bi.id_produto = pr.id_produto ";
			}else {
				varWhere = " AND ecf.id_pessoa_vend = -1 ";
				varWhereDev = " AND ecf.id_pessoa_vend = -1 "+ 
							  " AND bi.id_produto = pr.id_produto ";				
			}
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tab.produtoId AS produtoId, ")
				 .append(" MAX(tab.descricao) AS descricao, ")
				 .append(" MAX(tab.produtoCod) AS produtoCod, ")
				 .append(" MAX(tab.produto) AS produto, ")
				 .append(" SUM(tab.qtdeAuto) AS qtdeAuto, ")
				 .append(" MAX(tab.un) AS un, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 and SUM(tab.qtdeAuto) > 0 then (SUM(tab.valor) / SUM(tab.qtdeAuto)) else 0.00 end AS preco, ")
				 .append(" SUM(tab.valor) AS valor, ")
				 .append(" SUM(tab.vlDevolvido) AS vlDevolvido, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto ")
			.append(" FROM ( ");
		
				sql.append("SELECT ").append(varCollumn)
							.append(" SUM(ecfi.quantidade - ecfi.qtdpedido) AS qtdeAuto, ") 
							.append(" 0 AS qtdeAutoDev, ") 
							.append(" SUM(TRUNC(((ecfi.quantidade - ecfi.qtdpedido) * ecfi.preco), 2)) AS valor, ") 
							.append(" 0 AS vlDevolvido, ")
							.append(" CAST(SUM(ecfi.quantidade * ecfi.preco * ecfi.percdesconto) / SUM(ecfi.quantidade * ecfi.preco) AS numeric(18,2)) AS desconto ")
						.append(" FROM ").append(varFrom)
							 .append(" ecf_vendas ecf, ") 
							 .append(" ecf_vendasitem ecfi ") 
						.append(" WHERE ecf.id_ecfvendas = ecfi.id_ecfvendas ")
						  .append(" AND ecfi.id_produto = pr.id_produto ")
						  .append(" AND pr.id_unidade_venda = u.id_unidade ") 
						  .append(" AND ecf.datavenda between :dt1 and :dt2 ")
						  .append(" AND (ecfi.quantidade - ecfi.qtdpedido) > 0 ") 
						  .append(" AND ecf.concluida = 1 ")
						  .append(" AND ecf.cancelada = 0 ")
						  .append(" AND ecfi.cancelada = 0 ")
						  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp,ecf.id_pessoa_emp) ")
						  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
						  .append(varWhere)
						  .append(" GROUP BY 1 ");
				
				sql.append("UNION ALL ");
		
				sql.append("SELECT ").append(varCollumn)
						 .append(" 0 AS qtdeAuto, ") 
						 .append(" SUM(ecfi.quantidade - ecfi.qtdpedido) AS qtdeAutoDev, ") 
					     .append(" 0 AS valor, ") 
					     .append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ")
					     .append(" 0 AS desconto ")
					  .append(" FROM boletimdevolucao b, ").append(varFrom)
					       .append(" boletimdevolitem bi, ")
					  	   .append(" ecf_vendas ecf, ")
					  	   .append(" ecf_vendasitem ecfi ")
					  .append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ")
					    .append(" AND b.momento BETWEEN :dt1 AND :dt2 ")
					    .append(" AND b.id_boletimdevolstatus <> 1 ")
					    .append(" AND bi.id_ecfvendasitem = ecfi.id_ecfvendasitem ")
					    .append(" AND pr.id_unidade_venda = u.id_unidade ") 
					    .append(" AND ecf.id_ecfvendas = ecfi.id_ecfvendas ")
						.append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp,ecf.id_pessoa_emp) ")
						.append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
						.append(" AND (ecfi.quantidade - ecfi.qtdpedido) > 0 ").append(varWhereDev) 
						.append(" GROUP BY 1 ");
				  
		sql.append(") tab ")
			.append(" GROUP BY tab.produtoId ")		  
			.append(" ORDER BY 8 desc");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produto", Hibernate.STRING)
				.addScalar("qtdeAuto", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE).setResultTransformer(Transformers.aliasToBean(VendasProdutoDTO.class));
				
				query.setParameter("dt1", dataFilter1);
				query.setParameter("dt2", dataFilter2);
				query.setParameter("id_pessoa_emp", empresaFB.getId());
				if(vendedorFB!=null) {
					query.setParameter("vendedor", vendedorFB.getId());
				}else {
					query.setParameter("vendedor", null);
				}			
				
				
				if(porFilter.equals("fornecedor")) {
					query.setParameter("id", id);
				}else if (porFilter.equals("linhaProduto")) {
					query.setParameter("id", id);
				}
				
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendasProdutoDTO> listarPedido(EmpresaFB empresaFB, VendedorFB vendedorFB, Date dataFilter1, Date dataFilter2, Integer id, String porFilter) {
		
		String varFrom = "";
		String varCollumn = "";
		String varWhere = "";
		String varWhereDev = "";
		if(porFilter.equals("fornecedor")) {
			
			varFrom = " produto pr, unidade u, pessoa p, ";
			
			varCollumn = " pr.id_produto AS produtoId, MAX(p.nomefantmnem) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(pvi.preco) preco, ";
			
			varWhere = " AND pr.id_pessoa_forn = p.id_pessoa " + 
					   " AND pr.id_pessoa_forn = :id  " + 
					   " AND pr.id_unidade_venda = u.id_unidade ";
			
			varWhereDev = " AND bi.id_produto = pr.id_produto " + 
				   	  	  " AND pr.id_pessoa_forn = p.id_pessoa " +
				   	  	  " AND pr.id_pessoa_forn = :id " + 
				   	  	  " AND pr.id_unidade_venda = u.id_unidade ";
			
		}else if(porFilter.equals("linhaProduto")) {
			
			varCollumn = " pr.id_produto AS produtoId, MAX(lp.descricao) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(pvi.preco) preco, ";
			
			varFrom = " produto pr, unidade u, linhaproduto lp, linhaproduto dep, ";
			
			varWhere = " AND dep.id_linhaproduto = :id " + 
					   " AND lp.id_linhaproduto = pr.id_linhaproduto " + 
					   " AND lp.codedt like dep.codedt || '%'" + 
					   " AND pr.id_unidade_venda = u.id_unidade ";
			
			varWhereDev = " AND dep.id_linhaproduto = :id " + 
					      " AND lp.id_linhaproduto = pr.id_linhaproduto " + 
					      " AND lp.codedt like dep.codedt || '%'" + 
					      " AND bi.id_produto = pr.id_produto " +
					      " AND pr.id_unidade_venda = u.id_unidade ";
			
		}else if(porFilter.equals("tipovend")) {
			
			varCollumn = " pr.id_produto AS produtoId, MAX(tv.descricao) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(pvi.preco) preco, ";

			varFrom = " produto pr, unidade u, vendedor v, tipovendedor tv, ";
			
			varWhere = " AND ecf.id_pessoa_vend = V.id_pessoa "+
					   " AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				       " AND tv.ID_TIPOVENDEDOR = :id  "; 
			
			varWhereDev = " AND b.id_pessoa_vend = V.id_pessoa " + 
					      " AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				          " AND tv.ID_TIPOVENDEDOR = :id  "; 			
			
		}else {
			
			varFrom = " produto pr, unidade u, pessoa p, ";
			
			varCollumn = " pr.id_produto AS produtoId, MAX(p.nomefantmnem) AS descricao, MAX(pr.codinterno) AS produtoCod, MAX(pr.descricao) produto, MAX(u.desccf) un, AVG(pvi.preco) preco, ";
			
			varWhere = " AND pv.id_pessoa_vend = :id " +
					   " AND pv.id_pessoa_vend = p.id_pessoa ";
			
			varWhereDev = " AND pv.id_pessoa_vend = :id "+ 
						  " AND pv.id_pessoa_vend = p.id_pessoa " + 			  
						  " AND bi.id_produto = pr.id_produto " +
						  " AND pr.id_unidade_venda = u.id_unidade ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tab.produtoId AS produtoId, ")
				 .append(" MAX(tab.descricao) AS descricao, ")
				 .append(" MAX(tab.produtoCod) AS produtoCod, ")
				 .append(" MAX(tab.produto) AS produto, ")
				 .append(" SUM(tab.qtdeAuto) AS qtdeAuto, ")
				 .append(" MAX(tab.un) AS un, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 and SUM(tab.qtdeAuto) > 0 then (SUM(tab.valor) / SUM(tab.qtdeAuto)) else 0.00 end AS preco, ")
				 .append(" SUM(tab.valor) AS valor, ")
				 .append(" SUM(tab.vlDevolvido) AS vlDevolvido, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto ")
			.append(" FROM ( ");
		
				sql.append("SELECT ").append(varCollumn)
							.append(" SUM(pvi.quantidade) AS qtdeAuto, ") 
							.append(" 0 AS qtdeAutoDev, ")
							.append(" SUM(TRUNC(pvi.quantidade * pvi.preco, 2)) AS valor, ") 
							.append(" 0 AS vlDevolvido, ")
							.append(" CAST(SUM(pvi.quantidade * pvi.preco * pvi.percdesconto) / SUM(pvi.quantidade * pvi.preco) AS numeric(18,2)) AS desconto ")
						.append(" FROM ").append(varFrom)
							 .append(" pedvenda pv, ") 
							 .append(" pedvendaitem pvi, ")
						  	 .append(" pedvendastatus pvs, ")
						  	 .append(" tipomovfisc tmf ")
						  .append(" WHERE pv.id_pedvenda = pvi.id_pedvenda ")
						   .append("  AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS ")
						   .append("  AND pv.id_tipomovfisc = tmf.id_tipomovfisc ")
					       .append("  AND pvs.EFETIVADO = 1 ")
					       .append("  AND tmf.classe in (0,1) ")
						   .append("  AND pvi.id_produto = pr.id_produto ")
						   .append("  AND pr.id_unidade_venda = u.id_unidade ") 
						   .append("  AND pv.efetivacao between :dt1 and :dt2 ")
						   .append("  AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp,pv.id_pessoa_emp) ")
						   .append("  AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
						   .append(varWhere)
						  .append(" GROUP BY 1 ");
				sql.append("UNION ALL ");
		
				sql.append("SELECT ").append(varCollumn)
						.append(" 0 AS qtdeAuto, ") 
						.append(" SUM(bi.quantidade) AS qtdeAutoDev, ")
						.append(" 0 AS valor, ")
						.append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ") 
						.append(" 0 AS desconto  ")
					.append("FROM boletimdevolucao b, ").append(varFrom)  
						.append(" boletimdevolitem bi, ")
						.append(" ecf_vendasitem ecfi, ") 
						.append(" ecf_vendas ecf, ")
						.append(" pedvenda pv, ")
						.append(" pedvendaitem pvi, ")
						.append(" pedvendastatus pvs, ")
					  	.append(" tipomovfisc tmf ")
					.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ") 
					  .append(" AND b.id_boletimdevolstatus <> 1 ")
					  .append(" AND b.momento between :dt1 AND :dt2 ")
					  .append(" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp,pv.id_pessoa_emp) ")
					  .append(" AND pvi.id_produto = bi.id_produto ")
					  .append(" AND pvi.id_pedvenda = pv.id_pedvenda ")
				      .append(" AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS ")
					  .append(" AND pv.id_tipomovfisc = tmf.id_tipomovfisc ")
				      .append(" AND pvs.EFETIVADO = 1 ")
				      .append(" AND tmf.classe in (0,1) ")
					  .append(" AND bi.id_ecfvendasitem = ecfi.id_ecfvendasitem ") 
					  .append(" AND ecf.id_ecfvendas = ecfi.id_ecfvendas ")
					  .append(" AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda ")
					  .append(" AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda ")
					  .append("  AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend) ")
					  .append(varWhereDev) 
					.append(" GROUP BY 1 ");
				
				sql.append("UNION ALL ");
				
				sql.append("SELECT ").append(varCollumn)
						.append(" 0 AS qtdeAuto, ") 
						.append(" SUM(bi.quantidade) AS qtdeAutoDev, ")
						.append(" 0 AS valor, ")
						.append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ") 
						.append(" 0 AS desconto  ")
					.append("FROM boletimdevolucao b, ").append(varFrom)  
						.append(" boletimdevolitem bi, ")
						.append(" nfvendaitem nfi, ") 
						.append(" nfvenda nf, ")
						.append(" pedvenda pv, ")
						.append(" pedvendaitem pvi, ")
						.append(" pedvendastatus pvs, ")
					  	.append(" tipomovfisc tmf ")
					.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ") 
					  .append(" AND b.id_boletimdevolstatus <> 1 ")
					  .append(" AND b.momento between :dt1 AND :dt2 ")
					  .append(" AND b.id_pessoa_emp = coalesce(:id_pessoa_emp, b.id_pessoa_emp) ")
					  .append(" AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend) ")
					  .append(" AND pvi.id_produto = bi.id_produto ")
					  .append(" AND bi.id_ecfvendasitem not in (SELECT ei.id_ecfvendasitem FROM ecf_vendasitem ei WHERE ei.id_ecfvendasitem = bi.id_ecfvendasitem) ")
					  .append(" AND pvi.id_pedvenda = pv.id_pedvenda ")
				      .append(" AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS ")
					  .append(" AND pv.id_tipomovfisc = tmf.id_tipomovfisc ")
				      .append(" AND pvs.EFETIVADO = 1 ")
				      .append(" AND tmf.classe in (0,1) ")
					  .append(" AND bi.id_nfvendaitem = nfi.id_nfvendaitem ")
					  .append(" AND nf.id_nfvenda = nfi.id_nfvenda ")
					  .append(" AND nf.id_pedvenda = pv.id_pedvenda ").append(varWhereDev) 
					.append(" GROUP BY 1 ");
				  
		sql.append(") tab ")
			.append(" GROUP BY tab.produtoId ")		  
			.append(" ORDER BY 8 desc");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produto", Hibernate.STRING)
				.addScalar("qtdeAuto", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE).setResultTransformer(Transformers.aliasToBean(VendasProdutoDTO.class));
				
				query.setParameter("dt1", dataFilter1);
				query.setParameter("dt2", dataFilter2);
				query.setParameter("id_pessoa_emp", empresaFB.getId());
				if(vendedorFB!=null) {
					query.setParameter("vendedor", vendedorFB.getId());
				}else {
					query.setParameter("vendedor", null);
				}			
				
				
				query.setParameter("id", id);
				
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendasProdutoDTO> listarAmbos(EmpresaFB empresaFB, VendedorFB vendedorFB, Date dataFilter1, Date dataFilter2, Integer id, String porFilter) {
		
		String varWhere = "";
		
		if(porFilter.equals("fornecedor")) {
			
			varWhere = " AND pr.id_pessoa_forn = :id  "; 
			
		}else if(porFilter.equals("linhaProduto")) {
			
			varWhere = " AND dep.id_linhaproduto = :id "; 
					   
		}else if(porFilter.equals("tipovend")) {
			
			varWhere = " AND tv.ID_TIPOVENDEDOR = :id  "; 
			
		}else {
			
			varWhere = " AND v.id_pessoa = :id ";
			
		}
		
		String sql = " select tabresult.produtoId, "+
				"        tabresult.descricao, "+
				"        tabresult.produtoCod, "+
				"        tabresult.produto, "+
				"        tabresult.qtdeAuto, "+
				"        tabresult.un, "+
				"        tabresult.preco, "+
				"        tabresult.valor, "+
				"        tabresult.vlDevolvido  , "+
				"        CASE WHEN (tabresult.valorbruto) > 0.0 THEN CAST(((tabresult.ValorDesconto) / (tabresult.valorbruto))*100.00 AS NUMERIC(18, "+
				"          2))  "+
				"          ELSE 0.00  "+
				"        END AS desconto "+
				" from ( "+
				"     SELECT "+
				"         tab.produtoId AS produtoId, "+
				"         MAX(tab.descricao) AS descricao, "+
				"         MAX(tab.produtoCod) AS produtoCod, "+
				"         MAX(tab.produto) AS produto, "+
				"         SUM(tab.qtdeAuto) AS qtdeAuto, "+
				"         MAX(tab.un) AS un, "+
				"         CASE "+
				"             WHEN SUM(tab.valor) > 0 "+
				"             AND SUM(tab.qtdeAuto) > 0 THEN (SUM(tab.valor) / SUM(tab.qtdeAuto)) "+
				"             ELSE 0.00 "+
				"         END AS preco, "+
				"         CAST(SUM(tab.valor) as numeric(18,2)) AS valor, "+
				"         CAST(SUM(tab.valorBruto) as numeric(18,2)) AS valorBruto, "+
				"         CAST(SUM(tab.vlDevolvido) as numeric(18,2)) AS vlDevolvido  , "+
				"         CAST(SUM(tab.desconto) as numeric(18,2)) ValorDesconto "+
				"     FROM "+
				"         ( "+
				"         SELECT "+
				"             pr.id_produto AS produtoId, "+
				"             MAX(tv.descricao) AS descricao, "+
				"             MAX(pr.codinterno) AS produtoCod, "+
				"             MAX(pr.descricao) produto, "+
				"             MAX(u.desccf) un, "+
				"             AVG(pvi.preco) preco, "+
				"             SUM(pvi.quantidade) AS qtdeAuto, "+
				"             0 AS qtdeAutoDev, "+
				"             SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "+
				"                             (pvi.quantidade - pvi.qtdsaldoatender)) * PVI.preco AS NUMERIC(18, 2)) ELSE 0 END) AS valor, "+
				"             SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "+
				"                             (pvi.quantidade - pvi.qtdsaldoatender)) * PVI.precoprom AS NUMERIC(18, 2)) ELSE 0 END) AS valorBruto, "+
				"             0 AS vlDevolvido, "+
				"             CAST(SUM( iif(pv.id_pedvendastatus IN (4, 5), "+
				"                   pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.precoprom * pvi.percdesconto/100.00) AS NUMERIC(18,4)) "+
				"                                                                                                                     AS desconto "+
				"         FROM "+
				"             produto pr, "+
				"             linhaproduto lp, "+
				"             linhaproduto dep, "+
				"             unidade u, "+
				"             vendedor v, "+
				"             tipovendedor tv, "+
				"             pedvenda pv, "+
				"             pedvendaitem pvi, "+
				"             tipomovfisc tmf "+
				"         WHERE "+
				"             pv.id_pedvenda = pvi.id_pedvenda "+
				"             AND pvi.id_produto = pr.id_produto "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             AND pr.id_linhaproduto = lp.id_linhaproduto "+
				"   		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
				"             AND pv.id_tipomovfisc = tmf.id_tipomovfisc "+
				"             AND tmf.classe IN (0, 1) "+
				"             AND pv.efetivacao BETWEEN :dt1 AND :dt2 "+
				"             AND pv.id_pedvendastatus IN (4, 5, 6) "+
				"             AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, "+
				"             pv.id_pessoa_emp) "+
				"             AND pv.id_pessoa_vend = COALESCE(:vendedor, "+
				"             pv.id_pessoa_vend) "+
				"             AND pv.ID_PESSOA_VEND = V.ID_PESSOA "+
				"             AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             "+varWhere+" "+
				"         GROUP BY "+
				"             1 "+
				"     UNION ALL "+
				"         SELECT "+
				"             pr.id_produto AS produtoId, "+
				"             MAX(tv.descricao) AS descricao, "+
				"             MAX(pr.codinterno) AS produtoCod, "+
				"             MAX(pr.descricao) produto, "+
				"             MAX(u.desccf) un, "+
				"             AVG(ecfi.preco) preco, "+
				"             SUM(ecfi.quantidade - ecfi.qtdpedido) AS qtdeAuto, "+
				"             0 AS qtdeAutoDev, "+
				"             SUM(TRUNC(((ecfi.quantidade - COALESCE(ecfi.qtdpedido, 0)) * (ecfi.valorliquidoitem / ecfi.quantidade)), 2)) AS valor, "+
				"             SUM(TRUNC(((ecfi.quantidade - COALESCE(ecfi.qtdpedido, 0)) * ecfi.preco), 2)) AS valorBruto, "+
				"             0 AS vlDevolvido, "+
				"             CAST(SUM(ecfi.quantidade * ecfi.preco * ecfi.percdesconto/100.00) AS NUMERIC(18, 4)) AS desconto "+
				"         FROM "+
				"             produto pr, "+
				"             linhaproduto lp, "+
				"             linhaproduto dep, "+
				"             unidade u, "+
				"             vendedor v, "+
				"             tipovendedor tv, "+
				"             ecf_vendas ecf, "+
				"             ecf_vendasitem ecfi "+
				"         WHERE "+
				"             ecf.id_ecfvendas = ecfi.id_ecfvendas "+
				"             AND ecfi.id_produto = pr.id_produto "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             AND pr.id_linhaproduto = lp.id_linhaproduto "+
				"   		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
				"             AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"             AND (ecfi.quantidade - ecfi.qtdpedido) > 0 "+
				"             AND ecf.concluida = 1 "+
				"             AND ecf.cancelada = 0 "+
				"             AND ecfi.cancelada = 0 "+
				"             AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, "+
				"             ecf.id_pessoa_emp) "+
				"             AND ecf.id_pessoa_vend = COALESCE(:vendedor, "+
				"             ecf.id_pessoa_vend) "+
				"             AND ecf.id_pessoa_vend = V.id_pessoa "+
				"             AND V.ID_TIPOVENDEDOR = tv.ID_TIPOVENDEDOR "+
				"             "+varWhere+" "+
				"         GROUP BY "+
				"             1 "+
				"     UNION ALL "+
				"         SELECT "+
				"             pr.id_produto AS produtoId, "+
				"             MAX(tv.descricao) AS descricao, "+
				"             MAX(pr.codinterno) AS produtoCod, "+
				"             MAX(pr.descricao) produto, "+
				"             MAX(u.desccf) un, "+
				"             AVG(bdi.valorunit) preco, "+
				"             0 AS qtdeAuto, "+
				"             SUM(bdi.quantidade) AS qtdeAutoDev, "+
				"             0 AS valor, "+
				"             0 AS valorBruto, "+
				"             ROUND(SUM(bdi.quantidade * bdi.valorunit), 2) AS vlDevolvido, "+
				"             0 AS desconto "+
				"          FROM "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             ecf_vendasitem evi, "+
				"             ecf_vendas ev, "+
				"             PRODUTO PR , "+
				"             unidade u, "+
				"             linhaproduto lp, "+
				"             linhaproduto dep, "+
				"             cliente cl, "+
				"             pessoa p, "+
				"             gestaovendamob gvm, "+
				"             vendedor v, "+
				" 			  pessoa vnd, "+
				"             tipovendedor tv, "+
				"             TIPOMOVFISC tmf "+
				"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
				"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
				"             and evi.id_ecfvendas = ev.id_ecfvendas "+
				"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"   		  and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
				"             AND pr.id_pessoa_forn = p.id_pessoa "+
				"             and cl.id_pessoa = bd.id_pessoa_cli "+
				"             and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
				"             and tmf.CLASSE in (0, 1) "+
				"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
				"             and bd.id_pessoa_vend = v.id_pessoa "+
				"             and v.id_pessoa = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
				"             and bd.id_pessoa_emp = COALESCE(:id_pessoa_emp,bd.id_pessoa_emp) "+
				"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"             "+varWhere+" "+
				"          GROUP BY "+
				"              1 "+
				"     UNION ALL "+
				"         SELECT "+
				"             pr.id_produto AS produtoId, "+
				"             MAX(tv.descricao) AS descricao, "+
				"             MAX(pr.codinterno) AS produtoCod, "+
				"             MAX(pr.descricao) produto, "+
				"             MAX(u.desccf) un, "+
				"             AVG(bdi.valorunit) preco, "+
				"             0 AS qtdeAuto, "+
				"             SUM(bdi.quantidade) AS qtdeAutoDev, "+
				"             0 AS valor, "+
				"             0 AS valorBruto, "+
				"             ROUND(SUM(bdi.quantidade * bdi.valorunit), 2) AS vlDevolvido, "+
				"             0 AS desconto "+
				"          FROM "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             pedvendaitem pvi, "+
				"             pedvenda pv, "+
				"             PRODUTO PR , "+
				"             unidade u, "+
				"             linhaproduto lp, "+
				"             linhaproduto dep, "+
				"             cliente cl, "+
				"             pessoa p, "+
				"             gestaovendamob gvm, "+
				"             vendedor v, "+
				"             pessoa vnd, "+
				"             tipovendedor tv, "+
				"             TIPOMOVFISC tmf "+
				"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
				"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
				"             and pvi.id_pedvenda = pv.id_pedvenda "+
				"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"             and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
				"             AND pr.id_pessoa_forn = p.id_pessoa "+
				"             and cl.id_pessoa = bd.id_pessoa_cli "+
				"             and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
				"             and tmf.CLASSE in (0, 1) "+
				"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
				"             and bd.id_pessoa_vend = v.id_pessoa "+
				"             and v.id_pessoa = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
				"             and bd.id_pessoa_emp = COALESCE(:id_pessoa_emp,bd.id_pessoa_emp) "+
				"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"             "+varWhere+" "+
				"          GROUP BY "+
				"             1 "+
				"     UNION ALL "+
				"         SELECT "+
				"             pr.id_produto AS produtoId, "+
				"             MAX(tv.descricao) AS descricao, "+
				"             MAX(pr.codinterno) AS produtoCod, "+
				"             MAX(pr.descricao) produto, "+
				"             MAX(u.desccf) un, "+
				"             AVG(bdi.valorunit) preco, "+
				"             0 AS qtdeAuto, "+
				"             SUM(bdi.quantidade) AS qtdeAutoDev, "+
				"             0 AS valor, "+
				"             0 AS valorBruto, "+
				"             ROUND(SUM(bdi.quantidade * bdi.valorunit), 2) AS vlDevolvido, "+
				"             0 AS desconto "+
				"          FROM "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             NFVENDAITEM nfi, "+
				"             NFVENDA nf, "+
				"             PRODUTO PR , "+
				"             unidade u, "+
				"             linhaproduto lp, "+
				"             linhaproduto dep, "+
				"             pessoa p, "+
				"             cliente cl, "+
				"             gestaovendamob gvm, "+
				"             vendedor v, "+
				"             pessoa vnd, "+
				"             tipovendedor tv, "+
				"             TIPOMOVFISC tmf "+
				"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
				"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
				"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
				"             and tmf.ID_TIPOMOVFISC = nf.ID_TIPOMOVFISC "+
				"             and tmf.CLASSE in (0, 1) "+
				"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"   		  and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
				"             AND pr.id_pessoa_forn = p.id_pessoa "+
				"             and cl.id_pessoa = bd.id_pessoa_cli "+
				"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
				"             and bd.id_pessoa_vend = v.id_pessoa "+
				"             and v.id_pessoa = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
				"             and bd.id_pessoa_emp = COALESCE(:id_pessoa_emp,bd.id_pessoa_emp) "+
				"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"             "+varWhere+" "+
				"          GROUP BY "+
				"             1 "+
				"             ) tab "+
				"     GROUP BY "+
				"         tab.produtoId "+
				"     ORDER BY "+
				"         8 DESC "+
				"   ) tabresult ";

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produto", Hibernate.STRING)
				.addScalar("qtdeAuto", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE).setResultTransformer(Transformers.aliasToBean(VendasProdutoDTO.class));
				
				query.setParameter("dt1", dataFilter1);
				query.setParameter("dt2", dataFilter2);
				query.setParameter("id_pessoa_emp", empresaFB.getId());
				if(vendedorFB!=null) {
					query.setParameter("vendedor", vendedorFB.getId());
				}else {
					query.setParameter("vendedor", null);
				}			
				
				query.setParameter("id", id);
				
		return query.list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendasProdutoDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, FornecedorFB fornecedorFilter, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter, Integer id) {
		String sql = "";
		String sqlFiltro1 = "";
		String sqlFiltro2 = "";
		String sqlFiltro3 = "";
		String sqlFiltro4 = "";
		String sqlFiltro5 = "";
		String varCampos = "";
		String varWhere = "";
		String varGroup = "";
		
		if(segmentoFilter!=null && !"".equals(segmentoFilter) && !"null".equals(segmentoFilter)) {
			if("atacado".equals(segmentoFilter)){
				         sqlFiltro2 = " and ev.id_ecfvendas = -1 ";
				         sqlFiltro3 = " and ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null)) ";
				         sqlFiltro4 = " and ev.id_ecfvendas = -1  ";
			}else {
				        sqlFiltro1 = " and pv.id_pedvenda = -1 ";
				        sqlFiltro3 = " and bdi.id_ecfvendasitem is not null ";
				        sqlFiltro4 = " and bdi.id_ecfvendasitem is not null ";
				        if("pedido".equals(vendasPorFilter)) {
				          sqlFiltro5 = " and pv.id_pedvenda = -1 ";
				        }
			}	        
		}
		
		if (porFilter.equals("fornecedor")) {
			varWhere = " and pr.id_pessoa_forn = "+id;
			
		}else if (porFilter.equals("vendedor")) {
			varWhere = " and vnd.id_pessoa = "+id;
			
		}else if (porFilter.equals("linhaProduto")) {
			varWhere = " and dep.id_linhaproduto = "+id;
		
		}else if (porFilter.equals("tipovend")) {
			varWhere = " and tv.id_tipovendedor = "+id;	
		}		
		
		varCampos = " pr.id_produto as produtoId, "+
		            " max(pr.descricao) as produto, "+
				    " max(pr.codinterno) as produtoCod, "+
		            " max(un.desccf) un, ";
		varGroup = " pr.id_produto ";
		
		if("pedido".equals(vendasPorFilter)) {
			sql = 
					" select "+
							"     tab2.produtoId, "+
							"     max(tab2.produto) as produto, "+
							"     max(tab2.produtoCod) produtoCod, "+
							"     max(tab2.un) un, "+
							"     max(tab2.preco) preco, "+
							"     coalesce(SUM(tab2.qtdeAuto), 0.0) as qtdeAuto, "+
							"     coalesce(SUM(tab2.valor), 0.0) as valor, "+
							"     coalesce(SUM(tab2.vlDevolvido), 0.0) as vlDevolvido, "+
							"     coalesce(SUM(tab2.VALORCUSTO), 0.0) as VALORCUSTO, "+
							"     coalesce(SUM(tab2.lucro), 0.0) as LUCRO, "+
							"     case "+
							"         when SUM(tab2.valor-tab2.vlDevolvido) = 0 then 0 "+
							"         WHEN SUM(tab2.lucro) <= 0 then 0 "+
							"         else (((SUM(tab2.lucro)/(SUM(tab2.valor-tab2.vlDevolvido)))* 100)) "+
							"     end margem, "+
							"     case "+
							"         SUM(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDO) when 0 then 0 "+
							"         else (((SUM(tab2.lucro) / SUM(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDO))* 100)) "+
							"     end markup, "+
							"     SUM(tab2.VALCUSTODEVOLVIDO) AS VALCUSTODEVOLVIDO, "+
							"     sum(tab2.desconto) AS desconto "+
							" from "+
							"     ( "+					
					" select "+
							"     tab.produtoId, "+
							"     max(tab.produto) produto, "+
							"     max(tab.produtoCod) produtoCod, "+
							"     max(tab.un) un, "+
							"     max(tab.preco) preco, "+
							"     coalesce(SUM(tab.qtd), 0.0) as qtdeAuto, "+
							"     coalesce(SUM(tab.faturamento), 0.0) as valor, "+
							"     coalesce(SUM(tab.VALORCUSTO), 0.0) as VALORCUSTO, "+
							"     ((coalesce(SUM(tab.faturamento), 0.0)-coalesce(SUM(tab.devolucao), 0.0))-coalesce(SUM(tab.VALORCUSTO), 0.0))+ coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as LUCRO, "+
							"     coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as VALCUSTODEVOLVIDO, "+
							"     coalesce(SUM(tab.devolucao), 0.0) as vlDevolvido, "+
							"     coalesce(SUM(tab.desconto), 0.0) as desconto "+
							" from "+
							"     (  "+
							"     select  "+
									"   "+varCampos+" "+
									"	 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) as numeric(18, 3)) else 0 end) qtd,  "+
									"    MAX(PVI.PRECO) AS preco, "+
									"	 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) faturamento,  "+
									"    SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.CUSTOGERULTCOMPRAUV as numeric(18,4)) else 0 end) VALORCUSTO, "+
									"    0 as VALCUSTODEVOLVIDO, "+									
									"	 0 as devolucao, "+
									"	 0 as desconto "+
							"     from  "+
									"	 PEDVENDA PV,  "+
									"	 PEDVENDAITEM PVI,  "+
									"	 PRODUTO PR,  "+
									"    unidade un, "+
									"	 PEDVENDASTATUS PVS,  "+
									"	 TIPOMOVFISC tmf,  "+
									"	 linhaproduto lp,  "+
									"	 linhaproduto dep, "+
									"	 cliente cl,  "+
									"	 gestaovendamob gvm,  "+
									"	 vendedor v,  "+
									"	 tipovendedor tv,  "+
									"	 pessoa vnd, "+
									"	 pessoa frn, "+
									"	 tipooperacaofiscal tof  "+
							"	 where PV.ID_PEDVENDA = PVI.ID_PEDVENDA  "+
							"	   and pr.id_linhaproduto = lp.id_linhaproduto  "+
							"	   and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+							
							"	   and PVI.ID_PRODUTO = PR.ID_PRODUTO  "+
							"      and pr.id_unidade_venda = un.id_unidade "+
							"	   and PV.ID_PEDVENDASTATUS = PVS.ID_PEDVENDASTATUS  "+
							"	   and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC  "+
							"	   and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal  "+
							"	   and cl.id_pessoa = pv.id_pessoa_cli  "+
							"	   and pv.id_pessoa_vend = v.id_pessoa  "+
							"	   and v.id_pessoa = vnd.id_pessoa "+
							"	   and v.id_tipovendedor = tv.id_tipovendedor "+
							"	   and pr.id_pessoa_forn = frn.id_pessoa "+
							"	   and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
							"	   and tmf.CLASSE in (0, 1)  "+
							"	   and PVS.EFETIVADO = 1  "+
							"	   and pv.id_pessoa_emp = COALESCE(:id_pessoa, pv.id_pessoa_emp) "+
							"	   and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
							"	   and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"	   and PV.EFETIVACAO between :dt1 and :dt2  "+
							"      "+sqlFiltro1+" "+
							"      "+varWhere+" "+
							"	 group by "+varGroup+" "+
							" union all  "+
							"    select  "+
									"   "+varCampos+" "+
									"    SUM(EVI.quantidade-coalesce(EVI.qtdpedido, 0)) as qtd, "+
									"    MAX(evi.preco) as preco, "+
									"	 SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(case evi.quantidade when 0 then 0 else (EVI.valorliquidoitem/evi.quantidade) end), 2)) as faturamento,  "+
									"    SUM(cast(((eVI.QUANTIDADE-coalesce(evi.qtdpedido, 0)) * evi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) VALORCUSTO, "+									
									"    0 as VALCUSTODEVOLVIDO, "+
									"	 0 as devolucao, "+
									"	 0 as desconto "+
							"     from  "+
									"	 ECF_VENDAS ev,  "+
									"	 ECF_VENDASITEM eVI,  "+
									"	 PRODUTO PR ,  "+
									"    unidade un, "+
									"	 linhaproduto lp,  "+
									"	 linhaproduto dep, "+
									"	 cliente cl,  "+
									"	 gestaovendamob gvm,  "+
									"	 vendedor v, "+
									"	 tipovendedor tv,  "+
									"	 pessoa vnd, "+
									"	 pessoa frn "+
							"     where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS  "+
							"	    and pr.id_linhaproduto = lp.id_linhaproduto  "+
							"	    and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+							
							"  	    and eVI.ID_PRODUTO = PR.ID_PRODUTO  "+
							"       and pr.id_unidade_venda = un.id_unidade "+
							"	    and cl.id_pessoa = ev.id_pessoa_cli  "+
							"	    and ev.CANCELADA = 0  "+
							"	    and evi.CANCELADA = 0  "+
							"	    and ev.CONCLUIDA = 1  "+
							"	    and coalesce(ev.ID_DAV, 0) = 0  "+
							"	    and ev.isvenda = 1  "+
							"	    and ev.id_pessoa_vend = v.id_pessoa  "+
							"  	    and v.id_pessoa = vnd.id_pessoa "+
							"	    and v.id_tipovendedor = tv.id_tipovendedor "+
							"	    and pr.id_pessoa_forn = frn.id_pessoa "+
							"	    and ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp)  "+
							"	    and ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)   "+
							"	    and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"	    and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
							"	    and ev.DATAVENDA between :dt1 and :dt2  "+
							"       "+sqlFiltro2+" "+
							"       "+varWhere+" "+
							"     group by "+varGroup+" "+
							" union all "+
							"     select  "+
									"   "+varCampos+" "+
									"    0 as qtd, "+
									"    0 as preco, "+
									"	 0 as fat, "+
									"    0 as VALORCUSTO, "+									
									"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO, "+
									"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
									"	 0 as desconto "+
							"     from  "+
									"	 BOLETIMDEVOLUCAO bd,  "+
									"	 BOLETIMDEVOLITEM bdI,  "+
									"	 NFVENDAITEM nfi,  "+
									"	 NFVENDA nf,  "+
									"	 PRODUTO PR ,  "+
									"    unidade un, "+									
									"	 linhaproduto lp,  "+
									"	 linhaproduto dep, "+
									"	 cliente cl,  "+
									"	 gestaovendamob gvm,  "+
									"	 vendedor v,  "+
									"	 tipovendedor tv,  "+
									"	 pessoa vnd, "+
									"	 pessoa frn "+
							"	 where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO  "+
							"	  and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM  "+
							"	  and nfi.ID_NFVENDA = nf.ID_NFVENDA  "+
							"	  and pr.id_linhaproduto = lp.id_linhaproduto  "+
							"	  and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
							"	  and bdI.ID_PRODUTO = PR.ID_PRODUTO  "+
							"     and pr.id_unidade_venda = un.id_unidade "+
							"	  and cl.id_pessoa = bd.id_pessoa_cli  "+
							"	  and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
							"	  and bd.id_pessoa_vend = v.id_pessoa  "+
							"	  and v.id_pessoa = vnd.id_pessoa "+
							"	  and v.id_tipovendedor = tv.id_tipovendedor "+
							"	  and pr.id_pessoa_forn = frn.id_pessoa "+							
							"	  and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
							"	  and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
							"	  and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"	  and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
							"	  and ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null))  "+
							"	  and bd.MOMENTO between :dt1 and :dt2  "+
							"     "+sqlFiltro3+" "+
							"     "+varWhere+" "+
							"    group by "+varGroup+" "+							
							" union all "+
							"     select  "+
									"   "+varCampos+" "+
									"    0 as qtd, "+
									"    0 as preco, "+
									"	 0 faturamento,  "+
									"    0 as VALORCUSTO, "+									
									"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO, "+
									"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucao, "+
									"	 0 as desconto "+
							"     from  "+
									"	 BOLETIMDEVOLUCAO bd,  "+
									"	 BOLETIMDEVOLITEM bdI,  "+
									"	 ecf_vendasitem evi,  "+
									"	 ecf_vendas ev,  "+
									"	 PRODUTO PR,  "+
									"    unidade un, "+
									"	 linhaproduto lp,  "+
									"	 linhaproduto dep, "+
									"	 cliente cl,  "+
									"	 gestaovendamob gvm,  "+
									"	 vendedor v,  "+
									"	 tipovendedor tv,  "+
									"	 pessoa vnd, "+
									"	 pessoa frn "+
							"	where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO  "+
							"	  and bdi.id_ecfvendasitem = evi.id_ecfvendasitem  "+
							"	  and evi.id_ecfvendas = ev.id_ecfvendas  "+
							"	  and pr.id_linhaproduto = lp.id_linhaproduto  "+
							"	  and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
							"	  and bdI.ID_PRODUTO = PR.ID_PRODUTO  "+
							"     and pr.id_unidade_venda = un.id_unidade "+
							"	  and cl.id_pessoa = bd.id_pessoa_cli  "+
							"	  and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
							"	  and bd.id_pessoa_vend = v.id_pessoa  "+
							"	   and v.id_tipovendedor = tv.id_tipovendedor "+
							"	  and v.id_pessoa = vnd.id_pessoa "+
							"	  and pr.id_pessoa_forn = frn.id_pessoa "+							
							"	  and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
							"	  AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
							"	  and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"	  and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
							"	  and bd.MOMENTO between :dt1 and :dt2  "+
							"     "+sqlFiltro4+" "+
							"     "+varWhere+" "+
							"   group by "+varGroup+" "+							
							" union all "+
							"     select  "+
									"   "+varCampos+" "+
									"    0 as qtd, "+
									"    0 as preco, "+
									"	 0 faturamento,  "+
									"    0 as VALORCUSTO, "+									
									"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO, "+
									"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
									"	 0 as desconto "+
							"     from  "+
									"	 BOLETIMDEVOLUCAO bd,  "+
									"	 BOLETIMDEVOLITEM bdI,  "+
									"	 pedvendaitem pvi,  "+
									"	 pedvenda pv,  "+
									"	 PRODUTO PR ,  "+
									"    unidade un, "+
									"	 linhaproduto lp,  "+
									"	 linhaproduto dep, "+
									"	 cliente cl,  "+
									"	 gestaovendamob gvm,  "+
									"	 vendedor v,  "+
									"	 tipovendedor tv,  "+
									"	 pessoa vnd, "+
									"	 pessoa frn "+
							"    where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO  "+
							"	   and bdi.id_pedvendaitem = pvi.id_pedvendaitem  "+
							"	   and pvi.id_pedvenda = pv.id_pedvenda  "+
							"	   and pr.id_linhaproduto = lp.id_linhaproduto  "+
							"	   and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
							"	   and bdI.ID_PRODUTO = PR.ID_PRODUTO  "+
							"      and pr.id_unidade_venda = un.id_unidade "+
							"	   and cl.id_pessoa = bd.id_pessoa_cli  "+
							"	   and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
							"	   and bd.id_pessoa_vend = v.id_pessoa  "+
							"	   and v.id_pessoa = vnd.id_pessoa "+
							"	   and v.id_tipovendedor = tv.id_tipovendedor "+
							"	   and pr.id_pessoa_forn = frn.id_pessoa "+							
							"	   and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
							"	   and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
							"	   and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
							"	   and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"	   and bd.MOMENTO between :dt1 and :dt2  "+
							"      "+sqlFiltro5+" "+
							"      "+varWhere+" "+
							"   group by "+varGroup+" "+							
							"   ) tab "+
							"  group by tab.produtoId "+
							" ) tab2 "+
							" group by tab2.produtoId "+
							"  order by 7 desc ";

					
		}else {
		
		
			sql =   " select "+
					"     tab2.produtoId, "+
					"     max(tab2.produto) as produto, "+
					"     max(tab2.produtoCod) produtoCod, "+
					"     max(tab2.un) un, "+
					"     coalesce(SUM(tab2.qtdeAuto), 0.0) as qtdeAuto, "+
					"     COALESCE(max(tab2.preco), 0.0) AS preco, "+					
					"     coalesce(SUM(tab2.valor), 0.0) as valor, "+
					"     coalesce(SUM(tab2.vlDevolvido), 0.0) as vlDevolvido, "+
					"     coalesce(SUM(tab2.VALORCUSTO), 0.0) as VALORCUSTO, "+
					"     coalesce(SUM(tab2.lucro), 0.0) as LUCRO, "+
					"     case "+
					"         when SUM(tab2.valor-tab2.vlDevolvido) = 0 then 0 "+
					"         WHEN SUM(tab2.lucro) <= 0 then 0 "+
					"         else (((SUM(tab2.lucro)/(SUM(tab2.valor-tab2.vlDevolvido)))* 100)) "+
					"     end margem, "+
					"     case "+
					"         SUM(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDO) when 0 then 0 "+
					"         else (((SUM(tab2.lucro) / SUM(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDO))* 100)) "+
					"     end markup, "+
					"     SUM(tab2.VALCUSTODEVOLVIDO) AS VALCUSTODEVOLVIDO, "+
					"     0 AS desconto "+
					" from "+
					"     ( "+	
					" SELECT tab.id as produtoId, "+
					"    max(tab.descricao) produto, "+
					"    max(tab.produtoCod) produtoCod, "+
					"    max(tab.un) un, "+
					"    COALESCE(SUM(tab.qtd), 0.0) AS qtdeAuto, "+
					"    COALESCE(max(tab.preco), 0.0) AS preco, "+
					"    COALESCE(SUM(tab.faturamento), 0.0) AS valor, "+
					"    COALESCE(SUM(tab.VALORCUSTO), 0.0) AS VALORCUSTO, "+
					"    ((coalesce(SUM(tab.faturamento), 0.0)-coalesce(SUM(tab.devolucao), 0.0))-coalesce(SUM(tab.VALORCUSTO), 0.0))+ coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as LUCRO, "+					
					"    COALESCE(SUM(tab.VALCUSTODEVOLVIDO), 0.0) AS VALCUSTODEVOLVIDO, "+
					"    COALESCE(SUM(tab.devolucao), 0.0) AS vlDevolvido, "+					
					"    COALESCE(SUM(tab.desconto), 0.0) AS desconto "+
					"  FROM ( "+
					"      SELECT pr.id_produto as id, "+
					"        max(pr.descricao) as descricao, "+
					"        max(pr.codinterno) produtoCod, "+
					"        max(un.desccf) un, "+
					"        sum(evi.quantidade) as qtd, "+
					"        SUM(evi.valorliquidoitem) AS faturamento,  "+
					"        SUM(cast((evi.QUANTIDADE * evi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALORCUSTO, "+
					"        0 AS VALCUSTODEVOLVIDO, "+
					"        max(evi.preco) preco, "+
					"        0 AS devolucao, "+
					"        0 AS desconto "+
					"      FROM ecf_vendas ev,    "+
					"           ecf_vendasitem evi, "+
					"           produto pr, "+
					"           linhaproduto lp, "+
					"           linhaproduto dep, "+
					"           pessoa vnd, "+
					"	 		vendedor v,  "+
					"	 		tipovendedor tv,  "+
					"           pessoa frd, "+
					"           unidade un "+
					"      WHERE evi.id_ecfvendas = ev.id_ecfvendas "+
					"        and ev.id_pessoa_vend = vnd.id_pessoa "+
					"	     and v.id_pessoa = vnd.id_pessoa "+
					"	  	 and v.id_tipovendedor = tv.id_tipovendedor "+
					"        and evi.id_produto = pr.id_produto "+
					"        and pr.id_linhaproduto = lp.id_linhaproduto "+
					"        AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
					"        and pr.id_pessoa_forn = frd.id_pessoa "+
					"        and pr.id_unidade_venda = un.id_unidade "+
					"        AND ev.datavenda between :dt1 AND :dt2    "+
					"        AND ev.concluida = 1    "+
					"        AND ev.cancelada = 0    "+
					"        AND evi.cancelada = 0   "+
					"        AND ev.isvenda = 1  "+
					"        and coalesce(ev.ID_DAV,0) = 0  "+
					"        AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp)  "+
					"        AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)  "+
					"	     and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"        "+sqlFiltro2+" "+
					"        "+varWhere+" "+
					"      group by pr.id_produto "+
					"     UNION ALL    "+
					"     SELECT pr.id_produto as id, "+
					"           max(pr.descricao) as descricao, "+
					"           max(pr.codinterno) produtoCod, "+
					"           max(un.desccf) un, "+
					"           sum(nfvi.quantidade) qtd, "+
					"           SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS faturamento,   "+
					"           sum(cast((nfvi.QUANTIDADE * nfvi.CUSTOMEDIOONLINE) as numeric(18,4))) VALORCUSTO, "+
					"           0 AS VALCUSTODEVOLVIDO, "+					
					"           avg(nfvi.PRECO) preco, "+
					"           0 AS devolucao, "+
					"           0 AS desconto "+
					"        FROM NFVENDA nfv,    "+
					"         NFVENDAITEM nfvi,    "+
					"         TIPOMOVFISC tmf,   "+
					"         PEDVENDA pv, "+
					"         produto pr, "+
					"         linhaproduto lp, "+
					"         linhaproduto dep, "+
					"         pessoa vnd, "+
					"	 	  vendedor v,  "+
					"	 	  tipovendedor tv,  "+
					"         pessoa frd, "+
					"         unidade un "+
					"        WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
					"          AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
					"          AND nfv.id_pedvenda = pv.id_pedvenda   "+
					"          AND pv.id_pessoa_vend = vnd.id_pessoa "+
					"	       and v.id_pessoa = vnd.id_pessoa "+
					"	  	   and v.id_tipovendedor = tv.id_tipovendedor "+
					"          and nfvi.id_produto = pr.id_produto "+
					"          and pr.id_linhaproduto = lp.id_linhaproduto "+
					"          and pr.id_pessoa_forn = frd.id_pessoa "+
					"          AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
					"          AND nfv.DATAEMISS between :dt1 AND :dt2 "+
					"          AND nfv.CANCELADA = 0    "+
					"          AND tmf.CLASSE = 0  "+
					"          AND nfv.TIPO = 'S'    "+
					"          AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)   "+
					"          AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) "+
					"	       and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"          and pr.id_unidade_venda = un.id_unidade "+
					"          "+sqlFiltro1+" "+
					"          "+varWhere+" "+
					"        group by pr.id_produto "+
					"     UNION ALL   "+
					"     SELECT pr.id_produto as id, "+
					"           max(pr.descricao) as descricao, "+
					"           max(pr.codinterno) produtoCod, "+
					"           max(un.desccf) un, "+
					"           0 as qtd, "+
					"           0 AS faturamento,    "+
					"           0 AS VALCUSTODEVOLVIDO, "+
					"           SUM(cast((bdi.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALCUSTODEVOLVIDO, "+					
					"           0 as preco, "+
					"           SUM(bdi.quantidade * bdi.valorunit) AS devolucao, "+
					"           0 AS desconto "+
					"      FROM boletimdevolucao bd,    "+
					"           boletimdevolitem bdi,    "+
					"           tipomovfisc tmf, "+
					"           produto pr, "+
					"           linhaproduto lp, "+
					"           linhaproduto dep, "+
					"           pessoa vnd, "+
					"	 		vendedor v,  "+
					"	 		tipovendedor tv,  "+
					"           pessoa frd, "+
					"           unidade un "+
					"      WHERE bd.id_boletimdevolucao = bdi.id_boletimdevolucao "+
					"        AND tmf.id_tipomovfisc = bd.id_tipomovfisc "+
					"        and bdi.id_produto = pr.id_produto "+
					"        and pr.id_linhaproduto = lp.id_linhaproduto "+
					"        AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
					"        and bd.id_pessoa_vend = vnd.id_pessoa "+
					"	     and v.id_pessoa = vnd.id_pessoa "+
					"	  	 and v.id_tipovendedor = tv.id_tipovendedor "+
					"        and pr.id_pessoa_forn = frd.id_pessoa "+
					"        AND tmf.altestqfisico = 1 "+
					"        AND bd.momento between :dt1 AND :dt2    "+
					"        AND bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
					"        AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
					"	     and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"        AND bd.id_boletimdevolstatus <> 1   "+
					"        and pr.id_unidade_venda = un.id_unidade "+
					"        "+sqlFiltro3+" "+
					"        "+varWhere+" "+
					"      group by pr.id_produto "+
					"  )tab "+
					"  group by tab.id "+
					" ) tab2 "+
					"  group by tab2.produtoId "+
					"  order by 7 desc ";

		}
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produto", Hibernate.STRING)
				.addScalar("qtdeAuto", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("margem", Hibernate.DOUBLE)
				.addScalar("markup", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE);
				
				query.setParameter("dt1", dataFilter1);
				query.setParameter("dt2", dataFilter2);
				
				if(empresaFilter!=null) {
					query.setParameter("id_pessoa", empresaFilter.getId());
				}else {
					query.setParameter("id_pessoa", null);
				}	
				
				if(vendedorFilter!=null) {
					query.setParameter("vendedor", vendedorFilter.getId());
				}else {
					query.setParameter("vendedor", null);
				}	
				
				if(fornecedorFilter!=null) {
					query.setParameter("fornecedor", fornecedorFilter.getId());
				}else {
					query.setParameter("fornecedor", null);
				}
				
				query.setResultTransformer(Transformers.aliasToBean(VendasProdutoDTO.class));
				
				return query.list();
		}	

}
