package br.com.coletor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.coletor.model.ProdutoColetor;

public class DAOProdutoColetor {
	
	private Session session;

	public DAOProdutoColetor(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<ProdutoColetor> listar() {
		
		String sql = " select p.id_produto as id, "
				 		  + " l.descricao as linhaProduto, "
				 		  + " u.descresumida as unidade, "
						  + " p.codinterno, "
						  + " p.codbarra, "
						  + " p.codbarraDun14, "
						  + " p.descricao, "
						  + " p.qtdembfechvenda, "
						  + " p.descembfechada as descembfechvenda, "
						  + " p.controlalote as controlaLote, "
						  + " p.altura, "
						  + " p.largura, "
						  + " p.comprimento, "
						  + " p.pesobrutokg as pesoBrutoKg, "
						  + " p.pesoliquidokg as pesoLiquidoKg, "
						  + " p.mesapallet as mesaPallet, "
						  + " coalesce(p.alturapallet, 0) as alturaPallet, "
						  + " p.SHELFLIFE as shelfLife, "
						  + " p.QTDDECIMAL as qtdDecimal "
						+ " from produto p, linhaproduto l, unidade u "
						+ " where p.id_linhaproduto = l.id_linhaproduto "
						+ " and p.id_unidade_venda = u.id_unidade "
						+ " and p.ativo = :ativo "
						+ " order by p.descricao ";
		
		Query query = (Query) session.createSQLQuery(sql.toString()).addEntity(ProdutoColetor.class);
		query.setParameter("ativo", 1);
		
		return query.list();
	}

}
