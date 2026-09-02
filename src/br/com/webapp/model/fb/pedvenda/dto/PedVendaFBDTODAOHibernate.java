package br.com.webapp.model.fb.pedvenda.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.Funcoes;

public class PedVendaFBDTODAOHibernate implements PedVendaFBDTODAO{
	
	private StringBuilder COLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public PedVendaFBDTODAOHibernate() {
		COLUMNS = new StringBuilder();
		COLUMNS.append(" a.id_pedvenda as pedVendaId, ")
			   .append(" (emp.id_pessoa) as empresaId, ")
			   .append(" (emp.nomefantmnem) empresaDesc, ")
			   .append(" (cli.id_pessoa) as clienteId, ")
			   .append(" (cli.razaosocialnome) as clienteDesc, ")
			   .append(" (a.nomeCliente) as nomeCliente, ")
			   .append(" (vend.id_pessoa) as vendedorId, ")
			   .append(" (vend.nomefantmnem) as vendedorDesc, ")
			   .append(" (cp.id_condpagto) as condPagtoId, ")
			   .append(" (cp.descricao) as condPagtoDesc, ")
			   .append(" (a.entrada) as dtEntrada, ")
			   .append(" (a.conclusao) as dtConclusao, ")
			   .append(" (a.efetivacao) as dtEfetivacao, ")
			   .append(" (ps.id_pedvendastatus) as pedVendaStatusId, ")
			   .append(" (ps.descricao) as pedVendaStatusDesc, ")
			   .append(" a.valpedido as valor, ")
			   .append(" (fp.descricao) as formaPagtoDesc, ")
			   .append(" (fp.ID_FORMAPAGTOPV) as formaPagtoId, ")
			   .append(" (a.encomenda) as encomenda ");
	}

	
//	a.id_pessoa_cli = cli.id_pessoa
	@Override
	public PedVendaFBDTO carregar(Integer pedVendaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		   .append(" FROM pedvenda a, pessoa emp, pessoa cli, pessoa vend, condpagto cp, pedvendastatus ps, formapagtopv fp ")
		   .append(" WHERE a.id_pessoa_emp = emp.id_pessoa ")
		   .append(" AND a.id_pessoa_cli = cli.id_pessoa ")
		   .append(" AND a.id_pessoa_vend = vend.id_pessoa ")
		   .append(" AND a.id_condpagto = cp.id_condpagto ")
		   .append(" AND a.id_pedvendastatus = ps.id_pedvendastatus ")
		   .append(" AND a.id_pedvenda =:id ");
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaFBDTO.class);
		query.setParameter("id", pedVendaId);
		query.setMaxResults(1);
		return (PedVendaFBDTO) query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaFBDTO> listar(String numPedidoFilter, String tipoDataFilter, Date dataFilter1, Date dataFilter2, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, PedVendaStatusFB pedVendaStatusFilter, Boolean carteirafilter, Integer tipoPedido, UsuarioFB usuario) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
			.append("  FROM pedvenda a, ")
			.append("	    pessoa emp, ")
			.append("	    pessoa cli, ")
			.append("       pessoa vend, ")
			.append("       vendedor vnd ")
			.append("       LEFT JOIN gestaovenda g ON (vnd.ID_GESTAOVENDA = g.ID_GESTAOVENDA), ")
			.append("       condpagto cp, ")
			.append("       pedvendastatus ps, ")
			.append("       formapagtopv fp, ")
			.append("       usuario u ")
			.append(" WHERE a.id_pessoa_emp = emp.id_pessoa ")
			.append("   AND a.id_pessoa_cli = cli.id_pessoa ")
			.append("   AND a.id_pessoa_vend = vend.id_pessoa ")
			.append("   AND vend.ID_PESSOA = vnd.ID_PESSOA ")
			.append("   AND a.id_condpagto = cp.id_condpagto ")
			.append("   AND a.id_pedvendastatus = ps.id_pedvendastatus ")
			.append("   AND a.id_usuario = u.id_usuario ")
			.append("   AND a.formapagto = fp.id_formapagtopv ");

		if(tipoDataFilter==null) {
			tipoDataFilter = "entrada";
		}

