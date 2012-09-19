package br.com.ufpb.appSNAUtil;

import java.util.HashMap;
import java.util.Map.Entry;

import br.com.ufpb.appSNAUtil.util.TwitterUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TesteTwitterUtil {
	
	public static void main(String[] args) throws TwitterException {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey("PEwerwPBLopBrxrdWCdCHQ")
    	  .setOAuthConsumerSecret("oLGCU3gRTNboUPi1VjCORHKyp2h93YnodZpNqekIOOU")
    	  .setOAuthAccessToken("312660739-pFFGuPbuZBzOn7yJZJkt6LpVMJs8jhz71eXrwke8")
    	  .setOAuthAccessTokenSecret("JY4MtWT4VFivqnKU0OZR3pa2KK6akRsFIvy94B3SY");
    	
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();        
        
        
        User u = twitter.showUser(312660739);
        
        HashMap<String, String> dadosDoUsuario =  TwitterUtil.getUserData(u);
        
        for (Entry<String, String> entry : dadosDoUsuario.entrySet()){  
           System.out.println(entry.getKey() + ": " + entry.getValue()); 
        }  

       
		
		
	}

}
