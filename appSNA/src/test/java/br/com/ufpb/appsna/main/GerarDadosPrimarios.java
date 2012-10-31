package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twitter4j.TwitterException;
import twitter4j.User;

import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;
import br.com.ufpb.appSNAUtil.util.StringUtil;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class GerarDadosPrimarios {
	
	public static void main(String[] args) {
		
		List<String> listaDeNomes = new ArrayList<String>();
		
		listaDeNomes.add("AIRTONGTORRES");
		listaDeNomes.add("alamorocha");
		listaDeNomes.add("ale_patricio");
		listaDeNomes.add("ALLYSONDINIZ");
		listaDeNomes.add("DEZINHAJPA");
		listaDeNomes.add("ARTHURFERRO");
		listaDeNomes.add("atila_jp");
		listaDeNomes.add("AYLTONJR");
		listaDeNomes.add("ELVISREI");
		listaDeNomes.add("evaldodesousa");
		listaDeNomes.add("fabianovidaltur");
		listaDeNomes.add("BRASILTONY");
		listaDeNomes.add("CHIQUELMEBATERA");
		listaDeNomes.add("FLUGARCEZ");
		listaDeNomes.add("IVANILDOPB");
		listaDeNomes.add("KellylopesLOPES");
		listaDeNomes.add("GALVAOJPA");
		listaDeNomes.add("luanadepaulane1");
		listaDeNomes.add("lucasduartereal");
		listaDeNomes.add("Mariacristin339");
		listaDeNomes.add("ONAMEN");
		listaDeNomes.add("jricardoamorim");
		listaDeNomes.add("RINALDOPESSOA");
		listaDeNomes.add("RIQUELSON");
		listaDeNomes.add("NTURISMO_JPPB");
		listaDeNomes.add("ThiagoADVJP");

		AccountCarrousel.startListReady();

		
		FileUtil.criaArquivo(Constantes.DIR_APPSNA_RELACIONAMENTOS + "\\arquivo26.csv", false);

		String[] cabecalho = { "Id", "Nome", "Screename", "Biografia",
				"Localização", "TotalFollowers", "TotalFollowing",
				"TotalTweets", "URL", "TimeZone", "Linguagem",
				"Data de Criação", "URL Imagem"};
		FileUtil.criarCabecalho(cabecalho);
		FileUtil.refresh();

		for (String amigoId : listaDeNomes) {

			User u = null;
			try {
				u = AccountCarrousel.CURRENT_ACCOUNT.showUser(amigoId);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			UserTO amigo = TwitterUtil.getUserData(u);

			FileUtil.escreveNoArquivo(amigo.getId());
			FileUtil.quebra();

			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo
					.getNome()));
			FileUtil.quebra();

			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo
					.getScreename()));
			FileUtil.quebra();

			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo
					.getBiografia()));
			FileUtil.quebra();

			FileUtil.escreveNoArquivo(StringUtil.stringProcessing(amigo
					.getLocalização()));
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

	}


