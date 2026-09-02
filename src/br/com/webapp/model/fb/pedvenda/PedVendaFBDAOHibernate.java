package br.com.webapp.model.fb.pedvenda;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.DAOException;

public class PedVendaFBDAOHibernate implements PedVendaFBDAO {

	private StringBuilder COLLUMNS;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public PedVendaFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_PEDVENDA AS id, ")
				.append(" a.ID_PESSOA_CLI AS clienteId, ")
				.append(" a.ID_PESSOA_VEND as vendedorId, ")
				.append(" a.ID_PESSOA_EMP as empresaId, ")
				.append(" a.ID_CONDPAGTO as condPagtoId, ")
				.append(" a.ID_TIPOMOVFISC as movFiscTipoId, ")
				.append(" a.ID_TIPOFRETE as freteTipoId, ")
				.append(" a.ID_TABPRECO as tabPrecoId, ")
				.append(" a.ID_PEDVENDASTATUS as pedVendaStatusId, ")
				.append(" a.ID_TIPOCOBR as cobrTipoId, ")
				.append(" a.ENTRADA as entrada, ")
				.append(" a.NOMECLIENTE as nomeCliente, ")
				.append(" a.CONCLUSAO as conclusao, ")
				.append(" a.EFETIVACAO as efetivacao, ")
				.append(" a.LIQUIDACAO as liquidacao, ")
				.append(" a.VALPEDIDO as valPedido, ")
				.append(" a.NUMPEDCLI as numPedCli, ")
				.append(" a.VALFRETE as valFrete, ")
				.append(" a.VALDESPACESS as valDespAcess, ")
				.append(" a.OBSERVACAO as observacao, ")
				.append(" a.ENTREGA as entrega, ")
				.append(" a.ID_USUARIO as usuarioId,")
				.append(" a.ISBLOQPRECO as bloqPreco, ")
				.append(" a.ISBLOQCRED as bloqCred, ")
				.append(" a.ISBLOQCAR as bloqCar, ")
				.append(" a.PREVRETIRADA as prevRetirada, ")
				.append(" a.FORMAPAGTO as formaPagtoId, ")
				.append(" a.ID_USUARIO_LOCK as usuarioLockId, ")
				.append(" a.IDNUMSOLEXTERNA as numSolExterna,")
				.append(" a.LIBERADOINTEG as liberadoInteg,")
				.append(" a.ID_ENDERECO_ENTREGA as enderecoEntregaId, ")
				.append(" a.VALORDESCONTO as valorDesconto, ")
				.append(" a.VALORST as valorST, ")
				.append(" a.UFCLI as ufCli, ")
				.append(" a.ALIQICMSDEST as aliqICMSDest,")
				.append(" a.VALTOTGERADODUP as valTotGeradoGroup, ")
				.append(" a.SEPARAANT as separaAnt, ")
				.append(" a.TIPOPEDIDO as tipoPedido, ")
				.append(" a.PREVRETIRADADATAHORA as prevRetiradaDataHora, ")
				.append(" a.DATAULTALTERACAO as dataUltAlteracao, ")
				.append(" a.SEQ_PEDVENDA as seqPedVenda, ")
				.append(" a.CALCVENCDUPDATAEFET as calcVencUpDataEfet, ")
				.append(" a.IMPRESSO as impresso, ")
				.append(" a.ORCAMENTO as orcamento, ")
				.append(" a.VALORIPI as valorIPI, ")
				.append(" a.OBSERVACAO2 as observacao2, ")
				.append(" a.DESCFLEX as descFlex, ")
				.append(" a.DESCGESTAOVENDA as descGestaoVenda, ")
				.append(" a.SALDOINICVENDEDOR as saldoInicVendedor, ")
				.append(" a.COMPOEFLUXO as compoEFluxo, ")
				.append(" a.VALTAXAENTREGA as valTaxaEntrega, ")
				.append(" a.UFEMPRESA as ufEmpresa, ")
				.append(" a.PERCRATEIODUP as percRateioDup, ")
				.append(" a.ID_MOEDA as moedaId, ")
				.append(" a.VALORCOTACAO as valorCotacao, ")
				.append(" a.DATACOTACAO as dataCotacao, ")
				.append(" a.TIPOCAMBIO as tipoCambio, ")
				.append(" a.TIPOENTRADAPEDIDO as tipoEntradaPedido, ")
				.append(" a.VALIDADECOTACAODIAS as valCotDias, ")
				.append(" a.CONTATO as contato, ")
				.append(" a.CONTATOEMAIL as contatoEmail, ")
				.append(" a.CONTATOTELEFONE as contatoTelefone, ")
				.append(" a.ID_PESSOA_VENDAORDREM as pessoaVendaOrdemId, ")
				.append(" a.PREVCLIENTE as prevCliente, ")
				.append(" a.PEDN as pedn, ")
				.append(" a.CALCVENCDUPDATAEFET as calcVendDUpdataEfet, ")
				.append(" a.ENCOMENDA as encomenda, ")
				.append(" a.DESCONTO as desconto, ")
				.append(" COALESCE(a.EXISTEOC, 0) as existeOc, ")
				.append(" a.ID_USUARIO_WEB as usuarioWebId ");
				
	}
	
	
	@Override
	public PedVendaFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ").append(COLLUMNS)
			   .append(" FROM pedvenda a ")
			   .append(" WHERE a.ID_PEDVENDA = :id ");
		//Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(PedVendaFB.class);
		Query query = (Query) session.createSQLQuery(sql.toString())
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("clienteId", Hibernate.INTEGER)
						.addScalar("vendedorId", Hibernate.INTEGER)
						.addScalar("empresaId", Hibernate.INTEGER)
						.addScalar("condPagtoId", Hibernate.INTEGER)
						.addScalar("movFiscTipoId", Hibernate.INTEGER)
						.addScalar("freteTipoId", Hibernate.INTEGER)
						.addScalar("tabPrecoId", Hibernate.STRING)
						.addScalar("pedVendaStatusId", Hibernate.INTEGER)
						.addScalar("cobrTipoId", Hibernate.INTEGER)
						.addScalar("usuarioId", Hibernate.INTEGER)
						.addScalar("formaPagtoId", Hibernate.INTEGER)
						.addScalar("usuarioLockId", Hibernate.INTEGER)
						.addScalar("enderecoEntregaId", Hibernate.INTEGER)
						.addScalar("moedaId", Hibernate.INTEGER)
						.addScalar("pessoaVendaOrdemId", Hibernate.INTEGER)
						.addScalar("entrada", Hibernate.TIMESTAMP)
						.addScalar("nomeCliente", Hibernate.STRING)
						.addScalar("conclusao", Hibernate.TIMESTAMP)
						.addScalar("efetivacao", Hibernate.TIMESTAMP)
						.addScalar("liquidacao", Hibernate.TIMESTAMP)
						.addScalar("valPedido", Hibernate.DOUBLE)
						.addScalar("numPedCli", Hibernate.STRING)
						.addScalar("valFrete", Hibernate.DOUBLE)
						.addScalar("valDespAcess", Hibernate.DOUBLE)
						.addScalar("observacao", Hibernate.STRING)
						.addScalar("entrega", Hibernate.INTEGER)
						.addScalar("bloqPreco", Hibernate.INTEGER)
						.addScalar("bloqCred", Hibernate.INTEGER)
						.addScalar("bloqCar", Hibernate.INTEGER)
						.addScalar("prevRetirada", Hibernate.DATE)
						.addScalar("numSolExterna", Hibernate.STRING)
						.addScalar("liberadoInteg", Hibernate.INTEGER)
						.addScalar("valorDesconto", Hibernate.DOUBLE)
						.addScalar("valorST", Hibernate.DOUBLE)
						.addScalar("ufCli", Hibernate.STRING)
						.addScalar("aliqICMSDest", Hibernate.DOUBLE)
						.addScalar("valTotGeradoDup", Hibernate.DOUBLE)
						.addScalar("separaAnt", Hibernate.INTEGER)
						.addScalar("tipoPedido", Hibernate.INTEGER)
						.addScalar("prevRetiradaDataHora", Hibernate.TIMESTAMP)
						.addScalar("dataUltAlteracao", Hibernate.TIMESTAMP)
						.addScalar("seqPedVenda", Hibernate.INTEGER)
						.addScalar("calcVendDUpdataEfet", Hibernate.INTEGER)
						.addScalar("impresso", Hibernate.INTEGER)
						.addScalar("orcamento", Hibernate.INTEGER)
						.addScalar("valorIPI", Hibernate.DOUBLE)
						.addScalar("observacao2", Hibernate.STRING)
						.addScalar("descFlex", Hibernate.DOUBLE)
						.addScalar("descGestaoVenda", Hibernate.DOUBLE)
						.addScalar("saldoInicVendedor", Hibernate.DOUBLE)
						.addScalar("compoEFluxo", Hibernate.INTEGER)
						.addScalar("valTaxaEntrega", Hibernate.DOUBLE)
						.addScalar("ufEmpresa", Hibernate.STRING)
						.addScalar("percRateioDup", Hibernate.DOUBLE)
						.addScalar("valorCotacao", Hibernate.DOUBLE)
						.addScalar("dataCotacao", Hibernate.DATE)
						.addScalar("tipoCambio", Hibernate.INTEGER)
						.addScalar("tipoEntradaPedido", Hibernate.INTEGER)
						.addScalar("validadeCotacaoDias", Hibernate.INTEGER)
						.addScalar("contato", Hibernate.STRING)
						.addScalar("contatoEmail", Hibernate.STRING)
						.addScalar("contatoTelefone", Hibernate.STRING)
						.addScalar("prevCliente", Hibernate.DATE)
						.addScalar("pedn", Hibernate.INTEGER)
						.addScalar("encomenda", Hibernate.INTEGER)
						.addScalar("desconto", Hibernate.DOUBLE)
						.addScalar("existeOc", Hibernate.BOOLEAN)
						.addScalar("usuarioWebId", Hibernate.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(PedVendaFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		return (PedVendaFB) query.uniqueResult();
	}
	
	@Override
	public Integer insert(PedVendaFB pedVendaFB) throws DAOException {
		try {
			
			Integer pedVendaFBId = getSeq();
			System.out.println("[PedVendaFBDAOHibernate][insert][id]" + pedVendaFBId);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PEDVENDA (ID_PEDVENDA, ID_PESSOA_CLI, ID_PESSOA_VEND, ID_PESSOA_EMP, ID_CONDPAGTO, ID_TIPOMOVFISC, ID_TIPOFRETE, ID_TABPRECO, ID_PEDVENDASTATUS, ID_TIPOCOBR, ENTRADA, CONCLUSAO, EFETIVACAO, LIQUIDACAO, VALPEDIDO, NUMPEDCLI, VALFRETE, VALDESPACESS, OBSERVACAO, ENTREGA, ID_USUARIO, ISBLOQPRECO, ISBLOQCRED, ISBLOQCAR, PREVRETIRADA, FORMAPAGTO, ID_USUARIO_LOCK, IDNUMSOLEXTERNA, LIBERADOINTEG, ID_ENDERECO_ENTREGA, VALORDESCONTO, VALORST, UFCLI, ALIQICMSDEST, VALTOTGERADODUP, SEPARAANT, TIPOPEDIDO, PREVRETIRADADATAHORA, DATAULTALTERACAO, CALCVENCDUPDATAEFET, IMPRESSO, ORCAMENTO, VALORIPI, OBSERVACAO2, DESCFLEX, DESCGESTAOVENDA, SALDOINICVENDEDOR, COMPOEFLUXO, VALTAXAENTREGA, UFEMPRESA, PERCRATEIODUP, ID_MOEDA, VALORCOTACAO, DATACOTACAO, TIPOCAMBIO, TIPOENTRADAPEDIDO, VALIDADECOTACAODIAS, CONTATO, CONTATOEMAIL, CONTATOTELEFONE, ID_PESSOA_VENDAORDREM, PREVCLIENTE, PEDN, ENCOMENDA, DESCONTO, EXISTEOC, NOMECLIENTE, ID_USUARIO_WEB) ")
			.append("VALUES (:ID_PEDVENDA, ")
			        .append(":ID_PESSOA_CLI, ")
			        .append(":ID_PESSOA_VEND, ")
			        .append(":ID_PESSOA_EMP, ")
			        .append(":ID_CONDPAGTO, ")
			        .append(":ID_TIPOMOVFISC, ")
			        .append(":ID_TIPOFRETE, ")
			        .append(":ID_TABPRECO, ")
			        .append(":ID_PEDVENDASTATUS, ")
			        .append(":ID_TIPOCOBR, ")
			        .append(":ENTRADA, ")
			        .append(":CONCLUSAO, ")
			        .append(":EFETIVACAO, ")
			        .append(":LIQUIDACAO, ")
			        .append(":VALPEDIDO, ")
			        .append(":NUMPEDCLI, ")
			        .append(":VALFRETE, ")
			        .append(":VALDESPACESS, ")
			        .append(":OBSERVACAO, ")
			        .append(":ENTREGA, ")
			        .append(":ID_USUARIO, ")
			        .append(":ISBLOQPRECO, ")
			        .append(":ISBLOQCRED, ")
			        .append(":ISBLOQCAR, ")
			        .append(":PREVRETIRADA, ")
			        .append(":FORMAPAGTO, ")
			        .append(":ID_USUARIO_LOCK, ")
			        .append(":IDNUMSOLEXTERNA, ")
			        .append(":LIBERADOINTEG, ")
			        .append(":ID_ENDERECO_ENTREGA, ")
			        .append(":VALORDESCONTO, ")
			        .append(":VALORST, ")
			        .append(":UFCLI, ")
			        .append(":ALIQICMSDEST, ")
			        .append(":VALTOTGERADODUP, ")
			        .append(":SEPARAANT, ")
			        .append(":TIPOPEDIDO, ")
			        .append(":PREVRETIRADADATAHORA, ")
			        .append(":DATAULTALTERACAO, ")
			        .append(":CALCVENCDUPDATAEFET, ")
			        .append(":IMPRESSO, ")
			        .append(":ORCAMENTO, ")
			        .append(":VALORIPI, ")
			        .append(":OBSERVACAO2, ")
			        .append(":DESCFLEX, ")
			        .append(":DESCGESTAOVENDA, ")
			        .append(":SALDOINICVENDEDOR, ")
			        .append(":COMPOEFLUXO, ")
			        .append(":VALTAXAENTREGA, ")
			        .append(":UFEMPRESA, ")
			        .append(":PERCRATEIODUP, ")
			        .append(":ID_MOEDA, ")
			        .append(":VALORCOTACAO, ")
			        .append(":DATACOTACAO, ")
			        .append(":TIPOCAMBIO, ")
			        .append(":TIPOENTRADAPEDIDO, ")
			        .append(":VALIDADECOTACAODIAS, ")
			        .append(":CONTATO, ")
			        .append(":CONTATOEMAIL, ")
			        .append(":CONTATOTELEFONE, ")
			        .append(":ID_PESSOA_VENDAORDREM, ")
			        .append(":PREVCLIENTE, ")
			        .append(":PEDN, ")
			        .append(":ENCOMENDA, ")
			        .append(":DESCONTO, ")
			        .append(":EXISTEOC, ")
			        .append(":NOMECLIENTE, ")
				    .append(":ID_USUARIO_WEB) ");
			
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("ID_PESSOA_CLI", pedVendaFB.getClienteId());
	        query.setParameter("ID_PESSOA_VEND", pedVendaFB.getVendedorId());
	        query.setParameter("ID_PESSOA_EMP", pedVendaFB.getEmpresaId());
	        query.setParameter("ID_CONDPAGTO", pedVendaFB.getCondPagtoId());
	        query.setParameter("ID_TIPOMOVFISC", pedVendaFB.getMovFiscTipoId());
	        query.setParameter("ID_TIPOFRETE", pedVendaFB.getFreteTipoId());
	        query.setParameter("ID_TABPRECO", pedVendaFB.getTabPrecoId());
	        query.setParameter("ID_PEDVENDASTATUS", pedVendaFB.getPedVendaStatusId());
	        query.setParameter("ID_TIPOCOBR", pedVendaFB.getCobrTipoId());
	        query.setParameter("ENTRADA", pedVendaFB.getEntrada());
	        query.setParameter("CONCLUSAO", pedVendaFB.getConclusao());
	        query.setParameter("EFETIVACAO", pedVendaFB.getEfetivacao());
	        query.setParameter("LIQUIDACAO", pedVendaFB.getLiquidacao());
	        query.setParameter("VALPEDIDO", pedVendaFB.getValPedido());
	        query.setParameter("NUMPEDCLI", pedVendaFB.getNumPedCli());
	        query.setParameter("VALFRETE", pedVendaFB.getValFrete());
	        query.setParameter("VALDESPACESS", pedVendaFB.getValDespAcess());
	        query.setParameter("OBSERVACAO", pedVendaFB.getObservacao());
	        query.setParameter("ENTREGA", pedVendaFB.getEntrega());
	        query.setParameter("ID_USUARIO", pedVendaFB.getUsuarioId());
	        query.setParameter("ISBLOQPRECO", pedVendaFB.getBloqPreco());
	        query.setParameter("ISBLOQCRED", pedVendaFB.getBloqCred());
	        query.setParameter("ISBLOQCAR", pedVendaFB.getBloqCar());
	        query.setParameter("PREVRETIRADA", pedVendaFB.getPrevRetirada());
	        query.setParameter("FORMAPAGTO", pedVendaFB.getFormaPagtoId());
	        query.setParameter("ID_USUARIO_LOCK", pedVendaFB.getUsuarioLockId());
	        query.setParameter("IDNUMSOLEXTERNA", pedVendaFB.getNumSolExterna());
	        query.setParameter("LIBERADOINTEG", pedVendaFB.getLiberadoInteg());
	        query.setParameter("ID_ENDERECO_ENTREGA", pedVendaFB.getEnderecoEntregaId());
	        query.setParameter("VALORDESCONTO", pedVendaFB.getValorDesconto());
	        query.setParameter("VALORST", pedVendaFB.getValorST());
	        query.setParameter("UFCLI", pedVendaFB.getUfCli());
	        query.setParameter("ALIQICMSDEST", pedVendaFB.getAliqICMSDest());
	        query.setParameter("VALTOTGERADODUP", pedVendaFB.getValTotGeradoDup());
	        query.setParameter("SEPARAANT", pedVendaFB.getSeparaAnt());
	        query.setParameter("TIPOPEDIDO", pedVendaFB.getTipoPedido());
	        query.setParameter("PREVRETIRADADATAHORA", pedVendaFB.getPrevRetiradaDataHora());
	        query.setParameter("DATAULTALTERACAO", pedVendaFB.getDataUltAlteracao());
	        query.setParameter("CALCVENCDUPDATAEFET", pedVendaFB.getCalcVendDUpdataEfet());
	        query.setParameter("IMPRESSO", pedVendaFB.getImpresso());
	        query.setParameter("ORCAMENTO", pedVendaFB.getOrcamento());
	        query.setParameter("VALORIPI", pedVendaFB.getValorIPI());
	        query.setParameter("OBSERVACAO2", pedVendaFB.getObservacao2());
	        query.setParameter("DESCFLEX", pedVendaFB.getDescFlex());
	        query.setParameter("DESCGESTAOVENDA", pedVendaFB.getDescGestaoVenda());
	        query.setParameter("SALDOINICVENDEDOR", pedVendaFB.getSaldoInicVendedor());
	        query.setParameter("COMPOEFLUXO", pedVendaFB.getCompoEFluxo());
	        query.setParameter("VALTAXAENTREGA", pedVendaFB.getValTaxaEntrega());
	        query.setParameter("UFEMPRESA", pedVendaFB.getUfEmpresa());
	        query.setParameter("PERCRATEIODUP", pedVendaFB.getPercRateioDup());
	        query.setParameter("ID_MOEDA", pedVendaFB.getMoedaId());
	        query.setParameter("VALORCOTACAO", pedVendaFB.getValorCotacao());
	        query.setParameter("DATACOTACAO", pedVendaFB.getDataCotacao());
	        query.setParameter("TIPOCAMBIO", pedVendaFB.getTipoCambio());
	        query.setParameter("TIPOENTRADAPEDIDO", pedVendaFB.getTipoEntradaPedido());
	        query.setParameter("VALIDADECOTACAODIAS", pedVendaFB.getValidadeCotacaoDias());
	        query.setParameter("CONTATO", pedVendaFB.getContato());
	        query.setParameter("CONTATOEMAIL", pedVendaFB.getContatoEmail());
	        query.setParameter("CONTATOTELEFONE", pedVendaFB.getContatoTelefone());
	        query.setParameter("ID_PESSOA_VENDAORDREM", pedVendaFB.getPessoaVendaOrdemId());
	        query.setParameter("PREVCLIENTE", pedVendaFB.getPrevCliente());
	        query.setParameter("PEDN", pedVendaFB.getPedn());
	        query.setParameter("ENCOMENDA", pedVendaFB.getEncomenda());
	        query.setParameter("DESCONTO", pedVendaFB.getDesconto() == null ? 0.0 : pedVendaFB.getDesconto());
	        query.setParameter("EXISTEOC", 0);
	        query.setParameter("NOMECLIENTE", pedVendaFB.getNomeCliente());
	        query.setParameter("ID_USUARIO_WEB", pedVendaFB.getUsuarioWebId());
	        
	        
			query.executeUpdate();
	        
			return pedVendaFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
			
	}
	
	@Override
	public void update(PedVendaFB pedVendaFB) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][update][id]" + pedVendaFB.getId());
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDA SET ")
					        .append("ID_CONDPAGTO = :ID_CONDPAGTO, ")
					        .append("ID_TIPOMOVFISC = :ID_TIPOMOVFISC, ")
					        .append("ID_TIPOFRETE = :ID_TIPOFRETE, ")
					        .append("ID_TABPRECO = :ID_TABPRECO, ")
					        .append("ID_PEDVENDASTATUS = :ID_PEDVENDASTATUS, ")
					        .append("ID_TIPOCOBR = :ID_TIPOCOBR, ")
					        .append("ID_PESSOA_CLI = :ID_PESSOA_CLI, ")
					        .append("ID_PESSOA_EMP = :ID_PESSOA_EMP, ")					        
					        .append("CONCLUSAO = :CONCLUSAO, ")
					        .append("EFETIVACAO = :EFETIVACAO, ")
					        .append("LIQUIDACAO = :LIQUIDACAO, ")
					        .append("VALPEDIDO = :VALPEDIDO, ")
					        .append("NUMPEDCLI = :NUMPEDCLI, ")
					        .append("VALFRETE = :VALFRETE, ")
					        .append("VALDESPACESS = :VALDESPACESS, ")
					        .append("OBSERVACAO = :OBSERVACAO, ")
					        .append("ENTREGA = :ENTREGA, ")
					        .append("ID_USUARIO = :ID_USUARIO, ")
					        .append("ID_USUARIO_WEB = :ID_USUARIO_WEB, ")
					        .append("ISBLOQPRECO = :ISBLOQPRECO, ")
					        .append("ISBLOQCRED = :ISBLOQCRED, ")
					        .append("ISBLOQCAR = :ISBLOQCAR, ")
					        .append("PREVRETIRADA = :PREVRETIRADA, ")
					        .append("FORMAPAGTO = :FORMAPAGTO, ")
					        .append("ID_USUARIO_LOCK = :ID_USUARIO_LOCK, ")
					        .append("IDNUMSOLEXTERNA = :IDNUMSOLEXTERNA, ")
					        .append("LIBERADOINTEG = :LIBERADOINTEG, ")
					        .append("ID_ENDERECO_ENTREGA = :ID_ENDERECO_ENTREGA, ")
					        .append("VALORDESCONTO = :VALORDESCONTO, ")
					        .append("VALORST = :VALORST, ")
					        .append("UFCLI = :UFCLI, ")
					        .append("ALIQICMSDEST = :ALIQICMSDEST, ")
					        .append("VALTOTGERADODUP = :VALTOTGERADODUP, ")
					        .append("SEPARAANT = :SEPARAANT, ")
					        .append("TIPOPEDIDO = :TIPOPEDIDO, ")
					        .append("PREVRETIRADADATAHORA = :PREVRETIRADADATAHORA, ")
					        .append("DATAULTALTERACAO = :DATAULTALTERACAO, ")
					        .append("CALCVENCDUPDATAEFET = :CALCVENCDUPDATAEFET, ")
					        .append("IMPRESSO = :IMPRESSO, ")
					        .append("ORCAMENTO = :ORCAMENTO, ")
					        .append("VALORIPI = :VALORIPI, ")
					        .append("OBSERVACAO2 = :OBSERVACAO2, ")
					        .append("DESCFLEX = :DESCFLEX, ")
					        .append("DESCGESTAOVENDA = :DESCGESTAOVENDA, ")
					        .append("SALDOINICVENDEDOR = :SALDOINICVENDEDOR, ")
					        .append("COMPOEFLUXO = :COMPOEFLUXO, ")
					        .append("VALTAXAENTREGA = :VALTAXAENTREGA, ")
					        .append("UFEMPRESA = :UFEMPRESA, ")
					        .append("PERCRATEIODUP = :PERCRATEIODUP, ")
					        .append("ID_MOEDA = :ID_MOEDA, ")
					        .append("VALORCOTACAO = :VALORCOTACAO, ")
					        .append("DATACOTACAO = :DATACOTACAO, ")
					        .append("TIPOCAMBIO = :TIPOCAMBIO, ")
					        .append("TIPOENTRADAPEDIDO = :TIPOENTRADAPEDIDO, ")
					        .append("VALIDADECOTACAODIAS = :VALIDADECOTACAODIAS, ")
					        .append("CONTATO = :CONTATO, ")
					        .append("CONTATOEMAIL = :CONTATOEMAIL, ")
					        .append("CONTATOTELEFONE = :CONTATOTELEFONE, ")
					        .append("ID_PESSOA_VENDAORDREM = :ID_PESSOA_VENDAORDREM, ")
					        .append("PREVCLIENTE = :PREVCLIENTE, ")
					        .append("PEDN = :PEDN, ")
					        .append("DESCONTO = :DESCONTO, ")
					        .append("NOMECLIENTE = :NOMECLIENTE ")
					      .append(" WHERE ID_PEDVENDA = :ID_PEDVENDA");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFB.getId());
			query.setParameter("ID_PESSOA_CLI", pedVendaFB.getClienteId());
			query.setParameter("ID_PESSOA_EMP", pedVendaFB.getEmpresaId());
	        query.setParameter("ID_CONDPAGTO", pedVendaFB.getCondPagtoId());
	        query.setParameter("ID_TIPOMOVFISC", pedVendaFB.getMovFiscTipoId());
	        query.setParameter("ID_TIPOFRETE", pedVendaFB.getFreteTipoId());
	        query.setParameter("ID_TABPRECO", pedVendaFB.getTabPrecoId());
	        query.setParameter("ID_PEDVENDASTATUS", pedVendaFB.getPedVendaStatusId());
	        query.setParameter("ID_TIPOCOBR", pedVendaFB.getCobrTipoId());
	        query.setParameter("CONCLUSAO", pedVendaFB.getConclusao());
	        query.setParameter("EFETIVACAO", pedVendaFB.getEfetivacao());
	        query.setParameter("LIQUIDACAO", pedVendaFB.getLiquidacao());
	        query.setParameter("VALPEDIDO", pedVendaFB.getValPedido());
	        query.setParameter("NUMPEDCLI", pedVendaFB.getNumPedCli());
	        query.setParameter("VALFRETE", pedVendaFB.getValFrete());
	        query.setParameter("VALDESPACESS", pedVendaFB.getValDespAcess());
	        query.setParameter("OBSERVACAO", pedVendaFB.getObservacao());
	        query.setParameter("ENTREGA", pedVendaFB.getEntrega());
	        query.setParameter("ID_USUARIO", pedVendaFB.getUsuarioId());
			query.setParameter("ISBLOQPRECO", pedVendaFB.getBloqPreco());
	        query.setParameter("ISBLOQCRED", pedVendaFB.getBloqCred());
	        query.setParameter("ISBLOQCAR", pedVendaFB.getBloqCar());
	        query.setParameter("PREVRETIRADA", pedVendaFB.getPrevRetirada());
	        query.setParameter("FORMAPAGTO", pedVendaFB.getFormaPagtoId());
	        query.setParameter("ID_USUARIO_LOCK", pedVendaFB.getUsuarioLockId());
	        query.setParameter("IDNUMSOLEXTERNA", pedVendaFB.getNumSolExterna());
	        query.setParameter("LIBERADOINTEG", pedVendaFB.getLiberadoInteg());
	        query.setParameter("ID_ENDERECO_ENTREGA", pedVendaFB.getEnderecoEntregaId());
	        query.setParameter("VALORDESCONTO", pedVendaFB.getValorDesconto());
	        query.setParameter("VALORST", pedVendaFB.getValorST());
	        query.setParameter("UFCLI", pedVendaFB.getUfCli());
	        query.setParameter("ALIQICMSDEST", pedVendaFB.getAliqICMSDest());
	        query.setParameter("VALTOTGERADODUP", pedVendaFB.getValTotGeradoDup());
	        query.setParameter("SEPARAANT", pedVendaFB.getSeparaAnt());
	        query.setParameter("TIPOPEDIDO", pedVendaFB.getTipoPedido());
	        query.setParameter("PREVRETIRADADATAHORA", pedVendaFB.getPrevRetiradaDataHora());
	        query.setParameter("DATAULTALTERACAO", pedVendaFB.getDataUltAlteracao());
	        query.setParameter("CALCVENCDUPDATAEFET", pedVendaFB.getCalcVendDUpdataEfet());
	        query.setParameter("IMPRESSO", pedVendaFB.getImpresso());
	        query.setParameter("ORCAMENTO", pedVendaFB.getOrcamento());
	        query.setParameter("VALORIPI", pedVendaFB.getValorIPI());
	        query.setParameter("OBSERVACAO2", pedVendaFB.getObservacao2());
	        query.setParameter("DESCFLEX", pedVendaFB.getDescFlex());
	        query.setParameter("DESCGESTAOVENDA", pedVendaFB.getDescGestaoVenda());
	        query.setParameter("SALDOINICVENDEDOR", pedVendaFB.getSaldoInicVendedor());
	        query.setParameter("COMPOEFLUXO", pedVendaFB.getCompoEFluxo());
	        query.setParameter("VALTAXAENTREGA", pedVendaFB.getValTaxaEntrega());
	        query.setParameter("UFEMPRESA", pedVendaFB.getUfEmpresa());
	        query.setParameter("PERCRATEIODUP", pedVendaFB.getPercRateioDup());
	        query.setParameter("ID_MOEDA", pedVendaFB.getMoedaId());
	        query.setParameter("VALORCOTACAO", pedVendaFB.getValorCotacao());
	        query.setParameter("DATACOTACAO", pedVendaFB.getDataCotacao());
	        query.setParameter("TIPOCAMBIO", pedVendaFB.getTipoCambio());
	        query.setParameter("TIPOENTRADAPEDIDO", pedVendaFB.getTipoEntradaPedido());
	        query.setParameter("VALIDADECOTACAODIAS", pedVendaFB.getValidadeCotacaoDias());
	        query.setParameter("CONTATO", pedVendaFB.getContato());
	        query.setParameter("CONTATOEMAIL", pedVendaFB.getContatoEmail());
	        query.setParameter("CONTATOTELEFONE", pedVendaFB.getContatoTelefone());
	        query.setParameter("ID_PESSOA_VENDAORDREM", pedVendaFB.getPessoaVendaOrdemId());
	        query.setParameter("PREVCLIENTE", pedVendaFB.getPrevCliente());
	        query.setParameter("PEDN", pedVendaFB.getPedn());
	        query.setParameter("DESCONTO", pedVendaFB.getDesconto() == null ? 0.0 : pedVendaFB.getDesconto());
	        query.setParameter("NOMECLIENTE", pedVendaFB.getNomeCliente());
	        query.setParameter("ID_USUARIO_WEB", pedVendaFB.getUsuarioWebId());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public void updateEmDigitacaoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][updateEmDigitacaoPedVenda][id]" + pedVendaFBId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDA SET ")
					        .append("ID_PEDVENDASTATUS = :ID_PEDVENDASTATUS, ")
					        .append(" ID_USUARIO_WEB = :usuarioId, ")
					      .append(" WHERE ID_PEDVENDA = :ID_PEDVENDA");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("usuarioId", usuarioId);
	        query.setParameter("ID_PEDVENDASTATUS", PedVendaFB.SITUACAO_DIGITACAO);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public void updateLiberarPedVenda(Integer pedVendaFBId, Integer pedVendaStatusFBId, Date dtEfetivacao, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][updateLiberarPedVenda][id]" + pedVendaFBId);
			
			String varSet = "";
			if(dtEfetivacao!=null) {
				varSet = ", EFETIVACAO = :EFETIVACAO";
			}
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDA SET ")
			                .append(" ISBLOQPRECO  = 0, ")
			                .append(" ID_USUARIO_WEB = :usuarioId, ")
					        .append(" ID_PEDVENDASTATUS = :ID_PEDVENDASTATUS ").append(varSet)
						  .append(" WHERE ID_PEDVENDA = :ID_PEDVENDA");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("usuarioId", usuarioId);
	        query.setParameter("ID_PEDVENDASTATUS", pedVendaStatusFBId); 
	        if(dtEfetivacao!=null) {
	        	query.setParameter("EFETIVACAO", dtEfetivacao);
	        }
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public void updateNaoLiberarPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][updateNaoLiberarPedVenda][id]" + pedVendaFBId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDA SET ")
					        .append("ID_PEDVENDASTATUS = :ID_PEDVENDASTATUS, ")
					        .append(" ID_USUARIO_WEB = :usuarioId ")
					      .append(" WHERE ID_PEDVENDA = :ID_PEDVENDA");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("usuarioId", usuarioId);
	        query.setParameter("ID_PEDVENDASTATUS", PedVendaFB.SITUACAO_NAO_LIBERADO);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}
	
	@Override
	public void updateAguardPagtoPedVenda(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][updateAguardPagtoPedVenda][id]" + pedVendaFBId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PEDVENDA SET ")
						    .append(" ID_USUARIO_WEB = :usuarioId, ")
					        .append("ID_PEDVENDASTATUS = :ID_PEDVENDASTATUS ")
					      .append(" WHERE ID_PEDVENDA = :ID_PEDVENDA");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("usuarioId", usuarioId);
	        query.setParameter("ID_PEDVENDASTATUS", PedVendaFB.SITUACAO_AGUARDANDO_PAGTO);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(Integer pedVendaFBId, Integer usuarioId) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][excluir][id]" + pedVendaFBId);
			
			String sql = "UPDATE PEDVENDA SET ID_PEDVENDASTATUS = 2, "+
			             "                    ID_USUARIO_WEB = :ID_USUARIO_WEB "+
					     "              WHERE ID_PEDVENDA = :ID_PEDVENDA";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_PEDVENDA", pedVendaFBId);
			query.setParameter("ID_USUARIO_WEB", usuarioId);
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaFB> listar(Integer vendedorId, Integer clienteId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS)
		   .append(" FROM pedvenda a")
		   .append(" WHERE a.ID_PESSOA_VEND = :idPessoaVend ")
           .append(" AND a.ID_PESSOA_CLI = :idCliente");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("clienteId", Hibernate.INTEGER)
				.addScalar("vendedorId", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("condPagtoId", Hibernate.INTEGER)
				.addScalar("movFiscTipoId", Hibernate.INTEGER)
				.addScalar("freteTipoId", Hibernate.INTEGER)
				.addScalar("tabPrecoId", Hibernate.STRING)
				.addScalar("pedVendaStatusId", Hibernate.INTEGER)
				.addScalar("cobrTipoId", Hibernate.INTEGER)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("formaPagtoId", Hibernate.INTEGER)
				.addScalar("usuarioLockId", Hibernate.INTEGER)
				.addScalar("enderecoEntregaId", Hibernate.INTEGER)
				.addScalar("moedaId", Hibernate.INTEGER)
				.addScalar("pessoaVendaOrdemId", Hibernate.INTEGER)
				.addScalar("entrada", Hibernate.TIMESTAMP)
				.addScalar("nomeCliente", Hibernate.STRING)				
				.addScalar("conclusao", Hibernate.TIMESTAMP)
				.addScalar("efetivacao", Hibernate.TIMESTAMP)
				.addScalar("liquidacao", Hibernate.TIMESTAMP)
				.addScalar("valPedido", Hibernate.DOUBLE)
				.addScalar("numPedCli", Hibernate.STRING)
				.addScalar("valFrete", Hibernate.DOUBLE)
				.addScalar("valDespAcess", Hibernate.DOUBLE)
				.addScalar("observacao", Hibernate.STRING)
				.addScalar("entrega", Hibernate.INTEGER)
				.addScalar("bloqPreco", Hibernate.INTEGER)
				.addScalar("bloqCred", Hibernate.INTEGER)
				.addScalar("bloqCar", Hibernate.INTEGER)
				.addScalar("prevRetirada", Hibernate.DATE)
				.addScalar("numSolExterna", Hibernate.STRING)
				.addScalar("liberadoInteg", Hibernate.INTEGER)
				.addScalar("valorDesconto", Hibernate.DOUBLE)
				.addScalar("valorST", Hibernate.DOUBLE)
				.addScalar("ufCli", Hibernate.STRING)
				.addScalar("aliqICMSDest", Hibernate.DOUBLE)
				.addScalar("valTotGeradoDup", Hibernate.DOUBLE)
				.addScalar("separaAnt", Hibernate.INTEGER)
				.addScalar("tipoPedido", Hibernate.INTEGER)
				.addScalar("prevRetiradaDataHora", Hibernate.TIMESTAMP)
				.addScalar("dataUltAlteracao", Hibernate.TIMESTAMP)
				.addScalar("seqPedVenda", Hibernate.INTEGER)
				.addScalar("calcVendDUpdataEfet", Hibernate.INTEGER)
				.addScalar("impresso", Hibernate.INTEGER)
				.addScalar("orcamento", Hibernate.INTEGER)
				.addScalar("valorIPI", Hibernate.DOUBLE)
				.addScalar("observacao2", Hibernate.STRING)
				.addScalar("descFlex", Hibernate.DOUBLE)
				.addScalar("descGestaoVenda", Hibernate.DOUBLE)
				.addScalar("saldoInicVendedor", Hibernate.DOUBLE)
				.addScalar("compoEFluxo", Hibernate.INTEGER)
				.addScalar("valTaxaEntrega", Hibernate.DOUBLE)
				.addScalar("ufEmpresa", Hibernate.STRING)
				.addScalar("percRateioDup", Hibernate.DOUBLE)
				.addScalar("valorCotacao", Hibernate.DOUBLE)
				.addScalar("dataCotacao", Hibernate.DATE)
				.addScalar("tipoCambio", Hibernate.INTEGER)
				.addScalar("tipoEntradaPedido", Hibernate.INTEGER)
				.addScalar("validadeCotacaoDias", Hibernate.INTEGER)
				.addScalar("contato", Hibernate.STRING)
				.addScalar("contatoEmail", Hibernate.STRING)
				.addScalar("contatoTelefone", Hibernate.STRING)
				.addScalar("prevCliente", Hibernate.DATE)
				.addScalar("pedn", Hibernate.INTEGER)
				.addScalar("encomenda", Hibernate.INTEGER)
				.addScalar("desconto", Hibernate.DOUBLE)
				.addScalar("existeOc", Hibernate.BOOLEAN)
				.addScalar("usuarioWebId", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(PedVendaFB.class));
		
		query.setParameter("idPessoaVend", vendedorId);
		query.setParameter("idCliente", clienteId);
		
		return query.list();
	}

	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_PEDVENDA_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do PedVendaFB.");
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

}
