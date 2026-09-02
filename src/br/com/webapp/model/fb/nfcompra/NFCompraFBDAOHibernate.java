package br.com.webapp.model.fb.nfcompra;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.web.util.DAOException;

public class NFCompraFBDAOHibernate implements NFCompraFBDAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	public List<NFCompraFB> listar(Date data1, Date data2) {
		
		String sql = "select a.id_nfcompra AS id, "
						 + " a.id_pessoa_forn AS fornecedor, "
						 + " a.id_pessoa_emp AS empresa, "
						 + " a.chaveacessonfe AS chaveAcessoNfe, "
						 + " a.numnf AS numNf, "
						 + " a.serienf AS serieNf, "
						 + " a.valortotalnf AS valorTotalNf, "
						 + " a.dataemissao AS dtEmissao, "
						 + " a.dataentrada AS dtEntrada, "
						 + " b.descricao AS descricaoStatus, "
						 + " a.seq_nfcompra AS version "
				+ " FROM nfcompra a, nfcomprastatus b "
				+ " WHERE a.dataentrada BETWEEN :data1 AND :data2 "
				  + " AND (a.chaveacessonfe is not null AND trim(a.chaveacessonfe) != '') "
				  + " AND a.id_nfcomprastatus = b.id_nfcomprastatus ";
		
		Query q = this.session.createSQLQuery(sql)
						.addScalar("id")
						.addScalar("fornecedor")
						.addScalar("empresa")
						.addScalar("chaveAcessoNfe")
						.addScalar("numNf")
						.addScalar("serieNf")
						.addScalar("valorTotalNf", Hibernate.DOUBLE)
						.addScalar("dtEmissao", Hibernate.DATE)
						.addScalar("dtEntrada", Hibernate.DATE)
						.addScalar("descricaoStatus")
						.addScalar("version")
						.setResultTransformer(Transformers.aliasToBean(NFCompraFB.class));
		
		q.setParameter("data1", data1);
		q.setParameter("data2", data2);
		
