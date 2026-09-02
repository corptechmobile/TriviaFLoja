package br.com.coletor.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorOrdSep;
import br.com.coletor.model.ColetorOrdSepItem;
import br.com.coletor.model.ColetorOrdSepItemContagem;
import br.com.webapp.web.util.DAOException;

public class DAOColetorOrdSep {
	
	private Session session;

	public DAOColetorOrdSep(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorOrdSep carregar(Integer ordemCarregId) {
		
		String sql = "SELECT tab.id, "
				 		 + " tab.numPedVenda, " 
						 + " tab.empresaId, "
						 + " tab.empresaNomeFant, "
						 + " tab.clienteId, "
						 + " tab.clienteCnpj, "
						 + " tab.clienteNomeFant, "
						 + " tab.tipoFretePedVenda, " 
						 + " tab.tipoFrete, "
						 + " tab.totalItensPedVenda, " 
						 + " tab.totalVolumesPedVenda, "
						 + " tab.numProcTransp, "
						 + " tab.ordemEntrega, "
						 + " tab.rotaEntrega, "
			             + " tab.bairroEntrega, "
						 + " tab.cidadeEntrega, "
						 + " tab.estadoEntrega, "
						 + " tab.nomeTransportador, "
						 + " tab.placaVeiculo, "
						 + " tab.observacao, "
						 + " tab.dtEntradaPedVenda, "
						 + " tab.dtPrevSaida, "
						 + " tab.dtCreate,"
						 + " tab.status "
				 + " FROM ( " 
						+ "SELECT a.id_ordemcarreg AS id, "
						 		 + " p.id_pedvenda AS numPedVenda, " 
						
								 + " p.id_pessoa_emp AS empresaId, "
								 + " emp.NOMEFANTMNEM AS empresaNomeFant, "
						
								 + " p.id_pessoa_cli AS clienteId, "
								 + " cli.CNPJCPF AS clienteCnpj, "
								 + " cli.NOMEFANTMNEM AS clienteNomeFant, "
								 
								 + " t.descricao AS tipoFretePedVenda, " 
								 + " t.descricao AS tipoFrete, "
								 + " (select count(id_pedvendaitem) as total FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalItensPedVenda, " 
								 + " (select sum(quantidade) as volume FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalVolumesPedVenda, "
								 
								 + " null AS numProcTransp, "
								 + " null AS ordemEntrega, "
								 + " null AS rotaEntrega, "
					             + " ed.bairro AS bairroEntrega, "
								 + " ed.cidade AS cidadeEntrega, "
								 + " ed.id_estado AS estadoEntrega, "
								 + " null AS nomeTransportador, "
								 + " null AS placaVeiculo, "
								 + " p.observacao AS observacao, "
								 
								 + " p.entrada AS dtEntradaPedVenda, "
								 + " CAST(p.prevretiradadatahora AS DATE) AS dtPrevSaida, "
								 + " a.momento AS dtCreate,"
								 + " a.status AS status "
							+ " FROM ordemcarreg a, "
								 + " pedvenda p, "
								 + " tipofrete t, "
								 + " pessoa cli, "
								 + " pessoa emp, "
								 + " endereco ed "
							+ " WHERE a.id_pedvenda = p.id_pedvenda  "
						 	  + " AND p.id_tipofrete = t.id_tipofrete  "
							  + " AND a.id_proctranspped is null "
							  + " AND p.id_pessoa_cli = cli.id_pessoa  "
							  + " AND p.id_pessoa_emp = emp.id_pessoa  "
							  + " AND cli.id_endereco_principal = ed.id_endereco "
							  + " AND a.id_ordemcarreg = :ordemCarregId "
				    + " union "
					  + " SELECT a.id_ordemcarreg AS id, "
							 + " p.id_pedvenda AS numPedVenda, " 
					
							 + " p.id_pessoa_emp AS empresaId, "
							 + " emp.NOMEFANTMNEM AS empresaNomeFant, "
					
							 + " p.id_pessoa_cli AS clienteId, "
							 + " cli.CNPJCPF AS clienteCnpj, "
							 + " cli.NOMEFANTMNEM AS clienteNomeFant, "
							 
							 + " t.descricao AS tipoFretePedVenda, " 
							 + " t.descricao AS tipoFrete, "
							 + " (select count(id_pedvendaitem) as total FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalItensPedVenda, " 
							 + " (select sum(quantidade) as volume FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalVolumesPedVenda, "
							 
							 + " ptp.id_proctransp as numProcTransp, "
							 + " ptr.ordementrega as ordemEntrega, "
							 + " al.descricao as rotaEntrega, "
					         + " ed.bairro AS bairroEntrega, "
							 + " ed.cidade AS cidadeEntrega, "
							 + " ed.id_estado AS estadoEntrega, "
							 + " (select ppt.nomefantmnem  from pessoa ppt where pt.id_pessoa_transp = ppt.id_pessoa) as nomeTransportador, "
							 + " pt.placaveiculo as placaVeiculo, "
							 + " p.observacao AS observacao, "
							 
							 + " p.entrada AS dtEntradaPedVenda, "
							 + " pt.datasaida as dtPrevSaida, "
							 + " a.momento AS dtCreate,"
							 + " a.status AS status "
						+ " FROM ordemcarreg a "
						     + " LEFT JOIN arealogistica al ON (a.id_arealogistica = al.id_arealogistica), "
							 + " pedvenda p, "
							 + " tipofrete t, "
							 + " proctranspped ptp, "
							 + " proctransp pt, "
							 + " proctransprota ptr, "
							 + " pessoa cli, "
							 + " pessoa emp, "
							 + " endereco ed "
						+ " WHERE a.id_pedvenda = p.id_pedvenda "
						  + " AND p.id_tipofrete = t.id_tipofrete "
						  + " AND a.id_proctranspped = ptp.id_proctranspped "
						  + " AND ptp.id_proctransp = ptr.id_proctransp "
						  + " AND ptp.id_proctransp = pt.id_proctransp "
						  + " AND p.id_pessoa_cli = ptr.id_pessoa_cli "
					 	  + " AND p.id_pessoa_cli = cli.id_pessoa  "
					 	  + " AND p.id_pessoa_emp = emp.id_pessoa  "
					 	  + " AND cli.id_endereco_principal = ed.id_endereco "
						  + " AND a.id_ordemcarreg = :ordemCarregId "
				+ " ) tab ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSep.class);
		query.setParameter("ordemCarregId", ordemCarregId);
		query.setMaxResults(1);
		
		return (ColetorOrdSep) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorOrdSep> listarSemProcTransp(Integer usuarioId, Integer numFilter) {
		
		String varWhere = "";
		if(numFilter != null){
			varWhere += " AND (a.id_ordemcarreg = :numFilter or p.id_pedvenda = :numFilter) ";
		}
		
		String sql = "SELECT a.id_ordemcarreg AS id, "
				 		 + " p.id_pedvenda AS numPedVenda, " 
				
						 + " p.id_pessoa_emp AS empresaId, "
						 + " emp.NOMEFANTMNEM AS empresaNomeFant, "
				
						 + " p.id_pessoa_cli AS clienteId, "
						 + " cli.CNPJCPF AS clienteCnpj, "
						 + " cli.NOMEFANTMNEM AS clienteNomeFant, "
						 
						 + " t.descricao AS tipoFretePedVenda, " 
						 + " t.descricao AS tipoFrete, "
						 + " (select count(id_pedvendaitem) as total FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalItensPedVenda, " 
						 + " (select sum(quantidade) as volume FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalVolumesPedVenda, "
						 
						 + " null AS numProcTransp, "
						 + " null AS ordemEntrega, "
						 + " null AS rotaEntrega, "
			             + " ed.bairro AS bairroEntrega, "
						 + " ed.cidade AS cidadeEntrega, "
						 + " ed.id_estado AS estadoEntrega, "
						 + " null AS nomeTransportador, "
						 + " null AS placaVeiculo, "
						 + " p.observacao AS observacao, "
						 
						 + " p.entrada AS dtEntradaPedVenda, "
						 + " p.prevretiradadatahora AS dtPrevSaida, "
						 + " a.momento AS dtCreate,"
						 + " a.status AS status "
				+ " FROM ordemcarreg a, "
					 + " pedvenda p, "
					 + " tipofrete t, "
					 + " pessoa cli, "
					 + " pessoa emp, "
					 + " endereco ed, "
					 + " USUARIOEMPRESA ue "
				+ " WHERE a.id_pedvenda = p.id_pedvenda  "
			 	  + " AND p.id_tipofrete = t.id_tipofrete  "
				  + " AND a.id_proctranspped is null "
				  + " AND p.id_pessoa_cli = cli.id_pessoa  "
				  + " AND p.id_pessoa_emp = emp.id_pessoa  "
				  + " AND cli.id_endereco_principal = ed.id_endereco "
				  + " AND ue.ID_PESSOA_EMP = p.ID_PESSOA_EMP "
				  + " AND ue.ID_USUARIO = :usuarioId "
				  + " AND a.status = :status "
				  + " AND a.momento >= '2024-02-01 00:00:00' "
				  + " AND not exists (select id_ordemcarreg from wms_ordemcarreg where id_ordemcarreg = a.id_ordemcarreg) " + varWhere
				+ " ORDER BY a.id_ordemcarreg ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSep.class);
		query.setParameter("status", ColetorOrdSep.STATUS_EM_ABERTO);
		query.setParameter("usuarioId", usuarioId);
		
		
		if(numFilter != null){
			query.setParameter("numFilter", numFilter);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorOrdSep> listarComProcTransp(Integer usuarioId, Integer numFilter) {
		
		String varWhere = "";
		if(numFilter != null){
			varWhere += " AND (a.id_ordemcarreg = :numFilter or p.id_pedvenda = :numFilter or ptp.id_proctransp = :numFilter) ";
		}
		
		String sql = "SELECT a.id_ordemcarreg AS id, "
				 		 + " p.id_pedvenda AS numPedVenda, " 
				
						 + " p.id_pessoa_emp AS empresaId, "
						 + " emp.NOMEFANTMNEM AS empresaNomeFant, "
				
						 + " p.id_pessoa_cli AS clienteId, "
						 + " cli.CNPJCPF AS clienteCnpj, "
						 + " cli.NOMEFANTMNEM AS clienteNomeFant, "
						 
						 + " t.descricao AS tipoFretePedVenda, " 
						 + " t.descricao AS tipoFrete, "
						 + " (select count(id_pedvendaitem) as total FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalItensPedVenda, " 
						 + " (select sum(quantidade) as volume FROM pedvendaitem where id_pedvenda = p.id_pedvenda) as totalVolumesPedVenda, "
						 
						 + " ptp.id_proctransp as numProcTransp, "
						 + " ptr.ordementrega as ordemEntrega, "
						 + " al.descricao as rotaEntrega, "
			             + " ed.bairro AS bairroEntrega, "
						 + " ed.cidade AS cidadeEntrega, "
						 + " ed.id_estado AS estadoEntrega, "
						 + " (select ppt.nomefantmnem  from pessoa ppt where pt.id_pessoa_transp = ppt.id_pessoa) as nomeTransportador, "
						 + " pt.placaveiculo as placaVeiculo, "
						 + " p.observacao AS observacao, "
						 
						 + " p.entrada AS dtEntradaPedVenda, "
						 + " pt.datasaida as dtPrevSaida, "
						 + " a.momento AS dtCreate,"
						 + " a.status AS status "
					+ " FROM ordemcarreg a "
					     + " LEFT JOIN arealogistica al ON (a.id_arealogistica = al.id_arealogistica), "
						 + " pedvenda p, "
						 + " tipofrete t, "
						 + " proctranspped ptp, "
						 + " proctransp pt, "
						 + " proctransprota ptr, "
						 + " pessoa cli, "
						 + " pessoa emp, "
						 + " endereco ed, "
						 + " USUARIOEMPRESA ue "
					+ " WHERE a.id_pedvenda = p.id_pedvenda "
					  + " AND p.id_tipofrete = t.id_tipofrete "
					  + " AND a.id_proctranspped = ptp.id_proctranspped "
					  + " AND ptp.id_proctransp = ptr.id_proctransp "
					  + " AND ptp.id_proctransp = pt.id_proctransp "
					  + " AND p.id_pessoa_cli = ptr.id_pessoa_cli "
				  	  + " AND p.id_pessoa_cli = cli.id_pessoa  "
				  	  + " AND p.id_pessoa_emp = emp.id_pessoa  "
				  	  + " AND cli.id_endereco_principal = ed.id_endereco "
				  	  + " AND ue.ID_PESSOA_EMP = p.ID_PESSOA_EMP "
					  + " AND ue.ID_USUARIO = :usuarioId "
					  + " AND a.status = :status "
					  + " AND not exists (select id_ordemcarreg from wms_ordemcarreg where id_ordemcarreg = a.id_ordemcarreg) "
					  + " AND a.momento >= '2024-02-01 00:00:00' " + varWhere
					+ " ORDER BY a.id_ordemcarreg "; 
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSep.class);
		query.setParameter("status", ColetorOrdSep.STATUS_EM_ABERTO);
		query.setParameter("usuarioId", usuarioId);
		
		if(numFilter != null){
			query.setParameter("numFilter", numFilter);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorOrdSepItem> listarItens(Integer ordemCarregId) {
		
		String sql = "SELECT a.id_ordemcarregitem AS id, "
						 + " a.id_ordemcarreg AS ordemSeparacaoId, "
						 + " c.id_produto AS produtoId, "
						 + " a.quantidade AS qtd, "
						 + " u.DESCRESUMIDA AS unidVendaDesc "
					+ " FROM ordemcarregitem a, "
						 + " ordemcarreg b, "
						 + " pedvendaitem c, "
						 + " produto p, "
						 + " unidade u "
					+ " WHERE a.id_ordemcarreg = b.id_ordemcarreg "
					  + " AND a.id_pedvendaitem = c.id_pedvendaitem "
					  + " AND c.id_produto = p.id_produto "
					  + " AND p.id_unidade_venda = u.id_unidade "
					  + " AND b.id_ordemcarreg = :ordemCarregId "
					+ " ORDER BY a.id_ordemcarreg ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepItem.class);
		query.setParameter("ordemCarregId", ordemCarregId);
		
		return query.list();
	}
	
	public Integer integracao(Integer coletorOrdSepId, Integer conferenteId) throws DAOException {
		
		Integer seqId = getSeqId();
		
		String sql = "INSERT INTO WMS_ORDEMCARREG "
					+ " (ID_ORDEMCARREG_WMS, ID_ORDEMCARREG, STATUS, MOMENTOFIMSEPARACAO, MENSAGEM, ID_PESSOA_CONF) "
					+ "VALUES "
					+ " (:ID_ORDEMCARREG_WMS, :ID_ORDEMCARREG, 0, CURRENT_TIMESTAMP, '', :ID_PESSOA_CONF)";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorOrdSepItemContagem.class);
		query.setParameter("ID_ORDEMCARREG_WMS", seqId);
		query.setParameter("ID_ORDEMCARREG", coletorOrdSepId);
		query.setParameter("ID_PESSOA_CONF", conferenteId);
		int result = query.executeUpdate();
		if(result == 0) {
			throw new DAOException("Erro ao realizar integração da Confeência/Saída com o ERP.");
		}
		
		return seqId;
	}
	
	private Integer getSeqId() throws DAOException {
		try {
			String sql = "select gen_id(GEN_WMS_ORDEMCARREG_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaFB.");
		}
	}
	
}