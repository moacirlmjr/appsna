package br.com.puc.snaTwitterWeb.threads;

import java.io.File;
import java.util.List;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.partition.api.Partition;
import org.gephi.partition.api.PartitionController;
import org.gephi.partition.plugin.NodeColorTransformer;
import org.gephi.project.api.ProjectController;
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
				if (filtro.isDirecionado()) {
					e1 = graphModel.factory().newEdge(n0, n1, qteMencoes, true);
				} else {
					e1 = graphModel.factory()
							.newEdge(n0, n1, qteMencoes, false);
				}
				Graph graph = null;
				if (filtro.isDirecionado()) {
					graph = graphModel.getDirectedGraph();
					boolean teste1 = true, teste2 = true;
					for (Node n : graph.getNodes().toArray()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n0.getAttributes().getValue(
										"id")))) {
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
							teste2 = false;
							break;
						}
					}

					if (teste2) {
						graph.addNode(n1);
					}

					if (teste1 && teste2) {
						graph.addEdge(e1);
					}
				} else {
					graph = graphModel.getUndirectedGraph();
					boolean teste1 = true, teste2 = true;
					for (Node n : graph.getNodes()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n0.getAttributes().getValue(
										"id")))) {
							teste1 = false;
							break;
						}
					}
					if (teste1) {
						graph.addNode(n0);
					}

					for (Node n : graph.getNodes()) {
						if (((String) n.getAttributes().getValue("id"))
								.equals(((String) n1.getAttributes().getValue(
										"id")))) {
							teste2 = false;
							break;
						}
					}

					if (teste2) {
						graph.addNode(n1);
					}
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
				if (filtro.getTipoRankSize().equals(
						TipoRankSize.RANKINGSIZEPAGERANK)) {
					column = attributeModel.getNodeTable().getColumn(
							PageRank.PAGERANK);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankSize.RANKINGSIZEGRAU)
						&& filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.INDEGREE);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankSize.RANKINGSIZEGRAU)
						&& !filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.DEGREE);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankSize.RANKINGSIZECENTRAILIDADE)) {
					column = attributeModel.getNodeTable().getColumn(
							GraphDistance.BETWEENNESS);
				}

				AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController
						.getModel().getTransformer(Ranking.NODE_ELEMENT,
								Transformer.RENDERABLE_SIZE);
				sizeTransformer.setMinSize(30);
				sizeTransformer.setMaxSize(300);

				Ranking ranking = rankingController.getModel().getRanking(
						Ranking.NODE_ELEMENT, column.getId());
				rankingController.transform(ranking, sizeTransformer);
			}

			if (filtro.getTipoRankColor() != null) {
				if (filtro.getTipoRankSize().equals(
						TipoRankColor.RANKINGCOLORCENTRAILIDADE)) {
					column = attributeModel.getNodeTable().getColumn(
							GraphDistance.BETWEENNESS);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankColor.RANKINGCOLORGRAU)
						&& filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.INDEGREE);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankColor.RANKINGCOLORGRAU)
						&& !filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Degree.DEGREE);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankColor.RANKINGCOLORMODULARITY)
						&& !filtro.isDirecionado()) {
					column = attributeModel.getNodeTable().getColumn(
							Modularity.MODULARITY_CLASS);
				} else if (filtro.getTipoRankSize().equals(
						TipoRankColor.RANKINGCOLORPAGERANK)) {
					column = attributeModel.getNodeTable().getColumn(
							PageRank.PAGERANK);
				}
				
				PartitionController partitionController = Lookup.getDefault()
						.lookup(PartitionController.class);
				Partition p = partitionController.buildPartition(column,
						graph);
				NodeColorTransformer nodeColorTransformer = new NodeColorTransformer();
				nodeColorTransformer.randomizeColors(p);
				partitionController.transform(p, nodeColorTransformer);
			}
			

			// Export to GRaphml
			ExportController ec = Lookup.getDefault().lookup(
					ExportController.class);
			ec.exportFile(new File(Constantes.DIR_GRAPHML
					+ filtro.getEndGraphml()));

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
