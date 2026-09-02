package teste;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFB;
import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFBRN;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.HibernateUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



public class TesteExcel {

	public static void main(String[] args) throws BiffException, IOException {

		Session session = null;
		
		HibernateUtil.getSessionFactoryFirebird().openSession();
		session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();     
		session.beginTransaction();

		InfoGerProdutoFBRN infoGerProdutoFBRN = new InfoGerProdutoFBRN();
		
		Workbook workbook = Workbook.getWorkbook(new File("C:\\Desenvolvimento\\F-Loja\\impCusto.xls"));
		
		Sheet sheet = workbook.getSheet(0);

		int linhas = sheet.getRows();
		int colunas = sheet.getColumns();

		System.out.println("Numero de linhas: " + linhas);
		System.out.println("Numero de colunas: " + colunas);

		for (int i = 1; i < linhas;i++) {

			//System.out.println("Linha: " + i);
			
			try {
			
				Cell a1 = sheet.getCell(0, i);
				Cell b2 = sheet.getCell(1, i);

				if(a1.getContents()!=null && b2.getContents()!=null) {
					InfoGerProdutoFB infoGerProdutoFB = new InfoGerProdutoFB();
					infoGerProdutoFB.setProdutoId(Integer.parseInt(a1.getContents()));
					infoGerProdutoFB.setCustoMedio(Double.parseDouble(b2.getContents()));
					infoGerProdutoFB.setCustoMedioOnLine(Double.parseDouble(b2.getContents()));
					infoGerProdutoFB.setCustoGerAtual(Double.parseDouble(b2.getContents()));
					
					infoGerProdutoFBRN.update(infoGerProdutoFB);
					
					System.out.println("Codigo "+a1.getContents()+": "+b2.getContents());
					
				}
			
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}	
	}

}
