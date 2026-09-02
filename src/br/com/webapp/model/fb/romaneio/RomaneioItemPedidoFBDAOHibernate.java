package br.com.webapp.model.fb.romaneio;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.RNException;



public class RomaneioItemPedidoFBDAOHibernate  implements RomaneioItemPedidoFBDAO{
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RomaneioItemFB> listar(Integer romaneioId) {

		String sql =    " SELECT r.ID_ROMANEIO AS romaneioId, "+
						" 	   ri.ID_ROMANEIOITEM AS romaneioItemId, "+
						"        p.ID_PRODUTO AS produtoId,  "+
						"        u.ID_UNIDADE,  "+
						"        max(u.DESCRESUMIDA) AS unidadeDesc, "+
						"        max(p.CODINTERNO) AS produtoCod,  "+
						"        max(p.DESCRICAO) AS produtoDesc,  "+
						"        max(ri.QTDROMANEIO) AS qtdRomaneio,  "+
						"        max(ri.QTDCONFERIDA) AS qtdConferida, "+
						"        max(ri.QTDAJUSTE) AS qtdAjuste, "+
						"     	 max(rip.qtdretirada) AS qtdRetirada "+
						"   FROM romaneio r, "+
						"        ROMANEIOITEM ri, "+
						"        ROMANEIOITEMPEDIDO rip,  "+
						"        produto p, "+
						"        ORDEMCARREGITEM oci,  "+
						"        pedvendaitem pvi, "+
						"        unidade u "+
						"  WHERE R.ID_ROMANEIO = RI.ID_ROMANEIO "+
						"    AND r.ID_ROMANEIO = rip.ID_ROMANEIO  "+
						"    AND ri.ID_PRODUTO = p.ID_PRODUTO "+
						"    AND rip.ID_ORDEMCARREGITEM = oci.ID_ORDEMCARREGITEM  "+
						"    AND oci.ID_PEDVENDAITEM = pvi.ID_PEDVENDAITEM  "+
						"    AND pvi.ID_UNIDADE_VENDASEL = u.ID_UNIDADE "+
						"    AND pvi.ID_PRODUTO = ri.ID_PRODUTO  "+
						"    AND R.ID_ROMANEIO = :romaneioId "+
						"  GROUP BY r.ID_ROMANEIO, "+
						"           ri.ID_ROMANEIOITEM, "+
						"           p.ID_PRODUTO,  "+
						"           u.ID_UNIDADE "+
						"    ORDER BY 7 ";
				

			Query q = this.session.createSQLQuery(sql)
					.addScalar("romaneioId", Hibernate.INTEGER)
					.addScalar("romaneioItemId", Hibernate.INTEGER)
					.addScalar("produtoId", Hibernate.INTEGER)
					.addScalar("produtoCod", Hibernate.STRING)
					.addScalar("produtoDesc", Hibernate.STRING)
					.addScalar("unidadeDesc", Hibernate.STRING)
					.addScalar("qtdRomaneio", Hibernate.DOUBLE)
					.addScalar("qtdConferida", Hibernate.DOUBLE)
					.addScalar("qtdAjuste", Hibernate.DOUBLE)
					.addScalar("qtdRetirada", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(RomaneioItemFB.class));
			
					q.setParameter("romaneioId", romaneioId);
			
			return q.list();
	}


	@Override
	public void updateQtd(Integer id, double quantidade) {
				String sql = "UPDATE ROMANEIOITEMPEDIDO SET QTDRETIRADA = :quantidade WHERE ID_ROMANEIOITEMPEDIDO = :ID_ROMANEIOITEMPEDIDO ";
				Query query = (Query) session.createSQLQuery(sql.toString());
				query.setParameter("ID_ROMANEIOITEMPEDIDO", id); 
				query.setParameter("quantidade", quantidade);
				query.executeUpdate();
	}

	@Override
	public void delete(Integer Id) {
		String sql = "DELETE FROM COLETOR_PC_ITEM WHERE ID_CPC_ITEM = :Id ";
		Query query = session.createSQLQuery(sql);
	      query.setParameter("Id", Id);
	      query.executeUpdate();	
	}

