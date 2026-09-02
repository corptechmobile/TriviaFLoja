package br.com.webapp.model.fb.nfcompra;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;

public class NFCompraItemFBDAOHibernate implements NFCompraItemFBDAO{

	
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	public List<NFCompraItemFB> listar(Integer version) {
		String sql = "select b.id_nfcompraitem AS id, "
						 + " b.id_nfcompra AS nfCompra, "
						 + " b.id_produto AS produto, "
						 + " b.id_unidade AS unidadeCompra, "
						 + " b.id_unidade_venda AS unidadeVenda, "
						 + " b.quantidade AS qtdUnidadeCompra, "
						 + " b.qtdunidvenda AS qtdUnidadeVenda, "
						 + " b.fatorconv AS fatorConv, "
						 + " b.invfator AS fatorInv, "
						 + " a.seq_nfcompra AS version "
				+ " FROM nfcompra a, nfcompraitem b "
				+ " WHERE a.id_nfcompra = b.id_nfcompra "
				  + " AND COALESCE(a.seq_nfcompra, 0) > :version"
				  + " AND (a.chaveacessonfe is not null AND trim(a.chaveacessonfe) != '') "
				  + " AND a.datacadastro > '2017-12-01 00:00:00' " // TODO apanes para primeira carga
				  + " ORDER BY b.id_nfcompraitem "; 

		Query q = this.session.createSQLQuery(sql)
						.addScalar("id")
						.addScalar("nfCompra")
						.addScalar("produto")
						.addScalar("unidadeCompra")
						.addScalar("unidadeVenda")
						.addScalar("qtdUnidadeCompra", Hibernate.DOUBLE)
						.addScalar("qtdUnidadeVenda", Hibernate.DOUBLE)
						.addScalar("fatorConv", Hibernate.DOUBLE)
						.addScalar("fatorInv")
						.addScalar("version")
						.setResultTransformer(Transformers.aliasToBean(NFCompraItemFB.class));
		
		q.setParameter("version", version);
		
		return q.list();
	}

	@Override
	public List<NFCompraItemFB> listar(NFCompraFB nf) {
		
		String sql = " SELECT n.ID_NFCOMPRA AS nfCompraId, "+
			       " p.ID_PRODUTO	AS produtoId, "+
			       " p.CODINTERNO AS produtoCod, "+
			       " p.DESCRICAO AS produtoDesc, "+
			       " ni.ID_UNIDADE AS unidadeCompraId, "+
			       " ni.ID_UNIDADE_VENDA AS unidadeVendaId, "+
			       " ni.QUANTIDADE AS qtdUnidadeCompra, "+
			       " ni.QUANTUNIDBASICA, "+
			       " ni.QTDUNIDVENDA AS qtdUnidadeVenda, "+
			       " p.QTDEMBFECHVENDA AS fatorConv "+
			  " FROM NFCOMPRA n, "+
			       " NFCOMPRAITEM ni, "+
			       " produto p, "+
			       " UNIDADE u "+
			 " WHERE n.ID_NFCOMPRA = ni.ID_NFCOMPRA  "+
			   " AND ni.ID_PRODUTO = p.ID_PRODUTO "+
			   " AND ni.ID_UNIDADE = u.ID_UNIDADE  "+
			   " AND n.ID_NFCOMPRA = :nfCompraId ";

		Query q = this.session.createSQLQuery(sql)
		.addScalar("produtoId", Hibernate.INTEGER)
		.addScalar("nfCompraId", Hibernate.INTEGER)
		.addScalar("produtoCod", Hibernate.STRING)
		.addScalar("produtoDesc", Hibernate.STRING)
		.addScalar("unidadeCompraId", Hibernate.INTEGER)
		.addScalar("unidadeVendaId", Hibernate.INTEGER)
		.addScalar("qtdUnidadeCompra", Hibernate.DOUBLE)
		.addScalar("qtdUnidadeVenda", Hibernate.DOUBLE)
		.addScalar("fatorConv", Hibernate.DOUBLE) 
		.setResultTransformer(Transformers.aliasToBean(NFCompraItemFB.class));
		q.setParameter("nfCompraId", nf.getId());
		return q.list();
	}

}
