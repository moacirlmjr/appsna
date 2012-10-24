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
import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;
import br.com.ufpb.appSNAUtil.model.thread.AccountThread;
import br.com.ufpb.appSNAUtil.model.thread.VerificarListaReady;

public class TwitterUtil {
	// TODO tratar a exceção twitter exception e fazer um algoritmo para
	// realizar a troca de chaves quando isso ocorrer
	// TODO fazer com que cada metodo tenha como parametro o bean Twitter
	// TODO fazer metodo para pesquisar sobre a query desejada na timeline de
	// algum usuario
	// TODO verificar durante a analise a eliminação de dados já analizados

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

	public static List<Long> retornarListaAmigosIdsList(String screenName,
			boolean isOnlyJP) throws Exception {
		List<Long> list = new ArrayList<Long>();
		IDs ids = null;
		int count = 0;
		try {
			ids = AccountCarrousel.CURRENT_ACCOUNT
					.getFriendsIDs(screenName, -1);
			for (long id : ids.getIDs()) {
				try {
					if (!isOnlyJP){
						User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(id);
						if(u.getLocation().replace('ã', 'a').toLowerCase()
								.equals("joao pessoa")){
							list.add(id);
						}
					}else{
						list.add(id);
					}
				} catch (TwitterException e) {
					AppSNALog.error(e.toString());
					if (e.getStatusCode() != 403) {
						tratarTwitterException(e);
					}
					
					if (!isOnlyJP){
						User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(id);
						if(u.getLocation().replace('ã', 'a').toLowerCase()
								.equals("joao pessoa")){
							list.add(id);
						}
					}else{
						list.add(id);
					}
				}
				count++;
			}

			return list;
		} catch (TwitterException e) {
			AppSNALog.error(e.toString());
			if (e.getStatusCode() != 403) {
				tratarTwitterException(e);
			}
			return retornarListaAmigosTratarException(screenName, ids, list,
					isOnlyJP, count);
		}
	}

	private static List<Long> retornarListaAmigosTratarException(
			String screenName, IDs ids, List<Long> list, boolean isOnlyJP,
			int count) throws Exception {

		for (int i = count; i < ids.getIDs().length; i++, count++) {
			if (!list.contains(ids.getIDs()[i])) {
				try {
					if (!isOnlyJP){
						User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(screenName);
						if(u.getLocation().replace('ã', 'a').toLowerCase()
								.equals("joao pessoa")){
							list.add(ids.getIDs()[i]);
						}
					}else{
						list.add(ids.getIDs()[i]);
					}
				} catch (TwitterException e) {
					AppSNALog.error(e.toString());
					if (e.getStatusCode() == 400) {
						tratarTwitterException(e);
						return retornarListaAmigosTratarException(screenName,
								ids, list, isOnlyJP, count);
					}
				}
			}
		}

		return list;
	}

