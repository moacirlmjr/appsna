package br.com.puc.appSNA.model.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;

public class SNATwitterStatusListenerArquivo implements StatusListener {

	@Override
	public void onStatus(Status statusTwitter) {
		try {
			synchronized (statusTwitter) {
				File file = new File(Constantes.DIR + "stream.txt");
				InputStream is = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String linha = "";
				int count = 0;
				while ((linha = br.readLine()) != null) {
					count++;
				}
				if(count > 39){
					if(file.exists()){
						PrintWriter pw = new PrintWriter(file);
						pw.close();
					}
				}
				
				
				FileOutputStream fos = new FileOutputStream(file,true); 
				fos.write(("<strong>" + statusTwitter.getUser().getScreenName() + ": </strong> "
						+ statusTwitter.getText() + "\n").getBytes());  
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO - NOTHING TO DO
		// AppSNALog.info("Got a status deletion notice id:"
		// + statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO - NOTHING TO DO
		// AppSNALog
		// .info("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO - NOTHING TO DO
		// AppSNALog.info("Got scrub_geo event userId:" + userId
		// + " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO - NOTHING TO DO
		// AppSNALog.info("Got stall warning:" + warning);
	}

	@Override
	public void onException(Exception ex) {
		AppSNALog.error(ex.toString());
	}

}
