package br.com.ufpb.appSNA.model.listener;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class ElectionStatusListener implements StatusListener {

	 @Override
     public void onStatus(Status status) {
		 //TODO - PERSISTIR
         System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
     }

     @Override
     public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		 //TODO - NOTHING TO DO
         System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
     }

     @Override
     public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    	//TODO - NOTHING TO DO
         System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
     }

     @Override
     public void onScrubGeo(long userId, long upToStatusId) {
    	//TODO - NOTHING TO DO
         System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
     }

     @Override
     public void onStallWarning(StallWarning warning) {
    	//TODO - NOTHING TO DO
         System.out.println("Got stall warning:" + warning);
     }

     @Override
     public void onException(Exception ex) {
    	//TODO - CAPTURAR A EXCEÇÃO
         ex.printStackTrace();
     }

}
