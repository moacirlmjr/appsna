package br.com.ufpb.appSNA.model.thread;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import br.com.ufpb.appSNA.model.listener.ElectionStatusListener;

public class TwitterStreamElection2012 implements Runnable {

	private TwitterStream twitterStream;
	private FilterQuery filterQuery;
	private ElectionStatusListener statusListener;
	private String nomeArq;

	@Override
	public void run() {
		statusListener.openFile(nomeArq);
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

	public ElectionStatusListener getStatusListener() {
		return statusListener;
	}

	public void setStatusListener(ElectionStatusListener statusListener) {
		this.statusListener = statusListener;
	}

	public String getNomeArq() {
		return nomeArq;
	}

	public void setNomeArq(String nomeArq) {
		this.nomeArq = nomeArq;
	}

}
