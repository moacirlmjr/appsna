package br.com.ufpb.appsna.main;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;
import br.com.ufpb.appSNAUtil.util.StringUtil;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteGeracaoRelatorio {

	public static void main(String[] args) throws Exception {

		List<String> listaDeNomes = new ArrayList<String>();
//
//		listaDeNomes.add("AIRTONGTORRES");
//		listaDeNomes.add("alamorocha");
//		listaDeNomes.add("ale_patricio");
//		listaDeNomes.add("ALLYSONDINIZ");
//		listaDeNomes.add("DEZINHAJPA");
//		listaDeNomes.add("ARTHURFERRO");
//		listaDeNomes.add("atila_jp");
		
		listaDeNomes.add("AYLTONJR");
		listaDeNomes.add("ELVISREI");
		listaDeNomes.add("evaldodesousa");
//		listaDeNomes.add("fabianovidaltur");
//		listaDeNomes.add("BRASILTONY");
//		listaDeNomes.add("CHIQUELMEBATERA");
//		listaDeNomes.add("FLUGARCEZ");
//		
//		listaDeNomes.add("IVANILDOPB");
//		listaDeNomes.add("KellylopesLOPES");
//		listaDeNomes.add("GALVAOJPA");
//		listaDeNomes.add("luanadepaulane1");
//		listaDeNomes.add("lucasduartereal");
//		listaDeNomes.add("Mariacristin339");
//		
//		listaDeNomes.add("ONAMEN");
//		listaDeNomes.add("jricardoamorim");
//		listaDeNomes.add("RINALDOPESSOA");
//		listaDeNomes.add("RIQUELSON");
//		listaDeNomes.add("NTURISMO_JPPB");
//		listaDeNomes.add("ThiagoADVJP");
		
		AccountCarrousel.startListReady();

		Map<String, Long> mapUsers = TwitterUtil.retornarUserId(listaDeNomes, false);

		List<Long> listaCsv = new LinkedList<Long>();

		for (String key : mapUsers.keySet()) {
			List<Long> listAmigoId = TwitterUtil
					.retornarListaAmigosIdsList(key,false);
			listaCsv = verificar(listaCsv, listAmigoId);
		}
		
		FileUtil.criaArquivo(Constantes.DIR_APPSNA + "\\arquivo1.csv", true);
		
		String[] cabecalho = {"Id", "Nome", "Screename", "Biografia", "Localização", "TotalFollowers", "TotalFollowing", 
				"TotalTweets", "URL", "TimeZone","Linguagem", "Data de Criação", "URL Imagem"};
		FileUtil.criarCabecalho(cabecalho);	
		FileUtil.refresh();
		
		for (Long amigoId : listaCsv) {
			UserTO amigo = TwitterUtil.getUserData(amigoId);
			
			FileUtil.escreveNoArquivo(amigo.getId());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo.getNome()));
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo.getScreename()));
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo.getBiografia()));
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo.getLocalização()));
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getTotalFollowers());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getTotalFollowing());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getTotalTweets());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getURL());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getTimeZone());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getLinguagem());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(amigo.getDataDeCriacao());
			FileUtil.quebra();
			
			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo.getURLImage()));
			FileUtil.quebraLinha(1);
			
			FileUtil.refresh();
		}

		FileUtil.salvarArquivo();
		System.exit(0);
	}

	private static List<Long> verificar(List<Long> listCsv, List<Long> listAmigos) {
		List<Long> listAux = new ArrayList<Long>();
		listAux.addAll(listCsv);
		for (Long amigoId : listAmigos) {
			if(listAux.size() != 0){
				for (Long idCsv : listAux) {
					if (amigoId.longValue() != idCsv.longValue()) {
						listCsv.add(amigoId);
						break;
					}
				}
			}else{
				listCsv.addAll(listAmigos);
				break;
			}
			
			
		}
		return listCsv;
	}

}
