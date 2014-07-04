package br.com.puc.snaTwitterWeb.threads;

import java.util.LinkedList;
import java.util.List;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.beans.to.EdgeTO;
import br.com.puc.appSNA.model.beans.to.MencaoTO;
import br.com.puc.appSNA.model.beans.to.NodeTO;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.model.dao.StatusDAO;
import br.com.puc.appSNA.model.dao.StatusDAOImpl;
import br.com.puc.appSNA.model.dao.UsuarioDAO;
import br.com.puc.appSNA.model.dao.UsuarioDAOImpl;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.appSNA.util.PaperUtil;

public class GerarGraphMLByFiltro implements Runnable {

	private Filtro filtro;

	@Override
	public void run() {
		StatusDAO statusDAO = new StatusDAOImpl();
		try {
			List<MencaoTO> list = statusDAO.listByFiltro(filtro);

			UsuarioDAO userDAO = new UsuarioDAOImpl();
			List<NodeTO> listNodeTo = new LinkedList<>();
			List<EdgeTO> listEdgeTo = new LinkedList<>();

			for (MencaoTO mencao : list) {
				Long idUserOrigem = mencao.getIdTwitterOrigem();
				Long idUserDestino = mencao.getIdTwitterDestino();
				Integer qteMencoes = mencao.getQteMencoes();

				Usuario userOrigem;
				userOrigem = userDAO.findById(idUserOrigem);
				Usuario userDestino = userDAO.findById(idUserDestino);

				listNodeTo.add(new NodeTO(idUserOrigem, userOrigem
						.getScreename()));
				listNodeTo.add(new NodeTO(idUserDestino, userDestino
						.getScreename()));

				listEdgeTo.add(new EdgeTO(idUserOrigem, idUserDestino,
						qteMencoes));
			}

			PaperUtil.criaCabecalho(true);
			PaperUtil.criarNodos(listNodeTo);
			PaperUtil.criarArestas(listEdgeTo);
			PaperUtil.criaArquivo(Constantes.DIR_GRAPHML
					+ filtro.getEndGraphml());

			filtro.setStatus("TERMINADO");
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			filtroDAO.update(filtro);
		} catch (Exception e) {
			filtro.setStatus("ERRO");
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			try {
				filtroDAO.update(filtro);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

}
