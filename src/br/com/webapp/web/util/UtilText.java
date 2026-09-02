package br.com.webapp.web.util;

import java.util.ArrayList;
import java.util.List;

public class UtilText {
	
	public static void main(String[] args) {
		System.out.println(UtilText.maiusculo("leonardo DA VINci leitE pAiva"));
		System.out.println(UtilText.maiusculo("helio de lima amancio de melo"));
	}
	
	public static String maiusculo(String s) {
		
		List<String> notUpperCase = new ArrayList<String>();
		notUpperCase.add("de");
		notUpperCase.add("da");
		
		String result = "";
		
		if(s!=null) {
			s = s.toLowerCase();
			
			String[] arr = s.split(" ");
		    StringBuffer sb = new StringBuffer();

		    for(int i = 0; i < arr.length; i++) {
		    	if(arr[i] != null && !"".equals(arr[i])) {
			    	if(notUpperCase.contains(arr[i])) {
			    		sb.append(arr[i]).append(" ");
			    	}else {
			    		sb.append(Character.toUpperCase(arr[i].charAt(0)));
			    		sb.append(arr[i].substring(1)).append(" ");
			    	}
		    	}
		    }
		    return sb.toString().trim();
		}
		
		return result;
	}

}
