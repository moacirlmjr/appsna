package br.com.ufpb.appSNA.main;

import br.com.ufpb.appSNA.model.enumeration.AuthEnum;
import br.com.ufpb.appSNA.model.listener.ElectionStatusListener;
import br.com.ufpb.appSNA.model.thread.TwitterStreamElection2012;
import br.com.ufpb.appSNA.util.TwitterUtil;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Start {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.MOACIR_KEY))
					.getInstance();

			String track[] = { "Luciano_Cartaxo", "Luciano Cartaxo",
					"luciano_cartaxo", "luciano cartaxo", "#VOTE13" };
			long follow[] = { 128031815L };

			FilterQuery filterQuery = new FilterQuery();
			filterQuery.follow(follow);
			filterQuery.track(track);

			ElectionStatusListener statusListener = new ElectionStatusListener();
			statusListener.setTermos(track);

			TwitterStreamElection2012 thread = new TwitterStreamElection2012();
			thread.setFilterQuery(filterQuery);
			thread.setStatusListener(statusListener);
			thread.setTwitterStream(twitterStream);
			thread.setNomeArq("resultado.csv");
			thread.run();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
