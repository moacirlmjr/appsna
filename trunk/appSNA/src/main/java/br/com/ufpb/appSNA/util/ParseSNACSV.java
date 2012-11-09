package br.com.ufpb.appSNA.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;


public class ParseSNACSV {

	private static final int ID = 0;
	private static final int NOME = 1;
	private static final int SCREENNAME = 2;
	private static final int BIOGRAFIA = 3;
	private static final int LOCALIZACAO = 4;
	private static final int TOTALFOLLOWING = 5;
	private static final int TOTALFOLLOWERS = 6;
	private static final int TOTALTWEETS = 7;
	private static final int URL = 8;
	private static final int TIMEZONE = 9;
	private static final int LINGUAGEM = 10;
	private static final int DATADECRIACAO = 11;
	private static final int URLIMAGEM = 12;
	
	
	private static final int IDSOURCE = 0;
	private static final int IDTARGET = 1;


	public static List<SNAElement> realizarParserArquivoCDR(File cdr) throws Exception {
		List<SNAElement> list = new ArrayList<SNAElement>();

		FileReader fr = new FileReader(cdr);
		BufferedReader in = new BufferedReader(fr);
		String line;
		
		while ((line = in.readLine()) != null) {
			
			SNAElement SNAEl = new SNAElement();

			String lineArray[] = line.split(";");
			//System.out.println(lineArray[DATADECRIACAO]);

			SNAEl.setId_usuario(Long.valueOf(lineArray[ID]));
			SNAEl.setNome(lineArray[NOME]);
			SNAEl.setScreename(lineArray[SCREENNAME]);
			SNAEl.setBiografia(lineArray[BIOGRAFIA]);
			SNAEl.setLocalização(lineArray[LOCALIZACAO]);
			SNAEl.setTotalFollowing(Integer.valueOf(lineArray[TOTALFOLLOWING]));
			SNAEl.setTotalFollowers(Integer.valueOf(lineArray[TOTALFOLLOWERS]));
			SNAEl.setTotalTweets(Integer.valueOf(lineArray[TOTALTWEETS]));
			SNAEl.setURL(lineArray[URL]);
			SNAEl.setTimeZone(lineArray[TIMEZONE]);
			SNAEl.setLinguagem(lineArray[LINGUAGEM]);
			SNAEl.setDataDeCriacao(DateUtil.dateParser(lineArray[DATADECRIACAO]).getTime());		
			SNAEl.setURLImagem(lineArray[URLIMAGEM]);

			list.add(SNAEl);
		}
		return list;
	}
	
	
	public static List<Relacionamento> realizarParserArquivoRelacionamentoCDR(File cdr) throws Exception {
		List<Relacionamento> list = new ArrayList<Relacionamento>();

		FileReader fr = new FileReader(cdr);
		BufferedReader in = new BufferedReader(fr);
		String line;
		
		while ((line = in.readLine()) != null) {
			
			Relacionamento rel = new Relacionamento();
			String lineArray[] = line.split(";");
			rel.setId_source(Long.valueOf(lineArray[IDSOURCE]));
			rel.setId_target(Long.valueOf(lineArray[IDTARGET]));
			list.add(rel);
		}
		return list;
	}


}
