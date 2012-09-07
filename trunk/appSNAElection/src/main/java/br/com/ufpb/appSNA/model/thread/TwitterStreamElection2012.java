package br.com.ufpb.appSNA.model.thread;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class TwitterStreamElection2012 implements Runnable {

	private TwitterStream twitterStream;
	private FilterQuery filterQuery;
	private StatusListener statusListener;

	@Override
	public void run() {
        twitterStream.addListener(statusListener);
        twitterStream.filter(filterQuery);
	}

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public FilterQuery getFilterQuery() {
		return filterQuery;
	}

	public void setFilterQuery(FilterQuery filterQuery) {
		this.filterQuery = filterQuery;
	}

	public StatusListener getStatusListener() {
		return statusListener;
	}

	public void setStatusListener(StatusListener statusListener) {
		this.statusListener = statusListener;
	}

}
