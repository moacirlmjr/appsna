package br.com.puc.appSNA.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import br.com.puc.appSNA.model.beans.to.XmlTO;

public class XMLUtil {

	public static StringBuffer arquivo = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
			"\n<!-- GraphML gerado automaticamente pela AppSNA -->").append(
			"\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");

	// Método utilizado para gerar o cabecalho do XML
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

	public static void generateNodes(String name) {
		arquivo.append("\n\t\t<node id=\"").append(name).append("\">")
				.append("\n\t\t\t<data key=\"name\">").append(name)
				.append("</data>").append("\n\t\t</node>");
	}
	
	public static void generateNodes(long id_twitter, long id_label,
			String nome, int tipo, int qtde_negativas, int vizinhanca_all,
			int vizinhanca_simple, int neg_vizinhanca, float inadimplencia) {
		arquivo.append("\n\t\t<node id=\"").append(id_twitter).append("\">")
				.append("\n\t\t\t<data key=\"id_twitter\">").append(id_twitter)
				.append("</data>").append("\n\t\t\t<data key=\"id_label\">")
				.append(id_label).append("</data>")
				.append("\n\t\t\t<data key=\"name\">").append(nome)
				.append("</data>").append("\n\t\t\t<data key=\"tipo\">")
				.append(tipo).append("</data>")
				.append("\n\t\t\t<data key=\"qtde_negativas\">")
				.append(qtde_negativas).append("</data>")
				.append("\n\t\t\t<data key=\"vizinhanca_all\">")
				.append(vizinhanca_all).append("</data>")
				.append("\n\t\t\t<data key=\"vizinhanca_simple\">")
				.append(vizinhanca_simple).append("</data>")
				.append("\n\t\t\t<data key=\"neg_vizinhanca\">")
				.append(neg_vizinhanca).append("</data>")
				.append("\n\t\t\t<data key=\"inadimplencia\">")
				.append(inadimplencia).append("</data>")
				.append("\n\t\t</node>");
	}

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

	public static void generateEdges(int idSource, int idTarget, int total,
			double capacity) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("<data key=\"total\">").append(total)
				.append("</data>\n\t\t</node>")
				.append("<data key=\"capacity\">").append(capacity)
				.append("</data>\n\t\t</node>").append("\"></edge>\n\t\t\t");
	}

	// Metodo sobrescrito
	public static void generateEdges(String idSource, String idTarget,
			int total_mencoes) {
		arquivo.append("\n\t\t<edge source=\"").append(idSource)
				.append("\" target=\"").append(idTarget)
				.append("\">\n\t\t<data key=\"qteMencoes\">")
				.append(total_mencoes).append("</data>\n\t\t")
				.append("</edge>\n\t\t\t");
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

	// Método utilizado para salvar o arquivo no disco
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
