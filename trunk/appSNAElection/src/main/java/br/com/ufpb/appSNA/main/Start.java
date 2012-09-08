package br.com.ufpb.appSNA.main;

import java.util.LinkedList;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.ufpb.appSNA.model.beans.to.EntradaTO;
import br.com.ufpb.appSNA.model.enumeration.AuthEnum;
import br.com.ufpb.appSNA.model.listener.ElectionStatusListener;
import br.com.ufpb.appSNA.model.thread.TwitterStreamElection2012;
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
			List<EntradaTO> listEntradaTO = new LinkedList<EntradaTO>();
			EntradaTO to = new EntradaTO();
			for (String key : ec.getKeys()) {
				if ((to.getScreen_name() != null && !to.getScreen_name()
						.equals(""))
						&& (to.getTermos() != null && !to.getTermos()
								.equals("")) && to.getUserId() != 0) {
					listEntradaTO.add(to);
					to = new EntradaTO();
				}

				String valor = ec.getEntrada(key);
				if (key.contains("screenName")) {
					to.setScreen_name(valor);
				} else if (key.contains("userid")) {
					to.setUserId(Long.parseLong(valor));
				} else if (key.contains("termos")) {
					to.setTermos(valor);
				}
			}

			FilterQuery filterQuery = new FilterQuery();
			ElectionStatusListener statusListener = new ElectionStatusListener();
			TwitterStreamElection2012 thread = new TwitterStreamElection2012();

			for (EntradaTO eTo : listEntradaTO) {
				String track[] = eTo.getTermos().split(",");
				long follow[] = { eTo.getUserId() };

				filterQuery.follow(follow);
				filterQuery.track(track);

				statusListener.setTermos(track);

				thread.setFilterQuery(filterQuery);
				thread.setStatusListener(statusListener);
				thread.setTwitterStream(twitterStream);
				thread.setNomeArq(eTo.getScreen_name() + ".csv");
				thread.run();

				filterQuery = new FilterQuery();
				statusListener = new ElectionStatusListener();
				thread = new TwitterStreamElection2012();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
