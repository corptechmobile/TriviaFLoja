package br.com.webapp.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;

import br.com.webapp.web.util.HibernateUtil;

public class ConexaoHibernateFilter implements Filter {

	/**
	 * @uml.property  name="sf"
	 * @uml.associationEnd  
	 */
	private SessionFactory	sfFireBird;
	private SessionFactory	sfPostGres;

	public void init(FilterConfig config) throws ServletException {
		this.sfFireBird = HibernateUtil.getSessionFactoryFirebird();
		this.sfPostGres = HibernateUtil.getSessionfactorypostgres();
	}

	public void destroy() { }

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException {

		try {

			this.sfFireBird.getCurrentSession().beginTransaction();
			this.sfPostGres.getCurrentSession().beginTransaction();

			chain.doFilter(servletRequest, servletResponse);
			
			if (this.sfFireBird.getCurrentSession().getTransaction().isActive()) {
				this.sfFireBird.getCurrentSession().getTransaction().commit();
			}
			if (this.sfPostGres.getCurrentSession().getTransaction().isActive()) {
				this.sfPostGres.getCurrentSession().getTransaction().commit();
			}

		} catch (Throwable ex) {
			try {
				if (this.sfFireBird.getCurrentSession().getTransaction().isActive()) {
					this.sfFireBird.getCurrentSession().getTransaction().rollback();
				}
				if (this.sfPostGres.getCurrentSession().getTransaction().isActive()) {
					this.sfPostGres.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			ex.printStackTrace();
			//throw new ServletException(ex);
		}
		
		finally{
			try {
				this.sfFireBird.getCurrentSession().close();
				this.sfPostGres.getCurrentSession().close();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

}