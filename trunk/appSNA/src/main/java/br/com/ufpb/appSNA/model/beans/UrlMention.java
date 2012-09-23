package br.com.ufpb.appSNA.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class UrlMention extends AppSNAEntityMaster{

	private long id;
	private long id_status;
	private long id_elemento;
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId_status() {
		return id_status;
	}

	public void setId_status(long id_status) {
		this.id_status = id_status;
	}

	public long getId_elemento() {
		return id_elemento;
	}

	public void setId_elemento(long id_elemento) {
		this.id_elemento = id_elemento;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
