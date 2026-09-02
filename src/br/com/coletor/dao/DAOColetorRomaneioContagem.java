package br.com.coletor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.espelho.EspelhoColetorRomaneioContagem;
import br.com.coletor.model.ColetorRomaneioContagem;
import br.com.coletor.model.dto.ColetorRomaneioResumoDTO;
import br.com.webapp.web.util.UtilData;

public class DAOColetorRomaneioContagem {
	
	private Session session;

	public DAOColetorRomaneioContagem(Session session) {
		super();
		this.session = session;
	}

	public void inserir(EspelhoColetorRomaneioContagem espelho) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			insert.append(" INSERT INTO COLETOR_ROMANEIO_CONTAGEM ")
					.append(" ( ")
					.append(" CHAVE, ")
					.append(" ID_ROMANEIO, ")
					.append(" ID_USUARIO, ")
					.append(" ID_PRODUTO, ")
					.append(" CODBARRA, ")
					.append(" QTD, ")
					.append(" DTLEITURA ")
					.append(" ) ")
					.append(" VALUES ")
					.append(" ( ")
					.append(" :CHAVE, ")
					.append(" :ID_ROMANEIO, ")
					.append(" :ID_USUARIO, ")
					.append(" :ID_PRODUTO, ")
					.append(" :CODBARRA, ")
					.append(" :QTD, ")
					.append(" :DTLEITURA ")
					.append(" ); ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());

		    query.setParameter("CHAVE", espelho.getChave());
		    query.setParameter("ID_ROMANEIO", espelho.getColetorRomaneioId());
		    query.setParameter("ID_USUARIO", espelho.getUsuarioId());
		    query.setParameter("ID_PRODUTO", espelho.getProdutoId());
		    query.setParameter("CODBARRA", espelho.getCodBarra());
		    query.setParameter("QTD", espelho.getQtd());
		    query.setParameter("DTLEITURA", UtilData.formatarStringParaData(espelho.getDtLeitura(), UtilData.FORMATO_DATA_HORA));
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format("Erro ao tentar processar contagem com chave %s", espelho.getChave()));
		}
		
	}
	
	public void update(ColetorRomaneioResumoDTO dto) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			insert.append(" update ROMANEIOITEM ")
					.append(" set QTDCONFERIDA = :QTDCONFERIDA, ")
						.append(" QTDAJUSTE = :QTDAJUSTE ")
					.append(" where ID_ROMANEIOITEM = :ID_ROMANEIOITEM ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());

		    query.setParameter("ID_ROMANEIOITEM", dto.getRomaneioItemId());
		    query.setParameter("QTDCONFERIDA", dto.getQtdConferida());
		    query.setParameter("QTDAJUSTE", dto.getQtdAjuste());
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format("Erro ao tentar processar dados de contagem do produto no Romaneio %s", dto.getRomaneioId().toString()));
		}
		
	}

	public void delete(Integer coletorRomeneioId, Integer usuarioId) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			insert.append(" DELETE FROM COLETOR_ROMANEIO_CONTAGEM ")
					.append(" WHERE ID_ROMANEIO = :coletorRomeneioId ")
					.append(" AND ID_USUARIO = :usuarioId ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());

		    query.setParameter("coletorRomeneioId", coletorRomeneioId);
		    query.setParameter("usuarioId", usuarioId);
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format("Erro ao tentar processar exclusão de contagem do Romaneio %s", coletorRomeneioId.toString()));
		}
		
	}

	public void integracao(Integer romaneioId) throws Exception {
		for(ColetorRomaneioResumoDTO rs : listarResumo(romaneioId)) {
			update(rs);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorRomaneioResumoDTO> listarResumo(Integer coletorRomaneioId) {
		String sql = "SELECT ri.ID_ROMANEIO AS romaneioId, "
				+ "			 ri.ID_ROMANEIOITEM AS romaneioItemId, "
				+ "			 ri.ID_PRODUTO AS produtoId, "
				+ "			 max(ri.QTDROMANEIO) AS qtdRomaneio, "
				+ "			 COALESCE(SUM(rc.QTD), 0) AS qtdConferida "
				+ "		FROM ROMANEIOITEM ri "
				+ "			 LEFT JOIN COLETOR_ROMANEIO_CONTAGEM rc ON (ri.ID_ROMANEIO = rc.ID_ROMANEIO AND ri.ID_PRODUTO = rc.ID_PRODUTO) "
				+ "		WHERE ri.ID_ROMANEIO = :coletorRomaneioId "
				+ "		GROUP BY ri.ID_ROMANEIO, ri.ID_ROMANEIOITEM, ri.ID_PRODUTO";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorRomaneioResumoDTO.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ColetorRomaneioContagem> listar(Integer coletorRomaneioId, Integer usuarioId) {
		
		String sql = "SELECT CHAVE as chave, "
						 + " ID_ROMANEIO AS romaneioId, "
						 + " ID_USUARIO AS usuarioId, "
						 + " ID_PRODUTO AS produtoId, "
						 + " CODBARRA AS codBarra, "
						 + " QTD AS qtd, "
						 + " DTLEITURA as dtLeitura "
					+ "FROM COLETOR_ROMANEIO_CONTAGEM "
					+ "WHERE ID_ROMANEIO = :coletorRomaneioId "
					+ "AND ID_USUARIO = :usuarioId "
					+ "ORDER BY DTLEITURA ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorRomaneioContagem.class);
		query.setParameter("coletorRomaneioId", coletorRomaneioId);
		query.setParameter("usuarioId", usuarioId);
		return query.list();
	}
	
	public List<EspelhoColetorRomaneioContagem> convertEspelho(List<ColetorRomaneioContagem> lista){
		List<EspelhoColetorRomaneioContagem> result = new ArrayList<EspelhoColetorRomaneioContagem>();
		for(ColetorRomaneioContagem rs : lista) {
			result.add(new EspelhoColetorRomaneioContagem(rs));
		}
		return result;
	}

}