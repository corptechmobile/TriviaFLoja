package teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumo;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumoRN;
import br.com.webapp.web.util.HibernateUtil;

public class VendaFornResumoTeste {

public static void main(String[] args) {
		
		Session session = null;
		try {
			
			HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date yourDate = null;
			Date currentDate = new Date();
			yourDate = sdf.parse("01-08-2018");
			
			VendaFornResumoRN vendaFornResumoRN = new VendaFornResumoRN();
			EmpresaFB empresaFB = new EmpresaFB();
			empresaFB.setId(1);
			
			VendaFornResumo lista = vendaFornResumoRN.carregar(empresaFB, null, null, null, yourDate, currentDate, "notafiscal", null);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(lista);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
