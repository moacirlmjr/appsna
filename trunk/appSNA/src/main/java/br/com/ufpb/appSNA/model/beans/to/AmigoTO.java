package br.com.ufpb.appSNA.model.beans.to;

import java.util.List;

import twitter4j.Status;
import twitter4j.User;

public class AmigoTO {

	private User twitterFriendUser;
	private List<Status> listTwitters;

	public AmigoTO() {

	}

	public User getTwitterFriendUser() {
		return twitterFriendUser;
	}

	public void setTwitterFriendUser(User twitterFriendUser) {
		this.twitterFriendUser = twitterFriendUser;
	}

	public List<Status> getListTwitters() {
		return listTwitters;
	}

	public void setListTwitters(List<Status> listTwitters) {
		this.listTwitters = listTwitters;
	}

}