	public static Map<String, Long> retornarUserId(List<String> list,
			boolean isOnlyJP) throws Exception {
		try {
			Map<String, Long> users = new LinkedHashMap<String, Long>();

			for (String screenName : list) {
				User u = AccountCarrousel.CURRENT_ACCOUNT.showUser(screenName);
				
				if (!isOnlyJP
						|| u.getLocation().replace('ã', 'a').toLowerCase()
								.equals("joao pessoa"))
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
			uto.setNome(u.getName() == null ? "não informado" : u.getName());
			uto.setScreename(u.getScreenName() == null ? "não informado" : u
					.getScreenName());
			uto.setBiografia(u.getDescription() == null ? "não informado" : u
					.getDescription());
			uto.setLocalização(u.getLocation() == null ? "não informado" : u
					.getLocation());
			uto.setTotalFollowers(String.valueOf(u.getFollowersCount()) == null ? "não informado"
					: u.getFollowersCount() + "");
			uto.setTotalFollowing(String.valueOf(u.getFriendsCount()) == null ? "não informado"
					: u.getFriendsCount() + "");
			uto.setTotalTweets(String.valueOf(u.getStatusesCount()) == null ? "não informado"
					: u.getStatusesCount() + "");
			uto.setURL(u.getURL() != null ? u.getURL().getHost()
					: "não informado");
			uto.setTimeZone(u.getTimeZone() == null ? "não informado" : u
					.getTimeZone());
			uto.setLinguagem(u.getLang() == null ? "não informado" : u
					.getLang());
			uto.setDataDeCriacao((u.getCreatedAt() == null ? "não informado"
					: String.valueOf(u.getCreatedAt())));
			uto.setURLImage((u.getProfileImageURL() == null ? "não informado"
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
		uto.setNome(u.getName() == null ? "não informado" : u.getName());
		uto.setScreename(u.getScreenName() == null ? "não informado" : u
				.getScreenName());
		uto.setBiografia(u.getDescription() == null ? "não informado" : u
				.getDescription());
		uto.setLocalização(u.getLocation() == null ? "não informado" : u
				.getLocation());
		uto.setTotalFollowers(String.valueOf(u.getFollowersCount()) == null ? "não informado"
				: u.getFollowersCount() + "");
		uto.setTotalFollowing(String.valueOf(u.getFriendsCount()) == null ? "não informado"
				: u.getFriendsCount() + "");
		uto.setTotalTweets(String.valueOf(u.getStatusesCount()) == null ? "não informado"
				: u.getStatusesCount() + "");
		uto.setURL(u.getURL() != null ? u.getURL().getHost() : "não informado");
		uto.setTimeZone(u.getTimeZone() == null ? "não informado" : u
				.getTimeZone());
		uto.setLinguagem(u.getLang() == null ? "não informado" : u.getLang());
		uto.setDataDeCriacao((u.getCreatedAt() == null ? "não informado"
				: String.valueOf(u.getCreatedAt())));
		uto.setURLImage((u.getProfileImageURL() == null ? "não informado"
				: String.valueOf(u.getProfileImageURL())));

		return uto;
	}

	private static void tratarTwitterException(TwitterException e)
			throws InterruptedException {
		try {

			if (e.getRateLimitStatus() != null) {
				if (e.getRateLimitStatus().getRemainingHits() == 0) {

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
						at.setName("AccountThread-"
								+ AccountCarrousel.CURRENT_ACCOUNT
										.getOAuthAccessToken().getUserId());
						at.setTimeRemaining(timeRemaining);
						at.start();

						mutex.wait();
					}
				}
			}
		} catch (Exception ex) {
			AppSNALog.error(ex.toString());
		}
	}

	public static int getTotalFollowingGroup(List<String> listUsers) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("pKPaLdk6DofC7bfNNA1qw")
				.setOAuthConsumerSecret(
						"Ks5QO1enyC8Co5My1BVoSN9HVFDhFi8PKsfivr0Xs")
				.setOAuthAccessToken(
						"131686365-iWIbKNxKlgUK4Wa2gMx6Ojjsu62aKZNUDvNfbBN2")
				.setOAuthAccessTokenSecret(
						"2kPLmaJiDNEAplKIAlJz8Jhxf7JXgp0E00EoCjQi0");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		int totalFollowingGroup = 0;

		for (String u : listUsers) {
			try {
				User y = twitter.showUser(u);
				int t = y.getFriendsCount();
				System.out.println(y.getScreenName() + ": total de " + t + " amigos");
				totalFollowingGroup += t;

			} catch (TwitterException e) {
				AppSNALog.error(e.toString());
			}
		}

		return totalFollowingGroup;

	}
	
	
	public static int getTotalFollowersGroup(List<String> listUsers) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("pKPaLdk6DofC7bfNNA1qw")
				.setOAuthConsumerSecret(
						"Ks5QO1enyC8Co5My1BVoSN9HVFDhFi8PKsfivr0Xs")
				.setOAuthAccessToken(
						"131686365-iWIbKNxKlgUK4Wa2gMx6Ojjsu62aKZNUDvNfbBN2")
				.setOAuthAccessTokenSecret(
						"2kPLmaJiDNEAplKIAlJz8Jhxf7JXgp0E00EoCjQi0");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		int totalFollowersGroup = 0;

		for (String u : listUsers) {
			try {
				User y = twitter.showUser(u);
				int t = y.getFollowersCount();
				System.out.println(y.getScreenName() + ": total de " + t + " Followers");
				totalFollowersGroup += t;

			} catch (TwitterException e) {
				AppSNALog.error(e.toString());
			}
		}

		return totalFollowersGroup;

	}


	public static int getTotalFollowingGroups(List<User> listUsers) {

		int totalFollowingGroup = 0;

		for (User u : listUsers) {
			totalFollowingGroup += u.getFriendsCount();
		}

		return totalFollowingGroup;

	}
	
	
	public static int getTotalFollowersGroups(List<User> listUsers) {

		int totalFollowersGroup = 0;

		for (User u : listUsers) {
			totalFollowersGroup += u.getFollowersCount();
		}

		return totalFollowersGroup;

	}

	public static List<User> getRelationshipFromFriend(User source,
			User target, int rate) {
		List<User> listSource = new ArrayList<User>();
		List<User> listTarget = new ArrayList<User>();

		try {
			listSource = retornarListaAmigos(source.getScreenName());
			listTarget = retornarListaAmigos(target.getScreenName());
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

		List<User> listAux = new ArrayList<User>();

		for (User x : listSource) {
			for (User y : listTarget) {
				if (x.equals(y)) {
					if (listAux.size() <= rate) {
						listAux.add(x);
					} else {
						break;
					}
				}
			}
		}

		return listAux;

	}

	public static List<User> getRelationshipFromFriend(User source, User target) {
		
		List<User> listSource = new ArrayList<User>();
		List<User> listTarget = new ArrayList<User>();
		List<User> listAux = new ArrayList<User>();
		
		try {
			if(isFollowing(source.getScreenName(), target.getScreenName())|| isFollowing(target.getScreenName(), source.getScreenName())){
				listAux.add(source);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			listSource = retornarListaAmigos(source.getScreenName());
			listTarget = retornarListaAmigos(target.getScreenName());
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

		

		for (User x : listSource) {
			for (User y : listTarget) {
				if (x.equals(y)) {
					listAux.add(x);
				}

			}
		}

		if (!listAux.isEmpty()) {
			return listAux;
		} else {

			List<User> listSourceLevelTwo = new ArrayList<User>();

			for (User u : listSource) {

				try {
					listSourceLevelTwo = retornarListaAmigos(u.getScreenName());
				} catch (Exception e) {
					AppSNALog.error(e.toString());
				}

				for (User w : listSourceLevelTwo) {
					for (User z : listTarget) {
						if (w.equals(z)) {
							listAux.add(w);
						}

					}
				}
			}

			return listAux;

		}

	}

}