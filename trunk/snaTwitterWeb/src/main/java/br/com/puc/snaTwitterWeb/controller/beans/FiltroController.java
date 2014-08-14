package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.to.EdgeTO;
import br.com.puc.appSNA.model.beans.to.NodeTO;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.snaTwitterWeb.threads.GerarGraphMLByFiltro;
import br.com.puc.snaTwitterWeb.util.FacesUtil;

@ManagedBean(name = "filtroController")
@SessionScoped
public class FiltroController implements Serializable {

	private Filtro filtro;
	private List<Filtro> listFiltros;
	private List<NodeTO> listNodes;
	private List<EdgeTO> listEdges;
	private List<NodeTO> listNodesTermos;
	private List<EdgeTO> listEdgesTermos;
	private StreamedContent graphml;
	private Integer nodeCount;
	private Integer edgeCount;

	private Integer nodeCountTermos;
	private Integer edgeCountTermos;

	private static final int ACTIVES_TASK = 2;
	private static final int NTHREADS = Runtime.getRuntime()
			.availableProcessors() * 8;
	private static final ExecutorService exec = Executors
			.newFixedThreadPool(NTHREADS);

	private TagCloudModel model;

	public FiltroController() {
		filtro = new Filtro();
	}

	public void carregarGraphml(ActionEvent ev) throws Exception {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String graphmlString = params.get("graphml");
		InputStream input = new FileInputStream(new File(Constantes.DIR_GRAPHML
				+ graphmlString));
		graphml = new DefaultStreamedContent(input, "application/graphml",
				graphmlString);
	}

	public void carregarGraphmlTermo(ActionEvent ev) throws Exception {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String graphmlString = params.get("graphml");
		InputStream input = new FileInputStream(new File(Constantes.DIR_GRAPHML
				+ graphmlString));
		graphml = new DefaultStreamedContent(input, "application/graphml",
				graphmlString);
	}

	public void excluirFiltro(Filtro f) throws Exception {
		try {
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			f = filtroDAO.findById(f.getId());
			if (f != null) {
				filtroDAO.remove(f);
				File file = new File(Constantes.DIR_GRAPHML + f.getEndGraphml());
				file.delete();
			}

			FacesUtil.registrarFacesMessage("Filtro Excluido com sucesso",
					FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao remover o filtro",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
	}

	public String carregarFiltro(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}

			return "viewGraph.jsf";
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "";
	}

	public String carregarFiltroTermo(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}

			return "viewGraphTermo.jsf";
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "";
	}

	public String carregarFiltroCG(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}

