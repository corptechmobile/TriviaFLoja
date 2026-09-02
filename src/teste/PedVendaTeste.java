package teste;

import org.hibernate.Session;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class PedVendaTeste {

	public static void main(String[] args) {
			
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
    		PedVendaFB pedVendaFB = pedVendaFBRN.carregar(18);
    		
    		
    		System.out.println("pedVendaFB[getId]: " + pedVendaFB.getId());
    		System.out.println("pedVendaFB[getClienteId]: " + pedVendaFB.getClienteId());
    		System.out.println("pedVendaFB[getVendedorId]: " + pedVendaFB.getVendedorId());
    		System.out.println("pedVendaFB[get]" + pedVendaFB.getEfetivacao());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
