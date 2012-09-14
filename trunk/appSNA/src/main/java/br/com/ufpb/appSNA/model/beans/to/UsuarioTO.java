package br.com.ufpb.appSNA.model.beans.to;

import java.util.List;

import twitter4j.User;

public class UsuarioTO {

	private User twitterUser;
	private List<AmigoTO> listAmigos;

	public UsuarioTO() {

	}

	public UsuarioTO(User twitterUser, List<AmigoTO> listAmigos) {
		super();
		this.twitterUser = twitterUser;
		this.listAmigos = listAmigos;
	}

	public User getTwitterUser() {
		return twitterUser;
	}

	public void setTwitterUser(User twitterUser) {
		this.twitterUser = twitterUser;
	}

	public List<AmigoTO> getListAmigos() {
		return listAmigos;
	}

	public void setListAmigos(List<AmigoTO> listAmigos) {
		this.listAmigos = listAmigos;
	}

}
