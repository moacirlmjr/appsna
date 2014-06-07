package br.com.puc.appSNA.model.beans.to;

public class MentionTO {
	
	private long id_target;
	private int total_mention;
	
	
	public MentionTO(long id_target, int total_mention) {
		super();
		this.id_target = id_target;
		this.total_mention = total_mention;
	}


	public MentionTO() {
		super();		
	}


	public long getId_target() {
		return id_target;
	}


	public void setId_target(long id_target) {
		this.id_target = id_target;
	}


	public int getTotal_mention() {
		return total_mention;
	}


	public void setTotal_mention(int total_mention) {
		this.total_mention = total_mention;
	}
	
	
	
	
	
	
	
	

}
