package br.com.ufpb.appSNAUtil.util;

public class StringUtil {
	
	public static String stringProcessing(String anterior){
		String nova;
		nova = anterior.replace("\r", "$").replace("\n", "$").replace(";", "$");
		return nova;
	}

}
