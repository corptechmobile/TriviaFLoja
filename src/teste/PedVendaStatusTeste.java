package teste;

import org.hibernate.Session;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFBRN;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class PedVendaStatusTeste {

	public static void main(String[] args) {
Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		PedVendaStatusFBRN pedVendaFBRN = new PedVendaStatusFBRN();
    		PedVendaStatusFB pedVendaFB = pedVendaFBRN.carregar(2);
    		
    		System.out.println("pedVendaFB[getId]: " + pedVendaFB.getId());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}

	}

}