			return "viewGraphComponenteGigante.jsf";
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "";
	}

	public String carregarFiltroTermoCG(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}

			return "viewGraphTermoComponenteGigante.jsf";
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "";
	}

	public void carregarMetricasNos(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}

			// REDE DE MEÇÕES
			listNodes = new ArrayList<>();
			listEdges = new ArrayList<>();
			ProjectController pc = Lookup.getDefault().lookup(
					ProjectController.class);
			pc.newProject();
			Workspace workspace = pc.getCurrentWorkspace();

			ImportController importController = Lookup.getDefault().lookup(
					ImportController.class);

			Container container;
			File file = new File(Constantes.DIR_GRAPHML
					+ filtro.getEndGraphml());
			container = importController.importFile(file);

			if (filtro.isDirecionado()) {
				container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);
			} else {
				container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);
			}

			container.setAllowAutoNode(false);

			importController.process(container, new DefaultProcessor(),
					workspace);

			GraphModel graphModel = Lookup.getDefault()
					.lookup(GraphController.class).getModel();

			Graph dg = null;
			if (filtro.isDirecionado()) {
				dg = graphModel.getDirectedGraph();
			} else {
				dg = graphModel.getUndirectedGraph();
			}
			nodeCount = dg.getNodeCount();
			edgeCount = dg.getEdgeCount();
			for (Node node : dg.getNodes()) {
				NodeTO nodeTO = new NodeTO();
				nodeTO.setId_node((String) node.getAttributes()
						.getValue("id"));
				nodeTO.setNome((String) node.getAttributes().getValue("label"));

				if (filtro.isGrau() && filtro.isDirecionado()) {
					nodeTO.setGrau((Integer) node.getAttributes().getValue(
							"degree"));
					nodeTO.setGrauEntrada((Integer) node.getAttributes()
							.getValue("outdegree"));
					nodeTO.setGrauSaida((Integer) node.getAttributes()
							.getValue("indegree"));
				} else if (filtro.isGrau()) {
					nodeTO.setGrau((Integer) node.getAttributes().getValue(
							"degree"));
				}

				if (filtro.isCentralidade()) {
					nodeTO.setBetweenness((Double) node.getAttributes()
							.getValue("betweenesscentrality"));
					nodeTO.setCloseness((Double) node.getAttributes().getValue(
							"closnesscentrality"));
					nodeTO.setEccentricity((Double) node.getAttributes()
							.getValue("eccentricity"));
				}

				if (filtro.isModularity()) {
					nodeTO.setModularidade((Integer) node.getAttributes()
							.getValue("modularity_class"));
				}

				if (filtro.isPageRank()) {
					nodeTO.setPageRank((Double) node.getAttributes().getValue(
							"pageranks"));
				}

				listNodes.add(nodeTO);
			}

			for (Edge edge : dg.getEdges()) {
				EdgeTO edgeTO = new EdgeTO();
				edgeTO.setId_source((String) edge.getSource()
						.getAttributes().getValue("id"));
				edgeTO.setId_target((String) edge.getTarget()
						.getAttributes().getValue("id"));
				edgeTO.setWeight(((Float) edge.getWeight()).intValue());
				listEdges.add(edgeTO);
			}

			// REDE DE TERMOS
			listNodesTermos = new ArrayList<>();
			listEdgesTermos = new ArrayList<>();
			pc = Lookup.getDefault().lookup(ProjectController.class);
			pc.newProject();
			workspace = pc.getCurrentWorkspace();

			importController = Lookup.getDefault().lookup(
					ImportController.class);

			file = new File(Constantes.DIR_GRAPHML + "TEMA_"
					+ filtro.getEndGraphml());
			container = importController.importFile(file);

			container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);
			container.setAllowAutoNode(false);

			importController.process(container, new DefaultProcessor(),
					workspace);

			graphModel = Lookup.getDefault().lookup(GraphController.class)
					.getModel();

			dg = graphModel.getUndirectedGraph();
			nodeCountTermos = dg.getNodeCount();
			edgeCountTermos = dg.getEdgeCount();
			model = new DefaultTagCloudModel();
			for (Node node : dg.getNodes()) {
				NodeTO nodeTO = new NodeTO();
				nodeTO.setId_node((String) node.getAttributes()
						.getValue("id"));
				nodeTO.setNome((String) node.getAttributes().getValue("label"));

				nodeTO.setGrau((Integer) node.getAttributes()
						.getValue("degree"));

				nodeTO.setBetweenness((Double) node.getAttributes().getValue(
						"betweenesscentrality"));
				nodeTO.setCloseness((Double) node.getAttributes().getValue(
						"closnesscentrality"));

				nodeTO.setModularidade((Integer) node.getAttributes().getValue(
						"modularity_class"));

				nodeTO.setPageRank((Double) node.getAttributes().getValue(
						"pageranks"));

				listNodesTermos.add(nodeTO);
				model.addTag(new DefaultTagCloudItem(nodeTO.getNome(), nodeTO.getGrau()));
			}

			for (Edge edge : dg.getEdges()) {
				EdgeTO edgeTO = new EdgeTO();
				edgeTO.setId_source((String) edge.getSource()
						.getAttributes().getValue("id"));
				edgeTO.setId_target((String) edge.getTarget()
						.getAttributes().getValue("id"));
				edgeTO.setWeight(((Float) edge.getWeight()).intValue());
				listEdgesTermos.add(edgeTO);
			}

		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
	}

	public void reBuscar(Filtro filtro) {
		try {
			GerarGraphMLByFiltro parser = new GerarGraphMLByFiltro();
			parser.setFiltro(filtro);
			parser.setArquivoTermos(FacesUtil
					.obterCaminhoReal("termoASeremRetirados.txt"));
			exec.submit(parser);

			FacesUtil.registrarFacesMessage("Em breve sua rede será gerada",
					FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao salvar o filtro",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public List<Filtro> getListFiltros() {
		FiltroDAO filtroDAO = new FiltroDAOImpl();
		try {
			listFiltros = filtroDAO.list();
		} catch (Exception e) {
			AppSNALog.error(e);
		}
		return listFiltros;
	}

	public void setListFiltros(List<Filtro> listFiltros) {
		this.listFiltros = listFiltros;
	}

	public StreamedContent getGraphml() {
		return graphml;
	}

	public void setGraphml(StreamedContent graphml) {
		this.graphml = graphml;
	}

	public List<NodeTO> getListNodes() {
		return listNodes;
	}

	public void setListNodes(List<NodeTO> listNodes) {
		this.listNodes = listNodes;
	}

	public List<EdgeTO> getListEdges() {
		return listEdges;
	}

	public void setListEdges(List<EdgeTO> listEdges) {
		this.listEdges = listEdges;
	}

	public Integer getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(Integer nodeCount) {
		this.nodeCount = nodeCount;
	}

	public Integer getEdgeCount() {
		return edgeCount;
	}

	public void setEdgeCount(Integer edgeCount) {
		this.edgeCount = edgeCount;
	}

	public List<NodeTO> getListNodesTermos() {
		return listNodesTermos;
	}

	public void setListNodesTermos(List<NodeTO> listNodesTermos) {
		this.listNodesTermos = listNodesTermos;
	}

	public List<EdgeTO> getListEdgesTermos() {
		return listEdgesTermos;
	}

	public void setListEdgesTermos(List<EdgeTO> listEdgesTermos) {
		this.listEdgesTermos = listEdgesTermos;
	}

	public Integer getNodeCountTermos() {
		return nodeCountTermos;
	}

	public void setNodeCountTermos(Integer nodeCountTermos) {
		this.nodeCountTermos = nodeCountTermos;
	}

	public Integer getEdgeCountTermos() {
		return edgeCountTermos;
	}

	public void setEdgeCountTermos(Integer edgeCountTermos) {
		this.edgeCountTermos = edgeCountTermos;
	}

	public TagCloudModel getModel() {
		return model;
	}

	public void setModel(TagCloudModel model) {
		this.model = model;
	}

}
