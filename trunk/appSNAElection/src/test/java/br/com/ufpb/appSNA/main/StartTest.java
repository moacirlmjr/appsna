package br.com.ufpb.appSNA.main;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.ufpb.appSNA.model.enumeration.AuthEnum;
import br.com.ufpb.appSNA.model.listener.ElectionStatusListener;
import br.com.ufpb.appSNA.model.thread.TwitterStreamElection2012;
import br.com.ufpb.appSNA.util.TwitterUtil;

public class StartTest {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.MOACIR_KEY))
					.getInstance();

			String track[] = { "Luciano_Cartaxo", "Luciano Cartaxo",
					"luciano_cartaxo", "luciano cartaxo", "#VOTE13","#Vote13",
					"zé maranhão", "Zé Maranhão", "Zé_Maranhão", "ze_maranhão", "#Equipe15", "zemaranhaojp",
					"Cicero Lucena", "Cicero_lucena", "cicero_lucena", "cicero_lucena" , "#Cicero45","cicerolucena",
					"Estela Izabel", "estela izabel", "Estela_Izabel", "estela_izabel","#Estela40", "cicerolucena"};
			long follow[] = { 128031815L,395489108L,49632512L,165390440L };
			
			
			FilterQuery filterQuery = new FilterQuery();
			filterQuery.follow(follow);
			filterQuery.track(track);

			ElectionStatusListener statusListener = new ElectionStatusListener();
			statusListener.setTermos(track);

			twitterStream.addListener(statusListener);
			twitterStream.filter(filterQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
