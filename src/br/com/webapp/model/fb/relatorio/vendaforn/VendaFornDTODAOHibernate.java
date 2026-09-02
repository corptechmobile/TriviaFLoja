package br.com.webapp.model.fb.relatorio.vendaforn;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumo;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class VendaFornDTODAOHibernate implements VendaFornDTODAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked") 
	@Override
	public List<VendaFornDTO> listarAutoServico(EmpresaFB empresa, VendedorFB vendedor, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2) {
		
		String varFrom = "";
		String varCollumn = "";
		String varWhere = "";
		String varWhereDev = "";
		if(porFilter.equals("fornecedor")) {
			
			varFrom = " pessoa p, produto pr, ";
			
			varCollumn = " p.id_pessoa AS id, MAX(p.nomefantmnem) AS descricao, ";
			
			varWhere = " AND ecfi.id_produto = pr.id_produto " + 
					   " AND pr.id_pessoa_forn = p.id_pessoa "; 
			
			varWhereDev = " AND bi.id_produto = pr.id_produto " + 
					   	  " AND pr.id_pessoa_forn = p.id_pessoa "; 
		
		} else if(porFilter.equals("linhaProduto")) {
			
			varFrom = " linhaproduto lp, linhaproduto dep, produto pr, ";
			
			varCollumn = " dep.id_linhaproduto id, MAX(dep.descricao) descricao, ";
			
			varWhere = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 + " AND ecfi.id_produto = pr.id_produto ";
			
			varWhereDev  = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 	 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 	 + " AND bi.id_produto = pr.id_produto ";
		} else {
			varFrom = "";
			varCollumn = " -1 AS id, 'Sem Vendedor' AS descricao, ";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tab.id AS id, ")
				 .append(" MAX(tab.descricao) AS descricao, ")
	     		 .append(" SUM(tab.valor) AS valor, ")
	     		 .append(" SUM(tab.vlDevolvido) AS vlDevolvido, ")
	     		 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.0 END AS desconto ")
	     	.append(" FROM ( ");
	     
				sql.append("SELECT  ").append(varCollumn)
					     .append(" SUM(TRUNC(((ecfi.quantidade - ecfi.qtdpedido) * ecfi.preco), 2)) AS valor, ") 
					     .append(" 0 AS vlDevolvido, ")
					     .append(" CAST(SUM(ecfi.quantidade * ecfi.preco * ecfi.percdesconto) / SUM(ecfi.quantidade * ecfi.preco) AS numeric(18,2)) AS desconto ")
					  .append(" FROM ecf_vendas ecf, ").append(varFrom)
					       .append(" ecf_vendasitem ecfi, ")
					       .append(" vendedor v ")
					  .append(" WHERE ecf.id_ecfvendas = ecfi.id_ecfvendas ")
					    .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
						.append(" AND ecf.id_pessoa_emp = :id_pessoa_emp ")
						.append(" AND ecf.id_pessoa_vend = v.id_pessoa ")
						.append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
						.append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  ")
						.append(" AND (ecfi.quantidade - ecfi.qtdpedido) > 0 ") 
					    .append(" AND ecf.concluida = 1 ") 
					    .append(" AND ecf.cancelada = 0 ") 
					    .append(" AND ecfi.cancelada = 0 ").append(varWhere)
					  .append(" GROUP BY 1 ");
					 
			sql.append(" UNION ALL ");
				 
				 sql.append("SELECT  ").append(varCollumn)
					     .append(" 0 AS valor, ") 
					     .append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ")
					     .append(" 0 AS desconto ")
					  .append(" FROM boletimdevolucao b, ").append(varFrom)
					       .append(" boletimdevolitem bi, ")
					  	   .append(" ecf_vendas ecf, ")
					  	   .append(" ecf_vendasitem ecfi, ")
					  	 .append(" tipomovfisc tmf, ")
					  	 .append(" vendedor v ")
					  .append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ")
					    .append(" AND tmf.id_tipomovfisc = b.id_tipomovfisc ")
				  	    .append(" AND tmf.altestqfisico = 1 ")
					    .append(" AND b.momento BETWEEN :dt1 AND :dt2 ")
					    .append(" AND b.id_boletimdevolstatus <> 1 ")
					    .append(" AND bi.id_ecfvendasitem = ecfi.id_ecfvendasitem ")
					    .append(" AND ecf.id_ecfvendas = ecfi.id_ecfvendas ")
						.append(" AND ecf.id_pessoa_emp = :id_pessoa_emp ")
						.append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  ")
						.append(" AND b.id_pessoa_vend = v.id_pessoa ")
						.append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
						.append(" AND (ecfi.quantidade - ecfi.qtdpedido) > 0 ").append(varWhereDev) 
						.append(" GROUP BY 1 ");
		 
			sql.append(") tab ")
				.append(" GROUP BY tab.id ")
				.append(" ORDER BY 3 desc"); 
			
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE);
		
			query.setParameter("id_pessoa_emp", empresa.getId());
			if(vendedor!=null) {
				query.setParameter("vendedor", vendedor.getId());
			}else {
				query.setParameter("vendedor", null);
			}
			
			if(tipoVendedorFilter!=null) {
				query.setParameter("tipovendedor", tipoVendedorFilter.getId());
			}else {
				query.setParameter("tipovendedor", null);
			}
			
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
			query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
			
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornDTO> listarPedido(EmpresaFB empresa, VendedorFB vendedor, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2) {
		
		String varFrom = "";
		String varCollumn = "";
		String varWhere = "";
		String varWhereDev = "";
		if(porFilter.equals("fornecedor")) {
			
			varFrom = " pessoa p, produto pr, ";
			
			varCollumn = " p.id_pessoa AS id, MAX(p.nomefantmnem) AS descricao, ";
			
			varWhere = " AND pvi.id_produto = pr.id_produto " + 
					   " AND pr.id_pessoa_forn = p.id_pessoa "; 
			
			varWhereDev = " AND bi.id_produto = pr.id_produto " + 
					   	  " AND pr.id_pessoa_forn = p.id_pessoa "; 
		
		} else if(porFilter.equals("linhaProduto")) {
			
			varFrom = " linhaproduto lp, linhaproduto dep, produto pr, ";
			
			varCollumn = " dep.id_linhaproduto id, MAX(dep.descricao) descricao, ";
			
			varWhere = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 + " AND pvi.id_produto = pr.id_produto ";
			
			varWhereDev  = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 	 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 	 + " AND bi.id_produto = pr.id_produto ";
		} else {
			
			varFrom = " pessoa p, ";
			
			varCollumn = " p.id_pessoa AS id, MAX(p.nomefantmnem) AS descricao, ";
			
			varWhere = " AND pv.id_pessoa_vend = p.id_pessoa ";
			
			varWhereDev  = " AND pv.id_pessoa_vend = p.id_pessoa ";
			
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tab.id AS id, ")
				 .append(" MAX(tab.descricao) AS descricao, ")
				 .append(" SUM(tab.valor) AS valor, ")
				 .append(" SUM(tab.vlDevolvido) AS vlDevolvido, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.0 END AS desconto ")
			.append(" FROM ( ");
			
			sql.append("SELECT ").append(varCollumn) 
					 .append(" SUM(TRUNC((iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco), 2)) AS valor, ")
					 .append(" 0 AS vlDevolvido, ")
					 .append(" CAST(SUM(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco * pvi.percdesconto) / SUM(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco) AS numeric(18,2)) AS desconto ") 
				  .append(" FROM pedvenda pv, ").append(varFrom) 
				  	   .append(" pedvendaitem pvi, ")
				  	   .append(" tipomovfisc tmf, ")
				  	   .append(" vendedor v ")
				  .append(" WHERE pv.id_pedvenda = pvi.id_pedvenda ")
				    .append(" AND pv.id_tipomovfisc = tmf.id_tipomovfisc ") 
				    .append(" AND tmf.classe in (0,1) ")
				    .append(" AND pv.efetivacao BETWEEN :dt1 AND :dt2 ") 
				    .append(" AND pv.id_pedvendastatus IN (4, 5, 6, 7) ")
				    .append(" AND pv.id_pessoa_emp = :id_pessoa_emp ")
  				    .append(" AND pv.id_pessoa_vend = v.id_pessoa ")
					.append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				    .append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  ").append(varWhere)
				  .append(" GROUP BY 1 ");
		
		sql.append("UNION ALL ");
		
			sql.append("SELECT ").append(varCollumn)
					.append(" 0 AS valor, ")
					.append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ") 
					.append(" 0 AS desconto  ")
				.append("FROM boletimdevolucao b, ").append(varFrom)  
					.append(" boletimdevolitem bi, ")
					.append(" ecf_vendasitem ecfi, ") 
					.append(" ecf_vendas ecf, ")
					.append(" pedvendaitem pvi, ")
					.append(" pedvenda pv, ")
			  	    .append(" tipomovfisc tmf, ")
			  	    .append(" vendedor v ")
				.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ") 
				  .append(" AND b.id_boletimdevolstatus <> 1 ")
				  .append(" AND b.momento between :dt1 AND :dt2 ")
				  .append(" AND ecf.id_pessoa_emp = :id_pessoa_emp ")
   			      .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  ")
				  .append(" AND b.id_pessoa_vend = v.id_pessoa ")
				  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				  .append(" AND pvi.id_produto = bi.id_produto ")
				  .append(" AND pvi.id_pedvenda = pv.id_pedvenda ")
				  .append(" AND bi.id_ecfvendasitem = ecfi.id_ecfvendasitem ") 
				  .append(" AND ecf.id_ecfvendas = ecfi.id_ecfvendas ")
				  .append(" AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda ")
 			      .append(" AND pv.id_tipomovfisc = tmf.id_tipomovfisc ") 
				  .append(" AND tmf.classe in (0,1) ")
				  .append(" AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda ").append(varWhereDev) 
				.append(" GROUP BY 1 ");
			
			sql.append("UNION ALL ");
			
			sql.append("SELECT ").append(varCollumn)
					.append(" 0 AS valor, ")
					.append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ") 
					.append(" 0 AS desconto  ")
				.append("FROM boletimdevolucao b, ").append(varFrom)  
					.append(" boletimdevolitem bi, ")
					.append(" nfvendaitem nfi, ") 
					.append(" nfvenda nf, ")
					.append(" pedvendaitem pvi, ")
					.append(" pedvenda pv, ")
			  	    .append(" tipomovfisc tmf, ")
			  	    .append(" vendedor v ")
				.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ") 
				  .append(" AND b.id_boletimdevolstatus <> 1 ")
				  .append(" AND b.momento between :dt1 AND :dt2 ")
				  .append(" AND b.id_pessoa_emp = :id_pessoa_emp ")
				  .append(" AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend)  ")
				  .append(" AND b.id_pessoa_vend = v.id_pessoa ")
				  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				  .append(" AND bi.id_produto = pvi.id_produto ")
				  .append(" AND bi.id_ecfvendasitem not in (SELECT ei.id_ecfvendasitem FROM ecf_vendasitem ei WHERE ei.id_ecfvendasitem = bi.id_ecfvendasitem) ")
				  .append(" AND pvi.id_pedvenda = pv.id_pedvenda ")
				  .append(" AND bi.id_nfvendaitem = nfi.id_nfvendaitem ") 
				  .append(" AND nf.id_nfvenda = nfi.id_nfvenda ")
				  .append(" AND nf.id_pedvenda = pv.id_pedvenda ")
			      .append(" AND pv.id_tipomovfisc = tmf.id_tipomovfisc ") 
			      .append(" AND tmf.altestqfisico = 1 ")
				  .append(" AND tmf.classe in (0,1) ")
				  .append(" ").append(varWhereDev) 
				.append(" GROUP BY 1 ");
			
			
			sql.append(") tab ")
			   .append(" GROUP BY tab.id ")
			   .append(" ORDER BY 3 desc"); 
				  
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE);
		
			query.setParameter("id_pessoa_emp", empresa.getId());
			
			if(vendedor!=null) {
				query.setParameter("vendedor", vendedor.getId());
			}else {
				query.setParameter("vendedor", null);
			}
			
			if(tipoVendedorFilter!=null) {
				query.setParameter("tipovendedor", tipoVendedorFilter.getId());
			}else {
				query.setParameter("tipovendedor", null);
			}
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
			query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<VendaFornDTO> listarPedidoSemAutoServico(EmpresaFB empresa, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2) {
		
		String varFrom = "";
		String varCollumn = "";
		String varWhere = "";
		String varWhereDev = "";
		if(porFilter.equals("fornecedor")) {
			
			varFrom = " pessoa p, produto pr, ";
			
			varCollumn = " p.id_pessoa AS id, MAX(p.nomefantmnem) AS descricao, ";
			
			varWhere = " AND pvi.id_produto = pr.id_produto " + 
					   " AND pr.id_pessoa_forn = p.id_pessoa "; 
			
			varWhereDev = " AND bi.id_produto = pr.id_produto " + 
					   	  " AND pr.id_pessoa_forn = p.id_pessoa "; 
		
		} else if(porFilter.equals("linhaProduto")) {
			
			varFrom = " linhaproduto lp, linhaproduto dep, produto pr, ";
			
			varCollumn = " dep.id_linhaproduto id, MAX(dep.descricao) descricao, ";
			
			varWhere = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 + " AND pvi.id_produto = pr.id_produto ";
			
			varWhereDev  = " AND dep.codedt = substring(lp.codedt FROM 1 for 6) "
					 	 + " AND pr.id_linhaproduto = lp.id_linhaproduto "
					 	 + " AND bi.id_produto = pr.id_produto ";
		} else {
			
			varFrom = " pessoa p, ";
			
			varCollumn = " p.id_pessoa AS id, MAX(p.nomefantmnem) AS descricao, ";
			
			varWhere = " AND pv.id_pessoa_vend = p.id_pessoa ";
			
			varWhereDev  = " AND b.id_pessoa_vend = p.id_pessoa ";
			
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tab.id AS id, ")
				 .append(" MAX(tab.descricao) AS descricao, ")
				 .append(" SUM(tab.valor) AS valor, ")
				 .append(" SUM(tab.vlDevolvido) AS vlDevolvido, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.0 END AS desconto ")
			.append(" FROM ( ");
			
			sql.append("SELECT ").append(varCollumn) 
					 .append(" SUM(TRUNC((iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco), 2)) AS valor, ")
					 .append(" 0 AS vlDevolvido, ")
					 .append(" CASE WHEN sum(Iif(pv.id_pedvendastatus IN (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco) > 0 THEN Cast(Sum(Iif(pv.id_pedvendastatus IN (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco * pvi.percdesconto) / Sum(Iif(pv.id_pedvendastatus IN (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco) AS NUMERIC(18,2)) ELSE 0 END AS desconto ") 
				  .append(" FROM pedvenda pv, ").append(varFrom) 
				  	   .append(" pedvendaitem pvi, ")
				  	   .append(" pedvendastatus pvs, ")
				  	   .append(" tipomovfisc tmf, ")
				  	   .append(" vendedor v ")
				  .append(" WHERE pv.id_pedvenda = pvi.id_pedvenda ")
				   .append("  AND pv.id_pessoa_vend = v.id_pessoa ")
				   .append("  AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS ")
				   .append("  AND pv.id_tipomovfisc = tmf.id_tipomovfisc ")
			       .append("  AND pvs.EFETIVADO = 1 ")
			       .append("  AND tmf.classe in (0,1) ")
				    .append(" AND pv.efetivacao BETWEEN :dt1 AND :dt2 ")
				    .append(" AND pv.id_pessoa_emp = :id_pessoa_emp ")
				    .append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  ")
				    .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				    .append(varWhere)
				  .append(" GROUP BY 1 ");
		
		sql.append("UNION ALL ");
		
			sql.append("SELECT ").append(varCollumn)
					.append(" 0 AS valor, ")
					.append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, ") 
					.append(" 0 AS desconto  ")
				.append("FROM boletimdevolucao b, ").append(varFrom)  
					.append(" boletimdevolitem bi, ")
					.append(" tipomovfisc tmf, ")
					.append(" vendedor v ")
				.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ") 
				 .append("  AND b.id_pessoa_vend = v.id_pessoa ")
				  .append(" AND tmf.id_tipomovfisc = b.id_tipomovfisc ")
			  	  .append(" AND tmf.altestqfisico = 1 ")
				  .append(" AND b.id_boletimdevolstatus <> 1 ")
				  .append(" AND b.momento between :dt1 AND :dt2 ")
				  .append(" AND B.id_pessoa_emp = :id_pessoa_emp ")
				  .append(" AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend)  ")
				  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				  .append(varWhereDev)
				.append(" GROUP BY 1 ");
			sql.append(") tab ")
			   .append(" GROUP BY tab.id ")
			   .append(" ORDER BY 3 desc"); 
				  
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE);
		
			query.setParameter("id_pessoa_emp", empresa.getId());
			if(vendedorFilter!=null) {
				query.setParameter("vendedor", vendedorFilter.getId());
			}else {
				query.setParameter("vendedor", null);
			}
			
			if(tipoVendedorFilter!=null) {
				query.setParameter("tipovendedor", tipoVendedorFilter.getId());
			}else {
				query.setParameter("tipovendedor", null);
			}
			
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
			query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
			
		return query.list();
	}	

	@SuppressWarnings("unchecked")
	@Override 
	public List<VendaFornDTO> listarAmbos(EmpresaFB empresa, VendedorFB vendedor, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2) {
		String sql = "";
		String varCampos = "";
		String varGroup = "";
		
		if (porFilter.equals("fornecedor")) {
			varCampos = " p.id_pessoa AS id, MAX(p.nomefantmnem) descricao, ";
			varGroup = " p.id_pessoa ";
		
		}else if(porFilter.equals("tipovend")) {
			varCampos = " tv.id_tipovendedor AS id, MAX(tv.descricao) descricao, ";
			varGroup  = " tv.id_tipovendedor ";
			
		}else if(porFilter.equals("linhaProduto")) {
			varCampos = " dep.id_linhaproduto AS id, MAX(dep.descricao) AS descricao, ";
			varGroup  = " dep.id_linhaproduto ";
			
		}else{
			varCampos = " vnd.id_pessoa AS id, MAX(vnd.nomefantmnem) AS descricao, ";
			varGroup  = " vnd.id_pessoa ";

		}
		
		sql = " select tabresult.id as id, "+
				"        MAX(tabresult.descricao) AS descricao, "+
				"        CAST(SUM(tabresult.valor) as numeric(18,2)) AS valor, "+
				"        CAST(SUM(tabresult.vlDevolvido) as numeric(18,2)) AS vlDevolvido, "+
				"        CASE WHEN SUM(valorbruto) > 0.0 THEN CAST((SUM(ValorDesconto) / SUM(valorbruto))*100.00 AS NUMERIC(18, "+
				"             2)) "+
				"             ELSE 0.00 "+
				"         END AS desconto "+
				" from ( "+
				"     SELECT "+
				"         tab.id AS id, "+
				"         MAX(tab.descricao) AS descricao, "+
				"         CAST(SUM(tab.valor) as numeric(18,2)) AS valor, "+
				"         CAST(SUM(tab.valorbruto) as numeric(18,2)) AS valorbruto, "+
				"         CAST(SUM(tab.vlDevolvido) as numeric(18,2)) AS vlDevolvido, "+
				"         CAST(SUM(tab.desconto) as numeric(18,2)) ValorDesconto "+
				"  "+
				"     FROM "+
				"         ( "+
				"         SELECT "+
				"             "+varCampos+" "+ 
				"             SUM(TRUNC(((ecfi.quantidade - COALESCE(ecfi.qtdpedido, 0)) * (ecfi.valorliquidoitem / ecfi.quantidade)), 2)) AS valor, "+
				"             SUM(TRUNC(((ecfi.quantidade - COALESCE(ecfi.qtdpedido, 0)) * ecfi.preco), 2)) AS valorBruto, "+
				"             0 AS vlDevolvido, "+
				"             CAST(SUM(ecfi.quantidade * ecfi.preco * ecfi.percdesconto/100.00) AS NUMERIC(18, 4)) AS desconto "+
				"         FROM "+
				"             pessoa p, "+
				"             produto pr, "+
				"             linhaproduto lp, "+
				" 	 		  linhaproduto dep,  "+
				"             unidade u, "+
				"             ecf_vendas ecf, "+
				"             ecf_vendasitem ecfi, "+
				"             vendedor v, "+
				"             pessoa vnd, "+
				"             tipovendedor tv "+
				"         WHERE "+
				"             ecf.id_ecfvendas = ecfi.id_ecfvendas "+
				"             AND ecfi.id_produto = pr.id_produto "+
				"             AND pr.id_linhaproduto = lp.id_linhaproduto "+
				"    		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             AND pr.id_pessoa_forn = p.id_pessoa "+
				"             AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"             AND ecf.id_pessoa_vend = v.id_pessoa "+
				"             AND v.id_pessoa = vnd.id_pessoa "+
				"             AND ecf.id_pessoa_vend = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             AND v.id_tipovendedor = COALESCE(:tipovendedor, "+
				"             v.id_tipovendedor) "+
				"             AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, "+
				"             ecf.id_pessoa_emp) "+
				"             AND ecf.id_pessoa_vend = COALESCE(:vendedor, "+
				"             ecf.id_pessoa_vend) "+
				"             AND (ecfi.quantidade-ecfi.qtdpedido)>0 "+
				"             AND ecf.concluida = 1 "+
				"             AND ecf.cancelada = 0 "+
				"             AND ecfi.cancelada = 0 "+
				"         GROUP BY "+varGroup+" "+
				"     UNION ALL "+
				"         SELECT "+
				"             "+varCampos+" "+
				"             SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "+
				"                                            (pvi.quantidade - pvi.qtdsaldoatender)) * PVI.preco AS NUMERIC(18, 4)) ELSE 0 END) AS valor, "+
				"             SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "+
				"                                            (pvi.quantidade - pvi.qtdsaldoatender)) * PVI.precoprom AS NUMERIC(18, 4)) ELSE 0 END) AS valorBruto, "+
				"             0 AS vlDevolvido, "+
				"             CAST(SUM( iif(pv.id_pedvendastatus IN (4, 5), "+
				"                      pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.precoprom * pvi.percdesconto/100.00) AS NUMERIC(18,4)) AS desconto "+
				"         FROM "+
				"             fornecedor f, "+
				"             pessoa p, "+
				"             produto pr, "+
				"             linhaproduto lp, "+
				" 	 		  linhaproduto dep,  "+
				"             pedvenda pv, "+
				"             pedvendaitem pvi, "+
				"             tipomovfisc tmf, "+
				"             unidade u, "+
				"             vendedor v, "+
				"             pessoa vnd, "+
				"             tipovendedor tv "+
				"         WHERE "+
				"             pvi.id_produto = pr.id_produto "+
				"             AND pr.id_linhaproduto = lp.id_linhaproduto "+
				"    		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
				"             AND pr.id_unidade_venda = u.id_unidade "+
				"             AND pr.id_pessoa_forn = f.id_pessoa "+
				"             AND f.id_pessoa = p.id_pessoa "+
				"             AND pv.id_pedvenda = pvi.id_pedvenda "+
				"             AND pv.id_pessoa_vend = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             AND pv.efetivacao BETWEEN :dt1 AND :dt2 "+
				"             AND pv.id_pedvendastatus IN (4, 5, 6, 7) "+
				"             AND pv.id_tipomovfisc = tmf.id_tipomovfisc "+
				"             AND tmf.classe IN (0, 1) "+
				"             AND pv.id_pessoa_vend = v.id_pessoa "+
				"             AND v.id_pessoa = vnd.id_pessoa "+
				"             AND v.id_tipovendedor = COALESCE(:tipovendedor, "+
				"             v.id_tipovendedor) "+
				"             AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, "+
				"             pv.id_pessoa_emp) "+
				"             AND pv.id_pessoa_vend = COALESCE(:vendedor, "+
				"             pv.id_pessoa_vend) "+
				"         GROUP BY "+varGroup+" "+
				"     UNION ALL "+
				"         select "+
				"             "+varCampos+" "+
				"             0 valor, "+
				"             0 as valorbruto, "+
				"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as vlDevolvido, "+
				"             0 as desconto "+
				"         from "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             NFVENDAITEM nfi, "+
				"             NFVENDA nf, "+
				"             PRODUTO PR , "+
				"             linhaproduto lp, "+
				" 	 		  linhaproduto dep,  "+
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
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"    		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
				"             AND pr.id_pessoa_forn = p.id_pessoa "+
				"             and cl.id_pessoa = bd.id_pessoa_cli "+
				"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
				"             and bd.id_pessoa_vend = v.id_pessoa "+
				"             and v.id_pessoa = vnd.id_pessoa "+
				"             and tv.id_tipovendedor = v.id_tipovendedor "+
				"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
				"             and bd.id_pessoa_emp = COALESCE(:id_pessoa_emp,bd.id_pessoa_emp) "+
				"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
				"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"         group by "+varGroup+" "+
				"     union all "+
				"         select "+
				"             "+varCampos+" "+
				"             0 valor, "+
				"             0 as valorbruto, "+
				"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as vlDevolvido, "+
				"             0 as desconto "+
				"         from "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             ecf_vendasitem evi, "+
				"             ecf_vendas ev, "+
				"             PRODUTO PR , "+
				"             linhaproduto lp, "+
				" 	 		  linhaproduto dep,  "+
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
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"    		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
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
				"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"         group by "+varGroup+" "+
				"     union all "+
				"         select "+
				"             "+varCampos+" "+
				"             0 valor, "+
				"             0 as valorbruto, "+
				"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as vlDevolvido, "+
				"             0 as desconto "+
				"         from "+
				"             BOLETIMDEVOLUCAO bd, "+
				"             BOLETIMDEVOLITEM bdI, "+
				"             pedvendaitem pvi, "+
				"             pedvenda pv, "+
				"             PRODUTO PR , "+
				"             linhaproduto lp, "+
				" 	 		  linhaproduto dep,  "+
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
				"             and pr.id_linhaproduto = lp.id_linhaproduto "+
				"    		  AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
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
				"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
				"             and bd.MOMENTO between :dt1 and :dt2 "+
				"         group by "+varGroup+" "+			
				"		) tab "+
				"     GROUP BY "+
				"         tab.id "+
				"   ) tabresult "+
				"   GROUP BY tabresult.id "+
				"     ORDER BY 3 DESC ";
		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE);
		
			if(empresa!=null) {
				query.setParameter("id_pessoa_emp", empresa.getId());
			}else{
				query.setParameter("id_pessoa_emp", null);
			}
			
			if(vendedor!=null) {
				query.setParameter("vendedor", vendedor.getId());
			}else {
				query.setParameter("vendedor", null);
			}	
			
			if(tipoVendedorFilter!=null) {
				query.setParameter("tipovendedor", tipoVendedorFilter.getId());
			}else {
				query.setParameter("tipovendedor", null);
			}
			
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
			query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
			
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornDTO> dashBoardPorMes(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Date cDtMesAnt, Date cDtMesAnt2, Date cDtAnoAnt, Date cDtAnoAnt2, String vendasPorFilter, String segmentoFilter) {
		String sql = "";
		String sqlFiltro1 = "";
		String sqlFiltro2 = "";
		String sqlFiltro3 = "";
		String sqlFiltro4 = "";
		String sqlFiltro5 = "";
		
		if(segmentoFilter!=null && !"".equals(segmentoFilter) && !"null".equals(segmentoFilter)) {
			if("atacado".equals(segmentoFilter)){
				         sqlFiltro2 = " and   ev.id_ecfvendas = -1 ";
				         sqlFiltro3 = " and   ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null)) ";
				         sqlFiltro4 = " and   ev.id_ecfvendas = -1  ";
			}else {
				        sqlFiltro1 = " and   pv.id_pedvenda = -1 ";
				        sqlFiltro3 = " and   bdi.id_ecfvendasitem is not null ";
				        sqlFiltro4 = " and   bdi.id_ecfvendasitem is not null ";
				        if("pedido".equals(vendasPorFilter)) {
				          sqlFiltro5 = " and   pv.id_pedvenda = -1 ";
				        }
			}	        
		}			
		
		if("pedido".equals(vendasPorFilter)) {
			sql =   " select "+
						"    EXTRACT (DAY from tab.data) descricao, "+
						"  	 coalesce(SUM(tab.faturamento-tab.devolucao), 0.0) as valor "+
					" from  "+
						" (   "+
						"  select   "+
						" 	 cast(PV.EFETIVACAO as date) data, "+
						" 	 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) faturamento, "+
						" 	 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.CUSTOGERULTCOMPRAUV as numeric(18,4)) else 0 end) VALORCUSTO, "+
						" 	 0 as devolucao, "+
						" 	 0 as VALCUSTODEVOLVIDO, "+
						" 	 CAST(SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO * pvi.percdesconto as numeric(18, 4)) else 0 end) / SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else NULL end) AS numeric(18,4)) as desconto  "+
						"  from   "+
							 " PEDVENDA PV,   "+
						" 	 PEDVENDAITEM PVI,   "+
						" 	 PRODUTO PR,   "+
						" 	 PEDVENDASTATUS PVS,   "+
						" 	 TIPOMOVFISC tmf,   "+
						" 	 linhaproduto lp,   "+
						" 	 linhaproduto dep,  "+
						" 	 cliente cl,   "+
						" 	 gestaovendamob gvm,   "+
						" 	 vendedor v,   "+
						" 	 pessoa vnd,  "+
						" 	 pessoa frn,  "+
						" 	 tipooperacaofiscal tof   "+
						"  where PV.ID_PEDVENDA = PVI.ID_PEDVENDA   "+
						"    and pr.id_linhaproduto = lp.id_linhaproduto   "+
						"    and dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
						"    and PVI.ID_PRODUTO = PR.ID_PRODUTO   "+
						"    and PV.ID_PEDVENDASTATUS = PVS.ID_PEDVENDASTATUS   "+
						"    and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC   "+
						"    and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal   "+
						"    and cl.id_pessoa = pv.id_pessoa_cli   "+
						"    and pv.id_pessoa_vend = v.id_pessoa   "+
						"    and v.id_pessoa = vnd.id_pessoa  "+
						"    and pr.id_pessoa_forn = frn.id_pessoa  "+
						"    and gvm.id_gestaovendamob = v.id_gestaovendamob   "+
						"    and tmf.CLASSE in (0, 1)   "+
						"    and PVS.EFETIVADO = 1   "+
						"    and pv.id_pessoa_emp = COALESCE(:id_pessoa, pv.id_pessoa_emp)  "+
						"    and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)    "+
						" 	 AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"    and PV.EFETIVACAO between :dt1 and :dt2   "+
						"    "+sqlFiltro1+" "+
						"  group by cast(PV.EFETIVACAO as date) "+
						" union all   "+
						" select   "+
						"        cast(ev.datavenda as date) data, "+
						"        SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as faturamento, "+
						"        SUM(cast(((eVI.QUANTIDADE-coalesce(evi.qtdpedido, 0)) * evi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) VALORCUSTO, "+
						" 	 0 as devolucao,  "+
						" 	0 as VALCUSTODEVOLVIDO,  "+
						" 	 0 as desconto  "+
						"  from   "+
							 " ECF_VENDAS ev,   "+
						" 	 ECF_VENDASITEM eVI,   "+
						" 	 PRODUTO PR ,   "+
						" 	 linhaproduto lp,   "+
						" 	 linhaproduto dep,  "+
						" 	 cliente cl,   "+
						" 	 gestaovendamob gvm,   "+
						" 	 vendedor v,  "+
						" 	 pessoa vnd,  "+
						" 	 pessoa frn  "+
						"  where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS   "+
						"     and pr.id_linhaproduto = lp.id_linhaproduto   "+
						"     and dep.codedt=substring(lp.codedt FROM 1 FOR 6)                              "+
						"       and eVI.ID_PRODUTO = PR.ID_PRODUTO   "+
						"     and cl.id_pessoa = ev.id_pessoa_cli   "+
						"     and ev.CANCELADA = 0   "+
						"     and evi.CANCELADA = 0   "+
						"     and ev.CONCLUIDA = 1   "+
						"     and coalesce(ev.ID_DAV, 0) = 0   "+
						"     and ev.isvenda = 1   "+
						"     and ev.id_pessoa_vend = v.id_pessoa   "+
						"       and v.id_pessoa = vnd.id_pessoa  "+
						"     and pr.id_pessoa_forn = frn.id_pessoa  "+
						"     and ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp)   "+
						"     and ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)    "+
						" 	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     and gvm.id_gestaovendamob = v.id_gestaovendamob   "+
						"     and ev.DATAVENDA between :dt1 and :dt2   "+
						"     "+sqlFiltro2+" "+
						"  group by cast(ev.datavenda as date) "+
						" union all  "+
						"  select   "+
						"        cast(bd.MOMENTO as date) data, "+
						" 	 0 as faturamento,  "+
						" 	0 VALORCUSTO,  "+
						" 	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao,  "+
						" 	SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO,  "+
						" 	 0 as desconto  "+
						"  from   "+
							 " BOLETIMDEVOLUCAO bd,   "+
						" 	 BOLETIMDEVOLITEM bdI,   "+
						" 	 NFVENDAITEM nfi,   "+
						" 	 NFVENDA nf,   "+
						" 	 PRODUTO PR ,   "+
						" 	 linhaproduto lp,   "+
						" 	 linhaproduto dep,  "+
						" 	 cliente cl,   "+
						" 	 gestaovendamob gvm,   "+
						" 	 vendedor v,   "+
						" 	 pessoa vnd,  "+
						" 	 pessoa frn  "+
						"  where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO   "+
						"   and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM   "+
						"   and nfi.ID_NFVENDA = nf.ID_NFVENDA   "+
						"   and pr.id_linhaproduto = lp.id_linhaproduto   "+
						"   and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
						"   and bdI.ID_PRODUTO = PR.ID_PRODUTO   "+
						"   and cl.id_pessoa = bd.id_pessoa_cli   "+
						"   and bd.ID_BOLETIMDEVOLSTATUS <> 1   "+
						"   and bd.id_pessoa_vend = v.id_pessoa   "+
						"   and v.id_pessoa = vnd.id_pessoa  "+
						"   and pr.id_pessoa_forn = frn.id_pessoa                              "+
						"   and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)   "+
						"   and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)    "+
						" 	AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"   and gvm.id_gestaovendamob = v.id_gestaovendamob   "+
						"   and ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null))   "+
						"   and bd.MOMENTO between :dt1 and :dt2   "+
						"   "+sqlFiltro3+" "+
						" group by cast(bd.MOMENTO as date) "+
						" union all  "+
						"  select   "+
						"       cast(bd.MOMENTO as date) data, "+
						" 	 0 faturamento,   "+
						" 	0 VALORCUSTO,  "+
						" 	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucao,  "+
						" 	SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) as VALCUSTODEVOLVIDO,  "+
						" 	 0 as desconto  "+
						"  from   "+
							 " BOLETIMDEVOLUCAO bd,   "+
						" 	 BOLETIMDEVOLITEM bdI,   "+
						" 	 ecf_vendasitem evi,   "+
						" 	 ecf_vendas ev,   "+
						" 	 PRODUTO PR ,   "+
						" 	 linhaproduto lp,   "+
						" 	 linhaproduto dep,  "+
						" 	 cliente cl,   "+
						" 	 gestaovendamob gvm,   "+
						" 	 vendedor v,   "+
						" 	 pessoa vnd,  "+
						" 	 pessoa frn  "+
						" where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO   "+
						"   and bdi.id_ecfvendasitem = evi.id_ecfvendasitem   "+
						"   and evi.id_ecfvendas = ev.id_ecfvendas   "+
						"   and pr.id_linhaproduto = lp.id_linhaproduto   "+
						"   and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
						"   and bdI.ID_PRODUTO = PR.ID_PRODUTO   "+
						"   and cl.id_pessoa = bd.id_pessoa_cli   "+
						"   and bd.ID_BOLETIMDEVOLSTATUS <> 1   "+
						"   and bd.id_pessoa_vend = v.id_pessoa   "+
						"   and v.id_pessoa = vnd.id_pessoa  "+
						"   and pr.id_pessoa_forn = frn.id_pessoa                              "+
						"   and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)   "+
						"   AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)    "+
						" 	AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"   and gvm.id_gestaovendamob = v.id_gestaovendamob   "+
						"   and bd.MOMENTO between :dt1 and :dt2   "+
						"   "+sqlFiltro4+" "+
						" group by cast(bd.MOMENTO as date) "+
						" union all  "+
						"  select   "+
						"        cast(bd.MOMENTO as date) data, "+
						" 	 0 faturamento,   "+
						" 	0 VALORCUSTO,  "+
						" 	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao,  "+
						" 	SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO,  "+
						" 	 0 as desconto  "+
						"  from   "+
							 " BOLETIMDEVOLUCAO bd,   "+
						" 	 BOLETIMDEVOLITEM bdI,   "+
						" 	 pedvendaitem pvi,   "+
						" 	 pedvenda pv,   "+
						" 	 PRODUTO PR ,   "+
						" 	 linhaproduto lp,   "+
						" 	 linhaproduto dep,  "+
						" 	 cliente cl,   "+
						" 	 gestaovendamob gvm,   "+
						" 	 vendedor v,   "+
						" 	 pessoa vnd,  "+
						" 	 pessoa frn  "+
						" where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO   "+
						"    and bdi.id_pedvendaitem = pvi.id_pedvendaitem   "+
						"    and pvi.id_pedvenda = pv.id_pedvenda   "+
						"    and pr.id_linhaproduto = lp.id_linhaproduto   "+
						"    and dep.codedt=substring(lp.codedt FROM 1 FOR 6)  "+
						"    and bdI.ID_PRODUTO = PR.ID_PRODUTO   "+
						"    and cl.id_pessoa = bd.id_pessoa_cli   "+
						"    and bd.ID_BOLETIMDEVOLSTATUS <> 1   "+
						"    and bd.id_pessoa_vend = v.id_pessoa   "+
						"    and v.id_pessoa = vnd.id_pessoa  "+
						"    and pr.id_pessoa_forn = frn.id_pessoa                              "+
						"    and gvm.id_gestaovendamob = v.id_gestaovendamob   "+
						"    and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)   "+
						"    and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)    "+
						" 	 AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"    and bd.MOMENTO between :dt1 and :dt2   "+
						"    "+sqlFiltro5+" "+
						" group by cast(bd.MOMENTO as date) "+
						" )tab  "+
						" group by EXTRACT (DAY from tab.data) "+
						" order by 1 ";					
		}else {
		
				  sql = " SELECT   EXTRACT (DAY from tab.datavenda) as descricao, "+
						"          COALESCE(Sum(tab.faturamento-tab.devolucao), 0.0) AS valor "+
						" FROM     ( "+
						"       SELECT   cast(ev.datavenda as date) datavenda, "+
						"                Sum(evi.valorliquidoitem) AS faturamento, "+
						"                Sum(Cast((evi.quantidade * evi.custogerultcomprauv) AS NUMERIC(18,4))) as valorcusto, "+
						"                0 AS devolucao "+
						"       FROM     ecf_vendas ev, "+
						"                ecf_vendasitem evi, "+
						"                produto pr, "+
						"                linhaproduto lp, "+
						"                linhaproduto dep, "+
						"                pessoa vnd, "+
						"                pessoa frn, "+
						"			     vendedor v "+
						"       WHERE    evi.id_ecfvendas = ev.id_ecfvendas "+
						"       AND      ev.id_pessoa_vend = vnd.id_pessoa "+
						"       AND      evi.id_produto = pr.id_produto "+
						"       AND      pr.id_linhaproduto = lp.id_linhaproduto "+
						"       AND      dep.codedt=substring(lp.codedt from 1 FOR 6) "+
						"       AND      pr.id_pessoa_forn = frn.id_pessoa "+
						"       AND      ev.datavenda BETWEEN :dt1 AND      :dt2 "+
						"       AND      ev.concluida = 1 "+
						"       AND      ev.cancelada = 0 "+
						"       AND      evi.cancelada = 0 "+
						"       AND      ev.isvenda = 1 "+
						"       AND      COALESCE(ev.id_dav,0) = 0 "+
						" 	    AND      ev.id_pessoa_vend = v.id_pessoa "+
				    	"       AND      v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND      ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp) "+
						"       AND      ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend) "+
						"       "+sqlFiltro2+" "+
						"       GROUP BY cast(ev.datavenda as date) "+
						"       UNION ALL "+
						"       SELECT   cast(nfv.dataemiss as date) as datavenda, "+
						"                sum( "+
						"                CASE tmf.classe "+
						"                         WHEN 0 THEN cast(trunc((nfvi.quantidade * nfvi.preco), 2) AS numeric(18,4)) "+
						"                         ELSE 0 "+
						"                END)                                                                  AS faturamento, "+
						"                sum(cast((nfvi.quantidade * nfvi.customedioonline) AS numeric(18,4)))    valorcusto, "+
						"                0                                                                     AS devolucao "+
						"       FROM     nfvenda nfv, "+
						"                nfvendaitem nfvi, "+
						"                tipomovfisc tmf, "+
						"                pedvenda pv, "+
						"                produto pr, "+
						"                linhaproduto lp, "+
						"                linhaproduto dep, "+
						"                pessoa vnd, "+
						"                pessoa frn, "+
						"			     vendedor v "+
						"       WHERE    nfv.id_nfvenda = nfvi.id_nfvenda "+
						"       AND      nfv.id_tipomovfisc = tmf.id_tipomovfisc "+
						"       AND      nfv.id_pedvenda = pv.id_pedvenda "+
						"       AND      pv.id_pessoa_vend = vnd.id_pessoa "+
						"       AND      nfvi.id_produto = pr.id_produto "+
						"       AND      pr.id_linhaproduto = lp.id_linhaproduto "+
						"       AND      pr.id_pessoa_forn = frn.id_pessoa "+
						"       AND      dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
						"       AND      nfv.dataemiss BETWEEN :dt1 AND      :dt2 "+
						"       AND      nfv.cancelada = 0 "+
						"       AND      tmf.classe = 0 "+
						"       AND      nfv.tipo = 'S' "+
						" 	    AND      pv.id_pessoa_vend = v.id_pessoa "+
				    	"       AND      v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND      nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp) "+
						"       AND      pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) "+
						"       "+sqlFiltro1+" "+
						"       GROUP BY cast(nfv.dataemiss as date) "+
						"       UNION ALL "+
						"       SELECT   cast(bd.momento as date) as datavenda, "+
						"                0                                                           AS faturamento, "+
						"                0                                                                      AS valorcusto, "+
						"                sum(bdi.quantidade * bdi.valorunit)                                    AS devolucao "+
						"       FROM     boletimdevolucao bd, "+
						"                boletimdevolitem bdi, "+
						"                tipomovfisc tmf, "+
						"                produto pr, "+
						"                linhaproduto lp, "+
						"                linhaproduto dep, "+
						"                pessoa vnd, "+
						"                pessoa frn, "+
						"				 vendedor v "+
						"       WHERE    bd.id_boletimdevolucao = bdi.id_boletimdevolucao "+
						"       AND      tmf.id_tipomovfisc = bd.id_tipomovfisc "+
						"       AND      bdi.id_produto = pr.id_produto "+
						"       AND      pr.id_linhaproduto = lp.id_linhaproduto "+
						"       AND      dep.codedt=substring(lp.codedt FROM 1 FOR 6) "+
						"       AND      bd.id_pessoa_vend = vnd.id_pessoa "+
						"       AND      pr.id_pessoa_forn = frn.id_pessoa "+
						"       AND      tmf.altestqfisico = 1 "+
						"       AND      bd.momento BETWEEN :dt1 AND      :dt2 "+
						" 	    AND      bd.id_pessoa_vend = v.id_pessoa "+
				    	"       AND      v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND      bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp) "+
						"       AND      bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend) "+
						"       AND      bd.id_boletimdevolstatus <> 1 "+
						"       "+sqlFiltro3+" "+
						"       GROUP BY cast(bd.momento as date) "+
						" )tab "+
						" GROUP BY EXTRACT (DAY from tab.datavenda) "+
						" ORDER BY 1 ";				
		}		
		
		
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE);
		
			if(empresaFilter!=null) {
				query.setParameter("id_pessoa", empresaFilter);
			}else{
				query.setParameter("id_pessoa", null);
			}
			
			if(vendedorFilter!=null) {
				query.setParameter("vendedor", vendedorFilter);
			}else {
				query.setParameter("vendedor", null);
			}	
			
			if(tipoVendedorFilter!=null) {
				query.setParameter("tipovendedor", tipoVendedorFilter.getId());
			}else {
				query.setParameter("tipovendedor", null);
			}

			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
			
			query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
			
		return query.list();
	}

	@Override
	public List<VendaFornDTO> dashBoardPorMesAno(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Date cDtMesAnt, Date cDtMesAnt2, Date cDtAnoAnt, Date cDtAnoAnt2, String vendasPorFilter, String segmentoFilter) {
			String sql = "";
			String sqlFiltro1 = "";
			String sqlFiltro2 = "";
			String sqlFiltro3 = "";
			String sqlFiltro4 = "";
			String sqlFiltro5 = "";
			
			if(segmentoFilter!=null && !"".equals(segmentoFilter) && !"null".equals(segmentoFilter)) {
				if("atacado".equals(segmentoFilter)){
					         sqlFiltro2 = " and   ev.id_ecfvendas = -1 ";
					         sqlFiltro3 = " and   ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null)) ";
					         sqlFiltro4 = " and   ev.id_ecfvendas = -1  ";
				}else {
					        sqlFiltro1 = " and   pv.id_pedvenda = -1 ";
					        sqlFiltro3 = " and   bdi.id_ecfvendasitem is not null ";
					        sqlFiltro4 = " and   bdi.id_ecfvendasitem is not null ";
					        if("pedido".equals(vendasPorFilter)) {
					          sqlFiltro5 = " and   pv.id_pedvenda = -1 ";
					        }
				}	        
			}			
			
			if("pedido".equals(vendasPorFilter)) {
				sql = " select "+
						"     'Total' as descricao, "+
						"     tab2.faturamento-tab2.devolucao valor, "+
						"     tab2.fatAnt-tab2.devolucaoAnt as valorMesAnt, "+
						"     tab2.fatAnoAnt-tab2.devolucaoAnoAnt as valorAnoAnt "+
						" from "+
						"     ( "+
						"     select "+
						"         coalesce(SUM(tab.faturamento), 0.0) as faturamento, "+
						"         coalesce(SUM(tab.fatAnt), 0.0) as fatAnt, "+
						"         coalesce(SUM(tab.fatAnoAnt), 0.0) as fatAnoAnt, "+
						"         coalesce(SUM(tab.devolucao), 0.0) as devolucao, "+
						"         coalesce(SUM(tab.devolucaoAnt), 0.0) as devolucaoAnt, "+
						"         coalesce(SUM(tab.devolucaoAnoAnt), 0.0) as devolucaoAnoAnt "+
						"     from "+
						"         ( "+
						"         select "+
						"             SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             PEDVENDA PV, "+
						"             PEDVENDAITEM PVI, "+
						"             PRODUTO PR, "+
						"             PEDVENDASTATUS PVS, "+
						"             TIPOMOVFISC tmf, "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v, "+
						"             tipooperacaofiscal tof "+
						"             where PV.ID_PEDVENDA = PVI.ID_PEDVENDA "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and PVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and PV.ID_PEDVENDASTATUS = PVS.ID_PEDVENDASTATUS "+
						"             and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
						"             and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal "+
						"             and cl.id_pessoa = pv.id_pessoa_cli "+
						"             and pv.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and tmf.CLASSE in (0, 1) "+
						"             and PVS.EFETIVADO = 1 "+
						"             and pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)   "+
						"             and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and PV.EFETIVACAO between :dt1 AND :dt2 "+
						"            "+sqlFiltro1+" "+
						"     union all "+
						"         select "+
						"             SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             ECF_VENDAS ev, "+
						"             ECF_VENDASITEM eVI, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and eVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = ev.id_pessoa_cli "+
						"             and ev.CANCELADA = 0 "+
						"             and evi.CANCELADA = 0 "+
						"             and ev.CONCLUIDA = 1 "+
						"             and coalesce(ev.ID_DAV, 0) = 0 "+
						"             and ev.isvenda = 1 "+
						"             and ev.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and ev.id_pessoa_emp = COALESCE(:id_pessoa,ev.id_pessoa_emp)   "+
						"             and ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and ev.DATAVENDA between :dt1 AND :dt2 "+
						"            "+sqlFiltro2+" "+						
						"     union all "+
						"         select "+
						"             0 as faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             NFVENDAITEM nfi, "+
						"             NFVENDA nf, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
						"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt1 AND :dt2 "+
						"            "+sqlFiltro3+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             ecf_vendasitem evi, "+
						"             ecf_vendas ev, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
						"             and evi.id_ecfvendas = ev.id_ecfvendas "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt1 AND :dt2 "+
						"            "+sqlFiltro4+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             pedvendaitem pvi, "+
						"             pedvenda pv, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
						"             and pvi.id_pedvenda = pv.id_pedvenda "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt1 AND :dt2 "+
						"            "+sqlFiltro5+" "+
						"         UNION ALL "+
						"             select "+
						"                 0 faturamento, "+
						"                 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) as fatAnt, "+
						"                 0 as fatAnoAnt, "+
						"                 0 as devolucao, "+
						"                 0 as devolucaoAnt, "+
						"                 0 as devolucaoAnoAnt "+
						"             from "+
						"                 PEDVENDA PV, "+
						"                 PEDVENDAITEM PVI, "+
						"                 PRODUTO PR, "+
						"                 PEDVENDASTATUS PVS, "+
						"                 TIPOMOVFISC tmf, "+
						"                 linhaproduto lp, "+
						"                 cliente cl, "+
						"                 gestaovendamob gvm, "+
						"                 vendedor v, "+
						"                 tipooperacaofiscal tof "+
						"            where PV.ID_PEDVENDA = PVI.ID_PEDVENDA "+
						"                 and pr.id_linhaproduto = lp.id_linhaproduto "+
						"                 and PVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"                 and PV.ID_PEDVENDASTATUS = PVS.ID_PEDVENDASTATUS "+
						"                 and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
						"                 and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal "+
						"                 and cl.id_pessoa = pv.id_pessoa_cli "+
						"                 and pv.id_pessoa_vend = v.id_pessoa "+
						"                 and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"                 and tmf.CLASSE in (0, 1) "+
						"                 and PVS.EFETIVADO = 1 "+
						"                 and pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)   "+
						"                 and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"       	      AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"                 and PV.EFETIVACAO between :dt3 AND :dt4 "+
						"                 "+sqlFiltro1+" "+
						"         union all "+
						"             select "+
						"                 0 as faturamento, "+
						"                 SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as fatAnt, "+
						"                 0 as fatAnoAnt, "+
						"                 0 as devolucao, "+
						"                 0 as devolucaoAnt, "+
						"                 0 as devolucaoAnoAnt "+
						"             from "+
						"                 ECF_VENDAS ev, "+
						"                 ECF_VENDASITEM eVI, "+
						"                 PRODUTO PR , "+
						"                 linhaproduto lp, "+
						"                 cliente cl, "+
						"                 gestaovendamob gvm, "+
						"                 vendedor v "+
						"             where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS "+
						"                 and pr.id_linhaproduto = lp.id_linhaproduto "+
						"                 and eVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"                 and cl.id_pessoa = ev.id_pessoa_cli "+
						"                 and ev.CANCELADA = 0 "+
						"                 and evi.CANCELADA = 0 "+
						"                 and ev.CONCLUIDA = 1 "+
						"                 and coalesce(ev.ID_DAV, 0) = 0 "+
						"                 and ev.isvenda = 1 "+
						"                 and ev.id_pessoa_vend = v.id_pessoa "+
						"                 and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"                 and ev.id_pessoa_emp = COALESCE(:id_pessoa,ev.id_pessoa_emp)   "+
						"                 and ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)   "+
						"       	      AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"                 and ev.DATAVENDA between :dt3 AND :dt4 "+
						"                 "+sqlFiltro2+" "+						
						"         union all "+
						"              select "+
						"             0 as faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             NFVENDAITEM nfi, "+
						"             NFVENDA nf, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
						"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt3 AND :dt4 "+
						"            "+sqlFiltro3+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             ecf_vendasitem evi, "+
						"             ecf_vendas ev, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
						"             and evi.id_ecfvendas = ev.id_ecfvendas "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt3 AND :dt4 "+
						"            "+sqlFiltro4+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnt, "+
						"             0 as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             pedvendaitem pvi, "+
						"             pedvenda pv, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
						"             and pvi.id_pedvenda = pv.id_pedvenda "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt3 AND :dt4 "+
						"            "+sqlFiltro5+" "+
						"        UNION ALL "+
						"             select "+
						"                 0 faturamento, "+
						"                 0 as fatAnt, "+
						"                 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) as fatAnoAnt, "+
						"                 0 as devolucao, "+
						"                 0 as devolucaoAnt, "+
						"                 0 as devolucaoAnoAnt "+
						"             from "+
						"                 PEDVENDA PV, "+
						"                 PEDVENDAITEM PVI, "+
						"                 PRODUTO PR, "+
						"                 PEDVENDASTATUS PVS, "+
						"                 TIPOMOVFISC tmf, "+
						"                 linhaproduto lp, "+
						"                 cliente cl, "+
						"                 gestaovendamob gvm, "+
						"                 vendedor v, "+
						"                 tipooperacaofiscal tof "+
						"            where PV.ID_PEDVENDA = PVI.ID_PEDVENDA "+
						"                 and pr.id_linhaproduto = lp.id_linhaproduto "+
						"                 and PVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"                 and PV.ID_PEDVENDASTATUS = PVS.ID_PEDVENDASTATUS "+
						"                 and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
						"                 and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal "+
						"                 and cl.id_pessoa = pv.id_pessoa_cli "+
						"                 and pv.id_pessoa_vend = v.id_pessoa "+
						"                 and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"                 and tmf.CLASSE in (0, 1) "+
						"                 and PVS.EFETIVADO = 1 "+
						"                 and pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)   "+
						"                 and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"       	      AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"                 and PV.EFETIVACAO between :dt5 AND :dt6 "+
						"                 "+sqlFiltro1+" "+
						"         union all "+
						"             select "+
						"                 0 as faturamento, "+
						"                 0 as fatAnt, "+
						"                 SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) fatAnoAnt, "+
						"                 0 as devolucao, "+
						"                 0 as devolucaoAnt, "+
						"                 0 as devolucaoAnoAnt "+
						"             from "+
						"                 ECF_VENDAS ev, "+
						"                 ECF_VENDASITEM eVI, "+
						"                 PRODUTO PR , "+
						"                 linhaproduto lp, "+
						"                 cliente cl, "+
						"                 gestaovendamob gvm, "+
						"                 vendedor v "+
						"             where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS "+
						"                 and pr.id_linhaproduto = lp.id_linhaproduto "+
						"                 and eVI.ID_PRODUTO = PR.ID_PRODUTO "+
						"                 and cl.id_pessoa = ev.id_pessoa_cli "+
						"                 and ev.CANCELADA = 0 "+
						"                 and evi.CANCELADA = 0 "+
						"                 and ev.CONCLUIDA = 1 "+
						"                 and coalesce(ev.ID_DAV, 0) = 0 "+
						"                 and ev.isvenda = 1 "+
						"                 and ev.id_pessoa_vend = v.id_pessoa "+
						"                 and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"                 and ev.id_pessoa_emp = COALESCE(:id_pessoa,ev.id_pessoa_emp)   "+
						"                 and ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)   "+
						"       	      AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"                 and ev.DATAVENDA between :dt5 AND :dt6 "+
						"                 "+sqlFiltro2+" "+
						"         union all "+
						"              select "+
						"             0 as faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             NFVENDAITEM nfi, "+
						"             NFVENDA nf, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
						"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt5 AND :dt6 "+
						"            "+sqlFiltro3+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             ecf_vendasitem evi, "+
						"             ecf_vendas ev, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
						"             and evi.id_ecfvendas = ev.id_ecfvendas "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt5 AND :dt6 "+
						"            "+sqlFiltro4+" "+
						"     union all "+
						"         select "+
						"             0 faturamento, "+
						"             0 as fatAnt, "+
						"             0 as fatAnoAnt, "+
						"             0 as devolucao, "+
						"             0 as devolucaoAnt, "+
						"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnoAnt "+
						"         from "+
						"             BOLETIMDEVOLUCAO bd, "+
						"             BOLETIMDEVOLITEM bdI, "+
						"             pedvendaitem pvi, "+
						"             pedvenda pv, "+
						"             PRODUTO PR , "+
						"             linhaproduto lp, "+
						"             cliente cl, "+
						"             gestaovendamob gvm, "+
						"             vendedor v "+
						"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
						"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
						"             and pvi.id_pedvenda = pv.id_pedvenda "+
						"             and pr.id_linhaproduto = lp.id_linhaproduto "+
						"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
						"             and cl.id_pessoa = bd.id_pessoa_cli "+
						"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
						"             and bd.id_pessoa_vend = v.id_pessoa "+
						"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
						"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
						"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
						"       	  AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"             and bd.MOMENTO between :dt5 AND :dt6 "+
						"            "+sqlFiltro5+" "+
						"          )tab "+
						"       ) tab2 ";


						
			}else {
					
				sql = " SELECT tab2.datavenda as descricao, "+
						"        SUM(tab2.faturamento-tab2.valorDev) AS valor, "+
						"        SUM(tab2.fatAnt-tab2.valorDevAnt) AS valorAnoAnt, "+
						"        SUM(tab2.fatMesAnt-tab2.valorDevMesAnt) AS valorMesAnt "+					
						" FROM ( "+
						" SELECT "+
						" tab.datavenda, "+
						" COALESCE(SUM(tab.faturamento), 0.0) AS faturamento, "+
						" COALESCE(SUM(tab.valorDev), 0.0) AS valorDev, "+
						" COALESCE(SUM(tab.fatAnt), 0.0) AS fatAnt,    "+
						" COALESCE(SUM(tab.valorDevAnt), 0.0) AS valorDevAnt,    "+
						" COALESCE(SUM(tab.fatMesAnt), 0.0) AS fatMesAnt, "+
						" COALESCE(SUM(tab.valorDevMesAnt), 0.0) AS valorDevMesAnt "+
						" FROM ( "+
						" SELECT 1 datavenda, "+
						"     SUM(ecfi.valorliquidoitem) AS faturamento,  "+
						"     0 AS valorDev,  "+
						"     0 AS fatAnt,  "+
						"     0 AS valorDevAnt,  "+
						"     0 AS fatMesAnt, "+
						"     0 AS valorDevMesAnt "+						
						"   FROM ecf_vendas ecf,    "+
						"        ecf_vendasitem ecfi,  "+
						"		 vendedor v "+
						"   WHERE ecfi.id_ecfvendas = ecf.id_ecfvendas    "+
						"     AND ecf.datavenda between :dt1 AND :dt2    "+
						"     AND ecf.concluida = 1    "+
						"     AND ecf.cancelada = 0    "+
						"     AND ecfi.cancelada = 0   "+
						"     AND ecf.id_pessoa_emp = COALESCE(:id_pessoa, ecf.id_pessoa_emp)  "+
						"     AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  "+
						"     AND ecf.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"   group by 1 "+
						" UNION ALL    "+
						" SELECT 1 datavenda, "+
						"     	 SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS faturamento,   "+
						"     	 0 AS valorDev,  "+
						"     	 0 AS fatAnt,  "+
						"     	 0 AS valorDevAnt,  "+
						"     	 0 AS fatMesAnt, "+
						"     	 0 AS valorDevMesAnt "+						
						"     FROM NFVENDA nfv, "+
						"      NFVENDAITEM nfvi,    "+
						"      TIPOMOVFISC tmf,   "+
						"      PEDVENDA pv,   "+
						"      vendedor v "+
						"     WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
						"       AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
						"       AND nfv.id_pedvenda = pv.id_pedvenda   "+
						"       AND nfv.DATAEMISS between :dt1 AND :dt2 "+
						"       AND nfv.CANCELADA = 0    "+
						"       AND tmf.CLASSE = 0  "+
						"       AND nfv.TIPO = 'S'    "+
						"       AND pv.id_pessoa_vend = v.id_pessoa "+
				    	"       AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)   "+
						"       AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"   group by 1 "+
						" UNION ALL   "+
						" SELECT 1, "+
						"        0 AS faturamento,    "+
						"        SUM(bi.quantidade * bi.valorunit) AS valorDev, "+
						"        0 AS fatAnt,   "+
						"        0 AS valorDevAnt, "+
						"        0 fatMesAnt, "+
						"        0 AS valorDevMesAnt "+
						"   FROM boletimdevolucao b, "+
						"        boletimdevolitem bi,    "+
						"        tipomovfisc tmf,   "+
						"        vendedor v "+
						"   WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao    "+
						"     AND tmf.id_tipomovfisc = b.id_tipomovfisc "+
						"     AND tmf.altestqfisico = 1 "+
						"     AND b.momento between :dt1 AND :dt2    "+
						"     AND b.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     AND b.id_pessoa_emp = COALESCE(:id_pessoa,b.id_pessoa_emp)   "+
						"     AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend)   "+
						"     AND b.id_boletimdevolstatus <> 1 "+
						" group by 1    "+
						" UNION ALL   "+
						" SELECT 1 datavenda, "+
						"     	 0 AS faturamento,   "+
						"     	 0 AS valorDev,  "+
						"     	 SUM(ecfi.valorliquidoitem) AS fatAnt,  "+
						"     	 0 AS valorDevAnt,  "+
						"     	 0 AS fatMesAnt, "+
						"     	 0 AS valorDevMesAnt "+						
						"   FROM ecf_vendas ecf,    "+
						"        ecf_vendasitem ecfi,  "+
						"		 vendedor v "+
						"   WHERE ecfi.id_ecfvendas = ecf.id_ecfvendas    "+
						"     AND ecf.datavenda between :dt3 AND :dt4 "+
						"     AND ecf.concluida = 1    "+
						"     AND ecf.cancelada = 0    "+
						"     AND ecfi.cancelada = 0   "+
						"     AND ecf.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     AND ecf.id_pessoa_emp = COALESCE(:id_pessoa, ecf.id_pessoa_emp)  "+
						"     AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  "+
						"   group by 1 "+
						" UNION ALL    "+
						" SELECT 1 datavenda, "+
						"     	 0 AS faturamento,   "+
						"     	 0 AS valorDev,  "+
						"     	 SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS fatAnt,  "+
						"     	 0 AS valorDevAnt,  "+
						"     	 0 AS fatMesAnt, "+
						"     	 0 AS valorDevMesAnt "+						
						"     FROM NFVENDA nfv, "+
						"      NFVENDAITEM nfvi,    "+
						"      TIPOMOVFISC tmf,   "+
						"      PEDVENDA pv,   "+
						"      vendedor v "+
						"     WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
						"       AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
						"       AND nfv.id_pedvenda = pv.id_pedvenda   "+
						"       AND nfv.DATAEMISS between :dt3 AND :dt4 "+
						"       AND nfv.CANCELADA = 0    "+
						"       AND tmf.CLASSE = 0  "+
						"       AND nfv.TIPO = 'S'    "+
						"       AND pv.id_pessoa_vend = v.id_pessoa "+
				    	"       AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)   "+
						"       AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"   group by 1 "+
						" UNION ALL   "+
						" SELECT 1, "+
						"        0 AS faturamento,    "+
						"        0 AS valorDev, "+
						"        0 AS fatAnt,   "+
						"        SUM(bi.quantidade * bi.valorunit) AS valorDevAnt, "+
						"        0 fatMesAnt, "+
						"        0 AS valorDevMesAnt "+
						"   FROM boletimdevolucao b, "+
						"        boletimdevolitem bi,    "+
						"        tipomovfisc tmf,   "+
						"		 vendedor v "+
						"   WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao    "+
						"     AND tmf.id_tipomovfisc = b.id_tipomovfisc "+
						"     AND tmf.altestqfisico = 1 "+
						"     AND b.momento between :dt3 AND :dt4    "+
						"     AND b.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     AND b.id_pessoa_emp = COALESCE(:id_pessoa,b.id_pessoa_emp)   "+
						"     AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend)   "+
						"     AND b.id_boletimdevolstatus <> 1 "+
						" group by 1    "+
						" UNION ALL "+
						" SELECT 1 datavenda, "+
						"     	 0 AS faturamento,   "+
						"     	 0 AS valorDev,  "+
						"     	 0 AS fatAnt,  "+
						"     	 0 AS valorDevAnt,  "+
						"     	 SUM(ecfi.valorliquidoitem) AS fatMesAnt, "+
						"     	 0 AS valorDevMesAnt "+						
						"   FROM ecf_vendas ecf,    "+
						"        ecf_vendasitem ecfi,  "+
						"		 vendedor v "+
						"   WHERE ecfi.id_ecfvendas = ecf.id_ecfvendas    "+
						"     AND ecf.datavenda between :dt5 AND :dt6 "+
						"     AND ecf.concluida = 1    "+
						"     AND ecf.cancelada = 0    "+
						"     AND ecfi.cancelada = 0   "+
						"     AND ecf.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     AND ecf.id_pessoa_emp = COALESCE(:id_pessoa, ecf.id_pessoa_emp)  "+
						"     AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend)  "+
						"   group by 1 "+
						" UNION ALL    "+
						" SELECT 1 datavenda, "+
						"     	 0 AS faturamento,   "+
						"     	 0 AS valorDev,  "+
						"     	 0 AS fatAnt,  "+
						"     	 0 AS valorDevAnt,  "+
						"     	 SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS fatMesAnt, "+
						"     	 0 AS valorDevMesAnt "+						
						"     FROM NFVENDA nfv, "+
						"      NFVENDAITEM nfvi,    "+
						"      TIPOMOVFISC tmf,   "+
						"      PEDVENDA pv,   "+
						"      vendedor v "+
						"     WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
						"       AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
						"       AND nfv.id_pedvenda = pv.id_pedvenda   "+
						"       AND nfv.DATAEMISS between :dt5 AND :dt6 "+
						"       AND nfv.CANCELADA = 0    "+
						"       AND tmf.CLASSE = 0  "+
						"       AND nfv.TIPO = 'S'    "+
						"       AND pv.id_pessoa_vend = v.id_pessoa "+
				    	"       AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"       AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)   "+
						"       AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
						"   group by 1 "+
						" UNION ALL   "+
						" SELECT 1, "+
						"        0 AS faturamento,    "+
						"        0 AS valorDev, "+
						"        0 AS fatAnt,   "+
						"        0 AS valorDevAnt, "+
						"        0 fatMesAnt, "+
						"        SUM(bi.quantidade * bi.valorunit) AS valorDevMesAnt "+
						"   FROM boletimdevolucao b, "+
						"        boletimdevolitem bi,    "+
						"        tipomovfisc tmf,   "+
						"	     vendedor v "+
						"   WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao    "+
						"     AND tmf.id_tipomovfisc = b.id_tipomovfisc "+
						"     AND tmf.altestqfisico = 1 "+
						"     AND b.momento between :dt5 AND :dt6    "+
						"     AND b.id_pessoa_vend = v.id_pessoa "+
				    	"     AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) "+
						"     AND b.id_pessoa_emp = COALESCE(:id_pessoa,b.id_pessoa_emp)   "+
						"     AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend)   "+
						"     AND b.id_boletimdevolstatus <> 1 "+
						" group by 1    "+					
						" )tab "+
						" group by tab.datavenda "+
						" ) tab2 "+
						" GROUP BY tab2.datavenda ";
			}
	
			Query query = (Query) session.createSQLQuery(sql.toString())
					.addScalar("descricao", Hibernate.STRING)
					.addScalar("valor", Hibernate.DOUBLE)
					.addScalar("valorAnoAnt", Hibernate.DOUBLE)
					.addScalar("valorMesAnt", Hibernate.DOUBLE);
			
				if(empresaFilter!=null) {
					query.setParameter("id_pessoa", empresaFilter);
				}else{
					query.setParameter("id_pessoa", null);
				}
				
				if(vendedorFilter!=null) {
					query.setParameter("vendedor", vendedorFilter);
				}else {
					query.setParameter("vendedor", null);
				}	
				
				if(tipoVendedorFilter!=null) {
					query.setParameter("tipovendedor", tipoVendedorFilter.getId());
				}else {
					query.setParameter("tipovendedor", null);
				}
			
				query.setParameter("dt1", dataFilter1);
				query.setParameter("dt2", dataFilter2);
				query.setParameter("dt3", cDtMesAnt);
				query.setParameter("dt4", cDtMesAnt2);
				query.setParameter("dt5", cDtAnoAnt);
				query.setParameter("dt6", cDtAnoAnt2);
				
				query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
				
			return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter) {
		String sql = "";
		String sqlFiltro1 = "";
		String sqlFiltro2 = "";
		String sqlFiltro3 = "";
		String sqlFiltro4 = "";
		String sqlFiltro5 = "";
		String varCampos = "";
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
			varCampos = " pr.id_pessoa_forn as id, max(frn.nomefantmnem) as descricao, ";
			varGroup = " pr.id_pessoa_forn ";
			
		}else if (porFilter.equals("vendedor")) {
			varCampos = " vnd.id_pessoa as id, max(vnd.nomefantmnem) as descricao, ";
			varGroup = " vnd.id_pessoa ";
			
		}else if (porFilter.equals("linhaProduto")) {
			varCampos = " dep.id_linhaproduto as id, max(dep.descricao) descricao, ";
			varGroup = " dep.id_linhaproduto ";
		
		}else if (porFilter.equals("tipovend")) {
			varCampos = " tv.id_tipovendedor as id, max(tv.descricao) descricao, ";
			varGroup = " tv.id_tipovendedor ";
			
		}
		
		if("pedido".equals(vendasPorFilter)) {
			sql = 
					" select "+
							"     tab2.id, "+
							"     max(tab2.descricao) as descricao, "+
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
							" 		select "+
								"     tab.id, "+
								"     max(tab.descricao) descricao, "+
								"     coalesce(SUM(tab.faturamento), 0.0) as valor, "+
								"     coalesce(SUM(tab.devolucao), 0.0) as vlDevolvido, "+
								"     coalesce(SUM(tab.VALORCUSTO), 0.0) as VALORCUSTO, "+
								"     ((coalesce(SUM(tab.faturamento), 0.0)-coalesce(SUM(tab.devolucao), 0.0))-coalesce(SUM(tab.VALORCUSTO), 0.0))+ coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as LUCRO, "+
								"     coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as VALCUSTODEVOLVIDO, "+
								"     coalesce(SUM(tab.desconto), 0.0) as desconto "+
								" from "+
								"     (  "+
								"     select  "+
										"   "+varCampos+" "+
										"	 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) faturamento,  "+
										"    SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.CUSTOGERULTCOMPRAUV as numeric(18,4)) else 0 end) VALORCUSTO, "+
										"	 0 as devolucao, "+
										"    0 as VALCUSTODEVOLVIDO, "+									
										"	 CAST(SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO * pvi.percdesconto as numeric(18, 4)) else 0 end) / SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) AS numeric(18,4)) as desconto "+
								"     from  "+
										"	 PEDVENDA PV,  "+
										"	 PEDVENDAITEM PVI,  "+
										"	 PRODUTO PR,  "+
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
								"	   and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
								"	   and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
								"	   and PV.EFETIVACAO between :dt1 and :dt2  "+
								"       "+sqlFiltro1+" "+
								"	 group by "+varGroup+" "+
								" union all  "+
								"    select  "+
										"   "+varCampos+" "+
										"	 SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as faturamento,  "+
										"    SUM(cast(((eVI.QUANTIDADE-coalesce(evi.qtdpedido, 0)) * evi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) VALORCUSTO, "+									
										"	 0 as devolucao, "+
										"    0 as VALCUSTODEVOLVIDO, "+
										"	 0 as desconto "+
								"     from  "+
										"	 ECF_VENDAS ev,  "+
										"	 ECF_VENDASITEM eVI,  "+
										"	 PRODUTO PR ,  "+
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
								"	   and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
								"	    and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
								"	    and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
								"	    and ev.DATAVENDA between :dt1 and :dt2  "+
								"       "+sqlFiltro2+" "+
								"     group by "+varGroup+" "+
								" union all "+
								"     select  "+
										"   "+varCampos+" "+
										"	 0 as faturamento, "+
										"    0 VALORCUSTO, "+
										"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
										"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO, "+
										"	 0 as desconto "+
								"     from  "+
										"	 BOLETIMDEVOLUCAO bd,  "+
										"	 BOLETIMDEVOLITEM bdI,  "+
										"	 NFVENDAITEM nfi,  "+
										"	 NFVENDA nf,  "+
										"	 PRODUTO PR ,  "+
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
								"	  and cl.id_pessoa = bd.id_pessoa_cli  "+
								"	  and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
								"	  and bd.id_pessoa_vend = v.id_pessoa  "+
								"	  and v.id_pessoa = vnd.id_pessoa "+
								"	  and v.id_tipovendedor = tv.id_tipovendedor "+
								"	  and pr.id_pessoa_forn = frn.id_pessoa "+							
								"	  and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
								"	  and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
								"	  and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
								"	  and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
								"	  and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
								"	  and ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null))  "+
								"	  and bd.MOMENTO between :dt1 and :dt2  "+
								"            "+sqlFiltro3+" "+
								"    group by "+varGroup+" "+							
								" union all "+
								"     select  "+
										"   "+varCampos+" "+
										"	 0 faturamento,  "+
										"    0 VALORCUSTO, "+
										"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucao, "+
										"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) as VALCUSTODEVOLVIDO, "+
										"	 0 as desconto "+
								"     from  "+
										"	 BOLETIMDEVOLUCAO bd,  "+
										"	 BOLETIMDEVOLITEM bdI,  "+
										"	 ecf_vendasitem evi,  "+
										"	 ecf_vendas ev,  "+
										"	 PRODUTO PR ,  "+
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
								"	  and cl.id_pessoa = bd.id_pessoa_cli  "+
								"	  and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
								"	  and bd.id_pessoa_vend = v.id_pessoa  "+
								"	  and v.id_pessoa = vnd.id_pessoa "+
								"	  and v.id_tipovendedor = tv.id_tipovendedor "+
								"	  and pr.id_pessoa_forn = frn.id_pessoa "+							
								"	  and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
								"	  AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
								"	  and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
								"	  and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
								"	  and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
								"	  and bd.MOMENTO between :dt1 and :dt2  "+
								"            "+sqlFiltro4+" "+
								"   group by "+varGroup+" "+							
								" union all "+
								"     select  "+
										"   "+varCampos+" "+
										"	 0 faturamento,  "+
										"    0 VALORCUSTO, "+
										"	 SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
										"    SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDO, "+
										"	 0 as desconto "+
								"     from  "+
										"	 BOLETIMDEVOLUCAO bd,  "+
										"	 BOLETIMDEVOLITEM bdI,  "+
										"	 pedvendaitem pvi,  "+
										"	 pedvenda pv,  "+
										"	 PRODUTO PR ,  "+
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
								"	   and cl.id_pessoa = bd.id_pessoa_cli  "+
								"	   and bd.ID_BOLETIMDEVOLSTATUS <> 1  "+
								"	   and bd.id_pessoa_vend = v.id_pessoa  "+
								"	   and v.id_pessoa = vnd.id_pessoa "+
								"	   and v.id_tipovendedor = tv.id_tipovendedor "+
								"	   and pr.id_pessoa_forn = frn.id_pessoa "+							
								"	   and gvm.id_gestaovendamob = v.id_gestaovendamob  "+
								"	   and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp)  "+
								"	   and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
								"	   and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
								"	   and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
								"	   and bd.MOMENTO between :dt1 and :dt2  "+
								"            "+sqlFiltro5+" "+
								"   group by "+varGroup+" "+							
								"   )tab "+
								"  group by tab.id) AS tab2 "+
								"  group by tab2.id "+
								"  order by 3 desc ";

					
		}else {
		
		
			sql =   
					" select "+
							"     tab2.id, "+
							"     max(tab2.descricao) as descricao, "+
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
							" SELECT tab.id, "+
							"    max(tab.descricao) descricao, "+
							"    COALESCE(SUM(tab.faturamento), 0.0) AS valor, "+
							"    COALESCE(SUM(tab.devolucao), 0.0) AS vlDevolvido, "+
							"     coalesce(SUM(tab.VALORCUSTO), 0.0) as VALORCUSTO, "+
							"     ((coalesce(SUM(tab.faturamento), 0.0)-coalesce(SUM(tab.devolucao), 0.0))-coalesce(SUM(tab.VALORCUSTO), 0.0))+ coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as LUCRO, "+
							"     coalesce(SUM(tab.VALCUSTODEVOLVIDO), 0.0) as VALCUSTODEVOLVIDO, "+
							"    COALESCE(SUM(tab.desconto), 0.0) AS desconto "+
							"  FROM ( "+
							"      SELECT "+varCampos+" "+
							"        SUM(evi.valorliquidoitem) AS faturamento,  "+
							"        SUM(cast((evi.QUANTIDADE * evi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALORCUSTO, "+
							"        0 AS devolucao, "+
							"        0 AS VALCUSTODEVOLVIDO, "+
							"        (SELECT CAST(SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO * pvi.percdesconto as numeric(18, 4)) else 0 end) / SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) AS numeric(18,4)) AS desconto "+
							"              FROM pedvenda pv,    "+
							"               pedvendaitem pvi,    "+
							"               pedvendastatus pvs,   "+
							"               tipomovfisc tmf, "+
							"				vendedor v "+
							"         WHERE pv.id_pedvenda = pvi.id_pedvenda     "+
							"               AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS   "+
							"               AND pv.id_tipomovfisc = tmf.id_tipomovfisc   "+
							"               AND pvs.EFETIVADO = 1   "+
							"               AND tmf.classe in (0,1)   "+
							"               AND pv.efetivacao between :dt1 AND :dt2    "+
							"               AND pv.id_pessoa_vend = v.id_pessoa "+
							"               AND pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)    "+
							"	            and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
							"               AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)) AS desconto   "+
							"      FROM ecf_vendas ev,    "+
							"           ecf_vendasitem evi, "+
							"           produto pr, "+
							"           linhaproduto lp, "+
							"           linhaproduto dep, "+
							"           pessoa vnd, "+
							"	 		vendedor v,  "+
							"	 		tipovendedor tv,  "+
							"           pessoa frn "+
							"      WHERE evi.id_ecfvendas = ev.id_ecfvendas "+
							"        and ev.id_pessoa_vend = vnd.id_pessoa "+
							"	     and v.id_pessoa = vnd.id_pessoa "+
							"	     and v.id_tipovendedor = tv.id_tipovendedor "+
							"        and evi.id_produto = pr.id_produto "+
							"        and pr.id_linhaproduto = lp.id_linhaproduto "+
							"        AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
							"        and pr.id_pessoa_forn = frn.id_pessoa "+
							"        AND ev.datavenda between :dt1 AND :dt2    "+
							"        AND ev.concluida = 1    "+
							"        AND ev.cancelada = 0    "+
							"        AND evi.cancelada = 0   "+
							"        AND ev.isvenda = 1  "+
							"        and coalesce(ev.ID_DAV,0) = 0  "+
							"        AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp)  "+
							"        AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)  "+
							"	     and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
							"	     and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"       "+sqlFiltro2+" "+
							"     group by "+varGroup+" "+
							"     UNION ALL    "+
							"      SELECT "+varCampos+" "+
							"           SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS faturamento,   "+
							"           sum(cast((nfvi.QUANTIDADE * nfvi.CUSTOMEDIOONLINE) as numeric(18,4))) VALORCUSTO, "+
							"           0 AS devolucao, "+
							"           0 AS VALCUSTODEVOLVIDO, "+
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
							"         pessoa frn "+
							"        WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
							"          AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
							"          AND nfv.id_pedvenda = pv.id_pedvenda   "+
							"          AND pv.id_pessoa_vend = vnd.id_pessoa "+
							"	       and v.id_pessoa = vnd.id_pessoa "+
							"	       and v.id_tipovendedor = tv.id_tipovendedor "+
							"          and nfvi.id_produto = pr.id_produto "+
							"          and pr.id_linhaproduto = lp.id_linhaproduto "+
							"          and pr.id_pessoa_forn = frn.id_pessoa "+
							"          AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
							"          AND nfv.DATAEMISS between :dt1 AND :dt2 "+
							"          AND nfv.CANCELADA = 0    "+
							"          AND tmf.CLASSE = 0  "+
							"          AND nfv.TIPO = 'S'    "+
							"          AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)   "+
							"          AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)   "+
							"	       and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
							"	       and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"          "+sqlFiltro1+" "+
							"     group by "+varGroup+" "+
							"     UNION ALL   "+
							"      SELECT "+varCampos+" "+
							"           0 AS faturamento,    "+
							"           0 AS valorcusto,    "+
							"           SUM(bdi.quantidade * bdi.valorunit) AS devolucao, "+
							"           SUM(cast((bdi.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALCUSTODEVOLVIDO, "+
							"           0 AS desconto "+
							"      FROM boletimdevolucao bd,    "+
							"           boletimdevolitem bdi,    "+
							"           tipomovfisc tmf, "+
							"           produto pr, "+
							"           linhaproduto lp, "+
							"           linhaproduto dep, "+
							"           pessoa vnd, "+
							"	 	    vendedor v,  "+
							"	 		tipovendedor tv,  "+
							"           pessoa frn "+
							"      WHERE bd.id_boletimdevolucao = bdi.id_boletimdevolucao "+
							"        AND tmf.id_tipomovfisc = bd.id_tipomovfisc "+
							"        and bdi.id_produto = pr.id_produto "+
							"        and pr.id_linhaproduto = lp.id_linhaproduto "+
							"        AND dep.codedt=substring(lp.codedt from 1 FOR 6) "+
							"        and bd.id_pessoa_vend = vnd.id_pessoa "+
							"	     and v.id_pessoa = vnd.id_pessoa "+
							"	     and v.id_tipovendedor = tv.id_tipovendedor "+
							"        and pr.id_pessoa_forn = frn.id_pessoa "+
							"        AND tmf.altestqfisico = 1 "+
							"        AND bd.momento between :dt1 AND :dt2    "+
							"        AND bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)   "+
							"        AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)   "+
							"	     and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)   "+
							"	     and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"        AND bd.id_boletimdevolstatus <> 1   "+
							"       "+sqlFiltro3+" "+
							"      group by "+varGroup+" "+
							"  )tab  "+
							"  group by tab.id "+
					" ) tab2 "+
					"  group by tab2.id "+	
					"  order by 3 desc ";
		}
		
		 Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
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
				
				if(tipoVendedorFilter!=null) {
					query.setParameter("tipovendedor", tipoVendedorFilter.getId());
				}else {
					query.setParameter("tipovendedor", null);
				}
				
				if(fornecedorFilter!=null) {
					query.setParameter("fornecedor", fornecedorFilter.getId());
				}else {
					query.setParameter("fornecedor", null);
				}
				
				query.setResultTransformer(Transformers.aliasToBean(VendaFornDTO.class));
				
				return query.list();
		}

}
