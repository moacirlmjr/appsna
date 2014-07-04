package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
		FiltroDAO filtroDAO = new FiltroDAOImpl();
		try {
			listFiltros = filtroDAO.list();
		} catch (Exception e) {
			AppSNALog.error(e);
		}
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
			if(f != null){
				filtroDAO.remove(f);
				File file = new File(Constantes.DIR_GRAPHML + f.getEndGraphml());
				file.delete();
			}
			
			FacesUtil.registrarFacesMessage("Filtro Excluido com sucesso", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage("Ocorreu um erro ao remover o filtro", FacesMessage.SEVERITY_ERROR);
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
