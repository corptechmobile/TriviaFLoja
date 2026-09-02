package br.com.webapp.model.fb.relatorio.vendaforn.pedvenda;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class VendaFornPedVendaDAOHibernate implements VendaFornPedVendaDAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornPedVenda> listarPedVenda(VendaFornDTO selecionada, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter,  Date dataFilter1, Date dataFilter2, String porFilter) {
		
		String sql = "";
		String varAnd = "";
		String varAnd2 = "";
		String varGroupBy = "";
		boolean incluiAutoServico = true;
		String varGroupBy2 = ", evi.id_pedvendaitem";
		String varCollumn = " MAX(pvi.quantidade) AS qtde, "
						  + " MAX(u.desccf) AS un, "
						  + " MAX(cp.descricao) AS condPagto, "
						  + " MAX(pvi.preco) AS preco, "
						  + " trunc(MAX(pvi.quantidade * pvi.preco), 2) valor, "
						  +	"       SUM(CASE tm.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "
						  +	"          (pvi.quantidade - pvi.qtdsaldoatender)) * "
						  + "           PVI.precoprom AS NUMERIC(18, 2)) ELSE 0 END) AS valorBruto, "
						  + " CAST(SUM( iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "
						  + "     (pvi.quantidade - pvi.qtdsaldoatender)) * pvi.precoprom * pvi.percdesconto/100.00) AS NUMERIC(18,4)) AS valorDesconto, ";


	    String varCollumn2 = " MAX(evi.quantidade) AS qtde, " +
						     " MAX(u.desccf) AS un, "+
						     " (SELECT list(fpr.DESCRICAO) "+
								   "FROM ecf_vendas ev3, "+
								        "ECF_VENDASFORMAREC fp, "+
								        "FORMAPAGTOREC fpr "+
								  "WHERE fp.ID_ECFVENDAS = ev3.ID_ECFVENDAS "+
								    "AND fp.ID_FORMAPAGTOREC = fpr.ID_FORMAPAGTOREC "+
								    "AND ev3.ID_ECFVENDAS = ev.ID_ECFVENDAS) condPagto, "+
						     " MAX(evi.preco) AS preco, "+
						     " MAX(evi.VALORTOTAL) valor, "+
						     " SUM(TRUNC(((evi.quantidade - COALESCE(evi.qtdpedido, 0)) * evi.preco), 2)) valorbruto, "+
						     " CAST(SUM(evi.quantidade * evi.preco * evi.percdesconto/100.00) AS NUMERIC(18, 4)) AS valorDesconto, ";
	
		String varTable = " ";
		
		if (porFilter.equals("fornecedor") || porFilter.equals("linhaProduto") || porFilter.equals("vendedor") || porFilter.equals("tipovend")) {
			varTable = " produto pr, unidade u, ";
			varAnd = " AND pr.id_unidade_venda = u.id_unidade " +
					 " AND pvi.id_produto = pr.id_produto " + 
					 " AND pv.efetivacao BETWEEN :dt1 AND :dt2 "; 
					 //" AND pv.id_pedvendastatus IN (4, 5, 6, 7) ";
			
			varAnd2 = " AND pr.id_unidade_venda = u.id_unidade " +
					  " AND evi.id_produto = pr.id_produto " + 
					  " AND ev.MOMENTOVENDA BETWEEN :dt1 AND :dt2 "; 

		}
		
		if (porFilter.equals("fornecedor")) {
			varAnd += " AND pr.id_pessoa_forn = :id AND pr.id_produto = :id_produto ";
			varAnd2 += " AND pr.id_pessoa_forn = :id AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";
			
		} else if (porFilter.equals("linhaProduto")) {
			varTable += " linhaproduto lp, ";
			varAnd += " AND lp.id_linhaproduto = :id AND pr.id_produto = :id_produto ";
			varAnd2 += " AND lp.id_linhaproduto = :id AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";

		} else if (porFilter.equals("vendedor")) {
			varAnd += " AND pv.id_pessoa_vend = :id AND pr.id_produto = :id_produto ";
			if(!selecionada.getId().toString().equals("-1")) {
				varAnd2 += " AND ev.id_pessoa_vend = :id AND pr.id_produto = :id_produto ";
			}else{
				varAnd2 += " AND pr.id_produto = :id_produto ";
			}
			
			varGroupBy = ", pvi.id_pedvendaitem";
		} else if (porFilter.equals("tipovend")) {
			varTable += " vendedor v, tipovendedor tv, ";
			varAnd += " AND pv.ID_PESSOA_VEND = v.id_pessoa "+
					  " AND v.id_tipovendedor = tv.id_tipovendedor "+
					  " AND tv.id_tipovendedor = :id "+
					  " AND pr.id_produto = :id_produto ";
			varAnd2 += " AND ev.ID_PESSOA_VEND = v.id_pessoa "+
					  " AND v.id_tipovendedor = tv.id_tipovendedor "+
					   " AND tv.id_tipovendedor = :id "+
					   " AND pr.id_produto = :id_produto ";
			
			varGroupBy = ", pvi.id_pedvendaitem";
		} else if (porFilter.equals("fpagto") && vendaFornFPagtoDTO!=null) {
			
//			varCollumn = " MAX(pvi.quantidade) AS qtde, "
//					   + " NULL AS un, "
//					   + " MAX(cp.descricao) AS condPagto, "
//					   + " NULL AS preco, "
//					   + " MAX(pv.valPedido) AS valor, "
//					   + " CAST(SUM( iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "
//					   + "     (pvi.quantidade - pvi.qtdsaldoatender)) * pvi.precoprom * pvi.percdesconto/100.00) AS NUMERIC(18,4)) AS desconto, ";

			varCollumn = " MAX(pvi.quantidade) AS qtde, "
					  + " null AS un, "
					  + " MAX(cp.descricao) AS condPagto, "
					  + " MAX(pvi.preco) AS preco, "
					  + " trunc(MAX(pvi.quantidade * pvi.preco), 2) valor, "
					  +	"       SUM(CASE tm.CLASSE WHEN 0 THEN CAST(iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "
					  +	"          (pvi.quantidade - pvi.qtdsaldoatender)) * "
					  + "           PVI.precoprom AS NUMERIC(18, 2)) ELSE 0 END) AS valorBruto, "
					  + " CAST(SUM( iif(pv.id_pedvendastatus IN (4, 5), pvi.quantidade, "
					  + "     (pvi.quantidade - pvi.qtdsaldoatender)) * pvi.precoprom * pvi.percdesconto/100.00) AS NUMERIC(18,4)) AS valorDesconto, ";



			
			//			varCollumn2 = " MAX(evi.quantidade) AS qtde, "+
//					      " NULL AS un, "+
//					      " (SELECT list(fpr.DESCRICAO) "+
//								   "FROM ecf_vendas ev2, "+
//								        "ECF_VENDASFORMAREC fp, "+
//								        "FORMAPAGTOREC fpr "+
//								  "WHERE fp.ID_ECFVENDAS = ev2.ID_ECFVENDAS "+
//								    "AND fp.ID_FORMAPAGTOREC = fpr.ID_FORMAPAGTOREC "+
//								    "AND ev2.ID_ECFVENDAS = ev.ID_ECFVENDAS) condPagto, "+
//					    " NULL AS preco, "+
//					    " MAX(evi.VALORTOTAL) valor, "+
//					    " CAST(SUM(evi.quantidade * evi.preco * evi.percdesconto/100.00) AS NUMERIC(18, 4)) AS desconto, ";
			
			varCollumn2 = " MAX(evi.quantidade) AS qtde, " +
				     " null AS un, "+
				     " (SELECT list(fpr.DESCRICAO) "+
						   "FROM ecf_vendas ev3, "+
						        "ECF_VENDASFORMAREC fp, "+
						        "FORMAPAGTOREC fpr "+
						  "WHERE fp.ID_ECFVENDAS = ev3.ID_ECFVENDAS "+
						    "AND fp.ID_FORMAPAGTOREC = fpr.ID_FORMAPAGTOREC "+
						    "AND ev3.ID_ECFVENDAS = ev.ID_ECFVENDAS) condPagto, "+
				     " NULL AS preco, "+
				     " MAX(evi.VALORTOTAL) valor, "+
				     " SUM(TRUNC(((evi.quantidade - COALESCE(evi.qtdpedido, 0)) * evi.preco), 2)) valorbruto, "+
				     " CAST(SUM(evi.quantidade * evi.preco * evi.percdesconto/100.00) AS NUMERIC(18, 4)) AS valorDesconto, ";

			
			// ex: dinheiro, nota de credito
			if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT ecf.id_ecfvendas " + 
						" FROM ecf_vendas ecf, ecf_vendasformarec ecffr " + 
							" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
							  " AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
							  " AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
							  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
				    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
				    	  	  " AND ecf.concluida = 1 " +
				    	  	  " AND ecf.cancelada = 0 " +
				    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				    	  	  " AND ecf.id_pessoa_vend = COALESCE(:vendedor, ecf.id_pessoa_vend) ) "; 

				varAnd2 = " AND exists (SELECT ecf.id_ecfvendas " + 
						" FROM ecf_vendas ecf, ecf_vendasformarec ecffr " + 
							" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
							  " AND ecf.id_ecfvendas_pdv = ev.id_ecfvendas " +
							  " AND ecf.id_ecfpontovenda_pdv = ev.id_ecfpontovenda " +
							  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
				    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
				    	  	  " AND ecf.concluida = 1 " +
				    	  	  " AND ecf.cancelada = 0 " +
				    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+ 
				    	  	  " AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ) ";


			} 
			// ex: cartao  
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()!=null) {
				varAnd = " AND exists (SELECT ecf.id_ecfvendas " + 
										" FROM ecf_vendas ecf, ecf_vendasformarec ecffr, cartao cc " + 
											" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
											  " AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
											  " AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
											  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
											  " AND cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec " +
											  " AND upper(cc.nomeadministradora) = :nomeadministradora " +
											  " AND cc.qtdparcela = :qtdparcela " +
								    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
								    	  	  " AND ecf.concluida = 1 " +
								    	  	  " AND ecf.cancelada = 0 " +
								    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
								    	  	  " AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ) "; 

				varAnd2 = " AND exists (SELECT ecf.id_ecfvendas " + 
						" FROM ecf_vendas ecf, ecf_vendasformarec ecffr, cartao cc " + 
							" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
							  " AND ecf.id_ecfvendas_pdv = ev.id_ecfvendas " +
							  " AND ecf.id_ecfpontovenda_pdv = ev.id_ecfpontovenda " +
							  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
							  " AND cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec " +
							  " AND upper(cc.nomeadministradora) = :nomeadministradora " +
							  " AND cc.qtdparcela = :qtdparcela " +
				    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
				    	  	  " AND ecf.concluida = 1 " +
				    	  	  " AND ecf.cancelada = 0 " +
				    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				    	  	"   AND ecf.id_pessoa_vend = COALESCE(:vendedor,ecf.id_pessoa_vend) ) "; 

			}
			// ex: pedidos a faturar ...
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT pv2.id_pedvenda " + 
										" FROM pedvenda pv2, " + 
											 " tipomovfisc tm " + 
										" WHERE pv2.id_pedvenda = pv.id_pedvenda " +
										  " AND pv2.id_tipomovfisc = tm.id_tipomovfisc " + 
										  " AND tm.tipoimpfiscal = :tipoimpfiscal " + 
										  " AND tm.classe in (0,1) "+
										  " AND pv2.id_pedvendastatus = :statusLiberado " + 
										  " AND pv2.efetivacao between :dt1 and :dt2 " + 
										  " AND pv2.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv2.id_pessoa_emp) "+
										  " AND PV2.id_pessoa_vend = COALESCE(:vendedor,PV2.id_pessoa_vend) )";

				varAnd2 = " ";
				
				incluiAutoServico = false;
			}
			// ex: A vista, 30 dias, 60 dias ...
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()!=null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT nfv.id_pedvenda " + 
										" FROM NFVENDA nfv, " + 
											 " TIPOMOVFISC tmf, " + 
											 " condpagto cp " + 
										" WHERE pv.id_pedvenda = nfv.id_pedvenda " +
										  " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
										  " AND cp.id_condpagto = nfv.id_condpagto " + 
										  " AND nfv.CANCELADA = 0 " + 
										  " AND tmf.CLASSE = 0 " + 
										  " AND nfv.TIPO = 'S' " + 
										  " AND cp.id_condpagto = :idcp " + 
										  " AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 " + 
										  " AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) "+
										  " AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ) ";
				
				varAnd2 = " AND exists (SELECT nfv.id_pedvenda " + 
						" FROM NFVENDA nfv, " + 
							 " TIPOMOVFISC tmf, " + 
							 " condpagto cp " + 
						" WHERE ev.id_ecfvendas = nfv.id_ecfvendas " +
						  " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
						  " AND cp.id_condpagto = nfv.id_condpagto " + 
						  " AND nfv.CANCELADA = 0 " + 
						  " AND tmf.CLASSE = 0 " + 
						  " AND nfv.TIPO = 'S' " + 
						  " AND cp.id_condpagto = :idcp " + 
						  " AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 " + 
						  " AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) " +
						  " AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend) ) ";

			}
			
		}
		
		
		if (incluiAutoServico) {
			
			sql = " SELECT tab.id as id, "+
				    "     MAX(tab.tipo) as tipo, "+
				    "     MAX(tab.cliente) as cliente, "+
				    "     SUM(tab.qtde) as qtde, "+
				    "     MAX(tab.un) as un, "+
				    "     MAX(tab.condPagto) as condPagto, "+
				    "     SUM(tab.preco) as preco, "+
				    "     SUM(tab.valor) as valor, "+
				    "     CASE WHEN SUM(tab.valorbruto) > 0.0 THEN CAST((SUM(tab.valorDesconto) / SUM(tab.valorbruto))*100.00 AS NUMERIC(18, "+
					"          2))  "+
					"          ELSE 0.00  "+
					"     END AS desconto, "+
				    "     MAX(tab.encomenda) as encomenda "+
					" FROM ("+
					"SELECT pv.id_pedvenda id, "+ 
						"  '"+VendaFornPedVenda.TIPO_PEDIDO+"' tipo, " + 
						"  max(p.razaosocialnome) cliente, " + 
						"   " + varCollumn +
						"  max(pv.encomenda) AS encomenda " +
				  " FROM pessoa p, " + 
					   " pedvenda pv, " + 
					   " pedvendaitem pvi, " + 
					   " tipomovfisc tm, " + varTable +
					   " condpagto cp " +
				  " WHERE pv.id_pessoa_cli = p.id_pessoa " + 
					" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) " +
					" AND pv.ID_PESSOA_VEND = coalesce(:vendedor,pv.ID_PESSOA_VEND) "+
					" AND pv.id_condpagto = cp.id_condpagto " +
					" AND pv.id_pedvenda = pvi.id_pedvenda " +
					" AND pv.id_tipomovfisc = tm.id_tipomovfisc " +
					" AND tm.classe in (0,1) " +
				  	" "+ varAnd +" "+
		          " GROUP BY pv.id_pedvenda " + varGroupBy + 
		          " UNION "+
		          "SELECT ev.ID_ECFVENDAS         id, "+
				          "'Cupom'                tipo, "+
				          "Max(p.razaosocialnome) cliente,  "+
				          "   " + varCollumn2 +
				          "0 AS encomenda "+
				   "FROM   pessoa p, " + varTable +
				          "ecf_vendas  ev, "+
				          "ECF_VENDASITEM evi "+ 
				   "WHERE  ev.id_pessoa_cli = p.id_pessoa "+
			         "AND ev.id_pessoa_emp = COALESCE(:id_pessoa_emp, ev.id_pessoa_emp) "+
			         "AND ev.ID_PESSOA_VEND = coalesce(:vendedor,ev.ID_PESSOA_VEND) "+
			         "AND ev.ID_ECFVENDAS = evi.ID_ECFVENDAS "+
			         "AND ev.MOMENTOVENDA BETWEEN :dt1 AND :dt2 "+
					 "AND (evi.quantidade - evi.qtdpedido) > 0 "+ 
					 " AND ev.concluida = 1 "+ 
					 " AND ev.cancelada = 0 "+ 
					 " AND evi.cancelada = 0 "+
			         " "+ varAnd2 +" "+
			         " AND NOT EXISTS (SELECT pv.id_ecfvenda "+
			                            "FROM pedvenda pv, "+
			                            " tipomovfisc tm " + 			         
			                           "WHERE pv.ID_ECFVENDA = ev.id_ecfvendas "+
				       					" AND pv.id_tipomovfisc = tm.id_tipomovfisc " +
				    					" AND tm.classe in (0,1) " +
			                             "AND pv.EFETIVACAO BETWEEN :dt1 AND :dt2 "+
			                             "AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) "+
			                             "AND pv.ID_PESSOA_VEND = coalesce(:vendedor,pv.ID_PESSOA_VEND) ) "+
				   "GROUP  BY ev.ID_ECFVENDAS " + varGroupBy2 +	 
				   " ) as tab "+
				   "GROUP BY tab.id "+
				   " ORDER BY 6 DESC ";
		}else {
			
			sql = "SELECT tab.id as id, "+
				    "     MAX(tab.tipo) as tipo, "+
				    "     MAX(tab.un) as un, "+
				    "     MAX(tab.cliente) as cliente, "+
				    "     MAX(tab.condPagto) as condPagto, "+
				    "     SUM(tab.valor) as valor, "+
				    "     SUM(tab.qtde) as qtde, "+
				    "     SUM(tab.preco) as preco, "+
					"     CASE WHEN SUM(tab.valorbruto) > 0.0 THEN CAST((SUM(tab.valorDesconto) / SUM(tab.valorbruto))*100.00 AS NUMERIC(18, "+
					"          2))  "+
					"          ELSE 0.00  "+
					"     END AS desconto, "+
				    "     MAX(tab.encomenda) as encomenda "+
					" FROM ("+
						    " SELECT pv.id_pedvenda id, "+ 
									"  '"+VendaFornPedVenda.TIPO_PEDIDO+"' tipo, " + 
									"  max(p.razaosocialnome) cliente, " + 
									"   " + varCollumn +
									"  max(pv.encomenda) AS encomenda " +
							  " FROM pessoa p, " + 
								   " pedvenda pv, " + 
								   " pedvendaitem pvi, " + 
								   " tipomovfisc tm, " + varTable +
								   " condpagto cp " +
							  " WHERE pv.id_pessoa_cli = p.id_pessoa " + 
								" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) " +
								" AND pv.ID_PESSOA_VEND = coalesce(:vendedor,pv.ID_PESSOA_VEND) " +
								" AND pv.id_condpagto = cp.id_condpagto " +
								" AND pv.id_pedvenda = pvi.id_pedvenda " + 
								" AND pv.id_tipomovfisc = tm.id_tipomovfisc " +
								" AND tm.classe in (0,1) " +
								" "+ varAnd +" "+
					          " GROUP BY pv.id_pedvenda " + varGroupBy +
					    " ) as tab "+
			   " group by tab.id "+		          
			   " ORDER BY 6 DESC ";
		}
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("tipo", Hibernate.STRING)
				.addScalar("cliente", Hibernate.STRING)
				.addScalar("qtde", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("condPagto", Hibernate.STRING)
				.addScalar("encomenda", Hibernate.INTEGER);
		
		query.setParameter("id_pessoa_emp", empresaFilter.getId());
		if(vendedorFilter!=null) {
			query.setParameter("vendedor", vendedorFilter.getId());
		}else {
			query.setParameter("vendedor", null);
		}

		query.setParameter("dt1", dataFilter1);
		query.setParameter("dt2", dataFilter2);
		
		if(!porFilter.equals("fpagto")) {
			query.setParameter("id", selecionada.getId());
			query.setParameter("id_produto", vendasProdutoDTO.getProdutoId());
		}else {
			if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("id_formapagtorec", vendaFornFPagtoDTO.getFormaPagtoRecId());
			} else if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()!=null) {
				query.setParameter("id_formapagtorec", vendaFornFPagtoDTO.getFormaPagtoRecId());
				query.setParameter("nomeadministradora", vendaFornFPagtoDTO.getDescricao());
				query.setParameter("qtdparcela", vendaFornFPagtoDTO.getParcela());
			}else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("tipoimpfiscal", MovFiscTipoFB.VENDA_CONSULMIDOR);
				query.setParameter("statusLiberado", PedVendaFB.SITUACAO_LIBERADA);
			}else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()!=null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("idcp", vendaFornFPagtoDTO.getCondPagtoId());
			}
		}
			
		query.setResultTransformer(Transformers.aliasToBean(VendaFornPedVenda.class));
			
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<VendaFornPedVenda> listarPedVendaSemAutoServ(VendaFornDTO selecionada, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter,  Date dataFilter1, Date dataFilter2, String porFilter) {
		
		String sql = "";
		String varAnd = "";
		String varAnd2 = "";
		String varGroupBy = "";
		boolean incluiAutoServico = true;
		String varGroupBy2 = ", evi.id_pedvendaitem";
		String varCollumn = " MAX(pvi.quantidade) AS qtde, "
						  + " MAX(u.desccf) AS un, "
						  + " MAX(cp.descricao) AS condPagto, "
						  + " MAX(pvi.preco) AS preco, "
						  + " trunc(MAX(pvi.quantidade * pvi.preco), 2) valor, "
						  + " CAST(MAX((pvi.quantidade * pvi.preco * pvi.percdesconto) / (pvi.quantidade * pvi.preco)) AS numeric(18,2)) AS desconto, ";

		String varTable = " ";
		
		if (porFilter.equals("fornecedor") || porFilter.equals("linhaProduto") || porFilter.equals("vendedor")) {
			varTable = " produto pr, unidade u, ";
			varAnd = " AND pr.id_unidade_venda = u.id_unidade " +
					 " AND pvi.id_produto = pr.id_produto " + 
					 " AND pv.efetivacao BETWEEN :dt1 AND :dt2 "; 
					// " AND pv.id_pedvendastatus IN (4, 5, 6, 7) ";
			
		}
		
		if (porFilter.equals("fornecedor")) {
			varAnd += " AND pr.id_pessoa_forn = :id AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";
			
		} else if (porFilter.equals("linhaProduto")) {
			varTable += " linhaproduto lp, ";
			varAnd += " AND lp.id_linhaproduto = :id AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";
		} else if (porFilter.equals("vendedor")) {
			varAnd += " AND pv.id_pessoa_vend = :id AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";
		} else if (porFilter.equals("tipovend")) {
			varTable += " vendedor v, tipovendedor tv, ";
			varAnd += " AND pv.ID_PESSOA_VEND = v.id_pessoa "+
					  " AND v.id_tipovendedor = tv.id_tipovendedor "+
					  " AND tv.id_tipovendedor = :id "+
					  " AND pr.id_produto = :id_produto ";
			varGroupBy = ", pvi.id_pedvendaitem";
			
		} else if (porFilter.equals("fpagto") && vendaFornFPagtoDTO!=null) {
			
			varCollumn = " MAX(pvi.quantidade) AS qtde, "
					   + " NULL AS un, "
					   + " MAX(cp.descricao) AS condPagto, "
					   + " NULL AS preco, "
					   + " MAX(pv.valPedido) AS valor, "
					   + " CAST(SUM((pvi.quantidade * pvi.preco * pvi.percdesconto)) / sum(pvi.quantidade * pvi.preco) AS numeric(18,2)) AS desconto, ";

			
			// ex: dinheiro, nota de credito
			if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT ecf.id_ecfvendas " + 
						" FROM ecf_vendas ecf, ecf_vendasformarec ecffr " + 
							" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
							  " AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
							  " AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
							  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
				    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
				    	  	  " AND ecf.concluida = 1 " +
				    	  	  " AND ecf.cancelada = 0 " +
				    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				    	  	  " AND ecf.ID_PESSOA_VEND = coalesce(:vendedor,ecf.ID_PESSOA_VEND) ) "; 

			} 
			// ex: cartao  
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()!=null) {
				varAnd = " AND exists (SELECT ecf.id_ecfvendas " + 
										" FROM ecf_vendas ecf, ecf_vendasformarec ecffr, cartao cc " + 
											" WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " +
											  " AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
											  " AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
											  " AND ecffr.id_formapagtorec = :id_formapagtorec " + 
											  " AND cc.id_ecfvendasformarec = ecffr.id_ecfvendasformarec " +
											  " AND upper(cc.nomeadministradora) = :nomeadministradora " +
											  " AND cc.qtdparcela = :qtdparcela " +
								    	  	  " AND ecf.datavenda BETWEEN :dt1 AND :dt2 " +
								    	  	  " AND ecf.concluida = 1 " +
								    	  	  " AND ecf.cancelada = 0 " +
								    	  	  " AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
								    	  	  " AND ecf.ID_PESSOA_VEND = coalesce(:vendedor,ecf.ID_PESSOA_VEND) ) "; 


			}
			// ex: pedidos a faturar ...
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT pv2.id_pedvenda " + 
										" FROM pedvenda pv2, " + 
											 " tipomovfisc tm " + 
										" WHERE pv2.id_pedvenda = pv.id_pedvenda " +
										  " AND pv2.id_tipomovfisc = tm.id_tipomovfisc " + 
										  " AND tm.tipoimpfiscal = :tipoimpfiscal " +
										  " AND tm.classe = (0,1) " +										  
										  " AND pv2.id_pedvendastatus = :statusLiberado " + 
										  " AND pv2.efetivacao between :dt1 and :dt2 " + 
										  " AND pv2.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv2.id_pessoa_emp) "+
										  " AND pv2.ID_PESSOA_VEND = coalesce(:vendedor,pv2.ID_PESSOA_VEND) ) ";

				
				incluiAutoServico = false;
			}
			// ex: A vista, 30 dias, 60 dias ...
			else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()!=null && vendaFornFPagtoDTO.getParcela()==null) {
				varAnd = " AND exists (SELECT nfv.id_pedvenda " + 
										" FROM NFVENDA nfv, " + 
											 " TIPOMOVFISC tmf, " + 
											 " condpagto cp " + 
										" WHERE pv.id_pedvenda = nfv.id_pedvenda " +
										  " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
										  " AND cp.id_condpagto = nfv.id_condpagto " + 
										  " AND nfv.CANCELADA = 0 " + 
										  " AND tmf.CLASSE = 0 " + 
										  " AND nfv.TIPO = 'S' " + 
										  " AND cp.id_condpagto = :idcp " + 
										  " AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 " + 
										  " AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) "+
										  " AND pv2.ID_PESSOA_VEND = coalesce(:vendedor,pv2.ID_PESSOA_VEND) ) ";
				
				varAnd2 = " AND exists (SELECT nfv.id_pedvenda " + 
						" FROM NFVENDA nfv, " + 
							 " TIPOMOVFISC tmf, " + 
							 " condpagto cp " + 
						" WHERE ev.id_ecfvendas = nfv.id_ecfvendas " +
						  " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
						  " AND cp.id_condpagto = nfv.id_condpagto " + 
						  " AND nfv.CANCELADA = 0 " + 
						  " AND tmf.CLASSE = 0 " + 
						  " AND nfv.TIPO = 'S' " + 
						  " AND cp.id_condpagto = :idcp " + 
						  " AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 " + 
						  " AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) "+
						  " AND ev.ID_PESSOA_VEND = coalesce(:vendedor,ev.ID_PESSOA_VEND) ) ";

			}
			
		}
		
		

			sql = "SELECT pv.id_pedvenda id, "+ 
					"  '"+VendaFornPedVenda.TIPO_PEDIDO+"' tipo, " + 
					"  max(p.razaosocialnome) cliente, " + 
					"   " + varCollumn +
					"  max(pv.encomenda) AS encomenda " +
			  " FROM pessoa p, " + 
				   " pedvenda pv, " + 
				   " pedvendaitem pvi, "+
				   " pedvendastatus pvs, "+
				   " tipomovfisc tmf, " + varTable +
				   " condpagto cp "+ 
			  " WHERE pv.id_pessoa_cli = p.id_pessoa " + 
				" AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS "+ 
			    " AND pv.id_tipomovfisc = tmf.id_tipomovfisc "+
				" AND pvs.EFETIVADO = 1 "+
				" AND tmf.classe in (0,1) "+
				" AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) " +
				" AND pv.id_condpagto = cp.id_condpagto " +
				" AND pv.id_pedvenda = pvi.id_pedvenda " + 
				" and pv.ID_PESSOA_VEND = coalesce(:vendedor,pv.ID_PESSOA_VEND) "+
			  	" "+ varAnd +" "+
	          " GROUP BY pv.id_pedvenda " + varGroupBy + 
			   " ORDER BY 6 DESC ";
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("tipo", Hibernate.STRING)
				.addScalar("cliente", Hibernate.STRING)
				.addScalar("qtde", Hibernate.DOUBLE)
				.addScalar("un", Hibernate.STRING)
				.addScalar("preco", Hibernate.DOUBLE)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("condPagto", Hibernate.STRING)
				.addScalar("encomenda", Hibernate.INTEGER);
		
		query.setParameter("id_pessoa_emp", empresaFilter.getId());
		if(vendedorFilter!=null) {
			query.setParameter("vendedor", vendedorFilter.getId());
		}else {
			query.setParameter("vendedor", null);
		}
		
		query.setParameter("dt1", dataFilter1);
		query.setParameter("dt2", dataFilter2);
		
		if(!porFilter.equals("fpagto")) {
			query.setParameter("id", selecionada.getId());
			query.setParameter("id_produto", vendasProdutoDTO.getProdutoId());
		}else {
			if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("id_formapagtorec", vendaFornFPagtoDTO.getFormaPagtoRecId());
			} else if(vendaFornFPagtoDTO.getFormaPagtoRecId()!=null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()!=null) {
				query.setParameter("id_formapagtorec", vendaFornFPagtoDTO.getFormaPagtoRecId());
				query.setParameter("nomeadministradora", vendaFornFPagtoDTO.getDescricao());
				query.setParameter("qtdparcela", vendaFornFPagtoDTO.getParcela());
			}else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()==null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("tipoimpfiscal", MovFiscTipoFB.VENDA_CONSULMIDOR);
				query.setParameter("statusLiberado", PedVendaFB.SITUACAO_LIBERADA);
			}else if(vendaFornFPagtoDTO.getFormaPagtoRecId()==null && vendaFornFPagtoDTO.getCondPagtoId()!=null && vendaFornFPagtoDTO.getParcela()==null) {
				query.setParameter("idcp", vendaFornFPagtoDTO.getCondPagtoId());
			}
		}
			
		query.setResultTransformer(Transformers.aliasToBean(VendaFornPedVenda.class));
			
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaFornPedVenda> listarNotas(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter, String segmentoFilter) {
		String sql = "";
		String sqlFiltro1 = "";
		String sqlFiltro2 = "";
		String sqlFiltro3 = "";
		String sqlFiltro4 = "";
		String sqlFiltro5 = "";
		String varCampos = "";
		String varWhere = " and pr.id_produto = :produtoId ";
		String varGroup = "";
		
		if(segmentoFilter!=null && !"".equals(segmentoFilter) && !"null".equals(segmentoFilter)) {
			if("atacado".equals(segmentoFilter)){
				         sqlFiltro2 = " and ev.id_ecfvendas = -1 ";
			}else {
				        sqlFiltro1 = " and pv.id_pedvenda = -1 ";
			}	        
		}
		
			sql =   " SELECT tab.id, "+
						"    max(tab.tipo) tipo, "+
						"    max(tab.cliente) cliente, "+
						"    max(tab.formapgto) condPagto, "+
						"    max(tab.un) un, "+
						"    COALESCE(SUM(tab.qtd), 0.0) AS qtde, "+
						"    COALESCE(max(tab.preco), 0.0) AS preco, "+
						"    COALESCE(SUM(tab.faturamento), 0.0) AS valor, "+
						"    COALESCE(SUM(tab.desconto), 0.0) AS desconto "+
						"  FROM ( "+
						"      SELECT ev.id_ecfvendas as id, "+
						"        'Cupom' as tipo, "+
						"        max(cli.nomefantmnem) cliente, "+
						"        '' as formapgto, "+
						"        max(un.desccf) un, "+
						"        sum(evi.quantidade) as qtd, "+
						"        SUM(evi.valorliquidoitem) AS faturamento,  "+
						"        max(evi.preco) preco, "+
						"        0 AS devolucao, "+
						"        0 AS desconto "+
						"      FROM ecf_vendas ev,    "+
						"           ecf_vendasitem evi, "+
						"           produto pr, "+
						"           linhaproduto lp, "+
						"           pessoa cli, "+
						"           pessoa vnd, "+
						"           pessoa frd, "+
						"           unidade un "+
						"      WHERE evi.id_ecfvendas = ev.id_ecfvendas "+
						"        and ev.id_pessoa_vend = vnd.id_pessoa "+
						"        and ev.id_pessoa_cli = cli.id_pessoa "+
						"        and evi.id_produto = pr.id_produto "+
						"        and pr.id_linhaproduto = lp.id_linhaproduto "+
						"        and pr.id_pessoa_forn = frd.id_pessoa "+
						"        and pr.id_unidade_venda = un.id_unidade "+
						"        AND ev.datavenda between :dt1 AND :dt2    "+
						"        AND ev.concluida = 1    "+
						"        AND ev.cancelada = 0    "+
						"        AND evi.cancelada = 0   "+
						"        AND ev.isvenda = 1 "+
						"        and coalesce(ev.ID_DAV,0) = 0  "+
						"        AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp)  "+
						"        AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)  "+
						"        "+sqlFiltro2+" "+
						"        "+varWhere+" "+
						"      group by ev.id_ecfvendas, 'Cupom' "+
						"     UNION ALL    "+
						"     SELECT nfv.numnf||nfv.serie as id, "+
						"           'Nota' as tipo, "+
						"           max(cli.nomefantmnem) cliente, "+
						"           max(pgto.descricao) formapgto, "+
						"           max(un.desccf) un, "+
						"           sum(nfvi.quantidade) qtd, "+
						"           SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS faturamento,   "+
						"           avg(nfvi.PRECO) preco, "+
						"           0 AS devolucao, "+
						"           0 AS desconto "+
						"        FROM NFVENDA nfv,    "+
						"         NFVENDAITEM nfvi,    "+
						"         condpagto pgto, "+
						"         TIPOMOVFISC tmf,   "+
						"         PEDVENDA pv, "+
						"         produto pr, "+
						"         linhaproduto lp, "+
						"         linhaproduto dep, "+
						"         pessoa cli, "+
						"         pessoa vnd, "+
						"         pessoa frd, "+
						"           unidade un "+
						"        WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA    "+
						"          AND nfv.id_condpagto = pgto.id_condpagto "+
						"          AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC   "+
						"          AND nfv.id_pedvenda = pv.id_pedvenda   "+
						"          AND pv.id_pessoa_vend = vnd.id_pessoa "+
						"          and nfv.id_pessoa = cli.id_pessoa "+
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
						"          and pr.id_unidade_venda = un.id_unidade "+
						"        "+sqlFiltro1+" "+
						"        "+varWhere+" "+
						"        group by nfv.numnf||nfv.serie, 'Nota' "+
						"  )tab "+
						"  group by tab.id "+
						"  order by 8 desc ";


		
		Query query = (Query) session.createSQLQuery(sql)
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("tipo", Hibernate.STRING)
			.addScalar("cliente", Hibernate.STRING)
			.addScalar("qtde", Hibernate.DOUBLE)
			.addScalar("un", Hibernate.STRING)
			.addScalar("preco", Hibernate.DOUBLE)
			.addScalar("valor", Hibernate.DOUBLE)
			.addScalar("desconto", Hibernate.DOUBLE)
			.addScalar("condPagto", Hibernate.STRING);
				
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
				
				query.setParameter("produtoId", vendasProdutoDTO.getProdutoId());
				
				query.setResultTransformer(Transformers.aliasToBean(VendaFornPedVenda.class));
				
				return query.list();
			
	}

	@Override
	public List<VendaFornPedVenda> listarPedVendaFpgto(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter) {
	
		String sql = "";
		
			
		sql = " SELECT "+
				"     id_pedvenda id, "+
				"     'Nota Fiscal' tipo, "+
				"     cliente, "+
				"     condPagto, "+
				"     (tab.valor) AS valor, "+
				"     CASE "+
				"         WHEN (tab.valorbruto) > 0 THEN CAST(((ValorDesconto) / (valorbruto))*100.00 AS NUMERIC(18, 2)) "+
				"         ELSE 0.00 "+
				"     END AS desconto, "+
				"     NULL AS parcela "+
				" FROM "+
				"     ( "+
				"     SELECT "+
				"         PV.id_pedvenda, "+
				"         max(PCLI.razaosocialnome) cliente, "+
				"         MAX(cp.descricao) AS condPagto, "+
				"         SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.QUANTIDADE * nfvi.PRECO) AS NUMERIC(18, 4)) ELSE 0 END) AS valor, "+
				"         SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.QUANTIDADE * pvi.precoprom) AS NUMERIC(18, 4)) ELSE 0 END) AS valorbruto, "+
				"         SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.quantidade * pvi.precoprom * pvi.percdesconto/100.00)  AS NUMERIC(18, 4)) ELSE 0 END) AS valorDesconto "+
				"  "+
				"     FROM "+
				"         NFVENDA nfv, "+
				"         NFVENDAITEM nfvi, "+
				"         tipocobr TC, "+
				"         TIPOMOVFISC tmf, "+
				"         CONDPAGTO cp, "+
				"         pedvenda pv, "+
				"         pedvendaitem pvi, "+
				"         vendedor v, "+
				"         PESSOA PCLI "+
				"     WHERE "+
				"         nfv.ID_NFVENDA = nfvi.ID_NFVENDA "+
				"         AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC "+
				"         AND PCLI.id_pessoa = PV.id_pessoa_cli "+
				"         AND cp.id_condpagto = NFV.id_condpagto "+
				"         AND TC.id_tipocobr = NFV.id_tipocobr "+
				"         AND nfv.CANCELADA = 0 "+
				"         AND tmf.CLASSE = 0 "+
				"         AND nfv.TIPO = 'S' "+
				"         AND nfv.DATAEMISS BETWEEN :dt1 AND :dt2 "+
				"         AND pv.id_pessoa_vend = v.id_pessoa "+
				"         AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"         AND nfv.id_pessoa_emp = COALESCE(:id_pessoa_emp, nfv.id_pessoa_emp) "+
				"         AND pv.id_pessoa_vend = COALESCE(:vendedor, pv.id_pessoa_vend) "+
				"         AND nfv.id_pedvenda = pv.id_pedvenda "+
				"         AND pv.id_pedvenda = pvi.id_pedvenda "+
				"         AND pvi.id_produto = nfvi.id_produto "+
				"         AND CP.id_condpagto = :ID_FormaPagtoCondPafgto "+
				"         and 1 = :tiposql "+
				"     GROUP BY "+
				"         pv.id_pedvenda "+
				"  ) tab "+
				 "  "+
				" union ALL "+
				"  "+
				" SELECT "+
				"     id, "+
				"     tipo, "+
				"     cliente, "+
				"     condPagto, "+
				"     (tab.valor - tab.troco) AS valor, "+
				"     CASE "+
				"         WHEN (tab.valor) > 0 THEN CAST(((tab.valor * tab.desconto) / (tab.valor)) AS NUMERIC(18, 2)) "+
				"         ELSE 0.00 "+
				"     END AS desconto, "+
				"     NULL AS parcela "+
				" FROM "+
				"     ( "+
				"     SELECT "+
				"         ecf.id_ecfvendas id, "+
				"         MAX(PCLI.razaosocialnome) cliente, "+
				"         'Cupom' tipo, "+
				"         MAX(fpr.descricao) AS condPagto, "+
				"         CASE "+
				"             SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) "+
				"             ELSE SUM(ecffr.valor) "+
				"         END AS valor, "+
				"         ( "+
				"         SELECT "+
				"             CAST((SUM(ei1.quantidade * ei1.preco * iif (ei1.percdesconto > 0, ei1.percdesconto, "+
				"                                                         iif(ei1.percdescped < 0, 0,ei1.percdescped))) / "+
				"                                                         SUM(ei1.quantidade * ei1.preco)) AS NUMERIC(18, 2)) "+
				"         FROM "+
				"             ecf_vendasitem ei1 "+
				"         WHERE "+
				"             ei1.id_ecfvendas = ecf.id_ecfvendas "+
				"             AND ei1.cancelada = 0 "+
				"          ) AS Desconto, "+
				"  "+
				"         max( iif (:ID_FormaPagtoCondPafgto = 0, ecf.valortroco,0) "+
				"            ) AS troco "+
				"  "+
				"     FROM "+
				"         ecf_vendas ecf, "+
				"         formapagtorec fpr, "+
				"         ecf_vendasformarec ecffr, "+
				"         vendedor v, "+
				"         PESSOA PCLI "+
				"  "+
				"     WHERE "+
				"         ecf.id_ecfvendas = ecffr.id_ecfvendas "+
				"         AND ecffr.id_formapagtorec = fpr.id_formapagtorec "+
				"         and pcli.id_pessoa = ecf.id_pessoa_cli "+
				"         AND ecf.id_pessoa_vend = v.id_pessoa "+
				"         AND ecf.datavenda BETWEEN :dt1 AND :dt2 "+
				"         AND ecf.concluida = 1 "+
				"         AND ecf.cancelada = 0 "+
				"         AND ecffr.id_formapagtorec = :ID_FormaPagtoCondPafgto "+
				"         and ecffr.id_formapagtorec <> 2 "+
				"         AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"         AND ecf.id_pessoa_emp = COALESCE(:id_pessoa_emp, ecf.id_pessoa_emp) "+
				"         AND ecf.id_pessoa_vend = COALESCE(:vendedor, ecf.id_pessoa_vend) "+
				"         and 2 = :tiposql "+
				"     GROUP BY "+
				"         ecf.id_ecfvendas "+
				" ) tab "+
				"  "+
				" UNION ALL "+
				"  "+
				"  "+
				"     SELECT "+
				"         id_pedvenda id, "+
				"         'Pedidos' tipo, "+
				"         cliente, "+
				"         'Pedidos a Faturar' as condPagto, "+
				"         (tab.valor) AS valor, "+
				"         CASE "+
				"             WHEN (tab.valorbruto) > 0 THEN CAST(((ValorDesconto) / (valorbruto))*100.00 AS NUMERIC(18, 2)) "+
				"             ELSE 0.00 "+
				"         END AS desconto, "+
				"         NULL AS parcela "+
				"  "+
				"     FROM "+
				"         ( "+
				"         SELECT "+
				"             pv.id_pedvenda, "+
				"             MAX(pcli.razaosocialnome) cliente, "+
				"             MAX(pv.valpedido) AS valor, "+
				"             SUM(TRUNC(((pvi.quantidade) * pvi.precoprom), 2)) AS valorBruto, "+
				"             SUM(CAST((pvi.quantidade * pvi.precoprom * pvi.percdesconto/100.00)  AS NUMERIC(18, 4))) AS valorDesconto "+
				"         FROM "+
				"             pedvenda pv, "+
				"             pedvendaitem pvi, "+
				"             tipomovfisc tm, "+
				"             vendedor v, "+
				"             pessoa pcli "+
				"         WHERE "+
				"             pv.id_pedvenda = pvi.id_pedvenda "+
				"             AND pv.id_tipomovfisc = tm.id_tipomovfisc "+
				"             and pcli.id_pessoa = pv.id_pessoa_cli "+
				"             AND tm.tipoimpfiscal = 1 "+
				"             AND tm.classe = 0 "+
				"             AND pv.id_pedvendastatus = 4 "+
				"             AND pv.efetivacao BETWEEN :dt1 AND :dt2 "+
				"             AND pv.id_pessoa_vend = v.id_pessoa "+
				"             AND v.id_tipovendedor = COALESCE(:tipovendedor, v.id_tipovendedor) "+
				"             AND pv.id_pessoa_emp = COALESCE(:id_pessoa_emp, pv.id_pessoa_emp) "+
				"             AND pv.id_pessoa_vend = COALESCE(:vendedor, pv.id_pessoa_vend) "+
				"             and 3 = :tiposql "+
				"         GROUP BY "+
				"             pv.id_pedvenda "+
				"         ) tab ";


				 
				 
		
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("tipo", Hibernate.STRING)
				.addScalar("cliente", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("condPagto", Hibernate.STRING);
		
		query.setParameter("id_pessoa_emp", empresaFilter.getId());
		if(vendedorFilter!=null) {
			query.setParameter("vendedor", vendedorFilter.getId());
		}else {
			query.setParameter("vendedor", null);
		}

		query.setParameter("dt1", dataFilter1);
		query.setParameter("dt2", dataFilter2);
		query.setParameter("tipovendedor", null);
		
		
		query.setParameter("tiposql", vendaFornFPagtoDTO.getTipoSql());
		query.setParameter("ID_FormaPagtoCondPafgto", vendaFornFPagtoDTO.getFormaPagtoRecId()==null?vendaFornFPagtoDTO.getCondPagtoId():vendaFornFPagtoDTO.getFormaPagtoRecId());
			
		query.setResultTransformer(Transformers.aliasToBean(VendaFornPedVenda.class));
			
		return query.list();
	}	

}
