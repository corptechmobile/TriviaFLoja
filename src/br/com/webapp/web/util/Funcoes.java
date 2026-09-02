package br.com.webapp.web.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFBRN;
import net.sf.jasperreports.engine.util.DigestUtils;

public class Funcoes {
	
	public static final int TAMANHO_CNPJ = 14;
    public static final int TAMANHO_CPF = 11;
    public static final int TAMANHO_CEP = 8;
    
    public static final boolean PADRAO_DE_DESCONTO_NO_PEDIDO = false; // false = desconto no item; true = desconto no pedido 
    public static final int UNIDADE_PADRAO = 1;
    public static final int SO_ESTOQUE = 1;
    
    public static final int IS_TRANSFERENCIA = 0;
    public static final int IS_VENDA = 1;
    public static final int COMPARTILHA_ESTOQUE = 0;
    public static final int EMP_ENCH_EST_COMPART = 0;
	
	public static boolean isNumeric(String str){
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public static String formatCnpjCpfCep(String value) {
        if (value.length() == TAMANHO_CNPJ) { // cnpj
            Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
            Matcher matcher = pattern.matcher(value);

            if (matcher.matches()) {
                value = matcher.replaceAll("$1.$2.$3/$4-$5");
            }

        } else if (value.length() == TAMANHO_CPF) { // cpf
            Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
            Matcher matcher = pattern.matcher(value);

            if (matcher.matches()) {
                value = matcher.replaceAll("$1.$2.$3-$4");
            }

        } else if (value.length() == TAMANHO_CEP) { // cpf
            Pattern pattern = Pattern.compile("(\\d{5})(\\d{3})");
            Matcher matcher = pattern.matcher(value);

            if (matcher.matches()) {
                value = matcher.replaceAll("$1-$2");
            }

        }

        return value;
    }
	
	public static int CalcularAtraso(Date datainicio, Date datafim){
		if(datafim != null && datainicio != null){
			Days dias;
			DateTime dt1 = new DateTime(datainicio.getTime());
			DateTime dt2 = new DateTime(datafim.getTime());
			dias = Days.daysBetween(dt1, dt2);
			if(dias.getDays()>0){
				return dias.getDays();
			}
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		Calendar caIni = Calendar.getInstance();
		caIni.set(Calendar.DATE,01);
		caIni.set(Calendar.MONTH,03);
		caIni.set(Calendar.YEAR,2025);
		caIni.set(Calendar.HOUR, 00);
		caIni.set(Calendar.MINUTE, 00);
		caIni.set(Calendar.AM_PM, Calendar.AM);
		
		Date datainicio = caIni.getTime();
		//datainicio = null;
		
		Calendar caFim = Calendar.getInstance();
		caFim.set(Calendar.DATE, 31);
		caFim.set(Calendar.MONTH,03);
		caFim.set(Calendar.YEAR,2025);
		caFim.set(Calendar.HOUR, 0);
		caFim.set(Calendar.MINUTE, 0);
		caFim.set(Calendar.AM_PM, Calendar.AM);
		
		Date datafim = caFim.getTime();
		
		int dias = CalcularAtraso(datainicio, datafim);
		System.out.println("datainicio: "+datainicio);
		System.out.println("datafim: "+datafim);
		System.out.println("Dias atraso: "+dias);
		
		
		String chave = ColetorInvContagemFBRN.gerarChaveContagem(
	            10, 
	            5, 
	            1002, 
	            "7891234567890"
	        );

	        System.out.println(chave);
	}
	
	public static String iconDocumentoAnexo(String fileName){
		
		if (fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".docx")){
			return "file_docs.png";
		}else if (fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx")){
			return "file_xlss.png";
		}else if (fileName.toLowerCase().endsWith(".pdf")){
			return "file_pdfs.png";
		}else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")){
			return "file_jpgs.png";
		}else if (fileName.toLowerCase().endsWith(".txt")){
			return "file_txts.png";
		}else if (fileName.toLowerCase().endsWith(".rar")){
			return "file_rars.png";
		}else if (fileName.toLowerCase().endsWith(".tiff")){
			return "file_tiffs.png";
		}else{
			return "file_default.png";
		}	
		
	} 
	
	public static double percentual(double total, double valor) {
		if (valor > 0 && total > 0) {
			return ((valor / total) * 100);
		} else if (valor < 0 && total > 0) {
			return ((valor / total) * 100);
		} else {
			return 0;
		}

	}
	
	public static final String DECIMALFORMAT_1 = "#0.0";
	public static final String DECIMALFORMAT_2 = "#0.00";
	public static final String DECIMALFORMAT_3 = "#0.000";
	public static final String DECIMALFORMAT_4 = "#0.0000";
	
	public static String formatDecimal(double x, String format) {  
	    DecimalFormat df = new DecimalFormat(format);  
	    return df.format(x);
	}
	
