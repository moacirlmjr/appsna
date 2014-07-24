package br.com.puc.appSNA.util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.enumeration.AuthEnum;

public class TwitterUtil implements Serializable {

	public static TwitterFactory createTwitterFactory(AuthEnum authEnum)
			throws Exception {
		TwitterFactory tf = new TwitterFactory(
				createConfigurationBuilder(authEnum));
		return tf;
	}

	public static Configuration createConfigurationBuilder(AuthEnum authEnum)
			throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(authEnum.getConsumerToken())
				.setOAuthConsumerSecret(authEnum.getConsumerSecret())
				.setOAuthAccessToken(authEnum.getAccessToken())
				.setOAuthAccessTokenSecret(authEnum.getAccessSecret());
		return cb.build();
	}

	public static List<Usuario> retornarUsers(String screenName, Twitter twitter)
			throws Exception {
		List<Usuario> users = new LinkedList<Usuario>();
		ResponseList<User> usersRetornados;
		int page = 1;
		usersRetornados = twitter.searchUsers(screenName, page);
		for (User user : usersRetornados) {
			Usuario u = new Usuario();
			u.setId_usuario(user.getId());
			u.setScreename(user.getScreenName());
			u.setNome(user.getName());
			u.setURLImagem(user.getMiniProfileImageURL());
			users.add(u);
		}

		return users;
	}

}