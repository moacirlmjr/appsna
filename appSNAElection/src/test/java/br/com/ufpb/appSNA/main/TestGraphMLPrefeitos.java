package br.com.ufpb.appSNA.main;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.MyNode;
import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class TestGraphMLPrefeitos {

	public TestGraphMLPrefeitos(String nomeDoMeuGrafo, String path,
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

		for (RelatorioOcorrenciasTO roTO : listRO) {
			if (!sourceAtual.equals(roTO.getSource())) {
				sourceAtual = roTO.getSource();
				n1 = new MyNode();
				n1.setNome(roTO.getSource());
				XMLUtil.generateNodes(roTO.getSource());
			}

			n2 = new MyNode();
			n2.setNome(roTO.getTarget());
			XMLUtil.generateNodes(roTO.getTarget());
		}

	}

	private void criarArestas() throws Exception {

		XMLUtil.addSpace(2);

		MonitoradoDAOImpl mDAO = new MonitoradoDAOImpl();
		List<RelatorioOcorrenciasTO> listRO = mDAO.listRelatorioOcorrencia();
		
		for (RelatorioOcorrenciasTO roTO : listRO) {
			XMLUtil.generateEdges(roTO.getSource(), roTO.getTarget(),
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

		new TestGraphMLPrefeitos("grafoTeste.xml",
				"C:\\Users\\User\\Desktop\\grafoTeste2.xml", false);

	}
}
