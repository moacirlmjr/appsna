package br.com.ufpb.appSNAElection.main;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.MyNode;
import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class GraphMLPrefeitos {

	public GraphMLPrefeitos(String nomeDoMeuGrafo, String path,
			boolean direcionado) throws Exception {
		criaCabecalho(direcionado);
		criarNodos();
		criarArestas();
		criaArquivo(nomeDoMeuGrafo);
		salvarArquivo(path);
	}

	public void criaCabecalho(boolean direcionado) {

		XmlTO field1 = new XmlTO("name", true, "name", TypeEnum.STRING_TYPE);
		XmlTO field2 = new XmlTO("weight", false, "weight", TypeEnum.INT_TYPE);

		List<XmlTO> listaTO = new ArrayList<XmlTO>();
		listaTO.add(field1);
		listaTO.add(field2);

		XMLUtil.generateHeader(listaTO, direcionado);
	}

	private void criarNodos() throws Exception {

		XMLUtil.addSpace(2);

		MonitoradoDAOImpl mDAO = new MonitoradoDAOImpl();
		List<RelatorioOcorrenciasTO> listRO = mDAO.listRelatorioOcorrencia();
		String sourceAtual = "";

		MyNode n1 = null;
		MyNode n2 = null;
		
		List<String> listaNodes = new ArrayList<String>();

		for (RelatorioOcorrenciasTO roTO : listRO) {
			if (!sourceAtual.equals(roTO.getSource())) {
				sourceAtual = roTO.getSource();
				n1 = new MyNode();
				n1.setNome(roTO.getSource());
				XMLUtil.generateNodes(roTO.getSource());
				listaNodes.add(roTO.getSource());
			}

			n2 = new MyNode();
			n2.setNome(roTO.getTarget());
			if(verificar(roTO.getTarget(), listaNodes)){
				listaNodes.add(roTO.getTarget());
				XMLUtil.generateNodes(roTO.getTarget());
			}
		}

	}

	private void criarArestas() throws Exception {

		XMLUtil.addSpace(2);

		MonitoradoDAOImpl mDAO = new MonitoradoDAOImpl();
		List<RelatorioOcorrenciasTO> listRO = mDAO.listRelatorioOcorrencia();

		for (RelatorioOcorrenciasTO roTO : listRO) {
			XMLUtil.generateEdges(roTO.getTarget(), roTO.getSource(),
					roTO.getWeight());
		}

	}

	private void criaArquivo(String nomeDoMeuGrafo) {
		XMLUtil.fechaArquivo();
		XMLUtil.salvarXML(nomeDoMeuGrafo);

	}

	private void salvarArquivo(String path) {
		XMLUtil.salvarXML(path);
	}

	public static void main(String[] args) throws Exception {

		new GraphMLPrefeitos("grafoPrefeitos.graphml", Constantes.DIR_APPSNA
				+ "grafoPrefeitos.graphml", false);

	}

	private boolean verificar(String id, List<String> lista) {

		for (String teste : lista) {
			if (id.equals(teste)) {
				return false;
			}
		}

		return true;
	}
}
