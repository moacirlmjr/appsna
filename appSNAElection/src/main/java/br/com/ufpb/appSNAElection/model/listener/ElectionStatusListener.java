package br.com.ufpb.appSNAElection.model.listener;

import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import br.com.ufpb.appSNAElection.util.EntradaConfiguration;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.Constantes;

public class ElectionStatusListener implements StatusListener {

	private FileWriter file;
	private String termos[];

	@Override
	public void onStatus(Status status) {
		// TODO - PERSISTIR
		String resultado = "@" + status.getUser().getScreenName() + ";";
		AppSNALog.info("@" + status.getUser().getScreenName() + " - "
				+ status.getText());
		try {
			EntradaConfiguration ec = new EntradaConfiguration();
			String fileName = "";

			if (termos.length != 0) {
				for (String termo : termos) {
					if (status.getText().toLowerCase().contains(termo.toLowerCase())) {
						fileName = ec.getScreenNameCandidatoByTermo(termo) + ".csv"; 
						resultado += termo + ";";
						break;
					}

				}
			}
			resultado += status.getCreatedAt().getTime() + ";";
			if(status.getGeoLocation() != null){
				resultado += status.getGeoLocation().toString() + ";\n";
			}else{
				resultado += "NULL;\n";
			}
			this.openFile(Constantes.DIR_APPSNA + fileName);
			file.append(resultado);
			file.flush();
			this.closeFile();
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

	public void openFile(String fileName) {
		try {
			file = new FileWriter(fileName, true);
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	public void write(String text) {
		try {
			file.write(text);

		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	public void closeFile() {
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AppSNALog.error(e.toString());
		}

	}

	public FileWriter getFile() {
		return file;
	}

	public void setFile(FileWriter file) {
		this.file = file;
	}

	public String[] getTermos() {
		return termos;
	}

	public void setTermos(String[] termos) {
		this.termos = termos;
	}

}
