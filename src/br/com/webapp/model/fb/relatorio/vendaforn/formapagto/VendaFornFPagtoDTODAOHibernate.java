package br.com.webapp.model.fb.relatorio.vendaforn.formapagto;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class VendaFornFPagtoDTODAOHibernate implements VendaFornFPagtoDTODAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2) {
		
		// TODO nao utilizar 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(tab.id_formapagtorec) AS formaPagtoRecId,  ") 
				.append(" MAX(tab.id_condpagto) AS condPagtoId, ")
				.append(" tab.descricao AS descricao, ")
				.append(" SUM(tab.valor - tab.troco) AS valor, ")
				.append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
				.append(" tab.parcela AS parcela ")
				.append(" FROM ( ");
					
						sql.append(" SELECT ecffr.id_formapagtorec AS id_formapagtorec, ") 
								   .append(" NULL AS id_condpagto, ")
								   .append(" CASE ecffr.id_formapagtorec WHEN 2 THEN COALESCE(MAX(cc.nomeadministradora), 'NFC-e SEM REG.PAGTO') ELSE MAX(fpr.descricao) END AS descricao, ")
								   .append(" CASE SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) ELSE SUM(ecffr.valor) END AS valor, ")
								   .append(" (SELECT CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS numeric(18,2)) ") 
								   		.append(" FROM ecf_vendasitem ei1 ") 
								   		.append(" WHERE ei1.id_ecfvendas = ecf.id_ecfvendas ") 
								   		  .append(" AND ei1.cancelada = 0) AS desconto, ")
								   .append(" 0 AS troco, ")
								   .append(" cc.qtdparcela AS parcela ")
						 .append(" FROM ecf_vendas ecf, ")
						 	 .append(" formapagtorec fpr, ") 
						 	 .append(" ecf_vendasformarec ecffr ")
						 	 .append(" left join cartao cc on (cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec) ") 
							.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ")
							  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ") 
							  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
							  .append(" AND ecf.concluida = 1 ")
							  .append(" AND ecf.cancelada = 0 ") 
							  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
							  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
							.append(" GROUP BY ecf.id_ecfvendas, ecffr.id_formapagtorec, cc.qtdparcela ");
		
					sql.append(" union all ");
		
				    	sql.append(" SELECT MAX(ecffr.id_formapagtorec) AS id_formapagtorec, ") 
				    			  .append(" NULL AS id_condpagto, ")
				    			  .append(" MAX(fpr.descricao) AS descricao, ")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
				    			  .append(" 0 AS valor, ")
				    			  .append(" 0 AS desconto, ")
				    			  .append(" MAX(ecf.valortroco) AS troco, ")
				    			  .append(" null AS parcela ")
				    			  .append(" FROM ecf_vendas ecf, ") 
				    			  	   .append(" formapagtorec fpr, ") 
				    			  	   .append(" ecf_vendasformarec ecffr ")
			    				.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ") 
			    				  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ")
						    	  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
						    	  .append(" AND ecffr.id_formapagtorec = :id_formapagtorec ") 
						    	 // .append(" AND ecf.valortroco > 0 ") 
			    				  .append(" AND ecf.concluida = 1 ")
				    			  .append(" AND ecf.cancelada = 0 ") 
				    			  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
				    			  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
				    			.append(" GROUP BY ecf.id_ecfvendas ");
			    	
			    	sql.append(" union all ");
			    	
				    	sql.append("SELECT NULL AS id_formapagtorec, ")
				    			 .append(" cp.id_condpagto AS id_condpagto, ")
				    			 .append(" MAX(cp.descricao) AS descricao, ")
				    			 .append(" SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS valor, ")
				    			 .append(" CAST((SUM(pvi.quantidade * pvi.preco * pvi.percdesconto) / SUM(pvi.quantidade * pvi.preco)) AS numeric(18, 2)) AS desconto, ")
								 .append(" 0 AS troco, ")
								 .append(" null AS parcela ")
							 .append(" FROM NFVENDA nfv, ") 
							 	  .append(" NFVENDAITEM nfvi, ") 
							 	  .append(" tipocobr TC, ")
							 	  .append(" TIPOMOVFISC tmf, ")
							 	  .append(" CONDPAGTO cp, ")
							 	  .append(" pedvenda pv, ")
							 	  .append(" pedvendaitem pvi ")
						 	 .append(" WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA ") 
						 	   .append(" AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC ")
						 	   .append(" AND cp.id_condpagto = NFV.id_condpagto ")
						 	   .append(" AND TC.id_tipocobr = NFV.id_tipocobr ")
						 	   .append(" AND nfv.CANCELADA = 0 ")
						 	   .append(" AND tmf.CLASSE = 0 ")
						 	   .append(" AND nfv.TIPO = 'S' ") 
						 	   .append(" AND nfv.DATAEMISS between :dt1 and :dt2 ") 
						 	   .append(" AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) ") 
						 	   .append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
 						 	   .append(" AND nfv.id_pedvenda = pv.id_pedvenda ") 
						 	   .append(" AND pv.id_pedvenda = pvi.id_pedvenda ") 
						 	   .append(" AND pvi.id_produto = nfvi.id_produto ") 
						 	 .append(" GROUP BY cp.id_condpagto ");
			    	
			    	sql.append(" union all ");
			    	
				    	sql.append("SELECT NULL AS id_formapagtorec, ")
				    			 .append(" NULL AS id_condpagto, ")
				    			 .append(" 'Pedidos a Faturar' AS descricao, ")
				    			 .append(" SUM(tab1.valor) AS valor, ")
				    			 .append(" CAST((SUM(tab1.valor * tab1.desconto) / sum(tab1.valor)) AS numeric(18,2)) AS desconto, ")
				    			 .append(" 0  AS troco, ")
				    			 .append(" null AS parcela ")
				    		.append(" FROM ( ")
				    			.append(" SELECT pv.id_pedvenda, ")
				    				   .append(" MAX(pv.valpedido) AS valor, ")
				    				   .append(" CAST((SUM(pvi.quantidade * pvi.preco * pvi.percdesconto) / SUM(pvi.quantidade * pvi.preco)) AS numeric(18,2)) AS desconto ")
				    				.append(" FROM pedvenda pv, ")
				    					 .append(" pedvendaitem pvi, ")
				    					 .append(" tipomovfisc tm ")
			    					.append(" WHERE pv.id_pedvenda = pvi.id_pedvenda ")
			    					.append(" AND pv.id_tipomovfisc = tm.id_tipomovfisc ")
			    					.append(" AND tm.tipoimpfiscal = :tipoimpfiscal ")
			    					.append(" AND tm.classe in (0,1) ")
			    					.append(" AND pv.id_pedvendastatus = :statusLiberado ")
			    					.append(" AND pv.efetivacao between :dt1 and :dt2 ") 
								 	.append(" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) ") 
								 	.append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
			    					.append(" GROUP BY pv.id_pedvenda ")
	    					.append(") tab1 ")
	    					.append(" GROUP BY 3 ");
			    	  
    			  sql.append(") tab ")
    			  .append(" GROUP BY tab.descricao, tab.parcela ")
    			  .append(" ORDER BY tab.parcela ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
				if(vendedorFilter!=null) {
					query.setParameter("vendedor", vendedorFilter.getId());
				}else {
					query.setParameter("vendedor", null);
				}	
				query.setParameter("tipoimpfiscal", MovFiscTipoFB.VENDA_CONSULMIDOR);
				query.setParameter("statusLiberado", PedVendaFB.SITUACAO_LIBERADA);
				query.setParameter("id_formapagtorec", 0); // formapagtorec 0 = Dinherio
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listarCondPagto(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2) {
		String sql = ""+
	   	 " SELECT "+
		 "	    NULL AS formaPagtoRecId, "+
		 "	    tab.id_condpagto AS condPagtoId, "+
		 "	    MAX(tab.descricao) AS descricao, "+
		 "	    SUM(tab.valor) AS valor, "+
		 "	    CASE "+
		 "	        WHEN SUM(tab.valorbruto) > 0 THEN CAST((SUM(ValorDesconto) / SUM(valorbruto))*100.00 AS NUMERIC(18, 2)) "+
		 "	        ELSE 0.00 "+
		 "	    END AS desconto, "+
		 "	    NULL AS parcela, "+
		 "	    1 tipoSql "+
	  	 " FROM "+
		 "	    ( "+
		 "	    SELECT "+
		 "	        cp.id_condpagto AS id_condpagto, "+
		 "	        MAX(cp.descricao) AS descricao, "+
		 "	        SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.QUANTIDADE * nfvi.PRECO) AS NUMERIC(18, 4)) ELSE 0 END) AS valor, "+
		 "	        SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.QUANTIDADE * pvi.precoprom) AS NUMERIC(18, 4)) ELSE 0 END) AS valorbruto, "+
		 "	        SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.quantidade * pvi.precoprom * pvi.percdesconto/100.00)  AS NUMERIC(18, 4)) ELSE 0 END) AS valorDesconto "+
		 "	    FROM "+
		 "	        NFVENDA nfv, "+
		 "	        NFVENDAITEM nfvi, "+
		 "	        tipocobr TC, "+
		 "	        TIPOMOVFISC tmf, "+
		 "	        CONDPAGTO cp, "+
		 "	        pedvenda pv, "+
		 "	        pedvendaitem pvi, "+
		 "	        vendedor v "+
		 "	    WHERE "+
		 " 	        nfv.ID_NFVENDA = nfvi.ID_NFVENDA "+
		 "	        AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC "+
		 "	        AND cp.id_condpagto = NFV.id_condpagto "+
		 "	        AND TC.id_tipocobr = NFV.id_tipocobr "+
		 "	        AND nfv.CANCELADA = 0 "+
		 "	        AND tmf.CLASSE = 0 "+
		 "	        AND nfv.TIPO = 'S' "+
		 "	        AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 "+
		 "	        AND pv.id_pessoa_vend = v.id_pessoa "+
		 "	        AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
		 "	        AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) "+
		 "	        AND pv.id_pessoa_vend = COALESCE(:vendedor, pv.id_pessoa_vend) "+
		 "	        AND nfv.id_pedvenda = pv.id_pedvenda "+
		 "	        AND pv.id_pedvenda = pvi.id_pedvenda "+
		 "	        AND pvi.id_produto = nfvi.id_produto "+
		 "	    GROUP BY "+
		 "	        cp.id_condpagto ) tab "+
		 "	GROUP BY "+
		 "	    tab.id_condpagto "+
		 "	ORDER BY "+
		 "	    tab.id_condpagto ";
		 
		//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT NULL AS formaPagtoRecId, ") 
//				.append(" tab.id_condpagto AS condPagtoId, ")
//				.append(" MAX(tab.descricao) AS descricao, ")
//				.append(" SUM(tab.valor) AS valor, ")
//				.append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
//				.append(" NULL AS parcela ")
//				.append(" FROM ( ");
//		
//				    	sql.append("SELECT cp.id_condpagto AS id_condpagto, ")
//				    			 .append(" MAX(cp.descricao) AS descricao, ")
//				    			 .append(" SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS valor, ")
//				    			 .append(" CAST((SUM(pvi.quantidade * pvi.preco * pvi.percdesconto) / SUM(pvi.quantidade * pvi.preco)) AS numeric(18, 2)) AS desconto ")
//							 .append(" FROM NFVENDA nfv, ") 
//							 	  .append(" NFVENDAITEM nfvi, ") 
//							 	  .append(" tipocobr TC, ")
//							 	  .append(" TIPOMOVFISC tmf, ")
//							 	  .append(" CONDPAGTO cp, ")
//							 	  .append(" pedvenda pv, ")
//							 	  .append(" pedvendaitem pvi, ")
//							 	  .append(" vendedor v ")
//						 	 .append(" WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA ") 
//						 	   .append(" AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC ")
//						 	   .append(" AND cp.id_condpagto = NFV.id_condpagto ")
//						 	   .append(" AND TC.id_tipocobr = NFV.id_tipocobr ")
//						 	   .append(" AND nfv.CANCELADA = 0 ")
//						 	   .append(" AND tmf.CLASSE = 0 ")
//						 	   .append(" AND nfv.TIPO = 'S' ") 
//						 	   .append(" AND nfv.DATAEMISS between :dt1 and :dt2 ")
//						       .append(" AND pv.id_pessoa_vend = v.id_pessoa ")
//						       .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
//						 	   .append(" AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) ")
//						 	   .append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
// 						 	   .append(" AND nfv.id_pedvenda = pv.id_pedvenda ") 
//						 	   .append(" AND pv.id_pedvenda = pvi.id_pedvenda ") 
//						 	   .append(" AND pvi.id_produto = nfvi.id_produto ") 
//						 	 .append(" GROUP BY cp.id_condpagto ");
//			    	
//    			  sql.append(") tab ")
//    			  .append(" GROUP BY tab.id_condpagto ")
//    			  .append(" ORDER BY tab.id_condpagto ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.addScalar("tipoSql", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
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

				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listarCartoesGroupByParcela(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2) {
		String sql = ""+
				" SELECT "+
				"     MAX(tab.id_formapagtorec) AS formaPagtoRecId, "+
				"     NULL AS condPagtoId, "+
				"     CASE "+
				"         WHEN tab.parcela IS NOT NULL THEN 'CARTAO (' || tab.parcela || 'x)' "+
				"         ELSE MAX(tab.descricao) "+
				"     END AS descricao, "+
				"     SUM(tab.valor) AS valor, "+
				"     CASE "+
				"         WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS NUMERIC(18, 2)) "+
				"         ELSE 0.00 "+
				"     END AS desconto, "+
				"     tab.parcela AS parcela, "+
				"     4 tipoSql "+
				" FROM "+
				"     ( "+
				"      SELECT "+
				"         ecffr.id_formapagtorec AS id_formapagtorec, "+
				"         CASE "+
				"             ecffr.id_formapagtorec WHEN 2 THEN COALESCE(MAX(cc.nomeadministradora), 'NFC-e SEM REG.PAGTO') "+
				"             ELSE MAX(fpr.descricao) "+
				"         END AS descricao, "+
				"         CASE "+
				"             SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) "+
				"             ELSE SUM(ecffr.valor) "+
				"         END AS valor, "+
				"         ( "+
				"         SELECT "+
				"             CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS NUMERIC(18, 2)) "+
				"         FROM "+
				"             ecf_vendasitem ei1 "+
				"         WHERE "+
				"             ei1.id_ecfvendas = ecf.id_ecfvendas "+
				"             AND ei1.cancelada = 0) AS desconto, "+
				"         cc.qtdparcela AS parcela "+
				"     FROM "+
				"         ecf_vendas ecf, "+
				"         formapagtorec fpr, "+
				"         ecf_vendasformarec ecffr, "+
				"         cartao cc , "+
				"         vendedor v "+
				"     WHERE "+
				"         ecf.id_ecfvendas = ecffr.id_ecfvendas "+
				"         AND ecffr.id_formapagtorec = fpr.id_formapagtorec "+
				"         and cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec "+
				"         AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"         AND ecf.concluida = 1 "+
				"         AND ecf.cancelada = 0 "+
				"         AND ecffr.id_formapagtorec = 2 "+
				"         AND ecf.id_pessoa_vend = v.id_pessoa "+
				"         AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"         AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				"         AND ecf.id_pessoa_vend = COALESCE(:vendedor, ecf.id_pessoa_vend) "+
				"     GROUP BY "+
				"         ecf.id_ecfvendas, "+
				"         ecffr.id_formapagtorec, "+
				"         cc.qtdparcela  "+
				"  "+
				" union all "+
				"  "+
				" SELECT "+
				"         ecffr.id_formapagtorec AS id_formapagtorec, "+
				"          'NFC-e SEM REG.PAGTO' AS descricao, "+
				"         CASE "+
				"             SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) "+
				"             ELSE SUM(ecffr.valor) "+
				"         END AS valor, "+
				"         ( "+
				"         SELECT "+
				"             CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS NUMERIC(18, 2)) "+
				"         FROM "+
				"             ecf_vendasitem ei1 "+
				"         WHERE "+
				"             ei1.id_ecfvendas = ecf.id_ecfvendas "+
				"             AND ei1.cancelada = 0) AS desconto, "+
				"         NULL AS parcela "+
				"     FROM "+
				"         ecf_vendas ecf, "+
				"         formapagtorec fpr, "+
				"         ecf_vendasformarec ecffr, "+
				"         vendedor v "+
				"     WHERE "+
				"         ecf.id_ecfvendas = ecffr.id_ecfvendas "+
				"         AND ecffr.id_formapagtorec = fpr.id_formapagtorec "+
				"  "+
				"         AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"         AND ecf.concluida = 1 "+
				"         AND ecf.cancelada = 0 "+
				"         AND ecffr.id_formapagtorec = 2 "+
				"         AND ecf.id_pessoa_vend = v.id_pessoa "+
				"         AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"         AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				"         AND ecf.id_pessoa_vend = COALESCE(:vendedor, ecf.id_pessoa_vend) "+
				"         and not exists( select first 1 cc.id_cartao "+
				"                         from cartao cc "+
				"                         where cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec) "+
				"     GROUP BY "+
				"         ecf.id_ecfvendas, "+
				"         ecffr.id_formapagtorec "+
				"  "+
				"     ) tab "+
				" GROUP BY "+
				"     tab.parcela "+
				" ORDER BY "+
				"     tab.parcela ";
		
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT MAX(tab.id_formapagtorec) AS formaPagtoRecId, ") 
//				 .append(" NULL AS condPagtoId, ")
//				 .append(" CASE WHEN tab.parcela IS NOT NULL THEN  'CARTAO (' || tab.parcela || 'x)' ELSE MAX(tab.descricao) END AS descricao, ")
//				 .append(" SUM(tab.valor) AS valor, ")
//				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
//				 .append(" tab.parcela AS parcela ")
//				 .append(" FROM ( ");
//					
//					 sql.append(" SELECT ecffr.id_formapagtorec AS id_formapagtorec, ") 
//							   .append(" CASE ecffr.id_formapagtorec WHEN 2 THEN COALESCE(MAX(cc.nomeadministradora), 'NFC-e SEM REG.PAGTO') ELSE MAX(fpr.descricao) END AS descricao, ")
//							   .append(" CASE SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) ELSE SUM(ecffr.valor) END AS valor, ")
//							   .append(" (SELECT CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS numeric(18,2)) ") 
//							   		.append(" FROM ecf_vendasitem ei1 ") 
//							   		.append(" WHERE ei1.id_ecfvendas = ecf.id_ecfvendas ") 
//							   		  .append(" AND ei1.cancelada = 0) AS desconto, ")
//							   .append(" cc.qtdparcela AS parcela ")
//					    .append(" FROM ecf_vendas ecf, ")
//					    	 .append(" formapagtorec fpr, ") 
//					    	 .append(" ecf_vendasformarec ecffr ")
//					    	 .append(" left join cartao cc on (cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec), ")
//					    	 .append(" vendedor v ")
//				    	.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ")
//				    	  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ") 
//				    	  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
//				    	  .append(" AND ecf.concluida = 1 ")
//				    	  .append(" AND ecf.cancelada = 0 ") 
//				    	  .append(" AND ecffr.id_formapagtorec = :id_formapagtorec_cartao ")
//				    	  .append(" AND ecf.id_pessoa_vend = v.id_pessoa ")
//				    	  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
//				    	  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
//				    	  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
//				    	.append(" GROUP BY ecf.id_ecfvendas, ecffr.id_formapagtorec, cc.qtdparcela ");
//			    	
//    			  sql.append(") tab ")
//    			  .append(" GROUP BY tab.parcela ")
//    			  .append(" ORDER BY tab.parcela ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.addScalar("tipoSql", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
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

				//query.setParameter("id_formapagtorec_cartao", 2);
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listarCartoes(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Integer parcelas) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(tab.id_formapagtorec) AS formaPagtoRecId, ") 
				 .append(" NULL AS condPagtoId, ")
				 .append(" tab.descricao AS descricao, ")
				 .append(" SUM(tab.valor) AS valor, ")
				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
				 .append(" MAX(tab.parcela) AS parcela ")
				 .append(" FROM ( ");
					
					 sql.append(" SELECT ecffr.id_formapagtorec AS id_formapagtorec, ") 
							   .append(" MAX(cc.nomeadministradora) AS descricao, ")
							   .append(" CASE SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) ELSE SUM(ecffr.valor) END AS valor, ")
							   .append(" (SELECT CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS numeric(18,2)) ") 
							   		.append(" FROM ecf_vendasitem ei1 ") 
							   		.append(" WHERE ei1.id_ecfvendas = ecf.id_ecfvendas ") 
							   		  .append(" AND ei1.cancelada = 0) AS desconto, ")
							   .append(" cc.qtdparcela AS parcela ")
					    .append(" FROM ecf_vendas ecf, ")
					    	 .append(" formapagtorec fpr, ") 
					    	 .append(" ecf_vendasformarec ecffr, ")
					    	 .append(" cartao cc, ")
					    	 .append(" vendedor v ")
				    	.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ")
				    	  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ") 
				    	  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
				    	  .append(" AND ecf.concluida = 1 ")
				    	  .append(" AND ecf.cancelada = 0 ") 
				    	  .append(" AND ecffr.id_formapagtorec = :id_formapagtorec_cartao ")
				    	  .append(" AND ecf.id_pessoa_vend = v.id_pessoa ")
				    	  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
				    	  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
				    	  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
				    	  .append(" AND cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec ")
				    	  .append(" AND cc.qtdparcela = :parcelas ")
				    	.append(" GROUP BY ecf.id_ecfvendas, ecffr.id_formapagtorec, cc.qtdparcela ");
			    	
    			  sql.append(") tab ")
    			  .append(" GROUP BY tab.descricao ")
    			  .append(" ORDER BY tab.descricao ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
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

				query.setParameter("id_formapagtorec_cartao", 2);
				query.setParameter("parcelas", parcelas);
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listarOutros(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2) {
		String sql = ""+
				" SELECT "+
				"     MAX(tab.id_formapagtorec) AS formaPagtoRecId, "+
				"     NULL AS condPagtoId, "+
				"     tab.descricao AS descricao, "+
				"     SUM(tab.valor - tab.troco) AS valor, "+
				"     CASE "+
				"         WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS NUMERIC(18, 2)) "+
				"         ELSE 0.00 "+
				"     END AS desconto, "+
				"     NULL AS parcela, "+
				"     2 tipoSql "+
				" FROM "+
				"     ( "+
				"     SELECT "+
				"         ecffr.id_formapagtorec AS id_formapagtorec, "+
				"         MAX(fpr.descricao) AS descricao, "+
				"         CASE "+
				"             SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) "+
				"             ELSE SUM(ecffr.valor) "+
				"         END AS valor, "+
				"         ( "+
				"         SELECT "+
				"             CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS NUMERIC(18, 2)) "+
				"         FROM "+
				"             ecf_vendasitem ei1 "+
				"         WHERE "+
				"             ei1.id_ecfvendas = ecf.id_ecfvendas "+
				"             AND ei1.cancelada = 0) AS Desconto, "+
				"         MAX( iif (ecffr.id_formapagtorec = :id_formapagtorec_dinheiro, ecf.valortroco,0) ) "+
				"             AS troco "+
				"     FROM "+
				"         ecf_vendas ecf, "+
				"         formapagtorec fpr, "+
				"         ecf_vendasformarec ecffr, "+
				"         vendedor v "+
				"     WHERE "+
				"         ecf.id_ecfvendas = ecffr.id_ecfvendas "+
				"         AND ecffr.id_formapagtorec = fpr.id_formapagtorec "+
				"         AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"         AND ecf.concluida = 1 "+
				"         AND ecf.cancelada = 0 "+
				"         AND ecffr.id_formapagtorec != :id_formapagtorec_cartao "+
				"         AND ecf.id_pessoa_vend = v.id_pessoa "+
				"         AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"         AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				"         AND ecf.id_pessoa_vend = COALESCE(:vendedor, ecf.id_pessoa_vend) "+
				"     GROUP BY "+
				"         ecf.id_ecfvendas, "+
				"         ecffr.id_formapagtorec "+
				" ) tab "+
				" GROUP BY "+
				"     tab.descricao "+
				" ORDER BY "+
				"     tab.descricao     ";	
		
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT MAX(tab.id_formapagtorec) AS formaPagtoRecId, ") 
//				 .append(" NULL AS condPagtoId, ")
//				 .append(" tab.descricao AS descricao, ")
//				 .append(" SUM(tab.valor - tab.troco) AS valor, ")
//				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
//				 .append(" NULL AS parcela ")
//				 .append(" FROM ( ");
//					
//					 sql.append(" SELECT ecffr.id_formapagtorec AS id_formapagtorec, ") 
//							   .append(" MAX(fpr.descricao) AS descricao, ")
//							   .append(" CASE SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) ELSE SUM(ecffr.valor) END AS valor, ")
//							   .append(" (SELECT CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, ei1.percdescped)) / SUM(ei1.quantidade * ei1.preco)) AS numeric(18,2)) ") 
//							   		.append(" FROM ecf_vendasitem ei1 ") 
//							   		.append(" WHERE ei1.id_ecfvendas = ecf.id_ecfvendas ") 
//							   		  .append(" AND ei1.cancelada = 0) AS desconto, ")
//							   .append(" 0 AS troco ")
//					    .append(" FROM ecf_vendas ecf, ")
//					    	 .append(" formapagtorec fpr, ") 
//					    	 .append(" ecf_vendasformarec ecffr, ")
//					    	 .append(" vendedor v ")
//				    	.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ")
//				    	  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ") 
//				    	  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
//				    	  .append(" AND ecf.concluida = 1 ")
//				    	  .append(" AND ecf.cancelada = 0 ") 
//				    	  .append(" AND ecffr.id_formapagtorec != :id_formapagtorec_cartao ")
//				    	  .append(" AND ecf.id_pessoa_vend = v.id_pessoa ")
//				    	  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
//				    	  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
//				    	  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
//				    	.append(" GROUP BY ecf.id_ecfvendas, ecffr.id_formapagtorec ");
//					 
//					 sql.append(" union all ");
//						
//				    	sql.append(" SELECT MAX(ecffr.id_formapagtorec) AS id_formapagtorec, ") 
//				    			  .append(" MAX(fpr.descricao) AS descricao, ")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
//				    			  .append(" 0 AS valor, ")
//				    			  .append(" 0 AS desconto, ")
//				    			  .append(" MAX(ecf.valortroco) AS troco ")
//				    			  .append(" FROM ecf_vendas ecf, ") 
//				    			  	   .append(" formapagtorec fpr, ") 
//				    			  	   .append(" ecf_vendasformarec ecffr, ")
//				    			  	   .append(" vendedor v ")
//			    				.append(" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas ") 
//			    				  .append(" AND ecffr.id_formapagtorec = fpr.id_formapagtorec ")
//						    	  .append(" AND ecf.datavenda BETWEEN :dt1 AND :dt2 ")
//						    	  .append(" AND ecffr.id_formapagtorec = :id_formapagtorec_dinheiro ") 
//			    				  .append(" AND ecf.concluida = 1 ")
//				    			  .append(" AND ecf.cancelada = 0 ") 
//						    	  .append(" AND ecf.id_pessoa_vend = v.id_pessoa ")
//						    	  .append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
//				    			  .append(" AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) ")
//				    			  .append(" AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ")
//				    			.append(" GROUP BY ecf.id_ecfvendas ");
//			    	
//    			  sql.append(") tab ")
//    			  .append(" GROUP BY tab.descricao ")
//    			  .append(" ORDER BY tab.descricao ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.addScalar("tipoSql", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
				
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

				query.setParameter("id_formapagtorec_cartao", 2);
				query.setParameter("id_formapagtorec_dinheiro", 0);
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornFPagtoDTO> listarPedidosAFaturar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2) {
		String sql = ""+
				" SELECT "+
				"     NULL AS formaPagtoRecId, "+
				"     NULL AS condPagtoId, "+
				"     tab.descricao AS descricao, "+
				"     SUM(tab.valor) AS valor, "+
				"     CASE "+
				"         WHEN SUM(tab.valorbruto) > 0 THEN CAST((SUM(ValorDesconto) / SUM(valorbruto))*100.00 AS NUMERIC(18, 2)) "+
				"         ELSE 0.00 "+
				"     END AS desconto, "+
				"     NULL AS parcela , "+
				"     3 tipoSql "+
				" FROM "+
				"     ( "+
				"     SELECT "+
				"         'Pedidos a Faturar' AS descricao, "+
				"         SUM(tab1.valor) AS valor, "+
				"         SUM(tab1.valorBruto) AS valorBruto, "+
				"         SUM(valorDesconto) AS valorDesconto "+
				"     FROM "+
				"         ( "+
				"         SELECT "+
				"             pv.id_pedvenda, "+
				"             MAX(pv.valpedido) AS valor, "+
				"             SUM(TRUNC(((pvi.quantidade) * pvi.precoprom), 2)) AS valorBruto, "+
				"             SUM(CAST((pvi.quantidade * pvi.precoprom * pvi.percdesconto/100.00)  AS NUMERIC(18, 4))) AS valorDesconto "+
				"         FROM "+
				"             pedvenda pv, "+
				"             pedvendaitem pvi, "+
				"             tipomovfisc tm, "+
				"             vendedor v "+
				"         WHERE "+
				"             pv.id_pedvenda = pvi.id_pedvenda "+
				"             AND pv.id_tipomovfisc = tm.id_tipomovfisc "+
				"             AND tm.tipoimpfiscal = 1 "+
				"             AND tm.classe = 0 "+
				"             AND pv.id_pedvendastatus = 4 "+
				"             AND pv.efetivacao BETWEEN :dt1 AND :dt2 "+
				"             AND pv.id_pessoa_vend = v.id_pessoa "+
				"             AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"             AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) "+
				"             AND pv.id_pessoa_vend = COALESCE(:vendedor, pv.id_pessoa_vend) "+
				"         GROUP BY "+
				"             pv.id_pedvenda ) tab1 "+
				"     GROUP BY "+
				"         1 ) tab "+
				" GROUP BY "+
				"     tab.descricao "+
				" ORDER BY "+
				"     tab.descricao     ";	
		
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT NULL AS formaPagtoRecId, ") 
//				 .append(" NULL AS condPagtoId, ")
//				 .append(" tab.descricao AS descricao, ")
//				 .append(" SUM(tab.valor) AS valor, ")
//				 .append(" CASE WHEN SUM(tab.valor) > 0 THEN CAST((SUM(tab.valor * tab.desconto) / SUM(tab.valor)) AS numeric(18,2)) ELSE 0.00 END AS desconto, ") 
//				 .append(" NULL AS parcela ")
//				 .append(" FROM ( ");
//					
//					sql.append("SELECT 'Pedidos a Faturar' AS descricao, ")
//							 .append(" SUM(tab1.valor) AS valor, ")
//							 .append(" CAST((SUM(tab1.valor * tab1.desconto) / sum(tab1.valor)) AS numeric(18,2)) AS desconto ")
//						.append(" FROM ( ")
//							.append(" SELECT pv.id_pedvenda, ")
//								   .append(" MAX(pv.valpedido) AS valor, ")
//								   .append(" CAST((SUM(pvi.quantidade * pvi.preco * pvi.percdesconto) / SUM(pvi.quantidade * pvi.preco)) AS numeric(18,2)) AS desconto ")
//								.append(" FROM pedvenda pv, ")
//									 .append(" pedvendaitem pvi, ")
//									 .append(" tipomovfisc tm, ")
//									 .append(" vendedor v ")
//								.append(" WHERE pv.id_pedvenda = pvi.id_pedvenda ")
//								.append(" AND pv.id_tipomovfisc = tm.id_tipomovfisc ")
//								.append(" AND tm.tipoimpfiscal = :tipoimpfiscal ")
//								.append(" AND tm.classe in (0,1) ")
//								.append(" AND pv.id_pedvendastatus = :statusLiberado ")
//								.append(" AND pv.efetivacao between :dt1 and :dt2 ") 
//						    	.append(" AND pv.id_pessoa_vend = v.id_pessoa ")
//						    	.append(" AND v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
//							 	.append(" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) ")
//							 	.append(" AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
//								.append(" GROUP BY pv.id_pedvenda ")
//						.append(" ) tab1  ")
//						.append(" GROUP BY 1 ");
//			    	
//    			  sql.append(") tab ")
//    			  .append(" GROUP BY tab.descricao ")
//    			  .append(" ORDER BY tab.descricao ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("formaPagtoRecId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.addScalar("tipoSql", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
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
		
				//query.setParameter("tipoimpfiscal", MovFiscTipoFB.VENDA_CONSULMIDOR);
				//query.setParameter("statusLiberado", PedVendaFB.SITUACAO_LIBERADA);
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public VendaFornFPagtoDTO carregarBancaria(EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(TC.descricao) AS descricao, " + 
						 " SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO),2) AS numeric(18,4)) ELSE 0 END) AS valor, " +
						 " null AS parcela " +
					  " FROM NFVENDA nfv, " + 
					  	   " NFVENDAITEM nfvi, " + 
					  	   " tipocobr TC, " + 
					  	   " TIPOMOVFISC tmf " + 
					  " WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA " + 
					    " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
					    " AND TC.id_tipocobr = NFV.id_tipocobr " + 
					    " AND nfv.CANCELADA = 0 " + 
					    " AND tmf.CLASSE = 0 " + 
					    " AND nfv.TIPO = 'S' " + 
					    " AND nfv.DATAEMISS between :dt1 and :dt2 " + 
					    " AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) " +
					  " GROUP BY NFV.id_tipocobr ");
					
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("parcela", Hibernate.INTEGER)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("id_pessoa_emp", empresaFilter.getId());
//				if(vendedorFilter!=null) {
//					query.setParameter("vendedor", vendedorFilter.getId());
//				}else {
//					query.setParameter("vendedor", null);
//				}	
				
				query.setResultTransformer(Transformers.aliasToBean(VendaFornFPagtoDTO.class));
		query.setMaxResults(1);
		return (VendaFornFPagtoDTO) query.uniqueResult();
		
	}

}
