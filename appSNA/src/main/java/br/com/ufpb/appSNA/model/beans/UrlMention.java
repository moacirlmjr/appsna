package br.com.ufpb.appSNA.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class UrlMention extends AppSNAEntityMaster {

	private long id_usuario;
	private long id_status;
	private long id_urlmention;
	private String url;

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public long getId_status() {
		return id_status;
	}

	public void setId_status(long id_status) {
		this.id_status = id_status;
	}

	public long getId_urlmention() {
		return id_urlmention;
	}

	public void setId_urlmention(long id_urlmention) {
		this.id_urlmention = id_urlmention;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
