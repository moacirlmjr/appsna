package br.com.ufpb.appSNA.util;

import java.util.LinkedList;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import br.com.ufpb.appSNA.model.beans.HashTagMention;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.UrlMention;
import br.com.ufpb.appSNA.model.beans.UserMention;
import br.com.ufpb.appSNA.model.dao.HashTagMentionDAO;
import br.com.ufpb.appSNA.model.dao.HashTagMentionDAOImpl;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNA.model.dao.StatusDAO;
import br.com.ufpb.appSNA.model.dao.StatusDAOImpl;
import br.com.ufpb.appSNA.model.dao.URLMentionDAO;
import br.com.ufpb.appSNA.model.dao.URLMentionDAOImpl;
import br.com.ufpb.appSNA.model.dao.UserMentionDAO;
import br.com.ufpb.appSNA.model.dao.UserMentionDAOImpl;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class CapturaTimeline {

	public static void timelineCatch(List<String> nameList, int pageCount) {

		AccountCarrousel.startListReady();

		for (String username : nameList) {

			try {

				List<Status> statuses = new LinkedList<Status>();
				SNAElementDAO snaElemDAO = new SNAElementDAOImpl();
				
				int page = 1;
				while (page <= pageCount) {
					statuses.addAll(AccountCarrousel.CURRENT_ACCOUNT
							.getUserTimeline(username, new Paging(page, 1000)));
					page++;
				}

				System.out.println("Capturando dados de " + username);

				SNAElement elem;
				try {
					elem = getSnaElement(username);
					
					if(snaElemDAO.findById(elem.getId_usuario()).getId_usuario() == null){
						snaElemDAO.create(elem);
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}

				for (Status status : statuses) {

					long id_usuario = status.getUser().getId();
					long id_status = status.getId();
					GeoLocation loc = status.getGeoLocation();
					double latitude = 0.0;
					double longitude = 0.0;

					StatusDAO staDAO = new StatusDAOImpl();
					URLMentionDAO urlDAO = new URLMentionDAOImpl();
					UserMentionDAO userDAO = new UserMentionDAOImpl();
					HashTagMentionDAO hashDAO = new HashTagMentionDAOImpl();

					try {
						if (loc == null) {
							latitude = 0.0;
							longitude = 0.0;
						} else {
							latitude = loc.getLatitude();
							longitude = loc.getLongitude();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					br.com.ufpb.appSNA.model.beans.Status sta = new br.com.ufpb.appSNA.model.beans.Status();
					UrlMention urlmen = new UrlMention();
					UserMention usermen = new UserMention();
					HashTagMention hashtagmen = new HashTagMention();

					sta.setId_usuario(id_usuario);
					sta.setId_status(id_status);

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

					if (status.getURLEntities() != null) {

						for (URLEntity url : status.getURLEntities()) {

							urlmen.setId_usuario(id_usuario);
							urlmen.setId_status(id_status);
							urlmen.setUrl(url.getURL().toString());

							try {
								urlDAO.create(urlmen);
							} catch (Exception e) {
								AppSNALog.error("Erro no URLMentionDAO: "
										+ e.toString());
								e.printStackTrace();
							}
						}

					}

					if (status.getUserMentionEntities() != null) {

						for (UserMentionEntity user : status
								.getUserMentionEntities()) {
							
							if(snaElemDAO.findById(user.getId()).getId_usuario() == null){
								elem = getSnaElement(user.getScreenName());
								snaElemDAO.create(elem);
							}
							
							usermen.setId_usuario(id_usuario);
							usermen.setId_status(id_status);
							usermen.setId_user_mentionade(user.getId());
							usermen.setUsuario(user.getScreenName());

							try {
								userDAO.create(usermen);
							} catch (Exception e) {
								AppSNALog.error("Erro no UserMentionDAO: "
										+ e.toString());
								e.printStackTrace();
							}
						}
					}

					if (status.getHashtagEntities() != null) {

						for (HashtagEntity hashTag : status
								.getHashtagEntities()) {

							hashtagmen.setId_usuario(id_usuario);
							hashtagmen.setId_status(id_status);
							hashtagmen.setHashtag(hashTag.getText());

							try {
								hashDAO.create(hashtagmen);
							} catch (Exception e) {
								AppSNALog.error("Erro no HashTAgMentionDAO: "
										+ e.toString());
								e.printStackTrace();
							}
						}
					}
				}

			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Falha de busca na timeline: "
						+ te.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static SNAElement getSnaElement(String username) throws Exception {
		User userAtual = AccountCarrousel.CURRENT_ACCOUNT.showUser(username);

		SNAElement elem = new SNAElement();
		elem.setId_usuario(userAtual.getId());
		elem.setNome(userAtual.getName());
		elem.setScreename(userAtual.getScreenName());
		elem.setBiografia(userAtual.getDescription());
		elem.setLocalização(userAtual.getLocation());
		elem.setTotalFollowing(userAtual.getFriendsCount());
		elem.setTotalFollowers(userAtual.getFollowersCount());
		elem.setTotalTweets(userAtual.getStatusesCount());
		try {
			elem.setURL(userAtual.getURL() == null ? "não informado" : userAtual.getURL().toString());
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

		elem.setTimeZone(userAtual.getTimeZone());
		elem.setLinguagem(userAtual.getLang());

		elem.setDataDeCriacao(userAtual.getCreatedAt());
		elem.setURLImagem(userAtual.getProfileBackgroundImageUrl());

		return elem;
	}

}
