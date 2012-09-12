package br.com.ufpb.appSNA.main;

import br.com.ufpb.appSNA.model.beans.Graph;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;

public class Start {
	
	public static void main(String[] args) {
		
		try {
			AccountCarrousel.startListReady();
			Graph myGraph = new Graph();
			myGraph.getG();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
