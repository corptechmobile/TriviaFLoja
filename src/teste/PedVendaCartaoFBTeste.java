package teste;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFB;
import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFBRN;
import br.com.webapp.web.util.HibernateUtil;

public class PedVendaCartaoFBTeste {

	public static void main(String[] args) {
		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date yourDate = null;
			Date currentDate = new Date();
			yourDate = sdf.parse("10-10-2018");
    		
			PedVendaCartaoFBRN pedVendaCartaoFBRN = new PedVendaCartaoFBRN();
			
			
//    		List<PedVendaCartaoFB> lista = pedVendaCartaoFBRN.listar(yourDate, currentDate, 489);
//    		
//    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			String json = gson.toJson(lista);
//			System.out.println(json);
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