		if (dataFilter1 != null && dataFilter2 != null) {
			sql.append(" AND a."+tipoDataFilter+" BETWEEN :dataFilter1 AND :dataFilter2 ");
		}
		if (empresaFilter != null) {
			sql.append(" AND emp.id_pessoa =:idEmpresa ");
		}
		if (vendedorFilter != null) {
			sql.append(" AND vend.id_pessoa =:idVend ");
		}
		if (clienteFilter != null) {
			sql.append(" AND cli.id_pessoa =:idCli ");
		}
		if (pedVendaStatusFilter != null) {
			sql.append(" AND ps.id_pedvendastatus =:idPedVendaStatus ");
		}
		if (carteirafilter != null) {
			sql.append(" AND ps.carteira =:carteira");
		}
		Integer pedVendaFBId = null;
		if(numPedidoFilter != null && !"".equals(numPedidoFilter)) {
			try {
				pedVendaFBId = Integer.parseInt(numPedidoFilter);
				sql.append(" AND a.id_pedvenda = :pedVendaFBId");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(tipoPedido.equals(Funcoes.IS_TRANSFERENCIA)) {
			sql.append(" AND a.id_tipomovfisc in (select tmf.id_tipomovfisc from tipomovfisc tmf where tmf.classe in (2, 6, 9, 10) and tmf.natureza = 'S' group by tmf.id_tipomovfisc) ");
		}else {
			sql.append(" AND a.id_tipomovfisc not in (select tmf.id_tipomovfisc from tipomovfisc tmf where tmf.classe in (2, 6, 9, 10) and tmf.natureza = 'S' group by tmf.id_tipomovfisc) ");
		}
		
		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt()) && vendedorFilter == null) {
			sql.append(" AND g.CODEDT LIKE :codEdt ");
		}


