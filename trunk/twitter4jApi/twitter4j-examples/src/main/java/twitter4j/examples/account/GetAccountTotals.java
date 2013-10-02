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

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Gets account totals.
 * 
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetAccountTotals {
	/**
	 * Usage: java twitter4j.examples.account.GetAccountTotals
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
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

			User totals = twitter.showUser("EvangelistaJP");
			System.out.println("Updates: " + totals.getId());
			// System.out.println("Followers: " + totals.getFollowers());
			// System.out.println("Favorites: " + totals.getFavorites());
			// System.out.println("Friends: " + totals.getFriends());
			// System.out.println("id: " + twitter.getId());
			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get account totals: "
					+ te.getMessage());
			System.exit(-1);
		}
	}
}
