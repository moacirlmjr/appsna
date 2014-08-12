package br.com.puc.snaTwitterWeb.threads;

import java.io.File;
import java.util.List;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.plugin.graph.GiantComponentBuilder.GiantComponentFilter;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.GraphExporter;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.partition.api.Partition;
import org.gephi.partition.api.PartitionController;
import org.gephi.partition.plugin.NodeColorTransformer;
import org.gephi.plugins.layout.noverlap.NoverlapLayout;
import org.gephi.plugins.layout.noverlap.NoverlapLayoutBuilder;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.Degree;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.gephi.statistics.plugin.PageRank;
import org.gephi.statistics.plugin.builder.DegreeBuilder;
import org.gephi.statistics.plugin.builder.ModularityBuilder;
import org.gephi.statistics.plugin.builder.PageRankBuilder;
import org.gephi.statistics.spi.Statistics;
import org.openide.util.Lookup;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.Filtro.TipoDistribuicao;
import br.com.puc.appSNA.model.beans.Filtro.TipoRankColor;
import br.com.puc.appSNA.model.beans.Filtro.TipoRankSize;
import br.com.puc.appSNA.model.beans.to.MencaoTO;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.model.dao.StatusDAO;
import br.com.puc.appSNA.model.dao.StatusDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;

public class GerarGraphMLByFiltro implements Runnable {

	private Filtro filtro;

