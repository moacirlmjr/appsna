package br.com.ufpb.appSNA.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.Factory;

import br.com.ufpb.appSNA.model.beans.MyLink;
import br.com.ufpb.appSNA.model.beans.MyNode;
import edu.uci.ics.jung.algorithms.flows.EdmondsKarpMaxFlow;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class GraphMetrics {

	private DirectedSparseGraph<MyNode, MyLink> g;

	public void calcUnweightedShortestPath(MyNode n1, MyNode n2) {
		DijkstraShortestPath<MyNode, MyLink> alg = new DijkstraShortestPath<MyNode, MyLink>(
				g);
		List<MyLink> l = alg.getPath(n1, n2);
		System.out.println("O caminho mais curto não ponderada de " + n1
				+ " para " + n2 + " é:");
		System.out.println(l.toString());
	}

	public void calcWeightedShortestPath(MyNode n1, MyNode n2) {
		Transformer<MyLink, Double> wtTransformer = new Transformer<MyLink, Double>() {
			public Double transform(MyLink link) {
				return link.getWeight();
			}
		};
		DijkstraShortestPath<MyNode, MyLink> alg = new DijkstraShortestPath<MyNode, MyLink>(
				g, wtTransformer);
		List<MyLink> l = alg.getPath(n1, n2);
		Number dist = alg.getDistance(n1, n2);
		System.out.println("O caminho mais curto ponderada de " + n1 + " para "
				+ n2 + " é:");
		System.out.println(l.toString());
		System.out.println("e o comprimento do caminho é: " + dist);
	}

	@SuppressWarnings("unchecked")
	public void calcMaxFlow(MyNode n1, MyNode n2) {

		Transformer<MyLink, Double> capTransformer = new Transformer<MyLink, Double>() {
			public Double transform(MyLink link) {
				return link.getCapacity();
			}
		};
		Map<MyLink, Double> edgeFlowMap = new HashMap<MyLink, Double>();

		Factory<MyLink> edgeFactory = new Factory<MyLink>() {
			public MyLink create() {
				return new MyLink(1.0, 1.0);
			}
		};

		@SuppressWarnings("rawtypes")
		EdmondsKarpMaxFlow<MyNode, MyLink> alg = new EdmondsKarpMaxFlow(g, n1,
				n2, capTransformer, edgeFlowMap, edgeFactory);
		alg.evaluate();
		System.out.println("O fluxo máximo  de " + n1 + " para " + n2 + " é: "
				+ alg.getMaxFlow());
		System.out.println("O Total de arestas é: "
				+ alg.getMinCutEdges().size());
		System.out.println("O conjunto de arestas é: "
				+ alg.getMinCutEdges().toString());

	}

	public double calcBetweennessNode(MyNode n1) {
		return new BetweennessCentrality<MyNode, MyLink>(g).getVertexScore(n1);
	}

	public double calcBetweennessLink(MyLink l1) {
		return new BetweennessCentrality<MyNode, MyLink>(g).getEdgeScore(l1);
	}

	public double calcCloseness(MyNode n1) {
		return new ClosenessCentrality<MyNode, MyLink>(g).getVertexScore(n1);
	}

}
