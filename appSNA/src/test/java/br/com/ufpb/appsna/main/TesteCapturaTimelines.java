package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNA.model.beans.*;
import br.com.ufpb.appSNA.model.dao.HashTagMentionDAO;
import br.com.ufpb.appSNA.model.dao.HashTagMentionDAOImpl;
import br.com.ufpb.appSNA.model.dao.StatusDAO;
import br.com.ufpb.appSNA.model.dao.StatusDAOImpl;
import br.com.ufpb.appSNA.model.dao.URLMentionDAO;
import br.com.ufpb.appSNA.model.dao.URLMentionDAOImpl;
import br.com.ufpb.appSNA.model.dao.UserMentionDAO;
import br.com.ufpb.appSNA.model.dao.UserMentionDAOImpl;

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
				
				System.out.println("Capturando dados de " + username);
				
				for (Status status : statuses) {
					
					long id_usuario = status.getUser().getId();
					long id_status = status.getId();
					GeoLocation loc = status.getGeoLocation();
					double latitude=0.0;
					double longitude=0.0;
					
					StatusDAO staDAO = new StatusDAOImpl();
					URLMentionDAO urlDAO = new URLMentionDAOImpl();
					UserMentionDAO userDAO = new UserMentionDAOImpl();
					HashTagMentionDAO hashDAO = new HashTagMentionDAOImpl();
					
					try{
						if (loc.equals(null)){
							latitude=0.0;
							longitude=0.0;
						}else{
							latitude=loc.getLatitude();
							longitude=loc.getLongitude();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					br.com.ufpb.appSNA.model.beans.Status sta = new br.com.ufpb.appSNA.model.beans.Status();
					UrlMention urlmen = new UrlMention();
					UserMention usermen = new UserMention();
					HashTagMention hashtagmen = new HashTagMention();					
					
					sta.setId_usuario(id_usuario);
					sta.setId(id_status);
					sta.setDataDeCriacao(status.getCreatedAt());					
					sta.setTexto(status.getText());
					sta.setLatitude(latitude);
					sta.setLongitude(longitude);
					sta.setRetweeted(status.isRetweetedByMe());
					sta.setTotalRetweet(status.getRetweetCount());
					
					try {
						staDAO.create(sta);
					} catch (Exception e) {
						AppSNALog.error("Erro no StatusDAO: " + e.toString());
						e.printStackTrace();
					}
					
					if(!status.getURLEntities().equals(null)){
						for (URLEntity url : status.getURLEntities()){
							urlmen.setId_usuario(id_usuario);
							urlmen.setId_status(id_status);
							urlmen.setUrl(url.getURL().getPath());
							try {
								urlDAO.create(urlmen);
							} catch (Exception e) {
								AppSNALog.error("Erro no URLMentionDAO: " + e.toString());
								e.printStackTrace();
							}
						}
						
					}
					
					if(!status.getUserMentionEntities().equals(null)){
						for (UserMentionEntity user : status.getUserMentionEntities()){
							usermen.setId_usuario(id_usuario);
							usermen.setId_status(id_status);
							usermen.setUsuario(user.getScreenName());
							try {
								userDAO.create(usermen);
							} catch (Exception e) {	
								AppSNALog.error("Erro no UserMentionDAO: " + e.toString());
								e.printStackTrace();
							}
						}
					}
					
					
					if(!status.getHashtagEntities().equals(null)){
						for (HashtagEntity hashTag : status.getHashtagEntities()){
							hashtagmen.setId_usuario(id_usuario);
							hashtagmen.setId_status(id_status);
							hashtagmen.setHashtag(hashTag.getText());	
							try {
								hashDAO.create(hashtagmen);
							} catch (Exception e) {
								AppSNALog.error("Erro no HashTAgMentionDAO: " + e.toString());
								e.printStackTrace();
							}
						}
					}					
				}
				
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Falha de busca na timeline: " + te.getMessage());
				System.exit(-1);
			}
		}
	}
}


