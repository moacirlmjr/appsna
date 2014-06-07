package br.com.puc.appSNA.model.beans.to;

public class EdgeTO {
	
	long id_source;
	long id_target;
	int weight;
	
	
	public EdgeTO(long id_source, long id_target, int weight) {
		super();
		this.id_source = id_source;
		this.id_target = id_target;
		this.weight = weight;
	}
	
	public EdgeTO() {		
		
	}


	public long getId_source() {
		return id_source;
	}


	public void setId_source(long id_source) {
		this.id_source = id_source;
	}


	public long getId_target() {
		return id_target;
	}


	public void setId_target(long id_target) {
		this.id_target = id_target;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
	
	
	

}
