package br.com.ufpb.appSNAUtil.util;

import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.Twitter;
import br.com.ufpb.appSNAUtil.model.thread.AccountThread;
import br.com.ufpb.appSNAUtil.model.thread.VerificarListaReady;

public class RateLimiteStatusListetener implements RateLimitStatusListener {

	private static AtomicBoolean mutex;
	private static AtomicBoolean mutexListReady;

	@Override
	public void onRateLimitStatus(RateLimitStatusEvent event) {
		try {
			System.out.println( ((Twitter)event.getSource()).getOAuthAccessToken().getUserId() + " - " 
					+  event.getRateLimitStatus().getRemainingHits() + " / " + "Total de " + 			
					AccountCarrousel.totalHits.incrementAndGet() + " requisições na thread");

			if (event.getRateLimitStatus().getRemainingHits() == 0) {
				Long timeRemaining = ((long) event.getRateLimitStatus()
						.getSecondsUntilReset() * 1000);
				if (AccountCarrousel.LIST_ACOUNTS_READY.size() == 0) {
					mutexListReady = new AtomicBoolean();
					mutexListReady.set(true);
					VerificarListaReady vl = new VerificarListaReady();
					synchronized (mutexListReady) {
						vl.setName("VerificarListaReady");
						vl.setMutexListReady(mutexListReady);
						vl.start();
						mutexListReady.wait();
					}
				}
				mutex = new AtomicBoolean();
				mutex.set(true);
				AccountThread at = new AccountThread();
				synchronized (mutex) {
					at.setMutex(mutex);
					at.setName("AccountThread-"
							+ AccountCarrousel.CURRENT_ACCOUNT
									.getOAuthAccessToken().getUserId());
					at.setTimeRemaining(timeRemaining);
					at.start();

					mutex.wait();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRateLimitReached(RateLimitStatusEvent event) {
		System.out.println("teste");
	}

}
