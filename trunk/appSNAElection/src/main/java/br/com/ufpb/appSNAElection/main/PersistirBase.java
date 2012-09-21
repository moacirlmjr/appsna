package br.com.ufpb.appSNAElection.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAElection.model.dao.TermoDAO;
import br.com.ufpb.appSNAElection.model.dao.TermoDAOImpl;
import br.com.ufpb.appSNAElection.util.EntradaConfiguration;

public class PersistirBase {
	public static void main(String[] args) {
		try {
			EntradaConfiguration ec = new EntradaConfiguration();
			
			Monitorado m = new Monitorado();
			Termo t = new Termo();
			List<Termo> termos = new ArrayList<Termo>();
			MonitoradoDAO mDAO = new MonitoradoDAOImpl();
			TermoDAO tDAO = new TermoDAOImpl();
			
			int count = 1;
			for (String key : ec.getKeys()) {
				
				String valor = ec.getEntrada(key);
				if (key.contains("screenName")) {
					m.setScreen_name(valor);
				} else if (key.contains("userid")) {
					m.setTwitterId(Long.valueOf(valor));
				} else if (key.contains("termos")) {
					String termosArray[] = valor.split(",");
					for(String ter : termosArray){
						t = new Termo();
						t.setConteudo(ter);
						termos.add(t);
					}
				}
				
				if(count == 3){
					Long idMonitoramento = mDAO.create(m);
					for(Termo termoBd : termos){
						termoBd.setMonitorado_id(idMonitoramento);
					}
					tDAO.create(termos);
					m = new Monitorado();
				}
				count++;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
