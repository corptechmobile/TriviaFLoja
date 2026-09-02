
package br.com.webapp.model.fb.produto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class ProdutoEstoqueFBDAOHibernate implements ProdutoEstoqueFBDAO {
	
	private StringBuilder COLLUMNS_CONTROLA_LOTE;
	private StringBuilder COLLUMNS_NAO_CONTROLA_LOTE;
	private StringBuilder COLLUMNS_SEM_ESTOQUE_DISP;
	private StringBuilder COLLUMNS_PRODUTO_COMPOSTO;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public ProdutoEstoqueFBDAOHibernate() {
		COLLUMNS_CONTROLA_LOTE = new StringBuilder();
		COLLUMNS_CONTROLA_LOTE.append("dp.ID_DEPOSITO AS depositoId, ") 
							  .append("dp.NOME AS depositoDesc, ") 
							  .append("dp.ID_PESSOA_EMP AS empresaId, ") 
							  .append("emp.NOMEFANTMNEM AS empresaDesc, ") 
							  .append("lo.id_localidade AS localidadeId, ") 
							  .append("lo.NOME AS localidadeDesc, ")
							  .append("pr.id_produto AS produtoId, ")
							  .append("pl.id_produtolote AS produtoLoteId, ")
							  .append("pl.vencimento AS dtVencLote, ") 
							  .append("pl.codlote AS codLote, ") 
							  .append("pr.qtddecimal AS qtdDecimal, ")
							  .append("pr.qtdembfechvenda as qtdVendaAtac, ")
							  .append("epl.disponivel AS qtdDisponivel, ")
							  .append("COALESCE(pr.permitevendasemestoque, 0) AS permiteVendaSemEstoque ");
		
		COLLUMNS_NAO_CONTROLA_LOTE = new StringBuilder();
		COLLUMNS_NAO_CONTROLA_LOTE.append("dp.ID_DEPOSITO AS depositoId, ") 
								  .append("dp.NOME AS depositoDesc, ") 
								  .append("dp.ID_PESSOA_EMP AS empresaId, ") 
								  .append("emp.NOMEFANTMNEM AS empresaDesc, ") 
								  .append("lo.id_localidade AS localidadeId, ") 
								  .append("lo.NOME AS localidadeDesc, ")
								  .append("pr.id_produto AS produtoId, ")
								  .append("null AS produtoLoteId, ")
								  .append("null AS dtVencLote, ") 
								  .append("null AS codLote, ")
								  .append("pr.qtddecimal AS qtdDecimal, ")
								  .append("pr.qtdembfechvenda as qtdVendaAtac, ")
								  .append("e.disponivel AS qtdDisponivel, ")
								  .append("COALESCE(pr.permitevendasemestoque, 0) AS permiteVendaSemEstoque ");
		
		COLLUMNS_SEM_ESTOQUE_DISP = new StringBuilder();
		COLLUMNS_SEM_ESTOQUE_DISP.append("null AS depositoId, ") 
								  .append("null AS depositoDesc, ") 
								  .append("emp.ID_PESSOA AS empresaId, ") 
								  .append("emp.NOMEFANTMNEM AS empresaDesc, ") 
								  .append("null AS localidadeId, ") 
								  .append("null AS localidadeDesc, ")
								  .append("pr.id_produto AS produtoId, ")
								  .append("null AS produtoLoteId, ")
								  .append("null AS dtVencLote, ") 
								  .append("null AS codLote, ")
								  .append("pr.qtddecimal AS qtdDecimal, ")
								  .append("pr.qtdembfechvenda as qtdVendaAtac, ")
								  .append("9999999.99 AS qtdDisponivel, ")
								  .append("COALESCE(pr.permitevendasemestoque, 0) AS permiteVendaSemEstoque ");
		
		COLLUMNS_PRODUTO_COMPOSTO = new StringBuilder();
		COLLUMNS_PRODUTO_COMPOSTO.append("null AS depositoId, ") 
								  .append("null AS depositoDesc, ") 
								  .append("emp.ID_PESSOA AS empresaId, ") 
								  .append("emp.NOMEFANTMNEM AS empresaDesc, ") 
								  .append("null AS localidadeId, ") 
								  .append("null AS localidadeDesc, ")
								  .append("prComp.ID_PRODCOMPOSTO AS produtoId, ")
								  .append("null AS produtoLoteId, ")
								  .append("null AS dtVencLote, ") 
								  .append("null AS codLote, ")
								  .append("0 AS qtdDecimal, ")
								  .append("1 as qtdVendaAtac, ")
								  .append("(select qtdDisponivel from view_prodcomposto where id_prodcomposto = prComp.ID_PRODCOMPOSTO and id_empresa = :ID_EMPRESA) AS qtdDisponivel, ")
								  .append("0 AS permiteVendaSemEstoque ");
	}

	@Override
	public ProdutoEstoqueFB carregarControlaLote(Integer usuarioId, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_CONTROLA_LOTE)
			.append("FROM estoque_produtolote epl, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp, produtolote pl ") 
			.append("WHERE epl.ID_PRODUTO = pr.ID_PRODUTO ")
			  .append("AND epl.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND lo.ESTQDISP = 1 ")
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND epl.id_produtolote = pl.id_produtolote ") 
			  .append("AND epl.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND lo.ID_LOCALIDADE = :ID_LOCALIDADE ") 
			  .append("AND pl.ID_PRODUTOLOTE = :ID_PRODUTOLOTE ") 
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
//			  .append("AND (abs(epl.total) + abs(epl.reservado) + abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND (abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
			            	.append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
			            	  .append("AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
		
		
		Query q = (Query) session.createSQLQuery(sql.toString())
									.addScalar("depositoId", Hibernate.INTEGER)
									.addScalar("depositoDesc", Hibernate.STRING)
									.addScalar("empresaId", Hibernate.INTEGER)
									.addScalar("empresaDesc", Hibernate.STRING)
									.addScalar("localidadeId", Hibernate.INTEGER)
									.addScalar("localidadeDesc", Hibernate.STRING)
									.addScalar("produtoId", Hibernate.INTEGER)
									.addScalar("produtoLoteId", Hibernate.INTEGER)
									.addScalar("codLote", Hibernate.STRING)
									.addScalar("dtVencLote", Hibernate.DATE)
									.addScalar("qtdDecimal", Hibernate.INTEGER)
									.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
									.addScalar("qtdDisponivel", Hibernate.DOUBLE)
									.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
									.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		
		q.setParameter("ID_EMPRESA", produtoEstoqueFB.getEmpresaId());
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoEstoqueFB.getProdutoId());
		q.setParameter("ID_LOCALIDADE", produtoEstoqueFB.getLocalidadeId());
		q.setParameter("ID_PRODUTOLOTE", produtoEstoqueFB.getProdutoLoteId());
		q.setParameter("SOCOMESTOQUE", soComEstoque);
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		q.setMaxResults(1);
		return (ProdutoEstoqueFB) q.uniqueResult();
	}

	@Override
	public ProdutoEstoqueFB carregarNaoControlaLote(Integer usuarioId, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_NAO_CONTROLA_LOTE) 
			.append("FROM ESTOQUE e, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp ") 
			.append("WHERE e.ID_PRODUTO = pr.ID_PRODUTO ") 
			  .append("AND e.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND lo.ESTQDISP = 1 ")
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ")
			  .append("AND e.TIPO = 'F' ") 
			  .append("AND e.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND e.ID_LOCALIDADE = :ID_LOCALIDADE ") 
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
			  .append("AND (abs(e.TOTAL) + abs(e.VENDIDO) + abs(e.EMPRESA) + abs(e.RESERVADO) + abs(e.DISPONIVEL) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
					            .append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
					             .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", produtoEstoqueFB.getEmpresaId());
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoEstoqueFB.getProdutoId());
		q.setParameter("ID_LOCALIDADE", produtoEstoqueFB.getLocalidadeId());
		q.setParameter("SOCOMESTOQUE", soComEstoque);
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		q.setMaxResults(1);
		return (ProdutoEstoqueFB) q.uniqueResult();
	}
	
	@Override
	public ProdutoEstoqueFB carregarTodos(Integer usuarioId, Integer produtoControlaLote, ProdutoEstoqueFB produtoEstoqueFB, Integer soComEstoque) {
		
		StringBuilder sql = new StringBuilder();
		
		if(produtoControlaLote.equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
			sql.append("SELECT ").append(COLLUMNS_CONTROLA_LOTE)
			.append("FROM estoque_produtolote epl, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp, produtolote pl ") 
			.append("WHERE epl.ID_PRODUTO = pr.ID_PRODUTO ")
			  .append("AND epl.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND epl.id_produtolote = pl.id_produtolote ") 
			  .append("AND epl.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND lo.ID_LOCALIDADE = :ID_LOCALIDADE ") 
			  .append("AND pl.ID_PRODUTOLOTE = :ID_PRODUTOLOTE ") 
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
//			  .append("AND (abs(epl.total) + abs(epl.reservado) + abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND (abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
			            	.append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
			            	  .append("AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
		}else {
			sql.append("SELECT ").append(COLLUMNS_NAO_CONTROLA_LOTE) 
			.append("FROM ESTOQUE e, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp ") 
			.append("WHERE e.ID_PRODUTO = pr.ID_PRODUTO ") 
			  .append("AND e.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ")
			  .append("AND e.TIPO = 'F' ") 
			  .append("AND e.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND e.ID_LOCALIDADE = :ID_LOCALIDADE ") 
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
			  .append("AND (abs(e.TOTAL) + abs(e.VENDIDO) + abs(e.EMPRESA) + abs(e.RESERVADO) + abs(e.DISPONIVEL) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
					            .append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
					             .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");

		}
		
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		
		q.setParameter("ID_EMPRESA", produtoEstoqueFB.getEmpresaId());
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoEstoqueFB.getProdutoId());
		q.setParameter("ID_LOCALIDADE", produtoEstoqueFB.getLocalidadeId());

		if(produtoControlaLote.equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
			q.setParameter("ID_PRODUTOLOTE", produtoEstoqueFB.getProdutoLoteId());
		}
		
		q.setParameter("SOCOMESTOQUE", soComEstoque);
		
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		q.setMaxResults(1);
		return (ProdutoEstoqueFB) q.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEstoqueFB> listarControlaLote(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_CONTROLA_LOTE)
			.append("FROM estoque_produtolote epl, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp, produtolote pl ") 
			.append("WHERE epl.ID_PRODUTO = pr.ID_PRODUTO ")
			  .append("AND epl.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ")
			  .append("AND lo.ESTQDISP = 1 ")
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND epl.id_produtolote = pl.id_produtolote ") 
			  .append("AND epl.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE")
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
//			  .append("AND (abs(epl.total) + abs(epl.reservado) + abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ")
			  .append("AND (abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ")
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
					            .append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
					             .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("SOCOMESTOQUE", soComEstoque);
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoEstoqueFB> listarNaoControlaLote(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_NAO_CONTROLA_LOTE)  
			.append("FROM ESTOQUE e, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp ") 
			.append("WHERE e.ID_PRODUTO = pr.ID_PRODUTO ") 
			  .append("AND e.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND lo.ESTQDISP = 1 ")
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND e.TIPO = 'F' ") 
			  .append("AND e.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
			  .append("AND (abs(e.TOTAL) + abs(e.VENDIDO) + abs(e.EMPRESA) + abs(e.RESERVADO) + abs(e.DISPONIVEL) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
				            .append(" WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
				              .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
			 
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("SOCOMESTOQUE", soComEstoque);
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoEstoqueFB> listarVendaSemEstoqueDisponivel(Integer empresaId, Integer usuarioId, Integer produtoId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_SEM_ESTOQUE_DISP)  
			.append("FROM PRODUTO pr, PESSOA emp ") 
			.append("WHERE pr.ID_PRODUTO = :ID_PRODUTO ") 
			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ") 
			  .append("AND emp.ID_PESSOA = :ID_EMPRESA ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
				            .append(" WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
				              .append(" AND ue2.ID_PESSOA_EMP = emp.ID_PESSOA) "); 
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_PERMITE_VENDA_SEM_ESTOQUE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEstoqueFB> listarProdComposto(Integer empresaId, Integer usuarioId, Integer prodCompostoId, Integer soComEstoque) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS_PRODUTO_COMPOSTO)  
			.append("FROM PRODCOMPOSTO prComp, PESSOA emp ") 
			.append("WHERE prComp.ID_PRODCOMPOSTO = :ID_PRODUTO ") 
			  .append("AND emp.ID_PESSOA = :ID_EMPRESA ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
				            .append(" WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
				              .append(" AND ue2.ID_PESSOA_EMP = emp.ID_PESSOA) "); 
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", prodCompostoId);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoEstoqueFB> listarTodos(Integer empresaId, Integer usuarioId, Integer produtoControlaLote, Integer produtoId, Integer soComEstoque) {
		StringBuilder sql = new StringBuilder();

		if(produtoControlaLote.equals(ProdutoFB.PRODUTO_CONTROLA_LOTE)) {
			sql.append("SELECT ").append(COLLUMNS_CONTROLA_LOTE)
			.append("FROM estoque_produtolote epl, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp, produtolote pl ") 
			.append("WHERE epl.ID_PRODUTO = pr.ID_PRODUTO ")
			  .append("AND epl.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ")
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND epl.id_produtolote = pl.id_produtolote ") 
			  .append("AND epl.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE")
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
//			  .append("AND (abs(epl.total) + abs(epl.reservado) + abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ")
			  .append("AND (abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ")
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
					            .append("WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
					             .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");
		}else {
			sql.append("SELECT ").append(COLLUMNS_NAO_CONTROLA_LOTE)  
			.append("FROM ESTOQUE e, PRODUTO pr, LOCALIDADE lo, DEPOSITO dp, PESSOA emp ") 
			.append("WHERE e.ID_PRODUTO = pr.ID_PRODUTO ") 
			  .append("AND e.ID_LOCALIDADE = lo.ID_LOCALIDADE ") 
			  .append("AND lo.ID_DEPOSITO = dp.ID_DEPOSITO ") 
			  .append("AND dp.ID_PESSOA_EMP = emp.ID_PESSOA ") 
			  .append("AND e.TIPO = 'F' ") 
			  .append("AND e.ID_PRODUTO = :ID_PRODUTO ") 
//			  .append("AND COALESCE(pr.permitevendasemestoque, 0) = :PERMITEVENDASEMESTOQUE ")
			  .append("AND dp.ID_PESSOA_EMP = coalesce(:ID_EMPRESA, dp.ID_PESSOA_EMP) ") 
			  .append("AND (abs(e.TOTAL) + abs(e.VENDIDO) + abs(e.EMPRESA) + abs(e.RESERVADO) + abs(e.DISPONIVEL) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) ") 
			  .append("AND exists (SELECT ue2.ID_PESSOA_EMP FROM USUARIOEMPRESA ue2 ") 
				            .append(" WHERE ue2.ID_USUARIO = :ID_USUARIO ") 
				              .append(" AND ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) ");

		}
			 
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoLoteId", Hibernate.INTEGER)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("qtdDecimal", Hibernate.INTEGER)
				.addScalar("qtdVendaAtac", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("permiteVendaSemEstoque", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueFB.class));
		q.setParameter("ID_EMPRESA", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("SOCOMESTOQUE", soComEstoque);
		
		return q.list();
	}	

	@Override
	public void bloqueEstoque(Integer empresaFBId, Integer produtoFBId) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE produto_localidade  p_l ") 
					.append("SET p_l.id_produto = p_l.id_produto ") 
					.append("WHERE p_l.id_produto = :ID_PRODUTO ") 
					.append("AND p_l.tipo = 'F' ") 
					.append("AND exists (SELECT d.id_deposito ") 
						             .append("FROM deposito d, localidade l ") 
						             .append("WHERE d.id_pessoa_emp = :ID_PESSOA_EMP ") 
						               .append("AND l.id_deposito = d.id_deposito ") 
						               .append("AND l.id_localidade = p_l.id_localidade ) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PESSOA_EMP", empresaFBId);
			query.setParameter("ID_PRODUTO", produtoFBId);
			
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
}
