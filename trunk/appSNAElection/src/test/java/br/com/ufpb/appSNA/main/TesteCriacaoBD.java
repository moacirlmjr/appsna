package br.com.ufpb.appSNA.main;

import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAElection.util.CreateBD;

public class TesteCriacaoBD {
	
	public static void main(String[] args) {
		CreateBD.criar(BDUtil.DATABASE_NAME);
		CreateBD.excluir(BDUtil.DATABASE_NAME);		
	}

}
