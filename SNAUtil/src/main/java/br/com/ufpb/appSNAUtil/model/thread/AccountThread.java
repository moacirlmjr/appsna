package br.com.ufpb.appSNAUtil.model.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import twitter4j.Twitter;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.RateLimiteStatusListetener;

public class AccountThread extends Thread {

	private Long timeRemaining;
	private Long accountId;

	private AtomicBoolean mutex;

	@Override
	public void run() {
		// TODO tirar de current accont depois colocar na lista de wait, buscar
		// nova account e coloca-la em current
		try {
			AppSNALog.warn("Começando Tratamento de Conta");
			synchronized (mutex) {
				if (AccountCarrousel.CURRENT_ACCOUNT != null) {
					accountId = new Long(AccountCarrousel.CURRENT_ACCOUNT
							.getOAuthAccessToken().getUserId());
				}
				AccountCarrousel.LIST_ACOUNTS_WAIT
						.add(AccountCarrousel.CURRENT_ACCOUNT);
				AppSNALog.warn("Conta " + accountId
						+ " adiciona a Lista de Wait");

				if (AccountCarrousel.LIST_ACOUNTS_READY.size() != 0) {
					List<Twitter> listAux2 = new ArrayList<Twitter>(
							AccountCarrousel.LIST_ACOUNTS_READY);
					int index = 0;
					for (Twitter tw : listAux2) {
						if (tw.getRateLimitStatus().getRemainingHits() != 0) {
							AccountCarrousel.CURRENT_ACCOUNT = AccountCarrousel.LIST_ACOUNTS_READY
									.get(index);
							AccountCarrousel.CURRENT_ACCOUNT
									.addRateLimitStatusListener(new RateLimiteStatusListetener());
							AppSNALog
									.warn("Nova Conta Adiciona no current_account");
							AccountCarrousel.LIST_ACOUNTS_READY.remove(index);
							AppSNALog.warn("Conta removida da Lista de Ready");

							break;
						} else {
							AccountCarrousel.LIST_ACOUNTS_WAIT.add(tw);
							AccountCarrousel.LIST_ACOUNTS_READY.remove(index);
						}
						index++;
					}
				}
				mutex.notify();
			}

			Thread.sleep(timeRemaining);
			AppSNALog.warn("Tempo " + timeRemaining + " da  conta " + accountId
					+ " passou");
			List<Twitter> listAux = new ArrayList<Twitter>(
					AccountCarrousel.LIST_ACOUNTS_WAIT);
			int index = 0;
			for (Twitter t : listAux) {
				if (t.getOAuthAccessToken().getUserId() == accountId) {
					while (t.getRateLimitStatus().getRemainingHits() == 0) {
						Thread.sleep(600);
					}
					AppSNALog.warn("Removida da lista de wait");
					AccountCarrousel.LIST_ACOUNTS_WAIT.remove(index);
					AccountCarrousel.LIST_ACOUNTS_READY.add(t);
					AppSNALog.warn("adicionada na lista de ready");
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

}
