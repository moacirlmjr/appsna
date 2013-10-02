package br.com.ufpb.appsna.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import br.com.ufpb.appSNA.model.beans.to.EdgeTO;
import br.com.ufpb.appSNA.model.beans.to.NodeTO;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class TesteParseGraphML {

	public static void main(String[] args) throws Exception {

		List<NodeTO> listaNodes = new ArrayList<NodeTO>();
		List<EdgeTO> listaEdges = new ArrayList<EdgeTO>();
		
		FileReader fr = new FileReader(new File("C:\\Users\\User\\Desktop\\rel_Universidades_Teste_Comunidades.net"));
		BufferedReader in = new BufferedReader(fr);
		String line;
		String marcador = "";
		while ((line = in.readLine()) != null) {
			
			NodeTO n = new NodeTO();
			EdgeTO e = new EdgeTO();
			
			if(line.equals("*Vertices 10000") || line.equals("*Edges")){
				marcador = line;
				continue;
			}
			if(!line.equals("")){
				if(marcador.equals("*Vertices 10000")){
					String lineArray[] = line.split(" ");
					n.setId_node(Integer.valueOf(lineArray[0]));
					n.setNome(lineArray[1].replaceAll("\"", ""));
					listaNodes.add(n);
				}else if(marcador.equals("*Edges")){
					String lineArray[] = line.split(" ");
					e.setId_source(Integer.valueOf(lineArray[0]));
					e.setId_target(Integer.valueOf(lineArray[1]));
					e.setWeight(Integer.valueOf(lineArray[2]));
					listaEdges.add(e);
				}
			}
		}


		XmlTO x1 = new XmlTO("name", true, "name", TypeEnum.STRING_TYPE);
		XmlTO x2 = new XmlTO("id_label", true, "id_label", TypeEnum.INT_TYPE);
		XmlTO x3 = new XmlTO("weight", false, "weight", TypeEnum.INT_TYPE);

		List<XmlTO> listXmlTO = new ArrayList<XmlTO>();

		listXmlTO.add(x1);
		listXmlTO.add(x2);
		listXmlTO.add(x3);
		
		try {
			XMLUtil.generateHeader(listXmlTO, true);

			for (NodeTO node : listaNodes) {
				XMLUtil.generateNodes(node.getId_node(), node.getNome());
			}

			for (EdgeTO edge : listaEdges) {
				XMLUtil.generateEdges(edge.getId_source()+"", edge.getId_target()+"",	edge.getWeight());
			}

			XMLUtil.fechaArquivo();
			XMLUtil.salvarXML("grafoParser.graphml");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
