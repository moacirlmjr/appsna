package br.com.ufpb.appSNAUtil.model.thread;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.ufpb.appSNAUtil.SNATest;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class VerificarListaReady extends Thread {

	private AtomicBoolean mutexListReady;

	@Override
	public void run() {
		try {
			synchronized (mutexListReady) {
				while (AccountCarrousel.LIST_ACOUNTS_READY.size() == 0) {
					Thread.sleep(600);
				}
				mutexListReady.notify();
			}
		} catch (InterruptedException e) {
			AppSNALog.error(e.toString());
		}
	}

	public AtomicBoolean getMutexListReady() {
		return mutexListReady;
	}

	public void setMutexListReady(AtomicBoolean mutexListReady) {
		this.mutexListReady = mutexListReady;
	}

}
