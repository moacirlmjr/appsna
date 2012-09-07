package br.com.ufpb.appSNA.model.listener;

import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class ElectionStatusListener implements StatusListener {

	public static final Object LOCK = new Object();

	private FileWriter file;
	private String termos[];
	private int count = 0;

	@Override
	public void onStatus(Status status) {
		// TODO - PERSISTIR
		String resultado = "@" + status.getUser().getScreenName() + ";";
		System.out.println("@" + status.getUser().getScreenName() + " - "
				+ status.getText());
		if (termos.length != 0) {
			for (String termo : termos) {
				if (status.getText().contains(termo)) {
					resultado += termo + ";";
					break;
				}
				
			}
		}
		resultado += status.getCreatedAt().toString()+";";
		try {
			file.append(resultado);
			count++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count == 5) {
			this.closeFile();
			count = 0;
		}

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO - NOTHING TO DO
		System.out.println("Got a status deletion notice id:"
				+ statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO - NOTHING TO DO
		System.out.println("Got track limitation notice:"
				+ numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO - NOTHING TO DO
		System.out.println("Got scrub_geo event userId:" + userId
				+ " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO - NOTHING TO DO
		System.out.println("Got stall warning:" + warning);
	}

	@Override
	public void onException(Exception ex) {
		System.out
				.println("***** Limite de requisições atingido, aguardando para tentar novamente ******");
		ex.printStackTrace();

	}

	public void openFile(String fileName) {
		try {
			file = new FileWriter(fileName, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(String text) {
		try {
			file.write(text);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeFile() {
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
