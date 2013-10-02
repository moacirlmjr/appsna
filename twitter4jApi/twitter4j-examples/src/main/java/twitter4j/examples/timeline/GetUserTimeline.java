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

package twitter4j.examples.timeline;

import java.util.List;

import junit.framework.TestResult;
import twitter4j.Paging;
import twitter4j.PagingTest;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class GetUserTimeline {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        // gets Twitter instance with default credentials
    	ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("9D8Q5IFh9KDSUpFgFwVEZg")
				.setOAuthConsumerSecret("amWG2soLvbc5Uk9hXYck185cHH4qVZ2vI803avAQw")
				.setOAuthAccessToken("69753774-BlHvqdHCJHY82UNh4kyVGxdJRrrLexJxh6Abvq43Q")
				.setOAuthAccessTokenSecret("saKgrvdXtv5qjwtlFXAu62IvAGSrh4w2ICmjfMV1yvI");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            List<Status> statuses;
            String user = "moacirlmjr";
            statuses = twitter.getUserTimeline("IVANILDOPB",new Paging(1, 1000));
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText() + " - " + status.getCreatedAt());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
