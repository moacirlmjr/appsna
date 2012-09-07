package br.com.ufpb.appSNA.util;

import java.util.LinkedList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil {
	// TODO tratar a exceção twitter exception e fazer um algoritmo para
	// realizar a troca de chaves quando isso ocorrer
	// TODO fazer com que cada metodo tenha como parametro o bean Twitter
	// TODO fazer metodo para pesquisar sobre a query desejada na timeline de
	// algum usuario
	// TODO verificar durante a analise a eliminação de dados já analizados

	public static List<User> retornarListaAmigos(String screenName)
			throws Exception {

		Twitter twitter = createTwitterFactory().getInstance();
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

	public static TwitterFactory createTwitterFactory() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(Constantes.consumerTokenMoacir)
				.setOAuthConsumerSecret(Constantes.consumerSecretMoacir)
				.setOAuthAccessToken(Constantes.accessTokenMoacir)
				.setOAuthAccessTokenSecret(Constantes.tokenSecretMoacir);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf;
	}

	public static TwitterFactory createTwitterFactory(String consumerToken,
			String consumerSecret, String acessToken, String tokenSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerToken)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(acessToken)
				.setOAuthAccessTokenSecret(tokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf;
	}

	public static boolean isFollowed(String source, String target,
			Twitter twitter) throws Exception {
		try{
			return twitter.showFriendship(source, target)
				   .isSourceFollowedByTarget();
			
		}catch(TwitterException	e){
			AppSNALog.error(e.toString());
			//TODO buscar uma nova autenticação e chamar o metodo novamente
			return isFollowed(source, target, twitter);
		}
	}

	public static boolean isBlocking(String source, String target,
			Twitter twitter) throws Exception {
		try{
			return twitter.showFriendship(source, target).isSourceBlockingTarget();
		}catch(TwitterException	e){
			AppSNALog.error(e.toString());
			//TODO buscar uma nova autenticação e chamar o metodo novamente
			return isBlocking(source, target, twitter);
		}
	}

	public static boolean isFollowing(String source, String target,
			Twitter twitter) throws Exception {
		try{
			return twitter.showFriendship(source, target).isSourceFollowingTarget();
		}catch(TwitterException	e){
			AppSNALog.error(e.toString());
			//TODO buscar uma nova autenticação e chamar o metodo novamente
			return isFollowing(source, target, twitter);
		}
	}

	public static boolean isNotificationEnabled(String source, String target,
			Twitter twitter) throws Exception {
		try{
			return twitter.showFriendship(source, target)
			.isSourceNotificationsEnabled();
		}catch(TwitterException	e){
			AppSNALog.error(e.toString());
			//TODO buscar uma nova autenticação e chamar o metodo novamente
			return isNotificationEnabled(source, target, twitter);
		}
	}

	public static boolean isRelationship(String source, String target,
			Twitter twitter) throws Exception {
		try{
			return twitter.existsFriendship(source, target);
		}catch(TwitterException	e){
			AppSNALog.error(e.toString());
			//TODO buscar uma nova autenticação e chamar o metodo novamente
			return isRelationship(source, target, twitter);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(TwitterUtil.retornarListaAmigos("@moacirlmjr"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}