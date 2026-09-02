package teste;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTO;
import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTORN;
import br.com.webapp.web.util.HibernateUtil;

public class InsertUpdateAlcadaPagtoTeste {

	public static void main(String[] args) {

		Session session = null;
		
    	try {
    		
    		HibernateUtil.getSessionFactoryFirebird().openSession();
    		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
    		session.beginTransaction();
    		
    		AlcadaCondPagtoFBDTORN alcadaCondPagtoFBDTORN = new AlcadaCondPagtoFBDTORN();

//    		AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO = new AlcadaCondPagtoFBDTO();
//    		alcadaCondPagtoFBDTO.setGestaoVendaId(4);
//    		alcadaCondPagtoFBDTO.setCondPagtoId(1);
//    		alcadaCondPagtoFBDTO.setAlcada(12.7);
    		
    		
    		
//    		AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO = alcadaCondPagtoFBDTORN.carregar(4, 1);
//    		System.out.println(alcadaCondPagtoFBDTO.getGestaoVendaDesc());
    		
//    		List<AlcadaCondPagtoFBDTO> lista = new ArrayList<AlcadaCondPagtoFBDTO>();
//    		
//    		lista = alcadaCondPagtoFBDTORN.listar(null, null);
//    		
//    		for (AlcadaCondPagtoFBDTO rs : lista) {
//				System.out.println(rs.getAlcada());
//			}
    		
//    		System.out.println("gestaoVendaFB[getId]: " + gestaoVendaFB.getId());
//    		System.out.println("gestaoVendaFB[getNome]: " + gestaoVendaFB.getNome());
    		
    		session.getTransaction().commit();
    		
    	}catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
		}
	}

}