		return q.list();
		
	}

	@Override
	public List<NFCompraItemFB> listar(NFCompraFB nfCompra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<NFCompraFB> listar(Integer fornecedorId, Integer empresaId) {
		String sql = " SELECT a.id_nfcompra AS id,  "+
					        " a.id_pessoa_forn AS fornecedorId,  "+
					        " a.id_pessoa_emp AS empresaId,  "+
					        " max (f.RAZAOSOCIALNOME) AS fornecedorDesc, " +
						    " max(a.chaveacessonfe) AS chaveAcessoNfe,  "+
						    " max(a.numnf) AS numNf,  "+
						    " max(a.serienf) AS serieNf,  "+
						    " max(a.valortotalnf) AS valorTotalNf,  "+
						    " max(a.dataemissao) AS dtEmissao,  "+
						    " max(a.dataentrada) AS dtEntrada,  "+
						    " max(a.DATACADASTRO) AS dtcreate, " + 
						    " max(b.DESCRICAO) AS statusDesc, "+
						    " sum(i.QUANTIDADE) AS volume "+
					  " FROM nfcompra a,  "+
					  	   " pessoa f, "+ 
					  	   " nfcomprastatus b, "+
					  	   " nfcompraitem i "+
			         " WHERE a.id_nfcomprastatus = b.id_nfcomprastatus "+
				       " AND f.ID_PESSOA = a.ID_PESSOA_FORN "+
			           " AND a.ID_NFCOMPRA = i.ID_NFCOMPRA "+
					   " AND (a.chaveacessonfe IS NOT NULL "+
					   " AND trim(a.chaveacessonfe) != '') "+
					   " AND a.id_pessoa_forn = :fornecedorId "+
					   " AND a.ID_PESSOA_EMP = :empresaId "+
					   " AND b.ID_NFCOMPRASTATUS IN (:statusNFDigitada, :statusNFLiberada) "+
					   " AND not exists (select id_nfcompra from coletor_pc_nfcompra cpn, coletor_pc cp "+
					                    " where cp.ID_CPC = cpn.ID_CPC "+
					                    "   and cpn.id_nfcompra = a.id_nfcompra "+
					                    "   AND cp.STATUS <> :statusColetorExcluido) "+
					 " GROUP BY a.ID_NFCOMPRA , "+
							  " a.ID_PESSOA_FORN , "+
							  " a.ID_PESSOA_EMP   ";
		
		Query q = this.session.createSQLQuery(sql)
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("fornecedorId",Hibernate.INTEGER)
						.addScalar("fornecedorDesc", Hibernate.STRING)
						.addScalar("empresaId", Hibernate.INTEGER)
						.addScalar("chaveAcessoNfe", Hibernate.STRING)
						.addScalar("numNf", Hibernate.STRING)
						.addScalar("serieNf", Hibernate.STRING)
						.addScalar("valorTotalNf", Hibernate.DOUBLE)
						.addScalar("dtEmissao", Hibernate.DATE)
						.addScalar("dtEntrada", Hibernate.DATE)
						.addScalar("statusDesc", Hibernate.STRING)
						.addScalar("volume", Hibernate.DOUBLE)
						.setResultTransformer(Transformers.aliasToBean(NFCompraFB.class));
		q.setParameter("fornecedorId", fornecedorId);
		q.setParameter("empresaId", empresaId);
		q.setParameter("statusNFDigitada", NFCompraFB.STATUS_DIGITADA);
		q.setParameter("statusNFLiberada", NFCompraFB.STATUS_LIBERADA);
		q.setParameter("statusColetorExcluido", ColetorPCFB.STATUS_EXCLUIDO);
		
		
		return q.list();
	}

	@Override
	public List<NFCompraFB> listarPorPlanilhaCegaEFornecedor(ColetorPCFB coletorPCFB) {
			
		String sql = " SELECT a.id_nfcompra AS id,  "+
						        " a.id_pessoa_forn AS fornecedorId,  "+
						        " a.id_pessoa_emp AS empresaId,  "+
						        " max (f.RAZAOSOCIALNOME) AS fornecedorDesc, " +
							    " max(a.chaveacessonfe) AS chaveAcessoNfe,  "+
							    " max(a.numnf) AS numNf,  "+
							    " max(a.serienf) AS serieNf,  "+
							    " max(a.valortotalnf) AS valorTotalNf,  "+
							    " max(a.dataemissao) AS dtEmissao,  "+
							    " max(a.dataentrada) AS dtEntrada,  "+
							    " max(a.DATACADASTRO) AS dtcreate, " + 
							    " max(b.DESCRICAO) AS statusDesc, "+
							    " sum(i.QUANTIDADE) AS volume, "+
							    " count(i.id_nfcompraitem) AS itens "+
						  " FROM nfcompra a,  "+
						  	   " pessoa f, " + 
						  	   " nfcomprastatus b, "+
						  	   " nfcompraitem i, "+
						  	   " coletor_pc_nfcompra cnf "+
					     " WHERE a.id_nfcomprastatus = b.id_nfcomprastatus "+
					       " AND f.ID_PESSOA = a.ID_PESSOA_FORN "+
					       " AND a.ID_NFCOMPRA = i.ID_NFCOMPRA "+
						   " AND (a.chaveacessonfe IS NOT NULL "+
						   " AND trim(a.chaveacessonfe) != '') "+
						   " AND a.id_nfcompra = cnf.id_nfcompra "+
						   " AND cnf.id_cpc = :coletorPCFBId  "+
						 " GROUP BY a.ID_NFCOMPRA , "+
								  " a.ID_PESSOA_FORN , "+
								  " a.ID_PESSOA_EMP   ";
		
			Query q = this.session.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("fornecedorId",Hibernate.INTEGER)
					.addScalar("fornecedorDesc", Hibernate.STRING)
					.addScalar("empresaId", Hibernate.INTEGER)
					.addScalar("chaveAcessoNfe", Hibernate.STRING)
					.addScalar("numNf", Hibernate.STRING)
					.addScalar("serieNf", Hibernate.STRING)
					.addScalar("valorTotalNf", Hibernate.DOUBLE)
					.addScalar("dtEmissao", Hibernate.DATE)
					.addScalar("dtEntrada", Hibernate.DATE)
					.addScalar("statusDesc", Hibernate.STRING)
					.addScalar("volume", Hibernate.DOUBLE)
					.addScalar("itens", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(NFCompraFB.class));
			
			q.setParameter("coletorPCFBId", coletorPCFB.getId());
		
	return q.list();
	}
	@Override
	public List<NFCompraFB> listarPorPlanilhaCegaId(Integer coletorPCFBId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NFCompraFB salvar(NFCompraFB nfCompra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<NFCompraFBRN> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarPlanilhaCega(Integer planilhaCegaId, Integer planilhaCegaIdErp) throws DAOException {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE NFCOMPRA set ID_PLANILHACEGA = :planilhaCegaIdErp ")
			   .append(" WHERE ID_NFCOMPRA IN (SELECT ID_NFCOMPRA FROM COLETOR_PC_NFCOMPRA WHERE ID_CPC = :planilhaCegaId) ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("planilhaCegaId", planilhaCegaId);
			query.setParameter("planilhaCegaIdErp", planilhaCegaIdErp);
			
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}	
}
