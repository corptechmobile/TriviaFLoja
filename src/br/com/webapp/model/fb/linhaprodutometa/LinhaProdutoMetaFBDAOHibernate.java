package br.com.webapp.model.fb.linhaprodutometa;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.web.util.DAOException;

public class LinhaProdutoMetaFBDAOHibernate implements LinhaProdutoMetaFBDAO{

	private StringBuilder COLUMNS;

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public LinhaProdutoMetaFBDAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" omi.ID_LINHAPRODUTOMETA as id, ")
			   .append(" omi.ID_ORCAMENTOMETA as idOrcamentoMeta, ")
			   .append(" om.anoMes, ")
			   .append(" om.ID_PESSOA_EMP as idPessoaEmp, ")
			   .append(" f.NOMEFANTMNEM as descFornecedor, ")
			   .append(" f.cnpjCpf, ")
			   .append(" omi.ID_ORCAMENTOGRUPO as idOrcamentoGrupo, ")
			   .append(" og.DESCRICAO as descOrcamentoGrupo, ")
			   .append(" COALESCE(omi.VALORORCADO,0) as valor, ")
			   .append(" 0 AS valorPrevAnt, ")
			   .append(" 0 AS valorRealAnt, ")
			   .append(" 0 AS percPrevRealAnt, ") 
			   .append(" 0 AS percPrevRealAtual, ")
			   .append(" og.PERCFAT AS percFaturamento ");

	}

	
	@Override
	public LinhaProdutoMetaFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("   FROM ORCAMENTOMETA om, PESSOA f, orcamentogrupo og, linhaprodutometa omi ")
		   .append("  WHERE om.ID_PESSOA_EMP = f.ID_PESSOA ")
		   .append("    and om.ID_ORCAMENTOMETA = omi.ID_ORCAMENTOMETA ")
		   .append("    and omi.ID_ORCAMENTOGRUPO = og.ID_ORCAMENTOGRUPO ")
		   .append("    and omi.ID_LINHAPRODUTOMETA = :id ");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idOrcamentoMeta", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("idOrcamentoGrupo", Hibernate.INTEGER)
				.addScalar("descOrcamentoGrupo", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percFaturamento", Hibernate.DOUBLE)

				.setResultTransformer(Transformers.aliasToBean(LinhaProdutoMetaFB.class));
			query.setParameter("id", id);
			query.setMaxResults(1);
		return (LinhaProdutoMetaFB) query.uniqueResult();
	}


