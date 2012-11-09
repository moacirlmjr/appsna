package br.com.ufpb.appSNA.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Status extends AppSNAEntityMaster{
	
	private static final long serialVersionUID = 1L;
	
	private long id_status;
	private long id_usuario;
	private long dataDeCriacao;
	private String texto;
	private double latitude;
	private double longitude;
	private long totalRetweet;
	private boolean isRetweeted;	

	public long getId_status() {
		return id_status;
	}

	public void setId_status(long id_status) {
		this.id_status = id_status;
	}
	
	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public long getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(long dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getTotalRetweet() {
		return totalRetweet;
	}

	public void setTotalRetweet(long totalRetweet) {
		this.totalRetweet = totalRetweet;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

}
