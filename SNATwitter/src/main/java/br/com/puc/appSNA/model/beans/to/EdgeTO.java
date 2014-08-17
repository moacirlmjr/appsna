package br.com.puc.appSNA.model.beans.to;

import java.io.Serializable;

public class EdgeTO implements Serializable{
	
	String id_source;
	String id_target;
	int weight;
	
	
	public EdgeTO(String id_source, String id_target, int weight) {
		super();
		this.id_source = id_source;
		this.id_target = id_target;
		this.weight = weight;
	}
	
	public EdgeTO() {		
		
	}


	public String getId_source() {
		return id_source;
	}


	public void setId_source(String id_source) {
		this.id_source = id_source;
	}


	public String getId_target() {
		return id_target;
	}


	public void setId_target(String id_target) {
		this.id_target = id_target;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
	
	
	

}
