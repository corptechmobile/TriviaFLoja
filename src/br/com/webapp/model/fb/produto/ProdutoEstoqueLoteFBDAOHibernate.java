
package br.com.webapp.model.fb.produto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class ProdutoEstoqueLoteFBDAOHibernate implements ProdutoEstoqueLoteFBDAO {
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public ProdutoEstoqueLoteFBDAOHibernate() {}

	@Override
	public ProdutoEstoqueLoteFB carregar(Integer usuarioId, ProdutoEstoqueLoteFB produtoEstoqueLoteFB, Integer soComEstoque) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
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
									.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueLoteFB.class));
		
		q.setParameter("ID_EMPRESA", produtoEstoqueLoteFB.getEmpresaId());
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoEstoqueLoteFB.getProdutoId());
		q.setParameter("ID_LOCALIDADE", produtoEstoqueLoteFB.getLocalidadeId());
		q.setParameter("ID_PRODUTOLOTE", produtoEstoqueLoteFB.getProdutoLoteId());
		q.setParameter("SOCOMESTOQUE", soComEstoque);
//		q.setParameter("PERMITEVENDASEMESTOQUE", ProdutoFB.PRODUTO_NAO_PERMITE_VENDA_SEM_ESTOQUE);
		
		q.setMaxResults(1);
		return (ProdutoEstoqueLoteFB) q.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEstoqueLoteFB> listarEstoque(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		
		String sql = " select "+
					    " tab.ID_DEPOSITO as depositoId, "+
					    " tab.NomeDep as depositoDesc, "+
					    " tab.ID_PESSOA_EMP as empresaId, "+
					    " tab.NOMEFANTMNEM as empresaDesc, "+
					    " tab.NomeLocal as localidadeDesc, "+
					    " tab.ID_PRODUTO as produtoId, "+
					    " tab.ID_LOCALIDADE as localidadeId, "+
					    " tab.TOTAL as qtdTotal, "+
					    " tab.VENDIDO as qtdVendido, "+
					    " tab.EMPRESA as qtdEmpresa, "+
					    " tab.RESERVADO as qtdReservado, "+
					    " tab.LOCALDISPVND, "+
					    " tab.total-tab.vendido-tab.EstqBloq-RESERVADO as qtdDisponivel, "+
					    " tab.EstqBloq as qtdBloqueado "+
					 " from ( "+
					" select "+
					    " dp.ID_DEPOSITO, "+
					    " dp.NOME NomeDep, "+
					    " dp.ID_PESSOA_EMP, "+
					    " emp.NOMEFANTMNEM, "+
					    " lo.NOME NomeLocal, "+
					    " e.ID_PRODUTO, "+
					    " e.ID_LOCALIDADE, "+
					    " e.TIPO, "+
					    " e.TOTAL, "+
					    " e.VENDIDO, "+
					    " e.EMPRESA, "+
					    " e.RESERVADO, "+
					    " e.DISPONIVEL, "+
					    " e.LOCALDISPVND, "+
					    " (select coalesce(sum (pl2.QUANTIDADE),0) "+
					        " from PRODUTO_LOCALIDADE pl2, LOCALIDADE lo2, DEPOSITO dp2 "+
					        " where dp2.ID_DEPOSITO = lo2.ID_DEPOSITO "+
					        " and lo2.ID_LOCALIDADE = pl2.ID_LOCALIDADE "+
					        " and pl2.ID_PRODUTO = :ID_PRODUTO "+
					        " and pl2.TIPO = 'F' "+
					        " and dp2.ID_PESSOA_EMP =  coalesce(:ID_PESSOA_EMP, dp2.ID_PESSOA_EMP) "+
					        " and lo2.ID_DEPOSITO = lo.ID_DEPOSITO "+
					        " and lo2.ID_LOCALIDADE = lo.ID_LOCALIDADE "+
					        " and lo2.ESTQDISP = 0 "+
					    " ) EstqBloq "+
					"  "+
					" from ESTOQUE e, LOCALIDADE lo, DEPOSITO dp, PESSOA emp "+
					" where e.ID_LOCALIDADE = lo.ID_LOCALIDADE "+
					" and lo.ID_DEPOSITO = dp.ID_DEPOSITO "+
					" and dp.ID_PESSOA_EMP = emp.ID_PESSOA "+
					" and e.TIPO = 'F' "+
					" and e.ID_PRODUTO = :ID_PRODUTO "+
					" and dp.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp.ID_PESSOA_EMP) "+
					" and (abs(e.TOTAL) + abs(e.VENDIDO) + abs(e.EMPRESA) + "+
					     " abs(e.RESERVADO) + abs(e.DISPONIVEL) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) "+
					" and exists (select "+
					              " ue2.ID_PESSOA_EMP "+
					            " from "+
					              " USUARIOEMPRESA ue2 "+
					            " where "+
					              " ue2.ID_USUARIO = :ID_USUARIO "+
					            " and "+
					              " ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) "+
					 " ) as tab order by 3 ";
			
				
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("qtdTotal", Hibernate.DOUBLE)
				.addScalar("qtdVendido", Hibernate.DOUBLE)
				.addScalar("qtdEmpresa", Hibernate.DOUBLE)				
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdBloqueado", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueLoteFB.class));

		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("SOCOMESTOQUE", soComEstoque);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEstoqueLoteFB> listarLotes(Integer empresaId, Integer usuarioId, Integer produtoId, Integer soComEstoque) {
		
		String sql = " select "+
					  " dp.ID_DEPOSITO as depositoId, "+
					  " dp.NOME as depositoDesc, "+
					  " dp.ID_PESSOA_EMP as empresaId, "+
					  " emp.NOMEFANTMNEM as empresaDesc, "+
					  " lo.NOME as localidadeDesc, "+
					  " lo.id_localidade as localidadeId, "+
					  " pl.vencimento as dtVencLote, "+
					  " pl.codlote as codLote, "+
					  " pl.codlotecli, "+
					  " pl.codrastreabilidade, "+
					  " pl.producao as dtProducao, "+
					  " epl.id_produto as produtoId, "+
					  " epl.total as qtdTotal, "+
					  " epl.reservado as qtdReservado, "+
					  " epl.bloqueado as qtdBloqueado, "+
					  " epl.disponivel as qtdDisponivel "+
					" from "+
					  " estoque_produtolote epl, "+
					  " LOCALIDADE lo, "+
					  " DEPOSITO dp, "+
					  " PESSOA emp, "+
					  " produtolote pl "+
					" where epl.ID_LOCALIDADE = lo.ID_LOCALIDADE "+
					" and lo.ID_DEPOSITO = dp.ID_DEPOSITO "+
					" and dp.ID_PESSOA_EMP = emp.ID_PESSOA "+
					" and epl.id_produtolote = pl.id_produtolote "+
					" and epl.ID_PRODUTO = :ID_PRODUTO "+
					" and dp.ID_PESSOA_EMP = coalesce(:ID_PESSOA_EMP, dp.ID_PESSOA_EMP) "+
					" and (abs(epl.total) + abs(epl.reservado) + abs(epl.disponivel) > 0 or coalesce(:SOCOMESTOQUE, 0) = 0) "+
					" and exists (select ue2.ID_PESSOA_EMP "+
					             "  from USUARIOEMPRESA ue2 "+
					             " where ue2.ID_USUARIO = :ID_USUARIO "+
					             "   and ue2.ID_PESSOA_EMP = dp.ID_PESSOA_EMP) "+
					"ORDER BY 3 ";             
		
		Query q = (Query) session.createSQLQuery(sql.toString())
				.addScalar("depositoId", Hibernate.INTEGER)
				.addScalar("depositoDesc", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("localidadeId", Hibernate.INTEGER)
				.addScalar("localidadeDesc", Hibernate.STRING)
				.addScalar("codLote", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("dtVencLote", Hibernate.DATE)
				.addScalar("dtProducao", Hibernate.DATE)				
				.addScalar("qtdTotal", Hibernate.DOUBLE)
				.addScalar("qtdDisponivel", Hibernate.DOUBLE)
				.addScalar("qtdBloqueado", Hibernate.DOUBLE)
				.addScalar("qtdReservado", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(ProdutoEstoqueLoteFB.class));
		q.setParameter("ID_PESSOA_EMP", empresaId);
		q.setParameter("ID_USUARIO", usuarioId);
		q.setParameter("ID_PRODUTO", produtoId);
		q.setParameter("SOCOMESTOQUE", soComEstoque);
		
		return q.list();
	}
}
