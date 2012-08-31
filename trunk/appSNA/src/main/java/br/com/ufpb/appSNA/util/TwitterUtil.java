package br.com.ufpb.appSNA.util;

import java.util.LinkedList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil {

	public static List<User> retornarListaAmigos(String screenName) throws Exception{
		
		Twitter twitter = createTwitterFactory().getInstance();
		List<User> listUsers = new LinkedList<User>();
		IDs ids = twitter.getFriendsIDs(screenName, -1);
		//Funciona mas � improdutivo Ainda realiza inumeras requisi��es, procurar metodo ou 
		//servi�o que possa retornar os dados em uma requisi��o excede o RATE LIMIT
		for(long id : ids.getIDs()){
			listUsers.add(twitter.showUser(id));
		}
		
		return listUsers;
	}
	
	public static TwitterFactory createTwitterFactory(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey(Constantes.consumerTokenMoacir)
    	  .setOAuthConsumerSecret(Constantes.consumerSecretMoacir)
    	  .setOAuthAccessToken(Constantes.accessTokenMoacir)
    	  .setOAuthAccessTokenSecret(Constantes.tokenSecretMoacir);
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	return tf;
	}
	
	public static boolean isFollowed(String source, String target) throws TwitterException{
		Twitter twitter = createTwitterFactory().getInstance();
		Relationship b = twitter.showFriendship(source, target);
		return b.isSourceFollowedByTarget();
	}
	
	public static boolean isBlocking(String source, String target) throws TwitterException{
		Twitter twitter = createTwitterFactory().getInstance();
		Relationship b = twitter.showFriendship(source, target);
		return b.isSourceBlockingTarget();
	}
	
	public static boolean isFollowing(String source, String target) throws TwitterException{
		Twitter twitter = createTwitterFactory().getInstance();
		Relationship b = twitter.showFriendship(source, target);
		return b.isSourceFollowingTarget();
	}
	
	public static boolean isNotificationEnabled(String source, String target) throws TwitterException{
		Twitter twitter = createTwitterFactory().getInstance();
		Relationship b = twitter.showFriendship(source, target);
		return b.isSourceNotificationsEnabled();
	}
	
	public static boolean isRelationship(String source, String target) throws TwitterException{
		Twitter twitter = createTwitterFactory().getInstance();
		boolean b = twitter.existsFriendship(source, target);
		return b;
	}
	
		
	public static void main(String[] args) {
		try {
			System.out.println(TwitterUtil.retornarListaAmigos("@moacirlmjr"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}