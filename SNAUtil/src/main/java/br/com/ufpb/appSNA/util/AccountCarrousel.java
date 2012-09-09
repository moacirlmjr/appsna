package br.com.ufpb.appSNA.util;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNA.model.enumeration.AuthEnum;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class AccountCarrousel {

	public static List<Twitter> LIST_ACOUNTS_READY;
	public static List<Twitter> LIST_ACOUNTS_WAIT;
	public static Twitter CURRENT_ACCOUNT;

	public static final Object LOCK = new Object();

	public static void startListReady() {
		LIST_ACOUNTS_READY = new ArrayList<Twitter>();
		LIST_ACOUNTS_WAIT = new ArrayList<Twitter>();
		try {
			for (AuthEnum auth : AuthEnum.values()) {
				Twitter twitter = TwitterUtil.createTwitterFactory(auth)
						.getInstance();
				LIST_ACOUNTS_READY.add(twitter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startCurrentAccount(AuthEnum authEnum) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(authEnum.getConsumerToken())
				.setOAuthConsumerSecret(authEnum.getConsumerSecret())
				.setOAuthAccessToken(authEnum.getAccessToken())
				.setOAuthAccessTokenSecret(authEnum.getAccessSecret());

		TwitterFactory tf = new TwitterFactory(cb.build());
		CURRENT_ACCOUNT = tf.getInstance();
	}

}
