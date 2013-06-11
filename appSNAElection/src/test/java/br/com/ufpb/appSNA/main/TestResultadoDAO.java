package br.com.ufpb.appSNA.main;

import java.util.ArrayList;

import br.com.ufpb.appSNAElection.model.dao.ResultadoDAOImpl;

public class TestResultadoDAO {
	
	public static void main(String[] args) throws Exception {
		
		ResultadoDAOImpl rdao = new ResultadoDAOImpl();
		
		ArrayList<String> listaTeste = (ArrayList<String>) rdao.listaUsuariosMencionadors(11);
		System.out.println(listaTeste.size());
	}

}
