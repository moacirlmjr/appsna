package br.com.puc.appSNA.model.beans;


public class UserMention extends AppSNAEntityMaster {

	private static final long serialVersionUID = 1L;

	private long id_usuario;
	private long id_status;
	private long id_usermention;
	private long id_user_mentionade;
	private String usuario;

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

	public long getId_usermention() {
		return id_usermention;
	}

	public void setId_usermention(long id_usermention) {
		this.id_usermention = id_usermention;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long getId_user_mentionade() {
		return id_user_mentionade;
	}

	public void setId_user_mentionade(long id_user_mentionade) {
		this.id_user_mentionade = id_user_mentionade;
	}

}
