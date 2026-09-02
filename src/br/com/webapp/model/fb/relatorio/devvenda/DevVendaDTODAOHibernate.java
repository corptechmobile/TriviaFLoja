package br.com.webapp.model.fb.relatorio.devvenda;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public class DevVendaDTODAOHibernate implements DevVendaDTODAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public List<DevVendaDTO> listar(String boletim, EmpresaFB empresa, VendedorFB vendedor, ClienteFB cliente, Integer fornecedor, ProdutoFB produto, ProdutoLinhaFB linhaProduto, Date dataFilter1, Date dataFilter2, String agruparPor) {
		
		String varFromGroup = " b.id_pessoa_cli as id ";
		String varGroup = " b.id_pessoa_cli ";
		String varOrder = " 23 desc ";
		
		if(DevVendaDTO.AGRUPAR_BOLETIM.equals(agruparPor)) {
			varFromGroup = " b.id_boletimdevolucao as id ";
			varGroup = " b.id_boletimdevolucao ";
		}else if(DevVendaDTO.AGRUPAR_CLIENTE.equals(agruparPor)) {
			varFromGroup = " b.id_pessoa_cli as id ";
			varGroup = " b.id_pessoa_cli ";
		}else if(DevVendaDTO.AGRUPAR_LINHAPRODUTO.equals(agruparPor)) {
			varFromGroup = " dep.id_linhaproduto as id ";
			varGroup = " dep.id_linhaproduto ";
		}else if(DevVendaDTO.AGRUPAR_FORNECEDOR.equals(agruparPor)) {
			varFromGroup = " pr.id_pessoa_forn as id ";
			varGroup = " pr.id_pessoa_forn ";
		}else if(DevVendaDTO.AGRUPAR_VENDEDOR.equals(agruparPor)) {
			varFromGroup = " b.id_pessoa_vend as id ";
			varGroup = " b.id_pessoa_vend ";
		}else if(DevVendaDTO.AGRUPAR_EMPRESA.equals(agruparPor)) {
			varFromGroup = " b.id_pessoa_emp as id ";
			varGroup = " b.id_pessoa_emp ";
		}else if(DevVendaDTO.AGRUPAR_PRODUTO.equals(agruparPor)) {
			varFromGroup = " bi.id_produto as id ";
			varGroup = " bi.id_produto ";
		}else if(DevVendaDTO.AGRUPAR_BOLETIM_PRODUTO.equals(agruparPor)) {
			varFromGroup = " b.id_boletimdevolucao as id, bi.id_produto ";
			varGroup = " b.id_boletimdevolucao, bi.id_produto ";
			varOrder = " 24 desc ";
		}	
 
		String varWhere = "";
		if(boletim!=null && !"".equals(boletim)) {
			varWhere = " AND b.id_boletimdevolucao = :boletim ";
		}else{
			varWhere = " AND b.momento BETWEEN :dt1 AND :dt2 ";
		}
		
		if(empresa!=null) {
			varWhere += " AND b.id_pessoa_emp = :empresa ";
		}
		
		if(vendedor!=null) {
			varWhere += " AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend) ";
		}

		if(cliente!=null) {
			varWhere += " AND b.id_pessoa_cli = :cliente ";
		}
		
		if(fornecedor!=null) {
			varWhere += " AND forn.id_pessoa = :fornecedor ";
		}

		if(produto!=null) {
			varWhere += " AND bi.id_produto = :produto ";
		}
		
		if(linhaProduto!=null) {
			varWhere += " AND dep.id_linhaproduto = :linhaProduto ";
		}

		String sql = " select "+varFromGroup+", "+
				    "          max(b.id_boletimdevolucao) as boletimId, "+
				    "          max(b.momento) as momento, "+
					"          max(b.observacoes) as motivo, "+
			        "          max(ep.cnpjcpf) as empresaCNPJ, "+
			        "          max(ep.nomefantmnem) as empresaDesc, "+
			        "          max(b.id_pessoa_cli) as clienteId, "+
			        "          max(cli.razaosocialnome) as clienteDesc, "+
					"          max(cli.cnpjcpf) as clienteCNPJ, "+
					"          max(vnd.razaosocialnome) as vendedorDesc, "+
					"          max(vnd.cnpjcpf) as vendedorCNPJ, "+
					"          max(conf.razaosocialnome) as conferenteDesc, "+
					"          max(conf.cnpjcpf) as conferenteCNPJ, "+
					"          max(forn.razaosocialnome) as fornecedorDesc, "+
					"          max(forn.cnpjcpf) as fornecedorCNPJ, "+
			        " 		   max(pr.descresumida) as produtoDesc, "+
			        " 		   max(pr.codinterno) as produtoCod, "+
			        " 		   max(dep.descricao) as linhaProdutoDesc, "+
			        " 		   max(b.numnf) as numNF, "+
			        " 		   max(b.serienf) as serieNF, "+
			        " 		   max(t.numtitulo) as numTitulo, "+
			        " 		   max(coalesce(t.saldotitulo,0)) as saldoTitulo, "+
					"          round(sum(bi.quantidade * bi.valorunit), 2) AS vlDevolvido, "+
					"          max(b.valortotprod) as valorTotal "+
					"     from boletimdevolucao b  "+
			        " 		   LEFT JOIN titulopagto tp on (b.id_boletimdevolucao = tp.id_boletimdevolucao) "+
			        " 		   LEFT JOIN titulo t on (tp.id_titulo = t.id_titulo), "+
					"          boletimdevolitem bi, "+
					"          pessoa cli, "+
					"          produto pr, "+
					"          linhaproduto lp, " +
					"          linhaproduto dep, " +
					"          pessoa ep, "+
					"          pessoa vnd, "+
					"          pessoa conf, "+
					"          pessoa forn "+
					"    where b.id_boletimdevolucao = bi.id_boletimdevolucao "+
					"      AND b.id_boletimdevolstatus <> 1 "+
					"      AND b.id_pessoa_emp = ep.id_pessoa "+
					"      AND b.id_pessoa_cli = cli.id_pessoa "+
					"      AND b.id_pessoa_vend = vnd.id_pessoa "+
					"      AND b.id_pessoa_conf = conf.id_pessoa "+
					"      AND pr.id_pessoa_forn=forn.id_pessoa  " +
					"      AND bi.id_produto = pr.id_produto "+
					"      AND pr.id_linhaproduto=lp.id_linhaproduto  " +
					"      AND dep.codedt=substring(lp.codedt FROM 1 FOR 6) " + 
					"     "+varWhere+" "+
					"   group by "+varGroup+" "+
					"   order by "+varOrder+" ";
					
		
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("momento", Hibernate.DATE)
				.addScalar("motivo", Hibernate.STRING)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("empresaCNPJ", Hibernate.STRING)
				.addScalar("clienteId", Hibernate.INTEGER)
				.addScalar("clienteDesc", Hibernate.STRING)
				.addScalar("clienteCNPJ", Hibernate.STRING)
				.addScalar("vendedorDesc", Hibernate.STRING)
				.addScalar("vendedorCNPJ", Hibernate.STRING)
				.addScalar("fornecedorDesc", Hibernate.STRING)
				.addScalar("fornecedorCNPJ", Hibernate.STRING)
				.addScalar("conferenteDesc", Hibernate.STRING)
				.addScalar("conferenteCNPJ", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("linhaProdutoDesc", Hibernate.STRING)
				.addScalar("numNF", Hibernate.STRING)
				.addScalar("serieNF", Hibernate.STRING)
				.addScalar("numTitulo", Hibernate.STRING)
				.addScalar("saldoTitulo", Hibernate.DOUBLE)
				.addScalar("valorTotal", Hibernate.DOUBLE)
				.addScalar("vlDevolvido", Hibernate.DOUBLE);
		

		if(boletim!=null && !"".equals(boletim)) {
			Integer id = Integer.parseInt(boletim);
			query.setParameter("boletim", id);
		}else {
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
		}
		
		if(empresa!=null) {
			query.setParameter("empresa", empresa.getId());
		}	

		if(vendedor!=null) {
			query.setParameter("vendedor", vendedor.getId());
		}	
		
		if(cliente!=null) {
			query.setParameter("cliente", cliente.getId()); 
		}
		
		if(fornecedor!=null) {
			query.setParameter("fornecedor", fornecedor);
		}	
			
		if(produto!=null) {
			query.setParameter("produto", produto.getId()); 
		}

		if(linhaProduto!=null) {
			query.setParameter("linhaProduto", linhaProduto.getId());
		}	

		query.setResultTransformer(Transformers.aliasToBean(DevVendaDTO.class));
			
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<DevVendaDTO> listarBoletim(String boletim, EmpresaFB empresa, VendedorFB vendedor, ClienteFB cliente, ProdutoFB produto, Date dataFilter1, Date dataFilter2) {
		
		String varGroup = " b.id_boletimdevolucao, bi.id_produto ";
		String varOrder = " 15 desc ";
		
		String varWhere = "";
		if(boletim!=null && !"".equals(boletim)) {
			varWhere = " AND b.id_boletimdevolucao = :boletim ";
		}
		
		if(empresa!=null) {
			varWhere = " AND b.id_pessoa_emp = :empresa ";
		}
		
		if(vendedor!=null) {
			varWhere += " AND b.id_pessoa_vend = COALESCE(:vendedor,b.id_pessoa_vend) ";
		}

		if(cliente!=null) {
			varWhere += " AND b.id_pessoa_cli = :cliente ";
		}
		
		if(produto!=null) {
			varWhere += " AND bi.id_produto = :produto ";
		}

		String sql = " select "+varGroup+", "+
				    "          max(b.id_boletimdevolucao) as id, "+
				    "          max(b.momento) as momento, "+
					"          max(b.observacoes) as motivo, "+
			        "          max(ep.cnpjcpf) as empresaCNPJ, "+
			        "          max(ep.nomefantmnem) as empresaDesc, "+
			        "          max(b.id_pessoa_cli) as clienteId, "+
			        "          max(cli.razaosocialnome) as clienteDesc, "+
					"          max(cli.cnpjcpf) as clienteCNPJ, "+
					"          max(vnd.razaosocialnome) as vendedorDesc, "+
					"          max(vnd.cnpjcpf) as vendedorCNPJ, "+
			        " 		   max(pr.descresumida) as produtoDesc, "+
			        " 		   max(pr.codinterno) as produtoCod, "+
					"          round(sum(bi.quantidade * bi.valorunit), 2) AS vlDevolvido "+
					"     from boletimdevolucao b  "+
					
					"          boletimdevolitem bi, "+
					"          pessoa cli, "+
					"          produto pr, "+
					"          pessoa ep, "+
					"          pessoa vnd "+
					"    where b.id_boletimdevolucao = bi.id_boletimdevolucao "+
					"      AND b.id_boletimdevolstatus <> 1 "+
					"      AND b.id_pessoa_emp = ep.id_pessoa "+
					"      AND b.id_pessoa_cli = cli.id_pessoa "+
					"      AND b.id_pessoa_vend = vnd.id_pessoa "+
					"      AND bi.id_produto = pr.id_produto "+
					"      AND b.momento BETWEEN :dt1 AND :dt2 "+
					"     "+varWhere+" "+
					"   group by "+varGroup+" "+
					"   order by "+varOrder+" ";
					
		
		
		Query query = (Query) session.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("momento", Hibernate.DATE)
				.addScalar("motivo", Hibernate.STRING)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("empresaCNPJ", Hibernate.STRING)
				.addScalar("clienteId", Hibernate.INTEGER)
				.addScalar("clienteDesc", Hibernate.STRING)
				.addScalar("clienteCNPJ", Hibernate.STRING)
				.addScalar("vendedorDesc", Hibernate.STRING)
				.addScalar("vendedorCNPJ", Hibernate.STRING)
				.addScalar("produtoDesc", Hibernate.STRING)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("vlDevolvido", Hibernate.DOUBLE);

		if(boletim!=null && !"".equals(boletim)) {
			Integer id = Integer.parseInt(boletim);
			query.setParameter("boletim", id);
		}
		
		if(empresa!=null) {
			query.setParameter("empresa", empresa.getId());
		}	

		if(vendedor!=null) {
			query.setParameter("vendedor", vendedor.getId());
		}	
		
		if(cliente!=null) {
			query.setParameter("cliente", cliente.getId()); 
		}
		
		if(produto!=null) {
			query.setParameter("produto", produto.getId()); 
		}

		query.setParameter("dt1", dataFilter1);
		query.setParameter("dt2", dataFilter2);
		query.setResultTransformer(Transformers.aliasToBean(DevVendaDTO.class));
			
		return query.list();
	}

}