	public static String formatNumber(Double valor, Locale locale, int minFractionDigits, int maxFractionDigits){
		try {
			if(locale==null) {
				locale = new Locale ("pt", "BR");
			}
	        //NumberFormat format = NumberFormat.getNumberInstance(locale);
			NumberFormat nf = NumberFormat.getInstance(locale);
			nf.setMinimumFractionDigits(minFractionDigits);
			nf.setMaximumFractionDigits(maxFractionDigits);
			
			return nf.format(valor);
		} catch (Exception e) {
			return "n/d";
		}
	}

	public static List<SelectItem> criarSelectItemMeses() {
		List<SelectItem> listaMesesSelect = new ArrayList<SelectItem>();
		listaMesesSelect.add(new SelectItem("01", "Janeiro"));
		listaMesesSelect.add(new SelectItem("02", "Fevereiro"));
		listaMesesSelect.add(new SelectItem("03", "Março"));
		listaMesesSelect.add(new SelectItem("04", "Abril"));
		listaMesesSelect.add(new SelectItem("05", "Maio"));
		listaMesesSelect.add(new SelectItem("06", "Junho"));
		listaMesesSelect.add(new SelectItem("07", "Julho"));
		listaMesesSelect.add(new SelectItem("08", "Agosto"));
		listaMesesSelect.add(new SelectItem("09", "Setembro"));
		listaMesesSelect.add(new SelectItem("10", "Outubro"));
		listaMesesSelect.add(new SelectItem("11", "Novembro"));
		listaMesesSelect.add(new SelectItem("12", "Dezembro"));
		return listaMesesSelect;
	}
	
	public static double descontoPreco(double precoOriginal, double precoModificado) {
		double desconto = 0;
		double novoValor = 0;

		try {
			desconto = precoOriginal - precoModificado;
			novoValor = precoOriginal - desconto;
			
			double descBig = (1 - (novoValor / precoOriginal)) * 100;

			if (precoModificado != 0) {
				BigDecimal teste = new BigDecimal(String.valueOf(descBig));
				teste = teste.setScale(4, BigDecimal.ROUND_HALF_UP);
				return teste.doubleValue();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0;
	}
	
	public static double descontoPrecoPedVenda(double precoOriginal, double precoModificado) {
		double desconto = 0;
		double novoValor = 0;

		try {
			desconto = precoOriginal - precoModificado;
			novoValor = precoOriginal - desconto;
			
			double descBig = (1 - (novoValor / precoOriginal)) * 100;

			BigDecimal teste = new BigDecimal(String.valueOf(descBig));
			teste = teste.setScale(4, BigDecimal.ROUND_HALF_UP);
			return teste.doubleValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0;
	}
	
	public static double precoDesconto(double preco, double desconto) {
		double novoPreco = preco;

		if (preco != 0 && desconto != 0) {
			novoPreco = Funcoes.arrendondaValor(2, (preco * (100 - desconto) / 100));
		}

		return novoPreco;
	}
	
	public static double arrendondaValor(int decimal, double valor) {
		BigDecimal teste = new BigDecimal(String.valueOf(valor));
		teste = teste.setScale(decimal, BigDecimal.ROUND_HALF_EVEN);

		return teste.doubleValue();
	}
	
	
	public static Double truncValor(int decimal, double valor) {
		BigDecimal teste = new BigDecimal(String.valueOf(valor));
		teste = teste.setScale(decimal, BigDecimal.ROUND_DOWN);

		return teste.doubleValue();
	}
	
	public static boolean validaSplit(String varSplit) {
		if(!varSplit.equals("") && 
		   !varSplit.equals(" ") && 
		   !varSplit.equals(".") && 
		   !varSplit.equals(",") && 
		   !varSplit.equals("/") && 
		   !varSplit.equals("?") && 
		   !varSplit.equals("!") && 
		   !varSplit.equals("@") && 
		   !varSplit.equals("%") && 
		   !varSplit.equals("$") && 
		   !varSplit.equals("#") && 
		   !varSplit.equals(")") && 
		   !varSplit.equals("(") && 
		   !varSplit.equals("-") && 
		   !varSplit.equals("+") &&
		   !varSplit.equals("*") &&
		   !varSplit.equals("=") &&
		   !varSplit.equals("_") &&
		   !varSplit.equals("&")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static Date dataFilter1(Date dataFilter1) {
		if(dataFilter1!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			dataFilter1 = c.getTime();
		}
		return dataFilter1;
	}
	
	public static Date dataFilter2(Date dataFilter2) {
		if(dataFilter2!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter2);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			
			dataFilter2 = c.getTime();
		}
		return dataFilter2;
	}
	
	public static String senhaMD5(String senha) throws NoSuchAlgorithmException {
		return DigestUtils.instance().md5(senha).toString();
	}
	
	public static String gerarMD5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Converte byte para representação hexadecimal em 2 dígitos
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo MD5 não encontrado.", e);
        }
    }
	
	
	
}
