package br.com.ufpb.appSNA.model.beans;

import java.util.ArrayList;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class Graph {
	
	public ArrayList<MyNode> listaNodes;
	public ArrayList<MyLink> listaLinks;
	
	public MyNode n1;
	public MyNode n2;
	public MyNode n3;
	public MyNode n4;
	public MyNode n5;
	
	
	public MyLink l1;
	public MyLink l2;
	public MyLink l3;
	public MyLink l4;
	public MyLink l5;
	public MyLink l6;
	public MyLink l7;
	
	
	private DirectedSparseGraph<MyNode, MyLink> g;
	
	public Graph(){
		
		g = new DirectedSparseGraph<MyNode, MyLink>();
		
		listaNodes = new ArrayList<MyNode>();
		
		n1 = new MyNode(1);
		n2 = new MyNode(2);
		n3 = new MyNode(3);
		n4 = new MyNode(4);
		n5 = new MyNode(5);
		
		listaNodes.add(n1);
		listaNodes.add(n2);		
		listaNodes.add(n3);
		listaNodes.add(n4);
		listaNodes.add(n5);
		
		
		
		listaLinks = new ArrayList<MyLink>();
		
		l1 = new MyLink(2.0, 48);
		l2 = new MyLink(2.0, 48);
		l3 = new MyLink(3.0, 192);
		l4 = new MyLink(2.0, 48);
		l5 = new MyLink(2.0, 48);
		l6 = new MyLink(2.0, 48);
		l7 = new MyLink(10.0, 48);	
		
		

		g.addEdge(l1, n1, n2, EdgeType.DIRECTED); 																	
		g.addEdge(l2, n2, n3, EdgeType.DIRECTED);
		g.addEdge(l3, n3, n5, EdgeType.DIRECTED);
		g.addEdge(l4, n5, n4, EdgeType.DIRECTED); 																	
		g.addEdge(l5, n4, n2, EdgeType.DIRECTED); 
		g.addEdge(l6, n3, n1, EdgeType.DIRECTED); 
		g.addEdge(l7, n2, n5, EdgeType.DIRECTED);
												
	}
	

	public DirectedSparseGraph<MyNode, MyLink> getG() {
		return g;
	}


	public void setG(DirectedSparseGraph<MyNode, MyLink> g) {
		this.g = g;
	}
	
}