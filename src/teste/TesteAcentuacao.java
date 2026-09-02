package teste;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class TesteAcentuacao {
	
	public static void main(String[] args) {
		
		String texto = "cliente separaçāo antecipada, {} [] *&ˆ%$#@!~passarå ă tarde pra pegar.";
		
		System.out.print(removerAcentos(texto));
		System.out.println("");
		System.out.print(deAccent(texto));
	}
	
	public static String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
