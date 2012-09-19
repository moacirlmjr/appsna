package br.com.ufpb.appSNAUtil.model.beans.to;

public class UserTO {
	
	private String Nome;
	private String Screename;
	private String Biografia;
	private String Localização;
	private int TotalFollowers;
	private int TotalFollowing;
	private int TotalTweets;
	private String Status; 
	private String URL;
	private String TimeZone;
	private String Linguagem;
	
	
	public UserTO() {
		super();		
	}


	public UserTO(String nome, String screename, String biografia,
			String localização, int totalFollowers, int totalFollowing,
			int totalTweets, String status, String uRL, String timeZone,
			String linguagem) {
		super();
		Nome = nome;
		Screename = screename;
		Biografia = biografia;
		Localização = localização;
		TotalFollowers = totalFollowers;
		TotalFollowing = totalFollowing;
		TotalTweets = totalTweets;
		Status = status;
		URL = uRL;
		TimeZone = timeZone;
		Linguagem = linguagem;
	}


	public String getNome() {
		return Nome;
	}


	public void setNome(String nome) {
		Nome = nome;
	}


	public String getScreename() {
		return Screename;
	}


	public void setScreename(String screename) {
		Screename = screename;
	}


	public String getBiografia() {
		return Biografia;
	}


	public void setBiografia(String biografia) {
		Biografia = biografia;
	}


	public String getLocalização() {
		return Localização;
	}


	public void setLocalização(String localização) {
		Localização = localização;
	}


	public int getTotalFollowers() {
		return TotalFollowers;
	}


	public void setTotalFollowers(int totalFollowers) {
		TotalFollowers = totalFollowers;
	}


	public int getTotalFollowing() {
		return TotalFollowing;
	}


	public void setTotalFollowing(int totalFollowing) {
		TotalFollowing = totalFollowing;
	}


	public int getTotalTweets() {
		return TotalTweets;
	}


	public void setTotalTweets(int totalTweets) {
		TotalTweets = totalTweets;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String getURL() {
		return URL;
	}


	public void setURL(String uRL) {
		URL = uRL;
	}


	public String getTimeZone() {
		return TimeZone;
	}


	public void setTimeZone(String timeZone) {
		TimeZone = timeZone;
	}


	public String getLinguagem() {
		return Linguagem;
	}


	public void setLinguagem(String linguagem) {
		Linguagem = linguagem;
	}
	
	
	
	
	

}
