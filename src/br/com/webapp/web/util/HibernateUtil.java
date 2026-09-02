package br.com.webapp.web.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory	sessionFactoryFirebird	= buildSessionFactoryFirebird();
	private static final SessionFactory	sessionFactoryPostGres	= buildSessionFactoryPostGres();
	

	private static SessionFactory buildSessionFactoryFirebird() {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.out.println("Criacao inicial do objeto SessionFactory do firebird falhou. Erro: " + e);
			throw new ExceptionInInitializerError(e);
		}
	} 
	
	private static SessionFactory buildSessionFactoryPostGres() {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.postgres.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.out.println("Criacao inicial do objeto SessionFactory do postgres falhou. Erro: " + e);
			throw new ExceptionInInitializerError(e);
		}
	} 

	public static SessionFactory getSessionFactoryFirebird() {
		return sessionFactoryFirebird;
	}
	
	public static SessionFactory getSessionfactorypostgres() {
		return sessionFactoryPostGres;
	}
}
