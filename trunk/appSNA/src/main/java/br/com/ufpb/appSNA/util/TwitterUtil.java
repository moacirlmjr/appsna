package br.com.ufpb.appSNA.util;

import java.util.LinkedList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONObject;

public class TwitterUtil {

	private static final String friendsIdsRest = "http://api.twitter.com/1/friends/ids.json?screen_name=";
	
	public static List<User> retornarListaAmigos(String screenName) throws Exception{
		
		String result;
		result = URLUtil.obterResultadoUrl(friendsIdsRest + screenName);
		JSONObject resultJson = new JSONObject(result);
		JSONArray arrayIds = resultJson.getJSONArray("ids");
		Twitter twitter = createTwitterFactory().getInstance();
		List<User> listUsers = new LinkedList<User>();
		
		//TODO Funciona mas é improdutivo Ainda realiza inumeras requisições, procurar metodo ou serviço que possa retornar os dados em uma requisição
		for(int i = 0; i < arrayIds.length(); i++){
			String id = arrayIds.getString(i);
			listUsers.add(twitter.showUser(Long.parseLong(id)));
		}
		
		return listUsers;
	}
	
	public static TwitterFactory createTwitterFactory(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey(Constantes.consumerToken)
    	  .setOAuthConsumerSecret(Constantes.consumerSecret)
    	  .setOAuthAccessToken(Constantes.accessToken)
    	  .setOAuthAccessTokenSecret(Constantes.tokenSecret);
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	return tf;
	}
	
	public static void main(String[] args) {
		try {
			TwitterUtil.retornarListaAmigos("@moacirlmjr");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
