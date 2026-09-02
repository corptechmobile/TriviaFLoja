package br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.Funcoes;

public class PedVendaDivergRelDAOHibernate implements PedVendaDivergRelDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaDivergRel> listar(VendedorFB vendedorFB, EmpresaFB empresaFB, String produtoFilter, String[] splitDescricao, Integer tipoDataFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuarioFB, Integer tipoDiverg, Integer situacaoDiverg) {
		StringBuilder sql = new StringBuilder();
		
		String varOrderBy = "ORDER BY pv.entrada ";
		if (tipoDataFilter.equals(2)) {
			varOrderBy = "ORDER BY pvdv.interacao ";
		}
		
//		String varWhereProduto = " ";
//		if(produtoFilter!=null && !"".endsWith(produtoFilter)) {
//			varWhereProduto = " AND pr.codinterno = :produtoFilter ";
//		}
		
		String varWhere = ""; 
		if(produtoFilter!=null && !"".equals(produtoFilter) && splitDescricao.length==1) {
			varWhere = " AND (pr.DESCRICAO like :descricaoFilterLike";
			varWhere += " or pr.CODINTERNO = :descricaoFilter) ";
		}
		
		if(splitDescricao != null && splitDescricao.length>1) {
			varWhere = " AND (";
			
			int x = 0;
			for (String rs : splitDescricao) {
				if(Funcoes.validaSplit(rs)) {
					if(x>0) {
						varWhere += " AND ";
					}
					
					varWhere += " pr.DESCRICAO like '%"+rs+"%' ";
					
					x++;
				}
			}
			varWhere += " ) ";
		}
		
		sql.append(" SELECT  pv.id_pedvenda AS pedVendaId, " + 
				"        cp.id_condpagto AS condPagtoId, " + 
				"        cp.descricao AS condPagto, " + 
				"        emp.id_pessoa AS empresaId, " + 
				"        emp.nomefantmnem AS empresa, " + 
				"        vend.id_pessoa AS vendedorId, " + 
				"        vend.nomefantmnem AS vendedor, " + 
				"        cli.id_pessoa AS clienteId, " + 
				"        cli.nomefantmnem as cliente, " + 
				"        usu.id_usuario as usuarioId, " + 
				"        usu.nome as usuario, " + 
				"        pr.id_produto as produtoId, " + 
				"        pr.codinterno as produtoCod, " + 
				"        pr.descricao as produto, " + 
				"        pvdv.desconto as desconto, " + 
				"        pvdv.tipo as tipoDiverg, " + 
				"        pvdv.situacao as situacaoDiverg, " + 
				"        pvdv.observacao as observacao, " + 
				"        pv.entrada as entrada, " +
				"		 pvdv.interacao as interacao " +
				"FROM pedvenda pv, " + 
				"     pessoa emp, " + 
				"     pessoa vend, " + 
				"     pessoa cli, " + 
				"     usuario usu, " + 
				"     pedvendadiverg pvdv " + 
				"     left join  condpagto cp on (cp.id_condpagto = pvdv.id_condpagto) " + 
				"     left join pedvendaitem pdvi on (pdvi.id_pedvenda = pvdv.id_pedvenda AND pdvi.id_pedvendaitem = pvdv.id_pedvendaitem) " + 
				"     left join produto pr on (pdvi.id_produto = pr.id_produto) " + 
				"WHERE   pv.id_pedvenda = pvdv.id_pedvenda " + 
				"        AND pv.id_pessoa_emp = emp.id_pessoa " + 
				"        AND pv.id_pessoa_cli = cli.id_pessoa " + 
				"        AND pv.id_pessoa_vend = vend.id_pessoa " + 
				"        AND pvdv.id_usuario = usu.id_usuario " + varWhere);
		
		if (empresaFB != null) {
			sql.append(" AND pv.id_pessoa_emp = :id_pessoa_emp ");
		}
		if (vendedorFB != null) {
			sql.append(" AND vend.id_pessoa = :vend_id_pessoa ");
		}
		if (tipoDataFilter == 1 && dataFilter1 != null && dataFilter2 != null) {
			sql.append(" AND pv.entrada between :dt1 AND :dt2 ");
		}
		else if (tipoDataFilter == 2 && dataFilter1 != null && dataFilter2 != null) {
			sql.append(" AND pvdv.interacao between :dt1 AND :dt2 ");
		}
		if (tipoDiverg != 0 ) {
			sql.append(" AND pvdv.tipo = :tipoDiverg ");
		}
		if (situacaoDiverg != 3) {
			sql.append(" AND pvdv.situacao = :situacaoDiverg ");
		}
		
		sql.append(varOrderBy);
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("pedVendaId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("condPagto", Hibernate.STRING)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresa", Hibernate.STRING)
				.addScalar("vendedorId", Hibernate.INTEGER)
				.addScalar("vendedor", Hibernate.STRING)
				.addScalar("clienteId", Hibernate.INTEGER)
				.addScalar("cliente", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuario", Hibernate.STRING)
				.addScalar("produtoId", Hibernate.INTEGER)
				.addScalar("produtoCod", Hibernate.STRING)
				.addScalar("produto", Hibernate.STRING)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("tipoDiverg", Hibernate.INTEGER)
				.addScalar("situacaoDiverg", Hibernate.INTEGER)
				.addScalar("observacao", Hibernate.STRING)
				.addScalar("entrada", Hibernate.DATE)
				.addScalar("interacao", Hibernate.DATE);
		
		if (empresaFB != null) {
			query.setParameter("id_pessoa_emp", empresaFB.getId());
		}
		if (vendedorFB != null) {
			query.setParameter("vend_id_pessoa", vendedorFB.getId());
		}
		if (tipoDataFilter == 1 && dataFilter1 != null && dataFilter2 != null) {
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
		}
		else if (tipoDataFilter == 2 && dataFilter1 != null && dataFilter2 != null) {
			query.setParameter("dt1", dataFilter1);
			query.setParameter("dt2", dataFilter2);
		}
		if (tipoDiverg != 0 ) {
			query.setParameter("tipoDiverg", tipoDiverg);
		}
		if (situacaoDiverg != 3) {
			query.setParameter("situacaoDiverg", situacaoDiverg);
		}
		if(produtoFilter!=null && !"".equals(produtoFilter) && splitDescricao.length==1) {
			query.setParameter("descricaoFilterLike", "%" + produtoFilter + "%");
			query.setParameter("descricaoFilter", produtoFilter);
		}
		
		query.setResultTransformer(Transformers.aliasToBean(PedVendaDivergRel.class));
		return query.list();
				
	}
}
