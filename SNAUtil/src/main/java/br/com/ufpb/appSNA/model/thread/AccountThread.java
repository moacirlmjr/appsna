package br.com.ufpb.appSNA.model.thread;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import br.com.ufpb.appSNA.util.AccountCarrousel;

public class AccountThread extends Thread {

	private Long timeRemaining;
	private Twitter account;

	private AtomicBoolean mutex;

	@Override
	public void run() {
		// TODO tirar de current accont depois colocar na lista de wait, buscar
		// nova account e coloca-la em current
		try {
			synchronized (mutex) {

				AccountCarrousel.LIST_ACOUNTS_WAIT
						.add(AccountCarrousel.CURRENT_ACCOUNT);
				if (AccountCarrousel.LIST_ACOUNTS_READY.size() != 0) {
					AccountCarrousel.CURRENT_ACCOUNT = AccountCarrousel.LIST_ACOUNTS_READY
							.get(0);
					AccountCarrousel.LIST_ACOUNTS_READY.remove(0);
				}
				mutex.notify();
			}

			Thread.sleep(timeRemaining);
			List<Twitter> listAux = AccountCarrousel.LIST_ACOUNTS_WAIT;
			int index = 0;
			for (Twitter t : listAux) {
				if (t.getId() == account.getId()) {
					AccountCarrousel.LIST_ACOUNTS_WAIT.remove(index);
					AccountCarrousel.LIST_ACOUNTS_READY.add(t);
				}
				index++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

	public Long getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(Long timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public Twitter getAccount() {
		return account;
	}

	public void setAccount(Twitter account) {
		this.account = account;
	}

	public AtomicBoolean getMutex() {
		return mutex;
	}

	public void setMutex(AtomicBoolean mutex) {
		this.mutex = mutex;
	}

}
