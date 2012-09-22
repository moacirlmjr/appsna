package br.com.ufpb.appSNA.model.beans;

import java.util.Date;

public class Status {
	
	long id;
	long id_elemento;
	Date dataDeCriacao;
	String texto;
	long latitude;
	long bigint;
	int totalRetweet;
	boolean isRetweeted;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId_elemento() {
		return id_elemento;
	}
	public void setId_elemento(long id_elemento) {
		this.id_elemento = id_elemento;
	}
	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}
	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public long getBigint() {
		return bigint;
	}
	public void setBigint(long bigint) {
		this.bigint = bigint;
	}
	public int getTotalRetweet() {
		return totalRetweet;
	}
	public void setTotalRetweet(int totalRetweet) {
		this.totalRetweet = totalRetweet;
	}
	public boolean isRetweeted() {
		return isRetweeted;
	}
	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}
	
	


}
