package br.com.ufpb.appSNA.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Resultado;
import br.com.ufpb.appSNAElection.model.beans.to.ElectionTO;
import br.com.ufpb.appSNAElection.model.beans.to.LogTO;
import br.com.ufpb.appSNAElection.util.ParserElectionCsv;
import br.com.ufpb.appSNAElection.util.ParserLog;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;

public class TestParserLog {

	private static final int DATA = 0;
	private static final int PARTE2 = 1;
	private static final int SCREEN_NAME = 0;
	private static final int STATUS = 1;

	public static List<LogTO> realizarParserLog(File log) throws Exception {
		List<LogTO> list = new ArrayList<LogTO>();

		FileReader fr = new FileReader(log);
		BufferedReader in = new BufferedReader(fr);
		String line;
		String lineAnterior = "";
		while ((line = in.readLine()) != null) {
			LogTO logTo = new LogTO();

			if (line.toLowerCase().contains("mysql".toLowerCase())) {
				try {
					String lineArray[] = lineAnterior.split(";");
					logTo.setData(tratarDataHora(lineArray[DATA]));
					String parte2[] = lineArray[PARTE2].split(" - ");
					logTo.setScreenName(parte2[SCREEN_NAME]);
					logTo.setStatus(lineArray[PARTE2].substring(logTo
							.getScreenName().length() + 2));
					list.add(logTo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			lineAnterior = line;
		}
		return list;
	}

	private static Calendar tratarDataHora(String dataHora) {
		Calendar data = Calendar.getInstance();

		String dataHoraArray[] = dataHora.split(" ");
		String dataArray[] = dataHoraArray[0].split("-");
		data.set(Integer.valueOf(dataArray[0].replaceAll("[^\\p{ASCII}]", "")), Integer
				.parseInt(dataArray[1]) == 0 ? Integer.parseInt(dataArray[1])
				: (Integer.parseInt(dataArray[1]) - 1), Integer
				.parseInt(dataArray[2]));

		String tempo[] = dataHoraArray[1].split(":");
		data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tempo[0]));
		data.set(Calendar.MINUTE, Integer.parseInt(tempo[1]));
		data.set(Calendar.SECOND,
				Integer.parseInt(tempo[2].replace(",", "/").split("/")[0]));
		data.set(Calendar.MILLISECOND,
				Integer.parseInt(tempo[2].replace(",", "/").split("/")[1]));

		return data;
	}
	
	public static void main(String[] args) throws Exception {
		List<File> arquivosCsv = FileUtil.listarArquivosLog(Constantes.DIR_APPSNA);
		for(File f: arquivosCsv){
			List<LogTO> electionList = TestParserLog.realizarParserLog(f);
			FileUtil.criaArquivo(Constantes.DIR_APPSNA
					+ "\\arquivo2.csv", false);
			for (LogTO log : electionList) {
				FileUtil.escreveNoArquivo(log.getData().getTimeInMillis()+"");
				FileUtil.quebra();
				
				FileUtil.escreveNoArquivo(log.getScreenName());
				FileUtil.quebra();
				
				FileUtil.escreveNoArquivo(log.getStatus());
				FileUtil.quebra();
				
				FileUtil.quebraLinha(1);

				FileUtil.refresh();
			}
			
			FileUtil.salvarArquivo();
		}
	}
}
