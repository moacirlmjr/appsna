package br.com.ufpb.appSNA.view;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;

import br.com.ufpb.appSNA.model.beans.MyLink;
import br.com.ufpb.appSNA.model.beans.MyNode;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class ConfigureView {
	
	public static Frame configuration (DirectedSparseGraph<MyNode, MyLink> g){
		
		// Layout<V, E>, VisualizationComponent<V,E> pega o FRLayout,
		// StaticLayout, CircleLayout e KKLayout,
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Layout<Integer, String> layout = new CircleLayout(g);

		// Ajusta as dimensoões do grafo
		layout.setSize(new Dimension(300, 300));

		// Cria uma nova visualização
		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(
				layout);

		// Define o tamanho preferido do presente componente
		vv.setPreferredSize(new Dimension(450, 450));

		// Mostra os Labels dos Vértices e das Arestas
		vv.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller<Integer>());
		vv.getRenderContext().setEdgeLabelTransformer(
				new ToStringLabeller<String>());

		// Cria um grafo e adiciona ele a um componente de visualização
		@SuppressWarnings("rawtypes")
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);

		// Ajustes de janela
		JFrame frame = new JFrame("AppSNA - Projeto de Teste SERASA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		
		return frame;
		
	}

}
