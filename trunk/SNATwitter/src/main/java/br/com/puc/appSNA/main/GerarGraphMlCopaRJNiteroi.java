package br.com.puc.appSNA.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.beans.to.EdgeTO;
import br.com.puc.appSNA.model.beans.to.NodeTO;
import br.com.puc.appSNA.model.dao.UsuarioDAO;
import br.com.puc.appSNA.model.dao.UsuarioDAOImpl;
import br.com.puc.appSNA.util.PaperUtil;

public class GerarGraphMlCopaRJNiteroi {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("copaResults.csv");
		BufferedReader in = new BufferedReader(fr);
		String line;
		
		UsuarioDAO userDAO = new UsuarioDAOImpl();
		List<NodeTO> listNodeTo = new LinkedList<>();
		List<EdgeTO> listEdgeTo = new LinkedList<>();
		
		while ((line = in.readLine()) != null) {
			String[] resultsArray = line.split(";");
			Long idUserOrigem = Long.valueOf(resultsArray[0]);
			Long idUserDestino = Long.valueOf(resultsArray[1]);
			Integer qteMencoes = Integer.valueOf(resultsArray[2]);
			
			Usuario userOrigem = userDAO.findById(idUserOrigem);
			Usuario userDestino = userDAO.findById(idUserDestino);
			
			listNodeTo.add(new NodeTO(idUserOrigem, userOrigem.getScreename()));
			listNodeTo.add(new NodeTO(idUserDestino, userDestino.getScreename()));
			
			listEdgeTo.add(new EdgeTO(idUserOrigem, idUserDestino, qteMencoes));
		}
		
		PaperUtil.criaCabecalho(true);
		PaperUtil.criarNodos(listNodeTo);
		PaperUtil.criarArestas(listEdgeTo);
		PaperUtil
				.criaArquivo("C:\\Users\\Moacir\\Desktop\\GrafoCopaRJNiteroi.graphml");
	}
}
