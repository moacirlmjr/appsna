package br.com.ufpb.appSNA.main;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Graph;
import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAElection.view.ConfigureView;

public class TestGraphPrefeitos {
	
	public static void main(String[] args) {
		MonitoradoDAOImpl mDAO = new MonitoradoDAOImpl();
		List<RelatorioOcorrenciasTO> lista = new ArrayList<RelatorioOcorrenciasTO>();
		
		try {
			lista = mDAO.listRelatorioOcorrencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Graph g = new Graph(lista);
		ConfigureView cv = new ConfigureView();
		cv.configuration(g.getG());	
		
	}

}
