package br.com.ufpb.appSNAElection.model.listener;

import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import br.com.ufpb.appSNAElection.model.beans.Resultado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAElection.model.dao.ResultadoDAO;
import br.com.ufpb.appSNAElection.model.dao.ResultadoDAOImpl;
import br.com.ufpb.appSNAElection.model.dao.TermoDAO;
import br.com.ufpb.appSNAElection.model.dao.TermoDAOImpl;
import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class ElectionStatusListener implements StatusListener {

	private String termos[];

	@Override
	public void onStatus(Status status) {
		AppSNALog.info("@" + status.getUser().getScreenName() + " - "
				+ status.getText());
		try {
			TermoDAO tDAO = new TermoDAOImpl();
			ResultadoDAO rDAO = new ResultadoDAOImpl();
			
			Resultado r = new Resultado();
			r.setScreen_name("@"+status.getUser().getScreenName());
			r.setStatus(status.getText());
			
			if (termos.length != 0) {
				for (String termo : termos) {
					if (status.getText().toLowerCase()
							.contains(termo.toLowerCase())) {
						Termo termoBd =  tDAO.getTermoByConteudo(termo);
						r.setMonitorado_id(termoBd.getMonitorado_id());
						r.setTermoId(termoBd.getId());
						break;
					}

				}
			}
			r.setData(status.getCreatedAt());
			if (status.getGeoLocation() != null) {
				r.setLatitude(status.getGeoLocation().getLatitude()+"");
				r.setLongitude(status.getGeoLocation().getLongitude()+"");
			} else {
				r.setLatitude("");
				r.setLongitude("");
			}
			
			rDAO.create(r);
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO - NOTHING TO DO
		AppSNALog.info("Got a status deletion notice id:"
				+ statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO - NOTHING TO DO
		AppSNALog
				.info("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO - NOTHING TO DO
		AppSNALog.info("Got scrub_geo event userId:" + userId
				+ " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO - NOTHING TO DO
		AppSNALog.info("Got stall warning:" + warning);
	}

	@Override
	public void onException(Exception ex) {
		AppSNALog.error(ex.toString());
	}

	public String[] getTermos() {
		return termos;
	}

	public void setTermos(String[] termos) {
		this.termos = termos;
	}

}
