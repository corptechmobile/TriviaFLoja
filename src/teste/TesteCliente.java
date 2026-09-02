package teste;

import org.hibernate.Session;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class TesteCliente {
	
	public static void main(String[] args) {
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		ClienteFBRN clienteFBRN = new ClienteFBRN();
    		ClienteFB clienteFB = clienteFBRN.carregar("04328069462");
    		
    		System.out.println("clienteFB[getId]: " + clienteFB.getId());
    		System.out.println("clienteFB[getRazaoSocial]: " + clienteFB.getRazaoSocial());
    		System.out.println("clienteFB[getNomeFantasia]: " + clienteFB.getNomeFantasia());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
