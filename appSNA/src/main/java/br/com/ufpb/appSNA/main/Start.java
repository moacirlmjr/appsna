package br.com.ufpb.appSNA.main;

import java.awt.Frame;

import br.com.ufpb.appSNA.model.beans.Graph;
import br.com.ufpb.appSNA.view.ConfigureView;

public class Start {
	
	public static void main(String[] args) {
		
		try {
			Graph myGraph = new Graph();
			Frame myFrame = ConfigureView.configuration(myGraph.getG());
			myFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
