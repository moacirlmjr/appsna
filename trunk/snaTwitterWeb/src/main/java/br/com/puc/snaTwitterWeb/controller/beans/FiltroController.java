package br.com.puc.snaTwitterWeb.controller.beans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.swing.JFrame;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import processing.core.PApplet;
import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.snaTwitterWeb.util.FacesUtil;

@ManagedBean(name = "filtroController")
@ViewScoped
public class FiltroController implements Serializable {

	private Filtro filtro;
	private List<Filtro> listFiltros;
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

	public void excluirFiltro(ActionEvent ev) throws Exception {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			String filtroId = params.get("filtro");
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			Filtro f = filtroDAO.findById(Long.parseLong(filtroId));
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

	public static void main(String[] args) {
		ProjectController pc = Lookup.getDefault().lookup(
				ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();

		// Get controllers and models
		ImportController importController = Lookup.getDefault().lookup(
				ImportController.class);

		// Import file
		Container container;
		try {
			File file = new File("E:\\DESENVOLVIMENTO\\appSNA\\rede_1404433223108.graphml");
			container = importController.importFile(file);
			container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED); // Force
																		// DIRECTED
			container.setAllowAutoNode(false); // Don't create missing nodes
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		//Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);
        
        //See if graph is well imported
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        DirectedGraph graph = graphModel.getDirectedGraph();
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());

        //Layout for 1 minute
        
        PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel previewModel = previewController.getModel();
        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.FALSE);
        previewModel.getProperties().putValue(PreviewProperty.DIRECTED, Boolean.TRUE);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
        previewModel.getProperties().putValue(PreviewProperty.SHOW_EDGES, Boolean.TRUE);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 100);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
        previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.WHITE);
        previewController.refreshPreview();

        //New Processing target, get the PApplet
        ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();
        applet.init();

        //Refresh the preview and reset the zoom
        previewController.render(target);

        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.initAlgo();
        layout.resetPropertiesValues();
        layout.setOptimalDistance(200f);
        for (int i = 0; i < 100 && layout.canAlgo(); i++) {
        	layout.goAlgo();
        }
        layout.endAlgo();
        
        target.refresh();
        target.resetZoom();
        
        //Get Centrality
        RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel, attributeModel);
         
        //Rank color by Degree
        Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        AbstractColorTransformer colorTransformer = (AbstractColorTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
        colorTransformer.setColors(new Color[]{new Color(0xd44114), new Color(0xea0d0d)});
        rankingController.transform(degreeRanking,colorTransformer);
         
        //Rank size by centrality
        AttributeColumn centralityColumn = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS);
        Ranking centralityRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, centralityColumn.getId());
        AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        sizeTransformer.setMinSize(30);
        sizeTransformer.setMaxSize(300);
        rankingController.transform(centralityRanking,sizeTransformer);
         

        //Add the applet to a JFrame and display
        JFrame frame = new JFrame("Test Preview");
        frame.setLayout(new BorderLayout());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(applet, BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
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

}
