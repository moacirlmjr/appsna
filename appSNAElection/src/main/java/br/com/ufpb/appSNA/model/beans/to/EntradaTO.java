package br.com.ufpb.appSNA.model.beans.to;

public class EntradaTO {

	private String screen_name;
	private String termos;
	private long userId;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
