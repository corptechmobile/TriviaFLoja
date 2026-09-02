package teste;

import org.hibernate.Session;

import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class ProdLinhaTeste {

	public static void main(String[] args) {
		
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		ProdutoLinhaFBRN prodLinhaFBRN = new ProdutoLinhaFBRN();
    		ProdutoLinhaFB prodLinhaFB = prodLinhaFBRN.carregar(12);
    		
    		
    		System.out.println("prodLinhaFB[getId]: " + prodLinhaFB.getId());
    		System.out.println("prodLinhaFB[getDescricao]: " + prodLinhaFB.getDescricao());
    		System.out.println("prodLinhaFB[getCodEDT]" + prodLinhaFB.getCodEDT());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
