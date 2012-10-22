package br.com.ufpb.appSNAUtil.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.IDs;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;
import br.com.ufpb.appSNAUtil.model.thread.AccountThread;
import br.com.ufpb.appSNAUtil.model.thread.VerificarListaReady;

public class TwitterUtil {
	// TODO tratar a exce��o twitter exception e fazer um algoritmo para
	// realizar a troca de chaves quando isso ocorrer
	// TODO fazer com que cada metodo tenha como parametro o bean Twitter
	// TODO fazer metodo para pesquisar sobre a query desejada na timeline de
	// algum usuario
	// TODO verificar durante a analise a elimina��o de dados j� analizados

	private static AtomicBoolean mutex;
	private static AtomicBoolean mutexListReady;

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
			// TODO Funciona mas � improdutivo Ainda realiza inumeras
			// requisi��es,
			// procurar metodo ou
			// servi�o que possa retornar os dados em uma requisi��o excede o
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

	public static List<Long> retornarListaAmigosIdsList(String screenName,
			boolean isOnlyJP) throws Exception {
		try {
			IDs ids = AccountCarrousel.CURRENT_ACCOUNT.getFriendsIDs(
					screenName, -1);
			List<Long> list = new ArrayList<Long>();
			for (long id : ids.getIDs()) {
				User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(id);
				if (u.getLocation().replace('�', 'a').toLowerCase()
						.equals("joao pessoa")
						|| !isOnlyJP)
					list.add(id);
			}

			return list;
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);
			return retornarListaAmigosIdsList(screenName, isOnlyJP);
		}
	}

	public static Map<String, Long> retornarUserId(List<String> list,
			boolean isOnlyJP) throws Exception {
		try {
			Map<String, Long> users = new LinkedHashMap<String, Long>();

			for (String screenName : list) {
				User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(screenName);
				if (u.getLocation().replace('�', 'a').toLowerCase()
						.equals("joao pessoa")
						|| !isOnlyJP)
					users.put(screenName, u.getId());
			}

			return users;

		} catch (TwitterException e) {
			AppSNALog.error(e.toString());

			tratarTwitterException(e);

			return retornarUserId(list, isOnlyJP);
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

	public static UserTO getUserData(long idUser) throws Exception {

		try {
			User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(idUser);
			UserTO uto = new UserTO();

			uto.setId(String.valueOf(u.getId()));
			uto.setNome(u.getName() == null ? "n�o informado" : u.getName());
			uto.setScreename(u.getScreenName() == null ? "n�o informado" : u
					.getScreenName());
			uto.setBiografia(u.getDescription() == null ? "n�o informado" : u
					.getDescription());
			uto.setLocaliza��o(u.getLocation() == null ? "n�o informado" : u
					.getLocation());
			uto.setTotalFollowers(String.valueOf(u.getFollowersCount()) == null ? "n�o informado"
					: u.getFollowersCount() + "");
			uto.setTotalFollowing(String.valueOf(u.getFriendsCount()) == null ? "n�o informado"
					: u.getFriendsCount() + "");
			uto.setTotalTweets(String.valueOf(u.getStatusesCount()) == null ? "n�o informado"
					: u.getStatusesCount() + "");
			uto.setURL(u.getURL() != null ? u.getURL().getHost()
					: "n�o informado");
			uto.setTimeZone(u.getTimeZone() == null ? "n�o informado" : u
					.getTimeZone());
			uto.setLinguagem(u.getLang() == null ? "n�o informado" : u
					.getLang());
			uto.setDataDeCriacao((u.getCreatedAt() == null ? "n�o informado"
					: String.valueOf(u.getCreatedAt())));
			uto.setURLImage((u.getProfileImageURL() == null ? "n�o informado"
					: String.valueOf(u.getProfileImageURL())));

			return uto;

		} catch (TwitterException e) {
			AppSNALog.error(e.toString());
			tratarTwitterException(e);

			return getUserData(idUser);
		}

	}

	public static UserTO getUserData(User u) {

		UserTO uto = new UserTO();

		uto.setId(String.valueOf(u.getId()));
		uto.setNome(u.getName() == null ? "n�o informado" : u.getName());
		uto.setScreename(u.getScreenName() == null ? "n�o informado" : u
				.getScreenName());
		uto.setBiografia(u.getDescription() == null ? "n�o informado" : u
				.getDescription());
		uto.setLocaliza��o(u.getLocation() == null ? "n�o informado" : u
				.getLocation());
		uto.setTotalFollowers(String.valueOf(u.getFollowersCount()) == null ? "n�o informado"
				: u.getFollowersCount() + "");
		uto.setTotalFollowing(String.valueOf(u.getFriendsCount()) == null ? "n�o informado"
				: u.getFriendsCount() + "");
		uto.setTotalTweets(String.valueOf(u.getStatusesCount()) == null ? "n�o informado"
				: u.getStatusesCount() + "");
		uto.setURL(u.getURL() != null ? u.getURL().getHost() : "n�o informado");
		uto.setTimeZone(u.getTimeZone() == null ? "n�o informado" : u
				.getTimeZone());
		uto.setLinguagem(u.getLang() == null ? "n�o informado" : u.getLang());
		uto.setDataDeCriacao((u.getCreatedAt() == null ? "n�o informado"
				: String.valueOf(u.getCreatedAt())));
		uto.setURLImage((u.getProfileImageURL() == null ? "n�o informado"
				: String.valueOf(u.getProfileImageURL())));

		return uto;
	}

	private static void tratarTwitterException(TwitterException e)
			throws InterruptedException {
		try {

			Long timeRemaining = ((long) e.getRateLimitStatus()
					.getSecondsUntilReset() * 1000);
			if (AccountCarrousel.LIST_ACOUNTS_READY.size() == 0) {
				mutexListReady = new AtomicBoolean();
				mutexListReady.set(true);
				VerificarListaReady vl = new VerificarListaReady();
				synchronized (mutexListReady) {
					vl.setName("VerificarListaReady");
					vl.setMutexListReady(mutexListReady);
					vl.start();
					mutexListReady.wait();
				}
			}
			mutex = new AtomicBoolean();
			mutex.set(true);
			AccountThread at = new AccountThread();
			synchronized (mutex) {
				at.setMutex(mutex);
				at.setName("AccountThread-"+timeRemaining);
				at.setTimeRemaining(timeRemaining);
				at.start();

				mutex.wait();
			}
		} catch (Exception ex) {
			AppSNALog.error(ex.toString());
		}
	}

}