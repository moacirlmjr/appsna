package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.snaTwitterWeb.threads.GerarGraphMLByFiltro;
import br.com.puc.snaTwitterWeb.util.FacesUtil;

@ManagedBean(name = "pesquisaController")
@SessionScoped
public class PesquisaController implements Serializable {

	private String screenName;
	private String biografia;
	private String localizacao;
	private String termo;
	private Date dataInicio;
	private Date dataFim;

	private Filtro filtro;

	private List<String> screenNames;
	private List<String> biografias;
	private List<String> localizacoes;
	private List<String> termos;

	private static final int ACTIVES_TASK = 2;
	private static final int NTHREADS = Runtime.getRuntime()
			.availableProcessors() * 8;
	private static final ExecutorService exec = Executors
			.newFixedThreadPool(NTHREADS);

	public PesquisaController() {
		screenNames = new ArrayList<>();
		biografias = new ArrayList<>();
		localizacoes = new ArrayList<>();
		termos = new ArrayList<>();
	}

	public void adicionar(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");

		if (action.equals("screenName")) {
			screenNames.add(screenName);
			screenName = "";
		} else if (action.equals("biografia")) {
			biografias.add(biografia);
			biografia = "";
		} else if (action.equals("localizacao")) {
			localizacoes.add(localizacao);
			localizacao = "";
		} else if (action.equals("termo")) {
			termos.add(termo);
			termo = "";
		}
	}

	public void remover(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		String valor = params.get("valor");

		if (action.equals("screenName")) {
			screenNames.remove(valor);
		} else if (action.equals("biografia")) {
			biografias.remove(valor);
		} else if (action.equals("localizacao")) {
			localizacoes.remove(valor);
		} else if (action.equals("termo")) {
			termos.remove(valor);
		}
	}

	public String buscar() {
		filtro = new Filtro();
		filtro.setDataCriacao(new Date());
		filtro.setEndGraphml("rede_" + filtro.getDataCriacao().getTime()
				+ ".graphml");
		filtro.setDataInicio(dataInicio);
		filtro.setDataFim(dataFim);
		filtro.setStatus("ANALISANDO");

		try {
			if (screenNames.size() != 0) {
				filtro.setScreenNames(screenNames.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (biografias.size() != 0) {
				filtro.setBiografias(biografias.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (localizacoes.size() != 0) {
				filtro.setLocalizacoes(localizacoes.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (termos.size() != 0) {
				filtro.setTermosStatus(termos.toString().replaceAll(", ", ",")
						.replaceAll("\\[", "").replaceAll("\\]", ""));
			}

			FiltroDAO filtroDAO = new FiltroDAOImpl();
			Long id = filtroDAO.create(filtro);
			filtro.setId(id);

			screenNames = new ArrayList<>();
			biografias = new ArrayList<>();
			localizacoes = new ArrayList<>();
			termos = new ArrayList<>();

			GerarGraphMLByFiltro parser = new GerarGraphMLByFiltro();
			parser.setFiltro(filtro);
			exec.submit(parser);

			FacesUtil.registrarFacesMessage(
					"Filtro Salvo com Sucesso. Em breve sua rede será gerada",
					FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao salvar o filtro",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "paginas/listFiltros.jsf";
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<String> getScreenNames() {
		return screenNames;
	}

	public void setScreenNames(List<String> screenNames) {
		this.screenNames = screenNames;
	}

	public List<String> getBiografias() {
		return biografias;
	}

	public void setBiografias(List<String> biografias) {
		this.biografias = biografias;
	}

	public List<String> getLocalizacoes() {
		return localizacoes;
	}

	public void setLocalizacoes(List<String> localizacoes) {
		this.localizacoes = localizacoes;
	}

	public List<String> getTermos() {
		return termos;
	}

	public void setTermos(List<String> termos) {
		this.termos = termos;
	}

	private static Integer getQteThreadsRunning() {
		return Integer.parseInt(exec.toString().split(",")[ACTIVES_TASK]
				.split("=")[1].replace(" ", ""));
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
}
