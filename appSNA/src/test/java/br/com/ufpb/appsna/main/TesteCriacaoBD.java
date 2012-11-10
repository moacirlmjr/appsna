package br.com.ufpb.appsna.main;

import br.com.ufpb.appSNA.util.CreateBD;


public class TesteCriacaoBD {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CreateBD.criar("AppSNA");
		//CreateBD.excluir("AppSNA");
	}

}
