/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j.examples.account;

import twitter4j.HashtagEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Verifies credentials.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class VerifyCredentials {
    /**
     * Usage: java twitter4j.examples.account.VerifyCredentials
     *
     * @param args message
     */
    public static void main(String[] args) {
        try {
        	ConfigurationBuilder cb = new ConfigurationBuilder();
        	cb.setDebugEnabled(true)
        	  .setOAuthConsumerKey("PEwerwPBLopBrxrdWCdCHQ")
        	  .setOAuthConsumerSecret("oLGCU3gRTNboUPi1VjCORHKyp2h93YnodZpNqekIOOU")
        	  .setOAuthAccessToken("312660739-pFFGuPbuZBzOn7yJZJkt6LpVMJs8jhz71eXrwke8")
        	  .setOAuthAccessTokenSecret("JY4MtWT4VFivqnKU0OZR3pa2KK6akRsFIvy94B3SY");
        	
        	TwitterFactory tf = new TwitterFactory(cb.build());
        	Twitter twitter = tf.getInstance();

            User user = twitter.verifyCredentials();

            //Como pegar dados de um usuario para criação do relatorio
            User u = twitter.showUser("Danyllo_Wagner");
            System.out.println("Remaining hits: " + u.getRateLimitStatus());
            System.out.println("");
            System.out.println("relatorio de dados do usuario");
            System.out.println("");
            System.out.println("biografia: " + u.getDescription());            
            System.out.println("Nome: " + u.getName());
            System.out.println("Localização: " + u.getLocation());
            System.out.println("Screen name: " + u.getScreenName());   
            String te = String.valueOf(u.getFollowersCount());
            System.out.println("Total Followers: " + te);
            System.out.println("Total Following: " + u.getFriendsCount());
            System.out.println("Total de tweet: " + u.getStatusesCount());          
            System.out.println("Status: " + u.getStatus().getText());
            System.out.println("URL: " + u.getURL());
            System.out.println("Listed Count: " + u.getListedCount());
            System.out.println("TimeZone: " + u.getTimeZone());
            System.out.println("Linguagem: " + u.getLang());
            System.out.println("URL Image");
            System.out.println("UTC" + u.getUtcOffset());
           
            System.out.println("");
            System.out.println("");
            System.out.println("URL Entities");
            URLEntity [] ue = u.getStatus().getURLEntities();
            for(URLEntity url: ue){
            	System.out.println(url.getDisplayURL());
            	System.out.println(url.getExpandedURL());
            	System.out.println(url.getURL());
            }
            
            System.out.println("");
            System.out.println("");      
            System.out.println("User Entities");
            UserMentionEntity [] ume = u.getStatus().getUserMentionEntities();
            for(UserMentionEntity usermention : ume){
            	System.out.println(usermention.getName());
            	System.out.println(usermention.getScreenName());
            }
            
            System.out.println("");
            System.out.println("");
            System.out.println("Hashtag Entities");
            HashtagEntity [] hte =  u.getStatus().getHashtagEntities();
            for(HashtagEntity ht: hte){
            	System.out.println(ht.getText());
            }

            
            
            System.out.println("Successfully verified credentials of " + user.getScreenName());
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to verify credentials: " + te.getMessage());
            System.exit(-1);
        }
    }
}