		sql.append(" ORDER BY a.id_pedvenda desc ");

		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaFBDTO.class);

		if (dataFilter1 != null && dataFilter2 != null) {
			query.setParameter("dataFilter1", dataFilter1).setParameter("dataFilter2", dataFilter2);
		}
		if (empresaFilter != null) {
			query.setParameter("idEmpresa", empresaFilter.getId());
		}
		if (vendedorFilter != null) {
			query.setParameter("idVend", vendedorFilter.getId());
		}
		if (clienteFilter != null) {
			query.setParameter("idCli", clienteFilter.getId());
		}
		if (pedVendaStatusFilter != null) {
			query.setParameter("idPedVendaStatus", pedVendaStatusFilter.getId());
		}

		if (carteirafilter.equals(true)) {
			query.setParameter("carteira", 1);
		}else {
			query.setParameter("carteira", 0);
		}

		if(pedVendaFBId!=null) {
			query.setParameter("pedVendaFBId", pedVendaFBId);	
		}
		
		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt()) && vendedorFilter == null) {
			query.setParameter("codEdt", usuario.getGestaoVendaCodEdt()+"%");
		}

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaFBDTO> listarBloqueados(String descricaoFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuario) {
		Integer codPedido = null;
		boolean codPedOUNomeVend = true;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		.append(" FROM pedvenda a, ")
		.append("      pessoa emp, ") 
		.append("      pessoa cli, ")
		.append("      pessoa vend, ")
		.append("      vendedor vnd ")
		.append("      LEFT JOIN gestaovenda g ON (vnd.ID_GESTAOVENDA = g.ID_GESTAOVENDA), ")
		.append("      condpagto cp, ")
		.append("      pedvendastatus ps, ")
		.append("      formapagtopv fp, ")
		.append("      usuario u  ")
		.append(" WHERE a.id_pessoa_emp = emp.id_pessoa ")
		.append(" AND a.id_pessoa_cli = cli.id_pessoa ")
		.append(" AND a.id_pessoa_vend = vend.id_pessoa ")
		.append(" AND vend.ID_PESSOA = vnd.ID_PESSOA ")
		.append(" AND a.id_condpagto = cp.id_condpagto ")
		.append(" AND a.id_pedvendastatus = ps.id_pedvendastatus ")
		.append(" AND a.formapagto = fp.id_formapagtopv ")
		.append(" AND a.ID_USUARIO = u.ID_USUARIO ")
		.append(" AND ps.id_pedvendastatus = :statusBloqueado ");

		if (dataFilter1 != null && dataFilter2 != null) {
			sql.append(" AND a.entrada BETWEEN :dataFilter1 AND :dataFilter2 ");
		}

		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt())) {
			sql.append(" AND g.CODEDT LIKE :codEdt ");
		}
		
		if (usuario.getIsVendedor() != null && usuario.getIsVendedor() == 1 && vendedorFilter!=null) {
			sql.append(" AND vend.id_pessoa = :id ");
		}

		try {
			codPedido = Integer.parseInt(descricaoFilter);
		} catch (NumberFormatException e) {
			codPedOUNomeVend = false;
		}

		if (codPedOUNomeVend) {
			sql.append(" AND a.id_pedvenda = :codPedido ");
		}else {
			if (descricaoFilter != null && !descricaoFilter.equals("")) {
				sql.append(" AND cli.nomefantmnem like :descricaoFilter ");
			}
		}

		sql.append(" ORDER BY a.id_pedvenda desc ");

		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaFBDTO.class);

		query.setParameter("statusBloqueado", PedVendaFB.SITUACAO_BLOQUEADA);

		if (dataFilter1 != null && dataFilter2 != null) {
			query.setParameter("dataFilter1", dataFilter1);
			query.setParameter("dataFilter2", dataFilter2);
		}

		if (usuario.getIsVendedor() != null && usuario.getIsVendedor() == 1 && vendedorFilter!=null) {
			query.setParameter("id", vendedorFilter.getId());
		}

		if (codPedOUNomeVend) {
			query.setParameter("codPedido", codPedido);
		}else {
			if (descricaoFilter != null && !descricaoFilter.equals("")) {
				query.setParameter("descricaoFilter", descricaoFilter);
			}
		}
		
		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt())) {
			query.setParameter("codEdt", usuario.getGestaoVendaCodEdt()+"%");
		}
		
		return query.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaFBDTO> listarEmRecebimento(String descricaoFilter, VendedorFB vendedorFilter, UsuarioFB usuario) {
		Integer codPedido = null;
		boolean codPedOUNomeVend = true;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ").append(COLUMNS)
		.append(" FROM pedvenda a, ")
		.append("      pessoa emp, ")
		.append("      pessoa cli, ")
		.append("      pessoa vend, ")
		.append("      condpagto cp, ")
		.append("      pedvendastatus ps, ")
		.append("      formapagtopv fp, ")
		.append("      usuario u ")
		.append("      LEFT JOIN gestaovenda g ON (u.id_gestaovenda = g.id_gestaovenda) ")
		.append(" WHERE a.id_pessoa_emp = emp.id_pessoa ")
		.append(" AND a.id_pessoa_cli = cli.id_pessoa ")
		.append(" AND a.id_pessoa_vend = vend.id_pessoa ")
		.append(" AND a.id_condpagto = cp.id_condpagto ")
		.append(" AND a.id_pedvendastatus = ps.id_pedvendastatus ")
		.append(" AND a.formapagto = fp.id_formapagtopv ")
		.append(" AND a.id_usuario = u.id_usuario ")
		.append(" AND ps.id_pedvendastatus = :statusBloqueado ");

		if (vendedorFilter != null) {
			sql.append(" AND vend.id_pessoa = :id ");
		}
		
		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt())) {
			sql.append(" AND g.CODEDT LIKE :codEdt ");
		}

		try {
			codPedido = Integer.parseInt(descricaoFilter);
		} catch (NumberFormatException e) {
			codPedOUNomeVend = false;
		}

		if (codPedOUNomeVend) {
			sql.append(" AND a.id_pedvenda = :codPedido ");
		}else {
			if (descricaoFilter != null && !descricaoFilter.equals("")) {
				sql.append(" AND cli.nomefantmnem like :descricaoFilter ");
			}
		}

		sql.append(" ORDER BY a.id_pedvenda desc ");

		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaFBDTO.class);

		query.setParameter("statusBloqueado", PedVendaFB.SITUACAO_EM_RECEBIMENTO);

		if (vendedorFilter != null) {
			query.setParameter("id", vendedorFilter.getId());
		}
		
		if(usuario.getGestaoVendaCodEdt()!=null && !"".equals(usuario.getGestaoVendaCodEdt())) {
			query.setParameter("codEdt", usuario.getGestaoVendaCodEdt()+"%");
		}

		if (codPedOUNomeVend) {
			query.setParameter("codPedido", codPedido);
		}else {
			if (descricaoFilter != null && !descricaoFilter.equals("")) {
				query.setParameter("descricaoFilter", descricaoFilter);
			}
		}
		return query.list();

	}
}
