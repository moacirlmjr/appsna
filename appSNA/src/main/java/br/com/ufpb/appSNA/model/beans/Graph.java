package br.com.ufpb.appSNA.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNA.model.enumeration.AuthEnum;
import br.com.ufpb.appSNA.util.TwitterUtil;

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
	
	public Graph() throws Exception{
		
		List<String> listaDeNomes = new ArrayList<String>();
		listaDeNomes.add("AIRTONGTORRES");
		listaDeNomes.add("alamorocha");
		listaDeNomes.add("ale_patricio");
		listaDeNomes.add("ALLYSONDINIZ");
		listaDeNomes.add("DEZINHAJPA");
		listaDeNomes.add("ARTHURFERRO");
		listaDeNomes.add("atila_jp");
		listaDeNomes.add("AYLTONJR");
		listaDeNomes.add("ELVISREI");
		listaDeNomes.add("evaldodesousa");
		listaDeNomes.add("fabianovidaltur");
		listaDeNomes.add("BRASILTONY");
		listaDeNomes.add("CHIQUELMEBATERA");
		listaDeNomes.add("FLUGARCEZ");
		listaDeNomes.add("IVANILDOPB");
		listaDeNomes.add("KellylopesLOPES");
		listaDeNomes.add("GALVAOJPA");
		listaDeNomes.add("luanadepaulane1");
		listaDeNomes.add("lucasduartereal");
		listaDeNomes.add("Mariacristin339");
		listaDeNomes.add("ONAMEN");
		listaDeNomes.add("jricardoamorim");
		listaDeNomes.add("RINALDOPESSOA");
		listaDeNomes.add("RIQUELSON");
		listaDeNomes.add("NTURISMO_JPPB");
		listaDeNomes.add("ThiagoADVJP");
		
		
		g = new DirectedSparseGraph<MyNode, MyLink>();
		
		listaNodes = new ArrayList<MyNode>();

		Map<String, Long> mapUsers = TwitterUtil.retornarUserId(listaDeNomes, AuthEnum.DANYLLO_KEY);
		
		MyNode node = null;
		
		for(String key : mapUsers.keySet()){
			node = new MyNode(mapUsers.get(key).intValue(), key, TwitterUtil.retornarListaAmigosIdsList(key,AuthEnum.DANYLLO_KEY));
			listaNodes.add(node);
		}
		
		MyNode amigo = null;
		
		/*for(MyNode n : listaNodes){
			for(Long amigoId : n.getListadeAmigos()){
				amigo = new MyNode(amigoId.intValue(), "A"+ amigoId);
				g.addEdge(new MyLink(), n, amigo, EdgeType.DIRECTED);
			}
		}
		*/		
		
		for(MyNode n : listaNodes){
				Long amigoId = n.getListadeAmigos().get(0);
				amigo = new MyNode(amigoId.intValue(), "A"+ amigoId);
				g.addEdge(new MyLink(), n, amigo, EdgeType.DIRECTED);
			
		}
		
		
												
	}
	

	public DirectedSparseGraph<MyNode, MyLink> getG() {
		return g;
	}


	public void setG(DirectedSparseGraph<MyNode, MyLink> g) {
		this.g = g;
	}
	
}