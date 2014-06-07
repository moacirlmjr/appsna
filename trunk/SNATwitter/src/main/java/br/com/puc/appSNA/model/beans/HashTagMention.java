package br.com.puc.appSNA.model.beans;


public class HashTagMention extends AppSNAEntityMaster {

	private static final long serialVersionUID = 1L;
	
	private long id_usuario;
	private long id_status;
	private long id_hashtagmention;
	private String hashtag;

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

	public long getId_hashtagmention() {
		return id_hashtagmention;
	}

	public void setId_hashtagmention(long id_hashtagmention) {
		this.id_hashtagmention = id_hashtagmention;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

}
