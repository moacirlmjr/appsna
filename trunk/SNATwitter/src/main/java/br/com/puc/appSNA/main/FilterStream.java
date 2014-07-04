package br.com.puc.appSNA.main;

import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.puc.appSNA.model.enumeration.AuthEnum;
import br.com.puc.appSNA.model.listener.SNATwitterStatusListener;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.appSNA.util.TwitterUtil;

public class FilterStream {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		AppSNALog.info("Começando Mineração Twitter: " + Constantes.LANG);
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.DANYLLO_KEY))
					.getInstance();
			
			StatusListener listener = new SNATwitterStatusListener();

		    twitterStream.addListener(listener);
		    twitterStream.sample();

		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

}
