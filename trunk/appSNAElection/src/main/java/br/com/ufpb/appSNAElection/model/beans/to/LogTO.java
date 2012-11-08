package br.com.ufpb.appSNAElection.model.beans.to;

import java.util.Calendar;

public class LogTO {

	private Calendar data;
	private String screenName;
	private String status;

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
