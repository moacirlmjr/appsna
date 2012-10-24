package br.com.ufpb.appSNAUtil.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.Twitter;
import br.com.ufpb.appSNAUtil.model.enumeration.AuthEnum;
import br.com.ufpb.appSNAUtil.model.thread.AccountWaitThread;
import br.com.ufpb.appSNAUtil.model.thread.VerificarListaReady;

public class AccountCarrousel {

	public static List<Twitter> LIST_ACOUNTS_READY;
	public static List<Twitter> LIST_ACOUNTS_WAIT;
	public static Twitter CURRENT_ACCOUNT;

	public static final Object LOCK = new Object();
	private static AtomicBoolean mutex;
	private static AtomicBoolean mutexListReady;

	public static void startListReady() {
		LIST_ACOUNTS_READY = new ArrayList<Twitter>();
		LIST_ACOUNTS_WAIT = new ArrayList<Twitter>();
		int count = 0;
		try {
			for (AuthEnum auth : AuthEnum.values()) {
				Twitter twitter = TwitterUtil.createTwitterFactory(auth)
						.getInstance();
				if (twitter.getRateLimitStatus().getRemainingHits() == 0) {
					Long timeRemaining = ((long) twitter.getRateLimitStatus()
							.getSecondsUntilReset() * 1000);
					LIST_ACOUNTS_WAIT.add(twitter);
					AccountWaitThread at = new AccountWaitThread();
					at.setName("AccountWaitThread-"
							+ twitter
									.getOAuthAccessToken().getUserId());
					at.setTimeRemaining(timeRemaining);
					at.setAccountId(twitter.getOAuthAccessToken().getUserId());
					at.start();

				} else {
					if (count == 0) {
						CURRENT_ACCOUNT = twitter;
					} else {
						LIST_ACOUNTS_READY.add(twitter);
					}
					count++;
				}
			}
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
				
				List<Twitter> listAux = new ArrayList<Twitter>(AccountCarrousel.LIST_ACOUNTS_READY);
				int index = 0;
				for (Twitter t : listAux) {
					if (t.getRateLimitStatus().getRemainingHits() != 0) {
						CURRENT_ACCOUNT = t;
						LIST_ACOUNTS_READY.remove(index);
						break;
					}
					index++;
				}
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}
}
