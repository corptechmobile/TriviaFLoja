package br.com.coletor.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ColetorInv;
import br.com.coletor.model.ColetorRomaneio;
import br.com.coletor.model.ColetorRomaneioItem;
import br.com.coletor.model.dto.ColetorRomaneioCorteDTO;

public class DAOColetorRomaneio {
	
	private Session session;

	public DAOColetorRomaneio(Session session) {
		super();
		this.session = session;
	}
	
	public ColetorRomaneio carregar(Integer coletorRomaneioId) {
		
		String sql = " SELECT "
						+ "	r.ID_ROMANEIO AS id, "
						+ "	r.ID_PROCTRANSP AS procTranspId, "
						+ " pt.ID_PESSOA_EMP AS empresaId, "
						+ " emp.NOMEFANTMNEM AS empresaNomeFant, "
						+ "	r.STATUS AS status, "
						+ "	r.MOMENTOCONF AS dtInicio, "
						+ "	r.MOMENTOFINALIZADO AS dtTermino,	 "
						+ "	r.MOMENTOCORTE AS dtCorte, "
						+ "	r.MOMENTOCANC AS dtCancelado, "
						+ "	r.MOMENTOGER AS dtCriacao, "
						+ "	r.ID_USUARIO_GER AS usuarioCriacaoId, "
						+ "	ug.NOME AS usuarioCriacaoNome, "
						+ "	r.ID_USUARIO_CONF AS usuarioConfId, "
						+ "	uconf.NOME AS usuarioConfNome, "
						+ "	r.ID_USUARIO_CORTE AS usuarioCorteId, "
						+ "	ucorte.NOME AS usuarioCorteNome, "
						+ "	r.ID_USUARIO_FINALIZADO AS usuarioTerminoId, "
						+ "	uf.NOME AS usuarioTerminoNome, "
						+ "	r.ID_USUARIO_CANC AS usuarioCanceladoId, "
						+ "	ucancel.NOME AS usuarioCanceladoNome "
					+ " FROM "
						+ " ROMANEIO r "
						+ "	LEFT JOIN USUARIO uconf ON (r.ID_USUARIO_CONF = uconf.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO ucorte ON (r.ID_USUARIO_CORTE = ucorte.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO uf ON (r.ID_USUARIO_FINALIZADO = uf.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO ucancel ON (r.ID_USUARIO_CANC = ucancel.ID_USUARIO ), "
						+ " USUARIO ug, "
						+ "	PROCTRANSP pt, "
						+ " PESSOA emp "
					+ " WHERE r.ID_USUARIO_GER = ug.ID_USUARIO "
					+ " AND R.ID_PROCTRANSP = pt.ID_PROCTRANSP "
					+ " AND pt.ID_PESSOA_EMP = emp.ID_PESSOA "
					+ " AND r.ID_ROMANEIO = :coletorRomaneioId ";

		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorRomaneio.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setMaxResults(1);
		
		return (ColetorRomaneio)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorRomaneio> listar(Integer usuarioId, Integer numFilter) {
		
		String varWhere = "";
		if(numFilter != null){
			varWhere += " AND (r.ID_ROMANEIO = :numFilter or r.ID_PROCTRANSP = :numFilter) ";
		}
		
		String sql = " SELECT "
						+ "	r.ID_ROMANEIO AS id, "
						+ "	r.ID_PROCTRANSP AS procTranspId, "
						+ " pt.ID_PESSOA_EMP AS empresaId, "
						+ " emp.NOMEFANTMNEM AS empresaNomeFant, "
						+ "	r.STATUS AS status, "
						+ "	r.MOMENTOCONF AS dtInicio, "
						+ "	r.MOMENTOFINALIZADO AS dtTermino,	 "
						+ "	r.MOMENTOCORTE AS dtCorte, "
						+ "	r.MOMENTOCANC AS dtCancelado, "
						+ "	r.MOMENTOGER AS dtCriacao, "
						+ "	r.ID_USUARIO_GER AS usuarioCriacaoId, "
						+ "	ug.NOME AS usuarioCriacaoNome, "
						+ "	r.ID_USUARIO_CONF AS usuarioConfId, "
						+ "	uconf.NOME AS usuarioConfNome, "
						+ "	r.ID_USUARIO_CORTE AS usuarioCorteId, "
						+ "	ucorte.NOME AS usuarioCorteNome, "
						+ "	r.ID_USUARIO_FINALIZADO AS usuarioTerminoId, "
						+ "	uf.NOME AS usuarioTerminoNome, "
						+ "	r.ID_USUARIO_CANC AS usuarioCanceladoId, "
						+ "	ucancel.NOME AS usuarioCanceladoNome "
					+ " FROM "
						+ " ROMANEIO r "
						+ "	LEFT JOIN USUARIO uconf ON (r.ID_USUARIO_CONF = uconf.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO ucorte ON (r.ID_USUARIO_CORTE = ucorte.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO uf ON (r.ID_USUARIO_FINALIZADO = uf.ID_USUARIO ) "
						+ "	LEFT JOIN USUARIO ucancel ON (r.ID_USUARIO_CANC = ucancel.ID_USUARIO ), "
						+ "	USUARIO ug, "
						+ "	PROCTRANSP pt, "
						+ " PESSOA emp "
					+ " WHERE r.ID_USUARIO_GER = ug.ID_USUARIO "
					+ " AND R.ID_PROCTRANSP = pt.ID_PROCTRANSP "
					+ " AND pt.ID_PESSOA_EMP = emp.ID_PESSOA "
					+ " AND (r.ID_USUARIO_CONF IS NULL OR r.ID_USUARIO_CONF = :usuarioId) "
					+ " AND r.STATUS IN (:statusEmAberto, :statusEmConferencia) "
					+ " ORDER BY r.ID_ROMANEIO DESC ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorRomaneio.class);
		query.setParameter("statusEmAberto", ColetorRomaneio.STATUS_EM_ABERTO);
		query.setParameter("statusEmConferencia", ColetorRomaneio.STATUS_EM_CONFERENCIA);
		query.setParameter("usuarioId", usuarioId);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorRomaneioItem> listarItens(Integer coletorRomaneioId) {
		
		String sql = " SELECT ri.ID_ROMANEIOITEM AS id, "
						  + " ri.ID_ROMANEIO AS coletorRomaneioId, "
						  + " ri.ID_PRODUTO AS produtoId, "
						  + " (ri.QTDROMANEIO-ri.QTDAJUSTE) AS qtd, "
					 	  + " ri.QTDCONFERIDA AS qtdConferida, "
						  + " ri.QTDAJUSTE AS qtdAjuste, "
						  + " u.DESCRESUMIDA AS unidVendaDesc "
					+ " FROM ROMANEIOITEM ri, "
						 + " PRODUTO pr, "
						 + " UNIDADE u "
					+ " WHERE ri.ID_PRODUTO = pr.ID_PRODUTO  "
					  + " AND pr.ID_UNIDADE_VENDA = u.ID_UNIDADE  "
				 	  + " AND ri.ID_ROMANEIO = :coletorRomaneioId "
					+ " ORDER BY ri.ID_ROMANEIOITEM";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorRomaneioItem.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		
		return query.list();
	}
	
	public ColetorRomaneioCorteDTO countItensCortes(Integer coletorRomaneioId) {
		String sql = "SELECT i.ID_ROMANEIO as id, "
						+ " count(i.ID_ROMANEIOITEM) as total "
						+ " FROM ROMANEIOITEM i "
						+ "	WHERE i.ID_ROMANEIO = :coletorRomaneioId "
						+ "	AND i.QTDAJUSTE <> 0 "
						+ " GROUP BY i.ID_ROMANEIO ";
		
		Query query = (Query) session.createSQLQuery(sql).addEntity(ColetorRomaneioCorteDTO.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setMaxResults(1);
		return (ColetorRomaneioCorteDTO) query.uniqueResult();
	}
	
	public void updateEmConferencia(Integer coletorRomaneioId, Date dtInicio, Integer usuarioId) {
		String sql = " update ROMANEIO "
					+ " set STATUS = :statusEmConf, "
						+ " MOMENTOCONF = :dtInicio, "
						+ " ID_USUARIO_CONF = :usuarioId "
					+ " where ID_ROMANEIO = :coletorRomaneioId ";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("statusEmConf", ColetorRomaneio.STATUS_EM_CONFERENCIA);
		query.setParameter("dtInicio", dtInicio);
		query.setParameter("usuarioId", usuarioId);
		
		
		query.executeUpdate();
		
	}
	
	public void updateCancelado(Integer coletorRomaneioId, Date dtCancelado, Integer usuarioId) {
		String sql = " update ROMANEIO "
					+ " set STATUS = :statusCancelado, "
						+ " MOMENTOCANC = :dtCancelado, "
						+ " ID_USUARIO_CANC = :usuarioId "
					+ " where ID_ROMANEIO = :coletorRomaneioId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("statusCancelado", ColetorRomaneio.STATUS_CANCELADO);
		query.setParameter("dtCancelado", dtCancelado);
		query.setParameter("usuarioId", usuarioId);
		
		query.executeUpdate();
	}
	
	public void updateConferidoCorte(Integer coletorRomaneioId, Date dtConf, Integer usuarioId) {
		String sql = " update ROMANEIO "
					+ " set STATUS = :statusConfCorte, "
						+ " MOMENTOCORTE = :dtConfCorte, "
						+ " ID_USUARIO_CORTE = :usuarioId "
					+ " where ID_ROMANEIO = :coletorRomaneioId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("statusConfCorte", ColetorRomaneio.STATUS_CONFERIDO_CORTE);
		query.setParameter("dtConfCorte", dtConf);
		query.setParameter("usuarioId", usuarioId);
		
		query.executeUpdate();
	}
	
	public void updateFinalizado(Integer coletorRomaneioId, Date dtFin, Integer usuarioId) {
		String sql = " update ROMANEIO "
					+ " set STATUS = :statusFin, "
						+ " MOMENTOFINALIZADO = :dtFin, "
						+ " ID_USUARIO_FINALIZADO = :usuarioId "
					+ " where ID_ROMANEIO = :coletorRomaneioId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("statusFin", ColetorRomaneio.STATUS_FINALIZADO);
		query.setParameter("dtFin", dtFin);
		query.setParameter("usuarioId", usuarioId);
		
		query.executeUpdate();
	}
	
	public void updateConferido(Integer coletorRomaneioId, Date dtConf, Integer usuarioId) {
		String sql = " update ROMANEIO "
					+ " set STATUS = :statusConf, "
						+ " MOMENTOCONFERIDO = :dtConf, "
						+ " ID_USUARIO_CONFERIDO = :usuarioId "
					+ " where ID_ROMANEIO = :coletorRomaneioId";
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInv.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("statusConf", ColetorRomaneio.STATUS_CONFERIDO);
		query.setParameter("dtConf", dtConf);
		query.setParameter("usuarioId", usuarioId);
		
		query.executeUpdate();
	}
	
}