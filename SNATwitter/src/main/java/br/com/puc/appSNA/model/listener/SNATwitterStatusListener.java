package br.com.puc.appSNA.model.listener;

import java.util.ArrayList;
import java.util.List;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import br.com.puc.appSNA.model.beans.UserMention;
import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.dao.StatusDAO;
import br.com.puc.appSNA.model.dao.StatusDAOImpl;
import br.com.puc.appSNA.model.dao.UserMentionDAO;
import br.com.puc.appSNA.model.dao.UserMentionDAOImpl;
import br.com.puc.appSNA.model.dao.UsuarioDAO;
import br.com.puc.appSNA.model.dao.UsuarioDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;

public class SNATwitterStatusListener implements StatusListener {

	@Override
	public void onStatus(Status statusTwitter) {
		try {
			if(statusTwitter.getLang().contains("en")){
				AppSNALog.info("@" + statusTwitter.getUser().getScreenName() + " - "
						+ statusTwitter.getText());
				
				UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
				StatusDAO statusDAO = new StatusDAOImpl();
				UserMentionDAO userMentionDAO = new UserMentionDAOImpl();
				
				User userTwitter = statusTwitter.getUser();
				Usuario user = usuarioDAO.findById(userTwitter.getId());
				if(user == null || user.getId_usuario() == null){
					user = new Usuario();
					user.setId_usuario(userTwitter.getId());
					user.setScreename(userTwitter.getScreenName());
					user.setLinguagem(userTwitter.getLang());
					user.setLocalização(userTwitter.getLocation());
					user.setDataDeCriacao(userTwitter.getCreatedAt());
					user.setBiografia(userTwitter.getDescription());
					user.setNome(userTwitter.getName());
					user.setTimeZone(userTwitter.getTimeZone());
					user.setTotalFollowers(userTwitter.getFollowersCount());
					user.setTotalFollowing(userTwitter.getFavouritesCount());
					user.setTotalTweets(userTwitter.getStatusesCount());
					user.setURL(userTwitter.getURL());
					user.setURLImagem(userTwitter.getBiggerProfileImageURL());
					usuarioDAO.create(user);
				}else if ((user != null && user.getId_usuario() != null) && user.getDataDeCriacao() == null){
					user.setLinguagem(userTwitter.getLang());
					user.setLocalização(userTwitter.getLocation());
					user.setDataDeCriacao(userTwitter.getCreatedAt());
					user.setBiografia(userTwitter.getDescription());
					user.setTimeZone(userTwitter.getTimeZone());
					user.setTotalFollowers(userTwitter.getFollowersCount());
					user.setTotalFollowing(userTwitter.getFavouritesCount());
					user.setTotalTweets(userTwitter.getStatusesCount());
					user.setURL(userTwitter.getURL());
					user.setURLImagem(userTwitter.getBiggerProfileImageURL());
					usuarioDAO.update(user);
				}
				
				br.com.puc.appSNA.model.beans.Status status = new br.com.puc.appSNA.model.beans.Status();
				status.setDataDeCriacao(statusTwitter.getCreatedAt());
				status.setId_status(statusTwitter.getId());
				status.setId_usuario(user.getId_usuario());
				status.setLinguagem(statusTwitter.getLang());
				if(statusTwitter.getGeoLocation() != null){
					status.setLatitude(statusTwitter.getGeoLocation().getLatitude());
					status.setLongitude(statusTwitter.getGeoLocation().getLongitude());
				}
				status.setRetweeted(statusTwitter.isRetweeted());
				status.setRetweet(statusTwitter.isRetweet());
				status.setTexto(statusTwitter.getText());
				status.setTotalRetweet(statusTwitter.getRetweetCount());
				statusDAO.create(status);
				
				UserMentionEntity[] userMentions =  statusTwitter.getUserMentionEntities();
				if(userMentions != null && userMentions.length > 0){
					List<UserMention> mencoes = new ArrayList<>(); 
					for(UserMentionEntity userMentionade: userMentions){
						user = usuarioDAO.findById(userMentionade.getId());
						if(user == null || user.getId_usuario() == null){
							user = new Usuario();
							user.setId_usuario(userMentionade.getId());
							user.setScreename(userMentionade.getScreenName());
							user.setNome(userMentionade.getName());
							usuarioDAO.create(user);
						}
						
						UserMention mencao = new UserMention();
						mencao.setId_status(status.getId_status());
						mencao.setId_usuario(status.getId_usuario());
						mencao.setId_user_mentionade(userMentionade.getId());
						mencao.setUsuario(userMentionade.getScreenName());
						mencoes.add(mencao);
					}
					
					userMentionDAO.create(mencoes);
				}
				
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO - NOTHING TO DO
//		AppSNALog.info("Got a status deletion notice id:"
//				+ statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO - NOTHING TO DO
//		AppSNALog
//				.info("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO - NOTHING TO DO
//		AppSNALog.info("Got scrub_geo event userId:" + userId
//				+ " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO - NOTHING TO DO
//		AppSNALog.info("Got stall warning:" + warning);
	}

	@Override
	public void onException(Exception ex) {
		AppSNALog.error(ex.toString());
	}

}
