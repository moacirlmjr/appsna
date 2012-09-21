package br.com.ufpb.appSNAElection.model.beans;

import java.util.Date;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Resultado extends AppSNAEntityMaster {

	private String screen_name;
	private String termo;
	private Date data;
	private long latitude;
	private long longitude;
	private Long monitorado_id;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public Long getMonitorado_id() {
		return monitorado_id;
	}

	public void setMonitorado_id(Long monitorado_id) {
		this.monitorado_id = monitorado_id;
	}

}