	@Override
	public RomaneioItemFB carregar(Integer romaneioFBId, Integer produtoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RomaneioItemDTOFB> listarParaAjuste(RomaneioItemFB itemSelecionado) {
		
		String sql = " SELECT r.ID_ROMANEIO AS romaneioId,  "+
						" 	  ri.ID_ROMANEIOITEM AS romaneioItemId,  "+
						"     p.ID_PRODUTO AS produtoId,   "+
						"     u.ID_UNIDADE,   "+
						"     pvi.ID_PEDVENDA as pedVendaId, "+
						"     max(cli.ID_PESSOA) AS clienteId, "+
						"     max(cli.tipopessoa) AS clienteTipo, "+
						"     max(cli.CNPJCPF) AS cnpjCpf, "+
						"     max(cli.NOMEFANTMNEM) AS clienteDesc, "+
						"     max(u.DESCRESUMIDA) AS unidadeDesc,  "+
						"     max(p.CODINTERNO) AS produtoCod,   "+
						"     max(p.DESCRICAO) AS produtoDesc,   "+
						"     max(ri.QTDROMANEIO) AS qtdRomaneio, "+
						"     max(ri.QTDCONFERIDA) AS qtdConferida,  "+
						"     max(ri.QTDAJUSTE) AS qtdAjuste, "+
						"     max(oci.QUANTIDADESEL) AS qtdPedido, "+
						"     max(rip.qtdretirada) AS qtdRetirada "+
					 "   FROM romaneio r,  "+
						"     ROMANEIOITEM ri, "+
						"     ROMANEIOITEMPEDIDO rip,   "+
						"     produto p,  "+
						"     ORDEMCARREGITEM oci,   "+
						"     pedvendaitem pvi, "+
						"     pedvenda pv, "+
						"     pessoa cli, "+
						"     unidade u  "+
					 "  WHERE R.ID_ROMANEIO = RI.ID_ROMANEIO  "+
						" AND r.ID_ROMANEIO = rip.ID_ROMANEIO   "+
						" AND ri.ID_PRODUTO = p.ID_PRODUTO  "+
						" AND rip.ID_ORDEMCARREGITEM = oci.ID_ORDEMCARREGITEM   "+
						" AND oci.ID_PEDVENDAITEM = pvi.ID_PEDVENDAITEM   "+
						" AND pvi.ID_UNIDADE_VENDASEL = u.ID_UNIDADE  "+
						" AND pvi.ID_PRODUTO = ri.ID_PRODUTO   "+
						" AND pvi.ID_PEDVENDA = pv.ID_PEDVENDA "+
						" AND pv.ID_PESSOA_CLI = cli.ID_PESSOA  "+
						" AND R.ID_ROMANEIO = :romaneioId  "+
						" AND RI.ID_PRODUTO = :produtoId "+
					  " GROUP BY r.ID_ROMANEIO,  "+
						"        ri.ID_ROMANEIOITEM,  "+
						"        p.ID_PRODUTO,   "+
						"        u.ID_UNIDADE, "+
						"        pvi.ID_PEDVENDA "+
					"   ORDER BY 5, 7 ";				
				
		
			Query q = this.session.createSQLQuery(sql)
					.addScalar("romaneioId", Hibernate.INTEGER)
					.addScalar("romaneioItemId", Hibernate.INTEGER)
					.addScalar("pedVendaId", Hibernate.INTEGER)
					.addScalar("clienteId", Hibernate.INTEGER)
					.addScalar("clienteTipo", Hibernate.STRING)
					.addScalar("cnpjCpf", Hibernate.STRING)
					.addScalar("clienteDesc", Hibernate.STRING)
					.addScalar("produtoId", Hibernate.INTEGER)
					.addScalar("produtoCod", Hibernate.STRING)
					.addScalar("produtoDesc", Hibernate.STRING)
					.addScalar("unidadeDesc", Hibernate.STRING)
					.addScalar("qtdRomaneio", Hibernate.DOUBLE)
					.addScalar("qtdConferida", Hibernate.DOUBLE)
					.addScalar("qtdAjuste", Hibernate.DOUBLE)
					.addScalar("qtdPedido", Hibernate.DOUBLE)
					.addScalar("qtdRetirada", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(RomaneioItemDTOFB.class));
			
					q.setParameter("romaneioId", itemSelecionado.getRomaneioId());
					q.setParameter("produtoId", itemSelecionado.getProdutoId());
			
			return q.list();
	}
	
}
