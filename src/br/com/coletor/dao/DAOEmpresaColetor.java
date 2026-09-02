package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.EmpresaColetor;

public class DAOEmpresaColetor {
	
	private Session session;

	public DAOEmpresaColetor(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<EmpresaColetor> listar(Integer usuarioId) {
		String sql = "SELECT "
						+ "	e.ID_PESSOA AS id, "
						+ "	p.CNPJCPF AS cnpjCpf, "
						+ "	p.NOMEFANTMNEM AS nomeFantasia, "
						+ "	e.P_CONFERENCIACEGADESAIDA AS confCegaSaida "
					+ " FROM EMPRESA e, "
						+ "	 PESSOA p, "
						+ "	 USUARIOEMPRESA ue"
					+ " WHERE e.ID_PESSOA = p.ID_PESSOA "
					  + " AND ue.ID_PESSOA_EMP = p.ID_PESSOA "
					  + " AND ue.ID_USUARIO = :usuarioId"
					+ " ORDER BY p.NOMEFANTMNEM";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(EmpresaColetor.class);
		query.setParameter("usuarioId", usuarioId);
		
		return query.list();
	}

}