	@Override
	public void run() {
		AppSNALog.info("Entrou na Tread - Filtro: " + filtro.toString());
		StatusDAO statusDAO = new StatusDAOImpl();
		try {
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			List<MencaoTO> list = statusDAO.listByFiltro(filtro);

			filtro.setStatus("GERANDOGRAPHML");
			filtroDAO.update(filtro);

			// Init a project - and therefore a workspace
			ProjectController pc = Lookup.getDefault().lookup(
					ProjectController.class);
			pc.newProject();

			GraphModel graphModel = Lookup.getDefault()
					.lookup(GraphController.class).getModel();

			for (MencaoTO mencao : list) {
				Long idUserOrigem = mencao.getIdTwitterOrigem();
				Long idUserDestino = mencao.getIdTwitterDestino();
				Integer qteMencoes = mencao.getQteMencoes();

				Node n0 = graphModel.factory().newNode(idUserOrigem + "");
				n0.getNodeData().setLabel(mencao.getOrigem());

				Node n1 = graphModel.factory().newNode(idUserDestino + "");
				n1.getNodeData().setLabel(mencao.getDestino());
				Edge e1 = null;
				Graph graph = null;
				if (filtro.isDirecionado()) {
					graph = graphModel.getDirectedGraph();
					boolean teste1 = true, teste2 = true;
					for (Node n : graph.getNodes().toArray()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n0.getAttributes().getValue(
										"id")))) {
							n0 = n;
							teste1 = false;
							break;
						}
					}

					if (teste1) {
						graph.addNode(n0);
					}

					for (Node n : graph.getNodes().toArray()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n1.getAttributes().getValue(
										"id")))) {
							n1 = n;
							teste2 = false;
							break;
						}
					}

					if (teste2) {
						graph.addNode(n1);
					}
					
					e1 = graphModel.factory().newEdge(n0, n1, qteMencoes, true);
					graph.addEdge(e1);
				} else {
					graph = graphModel.getUndirectedGraph();
					boolean teste1 = true, teste2 = true;
					for (Node n : graph.getNodes().toArray()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n0.getAttributes().getValue(
										"id")))) {
							n0 = n;
							teste1 = false;
							break;
						}
					}
					if (teste1) {
						graph.addNode(n0);
					}

					for (Node n : graph.getNodes().toArray()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n1.getAttributes().getValue(
										"id")))) {
							n1 = n;
							teste2 = false;
							break;
						}
					}

					if (teste2) {
						graph.addNode(n1);
					}
					e1 = graphModel.factory()
							.newEdge(n0, n1, qteMencoes, false);
					graph.addEdge(e1);
				}
			}

			filtro.setStatus("CALCULANDOMETRICAS");
			filtroDAO.update(filtro);
			Graph graph = null;
			if (filtro.isDirecionado()) {
				graph = graphModel.getDirectedGraph();
			} else {
				graph = graphModel.getUndirectedGraph();
			}
			System.out.println("Nodes: " + graph.getNodeCount());
			System.out.println("Edges: " + graph.getEdgeCount());

			// Export full graph
			AttributeModel attributeModel = Lookup.getDefault()
					.lookup(AttributeController.class).getModel();

			if (filtro.isCentralidade()) {
				// Get Centrality
				GraphDistance distance = new GraphDistance();
				distance.setDirected(true);
				distance.execute(graphModel, attributeModel);
			}

			Statistics statistics = null;
			if (filtro.isGrau()) {
				// Get Degree
				DegreeBuilder dBuilder = new DegreeBuilder();
				statistics = dBuilder.getStatistics();
				statistics.execute(graphModel, attributeModel);
			}

			if (filtro.isPageRank()) {
				// Get PageRank
				PageRankBuilder pgb = new PageRankBuilder();
				statistics = pgb.getStatistics();
				statistics.execute(graphModel, attributeModel);

			}

			if (filtro.isModularity()) {
				ModularityBuilder mdb = new ModularityBuilder();
				statistics = mdb.getStatistics();
				statistics.execute(graphModel, attributeModel);
			}

			RankingController rankingController = Lookup.getDefault().lookup(
					RankingController.class);
			AttributeColumn column = null;

			if (filtro.getTipoRankSize() != null) {
				if (filtro.getTipoRankSize().equals(TipoRankSize.PAGERANK)) {
					column = attributeModel.getNodeTable().getColumn(
							PageRank.PAGERANK);
				} else if (filtro.getTipoRankSize().equals(TipoRankSize.GRAU)
						&& filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.INDEGREE);
				} else if (filtro.getTipoRankSize().equals(TipoRankSize.GRAU)
						&& !filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.DEGREE);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankSize.CENTRAILIDADE)) {
					column = attributeModel.getNodeTable().getColumn(
							GraphDistance.BETWEENNESS);
				}

				AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController
						.getModel().getTransformer(Ranking.NODE_ELEMENT,
								Transformer.RENDERABLE_SIZE);
				sizeTransformer.setMinSize(10);
				sizeTransformer.setMaxSize(100);

				Ranking ranking = rankingController.getModel().getRanking(
						Ranking.NODE_ELEMENT, column.getId());
				rankingController.transform(ranking, sizeTransformer);
			}

			if (filtro.getTipoRankColor() != null) {
				if (filtro.getTipoRankColor().equals(
						TipoRankColor.CENTRALIDADE)) {
					column = attributeModel.getNodeTable().getColumn(
							GraphDistance.BETWEENNESS);
				} else if (filtro.getTipoRankColor().equals(TipoRankColor.GRAU)
						&& filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.INDEGREE);
				} else if (filtro.getTipoRankColor().equals(TipoRankColor.GRAU)
						&& !filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.DEGREE);
				} else if (filtro.getTipoRankColor().equals(
						TipoRankColor.MODULARITY)) {
					column = attributeModel.getNodeTable().getColumn(
							Modularity.MODULARITY_CLASS);
				} else if (filtro.getTipoRankColor().equals(
						TipoRankColor.PAGERANK)) {
					column = attributeModel.getNodeTable().getColumn(
							PageRank.PAGERANK);
				}

				PartitionController partitionController = Lookup.getDefault()
						.lookup(PartitionController.class);
				Partition p = partitionController.buildPartition(column, graph);
				NodeColorTransformer nodeColorTransformer = new NodeColorTransformer();
				nodeColorTransformer.randomizeColors(p);
				partitionController.transform(p, nodeColorTransformer);
			}

			if (filtro.getTipoDistribuicao() != null) {
				if (filtro.getTipoDistribuicao().equals(
						TipoDistribuicao.FORCE_ATLAS)) {
					ForceAtlas2 layout = new ForceAtlas2(
							new ForceAtlas2Builder());
					layout.setGraphModel(graphModel);
					layout.resetPropertiesValues();
					layout.setAdjustSizes(true);
					layout.setOutboundAttractionDistribution(true);

					layout.initAlgo();
					for (int i = 0; i < 100 && layout.canAlgo(); i++) {
						layout.goAlgo();
					}
					layout.endAlgo();

				} else if (filtro.getTipoDistribuicao().equals(
						TipoDistribuicao.YIFAN_HU)) {
					YifanHuLayout layout = new YifanHuLayout(null,
							new StepDisplacement(1f));
					layout.setGraphModel(graphModel);
					layout.resetPropertiesValues();
					layout.setOptimalDistance(200f);

					layout.initAlgo();
					for (int i = 0; i < 100 && layout.canAlgo(); i++) {
						layout.goAlgo();
					}
					layout.endAlgo();
				}

				NoverlapLayout layout2 = new NoverlapLayout(
						new NoverlapLayoutBuilder());
				layout2.setGraphModel(graphModel);
				layout2.resetPropertiesValues();

				layout2.initAlgo();
				for (int i = 0; i < 100 && layout2.canAlgo(); i++) {
					layout2.goAlgo();
				}
				layout2.endAlgo();
			}

			// Export to GRaphml
			ExportController ec = Lookup.getDefault().lookup(
					ExportController.class);
			ec.exportFile(new File(Constantes.DIR_GRAPHML
					+ filtro.getEndGraphml()));
			
			FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
	        GiantComponentFilter gcf = new GiantComponentFilter();
	        gcf.init(graphModel.getGraph());
	        Query query = filterController.createQuery(gcf);
	        GraphView view = filterController.filter(query);
	        graphModel.setVisibleView(view);
	        
	        GraphExporter exporter = (GraphExporter) ec.getExporter("gexf");     //Get GEXF exporter
	        exporter.setExportVisible(true);  //Only exports the visible (filtered) graph
	        Workspace workspace = pc.getCurrentWorkspace();
	        exporter.setWorkspace(workspace);
	        ec.exportFile(new File(Constantes.DIR_GRAPHML + "CG_"
					+ filtro.getEndGraphml()),exporter);

			filtro.setStatus("TERMINADO");
			filtroDAO.update(filtro);
		} catch (Exception e) {
			filtro.setStatus("ERRO");
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			try {
				filtroDAO.update(filtro);
			} catch (Exception e1) {
				AppSNALog.error(e1);
			}
			AppSNALog.error(e);
		}
		AppSNALog.info("Terminou Tread - Filtro: " + filtro.toString());
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

}
