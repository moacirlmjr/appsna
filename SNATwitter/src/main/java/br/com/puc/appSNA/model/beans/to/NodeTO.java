package br.com.puc.appSNA.model.beans.to;

public class NodeTO {
	
	Long id_node;
	String nome;
	
	
	public NodeTO(Long id_node, String nome) {
		super();
		this.id_node = id_node;
		this.nome = nome;
	}
	
	
	public NodeTO() {
		
	}


	public Long getId_node() {
		return id_node;
	}
	public void setId_node(Long id_node) {
		this.id_node = id_node;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
