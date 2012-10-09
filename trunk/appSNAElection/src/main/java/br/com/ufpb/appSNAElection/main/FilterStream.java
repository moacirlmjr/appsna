package br.com.ufpb.appSNAElection.main;

import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAElection.model.dao.TermoDAO;
import br.com.ufpb.appSNAElection.model.dao.TermoDAOImpl;
import br.com.ufpb.appSNAElection.model.listener.ElectionStatusListener;
import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class FilterStream {

	public static void main(String[] args) {

		TwitterStream twitterStream;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil.createConfigurationBuilder(AuthEnum.MOACIR_KEY2))
					.getInstance();

			MonitoradoDAO mDAO = new MonitoradoDAOImpl();
			TermoDAO tDAO = new TermoDAOImpl();

			List<Monitorado> listMonitorados = mDAO.listMonitorandos();
			String monitadosId = "";
			int count = 0;
			for(Monitorado m : listMonitorados){
				if(count++ == (listMonitorados.size() - 1)){
					monitadosId += m.getId();
				}else{
					monitadosId += m.getId() + ",";
				}
			}
			
			List<Termo> listTermos = tDAO.getTermosByMonitorandos(monitadosId);  
			
			long[] followArray = new long[listMonitorados.size()];
			String[] track = new String[listTermos.size()];
			count = 0;
			for(Monitorado m : listMonitorados){
				followArray[count++] = m.getTwitterId();
			}
			
			count = 0;
			for(Termo t: listTermos){
				track[count++] = t.getConteudo();
			}
			
			FilterQuery filterQuery = new FilterQuery();
			ElectionStatusListener statusListener = new ElectionStatusListener();

			filterQuery.follow(followArray);
			filterQuery.track(track);

			statusListener.setTermos(track);

			twitterStream.addListener(statusListener);
			twitterStream.filter(filterQuery);

		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

}
