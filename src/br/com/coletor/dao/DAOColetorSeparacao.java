package br.com.coletor.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorInv;
import br.com.coletor.model.ColetorSeparacao;

public class DAOColetorSeparacao {
	
	private Session session;

	public DAOColetorSeparacao(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorSeparacao carregar(Integer coletorSeparacaoId) {
		
		String sql = "SELECT "
						+ "	c.ID_COLETORSEPARACAO AS id, "
						+ "	c.ID_ORDEMCARREG AS ordemCarregId, "
						+ "	c.ID_PEDVENDA AS pedVendaId, "
						+ "	c.ID_PESSOA_EMP AS empresaId, "
						+ "	pe.NOMEFANTMNEM AS empresaNomeFant, "
						+ "	c.ID_PESSOA_CLI AS clienteId, "
						+ "	cli.CNPJCPF AS clienteCnpjCpf, "
						+ "	cli.NOMEFANTMNEM AS clienteNomeFant, "
						+ "	c.ID_SEPARADOR AS separadorId, "
						+ "	ps.NOMEFANTMNEM AS separadorNome, "
						+ "	c.ID_USUARIO AS usuarioId, "
						+ "	u.NOME AS usuarioNome, "
						+ "	c.DTINICIOSEP AS dtInicioSep, "
						+ "	c.DTTERMINOSEP AS dtTerminoSep, "
						+ "	c.STATUS "
					+ "FROM COLETOR_SEPARACAO c, "
						+ "	PESSOA pe, "
						+ "	PESSOA cli, "
						+ "	PESSOA ps, "
						+ "	USUARIO u "
					+ " WHERE c.ID_COLETORSEPARACAO = :coletorSeparacaoId "
					+ "AND c.ID_PESSOA_EMP = pe.id_pessoa "
					+ "AND c.ID_PESSOA_CLI = cli.ID_PESSOA "
					+ "AND c.ID_SEPARADOR = ps.ID_PESSOA "
					+ "AND c.ID_USUARIO = u.ID_USUARIO ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorSeparacao.class);
		query.setParameter("coletorSeparacaoId", coletorSeparacaoId);
		query.setMaxResults(1);
		
		return (ColetorSeparacao) query.uniqueResult();
	}
	
	public ColetorSeparacao carregar(Integer ordemCarregId, Integer separadorId) {
		
		String sql = "SELECT "
						+ "	c.ID_COLETORSEPARACAO AS id, "
						+ "	c.ID_ORDEMCARREG AS ordemCarregId, "
						+ "	c.ID_PEDVENDA AS pedVendaId, "
						+ "	c.ID_PESSOA_EMP AS empresaId, "
						+ "	pe.NOMEFANTMNEM AS empresaNomeFant, "
						+ "	c.ID_PESSOA_CLI AS clienteId, "
						+ "	cli.CNPJCPF AS clienteCnpjCpf, "
						+ "	cli.NOMEFANTMNEM AS clienteNomeFant, "
						+ "	c.ID_SEPARADOR AS separadorId, "
						+ "	ps.NOMEFANTMNEM AS separadorNome, "
						+ "	c.ID_USUARIO AS usuarioId, "
						+ "	u.NOME AS usuarioNome, "
						+ "	c.DTINICIOSEP AS dtInicioSep, "
						+ "	c.DTTERMINOSEP AS dtTerminoSep, "
						+ "	c.STATUS "
					+ "FROM COLETOR_SEPARACAO c, "
						+ "	PESSOA pe, "
						+ "	PESSOA cli, "
						+ "	PESSOA ps, "
						+ "	USUARIO u "
					+ " WHERE c.ID_ORDEMCARREG = :ordemCarregId "
					+ "AND c.ID_SEPARADOR = :separadorId "
					+ "AND c.ID_PESSOA_EMP = pe.id_pessoa "
					+ "AND c.ID_PESSOA_CLI = cli.ID_PESSOA "
					+ "AND c.ID_SEPARADOR = ps.ID_PESSOA "
					+ "AND c.ID_USUARIO = u.ID_USUARIO ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorSeparacao.class);
		query.setParameter("ordemCarregId", ordemCarregId);
		query.setParameter("separadorId", separadorId);
		query.setMaxResults(1);
		
		return (ColetorSeparacao) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorSeparacao> listar(Integer usuarioId, Integer pedVendaId, Integer status) {
		
		String varWhere = "";
		if(pedVendaId != null){
			varWhere += " AND c.id_pedvenda = :pedVendaId ";
		}
		
		if(status != null){
			varWhere += " AND c.status = :status ";
		}
		
		String sql = "SELECT "
						+ "	c.ID_COLETORSEPARACAO AS id, "
						+ "	c.ID_ORDEMCARREG AS ordemCarregId, "
						+ "	c.ID_PEDVENDA AS pedVendaId, "
						+ "	c.ID_PESSOA_EMP AS empresaId, "
						+ "	pe.NOMEFANTMNEM AS empresaNomeFant, "
						+ "	c.ID_PESSOA_CLI AS clienteId, "
						+ "	cli.CNPJCPF AS clienteCnpjCpf, "
						+ "	cli.NOMEFANTMNEM AS clienteNomeFant, "
						+ "	c.ID_SEPARADOR AS separadorId, "
						+ "	ps.NOMEFANTMNEM AS separadorNome, "
						+ "	c.ID_USUARIO AS usuarioId, "
						+ "	u.NOME AS usuarioNome, "
						+ "	c.DTINICIOSEP AS dtInicioSep, "
						+ "	c.DTTERMINOSEP AS dtTerminoSep, "
						+ "	c.STATUS "
					+ "FROM COLETOR_SEPARACAO c, "
						+ "	PESSOA pe, "
						+ "	PESSOA cli, "
						+ "	PESSOA ps, "
						+ "	USUARIO u "
					+ "WHERE c.ID_PESSOA_EMP = pe.id_pessoa "
					+ "AND c.ID_PESSOA_CLI = cli.ID_PESSOA "
					+ "AND c.ID_SEPARADOR = ps.ID_PESSOA "
					+ "AND c.ID_USUARIO = u.ID_USUARIO "
					+ "AND c.ID_USUARIO = :usuarioId " + varWhere;
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorSeparacao.class);
		
		query.setParameter("usuarioId", usuarioId);
		
		if(pedVendaId != null){
			query.setParameter("pedVendaId", pedVendaId);
		}
		
		if(status != null){
			query.setParameter("status", status);
		}
		
		return query.list();
	}
	
	public void iniciar(Integer ordemCarregId, Integer pedVendaId, Integer empresaId, Integer clienteId, Integer usuarioId, Integer separadorId, Date dtIni) {
		String sql = "INSERT INTO COLETOR_SEPARACAO "
				+ "	(ID_ORDEMCARREG, ID_PEDVENDA, ID_PESSOA_EMP, ID_PESSOA_CLI, ID_SEPARADOR, ID_USUARIO, DTINICIOSEP, DTTERMINOSEP, STATUS) "
				+ "	VALUES( "
					+ " :ordemCarregId, "
					+ " :pedVendaId, "
					+ " :empresaId, "
					+ " :clienteId, "
					+ " :separadorId, "
					+ " :usuarioId, "
					+ " :dtIni, "
					+ " null, "
					+ " :status)";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("ordemCarregId", ordemCarregId);
		query.setParameter("pedVendaId", pedVendaId);
		query.setParameter("empresaId", empresaId);
		query.setParameter("clienteId", clienteId);
		query.setParameter("usuarioId", usuarioId);
		query.setParameter("separadorId", separadorId);
		query.setParameter("dtIni", dtIni);
		query.setParameter("status", ColetorSeparacao.STATUS_EM_SEPARACAO);
		
		query.executeUpdate();
	}
	
	public void finalizar(Integer coletorSeparacaoId, Date dtFin) {
		String sql = " update COLETOR_SEPARACAO "
					+ " set STATUS = :status, "
						+ " DTTERMINOSEP = :dtFin "
					+ " WHERE ID_COLETORSEPARACAO = :coletorSeparacaoId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorSeparacaoId", coletorSeparacaoId);
		query.setParameter("dtFin", dtFin);
		query.setParameter("status", ColetorSeparacao.STATUS_FINALIZADA);
		
		query.executeUpdate();
	}
	
	public void excluir(Integer coletorSeparacaoId) {
		String sql = " DELETE FROM COLETOR_SEPARACAO WHERE ID_COLETORSEPARACAO = :coletorSeparacaoId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorSeparacaoId", coletorSeparacaoId);
		query.executeUpdate();
	}
	
}