package br.com.ufpb.appSNA.model.beans;



public class MyLink {
	
	static int edgeCount = 0; 	
	private int id;
	private double weight;
	private double capacity;

	public MyLink() {
		this.id = edgeCount++;	
		this.weight=1.0;
		this.capacity=10.0;
	}	
	
	public MyLink(double weight, double capacity) {
		this.id = edgeCount++;
		this.weight = weight;
		this.capacity = capacity;
	}	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	
	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
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