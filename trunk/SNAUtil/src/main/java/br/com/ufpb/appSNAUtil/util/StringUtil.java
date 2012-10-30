package br.com.ufpb.appSNAUtil.util;

public class StringUtil {
	
	public static String stringProcessing(String anterior){
		String nova;
		nova = anterior.replace("\r", "$").replace("\n", "$").replace(";", "$").replace("", " ");
		return nova;
	}
	
	public static boolean wordVerification(String word){
		
		if(word==null || word==""){
			return false;
		}
		return true;
		
	}

}
