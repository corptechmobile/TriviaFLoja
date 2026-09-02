package br.com.webapp.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;

public class UtilData {

	public static final String FORMATO_DATA_INVERTIDA = "yyyy-MM-dd";
	public static final String FORMATO_DATA_HORA = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATO_DATA_HORA_FIREBIRD = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMATO_DATA_ANO_MES = "yyyy-MM";
	public static final String HORA_FINAL = " 23:59:59";
	public static final String HORA_INICIAL = " 00:00:00";
	
	public static final String FORMATO_DATA_HORA_PTBR = "dd/MM/yyyy HH:mm";
	public static final String FORMATO_DATA_PTBR = "dd/MM/yyyy";

	public static Date formatarStringParaData(String data, String formatoData){
		Date novaData = null;
		SimpleDateFormat format	= new SimpleDateFormat(formatoData);

		try {
			novaData = format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return novaData;
	}

	public static String formatarData(Date data, String formatoData){
		String dataFormatada = null;
		SimpleDateFormat format	= new SimpleDateFormat(formatoData);

		try {
			dataFormatada = format.format(data);
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return dataFormatada;
	}

	public static String formatarData(Date data, String formatoData, boolean retornarVazio){
		String dataFormatada = null;

		dataFormatada = formatarData(data, formatoData);

		if(dataFormatada == null && retornarVazio){
			dataFormatada = "";
		}

		return dataFormatada;
	}

	public static Date getDataInicioVerificacao(){
		Date dataInicio = null;
		Calendar calendar = Calendar.getInstance();
		String dataGerada;

		String anoAtual	= new SimpleDateFormat("yyyy").format(new Date());
		String mesAtual	= new SimpleDateFormat("MM").format(new Date());

		Date data = formatarStringParaData(anoAtual + "-" + mesAtual + "-01", FORMATO_DATA_INVERTIDA);
		calendar.setTime(data);

		if((calendar.get(Calendar.MONTH) + 1) < 10){
			dataGerada= calendar.get(Calendar.YEAR) + "-0" + (calendar.get(Calendar.MONTH)+1) + "-" + "01";
		}else{
			dataGerada = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + "01";
		}

		dataInicio = formatarStringParaData(dataGerada, FORMATO_DATA_INVERTIDA);

		return dataInicio;
	}

	public static String getAnoMes(Date data){
		if(data==null){
			data = new Date();
		}
		return new SimpleDateFormat("yyyy").format(data) + "" + new SimpleDateFormat("MM").format(data);
	}

	public static Date formatarData(String data, String formato) {
		Date dataFormatada = null;

		try {
			dataFormatada = new SimpleDateFormat(formato).parse(data);
		} catch (Exception e) {
		}

		return dataFormatada;
	}

	public static int getAno(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);

		return calendar.get(Calendar.YEAR);
	}

	public static String retrocederDataComQtdMeses(Date data, int qtdMeses, String hora) {
		StringBuilder buider = new StringBuilder();
		String mes = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.MONTH, -qtdMeses);

		mes = calendar.get(Calendar.MONTH) > 10 ? String.valueOf(calendar.get(Calendar.MONTH)) : "0" + calendar.get(Calendar.MONTH);

		buider.append(calendar.get(Calendar.YEAR)).append("-").append(mes).append("-").append(calendar.get(Calendar.DAY_OF_MONTH));

		if(hora != null && !"".equals(hora)){
			buider.append(hora);
		}

		return buider.toString();
	}
	
	public static int diaSemanaRota(Date data){
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			return 0;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
			return 1;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
			return 2;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
			return 3;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			return 4;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			return 5;
		}else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return 6;
		}
		
		return 0;
		
	}
	
	public static String diffBetweenDates(Date data1, Date data2){

		if(data1==null || data2==null){
			return "";
		}
		
		DateTime dataInicial = new DateTime(data1);
		DateTime dataFinal = new DateTime(data2);
		
		Period period   = new Period(dataInicial, dataFinal);
		
		long hr = period.getHours();
		long min = period.getMinutes();
		long dias = period.getDays();
		
		String result = "";
		if(dias>0){
			result += dias + "d ";
		}
		if(hr>0 && hr <24){
			result += hr + "h ";
		}
		if(min>0){
			result += min + "m";
		}
		
		return result;
		
	}
	
	public static Integer daysBetweenDates(Date data1, Date data2){

		if(data1==null || data2==null){
			return null;
		}
		LocalDate dt1 = new LocalDate(data1);
		LocalDate dt2 = new LocalDate(data2);
		return Days.daysBetween(dt1, dt2).getDays();
		
	}
	
	public static String mesAbreviado(int month, boolean uppercase){
		if(month == Calendar.JANUARY){
			if(uppercase){
				return "JAN";
			}else{
				return "Jan";
			}
		}else if(month == Calendar.FEBRUARY){
			if(uppercase){
				return "FEV";
			}else{
				return "Fev";
			}
		}else if(month == Calendar.MARCH){
			if(uppercase){
				return "MAR";
			}else{
				return "Mar";
			}
		}else if(month == Calendar.APRIL){
			if(uppercase){
				return "ABR";
			}else{
				return "Abr";
			}
		}else if(month == Calendar.MAY){
			if(uppercase){
				return "MAI";
			}else{
				return "Mai";
			}
		}else if(month == Calendar.JUNE){
			if(uppercase){
				return "JUN";
			}else{
				return "Jun";
			}
		}else if(month == Calendar.JULY){
			if(uppercase){
				return "JUL";
			}else{
				return "Jul";
			}
		}else if(month == Calendar.AUGUST){
			if(uppercase){
				return "AGO";
			}else{
				return "Ago";
			}
		}else if(month == Calendar.SEPTEMBER){
			if(uppercase){
				return "SET";
			}else{
				return "Set";
			}
		}else if(month == Calendar.OCTOBER){
			if(uppercase){
				return "OUT";
			}else{
				return "Out";
			}
		}else if(month == Calendar.NOVEMBER){
			if(uppercase){
				return "NOV";
			}else{
				return "Nov";
			}
		}else if(month == Calendar.DECEMBER){
			if(uppercase){
				return "DEZ";
			}else{
				return "Dez";
			}
		}
		
		return "";
	}
	
	public static String diaSemanaFirebirdToString(int dia) {
		String diaSemana = null;
		switch (dia) {
		case 0:
			diaSemana = "Domingo";
			break;
		case 1:
			diaSemana = "Segunda";
			break;
		case 2:
			diaSemana = "Terça";
			break;
		case 3:
			diaSemana = "Quarta";
			break;
		case 4:
			diaSemana = "Quinta";
			break;
		case 5:
			diaSemana = "Sexta";
			break;
		case 6:
			diaSemana = "Sábado";
			break;
		default:
			diaSemana = "Domingo";
			break;
		}
		
		return diaSemana;
	}
	
}
