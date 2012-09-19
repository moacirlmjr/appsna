package br.com.ufpb.appSNAElection.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.to.ElectionTO;

public class ParserElectionCsv {

	private static final int SCREEN_NAME = 0;
	private static final int TERMO = 1;
	private static final int DATA = 2;
	private static final int LOCATION = 3;
	
	public static List<ElectionTO> realizarParserArquivoCDR(File cdr) throws Exception{
		List<ElectionTO> list = new ArrayList<ElectionTO>();
		
		FileReader fr = new FileReader(cdr);
		BufferedReader in = new BufferedReader(fr);
		String line;
		while ((line = in.readLine()) != null) {
			ElectionTO eTO = new ElectionTO();
			
			String lineArray[] = line.split(";");
			
		}
		return null;
	}

	
}
