package br.com.ufpb.appSNAUtil.model.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class AccountWaitThread extends Thread {

	private Long timeRemaining;
	private Long accountId;

	private AtomicBoolean mutex;

	@Override
	public void run() {
		// TODO tirar de current accont depois colocar na lista de wait, buscar
		// nova account e coloca-la em current
		try {

			Thread.sleep(timeRemaining);
			List<Twitter> listAux = new ArrayList<Twitter>(
					AccountCarrousel.LIST_ACOUNTS_WAIT);
			int index = 0;
			for (Twitter t : listAux) {
				if (t.getOAuthAccessToken().getUserId() == accountId) {
					while(t.getRateLimitStatus().getRemainingHits() == 0){
						Thread.sleep(600);
					}
					AccountCarrousel.LIST_ACOUNTS_WAIT.remove(index);
					AccountCarrousel.LIST_ACOUNTS_READY.add(t);
					break;
				}
				index++;
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}

	}

	public Long getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(Long timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public AtomicBoolean getMutex() {
		return mutex;
	}

	public void setMutex(AtomicBoolean mutex) {
		this.mutex = mutex;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
