package br.com.ufpb.appSNAElection.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Monitorado extends AppSNAEntityMaster {

	private Long twitterId;
	private String screen_name;
	private String termos;

	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getTermos() {
		return termos;
	}

	public void setTermos(String termos) {
		this.termos = termos;
	}

}
