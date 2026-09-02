package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.espelho.EspelhoColetorInvContagem;
import br.com.coletor.model.ColetorInvContagem;
import br.com.webapp.web.util.UtilData;

public class DAOColetorInvContagem {
	
	private Session session;

	public DAOColetorInvContagem(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<ColetorInvContagem> listar(Integer coletorInvId, boolean excluido) {
		
		String sql = "SELECT ID_COLETOR_INV_CONTAGEM AS id, " 
							+" CHAVE as chave, "
							+" ID_COLETOR_INV as coletorInvId, "
							+" ID_USUARIO as usuarioId, "
							+" ID_PRODUTO as produtoId, "
							+" PRODUTONOVODESC as produtoNovoDesc, "
							+" CODBARRA as codBarra, "
							+" QTDUN as qtdUn, "
							+" QTDEMB as qtdEmb, "
							+" QTDEMBFECHVENDA qtdEmbFechVenda, "
							+" DESCEMBFECHVENDA as descEmbFechVenda, "
							+" DTLEITURA as dtLeitura, "
							+" DTERP as dtErp "
						+" FROM COLETOR_INV_CONTAGEM "
						+" WHERE ID_COLETOR_INV = :coletorInvId "
						  +" AND EXCLUIDO = :excluido "
						+" ORDER BY ID_COLETOR_INV_CONTAGEM ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ColetorInvContagem.class);
		query.setParameter("coletorInvId", coletorInvId);
		query.setParameter("excluido", excluido ? 1 : 0);
		
		return query.list();
	}
	
	public void inserir(EspelhoColetorInvContagem espelho) throws Exception {
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			insert.append(" INSERT INTO COLETOR_INV_CONTAGEM ")
					.append(" ( ")
					.append(" CHAVE, ")
					.append(" ID_COLETOR_INV, ")
					.append(" ID_USUARIO, ")
					.append(" ID_PRODUTO, ")
					.append(" PRODUTONOVODESC, ")
					.append(" CODBARRA, ")
					.append(" QTDUN, ")
					.append(" QTDEMB, ")
					.append(" QTDEMBFECHVENDA, ")
					.append(" DESCEMBFECHVENDA, ")
					.append(" DTLEITURA, ")
					.append(" DTERP, ")
					.append(" EXCLUIDO, ")
					.append(" FLAGZERAR ")
					.append(" ) ")
					.append(" VALUES ")
					.append(" ( ")
					.append(" :chave, ")
					.append(" :coletorInvId, ")
					.append(" :usuarioId, ")
					.append(" :produtoId, ")
					.append(" :produtoNovoDesc, ")
					.append(" :codBarra, ")
					.append(" :qtdUn, ")
					.append(" :qtdEmb, ")
					.append(" :qtdEmbFechVenda, ")
					.append(" :descEmbFechVenda, ")
					.append(" :dtLeitura, ")
					.append(" CURRENT_TIMESTAMP, ")
					.append(" :EXCLUIDO, ")
					.append(" :FLAGZERAR ")
					.append(" ); ");
			
			System.out.println(insert.toString());
		    Query query = session.createSQLQuery(insert.toString());
		    
		    query.setParameter("chave", espelho.getChave());
		    query.setParameter("coletorInvId", espelho.getColetorInvId());
		    query.setParameter("usuarioId", espelho.getUsuarioId());
		    query.setParameter("produtoId", espelho.getProdutoId());
		    query.setParameter("produtoNovoDesc", espelho.getProdutoNovoDesc());
		    query.setParameter("codBarra", espelho.getCodBarra());
		    query.setParameter("qtdUn", espelho.getQtdUn());
		    query.setParameter("qtdEmb", espelho.getQtdEmb());
		    query.setParameter("qtdEmbFechVenda", espelho.getQtdEmbFechVenda());
		    query.setParameter("descEmbFechVenda", espelho.getDescEmbFechVenda());
		    query.setParameter("dtLeitura", UtilData.formatarStringParaData(espelho.getDtLeitura(), UtilData.FORMATO_DATA_HORA));
		    query.setParameter("EXCLUIDO", 0);
		    
		    if(espelho.getFlagZerar()!=null) {
		    	query.setParameter("FLAGZERAR", espelho.getFlagZerar());
		    }else {
		    	query.setParameter("FLAGZERAR", 0);
		    }
		    
		    
		    query.executeUpdate();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format(" Erro ao tentar processar contagem com chave %s", espelho.getChave()));
		}
		
	} 

}
