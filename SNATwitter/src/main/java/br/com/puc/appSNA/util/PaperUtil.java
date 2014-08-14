package br.com.puc.appSNA.util;

import java.util.ArrayList;
import java.util.List;

import br.com.puc.appSNA.model.beans.to.EdgeTO;
import br.com.puc.appSNA.model.beans.to.NodeTO;
import br.com.puc.appSNA.model.beans.to.XmlTO;
import br.com.puc.appSNA.model.enumeration.TypeEnum;

public class PaperUtil {

	public static void criaCabecalho(boolean direcionado) {

		XmlTO field1 = new XmlTO("id", true, "id", TypeEnum.INT_TYPE);
		XmlTO field2 = new XmlTO("name", true, "name", TypeEnum.STRING_TYPE);
		XmlTO field3 = new XmlTO("qteMencoes", false, "qteMencoes",
				TypeEnum.INT_TYPE);

		List<XmlTO> listaTO = new ArrayList<XmlTO>();
		listaTO.add(field1);
		listaTO.add(field2);
		listaTO.add(field3);

		XMLUtil.generateHeader(listaTO, direcionado);
	}

	public static void criarNodos(List<NodeTO> list) throws Exception {

		XMLUtil.addSpace(2);
		List<String> listaNodes = new ArrayList<>();
		for (NodeTO node : list) {
			if (verificar(node.getId_node(), listaNodes)) {
				XMLUtil.generateNodes(node.getId_node(), node.getNome());
				listaNodes.add(node.getId_node());
			}
		}
	}

	public static void criarArestas(List<EdgeTO> list) throws Exception {

		XMLUtil.addSpace(2);

		for (EdgeTO edge : list) {
			XMLUtil.generateEdges(edge.getId_source() + "", edge.getId_target()
					+ "", edge.getWeight());
		}

	}

	public static void criaArquivo(String nomeDoMeuGrafo) {
		XMLUtil.fechaArquivo();
		XMLUtil.salvarXML(nomeDoMeuGrafo);

	}

	public static boolean verificar(String id, List<String> lista) {

		for (String teste : lista) {
			if (id.equals(teste)) {
				return false;
			}
		}

		return true;
	}
}
