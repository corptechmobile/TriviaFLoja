package teste;

public class ParseIntTeste {

	public static void main(String[] args) {
//		Integer cod = null;
//		String ped = "12enk";
//		try {
//			cod = Integer.parseInt(ped);
//			System.out.println(cod);
//		} catch (NumberFormatException e) {
//			System.out.println("Erro na conversão");
//		}
		
		String anoMes = "201810";
		String anoMesToString = anoMes.substring(4, 6) + "/" + anoMes.substring(0, 4);
		System.out.println("-> " + anoMesToString);
		
	}
}
