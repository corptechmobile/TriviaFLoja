package br.com.webapp.model.fb.planilhacegafirebird;

import java.math.BigInteger;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.webapp.web.util.RNException;


public class PlanilhaCegaFirebirdDAOHibernate implements PlanilhaCegaFirebirdDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Integer gerarId() throws RNException {
		try {
			String sql = "SELECT GEN_ID(GEN_PLANILHACEGA_ID, 1) FROM RDB$DATABASE"; 
			Query q = (Query) session.createSQLQuery(sql);
			BigInteger key = (BigInteger) q.uniqueResult();
			return Integer.parseInt(key.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro ao gerar Sequence da Planilha Cega.");
		}
	}

	public Integer inserir(PlanilhaCegaFirebird planilhaCegaFirebird) throws RNException {
		
		try {
			
			String sql = "INSERT INTO PLANILHACEGA "+
						    " (ID_PLANILHACEGA, "+
						    " ID_PESSOA_CONF, "+
						    " ID_USUARIO, "+
						    " DATACONFERENCIA, "+
						    " CONFRONTADA, "+
						    " FINALIZADA, "+
						    " ID_USUARIO_GERADOR, "+
						    " MOMENTOGERACAO) "+
						  " VALUES ( "+
						  		" :ID_PLANILHACEGA, "+
						  		" :ID_PESSOA_CONF, "+  
						  		" :ID_USUARIO, "+  
						  		" :DATACONFERENCIA, "+
						  		" :CONFRONTADA, "+ 
						  		" :FINALIZADA, "+ 
						  		" :ID_USUARIO_GERADOR,  "+  
						  		" :MOMENTOGERACAO "+
						  	" )";
	
			Query q = this.session.createSQLQuery(sql);
			q.setParameter("ID_PLANILHACEGA", planilhaCegaFirebird.getId());
			q.setParameter("ID_PESSOA_CONF", planilhaCegaFirebird.getPessoaConf());
			q.setParameter("ID_USUARIO", planilhaCegaFirebird.getUsuario());
			q.setParameter("DATACONFERENCIA", planilhaCegaFirebird.getDataConferencia());
			q.setParameter("CONFRONTADA", planilhaCegaFirebird.getConfrontada());
			q.setParameter("FINALIZADA", planilhaCegaFirebird.getFinalizada());
			q.setParameter("ID_USUARIO_GERADOR", planilhaCegaFirebird.getUsuarioGerador());
			q.setParameter("MOMENTOGERACAO", planilhaCegaFirebird.getMomentoGeracao());
			q.executeUpdate();
			
			return planilhaCegaFirebird.getId();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException("Erro na inclusão da Planilha Cega no Trivia ERP.");
		}
		
	}
	
	@Override
	public PlanilhaCegaFirebird verificarReAberturaConferencia(Integer idErp) {
		String sql = " SELECT a.ID_PLANILHACEGA AS id, "+
					        " a.ID_PESSOA_CONF AS pessoaConf, "+
					        " a.ID_USUARIO AS usuario, "+
					        " a.DATACONFERENCIA AS dataConferencia, "+
					        " a.CONFRONTADA AS confrontada, "+
					        " a.FINALIZADA AS finalizada, "+
					        " a.ID_USUARIO_GERADOR AS usuarioGerador, "+
					        " a.MOMENTOGERACAO AS momentoGeracao "+
					     " FROM PLANILHACEGA a "+
					     " WHERE a.id_planilhacega = :idErp ";

		Query q = this.session.createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("pessoaConf", Hibernate.INTEGER)
					.addScalar("usuario", Hibernate.INTEGER)
					.addScalar("dataConferencia", Hibernate.STRING)
					.addScalar("confrontada", Hibernate.INTEGER)
					.addScalar("finalizada", Hibernate.INTEGER)
					.addScalar("usuarioGerador", Hibernate.INTEGER)
					.addScalar("momentoGeracao", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(PlanilhaCegaFirebird.class));
		
		q.setParameter("idErp", idErp);
		q.setMaxResults(1);
		
		return (PlanilhaCegaFirebird) q.uniqueResult();
	}

	@Override
	public void rollBack() {
		this.session.getTransaction().rollback();
	}
	
}