package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Status;
import twitter4j.TwitterException;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNA.model.beans.*;

public class TesteCapturaTimelines {
	
	public static void main(String[] args) {
		
		List<String> listaDeNomes = new ArrayList<String>();
		
				listaDeNomes.add("@AIRTONGTORRES");
				listaDeNomes.add("@alamorocha");
				listaDeNomes.add("@ale_patricio");
				listaDeNomes.add("@ALLYSONDINIZ");
				listaDeNomes.add("@DEZINHAJPA");
				listaDeNomes.add("@ARTHURFERRO");
				listaDeNomes.add("@atila_jp");				
				listaDeNomes.add("@AYLTONJR");
				listaDeNomes.add("@ELVISREI");
				listaDeNomes.add("@evaldodesousa");
				listaDeNomes.add("@fabianovidaltur");
				listaDeNomes.add("@BRASILTONY");
				listaDeNomes.add("@CHIQUELMEBATERA");
				listaDeNomes.add("@FLUGARCEZ");				
				listaDeNomes.add("@IVANILDOPB");
				listaDeNomes.add("@KellylopesLOPES");
				listaDeNomes.add("@GALVAOJPA");
				listaDeNomes.add("@luanadepaulane1");
				listaDeNomes.add("@lucasduartereal");
				listaDeNomes.add("@Mariacristin339");				
				listaDeNomes.add("@ONAMEN");
				listaDeNomes.add("@jricardoamorim");
				listaDeNomes.add("@RINALDOPESSOA");
				listaDeNomes.add("@RIQUELSON");
				listaDeNomes.add("@NTURISMO_JPPB");
				listaDeNomes.add("@ThiagoADVJP");
		       
				AccountCarrousel.startListReady();
		    	
		for (String username : listaDeNomes) {
			
			try {
				List<Status> statuses = null;				
				if (args.length == 1) {					
					statuses = AccountCarrousel.CURRENT_ACCOUNT.getUserTimeline(username);
				} else {
					System.out.println("Erro na busca da timeline de " + username);
				}
				System.out.println("Showing" + username + "'s user timeline.");
				for (Status status : statuses) {
					
					long id_usuario = status.getUser().getId();
					long id_status = status.getId();
					
					br.com.ufpb.appSNA.model.beans.Status sta = new br.com.ufpb.appSNA.model.beans.Status();
					UrlMention urlmen = new UrlMention();
					
					
					sta.setId_usuario(id_usuario);
					sta.setId(id_status);
					sta.setDataDeCriacao(status.getCreatedAt());
					sta.setTexto(status.getText());
					sta.setRetweeted(status.isRetweetedByMe());
					sta.setTotalRetweet(status.getRetweetCount());
					
				}
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to get timeline: " + te.getMessage());
				System.exit(-1);
			}
		}
	}
}


