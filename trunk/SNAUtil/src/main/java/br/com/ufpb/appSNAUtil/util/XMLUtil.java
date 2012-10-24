package br.com.ufpb.appSNAUtil.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;

public class XMLUtil {

	public static StringBuffer arquivo = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
			"\n<!-- GraphML gerado automaticamente pela AppSNA -->").append(
			"\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");

	// M�todo utilizado para gerar o cabecalho do XML
	public static void generateHeader(List<XmlTO> listaDeCampos,
			boolean isDirected) {
		arquivo.append("\n\t<graph edgedefault=\"")
				.append(isDirected ? "Directed" : "Undirected").append("\">")
				.append("\n\n\t\t<!-- Esquema de Dados -->");
		for (XmlTO to : listaDeCampos) {
			arquivo.append("\n\t\t<key id=\"").append(to.getAttrId())
					.append("\" for=\"")
					.append(to.isForNode() ? "node" : "edge")
					.append("\" attr.name=\"").append(to.getAttrName())
					.append("\" attr.type=\"")
					.append(to.getAttrType().getType()).append("\"/>");
		}
		arquivo.append("\n\n\t\t<!-- Nos -->  ");
	}

	// M�todo utilizado para gerar o cabecalho do XML
	public static void generateHeader(String field1, String typeOfField1,
			String field2, String typeOfField2, boolean isDirected) {

		arquivo.append("\n\t<graph edgedefault=\"")
				.append(isDirected == true ? "Directed" : "Undirected")
				.append("\">").append("\n\n\t\t<!-- Esquema de Dados -->")
				.append("\n\t\t<key id=\"").append(field1)
				.append("\" for=\"node\" attr.name=\"").append(field1)
				.append("\" attr.type=\"").append(typeOfField1).append("\"/>")
				.append("\n\t\t<key id=\"").append(field2)
				.append("\" for=\"node\" attr.name=\"").append(field2)
				.append("\" attr.type=\"").append(typeOfField2).append("\"/>")
				.append("\n\n\t\t<!-- Nos -->  ");
	}

	// M�todo utilizado para gerar os nodos, ser� passado um id, para correla��o
	// nas arestas.
	public static void generateNodes(long userId, String name, String gender) {
		arquivo.append("\n\t\t<node id=\"").append(userId)
				.append("\">\n\t\t\t").append("<data key=\"name\">")
				.append(name).append("</data>")
				.append("\n\t\t\t<data key=\"gender\">").append(gender)
				.append("</data>\n\t\t</node>");
	}

	// M�todo sobrescrito.
	public static void generateNodes(long userId, String name) {
		arquivo.append("\n\t\t<node id=\"").append(userId).append("\">")
				.append("\n\t\t\t<data key=\"name\">").append(name)
				.append("</data>").append("\n\t\t</node>");
	}

	public static void generateNodes(String userId, String name) {
		arquivo.append("\n\t\t<node id=\"").append(userId).append("\">")
				.append("\n\t\t\t<data key=\"name\">").append(name)
				.append("</data>").append("\n\t\t</node>");
	}

	// M�todo sobrescrito.
	public static void generateNodes(String name) {
		arquivo.append("\n\t\t<node id=\"").append(name).append("\">")
				.append("\n\t\t\t<data key=\"name\">").append(name)
				.append("</data>").append("\n\t\t</node>");
	}

	// Nessa m�todo ser�o constru�dos as arestas correspondes ao id do usu�rio
	// de origem (idSource) ao id do usu�rio de destino (idTarget).
	public static void generateEdges(long idSource, long idTarget) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("\"></edge>\n\t\t\t");
	}
	
	public static void generateEdges(String idSource, String idTarget) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("\"></edge>\n\t\t\t");
	}

	// Metodo sobrescrito
	public static void generateEdges(int idSource, int idTarget, double weight,
			double capacity) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("<data key=\"weight\">").append(weight)
				.append("</data>\n\t\t</node>")
				.append("<data key=\"capacity\">").append(capacity)
				.append("</data>\n\t\t</node>").append("\"></edge>\n\t\t\t");
	}

	// Metodo sobrescrito
	public static void generateEdges(String idSource, String idTarget,
			int weight) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("\"><data key=\"weight\">").append(weight)
				.append("</data>\n\t\t").append("</edge>\n\t\t\t");
	}

	// Metodo para fechar o arquivo
	public static void fechaArquivo() {
		arquivo.append("\n\n\t</graph>\n</graphml>");
	}

	// Metodo para adicionar um espaco
	public static void addSpace(int num) {

		for (int n = 0; n < num; n++) {
			arquivo.append("\n");
		}

	}

	// M�todo utilizado para salvar o arquivo no disco
	public static void salvarXML(String path) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write(arquivo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro ao salvar arquivo...");
			System.exit(0);
		}

	}

}
