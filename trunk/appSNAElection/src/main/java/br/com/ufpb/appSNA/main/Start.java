package br.com.ufpb.appSNA.main;

import java.util.ArrayList;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.ufpb.appSNA.model.enumeration.AuthEnum;
import br.com.ufpb.appSNA.model.listener.ElectionStatusListener;
import br.com.ufpb.appSNA.util.EntradaConfiguration;
import br.com.ufpb.appSNA.util.TwitterUtil;

public class Start {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.MOACIR_KEY))
					.getInstance();

			EntradaConfiguration ec = new EntradaConfiguration();
			
			String termosAll = "";
			List<Long> follow = new ArrayList<Long>();

			for (String key : ec.getKeys()) {

				String valor = ec.getEntrada(key);
				if (key.contains("screenName")) {
					termosAll += valor + ",";
					termosAll += valor.toLowerCase() + ",";
				} else if (key.contains("userid")) {
					follow.add(Long.parseLong(valor));
				} else if (key.contains("termos")) {
					termosAll += valor+",";
				}

			}
			
			int count = 0;
			long[] followArray = new long[follow.size()];
			String[] track = new String[termosAll.split(",").length];
			count = 0;
			for(Long l : follow){
				followArray[count++] = l.longValue();
			}
			
			track = termosAll.split(",");

			FilterQuery filterQuery = new FilterQuery();
			ElectionStatusListener statusListener = new ElectionStatusListener();

			filterQuery.follow(followArray);
			filterQuery.track(track);

			statusListener.setTermos(track);

			twitterStream.addListener(statusListener);
			twitterStream.filter(filterQuery);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
