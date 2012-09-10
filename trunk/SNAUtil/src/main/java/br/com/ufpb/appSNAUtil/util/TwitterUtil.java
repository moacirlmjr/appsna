package br.com.ufpb.appSNAUtil.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;
import br.com.ufpb.appSNAUtil.model.thread.AccountThread;

public class TwitterUtil {
	// TODO tratar a exceção twitter exception e fazer um algoritmo para
	// realizar a troca de chaves quando isso ocorrer
	// TODO fazer com que cada metodo tenha como parametro o bean Twitter
	// TODO fazer metodo para pesquisar sobre a query desejada na timeline de
	// algum usuario
	// TODO verificar durante a analise a eliminação de dados já analizados
	
	private static AtomicBoolean mutex;

	public static List<User> retornarListaAmigos(String screenName,
			AuthEnum authEnum) throws Exception {

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

	public static IDs retornarListaAmigosIds(String screenName,
			AuthEnum authEnum) throws Exception {

		Twitter twitter = createTwitterFactory(authEnum).getInstance();
		IDs ids = twitter.getFriendsIDs(screenName, -1);

		return ids;
	}

	public static List<Long> retornarListaAmigosIdsList(String screenName,
			AuthEnum authEnum) throws Exception {

		Twitter twitter = createTwitterFactory(authEnum).getInstance();
		IDs ids = twitter.getFriendsIDs(screenName, -1);
		List<Long> list = new ArrayList<Long>();
		for (long id : ids.getIDs()) {
			list.add(id);
		}

		return list;
	}

	public static Map<String, Long> retornarUserId(List<String> list) throws Exception {
		Twitter twitter = null;
		try {
			Map<String, Long> users = new LinkedHashMap<String, Long>();
			twitter = AccountCarrousel.CURRENT_ACCOUNT;

			for (String screenName : list) {
				users.put(screenName, twitter.showUser(screenName).getId());
			}

			return users;

		} catch (TwitterException e) {
			AppSNALog.error(e.toString());
			// TODO buscar uma nova autenticação e chamar o metodo novamente

			Long timeRemaining = ((long) e.getRateLimitStatus()
					.getResetTimeInSeconds() * 1000);
			mutex = new AtomicBoolean();
			mutex.set(true);
			AccountThread at = new AccountThread();
			synchronized (mutex) {
				at.setAccount(twitter);
				at.setMutex(mutex);
				at.setTimeRemaining(timeRemaining);
				at.start();
				
				mutex.wait();
			}

			return retornarUserId(list);
		}
	}

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

	public static boolean isFollowed(String source, String target,
			Twitter twitter) throws Exception {
		try {
			return twitter.showFriendship(source, target)
					.isSourceFollowedByTarget();

		} catch (TwitterException e) {
			AppSNALog.error(e);
			// TODO buscar uma nova autenticação e chamar o metodo novamente

			Long timeRemaining = ((long) e.getRateLimitStatus()
					.getResetTimeInSeconds() * 1000);

			AccountThread at = new AccountThread();
			at.setAccount(twitter);
			at.setTimeRemaining(timeRemaining);
			at.start();

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
			AccountCarrousel.startCurrentAccount(AuthEnum.DANYLLO_KEY);
			AccountCarrousel.startListReady();
			TwitterUtil.retornarUserId(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}