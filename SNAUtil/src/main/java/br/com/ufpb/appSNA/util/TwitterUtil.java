package br.com.ufpb.appSNA.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNA.model.enumeration.AuthEnum;

public class TwitterUtil {
	// TODO tratar a exceção twitter exception e fazer um algoritmo para
	// realizar a troca de chaves quando isso ocorrer
	// TODO fazer com que cada metodo tenha como parametro o bean Twitter
	// TODO fazer metodo para pesquisar sobre a query desejada na timeline de
	// algum usuario
	// TODO verificar durante a analise a eliminação de dados já analizados

	public static List<User> retornarListaAmigos(String screenName,AuthEnum authEnum)
			throws Exception {

		Twitter twitter = createTwitterFactory(authEnum).getInstance();
		List<User> listUsers = new LinkedList<User>();
		IDs ids = twitter.getFriendsIDs(screenName, -1);
		// TODO Funciona mas é improdutivo Ainda realiza inumeras requisições,
		// procurar metodo ou
		// serviço que possa retornar os dados em uma requisição excede o RATE
		// LIMIT
		for (long id : ids.getIDs()) {
			listUsers.add(twitter.showUser(id));
		}

		return listUsers;
	}

	public static IDs retornarListaAmigosIds(String screenName,AuthEnum authEnum)
			throws Exception {

		Twitter twitter = createTwitterFactory(authEnum).getInstance();
		IDs ids = twitter.getFriendsIDs(screenName, -1);

		return ids;
	}
	
	public static Map<String, Long> retornarUserId(List<String> list, AuthEnum authEnum) throws Exception{
		Map<String, Long> users = new LinkedHashMap<String, Long>();
		Twitter twitter = createTwitterFactory(authEnum).getInstance();
		
		for(String screenName : list){
			users.put(screenName, twitter.showUser(screenName).getId());
		}
		
		return users;
	}

	public static TwitterFactory createTwitterFactory(AuthEnum authEnum) throws Exception{
		TwitterFactory tf = new TwitterFactory(createConfigurationBuilder(authEnum));
		return tf;
	}
	
	public static Configuration createConfigurationBuilder(AuthEnum authEnum) throws Exception{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(authEnum.getConsumerToken())
				.setOAuthConsumerSecret(authEnum.getConsumerSecret())
				.setOAuthAccessToken(authEnum.getAccessToken())
				.setOAuthAccessTokenSecret(authEnum.getAccessSecret());
		return cb.build();
	}

	public static boolean isFollowed(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.showFriendship(source, target)
					.isSourceFollowedByTarget();

		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente
			return isFollowed(source, target, twitter);
		}
	}

	public static boolean isBlocking(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.showFriendship(source, target)
					.isSourceBlockingTarget();
		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente
			return isBlocking(source, target, twitter);
		}
	}

	public static boolean isFollowing(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.showFriendship(source, target)
					.isSourceFollowingTarget();
		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente
			return isFollowing(source, target, twitter);
		}
	}

	public static boolean isNotificationEnabled(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.showFriendship(source, target)
					.isSourceNotificationsEnabled();
		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente
			return isNotificationEnabled(source, target, twitter);
		}
	}

	public static boolean isRelationship(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.existsFriendship(source, target);
		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente
			return isRelationship(source, target, twitter);
		}
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("moacirlmjr");
		list.add("Danyllo_Wagner");
		
		try {
			TwitterUtil.retornarUserId(list, AuthEnum.MOACIR_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}