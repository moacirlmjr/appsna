package br.com.ufpb.appSNAElection.model.listener;

import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import br.com.ufpb.appSNAElection.util.EntradaConfiguration;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;

public class ElectionStatusListener implements StatusListener {

	private String termos[];

	@Override
	public void onStatus(Status status) {
		String resultado = "@" + status.getUser().getScreenName() + ";";
		AppSNALog.info("@" + status.getUser().getScreenName() + " - "
				+ status.getText());
		try {
			EntradaConfiguration ec = new EntradaConfiguration();
			String fileName = "";

			if (termos.length != 0) {
				for (String termo : termos) {
					if (status.getText().toLowerCase()
							.contains(termo.toLowerCase())) {
						fileName = ec.getScreenNameCandidatoByTermo(termo
								.toLowerCase()) + ".csv";
						resultado += termo + ";";
						break;
					}

				}
			}
			resultado += status.getCreatedAt().getTime() + ";";
			if (status.getGeoLocation() != null) {
				resultado += status.getGeoLocation().getLatitude() + ","
						+ status.getGeoLocation().getLongitude() + ";";
			} else {
				resultado += "NULL;";
			}
			FileUtil.criaArquivo(Constantes.DIR_APPSNA + fileName, true);
			FileUtil.escreveNoArquivo(resultado);
			FileUtil.quebra();
			FileUtil.salvarArquivo();
		} catch (IOException e) {
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
