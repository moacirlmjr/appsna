package br.com.ufpb.appSNAElection.model.beans;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class Graph {
	
	public ArrayList<MyNode> listaNodes;
	public ArrayList<MyLink> listaLinks;
	
	
	
	private DirectedSparseGraph<MyNode, MyLink> g;
	
	public Graph(List<RelatorioOcorrenciasTO> listRO){
		
		MyNode n1 = null;
		MyNode n2 = null;
		MyLink l = null;
		String sourceAtual = ""; 
		
		g = new DirectedSparseGraph<MyNode, MyLink>();
		
		listaNodes = new ArrayList<MyNode>();
		
		for (RelatorioOcorrenciasTO roTO : listRO){
			if(!sourceAtual.equals(roTO.getSource())){
				sourceAtual = roTO.getSource();
				n1 = new MyNode();
				n1.setNome(roTO.getSource());
				listaNodes.add(n1);
			}
			
			n2 = new MyNode();
			n2.setNome(roTO.getTarget());
			listaNodes.add(n2);
			l = new MyLink(roTO.getWeight());
			g.addEdge(l, n1, n2, EdgeType.DIRECTED); 	
			
		}	
		
		for (RelatorioOcorrenciasTO roTO : listRO){
			if(!sourceAtual.equals(roTO.getSource())){
				sourceAtual = roTO.getSource();
				n1 = new MyNode();
				n1.setNome(roTO.getSource());
				listaNodes.add(n1);
			}
			
			n2 = new MyNode();
			n2.setNome(roTO.getTarget());
			listaNodes.add(n2);
			l = new MyLink(roTO.getWeight());
			g.addEdge(l, n1, n2, EdgeType.DIRECTED); 	
			
		}
		
	}
	

	public DirectedSparseGraph<MyNode, MyLink> getG() {
		return g;
	}


	public void setG(DirectedSparseGraph<MyNode, MyLink> g) {
		this.g = g;
	}
	
}