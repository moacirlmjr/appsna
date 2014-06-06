package br.com.puc.appSNA.main;

import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.puc.appSNA.model.enumeration.AuthEnum;
import br.com.puc.appSNA.model.listener.SNATwitterStatusListener;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.TwitterUtil;

public class FilterStream {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.MOACIR_KEY))
					.getInstance();
			
			StatusListener listener = new SNATwitterStatusListener();

		    twitterStream.addListener(listener);
		    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
		    twitterStream.sample();

		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

}
