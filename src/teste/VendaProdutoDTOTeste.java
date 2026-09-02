package teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTORN;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTORN;
import br.com.webapp.web.util.HibernateUtil;

public class VendaProdutoDTOTeste {

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
			
			VendasProdutoDTORN vendasProdutoDTORN = new VendasProdutoDTORN();
			
			VendaFornDTORN vendaFornDTORN = new VendaFornDTORN();
			EmpresaFB empresaFB = new EmpresaFB();
			empresaFB.setId(1);
			
//			List<VendaFornDTO> lista = vendaFornDTORN.listarPedido(empresaFB, yourDate, currentDate);
			
//			List<VendasProdutoDTO> lista = vendasProdutoDTORN.listarProdutosPedido(empresaFB, yourDate, currentDate, 17);
//			
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			String json = gson.toJson(lista);
//			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
