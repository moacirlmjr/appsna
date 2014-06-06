package br.com.puc.appSNA.model.listener;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import br.com.puc.appSNA.model.beans.UserMention;
import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.util.AppSNALog;

public class SNATwitterStatusListener implements StatusListener {

	@Override
	public void onStatus(Status statusTwitter) {
		try {
			if(statusTwitter.getLang().contains("en") || statusTwitter.getLang().contains("pt")){
				AppSNALog.info("@" + statusTwitter.getUser().getScreenName() + " - "
						+ statusTwitter.getText());
				
				User userTwitter = statusTwitter.getUser();
				// verificar se usuario existe, caso exista e tem dados incompletos atualiza-lo
				Usuario user = new Usuario();
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
				
				//Salvar Usuario
				
				br.com.puc.appSNA.model.beans.Status status = new br.com.puc.appSNA.model.beans.Status();
				status.setDataDeCriacao(statusTwitter.getCreatedAt());
				status.setId_status(statusTwitter.getId());
				status.setId_usuario(user.getId_usuario());
				status.setLatitude(statusTwitter.getGeoLocation().getLatitude());
				status.setLongitude(statusTwitter.getGeoLocation().getLongitude());
				status.setRetweeted(statusTwitter.isRetweeted());
				status.setTexto(statusTwitter.getText());
				status.setTotalRetweet(statusTwitter.getRetweetCount());
				
				//Salvar Status
				
				UserMentionEntity[] userMentions =  statusTwitter.getUserMentionEntities();
				if(userMentions != null && userMentions.length > 0){
					for(UserMentionEntity userMentionade: userMentions){
						//pesquisar por id se existe na base se n, criar
						user = new Usuario();
						user.setId_usuario(userMentionade.getId());
						user.setScreename(userMentionade.getScreenName());
						user.setNome(userMentionade.getName());
						
						//salvar usuario
						
						UserMention mencao = new UserMention();
						mencao.setId_status(status.getId_status());
						mencao.setId_usuario(status.getId_usuario());
						mencao.setId_user_mentionade(userMentionade.getId());
						mencao.setUsuario(userMentionade.getScreenName());
						
						//salvar mencao
						
					}
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
