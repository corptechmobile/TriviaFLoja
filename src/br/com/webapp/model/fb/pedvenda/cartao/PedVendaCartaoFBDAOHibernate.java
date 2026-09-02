package br.com.webapp.model.fb.pedvenda.cartao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class PedVendaCartaoFBDAOHibernate implements PedVendaCartaoFBDAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PedVendaCartaoFB> listar(Integer pedVendaId) {
		//StringBuilder sql = new StringBuilder();
		/*
		sql.append(" SELECT CC.nomeadministradora as nomeAdministradora, " + 
						  " CC.valor as valor, " + 
						  " CC.qtdparcela as qtdParcela " + 
						" FROM CARTAO CC, " + 
							 " ECF_VENDAS ecf, " + 
							 " ecf_vendasformarec EFR, " + 
							 " PEDVENDA PV " + 
						" WHERE CC.id_ecfvendasformarec=EFR.id_ecfvendasformarec " + 
						  " AND EFR.id_ecfvendas = ECF.id_ecfvendas " + 
						  " AND ECF.id_ecfvendas_pdv = PV.id_ecfvenda " + 
						  " AND ECF.id_ecfpontovenda_pdv = PV.id_ecfpontovenda " + 
						  " AND pv.id_pedvenda = :pedVendaId ");
		*/
		
		/*
		sql.append("SELECT tab.descricao AS nomeAdministradora, " + 
				   		 " (COALESCE(SUM(tab.valor), 0) - COALESCE(SUM(tab.troco), 0)) AS valor, " +
				   		 " tab.parcela AS qtdParcela " +
				   	 " FROM ( " + 
					   " SELECT CASE ecffr.id_formapagtorec WHEN 2 THEN COALESCE(MAX(cc.nomeadministradora), 'NFC-e SEM REG.PAGTO') ELSE MAX(fpr.descricao) END AS descricao, " + 
						   	  " CASE SUM(ecffr.valor) WHEN 0 THEN MAX(ecf.valorvenda) ELSE SUM(ecffr.valor) END AS valor, " +
						   	  " 0 AS troco, " +
						   	  " cc.qtdparcela AS parcela " +
						   	 "FROM ecf_vendas ecf, " + 
								" formapagtorec fpr, " +
								" pedvenda pv, " +
								" ecf_vendasformarec ecffr left join cartao cc on (cc.id_ecfvendasformarec=ecffr.id_ecfvendasformarec) " + 
							"WHERE ecf.id_ecfvendas = ecffr.id_ecfvendas " + 
								" AND ecffr.id_formapagtorec = fpr.id_formapagtorec " +
								" AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
								" AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
								" AND pv.id_pedvenda = :pedVendaId " + 
								" AND ecf.concluida = 1 " + 
								" AND ecf.cancelada = 0 " + 
							" GROUP BY ecf.id_ecfvendas, ecffr.id_formapagtorec, cc.qtdparcela "+
					" union all " +
						" SELECT 'Dinheiro' AS descricao, " + 
							  " 0 AS valor, " +
							  " ecf.valortroco AS troco, " +
							  " null AS parcela " +
							" FROM ecf_vendas ecf, pedvenda pv " + 
							" WHERE ecf.concluida = 1 " + 
							  " AND ecf.cancelada = 0 " + 
							  " AND ecf.id_ecfvendas_pdv = pv.id_ecfvenda " +
						  	  " AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda " +
						  	  " AND ecf.valortroco > 0 " +
							  " AND pv.id_pedvenda = :pedVendaId " +
				  " union all " +  	  
						" SELECT MAX(TC.descricao) AS descricao, " +
							   " SUM(CASE tmf.CLASSE WHEN 0 THEN CAST(TRUNC((nfvi.QUANTIDADE * nfvi.PRECO),2) AS numeric(18,4)) ELSE 0 END) AS valor, " + 
							   " null AS troco, " +
							   " null AS parcela " +
						  " FROM NFVENDA nfv, " +
						  	   " NFVENDAITEM nfvi,  " +
						  	   " tipocobr TC, " + 
						  	   " TIPOMOVFISC tmf " +
						  " WHERE nfv.ID_NFVENDA = nfvi.ID_NFVENDA " + 
						    " AND nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC " + 
						    " AND TC.id_tipocobr = NFV.id_tipocobr " +
						    " AND nfv.CANCELADA = 0 " +
						    " AND tmf.CLASSE = 0 " +
						    " AND nfv.TIPO = 'S' " +
						    " AND nfv.id_pedvenda = :pedVendaId " +
						  " GROUP BY NFV.id_tipocobr " + 
					") tab GROUP BY tab.descricao, tab.parcela ");
		*/
		
		String sql	=	" SELECT "+
							  " t.nomeAdministradora, "+
							  " (COALESCE(SUM(t.valor), 0) - COALESCE(SUM(t.troco), 0)) AS valor, "+
							  " t.qtdParcela "+
						" FROM "+
						   " ( "+
						      "  SELECT "+
						      "      CASE "+
						      "          WHEN ecffr.id_formapagtorec = 2 THEN COALESCE(MAX(cc.nomeadministradora), 'NFC-e SEM REG.PAGTO') "+
						      "          ELSE MAX(fpr.descricao) "+
						      "      END AS nomeAdministradora, "+
						      "      CASE "+
						      "          WHEN SUM(ecffr.valor) = 0 THEN MAX(ecf.valorvenda) "+
						      "          ELSE SUM(ecffr.valor) "+
						      "      END AS valor, "+
						      "      0 AS troco, "+
						      "      cc.qtdparcela AS qtdParcela "+
						      "  FROM "+
						      "      ecf_vendas ecf "+
						      "  INNER JOIN "+
						      "      ecf_vendasformarec ecffr ON ecf.id_ecfvendas = ecffr.id_ecfvendas "+
						      "  INNER JOIN "+
						      "      formapagtorec fpr ON ecffr.id_formapagtorec = fpr.id_formapagtorec "+
						      "  INNER JOIN "+
						      "      pedvenda pv ON ecf.id_ecfvendas_pdv = pv.id_ecfvenda "+
						      "                  AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda "+
						      "  LEFT JOIN "+
						      "      cartao cc ON ecffr.id_ecfvendasformarec = cc.id_ecfvendasformarec "+
						      "  WHERE pv.id_pedvenda = :pedVendaId "+
						      "    AND ecf.concluida = 1 "+
						      "    AND ecf.cancelada = 0 "+
						      "  GROUP BY "+
						      "      ecf.id_ecfvendas, "+
						      "      ecffr.id_formapagtorec, "+
						      "      cc.qtdparcela "+
						      "  UNION ALL "+
						      "  SELECT "+
						      "      'Dinheiro' AS nomeAdministradora, "+
						      "      0 AS valor, "+
						      "      ecf.valortroco AS troco, "+
						      "      NULL AS qtdParcela "+
						      "  FROM "+
						      "      ecf_vendas ecf "+
						      "  INNER JOIN "+
						      "      pedvenda pv ON ecf.id_ecfvendas_pdv = pv.id_ecfvenda "+
						      "                  AND ecf.id_ecfpontovenda_pdv = pv.id_ecfpontovenda "+
						      "  WHERE ecf.concluida = 1 "+
						      "    AND ecf.cancelada = 0 "+
						      "    AND ecf.valortroco > 0 "+
						      "    AND pv.id_pedvenda = :pedVendaId "+
						      "  UNION ALL "+
						      "  SELECT "+
						      "      MAX(tc.descricao) AS nomeAdministradora, "+
						      "      SUM(CASE tmf.CLASSE WHEN 0 THEN CAST((nfvi.QUANTIDADE * nfvi.PRECO) AS NUMERIC(18, 4)) ELSE 0 END) AS valor, "+
						      "      NULL AS troco, "+
						      "      NULL AS qtdParcela "+
						      "  FROM "+
						      "      NFVENDA nfv "+
						      "  INNER JOIN "+
						      "      NFVENDAITEM nfvi ON nfv.ID_NFVENDA = nfvi.ID_NFVENDA "+
						      "  INNER JOIN "+
						      "      TIPOMOVFISC tmf ON nfv.ID_TIPOMOVFISC = tmf.ID_TIPOMOVFISC "+
						      "  INNER JOIN "+
						      "      tipocobr tc ON nfv.id_tipocobr = tc.id_tipocobr "+
						      "  WHERE nfv.CANCELADA = 0 "+
						      "    AND tmf.CLASSE = 0 "+
						      "    AND nfv.TIPO = 'S' "+
						      "    AND nfv.id_pedvenda = :pedVendaId "+
						      "  GROUP BY "+
						      "      nfv.id_tipocobr "+ 
						   " ) AS t "+
						" GROUP BY "+
						   " t.nomeAdministradora, "+
						   " t.qtdParcela "+
						" ORDER BY "+
						   " t.nomeAdministradora ";
		
		Query query = (Query) this.session.createSQLQuery(sql)
				.addScalar("nomeAdministradora", Hibernate.STRING)
				.addScalar("valor", Hibernate.DOUBLE)
				.addScalar("qtdParcela", Hibernate.INTEGER)
				.setParameter("pedVendaId", pedVendaId)
				.setResultTransformer(Transformers.aliasToBean(PedVendaCartaoFB.class));
		
		return query.list();
	}

}
