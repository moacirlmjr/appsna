package br.com.ufpb.appSNA.util;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static String [] meses = DateFormatSymbols.getInstance().getShortMonths();
	
	private static final int DIA_SEMANA = 0;
	private static final int MES_CURTO = 1;
	private static final int DIA_NUMERO = 2;
	private static final int HORA_COMPLETA = 3;
	private static final int TIMEZONE = 4;
	private static final int ANO = 5;
	
	private static final int HORA = 0;
	private static final int MINUTO = 1;
	private static final int SEGUNDO = 2;
	
	
	public static Date dateParser(String data){
		
		String [] data_completa = data.split(" ");
		String [] hora_completa = data_completa[HORA_COMPLETA].split(":");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(data_completa[ANO]), retornarIndiceMes(data_completa[MES_CURTO]), Integer.valueOf(data_completa[DIA_NUMERO]), 
				Integer.valueOf(hora_completa[HORA]), Integer.valueOf(hora_completa[MINUTO]), Integer.valueOf(hora_completa[SEGUNDO]));	
		
		return cal.getTime();
		
		
	}
	
	
	private static int retornarIndiceMes (String shortMonth){
		
		int i = 0;
		for(String n: meses){
			if(n.equals(shortMonth)){
				break;
			}
			i++;
		}
		
		return i;
	}

}
