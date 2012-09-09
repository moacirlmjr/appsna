package br.com.ufpb.appSNA.model.beans;



public class MyLink {
	
	static int edgeCount = 0; 	
	private int id;

	public MyLink(double weight, double capacity) {
		this.id = edgeCount++;		
	}	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String toString() {		
		return "E" + id;
	}
	
	public String imprimeId() {		
		return "E" + id;
	}
	
	public String imprimePesoId() {		
		return "E" + id;
	}


}