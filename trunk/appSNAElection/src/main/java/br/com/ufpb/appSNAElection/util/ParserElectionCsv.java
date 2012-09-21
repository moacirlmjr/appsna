package br.com.ufpb.appSNAElection.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Resultado;

public class ParserElectionCsv {

	private static final int SCREEN_NAME = 0;
	private static final int TERMO = 1;
	private static final int DATA = 2;
	private static final int LOCATION = 3;
	private static final int LATITUDE = 0;
	private static final int LONGITUDE = 1;
	
	public static List<Resultado> realizarParserArquivoCDR(File cdr) throws Exception{
		List<Resultado> list = new ArrayList<Resultado>();
		
		FileReader fr = new FileReader(cdr);
		BufferedReader in = new BufferedReader(fr);
		String line;
		while ((line = in.readLine()) != null) {
			Resultado eTO = new Resultado();
			
			String lineArray[] = line.split(";");
			
			eTO.setScreen_name(lineArray[SCREEN_NAME]);
			eTO.setTermo(lineArray[TERMO]);
			eTO.setData(new Date(Long.valueOf(lineArray[DATA])));
			
			if(!lineArray[LOCATION].equals("NULL")){
				String locationArray[] = lineArray[LOCATION].split(",");
				
				eTO.setLatitude(Long.parseLong(locationArray[LATITUDE]));
				eTO.setLongitude(Long.parseLong(locationArray[LONGITUDE]));
			}
			
			list.add(eTO);
		}
		return list;
	}

	
}
