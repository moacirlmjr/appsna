package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.gephi.graph.api.Attributes;
import org.gephi.graph.api.DirectedGraph;
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

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.to.NodeTO;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.snaTwitterWeb.util.FacesUtil;

@ManagedBean(name = "filtroController")
@SessionScoped
public class FiltroController implements Serializable {

	private Filtro filtro;
	private List<Filtro> listFiltros;
	private List<NodeTO> listNodes;
	private StreamedContent graphml;

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

	public void carregarMetricasNos(Filtro f) {
		try {
			if (f != null) {
				filtro = f;
			}
			listNodes = new ArrayList<>();
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

			DirectedGraph dg = graphModel.getDirectedGraph();
			for(Node node:dg.getNodes()){
				NodeTO nodeTO = new NodeTO();
				nodeTO.setId_node(Long.parseLong((String) node.getAttributes().getValue("id")));
				nodeTO.setNome((String) node.getAttributes().getValue("label"));
				
				if(filtro.isGrau() && filtro.isDirecionado()){
					nodeTO.setGrau((Integer) node.getAttributes().getValue("degree"));
					nodeTO.setGrauEntrada((Integer) node.getAttributes().getValue("outdegree"));
					nodeTO.setGrauSaida((Integer) node.getAttributes().getValue("indegree"));
				}else if(filtro.isGrau()){
					nodeTO.setGrau((Integer) node.getAttributes().getValue("degree"));
				}
				
				if(filtro.isCentralidade()){
					nodeTO.setBetweenness((Double) node.getAttributes().getValue("betweenesscentrality"));
					nodeTO.setCloseness((Double) node.getAttributes().getValue("closnesscentrality"));
					nodeTO.setEccentricity((Double) node.getAttributes().getValue("eccentricity"));
				}
				
				if(filtro.isModularity()){
					nodeTO.setModularidade((Integer) node.getAttributes().getValue("modularity_class"));
				}
				
				if(filtro.isPageRank()){
					nodeTO.setPageRank((Double) node.getAttributes().getValue("pageranks"));
				}
				
				listNodes.add(nodeTO);
			}
			System.out.println();
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao carregar a rede",
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

}
