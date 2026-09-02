package teste;

import org.hibernate.Session;

import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class PedVendaItemTeste {

	public static void main(String[] args) {
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		PedVendaItemFBRN pedVendaFBRN = new PedVendaItemFBRN();
    		PedVendaItemFB pedVendaFB = pedVendaFBRN.carregar(2);
    		
    		
    		System.out.println("pedVendaFB[getId]: " + pedVendaFB.getId());
    		System.out.println("pedVendaFB[getClienteId]: " + pedVendaFB.getQuantidade());
    		System.out.println("pedVendaFB[getVendedorId]: " + pedVendaFB.getPrecoTabela());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
