package br.com.webapp.model.configuracao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class ConfiguracaoDAOHibernate implements ConfiguracaoDAO {
	
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public Configuracao carregar(String nome) {
		return (Configuracao) this.session.get(Configuracao.class, nome);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Configuracao> listar() {
		Criteria criteria = this.session.createCriteria(Configuracao.class);
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}

}
