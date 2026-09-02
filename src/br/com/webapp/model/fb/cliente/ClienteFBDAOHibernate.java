package br.com.webapp.model.fb.cliente;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBDTO;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.Funcoes;

public class ClienteFBDAOHibernate implements ClienteFBDAO {
	
	private StringBuilder COLLUMNS;
	
	private Session session;
	public void setSession(Session session){
		this.session = session;
	}
	
	public ClienteFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.id_pessoa AS id, ")
		 .append(" a.id_endereco_principal AS enderecoPrincipalId, ")
		 .append(" a.id_telefone_principal AS telefonePrincipalId, ")
		 .append(" a.tipopessoa AS tipoPessoa, ")
		 .append(" a.razaosocialnome AS razaoSocial, ")
		 .append(" a.nomefantmnem AS nomeFantasia, ")
		 .append(" a.cnpjcpf AS cnpjCpf, ")
		 .append(" a.inscest AS inscEst, ")
		 .append(" a.inscmun AS inscMun, ")
		 .append(" a.numrg AS numRg, ")
		 .append(" a.email AS email, ")
		 
		 .append(" b.id_tipocliente AS clienteTipoId, ")
		 .append(" b.id_endereco_cobr AS enderecoCobrancaId, ")
		 .append(" b.id_endereco_entrega AS enderecoEntregaId, ")
		 .append(" b.ativo AS ativo, ")
		 .append(" b.bloqmanual AS bloqManual, ")
		 .append(" b.coligada AS coligada, ")		 
		 
		 .append(" c.id_tipoendereco AS enderecoTipoId, ")
		 .append(" c.id_tabpais AS paisId, ")
		 .append(" c.id_estado AS estadoId, ")
		 .append(" c.id_municipio AS municipioId, ")
		 .append(" c.logradouro AS logradouro, ")
		 .append(" c.complemento AS complemento, ")
		 .append(" c.bairro AS bairro, ")
		 .append(" c.cidade AS cidade, ")
		 .append(" c.cep AS cep, ")
		 .append(" c.pontoreferencia AS pontoReferencia, ")
		 
		 .append(" d.id_tipofone AS telefoneTipoId, ")
		 .append(" d.codarea AS codArea, ")
		 .append(" d.numero AS numero, ")
		 .append(" d.ramal AS ramal, ")
		 
