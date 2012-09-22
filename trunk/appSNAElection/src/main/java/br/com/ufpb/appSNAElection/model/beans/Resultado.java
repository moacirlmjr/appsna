package br.com.ufpb.appSNAElection.model.beans;

import java.util.Date;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Resultado extends AppSNAEntityMaster {

	private String screen_name;
	private long termo_id;
	private Date data;
	private float latitude;
	private float longitude;
	private long monitorado_id;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public long getTermoId() {
		return termo_id;
	}

	public void setTermoId(long termo) {
		this.termo_id = termo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Long getMonitorado_id() {
		return monitorado_id;
	}

	public void setMonitorado_id(Long monitorado_id) {
		this.monitorado_id = monitorado_id;
	}

}
