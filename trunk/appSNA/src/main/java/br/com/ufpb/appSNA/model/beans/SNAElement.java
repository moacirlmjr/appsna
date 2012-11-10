package br.com.ufpb.appSNA.model.beans;

import java.util.Date;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class SNAElement extends AppSNAEntityMaster {
	
	private static final long serialVersionUID = 1L;
	
	private Long id_usuario;
	private String nome;
	private String Screename;
	private String Biografia;
	private String localização;
	private int totalFollowing;
	private int totalFollowers;
	private int totalTweets;
	private String URL;
	private String TimeZone;
	private String Linguagem;
	private Date dataDeCriacao;
	private String URLImagem;

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		return localização;
	}

	public void setLocalização(String localização) {
		this.localização = localização;
	}

	public int getTotalFollowing() {
		return totalFollowing;
	}

	public void setTotalFollowing(int totalFollowing) {
		this.totalFollowing = totalFollowing;
	}

	public int getTotalFollowers() {
		return totalFollowers;
	}

	public void setTotalFollowers(int totalFollowers) {
		this.totalFollowers = totalFollowers;
	}

	public int getTotalTweets() {
		return totalTweets;
	}

	public void setTotalTweets(int totalTweets) {
		this.totalTweets = totalTweets;
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

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public String getURLImagem() {
		return URLImagem;
	}

	public void setURLImagem(String uRLImagem) {
		URLImagem = uRLImagem;
	}

}
