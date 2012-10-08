package br.com.ufpb.appSNA.main;

import java.util.ArrayList;
import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;

public class TesteComponentesJDBC {
	
	public static void main(String[] args) {
		
		Monitorado m1 = new Monitorado();
		//m1.setId(1000);
		m1.setScreen_name("@Danyllo_Wagner");
		m1.setTwitterId((long) 98765432);
		
//		Monitorado m2 = new Monitorado();
//		//m2.setId(2);
//		m2.setScreen_name("@Moacir_Lopes");
//		m2.setTwitterId((long) 23456789);
//		
//		Monitorado m3 = new Monitorado();
//		//m3.setId(3);
//		m3.setScreen_name("@joaquim");
//		m3.setTwitterId((long) 23456789);
//		
//		Monitorado m4 = new Monitorado();
//		//m4.setId(4);
//		m4.setScreen_name("@jonas");
//		m4.setTwitterId((long) 23456789);
		
		MonitoradoDAOImpl mdao = new MonitoradoDAOImpl();
		
		ArrayList<Monitorado> listaMonitorados = new ArrayList<Monitorado>(); 
		listaMonitorados.add(m1);
//		listaMonitorados.add(m2);
//		listaMonitorados.add(m3);
//		listaMonitorados.add(m4);
		
		try {
			mdao.create(listaMonitorados);
		} catch (Exception e) {
			System.out.println("Erro na criação: " + e.toString());			
		}
		
		
	}

}
