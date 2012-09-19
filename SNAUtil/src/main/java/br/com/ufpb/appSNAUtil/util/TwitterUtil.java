package br.com.ufpb.appSNAUtil.util;

import java.util.ArrayList;
import java.util.HashMap;
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

	public static List<User> retornarListaAmigos(String screenName)
			throws Exception {

		try {
			List<User> listUsers = new LinkedList<User>();
			IDs ids = AccountCarrousel.CURRENT_ACCOUNT.getFriendsIDs(
					screenName, -1);
			// TODO Funciona mas é improdutivo Ainda realiza inumeras
			// requisições,
			// procurar metodo ou
			// serviço que possa retornar os dados em uma requisição excede o
			// RATE
			// LIMIT
			for (long id : ids.getIDs()) {
				listUsers.add(AccountCarrousel.CURRENT_ACCOUNT.showUser(id));
			}
			return listUsers;
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);
			return retornarListaAmigos(screenName);
		}

	}

	public static IDs retornarListaAmigosIds(String screenName)
			throws Exception {
		try {
			IDs ids = AccountCarrousel.CURRENT_ACCOUNT.getFriendsIDs(
					screenName, -1);
			return ids;
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return retornarListaAmigosIds(screenName);
		}
	}

	public static List<Long> retornarListaAmigosIdsList(String screenName)
			throws Exception {
		try {
			IDs ids = AccountCarrousel.CURRENT_ACCOUNT.getFriendsIDs(
					screenName, -1);
			List<Long> list = new ArrayList<Long>();
			for (long id : ids.getIDs()) {
				list.add(id);
			}

			return list;
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);
			return retornarListaAmigosIdsList(screenName);
		}
	}

	public static Map<String, Long> retornarUserId(List<String> list)
			throws Exception {
		try {
			Map<String, Long> users = new LinkedHashMap<String, Long>();

			for (String screenName : list) {
				users.put(screenName, AccountCarrousel.CURRENT_ACCOUNT
						.showUser(screenName).getId());
			}

			return users;

		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return retornarUserId(list);
		}
	}

	public static boolean isFollowed(String source, String target)
			throws Exception {
		try {
			return AccountCarrousel.CURRENT_ACCOUNT.showFriendship(source,
					target).isSourceFollowedByTarget();

		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return isFollowed(source, target);
		}
	}

	public static boolean isBlocking(String source, String target)
			throws Exception {
		try {
			return AccountCarrousel.CURRENT_ACCOUNT.showFriendship(source,
					target).isSourceBlockingTarget();
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return isBlocking(source, target);
		}
	}

	public static boolean isFollowing(String source, String target)
			throws Exception {
		try {
			return AccountCarrousel.CURRENT_ACCOUNT.showFriendship(source,
					target).isSourceFollowingTarget();
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return isFollowing(source, target);
		}
	}

	public static boolean isNotificationEnabled(String source, String target)
			throws Exception {
		try {
			return AccountCarrousel.CURRENT_ACCOUNT.showFriendship(source,
					target).isSourceNotificationsEnabled();
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return isNotificationEnabled(source, target);
		}
	}

	public static boolean isRelationship(String source, String target)
			throws Exception {
		try {
			return AccountCarrousel.CURRENT_ACCOUNT.existsFriendship(source,
					target);
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return isRelationship(source, target);
		}
	}

	public static HashMap<String, String> getUserData(Twitter twitter,
			long idUser) throws TwitterException {

		HashMap<String, String> userData = new HashMap<String, String>();
		User u = twitter.showUser(idUser);
		
		userData.put("Nome", u.getName());
		userData.put("Screename", u.getScreenName());
		userData.put("Biografia", u.getDescription());		
		userData.put("Localização", u.getLocation());
		String totalFollower = String.valueOf(u.getFollowersCount());
		userData.put("TotalFollowers", totalFollower);
		String friendsCount = String.valueOf(u.getFriendsCount());
		userData.put("TotalFollowing", friendsCount );
		String totalTweets = String.valueOf(u.getStatusesCount());
		userData.put("TotalTweets", totalTweets );
		userData.put("Status", u.getStatus().getText());
		userData.put("URL", u.getURL().getHost());
		userData.put("TimeZone", u.getTimeZone());
		userData.put("Linguagem", u.getLang());

		return userData;
	}

	public static HashMap<String, String> getUserData(User u){

		HashMap<String, String> userData = new HashMap<String, String>();

		userData.put("Nome", u.getName());
		userData.put("Screename", u.getScreenName());
		userData.put("Biografia", u.getDescription());		
		userData.put("Localização", u.getLocation());
		String totalFollower = String.valueOf(u.getFollowersCount());
		userData.put("TotalFollowers", totalFollower);
		String friendsCount = String.valueOf(u.getFriendsCount());
		userData.put("TotalFollowing", friendsCount );
		String totalTweets = String.valueOf(u.getStatusesCount());
		userData.put("TotalTweets", totalTweets );
		userData.put("Status", u.getStatus().getText());
		userData.put("URL", u.getURL().getHost());
		userData.put("TimeZone", u.getTimeZone());
		userData.put("Linguagem", u.getLang());

		return userData;
	}

	private static void tratarTwitterException(TwitterException e)
			throws InterruptedException {
		Long timeRemaining = ((long) e.getRateLimitStatus()
				.getResetTimeInSeconds() * 1000);
		mutex = new AtomicBoolean();
		mutex.set(true);
		AccountThread at = new AccountThread();
		synchronized (mutex) {
			at.setMutex(mutex);
			at.setTimeRemaining(timeRemaining);
			at.start();

			mutex.wait();
		}
	}

}