
package br.com.webapp.model.fb.coletor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.coletor.model.ColetorInv;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;

public class ColetorInvFBDAOHibernate implements ColetorInvFBDAO{

	private StringBuilder COLLUMNS;
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public ColetorInvFBDAOHibernate() {
		COLLUMNS = new StringBuilder();
		COLLUMNS.append(" a.ID_COLETOR_INV as id, ")
				.append(" a.DESCRICAO as descricao, ")
				.append(" a.ID_PESSOA_EMP as empresaId, ")
				.append(" p.nomefantmnem as empresaDesc, ")
				.append(" a.ID_USUARIO as usuarioId, ")
				.append(" u.nome as usuarioDesc, ")
				.append(" a.DTINICIO as dtInicio, ")
				.append(" a.DTTERMINO as dtTermino, ")
				.append(" a.DTCRIACAO as dtCriacao, ")				
				.append(" a.STATUS ");
	}
	
	@Override
	public ColetorInvFB carregar(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM coletor_inv a, ")
		   .append("      pessoa p, ")
		   .append("      usuario u ")
		   .append(" WHERE a.ID_PESSOA_EMP = P.ID_PESSOA ")
		   .append("   AND a.ID_USUARIO = u.ID_USUARIO ")
		   .append("   AND a.ID_COLETOR_INV = :id ")
		   .append(" ORDER BY a.DTCRIACAO desc");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvFB.class));
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return (ColetorInvFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvFB> listar() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM coletor_inv a, ")
		   .append("      pessoa p, ")
		   .append("      usuario u ")
		   .append(" WHERE a.ID_PESSOA_EMP = P.ID_PESSOA ")
		   .append("   AND a.ID_USUARIO = u.ID_USUARIO ")
		   .append(" ORDER BY a.DTCRIACAO desc");
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvFB.class));
		return query.list();
	}

	@Override
	public ColetorInvFB salvar(ColetorInvFB inventario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insert(ColetorInvFB inventario) throws DAOException {
		try {
			
			Integer inventarioId = getSeq();
			System.out.println("[ColetorInvFBDAOHibernate][insert][id]" + inventarioId);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO COLETOR_INV (ID_COLETOR_INV, ID_PESSOA_EMP, ID_USUARIO, DESCRICAO, DTCRIACAO, STATUS) ")
			.append("VALUES (:ID_COLETOR_INV, ")
			        .append(":ID_PESSOA_EMP, ")
			        .append(":ID_USUARIO, ")
			        .append(":DESCRICAO, ")
			        .append(":DTCRIACAO, ")
			        .append(":STATUS) ");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventarioId);
			query.setParameter("ID_PESSOA_EMP", inventario.getEmpresaId());
	        query.setParameter("ID_USUARIO", inventario.getUsuarioId());
	        query.setParameter("DESCRICAO", inventario.getDescricao());
	        query.setParameter("DTCRIACAO", inventario.getDtCriacao());
	        query.setParameter("STATUS", inventario.getStatus());
	        
			query.executeUpdate();
	        
			return inventarioId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
			
	}
	
	private Integer getSeq() throws DAOException {
		try {
			String sql = "select gen_id(GEN_COLETORINV_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do ColetorInvFB.");
		}	
	}

	private Integer getSeqInventario() throws DAOException {
		try {
			String sql = "select gen_id(GEN_INVENTARIO_ID, 1) from rdb$database;";
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Erro ao gerar Sequence do Inventario.");
		}	}
	
	
	@Override
	public void update(ColetorInvFB inventario) throws DAOException {
		try {
			
			System.out.println("[PedVendaFBDAOHibernate][update][id]" + inventario.getId());
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_INV SET ")
					        .append("DESCRICAO = :DESCRICAO, ")
					        .append("STATUS = :STATUS, ")
					        .append("DTINICIO = :DTINICIO, ")
					        .append("DTTERMINO = :DTTERMINO ")
					      .append(" WHERE ID_COLETOR_INV = :ID_COLETOR_INV");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventario.getId());
			query.setParameter("DESCRICAO", inventario.getDescricao());
			query.setParameter("STATUS", inventario.getStatus());
	        query.setParameter("DTINICIO", inventario.getDtInicio());
	        query.setParameter("DTTERMINO", inventario.getDtTermino());
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void excluir(Integer inventarioId) throws DAOException {
		try {
			
			System.out.println("[ColetorInvFBDAOHibernate][excluir][id]" + inventarioId);
			
			String sql = "UPDATE COLETOR_INV SET STATUS = :status WHERE ID_COLETOR_INV = :ID_COLETOR_INV";
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", inventarioId);
			query.setParameter("status", ColetorInvFB.STATUS_EXCLUIDO);
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}

		
	}

	@Override
	public ColetorInvFB verificarInvAbertoEmpresa(Integer empresaId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(COLLUMNS.toString())
		   .append(" FROM coletor_inv a, ")
		   .append("      pessoa p, ")
		   .append("      usuario u ")
		   .append(" WHERE a.ID_PESSOA_EMP = P.ID_PESSOA ")
		   .append("   AND a.ID_USUARIO = u.ID_USUARIO ")
		   .append("   AND a.ID_PESSOA_EMP = :id ")
		   .append("   AND a.status not in (:statusFinalizado, :statusExcluido) ")
		   .append(" ORDER BY a.DTCRIACAO desc");
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvFB.class));
		query.setParameter("id", empresaId);
		query.setParameter("statusFinalizado", ColetorInvFB.STATUS_FINALIZADO);
		query.setParameter("statusExcluido", ColetorInvFB.STATUS_EXCLUIDO);
		query.setMaxResults(1);
		
		return (ColetorInvFB) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColetorInvFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter) {
		String sql = "";
		String varWhere = "";
		
		if(empresaFilter!=null) {
			varWhere += " AND a.ID_PESSOA_EMP = :empresaId ";
		}
		
		if(data1Filter != null && data2Filter != null) {
			varWhere += " AND a.DTCRIACAO BETWEEN :dt1 AND :dt2 ";
		}

		if(concluidoFilter) {
			varWhere += " AND a.STATUS = :status ";
		}else {
			varWhere += " AND a.STATUS not in (:status,:statusExcluido) ";
		}

		sql = " SELECT "+
				" "+COLLUMNS.toString()+" "+	
			   "  FROM coletor_inv a, "+
			   "       pessoa p, "+
			   "       usuario u "+
			   " WHERE a.ID_PESSOA_EMP = P.ID_PESSOA "+
			   "   AND a.ID_USUARIO = u.ID_USUARIO "+
			   " "+varWhere+" "+
			   " ORDER BY a.DTCRIACAO desc";
		
		Query query = (Query) session.createSQLQuery(sql.toString())
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("empresaId", Hibernate.INTEGER)
				.addScalar("empresaDesc", Hibernate.STRING)
				.addScalar("descricao", Hibernate.STRING)
				.addScalar("usuarioId", Hibernate.INTEGER)
				.addScalar("usuarioDesc", Hibernate.STRING)
				.addScalar("dtInicio", Hibernate.TIMESTAMP)
				.addScalar("dtTermino", Hibernate.TIMESTAMP)
				.addScalar("dtCriacao", Hibernate.TIMESTAMP)
				.addScalar("status", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(ColetorInvFB.class));
		
		
		if(concluidoFilter) {
			query.setParameter("status", ColetorInvFB.STATUS_FINALIZADO);
		}else {	
			query.setParameter("statusExcluido", ColetorInvFB.STATUS_EXCLUIDO);
			query.setParameter("status", ColetorInvFB.STATUS_FINALIZADO);
		}	
		
		
		if(empresaFilter!=null) {
			query.setParameter("empresaId", empresaFilter.getId());
		}
		
		if(data1Filter != null && data2Filter != null) {
			query.setParameter("dt1", data1Filter);
			query.setParameter("dt2", data2Filter);
		}

		return query.list();
	}

	@Override
	public Integer criarInventario(ColetorInvFB inventario) throws DAOException {
		try {
			
			Integer inventarioId = getSeqInventario();
			System.out.println("[inventario][insert][id]" + inventarioId);
			
			String sql =    " INSERT INTO INVENTARIO   "+
							"     (ID_INVENTARIO,  "+
							"      ID_PESSOA_EMP,  "+
							"      MOMENTOINI,  "+
							"      MOMENTOFIM,  "+
							"      NUMCONTAGEM,  "+
							"      ID_USUARIO,  "+
							"      ORDERNACAO,  "+
							"      ESTQAJUSTADO,  "+
							"      MOMENTOBLOQMOVESTQ,  "+
							"      CONCLUIDO)  "+
							" SELECT "+inventarioId+", "+
							"        i.id_pessoa_emp, "+
							"        i.dtinicio, "+
							"        i.dttermino, "+
							"        1 as contagem, "+
							"        i.id_usuario, "+
							"        0 as ordenacao, "+
							"        0 as estqajustado, "+
							"        i.dtcriacao, "+
							"        1 as concluido "+
							"   FROM coletor_inv i "+
							"  WHERE i.id_coletor_inv = :coletorInvId "+
							"    AND i.status = 'F' ";
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("coletorInvId", inventario.getId());
	        
			query.executeUpdate();
	        
			return inventarioId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void incluirItensInventario(Integer inventarioId, Integer coletorInvId) throws DAOException {
		try {
			
			String sql =    " INSERT INTO INVITEM  "+
							"     (ID_INVITEM,  "+
							"      ID_INVENTARIO,  "+
							"      ID_PRODUTO,  "+
							"      ID_LOCALIDADE,  "+
							"      QTDINV,  "+
							"      CONCLUIDO )  "+
							" SELECT Gen_id(gen_invitem_id, 1), "+
							"        "+inventarioId+", "+
							"        i.id_produto, "+
							"		 max(coalesce((SELECT e.ID_LOCALIDADE "+
					        "                        FROM EMPRESALOCALPRODUTO e "+
					        "                       WHERE e.id_pessoa_emp = iv.id_pessoa_emp "+
					        "                         and e.id_produto = i.id_produto), "+
					        "                       (SELECT emp.id_localidade_padrao "+
					        "                          FROM empresa emp "+
					        "                         WHERE emp.id_pessoa = iv.id_pessoa_emp))) as localidade, "+
							"        case sum(coalesce(p.fatorconvunidbasica,0)) when 0 then 0 else "+
							"             trunc(sum((coalesce(i.qtdun,0) + "+
							"                        coalesce(i.qtdemb,0) * "+
							"                        coalesce(i.qtdembfechvenda,0)) / "+
							"                        coalesce(p.fatorconvunidbasica,0))) end as qtdInv, "+
							"        1 as concluido "+
							"   FROM coletor_inv_contagem i, "+
							"        coletor_inv iv, "+
							"        produto p "+
							"  WHERE i.id_produto = p.id_produto "+
							"	 AND iv.ID_COLETOR_INV = i.ID_COLETOR_INV "+
							"    and i.id_coletor_inv = :coletorInvId "+
							"    and i.excluido = 0 "+
							" group by i.id_produto ";
					
			Query query = (Query) session.createSQLQuery(sql);
			query.setParameter("coletorInvId", coletorInvId);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

	private Integer getSeqInvItem() throws DAOException {
		try {
			String sql = "SELECT GEN_ID(GEN_INVITEM_ID, 1) FROM RDB$DATABASE"; 
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}	
	}

	@Override
	public void inserirPosicaoEstoque(Integer inventarioId, Integer coletorInvId) throws DAOException {
		try { 
			
			String sql =    " delete from posicaoestoque where momento = (select dtcriacao "+
							"												from coletor_inv "+
							"											   where id_coletor_inv = :coletorInvId) ";
			Query query = (Query) session.createSQLQuery(sql);
			query.setParameter("coletorInvId", coletorInvId);
			query.executeUpdate();
			
			
			String sql2 =    " insert into posicaoestoque (id_produto, id_localidade, tipo, momento, quantidade)  "+
							"   select i.id_produto,  "+
							"          i.id_localidade,  "+
							"          coalesce(pl.tipo,'F') as tipo,  "+
							"          iv.momentobloqmovestq,  "+
							"          coalesce(pl.quantidade,0) as quantidade  "+
							"      from inventario iv,  "+
							"           invitem i  "+
							"           LEFT JOIN produto_localidade pl ON (i.id_produto = pl.id_produto  "+
							"                                               and i.id_localidade = pl.id_localidade  "+
							"                                                  and pl.tipo = 'F')  "+
							"     where iv.id_inventario = i.id_inventario  "+
							"       and i.id_inventario = :inventarioId ";


					
			Query query2 = (Query) session.createSQLQuery(sql2);
			query2.setParameter("inventarioId", inventarioId);
			query2.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}		
	}

	@Override
	public void atualizarProdutoLocalidade(Integer inventarioId) throws DAOException {
		try { 
			
			String sql =    " update produto_localidade "+
							"   set procinv = 1  "+
							"   where id_produto in  "+
							"       (select pl2.id_produto  "+
							"          from produto_localidade pl2  "+
							"           inner join invitem i on (pl2.id_produto = i.id_produto and pl2.id_localidade = i.id_localidade)  "+
							"       where i.id_inventario = :inventarioId "+
							"         and pl2.tipo = 'F'  "+
							"       )  "+
							"   and id_localidade in (select pl2.id_localidade  "+
							"          from produto_localidade pl2  "+
							"           inner join invitem i on (pl2.id_produto = i.id_produto and pl2.id_localidade = i.id_localidade)  "+
							"       where i.id_inventario = :inventarioId "+
							"         and pl2.tipo = 'F')  "+
							"  and tipo = 'F';  ";


					
			Query query = (Query) session.createSQLQuery(sql);
			query.setParameter("inventarioId", inventarioId);
			query.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}	
		


		
	}

	@Override
	public void inserirContagens(Integer inventarioId) throws DAOException {
		try { 
			
			String sql =    " DELETE FROM invitemcontagem "+
			                "  WHERE id_invitem in (select id_invitem "+
			                "						  from invitem "+
			                "						 where id_inventario = :inventarioId); ";

			Query query = (Query) session.createSQLQuery(sql);
			query.setParameter("inventarioId", inventarioId);
			query.executeUpdate();

			String sql2 =	" INSERT INTO invitemcontagem  "+
							"               (id_invitemcontagem,  "+
							"                id_invitem,  "+
							"                id_pessoa_conf,  "+
							"                coleta,  "+
							"                contagem,  "+
							"                quantidade,  "+
							"                realizada,  "+
							"                ordem)  "+
							"   SELECT Gen_id(gen_invitemcontagem_id, 1), "+
							"          iv.id_invitem id_invitem, "+
							"          i.id_pessoa_emp as id_usuario, "+
							"          1, "+
							"          1,  "+
							"          iv.qtdinv as qtdinv, "+
							"          1,  "+
							"          iv.seq_invitem as seq_invitem "+
							"     FROM invitem iv, inventario i "+
							"    WHERE iv.id_inventario = i.id_inventario "+
							"      AND iv.id_inventario = :inventarioId ";

			Query query2 = (Query) session.createSQLQuery(sql2);
			query2.setParameter("inventarioId", inventarioId);
			query2.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}			
	}

	@Override
	public void finalizarInventario(Integer coletorInvId) throws DAOException {
		try {
			
			System.out.println("[ColetorInvFBDAOHibernate][finalizar][id]" + coletorInvId);
		
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COLETOR_INV SET ")
					        .append("STATUS = :STATUS, ")
					        .append("DTTERMINO = current_timestamp ")
					      .append(" WHERE ID_COLETOR_INV = :ID_COLETOR_INV");
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("ID_COLETOR_INV", coletorInvId);
			query.setParameter("STATUS", ColetorInv.STATUS_FINALIZADO);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}		
	}
	
	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}

	@Override
	public void atualizarEmbFechVenda(Integer coletorInvId) throws DAOException {
		try {
			
			
			String sql = " update produto pr "+
						" set pr.codbarra = (select max(c.codbarra) "+
						"                    from coletor_inv_contagem c, "+
						"                         produto p "+
						"                    where c.id_produto = p.id_produto "+
						"                      and c.id_produto = pr.id_produto "+
						"                      and c.id_coletor_inv = :coletorInvId "+
						"                      and c.excluido = 0 "+
						"                    group by c.id_produto), "+
						"     pr.qtdembfechvenda = (select max(c.qtdembfechvenda) "+
						"                    from coletor_inv_contagem c, "+
						"                         produto p "+
						"                    where c.id_produto = p.id_produto "+
						"                      and c.id_produto = pr.id_produto "+
						"                      and c.id_coletor_inv = :coletorInvId "+
						"                      and c.excluido = 0 "+
						"                    group by c.id_produto), "+
						"     pr.descembfechada = (select max(coalesce(c.descembfechvenda,'')) "+
						"                    from coletor_inv_contagem c, "+
						"                         produto p "+
						"                    where c.id_produto = p.id_produto "+
						"                      and c.id_produto = pr.id_produto "+
						"                      and c.id_coletor_inv = :coletorInvId "+
						"                      and c.excluido = 0 "+
						"                    group by c.id_produto) "+
						"  where pr.id_produto in (select c.id_produto "+
						" 			   				 from coletor_inv_contagem c, "+
						" 								  produto p "+
						" 			   	            where c.id_produto = p.id_produto "+
						" 			     			  and c.id_coletor_inv = :coletorInvId "+
						" 			    			  and c.excluido = 0 "+
						" 			  			    group by c.id_produto) ";
			
			Query query = (Query) session.createSQLQuery(sql.toString());
			query.setParameter("coletorInvId", coletorInvId);
	        
			query.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
	}

}
