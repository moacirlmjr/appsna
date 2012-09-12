package br.com.ufpb.appSNAUtil.util;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;

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
		int count = 0;
		try {
			for (AuthEnum auth : AuthEnum.values()) {
				if (count == 0) {
					CURRENT_ACCOUNT = TwitterUtil.createTwitterFactory(auth)
							.getInstance();
				} else {
					Twitter twitter = TwitterUtil.createTwitterFactory(auth)
							.getInstance();
					LIST_ACOUNTS_READY.add(twitter);
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