@SuppressWarnings("unchecked")
@Override
public List<LinhaProdutoMetaFB> listar(String anomes, String anomesref, Integer empresa, Integer vendedor, Integer nivelLinhaProduto, boolean incluirDevolucao) {
	String varIncluirDevolucao = "";
	if(incluirDevolucao) {
		varIncluirDevolucao = "Sum(tab.valorReal+tab.valorRealDevol)  AS valorRealAnt, "+
							  "round(case sum(tab.valorPrevAnt) when 0 then 0 else ((sum(tab.valorReal)+sum(tab.valorRealDevol))/sum(tab.valorPrevAnt))*100 end,0) as percPrevRealAnt, ";
	}else {
		varIncluirDevolucao = "Sum(tab.valorReal)  AS valorRealAnt, "+
							  "round(case sum(tab.valorPrevAnt) when 0 then 0 else (sum(tab.valorReal)/sum(tab.valorPrevAnt))*100 end,0) as percPrevRealAnt, ";
	}
	
	String sql = ""+
	"SELECT   	 tab.id               AS idLinhaProduto, "+
				"max(tab.id_linhaprodutometa) as id, "+
				"Max(tab.descricao)   AS descLinhaProduto, "+
			    " "+varIncluirDevolucao+" "+
				"Sum(tab.valorReal+tab.valorRealDevol)  AS valorRealAnt, "+
			    "Sum(tab.valorRealDevol) AS valorRealDevol, "+
			    "sum(tab.valorPrev) as valor, "+
			    "sum(tab.valorPrevAnt) as valorPrevAnt, "+
			    "round(case sum(tab.valorPrevAnt) when 0 then 0 else (sum(tab.valorPrev)/sum(tab.valorPrevAnt))*100 end,0) as percPrevRealAtual, "+
			    "sum(tab.PERCPOSITIVACAO) as PERCPOSITIVACAO, "+
			    "sum(tab.MIXPRODUTO) as MIXPRODUTO "+
			" "+
			"FROM     ( "+
			             "SELECT   dep.id_linhaproduto AS id, "+
			                      "Max(dep.descricao)  AS descricao, "+
			                      "Max(lpm.id_linhaprodutometa)  AS id_linhaprodutometa, "+ 
			                      "0 as valorReal, "+
			                      "0 as valorRealDevol, "+
			                      "max(lpm.VALOR) as valorPrev, "+
			                      "max(lpmAnt.VALOR) as valorPrevAnt, "+
			                      "max(lpm.PERCPOSITIVACAO) as PERCPOSITIVACAO, "+
			                      "max(lpm.MIXPRODUTO) as MIXPRODUTO "+
			               "FROM   linhaproduto lp, "+
			                      "linhaproduto dep "+
			                      "LEFT JOIN LINHAPRODUTOMETA lpm ON (lpm.ID_LINHAPRODUTO = dep.ID_LINHAPRODUTO and  "+
			                                                         "lpm.ID_PESSOA_EMP = :empresa and "+
			                                                         "lpm.ID_PESSOA_VEND = :vendedor and "+
			                                                         "lpm.ANOMES = :anoMes) "+
			                      "LEFT JOIN LINHAPRODUTOMETA lpmAnt ON (lpmAnt.ID_LINHAPRODUTO = dep.ID_LINHAPRODUTO and "+
			                                                         "lpmAnt.ID_PESSOA_EMP = :empresa and "+
			                                                         "lpmAnt.ID_PESSOA_VEND = :vendedor and "+
			                                                         "lpmAnt.ANOMES = :anoMesRef) "+
			              "WHERE  dep.codedt=substring(lp.codedt from 1 FOR :nivelLinhaProd) "+
			                "AND dep.id_linhaproduto not in (1,2) "+                                         
			              "GROUP BY dep.ID_LINHAPRODUTO "+
			             "UNION ALL "+
			             "SELECT   dep.id_linhaproduto AS id, "+
			                      "Max(dep.descricao)  AS descricao, "+
			                      "null AS id_linhaprodutometa, "+
			                      "Sum(TRUNC((ecfi.quantidade-COALESCE(ecfi.qtdpedido,0))*(ecfi.valorliquidoitem/ecfi.quantidade),2)) AS valorReal, "+
			                      "0 AS valorRealDevol, "+
			                      "0 as valorPrev, "+
			                      "0 as valorPrevAnt, "+
			                      "0 as PERCPOSITIVACAO, "+
			                      "0 as MIXPRODUTO "+
			             "FROM     ecf_vendas ecf, "+
			                      "produto pr,  "+
			                      "linhaproduto lp,  "+
			                      "linhaproduto dep,  "+
			                      "ecf_vendasitem ecfi  "+
			             "WHERE    ecf.id_ecfvendas=ecfi.id_ecfvendas  "+
			             "AND      dep.codedt=substring(lp.codedt from 1 FOR :nivelLinhaProd) "+
			             "AND      pr.id_linhaproduto=lp.id_linhaproduto  "+
			             "AND      ecfi.id_produto=pr.id_produto  "+
			             "AND      ((extract(year from ecf.datavenda)||LPAD(EXTRACT(MONTH FROM ecf.datavenda),2,'0')) = :anoMesRef) "+
			             "AND      ecf.id_pessoa_emp = :empresa "+
			             "AND      ecf.id_pessoa_vend = :vendedor "+
                         "AND      coalesce(ecf.ID_DAV,0) = 0 "+
                         "AND      ecf.isvenda = 1 "+
			             "AND      ecf.concluida=1 "+
			             "AND      ecf.cancelada=0 "+
			             "AND      ecfi.cancelada=0  "+
			             "GROUP BY dep.id_linhaproduto  "+
			             "UNION ALL  "+
			             "SELECT   dep.id_linhaproduto AS id, "+
			                      "max(dep.descricao)  AS descricao, "+
			                      "null  AS id_linhaprodutometa, "+
			                      "sum(trunc(iif(pv.id_pedvendastatus in (4,5), pvi.quantidade,(pvi.quantidade - pvi.qtdsaldoatender))*pvi.preco,2)) AS valorReal, "+
			                      "0 AS valorRealDevol, "+
			                      "0 as valorPrev, "+
			                      "0 as valorPrevAnt, "+
			                      "0 as PERCPOSITIVACAO, "+
			                      "0 as MIXPRODUTO "+
			             "FROM     linhaproduto lp,  "+
			                      "linhaproduto dep,  "+
			                      "produto pr,  "+
			                      "pedvenda pv,  "+
			                      "pedvendaitem pvi  "+
			             "WHERE    pvi.id_produto = pr.id_produto  "+
			             "AND      pr.id_linhaproduto = lp.id_linhaproduto  "+
			             "AND      dep.codedt = substring(lp.codedt FROM 1 FOR :nivelLinhaProd) "+
			             "AND      pv.id_pedvenda = pvi.id_pedvenda  "+
			             "AND      ((extract(year from pv.efetivacao)||LPAD(EXTRACT(MONTH FROM pv.efetivacao),2,'0')) = :anoMesRef) "+
			             "AND      pv.id_pedvendastatus IN (4,5,6,7) "+
			             "AND      pv.id_pessoa_emp = :empresa "+
			             "AND      pv.id_pessoa_vend = :vendedor "+
			             "GROUP BY dep.id_linhaproduto  "+
			             "UNION ALL  "+
		                   "SELECT "+
                           "dep.id_linhaproduto                         AS id, "+
                           "max(dep.descricao)                          AS descricao,   "+
                           "null                       AS id_linhaprodutometa,  "+
                           "0 AS valorReal, "+
                           "sum(cast(bdi.quantidade * bdi.valorunit*-1 AS numeric(18,2))) AS valorRealDevol, "+
                           "0 as valorPrev,  "+
                           "0 as valorPrevAnt,  "+
                           "0 as PERCPOSITIVACAO,  "+
                           "0 as MIXPRODUTO  "+
                   "FROM   boletimdevolucao bd, "+
                          "boletimdevolitem bdi,   "+
                          "produto prd,  "+
                          "linhaproduto lp,  "+
                          "LINHAPRODUTO dep,  "+
                          "pessoa pvend,   "+
                          "vendedor v "+
                   "WHERE  bd.id_boletimdevolucao = bdi.id_boletimdevolucao  "+
                   "AND    bdi.ID_PRODUTO = prd.ID_PRODUTO  "+
                   "AND    prd.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO  "+
                   "AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivelLinhaProd) "+
                   "AND    pvend.id_pessoa = bd.id_pessoa_vend   "+
                   "AND    pvend.id_pessoa = v.id_pessoa   "+
                   "AND    bd.id_pessoa_emp = :empresa "+
                   "AND    bd.id_boletimdevolstatus <> 1   "+
                   "AND    ((extract(year from bd.momento)||LPAD(EXTRACT(MONTH FROM bd.momento),2,'0')) = :anoMesRef) "+
                   "AND    v.id_pessoa = COALESCE(:vendedor,v.id_pessoa) "+
                   "AND    1 = :IncluiDevol "+
                   "group by dep.ID_LINHAPRODUTO ) tab  "+
			"GROUP BY tab.id  "+
			"ORDER BY 4 desc  ";	

		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("idLinhaProduto", Hibernate.INTEGER)
				.addScalar("descLinhaProduto", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percPositivacao", Hibernate.DOUBLE)
				.addScalar("mixProduto", Hibernate.DOUBLE)				
				.setResultTransformer(Transformers.aliasToBean(LinhaProdutoMetaFB.class));
			query.setParameter("anoMes", anomes);
			query.setParameter("anoMesRef", anomesref);
	        query.setParameter("empresa", empresa);
	        query.setParameter("vendedor", vendedor);
	        query.setParameter("IncluiDevol", 1);
	        query.setParameter("nivelLinhaProd", nivelLinhaProduto);


		return query.list();
	}

	@Override
	public Integer insert(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException {
		try {
			Double percPositivacao = 0.0;
			if(linhaProdutoMetaFB.getPercPositivacao()!=null) {
				percPositivacao = linhaProdutoMetaFB.getPercPositivacao(); 
			}
			
			Double mixProduto = 0.0;
			if(linhaProdutoMetaFB.getMixProduto()!=null) {
				mixProduto = linhaProdutoMetaFB.getMixProduto(); 
			}

			Double valor = 0.0;
			if(linhaProdutoMetaFB.getValor()!=null) {
				valor = linhaProdutoMetaFB.getValor(); 
			}
			
			Integer linhaProdutoMetaFBId = getSeq();
			System.out.println("[MetaGastoFinanceiroFBDAOHibernate][insert][id]" + linhaProdutoMetaFBId);

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO LINHAPRODUTOMETA (id_linhaprodutometa, id_linhaproduto, id_pessoa_vend, id_pessoa_emp, anomes, valor, percpositivacao, mixproduto) ")
			.append("VALUES (:id_linhaprodutometa, ")
			        .append(":id_linhaproduto, ")
			        .append(":id_pessoa_vend, ")
			        .append(":id_pessoa_emp, ")
			        .append(":anomes, ")
			        .append(":valor, ")
			        .append(":percpositivacao, ")
			        .append(":mixproduto) ");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("id_linhaprodutometa", linhaProdutoMetaFBId);
			query.setParameter("id_linhaproduto", linhaProdutoMetaFB.getIdLinhaProduto());
	        query.setParameter("id_pessoa_vend", linhaProdutoMetaFB.getIdVendedor());
	        query.setParameter("id_pessoa_emp", linhaProdutoMetaFB.getIdPessoaEmp());
	        query.setParameter("anomes", linhaProdutoMetaFB.getAnoMes());
	        query.setParameter("valor", valor);
	        query.setParameter("percpositivacao", percPositivacao);
	        query.setParameter("mixproduto", mixProduto);

			query.executeUpdate();

			return linhaProdutoMetaFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public void alterar(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE LINHAPRODUTOMETA SET ")
					        .append("valor = :VALOR, ")
					        .append("percpositivacao = :PERCPOSITIVACAO, ")
					        .append("mixproduto = :MIXPRODUTO ")
					      .append(" WHERE id_linhaprodutometa = :ID_LINHAPRODUTOMETA");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_LINHAPRODUTOMETA", linhaProdutoMetaFB.getId());
	        query.setParameter("VALOR", linhaProdutoMetaFB.getValor());
	        query.setParameter("PERCPOSITIVACAO", linhaProdutoMetaFB.getPercPositivacao());
	        query.setParameter("MIXPRODUTO", linhaProdutoMetaFB.getMixProduto());

	        query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_linhaProdutoMeta_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do Orçamento Meta.");
		}
	}
	@Override
	public void rollback() {
		try {
			this.session.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public LinhaProdutoMetaFB salvar(LinhaProdutoMetaFB linhaProdutoMetaFB) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM LINHAPRODUTOMETA ")
		      .append(" WHERE ID_LINHAPRODUTOMETA = :ID_LINHAPRODUTOMETA");

			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_LINHAPRODUTOMETA", linhaProdutoMetaFB.getId());

			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<LinhaProdutoMetaFB> listar(String anomes, Integer idPessoaEmp, Integer idLinhaProduto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append("   FROM linhaprodutometa lpm, linhaproduto lp, PESSOA f ")
		   .append("  WHERE lpm.id_linhaproduto = lp.id_linhaproduto ")
		   .append("   AND lpm.ID_PESSOA_EMP = f.ID_PESSOA ");


		if (anomes != null && !"".equals(anomes)) {
			sql.append(" AND lpm.anomes = '"+anomes+"' ");
		}

		if (idPessoaEmp != null) {
			sql.append(" AND lpm.ID_PESSOA_EMP = "+idPessoaEmp+" ");
		}
		
		if (idLinhaProduto != null) {
			sql.append(" AND lpm.id_linhaproduto = "+idLinhaProduto+" ");
		}

		sql.append(" ORDER BY lp.descricao asc ");

		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("anoMes", Hibernate.STRING)
				.addScalar("idPessoaEmp", Hibernate.INTEGER)
				.addScalar("descFornecedor", Hibernate.STRING)
				.addScalar("cnpjCpf", Hibernate.STRING)
				.addScalar("idLinhaProduto", Hibernate.INTEGER)
				.addScalar("descLinhaProduto", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("valorPrevAnt", Hibernate.DOUBLE)
				.addScalar("valorRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAnt", Hibernate.DOUBLE)
				.addScalar("percPrevRealAtual", Hibernate.DOUBLE)
				.addScalar("percPositivacao", Hibernate.DOUBLE)
				.addScalar("mixProduto", Hibernate.DOUBLE)				
				.setResultTransformer(Transformers.aliasToBean(LinhaProdutoMetaFB.class));


		return query.list();
	
	}


	@Override
	public List<LinhaProdutoMetaFB> listar(String anomes, Integer id_pessoa_emp) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LinhaProdutoMetaFBDTO> listarMeta(String anomes, Integer empresa, Integer vendedor, String gestaoVendaMob, String visualizarPor, boolean incluirDevolucao) {
		String sql = "";
		String varIncluirDevolucao = "";
		Date dt1;
		Date dt2;
		
		
		Calendar caIni = Calendar.getInstance();
		int mes = Integer.parseInt(anomes.substring(4,6));
		caIni.set(Calendar.DATE, 1);
		caIni.set(Calendar.MONTH, mes-1);
		caIni.set(Calendar.YEAR, Integer.parseInt(anomes.substring(0,4)));
		caIni.set(Calendar.HOUR, 0);
		caIni.set(Calendar.MINUTE, 0);
		caIni.set(Calendar.SECOND, 0);
		caIni.set(Calendar.AM_PM, Calendar.AM);
		
		dt1 = caIni.getTime();
		
		Calendar caFim = Calendar.getInstance();
		int dia = caIni.getActualMaximum(Calendar.DAY_OF_MONTH);
		caFim.set(Calendar.DATE, dia);		
		caFim.set(Calendar.MONTH, mes-1);
		caFim.set(Calendar.YEAR, Integer.parseInt(anomes.substring(0,4)));
		caFim.set(Calendar.HOUR, 11);
		caFim.set(Calendar.MINUTE, 59);
		caFim.set(Calendar.SECOND, 59);
		caFim.set(Calendar.AM_PM, Calendar.PM);
		
		dt2 = caFim.getTime();

	    if(incluirDevolucao) {
	    	varIncluirDevolucao = "round(tabUnion.venda_acumulada,0)+round(tabUnion.devolucao_acumulada,0) as fatMensal, "+
	    						  "case tabUnion.meta_financeira when 0 then 0 else round((((tabUnion.venda_acumulada+tabUnion.devolucao_acumulada)/tabUnion.meta_financeira)*100),0) end as percMeta, "+
	   					          "((tabUnion.venda_acumulada+tabUnion.devolucao_acumulada) -  tabUnion.meta_financeira) as difMetaFat, ";

	    }else {
	    	varIncluirDevolucao = "round(tabUnion.venda_acumulada,0) as fatMensal, "+
					  			  "case tabUnion.meta_financeira when 0 then 0 else round(((tabUnion.venda_acumulada/tabUnion.meta_financeira)*100),0) end as percMeta, "+
				                  "(tabUnion.venda_acumulada -  tabUnion.meta_financeira) as difMetaFat, ";
	    }
		
		
		if(visualizarPor.equals("vendedor")) {
			
			sql = ""+
					"SELECT tabUnion.id_vendedor as id, "+
					       "tabUnion.vendedor as descricao, "+
					       "tabUnion.TipoVendedor, "+
					       " "+varIncluirDevolucao+" "+
					       "round(tabUnion.devolucao_acumulada,0) as devMensal, "+
					       "tabUnion.meta_cobertura as coberturaCarteira, "+
					       "tabUnion.meta_financeira as metaMensal, "+
					       "tabUnion.meta_mixproduto as mixAtual, "+
					       "tabUnion.produtos_vendidos as produtosVendidos, "+
					       "tabUnion.produtos_total as produtosTotal, "+
					       "tabUnion.clientes_total as clientesTotal, "+
					       "tabUnion.produtos_master as produtosMaster, "+
					       "tabUnion.produtos_vendido_master as produtosVendidoMaster, "+
					       "tabUnion.clientes_master as clientesMaster, "+
					       "tabUnion.clientes_vendidos as clientesVendidos,"+
					       "tabUnion.clientes_vendido_master as clientesVendidoMaster "+
					"FROM ( SELECT max(tab2.codedt)AS codedt , "+
					              "MAX(tab2.id_pessoa_vend) AS id_vendedor, "+
					              "upper(MAX(tab2.vendedor)) AS vendedor, "+
					              "tab2.IDTipoVendedor TipoVendedor, "+
					              "sum(tab2.valor) AS venda_acumulada, "+
					              "sum(tab2.valorDevolucao) AS devolucao_acumulada, "+
					              "sum(tab2.cobertura) AS meta_cobertura, "+
					              "sum(tab2.financeira) AS meta_financeira, "+
					              "sum(tab2.mixproduto) AS meta_mixproduto, "+
					              "SUM(tab2.produtos_vendido) AS produtos_vendidos, "+
					              "( "+
					               "SELECT count(prodv.id_produto) "+
					               "FROM ASSOCTIPOVENDPRODUTO prodv, "+
					                    "produto prod , "+
					                    "fechhistprod fhp "+
					               "WHERE prodv.ID_TIPOVENDEDOR = tab2.IDTipoVendedor "+
					               "AND prodv.id_produto = prod.id_produto "+
					               "and fhp.id_produto = prod.id_produto "+
					               "AND prod.ativo = 1 "+
					               "and fhp.anomes = :anoMes "+
					               "and fhp.existiuestoque = 1 "+
					              ") AS produtos_total, "+
					              "SUM(tab2.clientes_vendido) AS clientes_vendidos, "+
					              "( "+
					               "SELECT count(cliv.id_pessoa_cli) "+
					               "FROM assocvendcliente cliv, "+
					                    "cliente cl "+
					               "WHERE cliv.id_pessoa_vend = tab2.id_pessoa_vend "+
					               "AND cliv.id_pessoa_cli = cl.id_pessoa "+
					               "AND cl.ativo = 1 "+
					              ") AS clientes_total, "+
					              "( "+
					               "SELECT count(distinct (pv.id_produto)) "+
					               "FROM  ASSOCTIPOVENDPRODUTO  pv, "+
					                     "vendedor v, "+
					                     "gestaovendamob gv, "+
					                     "produto p, "+
					                    "fechhistprod fhp "+
					               "WHERE pv.ID_TIPOVENDEDOR = v.ID_TIPOVENDEDOR "+
					               "AND v.id_gestaovendamob = gv.id_gestaovendamob "+
					               "AND p.ID_PRODUTO = pv.ID_PRODUTO "+
					               "and fhp.id_produto = pv.id_produto "+
					               "AND p.ATIVO = 1 "+
					               "AND v.ativo = 1 "+
					               "AND v.ID_PESSOA = coalesce(:idvendedor,v.ID_PESSOA) "+
					               "AND gv.codedt like :gestaovendamob "+
					               "and fhp.anomes = :anoMes "+
					               "and fhp.existiuestoque = 1 "+
					              ") AS produtos_master, "+
					              "( "+
					               "SELECT count(distinct (id_produto)) "+
					               "FROM ( "+
					                       "SELECT peditem.id_produto "+
					                       "FROM pedvenda ped, "+
					                            "pedvendaitem peditem, "+
					                            "PEDVENDASTATUS pvs, "+
					                            "vendedor v, "+
					                            "gestaovendamob gv, "+
					                            "TIPOMOVFISC tmf "+
					                       "WHERE ped.id_pessoa_vend = v.id_pessoa "+
					                       "AND ped.id_pedvenda = peditem.id_pedvenda "+
					                       "and tmf.ID_TIPOMOVFISC =  ped.ID_TIPOMOVFISC "+
					                       "AND ped.id_pedvendastatus = pvs.id_pedvendastatus "+
					                       "AND v.id_gestaovendamob = gv.id_gestaovendamob "+
					                       "AND pvs.efetivado = 1 "+
					                       "and tmf.CLASSE in (0,:classe) "+
					                       "AND ped.ID_PESSOA_EMP = :EMPRESA "+
					                       "AND gv.codedt like :gestaovendamob "+
					                       "AND v.ativo = 1 "+
					                       "AND v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
					                       "AND ped.efetivacao BETWEEN :dt1 and :dt2 "+
					                       "group by peditem.id_produto "+
					                       "UNION ALL "+
					                       "select "+
					                           "evi.id_produto "+
					                        "from ECF_VENDAS ev, "+
					                             "ECF_VENDASITEM evi, "+
					                             "vendedor v, "+
					                             "gestaovendamob gv "+
					                        "where ev.ID_ECFVENDAS  = evi.ID_ECFVENDAS "+
					                          "AND ev.id_pessoa_vend = v.id_pessoa "+
					                          "AND v.id_gestaovendamob = gv.id_gestaovendamob "+
					                          "AND ev.ID_PESSOA_EMP = :EMPRESA "+
					                          "AND gv.codedt like :gestaovendamob "+
					                          "AND ev.CANCELADA     = 0 "+
					                          "AND ev.CONCLUIDA     = 1 "+
					                          "AND evi.CANCELADA    = 0 "+
					                          "AND ev.isvenda       = 1 "+
					                          "AND coalesce(ev.ID_DAV,0) = 0 "+
					                          "AND v.ativo = 1 "+
					                          "AND v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
						                      "AND ev.DATAVENDA BETWEEN :dt1 and :dt2 "+
					                        "group by evi.id_produto "+
					                     ") "+
					              ") AS produtos_vendido_master, "+
					              "( "+
					               "SELECT count(distinct (cv.id_pessoa_cli)) "+
					               "FROM assocvendcliente cv, "+
					                    "vendedor v, "+
					                    "gestaovendamob gv, "+
					                    "cliente cl "+
					               "WHERE cv.id_pessoa_vend = v.id_pessoa "+
					               "AND v.id_gestaovendamob = gv.id_gestaovendamob "+
					               "AND cv.id_pessoa_cli = cl.id_pessoa "+
					               "AND cl.ativo = 1 "+
					               "AND v.ativo = 1 "+
					               "AND v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
					               "AND gv.codedt like :gestaovendamob "+
					              ") AS clientes_master, "+
					              "( "+
					"                select  count(distinct id_pessoa_cli) "+
					                "from ( "+
					"                       SELECT ped.id_pessoa_cli "+
					                       "FROM pedvenda ped, "+
					                            "PEDVENDASTATUS pvs, "+
					                            "vendedor v, "+
					                            "gestaovendamob gv, "+
					                            "TIPOMOVFISC tmf, "+
					                            "ASSOCVENDCLIENTE avc2 "+
					                       "WHERE ped.id_pessoa_vend = v.id_pessoa "+
					                       "AND  ped.id_pedvendastatus = pvs.id_pedvendastatus "+
					                       "AND v.id_gestaovendamob = gv.id_gestaovendamob "+
					                       "and tmf.ID_TIPOMOVFISC = ped.ID_TIPOMOVFISC "+
					                       "and avc2.ID_PESSOA_VEND = ped.ID_PESSOA_VEND "+
					                       "and avc2.ID_PESSOA_CLI  = ped.ID_PESSOA_CLI "+
					                       "AND ped.ID_PESSOA_EMP = :EMPRESA "+
					                       "AND gv.codedt like :gestaovendamob "+
					                       "and tmf.CLASSE in (0,:classe) "+
					                       "AND pvs.efetivado = 1 "+
					                       "AND v.ativo = 1 "+
					                       "AND v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
					                       "AND ped.efetivacao BETWEEN :dt1 and :dt2 "+	
					                       "group by ped.id_pessoa_cli "+
					                      ") "+
					              ") AS clientes_vendido_master "+
					       "FROM ( "+
					             "select max(codedt) codedt, "+
					                    "sum(valor) valor, "+
					                    "sum(valorDevolucao) valorDevolucao, "+
					                    "id_pessoa_vend, "+
					                    "max(vendedor) vendedor, "+
					                    "max(TipoVendedor) IDTipoVendedor, "+
					                    "max(cobertura) cobertura, "+
					                    "sum(financeira) financeira, "+
					                    "max(mixproduto) mixproduto, "+
					                    "max(produtos_vendido) produtos_vendido, "+
					                    "max(clientes_vendido) clientes_vendido "+
					              "from ( select gvmob.codedt, "+
					                            "valor, "+
					                            "valorDevolucao, "+
					                            "id_pessoa_vend , "+
					                            "vendedor, "+
					                            "TipoVendedor, "+
					                            "cobertura, "+
					                            "financeira, "+
					                            "mixproduto, "+
					                            "produtos_vendido, "+
					                            "clientes_vendido "+
					                     "from (  SELECT codedt, "+
					                                    "sum(valor) valor, "+
					                                    "sum(valorDevolucao) valorDevolucao, "+
					                                    "id_pessoa_vend, "+
					                                    "upper(max(vendedor)) vendedor, "+
					                                    "max(TipoVendedor) TipoVendedor, "+
					                                    "0 cobertura, "+
					                                    "0 financeira, "+
					                                    "0 mixproduto, "+
					                                    "(count(distinct (produtos_vendido))-1) AS produtos_vendido, "+
					                                    "(count(distinct (clientes_vendido))-1) AS clientes_vendido "+
					                             "FROM ( "+
					                                  "SELECT substring(c.codedt from 1 for 6) AS codedt, "+
					                                          "cast(iif(a.id_pedvendastatus in (4,5), b.quantidade,(b.quantidade - b.qtdsaldoatender)) * b.PRECO as numeric(18,4)) valor, "+
					                                          "0 as valorDevolucao, "+
					                                          "a.id_pessoa_vend, "+
					                                          "pVend.nomefantmnem vendedor, "+
					                                          "d.ID_TIPOVENDEDOR TipoVendedor, "+
					                                          "b.id_produto  produtos_vendido, "+
					                                          "0  clientes_vendido "+
					                                   "FROM pedvenda a, "+
					                                        "pedvendaitem b, "+
					                                        "PEDVENDASTATUS pvs, "+
					                                        "pessoa pVend, "+
					                                        "gestaovendamob c, "+
					                                        "vendedor d, "+
					                                        "TIPOMOVFISC tmf "+
					                                   "WHERE a.id_pedvenda = b.id_pedvenda "+
					                                   "AND a.id_pessoa_vend = d.id_pessoa "+
					                                   "AND a.id_pedvendastatus = pvs.id_pedvendastatus "+
					                                   "AND pVend.id_pessoa = d.id_pessoa "+
					                                   "AND c.id_gestaovendamob = d.id_gestaovendamob "+
					                                   "and tmf.ID_TIPOMOVFISC = a.ID_TIPOMOVFISC "+
					                                   "AND a.ID_PESSOA_EMP = :EMPRESA "+
					                                   "AND c.codedt like :gestaovendamob "+
					                                   "AND pvs.efetivado = 1 "+
					                                   "and tmf.CLASSE in (0,:classe) "+
					                                   "AND d.ativo = 1 "+
					                                   "AND d.id_pessoa = coalesce(:idvendedor,d.id_pessoa) "+
								                       "AND a.efetivacao BETWEEN :dt1 and :dt2 "+	
					                                   "union all "+
					                                    "SELECT substring(c.codedt from 1 for 6) AS codedt, "+
					                                       "TRUNC((EVI.quantidade-COALESCE(EVI.qtdpedido,0))*(EVI.valorliquidoitem/evi.quantidade),2) valor, "+
					                                       "0 as valorDevolucao, "+
					                                       "-1, "+
					                                       "'Sem Vendedor' vendedor, "+
					                                       "v.ID_TIPOVENDEDOR TipoVendedor, "+
					                                       "evi.id_produto  produtos_vendido, "+
					                                       "0  clientes_vendido "+
					                                    "from ECF_VENDAS ev, "+
					                                         "ECF_VENDASITEM evi, "+
					                                         "pessoa pVend, "+
					                                         "gestaovendamob c, "+
					                                         "vendedor v "+
					                                    "where ev.ID_ECFVENDAS  = evi.ID_ECFVENDAS "+
					                                      "and ev.id_pessoa_vend = v.id_pessoa "+
					                                      "and pVend.id_pessoa = ev.id_pessoa_vend "+
					                                      "and c.id_gestaovendamob = v.id_gestaovendamob "+
					                                      "and ev.ID_PESSOA_EMP = :EMPRESA "+
					                                      "AND c.codedt like :gestaovendamob "+
					                                      "and ev.CANCELADA     = 0 "+
					                                      "and ev.CONCLUIDA     = 1 "+
					                                      "and evi.CANCELADA    = 0 "+
					                                      "and ev.isvenda       = 1 "+
					                                      "and coalesce(ev.ID_DAV,0) = 0 "+
					                                      "and v.ativo = 1 "+
					                                      "and v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
									                      "AND ev.DATAVENDA BETWEEN :dt1 and :dt2 "+	
					                                   "union all "+
					                                   "SELECT substring(c.codedt from 1 for 6) AS codedt, "+
					                                          "0 valor, "+
					                                          "0 as valorDevolucao, "+
					                                          "a.id_pessoa_vend, "+
					                                          "pVend.nomefantmnem vendedor, "+
					                                          "d.ID_TIPOVENDEDOR TipoVendedor, "+
					                                          "0  produtos_vendido, "+
					                                          "a.id_pessoa_cli  clientes_vendido "+
					                                   "FROM pedvenda a, "+
					                                        "PEDVENDASTATUS pvs, "+
					                                        "pessoa pVend, "+
					                                        "gestaovendamob c, "+
					                                        "vendedor d, "+
					                                        "TIPOMOVFISC tmf , "+
					                                        "ASSOCVENDCLIENTE avc1 "+
					                                   "WHERE  a.id_pessoa_vend = d.id_pessoa "+
					                                   "AND a.id_pedvendastatus = pvs.id_pedvendastatus "+
					                                   "AND pVend.id_pessoa = d.id_pessoa "+
					                                   "AND c.id_gestaovendamob = d.id_gestaovendamob "+
					                                   "and tmf.ID_TIPOMOVFISC = a.ID_TIPOMOVFISC "+
					                                   "and avc1.ID_PESSOA_VEND = a.ID_PESSOA_VEND "+
					                                   "and avc1.ID_PESSOA_CLI  = a.ID_PESSOA_CLI "+
					                                   "AND a.ID_PESSOA_EMP = :EMPRESA "+
					                                   "AND c.codedt like :gestaovendamob "+
					                                   "AND pvs.efetivado = 1 "+
					                                   "and tmf.CLASSE in (0,:classe) "+
					                                   "AND d.ativo = 1 "+
					                                   "AND d.id_pessoa = coalesce(:idvendedor,d.id_pessoa) "+
									                   "AND A.efetivacao BETWEEN :dt1 and :dt2 "+
					                                 ") tab1 GROUP BY codedt, id_pessoa_vend "+
					                           ") tab, "+
					                           "gestaovendamob gvmob "+
					                     "WHERE tab.codedt = gvmob.codedt "+
					"                     UNION ALL "+
					                    "SELECT substring(c.codedt from 1 for 6) AS codedt, "+
					                            "0 AS valor, "+
					                            "0 as valorDevolucao, "+
					                            "pVend.id_pessoa id_pessoa_vend, "+
					                            "upper(pVend.nomefantmnem) AS vendedor, "+
					                            "d.ID_TIPOVENDEDOR TipoVendedor, "+
					                            "mv.PERCPOSITIVACAO as cobertura, "+
					                            "mv.VALOR as financeira, "+
					                            "mv.mixproduto, "+
					                            "0 AS produtos_vendido, "+
					                            "0 AS clientes_vendido "+
					                     "FROM gestaovendamob c, "+
					                          "pessoa pVend, "+
					                          "vendedor d, "+
					                          "linhaprodutometa mv "+
					                     "WHERE c.id_gestaovendamob = d.id_gestaovendamob "+
					                     "AND pVend.id_pessoa = d.id_pessoa "+
					                     "AND pVend.id_pessoa = mv.id_pessoa_vend "+
					                     "AND pVend.isvendedor = 1 "+
					                     "AND mv.anomes = :anoMes "+
					                     "AND mv.ID_PESSOA_EMP = :EMPRESA "+
					                     "AND c.codedt like :gestaovendamob "+
					                     "AND d.ativo = 1 "+
					                     "and d.ID_PESSOA = coalesce(:idvendedor,d.ID_PESSOA) "+
					                     "UNION ALL "+
					          "SELECT substring(c.codedt from 1 for 6) AS codedt, "+
					          					"0 as valor, "+
					          					"cast(bdi.QUANTIDADE * bdi.VALORUNIT*-1 as numeric(18,2)) valorDevolucao, "+
					                            "pVend.id_pessoa id_pessoa_vend, "+
					                            "upper(pVend.nomefantmnem) AS vendedor, "+
					                            "v.ID_TIPOVENDEDOR TipoVendedor, "+
					                            "0 cobertura, "+
					                            "0 financeira, "+
					                            "0 mixproduto, "+
					                            "0 produtos_vendido, "+
					                            "0 clientes_vendido "+
					                     "from "+
					"                          BOLETIMDEVOLUCAO bd, "+
					                          "BOLETIMDEVOLITEM bdi, "+
					                          "pessoa pVend, "+
					                          "gestaovendamob c, "+
					                          "vendedor v "+
					                     "where bd.ID_BOLETIMDEVOLUCAO = bdi.ID_BOLETIMDEVOLUCAO "+
					                       "and pvend.id_pessoa = bd.id_pessoa_vend "+
					                       "and pvend.id_pessoa = v.id_pessoa "+
					                       "and c.id_gestaovendamob = v.id_gestaovendamob "+
					                       "and bd.ID_PESSOA_EMP = :EMPRESA "+
					                       "and bd.ID_BOLETIMDEVOLSTATUS <> 1 "+
					                       "AND bd.MOMENTO BETWEEN :dt1 and :dt2 "+
					                       "and v.id_pessoa = coalesce(:idvendedor,v.id_pessoa) "+
					                       "AND c.codedt like :gestaovendamob "+
					                       "and 1 = :IncluiDevol "+
					                  ") tabAgrupa "+
					                    "group by  id_pessoa_vend "+
					            ") tab2 "+
					       "GROUP BY tab2.id_pessoa_vend, tab2.IDTipoVendedor "+
					     ") tabUnion "+
					"ORDER BY tabUnion.venda_acumulada desc ";		
		} else if(visualizarPor.equals("linhaproduto")) {
			sql = ""+
					"SELECT   tabunion.id_linhaproduto AS id, "+
					"         tabunion.descricao    AS descricao, "+
					"         tabunion.tipovendedor,  "+
				       " "+varIncluirDevolucao+" "+
					"         round(tabUnion.devolucao_acumulada,0) as devMensal, "+
					"         tabunion.meta_cobertura                               AS coberturacarteira,  "+
					"         tabunion.meta_financeira                              AS metamensal,  "+
					"         tabunion.meta_mixproduto         AS mixatual,  "+
					"         tabunion.produtos_vendidos       AS produtosvendidos,  "+
					"         tabunion.produtos_total          AS produtostotal,  "+
					"         tabunion.clientes_total          AS clientestotal,  "+
					"         tabunion.produtos_master         AS produtosmaster,  "+
					"         tabunion.produtos_vendido_master AS produtosvendidomaster,  "+
					"         tabunion.clientes_master         AS clientesmaster,  "+
					"         tabunion.clientes_vendidos       AS clientesvendidos,  "+
					"         tabunion.clientes_vendido_master AS clientesvendidomaster  "+
					"FROM     (  "+
					"                  SELECT   Max(tab2.codedt)           AS codedt ,  "+
					"                           Max(tab2.id_linhaproduto)   AS id_linhaproduto, "+
					"                           Upper(Max(tab2.descricao))  AS descricao, "+
					"                           tab2.idtipovendedor           tipovendedor,  "+
					"                           Sum(tab2.valor)            AS venda_acumulada,  "+
					"                           Sum(tab2.valorDevolucao)   AS devolucao_acumulada,  "+
					"                           Sum(tab2.cobertura)        AS meta_cobertura,  "+
					"                           Sum(tab2.financeira)       AS meta_financeira,  "+
					"                           Sum(tab2.mixproduto)       AS meta_mixproduto,  "+
					"                           Sum(tab2.produtos_vendido) AS produtos_vendidos,  "+
					"                           (  "+
					"                                  SELECT Count(prodv.id_produto)  "+
					"                                  FROM   assoctipovendproduto prodv,  "+
					"                                         produto prod ,  "+
					"                                         fechhistprod fhp "+
					"                                  WHERE  prodv.id_tipovendedor = tab2.idtipovendedor  "+
					"                                  AND    prodv.id_produto = prod.id_produto  "+
					"                                  AND    fhp.id_produto = prod.id_produto  "+
					"                                  AND    prod.ativo = 1  "+
					"                                  AND    fhp.anomes = :anoMes  "+
					"                                  AND    fhp.existiuestoque = 1) AS produtos_total, "+
					"                           Sum(tab2.clientes_vendido)             AS clientes_vendidos,  "+
					"                           (  "+
					"                                  SELECT Count(cliv.id_pessoa_cli)  "+
					"                                  FROM   assocvendcliente cliv,  "+
					"                                         cliente cl  "+
					"                                  WHERE  cliv.id_pessoa_vend = coalesce(:idvendedor,cliv.id_pessoa_vend) "+
					"                                  AND    cliv.id_pessoa_cli = cl.id_pessoa "+
					"                                  AND    cl.ativo = 1 ) AS clientes_total,  "+
					"                           (  "+
					"                                  SELECT Count(DISTINCT (pv.id_produto))  "+
					"                                  FROM   assoctipovendproduto pv,  "+
					"                                         vendedor v,  "+
					"                                         gestaovendamob gv,  "+
					"                                         produto p,  "+
					"                                         fechhistprod fhp  "+
					"                                  WHERE  pv.id_tipovendedor = v.id_tipovendedor  "+
					"                                  AND    v.id_gestaovendamob = gv.id_gestaovendamob  "+
					"                                  AND    p.id_produto = pv.id_produto  "+
					"                                  AND    fhp.id_produto = pv.id_produto  "+
					"                                  AND    p.ativo = 1  "+
					"                                  AND    v.ativo = 1  "+
					"                                  AND    v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa)  "+
					"                                  AND    gv.codedt LIKE :gestaovendamob  "+
					"                                  AND    fhp.anomes = :anoMes  "+
					"                                  AND    fhp.existiuestoque = 1 ) AS produtos_master,  "+
					"                           (  "+
					"                                  SELECT Count(DISTINCT (id_produto))  "+
					"                                  FROM   (  "+
					"                                                  SELECT   peditem.id_produto  "+
					"                                                  FROM     pedvenda ped,  "+
					"                                                           pedvendaitem peditem,  "+
					"                                                           pedvendastatus pvs,  "+
					"                                                           vendedor v,  "+
					"                                                           gestaovendamob gv,  "+
					"                                                           tipomovfisc tmf  "+
					"                                                  WHERE    ped.id_pessoa_vend = v.id_pessoa  "+
					"                                                  AND      ped.id_pedvenda = peditem.id_pedvenda "+
					"                                                  AND      tmf.id_tipomovfisc = ped.id_tipomovfisc "+
					"                                                  AND      ped.id_pedvendastatus = pvs.id_pedvendastatus "+
					"                                                  AND      v.id_gestaovendamob = gv.id_gestaovendamob "+
					"                                                  AND      pvs.efetivado = 1  "+
					"                                                  AND      tmf.classe IN (0,:classe)  "+
					"                                                  AND      ped.id_pessoa_emp = :EMPRESA  "+
					"                                                  AND      gv.codedt LIKE :gestaovendamob  "+
					"                                                  AND      v.ativo = 1  "+
					"                                                  AND      v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa) "+
					"												   AND 		ped.efetivacao BETWEEN :dt1 and :dt2 "+
					"                                                  GROUP BY peditem.id_produto  "+
					"                                                  UNION ALL  "+
					"                                                  SELECT   evi.id_produto  "+
					"                                                  FROM     ecf_vendas ev,  "+
					"                                                           ecf_vendasitem evi,  "+
					"                                                           vendedor v,  "+
					"                                                           gestaovendamob gv  "+
					"                                                  WHERE    ev.id_ecfvendas = evi.id_ecfvendas  "+
					"                                                  AND      ev.id_pessoa_vend = v.id_pessoa  "+
					"                                                  AND      v.id_gestaovendamob = gv.id_gestaovendamob "+
					"                                                  AND      ev.id_pessoa_emp = :EMPRESA  "+
					"                                                  AND      gv.codedt LIKE :gestaovendamob  "+
					"                                                  AND      ev.cancelada = 0  "+
					"                                                  AND      ev.concluida = 1  "+
					"                                                  AND      evi.cancelada = 0  "+
					"                                                  AND      ev.isvenda = 1  "+
					"                                                  AND      COALESCE(ev.id_dav,0) = 0  "+
					"                                                  AND      v.ativo = 1  "+
					"                                                  AND      v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa) "+
					"												   AND 		ev.datavenda BETWEEN :dt1 and :dt2 "+
					"                                                  GROUP BY evi.id_produto ) ) AS produtos_vendido_master, "+
					"                           (  "+
					"                                  SELECT Count(DISTINCT (cv.id_pessoa_cli))  "+
					"                                  FROM   assocvendcliente cv,  "+
					"                                         vendedor v,  "+
					"                                         gestaovendamob gv,  "+
					"                                         cliente cl  "+
					"                                  WHERE  cv.id_pessoa_vend = v.id_pessoa  "+
					"                                  AND    v.id_gestaovendamob = gv.id_gestaovendamob  "+
					"                                  AND    cv.id_pessoa_cli = cl.id_pessoa  "+
					"                                  AND    cl.ativo = 1  "+
					"                                  AND    v.ativo = 1  "+
					"                                  AND    v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa)  "+
					"                                  AND    gv.codedt LIKE :gestaovendamob ) AS clientes_master,  "+
					"                           (  "+
					"                                  SELECT Count(DISTINCT id_pessoa_cli)  "+
					"                                  FROM   (  "+
					"                                                  SELECT   ped.id_pessoa_cli  "+
					"                                                  FROM     pedvenda ped,  "+
					"                                                           pedvendastatus pvs,  "+
					"                                                           vendedor v,  "+
					"                                                           gestaovendamob gv,  "+
					"                                                           tipomovfisc tmf,  "+
					"                                                           assocvendcliente avc2  "+
					"                                                  WHERE    ped.id_pessoa_vend = v.id_pessoa  "+
					"                                                  AND      ped.id_pedvendastatus = pvs.id_pedvendastatus "+
					"                                                  AND      v.id_gestaovendamob = gv.id_gestaovendamob "+
					"                                                  AND      tmf.id_tipomovfisc = ped.id_tipomovfisc "+
					"                                                  AND      avc2.id_pessoa_vend = ped.id_pessoa_vend "+
					"                                                  AND      avc2.id_pessoa_cli = ped.id_pessoa_cli "+
					"                                                  AND      ped.id_pessoa_emp = :EMPRESA  "+
					"                                                  AND      gv.codedt LIKE :gestaovendamob  "+
					"                                                  AND      tmf.classe IN (0,:classe)   "+
					"                                                  AND      pvs.efetivado = 1  "+
					"                                                  AND      v.ativo = 1  "+
					"                                                  AND      v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa) "+
					"												   AND 		ped.efetivacao BETWEEN :dt1 and :dt2 "+
					"                                                  GROUP BY ped.id_pessoa_cli ) ) AS clientes_vendido_master "+
					"                  FROM     (  "+
					"                                    SELECT   Max(codedt) codedt,  "+
					"                                             Sum(valor)  valor,  "+
					"                                             Sum(valorDevolucao)  valorDevolucao,  "+
					"                                             id_linhaproduto, "+
					"                                             Max(descricao)         descricao, "+
					"                                             Max(tipovendedor)     idtipovendedor,  "+
					"                                             Sum(cobertura)        cobertura,  "+
					"                                             Sum(financeira)       financeira,  "+
					"                                             Sum(mixproduto)       mixproduto,  "+
					"                                             Max(produtos_vendido) produtos_vendido,  "+
					"                                             Max(clientes_vendido) clientes_vendido  "+
					"                                    FROM     (  "+
					"                                                    SELECT gvmob.codedt,  "+
					"                                                           valor,  "+
					"                                                           valorDevolucao,  "+
					"                                                           id_linhaproduto, "+
					"                                                           descricao, "+
					"                                                           tipovendedor,  "+
					"                                                           cobertura,  "+
					"                                                           financeira,  "+
					"                                                           mixproduto,  "+
					"                                                           produtos_vendido,  "+
					"                                                           clientes_vendido  "+
					"                                                    FROM   (  "+
					"                                                                    SELECT   codedt, "+
					"                                                                             Sum(valor) valor,  "+
					"                                                                             Sum(valorDevolucao) valorDevolucao,  "+
					"                                                                             id_linhaproduto , "+
					"                                                                             Upper(Max(descricao))                      descricao, "+
					"                                                                             Max(tipovendedor)                         tipovendedor, "+
					"                                                                             0                                         cobertura, "+
					"                                                                             0                                         financeira, "+
					"                                                                             0                                         mixproduto, "+
					"                                                                             (Count(DISTINCT(produtos_vendido))-1) AS produtos_vendido, "+
					"                                                                             (Count(DISTINCT(clientes_vendido))-1) AS clientes_vendido "+
					"                                                                    FROM     (  "+
					"                                                                                    SELECT max(substring(c.codedt from 1 FOR 6)) AS codedt, "+
					"                                                                                           SUM(cast(iif(a.id_pedvendastatus in (4,5), b.quantidade,(b.quantidade - b.qtdsaldoatender)) * b.PRECO as numeric(18,4))) valor, "+
					"																							0 as valorDevolucao, "+			
					"                                                                                           dep.ID_LINHAPRODUTO ID_LINHAPRODUTO, "+
					"                                                                                           max(dep.DESCRICAO) DESCRICAO, "+
					"                                                                                           max(d.id_tipovendedor)  tipovendedor, "+
					"                                                                                           b.id_produto       produtos_vendido, "+
					"                                                                                           0                  clientes_vendido "+
					"                                                                                    FROM   pedvenda a, "+
					"                                                                                           pedvendaitem b, "+
					"                                                                                           produto prd, "+
					"                                                                                           linhaproduto lp, "+
					"                                                                                           linhaproduto dep, "+
					"                                                                                           pedvendastatus pvs, "+
					"                                                                                           pessoa pvend, "+
					"                                                                                           gestaovendamob c, "+
					"                                                                                           vendedor d, "+
					"                                                                                           tipomovfisc tmf "+
					"                                                                                    WHERE  a.id_pedvenda = b.id_pedvenda "+
					"                                                                                    AND    b.ID_PRODUTO = prd.ID_PRODUTO "+
					"                                                                                    AND    prd.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO "+
					"                                                                                    AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivellinhaproduto) "+
					"                                                                                    AND    a.id_pessoa_vend = d.id_pessoa "+
					"                                                                                    AND    a.id_pedvendastatus = pvs.id_pedvendastatus "+
					"                                                                                    AND    pvend.id_pessoa = d.id_pessoa "+
					"                                                                                    AND    c.id_gestaovendamob = d.id_gestaovendamob "+
					"                                                                                    AND    tmf.id_tipomovfisc = a.id_tipomovfisc "+
					"                                                                                    AND    a.id_pessoa_emp = :EMPRESA "+
					"     																				 AND    a.id_pedvendastatus in (4,5,6,7) " +					
					"                                                                                    AND    c.codedt LIKE :gestaovendamob "+
					"                                                                                    AND    pvs.efetivado = 1 "+
					"                                                                                    AND    tmf.classe IN (0,:classe)  "+
					"                                                                                    AND    d.ativo = 1 "+
					"                                                                                    AND    d.id_pessoa = COALESCE(:idvendedor,d.id_pessoa) "+
					"												   									 AND	a.efetivacao BETWEEN :dt1 and :dt2 "+
					"                                                                                    group by dep.ID_LINHAPRODUTO, b.id_produto "+
					"                                                                                    UNION ALL  "+
					"                                                                                    SELECT max(substring(c.codedt FROM 1 FOR 6))                                                          AS codedt, "+
					"                                                                                           sum(trunc((evi.quantidade-COALESCE(evi.qtdpedido,0))*(evi.valorliquidoitem/evi.quantidade),2))    valor, "+
					"																							0 as valorDevolucao, "+
					"                                                                                           dep.ID_LINHAPRODUTO ID_LINHAPRODUTO, "+
					"                                                                                           max(dep.DESCRICAO)  descricao, "+
					"                                                                                           max(v.id_tipovendedor) tipovendedor, "+
					"                                                                                           evi.id_produto    produtos_vendido, "+
					"                                                                                           0                 clientes_vendido "+
					"                                                                                    FROM   ecf_vendas ev, "+
					"                                                                                           ecf_vendasitem evi, "+
					"                                                                                           produto prd, "+
					"                                                                                           linhaproduto lp, "+
					"                                                                                           linhaproduto dep, "+
					"                                                                                           pessoa pvend, "+
					"                                                                                           gestaovendamob c, "+
					"                                                                                           vendedor v "+
					"                                                                                    WHERE  ev.id_ecfvendas = evi.id_ecfvendas "+
					"                                                                                    AND    evi.ID_PRODUTO = prd.ID_PRODUTO "+
					"                                                                                    AND    prd.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO "+
					"                                                                                    AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivellinhaproduto) "+
					"                                                                                    AND    ev.id_pessoa_vend = v.id_pessoa "+
					"                                                                                    AND    pvend.id_pessoa = ev.id_pessoa_vend "+
					"                                                                                    AND    c.id_gestaovendamob = v.id_gestaovendamob "+
					"                                                                                    AND    ev.id_pessoa_emp = :EMPRESA "+
					"                                                                                    AND    c.codedt LIKE :gestaovendamob "+
					"                                                                                    AND    ev.cancelada = 0 "+
					"                                                                                    AND    ev.concluida = 1 "+
					"                                                                                    AND    evi.cancelada = 0 "+
					"                                                                                    AND    ev.isvenda = 1 "+
					"                                                                                    AND    COALESCE(ev.id_dav,0) = 0 "+
					"                                                                                    AND    v.ativo = 1 "+
					"                                                                                    AND    v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa) "+
					"												   									 AND	ev.datavenda BETWEEN :dt1 and :dt2 "+
					"                                                                                    group by dep.ID_LINHAPRODUTO, evi.ID_PRODUTO "+
					"                                                                                    UNION ALL  "+
					"                                                                                    SELECT max(substring(c.codedt FROM 1 FOR 6)) AS codedt, "+
					"                                                                                             0                                   valor, "+
					"																							  0 as valorDevolucao, "+
					"                                                                                             dep.ID_LINHAPRODUTO ID_LINHAPRODUTO, "+
					"                                                                                             max(dep.DESCRICAO) as descricao, "+
					"                                                                                             max(d.id_tipovendedor)  tipovendedor, "+
					"                                                                                             0                  produtos_vendido, "+
					"                                                                                             a.id_pessoa_cli    clientes_vendido "+
					"                                                                                      FROM   pedvenda a, "+
					"                                                                                             pedvendaitem b, "+
					"                                                                                             produto prd, "+
					"                                                                                             linhaproduto lp, "+
					"                                                                                             linhaproduto dep, "+
					"                                                                                             pedvendastatus pvs, "+
					"                                                                                             pessoa pvend, "+
					"                                                                                             gestaovendamob c, "+
					"                                                                                             vendedor d, "+
					"                                                                                             tipomovfisc tmf , "+
					"                                                                                             assocvendcliente avc1 "+
					"                                                                                      WHERE  a.ID_PEDVENDA = b.ID_PEDVENDA "+
					"                                                                                      AND    b.ID_PRODUTO = prd.ID_PRODUTO "+
					"                                                                                      AND    prd.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO "+
					"                                                                                      AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivellinhaproduto) "+
					"                                                                                      AND    a.id_pessoa_vend = d.id_pessoa "+
					"                                                                                      AND    a.id_pedvendastatus = pvs.id_pedvendastatus "+
					"                                                                                      AND    pvend.id_pessoa = d.id_pessoa "+
					"                                                                                      AND    c.id_gestaovendamob = d.id_gestaovendamob "+
					"                                                                                      AND    tmf.id_tipomovfisc = a.id_tipomovfisc "+
					"                                                                                      AND    avc1.id_pessoa_vend = a.id_pessoa_vend "+
					"                                                                                      AND    avc1.id_pessoa_cli = a.id_pessoa_cli "+
					"                                                                                      AND    a.id_pessoa_emp = :EMPRESA "+
					"                                                                                      AND    c.codedt LIKE :gestaovendamob "+
					"                                                                                      AND    pvs.efetivado = 1 "+
					"                                                                                      AND    tmf.classe IN (0,:classe)  "+
					"                                                                                      AND    d.ativo = 1 "+
					"                                                                                      AND    d.id_pessoa = COALESCE(:idvendedor,d.id_pessoa) "+
					"												   									   AND	  a.efetivacao BETWEEN :dt1 and :dt2 "+
					"                                                                                      group by dep.ID_LINHAPRODUTO, a.id_pessoa_cli "+
					"                                                                                      ) tab1 "+
					"                                                                    GROUP BY codedt, id_linhaproduto ) tab, "+
					"                                                           gestaovendamob gvmob  "+
					"                                                    WHERE  tab.codedt = gvmob.codedt  "+
					"                                                    UNION ALL  "+
					"                                                    SELECT max(substring(c.codedt FROM 1 FOR 6)) AS codedt, "+
					"                                                           0 AS valor, "+
					"															0 as valorDevolucao, "+
					"                                                           dep.ID_LINHAPRODUTO, "+
					"                                                           max(upper(dep.DESCRICAO))              AS descricao, "+
					"                                                           max(d.id_tipovendedor)                   tipovendedor, "+
					"                                                           MAX(mv.percpositivacao)               AS cobertura, "+
					"                                                           SUM(mv.valor)                         AS financeira, "+
					"                                                           MAX(mv.mixproduto) mixproduto, "+
					"                                                           0 AS produtos_vendido, "+
					"                                                           0 AS clientes_vendido  "+
					"                                                    FROM   gestaovendamob c,  "+
					"                                                           pessoa pvend,  "+
					"                                                           vendedor d,  "+
					"                                                           linhaprodutometa mv, "+
					"                                                           LINHAPRODUTO lp, "+
					"                                                           linhaproduto dep "+
					"                                                    WHERE  c.id_gestaovendamob = d.id_gestaovendamob "+
					"                                                    AND    pvend.id_pessoa = d.id_pessoa  "+
					"                                                    AND    pvend.id_pessoa = mv.id_pessoa_vend  "+
					"                                                    AND    mv.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO "+
					"                                                    AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivellinhaproduto) "+
					"                                                    AND    pvend.isvendedor = 1  "+
					"                                                    AND    mv.anomes = :anoMes  "+
					"                                                    AND    mv.id_pessoa_emp = :EMPRESA "+
					"                                                    AND    c.codedt LIKE :gestaovendamob  "+
					"                                                    AND    d.ativo = 1  "+
					"                                                    AND    d.id_pessoa = COALESCE(:idvendedor,d.id_pessoa) "+
					"                                                    group by dep.ID_LINHAPRODUTO "+
					"                                                    UNION ALL  "+
					"                                                    SELECT max(substring(c.codedt FROM 1 FOR 9))                         AS codedt, "+
					"															0 as valor, "+
					"                                                           sum(cast(bdi.quantidade * bdi.valorunit*-1 AS numeric(18,2))) valorDevolucao, "+
					"                                                           dep.ID_LINHAPRODUTO, "+
					"                                                           max(upper(dep.DESCRICAO))                                AS descricao, "+
					"                                                           max(v.id_tipovendedor)                                    tipovendedor, "+
					"                                                           0                                                           cobertura, "+
					"                                                           0                                                           financeira, "+
					"                                                           0                                                           mixproduto, "+
					"                                                           0                                                           produtos_vendido, "+
					"                                                           0                                                           clientes_vendido "+
					"                                                    FROM   boletimdevolucao bd,  "+
					"                                                           boletimdevolitem bdi,  "+
					"                                                           produto prd, "+
					"                                                           linhaproduto lp, "+
					"                                                           LINHAPRODUTO dep, "+
					"                                                           pessoa pvend,  "+
					"                                                           gestaovendamob c,  "+
					"                                                           vendedor v  "+
					"                                                    WHERE  bd.id_boletimdevolucao = bdi.id_boletimdevolucao "+
					"                                                    AND    bdi.ID_PRODUTO = prd.ID_PRODUTO "+
					"                                                    AND    prd.ID_LINHAPRODUTO = lp.ID_LINHAPRODUTO "+
					"                                                    AND    dep.codedt = Substring(lp.codedt FROM 1 FOR :nivellinhaproduto) "+
					"                                                    AND    pvend.id_pessoa = bd.id_pessoa_vend  "+
					"                                                    AND    pvend.id_pessoa = v.id_pessoa  "+
					"                                                    AND    c.id_gestaovendamob = v.id_gestaovendamob "+
					"                                                    AND    bd.id_pessoa_emp = :EMPRESA  "+
					"                                                    AND    bd.id_boletimdevolstatus <> 1  "+
					"			   									     AND	bd.momento BETWEEN :dt1 and :dt2 "+
					"                                                    AND    v.id_pessoa = COALESCE(:idvendedor,v.id_pessoa) "+
					"                                                    AND    c.codedt LIKE :gestaovendamob  "+
					"                                                    AND    1 = :IncluiDevol "+
					"                                                    group by dep.ID_LINHAPRODUTO "+
					") tabagrupa "+
					"                                    GROUP BY id_linhaproduto ) tab2 "+
					"                  GROUP BY tab2.id_linhaproduto, "+
					"                           tab2.idtipovendedor ) tabunion  "+
					"ORDER BY tabunion.venda_acumulada DESC ";


		}
		


					Query query = (Query) session.createSQLQuery(sql)
							.addScalar("id", Hibernate.INTEGER)
							.addScalar("descricao", Hibernate.STRING)
							.addScalar("metaMensal", Hibernate.DOUBLE)
							.addScalar("devMensal", Hibernate.DOUBLE)
							.addScalar("fatMensal", Hibernate.DOUBLE)
							.addScalar("difMetaFat", Hibernate.DOUBLE)
							.addScalar("percMeta", Hibernate.DOUBLE)
							.addScalar("coberturaCarteira", Hibernate.DOUBLE)
							.addScalar("mixAtual", Hibernate.DOUBLE)
							.addScalar("produtosVendidos", Hibernate.DOUBLE)
							.addScalar("produtosTotal", Hibernate.DOUBLE)
							.addScalar("clientesTotal", Hibernate.DOUBLE)
							.addScalar("produtosMaster", Hibernate.DOUBLE)
							.addScalar("produtosVendidoMaster", Hibernate.DOUBLE)
							.addScalar("clientesVendidos", Hibernate.DOUBLE)
							.addScalar("clientesMaster", Hibernate.DOUBLE)
							.addScalar("clientesVendidoMaster", Hibernate.DOUBLE)
							.setResultTransformer(Transformers.aliasToBean(LinhaProdutoMetaFBDTO.class));
						query.setParameter("anoMes", anomes);
						query.setParameter("dt1", dt1);
						query.setParameter("dt2", dt2);
				        query.setParameter("idvendedor", vendedor);
				        query.setParameter("classe", 1);
			        	query.setParameter("IncluiDevol", 1);
				        query.setParameter("EMPRESA", empresa);
				        query.setParameter("gestaovendamob", gestaoVendaMob);
				        
				        EmpresaFB empresaFB = new EmpresaFBRN().carregar(empresa); 
				        
				        if(visualizarPor.equals("linhaproduto")) {
				        	System.out.println("NivelLinhaProduto: "+empresaFB.getNivelLinhaProduto());
				        	query.setParameter("nivellinhaproduto", empresaFB.getNivelLinhaProduto());
				        }
				        
				         
					return query.list();
	}



}
