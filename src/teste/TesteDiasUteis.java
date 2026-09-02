package teste;

import java.util.Calendar;

import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBRN;


public class TesteDiasUteis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DiasUteisFB diasUteis;
		
		
		String anoMes = "201908";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		int mes = Integer.parseInt(anoMes.substring(4,6));
		System.out.println("Mes: " + mes);
		c.set(Calendar.MONTH, mes-1);
		System.out.println("Data: " + c.getTime());

		c.set(Calendar.YEAR, Integer.parseInt(anoMes.substring(0,4)));
		int dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("Dias: " + dia);

		c.set(Calendar.DAY_OF_MONTH, dia);
		System.out.println("Data: " + c.getTime());
			
		diasUteis = new DiasUteisFBRN().carregar(c.getTime());
		System.out.println("diasUteis: " + diasUteis.getDiasUteis());
		System.out.println("prazo: " + diasUteis.getPrazoDecorrido());

	}

}
