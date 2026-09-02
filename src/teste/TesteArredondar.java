package teste;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TesteArredondar {
	
	public static void main(String[] args) {
		
		Double d = ((69.9*1d)*(2.75*1d));
		System.out.println("Valor da multiplicação: " +d);
		
		
		//BigDecimal bd = new BigDecimal(d.toString()).setScale(2, RoundingMode.HALF_EVEN);
		
		BigDecimal teste = new BigDecimal(String.valueOf(d));
		teste = teste.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		System.out.println("Arredondamento: "+teste.doubleValue());
		
		System.out.println(" ");
		
		Double d2 = 122.2250;
		System.out.println("Valor Truncado: "+d2);

		BigDecimal teste2 = new BigDecimal(String.valueOf(d2));
		teste2 = teste2.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		System.out.println("Arredondamento2: "+teste2.doubleValue());
		
	}

}
