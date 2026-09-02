package teste;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.webapp.model.fb.gestaovenda.GestaoVendaFB;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class GestaoVendaTeste {

	public static void main(String[] args) {
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		GestaoVendaFBRN gestaoVendaFBRN = new GestaoVendaFBRN();
    		
    		List<GestaoVendaFB> lista = new ArrayList<GestaoVendaFB>();
    		lista = gestaoVendaFBRN.listar();
    		
    		System.out.println(lista.size());
//    		System.out.println("gestaoVendaFB[getId]: " + gestaoVendaFB.getId());
//    		System.out.println("gestaoVendaFB[getNome]: " + gestaoVendaFB.getNome());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}
}
