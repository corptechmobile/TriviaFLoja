	package br.com.webapp.model.fb.relatorio.vendaforn.resumo;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class VendaFornResumoDAOHibernate implements VendaFornResumoDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public VendaFornResumo carregar(EmpresaFB empresaFilter, VendedorFB vendedorFB, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2, Date dataAnt1, Date dataAnt2, String vendasPorFilter, String segmentoFilter) {
		
		String sql = "";
		String sqlFiltro1 = "";
		String sqlFiltro2 = "";
		String sqlFiltro3 = "";
		String sqlFiltro4 = "";
		String sqlFiltro5 = "";
		
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
		
		if("pedido".equals(vendasPorFilter)) {
			sql = 
					" select "+
							"     tab2.numClientes, "+
							"     tab2.ticketMedio, "+
							"     tab2.faturamento, "+
							"     tab2.fatAnt-devolucaoAnt as fatAnt, "+
							"     tab2.VALORCUSTO as valorCusto, "+
							"     tab2.LUCRO, "+
							"     case "+
							"         tab2.faturamento when 0 then 0 "+
							"         else (((tab2.lucro/(tab2.faturamento-tab2.devolucao))* 100)) "+
							"     end margem, "+
							"     case "+
							"         tab2.VALORCUSTO when 0 then 0 "+
							"         else (((tab2.lucro/(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDA))* 100)) "+
							"     end markup, "+
							"     tab2.devolucao, "+
							"     tab2.VALCUSTODEVOLVIDA as valorCustoDevolvido, "+
							"     tab2.desconto, "+
							"     tab2.mediaDiaria "+
							" from "+
							"     ( "+
							"     select "+
							"         SUM(tab.numClientes) as numClientes, "+
							"         case "+
							"             when SUM(tab.numClientes) = 0 then 0.0 "+
							"             else cast(((SUM(tab.faturamento) - SUM(tab.devolucao)) / cast(SUM(tab.numClientes) as numeric(18, 4))) as numeric(18, 4)) "+
							"         end as ticketMedio, "+
							"         coalesce(SUM(tab.faturamento), 0.0) as faturamento, "+
							"         coalesce(SUM(tab.fatAnt), 0.0) as fatAnt, "+
							"         coalesce(SUM(tab.VALORCUSTO), 0.0) as VALORCUSTO, "+
							"         ((coalesce(SUM(tab.faturamento), 0.0)-coalesce(SUM(tab.devolucao), 0.0))-coalesce(SUM(tab.VALORCUSTO), 0.0))+ coalesce(SUM(tab.VALCUSTODEVOLVIDA), 0.0) as LUCRO, "+
							"         coalesce(SUM(tab.devolucao), 0.0) as devolucao, "+
							"         coalesce(SUM(tab.devolucaoAnt), 0.0) as devolucaoAnt, "+
							"         coalesce(SUM(tab.VALCUSTODEVOLVIDA), 0.0) as VALCUSTODEVOLVIDA, "+
							"         coalesce(SUM(tab.desconto), 0.0) as desconto, "+
							"         case "+
							"             when MAX(tab.qtdDias) = 0 then ROUND((SUM(tab.faturamento) - SUM(tab.devolucao)), 2) "+
							"             else cast(((SUM(tab.faturamento) - SUM(tab.devolucao)) / MAX(tab.qtdDias)) as numeric(18, 4)) "+
							"         end as mediaDiaria "+
							"     from "+
							"         ( "+
							"         select "+
							"             COUNT(DISTINCT(pv.ID_PESSOA_CLI)) as numClientes, "+
							"             SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) faturamento, "+
							"             0 as fatAnt, "+
							"             SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.CUSTOGERULTCOMPRAUV as numeric(18,4)) else 0 end) VALORCUSTO, "+
							"             0 as devolucao, "+
							"             0 as devolucaoAnt, "+
							"             0 as VALCUSTODEVOLVIDA, "+
							"             COUNT(distinct(pv.efetivacao)) as qtddias, "+
							"             CAST(SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO * pvi.percdesconto as numeric(18, 4)) else 0 end) / SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) AS numeric(18,4)) as desconto "+
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
							"             and cl.id_pessoa = pv.id_pessoa_cli "+
							"             and pv.id_pessoa_vend = v.id_pessoa "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and tmf.id_tipooperacaofiscal = tof.id_tipooperacaofiscal "+
							"             and PVS.EFETIVADO = 1 "+
							"            "+sqlFiltro1+" "+
							"             and pv.id_pessoa_emp = COALESCE(:id_pessoa, pv.id_pessoa_emp) "+
							"             and pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and PV.EFETIVACAO between :dt1 and :dt2 "+
							"     union all "+
							"         select "+
							"             COUNT(distinct(EV.ID_ECFVENDAS)) as numClientes, "+
							"             SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as faturamento, "+
							"             0 as fatAnt, "+
							"             SUM(cast(((eVI.QUANTIDADE-coalesce(evi.qtdpedido, 0)) * evi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) VALORCUSTO, "+
							"             0 as devolucao, "+
							"             0 as devolucaoAnt, "+
							"             0 as VALCUSTODEVOLVIDA, "+
							"             0 as qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             ECF_VENDAS ev, "+
							"             ECF_VENDASITEM eVI, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"         where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and eVI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = ev.id_pessoa_cli "+
							"             and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and ev.CANCELADA = 0 "+
							"             and evi.CANCELADA = 0 "+
							"             and ev.CONCLUIDA = 1 "+
							"             and coalesce(ev.ID_DAV, 0) = 0 "+
							"             and ev.isvenda = 1 "+
							"             and ev.id_pessoa_vend = v.id_pessoa "+
							"             and ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp) "+
							"             AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and ev.DATAVENDA between :dt1 and :dt2 "+
							"            "+sqlFiltro2+" "+
							"     union all "+
							"         select "+
							"             0 as numClientes, "+
							"             0 as faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
							"             0 as devolucaoAnt, "+
							"             SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDA, "+
							"             0 qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             NFVENDAITEM nfi, "+
							"             NFVENDA nf, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
							"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and tmf.ID_TIPOMOVFISC = nf.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp) "+
							"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and ((bdi.id_nfvendaitem is not null)  or (bdi.id_cupomfiscalitem is not null)) "+
							"             and bd.MOMENTO between :dt1 and :dt2 "+
							"            "+sqlFiltro3+" "+
							"     union all "+
							"         select "+
							"             0 as numClientes, "+
							"             0 faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucao, "+
							"             0 as devolucaoAnt, "+
							"             SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) as VALCUSTODEVOLVIDA, "+
							"             0 as qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             ecf_vendasitem evi, "+
							"             ecf_vendas ev, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
							"             and evi.id_ecfvendas = ev.id_ecfvendas "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp) "+
							"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and bd.MOMENTO between :dt1 and :dt2 "+
							"            "+sqlFiltro4+" "+
							"     union all "+
							"         select "+
							"             0 as numClientes, "+
							"             0 faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucao, "+
							"             0 as devolucaoAnt, "+
							"             SUM(cast((bdI.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18, 4))) as VALCUSTODEVOLVIDA, "+
							"             0 as qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             pedvendaitem pvi, "+
							"             pedvenda pv, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
							"             and pvi.id_pedvenda = pv.id_pedvenda "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and tmf.ID_TIPOMOVFISC = bd.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa, bd.id_pessoa_emp) "+
							"             and bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and bd.MOMENTO between :dt1 and :dt2 "+
							"            "+sqlFiltro5+" "+
							"         UNION ALL "+
							"             select "+
							"                 0 as numClientes, "+
							"                 0 faturamento, "+
							"                 SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) as fatAnt, "+
							"                 0 VALORCUSTO, "+
							"                 0 as devolucao, "+
							"                 0 as devolucaoAnt, "+
							"                 0 as VALCUSTODEVOLVIDA, "+
							"                 0 as qtddias, "+
							"                 0 as desconto "+
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
							"                 AND pv.id_pessoa_emp = COALESCE(:id_pessoa, pv.id_pessoa_emp) "+
							"                 AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  "+
							"                 and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	              and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"                 and tmf.CLASSE in (0, 1) "+
							"                 and PVS.EFETIVADO = 1 "+
							"                 and PV.EFETIVACAO between :dtAnt1 and :dtAnt2 "+
							"                 "+sqlFiltro1+" "+
							"         union all "+
							"             select "+
							"                 0 as numClientes, "+
							"                 0 as faturamento, "+
							"                 SUM(TRUNC((EVI.quantidade-coalesce(EVI.qtdpedido, 0))*(EVI.valorliquidoitem / evi.quantidade), 2)) as fatAnt, "+
							"                 0 VALORCUSTO, "+
							"                 0 as devolucao, "+
							"                 0 as devolucaoAnt, "+
							"                 0 as VALCUSTODEVOLVIDA, "+
							"                 0 as qtddias, "+
							"                 0 as desconto "+
							"             from "+
							"                 ECF_VENDAS ev, "+
							"                 ECF_VENDASITEM eVI, "+
							"                 PRODUTO PR , "+
							"                 linhaproduto lp, "+
							"                 cliente cl, "+
							"                 gestaovendamob gvm, "+
							"                 vendedor v, "+
							"                 TIPOMOVFISC tmf "+
							"             where eV.ID_ECFVENDAS = eVI.ID_ECFVENDAS "+
							"                 and pr.id_linhaproduto = lp.id_linhaproduto "+
							"                 and eVI.ID_PRODUTO = PR.ID_PRODUTO "+
							"                 and cl.id_pessoa = ev.id_pessoa_cli "+
							"                 and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
							"                 and tmf.CLASSE in (0, 1) "+
							"                 and ev.CANCELADA = 0 "+
							"                 and evi.CANCELADA = 0 "+
							"                 and ev.CONCLUIDA = 1 "+
							"                 and coalesce(ev.ID_DAV, 0) = 0 "+
							"                 and ev.isvenda = 1 "+
							"                 and ev.id_pessoa_vend = v.id_pessoa "+
							"                 AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp) "+
							"                 and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"                 AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend)  "+
							"                 and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	              and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"                 and ev.DATAVENDA between :dtAnt1 and :dtAnt2 "+
							"                 "+sqlFiltro2+" "+
							"         union all "+
							"              select "+
							"             0 as numClientes, "+
							"             0 as faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             0 as devolucao, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnt, "+
							"             0 as VALCUSTODEVOLVIDA, "+
							"             0 qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             NFVENDAITEM nfi, "+
							"             NFVENDA nf, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.ID_NFVENDAITEM = nfi.ID_NFVENDAITEM "+
							"             and nfi.ID_NFVENDA = nf.ID_NFVENDA "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and tmf.ID_TIPOMOVFISC = nf.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp) "+
							"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and bd.MOMENTO between :dtAnt1 and :dtAnt2 "+
							"            "+sqlFiltro3+" "+
							"     union all "+
							"         select "+
							"             0 as numClientes, "+
							"             0 faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             0 as devolucao, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18,4))) as devolucaoAnt, "+
							"             0 as VALCUSTODEVOLVIDA, "+
							"             0 as qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             ecf_vendasitem evi, "+
							"             ecf_vendas ev, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"             where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.id_ecfvendasitem = evi.id_ecfvendasitem "+
							"             and evi.id_ecfvendas = ev.id_ecfvendas "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp) "+
							"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and bd.MOMENTO between :dtAnt1 and :dtAnt2 "+
							"            "+sqlFiltro4+" "+
							"     union all "+
							"         select "+
							"             0 as numClientes, "+
							"             0 faturamento, "+
							"             0 as fatAnt, "+
							"             0 VALORCUSTO, "+
							"             0 as devolucao, "+
							"             SUM(cast((bdi.VALORUNIT*bdI.QUANTIDADE) as numeric(18, 4))) as devolucaoAnt, "+
							"             0 as VALCUSTODEVOLVIDA, "+
							"             0 as qtddias, "+
							"             0 as desconto "+
							"         from "+
							"             BOLETIMDEVOLUCAO bd, "+
							"             BOLETIMDEVOLITEM bdI, "+
							"             pedvendaitem pvi, "+
							"             pedvenda pv, "+
							"             PRODUTO PR , "+
							"             linhaproduto lp, "+
							"             cliente cl, "+
							"             gestaovendamob gvm, "+
							"             vendedor v, "+
							"             TIPOMOVFISC tmf "+
							"        where bd.ID_BOLETIMDEVOLUCAO = bdI.ID_BOLETIMDEVOLUCAO "+
							"             and bdi.id_pedvendaitem = pvi.id_pedvendaitem "+
							"             and pvi.id_pedvenda = pv.id_pedvenda "+
							"             and pr.id_linhaproduto = lp.id_linhaproduto "+
							"             and bdI.ID_PRODUTO = PR.ID_PRODUTO "+
							"             and cl.id_pessoa = bd.id_pessoa_cli "+
							"             and tmf.ID_TIPOMOVFISC = pv.ID_TIPOMOVFISC "+
							"             and tmf.CLASSE in (0, 1) "+
							"             and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
							"             and bd.id_pessoa_vend = v.id_pessoa "+
							"             and gvm.id_gestaovendamob = v.id_gestaovendamob "+
							"             and bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp) "+
							"             AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
							"             and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
							"	          and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
							"             and bd.MOMENTO between :dtAnt1 and :dtAnt2 "+
							"            "+sqlFiltro5+" "+
							"          )tab "+
							"       ) tab2 ";
					
		}else {
		
		
			sql =    " SELECT tab2.numClientes, "+
					"        tab2.ticketMedio, "+
					"        tab2.faturamento, "+
					"        tab2.fatAnt-devolucaoAnt as fatAnt, "+
					"        tab2.VALORCUSTO as valorCusto, "+
					"        tab2.LUCRO, "+
                    "        case tab2.faturamento when 0 then 0 else (((tab2.lucro/(tab2.faturamento-tab2.devolucao))*100)) end margem, "+
                    "        case tab2.VALORCUSTO when 0 then 0 else (((tab2.lucro/(tab2.VALORCUSTO-tab2.VALCUSTODEVOLVIDA))*100)) end markup, "+
					"        tab2.devolucao, "+
					"        tab2.VALCUSTODEVOLVIDA as valorCustoDevolvido, "+
					"        tab2.desconto, "+
					"        tab2.mediaDiaria "+
					"   FROM ( "+
					" SELECT SUM(tab.numClientes) AS numClientes,  "+
					"    CASE WHEN SUM(tab.numClientes) = 0 THEN 0.0 ELSE CAST(((SUM(tab.faturamento) - SUM(tab.devolucao)) / cast(SUM(tab.numClientes) as numeric(18,4))) AS numeric(18,4)) END AS ticketMedio,   "+
					"    COALESCE(SUM(tab.faturamento), 0.0) AS faturamento,   "+
					"    COALESCE(SUM(tab.fatAnt), 0.0) AS fatAnt,   "+
					"    COALESCE(SUM(tab.VALORCUSTO), 0.0) AS VALORCUSTO, "+
					"    ((COALESCE(SUM(tab.faturamento), 0.0)-COALESCE(SUM(tab.devolucao), 0.0))-COALESCE(SUM(tab.VALORCUSTO), 0.0))+COALESCE(SUM(tab.VALCUSTODEVOLVIDA), 0.0) AS LUCRO, "+
					"    COALESCE(SUM(tab.devolucao), 0.0) AS devolucao, "+
					"    COALESCE(SUM(tab.devolucaoAnt), 0.0) AS devolucaoAnt, "+						
					"    COALESCE(SUM(tab.VALCUSTODEVOLVIDA), 0.0) AS VALCUSTODEVOLVIDA, "+
					"    COALESCE(SUM(tab.desconto), 0.0) AS desconto,  "+
					"    CASE WHEN MAX(tab.qtdDias) = 0 THEN ROUND((SUM(tab.faturamento) - SUM(tab.devolucao)), 2) ELSE CAST(((SUM(tab.faturamento) - SUM(tab.devolucao)) / MAX(tab.qtdDias)) AS numeric(18,4)) END AS mediaDiaria   "+
					"  FROM (   "+
					"      SELECT COUNT(DISTINCT(ev.id_ecfvendas)) AS numClientes,   "+
					"                SUM(evi.valorliquidoitem) AS faturamento, "+
					"                0 AS fatAnt, "+
					"                SUM(cast((evi.QUANTIDADE * evi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALORCUSTO, "+
					"                0 AS devolucao, "+
					"                0 AS devolucaoAnt, "+						
					"                0 as VALCUSTODEVOLVIDA, "+
					"                COUNT(DISTINCT(ev.datavenda)) AS qtddias,   "+
					"                (SELECT CAST(SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO * pvi.percdesconto as numeric(18, 4)) else 0 end) / SUM(case tmf.CLASSE when 0 then cast(iif(pv.id_pedvendastatus in (4, 5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * PVI.PRECO as numeric(18, 4)) else 0 end) AS numeric(18,4)) AS desconto   "+
					"                      FROM pedvenda pv,   "+
					"                           pedvendaitem pvi,   "+
					"                           pedvendastatus pvs,  "+
					"                           tipomovfisc tmf  "+
					"                 WHERE pv.id_pedvenda = pvi.id_pedvenda    "+
					"                       AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS  "+
					"                       AND pv.id_tipomovfisc = tmf.id_tipomovfisc  "+
					"                       AND pvs.EFETIVADO = 1  "+
					"                       AND tmf.classe in (0,1)  "+
					"                       AND pv.efetivacao between :dt1 AND :dt2   "+
					"                       AND pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)   "+
					"                       AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)) AS desconto  "+
					"          FROM ecf_vendas ev,   "+
					"               ecf_vendasitem evi, "+
					"               PRODUTO PR,  "+
					"               vendedor v, "+
					"               TIPOMOVFISC tmf "+
					"          WHERE evi.id_ecfvendas = ev.id_ecfvendas   "+
					"            AND evi.id_produto = pr.id_produto "+
					"            and ev.id_pessoa_vend = v.id_pessoa "+
					"            and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
					"            and tmf.CLASSE in (0, 1) "+
					"            AND ev.datavenda between :dt1 AND :dt2   "+
					"            AND ev.concluida = 1   "+
					"            AND ev.cancelada = 0   "+
					"            AND evi.cancelada = 0  "+
					"            AND ev.isvenda = 1 "+
					"            and coalesce(ev.ID_DAV,0) = 0 "+
					"            AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp) "+
					"            AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend) "+
					"            and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	         and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            "+sqlFiltro2+" "+
					"     UNION ALL   "+
					"     SELECT 0 AS numClientes,   "+ //COUNT(DISTINCT(pv.ID_PESSOA_CLI))
					"               SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS faturamento,  "+
					"               0 AS fatAnt,  "+
					"               sum(cast((nfvi.QUANTIDADE * nfvi.CUSTOMEDIOONLINE) as numeric(18,4))) VALORCUSTO, "+
					"               0 AS devolucao,   "+
					"               0 AS devolucaoAnt, "+						
					"               0 as VALCUSTODEVOLVIDA, "+
					"               0 AS qtddias,   "+
					"               0 AS desconto   "+
					"            FROM NFVENDA nfv,   "+
					"                 NFVENDAITEM nfvi,   "+
					"                 TIPOMOVFISC tmf,  "+
					"                 PEDVENDA pv,  "+
					"                 PRODUTO PR,  "+
					"                 vendedor v "+
					"            WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA   "+
					"              AND nfvi.id_produto = pr.id_produto "+
					"              AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC  "+
					"              AND nfv.id_pedvenda = pv.id_pedvenda  "+
					"              and pv.id_pessoa_vend = v.id_pessoa "+
					"              AND nfv.DATAEMISS between :dt1 AND :dt2   "+
					"              AND nfv.CANCELADA = 0   "+
					"              AND tmf.CLASSE in (0, 1) "+
					"              AND nfv.TIPO = 'S'   "+
					"              AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)  "+
					"              AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  "+
					"              and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	           and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            "+sqlFiltro1+" "+
					"     UNION ALL  "+
					"      SELECT 0 AS numClientes,   "+
					"                0 AS faturamento, "+
					"                SUM(evi.valorliquidoitem) AS fatAnt, "+
					"                0 VALORCUSTO, "+
					"                0 AS devolucao, "+
					"               0 AS devolucaoAnt, "+						
					"                0 as VALCUSTODEVOLVIDA, "+
					"                0 qtddias,   "+
					"                0 AS desconto  "+
					"          FROM ecf_vendas ev,   "+
					"               ecf_vendasitem evi, "+
					"               PRODUTO PR,  "+
					"               vendedor v, "+
					"               TIPOMOVFISC tmf "+
					"          WHERE evi.id_ecfvendas = ev.id_ecfvendas   "+
					"            AND evi.id_produto = pr.id_produto "+
					"            and ev.id_pessoa_vend = v.id_pessoa "+
					"            and tmf.ID_TIPOMOVFISC = ev.ID_TIPOMOVFISC "+
					"            and tmf.CLASSE in (0, 1) "+
					"            AND ev.datavenda between :dtAnt1 AND :dtAnt2   "+
					"            AND ev.concluida = 1   "+
					"            AND ev.cancelada = 0   "+
					"            AND evi.cancelada = 0  "+
					"            AND ev.isvenda = 1 "+
					"            AND coalesce(ev.ID_DAV,0) = 0 "+
					"            AND ev.id_pessoa_emp = COALESCE(:id_pessoa, ev.id_pessoa_emp) "+
					"            AND ev.id_pessoa_vend = COALESCE(:vendedor,ev.id_pessoa_vend) "+
					"            and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	         and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            "+sqlFiltro2+" "+
					"     UNION ALL   "+
					"     SELECT 0 AS numClientes,   "+
					"               0 AS faturamento,  "+
					"               SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO), 2) AS numeric(18,4)) ELSE 0 END) AS fatAnt,  "+
					"               0 VALORCUSTO, "+
					"               0 AS devolucao,   "+
					"               0 AS devolucaoAnt, "+						
					"               0 as VALCUSTODEVOLVIDA, "+
					"               0 AS qtddias,   "+
					"               0 AS desconto   "+
					"            FROM NFVENDA nfv,   "+
					"                 NFVENDAITEM nfvi,   "+
					"                 TIPOMOVFISC tmf,  "+
					"                 PEDVENDA pv,  "+
					"                 PRODUTO PR,  "+
					"                 vendedor v "+
					"            WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA   "+
					"              AND nfvi.id_produto = pr.id_produto "+
					"              AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC  "+
					"              AND nfv.id_pedvenda = pv.id_pedvenda  "+
					"              and pv.id_pessoa_vend = v.id_pessoa "+
					"              AND nfv.DATAEMISS between :dtAnt1 AND :dtAnt2   "+
					"              AND nfv.CANCELADA = 0   "+
					"              AND tmf.CLASSE in (0, 1) "+
					"              AND nfv.TIPO = 'S'   "+
					"              AND nfv.id_pessoa_emp = COALESCE(:id_pessoa,nfv.id_pessoa_emp)  "+
					"              AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend)  "+
					"              and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	           and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            "+sqlFiltro1+" "+
					"     UNION ALL  "+
					"     SELECT 0 AS numClientes,   "+
					"               0 AS faturamento,   "+
					"               0 AS fatAnt,  "+
					"               0 as VALORCUSTO, "+
					"               SUM(bdi.quantidade * bdi.valorunit) AS devolucao, "+
					"               0 AS devolucaoAnt, "+						
					"               SUM(cast((bdi.QUANTIDADE * bdi.CUSTOGERULTCOMPRAUV) as numeric(18,4))) VALCUSTODEVOLVIDA, "+
					"               0 AS qtddias,  "+
					"               0 AS desconto  "+
					"          FROM boletimdevolucao bd,   "+
					"               boletimdevolitem bdi,   "+
					" 		        tipomovfisc tmf, "+
					"               PRODUTO PR,  "+
					"               vendedor v "+
					"          WHERE bd.id_boletimdevolucao = bdi.id_boletimdevolucao   "+
					"            AND bdi.id_produto = pr.id_produto "+
					"            AND tmf.id_tipomovfisc = bd.id_tipomovfisc " +
					"            and bd.id_pessoa_vend = v.id_pessoa "+
				    "            AND tmf.altestqfisico = 1 " +	
					"            and tmf.CLASSE in (0, 1) "+
					"            AND bd.momento between :dt1 AND :dt2   "+
					"            AND bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)  "+
					"            AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
					"            and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	         and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            AND bd.id_boletimdevolstatus <> 1  "+
					"            "+sqlFiltro3+" "+
					"     UNION ALL  "+
					"     SELECT 0 AS numClientes,   "+
					"               0 AS faturamento,   "+
					"               0 AS fatAnt,  "+
					"               0 as VALORCUSTO, "+
					"               0, "+
					"               SUM(bdi.quantidade * bdi.valorunit) AS devolucaoAnt, "+						
					"               0 VALCUSTODEVOLVIDA, "+
					"               0 AS qtddias,  "+
					"               0 AS desconto  "+
					"          FROM boletimdevolucao bd,   "+
					"               boletimdevolitem bdi,   "+
					" 		        tipomovfisc tmf, "+
					"               PRODUTO PR,  "+
					"               vendedor v "+
					"          WHERE bd.id_boletimdevolucao = bdi.id_boletimdevolucao   "+
					"            AND bdi.id_produto = pr.id_produto "+
					"            AND tmf.id_tipomovfisc = bd.id_tipomovfisc " +
					"            and tmf.CLASSE in (0, 1) "+
					"            and bd.id_pessoa_vend = v.id_pessoa "+
				    "            AND tmf.altestqfisico = 1 " +						
					"            AND bd.momento between :dtAnt1 AND :dtAnt2   "+
					"            AND bd.id_pessoa_emp = COALESCE(:id_pessoa,bd.id_pessoa_emp)  "+
					"            AND bd.id_pessoa_vend = COALESCE(:vendedor,bd.id_pessoa_vend)  "+
					"            and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor)  "+
					"	         and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) "+
					"            AND bd.id_boletimdevolstatus <> 1  "+
					"            "+sqlFiltro3+" "+
					"  )tab "+
					"  ) tab2 ";
		}
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("numClientes", Hibernate.INTEGER)
				.addScalar("ticketMedio", Hibernate.DOUBLE)
				.addScalar("faturamento", Hibernate.DOUBLE)
				.addScalar("fatAnt", Hibernate.DOUBLE)
				.addScalar("devolucao", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("mediaDiaria", Hibernate.DOUBLE)
				.addScalar("margem", Hibernate.DOUBLE)
				.addScalar("markup", Hibernate.DOUBLE)
				.addScalar("lucro", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("valorCusto", Hibernate.DOUBLE)
				.addScalar("valorCustoDevolvido", Hibernate.DOUBLE)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2)
				.setParameter("dtAnt1", dataAnt1)
				.setParameter("dtAnt2", dataAnt2);
				
				if(empresaFilter!=null) {
					query.setParameter("id_pessoa", empresaFilter.getId());
				}else {
					query.setParameter("id_pessoa", null);
				}	
				
				if(vendedorFB!=null) {
					query.setParameter("vendedor", vendedorFB.getId());
				}else {
					query.setParameter("vendedor", null);
				}			
				
				if(fornecedorFilter!=null) {
					query.setParameter("fornecedor", fornecedorFilter.getId());
				}else {
					query.setParameter("fornecedor", null);
				}
				
				if(tipoVendedorFilter!=null) {
					query.setParameter("tipovendedor", tipoVendedorFilter.getId());
				}else {
					query.setParameter("tipovendedor", null);
				}
				
				
		query.setResultTransformer(Transformers.aliasToBean(VendaFornResumo.class));
		
		query.setMaxResults(1);
		
		return (VendaFornResumo) query.uniqueResult();
		
	}
	
	public VendaFornResumo carregarSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedor, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(tab.numClientes) AS numClientes, ")
				  .append(" CASE WHEN SUM(tab.numClientes) = 0 THEN 0.0 ELSE CAST(((SUM(tab.faturamento) - SUM(tab.devolucao)) / cast(SUM(tab.numClientes) as numeric(18,4))) AS numeric(18,4)) END AS ticketMedio, ") 
				  .append(" COALESCE(SUM(tab.faturamento), 0.0) AS faturamento, ") 
				  .append(" COALESCE(SUM(tab.devolucao), 0.0) AS devolucao, ") 
				  .append(" COALESCE(SUM(tab.desconto), 0.0) AS desconto, ")
				  .append(" CASE WHEN MAX(tab.qtdDias) = 0 THEN ROUND((SUM(tab.faturamento) - SUM(tab.devolucao)), 2) ELSE CAST(((SUM(tab.faturamento) - SUM(tab.devolucao)) / MAX(tab.qtdDias)) AS numeric(18,4)) END AS mediaDiaria ") 
				.append(" FROM ( "); 
				  
		          sql.append("SELECT COUNT(DISTINCT(pv.ID_PESSOA_CLI)) AS numClientes, ")
		                    .append("SUM(TRUNC((iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender)) * pvi.preco), 2)) AS faturamento,  ")
		                    .append("0 AS devolucao,   ")
		                    .append("COUNT(DISTINCT(cast (pv.ENTRADA as DATE))) AS qtddias,  ")
		                    .append("SUM(pvi.quantidade * pvi.preco * pvi.percdesconto)/sum(pvi.quantidade * pvi.preco) AS desconto  ")
		               .append("FROM pedvenda pv,  ")
		                    .append("pedvendaitem pvi,  ")
					  	    .append(" pedvendastatus pvs, ")
					  	    .append(" tipomovfisc tmf, ")
					  	    .append(" produto pr, ")
					  	    .append(" vendedor v ")
		            .append("WHERE pv.id_pedvenda = pvi.id_pedvenda   ")
		            .append("  and pvi.id_produto = pr.id_produto ")
		            .append("  and pv.id_pessoa_vend = v.id_pessoa ")
				    .append("  AND pv.ID_PEDVENDASTATUS = pvs.ID_PEDVENDASTATUS ")
					.append("  AND pv.id_tipomovfisc = tmf.id_tipomovfisc ")
				    .append("  AND pvs.EFETIVADO = 1 ")
				    .append("  AND tmf.classe in (0,1) ")
		            .append("  AND pv.efetivacao between :dt1 AND :dt2  ")
		            .append(  "AND pv.id_pessoa_emp = COALESCE(:id_pessoa,pv.id_pessoa_emp)  ")
		            .append("  AND pv.id_pessoa_vend = COALESCE(:vendedor,pv.id_pessoa_vend) ")
		            .append("  and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
		            .append("  and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) ");

		
				  sql.append("UNION ALL "); 
				  
				  	sql.append("SELECT 0 AS numClientes, ") 
			  			     .append(" 0 AS faturamento, ") 
			  			     .append(" ROUND(SUM(bi.quantidade * bi.valorunit), 2) AS devolucao, ") 
			  			     .append(" 0 AS qtddias, ")
			  			     .append(" 0 AS desconto ")
				  		.append(" FROM boletimdevolucao b, ") 
					  		 .append(" boletimdevolitem bi, ")
					  		 .append(" tipomovfisc tmf, ")
					  		.append(" produto pr, ")
					  		.append(" vendedor v ")
					  	.append(" WHERE b.id_boletimdevolucao = bi.id_boletimdevolucao ")
					  	.append("  and b.id_pessoa_vend = v.id_pessoa ")
					  	  .append(" and bi.id_produto = pr.id_produto ")
					  	  .append(" AND tmf.id_tipomovfisc = b.id_tipomovfisc ")
					  	  .append(" AND tmf.altestqfisico = 1 ")
					  	  .append(" AND b.momento between :dt1 AND :dt2 ") 
						  .append(" AND b.id_pessoa_emp = COALESCE(:id_pessoa,b.id_pessoa_emp) ") 
						  .append(" AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend) ")
						  .append(" and v.id_tipovendedor = COALESCE(:tipovendedor,v.id_tipovendedor) ")
						  .append(" and pr.id_pessoa_forn = COALESCE(:fornecedor,pr.id_pessoa_forn) ")
						  .append(" AND b.id_boletimdevolstatus <> 1 ");
				  	
	  			sql.append(") tab ");
				
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("numClientes", Hibernate.INTEGER)
				.addScalar("ticketMedio", Hibernate.DOUBLE)
				.addScalar("faturamento", Hibernate.DOUBLE)
				.addScalar("devolucao", Hibernate.DOUBLE)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("mediaDiaria", Hibernate.DOUBLE)
				.setParameter("dt1", dataFilter1)
				.setParameter("dt2", dataFilter2);
				
				if(empresaFilter!=null) {
					query.setParameter("id_pessoa", empresaFilter.getId());
				}else {
					query.setParameter("id_pessoa", null);
				}
		
				if(vendedor!=null) {
					query.setParameter("vendedor", vendedor.getId());
				}else {
					query.setParameter("vendedor", null);
				}			
				
				if(fornecedorFilter!=null) {
					query.setParameter("fornecedor", fornecedorFilter.getId());
				}else {
					query.setParameter("fornecedor", null);
				}
				
				if(tipoVendedorFilter!=null) {
					query.setParameter("tipovendedor", tipoVendedorFilter.getId());
				}else {
					query.setParameter("tipovendedor", null);
				}
				
				query.setResultTransformer(Transformers.aliasToBean(VendaFornResumo.class));
				
		
		query.setMaxResults(1);
		
		return (VendaFornResumo) query.uniqueResult();
	}	

}
