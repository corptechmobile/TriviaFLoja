package br.com.webapp.web.util;

import br.com.webapp.model.configuracao.ConfiguracaoDAO;
import br.com.webapp.model.configuracao.ConfiguracaoDAOHibernate;
import br.com.webapp.model.fb.fornecedor.FornecedorFBDAO;
import br.com.webapp.model.fb.fornecedor.FornecedorFBDAOHibernate;
import br.com.webapp.model.menu.MenuAcessoDAO;
import br.com.webapp.model.menu.MenuAcessoDAOHibernate;
import br.com.webapp.model.menu.MenuFavoritoDAO;
import br.com.webapp.model.menu.MenuFavoritoDAOHibernate;
import br.com.webapp.model.menu.MenuHomeDAO;
import br.com.webapp.model.menu.MenuHomeDAOHibernate;
import br.com.webapp.model.menu.MenuRecenteDAO;
import br.com.webapp.model.menu.MenuRecenteDAOHibernate;
import br.com.webapp.model.usuario.UsuarioDAO;
import br.com.webapp.model.usuario.UsuarioDAOHibernate;
import br.com.webapp.model.usuariogrupo.UsuarioGrupoDAO;
import br.com.webapp.model.usuariogrupo.UsuarioGrupoDAOHibernate;


public class DAOFactoryPostGres {

	public static MenuAcessoDAO criarMenuAcessoDAO() {
		MenuAcessoDAOHibernate menuAcessoDAO = new MenuAcessoDAOHibernate();
		menuAcessoDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return menuAcessoDAO;
	}
	
	public static FornecedorFBDAO criarFornecedorDao() {
		FornecedorFBDAOHibernate fornecedorDao = new FornecedorFBDAOHibernate();
		fornecedorDao.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return fornecedorDao;
		
	}

	public static UsuarioGrupoDAO criarUsuarioGrupoDAO() {
		UsuarioGrupoDAOHibernate usuarioGrupoDAO = new UsuarioGrupoDAOHibernate();
		usuarioGrupoDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return usuarioGrupoDAO;
	}
	
	public static UsuarioDAO criarUsuarioDAO() {
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return usuarioDAO;
	}
	
	public static MenuHomeDAO criarMenuHomeDAO() {
		MenuHomeDAOHibernate menuHomeDAO = new MenuHomeDAOHibernate();
		menuHomeDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return menuHomeDAO;
	}

	public static MenuRecenteDAO criarMenuRecenteDAO() {
		MenuRecenteDAOHibernate menuRecenteDAO = new MenuRecenteDAOHibernate();
		menuRecenteDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return menuRecenteDAO;
	}

	public static MenuFavoritoDAO criarMenuFavoritoDAO() {
		MenuFavoritoDAOHibernate menuFavoritoDAO = new MenuFavoritoDAOHibernate();
		menuFavoritoDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return menuFavoritoDAO;
	}

	public static ConfiguracaoDAO criarConfiguracaoDAO() {
		ConfiguracaoDAOHibernate configuracaoDAO = new ConfiguracaoDAOHibernate();
		configuracaoDAO.setSession(HibernateUtil.getSessionfactorypostgres().getCurrentSession());
		return configuracaoDAO;
	}

}