		 .append(" b.id_tipofrete AS freteTipoId, ")
		 .append(" b.id_tipomovfisc AS movFiscTipoId, ")
		 .append(" b.id_condpagto AS condPagtoId, ")
		 .append(" b.id_tipocobr AS cobrTipoId, ")
		 .append(" null AS formaPagtoId,")
		.append(" b.DATAULTALTERACAO AS dataUltAlteracao,")
		.append(" b.ID_USUARIO_ULTALT AS usuarioUltAlteracao");
		
	}

	//.append(" (select first 1 cf.id_formapagtopv from condpagtoformapagtopv cf where cf.id_condpagto = b.id_condpagto) AS formaPagtoId");
	
	@Override
	public ClienteFB carregar(Integer clienteId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
			.append(" FROM pessoa a, cliente b, endereco c, telefone d ")
			.append(" WHERE a.id_pessoa = b.id_pessoa ")
			  .append(" AND a.id_endereco_principal = c.id_endereco ")
			  .append(" AND a.id_telefone_principal = d.id_telefone ")
			  .append(" AND a.id_pessoa = :clienteId");
		
		Query q = (Query) session.createSQLQuery(sql.toString()).addEntity(ClienteFB.class);
		q.setParameter("clienteId", clienteId);
		q.setMaxResults(1);
		
		return (ClienteFB) q.uniqueResult();
	}

	@Override
	public ClienteFB carregar(String cnpjCpf) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
				.append(" FROM pessoa a, cliente b, endereco c, telefone d ")
				.append(" WHERE a.id_pessoa = b.id_pessoa ")
				  .append(" AND a.id_endereco_principal = c.id_endereco ")
				  .append(" AND a.id_telefone_principal = d.id_telefone ")
				  //.append(" AND b.ativo = :ativo ")
				  .append(" AND a.cnpjcpf = :cnpjCpf");
		
		
		// .append(" AND b.bloqmanual = :sembloqueado ")
		
		Query q = (Query) session.createSQLQuery(sql.toString()).addEntity(ClienteFB.class);
		q.setParameter("cnpjCpf", cnpjCpf);
		//q.setParameter("ativo", ClienteFB.CLIENTE_ATIVO);
		//q.setParameter("sembloqueado", ClienteFB.CLIENTE_SEMBLOQUEIO);
		q.setMaxResults(1);
		
		return (ClienteFB) q.uniqueResult();
	}

	@Override
	public Integer insert(ClienteFB clienteFB) throws DAOException {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("select * from PROC_CADASTROCLIENTE (")
						    .append(":TIPOPESSOA,")
						    .append(":CPFCNPJ,")
						    .append(":RAZAOSOCIALNOME,")
						    .append(":NOMEFANTASIA,")
						    .append(":NUMRG,")
						    .append(":INSCESTADUAL,")
						    .append(":INSCMUNICIPAL,")
						    .append(":EMAIL,")
						    .append(":CEP,")
						    .append(":TIPOENDERECOID,")
						    .append(":PAISID,")
						    .append(":ESTADOID,")
						    .append(":CIDADEID,")
						    .append(":CIDADEDESC,")
						    .append(":LOGRADOURO,")
						    .append(":COMPLEMENTO,")
						    .append(":BAIRRO,")
						    .append(":PONTOREFERENCIA,")
						    .append(":TIPOFONEID,")
						    .append(":DDDFONE,")
						    .append(":NUMEROFONE,")
						    .append(":RAMALFONE )");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("TIPOPESSOA", clienteFB.getTipoPessoa());
		    query.setParameter("CPFCNPJ", clienteFB.getCnpjCpf());
		    query.setParameter("RAZAOSOCIALNOME", clienteFB.getRazaoSocial());
		    query.setParameter("NOMEFANTASIA", clienteFB.getNomeFantasia());
		    query.setParameter("NUMRG", clienteFB.getNumRg());
		    query.setParameter("INSCESTADUAL", clienteFB.getInscEst());
		    query.setParameter("INSCMUNICIPAL", clienteFB.getInscMun());
		    query.setParameter("EMAIL", clienteFB.getEmail());
		    query.setParameter("CEP", clienteFB.getCep());
		    query.setParameter("TIPOENDERECOID", clienteFB.getEnderecoTipoId());
		    query.setParameter("PAISID", clienteFB.getPaisId());
		    query.setParameter("ESTADOID", clienteFB.getEstadoId());
		    query.setParameter("CIDADEID", clienteFB.getMunicipioId());
		    query.setParameter("CIDADEDESC", clienteFB.getCidade());
		    query.setParameter("LOGRADOURO", clienteFB.getLogradouro());
		    query.setParameter("COMPLEMENTO", clienteFB.getComplemento());
		    query.setParameter("BAIRRO", clienteFB.getBairro());
		    query.setParameter("PONTOREFERENCIA", clienteFB.getPontoReferencia());
		    query.setParameter("TIPOFONEID", clienteFB.getTelefoneTipoId());
		    query.setParameter("DDDFONE", clienteFB.getCodArea());
		    query.setParameter("NUMEROFONE", clienteFB.getNumero());
		    query.setParameter("RAMALFONE", clienteFB.getRamal());
	        
		    query.setMaxResults(1);
		    
			Integer clienteFBId = (Integer) query.uniqueResult();
			
			return clienteFBId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public Integer update(ClienteFB clienteFB) throws DAOException {
		try {
			
			StringBuilder sql;
			
			// Pessoa
			sql = new StringBuilder();
			sql.append("UPDATE PESSOA SET ")
						.append("TIPOPESSOA = :TIPOPESSOA, ")
						.append("RAZAOSOCIALNOME = :RAZAOSOCIALNOME, ")
						.append("NOMEFANTMNEM = :NOMEFANTASIA, ")
						.append("NUMRG = :NUMRG, ")
						.append("INSCEST = :INSCESTADUAL, ")
						.append("INSCMUN = :INSCMUNICIPAL, ")
						.append("EMAIL = :EMAIL ")
						.append("WHERE ID_PESSOA = :ID_PESSOA ");
			
			Query queryPessoa = (Query) session.createSQLQuery(sql.toString());
			queryPessoa.setParameter("ID_PESSOA", clienteFB.getId());
			queryPessoa.setParameter("TIPOPESSOA", clienteFB.getTipoPessoa());
			queryPessoa.setParameter("RAZAOSOCIALNOME", clienteFB.getRazaoSocial());
		    queryPessoa.setParameter("NOMEFANTASIA", clienteFB.getNomeFantasia());
		    queryPessoa.setParameter("NUMRG", clienteFB.getNumRg());
		    queryPessoa.setParameter("INSCESTADUAL", clienteFB.getInscEst());
		    queryPessoa.setParameter("INSCMUNICIPAL", clienteFB.getInscMun());
		    queryPessoa.setParameter("EMAIL", clienteFB.getEmail());
		    queryPessoa.executeUpdate();
		    
		 // Cliente
		    sql = new StringBuilder();
			sql.append("UPDATE CLIENTE SET ")
						.append("DATAULTALTERACAO = :DATAULTALTERACAO, ")
						.append("ID_USUARIO_ULTALT = :ID_USUARIO_ULTALT ")
						.append("WHERE ID_PESSOA = :ID_PESSOA ");
			Query queryCliente = (Query) session.createSQLQuery(sql.toString());
			queryCliente.setParameter("DATAULTALTERACAO", clienteFB.getDataUltAlteracao());
			queryCliente.setParameter("ID_USUARIO_ULTALT", clienteFB.getUsuarioUltAlteracao());
			queryCliente.setParameter("ID_PESSOA", clienteFB.getId());
			queryCliente.executeUpdate();
		    
		    // Endereco
		    sql = new StringBuilder();
			sql.append("UPDATE ENDERECO SET ")
						.append("CEP = :CEP, ")
						.append("ID_TIPOENDERECO = :TIPOENDERECOID, ")
						.append("ID_TABPAIS = :PAISID, ")
						.append("ID_ESTADO = :ESTADOID, ")
						.append("ID_MUNICIPIO = :CIDADEID, ")
						.append("CIDADE = :CIDADEDESC, ")
						.append("LOGRADOURO = :LOGRADOURO, ")
						.append("COMPLEMENTO = :COMPLEMENTO, ")
						.append("BAIRRO = :BAIRRO, ")
						.append("PONTOREFERENCIA = :PONTOREFERENCIA ")
						.append("WHERE ID_ENDERECO = :ID_ENDERECO ");
			
			Query queryEndereco = (Query) session.createSQLQuery(sql.toString());
			queryEndereco.setParameter("ID_ENDERECO", clienteFB.getEnderecoPrincipalId());
			queryEndereco.setParameter("CEP", clienteFB.getCep());
			queryEndereco.setParameter("TIPOENDERECOID", clienteFB.getEnderecoTipoId());
			queryEndereco.setParameter("PAISID", clienteFB.getPaisId());
			queryEndereco.setParameter("ESTADOID", clienteFB.getEstadoId());
			queryEndereco.setParameter("CIDADEID", clienteFB.getMunicipioId());
			queryEndereco.setParameter("CIDADEDESC", clienteFB.getCidade());
			queryEndereco.setParameter("LOGRADOURO", clienteFB.getLogradouro());
			queryEndereco.setParameter("COMPLEMENTO", clienteFB.getComplemento());
			queryEndereco.setParameter("BAIRRO", clienteFB.getBairro());
			queryEndereco.setParameter("PONTOREFERENCIA", clienteFB.getPontoReferencia());
			queryEndereco.executeUpdate();
		    
			// Endereco
		    sql = new StringBuilder();
			sql.append("UPDATE TELEFONE SET ")
						.append("ID_TIPOFONE = :TIPOFONEID, ")
						.append("CODAREA = :DDDFONE, ")
						.append("NUMERO = :NUMEROFONE, ")
						.append("RAMAL = :RAMALFONE ")
						.append("WHERE ID_TELEFONE = :ID_TELEFONE ");
			Query queryTelefone = (Query) session.createSQLQuery(sql.toString());
		 	queryTelefone.setParameter("ID_TELEFONE", clienteFB.getTelefonePrincipalId());
		    queryTelefone.setParameter("TIPOFONEID", clienteFB.getTelefoneTipoId());
		    queryTelefone.setParameter("DDDFONE", clienteFB.getCodArea());
		    queryTelefone.setParameter("NUMEROFONE", clienteFB.getNumero());
		    queryTelefone.setParameter("RAMALFONE", clienteFB.getRamal());
		    queryTelefone.executeUpdate();
	        
			return clienteFB.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteFB> listar(String descricaoFilter) {
		String varWhere = ""; 
		Integer clienteId = null;
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			try {
				clienteId = Integer.parseInt(descricaoFilter);
				varWhere = " AND a.id_pessoa = :clienteId ";
			} catch (Exception e) {
				varWhere = " AND (razaosocialnome like :descricaoFilterLike";
				varWhere += " or nomefantmnem like :descricaoFilterLike ";
				varWhere += " or cnpjcpf = :descricaoFilter )";
			}
			
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
				.append(" FROM pessoa a, cliente b, endereco c, telefone d ")
				.append(" WHERE a.id_pessoa = b.id_pessoa ")
				  .append(" AND a.id_endereco_principal = c.id_endereco ")
				  .append(" AND a.id_telefone_principal = d.id_telefone ")
				  //.append(" AND b.ativo = :ativo ")
				  .append(varWhere);

		// .append(" AND b.bloqmanual = :sembloqueado ")
		
		Query q = (Query) session.createSQLQuery(sql.toString()).addEntity(ClienteFB.class);
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			if(clienteId==null) {
				q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
				q.setParameter("descricaoFilter", descricaoFilter);
			}else {
				q.setParameter("clienteId", clienteId);	
			}
		}
		
		//q.setParameter("ativo", ClienteFB.CLIENTE_ATIVO);
		//q.setParameter("sembloqueado", ClienteFB.CLIENTE_SEMBLOQUEIO);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteFB> listarClienteTransferencia(String descricaoFilter, Integer tipoPedido) {
		String varWhere = ""; 
		Integer clienteId = null;
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			try {
				clienteId = Integer.parseInt(descricaoFilter);
				varWhere = " AND a.id_pessoa = :clienteId ";
			} catch (Exception e) {
				varWhere = " AND (razaosocialnome like :descricaoFilterLike";
				varWhere += " or nomefantmnem like :descricaoFilterLike ";
				varWhere += " or cnpjcpf = :descricaoFilter )";
			}
			
		}
		
		if(tipoPedido.equals(Funcoes.IS_TRANSFERENCIA)) {
			varWhere += " AND b.id_tipomovfisc in (select tmf.id_tipomovfisc from tipomovfisc tmf where tmf.classe = 2 and tmf.natureza = 'S' group by tmf.id_tipomovfisc) ";
		}else {
			varWhere += " AND b.id_tipomovfisc not in (select tmf.id_tipomovfisc from tipomovfisc tmf where tmf.classe = 2 and tmf.natureza = 'S' group by tmf.id_tipomovfisc) ";
		}

		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString()) 
				.append(" FROM pessoa a, cliente b, endereco c, telefone d ")
				.append(" WHERE a.id_pessoa = b.id_pessoa ")
				  .append(" AND a.id_endereco_principal = c.id_endereco ")
				  .append(" AND a.id_telefone_principal = d.id_telefone ")
				  .append(varWhere);
		
		Query q = (Query) session.createSQLQuery(sql.toString()).addEntity(ClienteFB.class);
		if(descricaoFilter!=null && !"".equals(descricaoFilter)) {
			if(clienteId==null) {
				q.setParameter("descricaoFilterLike", "%" + descricaoFilter + "%");
				q.setParameter("descricaoFilter", descricaoFilter);
			}else {
				q.setParameter("clienteId", clienteId);	
			}
		}
		
		
		
		return q.list();
	}
	
	
	public List<ClienteNaoPositivadoFBDTO> listarClientesNaoPositivados(Date dt1, Date dt2, String cliente, String cidade, String bairro, String numero) {
		String varWhere = "";
		
		if(cliente != null) {
			varWhere += "AND (UPPER(p.razaosocialnome) like '%"+cliente+"%' or (p.cnpjcpf = '"+cliente+"') ) ";
		}
		
		if(cidade != null) {
			varWhere += "AND UPPER(e.cidade) like '%"+cidade.toUpperCase()+"%' ";
		}

		if(bairro != null) {
			varWhere += "AND UPPER(e.bairro) like '%"+bairro.toUpperCase()+"%' ";
		}
		
		if(numero != null) {
			varWhere += "AND tel.numero like '%"+numero+"%' ";
		}

		String sql = 	" select cl.id_pessoa id, "+
						"        p.tipopessoa tipoPessoa, "+		
						"        p.cnpjcpf cnpj, "+
						"        p.razaosocialnome descricao, "+
						"        tel.codarea codArea, "+
						"        tel.numero, "+
						"        e.cidade, "+
						"        e.bairro, "+
						"        icli.ped_dataultcompra dtUltimaCompra, "+
						"        coalesce(icli.ped_maiorcompra,0) maiorCompra, "+
						"        coalesce(icli.ped_freqcompra,0) freqCompra, "+
						"        coalesce(icli.ped_mediacompra,0) mediaCompra "+
						" from cliente cl, "+
						"      pessoa p, "+
						"      endereco e , "+
						"      telefone tel, "+
						"      infoger_clihist icli "+
						" where p.id_pessoa=cl.id_pessoa "+
						"   and p.id_endereco_principal=e.id_endereco "+
						"   and icli.id_pessoa_cli=p.id_pessoa "+
						"   and tel.id_telefone=p.id_telefone_principal "+
						"   and cl.ativo=1 "+
						"   "+varWhere+" "+
						"   and p.id_pessoa not in ( "+
						" select pv.id_pessoa_cli "+
						"   from pedvenda pv, "+
						"        pedvendastatus pvs, "+
						"        tipomovfisc tmf "+
						"  where pv.efetivacao between :dt1 and :dt2 "+
						"    and pv.id_pedvendastatus=pvs.id_pedvendastatus "+
						"    and pv.id_tipomovfisc=tmf.id_tipomovfisc "+
						"    and pvs.efetivado=1 "+
						"    and tmf.classger in (0,1) "+
						"  group by pv.id_pessoa_cli) "+
						"  order by e.cidade, e.bairro ";

			Query query = (Query) session.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER) 
					.addScalar("cnpj", Hibernate.STRING)
					.addScalar("tipoPessoa", Hibernate.STRING)
					.addScalar("descricao", Hibernate.STRING)
					.addScalar("codArea", Hibernate.STRING)
					.addScalar("numero", Hibernate.STRING)
					.addScalar("cnpj", Hibernate.STRING)
					.addScalar("cidade", Hibernate.STRING)
					.addScalar("bairro", Hibernate.STRING)
					.addScalar("dtUltimaCompra", Hibernate.DATE)
					.addScalar("maiorCompra", Hibernate.DOUBLE)
					.addScalar("freqCompra", Hibernate.INTEGER)
					.addScalar("mediaCompra", Hibernate.DOUBLE)
					.setResultTransformer(Transformers.aliasToBean(ClienteNaoPositivadoFBDTO.class));
				query.setParameter("dt1", dt1);
		        query.setParameter("dt2", dt2);


			return query.list();
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
	public ClienteCreditoFBDTO verificarLimiteCredito(Integer clienteId, Integer pedvendaId) {
		
		Integer pedvenda = 0;
		if(pedvendaId != null) {
			pedvenda = pedvendaId;
		}
		
		String sql = 	" select (coalesce(TITPEND,0) + coalesce(CHEQUEPEND,0) + coalesce(PEDIDOPEND,0)) responsabilidade, "+
				"        (coalesce(LIMITECRED,0) - (coalesce(TITPEND,0) + coalesce(CHEQUEPEND,0) + coalesce(PEDIDOPEND,0))) saldoDisponivel, "+
				"        coalesce(LIMITECRED,0) limiteCredito, "+
				"        possuiChequeDev, "+
				"        possuiDupVenc "+
				" from ( "+
				        " select "+
				"           tab.*, "+
				"          (iif(tab.LIMITEGRUPO = 0, tab.TitPend, "+
				"                (select "+
				"                 (sum(coalesce(tab4.TitPendTodos, 0)) - sum(coalesce(tab4.TitPendPedsBloqCred,0))) TitPend "+
				"               from "+
				"                (select "+
				"                   coalesce(sum(tt7.SALDOTITULO),0) TitPendTodos, "+
				"                   0 TitPendPedsBloqCred "+
				"                 from "+
				"                   TITULOREC tr7, "+
				"                   TITULO tt7 "+
				"                 where tr7.ID_TITULO = tt7.ID_TITULO "+
				"                 and tt7.ID_TITULOSIT in (1,2)  "+
				"                 and tr7.ID_PESSOA_CLI = tab.ID_PESSOA "+
				"                 union "+
				"                 select "+
				"                   0 TitPendTodos, "+
				"                   sum(coalesce(tt7.SALDOTITULO,0)) TitPendPedsBloqCred "+
				"                 from "+
				"                   TITULOREC tr7, "+
				"                   TITULO tt7, "+
				"                   PEDVENDA pv7 "+
				"                 where tr7.ID_TITULO = tt7.ID_TITULO "+
				"                 and tt7.ID_TITULOSIT in (1,2)  "+
				"                 and tr7.ID_PEDVENDA = pv7.ID_PEDVENDA "+
				"                 and (pv7.ID_PEDVENDA = :ID_PEDVENDA "+
				"                   or (pv7.ID_PEDVENDASTATUS = 1 and pv7.ISBLOQCRED = 1)) "+
				"                 and tr7.ID_PESSOA_CLI = tab.ID_PESSOA "+
				"                 ) tab4)) "+
				"           ) TitPendIndividual, "+
				"          (iif(tab.LIMITEGRUPO = 0, tab.PedidoPend, "+
				"                (select "+
				"                   tab3.SaldoPend "+
				"                 from "+
				"                  (select "+
				"                     sum(pv8.VALPEDIDO - coalesce(pv8.VALTOTGERADODUP,0)) SaldoPend "+
				"                   from "+
				"                     PEDVENDA pv8, "+
				"                     PEDVENDASTATUS pvs8, "+
				"                     TIPOMOVFISC tmf8 "+
				"                   where pv8.ID_PEDVENDASTATUS = pvs8.ID_PEDVENDASTATUS "+
				"                   and tmf8.ID_TIPOMOVFISC = pv8.ID_TIPOMOVFISC "+
				"                   and pvs8.CREDCLIENTE = 1 "+
				"                   and tmf8.LANCACARCAP = 1 "+
				"                   and tmf8.ETAPALANCACAR in (2,3) "+
				"                   and pv8.ID_PESSOA_CLI = tab.ID_PESSOA "+
				"                   ) tab3 "+
				"                 where abs(tab3.SaldoPend) > 3)) "+
				"           ) PedidoPendIndividual, "+
				"          (iif(tab.LIMITEGRUPO = 0, tab.ChequePend, "+
				"                (select "+
				"                   sum(coalesce(igc9.CH_VALORCHCARTEIRA,0)) "+
				"                 from "+
				"                   INFOGER_CLIHIST igc9 "+
				"                 where igc9.ID_PESSOA_CLI = tab.ID_PESSOA)) "+
				"           ) ChequePendIndividual "+
				"         from "+
				"          (select "+
				"             cli.ID_PESSOA, "+
				"             cli.LIMITEGRUPO, "+
				"            (case cli.LIMITEGRUPO "+
				"               when 0 then cli.LIMITECRED "+
				"               else ge.LIMITECREDITO "+
				"             end "+
				"             ) LimiteCred, "+
				"            (select "+
				"                 (sum(coalesce(tab4.TitPendTodos, 0)) - sum(coalesce(tab4.TitPendPedsBloqCred,0))) TitPend "+
				"               from "+
				"                (select "+
				"                   sum(coalesce(tt1.SALDOTITULO,0)) TitPendTodos, "+
				"                   0 TitPendPedsBloqCred "+
				"                 from "+
				"                   TITULOREC tr1, "+
				"                   TITULO tt1, "+
				"                   CLIENTE c1 "+
				"                 where tr1.ID_TITULO = tt1.ID_TITULO "+
				"                 and tt1.ID_TITULOSIT in (1,2)  "+
				"                 and tr1.ID_PESSOA_CLI = c1.ID_PESSOA "+
				"                 and (c1.ID_PESSOA = cli.ID_PESSOA "+
				"                   or (cli.LIMITEGRUPO = 1 and c1.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                 union "+
				"                 select "+
				"                   0 TitPendTodos, "+
				"                   sum(coalesce(tt1.SALDOTITULO,0)) TitPendPedsBloqCred "+
				"                 from "+
				"                   TITULOREC tr1, "+
				"                   TITULO tt1, "+
				"                   CLIENTE c1, "+
				"                   PEDVENDA pv1 "+
				"                 where tr1.ID_TITULO = tt1.ID_TITULO "+
				"                 and tt1.ID_TITULOSIT in (1,2)  "+
				"                 and tr1.ID_PESSOA_CLI = c1.ID_PESSOA "+
				"                 and tr1.ID_PEDVENDA = pv1.ID_PEDVENDA "+
				"                 and (pv1.ID_PEDVENDA = :ID_PEDVENDA "+
				"                   or (pv1.ID_PEDVENDASTATUS = 1 and pv1.ISBLOQCRED = 1)) "+
				"                 and (c1.ID_PESSOA = cli.ID_PESSOA "+
				"                   or (cli.LIMITEGRUPO = 1 and c1.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                 ) tab4 "+
				"             ) TitPend, "+
				"            (select "+
				"               tab2.ValPedPendDup "+
				"             from "+
				"              (select "+
				"                 sum(pv2.VALPEDIDO - iif(COALESCE(pv2.VALTOTGERADODUP,0) > 0 and "+
				"                                      COALESCE(pv2.VALTOTGERADODUP,0)> pv2.VALPEDIDO, "+
				"                                      pv2.VALPEDIDO,     COALESCE(pv2.VALTOTGERADODUP,0))) ValPedPendDup "+
				"               from "+
				"                 PEDVENDA pv2, "+
				"                 PEDVENDASTATUS pvs2, "+
				"                 TIPOMOVFISC tmf2, "+
				"                 CLIENTE c2 "+
				"               where pv2.ID_PEDVENDASTATUS = pvs2.ID_PEDVENDASTATUS "+
				"               and tmf2.ID_TIPOMOVFISC = pv2.ID_TIPOMOVFISC "+
				"               and pvs2.CREDCLIENTE = 1 "+
				"               and tmf2.LANCACARCAP = 1 "+
				"               and tmf2.ETAPALANCACAR in (2,3) "+
				"               and pv2.ID_PESSOA_CLI = c2.ID_PESSOA "+
				"               and (c2.ID_PESSOA = cli.ID_PESSOA "+
				"                 or (cli.LIMITEGRUPO = 1 and c2.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"               ) tab2 "+
				"             where abs(tab2.ValPedPendDup) > 3 "+
				"             ) PedidoPend, "+
				"            (select "+
				"               sum(coalesce(igc3.CH_VALORCHCARTEIRA,0)) "+
				"             from "+
				"               CLIENTE c3, "+
				"               INFOGER_CLIHIST igc3 "+
				"             where c3.ID_PESSOA = igc3.ID_PESSOA_CLI "+
				"             and (c3.ID_PESSOA = cli.ID_PESSOA "+
				"               or (cli.LIMITEGRUPO = 1 and c3.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"             ) ChequePend, "+
				"             iif((select "+
				"                    sum(coalesce(igc4.DUP_POSSUIVENCIDOABERT,0)) "+
				"                  from "+
				"                    CLIENTE c4, "+
				"                    INFOGER_CLIHIST igc4 "+
				"                  where c4.ID_PESSOA = igc4.ID_PESSOA_CLI "+
				"                  and (c4.ID_PESSOA = cli.ID_PESSOA "+
				"                    or (cli.LIMITEGRUPO = 1 and c4.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                  ) > 0, 1, 0 "+
				"                 ) PossuiDupVenc, "+
				"             iif((select first 1 "+
				"                    tt5.ID_TITULO "+
				"                  from "+
				"                    TITULOREC tr5, "+
				"                    TITULO tt5, "+
				"                    CLIENTE c5 "+
				"                  where tr5.ID_TITULO = tt5.ID_TITULO "+
				"                  and tt5.ID_TITULOSIT in (1,2)  "+
				"                  and tr5.ID_PESSOA_CLI = c5.ID_PESSOA "+
				"                  and (c5.ID_PESSOA = cli.ID_PESSOA "+
				"                    or (cli.LIMITEGRUPO = 1 and c5.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                  and current_date = tt5.DATAVENCIMENTO "+
				"                  ) > 0, 1, 0 "+
				"                 ) PossuiDupVencHoje, "+
				"              iif((select "+
				"                    sum(coalesce(igc6.CH_QTDCHDEVOLV,0)) "+
				"                  from "+
				"                    CLIENTE c6, "+
				"                    INFOGER_CLIHIST igc6 "+
				"                  where c6.ID_PESSOA = igc6.ID_PESSOA_CLI "+
				"                  and (c6.ID_PESSOA = cli.ID_PESSOA "+
				"                    or (cli.LIMITEGRUPO = 1 and c6.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                  ) > 0, 1, 0 "+
				"                 ) possuiChequeDev, "+
				"               iif( "+
				"                  (select first 1 ch.ID_CHEQUE "+
				"                   from cheque ch , CLIENTE c "+
				"                   where ch.ID_CHEQUESTATUS in (2,5) "+
				"                   and ch.id_pessoa_cli = c.ID_PESSOA "+
				"                   and  (id_pessoa = cli.id_pessoa "+
				"                         or (cli.limitegrupo = 1 and id_grupoempresarial = cli.id_grupoempresarial)) "+
				"                  ) > 0, 1, 0) possuiChequeDevPendReg, "+
				"               iif((select first 1 "+
				"                    tt5.ID_TITULO "+
				"                  from "+
				"                    TITULOREC tr5, "+
				"                    TITULO tt5, "+
				"                    CLIENTE c5 "+
				"                  where tr5.ID_TITULO = tt5.ID_TITULO "+
				"                  and tt5.ID_TITULOSIT in (1,2)  "+
				"                  and tr5.ID_PESSOA_CLI = c5.ID_PESSOA "+
				"                  and (c5.ID_PESSOA = cli.ID_PESSOA "+
				"                    or (cli.LIMITEGRUPO = 1 and c5.ID_GRUPOEMPRESARIAL = cli.ID_GRUPOEMPRESARIAL)) "+
				"                  and tr5.bloqcredcli = 1 "+
				"                  ) > 0, 1, 0 "+
				"                 ) PossuiBloqTit "+
				"           from "+
				"             CLIENTE cli, "+
				"             GRUPOEMPRESARIAL ge "+
				"           where cli.ID_GRUPOEMPRESARIAL = ge.ID_GRUPOEMPRESARIAL "+
				"           and cli.ID_PESSOA = :ID_PESSOA_CLI "+
				"           ) tab "+
				"  ) tabcred ";
				
			Query query = (Query) session.createSQLQuery(sql)
					.addScalar("responsabilidade", Hibernate.DOUBLE)
					.addScalar("saldoDisponivel", Hibernate.DOUBLE)
					.addScalar("limiteCredito", Hibernate.DOUBLE)
					.addScalar("possuiChequeDev", Hibernate.INTEGER)
					.addScalar("possuiDupVenc", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(ClienteCreditoFBDTO.class));
				query.setParameter("ID_PESSOA_CLI", clienteId);
		        query.setParameter("ID_PEDVENDA", pedvenda);
		        query.setMaxResults(1);		

			return (ClienteCreditoFBDTO) query.uniqueResult();
	}
}
